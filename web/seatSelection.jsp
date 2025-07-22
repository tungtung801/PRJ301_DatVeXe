<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<c:set var="currentUser" value="${sessionScope.user}"/>
<c:set var="isLoggedIn" value="${not empty currentUser}"/>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Tùng Long Bus Lines - Chọn chỗ</title>
        <link rel="stylesheet" type="text/css" href="assets/css/styleSeatSelection.css"/>
        <style>
            .seat-map-container {
                display: flex;
                flex-direction: column;
                gap: 30px;
            }

            .seat-map-column {
                margin-bottom: 20px;
            }

            .seat-row {
                display: flex;
                justify-content: center;
                gap: 20px;
                margin-bottom: 10px;
            }

            .seat {
                width: 50px;
                height: 50px;
                display: flex;
                align-items: center;
                justify-content: center;
                border: 1px solid #ccc;
                border-radius: 5px;
                cursor: pointer;
                font-weight: bold;
            }

            .available {
                background-color: #d4edda;
                cursor: pointer;
            }

            .booked {
                background-color: #f8d7da;
                cursor: not-allowed;
            }

            .selected {
                background-color: #cce5ff;
            }
        </style>
        <script>
  window.IS_LOGGED_IN = ${isLoggedIn};
  window.APP_CONTEXT_PATH = '${pageContext.request.contextPath}';
