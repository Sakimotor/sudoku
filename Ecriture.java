import java.io.*;
import java.nio.ByteBuffer;
import java.lang.Integer; 
  

/** 
* @version 1.0
* @author Jean-Philipp MAMBILA TETE et Maxence CRAMAREGEAS 
*/
		/**
	 * Classe servant à créer les fichiers <i>".gri"</i> contenant
	 * les <b>grilles</b> de jeu que l'on peut charger plus tard.
	 */
public class Ecriture{
    private String input;
    private String nomfichier;
		/**
	 * Constructeur qui récupère les détails sur le <b>fichier</b>
	 * où l'on veut écrire.
	 * @param n le <b>nom</b> du fichier à créer
     * @param i la <b> grille </b> à inscrire dans le fichier
	 */
	public Ecriture(String i, String n) {
		this.nomfichier = n;
		this.input = i;
    }
    	/**
		 * Méthode qui<b> écrit</b> dans un fichier(ou le crée s'il n'existe pas).

	 */
	public void Ecrire() {
		try{
            FileOutputStream fout=new FileOutputStream(this.nomfichier);
            int i = 0;
            int j = 0;  
            int longueur = 9;
            while (i < 9) {
                String valeurs = new String();
               while (valeurs.length() != longueur) {
                  

            if ((this.input.charAt(j)) != '\n' ) {        
            valeurs = valeurs + this.input.charAt(j);
            }
            else {
            }
            j = j + 1;
              
         }
         int nombre = Integer.parseInt(valeurs);
         byte[] b = ByteBuffer.allocate(4).putInt(nombre).array();

         fout.write(b);
         ++i;
        }
 
    
            fout.close();     
           }catch(Exception e){
               System.out.println(e);
               e.printStackTrace();}    
     }    



}