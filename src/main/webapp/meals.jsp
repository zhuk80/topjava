<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Meal list</title>
</head>
<body>
<h2><a href="index.html">Home</a></h2>

<h2>Meal list</h2>

<table width="90%" align="center">
    <tr>
        <td>Дата и время</td>
        <td>Описание</td>
        <td>Калории</td>
        <td></td>
        <td></td>
    </tr>
    <c:forEach items="${Meals}" var="meal">
        <c:choose>
            <c:when test="${meal.isExceed()}">
                <tr bgcolor="#ffc0cb">
            </c:when>
            <c:otherwise>
                <tr bgcolor="#32cd32">
            </c:otherwise>
        </c:choose>
        <fmt:parseDate value="${meal.getDateTime()}" var="parsedDate" pattern="yyyy-MM-dd'T'HH:mm"/>
        <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${parsedDate}"/></td>
        <td>${meal.getDescription()}</td>
        <td>${meal.getCalories()}</td>
        <td><a href="<c:url value='?action=edit&userId=${meal.getId()}' />">Edit</a></td>
        <td><a href="<c:url value='?action=delete&userId=${meal.getId()}' />">Delete</a></td>
        </tr>
    </c:forEach>

</table>
<br>
<a href="<c:url value='?action=new'/>">Add new meal</a></li>

</body>
</html>
