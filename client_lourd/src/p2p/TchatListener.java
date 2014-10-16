package p2p;

public interface TchatListener {
	/**
	 * Écouteur sur une potentielle nouvelle communication
	 * @param communication La nouvelle communication
	 */
	void nouvelleCommunication(Communication communication);
}
