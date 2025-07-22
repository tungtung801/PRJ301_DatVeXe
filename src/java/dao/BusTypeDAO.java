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
import model.BusTypeDTO;
import utils.DBUtils;

/**
 *
 * @author Tung Nguyen
 */
public class BusTypeDAO {

    public BusTypeDAO() {
    }
    
    private static final String GET_ALL = "SELECT * FROM [dbo].[BusTypes]";
    
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    
    
    // Viết method này để phục vụ addBus trong BusController
    public List<BusTypeDTO> getAllBusTypes(){
        List<BusTypeDTO> busTypeList = new ArrayList<>();
        BusTypeDTO busType = null;
        
        try {
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(GET_ALL);
            rs = ps.executeQuery();
            
            while(rs.next()){
                busType = new BusTypeDTO();
                busType.setBusTypeID(rs.getInt("BusTypeID"));
                busType.setTypeName(rs.getString("TypeName"));
                busType.setSeatCount(rs.getInt("SeatCount"));
                busType.setCategory(rs.getString("Category"));
                busType.setDescription(rs.getString("Description"));
                
                busTypeList.add(busType);
            }
        } catch (Exception e) {
        } finally {
            DBUtils.closeResources(conn, ps, rs);
        }
        return busTypeList;
    }
}
