package graph;

import infos.Utilisateur;

import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import p2p.Ami;
import p2p.CommunicationListener;
import p2p.ListeAmis;
import p2p.Message;
import p2p.Tchat;
import p2p.TchatListener;
import p2p.Communication;

public class Chat extends JPanel
{
    // Sérial par défaut
    private static final long serialVersionUID = 7586933709906927958L;

    // Zone d'écriture
    private final JTextArea textArea = new JTextArea(5, 100);
    
    // Messages
    private final JTextArea messages = new JTextArea();
    
    // Amis connectés
    private final JList<String> listeAmis = new JList<String>();
    // Liste des objets amis
    ListeAmis amis = null;

    // Status
    private final StatusBar statusBar = new StatusBar();
    
    // Objet du tchat
    private final Tchat tchat;
    
    // Communication qui est actuellement affiché
    private Communication communicationCourante = null;
    
    public Chat()
    {
        // Création d'ascenceurs
        JScrollPane ascMessages = new JScrollPane(messages);
        JScrollPane ascTextArea = new JScrollPane(textArea);
        JScrollPane ascListeAmis = new JScrollPane(listeAmis);

        // Taille des ascenceurs
        ascMessages.setPreferredSize(new Dimension(400, 330));
        ascTextArea.setPreferredSize(new Dimension(626, 80));
        ascListeAmis.setPreferredSize(new Dimension(170, 330));

        // Saut de lignes automatique pour les JTextArea
        messages.setLineWrap(true); 
        textArea.setLineWrap(true); 

        // Interdiction d'écriture dans la zone de réception des messages
        messages.setEditable(false);
        
        // StatusBar : Message
        statusBar.setTexte("Date du dernier message reçu: Aucun.");
        
        // Ajout des éléments
        add(ascMessages);
        add(ascListeAmis);
        add(ascTextArea);
        add(statusBar);
        
        // On lance le tchat
        tchat = new Tchat();
        new Thread(tchat).start();
        
        // Évènements de Tchat
        tchat.onStateRealized(new TchatListener() {
			public void nouvelleCommunication(Communication communication) {
				majCommunicationCourante(communication);
				
				majMessages();
			}
		});
        
        
        amis = tchat.getAmis();
        this.majAmis();
   
        // On reçoit les messages différés
     	tchat.majMessagesDifferes();
     		
        
        textArea.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent key) {
				if (key.getKeyCode() == KeyEvent.VK_ENTER) {
					if(communicationCourante == null)
						return;
								
					communicationCourante.envoieMessage(new Message(textArea.getText()));
					majMessages();
				}
			}
			
			public void keyReleased(KeyEvent key) {
				if (key.getKeyCode() == KeyEvent.VK_ENTER) {
					if(communicationCourante == null)
						return;
					
					textArea.setText("");
				}
			}
		});
    }
    
    /**
     * Met à jour la communication courante en rappliquant un écouteur dessus
     * @param c La nouvelle communication courante
     */
    private void majCommunicationCourante(Communication c) {
    	communicationCourante = c;
    	
    	c.onStateRealized(new CommunicationListener() {
			public void nouveauMessageRecu(Communication communication) {
				majMessages();
			}
		});
    	
    	majMessages();
    }
    
    /**
     * Met à jour le panneau des messages selon la communication courante
     */
    private void majMessages () {
    	messages.setText(communicationCourante.getListeMessage().toString());
    }
    
    /**
     * Met à jour le panneau des amis connectés
     */
    private void majAmis () {
    	// Récupération du nombre d'amis dans notre liste
    	int size = amis.getAmis().size();
    	// Création du tableau de String qui servira pour afficher les pseudos dans la JList
    	String[] amis_texte = new String[size];
    	
    	// Parcours de tous les amis pour ajouter leurs pseudo
    	for(int i=0; i<size; i++) {
    		Ami ami = amis.getAmis().get(i);
    		
    		if(ami.getPseudo().toLowerCase().compareTo(Utilisateur.GetInstance().getPseudo().toLowerCase()) != 0)
    			amis_texte[i] = ami.toString();
    	}
    	
    	// On affecte la nouvelle liste de pseudos à notre JList
    	listeAmis.setListData(amis_texte);
    	
    	// Ajout d'un listener pour écouter lorsque l'utilisateur change d'ami avec lequel il veut discuter
    	listeAmis.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				Ami ami_desire = null;
				
				// Récupération de l'objet de l'ami selon son pseudo
				try {
					ami_desire = amis.getAmiByPseudo((String) listeAmis.getSelectedValue());
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
				// On crée une nouvelle communication avec cet ami
				majCommunicationCourante(tchat.creerCommunication(ami_desire));
			}
		});
    }
}
