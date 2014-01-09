package org.stl.hitme.sysUtil.service;

import org.stl.hitme.R;
import org.stl.hitme.sysUtil.model.Constant.alertMusic;
import org.stl.hitme.sysUtil.model.Constant.viewModel;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SettingHelper {
	
	public static viewModel getGoalListViewModel(Context context)
	{
		viewModel goalListViewModel;
		try
		{
			String str_goalListView = context.getResources().getString(R.string.setting_goalListView);
			SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
			goalListViewModel = viewModel.valueOf(settings.getString(str_goalListView, viewModel.WHOLE.toString()));
		}
		catch(Exception e)
		{
			e.printStackTrace();
			goalListViewModel=viewModel.WHOLE;
		}
		return goalListViewModel;
	}
	
	public static alertMusic getAlertMusic(Context context)
	{
		alertMusic alertmusic;
		try
		{
			String str_alertMusic = context.getResources().getString(R.string.setting_alertMusic);
			SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
			alertmusic = alertMusic.valueOf(settings.getString(str_alertMusic, alertMusic.BOTH.toString()));
		}
		catch(Exception e)
		{
			e.printStackTrace();
			alertmusic=alertMusic.BOTH;
		}
		return alertmusic;
	}
}
