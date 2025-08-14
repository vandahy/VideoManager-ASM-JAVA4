<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<style>
    .btn-close {
        filter: invert(1);
    }
</style>

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
                    <li class="nav-item">
                        <a id="btnFavorite" class="nav-link" href="like">MY FAVORITE</a>
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
                            <li><a class="dropdown-item" href="change-password">Change Password</a>
                            </li>
                            <li><a class="dropdown-item" href="update-profile">Edit Profile</a></li>
                            <li><a class="dropdown-item" href="logout">Log Out</a></li>
                        </ul>
                    </li>
                </ul>

                <c:if test="${not empty user}">
                    <ul class="navbar-nav d-flex">
                        <li class="nav-item">
                            <a class="nav-link">Chào mừng, ${user.fullname}!</a>
                        </li>
                    </ul>
                </c:if>

            </div>
        </div>
    </nav>
</div>
<%--Menu--%>

<!-- Forgot Password -->
<div class="modal fade" id="forgotPassModal" tabindex="-1"
     aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content border border-black">
            <div class="modal-header bg-black">
                <h5 class="modal-title fw-bold text-white">Forgot Password
                </h5>
                <button type="button" class="btn-close"
                        data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body">
                <form action="forgot-password" method="post">
                    <div class="mb-3">
                        <label
                                class="form-label fw-bold text-uppercase">Username?</label>
                        <input type="text" name="username"
                               class="form-control border-warning">
                    </div>
                    <div class="mb-3">
                        <label
                                class="form-label fw-bold text-uppercase">Email?</label>
                        <input type="email" name="email"
                               class="form-control border-warning">
                    </div>
                    <button type="submit"
                            class="btn btn-outline-info float-end">Retrieve</button>
                </form>
            </div>
        </div>
    </div>
</div>
<!-- Forgot Password -->

<%--My Favorite--%>
<div id="favoriteSection" class="container my-5" style="display: none;">
    <div class="row gx-4 gy-4">
        <c:forEach var="f" items="${favorites}">
            <div class="col-12 col-sm-6 col-md-4">
                <div class="card h-100 shadow-sm">
                    <img src="${f.video.poster}" class="card-img-top"
                         alt="Youtube Thumbnail">
                    <div class="card-body d-flex flex-column">
                        <h5 class="card-title">${f.video.title}</h5>
                        <div class="mt-auto d-flex justify-content-end gap-2">
                            <form action="dislike" method="post">
                                <input type="hidden" name="videoId"
                                       value="${f.video.id}">
                                <input type="hidden" name="section"
                                       value="favorite">
                                <button type="submit"
                                        class="btn btn-outline-danger">Unlike</button>
                            </form>
                            <form>
                                <input type="hidden" name="videoId"
                                       value="${v.id}">
                                <button type="button"
                                        class="btn btn-outline-info"
                                        data-bs-toggle="modal"
                                        data-bs-target="#sendEmail"
                                        onclick="setShareVideoId('${v.id}')">Share</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>
<%--My Favorite--%>

<script>
    document.addEventListener("DOMContentLoaded", function () {
        const btnFavorite = document.getElementById("btnFavorite");
        const btnAccount = document.getElementById("btnAccount");
        const favoriteSection = document.getElementById("favoriteSection");
        const accountSection = document.getElementById("accountSection");

        btnFavorite.addEventListener("click", function (e) {
            e.preventDefault();
            if (window.location.pathname.includes('home')) {
                accountSection.style.display = "none";
                favoriteSection.style.display = "block";
            } else {
                window.location.href = "home?section=favorite";
            }
        });

        btnAccount.addEventListener("click", function () {
            favoriteSection.style.display = "none";
            accountSection.style.display = "block";
        })

        const urlParams = new URLSearchParams(window.location.search);
        if (urlParams.get('section') === 'favorite') {
            accountSection.style.display = "none";
            favoriteSection.style.display = "block";
        }
    });
</script>