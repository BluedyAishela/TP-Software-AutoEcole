package Controllers;


import Entities.Categorie;
import Entities.Vehicule;
import Tools.ConnexionBDD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CtrlVehicule {

    private Connection cnx;
    private PreparedStatement ps;
    private ResultSet rs;

    public CtrlVehicule() {
        cnx = ConnexionBDD.getCnx();
    }

    public ArrayList<Vehicule> getAllVehicules() {

        ArrayList<Vehicule> LesVehicules = new ArrayList<>();

        try {
            ps = cnx.prepareStatement("SELECT " +
                    "vehicule.id, " +
                    "vehicule.immatriculation, " +
                    "vehicule.marque, " +
                    "vehicule.modele, " +
                    "vehicule.annee, " +
                    "categorie.libelle " +
                    "FROM vehicule " +
                    "INNER JOIN categorie ON categorie.id = vehicule.idCategorie " +
                    "ORDER BY vehicule.id");
            rs = ps.executeQuery();

            while(rs.next()){
                Categorie Categorie = new Categorie(rs.getString(6));
                Vehicule uneVehicule = new Vehicule(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        Categorie
                );
                LesVehicules.add(uneVehicule);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return LesVehicules;
    }

    public ArrayList<Vehicule> getVehiculeById(int numeroVehi) {

        ArrayList<Vehicule> LesVehicules = new ArrayList<>();

        try {
            ps = cnx.prepareStatement("SELECT" +
                    " id," +
                    " codecategorie_id," +
                    " immatriculation," +
                    " marque," +
                    " modele," +
                    " annee" +
                    " FROM vehicule" +
                    " Where id = ?");
            ps.setInt(1,numeroVehi);
            rs = ps.executeQuery();

            while(rs.next()){
                Vehicule unVehicule = new Vehicule(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6)
                );
                LesVehicules.add(unVehicule);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return LesVehicules;
    }

    public ArrayList<String> getAllImmatriculation() {

        ArrayList<String> LesImmatriculations = new ArrayList<>();

        try {
            ps = cnx.prepareStatement("SELECT immatriculation FROM vehicule ORDER BY immatriculation");

            rs = ps.executeQuery();

            while(rs.next()){
                LesImmatriculations.add(rs.getString(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return LesImmatriculations;
    }
    public ArrayList<String> getImmatriculationByCat(String categorie) {

        ArrayList<String> LesImmatriculations = new ArrayList<>();

        try {
            ps = cnx.prepareStatement("SELECT immatriculation " +
                    "FROM vehicule " +
                    "INNER JOIN categorie ON vehicule.idCategorie = categorie.id " +
                    "WHERE categorie.libelle LIKE ? " +
                    "ORDER BY immatriculation");
            ps.setString(1, categorie);
            rs = ps.executeQuery();

            while(rs.next()){
                LesImmatriculations.add(rs.getString(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return LesImmatriculations;
    }

    public Integer getIdVehiculeByImmatriculation(String immatriculation) {

        try {
            ps = cnx.prepareStatement("SELECT vehicule.id FROM vehicule WHERE vehicule.immatriculation LIKE ?");
            ps.setString(1, immatriculation);
            rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public Boolean VerifyImmatriculation( String immatriculation){
        try {
            ps = cnx.prepareStatement("SELECT vehicule.id FROM vehicule WHERE vehicule.immatriculation LIKE ?");
            ps.setString(1, immatriculation);
            rs = ps.executeQuery();
            if (rs.next()){
                return true;
            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public Boolean VerifyImmatriculationUpdate(int idVehicule, String immatriculation){
        try {
            ps = cnx.prepareStatement("SELECT vehicule.id FROM vehicule WHERE vehicule.immatriculation LIKE ? and vehicule.id != ?");
            ps.setString(1, immatriculation);
            ps.setInt(2, idVehicule);
            rs = ps.executeQuery();
            if (rs.next()){
                return true;
            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean VerifyVehiculeLecon(String immatriculation){
        try {
            ps = cnx.prepareStatement("SELECT lecon.id " +
                    "FROM lecon " +
                    "INNER JOIN vehicule ON lecon.idVehicule = vehicule.id " +
                    "WHERE vehicule.immatriculation LIKE ?");
            ps.setString(1, immatriculation);
            rs = ps.executeQuery();
            if (rs.next()){
                return true;
            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void DeleteVehicule(String immatriculation){
        try {
            ps = cnx.prepareStatement("DELETE FROM `vehicule` WHERE vehicule.immatriculation LIKE ?");
            ps.setString(1, immatriculation);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean ADejaUneLecon(int idVehicule, String date, String heure) {

        try {
            ps = cnx.prepareStatement("SELECT lecon.id FROM lecon WHERE lecon.idVehicule = ? AND lecon.date = ? AND lecon.heure = ?");
            ps.setInt(1, idVehicule);
            ps.setString(2, date);
            ps.setString(3, heure);
            rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void AddVehicule(String immatriculation, String marque, String modele, int annee, String categorie){
        try {
            ps = cnx.prepareStatement("SELECT categorie.id from categorie where categorie.libelle Like ?");
            ps.setString(1, categorie);
            rs = ps.executeQuery();
            rs.next();
            int idCategorie = rs.getInt(1);
            ps = cnx.prepareStatement("INSERT INTO `vehicule`(`immatriculation`, `marque`, `modele`, `annee`, `idCategorie`) VALUES (?,?,?,?,?)");
            ps.setString(1, immatriculation);
            ps.setString(2, marque);
            ps.setString(3, modele);
            ps.setInt(4, annee);
            ps.setInt(5, idCategorie);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void UpdateVehicule(int id, String immatriculation, String marque, String modele, int annee, String categorie){
        try {
            CtrlCategorie ctrlCategorie = new CtrlCategorie();
            int idCategorie = ctrlCategorie.getIdByLibelle(categorie);
            ps = cnx.prepareStatement("UPDATE `vehicule` SET `immatriculation`= ?,`marque`= ?,`modele`= ?,`annee`= ?,`idCategorie`= ? WHERE id = ? ");
            ps.setString(1, immatriculation);
            ps.setString(2, marque);
            ps.setString(3, modele);
            ps.setInt(4, annee);
            ps.setInt(5, idCategorie);
            ps.setInt(6, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Vehicule> SearchVehiculeByCatAndImm(String immatriculation, String categorie) {

        ArrayList<Vehicule> LesVehicules = new ArrayList<>();

        try {
            String rqt = null;
            if (immatriculation == null && categorie != null){
                rqt = "WHERE categorie.libelle Like ?";

            }else if (immatriculation != null && categorie == null){
                rqt = "WHERE vehicule.immatriculation Like ?";

            } else if (immatriculation != null && categorie != null) {
                rqt = "WHERE vehicule.immatriculation Like ? and categorie.libelle LIKE ? ";
            }
            ps = cnx.prepareStatement("SELECT " +
                    "vehicule.id, " +
                    "vehicule.immatriculation, " +
                    "vehicule.marque, " +
                    "vehicule.modele, " +
                    "vehicule.annee, " +
                    "categorie.libelle " +
                    "FROM vehicule " +
                    "INNER JOIN categorie ON categorie.id = vehicule.idCategorie " +
                    rqt +
                    " ORDER BY vehicule.id");
            if (immatriculation == null && categorie != null){
                ps.setString(1, "%"+categorie + "%");
            }else if (immatriculation != null && categorie == null){
                ps.setString(1, "%"+immatriculation + "%");
            } else if (immatriculation != null && categorie != null) {
                ps.setString(2, "%"+categorie + "%");
                ps.setString(1, "%"+immatriculation + "%");
            }
            rs = ps.executeQuery();

            while(rs.next()){
                Categorie Categorie = new Categorie(rs.getString(6));
                Vehicule uneVehicule = new Vehicule(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        Categorie
                );
                LesVehicules.add(uneVehicule);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return LesVehicules;
    }
}
