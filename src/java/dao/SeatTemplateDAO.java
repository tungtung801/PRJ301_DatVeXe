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
import model.SeatTemplateDTO;
import utils.DBUtils;

/**
 *
 * @author Tung Nguyen
 */
public class SeatTemplateDAO {

    public SeatTemplateDAO() {
    }
    
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public List<SeatTemplateDTO> getSeatsByBusType(int busTypeID) {
        List<SeatTemplateDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM SeatTemplate WHERE BusTypeID = ?";
        
        try{
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, busTypeID);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                SeatTemplateDTO s = new SeatTemplateDTO();
                s.setTemplateID(rs.getInt("TemplateID"));
                s.setBusTypeID(rs.getInt("BusTypeID"));
                s.setSeatNumber(rs.getString("SeatNumber"));
                s.setSeatType(rs.getString("SeatType"));
                s.setPosition(rs.getString("Position"));
                s.setRow(rs.getInt("Row"));
                s.setColumn(rs.getInt("Column"));
                list.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            DBUtils.closeResources(conn, ps, rs);
        }
        return list;
    }
}
