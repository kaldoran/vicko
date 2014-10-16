<!-- Auteur : Reynaud Nicolas --> 

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <form action="Recherche.jsp" method="POST">
    <div id="head-top">
        <div id="head-global">
            <a href="Index.jsp"><span id="logo_container"><span id="logo"></span></span></a>
            <div id="searchbar">
                <input type="text" title="Rechercher" value="" name="recherche" />
                <input type="submit" title="" value="" />
            </div>
            <div id="user">
            	<% if ( Rang == 1) {%>
               		<button class="connexion" type="button" onclick=";self.location.href='Connexion.jsp';return false;">Connexion</button>
                <% } else { %>
                	<button class="connexion" type="button" style="width: 80px;" onclick=";self.location.href='Deconnexion.jsp';return false;">Deconnexion</button>
            	<% } %>
            </div>
        </div>
    </div>
    </form>