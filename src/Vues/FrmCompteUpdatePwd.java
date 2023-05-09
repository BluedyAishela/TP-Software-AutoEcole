package Vues;

import Controllers.CtrlUser;
import Entities.User;
import Tools.Function.UpdatePasswordFunction;
import Vues.Admin.FrmCompteResponsable;
import Vues.Admin.Utilisateurs.FrmUtilisateursResponsable;
import Vues.Eleve.FrmCompte;
import Vues.Moniteur.FrmCompteMoniteur;

import javax.swing.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.Objects;

public class FrmCompteUpdatePwd extends JFrame{
    private JPanel pnlRoot;
    private JLabel lblMdp;
    private JLabel lblMdpVerif;
    private JPasswordField txtMdp1;
    private JPasswordField txtMdp2;
    private JButton modifierLeMotDeButton;
    private CtrlUser ctrlUser;

    public FrmCompteUpdatePwd(int idUser){
        this.setTitle("Mon Compte - AutoEcole");
        this.setContentPane(pnlRoot);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.pack();
        this.setResizable(true);
        this.setLocationRelativeTo(null);

        ctrlUser = new CtrlUser();

        modifierLeMotDeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                updateMdp(idUser);
            }
        });

        txtMdp2.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    updateMdp(idUser);
                }
            }
        });
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e);
                ReturnCompte(idUser);
            }
        });
    }
    private void updateMdp(int idUser){
        if (!Objects.equals(txtMdp1.getText(), txtMdp2.getText())){
            JOptionPane.showMessageDialog(null, "Veuillez saisir le même mot de passe");
            txtMdp1.setText("");
            txtMdp2.setText("");
        }else if (Objects.equals(txtMdp1.getText(), "") || Objects.equals(txtMdp2.getText(), "")){
            JOptionPane.showMessageDialog(null, "Veuillez remplir les champs");
        }else if (txtMdp1.getText().length() > 50) {
            JOptionPane.showMessageDialog(null, "Veuillez saisir un mot de passe inférieur à 50 caractere");
        }else {
            UpdatePasswordFunction updatePasswordFunction = new UpdatePasswordFunction();
            updatePasswordFunction.UpdatePassword(idUser, txtMdp1.getText());
            JOptionPane.showMessageDialog(null, "La modification a été effectué");
            dispose();
        }
    }

    private void ReturnCompte(int idUser){
        User unUser = ctrlUser.getUserById(idUser);
        try {
            if (Objects.equals(unUser.getRole(), "ROLE_ELEVE")){
                FrmCompte frame = new FrmCompte(idUser);
                frame.setVisible(true);

            } else if (Objects.equals(unUser.getRole(), "ROLE_MONITEUR")){
                FrmCompteMoniteur frame = new FrmCompteMoniteur(idUser);
                frame.setVisible(true);

            } else if (Objects.equals(unUser.getRole(), "ROLE_RESPONSABLE")){
                FrmCompteResponsable frame = new FrmCompteResponsable(idUser);
                frame.setVisible(true);

            }
        } catch (SQLException | ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }
}
