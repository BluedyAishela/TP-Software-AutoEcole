package Entities;

import java.util.Date;

public class User {

    private int id;
    private String login;
    private String password;
    private String role;
    private String nom;
    private String prenom;
    private int sexe;
    private Date datedenaissance;
    private String adresse;
    private String codepostal;
    private String ville;
    private String telephone;

    public User(int id, String login, String password, String role, String nom, String prenom, int sexe, Date datedenaissance, String adresse, String codepostal, String ville, String telephone) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.role = role;
        this.nom = nom;
        this.prenom = prenom;
        this.sexe = sexe;
        this.datedenaissance = datedenaissance;
        this.adresse = adresse;
        this.codepostal = codepostal;
        this.ville = ville;
        this.telephone = telephone;
    }

    public User(int id, String login, String role, String nom, String prenom, int sexe, Date datedenaissance, String adresse, String codepostal, String ville, String telephone) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.role = role;
        this.nom = nom;
        this.prenom = prenom;
        this.sexe = sexe;
        this.datedenaissance = datedenaissance;
        this.adresse = adresse;
        this.codepostal = codepostal;
        this.ville = ville;
        this.telephone = telephone;
    }
    public User(String nom, String prenom){
        this.nom = nom;
        this.prenom = prenom;
    }
    public User(int id,String login,String nom, String prenom){
        this.id = id;
        this.login = login;
        this.nom = nom;
        this.prenom = prenom;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public int getSexe() {
        return sexe;
    }

    public void setSexe(int sexe) {
        this.sexe = sexe;
    }

    public Date getDatedenaissance() {
        return datedenaissance;
    }

    public void setDatedenaissance(Date datedenaissance) {
        this.datedenaissance = datedenaissance;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getCodepostal() {
        return codepostal;
    }

    public void setCodepostal(String codepostal) {
        this.codepostal = codepostal;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

}

