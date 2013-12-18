/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: CS Anonymous
 ******************************************************************************/

package edu.wpi.cs.loginactivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import edu.wpi.cs.marvin.R;
import edu.wpi.edu.cs.calendar.CalendarData;
import edu.wpi.edu.cs.calendar.CalendarDataModel;
import edu.wpi.edu.cs.calendar.CalendarException;
import edu.wpi.edu.cs.calendar.CombinedCommitmentList;
import edu.wpi.edu.cs.calendar.CombinedEventList;
import edu.wpi.edu.cs.calendar.Commitment;
import edu.wpi.edu.cs.calendar.CommitmentList;
import edu.wpi.edu.cs.calendar.Event;
import edu.wpi.edu.cs.calendar.EventList;
import edu.wpi.edu.cs.calendar.GetCalendarDataController;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.ActionMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
/*

 * This code is very messy I am not using most of it because it was taken from my day view class

 * 
 * */
public class AndroidEventFull extends Activity implements Runnable{

	
	LinearLayout mainLayout;
	LinearLayout topLayout;
	TextView titleDate;
	String currentDate;

	LinearLayout tabb1;
	LinearLayout tabb2;
	ScrollView tab1Scroll;
	ScrollView tab2Scroll;
	LinearLayout insideTab1;
	LinearLayout insideTab2;
	
	private ProgressDialog pd;
	
	ArrayList<TextView> events;
	ArrayList<TextView> commitments;
	
	private CalendarData CalData;
	private CalendarData getTeamCalData;
	private CommitmentList commitmentfullList;
	private List<Commitment> commitmentList;
	public static final int CalendarYellow = Color.rgb(255,255,220);
	public static final int CalendarRed =  Color.rgb(196, 0, 14);
	private EventList eventFullList;
	private List<Event> eventList;
	
