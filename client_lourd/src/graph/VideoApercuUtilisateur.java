package graph;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

/**
 * Classe fille de VideoApercu.
 * Un apercu d'une vidéo Utilisateur.
 */
public final class VideoApercuUtilisateur extends VideoApercu
{   
    /**
	 * 
	 */
	private static final long serialVersionUID = 1993221925986985919L;
	// Popup de la miniature
    private JPopupMenu popupApercu = new JPopupMenu();
    
    /**
     * Constructeur principal.
     * @param id Numéro de la vidéo
     * @param titre Titre de la vidéo
     * @param auteur Auteur de la vidéo
     * @param date Date de mise en ligne
     * @param img Lien vers l'image d'aperçu
     * @param vdl Conteneur de VideoApercuUtilisateur
     */
    public VideoApercuUtilisateur(int id, String titre, String auteur, String date, String img, 
                                  JPanel vdl) 
    {
        super(id, titre, auteur, date, img);
        
        // Popup items
        creerPopup(vdl);
        
        // Paramétrages des clicks
        clickImage();
        clickAuteur();
        clickTitre();
        clickApercu();
    }
    
    private void clickApercu()
    {
        this.addMouseListener(new MouseAdapter() 
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                // Clique droit : On ouvre la popup
                if(e.getButton() == MouseEvent.BUTTON3)
                   popupApercu.show(VideoApercuUtilisateur.this, e.getX(), e.getY());
            }
        });
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
                // Clique droit : On ouvre la popup
                else if(e.getButton() == MouseEvent.BUTTON3)
                   popupApercu.show(VideoApercuUtilisateur.this, e.getX(), e.getY());
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
                // Clique droit : On ouvre la popup
                if(e.getButton() == MouseEvent.BUTTON3)
                   popupApercu.show(VideoApercuUtilisateur.this, e.getX(), e.getY());
                else
                {
                    String message = "Vous êtes le propriétaire de cet upload. Aucune action à effectuer.";
                
                    // Peut importe le click, on affiche un message.
                    JOptionPane.showMessageDialog(null, message, "Information", JOptionPane.INFORMATION_MESSAGE);
                }
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
                // Clique droit : On ouvre la popup
                else if(e.getButton() == MouseEvent.BUTTON3)
                   popupApercu.show(VideoApercuUtilisateur.this, e.getX(), e.getY());
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
    
    private void creerPopup(final JPanel vdl)
    {
        // Modifier miniature
        JMenuItem item = new JMenuItem("Modifier Miniature");
        
        item.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                String fichier;
                JFileChooser dialogue = new JFileChooser();
                dialogue.showOpenDialog(null);  
                
                if(dialogue.getSelectedFile() != null)
                {
                    fichier = dialogue.getSelectedFile().getPath();
                    
                    // Bonne extension
                    if(fichier.endsWith(".png") || fichier.endsWith(".jpg") || fichier.endsWith(".jpeg"))
                    {
                        // Envoi de l'image au serveur
                        // #PASFAIT
                        
                        // On change la miniature locale
                        VideoApercuUtilisateur.this.image.changerImage(fichier, VideoApercu.LARGEUR_IMAGE, 
                                                                       VideoApercu.HAUTEUR_IMAGE);
                    }
                    // Erreur
                    else
                    {
                        String message = "Merci de n'utiliser que du png, jpg ou jpeg !";
                        JOptionPane.showMessageDialog(null, message, "Attention", JOptionPane.WARNING_MESSAGE);
                    }
                }             
            }
        });
                
        popupApercu.add(item);

        // Modifier Titre de la vidéo
        item = new JMenuItem("Modifier Titre");
        
        item.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                String titre = JOptionPane.showInputDialog(null, "Nouveau titre", "Edition du titre", 
                                                           JOptionPane.INFORMATION_MESSAGE);
               
                // Si le titre n'est pas vide
                if(titre != null)
                {
                    // MAJ côté serveur
                    // #PASFAIT
                    
                    // Application du titre local
                    VideoApercuUtilisateur.this.labelTitre.setText(
                            (titre.length() >= 25) ? titre.substring(0, 20) + "..." : titre);
                }
            }
        });
              
        popupApercu.add(item);
        popupApercu.addSeparator();
        
        // Supprimer la vidéo
        item = new JMenuItem("Supprimer la vidéo");
        
        item.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                int reponse = JOptionPane.showConfirmDialog(VideoApercuUtilisateur.this,
			       "Etes-vous sûr de vouloir supprimer cette vidéo ?",
			       "Suppression", 
			        JOptionPane.YES_NO_OPTION);
                
                if(reponse == JOptionPane.YES_OPTION)
                {
                    // Demande au serveur de supprimer
                    // #PASFAIT
                    
                    // Suppression
                    vdl.remove(VideoApercuUtilisateur.this);
                }
            }
        });
            
        popupApercu.add(item);
    }
}
