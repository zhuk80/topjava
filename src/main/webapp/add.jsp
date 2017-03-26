<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Add meal to list</title>
</head>
<body>
<h2><a href="index.html">Home</a></h2>

<h2>Add meal to list</h2>

<form method="post">
    <input type="hidden" name="userId" value="0"/>
    <table width="50%" align="center">
        <tr>
            <jsp:useBean id="now" class="java.util.Date"/>
            <td>Дата и время</td>
            <td><input type="text" name="dateTime"
                       value="<fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${now}" />"/></td>
        </tr>
        <tr>
            <td>Описание</td>
            <td><input type="text" name="description" value=""/></td>
        </tr>
        <tr>
            <td>Калории</td>
            <td><input type="text" name="calories" value=""/></td>
        </tr>
        <tr>
            <td><input type="submit" value="Add new meal"></td>
        </tr>

    </table>
</form>

</body>
</html>
