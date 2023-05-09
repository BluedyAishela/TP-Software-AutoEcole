package Vues;

import Tools.ConnexionBDD;
import Tools.Function.InscriptionFunction;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

public class FrmInscription extends JFrame {
    private JButton btn;
    private JPanel pnlRoot;
    private JTextField login;
    private JTextField pwd;
    private InscriptionFunction inscriptionFunction;

    public FrmInscription() throws SQLException, ClassNotFoundException {
        this.setTitle("Page d'inscription - AutoEcole");
        this.setContentPane(pnlRoot);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);

        ConnexionBDD cnx = new ConnexionBDD();
        inscriptionFunction = new InscriptionFunction();
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                inscriptionFunction.InscriptionUser(login.getText(),pwd.getText());
                JOptionPane.showMessageDialog(null,"utilisateur ajouté");
                login.setText("");
            }
        });
    }
}
