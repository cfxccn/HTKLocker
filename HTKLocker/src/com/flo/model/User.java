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

	@Column(column = "IS_TRAINED")
	private Boolean isTrained;

	@Column(column = "TRAIN_TIME")
	private Date trainTime;
	
	@Column(column = "LAST_VERIFY_TIME")
	private Date lastVerifyTime;
	
	@Column(column = "QUESTION")
	private String question;
	
	@Column(column = "THRESHOLD")
	private String threshold;
	
	public String getThreshold() {
		return threshold;
	}

	public void setThreshold(String threshold) {
		this.threshold = threshold;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public Date getTrainTime() {
		return trainTime;
	}

	public Date getLastVerifyTime() {
		return lastVerifyTime;
	}

	public void setLastVerifyTime(Date lastVerifyTime) {
		this.lastVerifyTime = lastVerifyTime;
	}

	public void setTrainTime(Date trainTime) {
		this.trainTime = trainTime;
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
