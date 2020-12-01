

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.concurrent.TimeUnit;
/**
 * @version 1.0
 * @author Jean-Philipp MAMBILA TETE et Maxence CRAMAREGEAS
 */
/**
 * Classe d'Affichage, c'est celle qui interprète toutes
 * les actions effectuées par l'utilisateur sous <b>forme graphique</b>.
 */

public class Affichage extends JComponent {
	/**
	 *
	 */
	private static final long serialVersionUID = -100427927955133878L;
	private final int rationum;
	private final int ratioden;
	private final int offsetX;
	private final int offsetY;
	private int bordure;
	private	boolean defaut;
	private boolean contrainte;
	private boolean remplie;

	private int[][] nombre = new int[9][9];
	private int[][] initial = new int[9][9];
	private int[][] temporaire1 = new int [9][9];
	private int[][] temporaire2 = new int [9][9];
	private int[][] temporaire3 = new int [9][9];
	private int[][] temporaire4 = new int [9][9];
	private boolean[][] tempotrop = new boolean [9][9];


	private Font modaerne;
	private Font petit;
	private FontMetrics metrics;
	private int textelongueur;
	private int textehauteur;
	private String grille;
	private int carreX;
	private int carreY;

	private String temps;
	private long tmp;
	/**
	 * Constructeur qui récupère les détails sur la fenêtre
	 * que l'on veut analyser.
	 * @param b la<b> bordure </b> du haut de notre fenêtre de jeu
	 * @param w la <b> (int)longueur</b> de notre fenêtre de jeu
	 * @param h la  <b>(int)largeur</b> de notre fenêtre de jeu
	 */
	public Affichage(int b, double w, double h, boolean r) {
		double numerateur = ((h/w)*1000000)%1000000;
		this.rationum =(int)numerateur ;
		this.ratioden = 1000000;
		this.bordure = b;
		double decalageX;
		double decalageY;
		if (r == true) {
			decalageX = 0;
			decalageY = h/2;

		}
		else {
			decalageX = w/4.35;
			decalageY = h/8;

		}
		this.offsetX =(int) decalageX;
		this.offsetY = (int) decalageY;
	}
	/**
	 * On <b>dessine</b> la grille ainsi que les nombres qu'elle contient
	 */
	@Override
	protected void paintComponent( Graphics pinceau) {
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		double longueur;
		double largeur;
		if (d.getWidth() >= d.getHeight()) {
			longueur =  d.getWidth();
			largeur = d.getHeight();
		}
		else {
			longueur =  d.getHeight();
			largeur = d.getWidth();

		}


// obligatoire : on crée un nouveau pinceau pour pouvoir le modifier plus tard
		Graphics secondPinceau = pinceau.create();
// obligatoire : si le composant n'est pas censé être transparent
		if (this.isOpaque()) {
			// obligatoire : on repeint toute la surface avec la couleur de fond
			secondPinceau.setColor(this.getBackground());
			secondPinceau.fillRect(0, 0, (int)longueur, (int)largeur+this.bordure);
		}
		secondPinceau.setColor(Color.BLACK);
//on dessine des carrés représentant des cases


		for (int j = 0; j < 9; j++) {
			for (int k = 0; k < 9; k++) {
				secondPinceau.drawRect(
						(int)longueur/9*j*this.rationum/this.ratioden+this.offsetX,
						((int)largeur+this.bordure)/9*k*this.rationum/this.ratioden+this.offsetY,
						(int)longueur/9*this.rationum/this.ratioden,
						((int)largeur+this.bordure)/9*this.rationum/this.ratioden);
			}
		}
		this.carreX =((int)longueur/9)*rationum/ratioden;
		this.carreY =((int)largeur+this.bordure)/9*this.rationum/this.ratioden;


// on dessine des rectangles représentant chacun une zone
		for (int l = 0; l < 3; l++) {
			for (int m = 0; m < 3; m++) {
				for (int n = 0; n < 7; n++) {
					secondPinceau.drawRect(
							(int)longueur/3*l*this.rationum/this.ratioden+this.offsetX-n,
							((int)largeur+this.bordure)/3*m*this.rationum/this.ratioden+this.offsetY-n,
							(int)longueur/3*this.rationum/this.ratioden,
							((int)largeur+this.bordure)/3*this.rationum/this.ratioden);
				}
			}
		}
// on dessine le nombre à ajouter
		secondPinceau.setColor(Color.RED);
//Importation de la police 'Modaerne' pour la grille
		try {
			this.modaerne = Font.createFont(Font.TRUETYPE_FONT, new File("Modaerne.ttf")).deriveFont((float)largeur/16);
			this.petit = Font.createFont(Font.TRUETYPE_FONT, new File("Modaerne.ttf")).deriveFont((float)largeur/37);
			GraphicsEnvironment ge =
					GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(this.modaerne);
			ge.registerFont(this.petit);
		} catch (IOException|FontFormatException e) {
			//Handle exception
		}
		secondPinceau.setFont(this.modaerne);
		this.metrics = secondPinceau.getFontMetrics();
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				this.textelongueur = metrics.stringWidth(Integer.toString(this.nombre[i][j]))*this.rationum/this.ratioden;
				this.textehauteur = metrics.getHeight()*this.rationum/this.ratioden;
				if (this.nombre[i][j] !=0) {
					if (this.nombre[i][j] == this.initial[i][j]){
						secondPinceau.setColor(Color.BLUE);
					} else {
						secondPinceau.setColor(Color.RED);
					}
					secondPinceau.drawString(Integer.toString(this.nombre[i][j]),
							(int)longueur/9*i*this.rationum/this.ratioden+this.offsetX
									+(int)(longueur/18)*this.rationum/this.ratioden - this.textelongueur/2,
							(int)(largeur+this.bordure)/9*(j+1)*this.rationum/this.ratioden+
									this.offsetY+((int)largeur+this.bordure)/18*this.rationum/this.ratioden- this.textehauteur);
				}
				else {
					if (this.temporaire1[i][j] != 0) {
						secondPinceau.setColor(new Color(0, 100, 0));
						secondPinceau.setFont(this.petit);
						this.metrics = secondPinceau.getFontMetrics();
						this.textelongueur = metrics.stringWidth(Integer.toString(this.nombre[i][j]))*this.rationum/this.ratioden;
						this.textehauteur = metrics.getHeight()*this.rationum/this.ratioden;

						secondPinceau.drawString(Integer.toString(this.temporaire1[i][j]),
								(int)longueur/9*i*this.rationum/this.ratioden+this.offsetX + this.textelongueur*2,
								(int)(largeur+this.bordure)/9*(j+1)*this.rationum/this.ratioden+
										this.offsetY- this.textehauteur*5/2);

						if (this.temporaire2[i][j] != 0) {
							secondPinceau.drawString(Integer.toString(this.temporaire2[i][j]),
									(int)longueur/9*(i+1)*this.rationum/this.ratioden+this.offsetX- this.textelongueur*5,
									(int)(largeur+this.bordure)/9*(j+1)*this.rationum/this.ratioden+
											this.offsetY- this.textehauteur*5/2);
						}

						if (this.temporaire3[i][j] != 0) {
							secondPinceau.drawString(Integer.toString(this.temporaire3[i][j]),
									(int)longueur/9*i*this.rationum/this.ratioden+this.offsetX + this.textelongueur*2,
									(int)(largeur+this.bordure)/9*(j+1)*this.rationum/this.ratioden+
											this.offsetY - this.textehauteur/2);
						}
						if (this.temporaire4[i][j] != 0) {
							secondPinceau.drawString(Integer.toString(this.temporaire4[i][j]),
									(int)longueur/9*(i+1)*this.rationum/this.ratioden+this.offsetX- this.textelongueur*5,
									(int)(largeur+this.bordure)/9*(j+1)*this.rationum/this.ratioden+
											this.offsetY - this.textehauteur/2);

						}
						secondPinceau.setFont(this.modaerne);
						this.metrics = secondPinceau.getFontMetrics();
					}
				}
			}
		}

		try {
			Font DFGothic = Font.createFont(Font.TRUETYPE_FONT, new File("DFGothic.ttf")).deriveFont((float)largeur/50);
			GraphicsEnvironment ge =
					GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(DFGothic);
			secondPinceau.setFont(DFGothic);
		} catch (IOException|FontFormatException e) {
			//Handle exception
		}

		if (this.tmp != 0){
			secondPinceau.setColor(Color.BLACK);
			secondPinceau.drawString("L'algorithme a mis " + this.temps + "secondes",this.getWidth()/6,this.getHeight()/15);
		}
	}

	/**
	 * Méthode qui vérifie si la case est modifiable ou pas
	 * @param l la ligne de la case concernée
	 * @param c la colonne de la case concernée
	 * @return le booléen qui dit si la case est touchable ou pas
	 */
	public boolean checkCase(int l, int c) {
		if (this.initial[l][c] == 0) {
			this.defaut = false;
		}
		else {
			this.defaut = true;
		}
		return this.defaut;
	}

	/**
	 * Méthode qui définit le <b>nombre à ajouter </b> suite aux interactions de l'utilisateurs
	 * dans la classe {@link Actions}.
	 * @see Actions
	 * @param l la <b>ligne</b> affectée
	 * @param c la <b>colonne</b> affectée
	 * @param n le <b>nombre</b> à insérer
	 */
	public void ajouterNombre(int l, int c, int n, int t) {
		if (n <= 0  || n > 9) {
			this.nombre[l][c] = 0;
			this.temporaire1[l][c] = 0;
			this.temporaire2[l][c] = 0;
			this.temporaire3[l][c] = 0;
			this.temporaire4[l][c] = 0;
			this.tempotrop[l][c] = false;
		}
		else {
			if (t != 0) {
				this.tempotrop[l][c] = false;
				this.nombre[l][c] = n;
				this.temporaire1[l][c] = 0;
				this.temporaire2[l][c] = 0;
				this.temporaire3[l][c] = 0;
				this.temporaire4[l][c] = 0;
				this.tempotrop[l][c] = false;

			}

			else {
				if (this.tempotrop[l][c] == false)	{
					this.nombre[l][c] = 0;
					if (this.temporaire1[l][c] == 0) {
						this.temporaire1[l][c] = n;
					}
					else if (this.temporaire2[l][c] == 0) {
						this.temporaire2[l][c] = n;
					}
					else if (this.temporaire3[l][c] == 0){
						this.temporaire3[l][c] = n;
					}
					else if (this.temporaire4[l][c] == 0) {
						this.temporaire4[l][c] = n;
						this.tempotrop[l][c] = true;
					}
				}
			}
		}
	}
	/**
	 * Méthode qui met en place le timer du mode automatique
	 * @param t le timer
	 */
	public void setTemps(long t){
		this.tmp = t;
		double convert = (double)this.tmp/1__000__000__000;
		this.temps = Double.toString(convert);
	}
	/**
	 * Méthode qui renvoie le nombre se trouvant à une case 
	 * @param l ligne de la case
	 * @param c colonne de la case
	 * @return la valeur du nombre
	 */
	public int getNombre(int l, int c) { return this.nombre[l][c]; }
	/**
	 * Méthode qui renvoie la présence ou non de quatre valeurs temporaires sur une case
	 * @param l la ligne de la case à vérifier
	 * @param c la colonne de la case à vérifier
	 * @return le booléen qui confirme ou infirme la présence des valeurs temporaires
	 */
	public boolean checkTemporaire(int l, int c) {
		return this.tempotrop[l][c];
	}
	/**
	 * Méthode qui vérifie si les contraintes du sudoku sont respectées ou pas
	 * @param l la <b> ligne </b> de la case à vérifier
	 * @param c la <b> colonne </b> de la case à vérifier
	 * @param n le <b> nombre </b> de la case à vérifier
	 * @return
	 */
	public boolean checkContrainte(int l, int c, int n) {
		if (n > 0 && n < 10) {
		int compteurligne = 0;
				for (int i = 0; i < 9; i++) {
				if (this.nombre[i][c] == n || this.nombre[l][i] == n) {
					++compteurligne;
				}
				if (compteurligne == 0) {
					int lzone = l-(l%3);
					int czone = c - (c%3);
					int compteurcontrainte = 0;
					for (int j = lzone; j < lzone +3; j++ ) {
						for (int k = czone; k < czone +3; k++) {
							if (this.nombre[j][k] == n) {
								++compteurcontrainte;
							}							
						}
					}
					if (compteurcontrainte == 0) {
						this.contrainte = true;
							}
							else {
								this.contrainte = false;
							}
				}
				else {
					this.contrainte = false;
				}
			}
	}
	else {
		this.contrainte = true;
	}
	return this.contrainte;
}
/**
 * Méthode qui dit si la grille a été remplie par l'utilisateur ou pas
 * @return un booléen qui confirme ou infirme le remplissage
 */
