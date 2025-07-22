<%--
    Document   : login
    Created on : May 31, 2025, 7:53:07 PM
    Author     : Tung Tung
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="utils.AuthUtils" %>
<%@page import="model.UserDTO" %>

<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Đăng nhập | Tùng Long Bus Lines</title>
        <link rel="stylesheet" href="assets/css/styleLogin.css"/>
    </head>
    <body>
        <%
            if(AuthUtils.isLoggedIn(request)){
            response.sendRedirect("Home.jsp");
            }else{
                Object messageObj = request.getAttribute("cantLogin");
                String message = (messageObj==null)?"":(messageObj)+"";
            
        %>

        <%@include file="header.jsp" %>

        <main class="main-content">
            <div class="login-pannel" id="loginPanel">
                <div class="login-pannel-left"> 
                </div>
                <div class="login-pannel-right">
                    <form action="MainController" method="post" class="login-form">
                        <input type="hidden" name="action" value="login">
                        <h2>Đăng nhập vào tài khoản </h2>
                        <div class="user-input">
                            <input type="text" name="uPN" placeholder="Nhập số điện thoại" pattern="[0-9]*" inputmode="numeric" required="required">
                            <input type="password" name="uPW" placeholder="Nhập mật khẩu" required="required">
                        </div>
                        <div class="form-button">
                            <div class="form-button-first">
                                <div class="remember-me-group">
                                    <input type="checkbox" name="uRemember" id="rememberMe">
                                    <label for="rememberMe">Ghi nhớ tôi</label>
                                </div>
                                <input type="submit" name="login-btn" value="Đăng nhập" class="submit-button">
                            </div>
                            <div class="form-button-second">
                                <span class="divider-line">______________________</span>
                                <p>Bạn chưa có tài khoản ?</p>
                                <span class="divider-line"></span>
                                <a href="register.jsp" class="register-link">Đăng kí ngay</a>
                            </div>
                            <div style="text-align: center;">
                                <a href="forgetpassword.jsp" class="forgot-link">Quên mật khẩu?</a>
                            </div>

                        </div>
                        <br>
                        <% if (message != null && !message.trim().isEmpty()) { %>
                        <div class="error-message"><%= message %></div>
                        <% } %>
                        <div class="copyright">
                            <p id="copyright">Bản quyền &COPY; Tùng Long Bus Lines</p>
                        </div>
                    </form>
                </div>
            </div>
        </main>
        <% } %>
    </body>
</html>
