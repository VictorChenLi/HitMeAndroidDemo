package org.stl.hitme.sysUtil.frameWork;


import org.stl.hitme.sysUtil.service.HitMeService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootBroadcastReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Intent serviceIntent = new Intent(context,HitMeService.class);
		context.startService(serviceIntent);
	}

}
