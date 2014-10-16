/**
 * Permet au client lourd d'envoyer un message ( utile au client P2P )
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

@WebServlet("/EnvoiMessage")
public class EnvoiMessage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connect c;

    public EnvoiMessage() {
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
	 * 	En données POST à http://localhost:8080/client_lege/EnvoiMessage :
	 *		id_membre_src - id de la personne qui envoi le message
	 *		id_membre_dest - id de la personne qui recoit le message
	 *		texte - Message
	 *	
	 * Affiche : 
	 *		true en cas de succes
	 *		False sinon ( False si , id_membre_src = id_membre_dest ou que un des champs est vide ) 
	 *
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		
		String id_membre_src = request.getParameter("id_membre_src");
		String id_membre_dest = request.getParameter("id_membre_dest");
		String texte = request.getParameter("texte");
		
		out.println(ReqP2P.setMessage(c, id_membre_src, id_membre_dest, texte));
	}

}
