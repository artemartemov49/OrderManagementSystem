<%--
  Created by IntelliJ IDEA.
  User: Artem
  Date: 8/16/2021
  Time: 8:56 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Products</title>
</head>
<body>
<%@ include file="header.jsp" %>
Product list:
<br>
<br>
<c:forEach var="order" items="${requestScope.products}">
    ${order.id}, ${order.name}, ${order.price}.
    <br>
</c:forEach>

<a href="${pageContext.request.contextPath}/create-product">
    <button type="button">Create product</button>
</a>

</body>
</html>
