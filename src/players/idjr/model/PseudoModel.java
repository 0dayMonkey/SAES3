package players.idjr.model;

public class PseudoModel {
    private String pseudo;

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public boolean isValidPseudo() {
        boolean isValid = pseudo != null && pseudo.length() >= 1;
        return isValid;
    }
}
