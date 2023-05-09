package Controllers;


import Entities.Licence;
import Tools.ConnexionBDD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CtrlLicence {

    private Connection cnx;
    private PreparedStatement ps;
    private ResultSet rs;

    public CtrlLicence() {
        cnx = ConnexionBDD.getCnx();
    }

    public ArrayList<Licence> getAllLicences() {

        ArrayList<Licence> LesLicences = new ArrayList<>();

        try {
            ps = cnx.prepareStatement("SELECT " +
                    " id," +
                    " codemoniteur_id," +
                    " codecategorie_id," +
                    " dateobtention," +
                    " FROM licence" +
                    " ORDER BY id"
            );
            rs = ps.executeQuery();

            while(rs.next()){
                Licence uneLicence = new Licence(
                        rs.getInt(4),
                        rs.getInt(3),
                        rs.getInt(2),
                        rs.getString(4)
                );
                LesLicences.add(uneLicence);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return LesLicences;
    }

    public ArrayList<Licence> getLicenceById(int numeroLic) {

        ArrayList<Licence> LesLicences = new ArrayList<>();

        try {
            ps = cnx.prepareStatement("SELECT " +
                    " id," +
                    " codemoniteur_id," +
                    " codecategorie_id," +
                    " dateobtention," +
                    " FROM licence" +
                    " Where id = ?"
            );
            ps.setInt(1,numeroLic);
            rs = ps.executeQuery();

            while(rs.next()){
                Licence uneLicence = new Licence(
                        rs.getInt(4),
                        rs.getInt(3),
                        rs.getInt(2),
                        rs.getString(4)
                );
                LesLicences.add(uneLicence);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return LesLicences;
    }

    public void addCategorieMoniteur(Integer idMoniteur, Integer idCategorie, String dateobtention) {

        try {
            ps = cnx.prepareStatement("INSERT INTO `licence`(`idMoniteur`, `idCategorie`, `dateobtention`) VALUES (?, ?, ?)");
            ps.setInt(1, idMoniteur);
            ps.setInt(2, idCategorie);
            ps.setString(3, dateobtention);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public boolean MoniteurADejaLicence(Integer idMoniteur, String libelleCategorie) {
        try {
            ps = cnx.prepareStatement("SELECT licence.id FROM `licence`\n" +
                    "INNER JOIN categorie ON categorie.id = licence.idCategorie\n" +
                    "WHERE licence.idMoniteur = ? AND categorie.libelle = ?");
            ps.setInt(1, idMoniteur);
            ps.setString(2, libelleCategorie);
            rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public void DeleteLicenceByIdMoniteur(int idMoni, int idCateg){
        try {
            ps = cnx.prepareStatement("DELETE FROM licence " +
                    "WHERE licence.idMoniteur = ? and licence.idCategorie = ?");
            ps.setInt(1, idMoni);
            ps.setInt(2, idCateg);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public int GetIdCategByLicence(String nomCateg){
        int id = 0;
        try {
            ps = cnx.prepareStatement("Select id " +
                    "from categorie " +
                    "where libelle like ?");
            ps.setString(1, nomCateg);
            rs = ps.executeQuery();
            while (rs.next()){
                id = rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return id;
    }

}
