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
 * Servlet implementation class Message
 */
@WebServlet("/Message")
public class Message extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connect c;
	
    public Message() {
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
	 *  En données POST à http://localhost:8080/client_lege/Message 
     *		id_membre - id du membre pour lequel on veut récuperer les messages
     *	
     * 	Affiche :
     *   	Listes des messages groupées par id d'envoyeur répondant a "message.dtd"
     *   
     * @throws SQLException
     * 
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ResultSet rs;
		PrintWriter out = response.getWriter();
		out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<!DOCTYPE note SYSTEM \"message.dtd\">");
		
		String id_membre = request.getParameter("id_membre");
		
		try {
			rs = ReqP2P.getMessage(c, id_membre);
			out.println("<membres>");
			if(rs != null && rs.next()) {
				out.println("<membre>");
				out.println("<id>" + rs.getInt("Id") + "</id>");
				out.println("<messages>");
				do {
					out.println("<message>");
					out.println("<date>"+ rs.getString("Date") +"</date>");
					out.println("<texte>"+ rs.getString("Msg") +"</texte>");
					out.println("</message>");
				}while(rs.next());
				out.println("</messages>");
				out.println("</membre>");
			}
			out.println("</membres>");
			ReqP2P.deleteMessage(c, id_membre);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
