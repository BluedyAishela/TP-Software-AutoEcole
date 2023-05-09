package Vues.Admin.Utilisateurs;

import Controllers.CtrlUser;
import Tools.Function.UpdatePasswordFunction;
import Tools.ModelJTable;
import Vues.Admin.Categorie.FrmCategorieResponsable;
import Vues.Admin.FrmCompteResponsable;
import Vues.Admin.FrmPlanningResponsable;
import Vues.Admin.Statistiques.FrmStatistiquesResponsable;
import Vues.Admin.Vehicule.FrmVehiculeResponsable;
import Vues.FrmConnexion;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.Objects;

public class FrmUtilisateursResponsable extends JFrame {
    private JPanel pnlRoot;
    private JToolBar jtbMoniteur;
    private JButton btnCompte;
    private JButton btnUtilisateurs;
    private JButton btnVehicule;
    private JButton btnCategorie;
    private JButton btnStatistiques;
    private JButton btnDeconnexion;
    private JTable tblUtilisateurs;
    private JButton btnModifier;
    private JTextField txtRechercheLogin;
    private JLabel lblRecherchlLogin;
    private JButton btnRecherche;
    private JButton btnReset;
    private JButton btnPlanning;
    private JButton btnResetMDP;
    private CtrlUser ctrlUser;
    private ModelJTable modelJTable;

    public FrmUtilisateursResponsable(int idUser){
        this.setTitle("Utilisateurs - AutoEcole");
        this.setContentPane(pnlRoot);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.pack();
        this.setResizable(true);
        this.setLocationRelativeTo(null);


        ctrlUser = new CtrlUser();
        modelJTable = new ModelJTable();

        setTblUtilisateurs();

        btnRecherche.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                modelJTable.loadDataUser(ctrlUser.SearchUserByLogin(txtRechercheLogin.getText()));
                tblUtilisateurs.setModel(modelJTable);
                txtRechercheLogin.setText("");
            }
        });
        btnReset.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                setTblUtilisateurs();
                txtRechercheLogin.setText("");
            }
        });

        btnModifier.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (tblUtilisateurs.getSelectedRowCount() == 0) {
                    JOptionPane.showMessageDialog(null, "Veuillez saisir un utilisateur à modifier");
                } else{
                    int idModif = Integer.parseInt(tblUtilisateurs.getValueAt(tblUtilisateurs.getSelectedRow(),0).toString());
                    String login = tblUtilisateurs.getValueAt(tblUtilisateurs.getSelectedRow(),1).toString();
                    String role = tblUtilisateurs.getValueAt(tblUtilisateurs.getSelectedRow(),4).toString();
                    if (Objects.equals(role, "Responsable")){
                        JOptionPane.showMessageDialog(null,"Vous ne pouvez pas modifier un Responsable");
                    }else {
                        FrmUpdateUserResponsable frame = new FrmUpdateUserResponsable(idUser, idModif, login, role);
                        frame.setVisible(true);
                        dispose();
                    }
                }
            }
        });
        btnResetMDP.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                UpdatePasswordFunction updatePasswordFunction = new UpdatePasswordFunction();
                if (tblUtilisateurs.getSelectedRowCount() == 0) {
                    JOptionPane.showMessageDialog(null,"Veuillez saisir un utilisateur");
                }else{
                    int result = JOptionPane.showConfirmDialog(null,"Voulez-vous vraiment réinitialiser le mot de passe de cet utilisateur ?", "Reset password",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE);
                    if (result == JOptionPane.YES_NO_OPTION){
                        updatePasswordFunction.UpdatePassword(Integer.parseInt(tblUtilisateurs.getValueAt(tblUtilisateurs.getSelectedRow(),0).toString()), "password");
                        JOptionPane.showMessageDialog(null, "Le mot de passe de l'utilisateur à été réinitialisé");
                    }
                }
            }
        });



        btnCategorie.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                FrmCategorieResponsable frame = new FrmCategorieResponsable(idUser);
                frame.setVisible(true);
                dispose();
            }
        });
        btnPlanning.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                FrmPlanningResponsable frame = new FrmPlanningResponsable(idUser);
                frame.setVisible(true);
                dispose();
            }
        });

        btnCompte.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                try {
                    FrmCompteResponsable frame = new FrmCompteResponsable(idUser);
                    frame.setVisible(true);
                    dispose();
                } catch (SQLException | ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
        btnVehicule.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                FrmVehiculeResponsable frame = new FrmVehiculeResponsable(idUser);
                frame.setVisible(true);
                dispose();
            }
        });
        btnUtilisateurs.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                FrmUtilisateursResponsable frame = new FrmUtilisateursResponsable(idUser);
                frame.setVisible(true);
                dispose();
            }
        });
        btnStatistiques.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                FrmStatistiquesResponsable frame = new FrmStatistiquesResponsable(idUser);
                frame.setVisible(true);
                dispose();
            }
        });
        btnDeconnexion.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                try {
                    FrmConnexion frame = new FrmConnexion();
                    frame.setVisible(true);
                    dispose();
                } catch (SQLException | ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

    }

    private void setTblUtilisateurs(){
        modelJTable.loadDataUser(ctrlUser.getAllUser());
        tblUtilisateurs.setModel(modelJTable);
        tblUtilisateurs.setAutoCreateRowSorter(true);
    }
}
