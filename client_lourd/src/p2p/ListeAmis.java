package p2p;

import java.util.ArrayList;

public class ListeAmis {
	// Liste des amis
	private ArrayList<Ami> amis = null;
	// Permet de mettre à jour la liste des amis via le serveur
	private AmisDAO amisDAO;

	/**
	 * Constructeur, initialise la liste des amis a null
	 */
	public ListeAmis() {
		this(new ArrayList<Ami>());
	}
	
	/**
	 * Constructeur, intialise la liste des amis via l'argument. 
	 * Aggrégation faible.
	 * @param amis
	 */
	public ListeAmis(ArrayList<Ami> amis) {
		amisDAO = new AmisDAO();
		this.amis = amis;
	}
	
	/**
	 * Retourne la liste des amis connectés
	 * @return Une liste des amis connectés
	 */
	public ArrayList<Ami> getConnectes(boolean est_connecte) {
		ArrayList<Ami> amis = new ArrayList<Ami>();
		
		for (Ami ami : amis) {
			if (ami.estConnecte() == est_connecte)
				amis.add(ami);
		}
		
		return amis;
	}
	
	/**
	 * Retourne la liste des amis
	 * @return Une liste contenant tous les amis
	 */
	public ArrayList<Ami> getAmis() {
		return this.amis;
	}
	
	/**
	 * Retourne l'ami correspondant à l'adresse IP spécifiée
	 * @param ip L'adresse ip de l'ami à chercher
	 * @return L'ami trouvé
	 * @throws Exception Levée si aucun ami avec l'adresse ip spécifiée n'a été trouvé
	 */
	public Ami getAmiByIp(String ip) throws Exception {
		// On cherche l'ami correspondant à notre adresse ip
		for (Ami ami : amis) {
			if (ami.estConnecte() && ami.getIp().equals(ip))
				return ami;
		}
		
		// On lève une exception si on a pas trouvé d'ami
		throw new Exception("Aucun ami trouvé avec l'adresse ip : " + ip.toString());
	}
	
	/**
	 * Recherche l'ami ayant un certain id
	 * @param id L'id désiré de l'ami
	 * @return L'ami ayant l'id désiré
	 * @throws Exception Levée si aucun ami trouvé
	 */
	public Ami getAmiById(int id) throws Exception {
		// On cherche l'ami correspondant à notre adresse ip
		for (Ami ami : amis) {
			if(ami.getId() == id)
				return ami;
		}
		
		// On lève une exception si on a pas trouvé d'ami
		throw new Exception("Aucun ami trouvé avec cet id : " + id);
	}
	
	
	public Ami getAmiByPseudo(String pseudo) throws Exception {
		// On cherche l'ami correspondant à notre adresse ip
		for (Ami ami : amis) {
			if(ami.getPseudo().compareTo(pseudo) == 0)
				return ami;
		}
		
		// On lève une exception si on a pas trouvé d'ami
		throw new Exception("Aucun ami trouvé avec ce pseudo : " + pseudo);
	}
	
	/**
	 * Met à jour la liste des amis via le serveur
	 */
	public void maj() {
		this.amis = amisDAO.getListeAmis();
	}
}
