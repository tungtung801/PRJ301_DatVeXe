package dao; // Hãy đảm bảo package này khớp với cấu trúc project của bạn

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.LocationDTO;
import utils.DBUtils;

public class LocationDAO {

    public LocationDAO() {
    }

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    private static final String GET_ALL = "SELECT * FROM [dbo].[Location]";
    private static final String CREATE = "INSERT INTO [dbo].[Location] ([name]) VALUES (?)";

    public List<LocationDTO> getAllLocations() {
        List<LocationDTO> locationList = new ArrayList<>();
        String sql = GET_ALL + " ORDER BY [location_id] ASC";
        LocationDTO location = null;

        try {
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                location = new LocationDTO();
                location.setLocation_id(rs.getInt("location_id"));
                location.setName(rs.getString("name"));
                locationList.add(location);
            }
        } catch (Exception e) {
            System.out.println("Cannot add location to list!");
            e.printStackTrace();
        } finally {
            DBUtils.closeResources(conn, ps, rs);
        }
        return locationList;
    }

    public LocationDTO getLocationByName(String keyword) {
        String sql = GET_ALL + " WHERE [name] = ?";
        LocationDTO location = null;

        try {
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, keyword);
            rs = ps.executeQuery();

            if (rs.next()) {
                location = new LocationDTO();
                location.setLocation_id(rs.getInt("location_id"));
                location.setName(rs.getString("name"));
            }
        } catch (Exception e) {
            System.out.println("Cannot get location with that keyword!");
            e.printStackTrace();
        } finally {
            DBUtils.closeResources(conn, ps, rs);
        }
        return location;
    }

    public boolean addLocation(LocationDTO newLocation) {

        boolean isSuccess = false;
        try {
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(CREATE);
            ps.setString(1, newLocation.getName());

            int rowAffected = ps.executeUpdate();
            return isSuccess = rowAffected > 0;
        } catch (Exception e) {
            System.out.println("Cannot get location with that keyword!");
            e.printStackTrace();
        } finally {
            DBUtils.closeResources(conn, ps, rs);
        }
        return isSuccess;
    }

}
