<!-- Auteur : Reynaud Nicolas --> 

<%@page import="webproject.ReqCommentaire"%>
<%@page import="webproject.ReqPlaylist"%>
<%@page import="webproject.InfoBdd"%>
<%@page import="webproject.Connect"%>
<%@page import="webproject.ReqVideo"%>
<%@page import="java.sql.ResultSet"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%!
	private String GetSessionAtt(HttpSession session, String attName) {
		Object att = session.getAttribute(attName);
		if (session != null) {
			if (att != null) {
				return att.toString();
			}
		}
		return "";
	}
	private String id;
	private Connect c;
	private ResultSet rs;%>

<%
	id = request.getParameter("id");
	c = new Connect(InfoBdd.LIEN, InfoBdd.PSEUDO, InfoBdd.MDP);
	System.out.println(ReqPlaylist.setPlaylist(c, GetSessionAtt(session,"Id"), id));
	response.sendRedirect("Video.jsp?id=" + id);
%>