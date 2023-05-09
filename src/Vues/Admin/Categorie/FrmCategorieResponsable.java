package Vues.Admin.Categorie;

import Controllers.CtrlCategorie;
import Entities.Categorie;
import Tools.ModelJTable;
import Vues.Admin.FrmCompteResponsable;
import Vues.Admin.FrmPlanningResponsable;
import Vues.Admin.Statistiques.FrmStatistiquesResponsable;
import Vues.Admin.Utilisateurs.FrmUtilisateursResponsable;
import Vues.Admin.Vehicule.FrmVehiculeResponsable;
import Vues.FrmConnexion;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;

public class FrmCategorieResponsable extends JFrame{
    private JPanel pnlRoot;
    private JTable tblCategorie;
    private JTextField txtLibelle;
    private JLabel lblLibelle;
    private JScrollPane JScrollPane;
    private JButton btnAjouter;
    private JLabel lblCategorie;

    private JLabel lblPrix;
    private JButton btnModifier;
    private JButton btnSupprimer;
    private JTextField txtRechercheLibelle;
    private JLabel lblRechercheLibelle;
    private JButton btnReset;
    private JButton btnRechercher;
    private JButton btnCompte;
    private JButton btnDeconnexion;
    private JButton btnCategorie;
    private JButton btnVehicule;
    private JSpinner spnPrix;
    private JToolBar jtbMoniteur;
    private JButton btnUtilisateurs;
    private JButton btnStatistiques;
    private JButton btnPlanning;
    private ModelJTable modelJTable;
    private CtrlCategorie ctrlCategorie;


    public FrmCategorieResponsable(Integer idUser){
        this.setTitle("Categorie - AutoEcole");
        this.setContentPane(pnlRoot);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.pack();
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        ctrlCategorie = new CtrlCategorie();
        modelJTable = new ModelJTable();
        setTblCategorie();

        SpinnerNumberModel model = new SpinnerNumberModel(0.0, 0.0, null, 0.5);
        spnPrix.setModel(model);

        btnAjouter.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                ArrayList<Categorie> test = ctrlCategorie.SearchCategorieByLibelle(txtLibelle.getText());
                if (txtLibelle.getText().isEmpty())
                {
                    JOptionPane.showMessageDialog(null, "Veuillez saisir un libelle");
                } else if (txtLibelle.getText().length()>50) {
                    JOptionPane.showMessageDialog(null,"Veuillez saisir un libelle avec un minimum de 50 caractères");
                } else if (!ctrlCategorie.SearchCategorieByLibelle(txtLibelle.getText()).isEmpty()){
                    JOptionPane.showMessageDialog(null, "Une catégorie porte déjà ce libelle");
                } else {
                    ctrlCategorie.AddCategorie(txtLibelle.getText(), Double.parseDouble(spnPrix.getValue().toString()));
                    setTblCategorie();
                }

            }
        });
        btnModifier.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (tblCategorie.getSelectedRowCount() == 0){
                    JOptionPane.showMessageDialog(null, "Sélectionnez une catégorie à modifier");
                }else {
                    FrmUpdateCategorieResponsable frame = new FrmUpdateCategorieResponsable(Integer.parseInt(tblCategorie.getValueAt(tblCategorie.getSelectedRow(),0).toString()),tblCategorie.getValueAt(tblCategorie.getSelectedRow(),1).toString(), tblCategorie.getValueAt(tblCategorie.getSelectedRow(),2).toString(), idUser);
                    frame.setVisible(true);
                    dispose();
                }
            }
        });
        btnSupprimer.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (tblCategorie.getSelectedRowCount() == 0){
                    JOptionPane.showMessageDialog(null, "Selectionnez une catégorie à modifier");
                }else if (ctrlCategorie.VerifyCategorieVehicule(Integer.parseInt(tblCategorie.getValueAt(tblCategorie.getSelectedRow(),0).toString()))) {
                    JOptionPane.showMessageDialog(null, "Cette catégorie est liée à un véhicule");
                }else if (ctrlCategorie.VerifyCategorieLicence(Integer.parseInt(tblCategorie.getValueAt(tblCategorie.getSelectedRow(),0).toString()))){
                    JOptionPane.showMessageDialog(null, "Un moniteur possède cette licence");
                }else {
                    ctrlCategorie.DeleteCategorie(Integer.parseInt(tblCategorie.getValueAt(tblCategorie.getSelectedRow(), 0).toString()));
                    setTblCategorie();
                }
            }
        });
        btnRechercher.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                modelJTable.loadCategorie(ctrlCategorie.SearchCategorieByLibelle(txtRechercheLibelle.getText()));
                tblCategorie.setModel(modelJTable);
            }
        });
        btnReset.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                setTblCategorie();
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
    private void setTblCategorie(){
        modelJTable.loadCategorie(ctrlCategorie.getAllCategories());
        tblCategorie.setModel(modelJTable);
        tblCategorie.setAutoCreateRowSorter(true);
    }

}
