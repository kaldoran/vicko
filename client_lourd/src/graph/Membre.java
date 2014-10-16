package graph;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.Border;

/**
 * Les informations et apparences d'un membre.
 */
public class Membre extends JPanel
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -216294610050046060L;

	/**
     * Homme.
     */
    public static final int HOMME = 0;
    
    /**
     * Femme.
     */
    public static final int FEMME = 1;
    
    // Attributs d'un membre
    private JLabel pseudo = new JLabel();
    private JTextField description = new JTextField();
    private JLabel sexe = new JLabel();
    private JLabel nb_articles = new JLabel();
    private JLabel nb_commentaires = new JLabel();
    private JLabel date_inscription = new JLabel();
    private JLabel email = new JLabel();

    // Etiquettes
    private final JLabel labelPseudo = new JLabel("Pseudo: ");
    private final JLabel labelDescription = new JLabel("Description:");
    private final JLabel labelSexe = new JLabel("Sexe: ");
    private final JLabel labelNb_articles = new JLabel("Nb. articles: ");
    private final JLabel labelNb_commentaires = new JLabel("Nb. commentaires: ");
    private final JLabel labelDate_inscription = new JLabel("Date d'inscription: ");
    private final JLabel labelEmail = new JLabel("E-mail: ");

    // Marges
    private static final Insets INSETS_GAUCHE = new Insets(7, 0, 7, 7);
    private static final Insets INSETS_DROITE = new Insets(7, 7, 7, 0);
   
    // Boutons
    private JButton boutonDemande = new JButton("Demande ami");
    private JButton boutonPlaylist = new JButton("Voir Playlist");
    private JButton boutonExport = new JButton("Exporter la playlist");

    // JTabbedPane englobant
    private static JTabbedPane tabbedPane;

    // Objet Rechercher
    private Recherche recherche;
            
    // Construit des gbc de bonnes tailles
    private GridBagConstraints gbc(int x, int y) 
    {
        GridBagConstraints gbc = new GridBagConstraints();
      
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        
        gbc.gridx = x;
        gbc.gridy = y;
      
        gbc.insets = x == 0 ? INSETS_GAUCHE : INSETS_DROITE;
        gbc.anchor = x == 0 ? GridBagConstraints.WEST : GridBagConstraints.EAST;
        gbc.fill   = x == 0 ? GridBagConstraints.BOTH : GridBagConstraints.HORIZONTAL;

        gbc.weightx = x == 0 ? 0 : 1;
        gbc.weighty = 1;

      return gbc;
    }
       
    // Surcharge de la fonction précédente, permet de gérer le nombre de cellules
    private GridBagConstraints gbc(int x, int y, int largeur, int hauteur)
    {
        GridBagConstraints gbc = gbc(x, y);
        gbc.gridwidth = largeur;
        gbc.gridheight = hauteur;

        return gbc;
    }

    /**
     * Constructeur d'un membre.
     * @param recherche Panel Recherche de l'application
     */
    public Membre(JTabbedPane tabbed, Recherche recherche)
    {
        this.recherche = recherche;
        tabbedPane = tabbed;
        
        Border border = BorderFactory.createTitledBorder("Informations du profil");

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBorder(border);

        // Pseudo
        panel.add(labelPseudo, gbc(0, 0));    
        panel.add(pseudo, gbc(1, 0));    
        
        // Description
        panel.add(labelDescription, gbc(0, 1));            
        description.setPreferredSize(new Dimension(250, 100));
        description.setEditable(false);
        panel.add(description, gbc(0, 2, 2, 3));    
        
        // Sexe
        panel.add(labelSexe, gbc(2, 0));
        panel.add(sexe, gbc(3, 0));
        
        // Nombre d'articles
        panel.add(labelNb_articles, gbc(2, 1));
        panel.add(nb_articles, gbc(3, 1));

        // Nombre de commentaires
        panel.add(labelNb_commentaires, gbc(2, 2));
        panel.add(nb_commentaires, gbc(3, 2));
        
        // Date d'inscription
        panel.add(labelDate_inscription, gbc(2, 3));
        panel.add(date_inscription, gbc(3, 3));

        // E-mail
        panel.add(labelEmail, gbc(2, 4));
        panel.add(email, gbc(3, 4));
        
        // Ajouts dans le panel
        panel.setPreferredSize(new Dimension(Fenetre.LARGEUR - 20, 200));
        this.add(panel);

        // Boutons
        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        
        // panel.add(boutonDemande, gbc(0, 0));
        panel.add(boutonPlaylist, gbc(0, 0, 2, 1));
        panel.add(boutonExport, gbc(0, 1, 2, 1));

        // Action des boutons
//        boutonDemande.addActionListener(new ActionListener() 
//        {
//            public void actionPerformed(ActionEvent e) 
//            {
//                // #PASFAIT
//                // #TUPEUXTJRSATTENDRE
//                // On demande au serveur d'être ami
//            }
//        });
        
        boutonPlaylist.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                // On change la playlist de l'onglet Recherche
                Membre.this.recherche.rechercherParPseudo(pseudo.getText());
                
                // On change d'onglet
                Membre.tabbedPane.setSelectedIndex(ContainerP.INDEX_RECHERCHE);
            }
        });
        
        boutonExport.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                // #PASFAIT
                
                // On demande au serveur d'exporter la liste
            }
        });
                
        this.add(panel);
        
        // On désactive les boutons
        boutonDemande.setEnabled(false);
        boutonExport.setEnabled(false);
        boutonPlaylist.setEnabled(false);
    }
    
    /**
     * Définir le pseudo du membre.
     * @param pseudo Pseudo à appliquer
     */
    public void setMembre(String pseudo)
    {
        GenerateurMembre.setMembre(this, pseudo);
    }
    
    /**
     * Définir toutes les infos d'un membre.
     * @param pseudo Pseudo du membre
     * @param description Description du membre
     * @param sexe Sexe du membre
     * @param nb_articles Nombe d'articles postés par le membre
     * @param nb_commentaires Nombre de commentaires du membre
     * @param date_inscription Date d'inscription du membre
     * @param email Email du membre
     */
    public void setMembre(String pseudo, String description, int sexe, int nb_articles, int nb_commentaires,
                          String date_inscription, String email)
    {
        // Attributs
        this.pseudo.setText(pseudo);
        this.description.setText(description);
        this.sexe.setText(sexe == HOMME ? "M" : "F");
        this.sexe.setForeground(sexe == HOMME ? Color.blue : Color.pink);
        this.nb_articles.setText(Integer.toString(nb_articles));
        this.nb_commentaires.setText(Integer.toString(nb_commentaires));
        this.date_inscription.setText(date_inscription);
        this.email.setText(email);
        
        // On active les boutons
        boutonDemande.setEnabled(true);
        boutonExport.setEnabled(true);
        boutonPlaylist.setEnabled(true);
    }
}
