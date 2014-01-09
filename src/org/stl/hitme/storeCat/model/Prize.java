package org.stl.hitme.storeCat.model;

public class Prize {
	private int id;
	private int goalID;
	private String title;
	private String content;
	private String prizeType;
	private int availableCount;
	private int rate;
	
	public int getRate() {
		return rate;
	}
	public void setRate(int rate) {
		this.rate = rate;
	}
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
	public String getPrizeType() {
		return prizeType;
	}
	public void setPrizeType(String prizeType) {
		this.prizeType = prizeType;
	}
	public int getAvailableCount() {
		return availableCount;
	}
	public void setAvailableCount(int availableCount) {
		this.availableCount = availableCount;
	}
	public Prize()
	{
		super();
	}
	public Prize(int id, int goalID, String title, String content,
			String prizeType, int availableCount, int rate) {
		super();
		this.id = id;
		this.goalID = goalID;
		this.title = title;
		this.content = content;
		this.prizeType = prizeType;
		this.availableCount = availableCount;
		this.rate = rate;
	}
	public Prize(int goalID, String title, String content,
			String prizeType, int availableCount, int rate) {
		super();
		this.goalID = goalID;
		this.title = title;
		this.content = content;
		this.prizeType = prizeType;
		this.availableCount = availableCount;
		this.rate = rate;
	}
}
