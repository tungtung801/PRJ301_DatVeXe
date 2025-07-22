<%-- 
    Document   : searchNV
    Created on : Jul 22, 2025, 4:41:33 PM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.UserDTO" %>
<%@page import="utils.AuthUtils" %>
<%@page import="java.util.List" %>
<%@page import="model.ScheduleDTO" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Tùng Long Bus Lines - Tìm kiếm lịch trình nhân sự</title>
        <link rel="stylesheet" href="assets/css/styleSearchNV.css"/>
    </head>
    <body>
        <%@include file="header.jsp" %>

<%
    // Check if user is authenticated first
    UserDTO currentUser = AuthUtils.getCurrentUser(request);
    if (currentUser == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    
    
    boolean isAuthorized = false;
    try {
        isAuthorized = AuthUtils.isNhanVien(request) || AuthUtils.isAdmin(request);
    } catch (Exception e) {
       
        response.sendRedirect("login.jsp");
        return;
    }
    
    if (isAuthorized) {
        String name = (String) request.getAttribute("name");
        String message = (String) request.getAttribute("message");
        List<ScheduleDTO> schelist = (List<ScheduleDTO>)request.getAttribute("schelist");
%>
        <div>
            <form action="ScheduleController" method="post">
                <input type="hidden" name="action" value="searchNV"/>
                <h2>Tra cứu lịch làm việc cho nhân sự:</h2>
                <p>Nhân sự vui lòng tra cứu lịch phục vụ tuyến xe của mình tại đây !</p><br>
                <input type="text" name="employeeName" value="<%=name!=null?name:""%>" 
                       placeholder="Nhập tên nhân sự ..."/>
                <input type="submit" value="Tra cứu" class="search-btn"/>
            </form>
        </div>
        <% if(schelist != null && !schelist.isEmpty()) { %>
        <table>
            <thead>
                <tr>
                    <th>Schedule ID</th>
                    <th>Route ID</th>
                    <th>Bus ID</th>
                    <th>Bus Number</th>
                    <th>Bus Name</th>
                    <th>Departure</th>
                    <th>Destination</th>
                    <th>Departure time</th>
                    <th>Arrival time</th>
                    <th>Driver Name</th>
                    <th>Attendant Name</th>
                </tr>
            </thead>  
            <tbody>
                <% for(ScheduleDTO p: schelist) { %>
                <tr>
                    <td data-label="Schedule ID" class="product-id"><%=p.getScheduleID()%></td>
                    <td data-label="Route ID" class="product-name"><%=p.getRouteID()%></td>
                    <td data-label="Bus ID"><%=p.getBusID()%></td>
                    <td data-label="Bus Number"><%=p.getBusNumber()%></td>

                    <td data-label="Bus Name"><%=p.getBusName()%></td>
                    <td data-label="Departure"><%=p.getDeparture()%></td>
                    <td data-label="Destination"> <%=p.getDestination()%></td>

                    <td data-label="Departure time"><%=p.getDepartureTime()%></td>
                    <td data-label="Arrival time"><%=p.getArrivalTime()%> </td>
                    <td data-label="Driver Name"><%=p.getDriverName()%></td>
                    <td data-label="Attendant Name"><%=p.getAttendantName()%> </td>
                </tr>
                <% } %>
            </tbody>
        </table>
        <% } else { %>
        <% if(message != null) { %>
        <%=message%>
        <% } %>
        <% } %>
        <% } %>   

        <%@include file="footer.jsp" %>
    </body>
</html>
