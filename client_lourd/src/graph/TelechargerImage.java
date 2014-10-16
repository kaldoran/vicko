package graph;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe permettant de télécharger une image.
 */
public final class TelechargerImage 
{
    // Utilitaire, pas d'utilité d'avoir un constructeur public
    private TelechargerImage() {}
    
    /**
     * Télécharge une image
     * @param lien Source de l'image
     * @param destination Destination locale
     */
    public static void getImage(String lien, String destination)
    {
        URL url;
        InputStream is;
        OutputStream os;

        byte[] b = new byte[1024];
        int c;
        
        try 
        {
            url = new URL(lien);
            is = url.openStream();
            os = new FileOutputStream(destination);
            
            while((c = is.read(b)) != -1)
                os.write(b, 0, c);
            
            is.close();
            os.close();
        } 
        catch (MalformedURLException ex) 
        {
            Logger.getLogger(TelechargerImage.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(TelechargerImage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
