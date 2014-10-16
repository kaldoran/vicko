package graph;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import serveur.API;

/**
 * Utilitaire permettant la création de liste de vidéos.
 * Que ce soit des listes Std ou Utilisateur.
 */
public final class GenerateurVideoListe extends Generateur
{
    /**
     * Constante permettant de demander la génération d'une liste normale.
     */
    public static final int STD = 0;
    
    /**
     * Constante permettant de demander la génération d'une liste utilisateur.
     */
    public static final int UTILISATEUR = 1;

    // Constructeur privé, la classe sert de boite à outils au même titre que la classe Math
    private GenerateurVideoListe() {};
   
    /**
     * Répertoire des images issues du xml.
     */
    public static final String DOSSIER_IMAGES = "imgs/";
   
    // Handler interne
    public static class Handler extends DefaultHandler
    {
        // La liste de vidéos
        private VideoListe liste;
      
        // Infos de la vidéo courante
        private int id;
        private String titre;
        private String auteur; 
        private String date;
        private String img;
        
        // Liste des états
        private final int IN_TITRE = 0;
        private final int IN_LIEN = 1;
        private final int IN_DATE = 2;
        private final int IN_AUTEUR = 3;

        // Etat
        int etat = -1;
        
        /**
         * Constructeur de l'handler
         * @param type Type de liste
         */
        public Handler(int type)
        {            
            // Création de la liste souhaitée
            if(type == GenerateurVideoListe.STD)
                liste = new VideoListeStd();
            else
                liste = new VideoListeUtilisateur();
        }
        
        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes)
        {
            switch (qName) 
            {
                // Nouvelle vidéo
                case "video":
                    id = Integer.parseInt(attributes.getValue("id_video"));
                    break;
                    
                // Infos de la vidéo
                case "titre":
                    etat = IN_TITRE;
                    break;
                case "lien_miniature":
                    etat = IN_LIEN;
                    break;
                case "date":
                    etat = IN_DATE;
                    break;
                case "auteur":
                    etat = IN_AUTEUR;
                    break;
            }
	}

        @Override
        public void endElement(String uri, String localName, String qName)
	{
            // Ajout de la vidéo dans la liste
            if(qName.equals("video"))
                liste.ajouterVideo(id, titre, auteur, date, img);
	}
        
        @Override
	public void characters(char[] ch, int start, int length)
	{
            String chaine = new String(ch, start, length);
            
            switch(etat)
            {
                case IN_TITRE:
                    titre = chaine;
                    break;
                case IN_LIEN:
                    img = DOSSIER_IMAGES + 
                          chaine.substring(chaine.lastIndexOf("/") + 1, chaine.length() - 1);
                    
                    File f = new File(img);

                    // Si l'image n'existe pas, on la télécharge
                    if(!f.exists())
                        TelechargerImage.getImage(chaine, img);
                   
                    break;
                case IN_DATE:
                    date = chaine;
                    break;
                case IN_AUTEUR:
                    auteur = chaine;
                    break;
            }
	}
        
        // Obtenir la liste de vidéos parsée
        public VideoListe getVideoListe()
        {
            return liste;
        }
    }
    
    /**
     * Permet d'obtenir une liste de vidéos.
     * @param type Type de liste : STD ou UTILISATEUR
     * @param requete Requête à transmettre au serveur selon ce format :
     * ":NomUtilisateur" ou "ensemble de mots" concaténé à une lettre :
     * - p : Popularité +
     * - m : Popularité -
     * - r : Plus récent
     * - n : Moins récent
     * @return 
     */
    public static VideoListe getVideoListe(int type, String requete)
    {
        Handler handler = new Handler(type);
        
        
        char type_recherche = requete.charAt(0);
        String recherche = requete.substring(1);
        String VideoListeXML;
        
        switch (type_recherche) {
			case ':':
				VideoListeXML = API.postRecherche(recherche, ":");;
			break;
			
			default:
				VideoListeXML = API.postRecherche(recherche, Character.toString(type_recherche));
			break;
		}

        File xml = new File("VideoListeXMLTEMP");
        try {
            FileWriter fw = new FileWriter (xml);
         
            fw.write (VideoListeXML);
            fw.write ("\n");
         
            fw.close();
        } catch (IOException exception) {
            System.out.println (exception.getMessage());
        }
        parse(xml, handler);
        xml.delete();
        
        return handler.getVideoListe();
    }
}
