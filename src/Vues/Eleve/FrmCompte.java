package Vues.Eleve;

import Controllers.CtrlUser;
import Entities.User;
import Tools.ConnexionBDD;

import Vues.FrmCompteUpdatePwd;
import Vues.FrmConnexion;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class FrmCompte extends JFrame {
    private JPanel pnlRoot;
    private JToolBar jtbEleve;
    private JButton btnCompte;
    private JButton btnPlanning;
    private JButton btnLecon;
    private JButton btnStats;
    private JButton btnDeconnexion;
    private JLabel lblCompte;
    private JTextField txtTelephone;
    private JComboBox cboSexe;
    private JTextField txtNom;
    private JTextField txtPrenom;
    private JTextField txtAdresse;
    private JTextField txtVille;
    private JPanel pnlDate;
    private JTextField txtCodePstl;
    private JPanel PnlInfo;
    private JButton btnValider;
    private JTextField txtLogin;
    private JButton btnUpdateMdp;
    private JLabel lblLogin;
    private JLabel lblNom;
    private JLabel lblPrenom;
    private JLabel lblSexe;
    private JLabel lblDateNaissance;
    private JLabel lblAdresse;
    private JLabel lblPostal;
    private JLabel lblTelephone;
    private JLabel lblVille;
    private JDateChooser cldDate;

    public FrmCompte(Integer idUser) throws SQLException, ClassNotFoundException {
        ConnexionBDD cnx = new ConnexionBDD();

        jtbEleve.setFloatable(false);

        this.setTitle("Mon Compte - AutoEcole");
        this.setContentPane(pnlRoot);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.pack();
        this.setResizable(true);
        this.setLocationRelativeTo(null);

        CtrlUser ctrlUser = new CtrlUser();

        cldDate = new JDateChooser();
        cldDate.setDateFormatString("yyyy-MM-dd");
        pnlDate.add(cldDate);
        JTextFieldDateEditor editor = (JTextFieldDateEditor) cldDate.getDateEditor();
        editor.setEditable(false);

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

        btnCompte.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                try {
                    FrmCompte frame = new FrmCompte(idUser);
                    frame.setVisible(true);
                    dispose();
                } catch (SQLException | ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

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
                        JOptionPane.showMessageDialog(null, "La modification a bien été effectuée");
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

        btnPlanning.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                try {
                    FrmPlanning frame = new FrmPlanning(idUser);
                    frame.setVisible(true);
                    dispose();
                } catch (SQLException | ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        btnLecon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                FrmLecon frame = null;
                try {
                    frame = new FrmLecon(idUser);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                frame.setVisible(true);
                dispose();
            }
        });
        btnStats.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                try {
                    FrmStats frame = new FrmStats(idUser);
                    frame.setVisible(true);
                    dispose();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
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
