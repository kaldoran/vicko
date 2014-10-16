package serveur;

import infos.Utilisateur;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public final class API {
	/* Ne pas oublier le / de fin */
	private static String urlServeur = new String("http://localhost:8080/web/");
	
	/**
	 * Constructeur privé, on ne peut pas instancier cette classe (car statique)
	 */
	private API() {
		 
	}
	
	/**
	 * Effectue une requête de type POST
	 * @param params Paramètres de la requête
	 * @return Le réponse du serveur
	 */
	private static String postRequest(String page, String params) {
		// Création de l'url
		URL url = null;
		try {
			url = new URL(urlServeur + page);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} 
		
		// Connexion http + configuration de la requête
		HttpURLConnection connection = null;
		try {
			connection = (HttpURLConnection) url.openConnection();
		} catch (IOException e) {
			e.printStackTrace();
		}           
		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setInstanceFollowRedirects(false); 
		
		try {
			connection.setRequestMethod("POST");
		} catch (ProtocolException e) {
			e.printStackTrace();
		} 
		
		connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); 
		connection.setRequestProperty("charset", "utf-8");
		connection.setRequestProperty("Content-Length", "" + Integer.toString(params.getBytes().length));
		connection.setUseCaches (false);

		// On envoie les données POST
		DataOutputStream wr = null;
		try {
			wr = new DataOutputStream(connection.getOutputStream ());
			wr.writeBytes(params);
			wr.flush();
			wr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Récupération de la réponse
		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		String inputLine;
		String reponse = "";
		
		if(in == null)
			System.out.println("ERROR IN IS NULL !");
 
		try {
			// On lit chaque ligne puis on ferme le buffer
			while ((inputLine = in.readLine()) != null) {
				reponse += inputLine;
			}
			
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		connection.disconnect();
		
		return reponse;
	}
	
	/**
	 * Effectue une requête de type GET sans paramètre à une page précise du site
	 * @param page La page désirée du site
	 * @return La réponse du serveur
	 */
	private static String getRequest(String page) {
		// Création de l'url
		URL url = null;
		try {
			url = new URL(urlServeur + page);
		} catch (MalformedURLException e3) {
			e3.printStackTrace();
		}
		
		// Création de la connexion http
		HttpURLConnection connection = null;
		try {
			connection = (HttpURLConnection) url.openConnection();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
 
		// Méthode GET
		try {
			connection.setRequestMethod("GET");
		} catch (ProtocolException e2) {
			e2.printStackTrace();
		}
		connection.setRequestProperty("User-Agent", "Mozilla/5.0");
		
		
		// Récupération de la réponse
		BufferedReader in = null;
		
		try {
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		} catch (IOException e1) {
			e1.printStackTrace();
			return "0";
		}
		
		String inputLine;
		String reponse = "";
 
		try {
			// On lit chaque ligne puis on ferme le buffer
			while ((inputLine = in.readLine()) != null) {
				reponse += inputLine;
			}
			
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		connection.disconnect();
		
		return reponse;
	}
	
	/**
	 * Récupère la réponse du serveur pour une liste des messages d'un membre (selon son id)
	 * @param id Id du membre 
	 * @return La réponse du serveur
	 */
	public static String getMessagesP2P (int id) {
		return postRequest("Message", "id_membre=" + id);
	}

	/**
	 * Récupère la réponse du serveur après lui avoir dis si l'utilisateur était ou non connecté au tchat P2P
	 * @param connecte 1 si l'utilisateur est connecté, 0 sinon
	 * @return La réponse du serveur
	 */
	public static String postAmiP2P (int connecte) {
		return postRequest("Connecte", "ip_membre=" + Utilisateur.GetInstance().getLocalIp()  + "&id_membre=" + Utilisateur.GetInstance().getId() + "&coOrNot=" + connecte);
	}

	/**
	 * Récupère la réponse du serveur pour la liste des amis connectés ou non au tchat
	 * @return La réponse du serveur
	 */
	public static String getListeAmisP2P () {
		return getRequest("Connecte");
	}
	
	/**
	 * Applique une requête pour poster un message différé
	 * @param id_src Id de l'envoyeur
	 * @param id_dest Id du destinataire
	 * @param texte Texte du message
	 * @return La réponse du serveur
	 */
	public static String postMessage (int id_src, int id_dest, String texte) {
		return postRequest("EnvoiMessage", "id_membre_src=" + id_src + "&id_membre_dest=" + id_dest + "&texte=" + texte);
	}
	
	/**
	 * Applique une requête pour vérifier la connexion d'un client
	 * @param pseudo Le pseudo de l'utilisateur
	 * @param mdp Le mot de passe de l'utilisateur
	 * @return La réponse du serveur (rappelons, True si correct ou False si incorrects)
	 */
	public static String postConnexion(String pseudo, String mdp) {
		return postRequest("Connexion", "username=" + pseudo + "&password=" + mdp + "&type=client");
	}
	
	/**
	 * Applique une requête pour connaitre une liste de vidéos par recherche
	 * @param recherche La chaine de caractère correspondant à la recherche
	 * @return La réponse du serveur (XML, respectant recherche.dtd)
	 */
	public static String postRecherche(String recherche, String type) {
		return postRequest("Recherche", "search=" + recherche + "&type=" + type);
	}
	
	/**
	 * Effectue une requête afin de trouver les données d'une vidéo par son id
	 * @param id_video L'id de la vidéo
	 * @return La réponse XML du serveur respectant video.dtd
	 */
	public static String postVideo (String id_video) {
		return postRequest("Video", "id_video=" + id_video);
	}
	
	/**
	 * Effectue une requête afin de trouver les commentaires selon une vidéo
	 * @param id_video L'id de la vidéo dont les commentaires sont cherchés
	 * @return La réponse XML du serveur respectant commentaire.dtd
	 */
	public static String postCommentaire (String id_video) {
		return postRequest("Commentaire", "id_video=" + id_video);
	}
	
	/**
	 * Envoi d'une nouvelle vidéo au serveur.
	 * @param id_membre - Id de l'auteur du post
	 * @param lien - Lien vers la video Youtube !
	 * @param lien_miniature - Lien vers la miniature
	 * @param titre - titre de la video
	 * @param texte - descriptin de la video
	 * @return true si aucune erreur, false sinon.
	 */
	public static String sendVideo(String id_membre, String lien, String lien_miniature, String titre, String texte)
	{
		return API.postRequest("EnvoiVideo", "id_membre=" + id_membre + "&lien=" + lien + "&lien_miniature=" + lien_miniature + "&titre=" + titre + "&texte=" + texte);
	}
	
	/**
	 * Effectue une requête afin d'obtenir les informations d'un membre.
	 * @param id_membre
	 * @return La réponse XML du serveur respectant membre.dtd
	 */
	public static String postMembre(String pseudo)
	{
		return API.postRequest("Membre", "pseudo=" + pseudo);
	}
	
	/**
	 * Effectue une requete de vote.
	 * @param id_article
	 * @param id_membre
	 * @param value
	 * @return Reponse du serveur si tout c'est bien passé ou non.
	 */
	public static String sendVote(String id_article, String id_membre, String value)
	{
		return API.postRequest("EnvoiVote", "id_membre=" + id_membre + "&id_article=" + id_article + "&value=" + value);
	}
	
	/**
	 * Requete d'ajout d'un commentaire à la vidéo d'id id_article.
	 * @param id_article
	 * @param id_membre
	 * @param commentaire
	 * @return Reponse du serveur si tout c'est bien passé ou non.
	 */
	public static String sendCommentaire(String id_article, String id_membre, String commentaire)
	{
		return API.postRequest("EnvoiCommentaire", "id_membre=" + id_membre + "&id_article=" + id_article + "&commentaire=" + commentaire);
	}
	
	public static String postPlaylist(String pseudo)
	{
		return API.postRequest("Playlist", "pseudo=" + pseudo);
	}
	
	public static String SetInPlaylist(String pseudo, String id_article)
	{
		return API.postRequest("Playlist", "pseudo=" + pseudo + "&id_article=" + id_article);
	}
}