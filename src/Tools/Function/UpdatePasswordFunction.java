package Tools.Function;

import Tools.ConnexionBDD;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UpdatePasswordFunction {
    private Connection cnx;
    private PreparedStatement ps;
    private ResultSet rs;

    public UpdatePasswordFunction() { cnx = ConnexionBDD.getCnx(); }

    public void UpdatePassword(int idUser, String password){
        try {
            ps = cnx.prepareStatement("UPDATE user SET `password` = ? Where id = ?");
            ps.setString(1, CryptPassword(password));
            ps.setInt(2, idUser);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public String CryptPassword(String password) {
        String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
        return hashed;
    }
}
