<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Login Webapp</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
</head>
<body>

<div class="container mt-4">

    <nav class="navbar bg-light">
        <div class="container-fluid">
            <a class="navbar-brand">SSC - Login Webapp</a>
            <a class="btn btn-light btn-sm pull-right" type="button" href="/logout"><i class="fa fa-sign-out"></i>
                &nbsp; Logout</a>
        </div>
    </nav>

    <c:if test="${not empty message}">

        <c:choose>
            <c:when test="${hasError}">
                <div class="alert alert-danger" role="alert">
                    ${message}
                </div>
            </c:when>
            <c:otherwise>
                <div class="alert alert-success" role="alert">
                        ${message}
                </div>
            </c:otherwise>
        </c:choose>
    </c:if>
    ${user.username}
</div>

</body>
</html>

