package ppti.presenter;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import ppti.data.Data;
import ppti.model.Player;
import ppti.model.PlayerListModel;
import ppti.model.PlayerPositionModel;
import ppti.view.GameConfigView;
import ppti.view.PlayerListView;
import ppti.view.PlayerPositionView;

import java.util.LinkedHashMap;
import java.util.Random;

// Présentateur pour la gestion de la liste des joueurs
public class PlayerListPresenter {
    private PlayerListView view;        // La vue associée à la liste des joueurs
    private PlayerListModel model;      // Le modèle contenant les données des joueurs
    private boolean running = true;     // Indicateur pour le fonctionnement des threads de réception
    private Stage primaryStage;         // La scène principale de l'application
    private int rotate;                 // L'angle de rotation de l'interface
    private GameConfigView view2;       // La vue de configuration du jeu

    // Constructeur du présentateur
    public PlayerListPresenter(PlayerListView view, PlayerListModel model, Stage primaryStage, int rotate, GameConfigView view2) {
        this.view = view;
        this.view2 = view2;
        this.model = model;
        this.primaryStage = primaryStage;
        this.rotate = rotate;
        initialize(); // Initialisation des composants
    }

    // Méthode pour initialiser les composants et les actions
    private void initialize() {
        // Configurer les actions des boutons
        view.getBackButton().setOnAction(event -> onBackButtonClicked());         // Action pour le bouton "Retour"
        view.getStartGameButton().setOnAction(event -> onStartGameButtonClicked()); // Action pour le bouton "Lancer la partie"
        primaryStage.setOnCloseRequest(event -> {
            Data.stopTcpReceiver();
            Data.stopUdpReceiver();
            running = false;
        });

        // Démarrer les récepteurs TCP et UDP pour écouter les messages entrants
        startTCPReceiver(); // Démarre le récepteur TCP
        startUDPReceiver(); // Démarre le récepteur UDP

        // Rafraîchir l'interface utilisateur
        refreshPlayerLists();       // Met à jour les listes des joueurs
        updatePlayerCountLabel();   // Met à jour le compteur de joueurs
    }

    // Action exécutée lorsque le bouton "Retour" est cliqué
    private void onBackButtonClicked() {
        Data.stopTcpReceiver();    // Arrête le récepteur TCP
        Data.stopUdpReceiver();    // Arrête le récepteur UDP
        running = false;           // Indique aux threads de réception de s'arrêter
        // Envoyer un message pour indiquer que la partie est annulée
        sendGameStatusUpdate("ANNULEE"); // Envoie une mise à jour du statut du jeu
        primaryStage.getScene().setRoot(view2.getView()); // Retourne à la vue de configuration du jeu
    }

    // Action exécutée lorsque le bouton "Lancer la partie" est cliqué
    @SuppressWarnings("unused")
	private void onStartGameButtonClicked() {
        Data.stopTcpReceiver();    // Arrête le récepteur TCP
        Data.stopUdpReceiver();    // Arrête le récepteur UDP
        running = false;           // Indique aux threads de réception de s'arrêter
        // Lancer la partie en créant le modèle, la vue et le présentateur pour la position des joueurs
        PlayerPositionModel playerPositionmodel = new PlayerPositionModel(model.getAcceptedPlayers(), view.getSelectedPlayer());
        PlayerPositionView playerPositionview = new PlayerPositionView(rotate);
        PlayerPositionPresenter playerPositionpresenter = new PlayerPositionPresenter(playerPositionmodel, playerPositionview, primaryStage, model);
        primaryStage.getScene().setRoot(playerPositionview.getLayout()); // Affiche la nouvelle vue
    }

