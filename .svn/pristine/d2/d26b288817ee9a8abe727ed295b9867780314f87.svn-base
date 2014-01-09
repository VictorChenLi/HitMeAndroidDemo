package org.stl.hitme.todo.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.stl.hitme.R;
import org.stl.hitme.storeCat.model.Goal;
import org.stl.hitme.storeCat.model.TaskAttributes;
import org.stl.hitme.storeCat.service.DBAccessImpl;
import org.stl.hitme.sysUtil.model.Constant;
import org.stl.hitme.sysUtil.service.TimeHelper;
import org.stl.hitme.todo.ui.AlertActivity;
import org.stl.hitme.todo.ui.TaskEditActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemLongClickListener;

public class ToDoListAdapter extends BaseAdapter {
	
	private Context demo;
	private LayoutInflater mInflater;    
    private List<TaskAttributes> mData;
    private DBAccessImpl dbAccessImpl;
    private int dataSize=30;
    private long maxDate=0;
    private Map<Integer,Boolean> dataFlagMap= new HashMap<Integer,Boolean>();
    private Map<Integer,Boolean> overDueFlagMap= new HashMap<Integer,Boolean>();
	
    public static class ViewHolder 
    {
    	TextView dataView;
    	TextView taskId;
    	TextView taskName;
    	TextView taskStatus;
    	TextView taskType;
    	TextView startTime;
    	TextView taskContent;
    	TextView spendTime;
    	RelativeLayout layout_listItem;
    	RelativeLayout layout_taskDetail;
    	RelativeLayout layout_taskInfo;
    	LinearLayout layout_dataView;
    	public void showDetail()
    	{
    		layout_taskDetail.setVisibility(View.VISIBLE);
    	}
    	public int getId()
    	{
    		return Integer.valueOf(taskId.getText().toString());
    	}
    }
    
	public ToDoListAdapter(Context context) {
		super();
		demo=context;
		dbAccessImpl=DBAccessImpl.getInstance(context);
        mInflater = LayoutInflater.from(context);    
        fillDataSet(dataSize);   
	}
	
	private void fillDataSet(int size) 
	{
		dataSize=size;
		dataFlagMap.clear();
		maxDate=0;
    	mData=dbAccessImpl.queryRunableTaskList(size);
//		mData=new ArrayList<TaskAttributes>();
//		Calendar cur = Calendar.getInstance();
//		for(int i=1;i<size;i++)
//		{
//			String strContent="This is "+i+"Row";
//			TaskAttributes task = new TaskAttributes();
//			task.setId(i);
//			task.setStartTime(cur.getTimeInMillis());
//			task.setContent(strContent);
//			task.setTitle(strContent);
//			task.setSpendTime(i*1000*60);
//			mData.add(task);
//			cur.add(Calendar.MINUTE, 1);
//		}
		for(TaskAttributes task:mData)
		{
			if(task.getEndTime()<Calendar.getInstance().getTimeInMillis())
				overDueFlagMap.put(task.getId(), true);
			else
				overDueFlagMap.put(task.getId(), false);
			if(TimeHelper.compareTimeFromMillis(task.getStartTime(), maxDate, Calendar.YEAR)>=0&&
					TimeHelper.compareTimeFromMillis(task.getStartTime(), maxDate, Calendar.DAY_OF_YEAR)>0)
			{
				dataFlagMap.put(task.getId(), true);
				maxDate=task.getStartTime();
			}
			else
			{
				dataFlagMap.put(task.getId(), false);
			}
		}
	}
	
	public void refreshAdapter(int size)
	{
		fillDataSet(size);
		this.notifyDataSetChanged();
	}
	
	public void refreshAdapter()
	{
		fillDataSet(dataSize);
		this.notifyDataSetChanged();
	}

	public int getCount() {
		return mData.size();
	}

	public Object getItem(int position) {
		return mData.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(convertView==null)
		{
			convertView = mInflater.inflate(R.layout.todolistitem, null);
			holder = this.findViewHolder(convertView);
		}
		else
		{
			holder = (ViewHolder) convertView.getTag();
		}
		this.fillViewHolder(holder, mData.get(position));
		adjustViewHolder(holder,mData.get(position));
		convertView.setTag(holder);
		return convertView;
	}
	
	private ViewHolder findViewHolder(View convertView)
	{
		ViewHolder holder=new ViewHolder();
		holder.taskId=(TextView) convertView.findViewById(R.id.tv_taskId);
		holder.taskName=(TextView) convertView.findViewById(R.id.tv_taskName);
		holder.taskStatus=(TextView) convertView.findViewById(R.id.tv_taskStatus);
		holder.startTime=(TextView) convertView.findViewById(R.id.tv_startTime);
		holder.taskType=(TextView) convertView.findViewById(R.id.tv_taskType);
		holder.taskContent=(TextView) convertView.findViewById(R.id.tv_taskContent);
		holder.spendTime=(TextView) convertView.findViewById(R.id.tv_spendTime);
		holder.dataView=(TextView) convertView.findViewById(R.id.tv_dataView);
		holder.layout_listItem=(RelativeLayout) convertView.findViewById(R.id.layout_listItem);
		holder.layout_taskDetail=(RelativeLayout) convertView.findViewById(R.id.layout_taskDetail);
		holder.layout_dataView=(LinearLayout) convertView.findViewById(R.id.layout_dataView);
		holder.layout_taskInfo=(RelativeLayout) convertView.findViewById(R.id.layout_taskInfo);
		return holder;
	}
	
