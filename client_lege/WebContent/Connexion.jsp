<!-- Auteur : Reynaud Nicolas --> 

<%@page import="webproject.InfoBdd"%>
<%@page import="webproject.Connect"%>
<%@page import="webproject.ReqVideo"%>
<%@page import="java.sql.ResultSet"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html lang="en">
<head>
    <meta http-equiv="Content-type" content="text/html; charset=UTF-8" />
    <link rel="stylesheet" href="index.css" type="text/css" />
    <title>Connexion - Vicko</title>
</head>

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

<body>
    <%@include file="includes/Recherche.jsp" %>
    
    <div id="body">
        <div id="video">
            <div id="formulaire">
        	<% if ( Rang == 1) {%>
                <form action="Connexion" method="POST">
                    <table>
                        <tr>
                            <td><label for="username_connexion_form">Nom d'utilisateur :</label></td>
                            <td><input type="text" name="username" id="username_connexion_form" /></td>
                        </tr>
                        <tr>
                            <td><label for="password_connexion_form">Mot de passe :</label></td>
                            <td><input type="password" name="password" id="password_connexion_form" /></td>
							<td><input type="hidden" id ="type" name="type" value="web"><br /></td>
                        </tr>
                        <tr>
                            <td style="text-align: center;"><input type="checkbox" name="rester_connecte" id="rester_connecte" /></td>
                            <td><label for="rester_connecte">Se souvenir de moi</label></td>
                        </tr>
                        <tr><td colspan="2" style="text-align: center;"><input type="submit" value="Se connecter" name="send" /></td></tr>
                        <tr><td colspan="2" style="text-align: center;"><a href="Inscription.jsp">S'inscrire ?</a></td></tr>
                    </table>
                </form>
            <%} else {%>
            	<p>Vous êtes déjà connecté.</p>
            <%}%>
            </div>
            <div id="copyright">[Reynaud Nicolas] ~ Copyright : 2014 - Vicko - Partage de video - Tous droits reserves.</div>
        </div>
    </div>
    
	<%@include file="includes/Menu.jsp" %>
	
</body>
</html>
