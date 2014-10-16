/**
 * Contient les requêtes associées aux videos
 * @author Reynaud Nicolas
 */

package webproject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class ReqVideo {
	
	public static final int DATEC  = 1;
	public static final int DATED  = 2;
	public static final int VOTEM  = 3;
	public static final int VOTEP  = 4;
		
	/**
	 * Retourne le nombre de video 
	 * Initialement prévu pour gerer le multi page
	 * @param c Connexion a utiliser
	 * @return nombre de video total
	 * 			0 si erreur
	 * 
	 * @throws SQLException
	 * 
	 */
	public static int nbVideo( Connect c) {
		ResultSet rs;
		
		try {
			rs = c.reqSelect("SELECT COUNT(*) As Total FROM Articles");
			if ( rs.next() )
				return rs.getInt("Total");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;		
	}
	
	/**
	 * Recuperer toutes les vidéos suivant un ordre
	 * @param c Connexion a utiliser 
	 * @param order ordre de recherche 
	 * @see ReqVideo#getAllVideoWithReq(Connect, String, int)
	 */
	public static ResultSet getAllVideo( Connect c, int order) {
		String req = "SELECT vote_article As Nb_vote, id_article AS Id, pseudo_membre As Auteur, titre_article AS Titre, lien_miniature_article As Lien, DATE_FORMAT(`date_postage_article`, '%d-%m-%Y') As Date FROM Articles, Membres WHERE id_membre_article = id_membre";

		return getAllVideoWithReq(c, req, order);
	}
	
	/**
	 * Recuperer toutes les vidéos suivant un ordre
	 * @param c Connexion a utiliser 
	 * @param search recherche a faire
	 * @param order ordre de recherche 
	 * @see ReqVideo#getAllVideoWithReq(Connect, String, int)
	 */
	public static ResultSet getAllVideo( Connect c, String search, int order) {
		String req = "SELECT vote_article As Nb_vote, id_article AS Id, pseudo_membre As Auteur, titre_article AS Titre, lien_miniature_article As Lien, DATE_FORMAT(`date_postage_article`, '%d-%m-%Y') As Date FROM Articles, Membres WHERE id_membre_article = id_membre AND titre_article LIKE '%"+search+"%'";
		
		return getAllVideoWithReq(c, req, order);
	}
	
	/**
	 * Recuperer toutes les vidéos suivant un ordre
	 * @param c Connexion a utiliser 
	 * @param search recherche a faire
	 * @see ReqVideo#getAllVideoWithReq(Connect, String, int)
	 */
	public static ResultSet getAllVideoMembre( Connect c, String search) {
		String req = "SELECT vote_article As Nb_vote, id_article AS Id, pseudo_membre As Auteur, titre_article AS Titre, lien_miniature_article As Lien, DATE_FORMAT(`date_postage_article`, '%d-%m-%Y') As Date FROM Articles, Membres WHERE id_membre_article = id_membre AND pseudo_membre LIKE \""+ search + "\"";
		return getAllVideoWithReq(c, req, DATED);
	}
	
	/**
	 * Recuperer toutes les vidéos suivant un ordre
	 * @param c Connexion a utiliser
	 * @param req requete a faire
	 * @param order ordre a utiliser
	 * @return resultSet sur le resultat si ok
	 * 			null sinon
	 */
	public static ResultSet getAllVideoWithReq( Connect c, String req, int order) {
		
		if ( order == DATEC )
			return c.reqSelect(req + " ORDER BY date_postage_article DESC");
		else if ( order == VOTEM )
			return c.reqSelect(req + " ORDER BY vote_article ASC");
		else if ( order == VOTEP )
			return c.reqSelect(req + " ORDER BY vote_article DESC");
		else if ( order == DATED )
			return c.reqSelect(req + " ORDER BY date_postage_article ASC");

		return null;
	}
	
	public static ResultSet getVideo( Connect c, String id_video ) {
		if ( id_video == null ) id_video = "";
				
		if ( id_video.isEmpty() ) 
			return null;
		
		return c.reqSelect("SELECT vote_article As Nb_vote, id_article As Id, titre_article As Titre, lien_video_article As Lien, texte_article As Texte, date_postage_article As Date, pseudo_membre As Auteur FROM Membres, Articles WHERE id_membre = id_membre_article AND id_article = "+ id_video );
	}
		
	public static boolean setVideo(Connect c, String id_membre, String lien, String lien_miniature, String titre, String texte) {
		if ( id_membre == null ) id_membre = "";
		if ( lien == null ) lien = "";
		if ( titre == null ) titre = "";
				
		if ( id_membre.isEmpty() || lien.isEmpty() || titre.isEmpty() )
			return false;

		if ( lien_miniature.equals(""))
			lien_miniature = "http://www.showinweb.com/wp-content/uploads/2013/04/BS9ye-320x180.jpg"; //"http://placehold.it/320x180/007dbb/FFFFFF/&text=video"

		
		if ( texte.isEmpty() )
			texte = "Aucune description";

		Pattern pattern = Pattern.compile("(?<=watch\\?v=|/videos/|embed\\/)[^#\\&\\?]*"); /* je ne suis pas l'auteur du pattern */
		java.util.regex.Matcher matcher = pattern.matcher(lien);

		if ( matcher.find() && matcher.group().length() == 11) {
			return c.reqChange("INSERT INTO Articles (id_membre_article, lien_video_article, lien_miniature_article, titre_article, texte_article, date_postage_article, vote_article ) VALUES ('"+ id_membre +"', '"+ matcher.group() +"', '"+ lien_miniature +"', '"+ titre +"', '"+ texte +"', CURRENT_DATE(), '0');")  > 0;
		
		}
		return false;
	}
	
	/**
	 * Retourne l'id d'une vidéo de facon random ( faux random ) 
	 * @param c	connexion a utiliser
	 * @return numéro de vidéo random
	 * 			0 si erreur
	 * 
	 * @throws SQLException
	 */
	public static int randVideo(Connect c) {
		ResultSet rs;
		try {
			rs = c.reqSelect("SELECT id_article As Id FROM Articles ORDER BY RAND() ");
			if ( rs.next() )
				return rs.getInt("Id");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return 0;
	}
}
