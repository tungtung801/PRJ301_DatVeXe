/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.BookingDTO;
import utils.DBUtils;

/**
 *
 * @author Tung Nguyen
 */
public class BookingDAO {

    public BookingDAO() {
    }

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public int createBookingNReturnId(BookingDTO newBooking, String[] seatIds) {
        String insertBooking = "INSERT INTO Bookings(UserID, ScheduleID, BookingDate, TotalAmount, Status) "
                + "OUTPUT INSERTED.BookingID "
                + "VALUES (?, ?, GETDATE(), ?, N'Chờ xác nhận')";

        String updateSeats = "UPDATE ScheduleSeats SET BookingID = ? WHERE ScheduleSeatID = ?";

        Connection conn = null;
        PreparedStatement psBooking = null;
        PreparedStatement psUpdateSeat = null;
        ResultSet rs = null;
        int bookingId = -1;

        try {
            conn = DBUtils.getConnection();
            conn.setAutoCommit(false); // Bắt đầu giao dịch

            // Bước 1: Tạo booking và lấy BookingID
            psBooking = conn.prepareStatement(insertBooking);
            psBooking.setString(1, newBooking.getUserID());
            psBooking.setInt(2, newBooking.getScheduleID());
            psBooking.setDouble(3, newBooking.getTotalAmount());
            rs = psBooking.executeQuery();

            if (rs.next()) {
                bookingId = rs.getInt(1);
            } else {
                throw new SQLException("Tạo booking thất bại, không có ID được trả về.");
            }

            // Bước 2: Cập nhật BookingID vào các ghế đã chọn
            if (bookingId != -1 && seatIds != null && seatIds.length > 0) {
                psUpdateSeat = conn.prepareStatement(updateSeats);
                for (String seatId : seatIds) {
                    int sId = Integer.parseInt(seatId);
                    psUpdateSeat.setInt(1, bookingId);
                    psUpdateSeat.setInt(2, sId);
                    psUpdateSeat.addBatch();
                }
                psUpdateSeat.executeBatch();
            }

            conn.commit(); // Commit giao dịch
            return bookingId;

        } catch (Exception e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            System.out.println("Xảy ra lỗi khi thêm Booking mới: " + e.getMessage());
            e.printStackTrace();
            return -1;
        } finally {
            DBUtils.closeResources(conn, ps, rs);

        }
    }

    public boolean updateBookingStatus(int bookingId, String status) {
        boolean isUpdated = false;

        String sql = "UPDATE [dbo].[Bookings] SET Status = ? WHERE BookingID = ?";

        try {
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, status);
            ps.setInt(2, bookingId);

            int rowAfftected = ps.executeUpdate();
            isUpdated = rowAfftected > 0;
        } catch (Exception e) {
            System.out.println("Xảy ra lỗi khi update trạng thái booking: " + e.getMessage());
            e.printStackTrace();;
        } finally {
            DBUtils.closeResources(conn, ps);
        }
        return isUpdated;
    }

    public void confirmPayment(int bookingId) {
        String updateBooking = "UPDATE Bookings SET Status = N'Đã thanh toán' WHERE BookingID = ?";
        String updateSeats = "UPDATE ScheduleSeats SET Status = N'Đã đặt' WHERE BookingID = ?";

        try ( Connection conn = DBUtils.getConnection();  PreparedStatement ps1 = conn.prepareStatement(updateBooking);  PreparedStatement ps2 = conn.prepareStatement(updateSeats)) {

            conn.setAutoCommit(false); // Giao dịch

            // Cập nhật trạng thái booking
            ps1.setInt(1, bookingId);
            ps1.executeUpdate();

            // Cập nhật trạng thái ghế
            ps2.setInt(1, bookingId);
            ps2.executeUpdate();

            conn.commit();
        } catch (Exception e) {
            System.out.println("Lỗi khi confirmPayment: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public BookingDTO getBookingById(int BookingId){
        BookingDTO booking = null;
        String sql = "SELECT * FROM [dbo].[Bookings] WHERE BookingID = ?";
        
        try {
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, BookingId);
            rs = ps.executeQuery();
            
            if(rs.next()){
                booking = new BookingDTO();
                booking.setBookingID(rs.getInt("BookingID"));
                booking.setUserID(rs.getString("UserID"));
                booking.setBookingDate(rs.getTimestamp("BookingDate"));
                booking.setTotalAmount(rs.getDouble("TotalAmount"));
                booking.setStatus(rs.getString("Status"));
            }
        } catch (Exception e) {
            System.out.println("Lỗi hệ thống khi tìm Booking với id: "+e.getMessage());
            e.printStackTrace();
        } finally {
            DBUtils.closeResources(conn, ps, rs);
        }
        return booking;
    }

}
