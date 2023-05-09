package Entities;

import java.util.Date;

public class Eleve {

    private int idEleve;
    private int idUser;
    public Eleve(int idEleve, int idUser) {
        this.idEleve = idEleve;
        this.idUser = idUser;
    }

    public int getIdEleve() {
        return idEleve;
    }

    public void setIdEleve(int idEleve) {
        this.idEleve = idEleve;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }
}
