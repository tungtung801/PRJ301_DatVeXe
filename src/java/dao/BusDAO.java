/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.BusDTO;
import utils.DBUtils;

/**
 *
 * @author Tung Nguyen
 */
public class BusDAO {

    public BusDAO() {
    }

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    private static final String GET_ALL
            = "SELECT b.*, bt.TypeName as BusTypeName "
            + "FROM [dbo].[Buses] b "
            + "LEFT JOIN [dbo].[BusTypes] bt ON b.BusTypeID = bt.BusTypeID";
    private static final String CREATE_BUS = "INSERT INTO [dbo].[Buses] "
            + "([BusNumber], [BusName], [BusTypeID], [Description], [Status]) "
            + "VALUES (?, ?, ?, ?, ?)";
    
    private static final String GET_BUS_BY_NUMBER = "SELECT * FROM [dbo].[Buses] "
            + "WHERE BusNumber = ?";
    
    
    private static final String UPDATE = "UPDATE [dbo].[Buses] SET [status] = ? WHERE [license_plate] = ?";

    public List<BusDTO> getAllBus() {
        List<BusDTO> busList = new ArrayList<>();
        BusDTO bus = null;

        String sql = GET_ALL;
        try {
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                bus = new BusDTO();
                bus.setBusID(rs.getInt("BusID"));
                bus.setBusNumber(rs.getString("BusNumber"));
                bus.setBusName(rs.getString("BusName"));
                bus.setBusTypeID(rs.getInt("BusTypeID"));
                bus.setBusTypeName(rs.getString("BusTypeName"));
                bus.setDescription(rs.getString("Description"));
                bus.setStatus(rs.getString("Status"));

                busList.add(bus);
            }
        } catch (Exception e) {
            System.out.println("Error in get all bus: " + e.getMessage());
            e.printStackTrace();
        } finally {
            DBUtils.closeResources(conn, ps, rs);
        }
        return busList;
    }
    
