<%-- 
    Document   : routeForm
    Created on : Jun 30, 2025, 8:05:05 PM
    Author     : Tung Nguyen
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="loggedInUser" value="${sessionScope.user}"/>
<c:set var="isLoggedIn" value="${not empty loggedInUser}"/>
<c:set var="isAdmin" value="${loggedInUser.role eq 'admin'}"/>
<c:set var="departure_keyword" value="${requestScope.departure_keyword}"/>
<c:set var="destination_keyword" value="${requestScope.destination_keyword}"/>
<c:set var="search_message" value="${requestScope.search_message}"/>
<c:set var="input_message" value="${requestScope.input_message}"/>
<c:set var="searchPerformed" value="${requestScope.searchPerformed}"/>
<c:set var="routeList" value="${requestScope.routes}"/>
<c:set var="departure_err" value="${requestScope.departure_err}"/>
<c:set var="destination_err" value="${requestScope.destination_err}"/>
<c:set var="distance_err" value="${requestScope.distance_err}"/>
<c:set var="checkError" value="${requestScope.checkError}"/>
<c:set var="add_message" value="${requestScope.add_message}"/>
<c:set var="locationList" value="${requestScope.locationList}"/>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin - Quản lí tuyến</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/styleRouteForm.css"/>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/styleUserInfo.css"/>
        <style>
            .form-row {
                display: flex;
                gap: 30px;
                margin-top: 30px;
                margin-bottom: 30px;
            }
            .add {
                flex: 2;
                background: white;
                padding: 25px;
                border-radius: 10px;
                box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
                border: 1px solid #e9ecef;
            }
            .show {
                flex: 3;
                background: white;
                border-radius: 10px;
                overflow: hidden;
                box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
                border: 1px solid #e9ecef;
            }
        </style>
    </head>
    <body>
        <c:choose>
            <c:when test="${isLoggedIn and isAdmin}">

                <%@include file="sideBar.jsp" %>

                <div class="main-content">
                    <!-- Header -->
                    <div class="content-header">
                        <h1><i class="fas fa-tachometer-alt"></i>Quản lí tuyến</h1>
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

                        <div class="search">
                            <h3>Tìm kiếm tuyến đường</h3>
                            <form action="RouteController" method="post">
                                <input type="hidden" name="action" value="searchRoute"/>
                                <input type="text" name="strDepartKeyword" placeholder="Nhập điểm xuất phát ..." 
                                       value="${departure_keyword}"/><br>

                                <input type="text" name="strDestinaKeyword" placeholder="Nhập đích đến ..." 
                                       value="${destination_keyword}"/>

                                <div class="message">
                                    <c:if test="${not empty input_message}">
                                        ${input_message}
                                    </c:if>
                                    <c:if test="${not empty search_message}">
                                        ${search_message}<br/>
                                    </c:if>
                                </div>
                                <input type="submit" value="Tìm tuyến"/>


                            </form>
                        </div>

                        <div class="form-row">
                            <div class="add">
                                <h3>Thêm tuyến đường mới</h3>
                                <form action="RouteController" method="post">
                                    <input type="hidden" name="action" value="addRoute">

                                    Nhập điểm xuất phát:
                                    <select name="departure_name">
                                        <option value="">-- Chọn điểm xuất phát --</option>
                                        <c:forEach var="l" items="${locationList}">
                                            <option value="${l.name}">${l.name}</option>
                                        </c:forEach>
                                    </select>

                                    Nhập đích đến:
                                    <select name="destination_name">
                                        <option value="">-- Chọn đích đến --</option>
                                        <c:forEach var="l" items="${locationList}">
                                            <option value="${l.name}">${l.name}</option>
                                        </c:forEach>
                                    </select>

                                    Nhập khoảng cách: <input type="text" name="distance">


                                    <input type="submit" value="Thêm tuyến mới">
                                    <div class="message">
                                        <c:if test="${not empty add_message}">
                                            ${add_message}
                                        </c:if>
                                        <c:if test="${not empty departure_err}">
                                            ${departure_err}
                                        </c:if>
                                        <c:if test="${not empty destination_err}">
                                            ${destination_err}
                                        </c:if>
                                        <c:if test="${not empty distance_err}">
                                            ${distance_err}
                                        </c:if>
                                    </div>
                                </form>
                            </div>

                            <div class="show">
                                <h3>Danh sách tuyến đường</h3>
                                <c:choose>
                                    <c:when test="${not empty routeList}">
                                        <div class="table-container">
                                            <table>
                                                <thead>
                                                    <tr>
                                                        <th>ID</th>
                                                        <th>Điểm Xuất Phát</th>
                                                        <th>Đích Đến</th>
                                                        <th>KM</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:forEach var="r" items="${routeList}">
                                                        <tr>
                                                            <td>${r.route_id}</td>
                                                            <td>${r.departure}</td>
                                                            <td>${r.destination}</td>
                                                            <td>${r.distance_km}</td>
                                                        </tr>
                                                    </c:forEach>
                                                </tbody>
                                            </table>
                                        </div>
                                    </c:when>
                                    <c:otherwise>
                                        <p>Không có tuyến đường nào trong danh sách.</p>
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
