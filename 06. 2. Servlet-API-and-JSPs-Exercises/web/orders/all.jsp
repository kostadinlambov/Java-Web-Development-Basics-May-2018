<%@ page import="org.softuni.fdmc.data.repos.OrderRepository" %>
<%@ page import="org.softuni.fdmc.data.models.Order" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>All Orders</title>
    <link rel="stylesheet" type="text/css"
          href="<c:url value="${pageContext.request.contextPath}/resources/css/bootstrap.min.css"/>"/>
</head>
<body>
<div class="container">
    <h1 style="text-align: center">All Orders</h1>
    <hr/>
    <% OrderRepository orderRepository = (OrderRepository) application.getAttribute("orders"); %>
    <% List<Order> sortedByDate = orderRepository.sortOrdersByDate(); %>
    <% DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); %>

    <%if (orderRepository.getAllOrders().size() > 0) {%>
    <table class="table table-bordered table-striped ">
        <tr class="table-success">
            <th>Client's name</th>
            <th>Cat's name</th>
            <th>Order time</th>
        </tr>

        <% for (Order order : sortedByDate) { %>
        <%
            LocalDateTime dateTime = order.getMadeOn();
            String formattedDateTime = dateTime.format(formatter);
        %>
        <tr >
            <td><%=order.getClient().getUsername() %>
            </td>
            <td><%=order.getCat().getName() %>
            </td>
            <td><%=formattedDateTime %>
            </td>
        </tr>
        <% } %>
    </table>

    <%} else {%>
    <h1 class="text-danger">Order list is empty!</h1>
    <%}%>

    <hr/>
    <a href="/" class="btn btn-outline-primary" role="button">Back to Home</a>
</div>
</body>
</html>
