<%-- 
    Document   : bookingSuccess
    Created on : Jul 12, 2025, 12:50:47 AM
    Author     : Tung Nguyen
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Thanh toán thành công - Cảm ơn quý khách</title>
        <link rel="stylesheet" href="assets/css/styleBookingSuccess.css">
    </head>
    <body>
        <%@include file="header.jsp" %>

        <div class="main-content">
            <div class="button">
                <a href="${pageContext.request.contextPath}/Home.jsp">🔙 Về trang chủ</a>
            </div>
            <div class="main">
                <h2 style="color: green;">🎉 Thanh toán thành công!</h2>
                <p>Cảm ơn quý khách đã xử dụng dịch vụ của</p>
                <p class="company-name">Công Ty Dịch Vụ Vận Tải Hành Khách Tùng Long - Tùng Long Bus Lines</p>
                <p>Mã vé của quý khách: <strong>${ticketCode}</strong></p>
                <br><br>
                <p>Mã vé đã được gửi qua Email của Quý khách để tiện việc tra cứu !</p>
                <p>Để tra cứu vé, vui lòng <a href="searchTicket.jsp">nhấp vào đây !</a></p>
            </div>

        </div>

        <%@include file="footer.jsp" %>
    </body>
</html>

