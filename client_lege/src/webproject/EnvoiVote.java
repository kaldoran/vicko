/**
 * Permet d'envoyer un vote au serveur
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

@WebServlet("/EnvoiVote")
public class EnvoiVote extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connect c;
	
    public EnvoiVote() {
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
	 * 	En données POST a http://localhost:8080/web/EnvoiVote
	 *		id_membre - Id du membre qui vote
	 *		id_article - Id de l'article sur lequel une personne vote
	 *		value - Supérieur a 0 si jamais c'est un vote position 
	 *		   		inférieur sinon
	 *
	 * Affiche :
	 *		true - Si aucune erreur ( membre n'ayant pas voté etc ) 
	 *		false - Sinon  
	 *
	 *  @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		
		String id_membre = request.getParameter("id_membre");
		String id_article = request.getParameter("id_article");
		String value = request.getParameter("value");
		
		out.println(ReqVote.setVote(c, id_membre, id_article, value));
	}
}
