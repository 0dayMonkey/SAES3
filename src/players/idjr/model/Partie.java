package players.idjr.model;

public class Partie {
    private String idp;       // Identifiant unique de la partie
    private String nomPartie; // Nom descriptif de la partie
    private String ip;        // Adresse IP de la partie
    private String statut;    // Statut de la partie (ATTENTE, COMPLETE, ANNULEE)

    public Partie(String idp, String nomPartie, String ip, String statut) {
        this.idp = idp;
        this.nomPartie = nomPartie;
        this.ip = ip;
        this.statut = statut;
    }

    // Getters et Setters
    public String getIdp() {
        return idp;
    }

    public void setIdp(String idp) {
        this.idp = idp;
    }

    public String getNomPartie() {
        return nomPartie;
    }

    public void setNomPartie(String nomPartie) {
        this.nomPartie = nomPartie;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }
    
    @Override
    public String toString() {
        return "Partie{" +
                "idp='" + idp + '\'' +
                ", nomPartie='" + nomPartie + '\'' +
                ", ip='" + ip + '\'' +
                ", statut='" + statut + '\'' +
                '}';
    }
}
