package controller;

import dao.LocationDAO;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.ArrayList;
import model.LocationDTO;
import utils.AuthUtils;

@WebServlet(name = "LocationController", urlPatterns = {"/LocationController"})
public class LocationController extends HttpServlet {

    private static final String LOCATION_FORM_PAGE = "/admin/locationForm.jsp";
    private static final String LOGIN_PAGE = "/login.jsp";
    private static final String WELCOME_PAGE = "/Home.jsp";
    LocationDAO ldao = new LocationDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String getAction = request.getParameter("action");
        String url = LOCATION_FORM_PAGE;

        try {
            if (getAction == null || "locationManage".equals(getAction) || "showLocation".equals(getAction)) {
                url = handleLocationShowing(request, response);
            } else if ("addLocation".equals(getAction)) {
                url = handleLocationAdding(request, response);
            }
        } catch (Exception e) {
            System.out.println("Lỗi trong LocationController: " + e.getMessage());
            e.printStackTrace();
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    private String handleLocationShowing(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        String url = LOCATION_FORM_PAGE;
        if (!AuthUtils.isLoggedIn(request)) {
            url = LOGIN_PAGE;
        }
        if (!AuthUtils.isAdmin(request)) {
            url = WELCOME_PAGE;
        }
        List<LocationDTO> locationList = new ArrayList<>();
        try {
            locationList = ldao.getAllLocations();
            boolean isSuccess = locationList != null;
            if (isSuccess) {
                request.setAttribute("showMessage", "Danh sách địa điểm tải lên thành công!");
                request.setAttribute("lList", locationList);
            } else {
                request.setAttribute("showMessage", "Danh sách địa điểm tải lên thất bại!");
            }
        } catch (Exception e) {
            System.out.println("");
        }

        return url;
    }

    private String handleLocationAdding(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        String url = LOCATION_FORM_PAGE;
        if (!AuthUtils.isLoggedIn(request)) {
            url = LOGIN_PAGE;
        }
        if (!AuthUtils.isAdmin(request)) {
            url = WELCOME_PAGE;
        }
        String keyword = request.getParameter("keyword");
        String keyword_err = null;
        String message = null;
        String pattern = "^[\\p{L} .]+$"; // kí tự latin và tiếng Việt có dấu xài \\p

        if (keyword == null || keyword.trim().isEmpty() || !keyword.matches(pattern)) {
            keyword_err = "Tên địa điểm không được bỏ trống hoặc sai định dạng!";
        } else {
            try {
                if (ldao.getLocationByName(keyword) != null) {
                    keyword_err = "Địa điểm đã tồn tại!";
                }
            } catch (Exception e) {
                System.out.println("Error");
            }
        }
        LocationDTO newLocation = new LocationDTO(keyword);
        if (keyword_err != null) {
            message = "Thêm thất bại, vui lòng kiểm tra lại tên địa điểm!";
            request.setAttribute("keyword", keyword);
        } else {
            boolean addSuccess = ldao.addLocation(newLocation);

            if (addSuccess) {
                message = "Thêm địa điểm mới thành công!";
            } else {
                message = "Thêm địa điểm mới thất bại!";
                request.setAttribute("keyword", keyword);
            }
        }
        request.setAttribute("newLocation", newLocation);
        request.setAttribute("keyword_err", keyword_err);
        request.setAttribute("message", message);
        handleLocationShowing(request, response);
        return url;
    }

}
