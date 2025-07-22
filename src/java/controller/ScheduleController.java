/*/////////////// GỐC
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import DTO.RouteDTO;
import dao.BusDAO;
import dao.LocationDAO;
import dao.RouteDAO;
import dao.ScheduleDAO;
import dao.StaffDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.BusDTO;
import model.LocationDTO;
import model.ScheduleDTO;
import model.StaffDTO;
import model.UserDTO;
import utils.AuthUtils;
import utils.DBUtils;

/**
 *
 * @author Tung Nguyen
 */
@WebServlet(name = "ScheduleController", urlPatterns = {"/ScheduleController"})
public class ScheduleController extends HttpServlet {

    private static final String WELCOME_PAGE = "/Home.jsp";
    private static final String LOGIN_PAGE = "/login.jsp";
    private static final String SCHEDULE_PAGE = "/searchTrip.jsp";
    private static final String SCHEDULE_ADMIN = "/admin/scheduleForm.jsp";

    ScheduleDAO sdao = new ScheduleDAO();
    LocationDAO ldao = new LocationDAO();
    BusDAO bdao = new BusDAO();
    RouteDAO rdao = new RouteDAO();
    StaffDAO edao = new StaffDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = SCHEDULE_PAGE;

        try {
            System.out.println("=== DEBUG: processRequest started ===");

            getAllLocation(request, response);
            System.out.println("getAllLocation completed");

            String getAction = request.getParameter("action");
            System.out.println("Action parameter: " + getAction);

            if ("scheduleManage".equals(getAction)) {
                url = handleScheduleShowingAdmin(request, response);
            } else if ("searchSchedule".equals(getAction)) {
                url = handleScheduleSearching(request, response);
            } else if ("addSchedule".equals(getAction)) {
                url = handleScheduleAdding(request, response);
            } else if ("updateSchedule".equals(getAction)) {
                url = handleUpdateSchedule(request, response);
            } else if ("prepareUpdateSchedule".equals(getAction)) {
                url = prepareUpdateSchedule(request, response);
            } else if ("searchNV".equals(getAction)) {
                url = handleSearchForEmploy(request, response);
            } else if ("gotosearchNV".equals(getAction)) {
                url = gotosearchNV(request, response);
            } else {
                url = handleDefaultScheduleDisplay(request, response);
            }

        } catch (Exception e) {
            System.out.println("ERROR in processRequest: " + e.getMessage());
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

    /// NÀY CHO NHÂN VIÊN (CẢ TÀI XẾ + TIÉP VIÊN)
    private String handleSearchForEmploy(HttpServletRequest request, HttpServletResponse response) {
        String url = "searchNV.jsp";
        ScheduleDAO sdao = new ScheduleDAO();
        String message = "";

        String name = request.getParameter("employeeName");
        List<ScheduleDTO> schelist = new ArrayList<>();
//        if(!AuthUtils.isNhanVien(request) || !AuthUtils.isAdmin(request)){
//            return "Home.jsp";
//        }
        if (name.isEmpty() || name == null) {
            message += "Enter Name to search.";
            request.setAttribute("message", message);
            return url;
        }
        try {
            schelist = sdao.getEmployeeSchedule(name);
            if (schelist.isEmpty() || schelist == null) {
                message += "Employee does not have schedule yet.";
            } else {

            }
        } catch (Exception e) {

        }
        request.setAttribute("name", name);
        request.setAttribute("schelist", schelist);
        request.setAttribute("message", message);
        return "searchNV.jsp";
    }

    private String gotosearchNV(HttpServletRequest request, HttpServletResponse response) {
        UserDTO currentUser = AuthUtils.getCurrentUser(request);
        if (currentUser == null) {
            return "login.jsp";
        }

        if (!(AuthUtils.isNhanVien(request) || AuthUtils.isAdmin(request))) {
            return "Home.jsp";
        }

        return "searchNV.jsp";
    }

    /// NÀY CỦA ADMIN =====================================================================================///
    private String handleScheduleShowingAdmin(HttpServletRequest request, HttpServletResponse response) {
        String url = SCHEDULE_ADMIN;
        if (!AuthUtils.isLoggedIn(request)) {
            url = LOGIN_PAGE;
        }
        if (!AuthUtils.isAdmin(request)) {
            url = WELCOME_PAGE;
        }

        // cập nhật trạng thái lịch trình sau khi quá thời gian đã được đặt trước đó
        sdao.autoUpdateScheduleStatus();
        List<ScheduleDTO> scheduleDisplayList = null;
        try {
            scheduleDisplayList = sdao.displayAllSchedules();
            if (scheduleDisplayList != null) {
                request.setAttribute("displayMessage", "Tải lên thông tin lịch trình thành công!");
                request.setAttribute("scheduleDisplayList", scheduleDisplayList);
            } else {
                request.setAttribute("displayMessage", "Tải lên thông tin lịch trình thất bại!");
            }
        } catch (Exception e) {
            System.out.println("Error in show(admin): " + e.getMessage());
            e.printStackTrace();
        }

        // gọi máy này dùng cho hàm add bên dưới
        getAllAvailableBus(request, response);
        getAllRoute(request, response);
        getAllLocation(request, response);
        getAvaiStaffByRole(request, response, "Tài xế");
        getAvaiStaffByRole(request, response, "Tiếp viên");
        return url;
    }

    private String handleScheduleAdding(HttpServletRequest request, HttpServletResponse response) {
        String url = SCHEDULE_ADMIN;
        if (!AuthUtils.isLoggedIn(request)) {
            url = LOGIN_PAGE;
        }
        if (!AuthUtils.isAdmin(request)) {
            url = WELCOME_PAGE;
        }

        int routeID = Integer.parseInt(request.getParameter("routeID"));
        int busID = Integer.parseInt(request.getParameter("busID"));
        int driverID = Integer.parseInt(request.getParameter("driverID"));
        int attendantID = Integer.parseInt(request.getParameter("attendantID"));
//        double price = Double.parseDouble(request.getParameter("price"));

        String price = request.getParameter("price");
        double price_fmt = 0;
        if (price != null || !price.trim().isEmpty()) {
            if (price.trim().length() == 3) {
                price += "000";
            }
            try {
                price_fmt = Double.parseDouble(price);
            } catch (NumberFormatException e) {
                System.out.println("Giá tiền không đúng định dạng!");
                e.printStackTrace();
            }
        } else {
            request.setAttribute("price", price);
        }

        Date departureDate = null;
        Date arrivalDate = null;

        try {
            // chuyeenr đổi ngày tháng từ form;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
            departureDate = sdf.parse(request.getParameter("departureTime"));
            arrivalDate = sdf.parse(request.getParameter("arrivalTime"));
        } catch (ParseException e) {
            System.out.println("Parsing error: " + e.getMessage());
            request.setAttribute("error", "Định dạng thời gian không hợp lệ!");
        }

        ScheduleDTO newSchedule = new ScheduleDTO(routeID, busID, new Timestamp(departureDate.getTime()), new Timestamp(arrivalDate.getTime()), driverID, attendantID, price_fmt, "Lên lịch");

        try {
            boolean success = sdao.create(newSchedule);
            if (success) {
                request.setAttribute("addMsg", "Tạo lịch trình mới thành công!");
            } else {
                request.setAttribute("addMsg", "Không thể tạo lịch trình mới!");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        handleScheduleShowingAdmin(request, response); // gọi lại method này để vừa hiển thị khi cập nhật mà còn có data để chọn
        return url;
    }

    // Add this new method to handle "prepareUpdateSchedule" action
    private String prepareUpdateSchedule(HttpServletRequest request, HttpServletResponse response) {
        String url = SCHEDULE_ADMIN;
        if (!AuthUtils.isLoggedIn(request)) {
            return LOGIN_PAGE;
        }
        if (!AuthUtils.isAdmin(request)) {
            return WELCOME_PAGE;
        }

        try {
            int scheduleID = Integer.parseInt(request.getParameter("scheduleID"));
            ScheduleDTO scheduleToEdit = sdao.ScheduleById(scheduleID);

            if (scheduleToEdit != null) {
                request.setAttribute("isEdit", true);
                request.setAttribute("scheduleToEdit", scheduleToEdit);

                // Lấy danh sách xe sẵn sàng
                List<BusDTO> availableBuses = getAllAvailableBus(request, response);

                // Nếu lịch trình đang gặp sự cố, đảm bảo loại bỏ xe hiện tại khỏi danh sách
                if ("Sự cố".equals(scheduleToEdit.getStatus())) {
                    // Tạo danh sách mới không chứa xe đang gặp sự cố
                    List<BusDTO> filteredBuses = new ArrayList<>();
                    for (BusDTO bus : availableBuses) {
                        if (bus.getBusID() != scheduleToEdit.getBusID()) {
                            filteredBuses.add(bus);
                        }
                    }
                    // Cập nhật lại danh sách xe sẵn sàng (đã loại bỏ xe đang gặp sự cố)
                    request.setAttribute("avaiBusList", filteredBuses);

                } else {
                    // Nếu lịch trình không gặp sự cố, sử dụng danh sách gốc
                    request.setAttribute("avaiBusList", availableBuses);
                }

                // Lấy dữ liệu khác cần thiết
                getAllRoute(request, response);
                getAvaiStaffByRole(request, response, "Tài xế");
                getAvaiStaffByRole(request, response, "Tiếp viên");

                // Hiển thị lại danh sách lịch trình
                handleScheduleShowingAdmin(request, response);
            } else {
                request.setAttribute("displayMessage", "Không tìm thấy lịch trình với ID: " + scheduleID);
            }
        } catch (Exception e) {
            System.out.println("Error in prepareUpdateSchedule: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("displayMessage", "Lỗi khi chuẩn bị cập nhật: " + e.getMessage());
        }

        return url;
    }

    private String handleUpdateSchedule(HttpServletRequest request, HttpServletResponse response) {
        String url = SCHEDULE_ADMIN;

        if (!AuthUtils.isLoggedIn(request)) {
            return LOGIN_PAGE;
        }
        if (!AuthUtils.isAdmin(request)) {
            return WELCOME_PAGE;
        }

        try {
            int scheduleID = Integer.parseInt(request.getParameter("scheduleID"));
            int busID = Integer.parseInt(request.getParameter("busID"));
            int oldBusID = Integer.parseInt(request.getParameter("oldB và usID"));
            String status = request.getParameter("status");

            System.out.println("Updating schedule ID: " + scheduleID);
            System.out.println("New bus ID: " + busID);
            System.out.println("Old bus ID: " + oldBusID);
            System.out.println("New status: " + status);

            // Lấy lịch trình hiện tại để giữ lại các trường không thay đổi
            ScheduleDTO currentSchedule = sdao.ScheduleById(scheduleID);
            if (currentSchedule == null) {
                request.setAttribute("displayMessage", "Không tìm thấy lịch trình!");
                handleScheduleShowingAdmin(request, response);
                return url;
            }

            // Chỉ cập nhật trường arrival time nếu có
            String arrivalTimeStr = request.getParameter("arrivalTime");
            if (arrivalTimeStr != null && !arrivalTimeStr.isEmpty()) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
                Date arrivalDate = sdf.parse(arrivalTimeStr);
                currentSchedule.setArrivalTime(new Timestamp(arrivalDate.getTime()));
            }

            // Cập nhật xe bus và trạng thái
            currentSchedule.setBusID(busID);
            currentSchedule.setStatus(status);

            // 1. Cập nhật lịch trình
            boolean scheduleUpdated = sdao.updateSchedule(currentSchedule);
            System.out.println("Schedule update result: " + scheduleUpdated);

            // 2. Nếu thay đổi xe, cập nhật trạng thái xe
            boolean busesUpdated = true;
            if (oldBusID != busID) {
                // Cập nhật xe cũ thành "Bảo trì"
                boolean oldBusUpdated = bdao.updateBusStatus(oldBusID, "Bảo trì");

                // Cập nhật xe mới thành "Bận"
                boolean newBusUpdated = bdao.updateBusStatus(busID, "Bận");

                busesUpdated = oldBusUpdated && newBusUpdated;
            }

            if (scheduleUpdated && busesUpdated) {
                request.setAttribute("displayMessage", "Cập nhật lịch trình và điều động xe thành công!");
            } else {
                request.setAttribute("displayMessage", "Cập nhật thất bại, đã rollback các thay đổi!");
            }

        } catch (Exception e) {
            System.out.println("Error in handleUpdateSchedule: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("displayMessage", "Lỗi khi cập nhật lịch trình: " + e.getMessage());
        }

        handleScheduleShowingAdmin(request, response);
        return url;
    }
    /// HẾT PHẦN CODE ADMIN ================================================================================================///

    private List<LocationDTO> getAllLocation(HttpServletRequest request, HttpServletResponse response) {
        List<LocationDTO> locationList = new ArrayList<>();

        locationList = ldao.getAllLocations();

        if (locationList != null) {
            request.setAttribute("lList", locationList);
        } else {
            System.out.println("Error occur in ldao.getAllLocations()");
        }
        return locationList;
    }

    private List<BusDTO> getAllAvailableBus(HttpServletRequest request, HttpServletResponse response) {

        List<BusDTO> avaiBusList = new ArrayList<>();
        avaiBusList = bdao.getBusByStatus("Sẵn sàng");
        if (avaiBusList != null) {
            request.setAttribute("avaiBusList", avaiBusList);
        } else {
            System.out.println("Error occur in ldao.getAvaiBuses()");
        }
        return avaiBusList;
    }

    private List<RouteDTO> getAllRoute(HttpServletRequest request, HttpServletResponse response) {

        List<RouteDTO> avaiRouteList = new ArrayList<>();
        avaiRouteList = rdao.getAll();
        if (avaiRouteList != null) {
            request.setAttribute("avaiRouteList", avaiRouteList);
        } else {
            System.out.println("Error occur in ldao.getAvaiRoute()");
        }
        return avaiRouteList;
    }

    private List<StaffDTO> getAvaiStaffByRole(HttpServletRequest request, HttpServletResponse response, String role) {

        List<StaffDTO> avaiStaffList = new ArrayList<>();
        avaiStaffList = edao.getAvaiStaffByRole(role);

        if (avaiStaffList != null) {
            if ("Tài xế".equals(role)) {
                request.setAttribute("driverList", avaiStaffList);
            } else if ("Tiếp viên".equals(role)) {
                request.setAttribute("attendantList", avaiStaffList);
            }
        } else {
            System.out.println("Error in getAvaiStaffByRole() for role: " + role);
        }
        return avaiStaffList;
    }

    private String handleScheduleSearching(HttpServletRequest request, HttpServletResponse response) {
        String url = SCHEDULE_PAGE;
        List<ScheduleDTO> searchList = null;
        Map<String, String> errors = new HashMap<>();

        String Departure = request.getParameter("Departure");
        String Destination = request.getParameter("Destination");
        String travelDate = request.getParameter("travelDate");

        // lưu lại keyword
        request.setAttribute("Departure", Departure);
        request.setAttribute("Destination", Destination);
        request.setAttribute("travelDate", travelDate);

        String generalMessage = "";
        boolean hasError = false;

        try {
            LocalDate travelDate_value = LocalDate.parse(travelDate);
            if (travelDate_value == null || travelDate_value.isBefore(LocalDate.now())) {
                errors.put("date_error", "Vui lòng chọn ngày ở hiện tại hoặc tương lai!");
                hasError = true;
            }
            if (hasError) {
                generalMessage = "Lỗi, vui lòng kiểm tra lại các trường đã nhập!";
            } else {
                boolean isDepartureEmpty = (Departure == null || Departure.trim().isEmpty());
                boolean isDestinationEmpty = (Destination == null || Destination.trim().isEmpty());

                if (isDepartureEmpty && isDestinationEmpty) {
                    searchList = sdao.getSchedulesByDate(travelDate_value);
                } else if (!isDepartureEmpty && isDestinationEmpty) {
                    searchList = sdao.getSchedulesByDeaprture(Departure, travelDate_value);
                } else {
                    searchList = sdao.getSchedules(Departure, Destination, travelDate_value);
                }

                if (searchList != null) {
                    request.setAttribute("searchList", searchList);
                } else {
                    generalMessage = "Đã xảy ra lỗi, vui lòng kiểm tra lại!";
                }
            }
        } catch (Exception e) {
            System.out.println("Lỗi tại method search: " + e.getMessage());
            e.printStackTrace();
        }
        request.setAttribute("generalMessage", generalMessage);
        request.setAttribute("isSearching", true);
        return url;
    }

    private String handleDefaultScheduleDisplay(HttpServletRequest request, HttpServletResponse response) {
        String url = SCHEDULE_PAGE;

        // cập nhật trạng thái lịch trình sau khi quá thời gian đã được đặt trước đó
        sdao.autoUpdateScheduleStatus();
        List<ScheduleDTO> searchList = null;

        try {
            // Load tất cả chuyến xe khả dụng
            searchList = sdao.getAllAvailableSchedules();

            if (searchList != null && !searchList.isEmpty()) {
                request.setAttribute("searchList", searchList);
            } else {
                request.setAttribute("generalMessage", "Hiện tại không có chuyến xe nào khả dụng.");
            }

            // Set flag để JSP biết đây không phải là kết quả tìm kiếm
            request.setAttribute("isSearching", false);

        } catch (Exception e) {
            System.out.println("Lỗi tại method handleDefaultScheduleDisplay: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("generalMessage", "Đã xảy ra lỗi khi tải dữ liệu.");
        }

        // load location vào dropdown menu của hàm search
        getAllLocation(request, response);
        return url;
    }

}
