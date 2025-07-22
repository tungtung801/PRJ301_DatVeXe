<%-- 
    Document   : staffForm
    Created on : Jul 5, 2025, 10:59:46 AM
    Author     : Tung Nguyen
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="currentUser" value="${sessionScope.user}"/>
<c:set var="eList" value="${requestScope.eList}"/>
<c:set var="message" value="${requestScope.message}"/>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin - Quản lý nhân viên</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/styleStaffForm.css"/>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/styleUserInfo.css"/>
    </head>
    <body>
        <c:choose>
            <c:when test="${not empty sessionScope.user and sessionScope.user.role eq 'admin'}">

                <%@include file="sideBar.jsp" %>
                <div class="main-content">
                    <div class="content-header">
                        <h1><i class="fas fa-tachometer-alt"></i>Quản lí nhân sự</h1>
                        <div class="user-info">
                            <div class="user-avatar">A</div>
                            <div>
                                <div style="font-weight: bold;">${currentUser.fullname}</div>
                                <div style="font-size: 0.9rem; color: #666;">${currentUser.role}</div>
                            </div>
                            <div class="logout">
                                <a href="${pageContext.request.contextPath}/MainController?action=logout">Đăng xuất</a>
                            </div>
                        </div>
                    </div> 


                    <div class="content">
                        <div class="message">
                            <c:if test="${not empty message}">
                                <div class="message success">${message}</div>
                            </c:if>

                            <c:if test="${not empty inputError}">
                                <div class="message error">${inputError}</div>
                            </c:if>

                        </div>

                        <!--================================= HIỂN THỊ NHÂN SỰ ====================================-->

                        <div class="show-box">
                            <!-- BẢNG TÀI XẾ -->
                            <div class="show show-half">
                                <h3>Bảng Tài Xế</h3>
                                <table>
                                    <thead>
                                        <tr>
                                            <td>ID Nhân Viên</td>
                                            <td>Tên Nhân Viên</td>
                                            <td>Trạng Thái</td>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="e" items="${eList}">
                                            <c:if test="${e.emp_role eq 'Tài xế'}">
                                                <tr>
                                                    <td>${e.emp_id}</td>
                                                    <td>${e.emp_name}</td>
                                                    <td>${e.status}</td>
                                                </tr>
                                            </c:if>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>

                            <!-- BẢNG TIẾP VIÊN -->
                            <div class="show show-half">
                                <h3>Bảng Tiếp Viên</h3>
                                <table>
                                    <thead>
                                        <tr>
                                            <td>ID Nhân Viên</td>
                                            <td>Tên Nhân Viên</td>
                                            <td>Trạng Thái</td>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="e" items="${eList}">
                                            <c:if test="${e.emp_role eq 'Tiếp viên'}">
                                                <tr>
                                                    <td>${e.emp_id}</td>
                                                    <td>${e.emp_name}</td>
                                                    <td>${e.status}</td>
                                                </tr>
                                            </c:if>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>


                        <!--================================= TUYỂN DỤNG NHÂN SỰ ====================================-->

                        <div class="add">
                            <h3>Tuyển dụng nhân viên</h3>
                            <form action="StaffController" method="post">
                                <input type="hidden" name="action" value="addStaff">
                                <input type="hidden" name="status" value="Nhàn rỗi">

                                <div class="form-group">
                                    <label>Vị trí cần tuyển dụng:</label>
                                    <select name="emp_role" required>
                                        <option value="Tài xế">1. Tài Xế</option>
                                        <option value="Tiếp viên">2. Tiếp viên</option>
                                    </select>
                                </div>

                                <div class="form-group">
                                    <label>Tên nhân viên:</label>
                                    <input type="text" name="emp_name" required>
                                </div>

                                <c:if test="${not empty inputError}">
                                    <div class="message">${inputError}</div>
                                </c:if>

                                <c:if test="${not empty addMsg}">
                                    <div class="message">${addMsg}</div>
                                </c:if>

                                <div class="form-group">
                                    <input type="submit" value="Tuyển dụng">
                                </div>
                            </form>
                        </div>
                    </div>
                </div>

            </c:when>
            <c:otherwise>
                <c:redirect url="/Home.jsp"/>
            </c:otherwise>
        </c:choose>        
    </body>
</html>