    // Méthode pour rafraîchir les listes des joueurs dans l'interface utilisateur
    private void refreshPlayerLists() {
        Platform.runLater(() -> {
            // Rafraîchir la liste des joueurs en attente
            view.getPendingPlayerList().getChildren().clear();
            for (Player player : model.getPendingPlayers().values()) {
                HBox playerBox = createPendingPlayerBox(player); // Crée une boîte pour chaque joueur en attente
                view.getPendingPlayerList().getChildren().add(playerBox);
            }

            // Rafraîchir la liste des joueurs acceptés
            view.getAcceptedPlayerList().getChildren().clear();
            for (Player player : model.getAcceptedPlayers().values()) {
                HBox playerBox = createAcceptedPlayerBox(player); // Crée une boîte pour chaque joueur accepté
                view.getAcceptedPlayerList().getChildren().add(playerBox);
            }

            // Mettre à jour la ComboBox des joueurs pour la sélection
            view.getPlayerComboBox().getItems().clear();
            view.getPlayerComboBox().getItems().addAll(model.getAcceptedPlayers().keySet());

            if (!model.getAcceptedPlayers().isEmpty()) {
                view.getPlayerComboBox().getSelectionModel().selectFirst(); // Sélectionne le premier joueur par défaut
            }

            // Activer ou désactiver le bouton "Lancer" en fonction du nombre de joueurs acceptés
            view.getStartGameButton().setDisable(model.getAcceptedPlayerCount() != model.getPlayerCount());
        });
    }

    // Méthode pour créer une boîte pour un joueur en attente avec les boutons d'acceptation ou de rejet
    private HBox createPendingPlayerBox(Player player) {
        Label nameLabel = new Label(player.getName()); // Affiche le nom du joueur
        Button acceptButton = new Button("Oui");       // Bouton pour accepter le joueur
        Button rejectButton = new Button("Non");       // Bouton pour rejeter le joueur

        acceptButton.setOnAction(event -> {
            model.acceptPlayer(player.getName());          // Accepte le joueur dans le modèle
            sendAcceptPlayerMessage(player);               // Envoie un message TCP pour confirmer l'acceptation
            refreshPlayerLists();                          // Rafraîchit les listes des joueurs
            updatePlayerCountLabel();                      // Met à jour le compteur de joueurs
            checkGameFullStatus();                         // Vérifie si la partie est complète
        });

        rejectButton.setOnAction(event -> {
            model.rejectPlayer(player.getName());          // Rejette le joueur du modèle
            sendRejectPlayerMessage(player);               // Envoie un message TCP pour refuser le joueur
            refreshPlayerLists();                          // Rafraîchit les listes des joueurs
            updatePlayerCountLabel();                      // Met à jour le compteur de joueurs
            checkGameFullStatus();                         // Vérifie si la partie est complète
        });

        HBox hbox = new HBox(10, nameLabel, acceptButton, rejectButton); // Crée un HBox avec les composants
        hbox.setAlignment(Pos.CENTER); // Centre les éléments horizontalement
        return hbox;
    }

    // Méthode pour créer une boîte pour un joueur accepté avec un bouton pour l'exclure
    private HBox createAcceptedPlayerBox(Player player) {
        Label nameLabel = new Label(player.getName()); // Affiche le nom du joueur
        Button excludeButton = new Button("Exclure");  // Bouton pour exclure le joueur

        excludeButton.setOnAction(event -> {
            model.excludePlayer(player.getName());          // Exclut le joueur du modèle
            sendRejectPlayerMessage(player);                // Envoie un message TCP pour informer le joueur exclu
            refreshPlayerLists();                           // Rafraîchit les listes des joueurs
            updatePlayerCountLabel();                       // Met à jour le compteur de joueurs
            checkGameFullStatus();                          // Vérifie si la partie est complète
        });

        HBox hbox = new HBox(10, nameLabel, excludeButton); // Crée un HBox avec les composants
        hbox.setAlignment(Pos.CENTER); // Centre les éléments horizontalement
        return hbox;
    }

    // Méthode pour mettre à jour le label affichant le nombre de joueurs acceptés
    private void updatePlayerCountLabel() {
        String text = "Nombre total de joueurs : " + model.getAcceptedPlayerCount() + "/" + model.getPlayerCount();
        view.getPlayerCountLabel().setText(text); // Met à jour le texte du label
    }

