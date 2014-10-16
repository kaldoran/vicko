/**
 * Permet l'inscription d'un membre graces aux données recu en donnée POST
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
 * Servlet implementation class Inscription
 */
@WebServlet("/Inscription")
public class Inscription extends HttpServlet {
	private static final long serialVersionUID = 1L;
    Connect c;
    
    public Inscription() {
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
	 * 	En données POST a http://localhost:8080/web/Inscription
	 *		username - Pseudo de l'utilisateur
	 *		password - Mot de passe
	 *		password2 - Confirmation du mdp
	 *		email - Email du membre
	 *		sexe - Sexe du membre
	 *
	 * Affiche :
	 *		true en cas de succes
	 *		false sinon
	 *
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();

		String pseudo = request.getParameter("username");
		String password = request.getParameter("password");
		String password2 = request.getParameter("password2");
		String email = request.getParameter("email");
		String sexe = request.getParameter("sexe");

		out.print(ReqMembre.setMembre(c, pseudo, password, password2, email, sexe));
	}
}
