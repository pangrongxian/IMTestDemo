package com.zyys.zyysdemo.bean;

/**
 * Created by Administrator on 2016/2/26.
 * 订单
 */
public class OrderForm {

    private String people;
    private String phone;
    private String address;
    private String medicine;//药物
    private String price;
    private String brokerage;//佣金
    private String remark;//备注

    public OrderForm(String people, String phone, String address, String medicine, String price, String brokerage, String remark) {
        this.people = people;
        this.phone = phone;
        this.address = address;
        this.medicine = medicine;
        this.price = price;
        this.brokerage = brokerage;
        this.remark = remark;
    }

    public String getPeople() {
        return people;
    }

    public void setPeople(String people) {
        this.people = people;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMedicine() {
        return medicine;
    }

    public void setMedicine(String medicine) {
        this.medicine = medicine;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getBrokerage() {
        return brokerage;
    }

    public void setBrokerage(String brokerage) {
        this.brokerage = brokerage;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "OrderForm{" +
                "people='" + people + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", medicine='" + medicine + '\'' +
                ", price='" + price + '\'' +
                ", brokerage='" + brokerage + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
