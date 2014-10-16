<!-- Auteur : Reynaud Nicolas --> 

<%@page import="webproject.InfoBdd"%>
<%@page import="webproject.Connect"%>
<%@page import="webproject.ReqMembre"%>
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
    private String Id = "0";
    private boolean edit = false;
    
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
	
	Pseudo = GetSessionAtt(session,"Pseudo");
	Date = GetSessionAtt(session,"Date");
		
%>
<body>
    <%@include file="includes/Recherche.jsp" %>

    <div id="body">
        <div id="video">
			<div id="formulaire">
			    <% rs = ReqMembre.getMembre(c, request.getParameter("id"), "");
        			if (rs != null && rs.next()) {   
        				/* si on est ici c'est que le parametre n'est pas null */
        				if (request.getParameter("id").equals( GetSessionAtt(session,"Id")))
        					edit = true;
        		%>
				<form action="UpdateMembre" method="POST">
				<img src="images/profil.gif" style="margin-left: 150px; position: absolute;" height="130" width="130">
                    <table>
                        <tr>
                            <td>Pseudo : </td>
                            <td class="bleu"><%= rs.getString("Pseudo")%></td>
                        </tr>
                        <tr>
                            <td>Email : </td>
                            <td class="bleu"><%= rs.getString("Email")%></td>
                        </tr>
                        <tr>
                            <td>Sexe : </td>
                            <td class="bleu"><%= rs.getString("Sexe")%></td>
                        </tr>
                        <tr>
                            <td>Date inscription : </td>
                            <td class="bleu"><%= rs.getString("Date") %></td>
                        </tr>
                     	<tr>
                            <td>Nombre article : </td>
                            <td class="bleu"><%= rs.getString("Nb_article") %></td>
                        </tr>
                        <tr>
                            <td>Nombre commentaire : </td>
                            <td class="bleu"><%= rs.getString("Nb_commentaire") %></td>
                        </tr> 

                        <%if ( edit == true ) {%>
	                        <tr>
	                            <td>Description : </td>
	                            <td class="bleu" ><textarea><%= rs.getString("Description") %></textarea></td>
	                        </tr>
                        <%} else { %>
	                        <tr>
	                            <td>Description : </td>
	                            <td class="bleu" ><%= rs.getString("Description") %></td>
	                        </tr>
                        <%}%>
                        <%if ( edit == true ) {%>
                        	<tr><td colspan="2" style="text-align: center;"><input type="submit" value="Envoyer" name="send" /></td></tr>
                    	<%}%>
                    </table>
                    <input type="hidden" name="type" value="nav"/>

                </form>
                <%} else { %>
                	<h1>Utilisateur introuvable .... Un caiman à du le manger :x</h1>
                <%}%>
 			</div>
            <div id="copyright">[Reynaud Nicolas] ~ Copyright : 2014 - Vicko - Partage de video - Tous droits reserves.</div>
        </div>
    </div>
    
    <%@include file="includes/Menu.jsp" %>

</body>
</html>