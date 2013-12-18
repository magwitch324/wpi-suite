package edu.wpi.cs.loginactivity;

import java.util.List;

import edu.wpi.cs.marvin.R;
import edu.wpi.edu.cs.calendar.CalendarData;
import edu.wpi.edu.cs.calendar.CalendarDataModel;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

public class AndroidCalendar extends Activity implements OnClickListener, OnDateChangeListener, OnTouchListener, onCalendarClick{
	
	private static final int CURDAY = 0;
	public static final int CalendarYellow = Color.rgb(255,255,220);
	public static final int CalendarRed =  Color.rgb(196, 0, 14);
	private static final int CALENDAR = 1;
	private static final int EVENTS = 2;
	private static final int COMMITMENTS = 3;
	private int WIDTH;
	private int HEIGHT;
	private TextView listNames;
	private AndroidCalendarView calendarView;
	private LinearLayout mainLayout;
	private LinearLayout topLayout;
	private Button currentDayButton;
	private Button viewAllEvents;
	private Button viewAllCommitments;
	private long todaysDate;
	private long currentDate;
	private int doubleClick = 0;
	private CalendarData CalData;
	private TextView calendarName;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getScreen();
		setupTopLayout();
		setupCalendar();
		setupMainLayout();
		//setContentView(R.layout.login_layout);
		//setupTestList();
		//printList(commitmentList.getCommitments());
		Color c = new Color();
		DaySingleton.getInstance().setTeam(DaySingleton.getInstance().getPESONAL());
		if(DaySingleton.getInstance().myTeam() == DaySingleton.getInstance().getPESONAL()){
	    CalData = CalendarDataModel.getInstance().getCalendarData(
				DaySingleton.getInstance().getProjectName() + "-"
						+ DaySingleton.getInstance().getUserName());
		}
		else if(DaySingleton.getInstance().myTeam() == DaySingleton.getInstance().getTEAM()){
			
		}
		
	}

	@SuppressLint("NewApi")
	private void getScreen() {
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		WIDTH = size.x;
		HEIGHT = size.y;
	
	}


	private void setupTopLayout() {
		topLayout = new LinearLayout(this);
		topLayout.setOrientation(LinearLayout.HORIZONTAL);
		topLayout.setBackgroundColor(CalendarRed);
		topLayout.setGravity(Gravity.RIGHT);
		LinearLayout.LayoutParams l1 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
		l1.gravity = Gravity.RIGHT;
		topLayout.setLayoutParams(l1);
		viewAllEvents = new Button(this);
		viewAllCommitments = new Button(this);
		viewAllEvents.setOnClickListener(this);
		viewAllCommitments.setOnClickListener(this);
		viewAllEvents.setBackgroundResource(R.drawable.agenda_icon);
		LinearLayout.LayoutParams l = new LinearLayout.LayoutParams(WIDTH/10,WIDTH/10);
		l.leftMargin = 10;
		l.rightMargin = 10;
		viewAllEvents.setLayoutParams(l);
		viewAllCommitments.setBackgroundResource(R.drawable.all_icon);
		viewAllCommitments.setLayoutParams(l);
		viewAllEvents.setId(EVENTS);
		viewAllCommitments.setId(COMMITMENTS);
		currentDayButton = new Button(this);
		currentDayButton.setOnClickListener(this);
		currentDayButton.setBackgroundResource(android.R.drawable.ic_menu_day);
		currentDayButton.setId(CURDAY);
		currentDayButton.setText("");
		LinearLayout.LayoutParams l3 = new LinearLayout.LayoutParams(WIDTH/2,LayoutParams.WRAP_CONTENT);
		calendarName = new TextView(this);
		calendarName.setText("WPI Calendar");
		calendarName.setTextSize(24);
		calendarName.setLayoutParams(l3);
		topLayout.addView(calendarName);
		topLayout.addView(viewAllEvents);
		topLayout.addView(viewAllCommitments);
		topLayout.addView(currentDayButton);
	}



	private void setupMainLayout() {
		mainLayout = new LinearLayout(this);
		mainLayout.setOnTouchListener((OnTouchListener) this);
		mainLayout.setOrientation(LinearLayout.VERTICAL);
		mainLayout.addView(topLayout);
		mainLayout.addView(calendarView);
		setContentView(mainLayout);
		
	}



	private void setupCalendar() {
		calendarView = new AndroidCalendarView(this);
		calendarView.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		calendarView.setBackgroundColor(CalendarYellow);
		//calendarView.setOnClickListener(this);
		
		calendarView.setOnDateChangeListener(this);
		calendarView.setClickable(true);
		calendarView.addMsgListener(this);
		//calendarView.setId(CALENDAR);
		todaysDate = calendarView.getDate();
		//setContentView(calendarView);
	}





	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case CURDAY:
			calendarView.setDate(todaysDate, true, true);
			break;
		case EVENTS:
			startActivity(new Intent("edu.wpi.cs.loginactivity.AndroidEventFull"));
			break;
		case COMMITMENTS:
			startActivity(new Intent("edu.wpi.cs.loginactivity.AndroidCommitmentFull"));
			break;
		}
		
	}



	@Override
	public void onSelectedDayChange(CalendarView view, int year, int month,
			int dayOfMonth) {
		
		//Log.d("HELLO", String.valueOf(((CalendarView) view).getDate()));
		
	}



	@Override
	public boolean onTouch(View v, MotionEvent event) {
		Log.d("", "IT WORKED");
		return true;
	}



	@Override
	public void onClick() {
		 DaySingleton.getInstance().setSelectedDay(calendarView.getDate());
		 Log.d("first", String.valueOf(calendarView.getDate()));
		 startActivity(new Intent("edu.wpi.cs.loginactivity.AndroidDayView"));
		
	}



	
	

}
