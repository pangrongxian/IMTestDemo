package com.zyys.zyysdemo.bean;

/**
 * Created by Administrator on 2016/2/27.
 */
public class OrderListForm {

    /**
     * id : 82
     * name : 钟马马
     * drug : 维他命C（一盒）
     * phone : 18620113249
     * to : 种马马
     * remark : 顺丰
     * money : 1
     * has_pay : 0
     * created_at : 2016-02-24 16:45:05
     * doctor_name : 马专家
     * commission : 20
     * record_name : 大雄
     * relation : 票
     * address : 信义路24号4栋336
     * voipAccount : 8002293600000043
     */

    private int id;
    private String name;
    private String drug;
    private String phone;
    private String to;
    private String remark;
    private String money;
    private int has_pay;
    private String created_at;
    private String doctor_name;
    private String commission;
    private String record_name;
    private String relation;
    private String address;
    private String voipAccount;


    public OrderListForm(String name, String created_at, String doctor_name) {
        this.name = name;
        this.created_at = created_at;
        this.doctor_name = doctor_name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDrug(String drug) {
        this.drug = drug;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public void setHas_pay(int has_pay) {
        this.has_pay = has_pay;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public void setDoctor_name(String doctor_name) {
        this.doctor_name = doctor_name;
    }

    public void setCommission(String commission) {
        this.commission = commission;
    }

    public void setRecord_name(String record_name) {
        this.record_name = record_name;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setVoipAccount(String voipAccount) {
        this.voipAccount = voipAccount;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDrug() {
        return drug;
    }

    public String getPhone() {
        return phone;
    }

    public String getTo() {
        return to;
    }

    public String getRemark() {
        return remark;
    }

    public String getMoney() {
        return money;
    }

    public int getHas_pay() {
        return has_pay;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getDoctor_name() {
        return doctor_name;
    }

    public String getCommission() {
        return commission;
    }

    public String getRecord_name() {
        return record_name;
    }

    public String getRelation() {
        return relation;
    }

    public String getAddress() {
        return address;
    }

    public String getVoipAccount() {
        return voipAccount;
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "created_at='" + created_at + '\'' +
                ", name='" + name + '\'' +
                ", doctor_name='" + doctor_name + '\'' +
                '}';
    }
}
