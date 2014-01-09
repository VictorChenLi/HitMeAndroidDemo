package org.stl.hitme.taskMgr.service;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.stl.hitme.storeCat.model.Goal;
import org.stl.hitme.storeCat.model.Stage;
import org.stl.hitme.storeCat.model.TaskAttributes;
import org.stl.hitme.storeCat.service.DBAccessImpl;
import org.stl.hitme.sysUtil.model.Constant;
import org.stl.hitme.sysUtil.model.Constant.viewModel;
import org.stl.hitme.sysUtil.service.ExecutorServiceHelper;
import org.stl.hitme.sysUtil.service.TimeHelper;
import org.stl.hitme.taskMgr.model.CompleteAlertTask;
import org.stl.hitme.taskMgr.model.RunTask;
import org.stl.hitme.taskMgr.model.StartAlertTask;

import android.content.Context;

public class TaskMgr {
	
	private Context context;
	private DBAccessImpl dbAccessImpl;
	
	public TaskMgr(Context context)
	{
		this.context=context;
		dbAccessImpl=DBAccessImpl.getInstance(context);
	}
	
	//1.put task into database 
	//2.if the startTime is today excute it(only Start task)
	public int putTask(TaskAttributes taskAtt)
	{
		int taskId = taskAtt.getId();
		if(taskAtt.getId()==0)
			taskId =dbAccessImpl.InsertTask(taskAtt);
		else
			dbAccessImpl.UpdateTask(taskAtt);
		return taskId;
	}
	
	public TaskAttributes getTask(int taskId)
	{
		return dbAccessImpl.describeTask(taskId);
	}
	
	public void createTask(TaskAttributes taskAtt)
	{
		Calendar startTime = Calendar.getInstance();
		startTime.setTimeInMillis(taskAtt.getStartTime());
		if(startTime.get(Calendar.DAY_OF_YEAR)==Calendar.getInstance().get(Calendar.DAY_OF_YEAR)&&!Constant.taskType.ALLDAY.isContain(taskAtt.getTaskType()))
		{
			taskAtt.setTaskStatus(Constant.taskStatus.RUN.toString());
			int taskId = this.putTask(taskAtt);
			taskAtt.setId(taskId);
			this.runStartTask(taskAtt);
		}
		else
		{
			if(startTime.get(Calendar.DAY_OF_YEAR)>=Calendar.getInstance().get(Calendar.DAY_OF_YEAR))
			{
				taskAtt.setTaskStatus(Constant.taskStatus.WAIT.toString());
			}
			else
			{
				if(startTime.getTimeInMillis()<Calendar.getInstance().getTimeInMillis())
				{
					taskAtt.setTaskStatus(Constant.taskStatus.OVERDUE.toString());
					if(Constant.taskType.ROUTINE.isContain(taskAtt.getTaskType()))
					{
						this.nextRoutineTask(taskAtt);
					}
				}
			}
			this.putTask(taskAtt);
		}
	}
	
	// generate next Routine task and delete the Routine type from current task
	public void nextRoutineTask(TaskAttributes taskAttr)
	{
		TaskAttributes nextTask=new TaskAttributes(taskAttr);
		nextTask.setAlarmTime(taskAttr.getAlarmTime()+TimeHelper.DAYInMillis);
		nextTask.setStartTime(taskAttr.getStartTime()+TimeHelper.DAYInMillis);
		nextTask.setEndTime(taskAttr.getEndTime()+TimeHelper.DAYInMillis);
		this.createTask(nextTask);
		taskAttr.setTaskType(Constant.taskType.ROUTINE.del(taskAttr.getTaskType()));
	}
	
	public void resumeTask(TaskAttributes taskAtt)
	{
		Calendar startTime = Calendar.getInstance();
		startTime.setTimeInMillis(taskAtt.getStartTime());
		switch(Constant.taskStatus.valueOf(taskAtt.getTaskStatus()))
		{
			case WAIT:
			{
				this.createTask(taskAtt);
				break;
			}
			case RUN:
			case DELAY:
			{
				if(taskAtt.getStartTime()<=Calendar.getInstance().getTimeInMillis())
					this.overdueTask(taskAtt);
				else
					this.runStartTask(taskAtt);
				break;
			}
			case START:
			case EXTEND:
			{
				if(taskAtt.getEndTime()<=Calendar.getInstance().getTimeInMillis())
					this.overdueTask(taskAtt);
				else
					this.runCompleteTask(taskAtt);
				break;
			}
		}
	}
	
