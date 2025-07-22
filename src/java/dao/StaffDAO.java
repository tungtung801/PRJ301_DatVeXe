/*////////// GỐC
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.StaffDTO;
import utils.DBUtils;

/**
 *
 * @author Tung Nguyen
 */
public class StaffDAO {

    public StaffDAO() {
    }

    private static final String GET_ALL = "SELECT * FROM [dbo].[Employee]";
    private static final String CREATE = "INSERT INTO [dbo].[Employee] (emp_role, status, emp_name) VALUES (?, ?, ?)";

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public List<StaffDTO> getAllEmployee() {
        List<StaffDTO> empList = new ArrayList<>();
        StaffDTO employee = null;

        try {
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(GET_ALL);
            rs = ps.executeQuery();

            while (rs.next()) {
                employee = new StaffDTO();
                employee.setEmp_id(rs.getInt("emp_id"));
                employee.setEmp_name(rs.getString("emp_name"));
                employee.setEmp_role(rs.getString("emp_role"));
                employee.setStatus(rs.getString("status"));

                empList.add(employee);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();;
        } finally {
            DBUtils.closeResources(conn, ps, rs);
        }

        return empList;
    }

    public StaffDTO getStaffByname(String name) {
        StaffDTO employee = new StaffDTO();
        String sql = "SELECT * FROM [dbo].[Employee] WHERE emp_name = ?";
        try {
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            rs = ps.executeQuery();
            if (rs.next()) {
                employee.setEmp_id(rs.getInt("emp_id"));
                employee.setEmp_name(rs.getString("emp_name"));
                employee.setEmp_role(rs.getString("emp_role"));
                employee.setStatus(rs.getString("status"));
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();;
        } finally {
            DBUtils.closeResources(conn, ps, rs);
        }

        return employee;
    }

    public List<StaffDTO> getAvaiStaffByRole(String role) {
        List<StaffDTO> staffList = new ArrayList<>();
        StaffDTO employee = null;

        String sql = "SELECT * FROM [dbo].[Employee] WHERE status = N'Nhàn rỗi' AND emp_role = ?";

        try {
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, role);
            rs = ps.executeQuery();
            while (rs.next()) {
                employee = new StaffDTO();
                employee.setEmp_id(rs.getInt("emp_id"));
                employee.setEmp_name(rs.getString("emp_name"));
                employee.setEmp_role(rs.getString("emp_role"));
                employee.setStatus(rs.getString("status"));

                staffList.add(employee);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();;
        } finally {
            DBUtils.closeResources(conn, ps, rs);
        }

        return staffList;
    }

    public boolean create(StaffDTO newStaff) {
        boolean isCreated = false;

        try {
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(CREATE);
            ps.setString(1, newStaff.getEmp_role());
            ps.setString(2, newStaff.getStatus());
            ps.setString(3, newStaff.getEmp_name());

            int rowAffected = ps.executeUpdate();
            isCreated = rowAffected > 0;

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();;
        } finally {
            DBUtils.closeResources(conn, ps, rs);
        }
        return isCreated;
    }
}