</script>
    </head>
    <body>

        <%@include file="header.jsp" %>

        <div class="main-content">
            <div class="main-left">
                <a href="${pageContext.request.contextPath}/ScheduleController" class="back-button">
                    <i class="fas fa-arrow-left"></i> Quay lại
                </a>
                <h2>Chuyến đi từ <span style="color: red">${departure}</span> đến <span style="color: red">${destination}</span></h2>
                <p><strong>Thời gian xuất bến: </strong><fmt:formatDate value="${departureTime}" pattern="HH:mm | dd/MM/yyyy"/></p>
                <p><strong>Thời gian cập bến (dự kiến): </strong><fmt:formatDate value="${arrivalTime}" pattern="HH:mm | dd/MM/yyyy"/></p>
                <p><strong>Loại xe: </strong>${busTypeName}</p>
                <p><strong>Tên xe: </strong>${busName}</p>
                <p><strong>Biển kiểm soát: </strong>${busNumber}</p>
                <p><strong>Còn trống: </strong>${availableSeats}<c:if test="${not empty totalSeats}">/${totalSeats}</c:if></p>
                <p><strong>Giá vé: </strong><span style="color: red; font-weight: bold;"><fmt:formatNumber value="${price}" maxFractionDigits="0"/>đ</span></p>
            </div>

            <div class="main-right">
                <form action="BookingController" method="post">
                    <input type="hidden" name="action" value="createBooking" />
                    <input type="hidden" name="scheduleId" value="${scheduleID}" />
                    <input type="hidden" name="price" id="hiddenPricePerSeat" value="${price}" />
                    <input type="hidden" name="selectedSeats" id="selectedSeatsInput" value="" />

                    <div class="seat-map-container">
                        <!-- ========== Tầng dưới ========== -->
                        <div class="seat-map-column">
                            <h2>Tầng dưới</h2>
                            <div class="seat-map">
                                <!-- Hàng ghế A01-A02 -->
                                <div class="seat-row">
                                    <c:forEach var="seat" items="${seatList}">
                                        <c:if test="${seat.seatNumber == 'A01' || seat.seatNumber == 'A02'}">
                                            <div class="seat ${seat.status == 'Đã đặt' ? 'booked' : 'available'}"
                                                 data-seat-id="${seat.scheduleSeatID}"
                                                 data-seat-number="${seat.seatNumber}">
                                                <c:if test="${seat.status == 'Trống'}">
                                                    <input type="checkbox" name="selectedSeats" value="${seat.scheduleSeatID}" data-seat-number="${seat.seatNumber}" hidden />
                                                    <span>${seat.seatNumber}</span>
                                                </c:if>
                                                <c:if test="${seat.status == 'Đã đặt'}">
                                                    <span>${seat.seatNumber}</span>
                                                </c:if>
                                            </div>
                                        </c:if>
                                    </c:forEach>
                                </div>

                                <!-- Hàng ghế A03-A04 -->
                                <div class="seat-row">
                                    <c:forEach var="seat" items="${seatList}">
                                        <c:if test="${seat.seatNumber == 'A03' || seat.seatNumber == 'A04'}">
                                            <div class="seat ${seat.status == 'Đã đặt' ? 'booked' : 'available'}"
                                                 data-seat-id="${seat.scheduleSeatID}"
                                                 data-seat-number="${seat.seatNumber}">
                                                <c:if test="${seat.status == 'Trống'}">
                                                    <input type="checkbox" name="selectedSeats" value="${seat.scheduleSeatID}" data-seat-number="${seat.seatNumber}" hidden />
                                                    <span>${seat.seatNumber}</span>
                                                </c:if>
                                                <c:if test="${seat.status == 'Đã đặt'}">
                                                    <span>${seat.seatNumber}</span>
                                                </c:if>
                                            </div>
                                        </c:if>
                                    </c:forEach>
                                </div>

                                <!-- Hàng ghế A05-A06 -->
                                <div class="seat-row">
                                    <c:forEach var="seat" items="${seatList}">
                                        <c:if test="${seat.seatNumber == 'A05' || seat.seatNumber == 'A06'}">
                                            <div class="seat ${seat.status == 'Đã đặt' ? 'booked' : 'available'}"
                                                 data-seat-id="${seat.scheduleSeatID}"
                                                 data-seat-number="${seat.seatNumber}">
                                                <c:if test="${seat.status == 'Trống'}">
                                                    <input type="checkbox" name="selectedSeats" value="${seat.scheduleSeatID}" data-seat-number="${seat.seatNumber}" hidden />
                                                    <span>${seat.seatNumber}</span>
                                                </c:if>
                                                <c:if test="${seat.status == 'Đã đặt'}">
                                                    <span>${seat.seatNumber}</span>
                                                </c:if>
                                            </div>
                                        </c:if>
                                    </c:forEach>
                                </div>

                                <!-- Hàng ghế A07-A08 -->
                                <div class="seat-row">
                                    <c:forEach var="seat" items="${seatList}">
                                        <c:if test="${seat.seatNumber == 'A07' || seat.seatNumber == 'A08'}">
                                            <div class="seat ${seat.status == 'Đã đặt' ? 'booked' : 'available'}"
                                                 data-seat-id="${seat.scheduleSeatID}"
                                                 data-seat-number="${seat.seatNumber}">
                                                <c:if test="${seat.status == 'Trống'}">
                                                    <input type="checkbox" name="selectedSeats" value="${seat.scheduleSeatID}" data-seat-number="${seat.seatNumber}" hidden />
                                                    <span>${seat.seatNumber}</span>
                                                </c:if>
                                                <c:if test="${seat.status == 'Đã đặt'}">
                                                    <span>${seat.seatNumber}</span>
                                                </c:if>
                                            </div>
                                        </c:if>
                                    </c:forEach>
                                </div>

                                <!-- Hàng ghế A09-A10 -->
                                <div class="seat-row">
                                    <c:forEach var="seat" items="${seatList}">
                                        <c:if test="${seat.seatNumber == 'A09' || seat.seatNumber == 'A10'}">
                                            <div class="seat ${seat.status == 'Đã đặt' ? 'booked' : 'available'}"
                                                 data-seat-id="${seat.scheduleSeatID}"
                                                 data-seat-number="${seat.seatNumber}">
                                                <c:if test="${seat.status == 'Trống'}">
                                                    <input type="checkbox" name="selectedSeats" value="${seat.scheduleSeatID}" data-seat-number="${seat.seatNumber}" hidden />
                                                    <span>${seat.seatNumber}</span>
                                                </c:if>
                                                <c:if test="${seat.status == 'Đã đặt'}">
                                                    <span>${seat.seatNumber}</span>
                                                </c:if>
                                            </div>
                                        </c:if>
                                    </c:forEach>
                                </div>

                                <!-- Thêm hàng cho ghế A11-A12 (chỉ hiển thị nếu có) -->
                                <c:if test="${fn:length(seatList) > 20}">
                                    <div class="seat-row">
                                        <c:forEach var="seat" items="${seatList}">
                                            <c:if test="${seat.seatNumber == 'A11' || seat.seatNumber == 'A12'}">
                                                <div class="seat ${seat.status == 'Đã đặt' ? 'booked' : 'available'}"
                                                     data-seat-id="${seat.scheduleSeatID}"
                                                     data-seat-number="${seat.seatNumber}">
                                                    <c:if test="${seat.status == 'Trống'}">
                                                        <input type="checkbox" name="selectedSeats" value="${seat.scheduleSeatID}" data-seat-number="${seat.seatNumber}" hidden />
                                                        <span>${seat.seatNumber}</span>
                                                    </c:if>
                                                    <c:if test="${seat.status == 'Đã đặt'}">
                                                        <span>${seat.seatNumber}</span>
                                                    </c:if>
                                                </div>
                                            </c:if>
                                        </c:forEach>
                                    </div>
                                </c:if>
                            </div>
                        </div>

                        <!-- ========== Tầng trên ========== -->
                        <div class="seat-map-column">
                            <h2>Tầng trên</h2>
                            <div class="seat-map">
                                <!-- Hàng ghế B01-B02 -->
                                <div class="seat-row">
                                    <c:forEach var="seat" items="${seatList}">
                                        <c:if test="${seat.seatNumber == 'B01' || seat.seatNumber == 'B02'}">
                                            <div class="seat ${seat.status == 'Đã đặt' ? 'booked' : 'available'}"
                                                 data-seat-id="${seat.scheduleSeatID}"
                                                 data-seat-number="${seat.seatNumber}">
                                                <c:if test="${seat.status == 'Trống'}">
                                                    <input type="checkbox" name="selectedSeats" value="${seat.scheduleSeatID}" data-seat-number="${seat.seatNumber}" hidden />
                                                    <span>${seat.seatNumber}</span>
                                                </c:if>
                                                <c:if test="${seat.status == 'Đã đặt'}">
                                                    <span>${seat.seatNumber}</span>
                                                </c:if>
                                            </div>
                                        </c:if>
                                    </c:forEach>
                                </div>

                                <!-- Hàng ghế B03-B04 -->
                                <div class="seat-row">
                                    <c:forEach var="seat" items="${seatList}">
                                        <c:if test="${seat.seatNumber == 'B03' || seat.seatNumber == 'B04'}">
                                            <div class="seat ${seat.status == 'Đã đặt' ? 'booked' : 'available'}"
                                                 data-seat-id="${seat.scheduleSeatID}"
                                                 data-seat-number="${seat.seatNumber}">
                                                <c:if test="${seat.status == 'Trống'}">
                                                    <input type="checkbox" name="selectedSeats" value="${seat.scheduleSeatID}" data-seat-number="${seat.seatNumber}" hidden />
                                                    <span>${seat.seatNumber}</span>
                                                </c:if>
                                                <c:if test="${seat.status == 'Đã đặt'}">
                                                    <span>${seat.seatNumber}</span>
                                                </c:if>
                                            </div>
                                        </c:if>
                                    </c:forEach>
                                </div>

                                <!-- Hàng ghế B05-B06 -->
                                <div class="seat-row">
                                    <c:forEach var="seat" items="${seatList}">
                                        <c:if test="${seat.seatNumber == 'B05' || seat.seatNumber == 'B06'}">
                                            <div class="seat ${seat.status == 'Đã đặt' ? 'booked' : 'available'}"
                                                 data-seat-id="${seat.scheduleSeatID}"
                                                 data-seat-number="${seat.seatNumber}">
                                                <c:if test="${seat.status == 'Trống'}">
                                                    <input type="checkbox" name="selectedSeats" value="${seat.scheduleSeatID}" data-seat-number="${seat.seatNumber}" hidden />
                                                    <span>${seat.seatNumber}</span>
                                                </c:if>
                                                <c:if test="${seat.status == 'Đã đặt'}">
                                                    <span>${seat.seatNumber}</span>
                                                </c:if>
                                            </div>
                                        </c:if>
                                    </c:forEach>
                                </div>

                                <!-- Hàng ghế B07-B08 -->
                                <div class="seat-row">
                                    <c:forEach var="seat" items="${seatList}">
                                        <c:if test="${seat.seatNumber == 'B07' || seat.seatNumber == 'B08'}">
                                            <div class="seat ${seat.status == 'Đã đặt' ? 'booked' : 'available'}"
                                                 data-seat-id="${seat.scheduleSeatID}"
                                                 data-seat-number="${seat.seatNumber}">
                                                <c:if test="${seat.status == 'Trống'}">
                                                    <input type="checkbox" name="selectedSeats" value="${seat.scheduleSeatID}" data-seat-number="${seat.seatNumber}" hidden />
                                                    <span>${seat.seatNumber}</span>
                                                </c:if>
                                                <c:if test="${seat.status == 'Đã đặt'}">
                                                    <span>${seat.seatNumber}</span>
                                                </c:if>
                                            </div>
                                        </c:if>
                                    </c:forEach>
                                </div>

                                <!-- Hàng ghế B09-B10 -->
                                <div class="seat-row">
                                    <c:forEach var="seat" items="${seatList}">
                                        <c:if test="${seat.seatNumber == 'B09' || seat.seatNumber == 'B10'}">
                                            <div class="seat ${seat.status == 'Đã đặt' ? 'booked' : 'available'}"
                                                 data-seat-id="${seat.scheduleSeatID}"
                                                 data-seat-number="${seat.seatNumber}">
                                                <c:if test="${seat.status == 'Trống'}">
                                                    <input type="checkbox" name="selectedSeats" value="${seat.scheduleSeatID}" data-seat-number="${seat.seatNumber}" hidden />
                                                    <span>${seat.seatNumber}</span>
                                                </c:if>
                                                <c:if test="${seat.status == 'Đã đặt'}">
                                                    <span>${seat.seatNumber}</span>
                                                </c:if>
                                            </div>
                                        </c:if>
                                    </c:forEach>
                                </div>

                                <!-- Hàng ghế B11-B12 -->
                                <div class="seat-row">
                                    <c:forEach var="seat" items="${seatList}">
                                        <c:if test="${seat.seatNumber == 'B11' || seat.seatNumber == 'B12'}">
                                            <div class="seat ${seat.status == 'Đã đặt' ? 'booked' : 'available'}"
                                                 data-seat-id="${seat.scheduleSeatID}"
                                                 data-seat-number="${seat.seatNumber}">
                                                <c:if test="${seat.status == 'Trống'}">
                                                    <input type="checkbox" name="selectedSeats" value="${seat.scheduleSeatID}" data-seat-number="${seat.seatNumber}" hidden />
                                                    <span>${seat.seatNumber}</span>
                                                </c:if>
                                                <c:if test="${seat.status == 'Đã đặt'}">
                                                    <span>${seat.seatNumber}</span>
                                                </c:if>
                                            </div>
                                        </c:if>
                                    </c:forEach>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="seat-legend">
                        <span><div class="box available"></div> Trống</span>
                        <span><div class="box booked"></div> Đã đặt</span>
                        <span><div class="box selected"></div> Đang chọn</span>
                    </div>

                    <div class="booking-summary">
                        <h3>Thông tin đặt vé</h3>
                        <div id="selectedSeatsInfo"></div>
                        <div id="totalPrice"></div>
                        <c:choose>
                            <c:when test="${isLoggedIn}">
                                <button type="submit" id="bookingBtn" style="display: none;">Tiếp tục đặt vé</button>
                            </c:when>
                            <c:otherwise>
                                <button type="button" id="bookingBtn" onclick="return confirm('Bạn cần đăng nhập để tiếp tục đặt vé')" style="display: none;">Tiếp tục đặt vé</button>
                            </c:otherwise>
                        </c:choose>

                    </div>
                </form>
            </div>
        </div>

        <script src="assets/js/seatSelectionJS.js"></script>
        <%@include file="footer.jsp" %>

    </body>
</html>