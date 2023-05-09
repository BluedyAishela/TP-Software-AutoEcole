package Vues.Moniteur;

import Controllers.CtrlLecon;
import Tools.ConnexionBDD;
import Tools.ModelJTable;
import Vues.FrmConnexion;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class FrmPlanningMoniteur extends JFrame {

    private JPanel pnlRoot;
    private JToolBar jtbMoniteur;
    private JButton btnCompte;
    private JButton btnPlanning;
    private JButton btnLicence;
    private JButton btnStats;
    private JButton btnDeconnexion;
    private JTree trDate;
    private JLabel lblRecherche;
    private JTable tblInfo;
    private JPanel pnlDate;
    private JButton btnPayer;
    private JButton btnNonPayer;
    private JLabel lblLecon;
    private JTable tblDates;
    private JLabel lblDate1;
    private JPanel pnlDate1;
    private JLabel lblDate2;
    private JPanel pnlDate2;
    private JButton btnRecherche;
    private JButton btnReset;
    private JDateChooser cldDate1;
    private JDateChooser cldDate2;
    private ModelJTable modelJTable;
    private DefaultMutableTreeNode root;
    private DefaultMutableTreeNode noeudDate;
    private DefaultMutableTreeNode noeudHeure;
    private DefaultTreeModel model;
    private JDateChooser cldDate;
    private CtrlLecon ctrlLecon;

    public FrmPlanningMoniteur(Integer idUser) throws SQLException, ClassNotFoundException {

        ConnexionBDD cnx = new ConnexionBDD();
        //        JToolBar -> Non possibilité de déplacer l'élement
        jtbMoniteur.setFloatable(false);

//        Diverses informations concernant la fenêtre
        this.setTitle("Mon Planning - AutoEcole");
        this.setContentPane(pnlRoot);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.pack();
        this.setBounds(100, 200, 800, 600);
        this.setResizable(true);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        ctrlLecon = new CtrlLecon();

        InitJCalendar();

        modelJTable = new ModelJTable();
        modelJTable.loadDataDates(ctrlLecon.getDateLeconByMoniteurId(idUser));
        tblDates.setModel(modelJTable);
        tblDates.setAutoCreateRowSorter(true);

        tblDates.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                InfoLecon(idUser);
            }
        });

        btnRecherche.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                if (cldDate1.getDate() == null){
                    JOptionPane.showMessageDialog(null,"Veuillez sélectionner la première date");
                } else if (cldDate2.getDate() == null) {
                    JOptionPane.showMessageDialog(null,"Veuillez sélectionner la seconde date");
                }else {
                    String dateformat1 = new SimpleDateFormat("yyyy/MM/dd").format(cldDate1.getDate());
                    String dateformat2 = new SimpleDateFormat("yyyy/MM/dd").format(cldDate2.getDate());

                    modelJTable = new ModelJTable();
                    modelJTable.loadDataDates(ctrlLecon.getLeconMoniteurBetweenDate(idUser, dateformat1, dateformat2));
                    tblDates.setModel(modelJTable);
                    if (tblDates.getRowCount() == 0){
                        JOptionPane.showMessageDialog(null, "Aucune leçon a été trouver durant la période choisis");
                        modelJTable.loadDataDates(ctrlLecon.getDateLeconByMoniteurId(idUser));
                        tblDates.setModel(modelJTable);
                    }
                    ResetCalendar();

                }
                ResetInfo();
            }
        });

        btnReset.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                modelJTable = new ModelJTable();
                modelJTable.loadDataDates(ctrlLecon.getDateLeconByMoniteurId(idUser));
                tblDates.setModel(modelJTable);
                ResetInfo();
                ResetCalendar();
            }
        });


        btnPayer.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (tblInfo.getSelectedRowCount() == 0) {

                    JOptionPane.showMessageDialog(null,"Veuillez saisir une leçon");
                }else {
                    ctrlLecon.PayerLecon(1, (Integer) tblInfo.getValueAt(tblInfo.getSelectedRow(), 0));
                    InfoLecon(idUser);
                }

            }
        });

        btnNonPayer.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (tblInfo.getSelectedRowCount() == 0) {

                    JOptionPane.showMessageDialog(null,"Veuillez saisir une leçon");
                }else {
                    ctrlLecon.PayerLecon(0, (Integer) tblInfo.getValueAt(tblInfo.getSelectedRow(), 0));
                    InfoLecon(idUser);
                }
            }
        });


        btnCompte.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                try {
                    FrmCompteMoniteur frame = new FrmCompteMoniteur(idUser);
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
                    FrmPlanningMoniteur frame = new FrmPlanningMoniteur(idUser);
                    frame.setVisible(true);
                    dispose();
                } catch (SQLException | ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        btnLicence.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                try {
                    FrmLicenceMoniteur frame = new FrmLicenceMoniteur(idUser);
                    frame.setVisible(true);
                    dispose();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        btnStats.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                try {
                    FrmStatsMoniteur frame = new FrmStatsMoniteur(idUser);
                    frame.setVisible(true);
                    dispose();
                } catch (SQLException | ClassNotFoundException ex) {
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
    private void InitJCalendar(){
        cldDate1 = new JDateChooser();
        cldDate1.setDateFormatString("yyyy-MM-dd");
        pnlDate1.add(cldDate1);
        JTextFieldDateEditor editor1 = (JTextFieldDateEditor) cldDate1.getDateEditor();
        editor1.setEditable(false);


        cldDate2 = new JDateChooser();
        cldDate2.setDateFormatString("yyyy-MM-dd");
        pnlDate2.add(cldDate2);
        JTextFieldDateEditor editor2 = (JTextFieldDateEditor) cldDate2.getDateEditor();
        editor2.setEditable(false);
        cldDate2.setEnabled(false);

        cldDate1.getDateEditor().addPropertyChangeListener(
                e -> {
                    cldDate2.setEnabled(true);
                    cldDate2.setMinSelectableDate(cldDate1.getDate());
                    cldDate2.setDate(cldDate1.getDate());
                }
        );

    }

    private void InfoLecon(int idUser){
        modelJTable = new ModelJTable();
        modelJTable.loadDataLeconMoni(ctrlLecon.getLeconByMoniIdAndDate(idUser, tblDates.getValueAt(tblDates.getSelectedRow(),0).toString()));
        tblInfo.setModel(modelJTable);
        tblInfo.setAutoCreateRowSorter(true);

    }
    private void ResetCalendar(){
        cldDate1.setCalendar(null);
        cldDate2.setCalendar(null);
        cldDate2.setEnabled(false);
    }
    private void ResetInfo(){
        tblInfo.setModel(new DefaultTableModel());
        tblInfo.setAutoCreateRowSorter(true);
    }
}
