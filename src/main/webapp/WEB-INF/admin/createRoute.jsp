<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<sql:setDataSource var="ds" driver="com.mysql.jdbc.Driver" url="jdbc:mysql://localhost:3306/TicketOfficeDB" user="root" password="root"/>

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
    <!-- Include Bootstrap CDN -->
    <link href=
                  "https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css"
          rel="stylesheet">
    <script src=
                    "https://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.js">
    </script>
    <script src=
                    "https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js">
    </script>

    <!-- Include Moment.js CDN -->
    <script type="text/javascript" src=
            "https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.9.0/moment.min.js">
    </script>

    <!-- Include Bootstrap DateTimePicker CDN -->
    <link
            href=
                    "https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.37/css/bootstrap-datetimepicker.min.css"
            rel="stylesheet">

    <script src=
                    "https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.37/js/bootstrap-datetimepicker.min.js">
    </script>

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

<div id="routeCreateErrorModal" class="modal fade" tabindex="-1">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header">
                <button class="close" data-dismiss="modal">X</button>
                <h4 class="modal-title"><fmt:message key="route.create.error"/></h4>
            </div>
            <div class="modal-body">${routeCreateValidationError}</div>
            <div class="modal-footer">
                <button class="btn btn-danger" data-dismiss="modal"><fmt:message key="close"/></button>
            </div>
        </div>
    </div>
</div>
<c:if test="${not empty routeCreateValidationError}">
    <script>
        $('#routeCreateErrorModal').modal("show");
        $('#routeCreateErrorModal').on('hidden.bs.modal', function (event){
            <c:remove var="routeCreateValidationError"/>

        })

    </script>
</c:if>

<div id="routeUpdateErrorModal" class="modal fade" tabindex="-1">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header">
                <button class="close" data-dismiss="modal">X</button>
                <h4 class="modal-title"><fmt:message key="route.update.error"/></h4>
            </div>
            <div class="modal-body">${routeUpdateValidationError}</div>
            <div class="modal-footer">
                <button class="btn btn-danger" data-dismiss="modal"><fmt:message key="close"/></button>
            </div>
        </div>
    </div>
</div>
<c:if test="${not empty routeUpdateValidationError}">
    <script>
        $('#routeUpdateErrorModal').modal("show");
        $('#routeUpdateErrorModal').on('hidden.bs.modal', function (event){
            <c:remove var="routeUpdateValidationError"/>

        })

    </script>
</c:if>

<br><br><br><br><br>

<div class="jumbotron">
    <div class="container">
        <div class="pull-right">
            <a href="${pageContext.request.contextPath}/newRoute" class="btn btn-default"><fmt:message key="route.add.new"></fmt:message> </a>
            <a href="${pageContext.request.contextPath}/adminListRoute" class="btn btn-default"><fmt:message key="route.list.all"></fmt:message> </a>
        </div>

        <sql:query dataSource="${ds}" var="resultSetStation">
        SELECT * from station;
        </sql:query>
        <sql:query dataSource="${ds}" var="resultSetTrain">
            SELECT * from train;
        </sql:query>


    <c:if test="${route != null}">
            <form class="form-horizontal" action="${pageContext.request.contextPath}/updateRoute" method="post">
        </c:if>
        <c:if test="${route == null}">
            <form class="form-horizontal" action="${pageContext.request.contextPath}/insertRoute" method="post">
        </c:if>

                <caption>
                    <h2>
                        <c:if test="${route != null}">
                            <fmt:message key="route.edit"/>
                        </c:if>
                        <c:if test="${route == null}">
                            <fmt:message key="route.add.new"/>
                        </c:if>
                    </h2>
                </caption>

                <c:if test="${route != null}">
                    <input type="hidden" name="idRoute" id="idRoute" value="<c:out value='${route.routeId}'/>"/>
                </c:if>
            <div class="form-group">
                <label for="firstStation"><fmt:message key="route.first.station"/> </label>
            </div>
            <select class="form-select" id="firstStation" name="firstStation">
                <c:forEach var="row" items="${resultSetStation.rows}">
                    <option value='<c:out value="${row.station_id}"/>'><c:out value="${row.station_name_ua}"/></option>
                </c:forEach>
            </select>
            <br>

            <div class="form-group">
                <label for="lastStation"><fmt:message key="route.last.station"/> </label>
            </div>
            <select class="form-select" id="lastStation" name="lastStation">
                <c:forEach var="row" items="${resultSetStation.rows}">
                    <option value='<c:out value="${row.station_id}"/>'><c:out value="${row.station_name_ua}"/></option>
                </c:forEach>
            </select>

            <div class="form-group">
                <label for="departureDate"><fmt:message key="route.departure"/> </label>
            </div>
            <div class="container">
                <div style="position: relative">
                    <div class="form-group">
                        <div class="input-group" id="departureDate">
                            <input type="text" class="form-control" name="departure" id="departure" />
                            <span class="input-group-addon">
                                <span class="glyphicon glyphicon-calendar"></span>
                            </span>
                        </div>
                    </div>
                </div>
            </div>

            <div class="form-group">
                <label for="arrival"><fmt:message key="route.arrival"/> </label>
            </div>
            <div class="container">
                <div style="position: relative">
                    <div class="form-group">
                        <div class="input-group" id="arrivalDate">
                            <input type="text" class="form-control" name="arrival" id="arrival" />
                            <span class="input-group-addon">
                                <span class="glyphicon glyphicon-calendar"></span>
                            </span>
                        </div>
                    </div>
                </div>
            </div>

            <div class="form-group">
                <label for="trainId"><fmt:message key="route.train.id"/> </label>
            </div>
            <select class="form-select" id="trainId" name="trainId">
                <c:forEach var="row" items="${resultSetTrain.rows}">
                    <option value='<c:out value="${row.train_id}"/>'><c:out value="${row.train_id}"/></option>
                </c:forEach>
            </select>

                <div class="form-group">
                    <label for="routePrice" class="control-label"><fmt:message key="route.price"/> (<fmt:message key="money.uah"/>)</label>
                </div>
                <div>
                    <input type="text" class="form-control" name="routePrice" id="routePrice" />
                </div>



                <br>
                <div class="pull-right">
                    <a href="${pageContext.request.contextPath}/logout" class="btn btn-danger"><fmt:message key="return"/> </a>
                    <button type="submit" class="btn btn-success"><fmt:message key="submit"/></button>
                </div>



            </form>
    </div>
</div>
</div>

<script>
    jQuery(function () {
        jQuery('#departureDate').datetimepicker({
            format: 'YYYY-MM-DD HH-mm'
        });
    });
</script>
<script>
    jQuery(function () {
        jQuery('#arrivalDate').datetimepicker({
            format: 'YYYY-MM-DD HH-mm'
        });
    });
</script>
</body>


</html>
