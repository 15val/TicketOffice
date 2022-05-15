<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="lang" value="${not empty param.lang ? param.lang : not empty lang ? lang : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${lang}" />
<fmt:setBundle basename="messages" />
<!DOCTYPE html>


<html language="${lang}">
<head>
    <title>User-Main</title>
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
                <li><a href="${pageContext.request.contextPath}/user/userbasis.jsp"><fmt:message key="main"/></a></li>
                <li><a href="${pageContext.request.contextPath}/user/myTickets.jsp"><fmt:message key="ticket.my"/></a></li>
                <li class="active"><a href="${pageContext.request.contextPath}/user/profile.jsp"><fmt:message key="profile"/></a></li>

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

<br><br><br><br><br><br>

<div id="topUpCompleteModal" class="modal fade" tabindex="-1">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header">
                <button class="close" data-dismiss="modal">X</button>
                <h4 class="modal-title"><fmt:message key="balance.top.up.complete"/></h4>
            </div>

            <div class="modal-footer">
                <button class="btn btn-success" data-dismiss="modal"><fmt:message key="close"/></button>
            </div>
        </div>
    </div>
</div>

<div id="ticketCreateErrorModal" class="modal fade" tabindex="-1">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header">
                <button class="close" data-dismiss="modal">X</button>
                <h4 class="modal-title"><fmt:message key="ticket.create.error"/></h4>
            </div>
            <div class="modal-body">${ticketCreateValidationError}</div>
            <div class="modal-footer">
                <button class="btn btn-danger" data-dismiss="modal"><fmt:message key="close"/></button>
            </div>
        </div>
    </div>
</div>
<c:if test="${not empty ticketCreateValidationError}">
    <script>
        $('#ticketCreateErrorModal').modal("show");
        $('#ticketCreateErrorModal').on('hidden.bs.modal', function (event){
            <c:remove var="ticketCreateValidationError"/>

        })

    </script>
</c:if>


<c:if test="${not empty sessionScope['topUpComplete']}">
    <script>

        $('#topUpCompleteModal').modal("show");

        $('#topUpCompleteModal').on('hidden.bs.modal', function (event){
            <c:remove var="topUpComplete"/>
        })

    </script>
</c:if>

<div id="topUpErrorModal" class="modal fade" tabindex="-1">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header">
                <button class="close" data-dismiss="modal">X</button>
                <h4 class="modal-title"><fmt:message key="balance.top.up.error"/></h4>
            </div>
            <div class="modal-body">${topUpValueValidationError}</div>
            <div class="modal-footer">
                <button class="btn btn-danger" data-dismiss="modal"><fmt:message key="close"/></button>
            </div>
        </div>
    </div>
</div>
<c:if test="${not empty topUpValueValidationError}">
    <script>
        $('#topUpErrorModal').modal("show");
        $('#topUpErrorModal').on('hidden.bs.modal', function (event){
            <c:remove var="topUpValueValidationError"/>

        })

    </script>
</c:if>

<div class="text-center">
    <a href="${pageContext.request.contextPath}/getUserProfile" class="btn btn-success btn-lg"><fmt:message key="profile.show"/> </a>
</div>
<div class="container">

    <div align="center">
        <table class="table table-bordered table-striped table-hover">
            <tr class="active">
                <th><fmt:message key="id"/> </th>
                <th><fmt:message key="nickname"/> </th>
                <th><fmt:message key="password"/> </th>
                <th><fmt:message key="email"/> </th>
                <th><fmt:message key="role"/> </th>
                <th><fmt:message key="name"/> </th>
                <th><fmt:message key="user.balance"/> </th>
                <th><fmt:message key="actions"/></th>
            </tr>

                <c:forEach var="userForProfile" items="${userProfile}">
                    <tr>
                        <td><c:out value="${userForProfile.id}"/></td>
                        <td><c:out value="${userForProfile.nickname}"/></td>
                        <td><c:out value="${userForProfile.password}"/></td>
                        <td><c:out value="${userForProfile.email}"/></td>
                        <td><c:out value="${userForProfile.role.toString()}"/></td>
                        <td><c:out value="${userForProfile.name}"/></td>
                        <td><c:out value="${userForProfile.balance}"/> <fmt:message key="money.uah"/></td>
                    <td>
                        <div class="text-center">
                        <form class="form-horizontal" method="post" action="${pageContext.request.contextPath}/topUpBalance">
                            <input type="hidden" name="idUser" id="idUser" value="<c:out value='${userForProfile.id}'/>"/>

                            <div>
                                <input type="text" class="form-control" name="topUpValue" id="topUpValue" />
                            </div>

                            <button type="submit" class="btn btn-success"><fmt:message key="balance.top.up"/> </button>
                        </form>
                        </div>
                    </td>
                </tr>
                </c:forEach>

        </table>
    </div>
</div>

</body>
</html>
