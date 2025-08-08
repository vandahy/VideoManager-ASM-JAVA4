<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="com.java04.entity.User" %>
<%
    User user = (User) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect("login");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Trang chủ</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.7/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-ndDqU0Gzau9qJ1lfW4pNLlhNTkCfHzAVBReH9diLvGRem5+R9g2FzA8ZGN954O5Q"
            crossorigin="anonymous"></script>
</head>
<style>
    .btn-close {
        filter: invert(1);
    }

    .video-frame {
        background-color: #f8f9fa;
        border: 2px solid #ffa500;
        border-radius: 10px;
        height: 360px;
        display: flex;
        align-items: center;
        justify-content: center;
        font-size: 2rem;
        color: #999;
    }

    .video-info {
        background-color: #fffbe6;
        border: 1px solid #ffa500;
        border-radius: 0.5rem;
        padding: 1rem;
        margin-top: 1rem;
    }

    .action-buttons {
        margin-top: 1rem;
    }

    .btn-like {
        background-color: #0d6efd;
        color: white;
    }

    .btn-share {
        background-color: #fd7e14;
        color: white;
    }

    .video-card img {
        height: 80px;
        object-fit: cover;
    }

    .video-title-link {
        font-weight: bold;
        text-decoration: underline;
        color: #333;
    }

    .video-title-link:hover {
        color: #0d6efd;
    }
</style>
<body>
<%--Menu--%>
<div class="container-fluid p-0">
    <nav class="navbar navbar-expand-sm navbar-dark bg-black">
        <div class="container">
            <a class="navbar-brand text-white d-flex align-items-center" href="home">
                <h3 class="mb-0">ONLINE ENTERTAINMENT</h3>
            </a>

            <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                    data-bs-target="#collapsibleNavbar">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="collapsibleNavbar">
                <ul class="navbar-nav me-auto">
                    <% if (user !=null && user.getAdmin() !=null && user.getAdmin()) { %>
                    <li class="nav-item">
                        <a class="nav-link" href="summary">Thống kê</a>
                    </li>
                    <% } %>
                    <li class="nav-item">
                        <a id="btnFavorite" class="nav-link" href="like" >MY FAVORITE</a>
                    </li>
                    <li class="nav-item dropdown">
                        <a id="btnAccount" class="nav-link dropdown-toggle" href="home" role="button"
                           data-bs-toggle="dropdown">
                            <i class="fa-solid fa-user"></i> MY ACCOUNT
                        </a>
                        <ul class="dropdown-menu border border-orange">
                            <li><a class="dropdown-item" href="login">Login</a></li>
                            <li><a class="dropdown-item" href="#" data-bs-toggle="modal"
                                   data-bs-target="#forgotPassModal">Forgot Password</a></li>
                            <li><a class="dropdown-item" href="change-password" >Change Password</a></li>
                            <li><a class="dropdown-item" href="update-profile" >Edit Profile</a></li>
                            <li><a class="dropdown-item" href="logout">Log Out</a></li>
                        </ul>
                    </li>
                </ul>

                <ul class="navbar-nav d-flex">
                    <li class="nav-item">
                        <a class="nav-link">Chào mừng, <%= user.getFullname() %>!</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
</div>
<%--Menu--%>

<%--List Video--%>
<div id="accountSection" class="container my-5" style="display: block;">
    <div class="row gx-4 gy-4">
        <c:forEach var="v" items="${videos}">
            <div class="col-12 col-sm-6 col-md-4">
                <div class="card h-100 shadow-sm">
                    <a href="videochitiet?videoId=${v.id}">
                        <img src="${v.poster}" class="card-img-top" alt="Youtube Thumbnail">
                    </a>
                    <div class="card-body d-flex flex-column">
                        <h5 class="card-title">${v.title}</h5>
                        <div class="mt-auto d-flex justify-content-end gap-2">
                            <form action="like" method="post">
                                <input type="hidden" name="videoId" value="${v.id}">
                                <button type="submit" class="btn btn-outline-danger">Like</button>
                            </form>
                            <form>
                                <input type="hidden" name="videoId" value="${v.id}">
                                <button type="button" class="btn btn-outline-info" data-bs-toggle="modal"
                                        data-bs-target="#sendEmail" onclick="setShareVideoId('${v.id}')">Share</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>

    <c:set var="currentPage" value="${currentPage}" />
    <c:set var="totalPages" value="${totalPages}" />

    <nav class="d-flex justify-content-center mt-5">
        <ul class="pagination">
            <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                <a class="page-link" href="home?page=1">|&lt;</a>
            </li>
            <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                <a class="page-link" href="home?page=${currentPage - 1}">&lt;&lt;</a>
            </li>

            <c:forEach var="i" begin="1" end="${totalPages}">
                <li class="page-item ${i == currentPage ? 'active' : ''}">
                    <a class="page-link" href="home?page=${i}">${i}</a>
                </li>
            </c:forEach>

            <li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
                <a class="page-link" href="home?page=${currentPage + 1}">&gt;&gt;</a>
            </li>
            <li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
                <a class="page-link" href="home?page=${totalPages}">&gt;|</a>
            </li>
        </ul>
    </nav>

