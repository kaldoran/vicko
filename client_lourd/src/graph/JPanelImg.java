package graph;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Un panel pouvant facilement contenir une image en arrière plan.
 */
public class JPanelImg extends JPanel
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 4319530624638526153L;
	// Label
    private JLabel label;

    /**
     * Constructeur de JPanelImg.
     * @param fichier Fichier image à charger.
     * @param largeur Largeur de l'image.
     * @param hauteur Hauteur de l'image.
     */
    public JPanelImg(String fichier, int largeur, int hauteur)
    {
        Toolkit toolkit = Toolkit.getDefaultToolkit();  
        Image image = toolkit.createImage(fichier);  
               
        // Application de l'image
        label = new JLabel(new ImageIcon(image.getScaledInstance(largeur, hauteur, Image.SCALE_DEFAULT)));
        this.setLayout(new GridLayout(1, 1));
        this.add(label);
    }    
    
    /**
     * Changer l'image du JPanel.
     * @param fichier Fichier image à charger.
     * @param largeur Largeur de l'image.
     * @param hauteur Hauteur de l'image.
     */
    public void changerImage(String fichier, int largeur, int hauteur)
    {
        Toolkit toolkit = Toolkit.getDefaultToolkit();  
        Image image = toolkit.createImage(fichier);  
        
        label.setIcon(new ImageIcon(image.getScaledInstance(largeur, hauteur, Image.SCALE_DEFAULT)));
    }
}
