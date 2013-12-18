package edu.wpi.cs.loginactivity;

import java.util.LinkedList;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

public class AndroidCalendarView extends CalendarView{

	private long currentDate;
	private int doubleClick = 0;
	private LinkedList<onCalendarClick> listeners = new LinkedList<onCalendarClick>();
	private float beforeY;
	
	public AndroidCalendarView(Context context) {
		super(context);
	}
	
	//Make a listener for the android calendar class that will send info when its been clicked here
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
	switch(ev.getAction()){
		case MotionEvent.ACTION_DOWN:
			beforeY = ev.getY();
		break;
		
		case MotionEvent.ACTION_UP:
			if(ev.getY() == beforeY){
			if(doubleClick == 0){
				doubleClick++;
				currentDate = this.getDate();
			}
			else if(doubleClick == 1 ){
				if(currentDate == this.getDate()){
					Log.d("HELLO", String.valueOf(this.getDate())); //here
					processMessage();
					doubleClick = 0;
				}
				else{
					doubleClick = 1;
					currentDate = this.getDate();
				}
			}
			}
		break;
	
	}
	return super.onInterceptTouchEvent(ev);
	}
	
	public void addMsgListener(onCalendarClick listener){
	    listeners.add(listener);
	}
	
	public boolean removeMsgListener(onCalendarClick listener){
	    return listeners.remove(listener);
	} 
	
	public void processMessage() {
	   
	        for(onCalendarClick l: listeners){
	            l.onClick();
	        }
	    
	} 

}
