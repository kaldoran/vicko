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
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import serveur.API;

public class AjouterCommentaire extends JDialog {

	private static final long serialVersionUID = 5564098710559605756L;
	
	private JTextArea commentaire = new JTextArea();
	private JButton boutonEnvoyer = new JButton("Envoyer");
	
	public AjouterCommentaire (final Integer id_video)
	{
		super();
		this.setTitle("Entrez votre commentaire");
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
      
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
              
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill   = GridBagConstraints.BOTH;

        gbc.weightx = 1;
        gbc.weighty = 1;

        commentaire.setPreferredSize(new Dimension(200, 100));
        commentaire.setMinimumSize(new Dimension(200, 100));
        commentaire.setLineWrap(true);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 6;
        gbc.gridheight = 6;
        this.add(commentaire, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridheight = 1;
        this.add(boutonEnvoyer, gbc);
        
        boutonEnvoyer.addMouseListener(new MouseAdapter() 
        {
        	@Override
            public void mouseClicked(MouseEvent e)
            {
        		if (commentaire.getText() == null)
        		{
        			String message = "Merci de ne pas laisser de champs vides !";
                    JOptionPane.showMessageDialog(null, message, "Attention", JOptionPane.WARNING_MESSAGE);
        		} else {
        			Utilisateur utl = Utilisateur.GetInstance();
        			API.sendCommentaire(Integer.toString(id_video), Integer.toString(utl.getId()), commentaire.getText());
        			dispose();
        		}
            }
        });
        
        this.pack();
        this.setVisible(true);
        
	}
}
