<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="com.java04.entity.User" %>

<!DOCTYPE html>
<html>

<head>
<title>Trang chủ</title>
<link rel="stylesheet"
    href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.7/dist/js/bootstrap.bundle.min.js"
    integrity="sha384-ndDqU0Gzau9qJ1lfW4pNLlhNTkCfHzAVBReH9diLvGRem5+R9g2FzA8ZGN954O5Q"
    crossorigin="anonymous"></script>
</head>
<style>
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
    <jsp:include page="menu.jsp" />
    <%--Menu--%>

    <%--List Video--%>
    <div id="accountSection" class="container my-5" style="display: block;">
        <div class="row gx-4 gy-4">
            <c:forEach var="v" items="${videos}">
                <div class="col-12 col-sm-6 col-md-4">
                    <div class="card h-100 shadow-sm">
                        <a href="videochitiet?videoId=${v.id}">
                            <img src="${v.poster}" class="card-img-top"
                                alt="Youtube Thumbnail">
                        </a>
                        <div class="card-body d-flex flex-column">
                            <h5 class="card-title">${v.title}</h5>

                            <div class="mt-auto d-flex justify-content-end gap-2">
                                <form action="like" method="post">
                                    <input type="hidden" name="videoId" value="${v.id}">
                                    <button type="submit"
                                        class="btn btn-outline-danger">Like</button>
                                </form>

                                <form>
                                    <input type="hidden" name="videoId" value="${v.id}">
                                    <button type="button" class="btn btn-outline-info"
                                        data-bs-toggle="modal" data-bs-target="#sendEmail"
                                        onclick="setShareVideoId('${v.id}')">Share</button>
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

    <!-- Send Email -->
    <div class="modal fade" id="sendEmail" tabindex="-1" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered modal-lg">
            <div class="modal-content">

                <div class="modal-header bg-black">
                    <h5 class="modal-title text-white">SEND VIDEO TO YOUR FRIEND
                    </h5>
                    <button type="button" class="btn-close"
                        data-bs-dismiss="modal"></button>
                </div>

                <form method="post" action="share">
                    <div class="modal-body">
                        <input type="hidden" id="shareVideoId" name="videoId">
                        <!-- để gán video -->
                        <div class="row g-3">
                            <div class="col-md-12">
                                <label class="form-label">Your Friend's
                                    Email?</label>
                                <input type="email" name="email"
                                    class="form-control" required>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="submit"
                            class="btn btn-outline-info px-4">Send</button>
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
    </script>
</body>

</html>