	// three condition: wait,run,overdue
	//1.set current time to the start time and end time equal start time plus spend time
	//2.remove the start thread if have
	//3.update the task status to START
	//4.run Complete task
	public void startTask(TaskAttributes taskAttr)
	{
		Calendar cur = Calendar.getInstance();
		taskAttr.setStartTime(cur.getTimeInMillis());
		taskAttr.setEndTime(cur.getTimeInMillis()+taskAttr.getSpendTime());
		
		String taskTag=taskAttr.getId()+Constant.taskStatus.START.toString();
		TaskMapMgr.removeTask(taskTag);
		
		taskAttr.setTaskStatus(Constant.taskStatus.START.toString());
		if(Constant.taskType.ROUTINE.isContain(taskAttr.getTaskType()))
		{
			this.nextRoutineTask(taskAttr);
		}
		if(Constant.taskType.ALLDAY.isContain(taskAttr.getTaskType()))
			taskAttr.setTaskType(Constant.taskType.ALLDAY.del(taskAttr.getTaskType()));
		dbAccessImpl.UpdateTask(taskAttr);
		this.runCompleteTask(taskAttr);
	}
	
	private void runStartTask(TaskAttributes taskAtt)
	{
		RunTask startTask = new StartAlertTask(context, taskAtt);
		String startTaskTag=taskAtt.getId()+Constant.taskStatus.START.toString();
		long delay = taskAtt.getAlarmTime()-Calendar.getInstance().getTimeInMillis();
		if(delay>=0)
			TaskMgr.runTask(startTask, startTaskTag, delay);
	}
	

	private void runCompleteTask(TaskAttributes taskAtt)
	{
		RunTask completeTask = new CompleteAlertTask(context, taskAtt);
		String completeTaskTag=taskAtt.getId()+Constant.taskStatus.COMPLETE.toString();
		long delay = taskAtt.getEndTime()-Calendar.getInstance().getTimeInMillis();
		if(delay>=0)
			TaskMgr.runTask(completeTask, completeTaskTag, delay);
	}
	
	public void resumeAllTask()
	{
		List<TaskAttributes> taskList= dbAccessImpl.queryRunableTaskList();
		for(TaskAttributes task : taskList)
		{
			this.resumeTask(task);
		}
	}
	
	public static void runTask(RunTask runTask,String taskTag,long delay)
	{
		if(null!=TaskMapMgr.getTaskByTaskId(taskTag))
		{
			TaskMapMgr.removeTask(taskTag);
		}
		TaskMapMgr.registTask(taskTag, runTask);
		ExecutorServiceHelper.schedule(runTask, delay,TimeUnit.MILLISECONDS);
	}
	
	//1.remove the startTask from map
	//2.change the alarm time and start time(if need)
	//3.change the task status
	//4.plus the delayTimes
	//5.put the new task
	public void delayTask(TaskAttributes taskAttr,long delay)
	{
		String taskTag=taskAttr.getId()+Constant.taskStatus.START.toString();
		TaskMapMgr.removeTask(taskTag);
		taskAttr.setAlarmTime(taskAttr.getAlarmTime()+delay);
		taskAttr.setStartTime(taskAttr.getStartTime()+delay);
		taskAttr.setEndTime(taskAttr.getEndTime()+delay);
		taskAttr.setTaskStatus(Constant.taskStatus.DELAY.toString());
		taskAttr.plusDelayTimes();
		this.runStartTask(taskAttr);
		this.putTask(taskAttr);
	}
	
	//1.remove the completeTask from map
	//2.change the end time
	//3.change the task status
	//4.plus the extendTimes
	//5.start the new Complete task
	public void extendTask(TaskAttributes taskAttr,long extend)
	{
		String taskTag=taskAttr.getId()+Constant.taskStatus.COMPLETE.toString();
		TaskMapMgr.removeTask(taskTag);
		taskAttr.setEndTime(taskAttr.getEndTime()+extend);
		taskAttr.setTaskStatus(Constant.taskStatus.EXTEND.toString());
		taskAttr.plusExtendTimes();
		this.runCompleteTask(taskAttr);
		this.putTask(taskAttr);
	}
	
