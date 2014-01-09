package org.stl.hitme.todo.ui;

import java.util.Calendar;

import org.stl.hitme.R;
import org.stl.hitme.storeCat.model.TaskAttributes;
import org.stl.hitme.storeCat.service.DBAccessImpl;
import org.stl.hitme.sysUtil.model.Constant;
import org.stl.hitme.sysUtil.model.GlobalVar;
import org.stl.hitme.sysUtil.service.TimeHelper;
import org.stl.hitme.taskMgr.service.TaskMgr;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class StartAlertFragment extends DialogFragment {

	private static final int delayTime=1000*60*5;
	private int taskId;
	private StartAlertFragment self;
	private TaskAttributes task;
	private TaskMgr taskMgr;
	private boolean operFlag=false;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		taskId=getArguments().getInt("taskId");
		task=DBAccessImpl.getInstance(getActivity()).describeTask(taskId);
		self=this;
		taskMgr=new TaskMgr(getActivity());
		this.setStyle(DialogFragment.STYLE_NO_TITLE, 0);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.startalert_fragment, container, false);
		TextView tv_taskName=(TextView) v.findViewById(R.id.tv_taskName);
		tv_taskName.setText(task.getTitle());
		TextView tv_taskContent=(TextView) v.findViewById(R.id.tv_taskContent);
		tv_taskContent.setText(task.getContent());
		TextView tv_taskStartTime=(TextView) v.findViewById(R.id.tv_taskStartTime);
		if(Constant.taskType.ALLDAY.isContain(task.getTaskType()))
		{
			tv_taskStartTime.setText(R.string.str_allday);
		}
		else
			tv_taskStartTime.setText(TimeHelper.getStrTimeFromMillis(task.getStartTime(), "HH:mm"));
		TextView tv_taskSpendTime=(TextView) v.findViewById(R.id.tv_taskSpendTime);
		tv_taskSpendTime.setText((task.getSpendTime()/(1000*60))+"M");
		Button btn_delay =(Button) v.findViewById(R.id.btn_delay);
		Button btn_start =(Button) v.findViewById(R.id.btn_start);
		Button btn_dismiss =(Button) v.findViewById(R.id.btn_dismiss);
		btn_delay.setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) {
				taskMgr.delayTask(task, delayTime);
				Toast.makeText(getActivity(), "The task will be delay 5 minute", Toast.LENGTH_LONG).show();
				operFlag=true;
				self.dismiss();
			}
			
		});
		btn_start.setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) {
				taskMgr.startTask(task);
				Toast.makeText(getActivity(), "The task will be start!", Toast.LENGTH_LONG).show();
				operFlag=true;
				self.dismiss();
			}
			
		});
		btn_dismiss.setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) {
				taskMgr.dismissTask(task);
				Toast.makeText(getActivity(), "The task will be dismiss!", Toast.LENGTH_LONG).show();
				operFlag=true;
				self.dismiss();
			}
			
		});
		return v;
		
	}

	@Override
	public void onDetach() {
		// if user do nothing and exist
		if(!operFlag&&task.getAlarmTime()<=Calendar.getInstance().getTimeInMillis())
			taskMgr.overdueTask(task);
		super.onDetach();
		GlobalVar.todoAdapter.refreshAdapter();
		getActivity().finish();
	}
	
	
	

}
