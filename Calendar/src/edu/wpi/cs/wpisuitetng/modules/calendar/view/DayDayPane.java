package edu.wpi.cs.wpisuitetng.modules.calendar.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SpringLayout;

import edu.wpi.cs.wpisuitetng.modules.calendar.CalendarException;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.CombinedCommitmentList;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.CombinedEventList;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.Event;

@SuppressWarnings("serial")
public class DayDayPane extends JPanel {
	GregorianCalendar acal;
	List<Event> eventlist = new ArrayList<Event>();
	List<Commitment> commlist = new ArrayList<Commitment>();
	List<CalendarObjectPanel> sortedobjects = new ArrayList<CalendarObjectPanel>();
	List<List<CalendarObjectPanel>> displayobjects = new ArrayList<List<CalendarObjectPanel>>();
	JSeparator[] halfhourmarks= new JSeparator[49];
	
	//JPanel linespane = new JPanel();
	//JPanel objectspane = new JPanel();
	
	public DayDayPane(GregorianCalendar acal){
		super();
		
		this.acal = (GregorianCalendar)acal.clone();
		this.setMinimumSize(new Dimension(50, 800));
		this.setPreferredSize(new Dimension(50, 800));
		
		SpringLayout layout = new SpringLayout();
		this.setLayout(layout);
		//objectspane.setMinimumSize(new Dimension(50, 800));
		//objectspane.setPreferredSize(new Dimension(50, 800));
		//objectspane.setBackground(new Color(0,0,0,0));
		//objectspane.setOpaque(false);
		///this.add(objectspane);
		
		//linespane.setLayout(new GridLayout(48,1));
		this.makeLines();
		//this.add(linespane);
		
		this.addComponentListener(new ComponentAdapter(){
			public void componentResized(ComponentEvent e) {
				System.out.println(e.getComponent().getSize().getWidth());
				//objectspane.setSize(getSize());
				//linespane.setSize(getSize());
				setLines();
				for(CalendarObjectPanel obj : sortedobjects){
					obj.refreshSize();
				}
				setPos();
				for(Component comp : getComponents()){
					System.out.println(comp.getBounds());
				}
				//objectspane.revalidate();
				//objectspane.repaint();
				revalidate();
				repaint();
			}
		});
	}
	
	protected void merge(){
		sortedobjects = new ArrayList<CalendarObjectPanel>();
		
		if(commlist.isEmpty() && !eventlist.isEmpty()){
			for(Event event : eventlist){
				sortedobjects.add(new CalendarObjectPanel(this, this.acal, event));
			}
		}
		//if we only have commitments
		else if(!commlist.isEmpty() && eventlist.isEmpty()){
			for(Commitment comm : commlist){
				sortedobjects.add(new CalendarObjectPanel(this, this.acal, comm));
			}
		}
		//if we have both
		else if(commlist != null && eventlist != null){
			int eindex = 0;
			int cindex = 0;
			GregorianCalendar ccal = new GregorianCalendar();
			GregorianCalendar ecal = new GregorianCalendar();
			
			while(true){
				if(cindex == commlist.size() && eindex == eventlist.size()){
					break;
				}
				
				if(cindex == commlist.size()){
					ccal.add(Calendar.DATE, 1);
				}
				else{
					ccal.setTime(commlist.get(cindex).getDueDate().getTime());
				}
				
				if(eindex == eventlist.size()){
					ecal.add(Calendar.DATE, 1);
				}
				else{
					ecal.setTime(eventlist.get(eindex).getStartTime().getTime());
				}
				
				if(ccal.before(ecal)){
					sortedobjects.add(new CalendarObjectPanel(this, this.acal, commlist.get(cindex)));
					cindex++;
				}
				else{
					sortedobjects.add(new CalendarObjectPanel(this, this.acal, eventlist.get(eindex)));
					eindex++;
				}
			}
		}
	}

