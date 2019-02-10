<%@ page import="java.util.List" %>
<%@ page import="org.softuni.java_ee_block.data.models.Tube" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: valch
  Date: 28-Jun-18
  Time: 16:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Java EE Block</title>
</head>
<body>

<h1>All tubes</h1>
<hr/>

<% List<Tube> tubes = new ArrayList<Tube>(){{
    add(new Tube("Feel it Steel", "Some cool new Song…", 5, "Prakash"));
    add(new Tube("Despacito", "No words … Just … No!", 250, "Stamat"));
    add(new Tube("Gospodari Na Efira – ep. 25", "Mnogo smqh imashe tuka…", 3, "Trichko"));
}} ;

application.setAttribute("tubes", tubes);

%>

<% for (Tube tube : tubes) {%>
<a href="/tubes/details.jsp?title=<%=tube.getTitle()%>"><%=tube.getTitle()%></a><br><br>
<% } %>




</body>
</html>
