package Controllers;

import Entities.Eleve;
import Tools.ConnexionBDD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class CtrlEleve {
    private Connection cnx;
    private PreparedStatement ps;
    private ResultSet rs;
    private Eleve unEleve;

    public CtrlEleve() {
        cnx = ConnexionBDD.getCnx();
    }

    public Integer getEleveIdByIdUser(Integer idUser) {
        try {
            ps = cnx.prepareStatement("SELECT eleve.id FROM eleve WHERE eleve.iduser = ?");
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

