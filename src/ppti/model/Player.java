package ppti.model;

import java.util.ArrayList;
import java.util.List;

// Classe représentant un joueur dans le modèle
public class Player {
    private String name;       // Nom du joueur
    private String ip;         // Adresse IP du joueur
    private String identity;   // Identité du joueur
    private String id;         // ID du joueur
    private int position;      // Position du joueur
    //private List<Card> cards;  // Cartes possédées par le joueur RR


    // Constructeur de la classe Player
    public Player(String name, String ip, String identity, String id) {
        this.name = name;
        this.ip = ip;
        this.identity = identity;
        this.id = id;
       // this.cards = new ArrayList<>(); // Initialise la liste des cartes RR

    }

    // Getters et setters pour les attributs
    public String getName() {
        return name;
    }

    public String getIp() {
        return ip;
    }

    public String getIdentity() {
        return identity;
    }

    public String getId() {
        return id;
    }

    public int getPosition() {
        return position;
    }

    // Définit la position du joueur
    public void setPosition(int position) {
        this.position = position;
    }
    
    /*
    // ajout pr classe Partie :
    
    // Gestion des cartes
    public void addCard(Card card) {
        cards.add(card); // Ajoute une carte à la liste
    }

    public void removeCard(Card card) {
        cards.remove(card); // Supprime une carte de la liste
    }

    public List<Card> getCards() {
        return cards; // Retourne la liste des cartes
    }*/

    
}
