package Controllers;

import Entities.Eleve;
import Entities.User;
import Tools.ConnexionBDD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.SplittableRandom;

public class CtrlUser {
    private Connection cnx;
    private PreparedStatement ps;
    private ResultSet rs;

    private User unUser;
    private String sexe;

    public CtrlUser() {
        cnx = ConnexionBDD.getCnx();
    }

    public ArrayList<User> getAllUser() {

        ArrayList<User> LesUsers = new ArrayList<>();

        try {
            ps = cnx.prepareStatement("SELECT" +
                    " id," +
                    " login," +
                    " role," +
                    " Nom," +
                    " Prenom," +
                    " Sexe," +
                    " DateDeNaissance," +
                    " Adresse," +
                    " CodePostal," +
                    " Ville," +
                    " Telephone" +
                    " FROM user" +
                    " ORDER BY id"
            );
            rs = ps.executeQuery();

            while(rs.next()){
                String role = null;
                if (Objects.equals(rs.getString(3), "ROLE_ELEVE")){
                    role = "Eleve";
                }else if (Objects.equals(rs.getString(3), "ROLE_MONITEUR")){
                    role = "Moniteur";
                }else if (Objects.equals(rs.getString(3), "ROLE_RESPONSABLE")){
                    role = "Responsable";
                }
                User unUser = new User(
                        rs.getInt(1),
                        rs.getString(2),
                        role,
                        rs.getString(4),
                        rs.getString(5),
                        rs.getInt(6),
                        rs.getDate(7),
                        rs.getString(8),
                        rs.getString(9),
                        rs.getString(10),
                        rs.getString(11)
                );
                LesUsers.add(unUser);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return LesUsers;
    }

    public User getUserById(int codeUser) {

        try {
            ps = cnx.prepareStatement("SELECT " +
                    " id," +
                    " login," +
                    " role," +
                    " Nom," +
                    " Prenom," +
                    " Sexe," +
                    " DateDeNaissance," +
                    " Adresse," +
                    " CodePostal," +
                    " Ville," +
                    " Telephone" +
                    " FROM user " +
                    " Where id = ?"
            );
            ps.setInt(1,codeUser);
            rs = ps.executeQuery();

            while (rs.next()){
                unUser = new User(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getInt(6),
                        rs.getDate(7),
                        rs.getString(8),
                        rs.getString(9),
                        rs.getString(10),
                        rs.getString(11)
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return unUser;
    }


    public void UpdateAllUser(String login, String nom, String prenom, int sexe, String date, String adresse, String codePostal, String ville, String telephone, int idUser) {


        try {
            ps = cnx.prepareStatement("Update user set " +
                    " login = ?," +
                    " nom = ?," +
                    " prenom = ?," +
                    " sexe = ?," +
                    " datedenaissance = ?," +
                    " adresse = ?," +
                    " codePostal = ?," +
                    " ville = ?," +
                    " telephone = ?" +
                    " Where id = ?"
            );
            ps.setString(1, login);
            ps.setString(2, nom);
            ps.setString(3, prenom);
            ps.setInt(4, sexe);
            ps.setString(5, date);
            ps.setString(6, adresse);
            ps.setString(7, codePostal);
            ps.setString(8, ville);
            ps.setString(9, telephone);
            ps.setInt(10,idUser);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public String GetRole(int id){
        String role = null;
        try {
            ps = cnx.prepareStatement("SELECT role from user where id = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            rs.next();
            role = rs.getString(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return role;
    }
    public void UpdateEleveMoniteur(String cboRole, int idUser) {
        try {
            if (Objects.equals(cboRole, "ROLE_MONITEUR")) {

                ps = cnx.prepareStatement("DELETE FROM eleve where idUser = ?");
                ps.setInt(1, idUser);
                ps.executeUpdate();
                ps = cnx.prepareStatement("INSERT INTO `moniteur` (`id`, `idUser`) VALUES (NULL, ?)");
                ps.setInt(1, idUser);
                ps.executeUpdate();

            } else if (Objects.equals(cboRole, "ROLE_ELEVE")) {

                ps = cnx.prepareStatement("DELETE FROM moniteur where idUser = ?");
                ps.setInt(1, idUser);
                ps.executeUpdate();
                ps = cnx.prepareStatement("INSERT INTO `eleve` (`id`, `idUser`) VALUES (NULL, ?)");
                ps.setInt(1, idUser);
                ps.executeUpdate();

            }

            ps = cnx.prepareStatement("Update user set " +
                    " role = ?" +
                    " Where id = ?"
            );

            ps.setString(1, cboRole);
            ps.setInt(2, idUser);

            ps.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void UpdateNameUser(String login, int idUser){
        try {
            ps = cnx.prepareStatement("Update user set " +
                    " login = ?" +
                    " Where id = ?"
            );
            ps.setString(1, login);
            ps.setInt(2, idUser);


            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public ArrayList<String> GetAllMoniteur() {

        ArrayList<String> users = new ArrayList<String>();
        try {
            ps = cnx.prepareStatement("SELECT user.id, concat(user.nom,' ', user.prenom) " +
                    "FROM user " +
                    "WHERE role like 'ROLE_MONITEUR' " +
                    "ORDER BY user.id");
            rs = ps.executeQuery();
            while (rs.next()){
                users.add(rs.getString(2));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return users;
    }

    public ArrayList<String> getAllSexeCBO() {
        ArrayList<String> sexe = new ArrayList<String>();
        sexe.add("Homme");
        sexe.add("Femme");
        sexe.add("Autre");
        sexe.add("Je ne souhaite pas répondre");
        return sexe;
    }
    public ArrayList<User> SearchUserByLogin(String login){
        ArrayList<User> users = new ArrayList<User>();
        try {
            ps = cnx.prepareStatement("SELECT" +
                    " id," +
                    " login," +
                    " role," +
                    " Nom," +
                    " Prenom," +
                    " Sexe," +
                    " DateDeNaissance," +
                    " Adresse," +
                    " CodePostal," +
                    " Ville," +
                    " Telephone" +
                    " FROM user" +
                    " WHERE login LIKE ?" +
                    " ORDER BY id");
            ps.setString(1, login + "%");
            rs = ps.executeQuery();

            while(rs.next()){
                String role = null;
                if (Objects.equals(rs.getString(3), "ROLE_ELEVE")){
                    role = "Eleve";
                }else if (Objects.equals(rs.getString(3), "ROLE_MONITEUR")){
                    role = "Moniteur";
                }else if (Objects.equals(rs.getString(3), "ROLE_RESPONSABLE")){
                    role = "Responsable";
                }
                User unUser = new User(
                        rs.getInt(1),
                        rs.getString(2),
                        role,
                        rs.getString(4),
                        rs.getString(5),
                        rs.getInt(6),
                        rs.getDate(7),
                        rs.getString(8),
                        rs.getString(9),
                        rs.getString(10),
                        rs.getString(11)
                );
                users.add(unUser);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return users;
    }


    public ArrayList<User> getAllLoginsByRole(String role) {

        ArrayList<User> lesUsers = new ArrayList<>();

        try {
            ps = cnx.prepareStatement("SELECT user.id, user.login, user.nom, user.prenom " +
                    "FROM user " +
                    "WHERE role LIKE ?"
            );
            if (Objects.equals(role, "Eleve")){
                ps.setString(1, "ROLE_ELEVE");
            } else if (Objects.equals(role, "Moniteur")) {
                ps.setString(1, "ROLE_MONITEUR");
            }
            rs = ps.executeQuery();
            while(rs.next()){
                User unlogin = new User(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4)
                );
                lesUsers.add(unlogin);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lesUsers;
    }

    public ArrayList<User> getUserByLoginRole(String login, String role) {
        ArrayList<User> lesUsers = new ArrayList<>();
        try {
            ps = cnx.prepareStatement("SELECT user.id, user.login, user.nom, user.prenom" +
                    " FROM user " +
                    " Where login like ?" +
                    " And role Like ?"
            );
            ps.setString(1,"%" + login + "%");
            if (Objects.equals(role, "Eleve")){
                ps.setString(2, "ROLE_ELEVE");
            } else if (Objects.equals(role, "Moniteur")) {
                ps.setString(2, "ROLE_MONITEUR");
            }
            rs = ps.executeQuery();

            while(rs.next()){
                User unlogin = new User(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4)
                );
                lesUsers.add(unlogin);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lesUsers;
    }
}
