package graph;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Classe fille de VideoApercu.
 * Implémente les aperçus des vidéos des utilisateurs.
 */
public final class VideoApercuStd extends VideoApercu
{   
    /**
	 * 
	 */
	private static final long serialVersionUID = -7745934706148965459L;

	/**
     * Constructeur.
     * @param id Id de la vidéo
     * @param titre Titre de la vidéo
     * @param auteur Auteur de la vidéo
     * @param date Date de mise en ligne de la vidéo
     * @param img Miniature de la vidéo
     */
    public VideoApercuStd(int id, String titre, String auteur, String date, String img) 
    {
        super(id, titre, auteur, date, img);
        
        clickImage();
        clickAuteur();
        clickTitre();
    }
    
    @Override
    protected void clickImage() 
    {
        image.addMouseListener(new MouseAdapter() 
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                // Clique gauche : On se rend sur la vidéo
                if(e.getButton() == MouseEvent.BUTTON1)
                    allerSurVideo();
            }
            
            @Override
            public void mouseEntered(MouseEvent e)
            {
                changerCurseur(image);
                changerAlpha(labelTitre, 128);
            }
            
            @Override
            public void mouseExited(MouseEvent e)
            {
                changerAlpha(labelTitre, 255);
            }
        });
    }

    @Override
    protected void clickAuteur() 
    {
        labelAuteur.addMouseListener(new MouseAdapter() 
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                // On met à jour la page de Membre
                ContainerP.GetInstance().getMembre().setMembre(VideoApercuStd.this.AUTEUR);

                // On se rend sur la page de l'auteur
                VideoApercuStd.tabbedPane.setSelectedIndex(ContainerP.INDEX_PROFIL_EN_COURS);
            }
            
            @Override
            public void mouseEntered(MouseEvent e)
            {
                changerCurseur(labelAuteur);
                changerAlpha(labelAuteur, 128);
            }
            
            @Override
            public void mouseExited(MouseEvent e)
            {
                changerAlpha(labelAuteur, 255);
            }
        });        
    }

    @Override
    protected void clickTitre() 
    {
        labelTitre.addMouseListener(new MouseAdapter() 
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                // Clique gauche : On se rend sur la vidéo
                if(e.getButton() == MouseEvent.BUTTON1)
                    allerSurVideo();
            }
            
            @Override
            public void mouseEntered(MouseEvent e)
            {
                changerCurseur(labelTitre);
                changerAlpha(labelTitre, 128);
            }
            
            @Override
            public void mouseExited(MouseEvent e)
            {
                changerAlpha(labelTitre, 255);
            }
        });
    }
}
