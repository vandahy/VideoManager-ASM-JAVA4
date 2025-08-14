<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Thống kê</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .table-wrap {
            overflow-x: auto;
        }
    </style>
    <script>
        // Router: giống trang video-list-admin
        (function () {
            document.addEventListener('DOMContentLoaded', function () {
                const containerSelector = '.container.mt-4';

                function loadPage(url, pushState) {
                    fetch(url, {headers: {'X-Requested-With': 'XMLHttpRequest'}})
                        .then(function (res) {
                            return res.text();
                        })
                        .then(function (html) {
                            var parser = new DOMParser();
                            var doc = parser.parseFromString(html, 'text/html');
                            var newContainer = doc.querySelector(containerSelector);
                            var currentContainer = document.querySelector(containerSelector);
                            if (newContainer && currentContainer) {
                                currentContainer.innerHTML = newContainer.innerHTML;
                                if (pushState) {
                                    history.pushState({url: url}, '', url);
                                }
                                window.scrollTo(0, 0);
                            } else if (pushState) {
                                window.location.href = url;
                            }
                        })
                        .catch(function () {
                            window.location.href = url;
                        });
                }

                function handleNavbarClick(event) {
                    var anchor = event.currentTarget;
                    var url = anchor.getAttribute('href');
                    if (!url || url.startsWith('http')) {
                        return;
                    }
                    event.preventDefault();
                    if (url.indexOf('/logout') !== -1) {
                        window.location.href = url;
                        return;
                    }
                    loadPage(url, true);
                }

                document.querySelectorAll('.navbar a.nav-link').forEach(function (a) {
                    a.addEventListener('click', handleNavbarClick);
                });

                window.addEventListener('popstate', function (e) {
                    if (e.state && e.state.url) {
                        loadPage(e.state.url, false);
                    }
                });
            });
        })();
    </script>
    <script>
        // Handlers cho 2 form tìm kiếm (Favorite Users & Shared Friends)
        function submitFavoriteUsersSearch(event) {
            event.preventDefault();
            var input = document.getElementById('favUsersSearch');
            var title = (input && input.value || '').trim();
            if (!title) {
                alert('Vui lòng nhập video title');
                return;
            }
            document.getElementById('favUsersForm').submit();
        }

        function submitSharedFriendsSearch(event) {
            event.preventDefault();
            var input = document.getElementById('sharedSearch');
            var title = (input && input.value || '').trim();
            if (!title) {
                alert('Vui lòng nhập video title');
                return;
            }
            document.getElementById('sharedForm').submit();
        }
    </script>
    <script>
        // Hiển thị alert nếu server set thuộc tính requestScope.alertMessage
        document.addEventListener('DOMContentLoaded', function () {
            var msg = '${requestScope.alertMessage}';
            if (msg && msg !== 'null' && msg !== '') {
                alert(msg);
            }
        });
    </script>
    <script>
        // Giữ lại tab đã mở sau khi submit
        document.addEventListener('DOMContentLoaded', function () {
            var activeTab = '${requestScope.activeReportTab}';
            if (activeTab) {
                var triggerEl = document.querySelector('button[data-bs-target="#' + activeTab + '"]');
                if (triggerEl && window.bootstrap && bootstrap.Tab) {
                    var tab = new bootstrap.Tab(triggerEl);
                    tab.show();
                }
            }
        });
    </script>
</head>
<body>

<nav class="navbar navbar-expand-lg navbar-warning bg-warning">
    <div class="container-fluid">
        <a class="navbar-brand fw-bold text-dark" href="#">👑 Admin Dashboard</a>
        <ul class="navbar-nav me-auto">
            <li class="nav-item">
                <!-- Link tới trang quản lý người dùng, dùng contextPath để không phụ thuộc cấu hình app -->
                <a class="nav-link text-dark" href="${pageContext.request.contextPath}/home">Trang chủ</a>
            </li>
            <li class="nav-item">
                <!-- Link tới trang quản lý người dùng, dùng contextPath để không phụ thuộc cấu hình app -->
                <a class="nav-link text-dark" href="${pageContext.request.contextPath}/user-management">Quản lý người
                    dùng</a>
            </li>
            <li class="nav-item">
                <!-- Link tới trang quản lý video -->
                <a class="nav-link text-dark" href="${pageContext.request.contextPath}/admin/video">Quản lý video</a>
            </li>
            <li class="nav-item">
                <!-- Link tới trang thống kê -->
                <a class="nav-link text-dark" href="${pageContext.request.contextPath}/admin/report">Thống kê</a>
            </li>
        </ul>
        <span class="navbar-text text-dark">
            Xin chào, ${sessionScope.user.fullname} |
            <a href="${pageContext.request.contextPath}/logout" class="text-dark fw-bold">Đăng xuất</a>
        </span>
    </div>
