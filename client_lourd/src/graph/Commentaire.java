package graph;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * Un Commentaire d'une vidéo.
 */
public class Commentaire extends JPanel
{

	private static final long serialVersionUID = -5016145546225486315L;
	
	// Attributs d'un commentaire
    private final JLabel auteur;
    private final JTextArea texte;
    private final JLabel date;
    
    /**
     * Constructeur d'un commentaire.
     * @param auteur Auteur du commentaire
     * @param texte Contenu du commentaire
     * @param date Date d'upload du commentaire
     */
    public Commentaire(String auteur, String texte, String date)
    {
        // Mise en place des attributs
        this.auteur = new JLabel(auteur);
        this.texte = new JTextArea(texte);
        this.date = new JLabel(date);
        
        // Conteneur
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Ajouts : Date et auteur
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEADING));
        
        this.auteur.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.auteur.setForeground(new Color(0, 128, 128));
        this.auteur.addMouseListener(new MouseAdapter() 
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                if(e.getButton() == MouseEvent.BUTTON1)
                {
                    // On met à jour la page de Membre
                    ContainerP.GetInstance().getMembre().setMembre(Commentaire.this.auteur.getText());

                    // On se rend sur la page de l'auteur
                    VideoApercuStd.tabbedPane.setSelectedIndex(ContainerP.INDEX_PROFIL_EN_COURS);
                }
            }
        });
        
        this.date.setForeground(new Color(0, 0, 0, 128));
        
        panel.add(new JLabel("Posté le "));
        panel.add(this.date);
        panel.add(new JLabel(" par "));
        panel.add(this.auteur);
        
        this.add(panel);
        
        // Ajout Contenu
        JScrollPane scroll = new JScrollPane(this.texte, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
                                             JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.texte.setLineWrap(true);
        this.texte.setEditable(false);
        this.texte.setPreferredSize(new Dimension(Fenetre.LARGEUR - 60, 40));
        this.texte.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        this.texte.setBackground(new Color(230, 230, 230));
        
        scroll.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        this.add(scroll);
    }
}
