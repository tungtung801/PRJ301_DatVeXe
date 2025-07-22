/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.BookingDAO;
import dao.ScheduleDAO;
import dao.ScheduleSeatDAO;
import dao.TicketDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import model.BookingDTO;
import model.ScheduleDTO;
import model.TicketDTO;

/**
 *
 * @author Tung Nguyen
 */
@WebServlet(name = "TicketController", urlPatterns = {"/TicketController"})
public class TicketController extends HttpServlet {

    private static final String TICKET_PAGE = "searchTicket.jsp";
    TicketDAO tdao = new TicketDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String url = TICKET_PAGE;
        try {
            String getAction = request.getParameter("action");
            if ("searchTicket".equals(getAction)) {
                url = handleTicketSearching(request, response);
            }
        } catch (Exception e) {
            System.out.println("Hành động không hợp lệ: " + e.getMessage());
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

    private String handleTicketSearching(HttpServletRequest request, HttpServletResponse response) {
        String url = TICKET_PAGE;
        try {
            String ticketCode = request.getParameter("ticketCode");
            String phoneNum = request.getParameter("phoneNum");

            if ((ticketCode == null || ticketCode.isEmpty()) && (phoneNum == null || phoneNum.isEmpty())) {
                request.setAttribute("inputErr", "Vui lòng nhập đủ các trường!");
                return url; // Dừng sớm nếu thiếu thông tin
            }

            TicketDTO ticket = tdao.getTicketByPhoneNCode(phoneNum, ticketCode);

            if (ticket != null) {
                int BookingId = ticket.getBookingID();

                if (BookingId != -1) {
                    BookingDAO bdao = new BookingDAO();
                    BookingDTO booking = bdao.getBookingById(BookingId);
                    request.setAttribute("booking", booking);

                    ScheduleSeatDAO SSdao = new ScheduleSeatDAO();
                    int scheduleId = SSdao.getScheduleIdByBookingID(BookingId);

                    ScheduleDAO sdao = new ScheduleDAO();
                    ScheduleDTO schedule = sdao.ScheduleById(scheduleId);

                    if (schedule != null) {
                        request.setAttribute("schedule", schedule);
                    }
                }
            } else {
                System.out.println("Lỗi khi tìm ticket với số điện thoại và code!");
                request.setAttribute("generalMsg", "Không tìm thấy vé với mã: " + ticketCode + ", vui lòng thử lại!");
            }

            request.setAttribute("ticketCode", ticketCode);
            request.setAttribute("phoneNum", phoneNum);

        } catch (Exception e) {
            System.out.println("Lỗi hệ thống: " + e.getMessage());
            e.printStackTrace();
        }

        return url;
    }

}
