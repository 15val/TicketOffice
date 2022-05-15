<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.entity.Route" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="lang" value="${not empty param.lang ? param.lang : not empty lang ? lang : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${lang}" />
<fmt:setBundle basename="messages" />
<!DOCTYPE html>

<html language="${lang}">
<head>
    <title>Admin-AllRoutes</title>
    <style>
        div{
            padding: 4px;
        }

    </style>
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

    <!-- Optional theme -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

    <!-- jQuery -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
    <!-- Latest compiled and minified JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>

    <SCRIPT type="text/javascript">
        window.history.forward();
        function noBack() { window.history.forward(); }
    </SCRIPT>
</head>
<body onload="noBack();" onpageshow="if (event.persisted) noBack();" onunload="">
<div class="navbar navbar-inverse navbar-fixed-top">
    <div class="container-fluid">
        <div class="navbar-header">
            <button class="navbar-toggle" data-toggle="collapse" data-target="#navbar-collapse-1">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#"><fmt:message key="ticketoffice"/> </a>
        </div>
        <div class="collapse navbar-collapse" id="navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li><a href="${pageContext.request.contextPath}/admin/showAllUsers.jsp"><fmt:message key="users"/></a></li>
                <li><a href="${pageContext.request.contextPath}/admin/showAllStations.jsp"><fmt:message key="stations"/></a></li>
                <li class="active"><a href="${pageContext.request.contextPath}/admin/showAllRoutes.jsp"><fmt:message key="routes"/></a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li><a class="btn btn-danger" href="${pageContext.request.contextPath}/logout"><fmt:message key="logout"/></a></li>

                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown"><fmt:message key="language.current"/><b class="caret"></b></a>
                    <ul class="dropdown-menu">
                        <li><a href="?lang=en">English</a></li>
                        <li><a href="?lang=ua">Українська</a></li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
</div>

<div id="routeCreateCompleteModal" class="modal fade" tabindex="-1">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header">
                <button class="close" data-dismiss="modal">X</button>
                <h4 class="modal-title"><fmt:message key="route.create.complete"/></h4>
            </div>

            <div class="modal-footer">
                <button class="btn btn-success" data-dismiss="modal"><fmt:message key="close"/></button>
            </div>
        </div>
    </div>
</div>


<c:if test="${not empty sessionScope['routeCreateComplete']}">
    <script>

        $('#routeCreateCompleteModal').modal("show");

        $('#routeCreateCompleteModal').on('hidden.bs.modal', function (event){
            <c:remove var="routeCreateComplete"/>
        })

    </script>
</c:if>

<div id="routeUpdateCompleteModal" class="modal fade" tabindex="-1">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header">
                <button class="close" data-dismiss="modal">X</button>
                <h4 class="modal-title"><fmt:message key="route.update.complete"/></h4>
            </div>

            <div class="modal-footer">
                <button class="btn btn-success" data-dismiss="modal"><fmt:message key="close"/></button>
            </div>
        </div>
    </div>
</div>


<c:if test="${not empty sessionScope['routeUpdateComplete']}">
    <script>

        $('#routeUpdateCompleteModal').modal("show");

        $('#routeUpdateCompleteModal').on('hidden.bs.modal', function (event){
            <c:remove var="routeUpdateComplete"/>
        })

    </script>
</c:if>

<br><br><br><br><br>

<div class="container">
    <div class="pull-right">
        <a href="${pageContext.request.contextPath}/newRoute" class="btn btn-default"><fmt:message key="route.add.new"></fmt:message> </a>
        <a href="${pageContext.request.contextPath}/adminListRoute" class="btn btn-default"><fmt:message key="route.list.all"></fmt:message> </a>
    </div>
    <div align="center">
        <table class="table table-bordered table-striped table-hover">
            <tr class="active">
                <th><fmt:message key="id"/> </th>
                <th><fmt:message key="route.first.station"/> </th>
                <th><fmt:message key="route.last.station"/> </th>
                <th><fmt:message key="route.departure"/> </th>
                <th><fmt:message key="route.arrival"/> </th>
                <th><fmt:message key="route.train.id"/> </th>
                <th><fmt:message key="route.time"/> </th>
                <th><fmt:message key="route.free.seats"/> </th>
                <th><fmt:message key="route.price"/> </th>
                <th><fmt:message key="actions"/></th>
            </tr>
            <c:forEach var="route" items="${routeList}">
                <tr>
                    <td><c:out value="${route.routeId}"/></td>
                    <c:if test="${lang eq 'ua'}">
                    <td><c:out value="${route.firstStation.getNameUA()}"/></td>
                    <td><c:out value="${route.lastStation.getNameUA()}"/></td>
                    </c:if>
                    <c:if test="${lang eq 'en'}">
                        <td><c:out value="${route.firstStation.getNameEN()}"/></td>
                        <td><c:out value="${route.lastStation.getNameEN()}"/></td>
                    </c:if>
                    <c:set var="departure" value="${route.departure}"/>
                    <c:set var="departureWithoutT" value="${fn:replace(departure, 'T', ' ')}"/>
                    <td><c:out value="${departureWithoutT}"/></td>
                    <c:set var="arrival" value="${route.arrival}"/>
                    <c:set var="arrivalWithoutT" value="${fn:replace(arrival, 'T', ' ')}"/>
                    <td><c:out value="${arrivalWithoutT}"/></td>

                    <td><c:out value="${route.trainId}"/></td>
                    <td><c:out value="${route.routeTime}"/></td>
                    <td><c:out value="${route.freeSeats}"/></td>
                    <td><c:out value="${route.price}"/> <fmt:message key="money.uah"/></td>

                    <td>
                        <a href="${pageContext.request.contextPath}/editRoute?idRoute=<c:out value='${route.routeId}'/>"><fmt:message key="edit"/> </a>
                        <br>
                        <a href="${pageContext.request.contextPath}/deleteRoute?idRoute=<c:out value='${route.routeId}'/>"><fmt:message key="delete"/> </a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>

<c:remove var="routeCreateValidationError"/>
<c:remove var="routeUpdateValidationError"/>

</body>
</html>
