/**
 * Genere le XML de sortie en fonction des parametres recu en donnée POST
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
 * Servlet implementation class Recherche
 */
@WebServlet("/Recherche")
public class Recherche extends HttpServlet {
	Connect c;
	
	private static final long serialVersionUID = 1L;
       
    public Recherche() {
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
	 * 	En données POST a http://localhost:8080/web/Recherche :
	 *		search - Chaine de caractere a rechercher ( Dans les titres des vidéos )
	 *		type - Type de la recherche :
	 *			m - Par popularité croissante
	 *			p - Par popularité décroissante
	 *			n - Par date ( plus récent au moins récent ) 
	 *			r - Par date ( plus récent au moins récent )
	 *
	 *	Si search n'est pas précisé alors la recherche sera fait uniquement par le critere type
	 *		r étant prioritaire si rien n'est renseigné
	 *	
 	 * 	Affiche : 
	 *		Liste de vidéo répondant a "recherche.dtd"
	 *
	 *	@throws SQLException
	 *	@see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ResultSet rs;
		String search = request.getParameter("search");
		String type = request.getParameter("type");
				
		PrintWriter out = response.getWriter();
		out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<!DOCTYPE note SYSTEM \"recherche.dtd\">");
		out.println("<videos>");

		try {
			rs = ReqRecherche.recherche(c, search, type);
			while(rs != null && rs.next()) {
				out.println("<video id_video=\""+rs.getInt("Id")+"\">");
				out.println("<titre>"+rs.getString("Titre")+"</titre>");
				out.println("<lien_miniature>"+rs.getString("Lien")+"</lien_miniature>");
				out.println("<date>"+rs.getString("Date")+"</date>");
				out.println("<auteur>"+rs.getString("Auteur")+"</auteur>");
				out.println("</video>");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		out.println("</videos>");
	}

}
