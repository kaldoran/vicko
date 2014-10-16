/**
 * Genere le XML en fonction des parametres recu
 * @author Reynaud Nicolas
 */

package webproject;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Connecte
 */
@WebServlet("/Connecte")
public class Connecte extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connect c;
	
    public Connecte() {
        super();
    }

	public void init(ServletConfig config) throws ServletException {
		c = new Connect(InfoBdd.LIEN, InfoBdd.PSEUDO, InfoBdd.MDP);
	}

	public void destroy() {
		c.close();
	}
	
	/**
	 * En données GET à http://localhost:8080/web/Connecte
	 *
	 * Affiche :
	 * 		Listes des connéctes et non connectés répondant a "connecte.dtd"
	 * 
	 * @throws SQLException
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ResultSet rs;
		PrintWriter out = response.getWriter();

		java.util.Date date = new java.util.Date();
		long Timestamp = date.getTime() / 1000; /* div par 1000 pour avoir le format unix */

		out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<!DOCTYPE note SYSTEM \"connecte.dtd\">");
		out.println("<connectes>");
		try {
			rs = ReqP2P.connecteP2P(c);
			while(rs.next()) {
				long time = rs.getLong("LastCo");
				out.print("<membre id_membre=\""+rs.getInt("Id")+"\" ip_membre=\""+ rs.getString("Ip") +"\" ");
				if ( Timestamp - 1800 < time && rs.getBoolean("CoOrNot")) /* Si la personne n'a pas actualisé ca page depuis 30 min */
					out.print("connecte=\"1\"");
				else
					out.print("connecte=\"0\"");
				out.print(">");
				out.print(rs.getString("Pseudo"));
				out.println("</membre>");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		out.println("</connectes>");
	}

	/**
	 * 	En données POST a http://localhost:8080/web/Connecte
	 *		id_membre - id du membre a mettre a jour
	 *		ip_membre - ip du membre
	 * 		coOrNot - Booleen, si il est égal a 1, le membre sera connecté [ Pendant 30 Min ]
	 * 					Si il vaut 0, le membre sera consideré comme deconnecté
     *	
	 * Affiche : 
	 * 		True en cas de succes
	 *		False Sinon
	 *
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();

		String id_membre = request.getParameter("id_membre");
		String ip_membre = request.getParameter("ip_membre");
		String coOrNot = request.getParameter("coOrNot");

		out.println(ReqConnexion.update_connexion(c, id_membre, ip_membre, coOrNot) > 0);
	}
}


