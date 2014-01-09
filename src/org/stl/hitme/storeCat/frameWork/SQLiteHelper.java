package org.stl.hitme.storeCat.frameWork;


import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class SQLiteHelper extends SQLiteOpenHelper {
	public static final String TB_Task="tbl_task";
	public static final String ID="id";
	public static final String Stage_ID="stageID";
	public static final String Str_Title="title";
	public static final String Long_AlarmTime="alarmTime";
	public static final String Str_Content="content";
	public static final String Int_TaskType="taskType";
	public static final String Int_DelayTimes="delayTimes";
	public static final String Int_TaskStatus="taskStatus";
	public static final String Long_StartTime="startTime";
	public static final String Long_EndTime="endTime";
	public static final String long_SpendTime="spendTime";
	public static final String Int_ExtendTimes="extendTimes";
	
	public static final String TB_Goal="tbl_goal";
	public static final String Real_Percentage="percentage";
	public static final String Str_GoalType="goalType";
	public static final String int_GoalValue="goalValue";
	public static final String int_AchievedValue="achievedValue";
	public static final String Str_goalStatus="goalStatus";
	
	public static final String TB_Stage="tbl_stage";
	public static final String Goal_ID="goalID";
	public static final String Int_Index="index";
	public static final String Long_InvestTime="investTime";
	public static final String Long_SpendTime="spendTime";
	public static final String Int_stageValue="stageValue";
	public static final String Str_stageStatus="stageStatus";
	
	public static final String TB_Prize="tbl_prize";
	public static final String Str_PrizeType="prizeType";
	public static final String Int_AvailableCount="availableCount";
	public static final String Int_Rate="rate";
	
	public SQLiteHelper(Context context,String name,CursorFactory factory, int version)
	{
		super(context, name, factory, version);
	}
	
	
	@Override
	public void onCreate(SQLiteDatabase arg0) {
		//创建表tbl_task
//		Object[] bindArgsTask = { TB_Task,ID,Stage_ID,Str_Title,Str_Content,Long_AlarmTime,Int_TaskType,Int_DelayTimes,Int_TaskStatus};
		String strCreatParam="CREATE TABLE IF NOT EXISTS "+TB_Task+" ("+ID+" INTEGER PRIMARY KEY, "+Stage_ID+" INTEGER NOT NULL, "+Str_Title+" VARCHAR, "+Str_Content+" VARCHAR, "+Long_AlarmTime+" INTEGER, "+Int_TaskType+" INTEGER, "+Int_DelayTimes+" INTEGER, "+Int_TaskStatus+" INTEGER, "+Long_StartTime+" INTEGER, "+Long_EndTime+" INTEGER, "+long_SpendTime+" INTEGER, "+Int_ExtendTimes+" INTEGER)";
		arg0.execSQL(strCreatParam);
		
		//创建表tbl_stage
//		Object[] bindArgsStage = { TB_Stage,ID,Stage_ID,Str_Title,Str_Content,Int_Index,Long_StartTime,Long_EndTime,Real_Percentage,Long_InvestTime,Long_SpendTime};
		strCreatParam="CREATE TABLE IF NOT EXISTS "+TB_Stage+" ( "+ID+" INTEGER PRIMARY KEY, "+Goal_ID+" INTEGER NOT NULL, "+Str_Title+" VARCHAR, "+Str_Content+" VARCHAR, ["+Int_Index+"] INTEGER, "+Long_StartTime+" INTEGER, "+Long_EndTime+" INTEGER, "+Real_Percentage+" INTEGER, "+Long_InvestTime+" INTEGER, "+Long_SpendTime+" INTEGER, "+Int_stageValue+" INTEGER, "+Str_stageStatus+" VERCHAR)";
		arg0.execSQL(strCreatParam);
		
		//创建表tbl_goal
//		Object[] bindArgsGoal = { TB_Goal,ID,Str_Title,Str_Content,Long_StartTime,Long_EndTime,Real_Percentage,Str_GoalType,int_GoalValue,int_AchievedValue};
		strCreatParam="CREATE TABLE IF NOT EXISTS "+TB_Goal+" ("+ID+" INTEGER PRIMARY KEY, "+Str_Title+" VARCHAR, "+Str_Content+" VARCHAR, "+Long_StartTime+" INTEGER, "+Long_EndTime+" INTEGER, "+Real_Percentage+" INTEGER, "+Str_GoalType+" VARCHAR, "+int_GoalValue+" INTEGER, "+int_AchievedValue+" INTEGER, "+Str_goalStatus+" VERCHAR)";
		arg0.execSQL(strCreatParam);
		
		//创建表tbl_prize
//		Object[] bindArgsGoal = { TB_Goal,ID,Str_Title,Str_Content,Long_StartTime,Long_EndTime,Real_Percentage,Str_GoalType,int_GoalValue,int_AchievedValue};
		strCreatParam="CREATE TABLE IF NOT EXISTS "+TB_Prize+" ("+ID+" INTEGER PRIMARY KEY, "+Goal_ID+" INTEGER NOT NULL, "+Str_Title+" VARCHAR, "+Str_Content+" VARCHAR, "+Str_PrizeType+" VARCHAR, "+Int_AvailableCount+" INTEGER, "+Int_Rate+" INTEGER)";
		arg0.execSQL(strCreatParam);
		
//		bindArgsTask = {TB_Goal,ID,Real_Percentage};
//		String strCreatParam="CREATE TABLE IF NOT EXISTS ? (? INTEGER PRIMARY KEY, ? INTEGER NOT NULL, ? INTEGER NOT NULL, ? INTEGER, ? VARCHAR, ? INTEGER, ? INTEGER, ? INTEGER)";
//		arg0.execSQL(strCreatParam);
		
//		Object[] bindArgsHistInfo = { TB_HistInfo,HistInfo_ID,Forekey,StrMsg,Sequence,DateReceive, Type };
//		String strCreatHistInfo="CREATE TABLE IF NOT EXISTS "+TB_HistInfo+" ("+HistInfo_ID+" INTEGER PRIMARY KEY,"+Forekey+" INTEGER NOT NULL,"+StrMsg+" VARCHAR,"+Sequence+" INTEGER,"+DateReceive+" INTEGER,"+Type+" INTEGER )";
//		arg0.execSQL(strCreatHistInfo);
//		
//		Object[] bindArgsReSend = { TB_ReSend,ReSend_ID,Forekey,StrMsg,Sequence,CryptKey };
//		String strCreatReSends="CREATE TABLE IF NOT EXISTS "+TB_ReSend+" ("+ReSend_ID+" INTEGER PRIMARY KEY,"+Forekey+" INTEGER NOT NULL,"+StrMsg+" VARCHAR,"+Sequence+" INTEGER,"+CryptKey+" VARCHAR )";
//		arg0.execSQL(strCreatReSends);
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		arg0.execSQL("DROP TABLE IF EXISTS "+TB_Task);
		arg0.execSQL("DROP TABLE IF EXISTS "+TB_Stage);
		arg0.execSQL("DROP TABLE IF EXISTS "+TB_Goal);
		arg0.execSQL("DROP TABLE IF EXISTS "+TB_Prize);
		onCreate(arg0);
	}

}
