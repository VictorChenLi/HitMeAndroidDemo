package org.stl.hitme.storeCat.model;

import org.stl.hitme.storeCat.service.DBAccessImpl;
import org.stl.hitme.sysUtil.model.Constant;

import android.content.Context;

public class Stage {
	private int id;
	private int goalID;
	private String title;
	private String content;
	private int index;
	private long startTime;
	private long endTime;
	private double percentage;
	private double idealPercentage;
	private long investTime;//Unit is minute
	private long spendTime;
	private long stageValue;
	private String stageStatus;
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getGoalID() {
		return goalID;
	}

	public void setGoalID(int goalID) {
		this.goalID = goalID;
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

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
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

	public double getPercentage() {
		return percentage;
	}

	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}

	public double getIdealPercentage() {
		return idealPercentage;
	}

	public void setIdealPercentage(double idealPercentage) {
		this.idealPercentage = idealPercentage;
	}
	
	public long getInvestTime() {
		return investTime;
	}

	public void setInvestTime(long investTime) {
		this.investTime = investTime;
	}

	public long getSpendTime() {
		return spendTime;
	}

	public void setSpendTime(long spendTime) {
		this.spendTime = spendTime;
	}

	public long getStageValue() {
		return stageValue;
	}

	public void setStageValue(long stageValue) {
		this.stageValue = stageValue;
	}

	public synchronized void plusSpendTime(DBAccessImpl dbAccessImpl, long spendTime)
	{
		this.spendTime+=spendTime;
		if(null!=this.stageStatus&&!this.stageStatus.equals(Constant.stageStatus.COMPLETE.toString())&&this.spendTime>=this.investTime)
		{
			this.stageStatus=Constant.stageStatus.COMPLETE.toString();
			Goal goal = dbAccessImpl.describeGoal(this.goalID);
			goal.plusAchievedValue(dbAccessImpl, this.stageValue);
		}
		dbAccessImpl.UpdateStage(this);
	}

	public String getStageStatus() {
		return stageStatus;
	}

	public void setStageStatus(String stageStatus) {
		this.stageStatus = stageStatus;
	}

	public Stage(int id, int goalID, String title, String content, int index,
			long startTime, long endTime, double percentage,
			long investTime, long spendTime, long stageValue, String stageStatus) {
		super();
		this.id = id;
		this.goalID = goalID;
		this.title = title;
		this.content = content;
		this.index = index;
		this.startTime = startTime;
		this.endTime = endTime;
		this.percentage = percentage;
		this.investTime = investTime;
		this.spendTime = spendTime;
		this.stageValue=stageValue;
		this.stageStatus=stageStatus;
	}

	public Stage(int goalID, String title, String content, int index,
			long startTime, long endTime, double percentage,
			double idealPercentage, long investTime, long spendTime, long stageValue, String stageStatus) {
		super();
		this.goalID = goalID;
		this.title = title;
		this.content = content;
		this.index = index;
		this.startTime = startTime;
		this.endTime = endTime;
		this.percentage = percentage;
		this.idealPercentage = idealPercentage;
		this.investTime = investTime;
		this.spendTime = spendTime;
		this.stageValue = stageValue;
		this.stageStatus=stageStatus;
	}

	public Stage()
	{
		super();
	}
	
}
