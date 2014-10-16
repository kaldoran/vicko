package p2p;

import java.util.Date;

/**
 * Patron d'un message
 *
 */
public interface MessageInterface {
	/**
	 * On doit pouvoir récupérer le texte d'un message
	 * @return Le texte du message
	 */
	public String getText();
	
	/**
	 * On doit pouvoir récupérer la date du message
	 * @return La date du message
	 */
	public Date getDate();
	
	/**
	 * On doit pouvoir directement afficher proprement le message
	 * @return Le message affiché proprement
	 */
	public String toString();
}
