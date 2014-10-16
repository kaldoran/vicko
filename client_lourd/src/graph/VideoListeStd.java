package graph;

import java.awt.Dimension;

/**
 * Une liste de vidéos de différents membres
 */
public class VideoListeStd extends VideoListe
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -7603651170778079811L;
	// Largeur et hauteur du conteneur
    private static final int LARGEUR = Fenetre.LARGEUR - 10;
    private static final int HAUTEUR = 416;

    public VideoListeStd()
    {
        super();
    }

     /**
     * Permet l'ajout de vidéos dans la liste.
     * @param id Id de la vidéo à ajouter
     * @param titre Titre de la vidéo à ajouter
     * @param auteur Auteur de la vidéo
     * @param date Date d'upload
     * @param img Miniature de la vidéo
     */
    @Override
    void ajouterVideo(int id, String titre, String auteur, String date, String img) 
    {
        panel.add(new VideoApercuStd(id, titre, auteur, date, img));
    }
    
    @Override
    public Dimension getMinimumSize() 
    {
        return new Dimension(LARGEUR, HAUTEUR);
    }

    @Override
    public Dimension getPreferredSize() 
    {
        return new Dimension(LARGEUR, HAUTEUR);
    }
}
