package infos;

/** 
 * Singleton regroupant les informations de l'utilisateur du client
 *
 */
public class Utilisateur {
	private static Utilisateur utilisateur = null;
	private int id;
	private String pseudo;
	private String localIP, publiqueIP;
	
	/**
	 * Retourne l'instance du singleton 
	 * @return L'instance du singleton
	 */
	public static synchronized Utilisateur GetInstance() {
        if(utilisateur == null)        
        	utilisateur = new Utilisateur();
        
        return utilisateur;
    }
	
	/**
	 * Initialise à rien le pseudo, et à -1 l'id
	 */
	private Utilisateur() {
		pseudo = "";
		id = -1;
		localIP = "";
		publiqueIP = "";
	}
	
	/**
	 * Modifie l'id de l'utilisateur
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * Retourne l'id (correspondant à celui de la bdd du serveur) de l'utilisateur
	 * @return L'id de l'utilisateur
	 */
	public int getId() {
		return this.id;
	}
	
	/**
	 * Change le pseudo de l'utilisateur du client
	 * @param pseudo Le nouveau pseudo
	 */
	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}
	
	/**
	 * Retourne le pseudo de l'utilisateur
	 * @return Le pseudo de l'utilisateur
	 */
	public String getPseudo() {
		return this.pseudo;
	}
	
	/**
	 * Met à jour l'ip locale de l'utilisateur
	 * @param ip Le nouvel ip
	 */
	public void setLocalIp(String ip) {
		this.localIP = ip;
	}
	
	/**
	 * Retourne l'ip locale de l'utilisateur (null si aucune ip)
	 * @return L'ip locale de l'utilisateur
	 */
	public String getLocalIp() {
		return this.localIP;
	}
	
	
	/**
	 * Met à jour l'ip publique de l'utilisateur
	 * @param ip Le nouvel ip
	 */
	public void setPubliqueIp(String ip) {
		this.publiqueIP = ip;
	}
	
	/**
	 * Retourne l'ip publique de l'utilisateur (null si aucune ip)
	 * @return L'ip publique de l'utilisateur
	 */
	public String getPubliqueIp() {
		return this.publiqueIP;
	}
}