    // Méthode pour vérifier si la partie est complète et mettre à jour le statut du jeu
    private void checkGameFullStatus() {
        if (model.getAcceptedPlayerCount() == model.getPlayerCount() && !model.isGameFull()) {
            // Si la partie est complète et le statut n'est pas encore mis à jour
            sendGameStatusUpdate("COMPLETE"); // Envoie une mise à jour du statut du jeu
            model.setGameFull(true);          // Met à jour le modèle
        } else if (model.getAcceptedPlayerCount() < model.getPlayerCount() && model.isGameFull()) {
            // Si la partie n'est plus complète
            sendGameStatusUpdate("ATTENTE");  // Envoie une mise à jour du statut du jeu
            model.setGameFull(false);         // Met à jour le modèle
        }
    }

    // Méthode pour envoyer un message TCP d'acceptation à un joueur
    private void sendAcceptPlayerMessage(Player player) {
        LinkedHashMap<String, String> attributs = new LinkedHashMap<>();
        Random random = new Random();
        attributs.put("idp", model.getGameAttributes().get("idp")); // Identifiant de la partie
        attributs.put("idj", "J" + random.nextInt(10_000));         // Identifiant du joueur
        String message = Data.writeMessage("ADP", attributs);       // Crée le message ADP
        Data.tcpSender(player.getIp(), 7777, message);              // Envoie le message au joueur via TCP
    }

    // Méthode pour envoyer un message TCP de refus à un joueur
    private void sendRejectPlayerMessage(Player player) {
        LinkedHashMap<String, String> attributs = new LinkedHashMap<>();
        attributs.put("idp", model.getGameAttributes().get("idp")); // Identifiant de la partie
        String message = Data.writeMessage("RDP", attributs);       // Crée le message RDP
        Data.tcpSender(player.getIp(), 7777, message);              // Envoie le message au joueur via TCP
    }

    // Méthode pour envoyer une mise à jour du statut de la partie via UDP
    private void sendGameStatusUpdate(String status) {
        LinkedHashMap<String, String> attributs = model.getGameAttributes();
        attributs.put("statut", status);                             // Met à jour le statut
        String message = Data.writeMessage("AMAJP", attributs);      // Crée le message AMAJP
        System.out.println(message);                                 // Affiche le message pour vérification
        String localInterface = Data.interfaceFinder();              // Trouve l'interface réseau locale
        Data.udpEmitter(message, localInterface);                    // Envoie le message via UDP
    }

