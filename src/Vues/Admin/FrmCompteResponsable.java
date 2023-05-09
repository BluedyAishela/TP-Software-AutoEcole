package Vues.Admin;

import Controllers.CtrlUser;
import Entities.User;
import Tools.ConnexionBDD;
import Vues.Admin.Categorie.FrmCategorieResponsable;
import Vues.Admin.Statistiques.FrmStatistiquesResponsable;
import Vues.Admin.Utilisateurs.FrmUtilisateursResponsable;
import Vues.Admin.Vehicule.FrmVehiculeResponsable;
import Vues.FrmCompteUpdatePwd;
import Vues.FrmConnexion;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class FrmCompteResponsable extends JFrame {
    private JPanel pnlRoot;
    private JPanel PnlInfo;
    private JTextField txtNom;
    private JComboBox cboSexe;
    private JTextField txtTelephone;
    private JTextField txtVille;
    private JTextField txtCodePstl;
    private JTextField txtAdresse;
    private JPanel pnlDate;
    private JTextField txtPrenom;
    private JButton btnValider;
    private JTextField txtLogin;
    private JLabel lblCompte;
    private JToolBar jtbMoniteur;
    private JButton btnCompte;
    private JButton btnCategorie;
    private JButton btnDeconnexion;
    private JButton btnVehicule;
    private JButton btnUtilisateurs;
    private JButton btnStatistiques;
    private JButton btnPlanning;
    private JLabel lblNom;
    private JLabel lblPrenom;
    private JLabel lblSexe;
    private JLabel lblNaissance;
    private JLabel lblAdresse;
    private JLabel lblPostal;
    private JLabel lblVille;
    private JLabel lblTelephone;
    private JLabel lblLogin;
    private JButton btnUpdateMdp;
    private JDateChooser cldDate;

    public FrmCompteResponsable(int idUser) throws SQLException, ClassNotFoundException {
        ConnexionBDD cnx = new ConnexionBDD();

        jtbMoniteur.setFloatable(false);

        this.setTitle("Mon Compte - AutoEcole");
        this.setContentPane(pnlRoot);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.pack();
        this.setResizable(true);
        this.setLocationRelativeTo(null);


        cldDate = new JDateChooser();
        cldDate.setDateFormatString("yyyy-MM-dd");
        pnlDate.add(cldDate);

        CtrlUser ctrlUser = new CtrlUser();
        User user = ctrlUser.getUserById(idUser);

        DefaultComboBoxModel<String> defaultComboBoxModel = new DefaultComboBoxModel<>(ctrlUser.getAllSexeCBO().toArray(new String[1]));
        if(user.getSexe() == 0) {
            defaultComboBoxModel.setSelectedItem("Homme");
        } else if (user.getSexe() == 1) {
            defaultComboBoxModel.setSelectedItem("Femme");
        } else if (user.getSexe() == 2) {
            defaultComboBoxModel.setSelectedItem("Autre");
        } else if (user.getSexe() == 3) {
            defaultComboBoxModel.setSelectedItem("Je ne souhaite pas répondre");
        }
        cboSexe.setModel(defaultComboBoxModel);

        txtLogin.setText(user.getLogin());
        txtNom.setText(user.getNom());
        txtPrenom.setText(user.getPrenom());
        cldDate.setDate(user.getDatedenaissance());
        txtAdresse.setText(user.getAdresse());
        txtCodePstl.setText(user.getCodepostal());
        txtVille.setText(user.getVille());
        txtTelephone.setText(user.getTelephone());

        btnValider.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String date = sdf.format(cldDate.getDate());

                System.out.println(cboSexe.getSelectedIndex());

                if(txtLogin.getText().length() > 50)
                {
                    JOptionPane.showMessageDialog(null, "Votre identifiant est trop grand !");
                } else if(txtNom.getText().length() > 50) {
                    JOptionPane.showMessageDialog(null, "Votre nom est trop grand !");
                } else if(txtPrenom.getText().length() > 30) {
                    JOptionPane.showMessageDialog(null, "Votre prénom est trop grand !");
//                } else if(cboSexe.contains(null)) {
//                    JOptionPane.showMessageDialog(null, "Veuillez sélectionner un sexe");
                } else if(cldDate.getDate() == null) {
                    JOptionPane.showMessageDialog(null, "Veuillez entrer une date de naissance");
                } else if(txtAdresse.getText().length() > 50) {
                    JOptionPane.showMessageDialog(null, "Votre adresse est trop grande !");
                } else if(txtCodePstl.getText().length() > 7) {
                    JOptionPane.showMessageDialog(null, "Votre code postal est trop grand !");
                } else if(txtVille.getText().length() > 50) {
                    JOptionPane.showMessageDialog(null, "Le nom de votre ville est trop grand !");
                } else if(txtTelephone.getText().length() > 12) {
                    JOptionPane.showMessageDialog(null, "Votre numéro de téléphone est trop long !");
                } else {

                        ctrlUser.UpdateAllUser(
                                txtLogin.getText(),
                                txtNom.getText(),
                                txtPrenom.getText(),
                                cboSexe.getSelectedIndex(),
                                date,
                                txtAdresse.getText(),
                                txtCodePstl.getText(),
                                txtVille.getText(),
                                txtTelephone.getText(),
                                idUser
                        );
                        JOptionPane.showMessageDialog(null, "La modification à bien été effectuée");
                }
            }
        });
        btnUpdateMdp.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                FrmCompteUpdatePwd frame = new FrmCompteUpdatePwd(idUser);
                frame.setVisible(true);
                dispose();
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
        btnPlanning.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                FrmPlanningResponsable frame = new FrmPlanningResponsable(idUser);
                frame.setVisible(true);
                dispose();
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
}
