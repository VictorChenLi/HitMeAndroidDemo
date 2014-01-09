package org.stl.hitme.sysUtil.ui;

import java.util.Calendar;

import org.stl.hitme.R;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
//import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.os.Bundle;
import android.os.Handler.Callback;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class TimePickerFragment extends DialogFragment implements
		OnTimeSetListener {
	
	private int year;
	private int month;
	private int day;
	private int hour;
	private int minute;
	private PickerType type=PickerType.TIME;
	private OnTimeSetListener timeListener;
	private OnDateSetListener dateListener;
	public enum PickerType{DATE,TIME};

	public int getHour() {
		return hour;
	}

	public int getMinute() {
		return minute;
	}

	public PickerType getType() {
		return type;
	}

	public void setType(PickerType type) {
		this.type = type;
	}

	public OnTimeSetListener getTimeListener() {
		return timeListener;
	}

	public void setTimeListener(OnTimeSetListener timeListener) {
		this.timeListener = timeListener;
	}

	public OnDateSetListener getDateListener() {
		return dateListener;
	}

	public void setDateListener(OnDateSetListener dateListener) {
		this.dateListener = dateListener;
	}

	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        int year= c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        if(type==PickerType.DATE)
        {
        	return new DatePickerDialog(getActivity(), DatePickerDialog.BUTTON_NEUTRAL, dateListener, year, month, day);
        }
        if(type==PickerType.TIME)
        {
            // Create a new instance of TimePickerDialog and return it
          return new TimePickerDialog(getActivity(), timeListener, hour, minute,
                  DateFormat.is24HourFormat(getActivity()));
        }
		return null;
    }
	
	public void onTimeSet(TimePicker arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		hour = arg1;
		minute = arg2;
		TextView dataView = (TextView) getActivity().findViewById(R.id.dataView);
		dataView.setText(hour+":"+minute);
		Toast.makeText(getActivity(), "It is"+arg1+" o'clock and "+arg2+" minute", Toast.LENGTH_LONG).show();
	}

}
