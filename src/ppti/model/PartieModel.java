package ppti.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PartieModel {
    private int mancheActuelle;                   // Numéro de la manche en cours
    private int pli;                              // Nombre de plis réalisés
    private List<Card> cartesJouees;             // Liste des cartes déjà jouées
    private List<Card> cartesMareesTotal;        // Toutes les cartes marées
    private List<Card> cartesMareesActuelles;    // Cartes marées de la manche actuelle
    private HashMap<Player, List<Card>> joueursCartes; // Cartes des joueurs

    // Constructeur
    public PartieModel() {
        this.mancheActuelle = 1;                 // Initialisation à la manche 1
        this.pli = 0;                            // Aucun pli au début
        this.cartesJouees = new ArrayList<>();   // Liste vide au départ
        this.cartesMareesTotal = new ArrayList<>();
        this.cartesMareesActuelles = new ArrayList<>();
        this.joueursCartes = new HashMap<>();    // Initialisation de la HashMap pour les cartes des joueurs
    }

    // Getters et Setters
    public int getMancheActuelle() {
        return mancheActuelle;
    }

    public void setMancheActuelle(int mancheActuelle) {
        this.mancheActuelle = mancheActuelle;
    }

    public int getPli() {
        return pli;
    }

    public void setPli(int pli) {
        this.pli = pli;
    }

    public List<Card> getCartesJouees() {
        return cartesJouees;
    }

    public void ajouterCarteJouee(Card carte) {
        cartesJouees.add(carte);
    }

    public List<Card> getCartesMareesTotal() {
        return cartesMareesTotal;
    }

    public void ajouterCarteMareesTotal(Card carte) {
        cartesMareesTotal.add(carte);
    }

    public List<Card> getCartesMareesActuelles() {
        return cartesMareesActuelles;
    }

    public void ajouterCarteMareesActuelles(Card carte) {
        cartesMareesActuelles.add(carte);
    }

    public void reinitialiserCartesJouees() {
        cartesJouees.clear();
    }

    public void reinitialiserCartesMareesActuelles() {
        cartesMareesActuelles.clear();
    }

    // Ajouter une carte à un joueur
    public void ajouterCartePourJoueur(Player joueur, Card carte) {
        // Si le joueur n'a pas encore de liste de cartes, on l'initialise
        if (!joueursCartes.containsKey(joueur)) {
            joueursCartes.put(joueur, new ArrayList<>());
        }
        joueursCartes.get(joueur).add(carte); // Ajoute la carte à la liste du joueur
    }

    // Récupérer les cartes d'un joueur
    public List<Card> getCartesJoueur(Player joueur) {
        return joueursCartes.getOrDefault(joueur, new ArrayList<>()); // Retourne les cartes du joueur
    }

    // Vérifier si un joueur a une carte spécifique
    public boolean joueurPossedeCarte(Player joueur, Card carte) {
        List<Card> cartes = joueursCartes.get(joueur);
        return cartes != null && cartes.contains(carte); // Vérifie si la carte est dans la liste du joueur
    }
}


