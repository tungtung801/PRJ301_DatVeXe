/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Tung Nguyen
 */
public class ScheduleSeatDTO {
    private int scheduleSeatID;
    private int scheduleID;
    private String seatNumber;
    private String seatType;
    private int position;
    private int row;
    private int column;
    private String status;
    private int BookingID;

    public ScheduleSeatDTO() {
    }

    public ScheduleSeatDTO(int scheduleSeatID, int scheduleID, String seatNumber, String seatType, int position, int row, int column, String status, int BookingID) {
        this.scheduleSeatID = scheduleSeatID;
        this.scheduleID = scheduleID;
        this.seatNumber = seatNumber;
        this.seatType = seatType;
        this.position = position;
        this.row = row;
        this.column = column;
        this.status = status;
        this.BookingID = BookingID;
    }

    public ScheduleSeatDTO(int scheduleID, String seatNumber, String seatType, int position, int row, int column, String status, int BookingID) {
        this.scheduleID = scheduleID;
        this.seatNumber = seatNumber;
        this.seatType = seatType;
        this.position = position;
        this.row = row;
        this.column = column;
        this.status = status;
        this.BookingID = BookingID;
    }
    
    

    public int getScheduleSeatID() {
        return scheduleSeatID;
    }

    public void setScheduleSeatID(int scheduleSeatID) {
        this.scheduleSeatID = scheduleSeatID;
    }

    public int getScheduleID() {
        return scheduleID;
    }

    public void setScheduleID(int scheduleID) {
        this.scheduleID = scheduleID;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getSeatType() {
        return seatType;
    }

    public void setSeatType(String seatType) {
        this.seatType = seatType;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getBookingID() {
        return BookingID;
    }

    public void setBookingID(int BookingID) {
        this.BookingID = BookingID;
    }
    
    
}
