package graph;

import infos.Utilisateur;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;

import serveur.API;

/**
 * Panneau de connexion de l'application.
 */
public class Connexion extends JPanel
{
	private static final long serialVersionUID = -405177430540173124L;

	// Boit de connexion
    private Box box = new Box(BoxLayout.Y_AXIS);

    // Labels et champs de saisies
    private JLabel labelUtilisateur = new JLabel("Utilisateur:  ");
    private JLabel labelMotDePasse  = new JLabel("Mot de passe:  ");

    private JTextField jtfUtilisateur = new JTextField();
    private JPasswordField jtfMotDePasse  = new JPasswordField();
    
    // Bouton de connexion
    private JButton boutonEnvoyer = new JButton ("Se connecter");
    
    // Image de fond
    private Image image = new ImageIcon( "imgs/fond_connexion.jpg").getImage();

    // Fenêtre contenant le formulaire de connexion
    private Fenetre fenetre = null;
    
    /**
     * Constructeur par défaut.
     * Prend en argument le pointeur de la fenêtre le contenant (pour pouvoir la modifier)
     */
    public Connexion(Fenetre fenetre)
    {                  
    	this.fenetre = fenetre;
    	
        // Boite : Champs + Bouton
        box.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        box.add(Box.createVerticalGlue());
        box.add(new BoiteConnexion());
        box.add(Box.createVerticalGlue());

        this.setLayout(new BorderLayout());
        this.add("Center", box);
    }
    
    @Override
    public void paintComponent(final Graphics g) 
    {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null);    
    }
    
    class BoiteConnexion extends JPanel 
    {
		private static final long serialVersionUID = -6417145418051843474L;
		private final int LARGEUR = 280;
        private final int HAUTEUR = 120;
        private final Color couleurFond = new Color(42, 38, 25, 128);
        
        public BoiteConnexion()
        {
            Font police = new Font("Arial", Font.PLAIN, 12);
   
            // Boite non opaque
            this.setOpaque(false);
            
            // Couleurs labels
            labelMotDePasse.setForeground(Color.WHITE);
            labelUtilisateur.setForeground(Color.WHITE);

            // Définition du Champ Mot de Passe
            jtfMotDePasse.setFont(police);
            jtfMotDePasse.setPreferredSize(new Dimension(150, 20));

            // Définition du Champ Utilisateur
            jtfUtilisateur.setFont(police);
            jtfUtilisateur.setPreferredSize(new Dimension(150, 20));

            // Définition bouton envoyer
            boutonEnvoyer.setBackground(new Color(128, 128, 128, 10));
            Border border = BorderFactory.createMatteBorder(2, 2, 2, 2, Color.white);
            boutonEnvoyer.setBorder(border);
            boutonEnvoyer.setForeground(Color.white);
            
            // Création de la boite
            GridBagConstraints c = new GridBagConstraints();
            this.setLayout(new GridBagLayout());
        
            c.gridwidth = 2;
            c.fill = GridBagConstraints.HORIZONTAL;
            this.add(labelUtilisateur, c);
            
            c.gridx = 2; 
            this.add(jtfUtilisateur, c);
            
            c.insets = new Insets(5, 0, 0, 0);
            c.gridx = 0;
            c.gridy = 1; 
            this.add(labelMotDePasse, c);
            
            c.gridx = 2;
            c.gridy = 1;           
            this.add(jtfMotDePasse, c);
            
            c.gridwidth = 4;
            c.gridx = 0;
            c.gridy = 2;
            c.insets = new Insets(10, 0, 0, 0);
            this.add(boutonEnvoyer, c);
            
            boutonEnvoyer.addMouseListener(new MouseAdapter() {
            	@Override
				public void mousePressed(MouseEvent arg0) {
					try {
						@SuppressWarnings("deprecation")
						String reponse = API.postConnexion(jtfUtilisateur.getText(), jtfMotDePasse.getText());
					
						if(reponse.compareTo("-1") == 0) {
							String message = "Mot de passe ou pseudo incorrects";
	                        JOptionPane.showMessageDialog(null, message, "Attention", JOptionPane.WARNING_MESSAGE);
						}
						else {
							// Maj des infos utilisateur + chargement pour la fenêtre du container
							Utilisateur.GetInstance().setPseudo(jtfUtilisateur.getText());
							Utilisateur.GetInstance().setId(Integer.parseInt(reponse));
							fenetre.creerMenuBar();
							fenetre.majContainerPrincipal();
						}
					} catch (Exception e) {
						System.out.println(e.getStackTrace());
					}
				} 
			});
        }
                
        @Override
        public Dimension getMinimumSize() 
        {
            return new Dimension(LARGEUR, HAUTEUR);
        }

        @Override
        public Dimension getMaximumSize() 
        {
            return new Dimension(LARGEUR, HAUTEUR);
        }

        @Override
        public Dimension getPreferredSize() 
        {
            return new Dimension(LARGEUR, HAUTEUR);
        }
        
        @Override
        public void paintComponent(Graphics g) 
        {
            g.setColor(couleurFond);
            Rectangle r = g.getClipBounds();
            g.fillRect(r.x, r.y, r.width, r.height);
            super.paintComponent(g);
        }
    }
}