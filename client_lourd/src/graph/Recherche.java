package graph;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Panneau de recherche, affiche une barre de recherche + une liste de vidéos std.
 */
public class Recherche extends JPanel
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 7619370439371056274L;

	// Champ de recherche 
    private JTextField champRecherche = new JTextField();
        
    // Boutton d'envoi de requête
    private JButton boutonRecherche = new JButton("Rechercher");
    
    // Options d'affichage
    @SuppressWarnings("rawtypes")
	private JComboBox optAffichage;
    
    // Label du choix d'affichage
    private JLabel labelOptAffichage  = new JLabel("Trier par: ");
    
    // Liste de vidéos
    private JPanel liste = new VideoListeStd();
    
    /**
     * Constructeur par défaut.
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public Recherche()
    {
        Font police = new Font("Arial", Font.PLAIN, 12);
      
        // Définition du Champ de recherche
        champRecherche.setFont(police);
        champRecherche.setPreferredSize(new Dimension(300, 26));
        
        // Création de la liste de choix
        Object[] lAffichage = new Object[]{"Popularité +", "Popularité -", "Plus récent", "Moins récent", "Auteur"};
                        
        // Création des options d'affichage
        optAffichage = new JComboBox(lAffichage);
                
        // Couleur options
        optAffichage.setBackground(Color.white);
                
        // Action du bouton de recherche
        boutonRecherche.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if(liste != null)
                    Recherche.this.remove(liste);
                
                String txt = champRecherche.getText();

                switch(optAffichage.getSelectedIndex())
                {
                    case 0:
                        liste = GenerateurVideoListe.getVideoListe(GenerateurVideoListe.STD, "p" + txt);
                        break;
                    case 1:
                        liste = GenerateurVideoListe.getVideoListe(GenerateurVideoListe.STD, "m" + txt);
                        break;
                    case 2:
                        liste = GenerateurVideoListe.getVideoListe(GenerateurVideoListe.STD, "r" + txt);
                        break;
                    case 3:
                        liste = GenerateurVideoListe.getVideoListe(GenerateurVideoListe.STD, "n" + txt);
                        break;
                    case 4:
                    	liste = GenerateurVideoListe.getVideoListe(GenerateurVideoListe.STD, ":" + txt);
                        break;
                }
                
                if(liste != null)
                    Recherche.this.add(liste);
                
                liste.revalidate();
                liste.repaint();
            }
        });
                
        // Ajout de la barre de recherche
        this.add(champRecherche);
        this.add(boutonRecherche);
        
        this.add(labelOptAffichage);
        this.add(optAffichage);
        
        liste = GenerateurVideoListe.getVideoListe(GenerateurVideoListe.STD, "p");
        this.add(liste);
    }
    
    /**
     * Force une recherche par pseudo.
     * @param pseudo Auteur des vidéos
     */
    public void rechercherParPseudo(String pseudo)
    {
        champRecherche.setText(":" + pseudo);
        
        if(liste != null)
            Recherche.this.remove(liste);
        
        liste = GenerateurVideoListe.getVideoListe(GenerateurVideoListe.STD, ":" + pseudo);
        
        if(liste != null)
            Recherche.this.add(liste);
    }
}
