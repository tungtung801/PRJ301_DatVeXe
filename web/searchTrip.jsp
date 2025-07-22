<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 
<c:set var="searchList" value="${requestScope.searchList}"/>

<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Tùng Long Bus Lines - Lịch Trình</title>
        <link rel="stylesheet" href="assets/css/styleSearchTrip.css"/>
    </head>
    <body>
        <div class="main-content">

            <%@include file="header.jsp" %>


            <div class="main">
                <div class="form-container">
                    <h2>Tìm kiếm Chuyến đi</h2>

                    <c:if test="${not empty generalMessage}">
                        <p class="error-message">${generalMessage}</p>
                    </c:if>

                    <form action="ScheduleController" method="get">
                        <input type="hidden" name="action" value="searchSchedule">

                        <div class="form-group">
                            <label for="departure">Điểm đi:</label>
                            <input type="text" id="departure" name="Departure" list="locations" placeholder="Chọn điểm đi" value="${Departure}">
                            <datalist id="locations"> 
                                <c:forEach var="l" items="${requestScope.lList}">
                                    <option value="${l.name}"></option>
                                </c:forEach>
                            </datalist>
                        </div>

                        <div class="form-group">
                            <label for="destination">Điểm đến:</label>
                            <input type="text" id="destination" name="Destination" list="locations" placeholder="Chọn điểm đến" value="${Destination}">
                        </div>

                        <c:if test="${not empty errors.input_error}">
                            <span class="message">${errors.input_error}</span>
                        </c:if>


                        <div class="form-group">
                            <label for="travelDate">Ngày đi:</label>
                            <input type="date" id="travelDate" name="travelDate" required value="${travelDate}">
                        </div>

                        <c:if test="${not empty errors.date_error}">
                            <span class="message">${errors.date_error}</span>
                        </c:if>

                        <input type="submit" value="Tìm chuyến xe">
                    </form>
                </div>


                <form action="ScheduleController" method="post">
                    <input type="hidden" name="action" value="displaySchedule" onload="this.form.submit()">
                </form>

                <div class="show">

                    <c:choose>
                        <c:when test="${isSearching and empty generalMessage}">
                            <h2>Kết Quả Tìm Kiếm Chuyến Xe</h2>
                            <c:if test="${empty searchList}">
                                <p class="info-message">Không tìm thấy chuyến xe nào phù hợp với yêu cầu của bạn.</p>
                            </c:if>
                        </c:when>
                        <c:otherwise>
                            <h2>Các Chuyến Xe Sắp Khởi Hành</h2>
                            <c:if test="${empty searchList}">
                                <p class="info-message">Hiện tại không có chuyến xe nào khả dụng. Vui lòng thử lại sau.</p>
                            </c:if>
                        </c:otherwise>
                    </c:choose>

                    <div class="ticket-list">
                        <c:forEach var="trip" items="${searchList}">
                            <div class="ticket-card">
                                <h3>${trip.departure} <img style="margin: 2px 6px;" width="10px" src="assets/images/right-arrow.png"> ${trip.destination}</h3>
                                <p><strong>Ngày đi:</strong> <fmt:formatDate value="${trip.departureTime}" pattern="dd/MM/yyyy"/></p>
                                <p><strong>Thời xuất bến:</strong> <fmt:formatDate value="${trip.departureTime}" pattern="HH:mm"/></p><!--
                                <p><strong>Thời gian cập bến (dự kiến):</strong> <fmt:formatDate value="${trip.arrivalTime}" pattern="HH:mm | dd/MM/yyyy"/></p>
                                <p><strong>Loại xe:</strong> ${trip.busTypeName}</p>-->
                                <p><strong>Giá vé:</strong> <fmt:formatNumber value="${trip.price}" maxFractionDigits="0"/>đ</p>
                                <p><strong>Ghế trống:</strong> ${trip.availableSeats}<c:if test="${not empty trip.totalSeats}">/${trip.totalSeats}</c:if></p>
                                <a href="${pageContext.request.contextPath}/ScheduleSeatController?action=viewScheduleDetail&scheduleId=${trip.scheduleID}" class="select-btn">Chọn chỗ</a>
                            </div>
                        </c:forEach>
                    </div>

                </div>
            </div>

            <%@include file="footer.jsp" %>

        </div>

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