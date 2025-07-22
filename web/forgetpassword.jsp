<%-- 
    Document   : forgetpassword
    Created on : Jul 7, 2025, 10:57:09 PM
    Author     : ADMIN
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.UserDTO" %>
<%@page import="utils.AuthUtils" %>
<%@page import="dao.UserDAO" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Thay đổi mật khẩu - Tùng Long Bus Lines</title>
        <link rel="stylesheet" href="assets/css/styleForgotPass.css"/>
    </head>
    <body>
        <%
            // Get attributes from request (for form submission responses)
            String confirm_error = (String)request.getAttribute("confirm_error");
            String check_error = (String)request.getAttribute("check_error");
            String success = (String)request.getAttribute("success");
            String message = (String)request.getAttribute("message");
            UserDTO user = (UserDTO)request.getAttribute("user");
            
            // Also check session for user
            if (user == null) {
                user = (UserDTO)session.getAttribute("user");
            }
        %>
        <%@include file="header.jsp" %>

        <main class="main-content">
            <div class="forgot-panel">
                <a class="back-link" href="login.jsp">← Quay về đăng nhập</a>

                <% if (user == null) { %>
                <h2>Bước 1: Xác minh danh tính</h2>
                <p>Vui lòng nhập số điện thoại và email để xác minh tài khoản.</p>

                <% if (message != null && !message.isEmpty()) { %>
                <div class="message-error"><%= message %></div>
                <% } %>

                <form action="MainController" method="post" class="forgot-form">
                    <input type="hidden" name="action" value="forgetpass"/>
                    <input type="text" name="phone" placeholder="Số điện thoại" required />
                    <input type="email" name="strEmail" placeholder="Email" required />
                    <input type="submit" value="Xác minh">
                </form>

                <% } else { %>
                <h2>Bước 2: Đổi mật khẩu mới</h2>
                <p>Đã xác minh! Vui lòng nhập mật khẩu mới bên dưới.</p>

                <% if (success != null && !success.isEmpty()) { %>
                <div class="message-success"><%= success %></div>
                <% } %>

                <% if (confirm_error != null && !confirm_error.isEmpty()) { %>
                <div class="message-error"><%= confirm_error %></div>
                <% } %>

                <% if (check_error != null && !check_error.isEmpty()) { %>
                <div class="message-error"><%= check_error %></div>
                <% } %>

                <% if (success == null || success.isEmpty()) { %>
                <form action="MainController" method="post" class="forgot-form">
                    <input type="hidden" name="action" value="forgetpass"/>
                    <input type="password" name="newpass" placeholder="Mật khẩu mới" required />
                    <input type="password" name="confirmnewpass" placeholder="Xác nhận mật khẩu" required />
                    <input type="submit" value="Cập nhật mật khẩu">
                </form>
                <% } %>
                <% } %>
            </div>
        </main>
        <%@include file="footer.jsp" %>
    </body>
</html>