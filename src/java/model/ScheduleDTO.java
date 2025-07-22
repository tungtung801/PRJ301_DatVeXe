/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

/**
 *
 * @author Tung Nguyen
 */
public class ScheduleDTO {

    private int scheduleID;
    private int routeID;
    private int busID;
    private Date departureTime;
    private Date arrivalTime;
    private int driverID;
    private int attendantID;
    private double price;
    private String status;

    // Thuộc tính giành cho hiển thị trong trang đặt vé của user
    private String departure;
    private String destination;
    private String busNumber;
    private String busName;
    private String busTypeName;
    private int availableSeats;
    private int totalSeats;
    
    // Thuộc tính giành để hiển thị lịch trình admin quản lí
    private String driverName;
    private String attendantName;
    
    public ScheduleDTO() {
    }   

    public ScheduleDTO(int routeID, int busID, Date departureTime, Date arrivalTime, int driverID, int attendantID, double price, String status, String departure, String destination, String busNumber, String busName, String busTypeName, int availableSeats, int totalSeats, String driverName, String attendantName) {
        this.routeID = routeID;
        this.busID = busID;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.driverID = driverID;
        this.attendantID = attendantID;
        this.price = price;
        this.status = status;
        this.departure = departure;
        this.destination = destination;
        this.busNumber = busNumber;
        this.busName = busName;
        this.busTypeName = busTypeName;
        this.availableSeats = availableSeats;
        this.totalSeats = totalSeats;
        this.driverName = driverName;
        this.attendantName = attendantName;
    }


    public ScheduleDTO(int routeID, int busID, Date departureTime, Date arrivalTime, int driverID, int attendantID, double price, String status) {
        this.routeID = routeID;
        this.busID = busID;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.driverID = driverID;
        this.attendantID = attendantID;
        this.price = price;
        this.status = status;
    }
    
    

    public int getScheduleID() {
        return scheduleID;
    }

    public void setScheduleID(int scheduleID) {
        this.scheduleID = scheduleID;
    }

    public int getRouteID() {
        return routeID;
    }

    public void setRouteID(int routeID) {
        this.routeID = routeID;
    }

    public int getBusID() {
        return busID;
    }

    public void setBusID(int busID) {
        this.busID = busID;
    }

    public Date getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }

    public Date getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getDriverID() {
        return driverID;
    }

    public void setDriverID(int driverID) {
        this.driverID = driverID;
    }

    public int getAttendantID() {
        return attendantID;
    }

    public void setAttendantID(int attendantID) {
        this.attendantID = attendantID;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getBusNumber() {
        return busNumber;
    }

    public void setBusNumber(String busNumber) {
        this.busNumber = busNumber;
    }

    public String getBusName() {
        return busName;
    }

    public void setBusName(String busName) {
        this.busName = busName;
    }

    public String getBusTypeName() {
        return busTypeName;
    }

    public void setBusTypeName(String busTypeName) {
        this.busTypeName = busTypeName;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getAttendantName() {
        return attendantName;
    }

    public void setAttendantName(String attendantName) {
        this.attendantName = attendantName;
    }

    
}
