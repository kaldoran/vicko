package graph;

import infos.Utilisateur;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

import serveur.API;

public class Video extends JPanel
{
	private static final long serialVersionUID = 5187084623782026033L;
	// Données d'une vidéo
    private JLabelURI lien = new JLabelURI("Exemple.", 
                                           "http://www.youtube.com/watch?v=0x2l0NgSvyM", 
                                           new Color(0, 100, 100), Color.lightGray);
    private JTextArea texte = new JTextArea("Description Exemple.");
    private JLabel date = new JLabel("23/04/2014");
    private JLabel auteur = new JLabel("Exemple Auteur");
    private JLabel nbVotes = new JLabel("1337");
	private int id = -1;
    
    // Conteneurs
    private JPanel infosUpload = new JPanel();
    private JPanel zoneVotes = new JPanel();

    // Boutons
    private JButton boutonVoteP = new JButton("Voter +");
    private JButton boutonVoteM = new JButton("Voter -");
    private JButton ecrireCommentaire = new JButton("Commenter");
    private JButton Favoris = new JButton("Favoris");
    
    // Onglets de l'application
    static protected JTabbedPane tabbedPane = ContainerP.tabbedPane;
    
    // Liste des commentaires de la vidéo
    CommentaireListe commentaires = new CommentaireListe();
        
    public Video()
    {	 
        // Paramétrages des containers
        this.setLayout(new WrapLayout(WrapLayout.LEADING));

        // Attributs
        
        // Lien
        TitledBorder border = BorderFactory.createTitledBorder("URL");
        border.setTitlePosition(TitledBorder.ABOVE_TOP);
        
        lien.setBorder(border);
        lien.setFont(new Font("Monospace", Font.PLAIN, 20));
        lien.setPreferredSize(new Dimension(Fenetre.LARGEUR - 20, 55));
        
        // Informations d'upload
        border = BorderFactory.createTitledBorder("Informations d'upload");
        border.setTitlePosition(TitledBorder.ABOVE_TOP);
     
        infosUpload.setLayout(new FlowLayout(FlowLayout.LEADING));
        infosUpload.setBorder(border);
        infosUpload.setPreferredSize(new Dimension(Fenetre.LARGEUR - 20, 48));
        infosUpload.add(new JLabel("Upload du: "));
        infosUpload.add(date);
        infosUpload.add(new JLabel(" par "));
        infosUpload.add(auteur);
        
        date.setForeground(new Color(0, 128, 128));
        auteur.setForeground(new Color(0, 128, 128));
        auteur.setCursor(new Cursor(Cursor.HAND_CURSOR));

        auteur.addMouseListener(new MouseAdapter() 
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {                
                if(e.getButton() == MouseEvent.BUTTON1)
                {
                    // On met à jour la page de Membre
                    ContainerP.GetInstance().getMembre().setMembre(auteur.getText());

                    // On se rend sur la page de l'auteur
                    VideoApercuStd.tabbedPane.setSelectedIndex(ContainerP.INDEX_PROFIL_EN_COURS);
                }
            }
        });
               
        // Description
        border = BorderFactory.createTitledBorder("Description");
        border.setTitlePosition(TitledBorder.ABOVE_TOP);
        texte.setBorder(border);
        texte.setPreferredSize(new Dimension(Fenetre.LARGEUR - 20, 70));
        texte.setEditable(false);
        texte.setBackground(new Color(0, 0, 0, 0));
        
        // Zone votes
        boutonVoteP.addMouseListener(new MouseAdapter() 
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                if(e.getButton() == MouseEvent.BUTTON1)
                {
                	Utilisateur utl = Utilisateur.GetInstance();
                	API.sendVote(Integer.toString(id), Integer.toString(utl.getId()), "1");
                	setVideo(id);
                }
            }
        });
        
        boutonVoteM.addMouseListener(new MouseAdapter() 
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                if(e.getButton() == MouseEvent.BUTTON1)
                {
                	Utilisateur utl = Utilisateur.GetInstance();
                	API.sendVote(Integer.toString(id), Integer.toString(utl.getId()), "0");
                	setVideo(id);
                }
            }
        });
        
        ecrireCommentaire.addMouseListener(new MouseAdapter() 
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                if(e.getButton() == MouseEvent.BUTTON1)
                {
                	@SuppressWarnings("unused")
					AjouterCommentaire comm = new AjouterCommentaire(id);
                	setVideo(id);
                }
            }
        });
        
        Favoris.addMouseListener(new MouseAdapter() 
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                if(e.getButton() == MouseEvent.BUTTON1)
                {
                	Utilisateur utl = Utilisateur.GetInstance();
                	API.SetInPlaylist(utl.getPseudo(), Integer.toString(id));
                }
            }
        });
        
        border = BorderFactory.createTitledBorder("Votes");
        border.setTitlePosition(TitledBorder.ABOVE_TOP);
        zoneVotes.setLayout(new FlowLayout(FlowLayout.LEADING));
        zoneVotes.setBorder(border);
        zoneVotes.setPreferredSize(new Dimension(Fenetre.LARGEUR - 20, 58));
        zoneVotes.add(new JLabel("Nombre de votes: "));
        zoneVotes.add(nbVotes);
        zoneVotes.add(boutonVoteP);
        zoneVotes.add(boutonVoteM);
        zoneVotes.add(ecrireCommentaire);
        zoneVotes.add(Favoris);
        
        nbVotes.setForeground(new Color(0, 128, 128));
        
        // Ajout des composants
        this.add(lien);
        this.add(infosUpload);
        this.add(texte);
        this.add(zoneVotes);
        this.add(commentaires);
    }
    
    /**
     * Définir une vidéo manuellement.
     * @param id Id de la vidéo
     * @param titre Titre de la vidéo
     * @param lien Lien de la vidéo
     * @param texte Description de la vidéo
     * @param date Date d'upload de la vidéo
     * @param auteur Auteur de la vidéo
     * @param nbVotes Nombre de votes de la vidéo
     */
    public void setVideo(int id, String titre, String lien, String texte, String date, String auteur, int nbVotes)
    {
        this.id = id;
        this.lien.setTitre(titre);
        this.lien.setLien(lien);
        this.texte.setText(texte);
        this.date.setText(date);
        this.auteur.setText(auteur);
        this.nbVotes.setText(String.valueOf(nbVotes));
    }
    
    public void setId(int id)
    {
        this.id = id;
    }
    
    public void setTitre(String titre)
    {
       this.lien.setTitre(titre);
    }
    
    public void setLien(String lien)
    {
       this.lien.setLien(lien);
    }
    
    public void setTexte(String texte)
    {
       this.texte.setText(texte);
    }
    
    public void setDate(String date)
    {
       this.date.setText(date);
    }
        
    public void setAuteur(String auteur)
    {
       this.auteur.setText(auteur);
    }
    
    public void setNbVotes(int nbVotes)
    {
        this.nbVotes.setText(String.valueOf(nbVotes));
    }
    
    /**
     * Met en place une liste de commentaires 
     * @param liste Liste de commentaires de la vidéo
     */
    public void setCommentaires(CommentaireListe liste)
    {
        if(commentaires != null)
        {
            this.remove(commentaires);
            commentaires = null;
        }
        
        if(liste != null)
        {
            this.add(liste);
            commentaires = liste;
        }
    }
    
    /**
     * Définir la vidéo par les données serveur ainsi que les commentaires.
     * @param id Id de la vidéo.
     */
    public void setVideo(int id)
    {
        GenerateurVideo.setVideo(this, id);
        this.revalidate();
        this.repaint();
    }
}
