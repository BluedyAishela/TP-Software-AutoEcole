package Vues.Admin;

import Controllers.CtrlLecon;
import Controllers.CtrlUser;
import Tools.ModelJTable;
import Vues.Admin.Categorie.FrmCategorieResponsable;
import Vues.Admin.Statistiques.FrmStatistiquesResponsable;
import Vues.Admin.Utilisateurs.FrmUtilisateursResponsable;
import Vues.Admin.Vehicule.FrmVehiculeResponsable;
import Vues.FrmConnexion;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.Objects;

public class FrmPlanningResponsable extends JFrame{
    private JToolBar jtbMoniteur;
    private JButton btnCompte;
    private JButton btnUtilisateurs;
    private JButton btnVehicule;
    private JButton btnCategorie;
    private JButton btnStatistiques;
    private JButton btnDeconnexion;
    private JPanel pnlRoot;
    private JTable tblPlanning;
    private JTable tblDate;
    private JButton btnPlanning;
    private JTextField txtRechercheLogin;
    private JLabel lblRechercheLogin;
    private JButton btnRechercher;
    private JTable tblUser;
    private JComboBox cboRole;
    private ModelJTable modelJTable;
    private CtrlLecon ctrlLecon;
    private CtrlUser ctrlUser;

    public FrmPlanningResponsable(int idUser){

        jtbMoniteur.setFloatable(false);

        this.setTitle("Planning - AutoEcole");
        this.setContentPane(pnlRoot);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.pack();
        this.setResizable(true);
        this.setLocationRelativeTo(null);

        ctrlLecon = new CtrlLecon();
        ctrlUser = new CtrlUser();


        setTblUser();

        tblUser.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                setTblDate();
            }
        });
        tblDate.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                setTblPlanning();
            }
        });
        cboRole.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                setTblUser();
                resetTblDatePlanning();
            }
        });
        btnRechercher.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                setTblUserRecherche();
                txtRechercheLogin.setText("");
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

    private void setTblUser(){
        modelJTable = new ModelJTable();
        modelJTable.loadUser(ctrlUser.getAllLoginsByRole(cboRole.getSelectedItem().toString()));
        tblUser.setModel(modelJTable);
        tblUser.setAutoCreateRowSorter(true);
    }
    private void setTblUserRecherche(){
        modelJTable = new ModelJTable();
        modelJTable.loadUser(ctrlUser.getUserByLoginRole(txtRechercheLogin.getText(), cboRole.getSelectedItem().toString()));
        tblUser.setModel(modelJTable);
        tblUser.setAutoCreateRowSorter(true);
    }

    private void setTblDate() {
        modelJTable = new ModelJTable();
        modelJTable.loadDataDates(ctrlLecon.getDateLeconByLogin(tblUser.getValueAt(tblUser.getSelectedRow(),1).toString(), cboRole.getSelectedItem().toString()));
        tblDate.setModel(modelJTable);
        tblDate.setAutoCreateRowSorter(true);
    }
    private void setTblPlanning(){
        modelJTable = new ModelJTable();
        if (Objects.equals(cboRole.getSelectedItem().toString(), "Eleve")){
            modelJTable.loadDataLeconEle(ctrlLecon.getLeconByEleveIdAndDate(Integer.parseInt(tblUser.getValueAt(tblUser.getSelectedRow(),0).toString()), tblDate.getValueAt(tblDate.getSelectedRow(),0).toString()));
        } else if (Objects.equals(cboRole.getSelectedItem().toString(), "Moniteur")) {
            modelJTable.loadDataLeconMoni(ctrlLecon.getLeconByMoniIdAndDate(Integer.parseInt(tblUser.getValueAt(tblUser.getSelectedRow(),0).toString()), tblDate.getValueAt(tblDate.getSelectedRow(),0).toString()));
        }
        tblPlanning.setModel(modelJTable);
        tblPlanning.setAutoCreateRowSorter(true);
    }
    private void resetTblDatePlanning(){
        tblPlanning.setModel(new DefaultTableModel());
        tblPlanning.setAutoCreateRowSorter(true);
        tblDate.setModel(new DefaultTableModel());
        tblDate.setAutoCreateRowSorter(true);
    }
}
