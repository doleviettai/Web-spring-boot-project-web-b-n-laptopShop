<!DOCTYPE html>
<html lang="en" xmlns:jsp="http://www.w3.org/1999/XSL/Transform">
<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
    <meta name="description" content="" />
    <meta name="author" content="" />
    <title>Product ${product.id} - SB Admin</title>
    <%@ page contentType="text/html; charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
                    <div class="col-12 mx-auto">
                        <div class="d-flex justify-content-between">
                            <h3>Table detail with id: ${id}</h3>
                        </div>

                        <hr />

                        <div class="card" style="width:60%">
                            <img class="card-img-top" src="/images/product/${product.image}" alt="Card image cap"/>

                            <div class="card-header">
                                Product information
                            </div>
                            <ul class="list-group list-group-flush">
                                <li class="list-group-item">ID: ${product.id}</li>
                                <li class="list-group-item">Name: ${product.name}</li>
                                <li class="list-group-item">Price: <fmt:formatNumber type="number" value="${product.price}" /></li>
                                <li class="list-group-item">Detail Desc: ${product.detailDesc}</li>
                                <li class="list-group-item">Short Desc: ${product.shortDesc}</li>
                                <li class="list-group-item">Quantity: ${product.quantity}</li>
                                <li class="list-group-item">Factory: ${product.factory}</li>
                                <li class="list-group-item">Target: ${product.target}</li>
                            </ul>

                            <a href="/admin/product" class="btn btn-success">Back</a>
                        </div>

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
</body>
</html>
