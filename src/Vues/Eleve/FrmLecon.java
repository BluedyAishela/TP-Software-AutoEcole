package Vues.Eleve;

import Controllers.*;
import Vues.FrmConnexion;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class FrmLecon extends JFrame {
    private JPanel pnlRoot;
    private JToolBar jtbEleve;
    private JButton btnCompte;
    private JButton btnPlanning;
    private JButton btnLecon;
    private JButton btnStats;
    private JButton btnDeconnexion;
    private JButton btnValider;
    private JComboBox cboMoniteur;
    private JSpinner spnHeure;
    private JPanel pnlDate;
    private JLabel lblDate;
    private JLabel lblHeure;
    private JLabel lblMoniteur;
    private JLabel lblVehicule;
    private JLabel lblLecon;
    private JComboBox cboVehicule;
    private JComboBox cboTemps;
    private JComboBox cboCategorie;
    private JLabel lblCategorie;
    private JDateChooser cldDate;
    private CtrlVehicule ctrlVehicule;
    private CtrlLecon ctrlLecon;
    private Integer idEleve;
    private Integer idMoniteur;
    private String dateformat;
    private Integer idVehicule;
    private Integer idUser;
    private CtrlMoniteur ctrlMoniteur;
    private CtrlEleve ctrlEleve;
    private CtrlCategorie ctrlCategorie;



    public FrmLecon(Integer idUser) throws SQLException {

        //        JToolBar -> Non possibilité de déplacer l'élement
        jtbEleve.setFloatable(false);

//        Diverses informations concernant la fenêtre
        this.setTitle("Leçon - AutoEcole");
        this.setContentPane(pnlRoot);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.pack();
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        ctrlVehicule = new CtrlVehicule();
        ctrlMoniteur = new CtrlMoniteur();
        ctrlLecon = new CtrlLecon();
        ctrlEleve = new CtrlEleve();
        ctrlCategorie = new CtrlCategorie();

        cldDate = new JDateChooser();
        cldDate.setDateFormatString("yyyy-MM-dd");
        pnlDate.add(cldDate);
        long milisec = System.currentTimeMillis();
        java.sql.Date date = new Date(milisec);
        cldDate.setMinSelectableDate(date);

        JTextFieldDateEditor editor = (JTextFieldDateEditor) cldDate.getDateEditor();
        editor.setEditable(false);

        spnHeure.setModel(new SpinnerNumberModel(8, 8, 10, 1));

        JFormattedTextField editortf = ((JSpinner.DefaultEditor) spnHeure.getEditor()).getTextField();
        editortf.setEditable(false);

        cldDate.getDateEditor().addPropertyChangeListener(
                e -> {
                    LoadMoniteur();
                }
        );
        spnHeure.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                LoadMoniteur();
            }
        });
        cboTemps.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                LoadMoniteur();
            }
        });

        cboTemps.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {

                SpinnerModel value = new SpinnerNumberModel(8, 8, 10, 1);
                if(cboTemps.getSelectedItem()=="Matin") {
                    value = new SpinnerNumberModel(8, 8, 10, 1);
                }
                if(cboTemps.getSelectedItem()=="Après-Midi") {
                    value = new SpinnerNumberModel(14, 14, 18, 1);
                }
                spnHeure.setModel(value);
                JFormattedTextField editortf = ((JSpinner.DefaultEditor) spnHeure.getEditor()).getTextField();
                editortf.setEditable(false);
            }
        });

        cboCategorie.setModel(new DefaultComboBoxModel(ctrlCategorie.getAllCategorieLibelle().toArray(new String[0])));
        LoadVehicule();

        cboCategorie.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                LoadVehicule();
            }
        });



        btnValider.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (cldDate.getDate() == null) {

                    JOptionPane.showMessageDialog(null, "Veuillez rentrer une date");

                } else if (cboMoniteur.getSelectedItem() == null) {

                    JOptionPane.showMessageDialog(null, "Aucun moniteur n'a été selectionné ! Veuillez en sélectionner un.");

                } else {

                    LoadDatas(idUser);

                    if (ctrlEleve.ADejaUneLecon(idEleve, dateformat, spnHeure.getValue().toString())) {
                        JOptionPane.showMessageDialog(null, "Vous avez déjà une leçon à cette date ou heure");
                    } else if (ctrlVehicule.ADejaUneLecon(idVehicule, dateformat, spnHeure.getValue().toString())) {
                        JOptionPane.showMessageDialog(null, "Ce véhicule a déjà une leçon à cette date ou heure");
                    } else {
                        ctrlLecon.addLecon(dateformat, spnHeure.getValue().toString(), idMoniteur, idEleve, idVehicule);
                        JOptionPane.showMessageDialog(null, "La leçon a été ajoutée avec succès");
                        LoadMoniteur();
                    }
                }
            }
        });



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
    private void LoadDatas(Integer idUser){
        idVehicule = ctrlVehicule.getIdVehiculeByImmatriculation(cboVehicule.getSelectedItem().toString());

        String str = cboMoniteur.getSelectedItem().toString();
        String[] arrOfStr = str.split(" ", 2);

        idMoniteur = ctrlMoniteur.getIdMoniteurByName(arrOfStr[0].toString(), arrOfStr[1].toString());
        idEleve = ctrlEleve.getEleveIdByIdUser(idUser);

        dateformat = new SimpleDateFormat("yyyy/MM/dd").format(cldDate.getDate());

        System.out.println("Date : " + dateformat);
        System.out.println("Heure : " + spnHeure.getValue().toString());
        System.out.println("Moniteur id : " + idMoniteur);
        System.out.println("Vehicule id : " + idVehicule);
        System.out.println("User id : " + idEleve);
        System.out.println("---------");
    }
    private void LoadVehicule(){
        cboVehicule.setModel(new DefaultComboBoxModel<>(ctrlVehicule.getImmatriculationByCat(cboCategorie.getSelectedItem().toString()).toArray(new String[0])));
    }
    private void LoadMoniteur(){
        if (cldDate.getDate() != null){
            String date = new SimpleDateFormat("yyyy-MM-dd").format(cldDate.getDate());
            cboMoniteur.setModel(new DefaultComboBoxModel(ctrlLecon.getMoniteurDispo(date, spnHeure.getValue().toString()).toArray(new String[0])));
        }
    }
}
