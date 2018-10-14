<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>List Users</title>
    <link href="/css/bootstrap.min.css" rel="stylesheet">
    <script src="/js/bootstrap.min.js"></script>
    <script src="/js/jquery-3.3.1.min.js"></script>
</head>
<body>

<nav class="navbar navbar-default navbar-fixed-top">
    <div class="container">
        ...
    </div>
</nav>

<div class="container">
    <div class="page-header">
        <h1>Список участников</h1>
    </div>
</div>

<div class="container">
    <div class="table-responsive">
        <table class="table table-striped table-bordered">
            <tr>
                <th>userFirstName:</th>
                <th>userLastName:</th>
                <th>userName:</th>
            </tr>
            <c:forEach items="${users}" var="user">
                <tr>
                    <td>${user.userFirstName}</td>
                    <td>${user.userLastName}</td>
                    <td>${user.userName}</td>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>

<footer class="footer">
    <div class="container">
        <p class="text-muted">@copy by ChatBot for SBT</p>
    </div>
</footer>

</body>
</html>
