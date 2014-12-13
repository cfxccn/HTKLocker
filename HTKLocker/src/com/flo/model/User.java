package com.flo.model;

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
