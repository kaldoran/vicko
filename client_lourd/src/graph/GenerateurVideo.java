package graph;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import serveur.API;

/**
 * Utilitaire pour générer une vidéo.
 */
public final class GenerateurVideo 
{
    // Utilitaire = constructeur privé
    private GenerateurVideo() {}
        
    private static void setInfos(Document document, Video video)
    {
        // Récupération de la vidéo
        NodeList infos = document.getElementsByTagName("video");
        NodeList childNodes = infos.item(0).getChildNodes();
        Node node;
        // Inutile de récupérer l'id en attribut, on l'a déjà
        // On se contente de remplir l'objet video
        for(int i = 0; (node = childNodes.item(i)) != null; i++) {
        	switch(node.getNodeName())
            {
                case "titre":
                    video.setTitre(node.getTextContent());
                    break;
                case "lien":
                    video.setLien(node.getTextContent());
                    break;
                case "texte":
                    video.setTexte(node.getTextContent());
                    break;
                case "date":
                    video.setDate(node.getTextContent());
                    break;
                case "auteur":
                    video.setAuteur(node.getTextContent());
                    break;
                case "nb_vote":
                    video.setNbVotes(Integer.parseInt(node.getTextContent()));
                    break;
            }
        }
    }
    
    private static void setCommentaires(Document document, Video video)
    {
        // Récupération de la vidéo
        NodeList lCommentaire = document.getElementsByTagName("commentaire");
        Node commentaire;
  
        // Liste de commentaires
        CommentaireListe liste = new CommentaireListe();
        
        // Inutile de récupérer l'id du commentaire (Raisons historiques)
        // On se contente de remplir la liste de commentaires
        for(int i = 0; (commentaire = lCommentaire.item(i)) != null; i++)
        {
            // Infos d'un commentaire
            NodeList lContenu = commentaire.getChildNodes();
            Node contenu;
                    
            String auteur = "";
            String texte = "";
            String date = "";
            
            for(int j = 0; (contenu = lContenu.item(j)) != null; j++)
                switch(contenu.getNodeName())
                {
                    case "auteur":
                        auteur = contenu.getTextContent();
                        break;
                    case "texte":
                        texte = contenu.getTextContent();
                        break;
                    case "date":
                        date = contenu.getTextContent();
                        break;
                }
            
            // On ajoute le commentaire
            liste.ajouterCommentaire(auteur, texte, date);
        }
        
        // On ajoute la liste dans la vidéo
        video.setCommentaires(liste);
    }
    
    public static void setVideo(Video video, int id)
    {
        try
        {
            // Telecharger les fichiers
        	String VideoXML = API.postVideo(Integer.toString(id));
        	String CommsXML = API.postCommentaire(Integer.toString(id));
        	
            // Création du parser
            DocumentBuilderFactory dbf= DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            
            // Lecture des infos de la vidéo découlant de l'id de la vidéo
            File xml = new File("VideoXMLTemp");
            try {
                FileWriter fw = new FileWriter (xml);
             
                fw.write (VideoXML);
                fw.write ("\n");
             
                fw.close();
            } catch (IOException exception) {
                System.out.println (exception.getMessage());
            }
            Document document = db.parse(xml);
            
            // Application des infos de la vidéo
            video.setId(id);
            setInfos(document, video);
            
            // Lecture des commentaires
            xml.delete();
            xml = new File("CommsXMLTemp");
            try {
                FileWriter fw = new FileWriter (xml);
             
                fw.write (CommsXML);
                fw.write ("\n");
             
                fw.close();
            } catch (IOException exception) {
                System.out.println (exception.getMessage());
            }
            document = db.parse(xml);
            xml.delete();
            
            // Application des commentaires
            setCommentaires(document, video);
            
        } catch(ParserConfigurationException pce){
            Logger.getLogger(Generateur.class.getName()).log(Level.SEVERE, null);
        } catch (SAXException pce) {
        	Logger.getLogger(Generateur.class.getName()).log(Level.SEVERE, null);
		} catch (IOException pce) {
			Logger.getLogger(Generateur.class.getName()).log(Level.SEVERE, null);
		}
    }          
}
