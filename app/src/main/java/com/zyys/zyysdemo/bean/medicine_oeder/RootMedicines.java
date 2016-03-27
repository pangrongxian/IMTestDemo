package com.zyys.zyysdemo.bean.medicine_oeder;

import java.util.List;

/**
 * Created by Administrator on 2016/3/13.
 */
public class RootMedicines {

    private String appointment_info;
    private String appointment_id;
    private String type_advisory;
    private String book_time;
    private String relation;
    private String record_name;
    private String patient_name;

    private List<DrugEntity> drug;//array

    //Msg medicineMsg = new Msg(record_name,relation,patient_name,book_time,type,drug,Msg.TYPE_OTHER);

    public RootMedicines(
            String record_name,
            String relation,
            String patient_name,
            String book_time,
            String type_advisory,
            List<DrugEntity> drug) {

        this.type_advisory = type_advisory;
        this.book_time = book_time;
        this.relation = relation;
        this.record_name = record_name;
        this.patient_name = patient_name;
        this.drug = drug;
    }

    public String getAppointment_info() {
        return appointment_info;
    }

    public void setAppointment_info(String appointment_info) {
        this.appointment_info = appointment_info;
    }

    public String getAppointment_id() {
        return appointment_id;
    }

    public void setAppointment_id(String appointment_id) {
        this.appointment_id = appointment_id;
    }

    public String getType_advisory() {
        return type_advisory;
    }

    public void setType_advisory(String type_advisory) {
        this.type_advisory = type_advisory;
    }

    public String getBook_time() {
        return book_time;
    }

    public void setBook_time(String book_time) {
        this.book_time = book_time;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getRecord_name() {
        return record_name;
    }

    public void setRecord_name(String record_name) {
        this.record_name = record_name;
    }

    public String getPatient_name() {
        return patient_name;
    }

    public void setPatient_name(String patient_name) {
        this.patient_name = patient_name;
    }

    public List<DrugEntity> getDrug() {
        return drug;
    }

    public void setDrug(List<DrugEntity> drug) {
        this.drug = drug;
    }



    @Override
    public String toString() {
        return "RootMedicines{" +
                "appointment_info='" + appointment_info + '\'' +
                ", appointment_id='" + appointment_id + '\'' +
                ", type_advisory='" + type_advisory + '\'' +
                ", book_time='" + book_time + '\'' +
                ", relation='" + relation + '\'' +
                ", record_name='" + record_name + '\'' +
                ", patient_name='" + patient_name + '\'' +
                ", drug=" + drug +
                '}';
    }
}
