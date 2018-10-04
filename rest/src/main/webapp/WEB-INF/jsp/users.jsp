<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>List Users</title>
</head>
<body>

    <c:forEach items="${users} var=${user}">
        ${user.name}
    </c:forEach>

</body>
</html>
