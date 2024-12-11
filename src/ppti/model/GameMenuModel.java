package ppti.model;

// Modèle pour le menu du jeu
public class GameMenuModel {
    private int currentAngle; // Angle actuel de rotation

    // Constructeur par défaut qui initialise l'angle à 0
    public GameMenuModel() {
        this.currentAngle = 0;
    }

    // Méthode pour obtenir l'angle actuel
    public int getCurrentAngle() {
        return currentAngle;
    }

    // Méthode pour définir un nouvel angle
    public void setCurrentAngle(int angle) {
        this.currentAngle = angle;
    }
}
