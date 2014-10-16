package graph;

import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Barre d'informations.
 */
public final class StatusBar extends JPanel
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -1193077234493237726L;

	// Texte de la barre
    private JLabel label;
      
    // Taille max de la barre
    private int largeurMax = 122;
    
    // Crée une chaine vide d'une certaine longeur.
    private String chaineVide(int largeur)
    {
        if(largeur <= 0)
            return new String();
        return new String(new char[largeur]).replace('\0', ' ');
    }
    
    /**
     * Constructeur par défaut.
     */
    public StatusBar()  
    {
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        label = new JLabel(chaineVide(largeurMax));
        this.add(label);
    }

    /**
     * Définir le texte de la barre.
     * @param texte Texte.
     */
    public void setTexte(String texte)
    {
        label.setText(texte + chaineVide(largeurMax - texte.length()));
    }
    
    /**
     * Définir la largeur max de la barre en nombre de caractères.
     * @param largeurMax Largeur max à définir.
     */
    public void setLargeurMax(int largeurMax)
    {
        this.largeurMax = largeurMax;
    }
}
