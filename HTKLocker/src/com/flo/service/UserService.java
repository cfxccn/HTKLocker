package com.flo.service;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.flo.model.User;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;

public class UserService {
	Context context;
	DbUtils db;

	public UserService(Context context) {
		this.context = context;
		db = DbUtils.create(this.context);
	}

	public List<User> getUserList() {
		List<User> userList = new ArrayList<User>();
		try {
			userList = db.findAll(User.class);
		} catch (DbException e) {
		}
		return userList;
	}

	public boolean addUser(User user) {
		try {
			db.save(user);
			return true;
		} catch (DbException e) {
			return false;
		}
	}

	public boolean deleteUser(User user) {
		try {
			db.delete(user);
			FileService fileService = new FileService(context);
			fileService.deleteUser(user.getNameId());
			return true;
		} catch (DbException e) {
			return false;
		}
	}

	public void trainUser(int id) {
		User user = new User();
		user.setId(id);
		user.setIsTrained(true);
		try {
			db.update(user, "ISTRAINED");
		} catch (DbException e) {
		}
	}
}
