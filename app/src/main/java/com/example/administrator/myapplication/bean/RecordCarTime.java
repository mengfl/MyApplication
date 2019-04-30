package com.example.administrator.myapplication.bean;

/**
 * Created by Administrator on 2017/8/18.
 */

public class RecordCarTime {
    private String cinId;
    private int row;
    private String testStartDate;

    public String getCinId() {
        return cinId;
    }

    public void setCinId(String cinId) {
        this.cinId = cinId;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public String getTestStartDate() {
        return testStartDate;
    }

    public void setTestStartDate(String testStartDate) {
        this.testStartDate = testStartDate;
    }
}
