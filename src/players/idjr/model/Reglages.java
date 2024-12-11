package players.idjr.model;

public class Reglages {
    private double luminosite;
    private boolean vibrationActive;
    private String langue;

    public Reglages() {
        this.luminosite = 50;
        this.vibrationActive = true;
        this.langue = "Français";
    }

    public double getLuminosite() {
        return luminosite;
    }

    public void setLuminosite(double luminosite) {
        this.luminosite = luminosite;
    }

    public boolean isVibrationActive() {
        return vibrationActive;
    }

    public void setVibrationActive(boolean vibrationActive) {
        this.vibrationActive = vibrationActive;
    }

    public String getLangue() {
        return langue;
    }

    public void setLangue(String langue) {
        this.langue = langue;
    }
}