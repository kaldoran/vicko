package p2p;


public class Ami {
	/** Adresse ip de l'ami (null si non connecté) */
	private String ip;
	/** Pseudo de l'ami */
	private final String pseudo;
	/** Ami connecté ? */
	private boolean connecte;
	/** À chaque ami peut-être rattaché une communication (directe ou via le serveur) */
	private Communication communication;
	/** Id de la base de donnée du serveur du membre */
	private int id;
	
	
	/**
	 * Constructeur d'un ami. Sans ip, donc positionne directement en non connecté
	 * @param id L'id de la bdd du serveur de l'ami
	 * @param pseudo Le pseudo de l'ami
	 */
	Ami (int id, String pseudo) {
		this(id, pseudo, null);
	}
	
	/**
	 * Constructeur d'un ami
	 * @param id L'id de la bdd du serveur de l'ami
	 * @param pseudo Le pseudo de l'ami
	 * @param ip L'ip de l'ami (null si non connecté)
	 */
	Ami (int id, String pseudo, String ip) {
		this.id = id;
		this.pseudo = pseudo;
		this.ip = ip;
		
		communication = null;
		
		// Si pas d'ip de précisé, alors l'ami est non connecté
		if(ip == null || ip == "")
			connecte = false;
		else
			connecte = true;
	}

	/**
	 * Retourne l'adresse IP de l'ami
	 * @return L'adresse IP de l'ami
	 */
	public String getIp() {
		return ip;
	}
	
	/**
	 * Retourne le pseudo de l'ami
	 * @return Le pseudo de l'ami
	 */
	public String getPseudo() {
		return this.pseudo;
	}
	
	/**
	 * Retourne l'id de l'ami
	 * @return L'id de l'ami
	 */
	public int getId() {
		return this.id;
	}
	
	/**
	 * Spécifie si un ami est connecté ou non
	 * @param connecte Vrai si connecté, faux sinon
	 */
	public void setConnecte(boolean connecte) {
		this.connecte = connecte;
	}
	
	/**
	 * Retourne si l'ami est connecté ou non
	 * @return Vrai si l'ami est connecté, faux sinon
	 */
	public boolean estConnecte() {
		return this.connecte;
	}
	
	/**
	 * Récupère le canal de communication avec l'ami
	 * @return La communication avec l'ami
	 */
	public Communication getCommunication() {
		return this.communication;
	}
	
	/**
	 * Spécifie le canal de communication de l'ami
	 * @param communication La communication partagée avec l'ami
	 */
	public void setCommunication(Communication communication) {
		this.communication = communication;
	}
	
	/**
	 * Retourne le nom et le prénom de l'ami dans un String
	 */
	public String toString() {
		return this.pseudo;
	}
	
	
}
