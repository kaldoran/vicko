<!-- Auteur : Reynaud Nicolas --> 

<%@page import="webproject.ReqCommentaire"%>
<%@page import="webproject.ReqPlaylist"%>
<%@page import="webproject.ReqVote"%>

<%@page import="webproject.InfoBdd"%>
<%@page import="webproject.Connect"%>
<%@page import="webproject.ReqVideo"%>
<%@page import="java.sql.ResultSet"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
    <meta http-equiv="Content-type" content="text/html; charset=UTF-8" />
    <link rel="stylesheet" href="index.css" type="text/css" />
    <title>Video - Vicko</title>
<%! private Integer Rang = 1;
    private String Pseudo = "";
    private String Date = "";
    private String Idvideo = "";
    private String Id_membre = "";
    private int id_rand = 0;
    private int Total = 0;
    
    private String GetSessionAtt (HttpSession session, String attName) {
    	Object att = session.getAttribute(attName);
    	if (session != null)
 		{
    		if (att != null) {
    			return att.toString();
    		}
 		}
		return "";
    }
    private boolean dejavote = true;
    private boolean dejafavo = true;
    private Connect c;
	private ResultSet rs;  
	
%>
    
<% 
	c = new Connect(InfoBdd.LIEN, InfoBdd.PSEUDO, InfoBdd.MDP);
	Idvideo = request.getParameter("id");
	id_rand = ReqVideo.randVideo(c);
	Total = ReqCommentaire.getNbCommentaire(c, Idvideo);
	
	try {
		Rang = Integer.valueOf(GetSessionAtt(session,"Rang"));
	} catch (Exception e) {
		Rang = 1;
	}
	Pseudo = GetSessionAtt(session,"Pseudo");
	Date = GetSessionAtt(session,"Date");
	Id_membre = GetSessionAtt(session,"Id");


	if ( Rang != 1) {
		dejavote = ReqVote.existsVote(c, Id_membre, Idvideo);
		dejafavo = ReqPlaylist.existsPlaylist(c, Id_membre, Idvideo);
	}
	
	
	rs = ReqVideo.getVideo(c, Idvideo);
	
%>
</head>
<body>
    <%@include file="includes/Recherche.jsp" %>
    
    <div id="body">
        <div id="video">
 			<%  if(rs != null && rs.next()) { %>
            	<div id="titre"><%= rs.getString("Titre") %><span class="vote"><%= rs.getString("Nb_vote") %> vote(s)</span></div>
            	<iframe width="640" height="360" class="display_video" src="<%= "https://www.youtube.com/embed/"+ rs.getString("Lien") %>" frameborder="0" allowfullscreen></iframe>
            	
            	<% if ( Rang != 1) {%>
	            	<div id="action">
	            		<span class="votes">
	            		    <%  if(dejavote) { %>
	            				Vote : <a href="Vote.jsp?id_article=<%=Idvideo%>&type=p"><img src="images/plus.png" height="16" width="16"></a>  <a href="Vote.jsp?id_article=<%=Idvideo%>&type=m"><img src="images/moins.png" height="16" width="16"></a>
	            			<%  } else {%>
	            				Déja voté !
	            			<%  } %>
	            		</span>
	            		<span class="playlistes">
	            			<%  if(dejafavo) { %>
	            			<a href="Favoris.jsp?id=<%=Idvideo%>">Ajouter aux favoris</a>
	            			<%  } else {%>
	            				Déjà en favoris !
	            			<%  } %>
	            		</span>
	            		<div class="clear"></div>
	            	</div>
            	<%  } %>
            	
            	<div id="description">
                	<span class="date"><span class="green">Date </span>: <%= rs.getString("Date") %></span>
                	<span class="auteur"><span class="green">Auteur </span>: <%= rs.getString("Auteur") %></span>
                	<div class="clear"></div>
                	<p class="infos">
                		<%= rs.getString("Texte") %>
                	</p>
           		</div>

        	<div id="formulaire_commentaires">
        		<form action="EnvoiCommentaire" method="POST">
        			 <table>
						<tr>
							<td><label for="pseudo">Pseudo :</label>
							</td>
							<td><input type="text" name="pseudo" value="<%= Pseudo %>" readonly="readonly"></td>
						</tr>
						<tr>
                            <td><label for="coms">Commentaire :</label></td>
                            <td><textarea name="commentaire" id="coms"></textarea></td>
                        </tr>
                       	<tr><td colspan="2" style="text-align: center;"><input type="submit" value="Envoyer" name="send" /></td></tr>

                	</table>
					<input type="hidden" name="id_membre" value="<%= Id_membre %>" > 
					<input type="hidden" name="id_article" value="<%= Idvideo %>" >
					<input type="hidden" name="type" value="nav" >
				</form>
        	</div>
        	<%  } else { %>
        		<div id="titre">Aucune vidéo avec cet Id<span class="vote">X vote(s)</span></div>
        	<%  } %>
        	<% if ( Total != 0) { %>
	        	<div id="commentaire">
	        	<% rs = ReqCommentaire.getCommentaire(c, Idvideo);
	        		while (rs != null && rs.next()) {
	        	%>
					<p class="infos">
						<a target="_blank" href="<%= "Profil.jsp?id="+rs.getString("IdM")%>"><%= rs.getString("Auteur") %></a> : 
						<%= rs.getString("Texte") %>
					</p>
					<% if ( --Total > 0) { %>
						<hr class="separator">
					<%  } %>
				<%  } %>
				</div>
			<%  } %>
        	<div id="copyright">[Reynaud Nicolas] ~ Copyright : 2014 - Vicko - Partage de video - Tous droits reserves.</div>
        </div>
    </div> 
    
	<%@include file="includes/Menu.jsp" %>

</body>
</html>