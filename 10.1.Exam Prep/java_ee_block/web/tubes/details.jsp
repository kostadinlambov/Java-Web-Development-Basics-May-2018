<%@ page import="org.softuni.java_ee_block.data.models.Tube" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: valch
  Date: 28-Jun-18
  Time: 16:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Java EE Block</title>
</head>
<body>

<% String title = request.getParameter("title"); %>
<% List<Tube> tubes = (List<Tube>) application.getAttribute("tubes"); %>

<% Tube tube = tubes.stream().filter(x -> x.getTitle().equals(title)).findFirst().orElse(null); %>


<h3>Tube: <%=tube.getTitle()%></h3>
<h3>Description: <%=tube.getDescription()%></h3>
<h3>Views: <%=tube.getViews()%></h3>
<h3>Uploader: <%=tube.getUploader()%></h3>

<a href="/tubes/all.jsp">Back to All Tubes</a>

</body>
</html>
