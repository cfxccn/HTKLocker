package com.flo.service;

import android.content.Context;

import com.flo.model.KeyValue;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;

public class KVService {
	DbUtils db;

	public KVService(Context context) {
		db = DbUtils.create(context);
	}

	public boolean setThreshold(int val) {
		KeyValue keyValue = new KeyValue();
		keyValue.setKey("THRESHOLD");
		keyValue.setValue("" + val);
		try {
			db.delete(KeyValue.class, WhereBuilder.b("KEY", "=", "THRESHOLD"));
			db.save(keyValue);
			return true;
		} catch (DbException e) {
			return false;
		}
	}

	public int getThreshold() {
		KeyValue keyValue = null;
		try {
			keyValue = db.findFirst(Selector.from(KeyValue.class).where("KEY",
					"=", "THRESHOLD"));
			if (keyValue == null) {
				return 50;
			}
			return Integer.valueOf(keyValue.getValue());
		} catch (DbException e) {
			return 50;
		}

	}
}
