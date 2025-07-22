<%--
    Document   : locationForm.jsp
    Created on : Jun 26, 2025
    Author     : Tung Nguyen
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="locationList" value="${requestScope.lList}"/>
<c:set var="message" value="${requestScope.message}"/>
<c:set var="loggedInUser" value="${sessionScope.user}"/>
<c:set var="isloggedIn" value="${not empty loggedInUser}"/>
<c:set var="isAdmin" value="${loggedInUser.role eq 'admin'}"/>
<c:set var="keyword_err" value="${requestScope.keyword_err}"/>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin - Quản lý Địa điểm</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/styleSideBarAdmin.css"/>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/styleUserInfo.css"/>
        <script src="https://kit.fontawesome.com/a076d05399.js" crossorigin="anonymous"></script>
        <style>
            .add {
                flex: 2;
            }

            .show {
                flex: 3;
            }
        </style>
    </head>
    <body>
        <c:choose>
            <c:when test="${isloggedIn and isAdmin}">
                <%@include file="sideBar.jsp" %>

                <div class="main-content">
                    <div class="content-header">
                        <h1><i class="fas fa-map-marker-alt"></i>Quản lí địa điểm</h1>
                        <div class="user-info">
                            <div class="user-avatar">A</div>
                            <div>
                                <div style="font-weight: bold;">${loggedInUser.fullname}</div>
                                <div style="font-size: 0.9rem; color: #666;">${loggedInUser.role}</div>
                            </div>
                            <div class="logout">
                                <a href="${pageContext.request.contextPath}/MainController?action=logout">Logout</a>
                            </div>
                        </div>
                    </div> 

                    <div class="main">
                        <div class="form-row">
                            <div class="add">
                                <form action="LocationController" method="post">
                                    <input type="hidden" name="action" value="addLocation">
                                    
                                    <label for="keyword">Tên địa điểm:</label>
                                    <input type="text" id="keyword" name="keyword">
                                    <div class="message">
                                        <span class="err">${not empty keyword_err ? keyword_err : ''}</span>
                                    </div>

                                    <input type="submit" value="Thêm địa điểm">
                                    <div class="message">
                                        <span>${not empty requestScope.message ? message : ''}</span>
                                    </div>
                                </form>   
                            </div>

                            <div class="show"> 
                                <h3>Danh sách địa điểm</h3>
                                <c:choose>
                                    <c:when test="${not empty locationList}">
                                        <table>
                                            <thead>
                                                <tr>
                                                    <td>ID</td>
                                                    <td>Tên</td>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach var="loc" items="${locationList}">
                                                    <tr>
                                                        <td>${loc.location_id}</td>
                                                        <td>${loc.name}</td>
                                                    </tr>
                                                </c:forEach>
                                            </tbody>
                                        </table>
                                    </c:when>
                                    <c:otherwise>
                                        <p>Không có địa điểm nào trong danh sách.</p>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </div>
                </div>
            </c:when>
            <c:otherwise>
                <c:redirect url="/Home.jsp"/>
            </c:otherwise>
        </c:choose>
    </body>
</html>
