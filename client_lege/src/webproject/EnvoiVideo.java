/**
 * Permet l'envoi de video au serveur
 * @author Reynaud Nicolas
 */

package webproject;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class EnvoiVideo
 */
@WebServlet("/EnvoiVideo")
public class EnvoiVideo extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connect c;
	
    public EnvoiVideo() {
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
	 * 	En données POST a http://localhost:8080/web/EnvoiVideo :
	 *		id_membre - Id de l'auteur du post
	 *		lien - Lien vers la video Youtube !
	 *		lien_miniature - Lien vers la miniature
	 *		titre - titre de la video
	 *		texte - descriptin de la video
	 *      type - type de nagivateur
	 *      
	 * Affiche : 
 	 *		true si aucune ereur
	 *		false sinon
	 * 
	 * si type = nav
	 * 		redirection vers Index.jsp en cas de succes
	 * 		redirection vers AjoutVideo.jsp sinon
	 * 
	 *  @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		
		boolean ret = false;
		String id_membre = request.getParameter("id_membre");
		String lien = request.getParameter("lien");
		String lien_miniature = request.getParameter("lien_miniature");
		String titre = request.getParameter("titre");
		String texte = request.getParameter("texte");
		String type = request.getParameter("type");
		
		if ( type == null) type = "";
		
		ret = ReqVideo.setVideo(c, id_membre, lien, lien_miniature, titre, texte);
		
		if ( type.equals("nav") ) {
			if (ret == true )
				response.sendRedirect("./Index.jsp");
			else
				response.sendRedirect("./AjoutVideo.jsp");
		}
		
		out.println(ret);
	
	}

}
