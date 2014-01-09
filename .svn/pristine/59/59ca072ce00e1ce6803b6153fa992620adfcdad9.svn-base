package org.stl.hitme.stage.ui;

import java.util.Calendar;

import org.stl.hitme.R;
import org.stl.hitme.storeCat.model.Goal;
import org.stl.hitme.storeCat.model.Stage;
import org.stl.hitme.storeCat.service.DBAccessImpl;
import org.stl.hitme.sysUtil.model.Constant;
import org.stl.hitme.sysUtil.service.TimeHelper;
import org.stl.hitme.sysUtil.ui.TimePickerFragment;
import org.stl.hitme.sysUtil.ui.TimePickerFragment.PickerType;

import android.app.Activity;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.os.Bundle;
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
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class StageEditActivity extends FragmentActivity {
	
	private StageEditActivity demo;
	private EditText et_name;
	private EditText et_content;
	private EditText et_deadline;
	private EditText et_startTime;
	private EditText et_stageValue;
	private EditText et_investTime;
	private TextView tv_goalTitle;
	private TableRow tr_stageValue;
	private Button btn_done;
	private Button btn_next;
	private Boolean isFirstTime=true;
	private int goalId;
	private Goal goal;
	private DBAccessImpl dbAccessImpl;
	private Boolean isEditFlag=false;
	private int stageId=0;
	
	private void findView()
	{
		et_name=(EditText) findViewById(R.id.et_Name);
		et_content=(EditText) findViewById(R.id.et_Content);
		et_deadline=(EditText) findViewById(R.id.et_deadline);
		et_stageValue=(EditText) findViewById(R.id.et_stageValue);
		et_investTime=(EditText) findViewById(R.id.et_investTime);
		tv_goalTitle=(TextView) findViewById(R.id.tv_goalTitle);
		btn_done=(Button) findViewById(R.id.btn_done);
		btn_next=(Button) findViewById(R.id.btn_next);
		et_startTime=(EditText) findViewById(R.id.et_startTime);
		tr_stageValue = (TableRow) findViewById(R.id.tr_stageValue);
	}
	
	private void fillData()
	{
		Stage stage = dbAccessImpl.describeStage(stageId);
		et_name.setText(stage.getTitle());
		et_content.setText(stage.getContent());
		et_deadline.setText(TimeHelper.getStrTimeFromMillis(stage.getEndTime(), "yyyy/MM/dd"));
		et_stageValue.setText(String.valueOf(stage.getStageValue()));
		et_investTime.setText(String.valueOf(TimeHelper.getPlainStrFromTimeMillis(stage.getInvestTime())));
		et_startTime.setText(TimeHelper.getStrTimeFromMillis(stage.getStartTime(), "yyyy/MM/dd"));
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
		btn_done.setOnClickListener(new OnClickListener()
		{

			public void onClick(View arg0) {
				saveStage();
				Toast.makeText(demo, et_name.getText().toString()+" will saved", Toast.LENGTH_LONG).show();
				Intent intent =new Intent(demo,StageListActivity.class);
				intent.putExtra("goalId", goalId);
				intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				demo.startActivity(intent);
				demo.finish();
			}
			
		});
		btn_next.setOnClickListener(new OnClickListener()
		{

			public void onClick(View arg0) {
				saveStage();
				Toast.makeText(demo, et_name.getText().toString()+" will saved and going to add another one", Toast.LENGTH_LONG).show();
				Intent intent =new Intent(demo,StageEditActivity.class);
				intent.putExtra("goalId", goalId);
				intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				demo.startActivity(intent);
				demo.finish();
			}
			
		});
		et_deadline.setOnFocusChangeListener(new OnFocusChangeListener(){

			public void onFocusChange(View arg0, boolean isOnFocus) {
				if(isOnFocus)
				{
					TimePickerFragment newFragment = new TimePickerFragment();
					newFragment.setType(TimePickerFragment.PickerType.DATE);
					OnDateSetListener listener = TimeHelper.getDataSetListener(new EditText[]{et_deadline});
					newFragment.setDateListener(listener);
	                newFragment.show(getSupportFragmentManager(), "DatePicker");
				}
				
			}
			
		});
		
	}
	
	public void initial()
	{
		Calendar c = Calendar.getInstance();
		et_startTime.setText(TimeHelper.getStrTimeFromMillis(c.getTimeInMillis(), "yyyy/MM/dd"));
		//add judge the value of GoalType
		goal = dbAccessImpl.describeGoal(goalId);
		if(goal.getGoalType().equals(Constant.goalType.UNQUANIFIABLE.toString()))
			tr_stageValue.setVisibility(View.GONE);
		tv_goalTitle.setText(goal.getTitle());
	}
	
	private void saveStage()
	{
		Stage stage = new Stage();
		if(isEditFlag)
			stage=dbAccessImpl.describeStage(stageId);
		stage.setContent(et_content.getText().toString());
		stage.setEndTime(TimeHelper.getTimeInMillis(et_deadline.getText().toString()));
		stage.setGoalID(goalId);
		stage.setInvestTime(TimeHelper.getPlainTimeInMillis(et_investTime.getText().toString()));
		if(goal.getGoalType().equals(Constant.goalType.QUANIFIABLE.toString()))
			stage.setStageValue(Integer.valueOf(et_stageValue.getText().toString()));
		else
		{
			//if the goal type is unquanifiable, add this stage value(investTime) to the goal value
			stage.setStageValue(stage.getInvestTime());
			goal.plusGoalValue(dbAccessImpl, stage.getStageValue());
		}
		stage.setStartTime(TimeHelper.getTimeInMillis(et_startTime.getText().toString()));
		stage.setTitle(et_name.getText().toString());
		stage.setStageStatus(Constant.stageStatus.RUN.toString());
		if(isEditFlag)
			dbAccessImpl.UpdateStage(stage);
		else
			dbAccessImpl.InsertStage(stage);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stageedit_activity);
		demo=this;
		goalId=this.getIntent().getIntExtra("goalId", 0);
		stageId=this.getIntent().getIntExtra("stageId", 0);
		isEditFlag=this.getIntent().getBooleanExtra("isEditFlag", false);
		dbAccessImpl=DBAccessImpl.getInstance(demo);
		findView();
		addListener();
		initial();
		if(isEditFlag)
			fillData();
	}
	
}
