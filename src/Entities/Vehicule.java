package Entities;

public class Vehicule {

    private int idVehicule;

    private int idcategorieVehicule;

    private String immatriculationVehicule;

    private String marqueeVehicule;

    private String modeleVehicule;

    private String anneeVehicule;
    private Categorie categorie;

    public Vehicule(int idVehicule, int idcategorieVehicule, String immatriculationVehicule, String marqueeVehicule, String modeleVehicule, String anneeVehicule) {
        this.idVehicule = idVehicule;
        this.idcategorieVehicule = idcategorieVehicule;
        this.immatriculationVehicule = immatriculationVehicule;
        this.marqueeVehicule = marqueeVehicule;
        this.modeleVehicule = modeleVehicule;
        this.anneeVehicule = anneeVehicule;
    }
    public Vehicule(int idVehicule, String immatriculationVehicule, String marqueeVehicule, String modeleVehicule, String anneeVehicule, Categorie categorie) {
        this.idVehicule = idVehicule;
        this.categorie = categorie;
        this.immatriculationVehicule = immatriculationVehicule;
        this.marqueeVehicule = marqueeVehicule;
        this.modeleVehicule = modeleVehicule;
        this.anneeVehicule = anneeVehicule;
    }

    public int getIdVehicule() {
        return idVehicule;
    }

    public void setIdVehicule(int idVehicule) {
            this.idVehicule = idVehicule;
    }

    public int getIdcategorieVehicule() {
        return idcategorieVehicule;
    }

    public void setIdcategorieVehicule(int idcategorieVehicule) {
        this.idcategorieVehicule = idcategorieVehicule;
    }

    public String getImmatriculationVehicule() {
        return immatriculationVehicule;
    }

    public void setImmatriculationVehicule(String immatriculationVehicule) {
        this.immatriculationVehicule = immatriculationVehicule;
    }

    public String getMarqueeVehicule() {
        return marqueeVehicule;
    }

    public void setMarqueeVehicule(String marqueeVehicule) {
        this.marqueeVehicule = marqueeVehicule;
    }

    public String getModeleVehicule() {
        return modeleVehicule;
    }

    public void setModeleVehicule(String modeleVehicule) {
        this.modeleVehicule = modeleVehicule;
    }

    public String getAnneeVehicule() {
        return anneeVehicule;
    }

    public void setAnneeVehicule(String anneeVehicule) {
        this.anneeVehicule = anneeVehicule;
    }

    public Categorie getCategorie() {
        return categorie;
    }
}
