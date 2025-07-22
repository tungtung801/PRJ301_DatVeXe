<%-- 
    Document   : welcome
    Created on : Jun 12, 2025, 11:30:55 AM
    Author     : Tung Tung
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="utils.AuthUtils" %>
<%@page import="model.UserDTO" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>WELCOME(test) Page</title>
    </head>
    <body>
        <%
            if(AuthUtils.isLoggedIn(request)){
                UserDTO currentLoginUser = AuthUtils.getCurrentUser(request);
        %>

        <header style="background-color: #01011B">
            <div class="logo">
                <a href="Home.jsp" title="Tùng Long Bus Lines">
                    <img src="assets/images/tunglong_logo.png" width="220px" height="70px"/>
                </a>
            </div>
            <nav class="nav-container">
                <ul class="nav-items">
                    <li><a href="Home.jsp">Trang chủ</a></li>
                    <li><a href="Trip.jsp">Đặt vé</a></li>
                    <li><a href="TicketDetail.jsp">Tra cứu vé</a></li>
                    <li><a href="Contact.jsp">Liên hệ</a></li>
                </ul>
            </nav>
        </header>

        <h1 style="color: highlight">WELCOME <%= currentLoginUser.getFullname()%></h1>
        <h4 style="color: sandybrown">You are login as: <%= currentLoginUser.getRole()%></h4>
        <span><a href="MainController?action=logout">Logout</a></span>

        <footer>
            <p class="footer-name"><strong>CÔNG TY DỊCH VỤ VẬN TẢI HÀNH KHÁCH TÙNG LONG - TÙNG LONG BUS LINES</strong></p>
            <div class="footer-container">
                <div class="footer-left">
                    <p><strong>Địa chỉ:</strong> Lô E2a-7, Đường D1, Phường Long Thạnh Mỹ, Thành phố Thủ Đức, Thành phố Hồ Chí Minh.</p>
                    <p><strong>Email:</strong> hotro@bustunglong.vn</p>
                    <p><strong>Hotline:</strong> 1900 9999</p>
                </div>
                <div class="footer-right">
                    <ul>
                        <li><a href="AboutUs.jsp">Về chúng tôi</a></li>
                        <li><a href="Question.jsp">Câu hỏi thường gặp và đánh giá của khách hàng</a></li>
                    </ul>
                    <div class="footer-tem">
                        <img src="assets/images/tem1.PNG"> 
                        <img src="assets/images/tem2.PNG">
                    </div>
                </div>
            </div>
            <p id="copyright">Bản quyền &copy thuộc về Công Ty Dịch Vụ Vận Tải Hành Khách Tùng Long - Tùng Long Bus Lines</p>
        </footer>
        <% } else { %>
        <div class="access-denied-message">
            <%= AuthUtils.getAccessDenied("welcome.jsp")%> <br>
            (You must login first, <a href="Home.jsp">Go back to home page</a>)
        </div>
        <% } %>
    </body>
</html>
