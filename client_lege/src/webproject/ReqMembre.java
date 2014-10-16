/**
 * Contient les requêtes relatives aux membres
 * @author Reynaud Nicolas
 */

package webproject;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReqMembre {
	
	/**
	 * Retourne l'id 'un membre en fonction du pseudo
	 * @param c Connexion a utiliser
	 * @param pseudo Pseudo du mmebre
	 * @return id du membre en cas de succes
	 * 			0 sinon
	 */
	public static int getIdMembre ( Connect c, String pseudo) {
		ResultSet rs;

		try {
			rs = c.reqSelect("SELECT id_membre As Id FROM Membres WHERE pseudo_membre LIKE '" + pseudo+"'");

			if ( rs.next() ) 
				return rs.getInt("Id");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return 0;
	}

	/**
	 * Recuperer un membre en particulier
	 * @param c Connexion a utiliser 
	 * @param id_membre id du membre
	 * @param pseudo pseudo du membre
	 * renseigner un des 2 champs
	 * 
	 * @return Resultset sur le membre en cas de succes
	 * 			null sinon
	 */
	public static ResultSet getMembre( Connect c, String id_membre, String pseudo ) {
		boolean emptyId = false;
		if ( id_membre == null ) id_membre = "";
		if ( pseudo == null ) pseudo = "";

		if ( (emptyId = id_membre.isEmpty()) || pseudo.isEmpty() ) {

			if ( emptyId )
				id_membre = Integer.toString(getIdMembre(c, pseudo));
			
			if ( !id_membre.isEmpty() )			
				return c.reqSelect("SELECT pseudo_membre As Pseudo, description_membre As Description, sexe_membre As Sexe, email_membre As Email, ( SELECT COUNT(*) FROM Articles WHERE id_membre_article = "+ id_membre +" ) As Nb_article, (SELECT COUNT(*) FROM Commentaires WHERE id_membre_commentaire = "+ id_membre +" ) As Nb_commentaire, DATE_FORMAT(date_inscription_membre, '%d-%m-%Y') As Date FROM Membres WHERE id_membre = "+ id_membre);
		}
		
		return null;
	}
	
	/**
	 * Verifi qu'un mebre existe
	 * @param c Connexion a utiliser
	 * @param pseudo_membre pseudo du mmebre
	 * @param email email du membre
	 * @return true si il existe
	 * 			false sinon
	 */
	public static boolean existsMembre(Connect c, String pseudo_membre, String email) {
		ResultSet rs;
		
		try {
			rs = c.reqSelect("SELECT COUNT(*) As Total FROM Membres WHERE pseudo_membre = '"+ pseudo_membre +"' OR email_membre = '"+email+"'");
			if ( rs.next() );
				return rs.getInt("Total") > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}
	
	/**
	 * Ajoute un membre
	 * @param c Connexion a utiliser
	 * @param pseudo_membre pseudo du membre
	 * @param password_membre mot de passe
	 * @param password_membre2 confirmation du mot de passe 
	 * @param email email du membre
	 * @param sexe sexe du membre
	 * @return true si le membre est inscrit ( I.E. tout est correcte ) 
	 * 			false sinon
	 */
	public static boolean setMembre(Connect c, String pseudo_membre, String password_membre, String password_membre2, String email, String sexe ) {

		if ( pseudo_membre == null ) pseudo_membre = "";
		if ( password_membre == null ) password_membre = "";
		if ( password_membre2 == null ) password_membre2 = "";
		if ( email == null) email = "";
		if ( sexe == null ) sexe = "";
		
		if ( pseudo_membre.isEmpty() || password_membre.isEmpty() || email.isEmpty() || !password_membre.equals(password_membre2) || sexe.equals("M") || sexe.equals("M"))
			return false;
		
		if ( existsMembre(c, pseudo_membre, email) ) 
			if ( c.reqChange("INSERT INTO ConnexionTchat (`ip_connexiontchat`, `timestamp_connexiontchat`, `co_or_not_connexiontchat`) VALUES ('', CURRENT_TIMESTAMP, '0')")  > 0 )
				return c.reqChange("INSERT INTO Membres (`pseudo_membre`, `password_membre`, `email_membre`, `sexe_membre`, `date_inscription_membre`) VALUES ('"+ pseudo_membre +"', '"+ password_membre +"', '"+ email +"', '"+ sexe +"', CURRENT_DATE())")  > 0;

		return false;			
	}
}
