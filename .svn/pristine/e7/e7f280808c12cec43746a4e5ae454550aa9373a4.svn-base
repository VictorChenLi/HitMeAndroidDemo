package org.stl.hitme.gaming.ui;

import org.stl.hitme.R;
import org.stl.hitme.gaming.service.PrizeMgr;
import org.stl.hitme.sysUtil.model.Constant;
import org.stl.hitme.sysUtil.model.GlobalVar;
import org.stl.hitme.sysUtil.ui.MainTabActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class PrizeEditActivity extends Activity {

	private PrizeEditActivity demo;
	private EditText et_Name;
	private EditText et_availableCount;
	private RadioGroup rg_prizeRate; 
	private Button btn_save;
	private PrizeMgr prizeMgr;
	private int rate=2;
	
	private void findView()
	{
		et_Name = (EditText)findViewById(R.id.et_Name);
		et_availableCount = (EditText)findViewById(R.id.et_availableCount);
		rg_prizeRate =(RadioGroup)findViewById(R.id.rg_prizeRate);
		btn_save=(Button)findViewById(R.id.btn_save);
	}
	
	private void initial()
	{
		findView();
		rg_prizeRate.setOnCheckedChangeListener(new OnCheckedChangeListener(){

			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if(checkedId==R.id.rb_nomal)
					rate=2;
				if(checkedId==R.id.rb_great)
					rate=5;
				if(checkedId==R.id.rb_superb)
					rate=10;
			}
		});
		btn_save.setOnClickListener(new OnClickListener()
		{
			public void onClick(View arg0) {
				prizeMgr.savePrize(et_Name.getText().toString(), rate, Constant.prizeType.User,Integer.valueOf(et_availableCount.getText().toString()));
				Toast.makeText(demo, et_Name.getText().toString()+" will saved", Toast.LENGTH_LONG).show();
				GlobalVar.prizeAdapter.refreshAdapter();
				Intent intent = new Intent(demo,MainTabActivity.class);
				intent.putExtra("tabIndex", Constant.tabIndex.GAME.ordinal());
				intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				demo.startActivity(intent);
				demo.finish();
			}
		});
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.prizeedit_activity);
		demo=this;
		prizeMgr=new PrizeMgr(demo);
		initial();
		
	}
	
}
