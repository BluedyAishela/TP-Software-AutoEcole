package Vues.Admin.Statistiques;

import Controllers.CtrlGraphique;
import Vues.Admin.Categorie.FrmCategorieResponsable;
import Vues.Admin.FrmCompteResponsable;
import Vues.Admin.FrmPlanningResponsable;
import Vues.Admin.Utilisateurs.FrmUtilisateursResponsable;
import Vues.Admin.Vehicule.FrmVehiculeResponsable;
import Vues.FrmConnexion;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class FrmStatistiquesResponsable extends JFrame {
    private JToolBar jtbMoniteur;
    private JButton btnCompte;
    private JButton btnUtilisateurs;
    private JButton btnVehicule;
    private JButton btnCategorie;
    private JButton btnStatistiques;
    private JButton btnDeconnexion;
    private JPanel pnlRoot;
    private JButton btnPlanning;
    private JLabel lblStatistiques;
    private JPanel pnlDate1;
    private JPanel pnlDate2;
    private JLabel lnlDate1;
    private JLabel lblDate2;
    private JButton btnRecherche;
    private JButton btnReset;
    private JPanel pnlGraphVehicule;
    private JPanel pnlGraphChiffeAffaire;
    private JPanel pnlMoniteur;
    private JPanel pnlGraphCategorie;
    private JDateChooser cldDate1;
    private JDateChooser cldDate2;
    private CtrlGraphique ctrlGraphique;

    public FrmStatistiquesResponsable(int idUser){
        this.setTitle("Statistiques - AutoEcole");
        this.setContentPane(pnlRoot);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.pack();
        this.setSize(900,1000);
        this.setResizable(true);
        this.setLocationRelativeTo(null);


        InitJCalendar();

        Graph1Vehicule(null, null);
        Graph2ChiffeAffaire(null,null);
        Graph3MoniteurSolicite(null,null);
        Graph4Categorie(null,null);



        btnReset.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                ResetCalendar();
                Graph1Vehicule(null,null);
                Graph2ChiffeAffaire(null,null);
                Graph3MoniteurSolicite(null,null);
                Graph4Categorie(null,null);
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
                    Graph1Vehicule(dateformat1, dateformat2);
                    Graph2ChiffeAffaire(dateformat1, dateformat2);
                    Graph3MoniteurSolicite(dateformat1, dateformat2);
                    Graph4Categorie(dateformat1, dateformat2);
                }
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
        btnPlanning.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                FrmPlanningResponsable frame = new FrmPlanningResponsable(idUser);
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



    private void Graph1Vehicule(String date1, String date2){
        pnlGraphVehicule.removeAll();
        ctrlGraphique = new CtrlGraphique();
        DefaultCategoryDataset donnees = new DefaultCategoryDataset();

        if (date1 == null && date2 == null){


            for (Map.Entry valeur : ctrlGraphique.getDatasGraphiqueVehicule(null, null).entrySet())
            {
                donnees.setValue(Integer.parseInt(valeur.getValue().toString()), "",valeur.getKey().toString());
            }
        } else if (date1 != null && date2 != null) {

            for (Map.Entry valeur : ctrlGraphique.getDatasGraphiqueVehicule(date1, date2).entrySet())
            {
                donnees.setValue(Integer.parseInt(valeur.getValue().toString()), "",valeur.getKey().toString());
            }
        }

        JFreeChart chart1 = ChartFactory.createBarChart
                (
                        "Véhicules les plus utilisé(s)",
                        "Immatriculation" ,
                        "Leçons passées",
                        donnees,
                        PlotOrientation.VERTICAL,
                        false,
                        true,
                        false
                );
        ChartPanel graph = new ChartPanel(chart1);
        CategoryPlot plot = (CategoryPlot)chart1.getPlot();
        plot.getDomainAxis().setCategoryLabelPositions(CategoryLabelPositions.UP_45);

        pnlGraphVehicule.add(graph);
        pnlGraphVehicule.validate();
    }

    private void Graph2ChiffeAffaire(String date1, String date2){
        pnlGraphChiffeAffaire.removeAll();
        ctrlGraphique = new CtrlGraphique();
        DefaultCategoryDataset donnees = new DefaultCategoryDataset();

        if (date1 == null && date2 == null){

            for (Map.Entry valeur : ctrlGraphique.getDataChiffreAffaire(null, null).entrySet())
            {
                donnees.setValue(Double.parseDouble(valeur.getValue().toString()), "",valeur.getKey().toString());
            }
        } else if (date1 != null && date2 != null) {

            for (Map.Entry valeur : ctrlGraphique.getDataChiffreAffaire(date1, date2).entrySet())
            {
                donnees.setValue(Double.parseDouble(valeur.getValue().toString()), "",valeur.getKey().toString());
            }
        }

        JFreeChart chart = ChartFactory.createBarChart
                (
                        "Chiffre d'affaire",
                        "Date" ,
                        "Chiffre d'affaire (€)",
                        donnees,
                        PlotOrientation.VERTICAL,
                        false,
                        true,
                        false
                );
        ChartPanel graph = new ChartPanel(chart);
        CategoryPlot plot = (CategoryPlot)chart.getPlot();
        plot.getDomainAxis().setCategoryLabelPositions(CategoryLabelPositions.UP_45);

        pnlGraphChiffeAffaire.add(graph);
        pnlGraphChiffeAffaire.validate();
    }
    private void Graph3MoniteurSolicite(String date1, String date2){
        pnlMoniteur.removeAll();
        ctrlGraphique = new CtrlGraphique();
        DefaultCategoryDataset donnees = new DefaultCategoryDataset();

        if (date1 == null && date2 == null){

            for (Map.Entry valeur : ctrlGraphique.getDataMoniteurSolicite(null, null).entrySet())
            {
                donnees.setValue(Integer.parseInt(valeur.getValue().toString()), "",valeur.getKey().toString());
            }
        } else if (date1 != null && date2 != null) {

            for (Map.Entry valeur : ctrlGraphique.getDataMoniteurSolicite(date1, date2).entrySet())
            {
                donnees.setValue(Integer.parseInt(valeur.getValue().toString()), "",valeur.getKey().toString());
            }
        }

        JFreeChart chart = ChartFactory.createBarChart
                (
                        "Moniteurs solicités",
                        "Moniteurs" ,
                        "Nombres de leçons",
                        donnees,
                        PlotOrientation.VERTICAL,
                        false,
                        true,
                        false
                );
        ChartPanel graph = new ChartPanel(chart);
        CategoryPlot plot = (CategoryPlot)chart.getPlot();
        plot.getDomainAxis().setCategoryLabelPositions(CategoryLabelPositions.UP_45);

        pnlMoniteur.add(graph);
        pnlMoniteur.validate();
    }
    private void Graph4Categorie(String date1, String date2){
        pnlGraphCategorie.removeAll();
        ctrlGraphique = new CtrlGraphique();
        DefaultCategoryDataset donnees = new DefaultCategoryDataset();

        if (date1 == null && date2 == null){

            for (Map.Entry valeur : ctrlGraphique.getDataCategorie(null, null).entrySet())
            {
                donnees.setValue(Integer.parseInt(valeur.getValue().toString()), "",valeur.getKey().toString());
            }
        } else if (date1 != null && date2 != null) {

            for (Map.Entry valeur : ctrlGraphique.getDataCategorie(date1, date2).entrySet())
            {
                donnees.setValue(Integer.parseInt(valeur.getValue().toString()), "",valeur.getKey().toString());
            }
        }

        JFreeChart chart = ChartFactory.createBarChart
                (
                        "Catégorie",
                        "Catégories" ,
                        "Nombres de leçons",
                        donnees,
                        PlotOrientation.VERTICAL,
                        false,
                        true,
                        false
                );
        ChartPanel graph = new ChartPanel(chart);
        CategoryPlot plot = (CategoryPlot)chart.getPlot();
        plot.getDomainAxis().setCategoryLabelPositions(CategoryLabelPositions.UP_45);

        pnlGraphCategorie.add(graph);
        pnlGraphCategorie.validate();
    }
}
