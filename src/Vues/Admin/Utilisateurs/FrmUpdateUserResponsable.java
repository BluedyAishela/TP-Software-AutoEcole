package Vues.Admin.Utilisateurs;

import Controllers.CtrlLecon;
import Controllers.CtrlUser;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Objects;

public class FrmUpdateUserResponsable extends JFrame {
    private JPanel pnlRoot;
    private JTextField txtLogin;
    private JLabel lblLogin;
    private JLabel lblRole;
    private JComboBox cboRole;
    private JButton btnModifier;
    private CtrlUser ctrlUser;

    public FrmUpdateUserResponsable(int idUser, int idModif, String login, String role){
        this.setTitle("Utilisateurs - AutoEcole");
        this.setContentPane(pnlRoot);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.pack();
        this.setResizable(true);
        this.setLocationRelativeTo(null);
        ctrlUser = new CtrlUser();
        CtrlLecon ctrlLecon = new CtrlLecon();
        txtLogin.setText(login);
        setCboRole(role);
        btnModifier.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (txtLogin.getText().isEmpty()){
                    JOptionPane.showMessageDialog(null,"Veuillez saisir un login");
                }else if (txtLogin.getText().length() > 50) {
                    JOptionPane.showMessageDialog(null,"Veuillez saisir un login avec une longueur de 50 caractères maximum");
                    txtLogin.setText(login);
                }else {
                    ctrlUser.UpdateNameUser(txtLogin.getText(), idModif);
                    if (!Objects.equals(cboRole.getSelectedItem(), ctrlUser.GetRole(idModif))) {
                        if (ctrlLecon.VerifLeconEleve(idModif) && ctrlLecon.VerifLeconMoniteur(idModif)) {
                            ctrlUser.UpdateEleveMoniteur((String) cboRole.getSelectedItem(), idModif);
                            JOptionPane.showMessageDialog(null,"Le rôle a été changé");
                        }
                        else {
                            JOptionPane.showMessageDialog(null, "L'utilisateur est affecté à une leçon. Le rôle ne peut être changé!");
                        }
                    }
                    JOptionPane.showMessageDialog(null,"Le nom a été changé");
                    dispose();
                }
            }
        });
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e);
                FrmUtilisateursResponsable frame = new FrmUtilisateursResponsable(idUser);
                frame.setVisible(true);
            }
        });
    }
    private void setCboRole(String role){
        String[] lstRoles = new String[] {"ROLE_ELEVE", "ROLE_MONITEUR"};
        DefaultComboBoxModel<String> defaultComboBoxModel = new DefaultComboBoxModel<>(lstRoles);
        for (String unRole : lstRoles){
            if (Objects.equals(role, "Eleve")){
                role = "ROLE_ELEVE";
            } else if (Objects.equals(role, "Moniteur")){
                role = "ROLE_MONITEUR";
            }
            if (Objects.equals(unRole, role)){
                defaultComboBoxModel.setSelectedItem(unRole);
            }
        }
        cboRole.setModel(defaultComboBoxModel);

    }
}
