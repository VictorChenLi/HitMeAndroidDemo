package org.stl.hitme.todo.ui;

import org.stl.hitme.storeCat.service.DBAccessImpl;
import org.stl.hitme.sysUtil.model.Constant;
import org.stl.hitme.sysUtil.service.PlayAlarmMusic;
import org.stl.hitme.taskMgr.service.TaskMgr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;

public class AlertActivity extends FragmentActivity {

	private int taskId;
	private AlertActivity self;
	private String alertType;
	private boolean viewFlag=false;
	private DialogFragment newFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		taskId=getIntent().getIntExtra("taskId", 0);
		alertType=getIntent().getStringExtra("alertType");
		viewFlag=getIntent().getBooleanExtra("viewFlag",false);
		self=this;
		showAlertFragment();
		//only started by thread need play the music and close automatically
		if(!viewFlag)
		{
			playAlertMusic();
			//alert will stop within 1 minute
			new Handler().postDelayed(new Runnable() {

				public void run() {
					newFragment.dismiss();
					stopAlertMusic();
				} 
	    		
	    	}, 1*60*1000);
		}
	}
	
	private void playAlertMusic()
	{
		Intent alertMusicIntent= new Intent(AlertActivity.this,PlayAlarmMusic.class);
		startService(alertMusicIntent);
	}
	private void stopAlertMusic()
	{
		Intent alertMusicIntent= new Intent(self,PlayAlarmMusic.class);
		self.stopService(alertMusicIntent);
	}
	
	private void showAlertFragment()
	{
		if(alertType.equals(Constant.alertType.STARTALERT.toString()))
		{
			newFragment = new StartAlertFragment();
			Bundle args=new Bundle();
			args.putInt("taskId", taskId);
			newFragment.setArguments(args);
	        newFragment.show(self.getSupportFragmentManager(), "Start Alert");
		}
		if(alertType.equals(Constant.alertType.COMPLETEALERT.toString()))
		{
			newFragment = new CompleteAlertFragment();
			Bundle args=new Bundle();
			args.putInt("taskId", taskId);
			newFragment.setArguments(args);
	        newFragment.show(self.getSupportFragmentManager(), "Start Alert");
		}
		if(alertType.equals(Constant.alertType.OVERDUEALERT.toString()))
		{
			newFragment = new OverDueFragment();
			Bundle args=new Bundle();
			args.putInt("taskId", taskId);
			newFragment.setArguments(args);
	        newFragment.show(self.getSupportFragmentManager(), "Start Alert");
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if(!viewFlag)
			stopAlertMusic();
	}
	
	

}
