package controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "MainController", urlPatterns = {"", "/MainController"})
public class MainController extends HttpServlet {
    
    private static final String WELCOME_PAGE = "Home.jsp";

    private boolean isUserAction(String action) {
        return "login".equals(action)
                || "logout".equals(action)
                || "register".equals(action)
                || "updateProfile".equals(action)
                || "changePassword".equals(action)
                || "forgetpass".equals(action);
    }

    // Không cần hàm isAdminAction nữa vì Filter sẽ xử lý việc đó
    // private boolean isAdminAction(String action) { ... }
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String action = request.getParameter("action");
        String url = WELCOME_PAGE;
        
        try {
            if (isUserAction(action)) {
                url = "/UserController";
            } else if ("locationManage".equals(action)) {
                url = "/LocationController"; // Forward trực tiếp
            } else if ("routeManage".equals(action)) {
                url = "/RouteController"; // Forward trực tiếp
            } else if ("busManage".equals(action)) {
                url = "/BusController"; // Forward trực tiếp
            } else if ("staffManage".equals(action)) {
                url = "/StaffController"; // Forward trực tiếp
            } else if ("scheduleManage".equals(action)) {
                url = "/ScheduleController"; // Forward trực tiếp
            }
        } catch (Exception e) {
            System.out.println("Đã xảy ra lỗi hệ thống: " + e.getMessage());
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
}
