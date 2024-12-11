package ppti.model;

import java.util.LinkedHashMap;
import java.util.Random;

import ppti.data.Data;

// Modèle de configuration du jeu
public class GameConfigModel {
    private String partName;    // Nom de la partie
    private int playerCount;    // Nombre total de joueurs
    private int botA;           // Nombre de bots de type A
    private int botB;           // Nombre de bots de type B
    private int botC;           // Nombre de bots de type C
    private String idp;         // Identifiant unique de la partie
    private int angle;          // Angle actuel de l'interface

    // Constructeur qui initialise l'angle et génère un identifiant de partie aléatoire
    public GameConfigModel(int angle) {
        this.idp = "P" + new Random().nextInt(10_000_000); // Génère un ID de partie unique
        System.out.println(this.idp);
        this.angle = angle;
        //this.partName = partName; // Initialisation du nom de la partie

    }
    
    // Retourne l'angle actuel de l'interface
    public int getCurrentAngle() {
        return angle;
    }

    // Définit le nom de la partie
    public void setPartName(String partName) {
        this.partName = partName;
    }
    
    // R Méthode pour récupérer le nom de la partie 
    public String getPartName() {
        return partName; // Retourne le nom de la partie
    }
    
    

    // Définit le nombre total de joueurs
    public void setPlayerCount(int playerCount) {
        this.playerCount = playerCount;
    }

    // Définit le nombre de bots de type A
    public void setBotA(int botA) {
        this.botA = botA;
    }

    // Définit le nombre de bots de type B
    public void setBotB(int botB) {
        this.botB = botB;
    }

    // Définit le nombre de bots de type C
    public void setBotC(int botC) {
        this.botC = botC;
    }

    // Retourne le nombre de bots de type A
    public int getBotA() {
        return botA;
    }

    // Retourne le nombre de bots de type B
    public int getBotB() {
        return botB;
    }

    // Retourne le nombre de bots de type C
    public int getBotC() {
        return botC;
    }

    // Génère et retourne une map des attributs de configuration de la partie
    public LinkedHashMap<String, String> getAttributes() {
        LinkedHashMap<String, String> attributes = new LinkedHashMap<>();
        attributes.put("idp", idp); // Identifiant de la partie
        attributes.put("ip", Data.ipFinder()); // Adresse IP du joueur (méthode à changer)
        attributes.put("port", "7777"); // Port utilisé
        attributes.put("nomp", partName); // Nom de la partie
        attributes.put("nbj", String.valueOf(playerCount)); // Nombre total de joueurs
        attributes.put("nbjrm", String.valueOf(playerCount - (botA + botB + botC))); // Nombre de joueurs réels
        attributes.put("nbjvm", String.valueOf(botA + botB + botC)); // Nombre de bots
        attributes.put("espa", "1"); // Espace alloué (à préciser)
        attributes.put("statut", "ATTENTE"); // Statut actuel de la partie
        return attributes;
    }
}
