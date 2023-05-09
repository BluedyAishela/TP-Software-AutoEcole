package Vues.Admin.Categorie;

import Controllers.CtrlCategorie;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class FrmUpdateCategorieResponsable extends JFrame {
    private JTextField txtLibelle;
    private JPanel pnlRoot;
    private JLabel lblLibelle;
    private JTextField txtPrix;
    private JLabel lblPrix;
    private JButton btnModifier;
    private JSpinner spnPrix;
    private CtrlCategorie ctrlCategorie;

    public FrmUpdateCategorieResponsable(int idCategorie, String libelle, String prix, int idUser) {

        this.setTitle("Modifier");
        this.setContentPane(pnlRoot);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.pack();
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        SpinnerNumberModel model = new SpinnerNumberModel(0.0, 0.0, null, 0.5);
        spnPrix.setModel(model);

        txtLibelle.setText(libelle);
        spnPrix.setValue(Double.parseDouble(prix));


        ctrlCategorie = new CtrlCategorie();

        btnModifier.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                if (txtLibelle.getText().isEmpty())
                {
                    JOptionPane.showMessageDialog(null, "Veuillez saisir un libelle");
                } else if (txtLibelle.getText().length()>50) {
                    JOptionPane.showMessageDialog(null,"Veuillez saisir un libelle avec un minimum de 50 caractères");
                } else if (!ctrlCategorie.SearchCategorieByLibelleUpdate(txtLibelle.getText(), idCategorie).isEmpty()){
                    JOptionPane.showMessageDialog(null, "Une catégorie porte déjà ce libelle");
                    txtLibelle.setText(libelle);
                } else {
                    ctrlCategorie.UpdateCategorie(txtLibelle.getText(), Double.parseDouble(spnPrix.getValue().toString()), libelle);
                    JOptionPane.showMessageDialog(null, "Modification réussie !");
                    dispose();
                }
            }
        });
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e);
                FrmCategorieResponsable frame = new FrmCategorieResponsable(idUser);
                frame.setVisible(true);
            }
        });

    }
}
