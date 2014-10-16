/**
 * Contient les requêtes associées aux votes
 * @author Reynaud Nicolas
 */

package webproject;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReqVote {
	
	public static boolean existsVote(Connect c, String id_membre, String id_article) {
		ResultSet rs;
		if ( id_article == null ) id_article = "";
		if ( id_membre == null ) id_membre = "";
		
		if ( id_article.isEmpty() || id_article.isEmpty() )
			return false;
		
		try {
			rs = c.reqSelect("SELECT COUNT(*) As Total FROM Votes WHERE id_membre_vote = "+ id_membre +" AND id_article_vote = "+ id_article );
			if (rs.next()) 
					return rs.getInt("Total") == 0;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;

	}
	
	public static boolean setVote(Connect c, String id_membre, String id_article, boolean increment) {
		
		if ( id_membre.isEmpty() || id_article.isEmpty() )
			return false;
		
		if ( existsVote(c, id_membre, id_article))	{	
			if ( c.reqChange("INSERT INTO Votes (`id_membre_vote`, `id_article_vote`, `date_vote`) VALUES ('"+ id_membre +"', '"+ id_article +"', CURRENT_DATE());")  > 0) {
				if ( increment )
					return c.reqChange("UPDATE Articles SET vote_article = vote_article + 1 WHERE id_article ="+id_article) > 0;
				else 
					return c.reqChange("UPDATE Articles SET vote_article = vote_article - 1 WHERE id_article = "+id_article) > 0;
			}
		}

		return false;
	}
	
	public static boolean setVote(Connect c, String id_membre, String id_article, String increment) {
		if ( Integer.parseInt(increment) > 0 )
			return setVote(c, id_membre, id_article, true);
		else
			return setVote(c, id_membre, id_article, false);
	}
}
