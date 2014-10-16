/**
 * Permet la connexion suivant le type de navigateur utilisé ( Navigateur web ou client lourd ) 
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
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Connexion
 */
@WebServlet("/Connexion")
public class Connexion extends HttpServlet {
	Connect c;

	private static final long serialVersionUID = 1L;

	public Connexion() {
		super();
	}

	public void init(ServletConfig config) throws ServletException {
		c = new Connect(InfoBdd.LIEN, InfoBdd.PSEUDO, InfoBdd.MDP);
	}

	public void destroy() {
		c.close();
	}
	/** 
	 *	En données POST A http://localhost:8080/web/Connexion:
	 *		username - Pseudo de l'utilisateur a tester
	 *		password - Mot de passe de l'utilisateur a tester
	 *		type - "client"
	 *		
	 *	Affiche :
	 *		l'id du membre si ok  ( I.E.  Mot de passe + pseudo correct )
	 *		-1 sinon
	 *	- Si type = "nav" alors les sessions seront crée et mis a jours 
	 *
	 *	@throws SQLException
	 *
	 *	@see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ResultSet rs;
		PrintWriter out = response.getWriter();

		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String type = request.getParameter("type");
		response.setContentType("text/html");

		try {
			rs = ReqConnexion.verifLogin(c, username, password);
			if ( rs != null && rs.next() ) {
				if ( rs.getInt("Total") == 1 ) {

					out.println(rs.getInt("Id"));

					if ( type != null && type.equals("web") ) {
						HttpSession session = request.getSession();
						session.setAttribute("Id", rs.getInt("Id"));
						session.setAttribute("Pseudo", rs.getString("Pseudo"));
						session.setAttribute("Date", rs.getString("Date"));
						session.setAttribute("Rang",  rs.getInt("Rang"));
						response.sendRedirect("./Connexion.jsp");
					}
					
					return;
				}
			}

			out.println("-1");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
