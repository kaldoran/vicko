<!-- Auteur : Reynaud Nicolas --> 

<%@page import="webproject.InfoBdd"%>
<%@page import="webproject.Connect"%>
<%@page import="webproject.ReqVideo"%>
<%@page import="webproject.ReqRecherche"%>
<%@page import="java.sql.ResultSet"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
    private String recherche  = "";
    
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
	recherche = request.getParameter("recherche");
	rs = ReqRecherche.recherche(c, recherche, Integer.toString(ReqVideo.DATEC) );
	
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
        	<%@include file="includes/AfficheVideo.jsp" %>

            <div id="multi_page">
                <ul>
                    <li><a href="#">Precedent</a></li>
                    <li><a href="?page=1">1</a></li>
                    <li><a href="?page=2">2</a></li>
                    <li><a href="#">Suivant</a></li>
                </ul>
            </div>
            <div id="copyright">[Reynaud Nicolas] ~ Copyright : 2014 - Vicko - Partage de video - Tous droits reserves.</div>
        </div>
    </div>
    
    <%@include file="includes/Menu.jsp" %>

</body>
</html>