	private void fillViewHolder(ViewHolder holder,TaskAttributes task)
	{
		holder.taskId.setText(String.valueOf(task.getId()));
		holder.taskName.setText(task.getTitle());
		holder.startTime.setText(TimeHelper.getStrTimeFromMillis(task.getStartTime(), "HH:mm"));
		holder.taskType.setText("");
		if(Constant.taskType.ALLDAY.isContain(task.getTaskType()))
		{
			holder.startTime.setText("");
			holder.taskType.setText("[AllDay]");
		}
		if(Constant.taskType.ROUTINE.isContain(task.getTaskType()))
		{
			holder.taskType.setText(holder.taskType.getText()+"[Routine]");
		}
		holder.taskContent.setText(task.getContent().subSequence(0, task.getContent().length()>70?70:task.getContent().length()));
		holder.spendTime.setText(TimeHelper.getStrTimeFromMillis(task.getSpendTime(),"mm"));
		holder.dataView.setText(TimeHelper.getWordTimeFromMillis(task.getStartTime()));
		holder.layout_taskDetail.setVisibility(View.GONE);
		holder.layout_dataView.setVisibility(View.GONE);
	}
	
	private void adjustViewHolder(ViewHolder holder,TaskAttributes task)
	{
		final int taskId = task.getId();
		final String taskTitle = task.getTitle();
		if(dataFlagMap.get(task.getId())==true)
		{
			holder.layout_dataView.setVisibility(View.VISIBLE);
		}
		else
		{
			holder.layout_dataView.setVisibility(View.GONE);
		}
		
		holder.layout_taskInfo.setBackgroundColor(Color.BLACK);
		holder.layout_listItem.setOnLongClickListener(new OnLongClickListener(){

			public boolean onLongClick(View v) {
				EditTaskActivityJump(taskId);
				Toast.makeText(demo, "Edit "+taskTitle, Toast.LENGTH_LONG).show();
				return true;
			}

			
		});
		
		//UNDO
		if(task.getTaskStatus().equals(Constant.taskStatus.RUN.toString())
				||task.getTaskStatus().equals(Constant.taskStatus.WAIT.toString())
				||task.getTaskStatus().equals(Constant.taskStatus.DELAY.toString()))
		{
			holder.layout_taskInfo.setBackgroundColor(Color.BLACK);
			holder.taskStatus.setText("["+Constant.UNDO+"]");
			holder.layout_listItem.setOnClickListener(new OnClickListener(){

				public void onClick(View v) {
					AlertActivityJump(taskId,Constant.alertType.STARTALERT);
				}
			});
		}
		
		//OVERDUE
		if(overDueFlagMap.get(task.getId())==true||task.getTaskStatus().equals(Constant.taskStatus.OVERDUE.toString()))
		{
			holder.layout_taskInfo.setBackgroundColor(0xffdf9464);
			holder.taskStatus.setText("["+Constant.OVERDUE+"]");
			holder.layout_listItem.setOnClickListener(new OnClickListener(){

				public void onClick(View v) {
					AlertActivityJump(taskId,Constant.alertType.OVERDUEALERT);
				}
			});
		}
		
		//DOING
		if(task.getTaskStatus().equals(Constant.taskStatus.START.toString())
				||task.getTaskStatus().equals(Constant.taskStatus.EXTEND.toString()))
		{
			holder.layout_taskInfo.setBackgroundColor(0xff33a3dc);
			holder.taskStatus.setText("["+Constant.DOING+"]");
			holder.layout_listItem.setOnClickListener(new OnClickListener(){

				public void onClick(View v) {
					AlertActivityJump(taskId,Constant.alertType.COMPLETEALERT);
				}
			});
		}
	}
	
	private void EditTaskActivityJump(int taskId)
	{
		Intent intent = new Intent(demo,TaskEditActivity.class);
		intent.putExtra("taskId", taskId);
		intent.putExtra("isEditFlag", true);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		demo.startActivity(intent);
	}
	
	private void AlertActivityJump(int taskId, Constant.alertType alertType)
	{
		Intent intent = new Intent(demo,AlertActivity.class);
		intent.putExtra("taskId", taskId);
		intent.putExtra("viewFlag", true);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.putExtra("alertType", alertType.toString());
		demo.startActivity(intent);
	}

}
