/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import model.ScheduleSeatDTO;
import utils.DBUtils;

/**
 *
 * @author Tung Nguyen
 */
public class ScheduleSeatDAO {

    public ScheduleSeatDAO() {
    }

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public List<ScheduleSeatDTO> getSeatsByScheduleID(int scheduleID) {
        List<ScheduleSeatDTO> scheduleSeatList = new ArrayList<>();
        ScheduleSeatDTO scheduleSeat = null;

        String sql = "SELECT * FROM ScheduleSeats WHERE ScheduleID = ? ORDER BY Position ASC";
        try {
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, scheduleID);
            rs = ps.executeQuery();

            while (rs.next()) {
                scheduleSeat = new ScheduleSeatDTO();
                scheduleSeat.setScheduleSeatID(rs.getInt("ScheduleSeatID"));
                scheduleSeat.setScheduleID(rs.getInt("ScheduleID"));
                scheduleSeat.setSeatNumber(rs.getString("SeatNumber"));
                scheduleSeat.setSeatType(rs.getString("SeatType"));
                scheduleSeat.setPosition(rs.getInt("Position"));
                scheduleSeat.setRow(rs.getInt("Row"));
                scheduleSeat.setColumn(rs.getInt("Column"));
                scheduleSeat.setStatus(rs.getString("Status"));
                scheduleSeat.setBookingID(rs.getInt("BookingID"));

                scheduleSeatList.add(scheduleSeat);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            DBUtils.closeResources(conn, ps, rs);
        }
        return scheduleSeatList;
    }

    public boolean updateSeatStatus(int scheduleSeatID, String status) {
        boolean isUpdated = false;
        String sql = "UPDATE ScheduleSeats SET Status = ? WHERE ScheduleSeatID = ?";

        try {
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, status);
            ps.setInt(1, scheduleSeatID);

            int rowAffected = ps.executeUpdate();
            isUpdated = rowAffected > 0;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            DBUtils.closeResources(conn, ps);
        }
        return isUpdated;
    }

    public List<String> getSeatNumbersByIds(String[] seatIds) {
        List<String> seatNumbers = new ArrayList<>();
        String sql = "SELECT SeatNumber FROM ScheduleSeats WHERE ScheduleSeatID IN ("
                + String.join(",", Collections.nCopies(seatIds.length, "?")) + ")";

        try {
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(sql);

            for (int i = 0; i < seatIds.length; i++) {
                ps.setInt(i + 1, Integer.parseInt(seatIds[i]));
            }

            rs = ps.executeQuery();
            while (rs.next()) {
                seatNumbers.add(rs.getString("SeatNumber"));
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi lấy SeatNumber theo ID: " + e.getMessage());
            e.printStackTrace();
        } finally {
            DBUtils.closeResources(conn, ps, rs);
        }

        return seatNumbers;
    }

    public int getAvailableSeats(int scheduleId) {
        String sql = "SELECT COUNT(*) FROM ScheduleSeats "
                + "WHERE ScheduleID = ? AND Status = N'Trống'";
        try ( Connection conn = DBUtils.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, scheduleId);
            try ( ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getInt(1) : 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int getTotalSeats(int scheduleId) {
        String sql = "SELECT COUNT(*) FROM ScheduleSeats WHERE ScheduleID = ?";
        try ( Connection conn = DBUtils.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, scheduleId);
            try ( ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getInt(1) : 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    } 
    
    public int getScheduleIdByBookingID(int BookingId){
        String sql = "SELECT ScheduleID FROM [dbo].[ScheduleSeats] WHERE BookingID = ?";
        try {
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, BookingId);
            rs = ps.executeQuery();
            
            if(rs.next()){
                return rs.getInt("ScheduleID");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            DBUtils.closeResources(conn, ps, rs);
        }
        return 0;
    }
}
