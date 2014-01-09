package org.stl.hitme.storeCat.model;

public class TaskAttributes {
	private int id;
	private int stageID;
	private String title;
	private String content;
	private long alarmTime;
	private int taskType;
	private int delayTimes;
	private String taskStatus;
	private long startTime;
	private long endTime;
	private long spendTime;
	private int extendTimes;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getStageID() {
		return stageID;
	}
	public void setStageID(int stageID) {
		this.stageID = stageID;
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
	public long getAlarmTime() {
		return alarmTime;
	}
	public void setAlarmTime(long alarmTime) {
		this.alarmTime = alarmTime;
	}
	public int getTaskType() {
		return taskType;
	}
	public void setTaskType(int taskType) {
		this.taskType = taskType;
	}
	public int getDelayTimes() {
		return delayTimes;
	}
	public void setDelayTimes(int delayTimes) {
		this.delayTimes = delayTimes;
	}
	public synchronized void plusDelayTimes()
	{
		this.delayTimes++;
	}
	public String getTaskStatus() {
		return taskStatus;
	}
	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
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
	public long getSpendTime() {
		return spendTime;
	}
	public void setSpendTime(long spendTime) {
		this.spendTime = spendTime;
	}
	public int getExtendTimes() {
		return extendTimes;
	}
	public void setExtendTimes(int extendTimes) {
		this.extendTimes = extendTimes;
	}
	public synchronized void plusExtendTimes()
	{
		this.extendTimes++;
	}
	public TaskAttributes(int id, int stageID, String title, String content,
			long alarmTime, int taskType, int delayTimes, String taskStatus,
			long startTime, long endTime, long spendTime, int extendTimes) {
		super();
		this.id = id;
		this.stageID = stageID;
		this.title = title;
		this.content = content;
		this.alarmTime = alarmTime;
		this.taskType = taskType;
		this.delayTimes = delayTimes;
		this.taskStatus = taskStatus;
		this.startTime = startTime;
		this.endTime = endTime;
		this.spendTime = spendTime;
		this.extendTimes = extendTimes;
	}
	public TaskAttributes(int stageID, String title, String content,
			long alarmTime, int taskType, int delayTimes, String taskStatus,
			long startTime, long endTime, long spendTime, int extendTimes) {
		super();
		this.stageID = stageID;
		this.title = title;
		this.content = content;
		this.alarmTime = alarmTime;
		this.taskType = taskType;
		this.delayTimes = delayTimes;
		this.taskStatus = taskStatus;
		this.startTime = startTime;
		this.endTime = endTime;
		this.spendTime = spendTime;
		this.extendTimes = extendTimes;
	}
	public TaskAttributes(TaskAttributes copyTask)
	{
		this.stageID=copyTask.getStageID();
		this.title=copyTask.getTitle();
		this.content=copyTask.getContent();
		this.alarmTime=copyTask.getAlarmTime();
		this.taskType=copyTask.getTaskType();
		this.delayTimes=copyTask.getDelayTimes();
		this.taskStatus=copyTask.getTaskStatus();
		this.startTime=copyTask.getStartTime();
		this.endTime=copyTask.getEndTime();
		this.spendTime=copyTask.getSpendTime();
		this.extendTimes=copyTask.getExtendTimes();
	}
	public TaskAttributes()
	{
		super();
	}

}
