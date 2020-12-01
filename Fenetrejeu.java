
import javax.swing.*;
import java.awt.*;
import java.io.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import static java.lang.System.nanoTime;

/** 
* @version 1.0
* @author Jean-Philipp MAMBILA TETE et Maxence CRAMAREGEAS 
*/
		/**
	 * Les <b>propriétés de la fenêtre</b> sont définies ici. Les
	 * méthodes concernant le comportement du jeu selon les
     * actions de l'utilisateurs sont appelées depuis une méthode de la classe {@link Actions}.
     * Si l'utilisateur décide de charger une grille, elle est récupérée grâce à la classe {@link Lecture},
     * puis dessinée à l'écran via la classe {@link Affichage}.
	 * Cette méthode est utilisée pour le programme de jeu
	 */
public class Fenetrejeu {
    private JFrame fenetre= new JFrame("Sudoku");
    private Image logo;
	private int bordure;
	private boolean vertical;
    public Fenetrejeu(){
		this.logo = Toolkit.getDefaultToolkit().getImage("sudokuk.jpg");
        this.fenetre.setIconImage(logo);
        this.fenetre.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.fenetre.setResizable(false);
        this.fenetre.setUndecorated(true);
        this.fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.fenetre.pack();
	}
    public void Afficher() {
    	//Définition des propriétés de la fenêtre
	Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
	double longueur;
	double largeur;
	long temps;
	if (d.getWidth() >= d.getHeight()) {
		this.vertical = false;
	 longueur =  d.getWidth();
	 largeur = d.getHeight();
	}
	else {
		vertical = true;
		 longueur =  d.getHeight();
		 largeur = d.getWidth();

	}
	//Création d'un objet de classe "Affichage" qui dessinera la grille et les nombres
	Affichage dessin = new Affichage(this.bordure, longueur,largeur, this.vertical);
	this.fenetre.add(dessin);
	JPanel panneau = new JPanel();
	JButton fermer = new JButton("X");

	fermer.setPreferredSize(new Dimension((int)longueur/25,(int) largeur/30));
	fermer.setBackground(Color.RED);
	fermer.setForeground(Color.WHITE);
	//Importation de la police 'Neuropol'
	try {
		Font neuropol = Font.createFont(Font.TRUETYPE_FONT, new File("Neuropol.ttf")).deriveFont((float)largeur/35);
		GraphicsEnvironment ge = 
			GraphicsEnvironment.getLocalGraphicsEnvironment();
		ge.registerFont(neuropol);
		fermer.setFont(neuropol);
   } catch (IOException|FontFormatException e) {
		//Handle exception
   }
    fermer.setFocusPainted(false);
    FlowLayout flowlayout = new FlowLayout(FlowLayout.RIGHT);
	panneau.setLayout(flowlayout);
	panneau.add(fermer);
    this.fenetre.add(panneau, BorderLayout.NORTH );
	JPanel pan2 = new JPanel();
	Color bleu = new Color(102, 161, 255);
		JButton menu = new JButton("Menu");
		menu.setPreferredSize(new Dimension((int)longueur/15,(int) largeur/30));
		menu.setBackground(bleu);
		menu.setForeground(Color.WHITE);
		menu.setFocusPainted(false);
	//Importation de la police "DFGothic"
	try {
		Font Gothic = Font.createFont(Font.TRUETYPE_FONT, new File("DFGothic.ttf")).deriveFont((float)largeur/50);
		GraphicsEnvironment ge = 
			GraphicsEnvironment.getLocalGraphicsEnvironment();
		ge.registerFont(Gothic);
		menu.setFont(Gothic);
   } catch (IOException|FontFormatException e) {
		//Handle exception
   }

	pan2.setLayout(new FlowLayout(FlowLayout.LEFT));
		pan2.add(menu);
	this.fenetre.add(pan2, BorderLayout.SOUTH);

    
	this.fenetre.setVisible(true);

    this.bordure = panneau.getHeight();

	Lecture reading = new Lecture(this.fenetre);
    JOptionPane.showMessageDialog(fenetre,"Veuillez charger une grille.","Charger grille",JOptionPane.WARNING_MESSAGE);
	chargerFichier(reading, dessin);
   //On décide si le mode de jeu est manuel ou automatique : decision vaut 0 s'il est manuel, 1 s'il est auto
	temps = modeResolution(dessin);
	Actions clique = new Actions(fenetre, dessin, this.bordure, false);
	this.fenetre.addMouseListener(clique);
	Boutons appui = new Boutons(fenetre, dessin);
	fermer.addActionListener(appui);
		menu.addActionListener(appui);
	}

	/**
	 * Méthode qui gère le chargement d'une grille
	 * @param r la  méthode de<b>lecture</b> à appliquer au fichier chargé
     * @param d la méthode de <b> dessin </b> de la grille chargée
	 */
	public void chargerFichier(Lecture r, Affichage d) {
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
			"Grilles de jeu", "gri");
		chooser.setFileFilter(filter);
		int returnVal = chooser.showOpenDialog(null);
		while (returnVal != JFileChooser.APPROVE_OPTION) {
			JOptionPane.showMessageDialog(fenetre,"Veuillez charger une grille.","Charger grille",JOptionPane.ERROR_MESSAGE);
			returnVal = chooser.showOpenDialog(null);
		}
		String grille = r.Lire(chooser.getSelectedFile().getPath());
		d.setGrille(grille);
	}


	/**
	 * Méthode qui laisse l'utilisateur choisir le <b> mode de résolution </b>
	 */
	public long modeResolution(Affichage d) {
		String[] boutons = {"Manuel","Automatique"};
		String s = new String();
		boolean resolu;
		long debutTemps;
		long fintemps;
		long temps = 0;
		int decisions = JOptionPane.showOptionDialog(fenetre,"Quel est le mode de résolution de la grille?",
		"Mode de Résolution",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,new ImageIcon(logo),
		boutons,boutons[0]);
		if (decisions == 0) {
			//Mode manuel
		}
		else {
			int[][] grille = new int[9][9];
			for (int i = 0; i < 9; i++){
				for (int j = 0; j < 9; j++){
					grille[i][j] = d.getNombre(i,j);
				}
			}
			Algorithme al = new Algorithme();
			debutTemps = setDebutTemps();
			resolu = al.resoudre(grille);
			if (resolu){
				temps = getTemps(debutTemps);
				d.setTemps(temps);
				//System.out.println(temps);
				s = al.toString(grille);
				d.setGrille(s);
				JOptionPane.showMessageDialog(this.fenetre, "La grille a été résolue!\n Pour recommencer une partie, veuillez retourner au menu", "Félicitations", JOptionPane.INFORMATION_MESSAGE);
			}else {
				JOptionPane.showMessageDialog(this.fenetre, "La grille que vous venez d'entrer est impossible à résoudre !", "Grille impossible", JOptionPane.ERROR_MESSAGE);
			}
		}
		return temps;
	}

	/**
	 * Méthode qui initialise le temps.
	 * @return le première valeur du compteur
	 */

	public long setDebutTemps(){
		long debutTemps;
		debutTemps = nanoTime();
		return debutTemps;
	}

	/**
	 * Méthode qui calcule la valeur totale du temps d'execution.
	 * @param d correspond à la valeur de départ du compteur.
	 * @return le temps total de l'execution de l'algorithme.
	 */

	public long getTemps(long d){
		long temps;
		temps = nanoTime() - d;
		return temps;
	}
}