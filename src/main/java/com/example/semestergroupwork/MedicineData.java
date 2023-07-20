package com.example.semestergroupwork;
/*
    TEAM NAME: AZTECH
    1. BAWAH ABUBA IBRAHIM - 10863084 - PROGRAMMER
    2. BAWAH MANSURA-10911156- DATABASE ADMIN
    3. GYENI PRISCILLA- 10865576-PROJECT MANAGER
    4. HAYIBOR CINDY -10905039 -UI/UX DESIGNER
    5. SAMUEL AMPONSAH - 10922693 - UI/UX DESIGNER

 */
import java.util.Date;

public class MedicineData {
    private Integer medicineID;
    private String brandName;
    private String productName;
    private Double price;
    private String status;
    private Date expiryDate;

    public MedicineData(int medicineID, String brandName, String productName, Double price, String status, Date expiryDate) {
        this.medicineID = medicineID;
        this.brandName = brandName;
        this.productName = productName;
        this.price = price;
        this.status = status;
        this.expiryDate = expiryDate;
    }

    public int getMedicineID() {
        return medicineID;
    }

    public String getBrandName() {
        return brandName;
    }

    public String getProductName() {
        return productName;
    }

    public Double getPrice() {
        return price;
    }

    public String getStatus() {
        return status;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }
}
