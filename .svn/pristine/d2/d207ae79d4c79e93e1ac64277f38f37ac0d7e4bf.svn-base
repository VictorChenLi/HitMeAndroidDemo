package org.stl.hitme.gaming.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.stl.hitme.R;
import org.stl.hitme.storeCat.model.Prize;
import org.stl.hitme.storeCat.service.DBAccessImpl;
import org.stl.hitme.sysUtil.model.Constant.prizeType;
import org.stl.hitme.sysUtil.model.Constant;
import org.stl.hitme.sysUtil.model.GlobalVar;
import org.stl.hitme.sysUtil.ui.ListSelecterFragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;

public class PrizeMgr {
	private Context context;
	private DBAccessImpl dbAccessImpl;
	
	public PrizeMgr(Context context)
	{
		this.context=context;
		dbAccessImpl=DBAccessImpl.getInstance(context);
	}
	
	public boolean consumePrize(int prizeId)
	{
		boolean flag = false;
		Prize prize = dbAccessImpl.describePrize(prizeId);
		if(prize.getAvailableCount()>0)
		{
			int count = prize.getAvailableCount();
			prize.setAvailableCount(--count);
			dbAccessImpl.UpdatePrize(prize);
			flag=true;
			GlobalVar.prizeAdapter.refreshAdapter();
		}
		return flag;
	}
	
	public void savePrize(String prizeTitle, int prizeRate, prizeType prizeType, int availableCount)
	{
		Prize prize = new Prize();
		prize.setAvailableCount(availableCount);
		prize.setContent(prizeTitle);
		prize.setGoalID(0);
		prize.setPrizeType(prizeType.toString());
		prize.setRate(prizeRate);
		prize.setTitle(prizeTitle);
		dbAccessImpl.InsertPrize(prize);
	}
	
	public static void showPrize(FragmentActivity fragActivity)
	{
		DialogFragment newFragment = new ListSelecterFragment();
		Bundle args=new Bundle();
		args.putString("listItemType", Constant.listItemType.PRIZE.toString());
		args.putBoolean("isDelParent", true);
		newFragment.setArguments(args);
        newFragment.show(fragActivity.getSupportFragmentManager(), "Select a Prize");
	}
	
	public static boolean radomPrize(Prize prize,int level)
	{
		boolean flag=false;
    	Random r = new Random();
    	int rdInt = r.nextInt(100);
		if(prize.getRate()==0)
			prize.setRate(1);
		int prizeSpot = 100/prize.getRate();
		prizeSpot+=level;
		if(rdInt<=prizeSpot)
			flag=true;
    	
    	return flag;
	}
	
	public String ratePrize(int rate)
	{
		String strRate="";
		if(rate<=3)
			strRate=context.getResources().getString(R.string.prize_rate_nomal);
		if(rate>3&&rate<=7)
			strRate=context.getResources().getString(R.string.prize_rate_great);
		if(rate>7)
			strRate=context.getResources().getString(R.string.prize_rate_superb);
		return "["+strRate+"]";
	}
}
