package ppti.view;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.layout.*;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import ppti.model.Player;
import javafx.scene.Group;

import java.util.List;

public class GameInterfacePptiView {
    private GridPane root;
    private List<Player> players;
    private static final double CARD_WIDTH = 60;
    private static final double CARD_HEIGHT = 90;
    private static final int MAX_CARDS = 12;

    private VBox centerArea;

    public GameInterfacePptiView(List<Player> players) {
        this.players = players;
        root = new GridPane();
        root.setStyle("-fx-background-color: white;");
        root.setPadding(new Insets(20));
        setupLayout();
    }

    private void setupLayout() {
        setupGridPane();
        setupPlayers();
        setupCenterArea();
    }

    private void setupGridPane() {
        root.getRowConstraints().clear();
        root.getColumnConstraints().clear();

        // Colonnes
        for (int i = 0; i < 5; i++) {
            ColumnConstraints col = new ColumnConstraints();
            col.setPercentWidth(20);
            root.getColumnConstraints().add(col);
        }

        // Lignes
        RowConstraints row1 = new RowConstraints();
        row1.setPercentHeight(30); // Augmentez la hauteur de la première ligne

        RowConstraints row2 = new RowConstraints();
        row2.setPercentHeight(15);

        RowConstraints row3 = new RowConstraints();
        row3.setPercentHeight(10);

        RowConstraints row4 = new RowConstraints();
        row4.setPercentHeight(15);

        RowConstraints row5 = new RowConstraints();
        row5.setPercentHeight(30); // Augmentez la hauteur de la dernière ligne

        root.getRowConstraints().addAll(row1, row2, row3, row4, row5);
    }

    private void setupPlayers() {
        for (Player player : players) {
            int positionIndex = player.getPosition(); // Indice de position de 1 à 10
            VBox playerArea = createPlayerArea(player.getName(), MAX_CARDS, positionIndex);
            playerArea.setUserData(positionIndex);

            // Déterminer la ligne et la colonne en fonction de l'indice de position
            int row = 0;
            int col = 0;

            switch (positionIndex) {
                case 1:
                    // Haut gauche
                    row = 0;
                    col = 1;
                    break;
                case 2:
                    // Haut centre
                    row = 0;
                    col = 2;
                    break;
                case 3:
                    // Haut droite
                    row = 0;
                    col = 3;
                    break;
                case 4:
                    // Droite haut
                    row = 1;
                    col = 4;
                    break;
                case 5:
                    // Droite bas
                    row = 3;
                    col = 4;
                    break;
                case 6:
                    // Bas droite
                    row = 4;
                    col = 3;
                    break;
                case 7:
                    // Bas centre
                    row = 4;
                    col = 2;
                    break;
                case 8:
                    // Bas gauche
                    row = 4;
                    col = 1;
                    break;
                case 9:
                    // Gauche bas
                    row = 3;
                    col = 0;
                    break;
                case 10:
                    // Gauche haut
                    row = 1;
                    col = 0;
                    break;
                default:
                    System.err.println("Indice de position inconnu: " + positionIndex);
                    continue;
            }
            Group playerGroup = new Group(playerArea);
            root.add(playerGroup, col, row);
            GridPane.setHalignment(playerGroup, HPos.CENTER);
            GridPane.setValignment(playerGroup, VPos.CENTER);
        }
    }

    private void setupCenterArea() {
        centerArea = new VBox(10); // Conteneur principal, avec espacement vertical de 10
        centerArea.setAlignment(Pos.CENTER);

        // Carte "CarteMareeVerso" en haut
        StackPane carteMareeVerso = createImageCard("/cartes/cartesMarree/CarteMareeVerso.png");

        // Cartes "carteMarree-1" et "CartesMaree-2" en bas
        HBox cartesMarees = new HBox(10); // Conteneur horizontal pour les cartes
        cartesMarees.setAlignment(Pos.CENTER);
        cartesMarees.getChildren().addAll(
                createImageCard("/cartes/cartesMarree/carteMarree-1.png"),
                createImageCard("/cartes/cartesMarree/CartesMaree-2.png")
        );

        // Ajouter les deux groupes dans la zone centrale
        centerArea.getChildren().addAll(carteMareeVerso, cartesMarees);

        // Ajouter la zone centrale au centre du GridPane (ligne 2, colonne 2)
        GridPane.setRowIndex(centerArea, 2);
        GridPane.setColumnIndex(centerArea, 2);
        GridPane.setHalignment(centerArea, HPos.CENTER);
        GridPane.setValignment(centerArea, VPos.CENTER);
        root.add(centerArea, 2, 2);
    }

    private VBox createPlayerArea(String playerName, int numCards, int positionIndex) {
        VBox playerArea = new VBox(10);
        playerArea.setAlignment(Pos.CENTER);

        // Création du label du nom du joueur
        Label nameLabel = new Label(playerName);
        nameLabel.setStyle("-fx-font-size: 14px;");
        nameLabel.setTranslateY(20);

     // StackPane principal pour gérer le chevauchement
        StackPane contentStack = new StackPane();
        
        // Création du StackPane pour les cartes bouées
        StackPane cardsStack = new StackPane();
        cardsStack.setMinHeight(CARD_HEIGHT + (numCards > 6 ? CARD_HEIGHT/2 : 0));

        HBox firstRow = new HBox(-25);
        firstRow.setAlignment(Pos.CENTER);

        HBox secondRow = new HBox(-25);
        secondRow.setAlignment(Pos.CENTER);

        for (int i = 0; i < numCards; i++) {
            StackPane card = createImageCard("/cartes/cartesBouee/rectoCarteBouee.png");
            if (i < 6) {
                firstRow.getChildren().add(card);
            } else {
                secondRow.getChildren().add(card);
            }
        }

        if (numCards > 6) {
            secondRow.setTranslateY(CARD_HEIGHT/2);
            cardsStack.getChildren().addAll(firstRow, secondRow);
        } else {
            cardsStack.getChildren().add(firstRow);
        }

        // Création de la carte météo
        StackPane weatherCard = createImageCard("/cartes/cartesMeteo/carteMeteo-10.png");

        // Organiser les éléments dans le playerArea
        playerArea.getChildren().addAll(weatherCard, cardsStack, nameLabel);

        // Appliquer la rotation si nécessaire
        if (positionIndex == 1 || positionIndex == 2 || positionIndex == 3) {
            playerArea.setRotate(180);
            playerArea.setPadding(new Insets(0, 0, 20, 0));
        }else if(positionIndex == 4 || positionIndex == 5) {
        	playerArea.setRotate(-90);
        }else if(positionIndex == 9 || positionIndex == 10) {
        	playerArea.setRotate(90);
        }

        return playerArea;
    }

    private StackPane createImageCard(String imagePath) {
        StackPane cardPane = new StackPane();
        Rectangle card = new Rectangle(CARD_WIDTH, CARD_HEIGHT);
        card.setStroke(Color.BLACK);
        card.setStrokeWidth(1);

        try {
            javafx.scene.image.Image image = new javafx.scene.image.Image(getClass().getResourceAsStream(imagePath));
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(CARD_WIDTH);
            imageView.setFitHeight(CARD_HEIGHT);
            cardPane.getChildren().addAll(card, imageView);
        } catch (Exception e) {
            cardPane.getChildren().add(card);
        }

        return cardPane;
    }

    public GridPane getView() {
        return root;
    }
}
