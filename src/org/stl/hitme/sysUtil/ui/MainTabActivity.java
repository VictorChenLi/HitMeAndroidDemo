package org.stl.hitme.sysUtil.ui;

import org.stl.hitme.R;
import org.stl.hitme.gaming.ui.PrizeWishListActivity;
import org.stl.hitme.goal.ui.GoalListActivity;
import org.stl.hitme.sysUtil.model.GlobalVar;
import org.stl.hitme.sysUtil.model.Constant.tabIndex;
import org.stl.hitme.sysUtil.service.HitMeService;
import org.stl.hitme.test.ui.FriendsActivity;
import org.stl.hitme.test.ui.ReviewActivity;
import org.stl.hitme.todo.ui.ToDoListActivity;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class MainTabActivity extends TabActivity {
	private String[] tabName;
	private int[] tabImg = new int[] { R.drawable.goal,R.drawable.todo_list,R.drawable.game, R.drawable.friends,R.drawable.review};
	private Class[] classes = new Class[] { GoalListActivity.class,//每个tab页对应的Activity  
			ToDoListActivity.class,PrizeWishListActivity.class, FriendsActivity.class,ReviewActivity.class };

	@Override  
    protected void onCreate(Bundle savedInstanceState) {  
        // TODO Auto-generated method stub  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.maintab_activity);
        tabName=this.getResources().getStringArray(R.array.tab_title);
        int tabIndex=getIntent().getIntExtra("tabIndex", 0);
        startService();
        createTab(tabIndex);  
    }
	
	public void startService()
	{
		Intent serviceIntent = new Intent(this,HitMeService.class);
		this.startService(serviceIntent);
	}
	
	private void createTab(int tabIndex)
	{
		TabHost tabHost = getTabHost();  
        Resources res = getResources();//动态生成Tab页  
        for (int i = 0; i < tabName.length; i++) {  
            TabSpec spec = tabHost.newTabSpec("tab" + i)  
                    .setIndicator(tabName[i], res.getDrawable(tabImg[i]))  
                    .setContent(new Intent(this, classes[i]));
            tabHost.addTab(spec);  
//            View view = tabHost.getTabWidget().getChildAt(i);  
//            view.getLayoutParams().height =View.;//设置tab页的高度  
        }  
        tabHost.setCurrentTab(tabIndex);  
	}
	
	@Override  
	public boolean onKeyDown(int keyCode, KeyEvent event)  
	{  
		if(keyCode == KeyEvent.KEYCODE_MENU)  
	    {             
	        super.openOptionsMenu();  
	        return true;  
	    }  
	    else  
	    {   
	        return super.onKeyDown(keyCode, event);  
	    }         
	} 
	
	@Override  
	public boolean onCreateOptionsMenu(Menu menu) {  
	 // TODO Auto-generated method stub   
	    menu.add(0, 1, 1, this.getString(R.string.menu_setting));    
	 return super.onCreateOptionsMenu(menu);   
	}  
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId()==1)  
	    {  
	    	Intent settingIntent = new Intent(this,SettingPreference.class);
	    	this.startActivity(settingIntent);
	    }
		return super.onOptionsItemSelected(item);
	}

	@Override  
	public boolean onMenuItemSelected(int featureId, MenuItem item) {  
	    // TODO Auto-generated method stub   
	    if(item.getItemId()==1)  
	    {  
	    	Intent settingIntent = new Intent(this,SettingPreference.class);
	    	this.startActivity(settingIntent);
	    }
	    return super.onMenuItemSelected(featureId, item);  
	}  
}
