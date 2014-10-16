package graph;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;

abstract public class VideoApercu extends JPanel
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 7633716820569103283L;

	/**
     * Titre de la vidéo.
     */
    public final String TITRE;
    
    /**
     * Auteur de la vidéo.
     */
    public final String AUTEUR;

    /**
     * Date d'upload de la vidéo.
     */
    public final String DATE;
    
    /**
     * Id de la vidéo.
     */
    public final int id;
    
    // Taille des zones de miniatures
    public final static int LARGEUR = 199;
    public final static int HAUTEUR = 168;
    
    public final static int LARGEUR_IMAGE = 185;
    public final static int HAUTEUR_IMAGE = 104;
    
    // JLabels de l'aperçu
    protected JLabel labelTitre; 
    protected JLabel labelAuteur;
    protected JLabel labelDate;
    
    // Image
    protected JPanelImg image;
    
    // Onglets de l'application
    static protected JTabbedPane tabbedPane = ContainerP.tabbedPane;
    
    /**
     * Constructeur par défaut d'un aperçu.
     * @param id Id de la vidéo.
     * @param titre Titre de la vidéo.
     * @param auteur Auteur de la vidéo.
     * @param date Date d'upload de la vidéo.
     * @param img Chemin vers l'Image/Miniature de la vidéo.
     */
    public VideoApercu(int id, String titre, String auteur, String date, String img) 
    {                
        // Attributs de la miniature
        this.TITRE = (titre.length() >= 25) ? titre.substring(0, 20) + "..." : titre;
        
        this.AUTEUR = auteur;
        this.DATE = date;
             
        this.id = id;
        
        // Grille
        this.setLayout(new GridBagLayout());
        
        // Taille d'une miniature
        this.setSize(LARGEUR, HAUTEUR);
        this.setPreferredSize(new Dimension(LARGEUR, HAUTEUR));
        this.setMinimumSize(new Dimension(LARGEUR, HAUTEUR));
        
        // Couleur du container
        this.setBackground(new Color(230, 230, 230));
        
        // Contraintes du container
        GridBagConstraints gbc = new GridBagConstraints();
 
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(-1, 2, 2, 2);
       
        // Padding et cadre de l'image
        image = new JPanelImg(img, LARGEUR_IMAGE, HAUTEUR_IMAGE);
        image.setBorder(new EmptyBorder(0, 0, 4, 0));
        image.setBackground(new Color(230, 230, 230));
        
        // Couleurs des titres, auteur et date
        labelTitre = new JLabel(this.TITRE);
        labelAuteur = new JLabel(this.AUTEUR);
        labelDate = new JLabel(this.DATE);
        
        labelAuteur.setForeground(new Color(0, 128, 128));
        labelDate.setForeground(new Color(0, 0, 0, 128));

        // Ajout des valeurs
        this.add(image);
        this.add(labelTitre, gbc);
        this.add(labelDate, gbc);
        this.add(labelAuteur, gbc);
    }
    
    /**
     * Action d'un click sur l'image.
     */
    abstract protected void clickImage();
    
    /**
     * Action d'un click sur l'auteur.
     */
    abstract protected void clickAuteur();
    
    /**
     * Action d'un click sur le titre.
     */
    abstract protected void clickTitre();
    
    /** 
     * Curseur à afficher en cas de sélection d'un élément.
     * @param component Element à modifier.
     */
    protected void changerCurseur(Component component)
    {
        component.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }
    
    /** 
     * Changer l'alpha d'un élément.
     * @param component Element à modifier.
     * @param alpha Alpha.
     */
    protected void changerAlpha(Component component, int alpha)
    {
        Color couleur = component.getForeground();
        component.setForeground(new Color(couleur.getRed(), couleur.getGreen(), couleur.getBlue(), alpha));        
    }
    
    /**
     * Se rendre sur une vidéo.
     */
    protected void allerSurVideo()
    {
        // Charger la vidéo correspondante
        ((Video)VideoApercu.tabbedPane.getComponentAt(ContainerP.INDEX_VIDEO_EN_COURS)).setVideo(id);
        
        // Changer d'onglet
        tabbedPane.setSelectedIndex(ContainerP.INDEX_VIDEO_EN_COURS);
    }
}
