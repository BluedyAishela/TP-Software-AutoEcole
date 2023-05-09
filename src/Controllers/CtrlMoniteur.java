package Controllers;


import Entities.Moniteur;
import Tools.ConnexionBDD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CtrlMoniteur {
    private Connection cnx;
    private PreparedStatement ps;
    private ResultSet rs;

    public CtrlMoniteur() {
        cnx = ConnexionBDD.getCnx();
    }

    public Integer getIdMoniteurByName(String nom, String prenom) {
        try {
            ps = cnx.prepareStatement("SELECT moniteur.id FROM moniteur \n" +
                    "INNER JOIN user ON user.id = moniteur.idUser \n" +
                    "WHERE user.nom = ? AND user.prenom = ?");
            ps.setString(1, nom);
            ps.setString(2, prenom);
            rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public Integer getMoniteurIdByIdUser(Integer idUser) {
        try {
            ps = cnx.prepareStatement("SELECT moniteur.id FROM moniteur WHERE moniteur.iduser = ?");
            ps.setInt(1, idUser);
            rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public boolean ADejaUneLecon(int idMoniteur, String date, String heure) {

        try {
            ps = cnx.prepareStatement("SELECT lecon.id FROM lecon WHERE lecon.idMoniteur = ? AND lecon.date = ? AND lecon.heure = ?");
            ps.setInt(1, idMoniteur);
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

}
