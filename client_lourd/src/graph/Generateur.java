package graph;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Générateur abstrait, parseur de fichiers xml
 */
public abstract class Generateur 
{
    /**
     * Méthode généraliste de parsage
     * @param fichier Fichier à parser
     * @param handler Handler à utiliser
     */
    protected static void parse(File fichier, DefaultHandler handler)
    {
        try 
        {
            SAXParserFactory saxPF = SAXParserFactory.newInstance();
            SAXParser parseur = saxPF.newSAXParser();
            
            parseur.parse(fichier, handler);
        } 
        catch(ParserConfigurationException ex) 
        {
            Logger.getLogger(Generateur.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
        	Logger.getLogger(Generateur.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(Generateur.class.getName()).log(Level.SEVERE, null, ex);
		}
    }    
}
