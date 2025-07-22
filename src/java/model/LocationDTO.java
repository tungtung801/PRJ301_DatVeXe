
package model;

/**
 *
 * @author Tung Tung
 */
public class LocationDTO {
    private int location_id;
    private String name;

    public LocationDTO() {
    }

    public LocationDTO(int location_id, String name) {
        this.location_id = location_id;
        this.name = name;
    }

    public LocationDTO(String name) {
        this.name = name;
    }
    

    public int getLocation_id() {
        return location_id;
    }

    public void setLocation_id(int location_id) {
        this.location_id = location_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
}
