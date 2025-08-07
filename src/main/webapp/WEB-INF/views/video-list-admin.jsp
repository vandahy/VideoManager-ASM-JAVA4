<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Video Manager</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-4">
    <h3 class="text-center text-uppercase">Quản lý video</h3>
    <hr>

    <!-- Tabs -->
    <ul class="nav nav-tabs" id="videoTabs" role="tablist">
        <li class="nav-item" role="presentation">
            <button class="nav-link active" id="edit-tab" data-bs-toggle="tab" data-bs-target="#edit" type="button"
                    role="tab">Video Edition
            </button>
        </li>
        <li class="nav-item" role="presentation">
            <button class="nav-link" id="list-tab" data-bs-toggle="tab" data-bs-target="#list" type="button" role="tab">
                Video List
            </button>
        </li>
    </ul>

    <!-- Tab contents -->
    <div class="tab-content" id="videoTabContent">
        <!-- Form tab -->
        <div class="tab-pane fade show active" id="edit" role="tabpanel">
            <div class="row mt-3">
                <!-- Poster -->
                <div class="col-md-4 text-center">
                    <div class="border bg-secondary-subtle mb-3" style="height: 180px;">
                        <img src="${form.poster}" class="img-fluid h-100" alt="Poster">
                    </div>
                </div>

                <!-- Form -->
                <div class="col-md-8">
                    <form method="post">
                        <div class="mb-3">
                            <label for="id" class="form-label">ID</label>
                            <input type="text" name="id" id="id" class="form-control" value="${form.id}"
                                   <c:if test="${form.id != null}">readonly</c:if>>

                        </div>

                        <div class="mb-3">
                            <label for="title" class="form-label">Title</label>
                            <input type="text" name="title" id="title" class="form-control" value="${form.title}">
                        </div>

                        <div class="mb-3">
                            <label for="links" class="form-label">YouTube Link</label>
                            <input type="text" name="links" id="links" class="form-control" value="${form.links}">
                        </div>

                        <div class="mb-3">
                            <label for="views" class="form-label">Views</label>
                            <input type="number" name="views" id="views" class="form-control" value="${form.views}">
                        </div>

                        <div class="mb-3">
                            <label for="poster" class="form-label">Poster URL</label>
                            <input type="text" name="poster" id="poster" class="form-control" value="${form.poster}">
                        </div>

                        <div class="mb-3">
                            <label for="description" class="form-label">Description</label>
                            <textarea name="description" id="description"
                                      class="form-control">${form.description}</textarea>
                        </div>

                        <div class="form-check mb-3">
                            <input class="form-check-input" type="checkbox" name="active"
                                   id="active" ${form.active ? "checked" : ""}>
                            <label class="form-check-label" for="active">Active</label>
                        </div>

                        <!--  Nút submit dùng formaction -->
                        <div class="mb-3">
                            <button class="btn btn-primary"
                                    formaction="${pageContext.request.contextPath}/admin/video/create">Create
                            </button>

                            <button class="btn btn-success"
                                    formaction="${pageContext.request.contextPath}/admin/video/update/${form.id}">Update
                            </button>

                            <a href="${pageContext.request.contextPath}/admin/video/delete/${form.id}"
                               class="btn btn-danger">Delete</a>
                            <a href="${pageContext.request.contextPath}/admin/video/reset"
                               class="btn btn-warning">Reset</a>
                        </div>
                    </form>
                </div>

            </div>
        </div>

        <!-- Video List tab -->
        <div class="tab-pane fade" id="list" role="tabpanel">
            <table class="table table-bordered table-striped table-hover mt-3">
                <thead class="table-dark">
                <tr>
                    <th>ID</th>
                    <th>Title</th>
                    <th>Link</th>
                    <th>Poster</th>
                    <th>Views</th>
                    <th>Description</th>
                    <th>Active</th>
                    <th>Action</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="video" items="${videos}">
                    <tr>
                        <td>${video.id}</td>
                        <td>${video.title}</td>
                        <td>${video.links}</td>
                        <td><img src="${video.poster}" style="height: 50px;" alt="Poster"></td>
                        <td>${video.views}</td>
                        <td>${video.description}</td>
                        <td>${video.active ? "Yes" : "No"}</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/admin/video/edit/${video.id}"
                               class="btn btn-sm btn-warning">Edit</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
