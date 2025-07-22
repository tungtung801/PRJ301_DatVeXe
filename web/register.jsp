<%-- 
    Document   : register.jsp
    Created on : May 31, 2025, 7:53:07 PM
    Author     : Tung Tung
--%>

<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="utils.AuthUtils" %>
<%@page import="model.UserDTO" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Đăng ký | Tùng Long Bus Lines</title>
        <link rel="stylesheet" href="assets/css/styleRegister.css"/>
    </head>
    <body>

        <%
            if (AuthUtils.isLoggedIn(request)) {
                response.sendRedirect("Home.jsp");
                return;
            }
            String name_err = (String) request.getAttribute("name_err");
            String phone_err = (String) request.getAttribute("phone_err");
            String password_err = (String) request.getAttribute("password_err");
            String email_err = (String) request.getAttribute("email_err");
            String message = (String) request.getAttribute("message");
            UserDTO user = (UserDTO)request.getAttribute("user");
        %>

        <%@include file="header.jsp" %>

        <main class="main-content">
            <div class="login-pannel" id="loginPanel">
                <div class="login-pannel-left"></div>

                <div class="login-pannel-right">
                    <form action="MainController" method="post" class="login-form">
                        <input type="hidden" name="action" value="register">
                        <h2>Đăng ký tài khoản mới</h2>

                        <div class="user-input">
                            <div class="form-group">
                                <% if (name_err != null) { %>
                                <div class="error-message"><%= name_err %></div>
                                <% } %>
                                <input type="text" name="uNA" placeholder="Nhập tên của bạn" required
                                       value="<%=user!= null?user.getFullname():""%>"
                                       >
                            </div>

                            <div class="form-group">
                                <% if (phone_err != null) { %>
                                <div class="error-message"><%= phone_err %></div>
                                <% } %>
                                <input type="text" name="uPN" placeholder="Nhập số điện thoại" pattern="[0-9]*" inputmode="numeric" required
                                       value="<%=user!= null?user.getPhone():""%>"
                                       >
                            </div>


                            <div class="form-group">
                                <% if (password_err != null) { %>
                                <div class="error-message"><%= password_err %></div>
                                <% } %>
                                <input type="password" name="uPW" placeholder="Nhập mật khẩu" required
                                       value="<%=user!= null?user.getPassword():""%>"
                                       > 
                            </div>


                            <div class="form-group">
                                <% if (email_err != null) { %>
                                <div class="error-message"><%= email_err %></div>
                                <% } %>
                                <input type="text" name="uEM" placeholder="Nhập email" required
                                       value="<%=user!= null?user.getEmail():""%>"
                                       >
                            </div>

                        </div>

                        <div class="form-button">
                            <input class="btn1" type="submit" value="Đăng ký">
                            <br><% if (message != null && !message.trim().isEmpty()) { %>
                            <div class="error-message"><%= message %></div>
                            <% } %>
                            <span class="divider-line">______________________</span>
                            <span>Hoặc đã có tài khoản rồi?</span>
                            <a class="btn2" href="login.jsp">Quay lại đăng nhập</a>
                        </div>
                            <div class="copyright">
                            <p id="copyright">Bản quyền &COPY; Tùng Long Bus Lines</p>
                        </div>
                    </form>


                </div>
            </div>
        </main>

    </body>
</html>
