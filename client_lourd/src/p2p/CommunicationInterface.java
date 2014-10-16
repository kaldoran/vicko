package p2p;

public interface CommunicationInterface {
	/**
	 * Envoie un message au destinataire
	 * @param message Le message à envoyer
	 */
	public abstract void envoieMessage(Message message);
	
	/**
	 * Une communication doit forcément pouvoir recevoir des messages
	 * @param message Le message reçu
	 */
	public void recoitMessage(Message message);
	
	/**
	 * Chaque communication doit être encapsulée dans un Thread, il nous faut définir une méthode run.
	 */
	public abstract void run();
}
