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
 * Servlet implementation class Commentaire
 */
@WebServlet("/Commentaire")
public class Commentaire extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	Connect c;
	
    public Commentaire() {
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
	 * En données POST a http://localhost:8080/web/Commentaire :
	 * 		id_video - Id de la vidéo dont les commentaires sont recherchés
	 *
	 * Affiche : 
	 * 		Listes des commentaires associée a l'id_video en répondant a "commentaire.dtd"
	 * 
	 *  @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ResultSet rs;
		String id_video = request.getParameter("id_video");
		
		PrintWriter out = response.getWriter();
		out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<!DOCTYPE note SYSTEM \"commentaire.dtd\">");
		out.println("<commentaires>");
		
		try {
			rs = ReqCommentaire.getCommentaire(c, id_video);
			while(rs != null && rs.next()) {
				out.println("<commentaire id_commentaire=\""+ rs.getInt("Id") +"\">");
				out.println("<auteur>"+ rs.getString("Auteur") +"</auteur>");
				out.println("<texte>"+ rs.getString("texte")+"</texte>");
				out.println("<date>"+ rs.getString("Date") +"</date>");
				out.println("</commentaire>");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		out.println("</commentaires>");
	}

}
