<!-- Auteur : Reynaud Nicolas --> 

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
if ( session.getAttribute("Rang") != null) {  
	System.out.print("ici");
    session.invalidate();
    response.sendRedirect("Index.jsp");
    return;
}
response.sendRedirect("Index.jsp");
%>