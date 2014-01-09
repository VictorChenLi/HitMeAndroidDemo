package org.stl.hitme.storeCat.service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.stl.hitme.storeCat.frameWork.SQLiteHelper;
import org.stl.hitme.storeCat.model.Goal;
import org.stl.hitme.storeCat.model.Prize;
import org.stl.hitme.storeCat.model.Stage;
import org.stl.hitme.storeCat.model.TaskAttributes;
import org.stl.hitme.sysUtil.model.Constant;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBAccessImpl implements DBAccess {
	
	private static DBAccessImpl dbAccessImpl=null;
	
	public static final String DB_NAME="HitMe.db";
	public static final int VERSION=1;
	public Context udcontext;
	
	SQLiteHelper m_Helper;
	SQLiteDatabase wdb;
	SQLiteDatabase rdb;
	
	private DBAccessImpl(Context context) {
    	udcontext=context;
    	m_Helper=new SQLiteHelper(context,DB_NAME,null,VERSION);
        wdb=m_Helper.getWritableDatabase();
        rdb=m_Helper.getReadableDatabase();
    }
	
	public static synchronized DBAccessImpl getInstance(Context context)
	{
		if(dbAccessImpl==null)
			dbAccessImpl= new DBAccessImpl(context);
		return dbAccessImpl;
	}
	
	/********************************** Task Operation********************************/
    public synchronized int InsertTask(TaskAttributes task)
    {
    	String strSql = "select id from tbl_task ORDER BY id desc LIMIT 0,1";
    	int nextId=0;
    	Cursor cursor = rdb.rawQuery(strSql, null);
    	if(cursor.moveToNext())
    		nextId = cursor.getInt(0);
    	strSql="Insert into [tbl_task](stageID,title,content,alarmTime,taskType,delayTimes,taskStatus,startTime,endTime,spendTime,extendTimes) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
    	Object[] bindArgs = { task.getStageID(),task.getTitle(),task.getContent(),task.getAlarmTime(),task.getTaskType(),task.getDelayTimes(),task.getTaskStatus(),task.getStartTime(),task.getEndTime(),task.getEndTime()-task.getStartTime(),task.getExtendTimes()};
    	wdb.execSQL(strSql,bindArgs);
    	return ++nextId;
    }
    
    public void UpdateTask(TaskAttributes task)
    {
    	String strSql="Update [tbl_task] set stageID=?, title=?, content=?, alarmTime=?, taskType=?, delayTimes=?, taskStatus=?, startTime=?, endTime=?, spendTime=?, extendTimes=? where id=?";
    	Object[] bindArgs = { task.getStageID(),task.getTitle(),task.getContent(),task.getAlarmTime(),task.getTaskType(),task.getDelayTimes(),task.getTaskStatus(),task.getStartTime(),task.getEndTime(),task.getEndTime()-task.getStartTime(),task.getExtendTimes(),task.getId()};
    	wdb.execSQL(strSql,bindArgs);
    }
    
    public void deletTask(int id)
    {
    	String strSql="Delete * from [tbl_task] where id=?";
    	Object[] bindArgs ={id};
    	rdb.execSQL(strSql,bindArgs);
    }
    
    private List<TaskAttributes> fillTaskList(Cursor cursor)
    {
    	List<TaskAttributes> list=new ArrayList<TaskAttributes>();
        while (cursor.moveToNext())
        	 list.add(new TaskAttributes(cursor.getInt(0),cursor.getInt(1),cursor.getString(2),cursor.getString(3),cursor.getLong(4),cursor.getInt(5),cursor.getInt(6),cursor.getString(7),cursor.getLong(8),cursor.getLong(9),cursor.getLong(10),cursor.getInt(11)));
        return list;
    }
    
    public List<TaskAttributes> queryTaskList()
    {
    	String strSql="Select * from [tbl_task]";
    	Cursor cursor = rdb.rawQuery(strSql,null);
        return fillTaskList(cursor);
    }
    
    public List<TaskAttributes> queryTaskList(String taskStatus)
    {
    	String strSql="Select * from [tbl_task] where taskStatus=?";
    	String[] bindArgs ={taskStatus};
    	Cursor cursor = rdb.rawQuery(strSql,bindArgs);
    	return fillTaskList(cursor);
    }
    
    public List<TaskAttributes> queryRunableTaskList(int size)
    {
    	String strSql="Select * from [tbl_task] where taskStatus<>? and taskStatus<>? order by [startTime] asc LIMIT 0,?";
    	String[] bindArgs ={Constant.taskStatus.COMPLETE.toString(),Constant.taskStatus.DISMISS.toString(),String.valueOf(size)};
    	Cursor cursor = rdb.rawQuery(strSql,bindArgs);
    	return fillTaskList(cursor);
    }
    
    public List<TaskAttributes> queryPeriodTaskList(long startTime, long endTime,Stage stage)
    {
    	String strSql="Select * from [tbl_task] where taskStatus=? and stageID=? and startTime>? and endTime<?";
    	String[] bindArgs ={Constant.taskStatus.COMPLETE.toString(),String.valueOf(stage.getId()),String.valueOf(startTime),String.valueOf(endTime)};
    	Cursor cursor = rdb.rawQuery(strSql,bindArgs);
    	return fillTaskList(cursor);
    }
    
    public List<TaskAttributes> queryPeriodTaskList(long startTime, long endTime,Goal goal)
    {
    	String strSql="Select * from [tbl_task] as t, [tbl_stage] as s where t.taskStatus=? and s.goalID=? and s.id=t.stageID and t.startTime>? and t.endTime<?";
    	String[] bindArgs ={Constant.taskStatus.COMPLETE.toString(),String.valueOf(goal.getId()),String.valueOf(startTime),String.valueOf(endTime)};
    	Cursor cursor=null;
    	try
    	{
    		cursor = rdb.rawQuery(strSql,bindArgs);
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    	return fillTaskList(cursor);
    }
    
    public List<TaskAttributes> queryRunableTaskList()
    {
    	return queryRunableTaskList(Integer.MAX_VALUE);
    }
    
    public TaskAttributes describeTask(int taskId)
    {
    	String strSql="Select * from [tbl_task] where id=?";
    	TaskAttributes task =null;
    	String[] bindArgs ={String.valueOf(taskId)};
    	Cursor cursor = rdb.rawQuery(strSql,bindArgs);
        return fillTaskList(cursor).get(0);
    }
    /********************************** Task Operation********************************/
    
    /********************************** Stage Operation********************************/
    public void InsertStage(Stage stage)
    {
    	String strSql="Insert into [tbl_stage](goalID,title,content,[index],startTime,endTime,percentage,investTime,spendTime,stageValue,stageStatus) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
    	Object[] bindArgs = { stage.getGoalID(),stage.getTitle(),stage.getContent(),stage.getIndex(),stage.getStartTime(),stage.getEndTime(),(double)stage.getSpendTime()/stage.getInvestTime(),stage.getInvestTime(),stage.getSpendTime(),stage.getStageValue(),stage.getStageStatus()};
    	wdb.execSQL(strSql,bindArgs);
    }
    
    public void UpdateStage(Stage stage)
    {
    	String strSql="Update [tbl_stage] set goalID=?, title=?, content=?, [index]=?, startTime=?, endTime=?, percentage=?, investTime=?, spendTime=?, stageStatus=? where id=?";
    	Object[] bindArgs = { stage.getGoalID(),stage.getTitle(),stage.getContent(),stage.getIndex(),stage.getStartTime(),stage.getEndTime(),(double)stage.getSpendTime()/stage.getInvestTime(),stage.getInvestTime(),stage.getSpendTime(),stage.getStageStatus(),stage.getId()};
    	wdb.execSQL(strSql,bindArgs);
    }
    
    public void deletStage(int id)
    {
    	String strSql="Delete * from [tbl_stage] where id=?";
    	Object[] bindArgs ={id};
    	rdb.execSQL(strSql,bindArgs);
    }
    
    public Stage describeStage(int stageId)
    {
    	String strSql="Select * from [tbl_stage] where id=?";
    	Stage stage =null;
    	String[] bindArgs ={String.valueOf(stageId)};
    	Cursor cursor = rdb.rawQuery(strSql,bindArgs);
        if (cursor.moveToNext())
        	stage = new Stage(cursor.getInt(0),cursor.getInt(1),cursor.getString(2),cursor.getString(3),cursor.getInt(4),cursor.getLong(5),cursor.getLong(6),cursor.getDouble(7),cursor.getInt(8),cursor.getInt(9),cursor.getInt(10),cursor.getString(11));
        return stage;
    }
    
    public List<Stage> queryStageList(int goalId)
    {
    	String strSql="Select * from [tbl_stage] where goalID=?";
    	String[] bindArgs ={String.valueOf(goalId)};
    	Cursor cursor = rdb.rawQuery(strSql,bindArgs);
        List<Stage> list=new ArrayList<Stage>();
        while (cursor.moveToNext())
        	 list.add(new Stage(cursor.getInt(0),cursor.getInt(1),cursor.getString(2),cursor.getString(3),cursor.getInt(4),cursor.getLong(5),cursor.getLong(6),cursor.getDouble(7),cursor.getInt(8),cursor.getInt(9),cursor.getInt(10),cursor.getString(11)));
        return list;
    }
    /********************************** Stage Operation********************************/
    
    /********************************** Goal Operation********************************/
    public void InsertGoal(Goal goal)
    {
    	String strSql="Insert into [tbl_goal](title,content,startTime,endTime,percentage,goalType,goalValue,achievedValue,goalStatus) VALUES (?,?,?,?,?,?,?,?,?)";
    	Object[] bindArgs = { goal.getTitle(),goal.getContent(),goal.getStartTime(),goal.getEndTime(),(double)goal.getAchievedValue()/goal.getGoalValue(),goal.getGoalType(),goal.getGoalValue(),goal.getAchievedValue(),goal.getGoalStatus()};
    	wdb.execSQL(strSql,bindArgs);
    }
    
    public void UpdateGoal(Goal goal)
    {
    	String strSql="Update [tbl_goal] set title=?, content=?, startTime=?, endTime=?, percentage=?, goalType=?, goalValue=?, achievedValue=?, goalStatus=? where id=?";
    	Object[] bindArgs = { goal.getTitle(),goal.getContent(),goal.getStartTime(),goal.getEndTime(),(double)goal.getAchievedValue()/goal.getGoalValue(),goal.getGoalType(),goal.getGoalValue(),goal.getAchievedValue(),goal.getGoalStatus(),goal.getId()};
    	wdb.execSQL(strSql,bindArgs);
    }
    
    public void deletGoal(int id)
    {
    	String strSql="Delete * from [tbl_goal] where id=?";
    	Object[] bindArgs ={id};
    	rdb.execSQL(strSql,bindArgs);
    }
    
    public Goal describeGoal(int goalId)
    {
    	String strSql="Select * from [tbl_goal] where id=?";
    	Goal goal =null;
    	String[] bindArgs ={String.valueOf(goalId)};
    	Cursor cursor = rdb.rawQuery(strSql,bindArgs);
        if (cursor.moveToNext())
        	goal = new Goal(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getLong(3),cursor.getLong(4),cursor.getDouble(5),cursor.getString(6),cursor.getInt(7),cursor.getInt(8),cursor.getString(9));
        return goal;
    }
    
    public List<Goal> queryGoalList()
    {
    	String strSql="Select * from [tbl_goal]";
    	Cursor cursor = rdb.rawQuery(strSql,null);
        List<Goal> list=new ArrayList<Goal>();
        while (cursor.moveToNext())
        	 list.add(new Goal(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getLong(3),cursor.getLong(4),cursor.getDouble(5),cursor.getString(6),cursor.getInt(7),cursor.getInt(8),cursor.getString(9)));
        return list;
    }
    
    public List<Goal> queryOrderGoalList()
    {
    	String strSql="Select * from [tbl_goal] order by [goalStatus] desc";
    	Cursor cursor = rdb.rawQuery(strSql,null);
        List<Goal> list=new ArrayList<Goal>();
        while (cursor.moveToNext())
        	 list.add(new Goal(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getLong(3),cursor.getLong(4),cursor.getDouble(5),cursor.getString(6),cursor.getInt(7),cursor.getInt(8),cursor.getString(9)));
        return list;
    }
    /********************************** Goal Operation********************************/
    
    /********************************** Prize Operation********************************/
    public void InsertPrize(Prize prize)
    {
    	String strSql="Insert into [tbl_prize](goalID,title,content,prizeType,availableCount,rate) VALUES (?,?,?,?,?,?)";
    	Object[] bindArgs = { prize.getGoalID(), prize.getTitle(),prize.getContent(),prize.getPrizeType(),prize.getAvailableCount(),prize.getRate()};
    	wdb.execSQL(strSql,bindArgs);
    }
    public void UpdatePrize(Prize prize)
    {
    	String strSql="Update [tbl_prize] set goalID=?, title=?, content=?, prizeType=?, availableCount=?, rate=? where id=?";
    	Object[] bindArgs = { prize.getGoalID(), prize.getTitle(),prize.getContent(),prize.getPrizeType(),prize.getAvailableCount(),prize.getRate(),prize.getId()};
    	wdb.execSQL(strSql,bindArgs);
    }
    public void deletPrize(int id)
    {
    	String strSql="Delete * from [tbl_prize] where id=?";
    	Object[] bindArgs ={id};
    	rdb.execSQL(strSql,bindArgs);
    }
    public Prize describePrize(int id)
    {
    	String strSql="Select * from [tbl_prize] where id=?";
    	Prize prize =null;
    	String[] bindArgs ={String.valueOf(id)};
    	Cursor cursor = rdb.rawQuery(strSql,bindArgs);
        if (cursor.moveToNext())
        	prize = new Prize(cursor.getInt(0),cursor.getInt(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getInt(5),cursor.getInt(6));
        return prize;
    }
    public List<Prize> queryPrizeList()
    {
    	return this.queryPrizeListByAvailableCount(0);
    }
    public List<Prize> queryAvailablePrizeList()
    {
    	return this.queryPrizeListByAvailableCount(1);
    }
    private List<Prize> queryPrizeListByAvailableCount(int count)
    {
    	String strSql="Select * from [tbl_prize] where availableCount>=? order by [rate] desc";
    	String[] bindArgs ={String.valueOf(count)};
    	Cursor cursor = rdb.rawQuery(strSql,bindArgs);
        List<Prize> list=new ArrayList<Prize>();
        while (cursor.moveToNext())
        	 list.add(new Prize(cursor.getInt(0),cursor.getInt(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getInt(5),cursor.getInt(6)));
        return list;
    }
}
