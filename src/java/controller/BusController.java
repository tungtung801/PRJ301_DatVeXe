/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.BusDAO;
import dao.BusTypeDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.BusDTO;
import utils.AuthUtils;

/**
 *
 * @author Tung Nguyen
 */
@WebServlet(name = "BusController", urlPatterns = {"/BusController"})
public class BusController extends HttpServlet {

    private static final String BUS_FORM = "/admin/busForm.jsp";
    private static final String WELCOME_PAGE = "/Home.jsp";
    private static final String LOGIN_PAGE = "/login.jsp";
    BusDAO bdao = new BusDAO();
    BusTypeDAO btdao = new BusTypeDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String url = BUS_FORM;
        String getAction = request.getParameter("action");
        try {
            if (getAction == null || "showBus".equals(getAction) || "busManage".equals(getAction)) {
                url = handleBusShowing(request, response);
            } else if ("addBus".equals(getAction)) {
                url = handleBusAdding(request, response);
            } else if ("searchBus".equals(getAction)) {
                url = handleBusSearching(request, response);
            }
        } catch (Exception e) {
            System.out.println("Invalid action: " + e.getMessage());
            e.printStackTrace();
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

    private String handleBusShowing(HttpServletRequest request, HttpServletResponse response) {
        String url = BUS_FORM;
        if (!AuthUtils.isLoggedIn(request)) {
            url = LOGIN_PAGE;
        }
        if (!AuthUtils.isAdmin(request)) {
            url = WELCOME_PAGE;
        }
        List<BusDTO> busList = new ArrayList<>();
        try {
            busList = bdao.getAllBus();
            boolean isSuccess = busList != null;
            if (isSuccess) {
                request.setAttribute("showMessage", "Thông tin phương tiện tải lên hoàn tất!");
                request.setAttribute("busList", busList);
            } else {
                request.setAttribute("showMessage", "Thông tin phương tiện tải lên thất bại!");
            }
        } catch (Exception e) {
        }

        request.setAttribute("busTypeList", btdao.getAllBusTypes());
        return url;
    }

    private String handleBusAdding(HttpServletRequest request, HttpServletResponse response) {
        String url = BUS_FORM;
        Map<String, String> errors = new HashMap<>();
        String generalMessage = "";

        if (!AuthUtils.isLoggedIn(request)) {
            url = LOGIN_PAGE;
            return url;
        }
        if (!AuthUtils.isAdmin(request)) {
            url = WELCOME_PAGE;
            return url;
        }

        String busNumber = request.getParameter("BusNumber");
        String busName = request.getParameter("BusName");
        String busTypeIDStr = request.getParameter("BusTypeID");
        String description = request.getParameter("Description");
        String status = request.getParameter("Status");

        boolean hasError = false;

        if (busNumber == null || busNumber.trim().isEmpty()) {
            errors.put("busNumberError", "Biển số xe không được để trống.");
            hasError = true;
        } else {
            if (bdao.getBusByNumber(busNumber) != null) {
                errors.put("busNumberError", "Biển số xe đã được đăng kí với xe khác!");
                hasError = true;
            }
        }

        if (busName == null || busName.trim().isEmpty()) {
            errors.put("busNameError", "Tên phương tiện không được để trống.");
            hasError = true;
        }

        int busTypeID = -1;
        if (busTypeIDStr == null || busTypeIDStr.trim().isEmpty()) {
            errors.put("busTypeIDError", "Loại xe không được để trống.");
            hasError = true;
        } else {
            try {
                busTypeID = Integer.parseInt(busTypeIDStr);

                boolean isBusTypeIDAllowed = false;

                if ("Thaco Mobihome 120SL 2025".equals(busName)
                        || "KIA Granbird Silkroad".equals(busName)
                        || "Hyundai Universe EX12.5".equals(busName)) {

                    int[] allowedBusTypeIDsArray = {1, 2};

                    for (int allowedID : allowedBusTypeIDsArray) {
                        if (allowedID == busTypeID) {
                            isBusTypeIDAllowed = true;
                            break;
                        }
                    }

                    if (!isBusTypeIDAllowed) { // Nếu sau khi duyệt hết mà vẫn là false
                        errors.put("busTypeIDError", "Loại ghế/phòng không phù hợp với loại phương tiện đã chọn.");
                        hasError = true;
                    }

                } else if ("KimLong99 G24".equals(busName)) {
                    int[] allowedBusTypeIDsArray = {2};

                    for (int allowedID : allowedBusTypeIDsArray) {
                        if (allowedID == busTypeID) {
                            isBusTypeIDAllowed = true;
                            break;
                        }
                    }

                    if (!isBusTypeIDAllowed) {
                        errors.put("busTypeIDError", "Loại ghế/phòng không phù hợp với loại phương tiện đã chọn.");
                        hasError = true;
                    }
                }

            } catch (NumberFormatException e) {
                errors.put("busTypeIDError", "ID loại xe không hợp lệ (phải là số).");
                hasError = true;
            }
        }

        // LUÔN TẢI DANH SÁCH BusTypeID trước khi làm chuyện khác, QUAN TRỌNG =====
        request.setAttribute("busTypeList", btdao.getAllBusTypes());

        if (hasError) {
            request.setAttribute("errors", errors);
            generalMessage = "Có lỗi xảy ra, vui lòng kiểm tra lại thông tin nhập!";
            request.setAttribute("generalMessage", generalMessage);
            url = handleBusShowing(request, response);
        } else {
            try {
                BusDTO newBus = new BusDTO(busNumber, busName, busTypeID, description, status);

                boolean isAdded = bdao.addBus(newBus);

                if (isAdded) {
                    generalMessage = "Thêm xe mới thành công!";
                    url = handleBusShowing(request, response);
                } else {
                    generalMessage = "Thêm xe thất bại. Vui lòng thử lại.";
                    url = handleBusShowing(request, response);
                }
            } catch (Exception e) {
                generalMessage = "Đã xảy ra lỗi hệ thống khi thêm xe: " + e.getMessage();
                System.err.println("Error adding bus: " + e.getMessage());
            } finally {
                request.setAttribute("generalMessage", generalMessage);
            }
        }

        return url;
    }

    private String handleBusSearching(HttpServletRequest request, HttpServletResponse response) {
        String url = BUS_FORM;

        if (!AuthUtils.isLoggedIn(request)) {
            url = LOGIN_PAGE;
        }

        if (!AuthUtils.isAdmin(request)) {
            url = WELCOME_PAGE;
        }

        String busName = request.getParameter("BusName");
        String status = request.getParameter("Status");
        
        boolean isBusNameEmpty = (busName == null || busName.trim().isEmpty());
        boolean isStatusEmpty = (status == null || status.trim().isEmpty());

        List<BusDTO> busSearchList = new ArrayList<>();

        try {
            if (isBusNameEmpty && isStatusEmpty) {
                request.setAttribute("searchMsg", "Vui lòng nhập tối thiểu 1 trường!");
            } else if (!isBusNameEmpty && isStatusEmpty) {
                busSearchList = bdao.getBusByName(busName);
            } else if (isBusNameEmpty && !isStatusEmpty) {
                busSearchList = bdao.getBusByStatus(status);
            } else {
                busSearchList = bdao.getBusByNameNStatus(busName, status);
            }
            
            if(busSearchList != null){
                request.setAttribute("busList", busSearchList);
            }
            // Load lại data sau khi search cho hàm add
            request.setAttribute("busTypeList", btdao.getAllBusTypes()); 
        } catch (Exception e) {
            System.out.println("Đã xảy ra lỗi hệ thống: "+e.getMessage());
            e.printStackTrace();
        }

        request.setAttribute("BusName", busName);
        request.setAttribute("Status", status);
        return url;
    }
}
