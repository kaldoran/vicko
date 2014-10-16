<!-- Auteur : Reynaud Nicolas --> 

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
    <title>Inscription - Vicko</title>
    
<%! private Integer Rang = 1;
    private String Pseudo = "";
    private String Date = "";
    private int id_rand = 0;
    
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
	try {
		Rang = Integer.valueOf(GetSessionAtt(session,"Rang"));
	} catch (Exception e) {
		Rang = 1;
	}
	Pseudo = GetSessionAtt(session,"Pseudo");
	Date = GetSessionAtt(session,"Date");
%>
</head>
<body>
    <%@include file="includes/Recherche.jsp" %>
    
    <div id="body">
        <div id="video">
            <div id="formulaire">
                <form action="Inscription" method="POST">
                    <table>
                        <tr>
                            <td><label for="username">Identifiant :</label></td>
                            <td><input type="text" id="username" name="username" value=""/></td>
                        </tr>
                        <tr>
                            <td><label for="password">Mot de passe :</label></td>
                            <td><input type="password" id="password" name="password" /></td>
                        </tr>
                        <tr>
                            <td><label for="password2">Confirmez votre mot de passe :</label></td>
                            <td><input type="password" id="password2" name="password2" /></td>
                        </tr>
                        <tr>
                            <td><label for="email">E-mail :</label></td>
                            <td><input type="text" id="email" name="email" value=""/></td>
                        </tr>
                        <tr>
                            <td>Sexe :</td>
                            <td><input type="radio" id="H" name="sexe" value='M'/> 
                            <label class="lgenre" for="H">Homme</label>
                            <input type="radio" id="F" name="sexe" value='F' /> 
                            <label class="lgenre" for="F">Femme</label></td>
                        </tr>
                        <tr><td colspan="2" style="text-align: center;"><input type="submit" value="Se connecter" name="send" /></td></tr>

                    </table>
                </form>
            </div>
            <div id="copyright">[Reynaud Nicolas] ~ Copyright : 2014 - Vicko - Partage de video - Tous droits reserves.</div>
        </div>
    </div>
    
	<%@include file="includes/Menu.jsp" %>

</body>
</html>