public boolean checkGrille() {
	int compteur = 0;
	for (int i = 0; i < 9 ; i++) {
		for (int j = 0; j < 9; j++) {
			if (this.nombre[i][j] != 0) {
				++compteur;
			}
		}
	}
	if (compteur == 81) {
		this.remplie = true;
		for (int i = 0; i < 9 ; i++) {
			for (int j = 0; j < 9; j++) {
					this.initial[i][j] = this.nombre[i][j];
			}
		}
	}
	else {
		this.remplie = false;
	}
	return this.remplie;
}
//renvoyer le décalage horizontal et vertical du sudoku par rapport à l'intérieur de la fenêtre
	/**
	 * Méthode qui renvoie le<b> décalage horizontal </b> de la grille par
	 * rapport à la fenêtre vers la classe {@link Actions} pour détecter le clic souris
	 * de l'utilisateur à l'intérieur de la grille.
	 * @return la valeur du <b>décalage horizontal</b>
	 */
	public int offsetLong() {
		return this.offsetX;
	}
	/**
	 * Méthode qui renvoie le <b>décalage vertical </b> de la grille par
	 * rapport à la fenêtre vers la classe {@link Actions} pour détecter le clic souris
	 * de l'utilisateur à l'intérieur de la grille.
	 * @return la valeur du <b>décalage vertical</b>
	 */
	public int offsetLarg() {
		return this.offsetY;
	}
