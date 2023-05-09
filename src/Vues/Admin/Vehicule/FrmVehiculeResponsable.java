package Vues.Admin.Vehicule;

import Controllers.CtrlCategorie;
import Controllers.CtrlVehicule;
import Tools.ModelJTable;
import Vues.Admin.Categorie.FrmCategorieResponsable;
import Vues.Admin.FrmCompteResponsable;
import Vues.Admin.FrmPlanningResponsable;
import Vues.Admin.Statistiques.FrmStatistiquesResponsable;
import Vues.Admin.Utilisateurs.FrmUtilisateursResponsable;
import Vues.FrmConnexion;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.time.Year;

public class FrmVehiculeResponsable extends JFrame {
    private JPanel pnlRoot;
    private JTable tblVehicule;
    private JButton btnSupprimer;
    private JButton btnModifier;
    private JTextField txtImmatriculation;
    private JTextField txtMarque;
    private JTextField txtModele;
    private JTextField txtCategorie;
    private JLabel lblImmatriculation;
    private JLabel lblMarque;
    private JLabel lblModele;
    private JLabel lblAnnee;
    private JLabel lblCategorie;
    private JButton btnAjouter;
    private JLabel lblVehicule;
    private JComboBox cboCategorie;
    private JSpinner spnAnnee;
    private JButton btnCompte;
    private JButton btnVehicule;
    private JButton btnCategorie;
    private JButton btnDeconnexion;
    private JLabel lblRechercheImm;
    private JButton btnRecherche;
    private JButton btnReset;
    private JTextField txtRechercheImm;
    private JLabel lblRechercheCat;
    private JComboBox cboRechercheCat;
    private JLabel lblRecherche;
    private JPanel pnlRecherche;
    private JPanel pnlAjout;
    private JLabel lblAjout;
    private JTextField txtRechercheCat;
    private JToolBar jtbMoniteur;
    private JButton btnUtilisateurs;
    private JButton btnStatistiques;
    private JButton btnPlanning;
    private ModelJTable modelJTable;
    private CtrlVehicule ctrlVehicule;