	protected void makeLines(){
		//half hour marks code
		SpringLayout layout = (SpringLayout)this.getLayout();
		for(int i = 0; i < 49; i++){
			halfhourmarks[i] = new JSeparator();
			
			Color col;
			if(i%2==0){
				col = Color.BLACK;
				layout.putConstraint(SpringLayout.WEST, halfhourmarks[i], 5, SpringLayout.WEST, this);
				layout.putConstraint(SpringLayout.EAST, halfhourmarks[i], 5, SpringLayout.EAST, this);
			}
			else{
				col = Color.GRAY;
				layout.putConstraint(SpringLayout.WEST, halfhourmarks[i], 15, SpringLayout.WEST, this);
				layout.putConstraint(SpringLayout.EAST, halfhourmarks[i], 15, SpringLayout.EAST, this);
			}
			
			halfhourmarks[i].setBackground(col);
			halfhourmarks[i].setForeground(col);
			
			if(i < 49){
				this.add(halfhourmarks[i]);
			}
		}
		layout.putConstraint(SpringLayout.NORTH, halfhourmarks[0], 0, SpringLayout.NORTH, this);
		this.setLines();
	}
	
	protected void setLines(){
		SpringLayout layout = (SpringLayout)this.getLayout();
		for(int i = 1; i < 49; i++){
			layout.putConstraint(SpringLayout.VERTICAL_CENTER, halfhourmarks[i], 
								(int)((this.getSize().getHeight())*i/48.0),
								SpringLayout.NORTH, this);
		}
	}

	protected void updatepane(){
		displayobjects = new ArrayList<List<CalendarObjectPanel>>();
		for(CalendarObjectPanel cop : sortedobjects){
			int column_index = 0;
			boolean isset = false;
			while(!isset){
				List<CalendarObjectPanel> column = null;
				try{
					column = displayobjects.get(column_index);
				}
				catch(IndexOutOfBoundsException e){
					 column = new ArrayList<CalendarObjectPanel>();
					 displayobjects.add(column);
				}
				
				int row_index = 0;
				while(!isset){
					CalendarObjectPanel obj = null;
					try{
						obj = column.get(row_index);
					}
					catch(IndexOutOfBoundsException e){
						column.add(cop);
						isset = true;
						break;
					}
					
					if(obj.doesConflict(obj)){
						break;
					}
					row_index++;
				}
				
				column_index++;
			}
		}
		
		for(List<CalendarObjectPanel> column : displayobjects){
			for(CalendarObjectPanel obj : column){
				obj.setWidth(this.getWidth(displayobjects.indexOf(column), obj));
			}
		}
		
		this.removeAll();
		this.makeLines();
		SpringLayout layout = (SpringLayout)this.getLayout();
		//objectspane.setLayout(layout);
		
		for(List<CalendarObjectPanel> column : displayobjects){
			for(CalendarObjectPanel obj : column){
				layout.putConstraint(SpringLayout.WEST, obj, 0, SpringLayout.WEST, this);
				if(displayobjects.indexOf(column) != 0){
					for(CalendarObjectPanel compobj : displayobjects.get(displayobjects.indexOf(column) - 1)){
						if(compobj.doesConflict(obj)){
							layout.putConstraint(SpringLayout.WEST, obj, 0, SpringLayout.EAST, compobj);
						}
					}
				}
			}
		}
		
		for(CalendarObjectPanel obj : sortedobjects){
			obj.refreshSize();
		}
		this.setPos();
		
		for(CalendarObjectPanel obj : sortedobjects){
			this.add(obj);
		}
	}
	
