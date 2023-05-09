package Tools.Function;

import Tools.ConnexionBDD;
import org.mindrot.jbcrypt.BCrypt;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;
import java.security.Key;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnexionFunction {

    private Connection cnx;
    private PreparedStatement ps;
    private ResultSet rs;

    public ConnexionFunction() { cnx = ConnexionBDD.getCnx(); }

    public boolean BoolConnexion(String Login, String Password) {
        try {
            ps = cnx.prepareStatement("SELECT login, password FROM user WHERE Login = ?");
            ps.setString(1,Login);
            rs = ps.executeQuery();
            BCrypt bCrypt = new BCrypt();

            if (Login.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Il est requis de compléter le champ correspondant au login");
                return false;
            }
            if (Password.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Il est requis de compléter le champ correspondant au mot de passe");
                return false;
            }
            if (!rs.next()) {
                JOptionPane.showMessageDialog(null, "L'utilisateur saisi n'existe pas");
                return false;
            }
            if (!bCrypt.checkpw(Password, rs.getString(2))) {
                JOptionPane.showMessageDialog(null,"Le mot de passe saisi n'est pas correct");
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

//    public String ConnexionDecrypt(String password){
//
//        BCrypt bCrypt = new BCrypt();
//        String decrypt = bCrypt.checkpw(password,)
//        return password;
//    }

    public String verifRole(String Login, String Password) {

        try {
            ps = cnx.prepareStatement("SELECT role FROM user WHERE login = ?");
            ps.setString(1, Login);
//        ps.setString(2, Password);
            rs = ps.executeQuery();
            rs.next();

            if(rs.getString(1).equals("ROLE_ELEVE")) {
                return "ROLE_ELEVE";
            } else if (rs.getString(1).equals("ROLE_MONITEUR")) {
                return "ROLE_MONITEUR";
            } else if (rs.getString(1).equals("ROLE_RESPONSABLE")) {
                return "ROLE_RESPONSABLE";
            } else {
                return "ERROR";
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public Integer getIdUserByLoginAndPassword(String login, String password) {

        try {
//            String hash = BCrypt.hashpw(password, BCrypt.gensalt());
            ps = cnx.prepareStatement("SELECT id FROM user WHERE login = ?");
            ps.setString(1, login);
//            ps.setString(2, hash);
            rs = ps.executeQuery();
            rs.next();

            return rs.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
