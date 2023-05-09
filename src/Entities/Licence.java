package Entities;

public class Licence {

    private int idLicence;

    private int idmoniteurLicence;

    private int idcategorieLicence;

    private String dateobtentionLicence;

    public Licence(int idLicence, int idmoniteurLicence, int idcategorieLicence, String dateobtentionLicence) {
        this.idLicence = idLicence;
        this.idmoniteurLicence = idmoniteurLicence;
        this.idcategorieLicence = idcategorieLicence;
        this.dateobtentionLicence = dateobtentionLicence;
    }
    public Licence(String dateObtention){
        this.dateobtentionLicence = dateObtention;
    }

    public int getIdLicence() {
        return idLicence;
    }

    public void setIdLicence(int idLicence) {
        this.idLicence = idLicence;
    }

    public int getIdmoniteurLicence() {
        return idmoniteurLicence;
    }

    public void setIdmoniteurLicence(int idmoniteurLicence) {
        this.idmoniteurLicence = idmoniteurLicence;
    }

    public int getIdcategorieLicence() {
        return idcategorieLicence;
    }

    public void setIdcategorieLicence(int idcategorieLicence) {
        this.idcategorieLicence = idcategorieLicence;
    }

    public String getDateobtentionLicence() {
        return dateobtentionLicence;
    }

    public void setDateobtentionLicence(String dateobtentionLicence) {
        this.dateobtentionLicence = dateobtentionLicence;
    }
}
