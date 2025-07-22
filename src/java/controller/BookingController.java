/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.BookingDAO;
import dao.ScheduleDAO;
import dao.ScheduleSeatDAO;
import dao.TicketDAO;
import dao.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import model.BookingDTO;
import model.ScheduleDTO;
import model.TicketDTO;
import model.UserDTO;
import utils.AuthUtils;
import utils.DBUtils;
import utils.Email;
import static utils.Email.sendEmailWithCustomTemplate;
import utils.TicketCodeGenerator;

/**
 *
 * @author Tung Nguyen
 */
@WebServlet(name = "BookingController", urlPatterns = {"/BookingController"})
public class BookingController extends HttpServlet {

    private static final String LOGIN_PAGE = "/login.jsp";
    private static final String WELCOME_PAGE = "/Home.jsp";
    private static final String SEAT_PAGE = "/seatSelection.jsp";
    private static final String BOOKING_PAGE = "/bookingConfirm.jsp";
    private static final String B_SUCCESS_PAGE = "/bookingSuccess.jsp";
    BookingDAO bookDAO = new BookingDAO();
    ScheduleDAO sdao = new ScheduleDAO();
    ScheduleSeatDAO SSdao = new ScheduleSeatDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = LOGIN_PAGE;
        try {
            String getAction = request.getParameter("action");
            if ("createBooking".equals(getAction)) {
                url = handleBookingCreate(request, response);
            } else if ("confirmPayment".equals(getAction)) {
                url = handlePaymentConfirm(request, response);
            }
        } catch (Exception e) {
            System.out.println("Hành động không hợp lệ: " + e.getMessage());
            e.printStackTrace();;
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

    private String handleBookingCreate(HttpServletRequest request, HttpServletResponse response) {
        String url = BOOKING_PAGE;
        int scheduleId = Integer.parseInt(request.getParameter("scheduleId"));
        if (!AuthUtils.isLoggedIn(request)) {
            url = LOGIN_PAGE;
            return url;
        }

        HttpSession session = request.getSession();

        String userId = ((UserDTO) session.getAttribute("user")).getUser_id();
        double price = Double.parseDouble(request.getParameter("price"));

        String[] seatId_arr = request.getParameter("selectedSeats").split(",");
        double totalAmount = seatId_arr.length * price;

        BookingDTO newBooking = new BookingDTO(userId, scheduleId, totalAmount);

        try {
            // Truyền thêm mảng seatId_arr vào phương thức
            int bookingId = bookDAO.createBookingNReturnId(newBooking, seatId_arr);
            if (bookingId != -1) {
                // set để hiển thị ra jsp
                request.setAttribute("bookingId", bookingId);
                request.setAttribute("selectedSeats", seatId_arr);
                request.setAttribute("totalAmount", totalAmount);
                request.setAttribute("scheduleId", scheduleId);

                // Lấy thông tin chuyến đi từ database để hiển thị jsp
                ScheduleDTO schedule = sdao.ScheduleById(scheduleId);

                if (schedule != null) {
                    // Truyền thông tin chuyến đi qua jsp
                    request.setAttribute("departure", schedule.getDeparture());
                    request.setAttribute("destination", schedule.getDestination());
                    request.setAttribute("departureTime", schedule.getDepartureTime());
                    request.setAttribute("arrivalTime", schedule.getArrivalTime());
                    request.setAttribute("busName", schedule.getBusName());
                    request.setAttribute("busNumber", schedule.getBusNumber());
                    request.setAttribute("busTypeName", schedule.getBusTypeName());
                    request.setAttribute("price", price);
                    request.setAttribute("scheduleId", schedule.getScheduleID());
                }
                // Thêm vào handleBookingCreate method

                List<String> seatNumbers = SSdao.getSeatNumbersByIds(seatId_arr);

                request.setAttribute("seatNumbers", seatNumbers);
                session.setAttribute("bookingInfo", newBooking);
            }
        } catch (Exception e) {
            request.setAttribute("error", "Đã xảy ra lỗi khi đặt vé: " + e.getMessage());
            e.printStackTrace();
        }
        return url;
    }

    private String handlePaymentConfirm(HttpServletRequest request, HttpServletResponse response) {
        String url = B_SUCCESS_PAGE;
        Email em = new Email();
        if (!AuthUtils.isLoggedIn(request)) {
            url = LOGIN_PAGE;
        } else {
            try {
                int bookingId = Integer.parseInt(request.getParameter("bookingId"));
                int scheduleId = Integer.parseInt(request.getParameter("scheduleId"));
                // gọi dao này sẽ thực hiện 2 việc
                // update status booking + update status ghế
                bookDAO.confirmPayment(bookingId);

                // Lấy thông tin cập nhật số ghế
                int availableSeats = SSdao.getAvailableSeats(scheduleId);
                int totalSeats = SSdao.getTotalSeats(scheduleId);

                // cập nhật để hiển thị ra jsp lúc user tus schedule                
                request.setAttribute("availableSeats", availableSeats);
                request.setAttribute("totalSeats", totalSeats);
                request.setAttribute("payMSG", "Thanh toán thành công!");

                // phần này cho ticket.
                HttpSession session = request.getSession();
                UserDTO passenger = (UserDTO) session.getAttribute("user");
                String passengerName = passenger.getFullname();
                String passengerPhone = passenger.getPhone();
                request.setAttribute("bookingId", bookingId);
                request.setAttribute("scheduleId", scheduleId);

                String ticketCode = TicketCodeGenerator.generateCode();
                TicketDTO newTicket = new TicketDTO(bookingId, scheduleId, passengerName, passengerPhone, ticketCode, "Hợp lệ");
                TicketDAO tdao = new TicketDAO();
                boolean canCreateTicket = tdao.createTicket(newTicket);
                if (canCreateTicket) {
                    request.setAttribute("ticketCode", ticketCode);
//                    String bookingId, String scheduleId, String passengerName, String passengerPhone, String ticketCode, 
//                    
                    String success = em.createBookingConfirmationTemplate(newTicket.getBookingID(), newTicket.getScheduleSeatID(), newTicket.getPassengerName(), newTicket.getPassengerPhone(), newTicket.getTicketCode());
                    String imagePath = "D:\\0.FU\\SU25\\PRJ301_SU25_DatVeXe\\Core\\web\\assets\\images\\tunglong_logo.png";
                    sendEmailWithCustomTemplate(passenger.getEmail(),
                            "Xác nhận đặt vé thành công",
                            success,
                            imagePath);
                } else {
                    System.out.println("Có lỗi xảy ra khi tạo ticket trong hệ thống!");
                }

            } catch (NumberFormatException e) {
                System.out.println("ID booking sai định dạng! " + e.getMessage());
                e.printStackTrace();
            }
        }
        return url;
    }

}
