

import java.io.*;
import javax.swing.*;
/**
 * @version 1.0
 * @author Jean-Philipp MAMBILA TETE et Maxence CRAMAREGEAS
 */
/**
 * Classe servant à interpréter les fichiers <i>".gri"</i> contenant
 * les <b>grilles</b> de jeu que l'on peut charger.
 */
public class Lecture{
	private String output;
	private JFrame vue;
	/**
	 * Constructeur qui récupère les détails sur la <b>fenêtre</b>
	 * que l'on veut modifier.
	 * @param v la <b>vue</b> sur laquelle on agit
	 */
	public Lecture(JFrame v) {
		this.output = "";
		this.vue = v;
	}
	public String Lire(String s) {
		try{
			FileInputStream d = new FileInputStream(s);
			DataInputStream f = new DataInputStream(d);
			try {
				if (f.available() <= 0) {
					JOptionPane.showMessageDialog( this.vue, "Le fichier ne semble pas correct",
							"Format incorrect", JOptionPane.ERROR_MESSAGE);
					System.err.println("format incorrect");
				}
				else {
					while (f.available() > 0){
						int valeur = f.readInt();
						output = output + String.format("%09d",valeur) + "\n";
					}
				}




			}catch (IOException e){
				JOptionPane.showMessageDialog( this.vue, "Erreur de  lecture",
						"Lecture échouée", JOptionPane.ERROR_MESSAGE);
				System.err.println("erreur lecture");
			}
			try {
				f.close();

			}catch(IOException e){
				JOptionPane.showMessageDialog( this.vue, "Erreur de fermeture",
						"Fermeture échouée", JOptionPane.ERROR_MESSAGE);
				System.err.println("erreur de fermeture");
			}
		}


		catch(FileNotFoundException e){
			JOptionPane.showMessageDialog( this.vue, "Fichier inexistant, la grille a été\nremise à zéro",
					"Fichier inexistant", JOptionPane.ERROR_MESSAGE);
			System.err.println("fichier non-existant");
		}
		return this.output;

	}
}