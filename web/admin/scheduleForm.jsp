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
                                                    <c:forEach var="s" items="${requestScope.scheduleDisplayList}">
                                                        <c:if test="${s.status eq 'Sự cố'}">
                                                            <td>Hành đông</td>
                                                        </c:if>
                                                    </c:forEach>
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
                                                        <td>
                                                            <c:choose>
                                                                <c:when test="${s.status eq 'Sự cố'}">
                                                                    <p style="color: red; font-weight: bold">${s.status}</p> 
                                                                </c:when>
                                                                <c:otherwise>
                                                                    ${s.status}
                                                                </c:otherwise>
                                                            </c:choose>  
                                                        </td>
                                                        <c:if test="${s.status eq 'Sự cố'}">
                                                            <td>
                                                                <form action="ScheduleController" method="post" style="display:inline;">
                                                                    <input type="hidden" name="action" value="prepareUpdateSchedule">
                                                                    <input type="hidden" name="scheduleID" value="${s.scheduleID}">
                                                                    <!-- Không cần gửi oldBusNumber nữa vì chúng ta sẽ lấy từ database -->
                                                                    <button type="submit" class="btn-update">Cập nhật/Điều động</button>
                                                                </form>
                                                            </td>
                                                        </c:if>


                                                    </tr>
                                                </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <p>Không có lịch trình nào trong danh sách !</p>
                                </c:otherwise>
                            </c:choose>
                        </div>


                        <!--================================= THÊM/CẬP NHẬT LỊCH TRÌNH ====================================-->
                        <div class="add">
                            <h3>${isEdit ? 'Cập nhật lịch trình' : 'Thêm lịch trình mới'}</h3>
                            <div class="form-container">

                                <div class="message">
                                    <c:if test="${not empty addMsg and not isEdit}">
                                        ${addMsg}
                                    </c:if>
                                    <c:if test="${not empty displayMessage and isEdit}">
                                        ${displayMessage}
                                    </c:if>
                                </div>

                                <form action="ScheduleController" method="post">
                                    <input type="hidden" name="action" value="${isEdit ? 'updateSchedule' : 'addSchedule'}">
                                    <c:if test="${isEdit}">
                                        <input type="hidden" name="scheduleID" value="${scheduleToEdit.scheduleID}">
                                        <input type="hidden" name="oldBusID" value="${scheduleToEdit.busID}">
                                    </c:if>

                                    <!-- Tuyến đường - readonly khi edit -->
                                    <div class="form-group">
                                        Tuyến đường:
                                        <c:choose>
                                            <c:when test="${isEdit}">
                                                <c:forEach var="r" items="${avaiRouteList}">
                                                    <c:if test="${r.route_id == scheduleToEdit.routeID}">
                                                        <input type="text" value="${r.departure} - ${r.destination}" readonly>
                                                        <input type="hidden" name="routeID" value="${scheduleToEdit.routeID}">
                                                    </c:if>
                                                </c:forEach>
                                            </c:when>
                                            <c:otherwise>
                                                <select name="routeID">
                                                    <c:forEach var="r" items="${avaiRouteList}">
                                                        <option value="${r.route_id}">${r.departure} - ${r.destination}</option>
                                                    </c:forEach>
                                                </select>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>

                                    <!-- Phương tiện - có thể thay đổi khi edit -->
                                    <div class="form-group">
                                        <c:choose>
                                            <c:when test="${isEdit}">
                                                Chọn phương tiện thay thế:
                                            </c:when>
                                            <c:otherwise>
                                                Chọn phương tiện phục vụ:
                                            </c:otherwise>
                                        </c:choose>
                                        <select name="busID">
                                            <c:forEach var="avb" items="${avaiBusList}">
                                                <option value="${avb.busID}" ${isEdit && avb.busID == scheduleToEdit.busID ? 'selected' : ''}>
                                                    ${avb.busNumber} - ${avb.busName}
                                                </option>
                                            </c:forEach>
                                        </select>
                                    </div>

                                    <!-- Giờ xuất bến - readonly khi edit -->
                                    <div class="form-group">
                                        Thời gian xuất bến:
                                        <c:choose>
                                            <c:when test="${isEdit}">
                                                <fmt:formatDate value="${scheduleToEdit.departureTime}" pattern="yyyy-MM-dd'T'HH:mm" var="formattedDepartureTime"/>
                                                <input type="datetime-local" name="departureTime" value="${formattedDepartureTime}" readonly>
                                            </c:when>
                                            <c:otherwise>
                                                <input type="datetime-local" id="travelDate" name="departureTime" value="${departureTime}">
                                            </c:otherwise>
                                        </c:choose>
                                    </div>

                                    <!-- Giờ cập bến - có thể thay đổi khi edit -->
                                    <div class="form-group">
                                        Thời gian cập bến:
                                        <c:choose>
                                            <c:when test="${isEdit}">
                                                <fmt:formatDate value="${scheduleToEdit.arrivalTime}" pattern="yyyy-MM-dd'T'HH:mm" var="formattedArrivalTime"/>
                                                <input type="datetime-local" name="arrivalTime" value="${formattedArrivalTime}">
                                            </c:when>
                                            <c:otherwise>
                                                <input type="datetime-local" id="arrivalDate" name="arrivalTime" value="${arrivalTime}">
                                            </c:otherwise>
                                        </c:choose>
                                    </div>

                                    <!-- Tài xế - readonly khi edit -->
                                    <div class="form-group">
                                        Tài xế:
                                        <c:choose>
                                            <c:when test="${isEdit}">
                                                <c:forEach var="d" items="${driverList}">
                                                    <c:if test="${d.emp_id == scheduleToEdit.driverID}">
                                                        <input type="text" value="${d.emp_name}" readonly>
                                                        <input type="hidden" name="driverID" value="${scheduleToEdit.driverID}">
                                                    </c:if>
                                                </c:forEach>
                                            </c:when>
                                            <c:otherwise>
                                                <select name="driverID">
                                                    <c:forEach var="d" items="${driverList}">
                                                        <option value="${d.emp_id}">${d.emp_name}</option>
                                                    </c:forEach>
                                                </select>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>

                                    <!-- Tiếp viên - readonly khi edit -->
                                    <div class="form-group">
                                        Tiếp viên:
                                        <c:choose>
                                            <c:when test="${isEdit}">
                                                <c:forEach var="d" items="${attendantList}">
                                                    <c:if test="${d.emp_id == scheduleToEdit.attendantID}">
                                                        <input type="text" value="${d.emp_name}" readonly>
                                                        <input type="hidden" name="attendantID" value="${scheduleToEdit.attendantID}">
                                                    </c:if>
                                                </c:forEach>
                                            </c:when>
                                            <c:otherwise>
                                                <select name="attendantID">
                                                    <c:forEach var="d" items="${attendantList}">
                                                        <option value="${d.emp_id}">${d.emp_name}</option>
                                                    </c:forEach>
                                                </select>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>

                                    <!-- Giá tiền - readonly khi edit -->
                                    <div class="form-group">
                                        Giá vé:
                                        <c:choose>
                                            <c:when test="${isEdit}">
                                                <input type="number" name="price" value="${scheduleToEdit.price}" readonly>
                                            </c:when>
                                            <c:otherwise>
                                                <input type="number" name="price">
                                            </c:otherwise>
                                        </c:choose>
                                    </div>

                                    <!-- Trạng thái (chỉ hiển thị khi cập nhật) -->
                                    <c:if test="${isEdit}">
                                        <div class="form-group">
                                            Trạng thái:
                                            <select name="status">
                                                <option value="Sự cố" ${scheduleToEdit.status eq 'Sự cố' ? 'selected' : ''}>Sự cố</option>
                                                <option value="Đang phục vụ" ${scheduleToEdit.status eq 'Đang phục vụ' ? 'selected' : ''}>Đang phục vụ</option>
                                                <option value="Lên lịch" ${scheduleToEdit.status eq 'Lên lịch' ? 'selected' : ''}>Lên lịch</option>
                                            </select>
                                        </div>
                                    </c:if>

                                    <!-- Button submit -->
                                    <div class="form-group">
                                        <button type="submit">${isEdit ? 'Cập nhật lịch trình' : 'Thêm lịch trình'}</button>
                                        <c:if test="${isEdit}">
                                            <a href="ScheduleController?action=scheduleManage" class="btn-cancel" style="margin-left: 10px; padding: 8px 16px; text-decoration: none; background-color: #f44336; color: white; border-radius: 4px;">Hủy</a>
                                        </c:if>
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
