package org.stl.hitme.taskMgr.model;

import org.stl.hitme.storeCat.model.TaskAttributes;
import org.stl.hitme.storeCat.service.DBAccessImpl;
import org.stl.hitme.sysUtil.model.Constant;
import org.stl.hitme.sysUtil.model.Constant.alertType;
import org.stl.hitme.sysUtil.service.ExecutorServiceHelper;
import org.stl.hitme.sysUtil.ui.ListSelecterFragment;
import org.stl.hitme.todo.ui.AlertActivity;
import org.stl.hitme.todo.ui.StartAlertFragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

public class CompleteAlertTask extends RunTask {

	private static final String TAG = ExecutorServiceHelper.class.getSimpleName();
	private TaskAttributes taskAttr = null;
	private Context target = null;

	public CompleteAlertTask(Context context, TaskAttributes taskItem)
	{
		super();
		taskAttr= taskItem;
		target = context;
	}
	
	public void run() {
		Log.d(TAG, taskAttr.getTitle());
		
		Intent intent = new Intent(target,AlertActivity.class);
		intent.putExtra("taskId", taskAttr.getId());
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.putExtra("alertType", Constant.alertType.COMPLETEALERT.toString());
		target.startActivity(intent);
	}

}
