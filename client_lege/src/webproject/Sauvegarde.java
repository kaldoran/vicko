/**
 * Permet de sauvegarder la playlist d'un membre
 * @author Reynaud Nicolas
 */

package webproject;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Sauvegarde
 */
@WebServlet("/Sauvegarde")
public class Sauvegarde extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Sauvegarde() {
        super();
    }

    /** 
     * Fait la sauvegarde d'une playlist
     * 
     * pseudo en donnée Get  
     * 		pseudo du membre dont on veut sauevagrder la playlist
     * 
     * @throws MalformedURLException, IOException
     * 
     */
	@SuppressWarnings("deprecation")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pseudo = request.getParameter("pseudo");	
		
		OutputStream os = response.getOutputStream();
		response.setContentType("text/txt");
		response.setHeader("Content Disposition", "attachment; filename=\"Sauvegarde.xml\"");

		try {

			URL get = new URL("http://localhost:8080/web/Playlist?pseudo="+pseudo);			
			DataInputStream dis = new DataInputStream(get.openStream());

			String input;
			while( (input = dis.readLine()) != null) {
				os.write((input + "\n").getBytes());
			}
			dis.close();
		} catch (MalformedURLException me) {
			System.out.println("Malformed URL Exception" + me);
		} catch (IOException e) {
			System.out.println("IO Exception" + e);
		}

		os.close();	
	}
}
