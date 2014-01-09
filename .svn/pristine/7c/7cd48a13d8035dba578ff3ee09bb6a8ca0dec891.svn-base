package org.stl.hitme.sysUtil.ui;

import org.stl.hitme.R;
import org.stl.hitme.sysUtil.model.GlobalVar;
import org.stl.hitme.sysUtil.model.Constant.viewModel;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;

public class SettingPreference extends PreferenceActivity implements OnPreferenceChangeListener   
{
	private String listView;
	private ListPreference goalListViewSelect; 
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 所的的值将会自动保存到SharePreferences
		addPreferencesFromResource(R.xml.setting_preference);
		listView = getResources().getString(R.string.setting_goalListView);
		goalListViewSelect=(ListPreference)findPreference(listView);
		goalListViewSelect.setOnPreferenceChangeListener(this);
	}
	


	public boolean onPreferenceChange(Preference preference, Object newValue) 
	{
		
		if(preference.getKey().equals(listView))  
        {  
            GlobalVar.goalListAdapter.setViewType(viewModel.valueOf((String) newValue));
            GlobalVar.goalListAdapter.refreshAdapter();
        }
		return true;
	}

}
