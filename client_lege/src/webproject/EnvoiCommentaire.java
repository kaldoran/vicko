/**
 * Permet l'envoi d'un commentaire depuis le client lourd au serveur
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

@WebServlet("/EnvoiCommentaire")
public class EnvoiCommentaire extends HttpServlet {
	private static final long serialVersionUID = 1L;
    Connect c;
    
    public EnvoiCommentaire() {
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
	 *  En données POST a http://localhost:8080/web/EnvoiCommentaire :
	 *		id_membre - Id de l'auteur du commentaire
	 *		id_article - Id de l'article sur lequel une personne commente
	 *		commentaire - Commentaire de al video
	 *		type - type de navigateur
	 * Affiche : 
	 *		true si aucune erreur
	 *		false sinon
	 * 
	 * Si type == nav, il y aura une redirection 
	 *  
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		
		String id_membre = request.getParameter("id_membre");
		String id_article = request.getParameter("id_article");
		String commentaire = request.getParameter("commentaire");
		String type = request.getParameter("type");
		
		out.println(ReqCommentaire.setCommentaire(c, id_membre, id_article, commentaire));
		
		if ( type == null ) type = "";
		if ( type.equals("nav")) 
			response.sendRedirect("http://localhost:8080/web/Video.jsp?id=" + id_article+"#commentaire");

	}
}
