package serveur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;

/**
 * Classe utilitaire pour traiter les IP
 *
 */
public final class IP {
	
	
	private IP () {}
	
	/**
	 * Retourne l'ip dans une string
	 * @return L'ip dans une string
	 * @throws Exception Si impossibilité de lire l'ip
	 */
	public static String getPublicIp() throws Exception {
        URL whatismyip = new URL("http://ip.cpy.re");
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(
                    whatismyip.openStream()));
            String ip = in.readLine();
            return ip;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
	
	
	/**
	 * Retourne l'adresse ip locale du client
	 * @return L'adresse ip locale du client (String)
	 * @throws UnknownHostException Levée si host inconnu (sans blague...)
	 */
	public static String getLocalIp() throws UnknownHostException {
		return InetAddress.getLocalHost().getHostAddress();
	}
}
