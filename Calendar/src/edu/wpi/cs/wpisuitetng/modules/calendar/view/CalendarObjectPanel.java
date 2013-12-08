package edu.wpi.cs.wpisuitetng.modules.calendar.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.modules.calendar.CalendarStandard;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.Event;

public class CalendarObjectPanel extends JPanel {
	Event event = null;
	Commitment comm = null;
	GregorianCalendar acal = new GregorianCalendar();
	JComponent parent = null;
	int width = 1;
	
	public CalendarObjectPanel(JComponent parent, GregorianCalendar acal, Event event){
		this(parent, acal);
		this.event = event;
	}
	
	public CalendarObjectPanel(JComponent parent, GregorianCalendar acal, Commitment comm){
		this(parent, acal);
		this.comm = comm;
	}
	
	protected CalendarObjectPanel(JComponent parent, GregorianCalendar acal){
		super();
		this.parent = parent;
		this.acal.setTime(acal.getTime());
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));
		this.setBackground(CalendarStandard.CalendarYellow);
	}
	
	public void refreshSize(){
		double par_width = parent.getSize().getWidth();
		double par_height = parent.getSize().getHeight();
		Dimension new_size = new Dimension((int)(par_width/width), (int)(getSizeIndex()/48.0*par_height));
		System.out.println("Refresh : " + new_size);
		this.setSize(new_size);
		this.setPreferredSize(new_size);
	}
	
	public int setWidth(int width){
		return (this.width = width);
	}
	
	public int getWidth(){
		return this.width;
	}
	
	public int getStartIndex(){
		GregorianCalendar tempstart = (GregorianCalendar)this.acal.clone();
		tempstart.set(Calendar.HOUR_OF_DAY, 0);
		tempstart.set(Calendar.MINUTE, 0);
		tempstart.set(Calendar.SECOND, 0);
		tempstart.set(Calendar.MILLISECOND, 0);
		
		int index = 0;
		if(!this.getStart().before(tempstart)){
			index = 2*this.getStart().get(Calendar.HOUR_OF_DAY);
			if(this.getStart().get(Calendar.MINUTE) >= 30 ){
				index++;
			}
		}
		System.out.println("Start index : " + index);
		return index;
	}
	
	public int getSizeIndex(){
		return getEndIndex() - getStartIndex();
	}
	
	public int getEndIndex(){
		GregorianCalendar tempend = (GregorianCalendar)this.acal.clone();
		tempend.set(Calendar.HOUR_OF_DAY, 0);
		tempend.set(Calendar.MINUTE, 0);
		tempend.set(Calendar.SECOND, 0);
		tempend.set(Calendar.MILLISECOND, 0);
		tempend.add(Calendar.DATE, 1);
		
		int index = 48;
		if(!this.getEnd().after(tempend)){
			index = 2*this.getEnd().get(Calendar.HOUR_OF_DAY);
			if(this.getEnd().get(Calendar.MINUTE) >= 30 ){
				index++;
			}
		}
		System.out.println("End index : " + index);
		return index;
	}
	
	public GregorianCalendar getStart(){
		if(event != null){
			return (GregorianCalendar)event.getStartTime().clone();
		}
		else if(comm != null){
			return (GregorianCalendar)comm.getDueDate().clone();
		}
		return new GregorianCalendar();
	}
	
	public GregorianCalendar getEnd(){
		if(event != null){
			return (GregorianCalendar)event.getEndTime().clone();
		}
		else if(comm != null){
			GregorianCalendar cal = (GregorianCalendar)(comm.getDueDate().clone());
			cal.add(Calendar.MINUTE, 30);
			return cal;
		}
		return new GregorianCalendar();
	}
	
	public boolean doesConflict(CalendarObjectPanel other){
		GregorianCalendar thisstart = this.getStart();
		GregorianCalendar thisend = this.getEnd();
		GregorianCalendar otherstart = other.getStart();
		GregorianCalendar otherend = other.getEnd();
		
		if(thisstart.before(otherstart)){
			if(thisend.after(otherstart)){
				return true;
			}
			if(thisend.before(otherend)){
				return true;
			}
		}
		else if(thisstart.after(otherstart)){
			if(thisstart.before(otherend)){
				return true;
			}
		}
		else{
			return true;
		}
		return false;
	}
	
}
