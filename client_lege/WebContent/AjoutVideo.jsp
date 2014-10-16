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
    <title>Index - Vicko</title>
</head>
<%! private Integer Rang = 1;
    private String Pseudo = "";
    private String Date = "";
    private int id_rand = 0;
    private String Id_membre = "";
    
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
    private Connect c;
	private ResultSet rs;    
%>
    
<% 
	c = new Connect(InfoBdd.LIEN, InfoBdd.PSEUDO, InfoBdd.MDP);
	id_rand = ReqVideo.randVideo(c);

	rs = ReqVideo.getAllVideo(c, ReqVideo.DATEC);
	try {
		Rang = Integer.valueOf(GetSessionAtt(session,"Rang"));
	} catch (Exception e) {
		Rang = 1;
	}
	Id_membre = GetSessionAtt(session,"Id");
	Pseudo = GetSessionAtt(session,"Pseudo");
	Date = GetSessionAtt(session,"Date");
		
%>
<body>
    <%@include file="includes/Recherche.jsp" %>

    <div id="body">
        <div id="video">
			<div id="formulaire">
				<form action="EnvoiVideo" method="POST">
                    <table>
                        <tr>
                            <td><label for="title">*Titre :</label></td>
                            <td><input type="text" class="form" id="title" name="titre" value=""/></td>
                        </tr>
                        <tr>
                            <td><label for="lien">*Lien :</label></td>
                            <td><input type="text" class="form" id="lien" name="lien" /></td>
                        </tr>
                        <tr>
                            <td><label for="lien_miniature">Lien de la miniature :</label></td>
                            <td><input type="text" class="form" id="lien_miniature" name="lien_miniature" /></td>
                        </tr>
                        <tr>
                            <td><label for="texte">Texte :</label></td>
                            <td><textarea class="form" id="texte" name="texte"></textarea></td>
                        </tr>
                        <tr><td colspan="2" style="text-align: center;"><input type="submit" value="Envoyer" name="send" /></td></tr>
                    </table>
                    <input type="hidden" name="type" value="nav"/>
                    <input type="hidden" name="id_membre" value="<%= Id_membre %>"/>
                </form>
 			</div>
            <div id="copyright">[Reynaud Nicolas] ~ Copyright : 2014 - Vicko - Partage de video - Tous droits reserves.</div>
        </div>
    </div>
    
    <%@include file="includes/Menu.jsp" %>

</body>
</html>