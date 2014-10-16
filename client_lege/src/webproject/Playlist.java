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
 * Servlet implementation class Playlist
 */
@WebServlet("/Playlist")
public class Playlist extends HttpServlet {
	private static final long serialVersionUID = 1L;
    Connect c;
    
    public Playlist() {
        super();
    }
    
	public void init(ServletConfig config) throws ServletException {
		c = new Connect(InfoBdd.LIEN, InfoBdd.PSEUDO, InfoBdd.MDP);
	}

	public void destroy() {
		c.close();
	}
	
	/**
	 * @see Playlist#doPost(HttpServletRequest, HttpServletResponse)
	 * @throws ServletException
	 * @throws IOException
	 * @throws SQLException
	 */
	private void exec(HttpServletRequest request, HttpServletResponse response, String pseudo) throws ServletException, IOException  {
		PrintWriter out = response.getWriter();
		ResultSet rs;
		
		out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<!DOCTYPE note SYSTEM \"playlist.dtd\">");
		out.println("<playlists>");
		try {
			rs = ReqPlaylist.getPlaylist(c, pseudo);
			while(rs != null && rs.next()) {
				out.print("<titre id_video=\""+ rs.getInt("IdA") +"\">");
				out.print(rs.getString("Titre"));
				out.println("</titre>");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		out.println("</playlists>");
	}
	
	private void exec(HttpServletRequest request, HttpServletResponse response, String pseudo, String id_article) throws ServletException, IOException  {
		if ( id_article == null ) id_article = "";
		
		if ( id_article.isEmpty())
			exec(request, response, pseudo);
		else {
			int id_membre = ReqMembre.getIdMembre(c, pseudo);
			if (id_membre != 0)
				ReqPlaylist.setPlaylist(c, Integer.toString(id_membre), id_article);
		}
		
	}
	
	/** 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pseudo = request.getParameter("pseudo");
		exec(request, response, pseudo);
	}
	
	/** 
	 * 
	 *	En données POST a http://localhost:8080/web/Playlist
	 *		pseudo - Pseudo du membre auquel ont veut ajouter une video
	 *		id_article - id de larticle a ajouter
	 *
	 *  Affiche :
	 *		True en cas de succes
	 *		false sinon 
	 *
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pseudo = request.getParameter("pseudo");
		String id_article = request.getParameter("id_article");

		exec(request, response, pseudo, id_article);

		
	}

}
