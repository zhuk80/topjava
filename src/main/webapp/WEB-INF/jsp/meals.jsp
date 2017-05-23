<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<script type="text/javascript" src="resources/js/datatablesUtil.js" defer></script>
<script type="text/javascript" src="resources/js/mealDatatables.js" defer></script>
<jsp:include page="fragments/bodyHeader.jsp"/>




<%--<div class="container">
    <div class="shadow">
        <h3><spring:message code="meals.title"/></h3>

        <div id="filter">
            <div class="container">
                <form method="post" action="meals/filter">
                    <dl>
                        <dt><spring:message code="meals.startDate"/>:</dt>
                        <dd><input type="date" name="startDate" value="${param.startDate}"></dd>
                    </dl>
                    <dl>
                        <dt><spring:message code="meals.endDate"/>:</dt>
                        <dd><input type="date" name="endDate" value="${param.endDate}"></dd>
                    </dl>
                    <dl>
                        <dt><spring:message code="meals.startTime"/>:</dt>
                        <dd><input type="time" name="startTime" value="${param.startTime}"></dd>
                    </dl>
                    <dl>
                        <dt><spring:message code="meals.endTime"/>:</dt>
                        <dd><input type="time" name="endTime" value="${param.endTime}"></dd>
                    </dl>
                    <button type="submit"><spring:message code="meals.filter"/></button>
                </form>
            </div>
        </div>

        <div class="view-box">
            <a class="btn btn-info" onclick="add()">
                <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
            </a>

            <table class="table table-striped display" id="datatable">
                <thead>
                <tr>
                    <th><spring:message code="meals.dateTime"/></th>
                    <th><spring:message code="meals.description"/></th>
                    <th><spring:message code="meals.calories"/></th>
                    <th></th>
                    <th></th>
                </tr>
                </thead>
                <c:forEach items="${meals}" var="meal">
                    <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.to.MealWithExceed"/>
                    <tr class="${meal.exceed ? 'exceeded' : 'normal'}">
                        <td>
                                &lt;%&ndash;${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}&ndash;%&gt;
                                &lt;%&ndash;<%=TimeUtil.toString(meal.getDateTime())%>&ndash;%&gt;
                                ${fn:formatDateTime(meal.dateTime)}
                        </td>
                        <td>${meal.description}</td>
                        <td>${meal.calories}</td>
                        <td><a href="meals/update?id=${meal.id}"><spring:message code="common.update"/></a></td>
                        <td>
                            <a class="btn btn-xs btn-danger delete" id="${meal.id}">
                            <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
                        </a>
                            &lt;%&ndash;<a href="meals/delete?id=${meal.id}"><spring:message code="common.delete"/></a>&ndash;%&gt;
                        </td>
                    </tr>
                </c:forEach>
            </table>

        </div>
    </div>
</div>--%>

<div class="container">
    <div id="filter">
    <%--<form class="form-horizontal" onsubmit="return filter_submit();" id="filterForm">--%>
        <%--<form method="post" action="meals/filter">--%>
        <form method = "post" id = "filterForm" name = "filterForm">
        <dl>
            <dt><spring:message code="meals.startDate"/>:</dt>
            <dd><input type="date" name="startDate" value="${param.startDate}" id="startDate"></dd>
        </dl>
        <dl>
            <dt><spring:message code="meals.endDate"/>:</dt>
            <dd><input type="date" name="endDate" value="${param.endDate}" id="endDate"></dd>
        </dl>
        <dl>
            <dt><spring:message code="meals.startTime"/>:</dt>
            <dd><input type="time" name="startTime" value="${param.startTime}" id="startTime"></dd>
        </dl>
        <dl>
            <dt><spring:message code="meals.endTime"/>:</dt>
            <dd><input type="time" name="endTime" value="${param.endTime}" id="endTime"></dd>
        </dl>
        <button type="button" onClick="filter_submit()"><spring:message code="meals.filter"/></button>
        <button type="button" onClick="filter_clear()">Clear</button>
    </form>
</div>
</div>


<div class="container">
    <div class="shadow">
        <h3><spring:message code="meals.title"/></h3>

        <%--<div id="filter-form">--%>

        <%--</div>--%>

        <div class="view-box">
            <a class="btn btn-info" onclick="add()">
                <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
            </a>

            <table class="table table-striped display" id="datatable">
                <thead>
                <tr>
                    <th><spring:message code="meals.dateTime"/></th>
                    <th><spring:message code="meals.description"/></th>
                    <th><spring:message code="meals.calories"/></th>
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
                        <td><a href="meals/update?id=${meal.id}"><spring:message code="common.update"/></a></td>
                        <td>
                            <a class="btn btn-xs btn-danger delete" id="${meal.id}">
                                <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
                            </a>
                                <%--<a href="meals/delete?id=${meal.id}"><spring:message code="common.delete"/></a>--%>
                        </td>
                    </tr>
                </c:forEach>
            </table>

        </div>
    </div>
</div>

<div class="modal fade" id="editRow">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h2 class="modal-title"><spring:message code="meals.add"/></h2>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" id="detailsForm">
                    <input type="text" hidden="hidden" id="id" name="id">

                    <div class="form-group">
                        <label for="dateTime" class="control-label col-xs-3"><spring:message code="meals.dateTime"/></label>

                        <div class="col-xs-9">
                            <input type="datetime-local" class="form-control" id="dateTime" name="dateTime" placeholder="<spring:message code="meals.dateTime"/>">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="description" class="control-label col-xs-3"><spring:message code="meals.description"/></label>

                        <div class="col-xs-9">
                            <input type="text" class="form-control" id="description" name="description" placeholder="<spring:message code="meals.description"/>">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="calories" class="control-label col-xs-3"><spring:message code="meals.calories"/></label>

                        <div class="col-xs-9">
                            <input type="number" class="form-control" id="calories" name="calories" placeholder="<spring:message code="meals.calories"/>">
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="col-xs-offset-3 col-xs-9">
                            <button type="submit" class="btn btn-primary">
                                <span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
                            </button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>



<jsp:include page="fragments/footer.jsp"/>
</body>
</html>