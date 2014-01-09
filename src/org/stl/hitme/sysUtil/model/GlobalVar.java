package org.stl.hitme.sysUtil.model;

import org.stl.hitme.gaming.service.PrizeListAdapter;
import org.stl.hitme.goal.service.GoalListAdapter;
import org.stl.hitme.stage.service.StageListAdapter;
import org.stl.hitme.sysUtil.service.HitMeService;
import org.stl.hitme.todo.service.ToDoListAdapter;

public class GlobalVar {

	public static PrizeListAdapter prizeAdapter;
	
	public static ToDoListAdapter todoAdapter;
	
	public static GoalListAdapter goalListAdapter;
	
	public static HitMeService globalService;
}
