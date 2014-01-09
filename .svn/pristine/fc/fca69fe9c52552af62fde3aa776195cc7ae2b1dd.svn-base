package org.stl.hitme.sysUtil.ui;

import org.stl.hitme.R;
import org.stl.hitme.gaming.service.PrizeListAdapter;
import org.stl.hitme.gaming.service.PrizeMgr;
import org.stl.hitme.goal.service.GoalListAdapter;
import org.stl.hitme.stage.service.StageListAdapter;
import org.stl.hitme.sysUtil.model.Constant;
import org.stl.hitme.sysUtil.model.GlobalVar;

//import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ListSelecterFragment extends DialogFragment {

	private int itemId;
	private String listItemType;
	private boolean isDelParent=false;
	private ListSelecterFragment self;
	ListAdapter adapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		listItemType=getArguments().getString("listItemType");
		isDelParent=getArguments().containsKey("isDelParent")?getArguments().getBoolean("isDelParent"):false;
		itemId=getArguments().getInt("itemId");
		self=this;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.list_fragment, container, false);
		ListView itemList=(ListView)v.findViewById(R.id.lv_itemList);
		if(listItemType.equals(Constant.listItemType.GOAL.toString()))
		{
//			adapter = new GoalListAdapter(getActivity());
			adapter=GlobalVar.goalListAdapter;
			itemList.setOnItemClickListener(new OnItemClickListener(){

				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
						long arg3) {
//					TextView tv_goalId = (TextView)arg1.findViewById(R.id.tv_itemId);
					ExperienceBar expBar = (ExperienceBar)arg1.findViewById(R.id.expBar_item);
					int goalId=expBar.getItemId();
					DialogFragment newFragment = new ListSelecterFragment();
					Bundle args=new Bundle();
					args.putString("listItemType", Constant.listItemType.STAGE.toString());
					args.putInt("itemId", goalId);
					newFragment.setArguments(args);
	                newFragment.show(getFragmentManager(), "Select a Stage");
	                self.dismiss();
				}});
		}
		if(listItemType.equals(Constant.listItemType.STAGE.toString()))
		{
			adapter = new StageListAdapter(getActivity(),itemId);
			itemList.setOnItemClickListener(new OnItemClickListener(){

				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
						long arg3) {
					ExperienceBar expBar = (ExperienceBar)arg1.findViewById(R.id.expBar_item);
//					TextView tv_stageId = (TextView)arg1.findViewById(R.id.tv_itemId);
//					int stageId=Integer.valueOf(tv_stageId.getText().toString());
					int stageId=expBar.getItemId();
					EditText et_stageName = (EditText)getActivity().findViewById(R.id.et_stageName);
					TextView tv_stageId = (TextView)getActivity().findViewById(R.id.tv_stageId);
					et_stageName.setText(expBar.getTitle());
					tv_stageId.setText(String.valueOf(stageId));
					self.dismiss();
				}});
		}
		if(listItemType.equals(Constant.listItemType.PRIZE.toString()))
		{
//			adapter = new GoalListAdapter(getActivity());
			adapter=new PrizeListAdapter(getActivity());
			((PrizeListAdapter)adapter).refreshAdapter(0);
			//if there are no prize, dismiss self immediately
			if(0==adapter.getCount())
			{
				Toast.makeText(getActivity(), "No prize is available!", Toast.LENGTH_LONG).show();
                self.onDetach();
			}
			itemList.setOnItemClickListener(new OnItemClickListener(){

				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
						long arg3) {
					TextView tv_prizeId = (TextView)arg1.findViewById(R.id.tv_prizeId);
					TextView tv_prizeTitle = (TextView)arg1.findViewById(R.id.tv_prizeTitle);
					PrizeMgr prizeMgr = new PrizeMgr(getActivity());
					prizeMgr.consumePrize(Integer.valueOf(tv_prizeId.getText().toString()));
					Toast.makeText(getActivity(), tv_prizeTitle.getText()+" was selected", Toast.LENGTH_LONG).show();
	                self.dismiss();
				}});
		}
		itemList.setAdapter(adapter);
		itemList.setItemsCanFocus(false);
		itemList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		
		((BaseAdapter) adapter).notifyDataSetChanged();
		return v;
	}

	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		super.onDetach();
		if(isDelParent)
			getActivity().finish();
	}
	

}
