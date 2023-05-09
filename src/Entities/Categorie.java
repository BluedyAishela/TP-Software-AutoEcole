package Entities;

public class Categorie {

    private int idCategorie;

    private String libelleCategorie;

    private float prixCategorie;
    private Licence licence;

    public Categorie(int idCategorie, String libelleCategorie, float prixCategorie) {
        this.idCategorie = idCategorie;
        this.libelleCategorie = libelleCategorie;
        this.prixCategorie = prixCategorie;
    }
    public Categorie(String libelleCategorie, float prixCategorie){
        this.libelleCategorie = libelleCategorie;
        this.prixCategorie = prixCategorie;
    }
    public Categorie(String libelleCategorie, Licence licence){
        this.libelleCategorie = libelleCategorie;
        this.licence = licence;
    }
    public Categorie(String libelleCategorie) {
        this.libelleCategorie = libelleCategorie;
    }

    public int getIdCategorie() {
        return idCategorie;
    }

    public void setIdCategorie(int idCategorie) {
        this.idCategorie = idCategorie;
    }

    public String getLibelleCategorie() {
        return libelleCategorie;
    }

    public void setLibelleCategorie(String libelleCategorie) {
        this.libelleCategorie = libelleCategorie;
    }

    public float getPrixCategorie() {
        return prixCategorie;
    }

    public void setPrixCategorie(float prixCategorie) {
        this.prixCategorie = prixCategorie;
    }

    public Licence getLicence() {
        return licence;
    }
}
