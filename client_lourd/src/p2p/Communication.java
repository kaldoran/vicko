package p2p;

public abstract class Communication implements Runnable, CommunicationInterface {
	// Liste des messages
	protected ListeMessage messages = null;
	// Écouteur sur la conversation
	protected CommunicationListener communicationListener;
	// Sert à savoir à qui on envoit les messages
	protected int id_ami;
	
	/**
	 * Envoie un message
	 * @param message Le message à envoyer
	 */
	public void envoieMessage(Message message) {
		messages.ajouteMessageSortant(message);
	}
	
	/**
	 * Lorsqu'on reçoit un message
	 */
	public void recoitMessage(Message message) {
		messages.ajouteMessageEntrant(message);
	}
	
	/**
	 * Permet d'affecter une nouvelle liste de messages à la communication. 
	 * Attention ! L'ancienne sera supprimée
	 * @param messages La nouvelle liste de messages
	 */
	public void setListeMessage(ListeMessage messages) {
		this.messages = messages;
	}
	
	/**
	 * Récupère la liste des messages de la communication
	 * @return La liste des messages
	 */
	public ListeMessage getListeMessage() {
		return this.messages;
	}
	
	/**
	 * Méthode permettant d'associer un listener
	 * @param l Le listener
	 */
	public void onStateRealized(CommunicationListener l)
	{
		this.communicationListener = l;
	}
}
