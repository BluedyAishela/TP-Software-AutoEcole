package Vues.Eleve;

import Vues.FrmConnexion;
import Vues.Moniteur.FrmLicenceMoniteur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

public class FrmAccueil extends JFrame {

    private JPanel pnlRoot;
    private JButton btnCompte;
    private JButton btnPlanning;
    private JButton btnLecon;
    private JButton btnStats;
    private JButton btnDeconnexion;
    private JButton btnInscription;
    private JLabel lblImage;
    private JLabel lblTitre;
    private JToolBar jtbEleve;

//    JFrame frame = new JFrame();
    public FrmAccueil(Integer idEleve) {

//        JToolBar -> Non possibilité de déplacer l'élement
        jtbEleve.setFloatable(false);

//        Diverses informations concernant la fenêtre
        this.setTitle("Page d'Accueil- AutoEcole");
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

        // System.out.println("L'élève ayant pour ID : " + idEleve + " s'est connecté avec succès !");

        btnCompte.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                try {
                    FrmCompte frame = new FrmCompte(idEleve);
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
                    FrmPlanning frame = new FrmPlanning(idEleve);
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
                    frame = new FrmLecon(idEleve);
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
                    FrmStats frame = new FrmStats(idEleve);
                    frame.setVisible(true);
                    dispose();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        btnInscription.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                try {
                    FrmLecon frame = new FrmLecon(idEleve);
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
}
