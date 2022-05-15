<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="English" value="en"/>
<c:set var="lang" value="${not empty param.lang ? param.lang : not empty lang ? lang : English}" scope="session" />

<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html language="${lang}">
<head>
    <title>Login in system</title>
    <style>
        div{
            padding: 4px;
        }
    </style>
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

    <!-- Optional theme -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"
          integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

    <!-- jQuery -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
    <!-- Latest compiled and minified JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
            integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
            crossorigin="anonymous"></script>

    <SCRIPT type="text/javascript">
        window.history.forward();

        function noBack() {
            window.history.forward();
        }
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
                <li class="active"><a href="${pageContext.request.contextPath}/login.jsp"
                                      class="btn btn-primary"><fmt:message key="please.login"/></a</li>
                <li><a href="${pageContext.request.contextPath}/register.jsp" class="btn btn-success"><fmt:message
                        key="or.register"/></a></li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown"><fmt:message key="language.current"/><b
                            class="caret"></b></a>
                    <ul class="dropdown-menu">
                        <li><a href="?lang=en">English</a></li>
                        <li><a href="?lang=ua">Українська</a></li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
</div>

<div id="regCompleteModal" class="modal fade" tabindex="-1">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header">
                <button class="close" data-dismiss="modal">X</button>
                <h4 class="modal-title"><fmt:message key="register.complete"/></h4>
            </div>

            <div class="modal-footer">
                <button class="btn btn-success" data-dismiss="modal"><fmt:message key="close"/></button>
            </div>
        </div>
    </div>
</div>

<div id="loginErrorModal" class="modal fade" tabindex="-1">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header">
                <button class="close" data-dismiss="modal">X</button>
                <h4 class="modal-title"><fmt:message key="login.error"/></h4>
            </div>
            <div class="modal-body">${loginError}</div>
            <div class="modal-footer">
                <button class="btn btn-danger" data-dismiss="modal"><fmt:message key="close"/></button>
            </div>
        </div>
    </div>
</div>

<div id="ticketCreateValidationErrorModal" class="modal fade" tabindex="-1">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header">
                <button class="close" data-dismiss="modal">X</button>
                <h4 class="modal-title"><fmt:message key="user.has.to.login"/></h4>
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
        $('#ticketCreateValidationErrorModal').modal("show");
        $('#ticketCreateValidationErrorModal').on('hidden.bs.modal', function (event){
            <c:remove var="ticketCreateValidationError"/>
        })

    </script>
</c:if>

<c:if test="${not empty loginError}">
    <script>
        $('#loginErrorModal').modal("show");
        $('#loginErrorModal').on('hidden.bs.modal', function (event){
            <c:remove var="loginError"/>
        })

    </script>
</c:if>

<c:if test="${not empty registrationComplete}">
    <script>
        $('#regCompleteModal').modal("show");

        $('#regCompleteModal').on('hidden.bs.modal', function (event){
            <c:remove var="registrationComplete"/>
        })

    </script>
</c:if>

<div class="jumbotron">
<div class="container">

    <h1><fmt:message key="please.login"/></h1>
    <form class="form-horizontal" method="post" action="${pageContext.request.contextPath}/login">
        <div class="form-group">
            <label for="nickname" class="control-label"><fmt:message key="nickname"/> </label>
        </div>
        <div>
            <input type="text" class="form-control" name="nickname" id="nickname">
        </div>


        <div class="form-group">
            <label for="password" class="control-label"><fmt:message key="password"/> </label>
        </div>
        <div>
            <input type="password" class="form-control" name="password" id="password">
        </div>

            <div class="pull-right">

                <a href="${pageContext.request.contextPath}/logout" class="btn btn-danger"><fmt:message key="return"/> </a>
                <a href="${pageContext.request.contextPath}/register.jsp" class="btn btn-warning"><fmt:message key="or.register"/></a>
                <button type="submit" class="btn btn-success"><fmt:message key="please.login"/></button>
            </div>

        </form>
    <div/>
</div>
</div>



</body>
</html>