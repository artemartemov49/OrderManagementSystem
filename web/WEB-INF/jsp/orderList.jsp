<%--
  Created by IntelliJ IDEA.
  User: Artem
  Date: 8/17/2021
  Time: 2:32 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Order List</title>
</head>
<body>
<%@ include file="header.jsp" %>
Order List:
<br>
<br>
<a href="${pageContext.request.contextPath}/filter-orders">
    <button type="button">Filter</button>
</a>
<br>
<br>
<c:forEach var="order" items="${requestScope.orders}">
    <form action="${pageContext.request.contextPath}/order-list" method="post">
        <b>${order.id}</b>, ${order.clientFIO}, ${order.date}, total sum: ${order.totalSum}.
        <button type="submit" name="deleteOrder" value="${order.id}">Delete order</button>
        <a href="${pageContext.request.contextPath}/edit-order?orderId=${order.id}">
            <button type="button">Edit Order</button>
        </a><br>
        <b>Items:</b><br>
        <c:forEach var="item" items="${order.itemDtos}">
            ${item.id}, ${item.name}, price: ${item.price}, count: ${item.count}, sum: ${item.sum}<br>
        </c:forEach>
    </form>
</c:forEach><br>
<a href="${pageContext.request.contextPath}/create-order">
    <button type="button">Create order</button>
</a>
</body>
</html>
