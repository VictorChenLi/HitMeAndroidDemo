package org.stl.hitme.stage.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.stl.hitme.R;
import org.stl.hitme.storeCat.model.Goal;
import org.stl.hitme.storeCat.model.Stage;
import org.stl.hitme.storeCat.service.DBAccessImpl;
import org.stl.hitme.sysUtil.model.Constant;
import org.stl.hitme.sysUtil.model.Constant.viewModel;
import org.stl.hitme.sysUtil.service.SettingHelper;
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

public class StageListAdapter extends BaseAdapter {

	private LayoutInflater mInflater;    
    private List<Stage> mData;
    private DBAccessImpl dbAccessImpl;
    private int goalId;
    private Context demo;
    private TaskMgr taskMgr;
    private viewModel viewType=viewModel.WHOLE;
    
    public StageListAdapter(Context context,int goalID)
    {
    	dbAccessImpl = DBAccessImpl.getInstance(context);
    	mInflater = LayoutInflater.from(context);
    	goalId = goalID;
    	demo=context;
    	taskMgr = new TaskMgr(context);
        initData();   
    }
    
    public void refreshAdapter()
    {
    	initData();
    	this.notifyDataSetChanged();
    }
    
    //To generate some test data
    private void initData() {    
    	mData=dbAccessImpl.queryStageList(goalId);
		viewType = SettingHelper.getGoalListViewModel(demo);
		
//        mData=new ArrayList<Stage>();
//        Random r=new Random();
//        for (int i = 0; i < 10; i++) {
//        	Stage stage=new Stage();
//            int idealPer=r.nextInt(100);
//            int realPer=r.nextInt(100);
//            stage.setPercentage(realPer);
//            stage.setIdealPercentage(idealPer);
//            stage.setTitle("The title of " + (i + 1) + " row");
//            mData.add(stage);    
//        }    
    }   
    
	public int getCount() {
		return mData.size();
	}

	public Object getItem(int arg0) {
		return mData.get(arg0);
	}

	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	public viewModel getViewType() {
		return viewType;
	}

	public void setViewType(viewModel viewType) {
		this.viewType = viewType;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ExperienceBar ExpBarItem = null;    
        //convertView为null的时候初始化convertView。    
        if (convertView == null||null!=mData.get(position)) {
            convertView = mInflater.inflate(R.layout.goallistitem, null);
            ExpBarItem = (ExperienceBar)convertView.findViewById(R.id.expBar_item);
            ExpBarItem.setItemId(mData.get(position).getId());
            ExpBarItem.setTitle(mData.get(position).getTitle());
            if(mData.get(position).getStageStatus().equals(Constant.stageStatus.COMPLETE.toString()))
            	ExpBarItem.setTitle(mData.get(position).getTitle()+"["+Constant.stageStatus.COMPLETE.toString()+"]");
            ExpBarItem.setBothProgress((int)taskMgr.generateRealPercentage(mData.get(position),viewType), (int)TimeHelper.generateIdealPercent(mData.get(position),viewType));
            convertView.setTag(ExpBarItem);    
        }
        return convertView; 
	}

}
