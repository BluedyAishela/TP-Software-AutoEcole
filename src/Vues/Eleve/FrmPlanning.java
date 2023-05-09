package Vues.Eleve;

import Controllers.CtrlLecon;
import Tools.ConnexionBDD;
import Tools.ModelJTable;
import Vues.FrmConnexion;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class FrmPlanning extends JFrame{
    private JPanel pnlRoot;
    private JToolBar jtbEleve;
    private JButton btnCompte;
    private JButton btnPlanning;
    private JButton btnLecon;
    private JButton btnStats;
    private JButton btnDeconnexion;
    private JLabel lblRecherche;
    private JLabel lblLecon;
    private JTable tblInfo;
    private JPanel pnlDate1;
    private JTable tblDates;
    private JButton btnRecherche;
    private JPanel pnlDate2;
    private JLabel lblDate1;
    private JLabel lblDate2;
    private JButton btnReset;
    private ModelJTable modelJTable;
    private CtrlLecon ctrlLecon;
    private JDateChooser cldDate1;
    private JDateChooser cldDate2;

    public FrmPlanning(int idUser) throws SQLException, ClassNotFoundException {

        ConnexionBDD cnx = new ConnexionBDD();


        //        JToolBar -> Non possibilité de déplacer l'élement
        jtbEleve.setFloatable(false);

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
        modelJTable.loadDataDates(ctrlLecon.getDateLeconByEleveId(idUser));
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
                    modelJTable.loadDataDates(ctrlLecon.getLeconEleveBetweenDate(idUser, dateformat1, dateformat2));
                    tblDates.setModel(modelJTable);
                    if (tblDates.getRowCount() == 0){
                        JOptionPane.showMessageDialog(null, "Aucune leçon a été trouvée durant la période choisie");
                        modelJTable.loadDataDates(ctrlLecon.getDateLeconByEleveId(idUser));
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
                modelJTable.loadDataDates(ctrlLecon.getDateLeconByEleveId(idUser));
                tblDates.setModel(modelJTable);
                ResetInfo();
                ResetCalendar();
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
        modelJTable.loadDataLeconEle(ctrlLecon.getLeconByEleveIdAndDate(idUser, tblDates.getValueAt(tblDates.getSelectedRow(),0).toString()));
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
