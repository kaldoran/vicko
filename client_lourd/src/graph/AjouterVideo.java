package graph;

import infos.Utilisateur;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
//import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import serveur.API;

/**
 * Fenêtre permettant l'ajout de vidéos;
 */
public class AjouterVideo extends JDialog
{
	private static final long serialVersionUID = -7024528581039550965L;
	private JLabel titre = new JLabel("Titre:");
    private JLabel apercu = new JLabel("Lien Miniature:");
    private JLabel url = new JLabel("URL:");
    
    private JTextField jtfTitre = new JTextField();
    private JTextField jtfApercu = new JTextField();
    private JTextField jtfUrl = new JTextField();

    private JTextArea description = new JTextArea();
    
    private JButton boutonEnvoyer = new JButton("Envoyer");
//    private JButton boutonImage = new JButton("Miniature...");
    
    @SuppressWarnings("unused")
	private VideoListeUtilisateur vlu;
    
    /**
     * Constructeur de la fenêtre
     * @param source Composant ayant lancé la fenêtre
     * @param vlu Liste de vidéos de l'utilisateur
     */
    public AjouterVideo(JFrame source, VideoListeUtilisateur vlu)
    {
        super(source, true);
    
        this.vlu = vlu;
        
        this.setTitle("Ajouter une vidéo");
        this.setLayout(new GridBagLayout());
  
        GridBagConstraints gbc = new GridBagConstraints();
      
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
              
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill   = GridBagConstraints.BOTH;

        gbc.weightx = 1;
        gbc.weighty = 1;
        
//        jtfApercu.setEditable(false);
        
        // Taille des champs
        jtfTitre.setPreferredSize(new Dimension(200, 22));
        jtfTitre.setMinimumSize(new Dimension(200, 22));
        jtfApercu.setPreferredSize(new Dimension(200, 22));
        jtfApercu.setMinimumSize(new Dimension(200, 22));
        jtfUrl.setPreferredSize(new Dimension(200, 22));
        jtfUrl.setMinimumSize(new Dimension(200, 22));
        description.setPreferredSize(new Dimension(200, 100));
        description.setMinimumSize(new Dimension(200, 100));
        
        // Saut de ligne pour la description
        description.setLineWrap(true);
        
        // Actions des boutons
//        boutonImage.addMouseListener(new MouseAdapter() 
//        {
//        	@Override
//            public void mouseClicked(MouseEvent e)
//            {
//                String fichier;
//                JFileChooser dialogue = new JFileChooser();
//                dialogue.showOpenDialog(null);  
//                
//                if(dialogue.getSelectedFile() != null)
//                {
//                    fichier = dialogue.getSelectedFile().getPath();
//                    
//                    if(fichier.endsWith(".png") || fichier.endsWith(".jpg") || fichier.endsWith(".jpeg"))
//                        AjouterVideo.this.jtfApercu.setText(fichier);
//                    else
//                    {
//                        String message = "Merci de n'utiliser que du png, jpg ou jpeg !";
//                        JOptionPane.showMessageDialog(null, message, "Attention", JOptionPane.WARNING_MESSAGE);
//                    }
//                }
//            }
//        });
        
        boutonEnvoyer.addMouseListener(new MouseAdapter() 
        {
        	@Override
            public void mouseClicked(MouseEvent e)
            {
                if(jtfTitre.getText().isEmpty() || jtfApercu.getText().isEmpty() ||
                   jtfUrl.getText().isEmpty() || description.getText().isEmpty())
                {
                    String message = "Merci de ne pas laisser de champs vides !";
                    JOptionPane.showMessageDialog(null, message, "Attention", JOptionPane.WARNING_MESSAGE);
                }
                else
                {
                    // Envoi au serveur...
                	/*Envoi video : 
						En données POST a http://localhost:8080/web/EnvoiVideo :
							id_membre - Id de l'auteur du post
							lien - Lien vers la video Youtube !
							lien_miniature - Lien vers la miniature
							titre - titre de la video
							texte - descriptin de la video
					
						Affiche : 
							true si aucune ereur
							false sinon
                	 */
                	Utilisateur utilisateur = Utilisateur.GetInstance();
                	String id_membre = Integer.toString(utilisateur.getId());
                	String lien = jtfUrl.getText();
                	String lien_miniature = jtfApercu.getText();
                	String titre = jtfTitre.getText();
                	String texte = description.getText();
                	API.sendVideo(id_membre, lien, lien_miniature, titre, texte);
                	dispose();
                }
            }
        });
        
        // Ajout des éléments
        gbc.gridx = 0;
        gbc.gridy = 0;
        this.add(titre, gbc);
        
        gbc.gridx = 1;
        this.add(jtfTitre, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        this.add(apercu, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 1;
        this.add(jtfApercu, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        this.add(url, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 2;
        this.add(jtfUrl, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.gridheight = 6;
        this.add(description, gbc);
        
//        gbc.gridy = 9;
//        gbc.gridwidth = 1;
//        gbc.gridheight = 1;
//        this.add(boutonImage, gbc);

        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        this.add(boutonEnvoyer, gbc);

        this.pack();
        this.setVisible(true);
    }
}
