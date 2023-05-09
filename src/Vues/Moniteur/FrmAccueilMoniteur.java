package Vues.Moniteur;

import Vues.FrmConnexion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

public class FrmAccueilMoniteur extends JFrame {
    private JPanel pnlRoot;
    private JPanel pnlTxt;
    private JButton btnCompte;
    private JButton btnPlanning;
    private JButton btnLicence;
    private JButton btnStats;
    private JButton btnDeconnexion;
    private JLabel lblImage;
    private JLabel lblTitre;
    private JLabel lblImg;
    private JToolBar jtbMoniteur;

    public FrmAccueilMoniteur(Integer idUser) {

        jtbMoniteur.setFloatable(false);

//        Diverses informations concernant la fenêtre
        this.setTitle("Page d'Accueil - AutoEcole");
        this.setContentPane(pnlRoot);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.pack();
        this.setResizable(false);
        this.setLocationRelativeTo(null);
//
        ImageIcon imageIcon = new ImageIcon(
                new ImageIcon(getClass().getResource("../../Images/moniteur-eleve.jpg"))
                        .getImage().getScaledInstance(500, 300, Image.SCALE_DEFAULT));
        lblImage.setIcon(imageIcon);

        // System.out.println("Le moniteur ayant pour ID : " + idUser + " s'est connecté avec succès !");

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
}
