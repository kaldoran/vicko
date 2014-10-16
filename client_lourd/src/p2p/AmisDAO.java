package p2p;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

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
 * Moteur de récupération de la liste des amis du serveur
 *
 */
public class AmisDAO {
	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	DocumentBuilder builder;
	Document doc;
	
	/**
	 * Constructeur, initialise les moteurs de parsage XML
	 */
	public AmisDAO() {
		factory = DocumentBuilderFactory.newInstance();
		factory.setValidating(false);
		
		try {
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Récupère la liste des amis du serveur
	 * @return La liste des amis
	 */
	public ArrayList<Ami> getListeAmis() {
		// Initialisation de la liste des amis
		ArrayList<Ami> amis = new ArrayList<Ami>();
		
		// Récupération de la réponse du serveur
		String reponse = API.getListeAmisP2P();
		
		// Parsage du document
		try {
			doc = builder.parse(new InputSource(new StringReader(reponse)));
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Récupération des amis
		NodeList membres = doc.getElementsByTagName("membre");
		
		// Construction de chaque ami, puis insertion dans la liste
		for (int i=0; i<membres.getLength(); i++) {
			Element ami = (Element) membres.item(i);
			int id = Integer.parseInt(ami.getAttribute("id_membre").toString());
			String pseudo = ami.getTextContent();
			String ip = ami.getAttribute("ip_membre").toString();
			int connecte = Integer.parseInt(ami.getAttribute("connecte").toString());
			
			// IP
			if(connecte == 1)
				amis.add(new Ami(id, pseudo, ip));
			else
				amis.add(new Ami(id, pseudo));
		}
		
		return amis;
	}
}
