package Vues.Admin;

import Vues.Admin.Categorie.FrmCategorieResponsable;
import Vues.Admin.Statistiques.FrmStatistiquesResponsable;
import Vues.Admin.Utilisateurs.FrmUtilisateursResponsable;
import Vues.Admin.Vehicule.FrmVehiculeResponsable;
import Vues.FrmConnexion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;


public class FrmAccueilResponsable extends JFrame {
    private JToolBar jtbMoniteur;
    private JButton btnCompte;
    private JButton btnDeconnexion;
    private JButton btnCategorie;
    private JPanel pnlRoot;
    private JButton btnVehicule;
    private JButton btnUtilisateurs;
    private JButton btnStatistiques;
    private JButton btnPlanning;
    private JLabel lblImage;
    private JLabel lblTitre;

    public FrmAccueilResponsable(int idUser) {


        this.setTitle("Page d'Accueil - AutoEcole");
        this.setContentPane(pnlRoot);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.pack();
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        ImageIcon imageIcon = new ImageIcon(
                new ImageIcon(getClass().getResource("../../Images/moniteur-eleve.jpg"))
                        .getImage().getScaledInstance(500, 300, Image.SCALE_DEFAULT));
        lblImage.setIcon(imageIcon);


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
    }
}
