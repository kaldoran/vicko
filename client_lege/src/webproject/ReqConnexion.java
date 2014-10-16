/**
 * Contient les requêtes associées a une connexion
 * @author Reynaud Nicolas
 */

package webproject;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReqConnexion {
	
	/**
	 * Verifi si un membre est correct
	 * @param c Connexion a utiliser
	 * @param pseudo Pseudo du membre
	 * @param password mot de passe
	 * @return Resulset en cas de succes sur le membre
	 * 			null sinon
	 * @throws SQLException
	 */
	public static ResultSet verifLogin( Connect c, String pseudo, String password ) throws SQLException {
		if ( pseudo == null ) pseudo = "";
		if ( password == null ) password = "";
		
		if ( pseudo.isEmpty() || password.isEmpty() )
			return null;

		return c.reqSelect("SELECT COUNT(*) AS Total, id_membre As Id, pseudo_membre As Pseudo, rang_membre AS Rang, date_inscription_membre AS Date FROM Membres WHERE Membres.pseudo_membre = '"+pseudo+"' AND Membres.password_membre = '"+password+"'");	
	}
	
	/**
	 * Met a jour le temps de connexion d'un membre et l'ip
	 * @param c Connexion a utiliser
	 * @param id_membre id du membre a mettre a jours
	 * @param ip_membre ip du membre
	 * @param coOrNot 1 si on veut le connexion
	 * 			0 si on veut le deconnecter
	 * @return si succes : nombre de ligne affectée
	 * 			-1 sinon
	 */
	public static int update_connexion( Connect c, String id_membre, String ip_membre, String coOrNot ) {
		if ( id_membre == null ) id_membre = "";
		
		if ( id_membre.isEmpty() )
			return -1;
		
		return c.reqChange("UPDATE ConnexionTchat SET timestamp_connexiontchat = now(),ip_connexiontchat = '" + ip_membre + "', co_or_not_connexiontchat = "+ coOrNot +" WHERE id_membre_connexiontchat = "+ id_membre);
	}
}
