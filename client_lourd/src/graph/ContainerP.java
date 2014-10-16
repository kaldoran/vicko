package graph;

import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 * Container principal de l'application. Contient entre autres tous les onglets
 * et JPanels associés. Accessoirement est un Singleton.
 */
public final class ContainerP extends JPanel
{

	private static final long serialVersionUID = 1674173730527990177L;

	// Instance unique
    private static ContainerP containerP = null;
    
    // Onglets
    protected static JTabbedPane tabbedPane = null;
    
    /**
     * Index de l'onglet "Recherche".
     */
    public static final int INDEX_RECHERCHE = 0;

    /**
     * Index de l'onglet "Ma playlist".
     */
    public static final int INDEX_MA_PLAYLIST = 1;

    /**
     * Index de l'onglet "Vidéo en cours".
     */
    public static final int INDEX_VIDEO_EN_COURS = 2;

    /**
     * Index de l'onglet "Profil en cours".
     */
    public static final int INDEX_PROFIL_EN_COURS = 3;
    
    /**
     * Index de l'onglet "Chat".
     */
    public static final int INDEX_CHAT = 4;
    
    /**
     * Index de l'onglet "Profil".
     */
    public static final int INDEX_PROFIL = 5;
    
    /**
     * Crée une instance.
     * @param pseudo Membre ayant lancé l'application
     * @return Une instance de ContainerP
     */
    public static synchronized ContainerP GetInstance(String pseudo)
    {
        if(containerP == null)        
            containerP = new ContainerP(pseudo);

        return containerP;
    }
    
    /**
     * Crée une instance sans nom. OU Récupère l'instance courante.
     * @return L'instance actuelle ou une instance sans nom
     */
    public static ContainerP GetInstance()
    {
        return GetInstance("");
    }
    
    // Constructeur privé => Singleton
    private ContainerP(String pseudo)
    {
        tabbedPane = new JTabbedPane();
        
        Recherche recherche = new Recherche();

        // Onglets
        tabbedPane.addTab("Recherche", recherche);
        tabbedPane.addTab("Ma playlist", GenerateurVideoListe.getVideoListe(GenerateurVideoListe.UTILISATEUR, 
                                                                            "F" + pseudo));

        tabbedPane.addTab("Vidéo en cours", new Video());
        tabbedPane.addTab("Profil en cours", new Membre(tabbedPane, recherche));
        tabbedPane.addTab("Chat", new Chat());
        tabbedPane.addTab("Mon profil", GenerateurMembre.getProfil(pseudo));

        // Index à l'ouverture du container
        tabbedPane.setSelectedIndex(INDEX_RECHERCHE);
        
        // Type de layout
        this.setLayout(new GridLayout(1, 1));
        
        // Ajout du container
        this.add(tabbedPane);
    }
    
    /**
     * Obtenir la liste de vidéos de l'utilisateur de l'app.
     * @return La liste de vidéos de l'utilisateur
     */
    public VideoListeUtilisateur getVLU()
    {
        return (VideoListeUtilisateur)tabbedPane.getComponentAt(INDEX_MA_PLAYLIST);
    }    
    
    /**
     * Obtenir la page de membre du container.
     * @return La page de membre
     */
    public Membre getMembre()
    {
        return (Membre)tabbedPane.getComponentAt(INDEX_PROFIL_EN_COURS);
    }
}
