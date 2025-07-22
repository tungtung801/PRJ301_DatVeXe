package controller;

import dao.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import model.UserDTO;
import utils.AuthUtils;
import utils.Email;

/**
 *
 * @author Tung Tung
 */
@WebServlet(name = "UserController", urlPatterns = {"/UserController"})
public class UserController extends HttpServlet {

    private static final String LOGIN_URL = "login.jsp";
    private static final String WELCOME_URL = "welcome.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String url = LOGIN_URL;
        try {
            String getAction = request.getParameter("action");
            if ("login".equals(getAction)) {
                url = handleLogin(request, response);
            } else if ("logout".equals(getAction)) {
                url = handleLogout(request, response);
            } else if ("register".equals(getAction)) {
                url = handleRegister(request, response);
            } else if ("updateProfile".equals(getAction)) {
                url = handleUpdateProfile(request, response);
            } else if ("changePassword".equals(getAction) || "forgetpass".equals(getAction)) {
                url = handleChangePassword(request, response);
            }
        } catch (Exception e) {
            System.out.println("Invalid action: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (url != null) {
                request.getRequestDispatcher(url).forward(request, response);
            }
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

    private String handleLogin(HttpServletRequest request, HttpServletResponse response) {
        String url = LOGIN_URL;
        HttpSession session = request.getSession();

        String phone = request.getParameter("uPN");
        String password = request.getParameter("uPW");

        UserDAO targetUser = new UserDAO();
        if (targetUser.login(phone, password)) {
            url = "Home.jsp";
            // lấy user ra để hiên thị trong welcome.jsp
            UserDTO user = targetUser.getUserByPhone(phone);
            session.setAttribute("user", user);
        } else {
            url = LOGIN_URL;
            request.setAttribute("cantLogin", "Số điện thoại hoặc Mật khẩu không hợp lệ !");
        }
        return url;
    }

    private String handleLogout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false); // false để không tạo mới nếu không có
        if (session != null) {
            session.invalidate();
        }
        response.sendRedirect("Home.jsp"); // <-- chuyển về trang home.jsp
        return null; // Vì đã redirect rồi
    }

    private String handleRegister(HttpServletRequest request, HttpServletResponse response) {
        String name = request.getParameter("uNA");
        String phone = request.getParameter("uPN");
        String password = request.getParameter("uPW");
        String email = request.getParameter("uEM");

        String message = null;
        String name_err = null;
        String phone_err = null;
        String password_err = null;
        String email_err = null;

        // Better email pattern
        String namePattern = "^[\\p{L} .'-]+$";  // Chỉ chữ và một số dấu
        String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        String phonePattern = "^0\\d{9}$";

        UserDAO udao = new UserDAO();

        // Validation
        if (name == null || name.trim().isEmpty() || !name.matches(namePattern)) {
            name_err = "Tên không chứa ký tự đặc biệt hay số!";
        }
        if (phone == null || phone.trim().isEmpty() || !phone.matches(phonePattern) || udao.isUserExist(phone)) {
            phone_err = "Nhập lại số điện thoại khác có đủ 10 con số !";
        }
        if (password == null || password.trim().isEmpty()) {
            password_err = "Vui lòng nhập mật khẩu !";
        }
        if (email == null || email.trim().isEmpty() || !email.matches(emailPattern)) {
            email_err = "Vui lòng nhập email hợp lệ có chứa \"@\"";
        }

        // Only proceed if NO validation errors
        if (name_err == null && phone_err == null && password_err == null && email_err == null) {
            Email em = new Email();
            String id = udao.autoGenerateUserId();
            UserDTO newUser = new UserDTO(id, name, email, password, "user", phone);

            if (udao.create(newUser)) {
                message = "Đăng ký thành công, hãy đăng nhập !";
                String imagePath = "D:\\0.FU\\SU25\\PRJ301_SU25_DatVeXe\\Core\\web\\assets\\images\\tunglong_logo.png";
                String noficreate = em.createRegistrationSuccessTemplate(newUser.getFullname(), newUser.getEmail(), newUser.getPhone(), "");
                em.sendEmailWithCustomTemplate(newUser.getEmail(),
                        "🎉 Chào mừng bạn đến với Tùng Long Bus Lines!",
                        noficreate,
                        imagePath);
            } else {
                message = "Đăng ký thất bại, vui lòng kiểm tra lại !";
            }
            request.setAttribute("user", newUser);
        }

        // Set attributes
        request.setAttribute("phone_err", phone_err);
        request.setAttribute("name_err", name_err);
        request.setAttribute("password_err", password_err);
        request.setAttribute("email_err", email_err); // Fixed: was "email"
        request.setAttribute("message", message);

        return "register.jsp";
    }

    private String handleUpdateProfile(HttpServletRequest request, HttpServletResponse response) {
        String UserId = request.getParameter("");
        UserDAO udao = new UserDAO();
        if (AuthUtils.isUser(request)) {
            UserDTO user = udao.getUserByID(UserId);
            if (user != null) {
                request.setAttribute("user", user);
                request.setAttribute("isUpdate", true);

            }
        }
        return "register.jsp";
    }

    static final String FORGETPASS_PAGE = "forgetpassword.jsp";

    private String handleChangePassword(HttpServletRequest request, HttpServletResponse response) {
        String url = FORGETPASS_PAGE;
        HttpSession session = request.getSession();
        String userPhone = request.getParameter("phone");
        String email = request.getParameter("strEmail");
        String pass = request.getParameter("newpass");
        String confirmpass = request.getParameter("confirmnewpass");
        String check_error = "";
        String pass_error = "";
        String success = "Đã cập nhật mật khẩu. Quý khách vui lòng đăng nhập lại!";
        Email em = new Email();
        String imagePath = "D:\\0.FU\\SU25\\PRJ301_SU25_DatVeXe\\Core\\web\\assets\\images\\tunglong_logo.png";
        try {
            UserDAO userDAO = new UserDAO();

            if (userPhone != null && email != null && pass == null && confirmpass == null) {
                if (userDAO.forgetpassword(userPhone, email)) {
                    UserDTO user = userDAO.getUserByPhone(userPhone);
                    session.setAttribute("user", user);
                    request.setAttribute("user", user);

                    url = "forgetpassword.jsp";
                } else {
                    request.setAttribute("message", "Số điện thoại hoặc Email không đúng!");
                    url = "forgetpassword.jsp";
                }
            } else if (pass != null && confirmpass != null) {
                UserDTO user = (UserDTO) session.getAttribute("user");
                if (user != null) {
                    if (!pass.equals(confirmpass)) {
                        pass_error += "Mật khẩu mới không khớp nhau!";
                        request.setAttribute("confirm_error", pass_error);
                        request.setAttribute("user", user);
                        url = "forgetpassword.jsp";
                    } else {
                        if (!userDAO.updatePassword(user.getPhone(), pass)) {
                            check_error += "Cập nhật mật khẩu thất bại!";
                            request.setAttribute("check_error", check_error);
                            request.setAttribute("user", user);
                            url = "forgetpassword.jsp";
                        } else {
                            request.setAttribute("success", success);
                            session.setAttribute("user", user);
                            LocalDateTime now = LocalDateTime.now();
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                            String formattedTime = now.format(formatter);
                            //public static String createPasswordChangeSuccessTemplate(String customerName, String newPassword, 
                            //   String changeTime, String phoneNumber)
                            String nofiforgetpass = em.createPasswordChangeSuccessTemplate(user.getFullname(), user.getPassword(), formattedTime, user.getPhone());
                            em.sendEmailWithCustomTemplate(user.getEmail().trim(),
                                    "Cập nhật mật khẩu thành công!",
                                    nofiforgetpass,
                                    imagePath);
                            url = "forgetpassword.jsp";
                        }
                    }
                } else {
                    request.setAttribute("message", "Đã hết phiên làm việc, vui lòng xác minh lại thông tin!");
                    url = "forgetpassword.jsp";
                }
            } else {
                // Invalid request
                url = "forgetpassword.jsp";
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "Có lỗi xảy ra, hãy thử lại!");
            url = "forgetpassword.jsp";
        }

        return url;
    }

}
