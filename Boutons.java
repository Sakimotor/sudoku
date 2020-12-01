

import javax.swing.*;
import java.awt.event.*;
import javax.swing.filechooser.FileNameExtensionFilter;

/** 
* @version 1.0
* @author Jean-Philipp MAMBILA TETE et Maxence CRAMAREGEAS 
*/
		/**
	 * Comportement du jeu selon les <b>boutons</b> de la fenêtre
     * appuyés par l'utilisateur.
	 */
public class Boutons implements ActionListener {
    private JFrame fenetre;
	private Affichage dessin;
    	/**
	 * Constructeur qui récupère les détails sur la <b>fenêtre</b>
	 * que l'on veut analyser.
	 * @param f la <b>vue</b> sur laquelle on agit
	 * @param d le <b>dessin</b> que l'on veut obtenir (si un bouton appelle une foncion de dessin)
	 */
public Boutons( JFrame f, Affichage d) {
    this.fenetre = f;
    this.dessin = d;
}
		/**
		 * Méthode qui effectue une action si l'utilisateur <b>appuie </b>sur un bouton.

	 */
@Override
public void actionPerformed(ActionEvent event) {
	String nom = event.getActionCommand();
    //bouton de fermeture
	if (nom.equals("X")) {
		this.fenetre.dispose();
		this.fenetre.dispatchEvent(new WindowEvent(fenetre, WindowEvent.WINDOW_CLOSING));
}   //boutons de chargement
    else if (nom.equals("Charger")) {
		Lecture reading = new Lecture(this.fenetre);
		chargerFichier(reading, this.dessin);
    }
    else if (nom.equals("Sauvegarder")) {
		sauvegarderFichier();
    }
    else if (nom.equals("Menu")) {
		this.fenetre.dispose();
		Menu m = new Menu();
		m.afficherMenu();
	}

}//récupère les actions des boutons

public void chargerFichier(Lecture r, Affichage d) {
	JFileChooser chooser = new JFileChooser();

	FileNameExtensionFilter filter = new FileNameExtensionFilter(
		"Grilles de jeu", "gri");
	chooser.setFileFilter(filter);
	int returnVal = chooser.showOpenDialog(null);
	if(returnVal == JFileChooser.APPROVE_OPTION) {
	String grille = r.Lire(chooser.getSelectedFile().getPath());
	d.setGrille(grille);
	}
}

public void sauvegarderFichier() {
	JFileChooser chooser = new JFileChooser();
	FileNameExtensionFilter filter = new FileNameExtensionFilter(
		"Grilles de jeu", "gri");
	chooser.setFileFilter(filter);
	int returnVal = chooser.showSaveDialog(null);
	if(returnVal == JFileChooser.APPROVE_OPTION) {

		try{
			Ecriture writing = new Ecriture(dessin.getGrille(),chooser.getSelectedFile().getPath() + ".gri");
			writing.Ecrire();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
}