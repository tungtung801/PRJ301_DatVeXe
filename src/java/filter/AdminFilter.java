package filter; // Hoặc package bạn muốn đặt cho filter

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.UserDTO; // Import UserDTO của bạn

import java.io.IOException;

/**
 * Servlet Filter để kiểm tra quyền Admin trước khi truy cập các trang hoặc chức
 * năng quản lý.
 */
@WebFilter(filterName = "AdminFilter", urlPatterns = {"/MainController"}) // Apply filter cho MainController
public class AdminFilter implements Filter {
    
    private static final String ADMIN_ACTIONS_PREFIX = "admin"; // Tiền tố cho các action của admin

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Có thể thực hiện các khởi tạo ở đây nếu cần, nhưng thường thì không
    }
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false); // Lấy session hiện có, không tạo mới

        String action = httpRequest.getParameter("action"); // Lấy action từ request

        // Kiểm tra nếu action thuộc về nhóm admin (ví dụ: locationManage, routeManage, ... bla bla :))
        boolean isAdminAction = (action != null
                && ("locationManage".equals(action)
                || "routeManage".equals(action)
                || "busManage".equals(action)
                || "staffManage".equals(action))
                || "scheduleManage".equals(action))
                || "addSchedule".equals(action);

        // Nếu đây là một hành động admin, thì kiểm tra quyền
        if (isAdminAction) {
            if (session != null) {
                UserDTO loggedInUser = (UserDTO) session.getAttribute("user");
                if (loggedInUser != null && "admin".equals(loggedInUser.getRole())) {
                    // Người dùng đã đăng nhập và có quyền admin, cho phép đi tiếp
                    chain.doFilter(request, response);
                } else {
                    // Người dùng không có quyền admin
                    System.out.println("Truy cập bị từ chối: Người dùng không có quyền admin.");
                    // Chuyển hướng về trang báo lỗi hoặc trang đăng nhập
                    httpResponse.sendRedirect("login.jsp?error=permissionDenied");
                }
            } else {
                // Session không tồn tại (chưa đăng nhập)
                System.out.println("Truy cập bị từ chối: Chưa đăng nhập.");
                httpResponse.sendRedirect("login.jsp?error=notLoggedIn");
            }
        } else {
            // Không phải hành động admin, cho phép đi tiếp
            chain.doFilter(request, response);
        }
    }
    
    @Override
    public void destroy() {
    }
}
