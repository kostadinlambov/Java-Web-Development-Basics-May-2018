<%@ page import="org.softuni.fdmc.data.models.Product" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="org.softuni.fdmc.util.Type" %>
<%@ page import="org.softuni.java_ee_block.data.models.Product" %>
<%@ page import="org.softuni.java_ee_block.data.util.Type" %>


<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Java EE Block</title>
</head>
<body>

<h1>All Products</h1>
<hr/>

<% List<Product> products = new ArrayList<Product>(){{
    add(new Product("Chushkopek", "A universal tool for …", Type.DOMESTIC));
    add(new Product("Injektoplqktor", "Dunno what this is…", Type.COSMETIC));
    add(new Product("Plumbus", "A domestic tool for everything", Type.FOOD));
}} ;

    application.setAttribute("products", products);

%>

<% for (Product product : products) {%>
<a href="/products/details.jsp?name=<%=product.getName()%>"><%=product.getName()%></a><br><br>
<% } %>




</body>
</html>