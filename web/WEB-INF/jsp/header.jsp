<%--
  Created by IntelliJ IDEA.
  User: Artem
  Date: 8/17/2021
  Time: 4:27 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Header</title>
</head>
<body>
<a href="${pageContext.request.contextPath}/create-product">create product</a>
<a href="${pageContext.request.contextPath}/product-list">product list</a>
<br>
<a href="${pageContext.request.contextPath}/create-order">create order</a>
<a href="${pageContext.request.contextPath}/order-list">order list</a>
<br>
<br>
</body>
</html>
