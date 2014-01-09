package org.stl.hitme.todo.ui;

import org.stl.hitme.R;
import org.stl.hitme.gaming.service.PrizeMgr;
import org.stl.hitme.storeCat.model.TaskAttributes;
import org.stl.hitme.storeCat.service.DBAccessImpl;
import org.stl.hitme.sysUtil.model.GlobalVar;
import org.stl.hitme.sysUtil.service.TimeHelper;
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

public class OverDueFragment extends DialogFragment {
	private int taskId;
	private OverDueFragment self;
	private TaskAttributes task;
	private TaskMgr taskMgr;
	
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
		View v = inflater.inflate(R.layout.overduealert_fragment, container, false);
		TextView tv_taskName=(TextView) v.findViewById(R.id.tv_taskName);
		tv_taskName.setText(task.getTitle());
		TextView tv_taskContent=(TextView) v.findViewById(R.id.tv_taskContent);
		tv_taskContent.setText(task.getContent());
		TextView tv_taskStartTime=(TextView) v.findViewById(R.id.tv_taskStartTime);
		tv_taskStartTime.setText(TimeHelper.getStrTimeFromMillis(task.getStartTime(), "HH:mm"));
		TextView tv_taskSpendTime=(TextView) v.findViewById(R.id.tv_taskSpendTime);
		tv_taskSpendTime.setText((task.getSpendTime()/(1000*60))+"M");
		Button btn_startNow =(Button) v.findViewById(R.id.btn_startNow);
		Button btn_haveCompleted =(Button) v.findViewById(R.id.btn_haveCompleted);
		Button btn_dismiss =(Button) v.findViewById(R.id.btn_dismiss);
		btn_startNow.setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) {
				taskMgr.startTask(task);
				Toast.makeText(getActivity(), "The task will be start Right Now", Toast.LENGTH_LONG).show();
				self.dismiss();
			}
			
		});
		btn_haveCompleted.setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) {
				taskMgr.finishTask(task);
				//TODO show the prize
				PrizeMgr.showPrize(getActivity());
				GlobalVar.goalListAdapter.refreshAdapter();
				Toast.makeText(getActivity(), "The task will be count!", Toast.LENGTH_LONG).show();
				self.dismiss();
			}
			
		});
		btn_dismiss.setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) {
				taskMgr.dismissTask(task);
				Toast.makeText(getActivity(), "The task will be dismiss!", Toast.LENGTH_LONG).show();
				self.dismiss();
			}
			
		});
		return v;
		
	}

	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		super.onDetach();
		GlobalVar.todoAdapter.refreshAdapter();
		getActivity().finish();
	}
}