    // Méthode pour démarrer le récepteur TCP dans un nouveau thread
    @SuppressWarnings("unchecked")
    private void startTCPReceiver() {
        new Thread(() -> {
            Data.tcpReceiver(7777, (message, playerIP) -> {
                Object[] messageList = Data.readMessage(message);
                LinkedHashMap<String, String> attributs = (LinkedHashMap<String, String>) messageList[1];

                if (messageList[0].toString().equals("DCP") && attributs.get("idp").equals(model.getGameAttributes().get("idp"))) {
                    String playerName = attributs.get("nomJ");
                    String identity = attributs.get("identite");
                    Random random = new Random();
                    String playerId = "J" + random.nextInt(10_000);

                    // Vérifier si le joueur peut être ajouté
                    if (canAddPlayer(identity)) {
                        Player player = new Player(playerName, playerIP, identity, playerId);
                        model.addPendingPlayer(player);                  // Ajoute le joueur à la liste des joueurs en attente
                        Platform.runLater(() -> refreshPlayerLists());   // Rafraîchit l'interface utilisateur
                    } else {
                        // Envoyer un message de refus au joueur
                        sendRejectPlayerMessage(new Player(playerName, playerIP, identity, playerId));
                    }
                }
            });

            while (running) {
                try {
                    Thread.sleep(100); // Pause pour éviter une boucle intensive
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); // Gère l'interruption du thread
                }
            }
        }).start();
    }

    // Méthode pour démarrer le récepteur UDP dans un nouveau thread
    @SuppressWarnings("unchecked")
    private void startUDPReceiver() {
        new Thread(() -> {
            Data.udpReceiver(Data.interfaceFinder(), (message) -> {
                Object[] messageList = Data.readMessage(message);
                LinkedHashMap<String, String> attributs = (LinkedHashMap<String, String>) messageList[1];

                if (messageList[0].toString().equals("RP")) {
                    // Gérer les requêtes de participation
                    LinkedHashMap<String, String> attributsUDP = new LinkedHashMap<>();
                    attributsUDP.put("idp", model.getGameAttributes().get("idp"));
                    attributsUDP.put("ip", Data.ipFinder());
                    attributsUDP.put("port", "7777");
                    attributsUDP.put("nomp", model.getGameAttributes().get("nomp"));
                    attributsUDP.put("nbj", model.getGameAttributes().get("nbj"));
                    attributsUDP.put("nbjrm", model.getGameAttributes().get("nbjrm"));
                    attributsUDP.put("nbjvm", model.getGameAttributes().get("nbjvm"));
                    attributsUDP.put("nbjrc", "" + model.countAcceptedPlayersByIdentity("JR"));
                    int totalBotAccepted = model.countAcceptedPlayersByIdentity("BOTF") +
                                           model.countAcceptedPlayersByIdentity("BOTM") +
                                           model.countAcceptedPlayersByIdentity("BOTE");
                    attributsUDP.put("nbjvc", "" + totalBotAccepted);
                    attributsUDP.put("espa", model.getGameAttributes().get("espa"));
                    attributsUDP.put("statut", "ATTENTE");

                    String messageUDP = Data.writeMessage("AMAJP", attributsUDP);
                    String localInterface = Data.interfaceFinder();

                    String identity = attributs.get("identite");
                    if (identity.equals("JR")) {
                        if (model.countAcceptedPlayersByIdentity(identity) < Integer.parseInt(model.getGameAttributes().get("nbjrm"))) {
                            Data.udpEmitter(messageUDP, localInterface); // Envoie la mise à jour
                        } else {
                            System.out.println("Plus de place pour les joueurs réels");
                        }
                    } else if (identity.equals("BOTF")) {
                        if (model.countAcceptedPlayersByIdentity(identity) < Integer.parseInt(model.getBotCounts().get("BOTF"))) {
                            Data.udpEmitter(messageUDP, localInterface); // Envoie la mise à jour
                        } else {
                            System.out.println("Plus de place pour les bots faibles");
                        }
                    } else if (identity.equals("BOTM")) {
                        if (model.countAcceptedPlayersByIdentity(identity) < Integer.parseInt(model.getBotCounts().get("BOTM"))) {
                            Data.udpEmitter(messageUDP, localInterface); // Envoie la mise à jour
                        } else {
                            System.out.println("Plus de place pour les bots moyens");
                        }
                    } else if (identity.equals("BOTE")) {
                        if (model.countAcceptedPlayersByIdentity(identity) < Integer.parseInt(model.getBotCounts().get("BOTE"))) {
                            Data.udpEmitter(messageUDP, localInterface); // Envoie la mise à jour
                        } else {
                            System.out.println("Plus de place pour les bots élevés");
                        }
                    }
                }
            });

            while (running) {
                try {
                    Thread.sleep(100); // Pause pour éviter une boucle intensive
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); // Gère l'interruption du thread
                }
            }
        }).start();
    }

    // Méthode pour vérifier si un joueur peut être ajouté en fonction de son identité
    private boolean canAddPlayer(String identity) {
        int acceptedCount = model.countAcceptedPlayersByIdentity(identity);

        switch (identity) {
            case "JR":
                // Vérifie si le nombre de joueurs réels acceptés est inférieur au maximum
                return acceptedCount < Integer.parseInt(model.getGameAttributes().get("nbjrm"));
            case "BOTF":
            case "BOTM":
            case "BOTE":
                // Vérifie si le nombre de bots acceptés est inférieur au maximum pour ce type
                return acceptedCount < Integer.parseInt(model.getBotCounts().get(identity));
            default:
                return false; // Identité inconnue, ne peut pas ajouter le joueur
        }
    }
}
