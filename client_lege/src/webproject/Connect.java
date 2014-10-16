/**
 * Contient les intéractions avec la BDD
 * @author Reynaud Nicolas
 */

package webproject;

import java.sql.*;


public class Connect {

	java.sql.Connection connection;
	java.sql.Statement st;

	/** Crée une connexion a la Base de données
	 * 
	 * @param url 			Url ou se trouve la base de donnée
	 * @param identifiant	Identifiant ( pseudo ) pour la base de donnée
	 * @param mdp			Mot de passe 
	 * 
	 * @throws				SQLException et ClassNotFoundException
	 */
	public Connect( String url, String identifiant, String mdp ) {

		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(url, identifiant, mdp);	
			st = connection.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}	

	/**
	 * Ferme la connexion a la base de donnée
	 * 
	 * @throws SQLException
	 */
	public void close() {
		try {
			connection.close();
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Permet d'effectuer une requete de type selection de donnée
	 * 
	 * @param query		Requetes SQL a effectuer 
	 * @return 			Un ResultSet contennant la requete 
	 * 					null en cas d'erreur
	 * 
	 * @throws 			SQLException
	 */
	public ResultSet reqSelect(String query) {
		try {
			return st.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}	

	/**
	 * Permet d'effectuer une requete de type modification et insertion de donnée
	 * 
	 * @param query		Requetes SQL a effectuer 
	 * @return 			Le nombre de ligne affectée par la requete 
	 * 					0 en cas d'erreur
	 * 
	 * @throws 			SQLException
	 */
	public int reqChange(String query) {
		try {
			return st.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
}


