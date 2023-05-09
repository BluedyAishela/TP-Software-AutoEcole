package Vues.Moniteur;

import Controllers.CtrlCategorie;
import Controllers.CtrlLicence;
import Controllers.CtrlMoniteur;
import Tools.ModelJTable;
import Vues.FrmConnexion;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class FrmLicenceMoniteur extends JFrame {

    private JToolBar jtbMoniteur;
    private JButton btnCompte;
    private JButton btnPlanning;
    private JButton btnLicence;
    private JButton btnStats;
    private JButton btnDeconnexion;
    private JButton btnAjouter;
    private JButton btnDelete;
    private JPanel pnlRoot;
    private JLabel lblTitre;
    private JTable tblLicence;
    private JComboBox cboCategorie;
    private JPanel pnlDate;
    private JLabel lblDate;
    private JLabel lblCategorie;

    private JDateChooser cldDate;
    private ModelJTable modelJTable;
    private CtrlCategorie ctrlCategorie;

    public FrmLicenceMoniteur(Integer idUser) throws SQLException {

        jtbMoniteur.setFloatable(false);

//        Diverses informations concernant la fenêtre
        this.setTitle("Licence - AutoEcole");
        this.setContentPane(pnlRoot);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.pack();
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        cldDate = new JDateChooser();
        cldDate.setDateFormatString("yyyy-MM-dd");
        long milisec = System.currentTimeMillis();
        java.sql.Date date = new Date(milisec);
        cldDate.setMaxSelectableDate(date);
        pnlDate.add(cldDate);

        JTextFieldDateEditor editor = (JTextFieldDateEditor) cldDate.getDateEditor();
        editor.setEditable(false);

        ctrlCategorie = new CtrlCategorie();
        CtrlLicence ctrlLicence = new CtrlLicence();
        CtrlMoniteur ctrlMoniteur = new CtrlMoniteur();

        Integer idMoniteur = ctrlMoniteur.getMoniteurIdByIdUser(idUser);

        cboCategorie.setModel(new DefaultComboBoxModel<>(ctrlCategorie.getAllCategorieLibelle().toArray(new String[0])));

        initJtable(idMoniteur);

        btnAjouter.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                String libelleCategorie = cboCategorie.getSelectedItem().toString();
                Integer idCategorie = ctrlCategorie.getIdByLibelle(libelleCategorie);

                if (cldDate.getDate() == null){
                    JOptionPane.showMessageDialog(null, "Veuillez selectionner une date");

                }else {
                   String dateformat = new SimpleDateFormat("yyyy/MM/dd").format(cldDate.getDate());
                    if(ctrlLicence.MoniteurADejaLicence(idMoniteur, libelleCategorie)) {
                        JOptionPane.showMessageDialog(null, "Vous possédez déjà cette licence !");
                    } else {
                        ctrlLicence.addCategorieMoniteur(idMoniteur, idCategorie, dateformat);
                        initJtable(idMoniteur);
                        cldDate.setCalendar(null);
                    }
                }

            }
        });
        btnDelete.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (tblLicence.getSelectedRowCount() == 0){
                    JOptionPane.showMessageDialog(null, "Veuillez selectionner une licence à supprimer");
                } else {
                    System.out.println(ctrlLicence.GetIdCategByLicence(tblLicence.getValueAt(tblLicence.getSelectedRow(), 0).toString()));
                    ctrlLicence.DeleteLicenceByIdMoniteur(idMoniteur, ctrlLicence.GetIdCategByLicence(tblLicence.getValueAt(tblLicence.getSelectedRow(), 0).toString()));
                    initJtable(idMoniteur);
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
    private void initJtable(int idMoniteur){
        modelJTable = new ModelJTable();
        modelJTable.loadLicense(ctrlCategorie.getAllCategoriebyIdMoni(idMoniteur));
        tblLicence.setModel(modelJTable);
        tblLicence.setAutoCreateRowSorter(true);
    }
}
