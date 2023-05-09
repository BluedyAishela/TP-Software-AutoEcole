package Tools;

import Entities.*;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class ModelJTable extends AbstractTableModel {
    private String[] colonnes;
    private Object[][] lignes;

    @Override
    public String getColumnName(int column) {
        return colonnes[column];
    }

    @Override
    public int getRowCount() {
        return lignes.length;
    }

    @Override
    public int getColumnCount() {
        return colonnes.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return lignes[rowIndex][columnIndex];
    }

    @Override
    public void setValueAt(Object value, int row, int column) {
        lignes[row][column] = value;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }



    public  void loadDataUser(ArrayList<User> lesUsers){
        colonnes = new String[]{"ID", "Login", "Nom", "Prénom", "Rôle", "Sexe", "Date de Naissance","Adresse", "Code Postal", "Ville", "Télephone"};

        lignes = new Object[lesUsers.size()][getColumnCount()];

        int i = 0;
        for (User user : lesUsers) {
            Integer sexe = user.getSexe();
            String sexeString = null;
            if (sexe == 0){
                sexeString = "Homme";
            }else if (sexe == 1){
                sexeString = "Femme";
            }else if (sexe == 2){
                sexeString = "Autre";
            }else if (sexe == 3){
                sexeString = "Je ne souhaite pas répondre";
            }
            lignes[i][0] = user.getId();
            lignes[i][1] = user.getLogin();
            lignes[i][2] = user.getNom();
            lignes[i][3] = user.getPrenom();
            lignes[i][4] = user.getRole();
            lignes[i][5] = sexeString;
            lignes[i][6] = user.getDatedenaissance();
            lignes[i][7] = user.getAdresse();
            lignes[i][8] = user.getCodepostal();
            lignes[i][9] = user.getVille();
            lignes[i][10] = user.getTelephone();
            i++;
        }
        fireTableChanged(null);
    }
    public void loadDataLeconEle(ArrayList<Lecon> lesLecons) {
        colonnes = new String[]{"Numéro","Moniteur", "Véhicule", "Heure", "Catégorie", "Prix","Reglée"};
        lignes = new Object[lesLecons.size()][getColumnCount()];
        int i = 0;
        for (Lecon lecon : lesLecons) {
            lignes[i][0] = lecon.getIdLecon();
            lignes[i][1] = lecon.getUser().getNom() + ' ' + lecon.getUser().getPrenom();
            lignes[i][2] = lecon.getImmatriculation();
            lignes[i][3] = lecon.getHeureLecon();
            lignes[i][4] = lecon.getCategorie().getLibelleCategorie();
            lignes[i][5] = lecon.getCategorie().getPrixCategorie();
            lignes[i][6] = lecon.getPayer();
            i++;
        }
        fireTableChanged(null);
    }

    public void loadDataLeconMoni(ArrayList<Lecon> lesLecons) {
        colonnes = new String[]{"Numéro","Elève", "Véhicule", "Heure", "Catégorie", "Prix","Reglée"};
        lignes = new Object[lesLecons.size()][getColumnCount()];
        int i = 0;
        for (Lecon lecon : lesLecons) {
            lignes[i][0] = lecon.getIdLecon();
            lignes[i][1] = lecon.getUser().getNom() + ' ' + lecon.getUser().getPrenom();
            lignes[i][2] = lecon.getImmatriculation();
            lignes[i][3] = lecon.getHeureLecon();
            lignes[i][4] = lecon.getCategorie().getLibelleCategorie();
            lignes[i][5] = lecon.getCategorie().getPrixCategorie();
            lignes[i][6] = lecon.getPayer();
            i++;
        }
        fireTableChanged(null);
    }
    public void loadDataDates(ArrayList<Lecon> lesLecons){
        colonnes = new String[]{"Les dates"};
        lignes = new Object[lesLecons.size()][getColumnCount()];
        int i = 0;
        for (Lecon lecon : lesLecons) {
            lignes[i][0] = lecon.getDateLecon();
            i++;
        }
        fireTableChanged(null);
    }
    public void loadLicense(ArrayList<Categorie> lesCategories){
        colonnes = new String[]{"Licence","Date Obtention"};
        lignes = new Object[lesCategories.size()][getColumnCount()];
        int i = 0;
        for (Categorie categorie : lesCategories) {
            lignes[i][0] = categorie.getLibelleCategorie();
            lignes[i][1] = categorie.getLicence().getDateobtentionLicence();
            i++;
        }
        fireTableChanged(null);
    }

    public void loadCategorie(ArrayList<Categorie> lesCategories){
        colonnes = new String[]{"id","Catégories", "Prix"};
        lignes = new Object[lesCategories.size()][getColumnCount()];
        int i = 0;
        for (Categorie categ : lesCategories) {
            lignes[i][0] = categ.getIdCategorie();
            lignes[i][1] = categ.getLibelleCategorie();
            lignes[i][2] = categ.getPrixCategorie();
            i++;
        }
        fireTableChanged(null);
    }

    public void loadVehicule(ArrayList<Vehicule> lesVehicules){
        colonnes = new String[]{"ID","Immatriculation","Marque","Modèle","Année","Catégorie"};
        lignes = new Object[lesVehicules.size()][getColumnCount()];
        int i = 0;
        for (Vehicule vehi : lesVehicules){
            lignes[i][0] = vehi.getIdVehicule();
            lignes[i][1] = vehi.getImmatriculationVehicule();
            lignes[i][2] = vehi.getMarqueeVehicule();
            lignes[i][3] = vehi.getModeleVehicule();
            lignes[i][4] = vehi.getAnneeVehicule();
            lignes[i][5] = vehi.getCategorie().getLibelleCategorie();
            i++;
        }
        fireTableChanged(null);
    }
    public void loadUser(ArrayList<User> lesUser){
        colonnes = new String[]{"ID","Login", "Nom"};
        lignes = new Object[lesUser.size()][getColumnCount()];
        int i = 0;
        for (User user : lesUser) {
            lignes[i][0] = user.getId();
            lignes[i][1] = user.getLogin();
            lignes[i][2] = user.getNom() + " " + user.getPrenom();
            i++;
        }
        fireTableChanged(null);
    }
}
