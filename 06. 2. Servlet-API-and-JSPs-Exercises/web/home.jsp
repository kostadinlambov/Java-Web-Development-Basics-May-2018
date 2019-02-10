<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>FDMC</title>
    <link rel="stylesheet" href="bootstrap.min.css">
    <link rel="stylesheet" type="text/css"
          href="<c:url value="${pageContext.request.contextPath}/resources/css/bootstrap.min.css"/>"/>
</head>
<body>
<div class="container text-center">
    <h1>Welcome to Fluffy Duffy Munchkin Cats!</h1>
    <%=session.getAttribute("username") == null
            ? "<h3>Login if you have an account, or Register if you don't!</h3>"
            : "<h3>Navigate through the application using the links below!</h3>"
    %>
    <hr/>
    <% if (session.getAttribute("username") == null) { %>
    <div class="col-md-12 text-center">
        <a href="/users/login" class="btn btn-primary">Login</a>
        <a href="/users/register" class="btn btn-warning">Register</a>
    </div>
    <% } else if ((boolean) session.getAttribute("isAdmin")) { %>
        <div class="col-md-12 text-center">
            <a href="/cats/create" class="btn btn-outline-primary" role="button">Create Cat</a>
            <a href="/cats/all" class="btn btn-primary">All Cats</a>
            <a href="/orders/all" class="btn btn-primary">All Orders</a>
            <a href="/users/logout" class="btn btn-warning">Logout</a>
        </div>
    <%--</div>--%>
    <% } else { %>
    <div class="col-md-12 text-center">
        <a href="/cats/all" class="btn btn-primary">All Cats</a>
        <a href="/users/logout" class="btn btn-warning">Logout</a>
    </div>
    <% } %>
</div>
</body>
</html>