	//1.remove the completeTask from map
	//2.change the task status
	//3.change the Stage's spendTime
	//4.update the task
	public void finishTask(TaskAttributes taskAttr)
	{
		String taskTag=taskAttr.getId()+Constant.taskStatus.COMPLETE.toString();
		TaskMapMgr.removeTask(taskTag);
		taskAttr.setTaskStatus(Constant.taskStatus.COMPLETE.toString());
		Stage stage = dbAccessImpl.describeStage(taskAttr.getStageID());
		stage.plusSpendTime(dbAccessImpl,(int)taskAttr.getSpendTime());
		this.putTask(taskAttr);
	}
	
	//1.remove the startTask from map
	//2.update the task status
	//3.run Complete task
	public void startNowTask(TaskAttributes taskAttr)
	{
		String taskTag=taskAttr.getId()+Constant.taskStatus.START.toString();
		TaskMapMgr.removeTask(taskTag);
		taskAttr.setTaskStatus(Constant.taskStatus.START.toString());
		dbAccessImpl.UpdateTask(taskAttr);
		this.runCompleteTask(taskAttr);
	}
	
	//1.remove the start and complete task from map
	//2.change the task status
	//3.update the task
	public void dismissTask(TaskAttributes taskAttr)
	{
		String startTaskTag=taskAttr.getId()+Constant.taskStatus.START.toString();
		String completeTaskTag=taskAttr.getId()+Constant.taskStatus.COMPLETE.toString();
		TaskMapMgr.removeTask(startTaskTag);
		TaskMapMgr.removeTask(completeTaskTag);
		taskAttr.setTaskStatus(Constant.taskStatus.DISMISS.toString());
		dbAccessImpl.UpdateTask(taskAttr);
	}
	
	public void overdueTask(TaskAttributes taskAttr)
	{
		taskAttr.setTaskStatus(Constant.taskStatus.OVERDUE.toString());
		dbAccessImpl.UpdateTask(taskAttr);
	}
	
	public double generateRealPercentage(Goal goal, viewModel viewmodel)
	{
//		if(viewmodel.equals(Constant.viewModel.WHOLE))
//			return goal.getRealPercentage();
		
		List<Stage> stageList = dbAccessImpl.queryStageList(goal.getId());
		long realAccumTime = 0;
		double idealAccumTime=0;
		for(Stage stage: stageList)
		{
			realAccumTime+=this.getRealAccumTime(stage, viewmodel);
			idealAccumTime+=this.getIdealAccumTime(stage, viewmodel);
		}
		return 100*realAccumTime/idealAccumTime;
	}
	
	public double generateRealPercentage(Stage stage, viewModel viewmodel)
	{
//		if(viewmodel.equals(Constant.viewModel.WHOLE))
//			return stage.getPercentage();
		
		return 100*this.getRealAccumTime(stage, viewmodel)/this.getIdealAccumTime(stage, viewmodel);
	}
	
	private double getIdealAccumTime(Stage stage, viewModel viewmodel)
	{
		long[] periodTime = TimeHelper.generatePeriodTime(stage.getStartTime(), stage.getEndTime(), viewmodel);
		long wholeTime = stage.getEndTime()-stage.getStartTime();
		double idealAccumTime = stage.getInvestTime()*((periodTime[1]-periodTime[0])/(double)wholeTime);
		return idealAccumTime;
	}
	
	private double getRealAccumTime(Stage stage, viewModel viewmodel)
	{
		long[] periodTime = TimeHelper.generatePeriodTime(stage.getStartTime(), stage.getEndTime(), viewmodel);
		List<TaskAttributes> taskList = dbAccessImpl.queryPeriodTaskList(periodTime[0],periodTime[1],stage);
		long realAccumTime = 0;
		for(TaskAttributes task: taskList)
		{
			realAccumTime+=task.getSpendTime();
		}
		return realAccumTime;
	}
	
}
