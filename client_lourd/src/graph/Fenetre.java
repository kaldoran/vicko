package graph;

import infos.Utilisateur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import serveur.API;

/**
 * Fenêtre de l'application.
 */
public final class Fenetre extends JFrame
{
    private static final long serialVersionUID = 9138805814912467518L;

	/**
     * Largeur de la fenêtre.
     */
    public final static int LARGEUR = 644;
    
    /**
     * Hauteur de la fenêtre.
     */
    public final static int HAUTEUR = 526;

    /**
     * Titre de la fenêtre.
     */
    public final static String TITRE = "Vicko";
    
    // Conteneur de connexion
    private JPanel container;
    
    // Conteneur principal
    private JPanel containerP = null;
   
    // Barre de menu
    private JMenuBar menuBar = new JMenuBar();
    
    // Menus
    private JMenu mFichier      = new JMenu("Fichier");
    private JMenu mListeLecture = new JMenu("Lecture");
    
    // Menus secondaires
    private JMenuItem miImporterVideo = new JMenuItem("Importer une vidéo");
    private JMenuItem miFermer        = new JMenuItem("Fermer");
    private JMenuItem miExporterListe = new JMenuItem("Exporter ma liste");
            
    // Initialise la barre de menu
    public void creerMenuBar()
    {
    	
        // Ajouts des éléments
        mFichier.add(miImporterVideo);
        mFichier.addSeparator();
        mFichier.add(miFermer);
        
        mListeLecture.add(miExporterListe);
        
        menuBar.add(mFichier);
        menuBar.add(mListeLecture);
        
        // Actions des éléments
        miImporterVideo.addActionListener(new ActionListener() 
        {
			@Override
            public void actionPerformed(ActionEvent e) 
            {
            	@SuppressWarnings("unused")
                AjouterVideo video = new AjouterVideo(Fenetre.this, ContainerP.GetInstance().getVLU());
            }        
        });
        
        miExporterListe.addActionListener(new ActionListener() 
        {
			@Override
            public void actionPerformed(ActionEvent e) 
            {
                Utilisateur utl = Utilisateur.GetInstance();
                String Playlist = API.postPlaylist(utl.getPseudo());
                
                String fichier = "Maplaylist.xml";
                JFileChooser dialogue = new JFileChooser();
				dialogue.showSaveDialog(null);  
				  
				if(dialogue.getSelectedFile() != null)
				{
					fichier = dialogue.getSelectedFile().getPath();
				      
				    if(fichier.endsWith(".xml"))
				    	;
				    else
				    {
				    	String message = "Merci de n'utiliser que du .xml !";
				    	JOptionPane.showMessageDialog(null, message, "Attention", JOptionPane.WARNING_MESSAGE);
				    }
				}
				
                File xml = new File(fichier);
                
                try {
                    FileWriter fw = new FileWriter (xml);
                 
                    fw.write (Playlist);
                    fw.write ("\n");
                 
                    fw.close();
                } catch (IOException exception) {
                    System.out.println (exception.getMessage());
                }
            }        
        });
        
        this.setJMenuBar(menuBar);

        //Barre désactivée        
        mFichier.setEnabled(false);
        mListeLecture.setEnabled(false);
    }
    	
    /**
     * Met à jour la fenêtre avec le container principal
     */
    public void majContainerPrincipal() {
    	Utilisateur utl = Utilisateur.GetInstance();
    	containerP = ContainerP.GetInstance(utl.getPseudo());
        this.setContentPane(containerP);       
        mFichier.setEnabled(true);
        mListeLecture.setEnabled(true);
        this.revalidate();
        this.repaint();
    }
    
    /**
     * Constructeur de la fenêtre de l'application.
     */
    public Fenetre()
    {
        this.setTitle(Fenetre.TITRE);
        this.setSize(Fenetre.LARGEUR, Fenetre.HAUTEUR);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);             

        creerMenuBar();
        
        // Ajout du formulaire de connexion
    	container = new Connexion(this);        
    	this.setContentPane(container);
        
        // Fenêtre visible et non redimensionnable par défaut        
        this.setResizable(false);
        this.setVisible(true);
    }
    

    public static void main(String args[])
    {              
        // Création de la fenêtre
        @SuppressWarnings("unused")
        Fenetre fenetre = new Fenetre();
    }
}
