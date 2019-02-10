<%@ page import="org.softuni.fdmc.data.models.Product" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Java EE Block</title>
</head>
<body>

<h1>Product Details</h1>

<% String name = request.getParameter("name"); %>
<% List<Product> tubes = (List<Product>) application.getAttribute("products"); %>

<% Product product = tubes.stream().filter(x -> x.getName().equals(name)).findFirst().orElse(null); %>

<% String type = product.getType().toString().substring(0,1) + product.getType().toString().substring(1).toLowerCase(); %>

<h3>Name: <%=product.getName()%></h3>
<h3>Description: <%=product.getDescription()%></h3>
<h3>Type: <%=type%></h3>


<a href="/products/all.jsp">Back to All Products</a>

</body>
</html>