</nav>

<div class="container mt-4">
    <h3 class="text-center text-uppercase">Thống kê</h3>
    <hr>

    <ul class="nav nav-tabs" id="reportTabs" role="tablist">
        <li class="nav-item" role="presentation">
            <button class="nav-link active" id="favorites-tab" data-bs-toggle="tab" data-bs-target="#favorites"
                    type="button" role="tab">
                Favorites
            </button>
        </li>
        <li class="nav-item" role="presentation">
            <button class="nav-link" id="favusers-tab" data-bs-toggle="tab" data-bs-target="#favusers" type="button"
                    role="tab">
                Favorite Users
            </button>
        </li>
        <li class="nav-item" role="presentation">
            <button class="nav-link" id="shared-tab" data-bs-toggle="tab" data-bs-target="#shared" type="button"
                    role="tab">
                Shared Friends
            </button>
        </li>
    </ul>

    <div class="tab-content mt-3" id="reportTabContent">
        <!-- Tab 1: Favorites -->
        <div class="tab-pane fade show active" id="favorites" role="tabpanel">
            <div class="table-wrap">
                <table class="table table-bordered table-striped table-hover">
                    <thead class="table-dark">
                    <tr>
                        <th>Video Title</th>
                        <th>Favorite Count</th>
                        <th>Latest Date</th>
                        <th>Oldest Date</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="r" items="${favoriteReports}">
                        <tr>
                            <td>${r.title}</td>
                            <td>${r.favoriteCount}</td>
                            <td>${r.latestDate}</td>
                            <td>${r.oldestDate}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>

        <!-- Tab 2: Favorite Users -->
        <div class="tab-pane fade" id="favusers" role="tabpanel">
            <form id="favUsersForm" class="row g-2 align-items-center" method="get"
                  action="${pageContext.request.contextPath}/admin/report/favorite-users">
                <div class="col-sm-6">
                    <input type="text" id="favUsersSearch" name="title" class="form-control"
                           placeholder="Nhập video title..." value="${param.title}">
                </div>
                <div class="col-sm-auto">
                    <button class="btn btn-primary" onclick="submitFavoriteUsersSearch(event)">Tìm kiếm</button>
                </div>
            </form>
            <div class="table-wrap mt-3">
                <table class="table table-bordered table-striped table-hover">
                    <thead class="table-dark">
                    <tr>
                        <th>Video Title</th>
                        <th>Username</th>
                        <th>Full name</th>
                        <th>Email</th>
                        <th>Favorite Date</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="u" items="${favoriteUsers}">
                        <tr>
                            <td>${u.videoTitle}</td>
                            <td>${u.username}</td>
                            <td>${u.fullname}</td>
                            <td>${u.email}</td>
                            <td>${u.favoriteDate}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>

        <!-- Tab 3: Shared Friends -->
        <div class="tab-pane fade" id="shared" role="tabpanel">
            <form id="sharedForm" class="row g-2 align-items-center" method="get"
                  action="${pageContext.request.contextPath}/admin/report/shared-friends">
                <div class="col-sm-6">
                    <input type="text" id="sharedSearch" name="title" class="form-control"
                           placeholder="Nhập video title..." value="${param.title}">
                </div>
                <div class="col-sm-auto">
                    <button class="btn btn-primary" onclick="submitSharedFriendsSearch(event)">Tìm kiếm</button>
                </div>
            </form>
            <div class="table-wrap mt-3">
                <table class="table table-bordered table-striped table-hover">
                    <thead class="table-dark">
                    <tr>
                        <th>Sender name</th>
                        <th>Sender email</th>
                        <th>Receiver email</th>
                        <th>Sent date</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="s" items="${sharedFriends}">
                        <tr>
                            <td>${s.senderName}</td>
                            <td>${s.senderEmail}</td>
                            <td>${s.receiverEmail}</td>
                            <td>${s.sentDate}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
