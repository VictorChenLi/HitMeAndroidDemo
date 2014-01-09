package org.stl.hitme.gaming.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.stl.hitme.R;
import org.stl.hitme.storeCat.model.Goal;
import org.stl.hitme.storeCat.model.Prize;
import org.stl.hitme.storeCat.service.DBAccessImpl;
import org.stl.hitme.sysUtil.service.TimeHelper;
import org.stl.hitme.sysUtil.ui.ExperienceBar;
import org.stl.hitme.taskMgr.service.TaskMgr;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class PrizeListAdapter extends BaseAdapter {

	private LayoutInflater mInflater;    
    private List<Prize> mData;
    private DBAccessImpl dbAccessImpl;
    private PrizeMgr prizeMgr;

    public static class ViewHolder
    {
    	TextView tv_prizeId ;
		TextView tv_prizeTitle;
		TextView tv_prizeRate;
		TextView tv_availableCount;
		public int getId()
    	{
    		return Integer.valueOf(tv_prizeId.getText().toString());
    	}
    }
    
    public PrizeListAdapter(Context context) {
    	prizeMgr=new PrizeMgr(context);
    	dbAccessImpl=DBAccessImpl.getInstance(context);
        mInflater = LayoutInflater.from(context);
    }   
    
    public void refreshAdapter()
    {
    	init();
    	this.notifyDataSetChanged();
    }
    
    public void refreshAdapter(int level)
    {
    	radomInit(level);
    	this.notifyDataSetChanged();
    }
    
    private void init()
    {
    	mData=dbAccessImpl.queryAvailablePrizeList();
    }
    
    private void radomInit(int level)
    {
    	List<Prize> prizeList = dbAccessImpl.queryAvailablePrizeList();
    	List<Prize> availablePrizeList = new ArrayList<Prize>();
    	
    	for(Prize prize : prizeList)
    	{
    		if(PrizeMgr.radomPrize(prize, level))
    			availablePrizeList.add(prize);
    	}
    	mData=availablePrizeList;
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

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = new ViewHolder();
        //convertView为null的时候初始化convertView。    
        if (convertView == null||null!=mData.get(position)) {
            convertView = mInflater.inflate(R.layout.prizelistitem, null);
            holder.tv_prizeId = (TextView)convertView.findViewById(R.id.tv_prizeId);
            holder.tv_prizeTitle = (TextView)convertView.findViewById(R.id.tv_prizeTitle);
            holder.tv_prizeRate = (TextView)convertView.findViewById(R.id.tv_prizeRate);
            holder.tv_availableCount = (TextView)convertView.findViewById(R.id.tv_availableCount);
            holder.tv_prizeId.setText(String.valueOf(mData.get(position).getId()));
            holder.tv_prizeTitle.setText(mData.get(position).getTitle());
            holder.tv_prizeRate.setText(prizeMgr.ratePrize(mData.get(position).getRate()));
            holder.tv_availableCount.setText("Remain "+String.valueOf(mData.get(position).getAvailableCount()));
            convertView.setTag(holder);
        }
        return convertView;
	}

}
