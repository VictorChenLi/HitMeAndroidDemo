package org.stl.hitme.sysUtil.ui;

import android.app.Activity;
//import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

public class ListFragActivity extends Activity {
	@Override    
	public void onCreate(Bundle savedInstanceState)	{
		super.onCreate(savedInstanceState); 
		if (getIntent() != null) { 
//			ListSelecterFragmentTest listFrag=new ListSelecterFragmentTest();
//			listFrag.setArguments(getIntent().getExtras());
//			FragmentTransaction ft = getFragmentManager().beginTransaction();             
//			ft.add(android.R.id.content, listFrag).commit(); 
        } 
	}
}