	/*protected void setHeights(){
		for(CalendarObjectPanel obj : sortedobjects){
			int width = (int)(obj.getPreferredSize().getWidth());
			double sup_height = this.getPreferredSize().getHeight();
			System.out.println("Height before : " + obj.getPreferredSize());
			obj.setPreferredSize(new Dimension(width, (int)(sup_height*obj.getSizeIndex()/48.0)));
			obj.setSize(new Dimension(width, (int)(sup_height*obj.getSizeIndex()/48.0)));
			System.out.println("Height after : " + obj.getPreferredSize());
		}
	}
	
	protected void setWidths(){
		for(CalendarObjectPanel obj : sortedobjects){
			int height = (int)obj.getPreferredSize().getHeight();
			double sup_width = this.getPreferredSize().getWidth();
			System.out.println("Width before : " + obj.getPreferredSize());
			obj.setPreferredSize(new Dimension((int)(sup_width/obj.getWidth()), height));
			obj.setSize(new Dimension((int)(sup_width/obj.getWidth()), height));
			System.out.println("Width after : " + obj.getPreferredSize());
		}
	}*/
	
	protected void setPos(){
		SpringLayout layout = (SpringLayout)this.getLayout();
		for(CalendarObjectPanel obj : sortedobjects){
			//layout.putConstraint(SpringLayout.NORTH, obj, 0, SpringLayout.NORTH, halfhourmarks[obj.getStartIndex()]);
			//layout.putConstraint(SpringLayout.SOUTH, obj, 0, SpringLayout.SOUTH, halfhourmarks[obj.getEndIndex()]);
			double sup_height = this.getPreferredSize().getHeight();
			System.out.println("Down by " + halfhourmarks[obj.getStartIndex()].getBounds() + " : " + halfhourmarks[obj.getStartIndex()].getParent().getBounds());
			layout.putConstraint(SpringLayout.NORTH, obj, (int)halfhourmarks[obj.getStartIndex()].getBounds().getY(), SpringLayout.NORTH, this);
		}
	}
	
	protected int getWidth(int column_index, CalendarObjectPanel cop){
		int width = 1;
		int max = 0;
		//Calculate width to the right
		try{
			for(CalendarObjectPanel obj :displayobjects.get(column_index-1)){
				if(obj.doesConflict(cop)){
					int temp = getWidth(column_index-1, obj, false);
					max = max < temp ? temp : max;
				}
			}
		}catch(IndexOutOfBoundsException e){}
		width += max;
		
		max = 0;
		//Calculate width to the left
		try{
			for(CalendarObjectPanel obj :displayobjects.get(column_index+1)){
				if(obj.doesConflict(cop)){
					int temp = getWidth(column_index+1, obj, true);
					max = max < temp ? temp : max;
				}
			}
		}catch(IndexOutOfBoundsException e){}
		width += max;
		return width;
	}
	
	protected int getWidth(int column_index, CalendarObjectPanel cop, boolean leftonly){
		int width = 1;
		int max = 0;
		if(!leftonly){
			//Calculate width to the right
			try{
				for(CalendarObjectPanel obj :displayobjects.get(column_index-1)){
					if(obj.doesConflict(cop)){
						int temp = getWidth(column_index-1, obj, leftonly);
						max = max < temp ? temp : max;
					}
				}
			}catch(IndexOutOfBoundsException e){}
			width += max;
		}
		else{
			max = 0;
			//Calculate width to the left
			try{
				for(CalendarObjectPanel obj :displayobjects.get(column_index+1)){
					if(obj.doesConflict(cop)){
						int temp = getWidth(column_index+1, obj, leftonly);
						max = max < temp ? temp : max;
					}
				}
			}catch(IndexOutOfBoundsException e){}
			width += max;
		}
		
		return width;
	}

	public void displayCommitments(List<Commitment> commList) {
		// if we are supposed to display commitments
		if (commList != null) {
			this.commlist = commList;
		} else {
			this.commlist = new ArrayList<Commitment>();
		}
		merge();
		updatepane();
	}
	
	public void displayEvents(List<Event> eventList) {
		// if we are supposed to display events
		if (eventList != null) {
			this.eventlist = eventList;
		} else {
			this.eventlist = new ArrayList<Event>();
		}
		merge();
		updatepane();
	}
}
