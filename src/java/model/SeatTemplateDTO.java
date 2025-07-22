/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Tung Nguyen
 */
public class SeatTemplateDTO {
    private int templateID;
    private int busTypeID;
    private String seatNumber;
    private String seatType;
    private String position;
    private int row;
    private int column;

    public SeatTemplateDTO() {
    }

    public SeatTemplateDTO(int busTypeID, String seatNumber, String seatType, String position, int row, int column) {
        this.busTypeID = busTypeID;
        this.seatNumber = seatNumber;
        this.seatType = seatType;
        this.position = position;
        this.row = row;
        this.column = column;
    }

    public int getTemplateID() {
        return templateID;
    }

    public void setTemplateID(int templateID) {
        this.templateID = templateID;
    }

    public int getBusTypeID() {
        return busTypeID;
    }

    public void setBusTypeID(int busTypeID) {
        this.busTypeID = busTypeID;
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
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
    
    
}
