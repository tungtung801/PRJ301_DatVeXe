<%@ page pageEncoding="UTF-8" %>
<%@ page import="model.UserDTO" %>
<%@ page import="utils.AuthUtils" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="loggedInUser" value="${sessionScope.user}"/>
<c:set var="isLoggedIn" value="${not empty loggedInUser}"/>
<c:set var="isAdmin" value="${loggedInUser.role eq 'admin'}"/>
<c:set var="isNhanVien" value="${loggedInUser.role eq 'tx' or loggedInUser.role eq 'tv'}"/>

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css" integrity="sha512-SnH5WK+bZxgPHs44uWIX+LLJAJ9/2PkPKZ5QiAj6Ta86w+fsb2TkcmfRyVX3pBnMFcV7oQPJkl9QevSCWr3W6A==" crossorigin="anonymous" referrerpolicy="no-referrer" />
<link rel="stylesheet" href="assets/css/styleHeader.css"/>

<header style="background-color: #01011B">
    <div class="logo">
        <a href="Home.jsp" title="Tùng Long Bus Lines" >
            <img src="assets/images/tunglong_logo.png" width="220px" height="70px"/>
        </a>
    </div>
    <nav class="nav-container">
        <ul class="nav-items">
            <li><a href="Home.jsp">Trang chủ</a></li>
            <li><a href="${pageContext.request.contextPath}/ScheduleController">Xem lịch trình</a></li>
            <li><a href="searchTicket.jsp">Tra cứu vé</a></li>
            <li><a href="aboutUs.jsp">Về chúng tôi</a></li>
        </ul>
    </nav>
    <span class="nav-span">|</span>
    <div class="nav-user">
        <c:choose>
            <c:when test="${isLoggedIn and isAdmin}">
                <span style="color: #ff5722; padding: 10px; font-weight: bold;"><i style="color: #ff5722;" class="fas fa-user-cog"></i> ${loggedInUser.fullname}</span>
                <a href="${pageContext.request.contextPath}/admin/admin.jsp" class="nav-user-accent">Dashboard</a>
                <a style="background-color: red;" href="MainController?action=logout" class="nav-user-accent">Đăng xuất</a>
            </c:when>
            <c:when test="${isLoggedIn and not isAdmin}">
                <span style="color: #00AE72; padding: 10px; font-weight: bold;"><i style="color: #00AE72;" class="fas fa-user-circle"></i> ${loggedInUser.fullname}</span>
                <a style="background-color: red;" href="MainController?action=logout" class="nav-user-accent">Đăng xuất</a>
            </c:when>
            <c:otherwise>
                <a href="login.jsp">Đăng nhập</a>
                <a href="register.jsp" class="nav-user-accent">Đăng ký</a>
            </c:otherwise>
        </c:choose>
    </div>

    <c:if test="${isLoggedIn and (isNhanVien or isAdmin)}">
        <form action="ScheduleController" method="post" class="staff-search-form">
            <input type="hidden" name="action" value="gotosearchNV"/>
            <input type="submit" value="Tìm kiếm dành cho Nhân Viên" class="staff-search-button"/>
        </form>
    </c:if>
</header>
<div class="header-spacer"></div>