
<!DOCTYPE html>
<html lang="en" xmlns:jsp="http://www.w3.org/1999/XSL/Transform" xmlns:form="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
    <meta name="description" content="" />
    <meta name="author" content="" />
    <title>Dashboard - SB Admin</title>
    <%@ page contentType="text/html; charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
    <link href="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/style.min.css" rel="stylesheet" />
    <link href="/css/styles.css" rel="stylesheet" />
    <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
</head>
<body class="sb-nav-fixed">
<jsp:include page="../layout/header.jsp"/>
<div id="layoutSidenav">
    <div id="layoutSidenav_nav">
        <jsp:include page="../layout/sidebar.jsp"/>
    </div>
    <div id="layoutSidenav_content">
        <main>

<div class="container mt-5">
    <div class="row">
        <div class="col-md-6 col-12 mx-auto">
            <h3>Update a user</h3>
            <hr />
            <form:form method="post" action="/admin/user/update" modelAttribute="newUser" enctype="multipart/form-data">
                <div class="mb-3">
                    <label class="form-label">ID:</label>
                    <form:input type="text" class="form-control" path="id"/>
                </div>
                <div class="mb-3">
                    <label class="form-label">Email:</label>
                    <form:input type="email" class="form-control" path="email" disabled="true"/>
                </div>
                <div class="mb-3">
                    <label class="form-label">Phone number:</label>
                    <form:input type="text" class="form-control" path="phone"/>
                </div>
                <div class="mb-3">
                    <label class="form-label">Full Name:</label>
                    <form:input type="text" class="form-control" path="fullname"/>
                </div>
                <div class="mb-3">
                    <label class="form-label">Role:</label>
                    <form:input type="text" class="form-control" path="role.name"/>
                </div>
                <div class="mb-3">
                    <label class="form-label">Address:</label>
                    <form:input type="text" class="form-control" path="address"/>
                </div>
                <div class="mb-3 col-12 col-md-6">
                    <label class="form-label" for="avatarFile">Avatar:</label>
                    <input type="file" class="form-control" id="avatarFile" name="hoidanitFile" multiple/>
                </div>
                <div class="mb-3 col-12">
                    <img style="max-height:250px; display: none;" alt="avatar preview" id="avatarPreview">
                </div>

                <button type="submit" class="btn btn-primary">Update</button>
            </form:form>
        </div>

    </div>
</div>
        </main>
        <jsp:include page="../layout/footer.jsp"/>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
<script src="/js/scripts.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.8.0/Chart.min.js" crossorigin="anonymous"></script>
<script src="/assets/demo/chart-area-demo.js"></script>
<script src="/assets/demo/chart-bar-demo.js"></script>
<script src="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/umd/simple-datatables.min.js" crossorigin="anonymous"></script>
<script src="/js/datatables-simple-demo.js"></script>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<script>
    $(document).ready(() => {
        const avatarFile = $("#avatarFile");
        const orgImage = "${newUser.avatar}";
        if (orgImage) {
            const urlImage = "/images/avatar/" + orgImage;
            $("#avatarPreview").attr("src", urlImage);
            $("#avatarPreview").css({ "display": "block" });
        }

        avatarFile.change(function (e) {
            const imgURL = URL.createObjectURL(e.target.files[0]);
            $("#avatarPreview").attr("src", imgURL);
            $("#avatarPreview").css({ "display": "block" });
        });
    });
</script>
</body>
</html>
