package org.stl.hitme.goal.ui;

import org.stl.hitme.R;
import org.stl.hitme.goal.service.GoalListAdapter;
import org.stl.hitme.stage.ui.StageListActivity;
import org.stl.hitme.sysUtil.model.GlobalVar;
import org.stl.hitme.sysUtil.model.Constant.viewModel;
import org.stl.hitme.sysUtil.ui.ExperienceBar;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class GoalListActivity extends Activity {
	
	private ListView listview;
	private Button btn_add;
	private GoalListActivity demo;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.goallist_activity); 
		demo=this;
		findView();
		init();
	}
	
	private void findView()
	{
		listview=(ListView)findViewById(R.id.goalList);
		btn_add=(Button)findViewById(R.id.btn_add);
	}
	
	private void init()
	{
		btn_add.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				goalCreateActivityJump();
			}
		});
		initalGoalList();
	}
	
	private void initalGoalList()
	{
		GoalListAdapter adapter;
		if(null==GlobalVar.goalListAdapter)
		{
			GlobalVar.goalListAdapter = new GoalListAdapter(this);
		}
		
		adapter=GlobalVar.goalListAdapter;
		listview.setAdapter(adapter);
		listview.setItemsCanFocus(false);
		listview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		listview.setOnItemLongClickListener(new OnItemLongClickListener(){


			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				ExperienceBar expBar = (ExperienceBar)arg1.findViewById(R.id.expBar_item);
				int goalId=expBar.getItemId();
				goalEditActivityJump(goalId);
				Toast.makeText(demo, "Edit "+expBar.getTitle(), Toast.LENGTH_LONG).show();
				return true;
			}
			
		});
		listview.setOnItemClickListener(new OnItemClickListener(){

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				ExperienceBar expBar = (ExperienceBar)arg1.findViewById(R.id.expBar_item);
//				TextView tv_goalId = (TextView)arg1.findViewById(R.id.tv_goalid);
//				int goalId=Integer.valueOf(tv_goalId.getText().toString());
				int goalId=expBar.getItemId();
				stageListActivityJump(goalId);
				Toast.makeText(demo, expBar.getTitle()+" was selected", Toast.LENGTH_LONG).show();
			}});
		adapter.notifyDataSetChanged();
	}
	
	public void goalCreateActivityJump()
	{
		Intent intent = new Intent(this,GoalEditActivity.class);
		this.startActivity(intent);
	}
	
	public void goalEditActivityJump(int goalId)
	{
		Intent intent = new Intent(this,GoalEditActivity.class);
		intent.putExtra("isEditFlag", true);
		intent.putExtra("goalId", goalId);
		this.startActivity(intent);
	}
	
	private void stageListActivityJump(int goalId)
	{
		Intent intent = new Intent(demo,StageListActivity.class);
		intent.putExtra("goalId", goalId);
		this.startActivity(intent);
	}
}
