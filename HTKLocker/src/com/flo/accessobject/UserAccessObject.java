package com.flo.accessobject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Context;

import com.flo.accessobject.FileAccessObject;
import com.flo.model.User;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;

public class UserAccessObject {
	Context context;
	DbUtils db;

	
	
	static UserAccessObject singleton=null;

	public static UserAccessObject getInstance(Context context) {
		if (singleton == null) {
			singleton = new UserAccessObject(context);
			return singleton;
		} else
			return singleton;
	}
	
	
	private UserAccessObject(Context context) {
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
		List<User> userList =new ArrayList<User>();
		try {
			userList = db.findAll(Selector.from(User.class)
                    .where("IS_TRAINED", "=", true));
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
		//FileService fileService = new FileService(context);
		FileAccessObject fileAccessObject=FileAccessObject.getInstance(context);
		try {
		//	db.deleteById(User.class, user.getId());
			db.delete(user);
			fileAccessObject.deleteUser(user.getNameId());
			return true;
		} catch (DbException e) {
			return false;
		}
	}

	public void trainUser(int id, String question) {
		User user = new User();
		user.setId(id);
		user.setIsTrained(true);
		user.setTrainTime(new Date());
		user.setQuestion(question);
		try {
			db.update(user, "IS_TRAINED");
			db.update(user, "TRAIN_TIME");
			db.update(user, "QUESTION");
		} catch (DbException e) {
		}
	}
	public void verifyUser(int id){
		User user = new User();
		user.setId(id);
		user.setLastVerifyTime(new Date());
		try {
			db.update(user, "LAST_VERIFY_TIME");
		} catch (DbException e) {
		}
	}
}
