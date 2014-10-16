/**
 * Contient les requêtes associées aux playlists ( les favoris d'un membre )
 * @author Reynaud Nicolas
 */

package webproject;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReqPlaylist {

	/**
	 * Recuperer la playlist d'un membre
	 * @param c Connexion a utiliser 
	 * @param pseudo pseudo du membre
	 * @return un resultSet sur le membre
	 * 			null si erreur
	 */
	public static ResultSet getPlaylist(Connect c, String pseudo) {
		if ( pseudo == null ) pseudo = "";
		
		if (pseudo.isEmpty())
			return null;
		
		int id_membre = ReqMembre.getIdMembre(c, pseudo);
		
		return c.reqSelect("SELECT vote_article As Nb_vote, id_article AS Id, pseudo_membre As Auteur, id_article_playlist As IdA, titre_article AS Titre, lien_miniature_article As Lien, DATE_FORMAT(`date_postage_article`, '%d-%m-%Y') As Date FROM Playlists, Articles, Membres WHERE id_membre_article = id_membre AND id_article_playlist = id_article AND id_membre_playlist = " + id_membre);
	}
	
	/**
	 * Verifi si l'article existe deja dans la playliste
	 * @param c Connexion a utiliser 
	 * @param id_membre id du mmebre
	 * @param id_article id de l'article a tester 
	 * @return true si il existe pas
	 * 			false sinon
	 */
	public static boolean existsPlaylist(Connect c, String id_membre, String id_article) {
		ResultSet rs;
		try {
			rs = c.reqSelect("SELECT COUNT(*) As Total FROM Playlists WHERE id_article_playlist = "+ id_article +" AND id_membre_playlist = " + id_membre );
			if ( rs.next())
				return rs.getInt("Total") == 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false; 
	}
	
	/**
	 * Ajoute un article dans al playlist d'un membre
	 * @param c Connexion a utiliser
	 * @param id_membre id du membre
	 * @param id_articleid de l'article a ajouter 
	 * @return true en cas de succes
	 * 			false sinon
	 */
	public static boolean setPlaylist(Connect c, String id_membre, String id_article) {
		if ( id_membre == null) id_membre = "";
		if ( id_article == null) id_article = "";
		
		if ( id_membre.isEmpty() || id_article.isEmpty() ) 
			return false;
		
		if (!existsPlaylist(c, id_membre, id_article))
			return false;
		
		return c.reqChange("INSERT INTO Playlists (`id_membre_playlist`, `id_article_playlist`, `date_playlist`) VALUES ('"+ id_membre +"', '"+ id_article +"', CURRENT_DATE());")  > 0;
	}
	
}