	private Spinner spinner;
	private Button refresh;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_layout);
		
		DaySingleton.getInstance().setTeam(DaySingleton.getInstance().getPESONAL());
		setCalData();
		
		
		DateFormat formatter = new SimpleDateFormat("MMM d yyyy");
		Log.d("current day", formatter.format(DaySingleton.getInstance().getSelectedDay()));
		currentDate =  formatter.format(DaySingleton.getInstance().getSelectedDay());
		setupTop();
		setupTabs();
		setupSpinner();
		
	}

	private void setCalData() {
		      
			CombinedCommitmentList combinedCommList;
			CombinedEventList combinedEventList;
			//If we are supposed to display just my calendar data
			if(DaySingleton.getInstance().myTeam() == DaySingleton.getInstance().getPESONAL() || DaySingleton.getInstance().myTeam() == DaySingleton.getInstance().getBOTH()){
			    CalData = CalendarDataModel.getInstance().getCalendarData(
						DaySingleton.getInstance().getProjectName() + "-"
								+ DaySingleton.getInstance().getUserName());	
				//create a combined Commitment list
				combinedCommList = new CombinedCommitmentList(
						new ArrayList<Commitment>(CalData
								.getCommitments().getCommitments()));
				//create a combined event list
				combinedEventList = CalData
						.getRepeatingEvents().toCombinedEventList();
				for (int i = 0; i < CalData.getEvents().getEvents().size(); i++) {
					combinedEventList.add(CalData.getEvents()
							.getEvents().get(i));
				}
				
				if (DaySingleton.getInstance().myTeam() == DaySingleton.getInstance().getBOTH()) {
					
					getTeamCalData = CalendarDataModel.getInstance().getCalendarData(
							DaySingleton.getInstance().getProjectName());
					// Iterate through team commitments and add each element to
					// combinedList
					// do it backwards to maintain order
					int j = getTeamCalData.getCommitments().getCommitments().size() - 1;
					for (int i = j; i >= 0; i--) {
						combinedCommList.add(getTeamCalData.getCommitments()
								.getCommitments().get(i));
					}
					

					//get the combined events for team
					final CombinedEventList teamRepeatEvents = 
							getTeamCalData.getRepeatingEvents().toCombinedEventList();
					for (int i = 0; i < teamRepeatEvents.getEvents().size(); i++){
						combinedEventList.add(teamRepeatEvents.getEvents().get(i));
					}

					// Iterate through team events and add each element to
					// combinedEventList
					// do it backwards to maintain order
					j = getTeamCalData.getEvents().getEvents().size() - 1;
					for (int i = j; i >= 0; i--) {
						combinedEventList.add(getTeamCalData.getEvents()
								.getEvents().get(i));
					}
					

				}
				
			}
			else {//if team is selected
				//create a combined Commitment list
				getTeamCalData = CalendarDataModel.getInstance().getCalendarData(
						DaySingleton.getInstance().getProjectName());
				
				combinedCommList = new CombinedCommitmentList(
						new ArrayList<Commitment>(getTeamCalData
								.getCommitments().getCommitments()));
				//create a combined event list
				combinedEventList = getTeamCalData
						.getRepeatingEvents().toCombinedEventList();
				for (int i = 0; i < getTeamCalData.getEvents()
						.getEvents().size(); i++) {
					combinedEventList.add(getTeamCalData.getEvents()
							.getEvents().get(i));
				}
			}
		    
	
			eventFullList = combinedEventList;
			commitmentfullList = combinedCommList;
		
	}

	private void setupSpinner() {
		spinner = (Spinner)findViewById(R.id.spinner1);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource( 
				this, R.array.day, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); 
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				if(arg0.getSelectedItemPosition() == DaySingleton.getInstance().getPESONAL()){
					DaySingleton.getInstance().setTeam(DaySingleton.getInstance().getPESONAL());
					setCalData();
					getTabs();
				}
				else if(arg0.getSelectedItemPosition() == DaySingleton.getInstance().getTEAM()){
					DaySingleton.getInstance().setTeam(DaySingleton.getInstance().getTEAM());
					setCalData();
					getTabs();
				}
				else if(arg0.getSelectedItemPosition() == DaySingleton.getInstance().getBOTH()){
					DaySingleton.getInstance().setTeam(DaySingleton.getInstance().getBOTH());
					setCalData();
					getTabs();
				}
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
			
			
		});
		
		
		
		
	}

	private void setupTabs() { 
		//tabs = new TabHost(this);
		//eventsTab = tabs.newTabSpec("Events");
		
        tabb1 = (LinearLayout) findViewById(R.id.tab1);
        tabb2 = (LinearLayout) findViewById(R.id.tab2);
		tab1Scroll = new ScrollView(this);
		tab2Scroll = new ScrollView(this);
		events = new ArrayList<TextView>();
		commitments = new ArrayList<TextView>();
		insideTab1 = new LinearLayout(this);
		insideTab2 = new LinearLayout(this);
		insideTab1.setOrientation(LinearLayout.VERTICAL);
		insideTab2.setOrientation(LinearLayout.VERTICAL);
		tab1Scroll.addView(insideTab1);
		tab2Scroll.addView(insideTab2);
        
		getTabs();
		
		tabb2.addView(tab1Scroll);
		//tabb2.addView(tab2Scroll);
		
        
		
	}

	private void getTabs() {
		
		
		/*
		
		for(int i = 0; i < 20; i++){
			TextView text = new TextView(this);
			text.setText("Event: "+i+"\n"+ currentDate+ "\n"+ "This is the description"+ "\n"+ "New"+ "\n");
			
			TextView text2 = new TextView(this);
			text2.setText("Commitment: "+i+"\n"+ currentDate + "\n"+ "This is the description"+ "\n"+ "New"+ "\n");
			//events.add(text);
			insideTab1.addView(text);
			insideTab2.addView(text2);
		}
		
		*/

		
        //events : change to events instead
		insideTab1.removeAllViews();
		insideTab2.removeAllViews();
		events.clear();
		commitments.clear();
				eventList = eventFullList.getEvents();
				DateFormat formatter = new SimpleDateFormat("EEE dd MMM yyy hh:mm a");  
		
				for(int i = 0; i< eventList.size(); i++){
					LinearLayout layout = new LinearLayout(this);
					ImageView icon = new ImageView(this);
					TextView text = new TextView(this);
					text.setText("Name: "+ eventList.get(i).getName()+"\n"+
					"Start Time: " + formatter.format(eventList.get(i).getStartTime().getTime())+"\n"+
					"End Time: " + formatter.format(eventList.get(i).getEndTime().getTime()) + "\n" +
					"Description: "+ eventList.get(i).getDescription());
					events.add(text);
					if(eventList.get(i).getIsPersonal()){
						icon.setImageResource(R.drawable.personalevent_icon);
					}
					else{
						icon.setImageResource(R.drawable.teamevent_icon);
					}
					layout.addView(icon);
					layout.addView(events.get(i));
					
					insideTab1.addView(layout);
					ImageView divider = new ImageView(this);
					LinearLayout.LayoutParams lp = 
					    new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 5);
					lp.setMargins(0, 1, 0, 1);
					divider.setLayoutParams(lp);
					divider.setBackgroundColor(CalendarRed);
					insideTab1.addView(divider);
				}		
		
		
		commitmentList = commitmentfullList.getCommitments();
				for(int i = 0; i< commitmentList.size(); i++){
					LinearLayout layout = new LinearLayout(this);
					ImageView icon = new ImageView(this);
					TextView text = new TextView(this);
					text.setText("Name: "+ commitmentList.get(i).getName()+"\n"+
					"Due Date: " + formatter.format(commitmentList.get(i).getDueDate().getTime())+"\n"+
					"Description: "+ commitmentList.get(i).getDescription()+"\n"+
					"Status: "+ commitmentList.get(i).getStatus());
					commitments.add(text);
					if(commitmentList.get(i).getIsPersonal()){
						icon.setImageResource(R.drawable.personalcommitment_icon);
					}
					else{
						icon.setImageResource(R.drawable.teamcommitment_icon);
					}
					layout.addView(icon);
					layout.addView(commitments.get(i));
					insideTab2.addView(layout);
					ImageView divider = new ImageView(this);
					LinearLayout.LayoutParams lp = 
					    new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 5);
					lp.setMargins(0, 1, 0, 1);
					divider.setLayoutParams(lp);
					divider.setBackgroundColor(CalendarRed);
					insideTab2.addView(divider);
				}
				
				
		
		
		
		
	}

	private void setupTop() {
		topLayout = (LinearLayout)findViewById(R.id.LinearLayout1);
		topLayout.setBackgroundColor(CalendarYellow);
		titleDate = (TextView)findViewById(R.id.textView1);
		titleDate.setText("All Events");
		titleDate.setTextSize(25);
		titleDate.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		refresh = (Button)findViewById(R.id.button1);
		refresh.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
		        pd = ProgressDialog.show(AndroidEventFull.this, "Loading..", "Getting Calendar Data", true,
		                false);
		        Thread thread = new Thread(AndroidEventFull.this);
		        thread.start();
			}
			
		});
	}

	@Override
	public void run() {
		DaySingleton.getInstance().setCalStatus("Grabbing");
		GetCalendarDataController.getInstance().retrieveCalendarData();
		while(DaySingleton.getInstance().getCalStatus().equals("Grabbing")){
			
		}
	
	handler.sendEmptyMessage(0);
	}
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
	
			pd.dismiss();
			setCalData();
			getTabs();
		
		}
	};
}
