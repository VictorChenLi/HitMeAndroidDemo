package org.stl.hitme.storeCat.model;

import org.stl.hitme.storeCat.service.DBAccessImpl;
import org.stl.hitme.sysUtil.model.Constant;

import android.content.Context;

public class Goal {
	private int id;
	private String title;
	private String content;
	private long startTime;
	private long endTime;
	private double realPercentage;
	private double idealPercentage;
	private String goalType;
	private long goalValue;
	private long achievedValue;
	private String goalStatus;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public long getStartTime() {
		return startTime;
	}
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	public long getEndTime() {
		return endTime;
	}
	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}
	public double getRealPercentage() {
		return realPercentage;
	}
	public void setRealPercentage(double realPercentage) {
		this.realPercentage = realPercentage;
	}
	public double getIdealPercentage() {
		return idealPercentage;
	}
	public void setIdealPercentage(double idealPercentage) {
		this.idealPercentage = idealPercentage;
	}
	public String getGoalType() {
		return goalType;
	}
	public void setGoalType(String goalType) {
		this.goalType = goalType;
	}
	
	public long getGoalValue() {
		return goalValue;
	}
	public void setGoalValue(long goalValue) {
		this.goalValue = goalValue;
	}
	public long getAchievedValue() {
		return achievedValue;
	}
	public void setAchievedValue(long achievedValue) {
		this.achievedValue = achievedValue;
	}
	public synchronized void plusGoalValue(DBAccessImpl dbAccessImpl, long goalValue)
	{
		this.goalValue+=goalValue;
		if(!this.goalStatus.equals(Constant.goalStatus.RUN.toString())&&this.goalValue>this.achievedValue)
		{
			this.goalStatus=Constant.goalStatus.RUN.toString();
		}
		dbAccessImpl.UpdateGoal(this);
	}
	public synchronized void plusAchievedValue(DBAccessImpl dbAccessImpl, long achievedValue)
	{
		this.achievedValue+=achievedValue;
		if(!this.goalStatus.equals(Constant.goalStatus.COMPLETE.toString())&&this.achievedValue>=this.goalValue)
		{
			this.goalStatus=Constant.goalStatus.COMPLETE.toString();
		}
		dbAccessImpl.UpdateGoal(this);
	}
	public String getGoalStatus() {
		return goalStatus;
	}
	public void setGoalStatus(String goalStatus) {
		this.goalStatus = goalStatus;
	}
	public Goal(int id, String title, String content, long startTime,
			long endTime, double realPercentage, double idealPercentage,
			String goalType, long goalValue, long achievedValue, String goalStatus) {
		super();
		this.id = id;
		this.title = title;
		this.content = content;
		this.startTime = startTime;
		this.endTime = endTime;
		this.realPercentage = realPercentage;
		this.idealPercentage = idealPercentage;
		this.goalType = goalType;
		this.goalValue = goalValue;
		this.achievedValue = achievedValue;
		this.goalStatus = goalStatus;
	}
	public Goal(int id, String title, String content, long startTime,
			long endTime, double realPercentage,
			String goalType, long goalValue, long achievedValue, String goalStatus) {
		super();
		this.id = id;
		this.title = title;
		this.content = content;
		this.startTime = startTime;
		this.endTime = endTime;
		this.realPercentage = realPercentage;
		this.goalType = goalType;
		this.goalValue = goalValue;
		this.achievedValue = achievedValue;
		this.goalStatus = goalStatus;
	}
	public Goal()
	{
		super();
	}
	
	
}
