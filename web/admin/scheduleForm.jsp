<%-- 
    Document   : scheduleForm
    Created on : Jul 8, 2025, 12:01:22 AM
    Author     : Tung Nguyen /// Bản gốc
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="currentUser" value="${sessionScope.user}"/>
<c:set var="isLoggedIn" value="${not empty currentUser}"/>
<c:set var="isAdmin" value="${currentUser.role eq 'admin'}"/>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin - Quản lý lịch trình</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/styleUserInfo.css"/>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/styleScheduleForm.css"/>
    </head>
    <body>

        <%@include file="sideBar.jsp" %>

        <c:choose>
            <c:when test="${isLoggedIn and isAdmin}">
                <div class="main-content">
                    <div class="content-header">
                        <h1><i class="fas fa-tachometer-alt"></i>Quản lí lịch trình</h1>
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


                    <div class="main">

                        <!--================================= HIỂN THỊ LỊCH TRÌNH ====================================-->
                        <div class="show">

                            <c:choose>
                                <c:when test="${not empty scheduleDisplayList}">
                                    <div class="message">
                                        <c:if test="${not empty displayMessage}">
                                            <span class="message">Thông báo: ${displayMessage}</span>
                                        </c:if>
                                    </div>

                                    <h3>Bảng thông tin lịch trình</h3>
                                    <div class="table-container">
                                        <table border="1" cellspacing="0"> 
                                            <thead>
                                                <tr>
                                                    <td>ID Lịch Trình</td>
                                                    <td>ID Tuyến Đường</td>
                                                    <td>Phương Tiện</td>
                                                    <td><strong>Điểm Đi</strong></td>
                                                    <td>Thời Gian Xuất Bến</td>
                                                    <td><strong>Điểm Đến</strong></td>
                                                    <td>Thời Gian Cập Bến</td>
                                                    <td>Tài Xế</td>
                                                    <td>Tiếp Viên</td>
                                                    <td>Giá Vé</td>
                                                    <td>Trạng Thái</td>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach var="s" items="${requestScope.scheduleDisplayList}">
                                                    <tr>
                                                        <td>${s.scheduleID}</td>
                                                        <td>${s.routeID}</td>
                                                        <td><strong>${s.busNumber}</strong> - ${s.busName}</td>
                                                        <td><strong>${s.departure}</strong></td>
                                                        <td><fmt:formatDate value="${s.departureTime}" pattern="HH:mm | dd/MM/yyyy"/></td>
                                                        <td><strong>${s.destination}</strong></td>
                                                        <td><fmt:formatDate value="${s.arrivalTime}" pattern="HH:mm | dd/MM/yyyy"/></td>
                                                        <td>${s.driverName}</td>
                                                        <td>${s.attendantName}</td>
                                                        <td><fmt:formatNumber value="${s.price}" maxFractionDigits="0"/>đ</td>
                                                        <td>${s.status}</td>
                                                    </tr>
                                                </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <p>Không có phương tiện nào trong danh sách</p>
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <!--================================= THÊM LỊCH TRÌNH MỚI ====================================-->

                        <div class="add">
                            <h3>Thêm lịch trình mới</h3>
                            <div class="form-container">

                                <div class="message">
                                    <c:if test="${not empty addMsg}">
                                        ${addMsg}
                                    </c:if>
                                </div>

                                <form action="ScheduleController" method="post">
                                    <input type="hidden" name="action" value="addSchedule">

                                    <!-- Tuyến đường -->
                                    <div class="form-group">
                                        Chọn 1 tuyến đường:
                                        <select name="routeID">
                                            <c:forEach var="r" items="${avaiRouteList}">
                                                <option value="${r.route_id}">${r.departure} - ${r.destination}</option>
                                            </c:forEach>
                                        </select>
                                    </div>

                                    <!-- Phương tiện -->
                                    <div class="form-group">
                                        Chọn phương tiện phục vụ:
                                        <select name="busID">
                                            <c:forEach var="avb" items="${avaiBusList}">
                                                <option value="${avb.busID}">${avb.busNumber} - ${avb.busName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>

                                    <!-- Giờ xuất bến -->
                                    <div class="form-group">
                                        Nhập thời gian xuất bến:
                                        <input type="datetime-local" id="travelDate" name="departureTime" value="${departureTime}">
                                    </div>

                                    <!-- Giờ cập bến -->
                                    <div class="form-group">
                                        Nhập thời gian cập bến:
                                        <input type="datetime-local" id="travelDate" name="arrivalTime" value="${arrivalTime}">
                                    </div>

                                    <!-- Tài xế -->
                                    <div class="form-group">
                                        Lựa chọn tài xế:
                                        <select name="driverID">
                                            <c:forEach var="d" items="${driverList}">
                                                <option value="${d.emp_id}">${d.emp_name}</option>
                                            </c:forEach>
                                        </select>
                                    </div>

                                    <!-- Tiếp viên -->
                                    <div class="form-group">
                                        Lựa chọn tiếp viên:
                                        <select name="attendantID">
                                            <c:forEach var="d" items="${attendantList}">
                                                <option value="${d.emp_id}">${d.emp_name}</option>
                                            </c:forEach>
                                        </select>

                                    </div>

                                    <!-- Giá tiền -->
                                    <div class="form-group">
                                        Nhập giá cho 1 vé:
                                        <input type="number" name="price">
                                    </div>

                                    <!-- Button submit -->
                                    <div class="form-group">
                                        <button type="submit">Thêm lịch trình</button>
                                    </div>
                                </form>
                            </div>
                        </div>


                    </div>
                </div>
            </c:when>
            <c:otherwise>
                <c:redirect url="/Home.jsp"/>
            </c:otherwise>
        </c:choose>

        <script>
            // Thiết lập ngày tối thiểu cho input date là ngày hiện tại
            document.addEventListener('DOMContentLoaded', function () {
                const today = new Date();
                const year = today.getFullYear();
                let mm = today.getMonth() + 1; // Months start at 0!
                let dd = today.getDate();

                if (dd < 10)
                    dd = '0' + dd;
                if (mm < 10)
                    mm = '0' + mm;

                const formattedToday = year + '-' + mm + '-' + dd;
                document.getElementById('travelDate').setAttribute('min', formattedToday);

                // Nếu travelDate chưa có giá trị (tức là lần đầu load trang), thì set nó là ngày hiện tại
                // Điều này giúp người dùng không phải click chọn ngày nếu họ chỉ muốn xem chuyến trong ngày
                const travelDateInput = document.getElementById('travelDate');
                if (!travelDateInput.value) {
                    travelDateInput.value = formattedToday;
                }
            });
        </script>
    </body>
</html>
