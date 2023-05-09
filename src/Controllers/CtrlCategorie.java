package Controllers;

import Entities.Categorie;
import Entities.Lecon;
import Entities.Licence;
import Entities.User;
import Tools.ConnexionBDD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.WeakHashMap;

public class CtrlCategorie {

    private Connection cnx;
    private PreparedStatement ps;
    private ResultSet rs;

    public CtrlCategorie() {
        cnx = ConnexionBDD.getCnx();
    }

    public ArrayList<Categorie> getAllCategories() {

        ArrayList<Categorie> LesCategories = new ArrayList<>();

        try {
            ps = cnx.prepareStatement("SELECT id," +
                    " libelle," +
                    " prix" +
                    " FROM categorie" +
                    " ORDER BY id"
            );
            rs = ps.executeQuery();

            while(rs.next()){
                Categorie uneCateorie = new Categorie(rs.getInt(1),rs.getString(2), rs.getFloat(3));
                LesCategories.add(uneCateorie);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return LesCategories;
    }

    public ArrayList<String> getAllCategorieLibelle() {

        ArrayList<String> LesCategories = new ArrayList<>();

        try {
            ps = cnx.prepareStatement("SELECT libelle FROM categorie ORDER BY libelle");
            rs = ps.executeQuery();

            while(rs.next()){
                LesCategories.add(rs.getString(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return LesCategories;
    }

    public ArrayList<Categorie> getCategorieById(int numeroCateg) {

        ArrayList<Categorie> LesCategories = new ArrayList<>();

        try {
            ps = cnx.prepareStatement("SELECT" +
                    " id," +
                    " libelle," +
                    " prix " +
                    "FROM categorie " +
                    "Where id = ?" +
                    "ORDER BY id"
            );
            ps.setInt(1,numeroCateg);
            rs = ps.executeQuery();

            while(rs.next()){
                Categorie uneCateorie = new Categorie(rs.getInt(1) , rs.getString(2), rs.getFloat(3));
                LesCategories.add(uneCateorie);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return LesCategories;
    }

    public float getPrixPermisByIdEleve(Integer idEleve) {

        Float prix;

        try {
            ps = cnx.prepareStatement("SELECT SUM(categorie.prix) FROM categorie\n" +
                    "INNER JOIN vehicule ON categorie.id = vehicule.idCategorie\n" +
                    "INNER JOIN lecon ON vehicule.id = lecon.idVehicule\n" +
                    "INNER JOIN eleve ON eleve.id = lecon.idEleve\n" +
                    "INNER JOIN user ON user.id = eleve.idUser\n" +
                    "WHERE user.id = ?"
            );
            ps.setInt(1,idEleve);
            rs = ps.executeQuery();
            rs.next();
            prix = rs.getFloat(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return prix;
    }

    public float getPrixNonPayeeByIdEleve(Integer idEleve){

        Float prix;

        try {
            ps = cnx.prepareStatement("SELECT SUM(categorie.prix) FROM categorie\n" +
                    "INNER JOIN vehicule ON categorie.id = vehicule.idCategorie\n" +
                    "INNER JOIN lecon ON vehicule.id = lecon.idVehicule\n" +
                    "INNER JOIN eleve ON eleve.id = lecon.idEleve\n" +
                    "INNER JOIN user ON user.id = eleve.idUser\n" +
                    "WHERE user.id = ? AND lecon.reglee = 0"
            );
            ps.setInt(1,idEleve);
            rs = ps.executeQuery();
            rs.next();
            prix = rs.getFloat(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return prix;
    }

    public Integer getIdByLibelle(String libelle) {
        try {
            ps = cnx.prepareStatement("SELECT categorie.id FROM categorie WHERE categorie.libelle LIKE ?");
            ps.setString(1, libelle);
            rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public ArrayList<Categorie> getAllCategoriebyIdMoni(int idMoni){
        ArrayList<Categorie> lesCategorie = new ArrayList<>();
        try {
            ps = cnx.prepareStatement("SELECT categorie.libelle, licence.dateobtention " +
                    "FROM licence " +
                    "INNER JOIN categorie ON licence.idCategorie = categorie.id " +
                    "WHERE licence.idMoniteur = ? " +
                    "ORDER BY categorie.libelle");
            ps.setInt(1, idMoni);
            rs = ps.executeQuery();
            while(rs.next()){
                Licence licence = new Licence(rs.getString(2));
                Categorie uneCategorie = new Categorie(
                        rs.getString(1),
                        licence
                );
                lesCategorie.add(uneCategorie);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lesCategorie;
    }
    public void AddCategorie(String libelle, double prix){
        try {
            ps = cnx.prepareStatement("INSERT INTO categorie (`libelle`, `prix`) VALUES (?, ?)");
            ps.setString(1, libelle);
            ps.setDouble(2, prix);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void UpdateCategorie(String newLibelle, double prix, String libelle){
        try {
            ps = cnx.prepareStatement("UPDATE categorie SET libelle = ?,prix = ? WHERE libelle = ?");
            ps.setString(1, newLibelle);
            ps.setDouble(2, prix);
            ps.setString(3, libelle);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public ArrayList<Categorie> SearchCategorieByLibelle(String libelle){
        ArrayList<Categorie> LesCategories = new ArrayList<>();

        try {
            ps = cnx.prepareStatement("SELECT " +
                    "id," +
                    "libelle," +
                    "prix " +
                    "FROM categorie " +
                    "WHERE libelle LIKE ?" +
                    "ORDER BY libelle"
            );
            ps.setString(1, '%'+ libelle +'%');
            rs = ps.executeQuery();

            while(rs.next()){
                Categorie uneCategorie = new Categorie(rs.getInt(1),rs.getString(2), rs.getFloat(3));
                LesCategories.add(uneCategorie);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return LesCategories;
    }

    public ArrayList<Categorie> SearchCategorieByLibelleUpdate(String libelle, int idCategorie){
        ArrayList<Categorie> LesCategories = new ArrayList<>();

        try {
            ps = cnx.prepareStatement("SELECT " +
                    "id," +
                    "libelle," +
                    "prix " +
                    "FROM categorie " +
                    "WHERE libelle LIKE ?" +
                    "AND id != ? " +
                    "ORDER BY libelle"
            );
            ps.setString(1, libelle);
            ps.setInt(2, idCategorie);
            rs = ps.executeQuery();

            while(rs.next()){
                Categorie uneCategorie = new Categorie(rs.getInt(1),rs.getString(2), rs.getFloat(3));
                LesCategories.add(uneCategorie);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return LesCategories;
    }
    public void DeleteCategorie(int idCategorie){
        try {
            ps = cnx.prepareStatement("DELETE FROM categorie WHERE categorie.id = ?");
            ps.setInt(1, idCategorie);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean VerifyCategorieVehicule(int idCategorie){
        try {
            ps = cnx.prepareStatement("SELECT vehicule.id " +
                    "FROM vehicule " +
                    "INNER JOIN categorie on vehicule.idCategorie = categorie.id " +
                    "WHERE vehicule.idCategorie = ?");
            ps.setInt(1, idCategorie);
            rs = ps.executeQuery();
            if (rs.next()){
                return true;
            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean VerifyCategorieLicence(int idCategorie){
        try {
            ps = cnx.prepareStatement("SELECT licence.id " +
                    "FROM licence " +
                    "INNER JOIN categorie on licence.idCategorie = categorie.id " +
                    "WHERE licence.idCategorie = ?");
            ps.setInt(1, idCategorie);
            rs = ps.executeQuery();
            if (rs.next()){
                return true;
            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
