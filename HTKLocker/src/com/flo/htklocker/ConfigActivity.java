package com.flo.htklocker;

import com.flo.service.KVService;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class ConfigActivity extends Activity {
	KVService kVService;
	SeekBar seekBar_Threshold;
	TextView textView_Threshold;
	int threshold;

	private void bindView() {
		seekBar_Threshold = (SeekBar) findViewById(R.id.seekBar_Threshold);
		threshold=kVService.getThreshold();
		seekBar_Threshold.setProgress(threshold);
		textView_Threshold = (TextView) findViewById(R.id.textView_Threshold);
		textView_Threshold.setText(" "+threshold);
	}

	private void bindListener() {
		seekBar_Threshold
				.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
					@Override
					public void onStopTrackingTouch(SeekBar arg0) {
						kVService.setThreshold(threshold);
					}
					@Override
					public void onStartTrackingTouch(SeekBar arg0) {
					}
					@Override
					public void onProgressChanged(SeekBar arg0, int arg1,
							boolean arg2) {
						threshold = arg1;
						textView_Threshold.setText(" " + threshold);
					}
				});
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_config);
		kVService=new KVService(getApplicationContext());
		getActionBar().setDisplayHomeAsUpEnabled(true);
		bindView();
		bindListener();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == android.R.id.home) {
			finish();
		}
		return super.onOptionsItemSelected(item);
	}
}
