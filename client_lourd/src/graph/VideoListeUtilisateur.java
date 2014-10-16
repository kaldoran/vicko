package graph;

/**
 * Implémente une liste de vidéos de l'utilisateur de l'application.
 * Permet ainsi les ajouts et modifications de vidéos.
 */
public class VideoListeUtilisateur extends VideoListe
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 2467202957369260138L;

	/**
     * Constructeur standard.
     */
    public VideoListeUtilisateur()
    {
        super();
    }

    /**
     * Permet l'ajout de vidéos dans la liste.
     * @param id Id de la vidéo à ajouter
     * @param titre Titre de la vidéo à ajouter
     * @param auteur Auteur de la vidéo (En théorie soit-même MAIS on peut espérer que dans une
     * future mise à jour, un "fork" de vidéos d'autres membres soit possible.)
     * @param date Date d'upload
     * @param img Miniature de la vidéo
     */
    @Override
    void ajouterVideo(int id, String titre, String auteur, String date, String img) 
    {
        panel.add(new VideoApercuUtilisateur(id, titre, auteur, date, img, panel));
    }
}
