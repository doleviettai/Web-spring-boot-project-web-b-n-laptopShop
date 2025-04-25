
<!DOCTYPE html>
<html lang="en" xmlns:jsp="http://www.w3.org/1999/XSL/Transform" xmlns:form="http://www.w3.org/1999/xhtml" xmlns:c="">
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
                    <div class="col-md-6 col-12 mx-auto">
                        <h3>Create a product</h3>
                        <hr />
                        <form:form method="post" enctype="multipart/form-data" action="/admin/product/create" modelAttribute="newProduct" class="row">
                            <c:set var="errorName">
                                <form:errors path="name" cssClass="invalid-feedback"/>
                            </c:set>
                            <c:set var="errorPrice">
                                <form:errors path="price" cssClass="invalid-feedback"/>
                            </c:set>
                            <c:set var="errorDetailDesc">
                                <form:errors path="detailDesc" cssClass="invalid-feedback"/>
                            </c:set>
                            <c:set var="errorShortDesc">
                                <form:errors path="shortDesc" cssClass="invalid-feedback"/>
                            </c:set>
                            <c:set var="errorQuantity">
                                <form:errors path="quantity" cssClass="invalid-feedback"/>
                            </c:set>

                            <div class="mb-3 col-12 col-md-6">
                                <label class="form-label">Name:</label>
                                <form:input type="text" class="form-control ${not empty errorName ? 'is-invalid' : ''}" path="name"/>
                                ${errorName}
                            </div>
                            <div class="mb-3 col-12 col-md-6">
                                <label class="form-label">Price:</label>
                                <form:input type="text" class="form-control ${not empty errorPrice ? 'is-invalid' : ''}" path="price"/>
                                ${errorPrice}
                            </div>
                            <div class="mb-3 col-12 col-md-12">
                                <label class="form-label">Detail description:</label>
                                <form:input type="text" class="form-control ${not empty errorDetailDesc ? 'is-invalid' : ''}" path="detailDesc"/>
                                ${errorDetailDesc}
                            </div>
                            <div class="mb-3 col-12 col-md-6">
                                <label class="form-label">Short description:</label>
                                <form:input type="text" class="form-control ${not empty errorShortDesc ? 'is-invalid' : ''}" path="shortDesc"/>
                                ${errorShortDesc}
                            </div>
                            <div class="mb-3 col-12 col-md-6">
                                <label class="form-label">Quantity:</label>
                                <form:input type="text" class="form-control ${not empty errorQuantity ? 'is-invalid' : ''}" path="quantity"/>
                                ${errorQuantity}
                            </div>
                            <div class="mb-3 col-12 col-md-6">
                                <label class="form-label">Facetory:</label>
                                <form:select class="form-select" path="factory">
                                    <form:option value="Apple">Apple(MacBook)</form:option>
                                    <form:option value="Asus">Asus</form:option>
                                    <form:option value="Lenovo">lenovo</form:option>
                                    <form:option value="Dell">Dell</form:option>
                                    <form:option value="Acer">Acer</form:option>
                                </form:select>
                            </div>

                            <div class="mb-3 col-12 col-md-6">
                                <label class="form-label">Target:</label>
                                <form:select class="form-select" path="target">
                                    <form:option value="Gaming">Chuyên chơi game</form:option>
                                    <form:option value="Sinhvien-Vanphong">Sinh Viên - Văn Phòng</form:option>
                                    <form:option value="ThietKeDoHoa">Thiết Kế Đồ Họa</form:option>
                                    <form:option value="Mong-Nhe">Mỏng - Nhẹ</form:option>
                                    <form:option value="DoanhNhan">Doanh Nhân</form:option>
                                </form:select>
                            </div>

                            <div class="mb-3 col-12 col-md-6">
                                <label class="form-label" for="avatarFile">Hình Ảnh:</label>
                                <input type="file" class="form-control" id="avatarFile" name="hoidanitFile" multiple/>
                            </div>
                            <div class="mb-3 col-12">
                                <img style="max-height:250px; display: none;" alt="avatar preview" id="avatarPreview">
                            </div>

                            <button type="submit" class="btn btn-primary">Create</button>
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
    avatarFile.change(function (e) {
    const imgURL = URL.createObjectURL(e.target.files[0]);
    $("#avatarPreview").attr("src", imgURL);
    $("#avatarPreview").css({ "display": "block" });
    });
    });
</script>
</body>
</html>
