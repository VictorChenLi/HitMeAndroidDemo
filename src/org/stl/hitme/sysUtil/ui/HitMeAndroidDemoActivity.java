package org.stl.hitme.sysUtil.ui;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import org.stl.hitme.R;
import org.stl.hitme.storeCat.model.TaskAttributes;
import org.stl.hitme.sysUtil.model.Constant;
import org.stl.hitme.sysUtil.service.ExecutorServiceHelper;
import org.stl.hitme.taskMgr.model.RunTask;
import org.stl.hitme.taskMgr.model.StartAlertTask;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("ShowToast")
public class HitMeAndroidDemoActivity extends FragmentActivity  {
    /** Called when the activity is first created. */
	private static HitMeAndroidDemoActivity demo;
    private EditText hourText; 
    private EditText minuteText;
    private EditText contentText; 
    private TextView dataView;
    private Button setBtn;
    private Button startBtn;
    
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        demo=this;
        hourText = (EditText) findViewById(R.id.edit_hour);
        minuteText= (EditText) findViewById(R.id.edit_minute);
        contentText = (EditText) findViewById(R.id.edit_content);
        dataView = (TextView) findViewById(R.id.dataView);
        setBtn = (Button) findViewById(R.id.set_alarm);
        startBtn = (Button) findViewById(R.id.start_alarm);
        setBtn.setOnClickListener(new OnClickListener() { 
        public void onClick(View v) { 
            	DialogFragment newFragment = new TimePickerFragment();
                newFragment.show(getSupportFragmentManager(), "timePicker");
        	}
        });
        startBtn.setOnClickListener(new OnClickListener() { 
            public void onClick(View v) { 
            		String str_hour = dataView.getText().toString().split(":")[0];
            		String str_minu = dataView.getText().toString().split(":")[1];
            		if(str_hour.isEmpty()||str_minu.isEmpty())
            		{
            			Toast.makeText(demo, "Please Input the Time!", Toast.LENGTH_LONG).show();
            		}
                    Calendar curTime = Calendar.getInstance();
                    Calendar setTime = Calendar.getInstance();
                    setTime.set(Calendar.HOUR_OF_DAY, Integer.valueOf(str_hour));
                    setTime.set(Calendar.MINUTE, Integer.valueOf(str_minu));
                    setTime.set(Calendar.SECOND, 0);
                    long delay = setTime.getTimeInMillis()-curTime.getTimeInMillis();
            		
            		Toast.makeText(demo, "The delay time is:"+String.valueOf(delay), Toast.LENGTH_LONG).show();
            		Log.d("test", "The delay time is:"+String.valueOf(delay));
            		TaskAttributes taskitem = new TaskAttributes(0, contentText.getText().toString(), contentText.getText().toString(), delay, 0, 0, Constant.taskStatus.RUN.toString(), delay, curTime.getTimeInMillis(),setTime.getTimeInMillis(),0);
            		
            		RunTask commonTask = new StartAlertTask(demo, taskitem);
            		ExecutorServiceHelper.schedule(commonTask, delay,TimeUnit.MILLISECONDS);
            		
            	}
        });
    }
}