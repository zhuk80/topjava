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
    <input type="hidden" name="userId" value="${meal.getId()}"/>
    <table width="50%" align="center">
        <tr>
            <jsp:useBean id="now" class="java.util.Date"/>
            <fmt:parseDate value="${meal.getDateTime()}" var="parsedDate" pattern="yyyy-MM-dd'T'HH:mm"/>
            <c:set var="dateTime" value="${meal.getDateTime()!=null ? parsedDate : now}"/>

            <td>Дата и время</td>
            <td><input type="text" name="dateTime"
                       value="<fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${dateTime}" />"/></td>
        </tr>
        <tr>
            <td>Описание</td>
            <c:set var="description" value="${meal.getDescription()!=null ? meal.getDescription() : ''}"/>
            <td><input type="text" name="description" value="${description}"/></td>
        </tr>
        <tr>
            <td>Калории</td>
            <c:set var="calories" value="${meal.getCalories()!=null ? meal.getCalories() : ''}"/>
            <td><input type="text" name="calories" value="${calories}"/></td>
        </tr>
        <tr>
            <td><input type="submit" value="<c:out value="${meal.getId()>0 ? 'Edit' : 'Add meal'}" />"></td>
        </tr>

    </table>
</form>

</body>
</html>