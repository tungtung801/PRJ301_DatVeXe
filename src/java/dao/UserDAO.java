package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.UserDTO;
import utils.DBUtils;

/**
 *
 * @author Tung Tung
 */
public class UserDAO {

    private static final String GET_ALL_USER = "SELECT * FROM [User]";
    private static final String GET_USER_BY_ID = "SELECT * FROM [User] WHERE user_id = ?";
    private static final String GET_USER_BY_PHONE = "SELECT * FROM [User] WHERE phone=?";
    private static final String GET_DESC_USER_ID = "SELECT TOP 1 user_id FROM [User] ORDER BY user_id DESC";
    private static final String CREATE_USER = "INSERT INTO [User] (user_id, fullname, email, password, role, phone) VALUES (?, ?, ?, ?, ?, ?)";

    public UserDAO() {
    }

    public boolean login(String phone, String password) {
        UserDTO user = getUserByPhone(phone);
        if (user != null) {
            if (user.getPhone().equals(phone)) {
                if (user.getPassword().equals(password)) {
                    return true;
                }
            }
        }
        return false;
    }

    public UserDTO getUserByID(String id) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        UserDTO user = null;

        try {
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(GET_USER_BY_ID);
            ps.setString(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                user = new UserDTO();
                user.setUser_id(rs.getString("user_id"));
                user.setFullname(rs.getString("fullname"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role"));
                user.setPhone(rs.getString("phone"));
            }
        } catch (Exception e) {
            System.out.println("Error in getUserById(): " + e.getMessage());
            e.printStackTrace();
        } finally {
            DBUtils.closeResources(conn, ps, rs);
        }
        return user;
    }

    public UserDTO getUserByPhone(String phone) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        UserDTO user = null;

        try {
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(GET_USER_BY_PHONE);
            ps.setString(1, phone);
            rs = ps.executeQuery();

            if (rs.next()) {
                user = new UserDTO();
                user.setUser_id(rs.getString("user_id"));
                user.setFullname(rs.getString("fullname"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role"));
                user.setPhone(rs.getString("phone"));
            }
        } catch (Exception e) {
            System.out.println("Error in getUserById(): " + e.getMessage());
            e.printStackTrace();
        } finally {
            DBUtils.closeResources(conn, ps, rs);
        }
        return user;
    }

    public List<UserDTO> getAllUser() {
        List<UserDTO> AllUsers = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(GET_ALL_USER);
            rs = ps.executeQuery();
            while (rs.next()) {
                UserDTO user = new UserDTO();
                user.setUser_id(rs.getString("user_id"));
                user.setFullname(rs.getString("fullname"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role"));
                user.setPhone(rs.getString("phone"));

                AllUsers.add(user);
            }
        } catch (Exception e) {
            System.err.println("Error in GetAllUser: " + e.getMessage());
            e.printStackTrace();
        } finally {
            DBUtils.closeResources(conn, ps, rs);
        }
        return AllUsers;
    }

//    public String autoGenerateUserId(){
//       List<UserDTO> AllUser= getAllUser();
//       int count=1;
//       for(UserDTO i:AllUser){
//           if(i!=null){
//             count+=1;  
//           }
//       }
//       
//       return "U"+count;
//    }
    public String autoGenerateUserId() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String newID = null;

        try {
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(GET_DESC_USER_ID);
            rs = ps.executeQuery();

            if (rs.next()) {
                // Extract last user id and increment numeric part
                String lastID = rs.getString("user_id"); // e.g., U0010
                int number = Integer.parseInt(lastID.substring(1)); // parse the numeric part
                number++; // increment
                newID = String.format("U%04d", number); // format with leading zeros
            } else {
                // No users yet, start with U0001
                newID = String.format("U%04d", 1);
            }
        } catch (Exception e) {
            System.out.println("Error in autoGenerateUserId(): " + e.getMessage());
            e.printStackTrace();
        } finally {
            DBUtils.closeResources(conn, ps, rs);
        }

        return newID;
    }

    public boolean create(UserDTO newUser) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean isSuccess = false;

        try {
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(CREATE_USER);
            ps.setString(1, newUser.getUser_id());
            ps.setString(2, newUser.getFullname());
            ps.setString(3, newUser.getEmail());
            ps.setString(4, newUser.getPassword());
            ps.setString(5, newUser.getRole());
            ps.setString(6, newUser.getPhone());

            int rowAffected = ps.executeUpdate();
            if (rowAffected > 0) {
                isSuccess = true;
            }
        } catch (Exception e) {
            System.out.println("Error in create(): " + e.getMessage());
            e.printStackTrace();
        } finally {
            DBUtils.closeResources(conn, ps);
        }
        return isSuccess;
    }

    public boolean isUserExist(String phone) {
        return getUserByPhone(phone) != null;
    }

    public boolean forgetpassword(String phone, String email) {
        UserDTO user = getUserByPhone(phone);
        if (user != null) {
            if (user.getEmail().equalsIgnoreCase(email.trim())) {
                return true;
            }
        }
        return false;
    }

    public boolean updatePassword(String phone, String newPassword) {
        String sql = "UPDATE [User] SET password = ? WHERE phone = ?";
        try {
            Connection conn = DBUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, newPassword);
            ps.setString(2, phone);
            int result = ps.executeUpdate();
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
