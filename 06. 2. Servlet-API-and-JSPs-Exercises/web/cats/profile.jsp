<%@ page import="java.util.List" %>
<%@ page import="org.softuni.fdmc.data.models.Cat" %>
<%@ page import="org.softuni.fdmc.data.repos.CatRepository" %>
<%@ page import="org.softuni.fdmc.data.models.Order" %>
<%@ page import="org.softuni.fdmc.data.models.User" %>
<%@ page import="org.softuni.fdmc.data.repos.UserRepository" %>
<%@ page import="org.softuni.fdmc.data.repos.OrderRepository" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Cat Profile</title>
    <link rel="stylesheet" type="text/css"
          href="<c:url value="${pageContext.request.contextPath}/resources/css/bootstrap.min.css"/>"/>
</head>
<body>
<div class="container">
    <% String catName = request.getParameter("catName"); %>
    <% String userName = (String) request.getSession().getAttribute("username"); %>
    <% Cat cat = ((CatRepository) application.getAttribute("cats")).getByName(catName); %>

    <% if (cat != null) { %>

    <h1>Cat - <%=cat.getName()%>
    </h1>
    <hr/>
    <h3>Breed: <%= cat.getBreed()%>
    </h3>
    <h3>Color: <%= cat.getColor()%>
    </h3>
    <h3>Number Of Legs: <%= cat.getNumberOfLegs()%>
    </h3>
    <h3>Views: <%=cat.increaseViewsCount()%>
    </h3>
    <h3>Creator: <%= cat.getCreator().getUsername()%>
    </h3>
    <% } else { %>
    <h1>Cat, with name <%=catName%> was not found.</h1>
    <% } %>

    <hr/>
    <form method="post" action="/orders/order">
        <input type="hidden" name="catName" id="catName" value="<%=catName %>">
        <input type="hidden" name="userName" id="userName" value="<%=userName %>">

        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button type="submit" class="btn btn-secondary">Order</button>
            </div>
        </div>
    </form>
    <hr/>
    <a href="/cats/all" class="btn btn-outline-primary" role="button">Back to All Cats</a>
</div>
</body>
</html>
