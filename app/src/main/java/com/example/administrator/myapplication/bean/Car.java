package com.example.administrator.myapplication.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/7/14.
 */

public class Car implements Serializable {
    private String carId;
    private boolean isNewRecord;
    private boolean isSelect;
    private String carType;
    private String carBrand;
    private String expectStartDate;
    private String carVin;
    private String submitPhotos;
    private int testNum;

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public boolean isNewRecord() {
        return isNewRecord;
    }

    public void setNewRecord(boolean newRecord) {
        isNewRecord = newRecord;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getCarBrand() {
        return carBrand;
    }

    public void setCarBrand(String carBrand) {
        this.carBrand = carBrand;
    }

    public String getExpectStartDate() {
        return expectStartDate;
    }

    public void setExpectStartDate(String expectStartDate) {
        this.expectStartDate = expectStartDate;
    }

    public String getCarVin() {
        return carVin;
    }

    public void setCarVin(String carVin) {
        this.carVin = carVin;
    }

    public String getSubmitPhotos() {
        return submitPhotos;
    }

    public void setSubmitPhotos(String submitPhotos) {
        this.submitPhotos = submitPhotos;
    }

    public int getTestNum() {
        return testNum;
    }

    public void setTestNum(int testNum) {
        this.testNum = testNum;
    }
}
