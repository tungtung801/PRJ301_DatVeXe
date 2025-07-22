package utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import model.UserDTO;

/**
 *
 * @author Tung Tung
 */
public class AuthUtils {

    public static UserDTO getCurrentUser(HttpServletRequest request) {
        // get curent user activating in session 
        HttpSession session = request.getSession();
        UserDTO user = null;
        if (session != null) {
            return user = (UserDTO) session.getAttribute("user");
        }
        return null;
    }

    public static boolean isLoggedIn(HttpServletRequest request) {
        return getCurrentUser(request) != null;
    }

    public static boolean hasRole(HttpServletRequest request, String role) {
        return getCurrentUser(request).getRole().equals(role);
    }

    public static boolean isAdmin(HttpServletRequest request) {
        return hasRole(request, "admin");
    }

    public static boolean isNhanVien(HttpServletRequest request) {
        if (hasRole(request, "tx") || hasRole(request, "tv")) {
            return true;
        }
        return false;
    }

    public static boolean isUser(HttpServletRequest request) {
        return hasRole(request, "user");
    }

    public static String getAccessDenied(String action) {
        return "Permission denied!";
    }

    public static String getLoginURL() {
        String url = "";
        return url = "login.jsp";
    }

}
