/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
 *
 * @author ADMIN
 */
public class RouteDTO {
    private int route_id;
    private String departure;
    private String destination;
    private float distance_km;

    public RouteDTO() {
    }

    public RouteDTO(int route_id, String departure, String destination, float distance_km) {
        this.route_id = route_id;
        this.departure = departure;
        this.destination = destination;
        this.distance_km = distance_km;
    }

    public RouteDTO(String departure, String destination, float distance_km) {
        this.departure = departure;
        this.destination = destination;
        this.distance_km = distance_km;
    }

    public int getRoute_id() {
        return route_id;
    }

    public void setRoute_id(int route_id) {
        this.route_id = route_id;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public float getDistance_km() {
        return distance_km;
    }

    public void setDistance_km(float distance_km) {
        this.distance_km = distance_km;
    }

    
}
