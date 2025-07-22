/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import DTO.RouteDTO;
import dao.LocationDAO;
import dao.RouteDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.LocationDTO;
import utils.AuthUtils;

/**
 *
 * @author Tung Nguyen
 */
@WebServlet(name = "RouteController", urlPatterns = {"/RouteController"})
public class RouteController extends HttpServlet {

    private static final String LOGIN_PAGE = "/login.jsp";
    private static final String WELCOME_PAGE = "/Home.jsp";
    private static final String ROUTE_PAGE = "/admin/routeForm.jsp";
    RouteDAO rdao = new RouteDAO();
    AuthUtils au = new AuthUtils();
    LocationDAO ldao = new LocationDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String url = ROUTE_PAGE;
        try {
            String action = request.getParameter("action");
            if (action.contains("routeManage") || action.contains("viewRoute")) {
                url = handleRouteViewing(request, response);
            } else if (action.contains("searchRoute")) {
                url = handleRouteSearching(request, response);
            } else if (action.contains("addRoute")) {
                url = handleRouteAdding(request, response);
            }
        } catch (Exception e) {
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private String handleRouteSearching(HttpServletRequest request, HttpServletResponse response) {
        String url = ROUTE_PAGE;
        if (!AuthUtils.isLoggedIn(request)) {
            url = LOGIN_PAGE;
        }
        if (!AuthUtils.isAdmin(request)) {
            return WELCOME_PAGE;
        }
        
        String departure_keyword = request.getParameter("strDepartKeyword");
        String destination_keyword = request.getParameter("strDestinaKeyword");

        if ((departure_keyword == null || departure_keyword.trim().isEmpty())
                && (destination_keyword == null || destination_keyword.trim().isEmpty())) {
            request.setAttribute("input_message", "Hãy nhập tối thiểu 1 trường!");
            return handleRouteViewing(request, response);
        } else {

            try {
                // Search for routes
                List<RouteDTO> routes = rdao.searchRoutes(departure_keyword, destination_keyword);

                if (routes != null && !routes.isEmpty()) {
                    request.setAttribute("routes", routes);
                    request.setAttribute("searchPerformed", true);
                } else {
                    request.setAttribute("search_message", "Không tìm thấy tuyến đường phù hợp!");
                }

                // Preserve search keywords for form  List<LocationDTO> locationList = ldao.getAllLocations();
                handleRouteViewing(request, response);
                request.setAttribute("departure_keyword", departure_keyword);
                request.setAttribute("destination_keyword", destination_keyword);

            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("search_message", "Error searching routes: " + e.getMessage());
                return handleRouteViewing(request, response);
            }
        }

        return url;
    }

    private String handleRouteViewing(HttpServletRequest request, HttpServletResponse response) {
        String url = ROUTE_PAGE;
        if (!AuthUtils.isLoggedIn(request)) {
            return LOGIN_PAGE;
        }
        if (!AuthUtils.isAdmin(request)) {
            return WELCOME_PAGE;
        }

        try {
            // Load routes
            if (request.getAttribute("searchPerformed") == null) {
                List<RouteDTO> routes = rdao.getAll();
                request.setAttribute("routes", routes);
            }

            // THÊM: Load locations
            List<LocationDTO> locationList = ldao.getAllLocations();
            request.setAttribute("locationList", locationList);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error_general", "Error: " + e.getMessage());
        }
        return url;
    }

    private String handleRouteAdding(HttpServletRequest request, HttpServletResponse response) {
        if (!AuthUtils.isLoggedIn(request)) {
            return LOGIN_PAGE;
        }
        if (!AuthUtils.isAdmin(request)) {
            return WELCOME_PAGE;
        }

        String checkError = "";
        String message = "";

        try {
            // Get form parameters
            String departure_name = request.getParameter("departure_name");
            String destination_name = request.getParameter("destination_name");
            String distanceParam = request.getParameter("distance");

            // Input validation
            if (departure_name == null || departure_name.trim().isEmpty()) {
                checkError += "Hãy nhập điểm đi!<br/>";
            }
            if (destination_name == null || destination_name.trim().isEmpty()) {
                checkError += "Hãy nhập điểm đến!<br/>";
            }
            if (departure_name != null && destination_name != null
                    && departure_name.trim().equalsIgnoreCase(destination_name.trim())) {
                checkError += "Điểm đi và Điểm đến không được trùng nhau!<br/>";
            }

            float distance = 0;
            if (distanceParam == null || distanceParam.trim().isEmpty()) {
                checkError += "Hãy nhập khoảng cách!<br/>";
            } else {
                try {
                    distance = Float.parseFloat(distanceParam.trim());
                    if (distance <= 0) {
                        checkError += "Khoảng cách nhập vào phải lớn hơn 0.<br/>";
                    }
                } catch (NumberFormatException e) {
                    checkError += "Sai định dạng!<br/>";
                }
            }

            // Check if locations exist in database
            LocationDTO departure_location = null;
            LocationDTO destination_location = null;

            if (departure_name != null && !departure_name.trim().isEmpty()) {
                departure_location = ldao.getLocationByName(departure_name.trim());
                if (departure_location == null) {
                    checkError += "Điểm đi: " + departure_name + " không tồn tại trong hệ thống!<br/>";
                }
            }

            if (destination_name != null && !destination_name.trim().isEmpty()) {
                destination_location = ldao.getLocationByName(destination_name.trim());
                if (destination_location == null) {
                    checkError += "Điểm đến: " + destination_name + " không tồn tại trong hệ thống!<br/>";
                }
            }

            // Check if route already exists
            if (checkError.isEmpty()) {
                RouteDTO existingRoute = rdao.search(departure_name.trim(), destination_name.trim());
                if (existingRoute != null) {
                    checkError += "Tuyến đường từ: " + departure_name + " đến " + destination_name + " đã tồn tại!<br/>";
                }
            }

            // Create route if no errors
            RouteDTO route = new RouteDTO(departure_name != null ? departure_name.trim() : "",
                    destination_name != null ? destination_name.trim() : "",
                    distance);

            if (checkError.isEmpty()) {
                boolean created = rdao.create(route);
                if (created) {
                    message = "Thêm tuyến đường mới thành công!";
                    // Clear the route object so form resets
                    route = new RouteDTO("", "", 0);
                } else {
                    checkError += "Thất bại khi thêm tuyên đường mới, hãy kiểm tra lại hệ thống!<br/>";
                }
            }
            // Set attributes for the response
            request.setAttribute("route", route);
            request.setAttribute("checkError", checkError);
            request.setAttribute("add_message", message);
            request.setAttribute("departure_err", checkError.contains("Departure") ? "Hãy kiểm tra lại điểm đi!" : "");
            request.setAttribute("destination_err", checkError.contains("Destination") ? "Hãy kiểm tra lại điểm đến!" : "");
            request.setAttribute("distance_err", checkError.contains("Distance") || checkError.contains("distance") ? "Hãy kiểm tra lại khoảng cách!" : "");

        } catch (Exception e) {
            checkError = "System error occurred: " + e.getMessage();
            request.setAttribute("checkError", checkError);
            e.printStackTrace();
        }

// về đây để tiêp tục load location lên select admin
        return handleRouteViewing(request, response);
    }

    private String handleRouteUpdating(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
