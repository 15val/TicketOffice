<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, java.text.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="English" value="en"/>
<c:set var="lang" value="${not empty param.lang ? param.lang : not empty lang ? lang : English}" scope="session" />
<fmt:setLocale value="${lang}" />
<fmt:setBundle basename="messages" />

<!DOCTYPE html>


<html language="${lang}">

<head>

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>TicketOffice</title>
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

            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li><a href="${pageContext.request.contextPath}/login.jsp" class="btn btn-primary"><fmt:message key="please.login"/></a</li>
                <li><a href="${pageContext.request.contextPath}/register.jsp" class="btn btn-success"><fmt:message key="or.register"/></a></li>
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

<br><br><br><br><br>

<div class="jumbotron">
    <div class="container">
        <c:forEach var="route" items="${routeList}">
            <c:set var="departure" value="${route.departure}"/>
            <c:set var="departureWithoutT" value="${fn:replace(departure, 'T', ' ')}"/>
            <c:set var="arrival" value="${route.arrival}"/>
            <c:set var="arrivalWithoutT" value="${fn:replace(arrival, 'T', ' ')}"/>

            <fmt:message key="the.train.number"/><c:out value="${route.trainId}"/> <fmt:message key="departures.at"/><c:out value="${departureWithoutT}"/> <fmt:message key="from.the.station"/>
            <c:if test="${lang eq 'ua'}">
                <c:out value="${route.firstStation.getNameUA()}"/>
            </c:if>
            <c:if test="${lang eq 'en'}">
                <c:out value="${route.firstStation.getNameEN()}"/>
            </c:if>
            <fmt:message key="and.arrives.at"/><c:out value="${arrivalWithoutT}"/> <fmt:message key="to.the.station"/>
            <c:if test="${lang eq 'ua'}">
                <c:out value="${route.lastStation.getNameUA()}"/>.
            </c:if>
            <c:if test="${lang eq 'en'}">
                <c:out value="${route.lastStation.getNameEN()}"/>.
            </c:if>

            <fmt:message key="the.travel.time.is"/><c:out value="${route.routeTime}"/>.
            <fmt:message key="the.price.is"/><c:out value="${route.price}"/> <fmt:message key="money.uah"/>.
            <fmt:message key="only"/><c:out value="${route.freeSeats}"/> <fmt:message key="free.seats.left"/>

            </c:forEach>
    </div>

    <div class="pull-right">
        <a href="${pageContext.request.contextPath}/logout" class="btn btn-danger"><fmt:message key="return"/> </a>
    </div>

</div>
</body>
</html>
