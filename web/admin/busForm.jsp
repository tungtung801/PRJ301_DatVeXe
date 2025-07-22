<%-- 
    Document   : busForm
    Created on : Jun 28, 2025, 8:47:26 AM
    Author     : Tung Nguyen
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="currentUser" value="${sessionScope.user}"/>
<c:set var="isLoggedIn" value="${not empty currentUser}"/>
<c:set var="isAdmin" value="${currentUser.role eq 'admin'}"/>
<c:set var="bList" value="${requestScope.bList}"/>
<c:set var="busTypeList" value="${requestScope.busTypeList}"/>
<c:set var="errors" value="${requestScope.errors}"/>
<c:set var="generalMessage" value="${requestScope.generalMessage}"/>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin - Quản lý phương tiện</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/styleBusForm.css"/>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/styleUserInfo.css"/>
    </head>
    <body>
        <c:choose>
            <c:when test="${isLoggedIn and isAdmin}">

                <%@include file="sideBar.jsp" %>

                <div class="main-content">
                    <div class="content-header">
                        <h1><i class="fas fa-tachometer-alt"></i>Quản lí phương tiện</h1>
                        <div class="user-info">
                            <div class="user-avatar">A</div>
                            <div>
                                <div>${currentUser.fullname}</div>
                                <div>${currentUser.role}</div>
                            </div>
                            <div class="logout">
                                <a href="${pageContext.request.contextPath}/MainController?action=logout">Đăng xuất</a>
                            </div>
                        </div>
                    </div> 

                    <div class="main">
                        <c:if test="${not empty search_message}">
                            <div class="message">${search_message}</div>
                        </c:if>

                        <div class="form-row">
                            <div class="add">
                                <h3>Tìm phương tiện</h3>
                                <form action="BusController" method="post">
                                    <input type="hidden" name="action" value="searchBus">

                                    <label>Nhập tên phương tiện:</label>
                                    <select name="BusName">
                                        <option value="">-- Chọn tên xe --</option>
                                        <option value="Thaco Mobihome 120SL 2025" ${BusName eq 'Thaco Mobihome 120SL 2025' ? 'selected' : ''}>Thaco Mobihome 120SL 2025</option>
                                        <option value="KIA Granbird Silkroad" ${BusName eq 'KIA Granbird Silkroad' ? 'selected' : ''}>KIA Granbird Silkroad</option>
                                        <option value="Hyundai Universe EX12.5" ${BusName eq 'Hyundai Universe EX12.5' ? 'selected' : ''}>Hyundai Universe EX12.5</option>
                                        <option value="KimLong99 G24" ${BusName eq 'KimLong99 G24' ? 'selected' : ''}>KimLong99 G24</option>
                                    </select>

                                    <label>Nhập trạng thái xe:</label>
                                    <select name="Status">
                                        <option value="">-- Tất cả trạng thái --</option>
                                        <option value="Sẵn sàng" ${Status eq 'Sẵn sàng' ? 'selected' : ''}>Sẵn sàng</option>
                                        <option value="Bận" ${Status eq 'Bận' ? 'selected' : ''}>Bận</option>
                                        <option value="Bảo trì" ${Status eq 'Bảo trì' ? 'selected' : ''}>Bảo trì</option>
                                    </select>

                                    <input type="submit" value="Tìm kiếm">
                                </form>
                            </div>

                            <div class="add">
                                <h3>Mua thêm phương tiện</h3>
                                <form action="BusController" method="post">
                                    <input type="hidden" name="action" value="addBus">

                                    <label for="busNumber">Nhập biển kiểm soát:</label>
                                    <input type="text" id="busNumber" name="BusNumber" required>
                                    <c:if test="${not empty errors.busNumberError}">
                                        <span class="err">${errors.busNumberError}</span>
                                    </c:if>

                                    <label for="busName">Chọn tên phương tiện:</label>
                                    <select name="BusName" id="busName" required>
                                        <option value="Thaco Mobihome 120SL 2025">Thaco Mobihome 120SL 2025</option>
                                        <option value="KIA Granbird Silkroad">KIA Granbird Silkroad</option>
                                        <option value="Hyundai Universe EX12.5">Hyundai Universe EX12.5</option>
                                        <option value="KimLong99 G24">KimLong99 G24</option>
                                    </select>
                                    <c:if test="${not empty errors.busNameError}">
                                        <span class="err">${errors.busNameError}</span>
                                    </c:if>

                                    <label for="busTypeId">Chọn loại xe (số chỗ | phòng):</label>
                                    <p>*Lưu ý: Vui lòng chọn đúng cấu hình ghế theo thiết kế của xe!</p>
                                    <select name="BusTypeID" id="busTypeId" required>
                                        <c:forEach var="bt" items="${busTypeList}">
                                            <option value="${bt.busTypeID}">${bt.category} ${bt.seatCount} Phòng</option>
                                        </c:forEach>
                                    </select>
                                    <c:if test="${not empty errors.busTypeIDError}">
                                        <span class="err">${errors.busTypeIDError}</span>
                                    </c:if>

                                    <label for="description">Mô tả:</label>
                                    <textarea id="description" name="Description" rows="3"></textarea>

                                    <label>Trạng thái xe:</label>
                                    <select name="StatusDisplay" disabled>
                                        <option value="Sẵn sàng" selected>Sẵn sàng</option>
                                    </select>
                                    <input type="hidden" name="Status" value="Sẵn sàng">

                                    <input type="submit" value="Đặt mua">
                                    <c:if test="${not empty generalMessage}">
                                        <div class="message">${generalMessage}</div>
                                    </c:if>
                                </form>
                            </div>
                        </div>

                        <div class="show">
                            <h3>Bảng thông tin phương tiện</h3>
                            <c:choose>
                                <c:when test="${not empty busList}">
                                    <c:if test="${not empty showMessage}">
                                        <div class="message">Thông báo: ${showMessage}</div>
                                    </c:if>
                                    <c:if test="${not empty searchMsg}">
                                        <div class="message">${searchMsg}</div>
                                    </c:if>

                                    <table>
                                        <thead>
                                            <tr>
                                                <td>ID</td>
                                                <td>Biển Số</td>
                                                <td>Tên Xe</td>
                                                <td>Loại Xe</td>
                                                <td>Mô Tả</td>
                                                <td>Trạng Thái</td>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="b" items="${busList}">
                                                <tr>
                                                    <td>${b.busID}</td>
                                                    <td>${b.busNumber}</td>
                                                    <td>${b.busName}</td>
                                                    <td>${b.busTypeName}</td>
                                                    <td>${b.description}</td>
                                                    <td>${b.status}</td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </c:when>
                                <c:otherwise>
                                    <p>Không tìm thấy phương tiện nào</p>
                                </c:otherwise>
                            </c:choose>
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
