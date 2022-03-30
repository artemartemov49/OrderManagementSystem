<%--
  Created by IntelliJ IDEA.
  User: Artem
  Date: 8/16/2021
  Time: 5:04 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Create Order</title>

</head>
<body>
<%@ include file="header.jsp" %>

To make order you should pick item(s), decide their count and write client FIO!<br>
<form action="${pageContext.request.contextPath}/create-order" method="post">
    <label for="clientFIO">Client FIO
        <input type="text" name="clientFIO" id="clientFIO">
    </label><br>
    <label for="productId">
        <c:forEach var="product" items="${requestScope.products}">
            <input type="checkbox" name="productId" id="productId" value="${product.id}">
            ${product.name}, ${product.price}
            <select name="${product.id}" id="count">
                <option></option>
                <c:forEach var="count" begin="1" end="10">
                    count:
                    <option name="${product.id}" value="${count}">${count}</option>
                </c:forEach>
            </select>
            <br>
        </c:forEach>
    </label>
    <button type="submit">Submit</button>
    <%@include file="errors.jsp" %>
</form>
</body>
</html>
