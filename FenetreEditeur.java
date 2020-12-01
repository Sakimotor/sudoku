
import javax.swing.*;
import java.awt.*;
import java.io.*;
import javax.swing.filechooser.FileNameExtensionFilter;

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
     * Cette méthode est utilisée pour le programme d'édition
	 */
public class FenetreEditeur {
    private JFrame fenetre= new JFrame("Sudoku");
    private Image logo;
	private int bordure;
    private boolean vertical;
    private int demandecharge;
    public FenetreEditeur(){
		this.logo = Toolkit.getDefaultToolkit().getImage("sudokuk.jpg");
        this.fenetre.setIconImage(logo);
        this.fenetre.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.fenetre.setResizable(false);

        this.fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);this.fenetre.setUndecorated(true);
        this.fenetre.pack();

    }
    public void Afficher() {
    	//Définition des propriétés de la fenêtre
	Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
	double longueur;
	double largeur;
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
	JButton charger = new JButton("Charger");
	JButton sauvegarder = new JButton("Sauvegarder");
	charger.setPreferredSize(new Dimension((int)longueur/8,(int) largeur/30));
	Color bleu = new Color(102, 161, 255);
	charger.setBackground(bleu);
	charger.setForeground(Color.WHITE);
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
		charger.setFont(Gothic);
		menu.setFont(Gothic);
		sauvegarder.setFont(Gothic);
   } catch (IOException|FontFormatException e) {
		//Handle exception
   }

	charger.setFocusPainted(false);
	pan2.setLayout(new FlowLayout(FlowLayout.LEFT));
	pan2.add(charger);
		sauvegarder.setPreferredSize(new Dimension((int)longueur/10,(int) largeur/30));
		sauvegarder.setBackground(bleu);
		sauvegarder.setForeground(Color.WHITE);
		sauvegarder.setFocusPainted(false);
		pan2.add(sauvegarder);
		pan2.add(menu);
	this.fenetre.add(pan2, BorderLayout.SOUTH);

    
	this.fenetre.setVisible(true);

    this.bordure = panneau.getHeight();

	Lecture reading = new Lecture(this.fenetre);
	String[] chargement = {"Charger","Non"};
	 this.demandecharge = JOptionPane.showOptionDialog(fenetre,"Voulez-vous charger une grille prédéfinie?",
    "Charger grille ?",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,new ImageIcon(logo),
	chargement,chargement[0]);
	if (this.demandecharge == 0) {
		chargerFichier(reading, dessin);
	}

	Actions clique = new Actions(fenetre, dessin, this.bordure, true);
	this.fenetre.addMouseListener(clique);
	Boutons appui = new Boutons(fenetre, dessin);
	fermer.addActionListener(appui);
	charger.addActionListener(appui);
    sauvegarder.addActionListener(appui);
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
		if(returnVal == JFileChooser.APPROVE_OPTION) {
		String grille = r.Lire(chooser.getSelectedFile().getPath());
		d.setGrille(grille);
		}
	}


}