package Tools;

//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//import java.util.TimeZone;

//public class ConnexionBDD {
//
//    private static Connection cnx;
//
//    public ConnexionBDD() throws ClassNotFoundException, SQLException {
//        // serveur + BDD + user + pwd
//        String pilote = "com.mysql.jdbc.Driver";
//        Class.forName(pilote);
//
//        cnx = DriverManager.getConnection("jdbc:mysql://localhost/autoecole?serverTimezone="
//                + TimeZone.getDefault().getID(), "root", "root");
//    }
//
//    public static Connection getCnx() {
//        return cnx;
//    }
//}
