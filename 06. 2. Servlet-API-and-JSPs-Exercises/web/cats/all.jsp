<%@ page import="java.util.List" %>
<%@ page import="org.softuni.fdmc.data.models.Cat" %>
<%@ page import="org.softuni.fdmc.data.repos.CatRepository" %>
<%@ page import="java.util.Set" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>All Cats</title>
    <link rel="stylesheet" href="bootstrap.min.css">
    <link rel="stylesheet" type="text/css"
          href="<c:url value="${pageContext.request.contextPath}/resources/css/bootstrap.min.css"/>"/>
</head>
<body>
<div class="container">
    <h1 class="text-center">All Cats</h1>
    <hr/>
    <% CatRepository cats = (CatRepository) application.getAttribute("cats"); %>
    <% List<Cat> sortedByViews = cats.sortByViews(); %>

    <%if (sortedByViews.size() == 0) {%>
    <h2 class="text-danger">There are no cats!</h2>
    <br/>
    <a href="/cats/create" class="btn btn-secondary" role="button">Create Cat</a>
    <%} else {%>
    <%} int i = 1; {%>
    <% for (Cat cat : sortedByViews) { %>
    <h3 class="text-success">
        <%=i++ %>. <a  href="/cats/profile?catName=<%= cat.getName()%>"><%= cat.getName()%>
        </a>
    </h3>
    <% } %>
    <%}%>
    <br/><hr/><br/>
    <a href="/" class="btn btn-outline-primary" role="button">Back to Home</a>
</div>
</body>
</html>
