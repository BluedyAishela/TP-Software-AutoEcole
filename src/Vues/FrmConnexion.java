package Vues;

import Tools.ConnexionBDD;
import Tools.Function.ConnexionFunction;
import Vues.Admin.FrmAccueilResponsable;
import Vues.Eleve.FrmAccueil;
import Vues.Moniteur.FrmAccueilMoniteur;

import javax.swing.*;
import java.awt.event.*;
import java.sql.SQLException;

public class FrmConnexion extends JFrame {

    private JPanel pnlRoot;
    private JTextField txtUser;
    private JTextField txtPwd;
    private JButton btnLogin;
    private JLabel lblUser;
    private JLabel lblPwd;

    public FrmConnexion() throws SQLException, ClassNotFoundException {
        this.setTitle("Page de connexion - AutoEcole");
        this.setContentPane(pnlRoot);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);

        ConnexionBDD cnx = new ConnexionBDD();


        txtPwd.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                   Connexion();
                }
            }
        });
        btnLogin.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Connexion();
            }
        });

    }
    private void Connexion(){
//                    On instancie la classe qui nous permettra d'exécuter nos différentes fonctions
            ConnexionFunction connexionFunction = new ConnexionFunction();
//                    On vérifie que l'utilisateur existe bien
            if (connexionFunction.BoolConnexion(txtUser.getText(), txtPwd.getText())) {
//                        Permet de récupérer l'id de l'user et de le renvoyer dans les autres pages par la suite
                Integer idUser = connexionFunction.getIdUserByLoginAndPassword(txtUser.getText(), txtPwd.getText());
//                        On vérifie le rôle de l'élève pour le rediriger vers son espace utilisateur
                if (connexionFunction.verifRole(txtUser.getText(), txtPwd.getText()).equals("ROLE_ELEVE")) {
                    FrmAccueil frame = new FrmAccueil(idUser);
                    frame.setVisible(true);
                    dispose();
                } else if (connexionFunction.verifRole(txtUser.getText(), txtPwd.getText()).equals("ROLE_MONITEUR")) {
                    FrmAccueilMoniteur frame = new FrmAccueilMoniteur(idUser);
                    frame.setVisible(true);
                    dispose();
                } else if (connexionFunction.verifRole(txtUser.getText(), txtPwd.getText()).equals("ROLE_RESPONSABLE")) {
                    FrmAccueilResponsable frame = new FrmAccueilResponsable(idUser);
                    frame.setVisible(true);
                    dispose();
                } else if (connexionFunction.verifRole(txtUser.getText(), txtPwd.getText()).equals("ERROR")) {
                    JOptionPane.showMessageDialog(null, "Le rôle mentionné pour cet utilisateur n'existe pas, veuillez contacter un administrateur");
                }
            }else {
                txtPwd.setText("");
            }
    }
}
