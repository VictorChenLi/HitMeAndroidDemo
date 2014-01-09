package org.stl.hitme.sysUtil.service;

import java.util.Calendar;

import org.stl.hitme.R;
import org.stl.hitme.gaming.service.PrizeListAdapter;
import org.stl.hitme.goal.service.GoalListAdapter;
import org.stl.hitme.sysUtil.model.GlobalVar;
import org.stl.hitme.sysUtil.model.Constant.viewModel;
import org.stl.hitme.taskMgr.model.RunTask;
import org.stl.hitme.taskMgr.service.TaskMgr;
import org.stl.hitme.todo.service.ToDoListAdapter;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

public class HitMeService extends Service {
	
	private HitMeService demo;
	private final String overNightJobTag="OVERNIGHTJOB";

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void taskJobs()
	{
		TaskMgr taskMgr=new TaskMgr(demo);
		taskMgr.resumeAllTask();
	}
	
	//To start today's tasks
	public void overNightJobs()
	{
		Calendar zeroClock = Calendar.getInstance();
		zeroClock.set(Calendar.DAY_OF_YEAR, Calendar.getInstance().get(Calendar.DAY_OF_YEAR)+1);
		zeroClock.set(Calendar.HOUR, 0);
		zeroClock.set(Calendar.MINUTE, 0);
		zeroClock.set(Calendar.SECOND, 0);
		zeroClock.set(Calendar.MILLISECOND, 0);
		long delay = zeroClock.getTimeInMillis()-Calendar.getInstance().getTimeInMillis();
		TaskMgr.runTask(new RunTask(){

			public void run() {
				TaskMgr taskMgr=new TaskMgr(demo);
				taskMgr.resumeAllTask();
				
			}
			
		}, overNightJobTag, delay);
	}
	
	public void initGlobalVar()
	{
		viewModel goalListViewModel = SettingHelper.getGoalListViewModel(this);
		GlobalVar.globalService=this;
		
		if(null==GlobalVar.prizeAdapter)
		{
			GlobalVar.prizeAdapter = new PrizeListAdapter(this);
			GlobalVar.prizeAdapter.refreshAdapter();
		}
		if(null==GlobalVar.todoAdapter)
		{
			GlobalVar.todoAdapter = new ToDoListAdapter(this);
		}
		if(null==GlobalVar.goalListAdapter)
		{
			GlobalVar.goalListAdapter = new GoalListAdapter(this);
		}
		GlobalVar.goalListAdapter.setViewType(goalListViewModel);
	}
	
	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		super.onStart(intent, startId);
		initGlobalVar();
	}

	@Override
	public void onCreate(){
		demo=this;
		Toast.makeText(this, "HitMe Service is Created", Toast.LENGTH_LONG).show();
		taskJobs();
		overNightJobs();
	}

}
