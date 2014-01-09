package org.stl.hitme.todo.ui;

import java.util.Calendar;

import org.stl.hitme.R;
import org.stl.hitme.gaming.service.PrizeMgr;
import org.stl.hitme.storeCat.model.TaskAttributes;
import org.stl.hitme.storeCat.service.DBAccessImpl;
import org.stl.hitme.sysUtil.model.Constant;
import org.stl.hitme.sysUtil.model.GlobalVar;
import org.stl.hitme.sysUtil.service.TimeHelper;
import org.stl.hitme.sysUtil.ui.ListSelecterFragment;
import org.stl.hitme.taskMgr.service.TaskMgr;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class CompleteAlertFragment extends DialogFragment {
	private static final int delayTime=1000*60*5;
	private int taskId;
	private CompleteAlertFragment self;
	private TaskAttributes task;
	private TaskMgr taskMgr;
	private boolean operFlag=false;
	private boolean isDelParent=true;
	
	private TextView tv_taskName;
	private TextView tv_taskContent;
	private TextView tv_taskStartTime;
	private TextView tv_taskSpendTime;
	private Button btn_extend;
	private Button btn_complete;
	private Button btn_dismiss;
	
	
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
	
	private void findView(View v)
	{
		tv_taskName=(TextView) v.findViewById(R.id.tv_taskName);
		tv_taskContent=(TextView) v.findViewById(R.id.tv_taskContent);
		tv_taskStartTime=(TextView) v.findViewById(R.id.tv_taskStartTime);
		tv_taskSpendTime=(TextView) v.findViewById(R.id.tv_taskSpendTime);
		btn_extend =(Button) v.findViewById(R.id.btn_extend);
		btn_complete =(Button) v.findViewById(R.id.btn_complete);
		btn_dismiss =(Button) v.findViewById(R.id.btn_dismiss);
	}
	
	private void init()
	{
		tv_taskName.setText(task.getTitle());
		tv_taskContent.setText(task.getContent());
		tv_taskStartTime.setText(TimeHelper.getStrTimeFromMillis(task.getEndTime(), "HH:mm"));
		if(Constant.taskType.ALLDAY.isContain(task.getTaskType()))
			tv_taskStartTime.setText(R.string.str_allday);
		tv_taskSpendTime.setText((task.getSpendTime()/(1000*60))+"M");
	}
	
	private void addListener()
	{
		btn_extend.setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) {
				taskMgr.extendTask(task, delayTime);
				Toast.makeText(getActivity(), "The task will be delay 5 minute", Toast.LENGTH_LONG).show();
				operFlag=true;
				self.dismiss();
			}
			
		});
		btn_complete.setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) {
				taskMgr.finishTask(task);
				Toast.makeText(getActivity(), "Task: "+task.getTitle()+" Complete!", Toast.LENGTH_LONG).show();
				operFlag=true;
				//TODO show the prize
				PrizeMgr.showPrize(getActivity());
				isDelParent=false;
				self.dismiss();
			}
			
		});
		btn_dismiss.setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) {
				//TODO remind weather del the routine
				
				taskMgr.dismissTask(task);
				Toast.makeText(getActivity(), "The task will be dismiss!", Toast.LENGTH_LONG).show();
				operFlag=true;
				self.dismiss();
			}
			
		});
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.completealert_fragment, container, false);
		findView(v);
		init();
		addListener();
		return v;
		
	}
	
	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		if(!operFlag&&task.getEndTime()<=Calendar.getInstance().getTimeInMillis())
			taskMgr.overdueTask(task);
		super.onDetach();
		GlobalVar.todoAdapter.refreshAdapter();
		if(isDelParent)
			getActivity().finish();
	}
}
