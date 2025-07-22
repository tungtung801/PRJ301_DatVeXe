<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="isLoggedIn" value="${not empty sessionScope.user}"/>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Tra cứu vé - Tùng Long Bus Lines</title>
        <link rel="stylesheet" href="assets/css/styleSearchTicket.css">

    </head>
    <body>
        <%@include file="header.jsp" %>

        <div class="main-content">
            <div class="form-container">
                <h3>🎟️ Tra cứu thông tin đặt vé</h3>
                <form action="TicketController" method="post">
                    <input type="hidden" name="action" value="searchTicket">
                    <input type="text" name="phoneNum" placeholder="Nhập số điện thoại của bạn" value="${phoneNum}" required="required">
                    <input type="text" name="ticketCode" placeholder="Nhập mã vé" value="${ticketCode}" required="required">

                    <input type="submit" value="🔍 Tra cứu vé">
                </form>
            </div>

            <div class="show">
                <c:choose>
                    <c:when test="${not empty schedule and not empty booking}">
                        <h3>✅ Kết quả tra cứu vé</h3>
                        <div class="result">         
                            <p><strong>Mã vé:</strong> ${ticketCode}</p>
                            <p><strong>Mã khách hàng:</strong> ${booking.getUserID()}</p>
                            <p><strong>Mã đặt vé:</strong> ${booking.getBookingID()}</p>
                            <p><strong>Mã lịch trình:</strong> ${schedule.getScheduleID()}</p>
                            <p><strong>Tuyến:</strong> ${schedule.getDeparture()} ➝ ${schedule.getDestination()}</p>
                            <p><strong>Thời gian đặt vé:</strong> 
                                <fmt:formatDate value="${booking.getBookingDate()}" pattern="HH:mm | dd/MM/yyyy"/>
                            </p>
                            <p><strong>Thành tiền:</strong> <fmt:formatNumber value="${booking.getTotalAmount()}" maxFractionDigits="0"/>đ</p>
                            <p><strong>Trạng thái:</strong> ${booking.getStatus()}</p>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="message">
                            <c:if test="${not empty inputErr}">
                                <div class="error-message">${inputErr}</div>
                            </c:if>

                            <c:if test="${not empty generalMsg}">
                                <p class="error-message">${generalMsg}</p>
                            </c:if>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>

        <%@include file="footer.jsp" %>
    </body>
</html>
