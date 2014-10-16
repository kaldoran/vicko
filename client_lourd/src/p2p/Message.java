package p2p;

import java.util.Date;

public class Message {
	String texte;
	Date date;
	
	/**
	 * Constructeur d'un message
	 * @param texte Le texte du message
	 */
	public Message(String texte) {
		this.texte = texte;
		this.date = new Date();
	}
	
	/**
	 * Constructeur d'un message (généralement différé)
	 * @param texte Le texte du message
	 * @param date La date du message
	 */
	public Message(String texte, Date date) {
		this.texte = texte;
		this.date = date;
	}
	
	/**
	 * Récupérer le texte du message
	 * @return Le texte du message
	 */
	public String getText() {
		return this.texte;
	}
	
	/**
	 * Récupérer la date du message
	 * @return La date du message
	 */
	public Date getDate() {
		return this.date;
	}
	
	/**
	 * Afficher le message
	 */
	public String toString() {
		return date + " : " + texte;
	}

}
