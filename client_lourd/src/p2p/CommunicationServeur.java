package p2p;

import infos.Utilisateur;
import serveur.API;

public class CommunicationServeur extends Communication {
	/**
	 * Constructeur, initialise la communication avec qui on veut discuter
	 * @param ami L'ami avec lequel on veut discuter
	 */
	public CommunicationServeur(Ami ami) {
		this.id_ami = ami.getId();
		messages = new ListeMessage();
	}
	
	/**
	 * Lorsqu'on démarre la communication dans un thread
	 */
	public void run() {
	}
	
	
	/**
	 * Envoie un message via la socket
	 * @param message Le message à envoyer
	 */
	public void envoieMessage(Message message) {
		messages.ajouteMessageSortant(message);
		
		API.postMessage(Utilisateur.GetInstance().getId(), id_ami, message.getText());
	}
	
	/**
	 * Ferme la socket de communication
	 */
	protected void finalize() throws Throwable {
		super.finalize();
	}
}
