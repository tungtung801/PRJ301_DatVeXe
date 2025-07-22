/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.ScheduleDAO;
import dao.ScheduleSeatDAO;
import dao.SeatTemplateDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.ScheduleDTO;
import model.ScheduleSeatDTO;
import model.SeatTemplateDTO;

/**
 *
 * @author Tung Nguyen
 */
@WebServlet(name = "ScheduleSeatController", urlPatterns = {"/ScheduleSeatController"})
public class ScheduleSeatController extends HttpServlet {

    private static final String SCHEDULE_SEAT = "seatSelection.jsp";
    ScheduleSeatDAO SSdao = new ScheduleSeatDAO();
    ScheduleDAO sdao = new ScheduleDAO();
    SeatTemplateDAO stdao = new SeatTemplateDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String url = SCHEDULE_SEAT;
        try {
            String getAction = request.getParameter("action");
            if ("viewScheduleDetail".equals(getAction)) {
                url = handleScheduleDetail(request, response);
            }
        } catch (Exception e) {
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response); // Thêm dòng này
    }

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
    private String handleScheduleDetail(HttpServletRequest request, HttpServletResponse response) {
        String url = SCHEDULE_SEAT;

        String scheduleId = request.getParameter("scheduleId");
        String scheduleId_err = "";
        int scheduleID_value = -1;

        if (scheduleId == null || scheduleId.trim().isEmpty()) {
            scheduleId_err = "Không có ID lịch trình!";
        } else {
            try {
                scheduleID_value = Integer.parseInt(scheduleId);
                ScheduleDTO schedule = sdao.ScheduleById(scheduleID_value);

                if (schedule != null) {
                    request.setAttribute("scheduleID", schedule.getScheduleID());
                    request.setAttribute("busID", schedule.getBusID());
                    request.setAttribute("busNumber", schedule.getBusNumber());
                    request.setAttribute("busName", schedule.getBusName());
                    request.setAttribute("busTypeName", schedule.getBusTypeName());
                    request.setAttribute("departure", schedule.getDeparture());
                    request.setAttribute("destination", schedule.getDestination());
                    request.setAttribute("departureTime", schedule.getDepartureTime());
                    request.setAttribute("arrivalTime", schedule.getArrivalTime());
                    request.setAttribute("price", schedule.getPrice());
                    request.setAttribute("driverName", schedule.getDriverName());
                    request.setAttribute("attendantName", schedule.getAttendantName());
                    request.setAttribute("availableSeats", schedule.getAvailableSeats());
                    request.setAttribute("totalSeats", schedule.getTotalSeats());

                    // lấy sơ đồ ghế
                    List<ScheduleSeatDTO> seats = SSdao.getSeatsByScheduleID(scheduleID_value);
                    request.setAttribute("seatList", seats);
                }
            } catch (NumberFormatException e) {
                System.out.println("scheduleID miss match format: " + e.getMessage());
                e.printStackTrace();
            }
        }
        
        request.setAttribute("scheduleID", scheduleId);
        return url;
    }

    private List<SeatTemplateDTO> getSeatByBusTypeID(HttpServletRequest request, HttpServletResponse response, int busTypeID) {
        String url = SCHEDULE_SEAT;
        List<SeatTemplateDTO> seatTemplateList = stdao.getSeatsByBusType(busTypeID);
        request.setAttribute("seatTemplateList", seatTemplateList);

        return seatTemplateList;
    }
}
