package org.stl.hitme.goal.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.stl.hitme.R;
import org.stl.hitme.storeCat.model.Goal;
import org.stl.hitme.storeCat.service.DBAccessImpl;
import org.stl.hitme.sysUtil.model.Constant;
import org.stl.hitme.sysUtil.model.GlobalVar;
import org.stl.hitme.sysUtil.model.Constant.viewModel;
import org.stl.hitme.sysUtil.service.TimeHelper;
import org.stl.hitme.sysUtil.ui.ExperienceBar;
import org.stl.hitme.taskMgr.service.TaskMgr;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class GoalListAdapter extends BaseAdapter {
	
	private LayoutInflater mInflater;    
    private List<Goal> mData;
    private DBAccessImpl dbAccessImpl;
    private TaskMgr taskMgr;
    private viewModel viewType=viewModel.WHOLE;
    
    
    public GoalListAdapter(Context context) {    
    	dbAccessImpl=DBAccessImpl.getInstance(context);
        mInflater = LayoutInflater.from(context);    
        taskMgr = new TaskMgr(context);
        init();    
    }    
    
    public void refreshAdapter()
    {
    	init();
    	this.notifyDataSetChanged();
    }
    

    //To generate some test data
    private void init() {    
    	mData=dbAccessImpl.queryOrderGoalList();
//        mData=new ArrayList<Goal>();
//        Random r=new Random();
//        for (int i = 0; i < 10; i++) {
//        	Goal goal=new Goal();
//            int idealPer=r.nextInt(100);
//            int realPer=r.nextInt(100);
//            goal.setRealPercentage(realPer);
//            goal.setIdealPercentage(idealPer);
//            goal.setTitle("The title of " + (i + 1) + " row");
//            mData.add(goal);    
//        }    
    }    
    
	public int getCount() {
		return mData.size();
	}

	public Object getItem(int arg0) {
		return mData.get(arg0);
	}

	public long getItemId(int arg0) {
		return arg0;
	}

	public viewModel getViewType() {
		return viewType;
	}

	public void setViewType(viewModel viewType) {
		this.viewType = viewType;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ExperienceBar ExpBarItem = null;    
		TextView tv_id = null;
        //convertView为null的时候初始化convertView。    
        if (convertView == null||null!=mData.get(position)) {
            convertView = mInflater.inflate(R.layout.goallistitem, null);
            ExpBarItem = (ExperienceBar)convertView.findViewById(R.id.expBar_item);
            ExpBarItem.setItemId(mData.get(position).getId());
//            tv_id = (TextView)convertView.findViewById(R.id.tv_goalid);
//            tv_id.setText(String.valueOf(mData.get(position).getId()));
            ExpBarItem.setTitle(mData.get(position).getTitle());
            if(Constant.goalStatus.COMPLETE.toString().equals(mData.get(position).getGoalStatus()))
            	ExpBarItem.setTitle(mData.get(position).getTitle()+"["+Constant.stageStatus.COMPLETE.toString()+"]");
            ExpBarItem.setBothProgress((int)taskMgr.generateRealPercentage(mData.get(position),viewType), (int)TimeHelper.generateIdealPercent(mData.get(position),viewType));
            convertView.setTag(ExpBarItem);    
        }
        return convertView;
	}

}
