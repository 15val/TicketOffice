<%@ page import="java.util.List" %>
<%@ page import="model.entity.User" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="controller.command.GetUsersCommand" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="lang" value="${not empty param.lang ? param.lang : not empty lang ? lang : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${lang}" />
<fmt:setBundle basename="messages" />
<!DOCTYPE html>

<html language="${lang}">
<head>
    <title>Table</title>
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
                <li class="active"><a href="${pageContext.request.contextPath}/admin/showAllUsers.jsp"><fmt:message key="users"/></a></li>
                <li><a href="${pageContext.request.contextPath}/admin/showAllStations.jsp"><fmt:message key="stations"/></a></li>
                <li><a href="${pageContext.request.contextPath}/admin/showAllRoutes.jsp"><fmt:message key="routes"/></a></li>
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

<br><br><br><br><br>

<div class="container">
    <table class="table table-bordered table-striped table-hover">
        <tr class="active">
            <th><fmt:message key="id"/> </th>
            <th><fmt:message key="nickname"/> </th>
            <th><fmt:message key="password"/> </th>
            <th><fmt:message key="email"/> </th>
            <th><fmt:message key="role"/> </th>
            <th><fmt:message key="name"/> </th>
            <th><fmt:message key="user.balance"/> </th>
        </tr>

        <%
            new GetUsersCommand().execute(request);
        %>

        <c:forEach var="user" items="${userList}">
            <tr>
                <td><c:out value="${user.id}"/></td>
                <td><c:out value="${user.nickname}"/></td>
                <td><c:out value="${user.password}"/></td>
                <td><c:out value="${user.email}"/></td>
                <td><c:out value="${user.role.toString()}"/></td>
                <td><c:out value="${user.name}"/></td>
                <td><c:out value="${user.balance}"/> <fmt:message key="money.uah"/></td>
            </tr>
        </c:forEach>

    </table>
</div>

<div class="container">
<nav aria-label="Navigation for users">
    <ul class="pagination">
        <c:if test="${currentPage != 1}">
            <li class="page-item">
                <a class="page-link" href="GetUsers?recordsPerPage=${recordsPerPage}&currentPage=${currentPage-1}"><fmt:message key="previous"/> </a>
            </li>
        </c:if>

        <c:forEach begin="1" end="${numberOfPages}" var="i">
            <c:choose>
                <c:when test="${currentPage eq i}">
                    <li class="page-item active">
                        <a class="page-link">${i} <span class="sr-only">(current)</span> </a>
                    </li>
                </c:when>
                <c:otherwise>
                    <li class="page-item">
                        <a class="page-link" href="GetUsers?recordsPerPage=${recordsPerPage}&currentPage=${i}">${i}</a>
                    </li>
                </c:otherwise>
            </c:choose>
        </c:forEach>

        <c:if test="${currentPage lt numberOfPages}">
            <li class="page-item">
                <a class="page-link" href="GetUsers?recordsPerPage=${recordsPerPage}&currentPage=${currentPage+1}"><fmt:message key="next"/></a>
            </li>
        </c:if>
    </ul>
</nav>
</div>


</body>
</html>
