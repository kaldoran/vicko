/**
 * Contient les requêtes associées aux commentaires
 * @author Reynaud Nicolas
 */

package webproject;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReqCommentaire {
	
	/** 
	 * récuperer le nombre de commentaire associée a une video
	 * @param c	connexion a utiliser
	 * @param id_video id de la video dont les commentaires seront recuperé
	 * @return Resultset en cas de succes
	 * 			null sinon
	 * 
	 * @throws SQLException
	 */
	public static int getNbCommentaire( Connect c, String id_video) {
		ResultSet rs;
		if ( id_video == null ) id_video = "";
		
		if ( id_video.isEmpty() )
			return 0;
		
		try {
			rs = c.reqSelect("SELECT COUNT(*) As Total FROM Commentaires WHERE id_article_commentaire = "+id_video);
			if ( rs.next() )
				return rs.getInt("Total");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	/** 
	 * Recuperer les commentaires d'une video
	 * @param c Connexion a utiliser
	 * @param id_video id de la video dont on cherche les commentaires
	 * @return ResultSet en cas de succes
	 *  		null sinon  
	 */
	public static ResultSet getCommentaire(Connect c, String id_video ) {
		if ( id_video == null ) id_video ="";
		if ( id_video.isEmpty() )
			return null;
		
		return c.reqSelect("SELECT id_commentaire As Id, id_membre As IdM, pseudo_membre As Auteur, texte_commentaire As Texte, DATE_FORMAT(`date_commentaire`, '%d-%m-%Y') As Date FROM Membres, Commentaires WHERE id_article_commentaire = " + id_video + " AND id_membre_commentaire = id_membre");
	}
	
	/**
	 * Ajoute un commentaire a une video
	 * @param c Connexion a utiliser 
	 * @param id_membre id du mmebre auteur du commentaire
	 * @param id_article id de l'article sur lequel ajouter le commentaire
	 * @param commentaire commentaire de l'article
	 * @return le nombre de ligne modifier si succes
	 * 			-1 sinon
	 */
	public static int setCommentaire(Connect c, String id_membre, String id_article, String commentaire) {
		if ( id_membre == null ) id_membre = "";
		if ( id_article == null ) id_article = "";
		if ( commentaire == null ) commentaire = "";
		
		if ( id_membre.isEmpty() || id_article.isEmpty() || commentaire.isEmpty() )
			return -1;
		
		return c.reqChange("INSERT INTO Commentaires (`id_membre_commentaire`, `id_article_commentaire`, `texte_commentaire`, `date_commentaire`) VALUES ('"+ id_membre +"', '"+ id_article +"', \""+ commentaire +"\", CURRENT_DATE());");
	}
}
