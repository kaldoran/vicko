package graph;

import java.awt.ComponentOrientation;
import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * Classe abstraite de liste de vidéos.
 */
abstract public class VideoListe extends JPanel
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 4735061868397081800L;
	// Panneau des miniatures contenu dans une scrollbar
    protected JPanel panel = new JPanel();

    /**
     * Constructeur de base.
     */
    public VideoListe()
    {
        JScrollPane scroll = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
                                             JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // Elements placés le plus à gauche possible
        panel.setLayout(new WrapLayout(WrapLayout.LEADING));

        // Elements placés de gauche à droite
        panel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        
        this.setLayout(new GridLayout(1, 1));
        this.add(scroll);
    }
    
    /**
     * Ajouter une vidéo.
     * @param id Numéro de la vidéo
     * @param titre Titre de la vidéo
     * @param auteur Auteur de la vidéo
     * @param date Date de mise en ligne
     * @param img Lien vers l'image d'aperçu
     */
    abstract void ajouterVideo(int id, String titre, String auteur, String date, String img);
}
