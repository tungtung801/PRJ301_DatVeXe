/* ///////////////// GỐC 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import model.ScheduleDTO;
import utils.DBUtils;

/**
 *
 * @author Tung Nguyen
 */
public class ScheduleDAO {

    public ScheduleDAO() {
    }

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    // ADMIN =============================================================================================
    public List<ScheduleDTO> displayAllSchedules() {
        List<ScheduleDTO> scheduleDisplayList = new ArrayList<>();
        ScheduleDTO schedule = null;

        String sql = "SELECT "
                + "s.*, "
                + "r.departure, r.destination, "
                + "b.BusName, b.BusNumber, "
                + "driver.emp_name AS DriverName, "
                + "attendant.emp_name AS AttendantName "
                + "FROM [dbo].[Schedules] s "
                + "JOIN [dbo].[Route] r ON s.RouteID = r.route_id "
                + "JOIN [dbo].[Buses] b ON s.BusID = b.BusID "
                + "JOIN [dbo].[Employee] driver ON s.DriverID = driver.emp_id "
                + "JOIN [dbo].[Employee] attendant ON s.AttendantID = attendant.emp_id ";
        try {
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                schedule = new ScheduleDTO();
                schedule.setScheduleID(rs.getInt("ScheduleID"));
                schedule.setRouteID(rs.getInt("RouteID"));
                schedule.setBusID(rs.getInt("BusID"));
                schedule.setDepartureTime(rs.getTimestamp("DepartureTime"));
                schedule.setArrivalTime(rs.getTimestamp("ArrivalTime"));
                schedule.setDriverID(rs.getInt("DriverID"));
                schedule.setAttendantID(rs.getInt("AttendantID"));
                schedule.setPrice(rs.getDouble("Price"));
                schedule.setStatus(rs.getString("Status"));

                // cái này để hiển thị ra jsp
                schedule.setDeparture(rs.getString("departure"));
                schedule.setDestination(rs.getString("destination"));
                schedule.setBusName(rs.getString("BusName"));
                schedule.setBusNumber(rs.getString("BusNumber"));
                schedule.setDriverName(rs.getString("DriverName"));
                schedule.setAttendantName(rs.getString("AttendantName"));

                scheduleDisplayList.add(schedule);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("SQL Error: " + e.getMessage()); // Thêm dòng này để xem lỗi cụ thể
        } finally {
            DBUtils.closeResources(conn, ps, rs);
        }

        return scheduleDisplayList;
    }
    // ADMIN =============================================================================================

    public List<ScheduleDTO> getAllAvailableSchedules() {
        List<ScheduleDTO> scheduleList = new ArrayList<>();
        ScheduleDTO schedule = null;

        String sql = "SELECT "
                + "s.*, "
                + "r.departure, "
                + "r.destination, "
                + "s.DepartureTime, "
                + "s.ArrivalTime, "
                + "s.Price, "
                + "b.BusNumber, "
                + "bt.TypeName AS BusTypeName, "
                + "(SELECT COUNT(SS.ScheduleSeatID) FROM [dbo].[ScheduleSeats] SS WHERE SS.ScheduleID = s.ScheduleID AND SS.Status = N'Trống') AS AvailableSeats, "
                + "(SELECT COUNT(SS.ScheduleSeatID) FROM [dbo].[ScheduleSeats] SS WHERE SS.ScheduleID = s.ScheduleID) AS TotalSeats "
                + "FROM [dbo].[Schedules] s "
                + "JOIN [dbo].[Route] r ON s.RouteID = r.route_id "
                + "JOIN [dbo].[Buses] b ON s.BusID = b.BusID "
                + "JOIN [dbo].[BusTypes] bt ON b.BusTypeID = bt.BusTypeID "
                + "WHERE s.Status = N'Lên lịch' " // Chỉ lấy các chuyến đang hoạt động
                + "AND s.DepartureTime >= GETDATE() " // Chỉ lấy các chuyến từ hiện tại trở đi
                + "ORDER BY s.[DepartureTime] ASC";

        try {
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                schedule = new ScheduleDTO();

                // Set core schedule properties
                schedule.setScheduleID(rs.getInt("ScheduleID"));
                schedule.setRouteID(rs.getInt("RouteID"));
                schedule.setBusID(rs.getInt("BusID"));
                schedule.setDepartureTime(rs.getTimestamp("DepartureTime"));
                schedule.setArrivalTime(rs.getTimestamp("ArrivalTime"));
                schedule.setDriverID(rs.getInt("DriverID"));
                schedule.setAttendantID(rs.getInt("AttendantID"));
                schedule.setPrice(rs.getDouble("Price"));
                schedule.setStatus(rs.getString("Status"));

                // Set additional properties needed for the JSP display
                schedule.setDeparture(rs.getString("departure"));
                schedule.setDestination(rs.getString("destination"));
                schedule.setBusNumber(rs.getString("BusNumber"));
                schedule.setBusTypeName(rs.getString("BusTypeName"));
                schedule.setAvailableSeats(rs.getInt("AvailableSeats"));
                schedule.setTotalSeats(rs.getInt("TotalSeats"));

                scheduleList.add(schedule);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("SQL Error: " + e.getMessage()); // Thêm dòng này để xem lỗi cụ thể
        } finally {
            DBUtils.closeResources(conn, ps, rs);
        }
        return scheduleList;
    }

