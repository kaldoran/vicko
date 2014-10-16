package graph;

import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * Une liste de commentaires d'une vidéo.
 */
public class CommentaireListe extends JPanel
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -749383228415586017L;
	private JPanel commentaires = new JPanel();
    
    /**
     * Constructeur par défaut.
     */
    public CommentaireListe()
    {
        JScrollPane scroll = new JScrollPane(commentaires, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
                                             JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        scroll.setPreferredSize(new Dimension(Fenetre.LARGEUR - 20, 190));
        commentaires.setLayout(new BoxLayout(commentaires, BoxLayout.Y_AXIS));

        this.setLayout(new GridLayout(1, 1));
        this.add(scroll);
    }
    
    /**
     * Ajoute un commentaire dans la liste
     * @param auteur Auteur du commentaire
     * @param texte Texte du commentaire
     * @param date Date de mise en ligne du commentaire
     */
    public void ajouterCommentaire(String auteur, String texte, String date)
    {
        commentaires.add(new Commentaire(auteur, texte, date));
    }
}
