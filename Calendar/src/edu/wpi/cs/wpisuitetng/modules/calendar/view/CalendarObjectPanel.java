package edu.wpi.cs.wpisuitetng.modules.calendar.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
	int columnwidth = 1;
	int columnspanned = 1;
	
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
		this.setBackground(Color.WHITE);
		this.addMouseListener(new MouseAdapter(){
		    public void mouseClicked(MouseEvent e) {
		    	if(e.getClickCount() > 1){
		    		callEdit();
		    	}
		    }
		});
	}
	
	public String getName(){
		if(event != null){
			return event.getName();
		}
		else if(comm != null){
			return comm.getName();
		}
		return "";
	}
	
	public void refreshSize(){
		double par_width = parent.getSize().getWidth();
		double par_height = parent.getSize().getHeight();
		Dimension new_size = new Dimension((int)((par_width-3*columnwidth)/columnwidth * columnspanned), (int)(getSizeIndex()/48.0*par_height));
		//System.out.println("Refresh : " + new_size + " : " + this.getName());
		//this.setSize(new_size);
		this.setPreferredSize(new_size);
	}
	
	public int setColumnWidth(int columnwidth){
		return (this.columnwidth = columnwidth);
	}
	
	public int getColumnWidth(){
		return this.columnwidth;
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
		//System.out.println("Start : " + index + " : " + this.getName());
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
		//System.out.println("End : " + index + " : " + this.getName());
		return index;
	}
	
	public GregorianCalendar getStart(){
		if(event != null){
			GregorianCalendar cal = new GregorianCalendar();
			cal.setTime(event.getStartTime().getTime());
			return cal;
		}
		else if(comm != null){
			GregorianCalendar cal = new GregorianCalendar();
			cal.setTime(comm.getDueDate().getTime());
			return cal;
		}
		return new GregorianCalendar();
	}
	
	public GregorianCalendar getEnd(){
		if(event != null){
			GregorianCalendar cal = new GregorianCalendar();
			cal.setTime(event.getEndTime().getTime());
			cal.add(Calendar.MINUTE, 290);
			return cal;
			//return (GregorianCalendar)event.getEndTime().clone();
		}
		else if(comm != null){
			GregorianCalendar cal = new GregorianCalendar();
			cal.setTime(comm.getDueDate().getTime());
			cal.add(Calendar.MINUTE, 30);
			return cal;
		}
		return new GregorianCalendar();
	}
	
	public boolean doesConflict(CalendarObjectPanel other){
		int thisstart = this.getStartIndex();
		int thisend = this.getEndIndex();
		int otherstart = other.getStartIndex();
		int otherend = other.getEndIndex();
		/*System.out.println("------------------------");
		System.out.println("" + this.getStartIndex() + " -- " + this.getEndIndex());
		System.out.println("" + other.getStartIndex() + " -- " + other.getEndIndex());
		System.out.println( (thisstart < otherend) && (thisend > otherstart));
		System.out.println("------------------------");*/
		return (thisstart < otherend) && (thisend > otherstart);
	}
	
	public void callEdit(){
		if(event != null){
			GUIEventController.getInstance().editEvent(event);
		}
		else if(comm != null){
			GUIEventController.getInstance().editCommitment(comm);
		}
	}
}
