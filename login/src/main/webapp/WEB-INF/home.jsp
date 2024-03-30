<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Login Webapp</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>

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

    <div class="row">
        <div class="col-12">
            <h3 class="my-4">Welcome, ${username}</h3>

        </div>
    </div>

    <div class="row">
        <div class="col-12">
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

        </div>
    </div>

    <div class="row">
        <div class="col-12">
            <a class="btn btn-success btn-sm" type="button" href="/user/create"><i class="fa fa-plus"></i>
                &nbsp; New User</a>
        </div>
    </div>

    <div class="row">
        <div class="col-12">
            <table class="table table-striped table-bordered">
                <thead>
                <tr>
                    <th class="py-3">Id</th>
                    <th class="py-3">Username</th>
                    <th class="py-3">Display Name</th>
                    <th class="py-3">Actions</th>
                </tr>
                </thead>
                <tbody>

                <c:forEach var="user" items="${users}">
                    <!-- Modal -->
                    <div class="modal fade" id="exampleModal_${user.username}" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="exampleModalLabel">Delete User</h5>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                </div>
                                <div class="modal-body">
                                    Are you sure you would like to delete the user?
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                    <form action="/user/delete" method="GET">
                                        <input type="hidden" name="username" value="${user.username}">
                                        <button type="submit" class="btn btn-danger">
                                            <i class="fa fa-trash"></i> Delete
                                        </button>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                    <tr>
                        <td class="py-3">${user.id}</td>
                        <td class="py-3">${user.username}</td>
                        <td class="py-3">${user.displayName}</td>
                        <td class="py-3">
                            <a class="btn btn-warning btn-sm" type="button" href="/user/edit?username=${user.username}"><i class="fa fa-pencil"></i></a>
                            <a class="btn btn-info btn-sm" type="button" href="/user/password?username=${user.username}"><i class="fa fa-key"></i></a>

                            <c:if test="${currentUser.username != user.username}">
                                <%--<a class="btn btn-danger btn-sm" type="button" href="/user/delete?username=${user.username}"><i
                                        class="fa fa-trash"></i></a>--%>

                                <%--onclick="location.href='/user/delete?username=${user.username}';"--%>

                                <button class="btn btn-danger btn-sm" type="button" data-bs-toggle="modal" data-bs-target="#exampleModal_${user.username}">
                                <i class="fa fa-trash"></i>
                                </button>




                            </c:if>

                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

        </div>
    </div>



</div>

</body>
</html>

