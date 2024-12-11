package players.idjr.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChoixPartiesModel {
    private final Map<String, Partie> listeParties; // Clé : idp

    public ChoixPartiesModel() {
        this.listeParties = new HashMap<>();
    }

    // Récupérer toutes les parties disponibles (retourne une copie non modifiable)
    public Map<String, Partie> getListeParties() {
        return Collections.unmodifiableMap(new HashMap<>(listeParties));
    }

    // Ajouter une nouvelle partie (évite les doublons)
    public synchronized void ajouterPartie(Partie partie) {
        if (!listeParties.containsKey(partie.getIdp())) {
            listeParties.put(partie.getIdp(), partie);
        }
    }

    // Supprimer une partie
    public synchronized void supprimerPartie(String idp) {
        listeParties.remove(idp);
    }

    // Mettre à jour une partie existante
    public synchronized void mettreAJourPartie(Partie partie) {
        if (listeParties.containsKey(partie.getIdp())) {
            listeParties.put(partie.getIdp(), partie);
        } else {
            ajouterPartie(partie);
        }
    }

    // Obtenir une partie par son idp
    public synchronized Partie getPartie(String idp) {
        return listeParties.get(idp);
    }

    public synchronized String getNomPartie(String idp) {
        Partie partie = getPartie(idp);
        return (partie != null) ? partie.getNomPartie() : null;
    }
}
