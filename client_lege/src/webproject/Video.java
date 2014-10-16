/**
 * Genere le xml de sortie suivant les parametres donnés
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
 * Servlet implementation class Video
 */
@WebServlet("/Video")
public class Video extends HttpServlet {
	private static final long serialVersionUID = 1L;

   	Connect c;
   	
    public Video() {
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
	 * 	En données POST à http://localhost:8080/web/Video :
	 *		id_video - Id de la vidéo dont les données sont cherchées.
	 * 	
	 * 	Affiche :
	 * 		Liste des vidéos répondant a "video.dtd"
	 * 
	 * @throws SQLException
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ResultSet rs;
		String id_video = request.getParameter("id_video");

		PrintWriter out = response.getWriter();
		out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<!DOCTYPE note SYSTEM \"video.dtd\">");

		try {
			rs = ReqVideo.getVideo(c, id_video);

			while(rs != null && rs.next()) {
				out.println("<video id_video=\""+ rs.getInt("Id") +"\">");
				out.println("<titre>"+ rs.getString("Titre") +"</titre>");
				out.println("<lien>"+ rs.getString("Lien") +"</lien>");
				out.println("<texte>"+ rs.getString("Texte") +"</texte>");
				out.println("<date>"+ rs.getString("Date") +"</date>");
				out.println("<auteur>"+ rs.getString("Auteur") +"</auteur>");
				out.println("<nb_vote>"+ rs.getInt("Nb_vote") +"</nb_vote>");
				out.println("</video>");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