//renvoyer le ratio de mise à l'échelle de la fenêtre
	/**
	 * Méthode qui renvoie le <b>numérateur de la mise à l 'échelle</b> de la
	 * fenêtre vers la classe {@link Actions} pour détecter le clic souris de
	 * de l'utilisateur à l'intérieur de la grille.
	 * @return le <b>numérateur</b> de la mise à l'échelle
	 */
	public int getNumerateur() {
		return this.rationum;
	}
	/**
	 * Méthode qui renvoie le <b>dénominateur de la mise à l'échelle</b> de la
	 * fenêtre vers la classe {@link Actions} pour détecter le clic souris de
	 * de l'utilisateur à l'intérieur de la grille.
	 * @return le <b>dénominateur</b> de la mise à l'échelle
	 */
	public int getDenominateur() {
		return this.ratioden;
	}
	/**
	 * Méthode qui renvoie la <b>(int)longueur d'une case</b> de la
	 * grille vers la classe {@link Actions} pour détecter le clic souris de
	 * de l'utilisateur à l'intérieur de celle-ci et ignorer les clics en dehors de la grille.
	 * @return la <b>(int)longueur</b> d'une case
	 */
	public int obtenirX() {
		return this.carreX;
	}
	/**
	 * Méthode qui renvoie la <b>(int)largeur d'une case</b> de la
	 * grille vers la classe {@link Actions} pour détecter le clic souris de
	 * de l'utilisateur à l'intérieur de celle-ci et ignorer les clics en dehors de la grille.
	 * @return la <b>(int)largeur</b> d'une case
	 */
	public int obtenirY() {
		return this.carreY;
	}
	/**
	 * Méthode qui reçoit la <b>grille</b> de jeu depuis la classe
	 *  {@link Fenetre}, et qui dessine celle-ci.
	 * @param g <b>la chaîne de caractères</b> représentant la grille
	 */
	public void setGrille(String g) {
		this.grille = g;
		String newline = "\n";
		String numero;
		int j = 0;
		for (int i = 0; i < this.grille.length(); i++) {
			if (this.grille.charAt(i) != newline.charAt(0)) {
				numero = String.valueOf(this.grille.charAt(i));
				this.nombre[j%9][j/9] = Integer.parseInt(numero);
				this.initial[j%9][j/9] = Integer.parseInt(numero);
				++j;
			}
		}

		repaint();
	}
	/**
	 * Méthode qui renvoie la <b>grille</b> dans son état actuel.
	 * Utile pour la classe {@link Ecriture} qui doit l'enregistrer
	 * dans un fichier.
	 * @return la <b>grille</b> actuelle
	 */
	public String getGrille() {
		String grid = new String();
		for (int i = 0; i < 9; i++) {
			for  (int j = 0; j < 9; j++) {
				grid = grid + this.nombre[j][i];
			}
			grid = grid + "\n";

		}
		return grid;

	}



}