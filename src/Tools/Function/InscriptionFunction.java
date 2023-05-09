package Tools.Function;

import Tools.ConnexionBDD;
import org.mindrot.jbcrypt.BCrypt;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InscriptionFunction {

    private Connection cnx;
    private PreparedStatement ps;
    private ResultSet rs;

    public InscriptionFunction() { cnx = ConnexionBDD.getCnx(); }

    public void InscriptionUser(String login, String password) {

        try {
            ps = cnx.prepareStatement("INSERT INTO `user`(`login`, `password`, `role`) VALUES (?, ?, ?)");
            ps.setString(1, login);
            ps.setString(2, CryptPassword(password));
            ps.setString(3, "ROLE_ELEVE");
            ps.executeUpdate();

            ps = cnx.prepareStatement("SELECT id from user where login = ?");
            ps.setString(1, login);
            rs = ps.executeQuery();
            rs.next();

            ps = cnx.prepareStatement("INSERT INTO `eleve` (`idUser`) VALUES (?)");
            ps.setInt(1, rs.getInt(1));
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }



    }

    public void InscriptionMoniteur(String login, String password) {

        try {
            ps = cnx.prepareStatement("INSERT INTO `user`(`login`, `password`, `role`) VALUES (?, ?, ?)");
            ps.setString(1, login);
            ps.setString(2, CryptPassword(password));
            ps.setString(3, "ROLE_MONITEUR");
            ps.executeUpdate();

            ps = cnx.prepareStatement("SELECT id from user where login = ?");
            ps.setString(1, login);
            rs = ps.executeQuery();
            rs.next();

            ps = cnx.prepareStatement("INSERT INTO `moniteur` (`idUser`) VALUES (?)");
            ps.setInt(1, rs.getInt(1));
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void InscriptionResponsable(String login, String password) throws SQLException {

        ps = cnx.prepareStatement("INSERT INTO `user`(`login`, `password`, `role`, `nom`, `prenom`, `sexe`, `datedenaissance`, `adresse`, `codepostal`, `ville`, `telephone`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        ps.setString(1, login);
        ps.setString(2, CryptPassword(password));
        ps.setString(3, "ROLE_RESPONSABLE");
        ps.setString(4, "Kageno");
        ps.setString(5, "Cid");
        ps.setInt(6, 1);
        ps.setString(7, "2001-10-04");
        ps.setString(8, "28 Rue Charles");
        ps.setString(9, "77600");
        ps.setString(10, "Lille");
        ps.setString(11, "0674359247");

        ps.executeUpdate();
    }


    public String CryptPassword(String password) {
        BCrypt bCrypt = new BCrypt();
        String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
        return hashed;
    }
}
