package org.stl.hitme.gaming.ui;

import org.stl.hitme.R;
import org.stl.hitme.gaming.service.PrizeListAdapter;
import org.stl.hitme.goal.service.GoalListAdapter;
import org.stl.hitme.goal.ui.GoalEditActivity;
import org.stl.hitme.sysUtil.model.GlobalVar;
import org.stl.hitme.sysUtil.ui.ExperienceBar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class PrizeWishListActivity extends Activity {
	
	private PrizeWishListActivity demo;
	private ListView listview;
	private Button btn_add;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.prizewishlist_activity); 
		demo=this;
		findView();
		init();
	}
	
	private void findView()
	{
		listview=(ListView)findViewById(R.id.prizewishList);
		btn_add=(Button)findViewById(R.id.btn_add);
	}
	
	private void init()
	{
		btn_add.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				Intent intent = new Intent(demo,PrizeEditActivity.class);
				demo.startActivity(intent);
			}
		});
		initalPrizeList();
	}
	
	private void initalPrizeList()
	{
		PrizeListAdapter adapter;
		if(null==GlobalVar.prizeAdapter)
		{
			GlobalVar.prizeAdapter = new PrizeListAdapter(this);
			GlobalVar.prizeAdapter.refreshAdapter();
		}
		
		adapter=GlobalVar.prizeAdapter;
		listview.setAdapter(adapter);
		listview.setItemsCanFocus(false);
		listview.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		listview.setOnItemClickListener(new OnItemClickListener(){

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
//				TextView tv_prizeId= (TextView)arg1.findViewById(R.id.tv_prizeId);
				TextView tv_prizeTitle= (TextView)arg1.findViewById(R.id.tv_prizeTitle);
				Toast.makeText(demo, tv_prizeTitle.getText()+" was selected", Toast.LENGTH_LONG).show();
			}});
		adapter.notifyDataSetChanged();
	}
	
}
