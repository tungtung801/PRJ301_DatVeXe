/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import DTO.RouteDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import utils.DBUtils;

/**
 *
 * @author Tung Nguyen
 */
public class RouteDAO {

    public RouteDAO() {
    }

    private static final String GET_ALL_ROUTE = "SELECT * FROM [Route]";
    private static final String GET_ROUTE_BY_ID = "SELECT * FROM [Route] WHERE route_id = ?";
    private static final String CREATE_ROUTE = "INSERT INTO [Route] (departure,destination,distance_km) VALUES ( ?, ?, ?)";
    private static final String UPDATE_ROUTE = "UPDATE [Route] SET  departure = ?, destination = ?, distance_km = ? WHERE route_id = ?";
    private static final String DELETE_ROUTE = "DELETE FROM [Route] WHERE route_id = ?";
    private static final String GET_DESC_USER_ID = "SELECT TOP 1 route_id FROM [Route] ORDER BY route_id DESC";

    private static final String ROUTE_FORM = "/admin/routeForm.jsp";
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public List<RouteDTO> getAll() {
        List<RouteDTO> routes = new ArrayList<>();

        try {
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(GET_ALL_ROUTE);
            rs = ps.executeQuery();

            while (rs.next()) {
                RouteDTO route = new RouteDTO();
                route.setRoute_id(rs.getInt("route_id"));
                route.setDeparture(rs.getString("departure"));
                route.setDestination(rs.getString("destination"));
                route.setDistance_km(rs.getFloat("distance_km"));

                routes.add(route);
            }
        } catch (Exception e) {
            System.err.println("Error in getAll(): " + e.getMessage());
            e.printStackTrace();
        } finally {
            DBUtils.closeResources(conn, ps, rs);
        }

        return routes;
    }

    public List<RouteDTO> searchRoutes(String departureKeyword, String destinationKeyword) {
        List<RouteDTO> results = new ArrayList<>();

        String sql = "SELECT * FROM [Route] WHERE departure LIKE ? AND destination LIKE ?";

        try {
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + departureKeyword + "%");
            ps.setString(2, "%" + destinationKeyword + "%");
            rs = ps.executeQuery();

            while (rs.next()) {
                RouteDTO route = new RouteDTO();
                route.setRoute_id(rs.getInt("route_id"));
                route.setDeparture(rs.getString("departure"));
                route.setDestination(rs.getString("destination"));
                route.setDistance_km(rs.getFloat("distance_km"));

                results.add(route);
            }

        } catch (Exception e) {
            System.err.println("Error in search(): " + e.getMessage());
            e.printStackTrace();
        } finally {
            DBUtils.closeResources(conn, ps, rs);
        }

        return results;
    }

    public RouteDTO search(String departure, String destination) {
        RouteDTO route = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM [Route] Where departure like ? AND destination like ?";
        try {
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, departure);
            ps.setString(2, destination);
            rs = ps.executeQuery();

            route.setRoute_id(rs.getInt("route_id"));
            route.setDeparture(rs.getString("departure"));
            route.setDestination(rs.getString("destination"));
            route.setDistance_km(rs.getFloat("distance_km"));

        } catch (Exception e) {
            System.err.println("Error in search(): " + e.getMessage());
            e.printStackTrace();
        } finally {
            DBUtils.closeResources(conn, ps, null);
        }

        return route;
    }
    
    public boolean create(RouteDTO route) {
        boolean success = false;
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(CREATE_ROUTE);
//INSERT INTO [Route] (departure,destination,dístance_km) VALUES ( ?, ?, ?)

            ps.setString(1, route.getDeparture());
            ps.setString(2, route.getDestination());
            ps.setFloat(3, route.getDistance_km());

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (Exception e) {
            System.err.println("Error in create(): " + e.getMessage());
            e.printStackTrace();
        } finally {
            DBUtils.closeResources(conn, ps);
        }

        return success;
    }
}
