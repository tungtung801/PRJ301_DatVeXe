/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.StaffDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import model.StaffDTO;
import utils.AuthUtils;

/**
 *
 * @author Tung Nguyen
 */
@WebServlet(name = "StaffController", urlPatterns = {"/StaffController"})
public class StaffController extends HttpServlet {

    private static final String WELCOME_PAGE = "/Home.jsp";
    private static final String LOGIN_PAGE = "/login.jsp";
    private static final String STAFF_PAGE = "/admin/staffForm.jsp";
    StaffDAO edao = new StaffDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String url = STAFF_PAGE;
        try {
            String getAction = request.getParameter("action");
            if ("staffManage".equals(getAction) || "showStaff".equals(getAction)) {
                url = handleStaffShowing(request, response);
            } else if ("addStaff".equals(getAction)) {
                url = handleStaffAdding(request, response);
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

    private String handleStaffShowing(HttpServletRequest request, HttpServletResponse response) {
        String url = STAFF_PAGE;

        if (!AuthUtils.isLoggedIn(request)) {
            url = LOGIN_PAGE;
        }

        if (!AuthUtils.isAdmin(request)) {
            url = WELCOME_PAGE;
        }

        List<StaffDTO> empList = new ArrayList<>();
        StaffDTO employee = null;

        try {
            empList = edao.getAllEmployee();
            boolean isSuccess = empList != null;

            if (isSuccess) {
                request.setAttribute("message", "Thông tin nhân sự tải lên hoàn tất!");
                request.setAttribute("eList", empList);
            } else {
                request.setAttribute("message", "Thông tin nhân sự tải lên thất bại");
            }
        } catch (Exception e) {
        }
        return url;
    }

    private String handleStaffAdding(HttpServletRequest request, HttpServletResponse response) {
        String url = STAFF_PAGE;

        if (!AuthUtils.isLoggedIn(request)) {
            url = LOGIN_PAGE;
        }

        if (!AuthUtils.isAdmin(request)) {
            url = WELCOME_PAGE;
        }

        String role = request.getParameter("emp_role");
        String name = request.getParameter("emp_name");
        String status = request.getParameter("status");

        StaffDTO newStaff = null;
        if ((role == null || role.trim().isEmpty())
                && (name == null || name.trim().isEmpty())
                && (status == null || status.trim().isEmpty())) {
            request.setAttribute("inputError", "Vui lòng nhập đầy đủ các trường!");
        } else {
            newStaff = new StaffDTO(name, role, status);

            try {
                boolean isCreated = edao.create(newStaff);
                if (isCreated) {
                    request.setAttribute("addMsg", "Tuyển dụng " + role + " mới thành công!");
                } else {
                    request.setAttribute("addMsg", "Tuyển dụng " + role + " mới thất bại!");
                }
            } catch (Exception e) {
                System.out.println("Error: "+e.getMessage());
                e.printStackTrace();
            }
        }
        handleStaffShowing(request, response);
        return url;
    }

}
