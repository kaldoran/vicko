package p2p;

public interface CommunicationListener {
	/**
	 * Déclencheur activé lorsqu'un nouveau message est reçu
	 * @param communication L'objet communication ayant reçu un nouveau message
	 */
	void nouveauMessageRecu(Communication communication);
}
