package Entities;

public class Moniteur {

    private int idMoniteur;
    private int idUser;

    public Moniteur(int idMoniteur, int idUser) {
        this.idMoniteur = idMoniteur;
        this.idUser = idUser;
    }

    public int getIdMoniteur() {
        return idMoniteur;
    }

    public void setIdMoniteur(int idMoniteur) {
        this.idMoniteur = idMoniteur;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }
}
