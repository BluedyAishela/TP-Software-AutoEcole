package Entities;

public class Lecon {

    private int idLecon;

    private int idmoniteurLecon;

    private int ideleveLecon;

    private int idvehiculeLecon;

    private String dateLecon;

    private String heureLecon;

    private int regleeLecon;

    private User user;
    private String nomEleve;
    private String nomMoniteur;
    private String immatriculation;
    private String payer;
    private Categorie categorie;

    public Lecon(int idLecon, int idmoniteurLecon, int ideleveLecon, int idvehiculeLecon, String dateLecon, String heureLecon, int regleeLecon) {
        this.idLecon = idLecon;
        this.idmoniteurLecon = idmoniteurLecon;
        this.ideleveLecon = ideleveLecon;
        this.idvehiculeLecon = idvehiculeLecon;
        this.dateLecon = dateLecon;
        this.heureLecon = heureLecon;
        this.regleeLecon = regleeLecon;
    }

    public Lecon(int idLecon, User user, String immatriculation, String heureLecon, int regleeLecon, Categorie categorie) {
        this.idLecon = idLecon;
        this.user = user;
        this.immatriculation = immatriculation;
        this.heureLecon = heureLecon;
        if (regleeLecon == 1){
            this.payer = "Payée";
        } else if (regleeLecon == 0) {
            this.payer = "Non Payée";
        }
        this.categorie = categorie;
    }

    public Lecon(String dateLecon) {
        this.dateLecon = dateLecon;
    }

    public int getIdLecon() {
        return idLecon;
    }

    public void setIdLecon(int idLecon) {
        this.idLecon = idLecon;
    }

    public int getIdmoniteurLecon() {
        return idmoniteurLecon;
    }

    public void setIdmoniteurLecon(int idmoniteurLecon) {
        this.idmoniteurLecon = idmoniteurLecon;
    }

    public int getIdeleveLecon() {
        return ideleveLecon;
    }

    public void setIdeleveLecon(int ideleveLecon) {
        this.ideleveLecon = ideleveLecon;
    }

    public int getIdvehiculeLecon() {
        return idvehiculeLecon;
    }

    public void setIdvehiculeLecon(int idvehiculeLecon) {
        this.idvehiculeLecon = idvehiculeLecon;
    }

    public String getDateLecon() {
        return dateLecon;
    }

    public void setDateLecon(String dateLecon) {
        this.dateLecon = dateLecon;
    }

    public String getHeureLecon() {
        return heureLecon;
    }

    public void setHeureLecon(String heureLecon) {
        this.heureLecon = heureLecon;
    }

    public int getRegleeLecon() {
        return regleeLecon;
    }

    public void setRegleeLecon(int regleeLecon) {
        this.regleeLecon = regleeLecon;
    }

    public String getNomEleve() {
        return nomEleve;
    }

    public void setNomEleve(String nomEleve) {
        this.nomEleve = nomEleve;
    }

    public String getImmatriculation() {
        return immatriculation;
    }

    public void setImmatriculation(String immatriculation) {
        this.immatriculation = immatriculation;
    }
    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public User getUser() {
        return user;
    }
}
