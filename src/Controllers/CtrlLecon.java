package Controllers;


import Entities.Categorie;
import Entities.Lecon;
import Entities.User;
import Tools.ConnexionBDD;
import com.sun.jdi.request.StepRequest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.TreeMap;

public class CtrlLecon {

    private Connection cnx;
    private PreparedStatement ps;
    private ResultSet rs;

    /**
     * Recupere la connexion
     */
    public CtrlLecon() {
        cnx = ConnexionBDD.getCnx();
    }

    /**
     * Recupere toute les lecons
     * @return ArrayList
     * @throws SQLException
     */
    public ArrayList<Lecon> getAllLecons()  {

        ArrayList<Lecon> LesLecons = new ArrayList<>();

        try {
            ps = cnx.prepareStatement("SELECT" +
                    " CodeLecon," +
                    " Date," +
                    " Heure," +
                    " CodeMoniteur," +
                    " CodeEleve," +
                    " Immatriculation," +
                    " Reglee " +
                    "FROM lecon"
            );

            rs = ps.executeQuery();

            while(rs.next()){
                Lecon uneLecon = new Lecon(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getInt(3),
                        rs.getInt(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getInt(7)
                );
                LesLecons.add(uneLecon);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return LesLecons;
    }

    /**
     * Recupere les informations d'une lecon
     * @param numeroLec
     * @return ArrayList
     * @throws SQLException
     */
    public ArrayList<Lecon> getLeconById(int numeroLec)  {

        ArrayList<Lecon> LesLecon = new ArrayList<>();

        try {
            ps = cnx.prepareStatement("SELECT" +
                    " CodeLecon," +
                    " Date," +
                    " Heure," +
                    " CodeMoniteur," +
                    " CodeEleve," +
                    " Immatriculation," +
                    " Reglee " +
                    " FROM lecon" +
                    " Where CodeLecon = ?"
            );

            ps.setInt(1,numeroLec);
            rs = ps.executeQuery();

            while(rs.next()){
                Lecon uneLecon = new Lecon(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getInt(3),
                        rs.getInt(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getInt(7));
                LesLecon.add(uneLecon);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return LesLecon;
    }

    /**
     * Recupere les informations d'une lecon d'une date
     * @param numeroEle
     * @param date
     * @return ArrayList
     * @throws SQLException
     */
    public ArrayList<Lecon> getLeconByEleveIdAndDate(int numeroEle, String date)  {

        ArrayList<Lecon> LesLecons = new ArrayList<>();


        try {
            ps = cnx.prepareStatement("SELECT lecon.id, user.nom, user.prenom, vehicule.immatriculation,lecon.heure,lecon.reglee, categorie.libelle, categorie.prix " +
                    "from lecon " +
                    "inner join moniteur on lecon.idMoniteur = moniteur.id " +
                    "inner join user on moniteur.idUser = user.id " +
                    "INNER JOIN vehicule on lecon.idVehicule = vehicule.id " +
                    "INNER JOIN eleve ON eleve.id = lecon.idEleve " +
                    "INNER join categorie on vehicule.idCategorie = categorie.id " +
                    "WHERE eleve.idUser = ? " +
                    "And date Like ?"
            );

            ps.setInt(1,numeroEle);
            ps.setString(2, date);
            rs = ps.executeQuery();
            while(rs.next()){
                Categorie categorie = new Categorie(rs.getString(7),rs.getFloat(8));
                User user = new User(rs.getString(2), rs.getString(3));
                Lecon uneLecon = new Lecon(
                        rs.getInt(1),
                        user,
                        rs.getString(4),
                        rs.getString(5),
                        rs.getInt(6),
                        categorie
                );
                LesLecons.add(uneLecon);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return LesLecons;
    }

    /**
     * Recupere toute les dates d'un eleve
     * @param numeroEle
     * @return ArrayList
     * @throws SQLException
     */
    public ArrayList<Lecon> getDateLeconByEleveId(int numeroEle)  {

        ArrayList<Lecon> LesLecons = new ArrayList<>();

        try {
            ps = cnx.prepareStatement("SELECT lecon.date " +
                    "from lecon " +
                    "INNER JOIN eleve ON eleve.id = lecon.idEleve " +
                    "WHERE eleve.idUser = ? " +
                    "GROUP BY lecon.date " +
                    "ORDER BY lecon.date ASC"

            );

            ps.setInt(1,numeroEle);
            rs = ps.executeQuery();
            while(rs.next()){
                Lecon uneLecon = new Lecon(
                       rs.getString(1)
                );
                LesLecons.add(uneLecon);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return LesLecons;
    }

    public ArrayList<Lecon> getDateLeconByLogin(String login, String role)  {

        ArrayList<Lecon> LesLecons = new ArrayList<>();

        try {
            if (Objects.equals(role, "Eleve")){
                ps = cnx.prepareStatement("SELECT lecon.date " +
                        "from lecon " +
                        "INNER JOIN eleve ON eleve.id = lecon.idEleve " +
                        "INNER JOIN user ON user.id = eleve.idUser "+
                        "WHERE user.login like ? " +
                        "GROUP BY lecon.date " +
                        "ORDER BY lecon.date ASC"
                );
            } else if (Objects.equals(role, "Moniteur")){
                ps = cnx.prepareStatement("SELECT lecon.date " +
                        "from lecon " +
                        "INNER JOIN moniteur ON moniteur.id = lecon.idMoniteur " +
                        "INNER JOIN user ON user.id = moniteur.idUser " +
                        "WHERE user.login like ? " +
                        "GROUP BY lecon.date " +
                        "ORDER BY lecon.date ASC"
                );
            }


            ps.setString(1,login);
            rs = ps.executeQuery();
            while(rs.next()){
                Lecon uneLecon = new Lecon(
                        rs.getString(1)
                );
                LesLecons.add(uneLecon);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return LesLecons;
    }
    /**
     * Ajoute une lecon pour un eleve
     * @param date
     * @param heure
     * @param idMoniteur
     * @param idEleve
     * @param idVehicule
     * @throws SQLException
     */
    public void addLecon(String date, String heure, int idMoniteur, int idEleve, int idVehicule) {
        try {
            ps = cnx.prepareStatement("INSERT INTO `lecon` (`date`, `heure`, `idMoniteur`, `idEleve`, `idVehicule`, `reglee`) VALUES ( ?, ?, ?, ?, ?, 0); ");

            ps.setString(1, date);
            ps.setString(2, heure);
            ps.setInt(3, idMoniteur);
            ps.setInt(4, idEleve);
            ps.setInt(5, idVehicule);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Recupere les dates et les heures des lecons
     * @param idEleve
     * @return ArrayList
     * @throws SQLException
     */
    public ArrayList<String> getDateLeconEleve(Integer idEleve) {


        ArrayList<String> dates = new ArrayList<String>();

        try {
            ps = cnx.prepareStatement("SELECT lecon.date, lecon.heure FROM lecon\n" +
                    "INNER JOIN eleve ON eleve.id = lecon.idEleve\n" +
                    "INNER JOIN moniteur ON moniteur.id = lecon.idMoniteur\n" +
                    "INNER JOIN user ON user.id = eleve.idUser OR user.id = moniteur.idUser\n" +
                    "WHERE user.id = ?"
            );
            ps.setInt(1,idEleve);
            rs = ps.executeQuery();

            while(rs.next()){
                dates.add(rs.getString(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return dates;
    }

    /**
     * Recupere les date entre 2 dates saisie
     * @param idUser
     * @param PremiereDate
     * @param DeuxiemeDate
     * @return ArrayList
     * @throws SQLException
     */
    public ArrayList<Lecon> getLeconEleveBetweenDate(Integer idUser, String PremiereDate, String DeuxiemeDate) {

        ArrayList<Lecon> LesLecons = new ArrayList<>();

        try {
            ps = cnx.prepareStatement("SELECT lecon.date FROM lecon \n" +
                    "INNER JOIN eleve ON eleve.id = lecon.idEleve\n" +
                    "INNER JOIN user ON user.id = eleve.idUser\n" +
                    "WHERE lecon.date >= ? AND lecon.date <= ? AND user.id = ? " +
                    "GROUP BY lecon.date " +
                    "ORDER BY lecon.date ASC"
            );

            ps.setString(1,PremiereDate);
            ps.setString(2, DeuxiemeDate);
            ps.setInt(3, idUser);
            rs = ps.executeQuery();
            while(rs.next()){
                Lecon uneLecon = new Lecon(
                        rs.getString(1));
                LesLecons.add(uneLecon);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return LesLecons;
    }
    public ArrayList<Lecon> getLeconMoniteurBetweenDate(Integer idUser, String PremiereDate, String DeuxiemeDate) {

        ArrayList<Lecon> LesLecons = new ArrayList<>();

        try {
            ps = cnx.prepareStatement("SELECT lecon.date FROM lecon " +
                    "INNER JOIN moniteur ON moniteur.id = lecon.idMoniteur " +
                    "INNER JOIN user ON user.id = moniteur.idUser " +
                    "WHERE lecon.date >= ? AND lecon.date <= ? AND user.id = ? " +
                    "GROUP BY lecon.date " +
                    "ORDER BY lecon.date ASC"
            );

            ps.setString(1,PremiereDate);
            ps.setString(2, DeuxiemeDate);
            ps.setInt(3, idUser);
            rs = ps.executeQuery();
            while(rs.next()){
                Lecon uneLecon = new Lecon(
                        rs.getString(1));
                LesLecons.add(uneLecon);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return LesLecons;
    }

    /**
     * Permet de regler ou pas une lecon
     * @param payer
     * @param id
     * @throws SQLException
     */
    public void PayerLecon(int payer, int id){
        try {
            if (payer == 0){
                ps = cnx.prepareStatement("UPDATE lecon SET reglee = ? " +
                        "WHERE id = ?");

            }
            if (payer == 1){
                ps = cnx.prepareStatement("UPDATE lecon SET reglee = ? " +
                        "WHERE id = ?");
            }
            ps.setInt(1, payer);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Recupere la date d'une lecon par l'id d'un moniteur
     * @param numeroMoni
     * @return
     */
    public ArrayList<Lecon> getDateLeconByMoniteurId(int numeroMoni)  {

        ArrayList<Lecon> LesLecons = new ArrayList<>();

        try {
            ps = cnx.prepareStatement("SELECT lecon.date " +
                    "from lecon " +
                    "INNER JOIN moniteur ON moniteur.id = lecon.idMoniteur " +
                    "WHERE moniteur.idUser = ? " +
                    "GROUP BY lecon.date " +
                    "ORDER BY lecon.date ASC"
            );

            ps.setInt(1,numeroMoni);
            rs = ps.executeQuery();
            while(rs.next()){
                Lecon uneLecon = new Lecon(
                        rs.getString(1)
                );
                LesLecons.add(uneLecon);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return LesLecons;
    }

    /**
     * Recupere les leçons d'un moniteur par rapport a une date
     * @param numeroMoni
     * @param date
     * @return
     */
    public ArrayList<Lecon> getLeconByMoniIdAndDate(int numeroMoni, String date)  {

        ArrayList<Lecon> LesLecons = new ArrayList<>();


        try {
            ps = cnx.prepareStatement("SELECT lecon.id, user.nom, user.prenom, vehicule.immatriculation,lecon.heure,lecon.reglee, categorie.libelle, categorie.prix " +
                    "from lecon " +
                    "inner join moniteur on lecon.idMoniteur = moniteur.id " +
                    "INNER JOIN eleve ON eleve.id = lecon.idEleve " +
                    "inner join user on eleve.idUser = user.id " +
                    "INNER JOIN vehicule on lecon.idVehicule = vehicule.id " +
                    "INNER join categorie on vehicule.idCategorie = categorie.id " +
                    "WHERE moniteur.idUser = ? " +
                    "And date Like ?"
            );

            ps.setInt(1,numeroMoni);
            ps.setString(2, date);
            rs = ps.executeQuery();
            while(rs.next()){
                Categorie categorie = new Categorie(rs.getString(7),rs.getFloat(8));
                User user = new User(rs.getString(2), rs.getString(3));
                Lecon uneLecon = new Lecon(
                        rs.getInt(1),
                        user,
                        rs.getString(4),
                        rs.getString(5),
                        rs.getInt(6),
                        categorie
                );
                LesLecons.add(uneLecon);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return LesLecons;
    }
    public ArrayList<String> getMoniteurDispo(String date, String heure){
        ArrayList<String> lesMoniteurs = new ArrayList<>();
        try {
            ps = cnx.prepareStatement("SELECT concat(user.nom,' ',user.prenom) " +
                    "FROM user " +
                    "WHERE user.role like 'ROLE_MONITEUR' " +
                    "AND user.id NOT IN(SELECT user.id " +
                    "FROM lecon " +
                    "INNER JOIN moniteur ON lecon.idMoniteur = moniteur.id " +
                    "INNER JOIN user on user.id = moniteur.idUser " +
                    "WHERE lecon.date LIKE ? " +
                    "AND heure like ? " +
                    "GROUP By user.id)"
                    );
            ps.setString(1, date);
            ps.setString(2, heure);
            rs = ps.executeQuery();
            while(rs.next()){
                lesMoniteurs.add(rs.getString(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lesMoniteurs;
    }

    public boolean VerifLeconEleve(int idUser){
        try {
            ps = cnx.prepareStatement("SELECT lecon.id from lecon where ideleve = (SELECT id from eleve where idUser = ?)");
            ps.setInt(1, idUser);
            rs = ps.executeQuery();
            if (!rs.next()){
                return true;
            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public boolean VerifLeconMoniteur(int idUser){
        try {
            ps = cnx.prepareStatement("SELECT lecon.id from lecon where ideleve = (SELECT id from moniteur where idUser = ?)");
            ps.setInt(1, idUser);
            rs = ps.executeQuery();
            if (!rs.next()){
                return true;
            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


}
