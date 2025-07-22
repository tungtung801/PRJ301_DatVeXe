/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Tung Nguyen
 */
public class BusDTO {

    private int BusID;
    private String BusNumber;
    private String BusName;
    private int BusTypeID;
    private String Description;
    private String Status;
    private String BusTypeName; // Thêm thuộc tính này để dùng cho các jsp hiển thị

    public BusDTO() {
    }

    public BusDTO(String BusNumber, String BusName, int BusTypeID, String Description, String Status, String BusTypeName) {
        this.BusNumber = BusNumber;
        this.BusName = BusName;
        this.BusTypeID = BusTypeID;
        this.Description = Description;
        this.Status = Status;
        this.BusTypeName = BusTypeName;
    }

    public BusDTO(String BusNumber, String BusName, int BusTypeID, String Description, String Status) {
        this.BusNumber = BusNumber;
        this.BusName = BusName;
        this.BusTypeID = BusTypeID;
        this.Description = Description;
        this.Status = Status;
    }



    public int getBusID() {
        return BusID;
    }

    public void setBusID(int BusID) {
        this.BusID = BusID;
    }

    public String getBusNumber() {
        return BusNumber;
    }

    public void setBusNumber(String BusNumber) {
        this.BusNumber = BusNumber;
    }

    public String getBusName() {
        return BusName;
    }

    public void setBusName(String BusName) {
        this.BusName = BusName;
    }

    public int getBusTypeID() {
        return BusTypeID;
    }

    public void setBusTypeID(int BusTypeID) {
        this.BusTypeID = BusTypeID;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public String getBusTypeName() {
        return BusTypeName;
    }

    public void setBusTypeName(String BusTypeName) {
        this.BusTypeName = BusTypeName;
    }

    
}
