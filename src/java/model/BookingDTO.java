/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;


import java.sql.Date;

/**
 *
 * @author Tung Nguyen
 */
public class BookingDTO {
    private int bookingID;
    private String userID;
    private int scheduleID;
    private java.sql.Timestamp bookingDate;
    private double totalAmount;
    private String status;

    public BookingDTO() {
    }

    public BookingDTO(String userID, int scheduleID, java.sql.Timestamp bookingDate, double totalAmount, String status) {
        this.userID = userID;
        this.scheduleID = scheduleID;
        this.bookingDate = bookingDate;
        this.totalAmount = totalAmount;
        this.status = status;
    }
    
    public BookingDTO(String userID, int scheduleID, double totalAmount) {
        this.userID = userID;
        this.scheduleID = scheduleID;
        this.totalAmount = totalAmount;
    }

    public int getBookingID() {
        return bookingID;
    }

    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public int getScheduleID() {
        return scheduleID;
    }

    public void setScheduleID(int scheduleID) {
        this.scheduleID = scheduleID;
    }

    public java.sql.Timestamp getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(java.sql.Timestamp bookingDate) {
        this.bookingDate = bookingDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    
}
