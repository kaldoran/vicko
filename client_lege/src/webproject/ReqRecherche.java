/**
 * Contient les requêtes associées a toutes les recherches
 * @author Reynaud Nicolas
 */

package webproject;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReqRecherche {
	
	/**
	 * Effectue une recherche
	 * @param c connexion a utiliser
	 * @param search recherche a faire
	 * @param type type de la recherche 
	 * 			m - Par popularité croissante
	 *			p - Par popularité décroissante
	 *			n - Par date ( plus récent au moins récent ) 
	 *			r - Par date ( plus récent au moins récent )
	 *
	 * @return un resultSet sur la liste de video trouver si succes
	 * 			null sinon
	 * 
	 * @throws SQLException
	 */
	public static ResultSet recherche(Connect c, String search, String type ) throws SQLException {
		if ( search == null ) search = "";
		if ( type == null ) type = "";
		
		if ( search.isEmpty() && type.isEmpty() )
			return null;
		
		if (search.isEmpty()) {
			if ( type.equals("m"))
				return ReqVideo.getAllVideo(c, ReqVideo.VOTEM);
			else if ( type.equals("p") )
				return ReqVideo.getAllVideo(c, ReqVideo.VOTEP);
			else if ( type.equals("n") )
				return ReqVideo.getAllVideo(c, ReqVideo.DATED);
			else if ( type.equals("F") )
				return null;
			else
				return ReqVideo.getAllVideo(c, ReqVideo.DATEC);
		}
		
		if ( type.equals("m")) 
			return ReqVideo.getAllVideo(c, search, ReqVideo.VOTEM);
		else if ( type.equals("p") )
			return ReqVideo.getAllVideo(c, search, ReqVideo.VOTEP);
		else if ( type.equals("n") )
			return ReqVideo.getAllVideo(c, search, ReqVideo.DATED);
		else if ( type.equals("r") )
			return ReqVideo.getAllVideo(c, search, ReqVideo.DATEC);
		else if ( type.equals("F") )
			return ReqPlaylist.getPlaylist(c, search);
		else 
			return ReqVideo.getAllVideoMembre(c, search);
	}
}
