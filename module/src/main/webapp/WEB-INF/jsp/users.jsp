<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>List Users</title>
    <link href="/css/bootstrap.min.css" rel="stylesheet">
    <script src="/js/bootstrap.min.js"></script>
</head>
<body>

<div class="container">
    <div class="page-header">
        <h1>Список участников</h1>
    </div>
</div>

<div class="container">
    <div class="table-responsive">
        <table class="table table-striped table-bordered">
            <tr>
                <th>Name:</th>
                <th>Surname:</th>
                <th>Middlename:</th>
                <th>Position:</th>
                <th>Company:</th>
                <th>Phone Number:</th>
                <th>email:</th>
            </tr>
            <c:forEach items="${users}" var="user">
                <tr>
                    <td>${user.name}</td>
                    <td>${user.surname}</td>
                    <td>${user.middleName}</td>
                    <td>${user.position}</td>
                    <td>${user.company}</td>
                    <td>${user.phoneNumber}</td>
                    <td>${user.email}</td>
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
