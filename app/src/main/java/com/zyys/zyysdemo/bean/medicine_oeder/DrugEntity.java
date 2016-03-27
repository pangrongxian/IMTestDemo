package com.zyys.zyysdemo.bean.medicine_oeder;

import java.util.List;

/**
 * Created by Administrator on 2016/3/13.
 */
public class DrugEntity {

    private String interval;
    private String mediaclName;
    private List<NumberEntity> numbers;//array

    private String productName;
    private String remark;
    private String unit;


    public DrugEntity(String interval, String mediaclName, List<NumberEntity> numbers) {
        this.interval = interval;
        this.mediaclName = mediaclName;
        this.numbers = numbers;
    }

    public DrugEntity(List<NumberEntity> numbers) {
        this.numbers = numbers;
    }

    public DrugEntity(String interval, String mediaclName) {
        this.interval = interval;
        this.mediaclName = mediaclName;
        this.numbers = numbers;
    }

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public String getMediaclName() {
        return mediaclName;
    }

    public void setMediaclName(String mediaclName) {
        this.mediaclName = mediaclName;
    }

    public List<NumberEntity> getNumbers() {
        return numbers;
    }

    public void setNumbers(List<NumberEntity> numbers) {
        this.numbers = numbers;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
