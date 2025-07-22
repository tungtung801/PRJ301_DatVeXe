/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Tung Nguyen
 */
public class TicketDTO {
    private int ticketID;
    private int bookingID;
    private int scheduleSeatID;
    private String passengerName;
    private String passengerPhone;
    private String ticketCode;
    private String status;

    public TicketDTO() {
    }

    public TicketDTO(int bookingID, int scheduleSeatID, String passengerName, String passengerPhone, String ticketCode, String status) {
        this.bookingID = bookingID;
        this.scheduleSeatID = scheduleSeatID;
        this.passengerName = passengerName;
        this.passengerPhone = passengerPhone;
        this.ticketCode = ticketCode;
        this.status = status;
    }

    public int getTicketID() {
        return ticketID;
    }

    public void setTicketID(int ticketID) {
        this.ticketID = ticketID;
    }

    public int getBookingID() {
        return bookingID;
    }

    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
    }

    public int getScheduleSeatID() {
        return scheduleSeatID;
    }

    public void setScheduleSeatID(int scheduleSeatID) {
        this.scheduleSeatID = scheduleSeatID;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public String getPassengerPhone() {
        return passengerPhone;
    }

    public void setPassengerPhone(String passengerPhone) {
        this.passengerPhone = passengerPhone;
    }

    public String getTicketCode() {
        return ticketCode;
    }

    public void setTicketCode(String ticketCode) {
        this.ticketCode = ticketCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    
}
