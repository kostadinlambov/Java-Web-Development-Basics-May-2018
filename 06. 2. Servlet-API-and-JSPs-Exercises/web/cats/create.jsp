<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Create Cat</title>
    <link rel="stylesheet" type="text/css"
          href="<c:url value="${pageContext.request.contextPath}/resources/css/bootstrap.min.css"/>"/>
</head>
<body>
<div class="container">
    <h1>Create a Cat!</h1>
    <%String errorMessage = (String) application.getAttribute("catsCreateErrorMessage");%>
    <%if (errorMessage != null) {%>
    <h1 class="text-danger"><%=errorMessage%>
    </h1><br/>
    <% application.setAttribute("catsCreateErrorMessage", null);%>
    <%} %>
    <form method="post" action="/cats/create">
        <div class="form-group">
            <label class="control-label col-sm-2" for="name">Name:</label>
            <div class="col-sm-10">
                <input type="text" name="name" class="form-control" id="name" placeholder="Enter name" required>
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-sm-2" for="breed">Breed:</label>
            <div class="col-sm-10">
                <input type="text" name="breed" class="form-control" id="breed" placeholder="Enter breed" required>
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-sm-3" for="color">Color:</label>
            <div class="col-sm-10">
                <input type="text" name="color" class="form-control" id="color"
                       placeholder="Enter color" required>
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-sm-3" for="numberOfLegs">Number Of Legs:</label>
            <div class="col-sm-10">
                <input type="number" name="numberOfLegs" class="form-control" id="numberOfLegs"
                       placeholder="Enter number of legs" required>
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button type="submit" class="btn btn-secondary">Create Cat</button>
            </div>
        </div>
    </form>

    <hr/>

    <a href="/" class="btn btn-outline-primary" role="button">Back to Home</a>
</div>
</body>
</html>
