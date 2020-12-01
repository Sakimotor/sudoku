
import java.util.stream.IntStream;

/**
 * @version 1.0
 * @author Jean-Philipp MAMBILA TETE et Maxence CRAMAREGEAS
 */

/**
 * Classe de l'algorithme, elle permet de <b>résoudre</b>
 * une grille en mode automatique.
 */


public class Algorithme {

    private int[][] grille = new int[9][9];

    /**
     * Méthode qui résoud la grille.
     * @param g correspond à la grille.
     */

    public boolean resoudre(int[][] g) {
        this.grille=g;
        for (int l = 0; l < 9; l++) {
            for (int c = 0; c < 9; c++) {
                if (this.grille[l][c]== 0) {
                    for (int k = 1; k <= 9; k++) {
                        this.grille[l][c]= k;
                        if (estValide(this.grille, l, c) && resoudre(this.grille)) {
                            return true;
                        }
                        this.grille[l][c]= 0;
                    }
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Méthode qui indique si la grille est valide.
     * @param g correspond à la grille.
     * @param l correspond à une ligne de la grille.
     * @param c correspond à une colonne de la grille.
     */

    private boolean estValide(int[][] g, int l, int c) {
        return (contrainteLigne(g, l)
                && contrainteColonne(g, c)
                && contrainteZone(g, l, c));
    }

    /**
     * Méthode qui indique si une ligne est valide.
     * @param g correspond à la grille.
     * @param l correspond à une ligne de la grille.
     */

    private boolean contrainteLigne(int[][]g, int l) {
        boolean[]contrainte = new boolean[9];
        return IntStream.range(0, 9)
                .allMatch(column -> verifContrainte(g, l, contrainte, column));
    }

    /**
     * Méthode qui indique si une colonne est valide.
     * @param g correspond à la grille.
     * @param c correspond à une colonne de la grille.
     */

    private boolean contrainteColonne(int[][]g, int c) {
        boolean[]contrainte = new boolean[9];
        return IntStream.range(0, 9)
                .allMatch(row -> verifContrainte(g, row, contrainte, c));
    }

    /**
     * Méthode qui indique si une zone est valide.
     * @param g correspond à la grille.
     * @param l correspond à une ligne de la grille.
     * @param col correspond à une colonne de la grille.
     */

    private boolean contrainteZone(int[][]g, int l, int col) {
        boolean[]contrainte = new boolean[9];
        int debutLigneZone = (l/3) * 3;
        int finLigneZone = debutLigneZone + 3;

        int debutColonneZone = (col/3) * 3;
        int finColonneZone = debutColonneZone + 3;

        for (int r = debutLigneZone; r < finLigneZone; r++) {
            for (int c = debutColonneZone; c < finColonneZone; c++) {
                if (!verifContrainte(g, r, contrainte, c)) return false;
            }
        }
        return true;
    }

    /**
     * Méthode qui indique si une contrainte est vérifiée ou non.
     * @param g correspond à la grille.
     * @param l correspond à une ligne de la grille.
     * @param contrainte correspond à une contrainte.
     * @param c correspond à une colonne de la grille.
     */

    public boolean verifContrainte(
            int[][] g,
            int l,
            boolean[]contrainte,
            int c) {
        if (g[l][c]!= 0) {
            if (!contrainte[g[l][c]- 1]) {
                contrainte[g[l][c]- 1]= true;
            } else {
                return false;
            }
        }
        return true;
    }

    /**
     * Méthode qui retourne la grille complétée en String.
     * @param g correspond à la grille.
     * @return la grille complétée dans le format String.
     */

    public String toString(int[][] g) {
        String grid = new String();
        for (int i = 0; i < 9; i++) {
            for  (int j = 0; j < 9; j++) {
                grid = grid + g[j][i];
            }
            grid = grid + "\n";

        }
        return grid;
    }

}
