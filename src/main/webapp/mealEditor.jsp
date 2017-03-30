<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title><c:out value="${meal.getId()>0 ? 'Edit meal list' : 'Add meal to list'}" /></title>
</head>
<body>
<h2><a href="index.html">Home</a></h2>

<h2><c:out value="${meal.getId()>0 ? 'Edit meal list' : 'Add meal to list'}" /></h2>

<form method="post">
    <input type="hidden" name="id" value="${meal.getId()}"/>
    <table width="50%" align="center">
        <tr>
            <fmt:parseDate value="${meal.getDateTime()}" var="parsedDate" pattern="yyyy-MM-dd'T'HH:mm"/>
            <td>Дата и время</td>
            <td><input type="text" name="dateTime" value="<fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${parsedDate}" />"/></td>
        </tr>
        <tr>
            <td>Описание</td>
            <td><input type="text" name="description" value="${meal.getDescription()}"/></td>
        </tr>
        <tr>
            <td>Калории</td>
            <td><input type="text" name="calories" value="${meal.getCalories()}"/></td>
        </tr>
        <tr>
            <td><input type="submit" value="<c:out value="${meal.getId()>0 ? 'Edit' : 'Add meal'}" />"></td>
        </tr>

    </table>
</form>

</body>
</html>