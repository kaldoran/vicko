package graph;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JPanel;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import serveur.API;

/**
 * Utilitaire permettant de générer le profil utilisateur de l'application
 * et de charger les informations d'un membre
 */
public final class GenerateurMembre extends Generateur
{
    // Constructeur privé, la classe sert de boite à outils au même titre que la classe Math
    private GenerateurMembre() {};
   
    // Handler interne
    public static class Handler extends DefaultHandler
    {        
        // Infos du membre
        private String pseudo;
        private String description;
        private String sexe; 
        private String email;
        private int nb_article;
        private int nb_commentaire;
        private String date_inscription;
        
        // Liste des états
        private final int IN_PSEUDO = 0;
        private final int IN_DESCRIPTION = 1;
        private final int IN_SEXE = 2;
        private final int IN_EMAIL = 3;
        private final int IN_NB_ARTICLE = 4;
        private final int IN_NB_COMMENTAIRE = 5;
        private final int IN_DATE = 6;

        // Etat
        int etat = -1;
        
        // Type de parsage
        private final int STD = 0;
        private final int UTILISATEUR = 1;
        
        private final int type;
        
        // Résultat final du parsage
        private JPanel panel;
        
        /**
         * Constructeur de l'handler
         * @param panel Si null alors il s'agit d'informations sur son propre compte sinon
         * il s'agit d'infos sur un compte membre
         */
        public Handler(JPanel panel) 
        {
            if(panel != null) {
                this.type = STD;
                this.panel = panel;
            }  else
                this.type = UTILISATEUR;
        }
        
        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes)
        {
            switch(qName) 
            {
                case "pseudo":           etat = IN_PSEUDO;         break;  
                case "description":      etat = IN_DESCRIPTION;    break;
                case "sexe":             etat = IN_SEXE;           break;
                case "email":            etat = IN_EMAIL;          break;
                case "nb_article":       etat = IN_NB_ARTICLE;     break;
                case "nb_commentaire":   etat = IN_NB_COMMENTAIRE; break;
                case "date_inscription": etat = IN_DATE;           break;
            }
        }
        
        @Override
		public void characters(char[] ch, int start, int length)
		{
	            String chaine = new String(ch, start, length);
	            switch(etat) 
	            {
	                case IN_PSEUDO:         pseudo           = chaine; break;  
	                case IN_DESCRIPTION:    description      = chaine; break;
	                case IN_SEXE:           sexe             = chaine; break;
	                case IN_EMAIL:          email            = chaine; break;
	                case IN_DATE:           date_inscription = chaine; break;
	
	                case IN_NB_ARTICLE:     nb_article       = Integer.parseInt(chaine); break;
	                case IN_NB_COMMENTAIRE: nb_commentaire   = Integer.parseInt(chaine); break;
	            }
		}
        
        @Override
        public void endDocument()
        {
            // Membre
            if(type == STD) {
                try {
                	((Membre)panel).setMembre(pseudo, description, type, nb_article, nb_commentaire, date_inscription, email);
                } catch (NullPointerException e) {
                	System.out.println("ps = " + pseudo +"\ndesc="+ description +"\ntype="+ type +"\nnba="+ nb_article +"\nnbc="+ nb_commentaire  +"\ndate="+  date_inscription +"\nemail="+ email);
                	panel = null;
                	System.err.println (e.getStackTrace());
                }
            // Mon profil
            } else {
            	if (sexe.equals("M"))
            		panel = new MonProfil(pseudo, email, description, date_inscription, MonProfil.HOMME);
            	else
            		panel = new MonProfil(pseudo, email, description, date_inscription, MonProfil.FEMME);
            }
        }
        
        // Résultat du parsage...
        public JPanel getResultat()
        {
            return panel;
        }
    }
    
    /** Permet de remplir un membre.
     * 
     * @param membre Membre à remplir
     * @param pseudo Pseudo du membre
     */
    public static void setMembre(Membre membre, String pseudo)
    {
        Handler handler = new Handler(membre);
        String MembreXML = API.postMembre(pseudo);
        File xml = new File("MembreXMLTEMP");
        
        try {
            FileWriter fw = new FileWriter (xml);
         
            fw.write (MembreXML);
            fw.write ("\n");
         
            fw.close();
        } catch (IOException exception) {
            System.out.println (exception.getMessage());
        }
        parse(xml, handler);
        xml.delete();
    }
    
    /** Permet de remplir son propre profil.
     * 
     * @param pseudo Informations du pseudo
     * @return Un profil d'un certain pseudo
     */
    public static MonProfil getProfil(String pseudo)
    {
        Handler handler = new Handler(null);
        
        // Récupération du fichier xml
        String ProfilXML = API.postMembre(pseudo);
        File xml = new File("ProfilXMLTEMP");
        try {
            FileWriter fw = new FileWriter (xml);
         
            fw.write (ProfilXML);
            fw.write ("\n");
         
            fw.close();
        } catch (IOException exception) {
            System.out.println (exception.getMessage());
        }
        parse(xml, handler);
        xml.delete();
        return (MonProfil)handler.getResultat();
    }
}
