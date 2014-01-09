package org.stl.hitme.todo.ui;

import org.stl.hitme.R;
import org.stl.hitme.goal.service.GoalListAdapter;
import org.stl.hitme.sysUtil.model.Constant;
import org.stl.hitme.sysUtil.model.GlobalVar;
import org.stl.hitme.todo.service.ToDoListAdapter;
import org.stl.hitme.todo.service.ToDoListAdapter.ViewHolder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class ToDoListActivity extends Activity {

	private ToDoListActivity demo;
	private ListView lv_todo;
	private Button btn_add;
	private ToDoListAdapter adapter;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.todolist_activity); 
		demo=this;
		findView();
		addListener();
		initalToDoList();
	}
	
	private void findView()
	{
		lv_todo=(ListView) findViewById(R.id.lv_todo);
		btn_add=(Button) findViewById(R.id.btn_add);
	}
	
	private void addListener()
	{
		btn_add.setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) {
				Intent intent = new Intent(demo,TaskEditActivity.class);
				demo.startActivity(intent);
			}
			
		});
	}
	
	private void initalToDoList()
	{
		adapter=GlobalVar.todoAdapter;
		lv_todo.setAdapter(adapter);
		lv_todo.setItemsCanFocus(false);
		lv_todo.setChoiceMode(ListView.CHOICE_MODE_NONE);
//		lv_todo.setOnItemClickListener(new OnItemClickListener(){
//
//			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//					long arg3) {
//				ViewHolder holder =(ViewHolder)arg1.getTag();
//				
//				Intent intent = new Intent(demo,AlertActivity.class);
//				intent.putExtra("taskId", holder.getId());
//				intent.putExtra("alertType", Constant.alertType.STARTALERT.toString());
//				demo.startActivity(intent);
//				
////				ViewHolder holder =(ViewHolder)arg1.getTag();
////				holder.showDetail();
//			}});
		lv_todo.setOnScrollListener(new OnScrollListener(){

			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				
				
		        boolean loadMore = firstVisibleItem + visibleItemCount > totalItemCount;  
		        if(loadMore){  
		        	final int newtotal = totalItemCount+visibleItemCount;
		        	new Handler().postDelayed(new Runnable() {

						public void run() {
							adapter.refreshAdapter(newtotal);
						} 
		        		
		        	}, 100);
		        }
				
			}

			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
			}
			
		});
		
		adapter.notifyDataSetChanged();
	}
	
	

}
