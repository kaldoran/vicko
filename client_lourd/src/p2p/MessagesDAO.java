package p2p;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import serveur.API;


/**
 * Moteur de gestion des messages différés
 *
 */
public class MessagesDAO {
	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	DocumentBuilder builder;
	Document doc;
	
	/**
	 * Constructeur, initialise les moteurs de parsage XML
	 */
	public MessagesDAO() {
		factory = DocumentBuilderFactory.newInstance();
		factory.setValidating(false);
		
		try {
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Met à jour les communications de la liste des amis lorsque l'utilisateur à reçu des messages différés
	 * @param id Id du membre souhaitant recevoir ses messages différés
	 * @param amis Le carnet d'adresse
	 */
	public void getDifferes(int id, ListeAmis amis) {
		// Récupération de la réponse du serveur
		String reponse = API.getMessagesP2P(id);
		
		// Parsage du document
		try {
			doc = builder.parse(new InputSource(new StringReader(reponse)));
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Récupération de toutes les communications reçues en notre absence
		NodeList communications_node = doc.getElementsByTagName("membre");

		// Traitement de chaque communication
		for (int i=0; i<communications_node.getLength(); i++) {
			
			Element comm = (Element) communications_node.item(i);
			int id_ami = Integer.parseInt(comm.getElementsByTagName("id").item(0).getTextContent());
			
			// On va créer la liste des messages pour cette communication
			ListeMessage messages = new ListeMessage();
			NodeList messages_node = comm.getElementsByTagName("message");
			
			for (int j=0; j<messages_node.getLength(); j++) {
				Element mess = (Element) messages_node.item(j);
				String texte = mess.getElementsByTagName("texte").item(0).getTextContent();
				
				messages.ajouteMessageEntrant(new Message(texte));
			}
			
			Ami ami = null;
			try {
				ami = amis.getAmiById(id_ami);
				Communication c = new CommunicationServeur(ami);
				c.setListeMessage(messages);
				
				// On met à jour la communication de l'ami concerné
				ami.setCommunication(c);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
}
