package p2p;

import infos.Utilisateur;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import serveur.API;
import serveur.IP;


public class Tchat implements Runnable {
	// Port fixé pour la communication entre clients
	final static int PORT = 4242;
	
	// Socket du serveur
	private ServerSocket serveurSocket = null;
	// Si vrai alors on attend n'importe quelle connexion
	private boolean attenteConnexion = true;
	// Liste des amis
	private ListeAmis amis = null;
	// Listener permettant de déclencher des évènements
	private TchatListener tchatListener;
	// Liste contenant toutes les communications du tchat
	private ArrayList<Communication> communications = new ArrayList<Communication>();
	
	// DAO permettant de gérer les messages différés du serveur
	private MessagesDAO messagesDAO;
	
	/**
	 * Constructeur, intialise la socket sur le port défini en constante interne et accepte des nouvelles connexions
	 */
	public Tchat () {
		messagesDAO = new MessagesDAO();
		amis = new ListeAmis();
		
		// On crée la socket du serveur sur le port spécifié
		try {
			serveurSocket = new ServerSocket(PORT);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Maj de l'adresse ip locale
		try {
			Utilisateur.GetInstance().setLocalIp(IP.getLocalIp());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
		// Maj de l'adresse ip publique
		try {
			Utilisateur.GetInstance().setPubliqueIp(IP.getPublicIp());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Lorsque le Thread se met en route, ce dernier se met en attente de connexion et crée les nouvelles communications
	 */
	public void run() {
		// On précise au serveur que l'on est connecté et qu'on attend des connexions
		this.majLocalConnecte(1);
		
		// Tant qu'on veut attendre une connexion
		while(attenteConnexion) {
			Socket socket = null;
			
			// On accepte une connexion
			try {
				socket = serveurSocket.accept();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			// On crée notre communication avec l'ami qui nous a contacté
			try {
				this.nouvelleCommunication(amis.getAmiByIp(socket.getInetAddress().getHostAddress().toString()), socket);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		// On précise au serveur que l'on est plus connecté et que l'on attend plus de connexions
		this.majLocalConnecte(0);
	}
	
	/**
	 * Crée une nouvelle communication avec un ami
	 * @param ami L'ami avec qui créer la nouvelle communication
	 */
	public Communication creerCommunication(Ami ami) {
		Communication c = null;
		
		// Si l'ami est connecté, on crée une communication directe (p2p) avec une socket si il n'en a pas déjà une
		if(ami.estConnecte()) {
			if(ami.getCommunication() == null || ami.getCommunication() instanceof CommunicationServeur) {
				Socket socket = null;
				
				try {
					socket = new Socket(InetAddress.getByName(ami.getIp()), PORT);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				c = this.nouvelleCommunication(ami, socket);
			}
			else {
				c = ami.getCommunication();
			}
		}
		// Sinon on communique avec lui via le serveur si il n'a pas déjà un communication avec lui
		else {
			if(ami.getCommunication() == null || ami.getCommunication() instanceof CommunicationSocket) {
				
				c = this.nouvelleCommunication(ami);
			}
			else {
				c = ami.getCommunication();
			}
		}
		
		return c;
	}
	
	/**
	 * Renvoie la liste des amis (après mise à jour)
	 * @return La liste des amis
	 */
	public ListeAmis getAmis() {
		amis.maj();
		
		return this.amis;
	}
	
	/**
	 * Récupère toutes les communications du tchat
	 * @return Java List contenant les communications
	 */
	public ArrayList<Communication> getCommunications() {
		return this.communications;
	}
	
	/**
	 * Méthode permettant d'associer un listener
	 * @param l Le listener
	 */
	public void onStateRealized(TchatListener l)
	{
		this.tchatListener = l;
	}
	
	
	/**
	 * Crée une nouvelle communication avec un ami hors ligne (via le serveur donc)
	 * @param amis_hors_ligne
	 */
	private Communication nouvelleCommunication(Ami amis_hors_ligne) {
		// Création de la communication, ajout dans notre liste puis action sur le déclencheur
		Communication c = new CommunicationServeur(amis_hors_ligne);
		communications.add(c);
		this.tchatListener.nouvelleCommunication(c);
		
		// On spécifie que cet ami a une nouvelle communication
		amis_hors_ligne.setCommunication(c);
		
		// On fait met en route notre communication
		Thread t = new Thread(c);
		t.start();
		
		return c;
	}
	
	/**
	 * Méthode créant une nouvelle communication à partir d'une socket.
	 * Déclenche un évènement contenant le nouveau thread créé.
	 * @param ami L'ami avec qui créer la communication
	 * @param s Socket avec laquelle créer une nouvelle communication
	 */
	private Communication nouvelleCommunication(Ami ami, Socket s) {
		// Création de la communication, ajout dans notre liste puis action sur le déclencheur
		Communication c = new CommunicationSocket(s);
		communications.add(c);
		this.tchatListener.nouvelleCommunication(c);
		
		// On spécifie que cet ami a une nouvelle communication
		ami.setCommunication(c);
		
		// On fait met en route notre communication
		Thread t = new Thread(c);
		t.start();
		
		return c;
	}
	
	/**
	 * Renvoie au serveur si le client local est connecté ou non (en attente de connexion)
	 */
	private void majLocalConnecte (int connecte) {
		API.postAmiP2P(connecte);
	}
	
	/**
	 * Reçoit les messages différés destinés à notre utilisateurs et met à jour les communications de la liste des amis
	 */
	public void majMessagesDifferes () {
		messagesDAO.getDifferes(Utilisateur.GetInstance().getId(), amis);
	}
	
	
	/**
	 * Ferme la socket d'écoute du serveur
	 */
	protected void finalize() throws Throwable {
		super.finalize();
		
		// On précise au serveur que l'on est plus connecté et que l'on attend plus de connexions
		this.majLocalConnecte(0);
		
		serveurSocket.close();
	}
}
