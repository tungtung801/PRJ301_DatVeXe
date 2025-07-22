/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Tung Nguyen
 */
public class BusTypeDTO {
    private int BusTypeID;
    private String TypeName;
    private int SeatCount;
    private String Category;
    private String Description;

    public BusTypeDTO() {
    }

    public BusTypeDTO(String TypeName, int SeatCount, String Category, String Description) {
        this.TypeName = TypeName;
        this.SeatCount = SeatCount;
        this.Category = Category;
        this.Description = Description;
    }

    public int getBusTypeID() {
        return BusTypeID;
    }

    public void setBusTypeID(int BusTypeID) {
        this.BusTypeID = BusTypeID;
    }

    public String getTypeName() {
        return TypeName;
    }

    public void setTypeName(String TypeName) {
        this.TypeName = TypeName;
    }

    public int getSeatCount() {
        return SeatCount;
    }

    public void setSeatCount(int SeatCount) {
        this.SeatCount = SeatCount;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String Category) {
        this.Category = Category;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }
    
    
}
