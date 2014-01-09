package org.stl.hitme.todo.ui;

import java.util.Calendar;

import org.stl.hitme.R;
import org.stl.hitme.storeCat.model.Goal;
import org.stl.hitme.storeCat.model.Stage;
import org.stl.hitme.storeCat.model.TaskAttributes;
import org.stl.hitme.storeCat.service.DBAccessImpl;
import org.stl.hitme.sysUtil.model.Constant;
import org.stl.hitme.sysUtil.model.GlobalVar;
import org.stl.hitme.sysUtil.service.TimeHelper;
import org.stl.hitme.sysUtil.ui.ListSelecterFragment;
import org.stl.hitme.sysUtil.ui.MainTabActivity;
import org.stl.hitme.sysUtil.ui.TimePickerFragment;
import org.stl.hitme.sysUtil.ui.TimePickerFragment.PickerType;
import org.stl.hitme.taskMgr.service.TaskMgr;

import android.app.Activity;
//import android.app.DialogFragment;
import android.app.DatePickerDialog.OnDateSetListener;
//import android.app.Fragment;
//import android.app.FragmentTransaction;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class TaskEditActivity extends FragmentActivity  {
	
	private TaskEditActivity demo;
	private EditText et_Name;
	private EditText et_content;
	private EditText et_startTime;
	private EditText et_alarmTime;
	private EditText et_investTime;
	private EditText et_stageName;
	private TextView tv_stageId;
	private TableRow tr_startTime;
	private TableRow tr_alarmTime;
	private TableRow tr_investTime;
	private RadioGroup rg_spendTime;
	private RadioButton rb_fiftyMinute;
	private CheckBox cb_allday;
	private CheckBox cb_routine;
	private CheckBox cb_auto;
	private Button btn_done;
	private boolean isFirstTime=true;
	private int taskType=0;
	private boolean isEditFlag=false;
	private int taskId=0;
	private TaskMgr taskMgr;
	private DBAccessImpl dbAccessImpl;
	
	private void findviews()
	{
		et_Name = (EditText) findViewById(R.id.et_Name);
		et_content = (EditText) findViewById(R.id.et_Content);
		et_startTime = (EditText) findViewById(R.id.et_startTime);
		et_alarmTime = (EditText) findViewById(R.id.et_alarmTime);
		et_investTime =(EditText) findViewById(R.id.et_investTime);
		et_stageName = (EditText) findViewById(R.id.et_stageName);
		tv_stageId = (TextView) findViewById(R.id.tv_stageId);
		tr_startTime = (TableRow) findViewById(R.id.tr_startTime);
		tr_alarmTime = (TableRow) findViewById(R.id.tr_alarmTime);
		tr_investTime = (TableRow) findViewById(R.id.tr_investTime);
		rg_spendTime = (RadioGroup) findViewById(R.id.rg_investTime);
		rb_fiftyMinute = (RadioButton) findViewById(R.id.rb_fiftyMinute);
		btn_done = (Button) findViewById(R.id.btn_done);
		cb_allday = (CheckBox) findViewById(R.id.cb_allday);
		cb_routine = (CheckBox) findViewById(R.id.cb_routine);
		cb_auto = (CheckBox) findViewById(R.id.cb_auto);
		tr_startTime.setVisibility(View.GONE);
		tr_alarmTime.setVisibility(View.GONE);
//		tr_investTime.setVisibility(View.GONE);
	}
	
	private void fillData()
	{
		TaskMgr taskMgr= new TaskMgr(demo);
		TaskAttributes task = taskMgr.getTask(taskId);
		Stage stage = dbAccessImpl.describeStage(task.getStageID());
		if(Constant.taskType.ALLDAY.isContain(task.getTaskType()))
			cb_allday.setChecked(true);
		else
			cb_allday.setChecked(false);
		if(Constant.taskType.AUTO.isContain(task.getTaskType()))
			cb_auto.setChecked(true);
		if(Constant.taskType.ROUTINE.isContain(task.getTaskType()))
			cb_routine.setChecked(true);
		
		et_Name.setText(task.getTitle());
		et_content.setText(task.getContent());
		et_startTime.setText(TimeHelper.getStrTimeFromMillis(task.getStartTime(),TimeHelper.STDTIMEFORMAT));
		et_alarmTime.setText(TimeHelper.getStrTimeFromMillis(task.getAlarmTime(),TimeHelper.STDTIMEFORMAT));
		et_investTime.setText(TimeHelper.getPlainStrFromTimeMillis(task.getSpendTime()));
		et_stageName.setText(stage.getTitle());
		tv_stageId.setText(String.valueOf(stage.getId()));
		taskType=task.getTaskType();
	}
	
	private void initial()
	{
		taskMgr = new TaskMgr(demo);
		dbAccessImpl = DBAccessImpl.getInstance(demo);
		isEditFlag=this.getIntent().getBooleanExtra("isEditFlag", false);
		taskId = this.getIntent().getIntExtra("taskId", 0);
		et_alarmTime.setText(TimeHelper.MAXDATASTR);
		et_startTime.setText(TimeHelper.MAXDATASTR);
		taskType = Constant.taskType.ALLDAY.add(taskType);
	}
	
	private void addListener()
	{
		et_content.setOnKeyListener(new OnKeyListener(){
			
			public boolean onKey(View arg0, int arg1, KeyEvent arg2) {
				isFirstTime=false;
				return false;
			}
			
		});
		et_content.setOnFocusChangeListener(new OnFocusChangeListener()
		{

			public void onFocusChange(View view, boolean isFocus) {
				EditText et =(EditText)view;
				if(isFirstTime&&isFocus)
				{
					et.setText("");
				}
				if(!isFocus&&et.getText().toString().equals(""))
				{
					et.setText(R.string.smart_word);
				}
			}
			
		});
		et_startTime.setOnFocusChangeListener(new OnFocusChangeListener(){

			public void onFocusChange(View arg0, boolean isOnFocus) {
				if(isOnFocus)
				{
					TimePickerFragment newFragment = new TimePickerFragment();
					newFragment.setType(TimePickerFragment.PickerType.TIME);
					OnTimeSetListener listener = TimeHelper.getTimeSetListener(new EditText[]{et_startTime,et_alarmTime});
					newFragment.setTimeListener(listener);
	                newFragment.show(getSupportFragmentManager(), "TimePicker");
				}
				
			}
			
		});
		et_alarmTime.setOnFocusChangeListener(new OnFocusChangeListener(){

			public void onFocusChange(View arg0, boolean isOnFocus) {
				if(isOnFocus)
				{
					TimePickerFragment newFragment = new TimePickerFragment();
					newFragment.setType(TimePickerFragment.PickerType.TIME);
					OnTimeSetListener listener = TimeHelper.getTimeSetListener(new EditText[]{et_alarmTime});
					newFragment.setTimeListener(listener);
	                newFragment.show(getSupportFragmentManager(), "TimePicker");
				}
				
			}
			
		});
		et_stageName.setOnFocusChangeListener(new OnFocusChangeListener(){

			public void onFocusChange(View arg0, boolean isOnFocus) {
				if(isOnFocus)
				{
					DialogFragment newFragment = new ListSelecterFragment();
					Bundle args=new Bundle();
					args.putString("listItemType", Constant.listItemType.GOAL.toString());
					newFragment.setArguments(args);
	                newFragment.show(getSupportFragmentManager(), "Select a Stage");
				}
			}
		});
		rg_spendTime.setOnCheckedChangeListener(new OnCheckedChangeListener(){

			public void onCheckedChanged(RadioGroup group, int checkedId) {
				RadioButton rb = (RadioButton)group.findViewById(checkedId);
				et_investTime.setText(rb.getText().toString());
			}
		});
		cb_allday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if(isChecked)
				{
					tr_startTime.setVisibility(View.GONE);
					tr_alarmTime.setVisibility(View.GONE);
					et_alarmTime.setText(TimeHelper.MAXDATASTR);
					et_startTime.setText(TimeHelper.MAXDATASTR);
					taskType=Constant.taskType.ALLDAY.add(taskType);
				}
				else
				{
					tr_startTime.setVisibility(View.VISIBLE);
					tr_alarmTime.setVisibility(View.VISIBLE);
					et_alarmTime.setText("");
					et_startTime.setText("");
					taskType=Constant.taskType.ALLDAY.del(taskType);
				}
			}
		});
		cb_routine.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if(isChecked)
					taskType=Constant.taskType.ROUTINE.add(taskType);
				else
					taskType=Constant.taskType.ROUTINE.del(taskType);
			}
		});
		cb_auto.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if(isChecked)
				{
					taskType=Constant.taskType.AUTO.add(taskType);
					if(!cb_allday.isChecked())
					{
						//TODO auto arrange the start and finish time
					}
				}
				else
				{
					taskType=Constant.taskType.AUTO.del(taskType);
				}
			}
		});
		btn_done.setOnClickListener(new OnClickListener()
		{

			public void onClick(View arg0) {
				
				saveTask();
				Intent intent = new Intent(demo,MainTabActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				intent.putExtra("tabIndex", Constant.tabIndex.TODO.ordinal());
				demo.startActivity(intent);
				demo.finish();
			}
			
		});
	}
	
	private void saveTask()
	{
		TaskAttributes task = new TaskAttributes();
		if(isEditFlag)
			task= taskMgr.getTask(taskId);
		TextView tv_stageId = (TextView) demo.findViewById(R.id.tv_stageId);
		task.setAlarmTime(TimeHelper.getTimeInMillis(et_alarmTime.getText().toString()));
		task.setContent(et_content.getText().toString());
		task.setEndTime(TimeHelper.getTimeInMillis(et_startTime.getText().toString())+TimeHelper.getPlainTimeInMillis(et_investTime.getText().toString()));
		task.setSpendTime(TimeHelper.getPlainTimeInMillis(et_investTime.getText().toString()));
		task.setStageID(Integer.valueOf(tv_stageId.getText().toString()));
		task.setStartTime(TimeHelper.getTimeInMillis(et_startTime.getText().toString()));
		task.setTaskStatus(Constant.taskStatus.RUN.toString());
		if(Constant.taskType.ALLDAY.isContain(taskType))
			task.setTaskStatus(Constant.taskStatus.WAIT.toString());
		task.setTitle(et_Name.getText().toString());
		task.setTaskType(taskType);
		TaskMgr taskMgr= new TaskMgr(demo);
		taskMgr.createTask(task);
		GlobalVar.todoAdapter.refreshAdapter();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.taskedit_activity);
		demo=this;
		findviews();
		initial();
		addListener();
		if(isEditFlag)
			fillData();
	}

}
