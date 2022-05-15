
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="lang" value="${not empty param.lang ? param.lang : not empty lang ? lang : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${lang}" />
<fmt:setBundle basename="messages" />
<!DOCTYPE html>

<html language="${lang}">
<head>
    <title>Admin-AllStations</title>
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
                <li class="active"><a href="${pageContext.request.contextPath}/admin/showAllStations.jsp"><fmt:message key="stations"/></a></li>
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

<div id="stationCreateErrorModal" class="modal fade" tabindex="-1">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header">
                <button class="close" data-dismiss="modal">X</button>
                <h4 class="modal-title"><fmt:message key="station.create.error"/></h4>
            </div>
            <div class="modal-body">${stationCreateValidationError}</div>
            <div class="modal-footer">
                <button class="btn btn-danger" data-dismiss="modal"><fmt:message key="close"/></button>
            </div>
        </div>
    </div>
</div>
<c:if test="${not empty stationCreateValidationError}">
    <script>
        $('#stationCreateErrorModal').modal("show");
        $('#stationCreateErrorModal').on('hidden.bs.modal', function (event){
            <c:remove var="stationCreateValidationError"/>

        })

    </script>
</c:if>

<div id="stationUpdateErrorModal" class="modal fade" tabindex="-1">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header">
                <button class="close" data-dismiss="modal">X</button>
                <h4 class="modal-title"><fmt:message key="station.update.error"/></h4>
            </div>
            <div class="modal-body">${stationUpdateValidationError}</div>
            <div class="modal-footer">
                <button class="btn btn-danger" data-dismiss="modal"><fmt:message key="close"/></button>
            </div>
        </div>
    </div>
</div>
<c:if test="${not empty stationUpdateValidationError}">
    <script>
        $('#stationUpdateErrorModal').modal("show");
        $('#stationUpdateErrorModal').on('hidden.bs.modal', function (event){
            <c:remove var="stationaUpdateValidationError"/>

        })

    </script>
</c:if>

<br><br><br><br><br>

<div class="jumbotron">
    <div class="container">
        <div class="pull-right">
            <a href="${pageContext.request.contextPath}/newStation" class="btn btn-default"><fmt:message key="station.add.new"></fmt:message> </a>
            <a href="${pageContext.request.contextPath}/listStation" class="btn btn-default"><fmt:message key="station.list.all"></fmt:message> </a>
        </div>

        <c:if test="${station != null}">
            <form class="form-horizontal" action="${pageContext.request.contextPath}/updateStation" method="post">
        </c:if>
        <c:if test="${station == null}">
            <form class="form-horizontal" action="${pageContext.request.contextPath}/insertStation" method="post">
        </c:if>
                    <caption>
                        <h2>
                            <c:if test="${station != null}">
                                <fmt:message key="station.edit"/>
                            </c:if>
                            <c:if test="${station == null}">
                               <fmt:message key="station.add.new"/>
                            </c:if>
                        </h2>
                    </caption>

                    <c:if test="${station != null}">
                        <input type="hidden" name="id" id="id" value="<c:out value='${station.id}'/>"/>
                    </c:if>
            <div class="form-group">
                <label for="stationNameUA" class="control-label"><fmt:message key="station.name.ua"/> </label>
            </div>
            <div>
                <input type="text" class="form-control" name="stationNameUA" id="stationNameUA"  value="<c:out value='${station.nameUA}'/>">
            </div>

            <div class="form-group">
                <label for="stationNameEN" class="control-label"><fmt:message key="station.name.en"/> </label>
            </div>
            <div>
                <input type="text" class="form-control" name="stationNameEN" id="stationNameEN"  value="<c:out value='${station.nameEN}'/>">
            </div>

            <br>
            <div class="pull-right">
                <a href="${pageContext.request.contextPath}/logout" class="btn btn-danger"><fmt:message key="return"/> </a>
                <button type="submit" class="btn btn-success"><fmt:message key="submit"/></button>
            </div>



            </form>
        </div>
    </div>


</body>
</html>
