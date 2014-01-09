package org.stl.hitme.sysUtil.ui;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ProgressBar;

public class ExperienceBar extends ProgressBar {
	
	private Paint textPaint;
	private Paint linePaint;
	private String strTitle="";
	private int itemId;
	private static final int TEXT_SIZE=25;
	private int real_Progress=0;
	private int ideal_Progress=0;

	public ExperienceBar(Context context) {
		super(context);
		initPaint();
	}
	
	public ExperienceBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initPaint();
	}
	
	public ExperienceBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		initPaint();
	}
	
	public synchronized void setBothProgress(int realProgress,int idealProgress) {
		real_Progress=realProgress;
		ideal_Progress=idealProgress;
		setTitle(strTitle);
		super.setProgress(real_Progress);
		super.setSecondaryProgress(idealProgress);
	}
	
	private void initPaint() {
		this.textPaint = new Paint();
		this.textPaint.setAntiAlias(true);
		this.textPaint.setColor(Color.WHITE);
		this.textPaint.setTextSize(TEXT_SIZE);
		this.linePaint = new Paint();
		this.linePaint.setAntiAlias(true);
		this.linePaint.setColor(Color.WHITE);
		this.linePaint.setStyle(Paint.Style.STROKE);
	}
	
	@Override
	protected synchronized void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		Rect rect = new Rect();
		this.textPaint.getTextBounds(this.strTitle, 0, this.strTitle.length(), rect);
		int text_x = getWidth()/25;
		int text_y = (getHeight() / 2) - rect.centerY();
		canvas.drawText(this.strTitle, text_x, text_y, this.textPaint);
		text_x = getWidth()*3/4;
		String str_real_progress = String.valueOf((this.getProgress() * 100) / this.getMax()) +"%";
		String str_ideal_progress = String.valueOf((this.ideal_Progress * 100) / this.getMax())+"%";
		canvas.drawText(str_real_progress+"/"+str_ideal_progress, text_x, text_y, this.textPaint);
//		int real_x= (int)((double)ideal_Progress/100 * getWidth());
//		int real_start_y = 0;
//		int real_stop_y = getHeight();
//		canvas.drawLine(real_x, real_start_y, real_x, real_stop_y, linePaint);
	}
	
	@SuppressWarnings("unused")
	private void setTitle() {
		setTitle(String.valueOf(this.getProgress()));
	}

	// …Ë÷√Œƒ◊÷ƒ⁄»›
	public void setTitle(String title) {
		this.strTitle = title;
	}
	
	public String getTitle()
	{
		return this.strTitle;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	
	

}
