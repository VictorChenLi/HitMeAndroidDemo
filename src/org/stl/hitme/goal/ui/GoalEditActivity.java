package org.stl.hitme.goal.ui;

import java.util.Calendar;

import org.stl.hitme.R;
import org.stl.hitme.storeCat.model.Goal;
import org.stl.hitme.storeCat.service.DBAccessImpl;
import org.stl.hitme.sysUtil.model.Constant;
import org.stl.hitme.sysUtil.model.GlobalVar;
import org.stl.hitme.sysUtil.service.TimeHelper;
import org.stl.hitme.sysUtil.ui.MainTabActivity;
import org.stl.hitme.sysUtil.ui.TimePickerFragment;

import android.app.Activity;
import android.app.DatePickerDialog.OnDateSetListener;
//import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler.Callback;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

public class GoalEditActivity extends FragmentActivity  {
	
	private GoalEditActivity demo;
	private EditText et_name;
	private EditText et_content;
	private EditText et_finishTime;
	private EditText et_startTime;
	private Button btn_save;
	private RadioGroup rb_group;
	private TableRow tr_targetValue;
	private TableRow tr_goalType;
	private EditText et_targetvalue;
	private Boolean isFirstTime=true;
	private Boolean isEditFlag=false;
	private int goalId=0;
	private String goalType=Constant.goalType.UNQUANIFIABLE.toString();
	private DBAccessImpl dbAccessImpl;
	
	
	private void findView()
	{
		et_name=(EditText) findViewById(R.id.et_Name);
		et_content=(EditText) findViewById(R.id.et_Content);
		et_finishTime=(EditText) findViewById(R.id.et_finishTime);
		et_startTime=(EditText) findViewById(R.id.et_startTime);
		btn_save=(Button) findViewById(R.id.btn_save);
		rb_group=(RadioGroup) findViewById(R.id.rg_quantifiable);
		tr_targetValue=(TableRow) findViewById(R.id.tr_targetValue);
		tr_goalType = (TableRow) findViewById(R.id.tr_goalType);
		et_targetvalue=(EditText) findViewById(R.id.et_targetvalue);
		
		//only provide unquanifiable goal type by now
		tr_targetValue.setVisibility(View.GONE);
		tr_goalType.setVisibility(View.GONE);
	}
	
	private void fillData(int goalId)
	{
		Goal goal = dbAccessImpl.describeGoal(goalId);
		et_name.setText(goal.getTitle());
		et_content.setText(goal.getContent());
		et_finishTime.setText(TimeHelper.getStrTimeFromMillis(goal.getEndTime(), "yyyy/MM/dd"));
		et_startTime.setText(TimeHelper.getStrTimeFromMillis(goal.getStartTime(), "yyyy/MM/dd"));
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
		btn_save.setOnClickListener(new OnClickListener()
		{

			public void onClick(View arg0) {
				saveGoal();
				Toast.makeText(demo, et_name.getText().toString()+" will saved", Toast.LENGTH_LONG).show();
				GlobalVar.goalListAdapter.refreshAdapter();
				Intent intent = new Intent(demo,MainTabActivity.class);
				intent.putExtra("tabIndex", Constant.tabIndex.GOAL.ordinal());
				demo.startActivity(intent);
				demo.finish();
			}
			
		});
		et_finishTime.setOnFocusChangeListener(new OnFocusChangeListener(){

			public void onFocusChange(View arg0, boolean isOnFocus) {
				if(isOnFocus)
				{
					TimePickerFragment newFragment = new TimePickerFragment();
					newFragment.setType(TimePickerFragment.PickerType.DATE);
					OnDateSetListener listener = new OnDateSetListener(){

						public void onDateSet(DatePicker view, int year,
								int monthOfYear, int dayOfMonth) {
							monthOfYear++;
							et_finishTime.setText(year+"/"+monthOfYear+"/"+dayOfMonth);
						}};
					newFragment.setDateListener(listener);
	                newFragment.show(getSupportFragmentManager(), "DatePicker");
				}
				
			}
			
		});
		et_startTime.setText(TimeHelper.getStrTimeFromMillis(Calendar.getInstance().getTimeInMillis(), "yyyy/MM/dd"));
		et_startTime.setOnFocusChangeListener(new OnFocusChangeListener(){

			public void onFocusChange(View arg0, boolean isOnFocus) {
				if(isOnFocus)
				{
					TimePickerFragment newFragment = new TimePickerFragment();
					newFragment.setType(TimePickerFragment.PickerType.DATE);
					OnDateSetListener listener = TimeHelper.getDataSetListener(new EditText[]{et_startTime});
					newFragment.setDateListener(listener);
	                newFragment.show(getSupportFragmentManager(), "DatePicker");
				}
				
			}
			
		});
		rb_group.setOnCheckedChangeListener(new OnCheckedChangeListener(){

			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if(checkedId==R.id.rb_no)
				{
					tr_targetValue.setVisibility(View.GONE);
					goalType=Constant.goalType.UNQUANIFIABLE.toString();
				}
				if(checkedId==R.id.rb_yes)
				{
					tr_targetValue.setVisibility(View.VISIBLE);
					goalType=Constant.goalType.QUANIFIABLE.toString();
				}
			}
		});
	}
	
	private void saveGoal()
	{
		Goal goal = new Goal();
		if(isEditFlag)
			goal=dbAccessImpl.describeGoal(goalId);
		goal.setTitle(et_name.getText().toString());
		goal.setContent(et_content.getText().toString());
		goal.setStartTime(TimeHelper.getTimeInMillis(et_startTime.getText().toString()));
		goal.setEndTime(TimeHelper.getTimeInMillis(et_finishTime.getText().toString()));
		goal.setGoalStatus(Constant.goalStatus.RUN.toString());
		goal.setGoalType(goalType);
		if(!et_targetvalue.getText().toString().equals(""))
			goal.setGoalValue(Integer.valueOf(et_targetvalue.getText().toString()));
		if(isEditFlag)
			dbAccessImpl.UpdateGoal(goal);
		else
			dbAccessImpl.InsertGoal(goal);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.goaledit_activity);
		demo=this;
		dbAccessImpl=DBAccessImpl.getInstance(demo);
		findView();
		addListener();
		isEditFlag=getIntent().getBooleanExtra("isEditFlag", false);
		goalId=getIntent().getIntExtra("goalId", 0);
		if(isEditFlag)
			fillData(goalId);
	}
	
}
