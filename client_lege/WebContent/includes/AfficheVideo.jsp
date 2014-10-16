<!-- Auteur : Reynaud Nicolas --> 

<ul class="miniature">
	<%  if ( !rs.isBeforeFirst()) {
	%>
        		<div id="titre">Aucune vidéo trouvée avec cette recherche<span class="vote">X vote(s)</span></div>
    <%
		}
		while(rs != null && rs.next()) { 
	%>
    	<li><span class="nbvote"><%= rs.getString("Nb_vote") %><img src="images/like.png" height="15" width="19"></span><a href="<%= "Video.jsp?id="+ rs.getString("Id") %>"><img class="mini" src="<%= rs.getString("Lien") %>"><span class="titre"><%= rs.getString("Titre") %></span></a></li>
	<%  } %>
</ul>