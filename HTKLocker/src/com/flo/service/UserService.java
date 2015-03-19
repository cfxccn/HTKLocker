package com.flo.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Context;

import com.flo.model.User;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
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
	
	public List<User> getTrainedUserList() {
		List<User> userList =null;
		try {
			userList = db.findAll(Selector.from(User.class)
                    .where("ISTRAINED", "=", true));
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
		FileService fileService = new FileService(context);

		try {
		//	db.deleteById(User.class, user.getId());
			db.delete(user);
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
		user.setTrainTime(new Date());

		try {
			db.update(user, "ISTRAINED");
			db.update(user, "TRAINTIME");

		} catch (DbException e) {
		}
	}
}