</div>
<%--List Video--%>

<%--My Favorite--%>
<div id="favoriteSection" class="container my-5" style="display: none;">
    <div class="row gx-4 gy-4">
        <c:forEach var="f" items="${favorites}">
            <div class="col-12 col-sm-6 col-md-4">
                <div class="card h-100 shadow-sm">
                    <img src="${f.video.poster}" class="card-img-top" alt="Youtube Thumbnail">
                    <div class="card-body d-flex flex-column">
                        <h5 class="card-title">${f.video.title}</h5>
                        <div class="mt-auto d-flex justify-content-end gap-2">
                            <form action="dislike" method="post">
                                <input type="hidden" name="videoId" value="${f.video.id}">
                                <button type="submit" class="btn btn-outline-danger">Unlike</button>
                            </form>
                            <form>
                                <input type="hidden" name="videoId" value="${v.id}">
                                <button type="button" class="btn btn-outline-info" data-bs-toggle="modal"
                                        data-bs-target="#sendEmail" onclick="setShareVideoId('${v.id}')">Share</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>
<%--My Favorite--%>

<!-- Forgot Password -->
<div class="modal fade" id="forgotPassModal" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content border border-black">
            <div class="modal-header bg-black">
                <h5 class="modal-title fw-bold text-white">Forgot Password</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body">
                <form action="forgot-password" method="post">
                    <div class="mb-3">
                        <label class="form-label fw-bold text-uppercase">Username?</label>
                        <input type="text" name="username" class="form-control border-warning">
                    </div>
                    <div class="mb-3">
                        <label class="form-label fw-bold text-uppercase">Email?</label>
                        <input type="email" name="email" class="form-control border-warning">
                    </div>
                    <button type="submit" class="btn btn-outline-info float-end">Retrieve</button>
                </form>
            </div>
        </div>
    </div>
</div>
<!-- Forgot Password -->

<!-- EDIT PROFILE -->
<div class="modal fade" id="editProfileModal" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered modal-lg">
        <div class="modal-content">

            <div class="modal-header bg-black">
                <h5 class="modal-title text-white">EDIT PROFILE</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
            </div>

            <div class="modal-body">
                <form>
                    <div class="row g-3">
                        <div class="col-md-6">
                            <label class="form-label">Username?</label>
                            <input type="text" class="form-control">
                        </div>
                        <div class="col-md-6">
                            <label class="form-label">Password?</label>
                            <input type="password" class="form-control">
                        </div>
                        <div class="col-md-6">
                            <label class="form-label">Full Name?</label>
                            <input type="text" class="form-control">
                        </div>
                        <div class="col-md-6">
                            <label class="form-label">Email Adddress?</label>
                            <input type="email" class="form-control">
                        </div>
                    </div>
                </form>
            </div>

            <div class="modal-footer">
                <button type="submit" class="btn btn-outline-info px-4">Change</button>
            </div>
        </div>
    </div>
</div>
<!-- EDIT PROFILE -->

<!-- Send Email -->
<div class="modal fade" id="sendEmail" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered modal-lg">
        <div class="modal-content">

            <div class="modal-header bg-black">
                <h5 class="modal-title text-white">SEND VIDEO TO YOUR FRIEND</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
            </div>

            <form method="post" action="share">
                <div class="modal-body">
                    <input type="hidden" id="shareVideoId" name="videoId"> <!-- để gán video -->
                    <div class="row g-3">
                        <div class="col-md-12">
                            <label class="form-label">Your Friend's Email?</label>
                            <input type="email" name="email" class="form-control" required>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn btn-outline-info px-4">Send</button>
                </div>
            </form>
        </div>
    </div>
</div>

<script>
    function setShareVideoId(id) {
        document.getElementById("shareVideoId").value = id;
    }

    // Bắt sự kiện click vào poster hoặc title
    document.addEventListener("DOMContentLoaded", function () {
        const posters = document.querySelectorAll(".card-img-top");
        const titles = document.querySelectorAll(".card-title");

        posters.forEach((poster) => {
            poster.style.cursor = "pointer";
            poster.addEventListener("click", function () {
                const title = this.closest(".card").querySelector(".card-title").innerText;
                showDetail(title, "This is a description for: " + title);
            });
        });

        titles.forEach((titleElement) => {
            titleElement.style.cursor = "pointer";
            titleElement.addEventListener("click", function () {
                const title = this.innerText;
                showDetail(title, "This is a description for: " + title);
            });
        });
    });
    document.addEventListener("DOMContentLoaded", function () {
        const btnFavorite = document.getElementById("btnFavorite");
        const btnAccount = document.getElementById("btnAccount");
        const favoriteSection = document.getElementById("favoriteSection");
        const accountSection = document.getElementById("accountSection");

        btnFavorite.addEventListener("click", function (e) {
            e.preventDefault();
            accountSection.style.display = "none";
            favoriteSection.style.display = "block";
        });

        btnAccount.addEventListener("click", function () {
            favoriteSection.style.display = "none";
            accountSection.style.display = "block";
        });
    });
</script>
</body>
</html>
