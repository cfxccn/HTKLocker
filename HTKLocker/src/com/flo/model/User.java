package com.flo.model;

import java.util.Date;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;
import com.lidroid.xutils.db.annotation.Unique;

@Table(name = "USER")
public class User {

	@Id
	private int id;

	@Unique
	@Column(column = "NAME")
	private String name;

	@Column(column = "ISTRAINED")
	private Boolean isTrained;

	@Column(column = "TRAINTIME")
	private Date trainTime;
	
	@Column(column = "TESTTIME")
	private Date testTime;
	
	
	
	public Date getTrainTime() {
		return trainTime;
	}

	public void setTrainTime(Date trainTime) {
		this.trainTime = trainTime;
	}

	public Date getTestTime() {
		return testTime;
	}

	public void setTestTime(Date testTime) {
		this.testTime = testTime;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNameId() {
		return "id"+id;		
	}

	public Boolean getIsTrained() {
		return isTrained;
	}

	public void setIsTrained(Boolean isTrained) {
		this.isTrained = isTrained;
	}
}
