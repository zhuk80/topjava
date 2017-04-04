<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<html>
<head>
    <title>Meal list</title>
    <style>
        .normal {
            color: green;
        }

        .exceeded {
            color: red;
        }
    </style>
</head>
<body>
<section>
    <h2><a href="index.html">Home</a></h2>
    <h2>Meal list</h2>
    <a href="meals?action=create">Add Meal</a>
    <hr>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <c:forEach items="${meals}" var="meal">
            <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.to.MealWithExceed"/>
            <tr class="${meal.exceed ? 'exceeded' : 'normal'}">
                <td>
                        <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
                        <%--<%=TimeUtil.toString(meal.getDateTime())%>--%>
                        ${fn:formatDateTime(meal.dateTime)}
                </td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a href="meals?action=update&id=${meal.id}">Update</a></td>
                <td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
    <br>
    <form method="post" action="meals">
        <input type="hidden" name="type" value="filtered"/>
    <table border="0" cellpadding="8" cellspacing="0">
        <tr>
            <th>От даты:</th>
            <th><input type="date" value="" name="dateFrom"></th>
            <th>От времени:</th>
            <th><input type="time" value="" name="timeFrom"></th>
        </tr>
        <tr>
            <th>До даты:</th>
            <th><input type="date" value="" name="dateTo"></th>
            <th>До времени:</th>
            <th><input type="time" value="" name="timeTo"></th>
        </tr>
        <tr>
            <td></td><td></td>
            <td><button type="reset">Clear</button></td>
            <td><button type="submit">Filter</button></td>
        </tr>
    </table>
    </form>
</section>
</body>
</html>
