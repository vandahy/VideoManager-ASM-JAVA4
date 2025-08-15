<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" isELIgnored="false" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
            <html>

            <head>
                <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
                <title>Detail Video</title>
            </head>
            <style>
                .navbar-yellow {
                    background-color: #ffc107 !important;
                    /* vàng đậm Bootstrap warning */
                }

                /* Style cho phần tiêu đề video trong danh sách "Video khác" */
                .col-md-4 a {
                    color: black !important;
                    text-decoration: none;
                    transition: all 0.2s ease;
                }

                .col-md-4 a:hover {
                    color: black !important;
                    text-decoration: underline;
                }

                .video-title {
                    color: black !important;
                    text-decoration: none;
                    transition: all 0.2s;
                }

                .video-title:hover {
                    text-decoration: underline;
                }

                .video-stats {
                    display: flex;
                    gap: 20px;
                    margin-bottom: 15px;
                }

                .video-stats span {
                    display: flex;
                    align-items: center;
                    gap: 5px;
                    font-size: 14px;
                    color: #666;
                }

                .video-stats i {
                    color: #ff6b6b;
                }

                .video-stats .likes i {
                    color: #e74c3c;
                }

                .video-stats .shares i {
                    color: #3498db;
                }
            </style>

            <body>
                <%--Menu--%>
                    <jsp:include page="menu.jsp" />
                    <%--Menu--%>
                        <div class="container mt-5">
                            <div class="row">
                                <!-- VIDEO CHÍNH -->
                                <div class="col-md-8">
                                    <div class="ratio ratio-16x9 mb-3">
                                        <iframe src="${video.links}" title="${video.title}" allowfullscreen></iframe>
                                    </div>
                                    <h3>${video.title}</h3>
                                    <p>${video.description}</p>
                                    <div class="video-stats">
                                        <span class="views">
                                            <i class="fas fa-eye"></i>
                                            ${video.views} lượt xem
                                        </span>
                                        <span class="likes">
                                            <i class="fas fa-heart"></i>
                                            ${likeCount} lượt thích
                                        </span>
                                        <span class="shares">
                                            <i class="fas fa-share"></i>
                                            ${shareCount} lượt chia sẻ
                                        </span>
                                    </div>
                                    <div class="mt-auto d-flex justify-content-end gap-2">
                                        <c:choose>
                                            <c:when test="${hasLiked}">
                                                <form action="dislike" method="post">
                                                    <input type="hidden" name="videoId" value="${video.id}">
                                                    <input type="hidden" name="returnUrl"
                                                        value="videochitiet?videoId=${video.id}">
                                                    <button type="submit"
                                                        class="btn btn-outline-secondary">Unlike</button>
                                                </form>
                                            </c:when>
                                            <c:otherwise>
                                                <form action="like" method="post">
                                                    <input type="hidden" name="videoId" value="${video.id}">
                                                    <input type="hidden" name="returnUrl"
                                                        value="videochitiet?videoId=${video.id}">
                                                    <button type="submit" class="btn btn-outline-danger">Like</button>
                                                </form>
                                            </c:otherwise>
                                        </c:choose>
                                        <form>
                                            <input type="hidden" name="videoId" value="${video.id}">
                                            <button type="button" class="btn btn-outline-info" data-bs-toggle="modal"
                                                data-bs-target="#sendEmail"
                                                onclick="setShareVideoId('${video.id}')">Share</button>
                                        </form>
                                    </div>
                                </div>

                                <!-- VIDEO KHÁC -->
                                <div class="col-md-4">
                                    <h5>📺 Video khác</h5>
                                    <c:forEach var="v" items="${recommended}">
                                        <div class="d-flex mb-2">
                                            <a href="videochitiet?videoId=${v.id}">
                                                <img src="${v.poster}" width="100" height="60"
                                                    style="object-fit: cover;" alt="${v.title}">
                                            </a>
                                            <div class="ms-2">
                                                <strong style="font-size: 0.9rem">
                                                    <a href="videochitiet?videoId=${v.id}"
                                                        class="video-title">${v.title}</a>
                                                </strong>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </div>
                            </div>
                        </div>

                        <!-- Send Email -->
                        <div class="modal fade" id="sendEmail" tabindex="-1" aria-hidden="true">
                            <div class="modal-dialog modal-dialog-centered modal-lg">
                                <div class="modal-content">

                                    <div class="modal-header bg-black">
                                        <h5 class="modal-title text-white">SEND VIDEO TO
                                            YOUR FRIEND</h5>
                                        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                                    </div>

                                    <form method="post" action="share">
                                        <div class="modal-body">
                                            <input type="hidden" id="shareVideoId" name="videoId"> <!-- để gán video -->
                                            <input type="hidden" name="returnUrl"
                                                value="videochitiet?videoId=${video.id}">
                                            <div class="row g-3">
                                                <div class="col-md-12">
                                                    <label class="form-label">Your Friend's
                                                        Email?</label>
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
                        <!-- Send Email -->

                        <script>
                            function setShareVideoId(id) {
                                document.getElementById("shareVideoId").value = id;
                            }
                        </script>
            </body>

            </html>