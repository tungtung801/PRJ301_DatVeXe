
package model;

/**
 *
 * @author Tung Tung
 */
public class UserDTO {
    private String user_id;
    private String fullname;
    private String email;
    private String password;
    private String role;
    private String phone;

    public UserDTO() {
    }

    public UserDTO(String user_id, String fullname, String email, String password, String role, String phone) {
        this.user_id = user_id;
        this.fullname = fullname;
        this.email = email;
        this.password = password;
        this.role = role;
        this.phone = phone;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    
}
