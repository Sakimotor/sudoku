

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.stream.IntStream;


/**
 * @version 1.0
 * @author Jean-Philipp MAMBILA TETE et Maxence CRAMAREGEAS
 */
/**
 * Classe  créée pour <b>détecter et effectuer les actions de l'utilisateur</b> au sein
 * de la grille de jeu.
 */
public class Actions implements MouseListener {
	private int coordX;
	private int coordY;
	private int bordure;
	private int ligne;
	private int colonne;
	private Affichage dessin;
	private JFrame vue;
	private String nombre;
	private Image logo;
	private ImageIcon icone;
	private boolean editeur;
	private int temporaire;
	private int[][] grille = new int[9][9];
	/**
	 * Constructeur qui récupère les détails sur la <b>fenêtre</b>
	 * que l'on veut analyser.
	 * @param v la <b>vue</b> sur laquelle on agit
	 * @param d le <b>dessin</b> que l'on veut obtenir
	 * @param b la <b>bordure</b> du haut de notre vue, contenant le bouton de fermeture
	 */
	public Actions( JFrame v, Affichage d, int b, boolean e) {
		this.bordure = b;
		this.vue = v;
		this.dessin = d;
		this.editeur = e;
		//on fait la mise à l'échelle

		this.logo = Toolkit.getDefaultToolkit().getImage("sudoku.png");
		this.icone = new ImageIcon(this.logo);
	}

	//en cas de clic, on cherche la case concernée et on agit dessus
	/**
	 * Méthode qui décide des actions à prendre lorsque l'utilisateur
	 * <b>clique</b> de la souris au sein de la grille de jeu.
	 */
	@Override
	public void mouseClicked (MouseEvent event) {
		this.coordX = event.getXOnScreen() -this.dessin.offsetLong();
		this.coordY = event.getYOnScreen()- this.dessin.offsetLarg() - this.bordure;
		for (int i = 0; i < 9; i++ ) {
			if (dessin.obtenirX()*i <= this.coordX && this.coordX <dessin.obtenirX()*(i+1)) {
				this.colonne = i;
			}
			if (dessin.obtenirY()*i <= this.coordY && this.coordY < dessin.obtenirY()*(i+1)) {
				this.ligne = i;
			}
		}
		if (this.coordX > 0 && this.coordX <= dessin.obtenirX()*9 && this.coordY > 0 && this.coordY <= dessin.obtenirY()*9) {
			if (this.editeur == false) {
				if (dessin.checkCase(this.colonne, this.ligne) == true) {
					JOptionPane.showMessageDialog(this.vue, "La valeur de cette case n'est pas\nmodifiable!", "Case intouchable", JOptionPane.ERROR_MESSAGE);
				}
				else {
					faireDessin();

				}
			}
			else {
				faireDessin();
			}

		}
	}
	/**
	 * * Méthode utile si l'on veut agir sur un <b> survol de souris </b> (par-dessus une case par exemple).
	 */
	@Override
	public void mouseEntered(MouseEvent evenement){


	}
	/**
	 * Méthode utile si l'on veut agir sur une <b> fin de survol  </b> survol de souris.
	 */        // debut du survol
	@Override
	public void mouseExited(MouseEvent evenement){

	}
	/**
	 * Méthode utile si l'on veut agir sur un <b> maintien de touche </b>.
	 */         // fin du survol
	@Override
	public void mousePressed(MouseEvent evenement){

	}
	/**
	 * Méthode utile si l'on veut agir sur un <b>lâcher de touche </b>.

	 */         // un bouton appuyé
	@Override
	public void mouseReleased(MouseEvent evenement) {;
	}        // un bouton relâché







	/**
	 * Méthode qui effectue les vérifications nécessaires au dessin du nombre sur la grille, s'il est valide
	 */

	public void faireDessin() {
		if (this.editeur == false) {
		String[] boutons = {"Oui","Non"};
		this.temporaire = JOptionPane.showOptionDialog(this.vue,"Voulez-vous insérer une valeur temporaire?",
				"Mode de Résolution",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,new ImageIcon(logo),
				boutons,boutons[0]);
		}
		else {
			this.temporaire = 1;
		}
		this.nombre = (String)JOptionPane.showInputDialog(this.vue, "Veuillez saisir la valeur à insérer dans la case.Si la valeur\nn'est pas comprise entre 1 et 9, la case sera vide", "Valeur à saisir", JOptionPane.QUESTION_MESSAGE,
				this.icone, null, "1");
		if (dessin.checkTemporaire(this.ligne, this.colonne) == false || this.temporaire == 1) {
			try {
				if (dessin.checkContrainte(this.colonne, this.ligne, Integer.parseInt(this.nombre)) == false) {
					JOptionPane.showMessageDialog(this.vue,
					 "La valeur rentre en conflit avec les valeurs précédentes !\nSi vous voulez rendre un nombre définitif temporaire, veuillez vider la case d'abord.",
					  "placement contradictoire", JOptionPane.ERROR_MESSAGE);
				}
				else {
				dessin.ajouterNombre(this.colonne, this.ligne, Integer.parseInt(this.nombre), this.temporaire);
				}
			}

			catch (NumberFormatException ne) {
				this.nombre = "0";
				dessin.ajouterNombre(this.colonne, this.ligne, Integer.parseInt(this.nombre), this.temporaire);
			}

			this.vue.repaint();
			if (dessin.checkGrille() == true) {
		JOptionPane.showMessageDialog(this.vue, "Vous avez résolu la grille !\n Pour une nouvelle partie, veuillez revenir au menu principal.", "Félicitations", JOptionPane.INFORMATION_MESSAGE);
			}
		}
		else {
			JOptionPane.showMessageDialog(this.vue, "Cette case comporte trop de valeurs temporaires !", "Trop de valeurs temporaires", JOptionPane.ERROR_MESSAGE);

		}
	}
}