<!-- Auteur : Reynaud Nicolas --> 

<%@page import="webproject.ReqVote"%>
<%@page import="webproject.InfoBdd"%>
<%@page import="webproject.Connect"%>
<%@page import="java.sql.ResultSet"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%!
	private String id_article;
	private String type;
	private String id_membre;
	private Connect c;
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
%>
<% 
	c = new Connect(InfoBdd.LIEN, InfoBdd.PSEUDO, InfoBdd.MDP);

	id_membre = GetSessionAtt(session, "Id");
	id_article = request.getParameter("id_article");
	type = request.getParameter("type");
	if ( id_membre != null && id_membre != "" && id_article != "" ) {
		if ( type.equals("m") )
			ReqVote.setVote(c, id_membre, id_article, false);
		if ( type.equals("p") )
			ReqVote.setVote(c, id_membre, id_article, true);
	}
	
	response.sendRedirect("Video.jsp?id="+id_article);
%>