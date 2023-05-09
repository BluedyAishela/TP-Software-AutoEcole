package Vues.Moniteur;

import Controllers.CtrlGraphique;
import Tools.ConnexionBDD;
import Vues.FrmConnexion;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.RingPlot;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class FrmStatsMoniteur extends JFrame{


    private JToolBar jtbMoniteur;
    private JButton btnCompte;
    private JButton btnPlanning;
    private JButton btnLicence;
    private JButton btnStats;
    private JButton btnDeconnexion;
    private JPanel pnlGraph1;
    private JPanel pnlGraph2;
    private JPanel pnlRoot;
    private JPanel pnlDate1;
    private JPanel pnlDate2;
    private JLabel lblDate1;
    private JLabel lblDate2;
    private JButton btnReset;
    private JButton btnRecherche;
    private JLabel lblStat;
    private CtrlGraphique ctrlGraphique;
    private JDateChooser cldDate1;
    private JDateChooser cldDate2;

    public FrmStatsMoniteur(Integer idUser) throws SQLException, ClassNotFoundException {

        jtbMoniteur.setFloatable(false);

//        Diverses informations concernant la fenêtre
        this.setTitle("Page de statistique - AutoEcole");
        this.setContentPane(pnlRoot);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.pack();
        this.setSize(900,500);
        this.setResizable(true);
        this.setLocationRelativeTo(null);

        ConnexionBDD cnx = new ConnexionBDD();

        Graph1LeconPasse(idUser, null, null);
        Graph2ChiffreAffaire(idUser, null, null);
        InitJCalendar();



        btnRecherche.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (cldDate1.getDate() == null){
                    JOptionPane.showMessageDialog(null,"Veuillez sélectionner la premiere date");
                } else if (cldDate2.getDate() == null) {
                    JOptionPane.showMessageDialog(null,"Veuillez sélectionner la seconde date");
                }else {
                    String dateformat1 = new SimpleDateFormat("yyyy/MM/dd").format(cldDate1.getDate());
                    String dateformat2 = new SimpleDateFormat("yyyy/MM/dd").format(cldDate2.getDate());
                    Graph1LeconPasse(idUser, dateformat1, dateformat2);
                    Graph2ChiffreAffaire(idUser, dateformat1, dateformat2);
                }
            }
        });
        btnReset.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Graph1LeconPasse(idUser, null, null);
                Graph2ChiffreAffaire(idUser, null, null);
                ResetCalendar();
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
    private void Graph1LeconPasse(int idUser, String date1, String date2){
        pnlGraph1.removeAll();
        ctrlGraphique = new CtrlGraphique();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();

        DefaultPieDataset donnees = new DefaultPieDataset();
        RingPlot plot = new RingPlot(donnees);
        if (date1 == null && date2 == null){
            for (Map.Entry value : ctrlGraphique.getGraphMoniLeconPasse(idUser, dtf.format(now),null, null).entrySet()) {
                donnees.setValue(value.getKey().toString(), Double.parseDouble(value.getValue().toString()));
            }
        } else if (date1 != null && date2 != null){
            for (Map.Entry value : ctrlGraphique.getGraphMoniLeconPasse(idUser, null,date1, date2).entrySet()) {
                donnees.setValue(value.getKey().toString(), Double.parseDouble(value.getValue().toString()));
            }
        }

        plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{1}"));

        JFreeChart chart = new JFreeChart("Stats leçon", JFreeChart.DEFAULT_TITLE_FONT, plot, true);
        ChartPanel pnl1 = new ChartPanel(chart);
        pnl1.setMouseWheelEnabled(true);

        pnlGraph1.add(pnl1);
        pnlGraph1.validate();

    }

    private void Graph2ChiffreAffaire(int idUser, String date1, String date2){
        pnlGraph2.removeAll();
        ctrlGraphique = new CtrlGraphique();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();

        DefaultCategoryDataset donnees = new DefaultCategoryDataset();
        if (date1 == null && date2 == null){
            for (Map.Entry valeur : ctrlGraphique.getGraphMoniChiffreAffaire(idUser, dtf.format(now), null, null).entrySet())
            {
                donnees.setValue(Double.parseDouble(valeur.getValue().toString()), "",valeur.getKey().toString());
            }
        } else if (date1 != null && date2 != null){
            for (Map.Entry valeur : ctrlGraphique.getGraphMoniChiffreAffaire(idUser, null, date1, date2).entrySet())
            {
                donnees.setValue(Double.parseDouble(valeur.getValue().toString()), "",valeur.getKey().toString());
            }
        }
        JFreeChart chart1 = ChartFactory.createBarChart
                (
                        "Chiffre d'affaire",
                        "Date",
                        "Chiffre d'affaire",
                        donnees,
                        PlotOrientation.VERTICAL,
                        false,
                        true,
                        false
                );
        ChartPanel graph = new ChartPanel(chart1);
        CategoryPlot plot = (CategoryPlot)chart1.getPlot();
        plot.getDomainAxis().setCategoryLabelPositions(CategoryLabelPositions.UP_45);

        pnlGraph2.add(graph);
        pnlGraph2.validate();
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

    private void ResetCalendar(){
        cldDate1.setCalendar(null);
        cldDate2.setCalendar(null);
        cldDate2.setEnabled(false);
    }

}
