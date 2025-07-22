<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Xác nhận đặt vé</title>
        <link rel="stylesheet" href="assets/css/styleBookingConfirm.css"/>
    </head>
    <body>
        <%@include file="header.jsp" %>

        <c:choose>
            <c:when test="${not empty sessionScope.user}">
                <h1>Xác nhận đặt vé</h1>
                <div class="main-content">
                    <div class="user">
                        <h3>Thông tin khách hàng:</h3>
                        <p><strong>ID khách hàng:</strong> ${sessionScope.user.user_id}</p>
                        <p><strong>Tên khách hàng:</strong> ${sessionScope.user.fullname}</p>
                        <p><strong>Số điện thoại:</strong> ${sessionScope.user.phone}</p>

                        <span class="text" style="text-align: center; font-weight: bold;">__________________________________________</span>
                        <div class="sub-content">
                            <span class="text" style="font-size: 18px; font-weight: bold">Quý khách vui lòng kiểm tra kĩ các thông tin:</span>
                            <div class="remind">
                                <p>1. Thông tin cá nhân.</p>
                                <p>2. Thông tin đặt vé.</p>
                            </div>

                            <div class="payment">
                                <div class="QR"">
                                    <img width="140px" src="assets/images/QR2.jpg"/>
                                    <div class="QR-detail">
                                        <p><strong>Tài khoản:</strong> Công Ty Dịch Vụ Vận Tải Hành Khách Tùng Long - Tùng Long Bus Lines</p>
                                        
                                        <p><strong>Nội dung:</strong> TLBLTT${bookingId}</p>
                                        
                                        <p></strong>Số tiền:</strong> <fmt:formatNumber value="${bookingInfo.totalAmount}" maxFractionDigits="0"/>đ</p>
                                    </div>
                                </div>
                                <div class="form-container">
                                    <form action="BookingController" method="post">
                                        <input type="hidden" name="action" value="confirmPayment" />
                                        <input type="hidden" name="bookingId" value="${bookingId}" />
                                        <input type="hidden" name="userId" value="${sessionScope.user.user_id}" />
                                        <input type="hidden" name="scheduleId" value="${scheduleId}" />
                                        <button type="submit">Xác nhận thanh toán</button>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="booking-info">
                        <h3>Thông tin đặt vé:</h3>
                        <p><strong>Mã đặt vé:</strong> ${bookingId}</p>
                        <p><strong>Mã chuyến:</strong> ${scheduleId}</p>

                        <!-- Thêm thông tin chuyến đi -->
                        <h3>Thông tin chuyến đi:</h3>
                        <p><strong>Tuyến: </strong>${departure} - ${destination}</p>
                        <p><strong>Thời gian khởi hành: </strong>
                            <fmt:formatDate value="${departureTime}" pattern="HH:mm | dd/MM/yyyy"/>
                        </p>
                        <p><strong>Thời gian đến (dự kiến): </strong>
                            <fmt:formatDate value="${arrivalTime}" pattern="HH:mm | dd/MM/yyyy"/>
                        </p>
                        <p><strong>Loại xe: </strong>${busTypeName}</p>
                        <p><strong>Tên xe: </strong>${busName}</p>
                        <p><strong>Biển số: </strong>${busNumber}</p>

                        <!-- Thông tin ghế đã chọn , !loop.last: không phải phần tử cuối cùng thì in thêm "," -->
                        <p><strong>Ghế/Giường đã chọn</strong>
                            <c:forEach var="seat" items="${seatNumbers}" varStatus="loop">
                                ${seat}<c:if test="${!loop.last}">, </c:if>
                            </c:forEach>
                        </p>


                        <p><strong>Giá 1 ghế/giường: </strong>
                            <fmt:formatNumber value="${price}"/>đ
                        </p>
                        <p><strong>Số lượng ghế: </strong>${fn:length(seatNumbers)}</p>
                        <span class="text" style="text-align: center; font-weight: bold;">__________________________________________</span>
                        <p><strong>Tổng thành tiền: </strong>
                            <fmt:formatNumber value="${bookingInfo.totalAmount}" maxFractionDigits="0"/>đ
                        </p>
                    </div>
                </div>
            </c:when>
            <c:otherwise>
                <c:redirect url="/ScheduleController"/>
            </c:otherwise>
        </c:choose>


        <%@include file="footer.jsp" %>
    </body>
</html>