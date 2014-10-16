package p2p;

import java.util.ArrayList;

public class ListeMessage {
	// Liste des messages entrants et sortants
	private ArrayList<Message> messagesEntrant = null, messagesSortant = null, messages = null;
	
	/**
	 * Constructeur, initialise les messages
	 */
	public ListeMessage() {
		messagesEntrant = new ArrayList<Message>();
		messagesSortant = new ArrayList<Message>();
		messages = new ArrayList<Message>();
	}
	
	/**
	 * Ajoute un message qui vient d'être envoyé dans la liste
	 * @param m Le message à ajouter
	 */
	public void ajouteMessageSortant(Message m) {
		messagesSortant.add(m);
		messages.add(m);
	}
	
	/**
	 * Ajoute un message qui vient d'être reçu dans la liste
	 * @param m Le message à ajouter
	 */
	public void ajouteMessageEntrant(Message m) {
		messagesEntrant.add(m);
		messages.add(m);
	}
	
	/**
	 * Récupère la liste des messages entrant
	 * @return La liste des messages entrant
	 */
	public ArrayList<Message> getMessagesEntrant() {
		return messagesEntrant;
	}

	/**
	 * Récupère la liste des messages sortant
	 * @return La liste des messages sortant
	 */
	public ArrayList<Message> getMessagesSortant() {
		return messagesSortant;
	}
	
	/**
	 * Récupère le dernier message entrant
	 * @return La dernier message
	 */
	public Message getLastMessageEntrant() {
		return messagesEntrant.get(messagesEntrant.size() - 1);
	}
	
	/**
	 * Récupère le dernier message sortant
	 * @return Le dernier message
	 */
	public Message getLastMessageSortant() {
		return messagesSortant.get(messagesSortant.size() - 1);
	}
	
	/**
	 * Récupère la liste de tous les messages (entrant et sortant, par ordre chronologique croissant)
	 * @return La liste des messages
	 */
	public ArrayList<Message> getMessages() {
		return messages;
	}
	
	/**
	 * Récupère le dernier message (entrant ou sortant)
	 * @return La dernier message de la communication
	 */
	public Message getLastMessage () {
		return messages.get(messages.size() - 1);
	}
	
	/**
	 * Retourne une chaine contenant chaque message, un par ligne.
	 */
	public String toString () {
		String retour = "";
		
		for (Message m : messages) {
			retour += m + "\n";
		}
		
		return retour;
	}
}
