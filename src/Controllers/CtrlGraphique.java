package Controllers;

import Tools.ConnexionBDD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CtrlGraphique {
    private Connection cnx;
    private PreparedStatement ps;
    private ResultSet rs;

    public CtrlGraphique() {
        cnx = ConnexionBDD.getCnx();
    }

    /**
     * Moniteur
     */
    public HashMap<String, Integer> getGraphMoniLeconPasse(int id, String date, String date1, String date2) {
        HashMap<String, Integer> datas = new HashMap<>();
        try {
            if (date != null && date1 == null && date2 == null){
                ps = cnx.prepareStatement("SELECT 'Leçon passée', COUNT(lecon.id) " +
                        "from lecon " +
                        "INNER JOIN moniteur ON lecon.idMoniteur = moniteur.id " +
                        "WHERE moniteur.idUser = ? " +
                        "AND date like ? " +
                        "AND reglee = 1 ");
                ps.setInt(1,id);
                ps.setString(2, date);
            } else if (date == null && date1 != null && date2 != null) {
                ps = cnx.prepareStatement("SELECT 'Leçon passée', COUNT(lecon.id) " +
                        "from lecon " +
                        "INNER JOIN moniteur ON lecon.idMoniteur = moniteur.id " +
                        "WHERE moniteur.idUser = ? " +
                        "AND date BETWEEN ? and ? " +
                        "AND reglee = 1 ");
                ps.setInt(1,id);
                ps.setString(2, date1);
                ps.setString(3, date2);
            }
            rs = ps.executeQuery();

            while(rs.next())
            {
                datas.put(rs.getString(1), rs.getInt(2));
            }

            if (date != null && date1 == null && date2 == null){
                ps = cnx.prepareStatement("SELECT 'Leçons restantes à passer', COUNT(lecon.id) " +
                        "from lecon " +
                        "INNER JOIN moniteur ON lecon.idMoniteur = moniteur.id " +
                        "WHERE moniteur.idUser = ? " +
                        "AND date like ? " +
                        "AND reglee = 0 ");
                ps.setInt(1,id);
                ps.setString(2, date);
            }else if (date == null && date1 != null && date2 != null) {
                ps = cnx.prepareStatement("SELECT 'Leçons restantes à passer', COUNT(lecon.id) " +
                        "from lecon " +
                        "INNER JOIN moniteur ON lecon.idMoniteur = moniteur.id " +
                        "WHERE moniteur.idUser = ? " +
                        "AND date BETWEEN ? and ? " +
                        "AND reglee = 0 ");
                ps.setInt(1,id);
                ps.setString(2, date1);
                ps.setString(3, date2);
            }

            rs = ps.executeQuery();

            while(rs.next())
            {
                datas.put(rs.getString(1), rs.getInt(2));
            }

            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(CtrlGraphique.class.getName()).log(Level.SEVERE, null, ex);
        }
        return datas;
    }
    public TreeMap<String,Double> getGraphMoniChiffreAffaire(int id, String date, String date1, String date2){
        TreeMap<String, Double> datas = new TreeMap();

        try {
            if (date != null && date1 == null && date2 == null){
                ps = cnx.prepareStatement("SELECT lecon.date, SUM(categorie.prix) " +
                        "from lecon " +
                        "INNER JOIN moniteur ON lecon.idMoniteur = moniteur.id " +
                        "INNER JOIN vehicule ON lecon.idVehicule = vehicule.id " +
                        "INNER JOIN categorie ON vehicule.idCategorie = categorie.id " +
                        "WHERE moniteur.idUser = ? " +
                        "AND date like ? " +
                        "AND lecon.reglee = 1 " +
                        "GROUP BY lecon.date ");
                ps.setInt(1, id);
                ps.setString(2, date);
            }else if (date == null && date1 != null && date2 != null) {
                ps = cnx.prepareStatement("SELECT lecon.date, round(SUM(categorie.prix),2) " +
                        "from lecon " +
                        "INNER JOIN moniteur ON lecon.idMoniteur = moniteur.id " +
                        "INNER JOIN vehicule ON lecon.idVehicule = vehicule.id " +
                        "INNER JOIN categorie ON vehicule.idCategorie = categorie.id " +
                        "WHERE moniteur.idUser = ? " +
                        "AND date BETWEEN ? and ? " +
                        "And reglee = 1 " +
                        "GROUP BY lecon.date ");
                ps.setInt(1, id);
                ps.setString(2, date1);
                ps.setString(3, date2);
            }
            rs = ps.executeQuery();
            while(rs.next())
            {
                datas.put(rs.getString(1), rs.getDouble(2));
            }
            rs.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return datas;
    }


    /**
     * Reponsable
     */

    public TreeMap<String,Integer> getDatasGraphiqueVehicule(String date1, String date2){
        TreeMap<String, Integer> datas = new TreeMap();
        int count = 0;
        try {
            if (date1 == null && date2 == null){
                ps = cnx.prepareStatement("SELECT vehicule.immatriculation, COUNT(vehicule.immatriculation) as count " +
                        "FROM lecon " +
                        "INNER JOIN vehicule ON lecon.idVehicule = vehicule.id " +
                        "GROUP BY vehicule.immatriculation " +
                        "ORDER BY count DESC ");
            } else if (date1 != null && date2 != null) {
                ps = cnx.prepareStatement("SELECT vehicule.immatriculation, COUNT(vehicule.immatriculation) as count " +
                        "FROM lecon " +
                        "INNER JOIN vehicule ON lecon.idVehicule = vehicule.id " +
                        "WHERE date BETWEEN ? AND ?" +
                        "GROUP BY vehicule.immatriculation " +
                        "ORDER BY count DESC ");
                ps.setString(1, date1);
                ps.setString(2, date2);
            }
            rs = ps.executeQuery();
            while (rs.next()){
                datas.put(rs.getString(1), rs.getInt(2));
            }
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return datas;
    }


    public TreeMap<String,Double> getDataChiffreAffaire(String date1, String date2){
        TreeMap<String, Double> datas = new TreeMap();

        try {
            if (date1 == null && date2 == null){
                ps = cnx.prepareStatement("SELECT lecon.date, round(SUM(categorie.prix),2) " +
                        "from lecon " +
                        "INNER JOIN vehicule ON lecon.idVehicule = vehicule.id " +
                        "INNER JOIN categorie ON vehicule.idCategorie = categorie.id " +
                        "And reglee = 1 " +
                        "GROUP BY lecon.date");
            } else if (date1 != null && date2 != null) {
                ps = cnx.prepareStatement("SELECT lecon.date, round(SUM(categorie.prix),2) " +
                        "from lecon " +
                        "INNER JOIN vehicule ON lecon.idVehicule = vehicule.id " +
                        "INNER JOIN categorie ON vehicule.idCategorie = categorie.id " +
                        "AND date BETWEEN ? AND ? " +
                        "And reglee = 1 " +
                        "GROUP BY lecon.date");
                ps.setString(1, date1);
                ps.setString(2, date2);
            }

            rs = ps.executeQuery();
            while(rs.next())
            {
                datas.put(rs.getString(1), rs.getDouble(2));
            }
            rs.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return datas;
    }
    public TreeMap<String,Integer> getDataMoniteurSolicite(String date1, String date2){
        TreeMap<String, Integer> datas = new TreeMap();

        try {
            if (date1 == null && date2 == null){
                ps = cnx.prepareStatement("SELECT CONCAT(user.nom, ' ', user.prenom) as nomcompl, COUNT(user.id) as count " +
                        "FROM lecon " +
                        "INNER JOIN moniteur ON lecon.idMoniteur = moniteur.id " +
                        "INNER JOIN user ON moniteur.idUser = user.id " +
                        "GROUP BY nomcompl " +
                        "ORDER BY count DESC");
            } else if (date1 != null && date2 != null) {
                ps = cnx.prepareStatement("SELECT CONCAT(user.nom, ' ', user.prenom) as nomcompl, COUNT(user.id) as count " +
                        "FROM lecon " +
                        "INNER JOIN moniteur ON lecon.idMoniteur = moniteur.id " +
                        "INNER JOIN user ON moniteur.idUser = user.id " +
                        "Where lecon.date BETWEEN ? AND ?" +
                        "GROUP BY nomcompl " +
                        "ORDER BY count DESC");
                ps.setString(1, date1);
                ps.setString(2, date2);
            }

            rs = ps.executeQuery();
            while(rs.next())
            {
                datas.put(rs.getString(1), rs.getInt(2));
            }
            rs.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return datas;
    }
    public TreeMap<String,Integer> getDataCategorie(String date1, String date2){
        TreeMap<String, Integer> datas = new TreeMap();

        try {
            if (date1 == null && date2 == null){
                ps = cnx.prepareStatement("SELECT categorie.libelle, COUNT(categorie.id) as COUNT " +
                        "FROM lecon " +
                        "INNER JOIN vehicule ON lecon.idVehicule = vehicule.id " +
                        "INNER JOIN categorie ON vehicule.idCategorie = categorie.id " +
                        "GROUP BY categorie.libelle "+
                        "ORDER BY count DESC");
            } else if (date1 != null && date2 != null) {
                ps = cnx.prepareStatement("SELECT categorie.libelle, COUNT(categorie.id) as COUNT " +
                        "FROM lecon " +
                        "INNER JOIN vehicule ON lecon.idVehicule = vehicule.id " +
                        "INNER JOIN categorie ON vehicule.idCategorie = categorie.id " +
                        "Where lecon.date BETWEEN ? AND ? " +
                        "GROUP BY categorie.libelle " +
                        "ORDER BY count DESC");
                ps.setString(1, date1);
                ps.setString(2, date2);
            }

            rs = ps.executeQuery();
            while(rs.next())
            {
                datas.put(rs.getString(1), rs.getInt(2));
            }
            rs.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return datas;
    }

    public HashMap<String, Float> getGraphElevePrixLecon(Integer id, String date, String date1, String date2){
        HashMap<String, Float> datas = new HashMap<>();
        try {
            if (date != null && date1 == null && date2 == null){

                    ps = cnx.prepareStatement("SELECT 'Leçons payées', SUM(categorie.prix) " +
                            "FROM categorie " +
                            "INNER JOIN vehicule ON categorie.id = vehicule.idCategorie " +
                            "INNER JOIN lecon ON vehicule.id = lecon.idVehicule " +
                            "INNER JOIN eleve ON eleve.id = lecon.idEleve " +
                            "WHERE eleve.idUser = ? " +
                            "AND lecon.reglee = 1 " +
                            "AND lecon.date Like ?"
                    );

                ps.setInt(1, id);
                ps.setString(2, date);
            } else if (date == null && date1 != null && date2 != null) {
                ps = cnx.prepareStatement("SELECT 'Leçons payées', SUM(categorie.prix) " +
                        "FROM categorie " +
                        "INNER JOIN vehicule ON categorie.id = vehicule.idCategorie " +
                        "INNER JOIN lecon ON vehicule.id = lecon.idVehicule " +
                        "INNER JOIN eleve ON eleve.id = lecon.idEleve " +
                        "WHERE eleve.idUser = ? " +
                        "AND lecon.reglee = 1 " +
                        "AND lecon.date BETWEEN ? AND ?"
                );
                ps.setInt(1, id);
                ps.setString(2, date1);
                ps.setString(3, date2);
            }
            rs = ps.executeQuery();

            while (rs.next()){
                datas.put(rs.getString(1), rs.getFloat(2));
            }


            if (date != null && date1 == null && date2 == null){
                ps = cnx.prepareStatement("SELECT 'Leçons non payées', SUM(categorie.prix) " +
                        "FROM categorie " +
                        "INNER JOIN vehicule ON categorie.id = vehicule.idCategorie " +
                        "INNER JOIN lecon ON vehicule.id = lecon.idVehicule " +
                        "INNER JOIN eleve ON eleve.id = lecon.idEleve " +
                        "WHERE eleve.idUser = ? " +
                        "AND lecon.reglee = 0 " +
                        "AND lecon.date Like ?"
                );
                ps.setInt(1, id);
                ps.setString(2, date);
            } else if (date == null && date1 != null && date2 != null) {
                ps = cnx.prepareStatement("SELECT 'Leçons non payées', SUM(categorie.prix) " +
                        "FROM categorie " +
                        "INNER JOIN vehicule ON categorie.id = vehicule.idCategorie " +
                        "INNER JOIN lecon ON vehicule.id = lecon.idVehicule " +
                        "INNER JOIN eleve ON eleve.id = lecon.idEleve " +
                        "WHERE eleve.idUser = ? " +
                        "AND lecon.reglee = 0 " +
                        "AND lecon.date BETWEEN ? AND ?"
                );
                ps.setInt(1, id);
                ps.setString(2, date1);
                ps.setString(3, date2);
            }
            rs = ps.executeQuery();
            while (rs.next()){
                datas.put(rs.getString(1), rs.getFloat(2));
            }

            rs.close();
            } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return datas;
    }

    public TreeMap<String, Integer> getGraphEleveLeconPassee(int id, String date, String date1, String date2){
        TreeMap<String, Integer> datas = new TreeMap();

        try {
            if (date != null && date1 == null && date2 == null){
                ps = cnx.prepareStatement("SELECT lecon.date, COUNT(lecon.id) " +
                        "FROM lecon " +
                        "INNER JOIN eleve ON lecon.idEleve = eleve.id " +
                        "WHERE eleve.idUser = ? " +
                        "AND lecon.date LIKE ? " +
                        "AND lecon.reglee = 1 " +
                        "GROUP BY lecon.date");
                ps.setInt(1, id);
                ps.setString(2, date);
            }else if (date == null && date1 != null && date2 != null) {
                ps = cnx.prepareStatement("SELECT lecon.date, COUNT(lecon.id) " +
                        "FROM lecon " +
                        "INNER JOIN eleve ON lecon.idEleve = eleve.id " +
                        "WHERE eleve.idUser = ? " +
                        "AND lecon.date BETWEEN ? AND ? " +
                        "AND lecon.reglee = 1 " +
                        "GROUP BY lecon.date");
                ps.setInt(1, id);
                ps.setString(2, date1);
                ps.setString(3, date2);
            }
            rs = ps.executeQuery();
            while(rs.next())
            {
                datas.put(rs.getString(1), rs.getInt(2));
            }
            rs.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return datas;
    }


}
