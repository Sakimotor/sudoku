
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;

/**
 * @version 1.0
 * @author Jean-Philipp MAMBILA TETE et Maxence CRAMAREGEAS
 */

/**
 * Classe de Menu, elle permet à l'utilisateur de choisir
 * <b>d'éditer</b> ou de <b>remplir</b> une grille <b></b>.
 */

public class Menu {

    private JFrame fenetre= new JFrame("Sudoku");
    private Image logo;
    private int choix;

    public Menu() {
        this.logo = Toolkit.getDefaultToolkit().getImage("sudokuk.jpg");
        this.fenetre.setIconImage(logo);
        this.fenetre.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.fenetre.setResizable(false);
        this.fenetre.setUndecorated(true);
        this.fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.fenetre.pack();
        this.fenetre.setVisible(false);
    }

    /**
     * Méthode qui <b>affiche</b> le Menu et permet à l'utilisateur de choisir entre
     * {@link Fenetrejeu} et
     * {@link FenetreEditeur}
     */

    public void afficherMenu(){
        String[] select = {"Éditer","Compléter"};
        this.choix = JOptionPane.showOptionDialog(fenetre,"Voulez-vous éditer ou compléter une grille ?",
                "Menu",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,new ImageIcon(logo),
                select,null);
        if (this.choix == 0) {
            FenetreEditeur edit = new FenetreEditeur();
            edit.Afficher();
        }else if (this.choix == 1){
            Fenetrejeu jeu = new Fenetrejeu();
            jeu.Afficher();
        }else {
            this.fenetre.dispatchEvent(new WindowEvent(fenetre, WindowEvent.WINDOW_CLOSING));
        }
    }
}