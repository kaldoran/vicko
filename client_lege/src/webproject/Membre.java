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
 * Servlet implementation class Membre
 */
@WebServlet("/Membre")
public class Membre extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connect c;

    public Membre() {
        super();
    }

	public void init(ServletConfig config) throws ServletException {
		c = new Connect(InfoBdd.LIEN, InfoBdd.PSEUDO, InfoBdd.MDP);
	}

	public void destroy() {
		c.close();
	}
	
	/** 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		out.print("Get out !");
	}

	/**
	 * 	En données POST à http://localhost:8080/web/Membre:
	 *		id_membre - Id du membre dont on veut les infos
	 *		pseudo - pseudo du membre
     *	
	 *  Remplir un des 2 champs au choix
	 *
	 *  Affiche : 
	 * 		Informations corréspondant a la dtd "membre.dtd"
	 * 
	 * @throws SQLException
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ResultSet rs;
		PrintWriter out = response.getWriter();
		String id_membre = request.getParameter("id_membre");
		String pseudo = request.getParameter("pseudo");
		out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<!DOCTYPE note SYSTEM \"membre.dtd\">");

		out.println("<membre>");
		
		try {
			rs = ReqMembre.getMembre(c, id_membre, pseudo );
			while(rs != null && rs.next()) {
				out.println("<informations>");
				out.println("<pseudo>"+ rs.getString("Pseudo")+"</pseudo>");
				out.println("<description>"+ rs.getString("Description") +"</description>");
				out.println("<sexe>"+ rs.getString("Sexe") +"</sexe>");
				out.println("<email>"+ rs.getString("Email") +"</email>");
				out.println("<nb_article>"+ rs.getInt("Nb_article")+"</nb_article>");
				out.println("<nb_commentaire>"+ rs.getInt("Nb_commentaire") +"</nb_commentaire>");
				out.println("<date_inscription>"+ rs.getString("Date") +"</date_inscription>");
				out.println("</informations>");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		out.println("</membre>");
	}

}
