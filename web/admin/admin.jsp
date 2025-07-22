<%-- 
    Document   : admin
    Created on : Jun 26, 2025, 8:01:27 PM
    Author     : Tung Nguyen
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="utils.AuthUtils" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="loggedInUser" value="${sessionScope.user}"/>
<c:set var="isLoggedIn" value="${not empty loggedInUser}"/>
<c:set var="isAdmin" value="${loggedInUser.role eq 'admin'}"/>


<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Tùng Long Bus Lines - Admin Dashboard</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/styleAdmin.css"/>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/styleUserInfo.css"/>
    </head>
    <body>
        <c:choose>
            <c:when test="${isLoggedIn and isAdmin}">

                <%@include file="sideBar.jsp" %>

                <div class="main-content">

                    <!-- Header -->
                    <div class="content-header">
                        <h1><i class="fas fa-tachometer-alt"></i>Admin Dashboard</h1>
                        <div class="user-info">
                            <div class="user-avatar">A</div>
                            <div>
                                <div style="font-weight: bold;">${loggedInUser.fullname}</div>
                                <div style="font-size: 0.9rem; color: #666;">${loggedInUser.role}</div>
                            </div>
                            <div class="logout">
                                <a href="${pageContext.request.contextPath}/MainController?action=logout">Đăng xuất</a>
                            </div>
                        </div>
                    </div> 
                            <div class="main">
                                <h2 style="color: red">Công Ty Dịch Vụ Vận Tải Hành Khách Tùng Long - Tùng Long Bus Lines</h2>
                                <h3>Chào Mừng Quay Trở Lại !</h3>
                                <br>
                                <p>Để quản lí trang, Admin vui lòng chọn các mục từ thanh tab bên trái !</p>
                            </div>



                </div>

            </c:when>
            <c:when test="${isLoggedIn and not isAdmin}">
                <p>Permission denied, <a href="${pageContext.request.contextPath}/login.jsp">go back</a></p>            
            </c:when>
            <c:otherwise>
                <p>If you are Administrator, please <a href="${pageContext.request.contextPath}/login.jsp">login</a></p>    
            </c:otherwise>
        </c:choose>

    </body>
</html>
