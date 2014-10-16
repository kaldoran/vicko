package p2p;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class CommunicationSocket extends Communication {
	// Entrée
	private BufferedReader in = null;
	// Sortie
	private PrintWriter out = null;
	// Socket de communication
	private Socket socket = null;
	
	
	/**
	 * Constructeur, créant une nouvelle communication à partir d'une socket (direct p2p)
	 * @param s La socket de communication
	 */
	public CommunicationSocket(Socket s) {
		
		socket = s;
		messages = new ListeMessage();
		
		// Récupération des buffers d'entrée et de sortie
		try {
			out = new PrintWriter(socket.getOutputStream());
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Lorsqu'on démarre la communication dans un thread
	 */
	public void run() {
		String texte = null;
		
		// On récupère tous les messages envoyés
		do {
			try {
				texte = in.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			messages.ajouteMessageEntrant(new Message(texte));
			communicationListener.nouveauMessageRecu(this);
			
		} while (!(texte.equals("exit") || texte == null));
	}
	
	
	/**
	 * Envoie un message via la socket
	 * @param message Le message à envoyer
	 */
	public void envoieMessage(Message message) {
		messages.ajouteMessageSortant(message);
		out.print(message.getText().toCharArray());
		out.flush();
	}
	
	/**
	 * Ferme la socket de communication
	 */
	protected void finalize() throws Throwable {
		super.finalize();
		
		socket.close();
	}
}
