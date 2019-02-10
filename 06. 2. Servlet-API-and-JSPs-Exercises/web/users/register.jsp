<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Register</title>
    <link rel="stylesheet" type="text/css"
          href="<c:url value="${pageContext.request.contextPath}/resources/css/bootstrap.min.css"/>"/>
</head>
<body>
<div class="container ">
    <h1>Register!</h1>
    <%String errorMessage = (String) application.getAttribute("registerErrorMessage");%>
    <%if (errorMessage != null) {%>
    <h1 class="text-danger" ><%=errorMessage%>
    </h1><br/>
    <% application.setAttribute("registerErrorMessage", null);%>
    <%}%>
    <%--<form method="post" action="/users/register">--%>
    <%--Username: <input type="text" name="username" required/><br/>--%>
    <%--Password: <input type="password" name="password" required/><br/>--%>
    <%--Confirm Password: <input type="password" name="confirmPassword" required/><br/>--%>
    <%--Admin: <input type="checkbox" name="admin"/><br><br/>--%>
    <%--&lt;%&ndash;<button type="submit">Register</button>&ndash;%&gt;</form>--%>


    <form method="post" action="/users/register">
        <div class="form-group">
            <label class="control-label col-sm-2" for="username">Username:</label>
            <div class="col-sm-10">
                <input type="text" name="username" class="form-control" id="username" placeholder="Enter username" required>
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-sm-2" for="pwd">Password:</label>
            <div class="col-sm-10">
                <input type="password" name="password" class="form-control" id="pwd" placeholder="Enter password" required>
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-sm-3" for="confirmpwd">Confirm Password:</label>
            <div class="col-sm-10">
                <input type="password" name="confirmPassword" class="form-control" id="confirmpwd"
                       placeholder="Enter password" required>
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <div class="checkbox">
                    <label><input type="checkbox" name="admin">Admin:</label>
                </div>
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button type="submit" class="btn btn-secondary">Register</button>
            </div>
        </div>
    </form>
    <hr/>
    <a href="/" class="btn btn-outline-primary" role="button">Back to Home</a>
</div>
</body>
</html>