//    // Thêm vào BusDAO
//public List<BusDTO> getAllAvailableBuses() {
//    List<BusDTO> busList = new ArrayList<>();
//    BusDTO bus = null;
//
//    String sql = "SELECT * FROM [dbo].[Buses] WHERE Status = N'Sẵn sàng'";
//    try {
//        conn = DBUtils.getConnection();
//        ps = conn.prepareStatement(sql);
//        rs = ps.executeQuery();
//
//        while (rs.next()) {
//            bus = new BusDTO();
//            bus.setBusID(rs.getInt("BusID"));
//            bus.setBusNumber(rs.getString("BusNumber"));
//            bus.setBusName(rs.getString("BusName"));
//            bus.setBusTypeID(rs.getInt("BusTypeID"));
//            bus.setDescription(rs.getString("Description"));
//            bus.setStatus(rs.getString("Status"));
//            busList.add(bus);
//        }
//    } catch (Exception e) {
//        System.out.println("Error: " + e.getMessage());
//        e.printStackTrace();
//    } finally {
//        DBUtils.closeResources(conn, ps, rs);
//    }
//    return busList;
//}

    public boolean addBus(BusDTO newBus) {
        boolean isAdded = false;

        try {
            conn = DBUtils.getConnection(); // Lấy kết nối từ lớp tiện ích
            if (conn != null) {
                ps = conn.prepareStatement(CREATE_BUS); // Sử dụng hằng số CREATE_BUS

                ps.setString(1, newBus.getBusNumber());
                ps.setString(2, newBus.getBusName());
                ps.setInt(3, newBus.getBusTypeID());
                ps.setString(4, newBus.getDescription());
                ps.setString(5, newBus.getStatus());

                int rowsAffected = ps.executeUpdate();

                if (rowsAffected > 0) {
                    isAdded = true;
                }
            }
        }catch (Exception e) {
            // Xử lý các lỗi khác (ví dụ: lỗi kết nối)
            System.err.println("General Error when adding bus: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Đảm bảo đóng tất cả tài nguyên để tránh rò rỉ bộ nhớ
            // (ResultSet rs không được dùng trong INSERT, nên là null)
            DBUtils.closeResources(conn, ps, null);
        }
        return isAdded;
    }
    
    public BusDTO getBusByNumber(String BusNumber){
        BusDTO bus = null;
        
        try{
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(GET_BUS_BY_NUMBER);
            ps.setString(1, BusNumber);
            rs = ps.executeQuery();
            
            if(rs.next()){
                bus = new BusDTO();
                bus.setBusID(rs.getInt("BusID"));
                bus.setBusNumber(rs.getString("BusNumber"));
                bus.setBusName(rs.getString("BusName"));
                bus.setBusTypeName(rs.getString("BusTypeName"));
                bus.setBusTypeID(rs.getInt("BusTypeID"));
                bus.setDescription(rs.getString("Description"));
                bus.setStatus(rs.getString("Status"));
            }
        }catch(Exception e){
            System.out.println("Error in getBusByNumber(): "+e.getMessage());
            e.printStackTrace();
        }finally{
            DBUtils.closeResources(conn, ps, rs);
        }
        return bus;
    }
    
    public List<BusDTO> getBusByName(String name){
        BusDTO bus = null;
        List<BusDTO> busList = new ArrayList<>();
        
        String sql = "SELECT b.*, bt.TypeName as BusTypeName " 
               + "FROM [dbo].[Buses] b "
               + "LEFT JOIN [dbo].[BusTypes] bt ON b.BusTypeID = bt.BusTypeID "
               + "WHERE b.BusName = ?";
        
        try{
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            rs = ps.executeQuery();
            
            while(rs.next()){
                bus = new BusDTO();
                bus.setBusID(rs.getInt("BusID"));
                bus.setBusNumber(rs.getString("BusNumber"));
                bus.setBusName(rs.getString("BusName"));
                bus.setBusTypeName(rs.getString("BusTypeName"));
                bus.setBusTypeID(rs.getInt("BusTypeID"));
                bus.setDescription(rs.getString("Description"));
                bus.setStatus(rs.getString("Status"));
                
                busList.add(bus);
            }
        }catch(Exception e){
            System.out.println("Error in getBusByNumber(): "+e.getMessage());
            e.printStackTrace();
        }finally{
            DBUtils.closeResources(conn, ps, rs);
        }
        return busList;
    }
    
    public List<BusDTO> getBusByStatus(String status){
        BusDTO bus = null;
        List<BusDTO> busList = new ArrayList<>();
        
        String sql = "SELECT b.*, bt.TypeName as BusTypeName " 
               + "FROM [dbo].[Buses] b "
               + "LEFT JOIN [dbo].[BusTypes] bt ON b.BusTypeID = bt.BusTypeID "
               + "WHERE b.Status = ?";
        
        try{
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, status);
            rs = ps.executeQuery();
            
            while(rs.next()){
                bus = new BusDTO();
                bus.setBusID(rs.getInt("BusID"));
                bus.setBusNumber(rs.getString("BusNumber"));
                bus.setBusName(rs.getString("BusName"));
                bus.setBusTypeName(rs.getString("BusTypeName"));
                bus.setBusTypeID(rs.getInt("BusTypeID"));
                bus.setDescription(rs.getString("Description"));
                bus.setStatus(rs.getString("Status"));
                
                busList.add(bus);
            }
        }catch(Exception e){
            System.out.println("Error in getBusByNumber(): "+e.getMessage());
            e.printStackTrace();
        }finally{
            DBUtils.closeResources(conn, ps, rs);
        }
        return busList;
    }
    
    public List<BusDTO> getBusByNameNStatus(String name, String status){
        BusDTO bus = null;
        List<BusDTO> busList = new ArrayList<>();
        
        String sql = "SELECT b.*, bt.TypeName as BusTypeName " 
               + "FROM [dbo].[Buses] b "
               + "LEFT JOIN [dbo].[BusTypes] bt ON b.BusTypeID = bt.BusTypeID "
               + "WHERE b.BusName = ? AND b.Status = ?";
        
        try{
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, status);
            rs = ps.executeQuery();
            
            while(rs.next()){
                bus = new BusDTO();
                bus.setBusID(rs.getInt("BusID"));
                bus.setBusNumber(rs.getString("BusNumber"));
                bus.setBusName(rs.getString("BusName"));
                bus.setBusTypeName(rs.getString("BusTypeName"));
                bus.setBusTypeID(rs.getInt("BusTypeID"));
                bus.setDescription(rs.getString("Description"));
                bus.setStatus(rs.getString("Status"));
                
                busList.add(bus);
            }
        }catch(Exception e){
            System.out.println("Error in getBusByNumber(): "+e.getMessage());
            e.printStackTrace();
        }finally{
            DBUtils.closeResources(conn, ps, rs);
        }
        return busList;
    }
}
