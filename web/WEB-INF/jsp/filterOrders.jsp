<%--
  Created by IntelliJ IDEA.
  User: Artem
  Date: 8/17/2021
  Time: 9:39 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Order list filter</title>
</head>
<body>
<%@ include file="header.jsp" %>
Order List:
<br>
<br>

<form action="${pageContext.request.contextPath}/filter-orders" method="post">
    <label for="fromDate">
        From date:
        <input type="date" name="fromDate" id="fromDate">
    </label>
    <label for="toDate">
        To date:
        <input type="date" name="toDate" id="toDate">
    </label>
    <button type="submit">Filter</button>
    <a href="${pageContext.request.contextPath}/order-list">
        <button type="button">Show all</button>
    </a>
</form>
<br>
${requestScope.noOrders}
<c:forEach var="orderDate" items="${requestScope.ordersByDate}">
    <form action="${pageContext.request.contextPath}/order-list" method="post">
        <b>${orderDate.id}</b>, ${orderDate.clientFIO}, ${orderDate.date}, total sum: ${orderDate.totalSum}.
        <button type="submit" name="deleteOrder" value="${orderDate.id}">Delete order</button>
        <a href="${pageContext.request.contextPath}/edit-order?orderId=${orderDate.id}">
            <button type="button">Edit Order</button>
        </a><br>
        <b>Items:</b><br>
        <c:forEach var="item" items="${orderDate.itemDtos}">
            ${item.id}, ${item.name}, price: ${item.price}, count: ${item.count}, sum: ${item.sum}<br>
        </c:forEach>
    </form>
</c:forEach><br>
<%@include file="errors.jsp" %>

</body>
</html>
