package graph;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 * Un JLabel cliquable pouvant ouvrir un lien dans un navigateur.
 */
public final class JLabelURI extends JLabel
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 7340604570200467478L;

	// Lien web
    private String lien;

    // Couleurs
    private Color couleurLien;
    private Color couleurSurvol;
    
    /**
     * Constructeur par défaut.
     */
    public JLabelURI()
    {
        super();
        
        // Curseur modifié au survol
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Souris
        this.addMouseListener(new MouseAdapter() 
        {
            @Override
            public void mouseClicked(MouseEvent e) 
            {
                if(Desktop.isDesktopSupported()) 
                    try 
                    {
                        Desktop.getDesktop().browse(new URI(JLabelURI.this.lien));
                    } 
                    catch (IOException t) 
                    {
                        String message = "Impossible d'ouvrir le lien ! Système non supporté !";
                        JOptionPane.showMessageDialog(null, message, "Attention", JOptionPane.WARNING_MESSAGE);
                    } catch (URISyntaxException t) {
                        String message = "Impossible d'ouvrir le lien ! Système non supporté !";
                        JOptionPane.showMessageDialog(null, message, "Attention", JOptionPane.WARNING_MESSAGE);
					}
            }
            
            @Override
            public void mouseEntered(MouseEvent e)
            {                
                JLabelURI.this.setForeground(JLabelURI.this.couleurSurvol);
            }
            
            @Override
            public void mouseExited(MouseEvent e)
            {
                JLabelURI.this.setForeground(JLabelURI.this.couleurLien);
            }
        });
    }
    
    /**
     * Constructeur définissant toutes les infos du JLabel
     * @param titre Titre du lien
     * @param lien URL du lien
     * @param couleurLien Couleur de base du lien
     * @param couleurSurvol Couleur au survol de la souris
     */
    public JLabelURI(String titre, String lien, Color couleurLien, Color couleurSurvol)
    {
        this();
        this.setInfos(titre, lien, couleurLien, couleurSurvol);
    }    
    
    /**
     * Définir le titre du lien
     * @param titre Titre du lien
     */
    public void setTitre(String titre)
    {
        this.setText(titre);
    }
    
    /**
     * Définir l'URL
     * @param lien URL du lien
     */
    public void setLien(String lien)
    {
        this.lien = lien;   
    }
    
    /**
     * Définir les infos du JLabel
     * @param titre Titre du lien
     * @param lien URL du lien
     * @param couleurLien Couleur du lien
     * @param couleurSurvol Couleur du lien au survol de la souris
     */
    public void setInfos(String titre, String lien, Color couleurLien, Color couleurSurvol)
    {
        this.setText(titre);
        this.lien = lien;
        this.couleurLien = couleurLien;
        this.couleurSurvol = couleurSurvol;    
        this.setForeground(couleurLien);
    }
}
