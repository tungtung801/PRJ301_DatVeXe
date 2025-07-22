<%-- 
    Document   : sideBar
    Created on : Jul 5, 2025, 11:00:25 AM
    Author     : Tung Nguyen
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/styleSideBarAdmin.css"/>

<div class="side-bar">
    <div class="logo">
        <img src="${pageContext.request.contextPath}/assets/images/tunglong_logo.png" alt="Logo">
    </div>
    
    <div class="admin-actions">
        <form action="${pageContext.request.contextPath}/MainController" method="post">
            <input type="hidden" name="action" value="locationManage">
            <input type="submit" value="Quản lí địa điểm">              
        </form>
        
        <form action="${pageContext.request.contextPath}/MainController" method="post">
            <input type="hidden" name="action" value="routeManage">
            <input type="submit" value="Quản lí tuyến đường">
        </form>
        
        <form action="${pageContext.request.contextPath}/MainController" method="post">
            <input type="hidden" name="action" value="scheduleManage">
            <input type="submit" value="Quản lí lịch trình">
        </form>
        
        <form action="${pageContext.request.contextPath}/MainController" method="post">
            <input type="hidden" name="action" value="busManage">
            <input type="submit" value="Quản lí phương tiện">
        </form>
        
        <form action="${pageContext.request.contextPath}/MainController" method="post">
            <input type="hidden" name="action" value="staffManage">
            <input type="submit" value="Quản lí nhân viên">
        </form>

        <div style="padding: 10px;">
            <a href="${pageContext.request.contextPath}/Home.jsp" style="color: white; font-weight: 500;">⬅ Trở về trang chủ</a>
        </div>
        
        <div style="display: block; position: absolute; bottom: 10; width: 100%"class="sub-content">
            <p>Bản quyền &COPY; thuộc về Công Ty Dịch Vụ Vận Tải Hành Khách Tùng Long - Tùng Long Bus Lines</p>
        </div>
    </div>
</div>
