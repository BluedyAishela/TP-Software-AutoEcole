package Vues.Admin.Vehicule;

import Controllers.CtrlCategorie;
import Controllers.CtrlVehicule;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.Year;
import java.util.Objects;

public class FrmUpdateVehiculeResponsable extends JFrame {
    private JPanel pnlRoot;
    private JTextField txtImmatriculation;
    private JTextField txtMarque;
    private JTextField txtModele;
    private JButton btnModifier;
    private JLabel lblMarque;
    private JLabel lblModele;
    private JLabel lblAnnee;
    private JLabel lblCategorie;
    private JLabel lblImmatriculation;
    private JComboBox cboCategorie;
    private JSpinner spnAnnee;
    private CtrlVehicule ctrlVehicule;

    public FrmUpdateVehiculeResponsable(int id, String immatriculation, String marque, String modele, int annee, String categorie, int idUser) {

        this.setTitle("Modifier");
        this.setContentPane(pnlRoot);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.pack();
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        txtImmatriculation.setText(immatriculation);
        txtMarque.setText(marque);
        txtModele.setText(modele);

        spnAnnee.setModel(new SpinnerNumberModel(annee, Year.now().getValue()-50, Year.now().getValue(), 1));
        JFormattedTextField editortf = ((JSpinner.DefaultEditor) spnAnnee.getEditor()).getTextField();
        editortf.setEditable(false);

        setCboCategorie(categorie);

        ctrlVehicule = new CtrlVehicule();
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e);
                FrmVehiculeResponsable frame = new FrmVehiculeResponsable(idUser);
                frame.setVisible(true);
                dispose();
            }
        });

        btnModifier.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (txtImmatriculation.getText().isEmpty() || txtImmatriculation.getText().length() != 9){
                    JOptionPane.showMessageDialog(null,"Saisissez une immatriculation et contenant 9 caractères maximum");
                }else if (txtMarque.getText().isEmpty() || txtMarque.getText().length() >=50) {
                    JOptionPane.showMessageDialog(null,"Saisissez une marque et contenant 50 caractères maximum");
                } else if (txtModele.getText().isEmpty() || txtModele.getText().length() >=50) {
                    JOptionPane.showMessageDialog(null,"Saisissez un modèle et contenant 50 caractères maximum");
                } else if (ctrlVehicule.VerifyImmatriculationUpdate(id,txtImmatriculation.getText())){
                    JOptionPane.showMessageDialog(null,"Un véhicule de la même immatriculation existe déjà, saisissez une nouvelle immatriculation");
                }else {
                    ctrlVehicule.UpdateVehicule(id, txtImmatriculation.getText(), txtMarque.getText(), txtModele.getText(), Integer.parseInt(spnAnnee.getValue().toString()), cboCategorie.getSelectedItem().toString());
                    JOptionPane.showMessageDialog(null, "La modification à été effectuée");
                    dispose();
                }
            }
        });
    }
    private void setCboCategorie(String categorie){
        CtrlCategorie ctrlCategorie = new CtrlCategorie();
        String[] lesCateg = ctrlCategorie.getAllCategorieLibelle().toArray(new String[0]);
        DefaultComboBoxModel<String> defaultComboBoxModel = new DefaultComboBoxModel<String>(lesCateg);
        for (String cat : lesCateg){
            if (Objects.equals(cat, categorie)){
                defaultComboBoxModel.setSelectedItem(cat);
            }
        }
        cboCategorie.setModel(defaultComboBoxModel);

    }
}
