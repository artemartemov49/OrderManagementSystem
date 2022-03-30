<%--
  Created by IntelliJ IDEA.
  User: Artem
  Date: 8/16/2021
  Time: 5:56 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Create Product</title>
</head>
<body>
<%@ include file="header.jsp"%>

<h>Create product</h>
<br>
<form action="${pageContext.request.contextPath}/create-product" method="post">

    <label for="name">Name:
        <input type="text" name="name" id="name">
    </label><br>
    <label for="price">Price:
        <input type="text" name="price" id="price">
    </label><br>
    <button type="submit">Send</button>
    <br>

<%@include file="errors.jsp"%>

</form>
</body>
</html>