    public List<ScheduleDTO> getSchedules(String Departure, String Destination, LocalDate travelDate) {
        List<ScheduleDTO> scheduleList = new ArrayList<>();
        ScheduleDTO schedule = null;

        String sql = "SELECT "
                + "s.*, "
                + "r.departure, "
                + "r.destination, "
                + "s.DepartureTime, "
                + "s.ArrivalTime, "
                + "s.Price, "
                + "b.BusNumber, "
                + "bt.TypeName AS BusTypeName, "
                + "(SELECT COUNT(SS.ScheduleSeatID) FROM [dbo].[ScheduleSeats] SS WHERE SS.ScheduleID = s.ScheduleID AND SS.Status = N'Trống') AS AvailableSeats, "
                + "(SELECT COUNT(SS.ScheduleSeatID) FROM [dbo].[ScheduleSeats] SS WHERE SS.ScheduleID = s.ScheduleID) AS TotalSeats "
                + "FROM [dbo].[Schedules] s "
                + "JOIN [dbo].[Route] r ON s.RouteID = r.route_id "
                + "JOIN [dbo].[Buses] b ON s.BusID = b.BusID "
                + "JOIN [dbo].[BusTypes] bt ON b.BusTypeID = bt.BusTypeID "
                + "WHERE r.departure LIKE ? "
                + "AND r.destination LIKE ? "
                + "AND CONVERT(DATE, s.[DepartureTime]) = ? " // Convert để chỉ lấy phần ngày-thngs-năm thày vig lấy cả phần giờ-phút-giây
                + "AND s.Status != 'Hoàn thành' "
                + "ORDER BY s.[DepartureTime] ASC";

        try {
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setNString(1, "%" + Departure + "%");
            ps.setNString(2, "%" + Destination + "%");
            ps.setDate(3, Date.valueOf(travelDate));
            rs = ps.executeQuery();

            while (rs.next()) {
                schedule = new ScheduleDTO();

                // Set core schedule properties
                schedule.setScheduleID(rs.getInt("ScheduleID"));
                schedule.setRouteID(rs.getInt("RouteID"));
                schedule.setBusID(rs.getInt("BusID"));
                schedule.setDepartureTime(rs.getTimestamp("DepartureTime"));
                schedule.setArrivalTime(rs.getTimestamp("ArrivalTime"));
                schedule.setDriverID(rs.getInt("DriverID"));
                schedule.setAttendantID(rs.getInt("AttendantID"));
                schedule.setPrice(rs.getDouble("Price"));
                schedule.setStatus(rs.getString("Status"));

                // Set additional properties needed for the JSP display
                schedule.setDeparture(rs.getString("departure"));
                schedule.setDestination(rs.getString("destination"));
                schedule.setBusNumber(rs.getString("BusNumber"));
                schedule.setBusTypeName(rs.getString("BusTypeName"));
                schedule.setAvailableSeats(rs.getInt("AvailableSeats"));
                schedule.setTotalSeats(rs.getInt("TotalSeats"));

                scheduleList.add(schedule);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("SQL Error: " + e.getMessage()); // Thêm dòng này để xem lỗi cụ thể
        } finally {
            DBUtils.closeResources(conn, ps, rs);
        }
        return scheduleList;
    }

    public List<ScheduleDTO> getSchedulesByDeaprture(String Departure, LocalDate travelDate) {
        List<ScheduleDTO> scheduleList = new ArrayList<>();
        ScheduleDTO schedule = null;

        String sql = "SELECT "
                + "s.*, "
                + "r.departure, "
                + "r.destination, "
                + "s.DepartureTime, "
                + "s.ArrivalTime, "
                + "s.Price, "
                + "b.BusNumber, "
                + "bt.TypeName AS BusTypeName, "
                + "(SELECT COUNT(SS.ScheduleSeatID) FROM [dbo].[ScheduleSeats] SS WHERE SS.ScheduleID = s.ScheduleID AND SS.Status = N'Trống') AS AvailableSeats, "
                + "(SELECT COUNT(SS.ScheduleSeatID) FROM [dbo].[ScheduleSeats] SS WHERE SS.ScheduleID = s.ScheduleID) AS TotalSeats "
                + "FROM [dbo].[Schedules] s "
                + "JOIN [dbo].[Route] r ON s.RouteID = r.route_id "
                + "JOIN [dbo].[Buses] b ON s.BusID = b.BusID "
                + "JOIN [dbo].[BusTypes] bt ON b.BusTypeID = bt.BusTypeID "
                + "WHERE r.departure LIKE ? "
                + "AND CONVERT(DATE, s.[DepartureTime]) = ? " // Convert để chỉ lấy phần ngày-thngs-năm thày vig lấy cả phần giờ-phút-giây
                + "AND s.Status != 'Hoàn thành' "
                + "ORDER BY s.[DepartureTime] ASC";

        try {
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setNString(1, "%" + Departure + "%");
            ps.setDate(2, Date.valueOf(travelDate));
            rs = ps.executeQuery();

            while (rs.next()) {
                schedule = new ScheduleDTO();

                // Set core schedule properties
                schedule.setScheduleID(rs.getInt("ScheduleID"));
                schedule.setRouteID(rs.getInt("RouteID"));
                schedule.setBusID(rs.getInt("BusID"));
                schedule.setDepartureTime(rs.getTimestamp("DepartureTime"));
                schedule.setArrivalTime(rs.getTimestamp("ArrivalTime"));
                schedule.setDriverID(rs.getInt("DriverID"));
                schedule.setAttendantID(rs.getInt("AttendantID"));
                schedule.setPrice(rs.getDouble("Price"));
                schedule.setStatus(rs.getString("Status"));

                // Set additional properties needed for the JSP display
                schedule.setDeparture(rs.getString("departure"));
                schedule.setDestination(rs.getString("destination"));
                schedule.setBusNumber(rs.getString("BusNumber"));
                schedule.setBusTypeName(rs.getString("BusTypeName"));
                schedule.setAvailableSeats(rs.getInt("AvailableSeats"));
                schedule.setTotalSeats(rs.getInt("TotalSeats"));

                scheduleList.add(schedule);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("SQL Error: " + e.getMessage()); // Thêm dòng này để xem lỗi cụ thể
        } finally {
            DBUtils.closeResources(conn, ps, rs);
        }
        return scheduleList;
    }

    public List<ScheduleDTO> getSchedulesByDate(LocalDate travelDate) {
        List<ScheduleDTO> scheduleList = new ArrayList<>();
        ScheduleDTO schedule = null;

        String sql = "SELECT "
                + "s.*, "
                + "r.departure, "
                + "r.destination, "
                + "s.DepartureTime, "
                + "s.ArrivalTime, "
                + "s.Price, "
                + "b.BusNumber, "
                + "bt.TypeName AS BusTypeName, "
                + "(SELECT COUNT(SS.ScheduleSeatID) FROM [dbo].[ScheduleSeats] SS WHERE SS.ScheduleID = s.ScheduleID AND SS.Status = N'Trống') AS AvailableSeats, "
                + "(SELECT COUNT(SS.ScheduleSeatID) FROM [dbo].[ScheduleSeats] SS WHERE SS.ScheduleID = s.ScheduleID) AS TotalSeats "
                + "FROM [dbo].[Schedules] s "
                + "JOIN [dbo].[Route] r ON s.RouteID = r.route_id "
                + "JOIN [dbo].[Buses] b ON s.BusID = b.BusID "
                + "JOIN [dbo].[BusTypes] bt ON b.BusTypeID = bt.BusTypeID "
                + "WHERE CONVERT(DATE, s.[DepartureTime]) >= ? " // Convert để chỉ lấy phần ngày-thngs-năm thày vig lấy cả phần giờ-phút-giây
                + "AND s.Status != 'Hoàn thành' "
                + "ORDER BY s.[DepartureTime] ASC";

        try {
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setDate(1, Date.valueOf(travelDate));
            rs = ps.executeQuery();

            while (rs.next()) {
                schedule = new ScheduleDTO();

                // Set core schedule properties
                schedule.setScheduleID(rs.getInt("ScheduleID"));
                schedule.setRouteID(rs.getInt("RouteID"));
                schedule.setBusID(rs.getInt("BusID"));
                schedule.setDepartureTime(rs.getTimestamp("DepartureTime"));
                schedule.setArrivalTime(rs.getTimestamp("ArrivalTime"));
                schedule.setDriverID(rs.getInt("DriverID"));
                schedule.setAttendantID(rs.getInt("AttendantID"));
                schedule.setPrice(rs.getDouble("Price"));
                schedule.setStatus(rs.getString("Status"));

                // Set additional properties needed for the JSP display
                schedule.setDeparture(rs.getString("departure"));
                schedule.setDestination(rs.getString("destination"));
                schedule.setBusNumber(rs.getString("BusNumber"));
                schedule.setBusTypeName(rs.getString("BusTypeName"));
                schedule.setAvailableSeats(rs.getInt("AvailableSeats"));
                schedule.setTotalSeats(rs.getInt("TotalSeats"));

                scheduleList.add(schedule);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("SQL Error: " + e.getMessage()); // Thêm dòng này để xem lỗi cụ thể
        } finally {
            DBUtils.closeResources(conn, ps, rs);
        }
        return scheduleList;
    }

    public boolean create(ScheduleDTO schedule) {
        boolean success = false;
        ResultSet rs = null;

        try {
            conn = DBUtils.getConnection();
            String sql = "{call CreateScheduleWithSeats(?, ?, ?, ?, ?, ?, ?, ?)}";

            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(sql);
            // Thiết lập tham số
            ps.setInt(1, schedule.getRouteID());
            ps.setInt(2, schedule.getBusID());
            ps.setTimestamp(3, new Timestamp(schedule.getDepartureTime().getTime()));
            ps.setTimestamp(4, new Timestamp(schedule.getArrivalTime().getTime()));
            ps.setInt(5, schedule.getDriverID());
            ps.setInt(6, schedule.getAttendantID());
            ps.setDouble(7, schedule.getPrice());
            ps.setString(8, schedule.getStatus());

            // Thực thi stored procedure
            rs = ps.executeQuery();
            if (rs.next()) {
                int newId = rs.getInt("NewScheduleID");
                schedule.setScheduleID(newId);
                success = true;
            }

        } catch (Exception e) {
            System.err.println("Error in create(): " + e.getMessage());
            e.printStackTrace();
        } finally {
            DBUtils.closeResources(conn, ps, rs);
        }
        return success;
    }

    public ScheduleDTO ScheduleById(int scheduleId) {
        ScheduleDTO schedule = null;
        String sql = "SELECT "
                + "s.*, "
                + "r.departure, "
                + "r.destination, "
                + "s.DepartureTime, "
                + "s.ArrivalTime, "
                + "s.Price, "
                + "b.BusNumber, "
                + "b.BusName, "
                + "bt.TypeName AS BusTypeName, "
                + "(SELECT COUNT(SS.ScheduleSeatID) FROM [dbo].[ScheduleSeats] SS WHERE SS.ScheduleID = s.ScheduleID AND SS.Status = N'Trống') AS AvailableSeats, "
                + "(SELECT COUNT(SS.ScheduleSeatID) FROM [dbo].[ScheduleSeats] SS WHERE SS.ScheduleID = s.ScheduleID) AS TotalSeats "
                + "FROM [dbo].[Schedules] s "
                + "JOIN [dbo].[Route] r ON s.RouteID = r.route_id "
                + "JOIN [dbo].[Buses] b ON s.BusID = b.BusID "
                + "JOIN [dbo].[BusTypes] bt ON b.BusTypeID = bt.BusTypeID "
                + "WHERE s.ScheduleID = ? ";

        try {
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(sql);

            System.out.println("⏩ Đang lấy schedule theo ID: " + scheduleId);

            ps.setInt(1, scheduleId);
            rs = ps.executeQuery();

            if (rs.next()) {
                schedule = new ScheduleDTO();
                schedule.setScheduleID(rs.getInt("ScheduleID"));
                schedule.setRouteID(rs.getInt("RouteID"));
                schedule.setBusID(rs.getInt("BusID"));
                schedule.setDepartureTime(rs.getTimestamp("DepartureTime"));
                schedule.setArrivalTime(rs.getTimestamp("ArrivalTime"));
                schedule.setDriverID(rs.getInt("DriverID"));
                schedule.setAttendantID(rs.getInt("AttendantID"));
                schedule.setPrice(rs.getDouble("Price"));
                schedule.setStatus(rs.getString("Status"));

                schedule.setDeparture(rs.getString("departure"));
                schedule.setDestination(rs.getString("destination"));
                schedule.setBusNumber(rs.getString("BusNumber"));
                schedule.setBusName(rs.getString("BusName"));
                schedule.setBusTypeName(rs.getString("BusTypeName"));
                schedule.setAvailableSeats(rs.getInt("AvailableSeats"));
                schedule.setTotalSeats(rs.getInt("TotalSeats"));
            }
        } catch (Exception e) {
            System.out.println("Error in getScheduleByID: " + e.getMessage());
            e.printStackTrace();
        } finally {
            DBUtils.closeResources(conn, ps, rs);
        }
        return schedule;
    }

    public void autoUpdateScheduleStatus() {
        String updateScheduleSQL = "UPDATE Schedules SET Status = N'Hoàn thành' WHERE ArrivalTime < GETDATE() AND Status = N'Đang phục vụ'";
        String updateSchedule2SQL = "UPDATE Schedules SET Status = N'Đang phục vụ' WHERE DepartureTime < GETDATE() AND Status = N'Lên lịch'";
        String updateBusSQL = "UPDATE Buses SET Status = N'Sẵn sàng' "
                + "WHERE BusID IN (SELECT BusID FROM Schedules WHERE DepartureTime < GETDATE() AND Status = N'Hoàn thành')";
        String updateStaffSQL = "UPDATE Employee SET status = N'Nhàn rỗi' "
                + "WHERE emp_id IN (SELECT emp_id FROM Schedules WHERE DepartureTime < GETDATE() AND Status = N'Hoàn thành')";

        try ( Connection conn = DBUtils.getConnection()) {
            conn.setAutoCommit(false); // Bắt đầu transaction

            // 1. Update schedule
            try ( PreparedStatement ps1 = conn.prepareStatement(updateScheduleSQL)) {
                int rowsUpdated = ps1.executeUpdate();
                System.out.println("Schedules updated to 'Hoàn thành': " + rowsUpdated);
            }

            try ( PreparedStatement ps2 = conn.prepareStatement(updateSchedule2SQL)) {
                int rowsUpdated = ps2.executeUpdate();
                System.out.println("Schedules updated to 'Đang phục vụ': " + rowsUpdated);
            }

            // 2. Update bus
            try ( PreparedStatement ps3 = conn.prepareStatement(updateBusSQL)) {
                int busUpdated = ps3.executeUpdate();
                System.out.println("Buses updated to 'Sẵn sàng': " + busUpdated);
            }

            // 3. Cập nhật trạng thái nhân viên
            try ( PreparedStatement ps4 = conn.prepareStatement(updateStaffSQL)) {
                int staffUpdated = ps4.executeUpdate();
                System.out.println("Staffs updated to 'Nhàn rỗi': " + staffUpdated);
            }
            conn.commit(); // Hoàn tất transaction
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean updateSchedule(ScheduleDTO schedule) {
        boolean isUpdate = false;
        String sql = "UPDATE Schedules SET busID = ?, driverID = ?, attendantID = ?, price = ?, status = ? WHERE scheduleID = ?";
        try {
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, schedule.getBusID());
            ps.setInt(2, schedule.getDriverID());
            ps.setInt(3, schedule.getAttendantID());
            ps.setDouble(4, schedule.getPrice());
            ps.setString(5, schedule.getStatus());
            ps.setInt(6, schedule.getScheduleID());
            int rowsAffected = ps.executeUpdate();
            isUpdate = rowsAffected > 0;
        } catch (Exception e) {
            System.out.println("Error in updateSchedule: " + e.getMessage());
        } finally {
            DBUtils.closeResources(conn, ps, rs);
        }
        return isUpdate;
    }

    // NHÂN VIÊN ============= //
    public List<ScheduleDTO> getEmployeeSchedule(String name) {
        String sql = "SELECT \n"
                + "    s.ScheduleID,\n"
                + "    s.RouteID,\n"
                + "    s.BusID,\n"
                + "    s.DepartureTime,\n"
                + "    s.ArrivalTime,\n"
                + "    s.DriverID,\n"
                + "    s.AttendantID,\n"
                + "    s.Price,\n"
                + "    s.Status,\n"
                + "    r.departure,\n"
                + "    r.destination,\n"
                + "    b.BusNumber,\n"
                + "    b.BusName,\n"
                + "    bt.TypeName AS BusTypeName,\n"
                + "    driver.emp_name AS DriverName,\n"
                + "    attendant.emp_name AS AttendantName,\n"
                + "    (SELECT COUNT(SS.ScheduleSeatID) FROM [dbo].[ScheduleSeats] SS WHERE SS.ScheduleID = s.ScheduleID AND SS.Status = N'Bận') AS AvailableSeats,\n"
                + "    (SELECT COUNT(SS.ScheduleSeatID) FROM [dbo].[ScheduleSeats] SS WHERE SS.ScheduleID = s.ScheduleID) AS TotalSeats\n"
                + "FROM [dbo].[Schedules] s\n"
                + "LEFT JOIN [dbo].[Route] r ON s.RouteID = r.route_id\n"
                + "LEFT JOIN [dbo].[Buses] b ON s.BusID = b.BusID\n"
                + "LEFT JOIN [dbo].[BusTypes] bt ON b.BusTypeID = bt.BusTypeID\n"
                + "LEFT JOIN [dbo].[Employee] driver ON s.DriverID = driver.emp_id\n"
                + "LEFT JOIN [dbo].[Employee] attendant ON s.AttendantID = attendant.emp_id\n"
                + "WHERE driver.emp_name LIKE ? \n"
                + "   OR attendant.emp_name LIKE ?\n"
                + "ORDER BY s.DepartureTime ASC";

        List<ScheduleDTO> list = new ArrayList<>();
        ScheduleDTO schedule = null;

        try {
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + name + "%");
            ps.setString(2, "%" + name + "%");
            rs = ps.executeQuery();

            while (rs.next()) {
                schedule = new ScheduleDTO();
                // Set core schedule properties
                schedule.setScheduleID(rs.getInt("ScheduleID"));
                schedule.setRouteID(rs.getInt("RouteID"));
                schedule.setBusID(rs.getInt("BusID"));
                schedule.setDepartureTime(rs.getTimestamp("DepartureTime"));
                schedule.setArrivalTime(rs.getTimestamp("ArrivalTime"));
                schedule.setDriverID(rs.getInt("DriverID"));
                schedule.setAttendantID(rs.getInt("AttendantID"));
                schedule.setPrice(rs.getDouble("Price"));
                schedule.setStatus(rs.getString("Status"));

                // Set additional fields
                schedule.setDeparture(rs.getString("departure"));
                schedule.setDestination(rs.getString("destination"));
                schedule.setBusNumber(rs.getString("BusNumber"));
                schedule.setBusName(rs.getString("BusName"));
                schedule.setBusTypeName(rs.getString("BusTypeName"));
                schedule.setDriverName(rs.getString("DriverName"));
                schedule.setAttendantName(rs.getString("AttendantName"));
                schedule.setAvailableSeats(rs.getInt("AvailableSeats"));
                schedule.setTotalSeats(rs.getInt("TotalSeats"));

                list.add(schedule);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("SQL Error: " + e.getMessage());
        } finally {
            DBUtils.closeResources(conn, ps, rs);
        }
        return list;
    }

    // test 
    public static void main(String[] args) {
        ScheduleDAO sdao = new ScheduleDAO();
        List<ScheduleDTO> list = sdao.getEmployeeSchedule("Huỳnh Anh Tú");
        if (list.isEmpty()) {
            System.out.println("false");
        } else {
            System.out.println("true");
        }
    }
}

