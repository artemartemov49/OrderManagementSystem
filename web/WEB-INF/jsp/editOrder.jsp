<%--
  Created by IntelliJ IDEA.
  User: Artem
  Date: 8/18/2021
  Time: 12:14 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>EditOrder</title>
</head>
<body>
<%@ include file="header.jsp" %>
<b>Edit Order:</b> <br>
<form action="${pageContext.request.contextPath}/edit-order" method="post">
    <b>${requestScope.order.id}</b>, <label>
    <input type="text" name="clientFIO"
           value="${requestScope.order.clientFIO}">
</label>, ${requestScope.order.date}, total
    sum: ${requestScope.order.totalSum}.
    <br><b>Items:</b><br>
    <label for="productId">
        <c:forEach var="product" items="${requestScope.products}">
            <input type="checkbox" name="productId" id="productId" value="${product.id}"
                   <c:if test="${requestScope.productsId.contains(product.id)}">checked=checked</c:if>>
            ${product.name}, ${product.price}
            <select name="${product.id}" id="count">
                <option name="${product.id}" <c:if test="${requestScope.productsId.contains(product.id)}">value="${requestScope.countMap.get(product.id)}"</c:if>>
                        ${requestScope.countMap.get(product.id)}
                </option>
                <c:forEach var="count" begin="1" end="10">
                    count:
                    <option name="${product.id}" value="${count}">${count}</option>
                </c:forEach>
            </select>
            <br>
        </c:forEach>
    </label>
    <button type="submit" name="orderId" value="${requestScope.order.id}">Edit</button>
</form>
<%@include file="errors.jsp"%>
</body>
</html>
