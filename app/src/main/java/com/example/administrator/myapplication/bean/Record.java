package com.example.administrator.myapplication.bean;

import android.text.TextUtils;

/**
 * Created by Administrator on 2017/7/17.
 */

public class Record {
    public static final int TYPE_PARENT=1;
    public static final int TYPE_CHILD=2;
    private int viewType;
    private boolean isExpand=false;
    private String name;

    private String id;
    private boolean isNewRecord;
    private String createDate;
    private String updateDate;
    private String currentStep;
    private String formId;
    private String stepStartDate;
    private String stepEndDate;
    private int stepTime;
    private String result;

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public boolean isExpand() {
        return isExpand;
    }

    public void setExpand(boolean expand) {
        isExpand = expand;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isNewRecord() {
        return isNewRecord;
    }

    public void setNewRecord(boolean newRecord) {
        isNewRecord = newRecord;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getCurrentStep() {
        return currentStep;
    }

    public void setCurrentStep(String currentStep) {
        this.currentStep = currentStep;
    }

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public String getStepStartDate() {
        if (TextUtils.isEmpty(stepStartDate)){
            return  "";
        }
        return stepStartDate;
    }

    public void setStepStartDate(String stepStartDate) {
        this.stepStartDate = stepStartDate;
    }

    public String getStepEndDate() {
        if (TextUtils.isEmpty(stepEndDate)){
            return  "";
        }
        return stepEndDate;
    }

    public void setStepEndDate(String stepEndDate) {
        this.stepEndDate = stepEndDate;
    }

    public int getStepTime() {
        return stepTime;
    }

    public void setStepTime(int stepTime) {
        this.stepTime = stepTime;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
