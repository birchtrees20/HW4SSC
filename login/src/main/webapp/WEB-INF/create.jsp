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
            <a class="navbar-brand" href="/">SSC - Login Webapp</a>
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

    <div class="row justify-content-md-center">
        <div class="col-sm-12 col-md col-lg-4 my-auto">
            <h2>Create New User</h2>
            <p>${error}</p>
            <form action="/user/create" method="post">

                <div class="input-group mb-3">
                    <span class="input-group-text" id="username" style="width: 40px"><i class="fa fa-user"></i></span>
                    <input type="text" class="form-control" name="username" placeholder="Username" aria-label="Username"
                           aria-describedby="username">
                </div>

                <div class="input-group mb-3">
                    <span class="input-group-text" id="displayName" style="width: 40px"><i class="fa fa-user"></i></span>
                    <input type="text" class="form-control" name="displayName" placeholder="Display Name" aria-label="displayName"
                           aria-describedby="displayName">
                </div>

                <div class="input-group mb-3">
                    <span class="input-group-text" id="password" style="width: 40px"><i class="fa fa-key"></i></span>
                    <input type="password" class="form-control" name="password" placeholder="Password"
                           aria-label="Password"
                           aria-describedby="password">
                </div>

                <div class="input-group mb-3">
                    <span class="input-group-text" id="cpassword" style="width: 40px"><i class="fa fa-key"></i></span>
                    <input type="password" class="form-control" name="cpassword" placeholder="Confirm Password"
                           aria-label="Password"
                           aria-describedby="cpassword">
                </div>


                <div class="d-grid gap-2">
                    <button class="btn btn-primary d-block" type="submit"><i class="fa fa-plus"></i> &nbsp; Create
                    </button>
                </div>
            </form>
        </div>
    </div>

</div>

</body>
</html>

