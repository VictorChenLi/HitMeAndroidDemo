package org.stl.hitme.stage.ui;

import java.util.Calendar;

import org.stl.hitme.R;
import org.stl.hitme.goal.ui.GoalEditActivity;
import org.stl.hitme.stage.service.StageListAdapter;
import org.stl.hitme.storeCat.model.Goal;
import org.stl.hitme.storeCat.service.DBAccessImpl;
import org.stl.hitme.sysUtil.ui.ExperienceBar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemLongClickListener;

public class StageListActivity extends Activity {

	private StageListActivity demo;
	private TextView tv_deadline;
	private TextView tv_goalContent;
	private ListView listview;
	private Button btn_add;
	private DBAccessImpl dbAccessImpl;
	private int goalId;
	
	private void findView()
	{
		tv_deadline = (TextView) findViewById(R.id.tv_dayTodealline);
		tv_goalContent=(TextView) findViewById(R.id.tv_goalcontent);
		listview = (ListView) findViewById(R.id.stageList);
		btn_add = (Button) findViewById(R.id.btn_add);
	}
	
	private void addListener()
	{
		btn_add.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				Intent intent =new Intent(demo,StageEditActivity.class);
				intent.putExtra("goalId", goalId);
				demo.startActivity(intent);
			}
			
		});
		listview.setOnItemLongClickListener(new OnItemLongClickListener(){


			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				ExperienceBar expBar = (ExperienceBar)arg1.findViewById(R.id.expBar_item);
				int stageId=expBar.getItemId();
				EditActivityJump(stageId);
				Toast.makeText(demo, "Edit "+expBar.getTitle(), Toast.LENGTH_LONG).show();
				return true;
			}
			
		});
	}
	
	public void EditActivityJump(int stageId)
	{
		Intent intent = new Intent(this,StageEditActivity.class);
		intent.putExtra("isEditFlag", true);
		intent.putExtra("stageId", stageId);
		intent.putExtra("goalId", goalId);
		this.startActivity(intent);
	}
	
	
	
	//Initial the views of this Activity
	private void init()
	{
		dbAccessImpl = DBAccessImpl.getInstance(demo);
		Goal goal = dbAccessImpl.describeGoal(goalId);
		long diffDay=(goal.getEndTime()-Calendar.getInstance().getTimeInMillis())/(24L * 60 * 60 * 1000); 
		String str_remain=goal.getTitle()+", Remain "+diffDay+" to Dealine!";
		tv_deadline.setText(str_remain);
		tv_goalContent.setText(goal.getContent());
		initialStageList(goalId);
	}
	
	//initial the data on ListView
	private void initialStageList(int goalId)
	{
		
		StageListAdapter adapter= new StageListAdapter(this,goalId);
		listview.setAdapter(adapter);
		listview.setItemsCanFocus(false);
		listview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stagelist_activity);
		demo=this;
		goalId = this.getIntent().getExtras().getInt("goalId");
		findView();
		init();
		addListener();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		init();
	}
	
}
