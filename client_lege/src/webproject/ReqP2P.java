/**
 * Contient les requêtes associées au client P2P
 * @author Reynaud Nicolas
 */

package webproject;

import java.sql.ResultSet;

public class ReqP2P {
	
	/**
	 * Liste des membres connecté ou non
	 * @param c connexion a utiliser
	 * @return ResultSet sur la liste des mmebres
	 */
	public static ResultSet connecteP2P(Connect c) {
		return c.reqSelect("SELECT id_membre AS Id, pseudo_membre As Pseudo, ip_connexiontchat As Ip, UNIX_TIMESTAMP(timestamp_connexiontchat) As LastCo, co_or_not_connexiontchat As CoOrNot FROM Membres, ConnexionTchat WHERE id_membre_connexiontchat = id_membre");
	}
	
	/**
	 * Recuperer les messages d'un membre
	 * @param c Connexion a utiliser
	 * @param id_membre id du membre
	 * @return Resultset sur les messages
	 * 			null si erreur
	 */
	public static ResultSet getMessage( Connect c, String id_membre) {
		if ( id_membre == null ) id_membre = "";
		if ( id_membre.isEmpty() )
			return null;
		
		return c.reqSelect("SELECT id_envoyeur_message As Id, msg_message As Msg, DATE_FORMAT(`date_message`, '%Y-%m-%d %H:%i:%s')As Date FROM Messages WHERE id_destinataire_message = " + id_membre);
	}
	
	/**
	 * Supprime les messages d'un membre
	 * @param c Connexion a utiliser
	 * @param id_membre id du membre
	 * @return nombre de ligne affecter si succes
	 * 			0 sinon
	 */
	public static int deleteMessage(Connect c, String id_membre) {
		if ( id_membre == null ) id_membre = "";
		
		if ( id_membre.isEmpty() )
			return 0;
		
		return c.reqChange("DELETE FROM Messages WHERE id_destinataire_message = " + id_membre);
	}
	
	/**
	 * Ajoute un message 
	 * @param c Connexion a utiliser
	 * @param id_membre_src id du membre auteur du message
	 * @param id_membre_dest id du membre destinataire
	 * @param texte Message
	 * @return true en cas de succes 
	 * 			false sinon
	 */
	public static boolean setMessage( Connect c, String id_membre_src, String id_membre_dest, String texte) {
		if ( id_membre_src == null ) id_membre_src = "";
		if ( id_membre_dest == null ) id_membre_dest = "";
		if ( texte == null ) texte = "";
		
		if ( id_membre_dest.isEmpty() || id_membre_src.isEmpty() || texte.isEmpty() )
			return false;
		
		return c.reqChange("INSERT INTO Messages(id_envoyeur_message, id_destinataire_message, msg_message) VALUES ("+ id_membre_src +", "+ id_membre_dest +", '"+ texte +"')") > 0;
	}
}