    public FrmVehiculeResponsable(Integer idUser){
        this.setTitle("Vehicule - AutoEcole");
        this.setContentPane(pnlRoot);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.pack();
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        ctrlVehicule = new CtrlVehicule();
        modelJTable = new ModelJTable();
        setTblVehicule();
        setCboCategorie();
        spnAnnee.setModel(new SpinnerNumberModel(Year.now().getValue(), Year.now().getValue()-50, Year.now().getValue(), 1));
        JFormattedTextField editortf = ((JSpinner.DefaultEditor) spnAnnee.getEditor()).getTextField();
        editortf.setEditable(false);

        btnAjouter.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                if (txtImmatriculation.getText().isEmpty() || txtImmatriculation.getText().length() != 9){
                    JOptionPane.showMessageDialog(null,"Saisissez une immatriculation et contenant 9 caractères maximum");
                    txtImmatriculation.setText("");
                }else if (txtMarque.getText().isEmpty() || txtMarque.getText().length() >=50) {
                    JOptionPane.showMessageDialog(null,"Saisissez une marque et contenant 50 caractères maximum");
                    txtMarque.setText("");
                } else if (txtModele.getText().isEmpty() || txtModele.getText().length() >=50) {
                    JOptionPane.showMessageDialog(null,"Saisissez un modèle et contenant 50 caractères maximum");
                    txtModele.setText("");
                } else if (ctrlVehicule.VerifyImmatriculation(txtImmatriculation.getText())){
                    JOptionPane.showMessageDialog(null,"Un véhicule de la même immatriculation existe déjà, saisissez une nouvelle immatriculation");
                    txtImmatriculation.setText("");
                }else {
                    ctrlVehicule.AddVehicule(txtImmatriculation.getText(), txtMarque.getText(), txtModele.getText(), (int) spnAnnee.getValue(), (String) cboCategorie.getSelectedItem());
                    setTblVehicule();
                    txtImmatriculation.setText("");
                    txtMarque.setText("");
                    txtModele.setText("");
                    spnAnnee.setModel(new SpinnerNumberModel(Year.now().getValue(), Year.now().getValue()-50, Year.now().getValue(), 1));
                    editortf.setEditable(false);
                }
            }
        });
        btnSupprimer.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (tblVehicule.getSelectedRowCount() == 0) {
                    JOptionPane.showMessageDialog(null,"Vous devez sélectionner un véhicule à supprimer");
                }else {
                    if (ctrlVehicule.VerifyVehiculeLecon(tblVehicule.getValueAt(tblVehicule.getSelectedRow(), 1).toString())) {
                        JOptionPane.showMessageDialog(null, "Ce véhicule est lié à une leçon et ne peut être supprimé");
                    } else {
                        ctrlVehicule.DeleteVehicule(tblVehicule.getValueAt(tblVehicule.getSelectedRow(), 1).toString());
                        setTblVehicule();
                    }
                }
            }
        });
        btnModifier.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (tblVehicule.getSelectedRowCount() == 0){
                    JOptionPane.showMessageDialog(null,"Veuillez saisir un véhicule à modifier");
                }else {
                    int id = Integer.parseInt(tblVehicule.getValueAt(tblVehicule.getSelectedRow(),0).toString());
                    String immatriculation = tblVehicule.getValueAt(tblVehicule.getSelectedRow(),1).toString();
                    String marque = tblVehicule.getValueAt(tblVehicule.getSelectedRow(),2).toString();
                    String modele = tblVehicule.getValueAt(tblVehicule.getSelectedRow(),3).toString();
                    int annee = Integer.parseInt(tblVehicule.getValueAt(tblVehicule.getSelectedRow(),4).toString());
                    String categorie = tblVehicule.getValueAt(tblVehicule.getSelectedRow(),5).toString();

                    FrmUpdateVehiculeResponsable frame = new FrmUpdateVehiculeResponsable(id, immatriculation, marque, modele, annee, categorie, idUser);
                    frame.setVisible(true);
                    dispose();
                }

            }
        });
        btnRecherche.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (!txtRechercheCat.getText().isEmpty() && !txtRechercheImm.getText().isEmpty()){
                    modelJTable.loadVehicule(ctrlVehicule.SearchVehiculeByCatAndImm(txtRechercheImm.getText(), txtRechercheCat.getText()));
                    tblVehicule.setModel(modelJTable);
                } else if (!txtRechercheImm.getText().isEmpty()){
                    modelJTable.loadVehicule(ctrlVehicule.SearchVehiculeByCatAndImm(txtRechercheImm.getText(),null));
                    tblVehicule.setModel(modelJTable);
                } else if (!txtRechercheCat.getText().isEmpty()) {
                    modelJTable.loadVehicule(ctrlVehicule.SearchVehiculeByCatAndImm(null, txtRechercheCat.getText()));
                    tblVehicule.setModel(modelJTable);
                } else if (txtRechercheCat.getText().isEmpty() || txtRechercheImm.getText().isEmpty()) {
                    setTblVehicule();
                }
                txtRechercheCat.setText("");
                txtRechercheImm.setText("");
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
        btnVehicule.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                FrmVehiculeResponsable frame = new FrmVehiculeResponsable(idUser);
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

    }


    private void setCboCategorie(){
        CtrlCategorie ctrlCategorie = new CtrlCategorie();
        cboCategorie.setModel(new DefaultComboBoxModel<String>(ctrlCategorie.getAllCategorieLibelle().toArray(new String[0])));
    }
    private void setTblVehicule(){
        modelJTable.loadVehicule(ctrlVehicule.getAllVehicules());
        tblVehicule.setModel(modelJTable);
        tblVehicule.setAutoCreateRowSorter(true);
    }
}
