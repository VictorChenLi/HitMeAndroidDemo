package org.stl.hitme.goal.ui;

import org.stl.hitme.R;
import org.stl.hitme.sysUtil.ui.ExperienceBar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;

public class GoalListTestActivity extends Activity {
	
	private ExperienceBar mProgress;
	private Button btn_go;
	
	private Handler mHandler;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.goallistitemtest); 

		
		findView();
		addListener();
		
		mProgress.setTitle("Loss the Weight");
		mProgress.setBothProgress(40, 70);
		
		
		mHandler = new Handler() 
		{
			public void handleMessage(Message msg) {
				mProgress.setProgress(msg.what);
				super.handleMessage(msg);
			}
		};
		
		
	}
	
	private void findView() {
		btn_go = (Button) findViewById(R.id.btn_start);
		mProgress = (ExperienceBar) findViewById(R.id.progressBar1);
	}
	
	private void addListener() {

		btn_go.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				new Thread(new Runnable() {
					public void run() {
						for (int i = 0; i <= 50; i++) {
							mHandler.sendEmptyMessage(i * 2);
							try {
								Thread.sleep(80);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}

				}).start();
			}
		});
	}
}
