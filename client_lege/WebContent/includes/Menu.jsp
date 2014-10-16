<!-- Auteur : Reynaud Nicolas --> 

<%@page import="webproject.ReqPlaylist"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <div id="menu">
    	<h1>Link</h1>
        <ul class="playlist">
            <li><a href="Index.jsp">Accueil</a></li>
            <li><a href="Top.jsp">Top</a></li>
            <li><a href="Flop.jsp">Flop</a></li>
            <li><a href="<%= "Video.jsp?id="+id_rand %>">Rand</a></li>
            <% if ( Rang != 1) {%>
            	<li><a href="AjoutVideo.jsp">Ajout video</a></li>
            <%}%>
        </ul>
        <hr class="separator">
        <% if ( Rang != 1) {%>
       		<h1>Mes favoris</h1><a href="<%= "Sauvegarde?pseudo="+Pseudo %>"><span class="fav"></span></a>
        	<ul class="playlist">
        		<% rs = ReqPlaylist.getPlaylist(c, Pseudo);
        			if (!rs.isBeforeFirst() ) {   
        		%>
        			<li><a href="Playlist.jsp">Aucune Playlist</a></li>
        		<%
        			} else {
	        			while (rs != null && rs.next()) {
	        		%>
            			<li><a href="<%= "Video.jsp?id="+rs.getString("IdA")%>"><%= rs.getString("Titre")%></a></li>
            		<%
            			}
            		}
            		%>
       		</ul>
        	<hr class="separator">
        <%}%>
        <% if ( Rang != 1) { %>
       		<h1><a href="<%= "Profil.jsp?id="+GetSessionAtt(session,"Id")%>">Mon profil</a></h1>
       	<% } else { %>
       		<h1>Mon profil</h1>
       	<%}%>
        <img src="images/profil.gif" style="" height="130" width="130">
        <ul class="playlist">
            <li class="profil">
            <% switch (Rang)
            {
             case 5:
             	out.print("Super Admin");
             break;
             case 4:
             	out.print("Admin");
             break;
             case 3:
             	out.print("Modérateur");
             break;
             case 2:
             	out.print("Vickdéoeur");
             break;
             default:
             	out.print("Bienvenu Visiteur");
             break;
            }
            %>
            </li>
            <li></li>
        	<% if ( Rang > 1) {%>
            	<li class="profil">Pseudo : <%= Pseudo %></li>
            	<li class="profil">Date inscription : <%= Date %></li>
            <% } %>
        </ul>
    </div>