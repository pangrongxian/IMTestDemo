package com.zyys.zyysdemo.bean;

import com.zyys.zyysdemo.bean.medicine_oeder.DrugEntity;

import java.io.Serializable;
import java.util.List;

public class Msg implements Serializable{

	public static final int TYPE_RECEIVED = 0;

	public static final int TYPE_SENT = 1;

	public static final int TYPE_OTHER = 2;

	private String content;

	private String record_name;
	private String relation;
	private String patient_name;
	private String appointment_id;
	private String book_time;
	private String type_advisory;
	private List<DrugEntity> drug;


	private int type;

	public Msg(int type) {
		this.type = type;
	}

	public Msg(String content) {
		this.content = content;
	}

	public Msg(String content, int type) {
		this.content = content;
		this.type = type;
	}

	//构造
	public Msg(String record_name,
			   String relation,
			   String patient_name,
			   String book_time,
			   String type_advisory,
			   String appointment_id,
			   List<DrugEntity> drug,
			   int type) {
		this.record_name = record_name;
		this.relation = relation;
		this.patient_name = patient_name;
		this.book_time = book_time;
		this.type_advisory = type_advisory;
		this.drug = drug;
		this.type = type;
		this.appointment_id = appointment_id;
	}

	public String getContent() {
		return content;
	}

	public int getType() {
		return type;
	}

	public String getRecord_name() {
		return record_name;
	}

	public void setRecord_name(String record_name) {
		this.record_name = record_name;
	}

	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}

	public String getPatient_name() {
		return patient_name;
	}

	public void setPatient_name(String patient_name) {
		this.patient_name = patient_name;
	}

	public String getBook_time() {
		return book_time;
	}

	public void setBook_time(String book_time) {
		this.book_time = book_time;
	}

	public String getType_advisory() {
		return type_advisory;
	}

	public void setType_advisory(String type_advisory) {
		this.type_advisory = type_advisory;
	}

	public List<DrugEntity> getDrug() {
		return drug;
	}

	public void setDrug(List<DrugEntity> drug) {
		this.drug = drug;
	}

	public String getAppointment_id() {
		return appointment_id;
	}

	public void setAppointment_id(String appointment_id) {
		this.appointment_id = appointment_id;
	}

	@Override
	public String toString() {
		return content;
	}
}
