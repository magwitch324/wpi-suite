/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: CS Anonymous
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.calendar.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JViewport;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import org.jdesktop.swingx.border.MatteBorderExt;

import edu.wpi.cs.wpisuitetng.modules.calendar.CalendarStandard;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.CommitmentList;

@SuppressWarnings("serial")
public class WeekPane extends JPanel implements ICalPane {
	JPanel mainPanel = new JPanel();
	
	JScrollPane scrollPane = new JScrollPane(mainPanel,
			ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	GregorianCalendar mydate;
	private JComponent days;
	private SpringLayout layout;
	
	AbCalendar calendarused;


	/**
	 * Create the panel.
	 */

	public WeekPane(GregorianCalendar datecalendar) {
		mydate = new GregorianCalendar();
		mydate.setTime(datecalendar.getTime());
		mydate.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);

//	   	while(mydate.get(Calendar.DAY_OF_WEEK) != mydate.getFirstDayOfWeek() ){
//	   		mydate.add(Calendar.DATE, -1);
//	   	}
	   	
	   	setLayout(new GridLayout(1,1));
	   	scrollPane.getVerticalScrollBar().setBackground(CalendarStandard.CalendarYellow);
		scrollPane.getVerticalScrollBar().setUnitIncrement(20);
		scrollPane.setMinimumSize(new Dimension(500, 300));
		scrollPane.setBackground(CalendarStandard.CalendarRed);
		
		// Set color within the scrollbar
		
		
		

		// Sets the UPPER LEFT corner box
		JPanel cornerBoxUL = new JPanel();
		cornerBoxUL.setBackground(CalendarStandard.CalendarRed);
		cornerBoxUL.setBorder(new MatteBorderExt(0, 0, 2, 0, Color.GRAY));
		scrollPane.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER,
				cornerBoxUL);
		
		// Sets the UPPER RIGHT corner box
		JPanel cornerBoxUR = new JPanel();
		cornerBoxUR.setBackground(CalendarStandard.CalendarRed);
		cornerBoxUR.setBorder(new MatteBorderExt(0, 0, 2, 0, Color.GRAY));
		scrollPane.setCorner(ScrollPaneConstants.UPPER_RIGHT_CORNER,
		cornerBoxUR);

		this.add(scrollPane);
		
		mainPanel.setPreferredSize(new Dimension(30, 2000));
		
		layout = new SpringLayout();
		mainPanel.setLayout(layout);
		
		scrollPane.setColumnHeader(getColumnheader());
		scrollPane.setRowHeaderView(getTimesBar(mainPanel.getPreferredSize().getHeight()));  
		
	   	
		scrollPane.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				GUIEventController.getInstance().setScrollBarValue(((JScrollPane)e.getSource()).getVerticalScrollBar().getValue());
			}
			
		});
		
	   	
	   	refresh();
	}

	/**
	 * Set up header section for the week pane
	 * @return
	 */
    protected JViewport getColumnheader(){
    	final JViewport port = new JViewport();
    	final JLabel[] labels = new JLabel[7];
    	final JPanel apane = new JPanel();
    	port.setView(apane);
    	apane.setBackground(CalendarStandard.CalendarRed);
	    apane.setBorder(new EmptyBorder(5,0,10,0));
    	apane.setLayout(new GridLayout(1,7));
    	
    	final String[][] weekdays = {{"Sunday, ", "Monday, ", "Tuesday, ",
    						"Wednesday, ", "Thursday, ", "Friday, ", "Saturday, " },
    						{"Sun, ", "Mon, ", "Tue, ","Wed, ", "Thu, ", "Fri, ", "Sat, " },
    						{"Sun", "Mon", "Tue","Wed", "Thu", "Fri", "Sat" }};
    	
    	Calendar acal = (Calendar)mydate.clone();
		for(int i=0; i < 7; i++) {
			weekdays[0][i] += acal.get(Calendar.DATE);
			weekdays[1][i] += acal.get(Calendar.DATE);
			acal.add(Calendar.DATE, 1);
			labels[i] = new JLabel(weekdays[0][i], SwingConstants.CENTER);
			labels[i].setFont(CalendarStandard.CalendarFontBold);
			labels[i].setForeground(Color.WHITE);
			apane.add(labels[i]);
		}
    	

    	port.addComponentListener(new ComponentAdapter(){
    		public void componentResized(ComponentEvent e){
    			int touse = 0;
    			do{
				    for(int i = 0; i<7; i++){
				    	labels[i].setText(weekdays[touse][i]);
				    }
				    touse++;
    			}while(apane.getPreferredSize().width > port.getSize().getWidth());
    			
    		}
    	});

	    
	    apane.setBorder(new MatteBorder(0, 0, 2, 0, Color.GRAY));
	    //apane.setPreferredSize(new Dimension(10, 40));
	    port.setPreferredSize(new Dimension(10, 40));
	    
    	return port;
    }

    
    /** Creates JPanel of commitment boxes for overlaying on detailed day
     * @param personalCommList
     * @param teamCommList
     * @return
     */
    protected JComponent getDays(List<Commitment> commList){
    	JPanel apane = new JPanel();
	    apane.setLayout(new GridLayout(1,7));
    	String[] weekdays = {"Sunday, ", "Monday, ", "Tuesday, ",
				"Wednesday, ", "Thursday, ", "Friday, ", "Saturday, " };
    	int initial = mydate.get(Calendar.DATE);
    	
       	for(int i=0; i < 7; i++) {
    		weekdays[i] += (initial + i);
    	}
       	
       	//Initialize variables for placing each commitment on correct day
       	List<List<Commitment>> comms = new ArrayList<List<Commitment>>();
       	for(int i = 0; i<7; i++)
       	{
       		comms.add(new ArrayList<Commitment>());
       	}
       	GregorianCalendar cal = new GregorianCalendar();
       	
       	//Place each commitment on its right day
       	for(Commitment comm: commList)
       	{
       		cal.setTime(comm.getDueDate().getTime());
       		comms.get(cal.get(Calendar.DAY_OF_WEEK) - 1).add(comm); //day of week is 1 - 7

       	}
       	
    	cal.setTime(mydate.getTime());
	    for(int i = 0; i<7; i++){
	    	DetailedDay aday = new DetailedDay(cal,new CommitDetailedPane(cal, comms.get(i)));
	    	aday.setBackground(CalendarStandard.CalendarYellow);
	    	System.out.println(cal.getTime().toString());
	    	aday.addMouseListener(new AMouseEvent(cal));
	    	apane.add( aday );
	    	cal.add(Calendar.DATE, 1);
	    }
    	return apane;
    }
    
	protected JComponent getTimesBar(double height){
		JPanel apane = new JPanel();
		apane.setBackground(CalendarStandard.CalendarRed);
		apane.setBorder(new MatteBorder(0, 0, 0, 2, Color.GRAY));
		apane.setBorder(new EmptyBorder(0, 5, 0, 5));
		SpringLayout layout = new SpringLayout();
		apane.setLayout(layout);

		String[] times = { "12:00", "1 AM", "2:00", "3:00", "4:00", "5:00",
				"6:00", "7:00", "8:00", "9:00", "10:00", "11:00", "12 PM",
				"1:00", "2:00", "3:00", "4:00", "5:00", "6:00", "7:00", "8:00",
				"9:00", "10:00", "11:00" };

		int max = 0;

		for (int i = 1; i < 24; i++) {
			JLabel alab = new JLabel("<html><font color='white'><b>" + times[i]
					+ "</b></font></html>");
			alab.setFont(CalendarStandard.CalendarFontBold);
			layout.putConstraint(SpringLayout.VERTICAL_CENTER, alab,
					(int) (height * i / 24.0), SpringLayout.NORTH, apane);
			layout.putConstraint(SpringLayout.EAST, alab, 0, SpringLayout.EAST,
					apane);
			max = alab.getPreferredSize().width > max ? alab.getPreferredSize().width
					: max;
			apane.add(alab);
		}

		apane.setPreferredSize(new Dimension(max + 5, (int) height));
		apane.setSize(new Dimension(max + 5, (int) height));

		return apane;
	}

	@Override
	public JPanel getPane() {
		return this;
	}
	
	/** Displays commitments on DetailedDays
     * @param commList List of commitments to be displayed
	 * @param dayTeamCommList 
     */
    public void displayCommitments(List<Commitment> commList) {
		if(commList != null){
    	days = getDays(commList);
		}
    	refresh();
	}
	
	public void refresh() {
		mainPanel.removeAll();
		if (days == null)
			days = getDays(new ArrayList<Commitment>());
		setLayout(new GridLayout(1,1));
 		layout.putConstraint(SpringLayout.WEST, days, 0, SpringLayout.WEST, mainPanel);
 		layout.putConstraint(SpringLayout.NORTH, days, 0, SpringLayout.NORTH, mainPanel);
 		layout.putConstraint(SpringLayout.SOUTH, days, 0, SpringLayout.SOUTH, mainPanel);
 		layout.putConstraint(SpringLayout.EAST, days, 0, SpringLayout.EAST, mainPanel);
 		mainPanel.add(days);
    	mainPanel.revalidate();
    	mainPanel.repaint();
    	scrollPane.revalidate();
		scrollPane.repaint();
   	 	scrollPane.getVerticalScrollBar().setValue(GUIEventController.getInstance().getScrollBarValue());

	}


	protected class AMouseEvent extends MouseAdapter{
		GregorianCalendar adate = new GregorianCalendar();
		
		public AMouseEvent(GregorianCalendar adate){
			this.adate.setTime(adate.getTime());

		}

	    public void mouseClicked(MouseEvent e) {
	    	if(e.getClickCount() > 1){
	    		//save scroll bar location
	    		GUIEventController.getInstance().setScrollBarValue(scrollPane.getVerticalScrollBar().getValue());
	    		//switch to day view
	    		GUIEventController.getInstance().switchView(adate, AbCalendar.types.DAY);
	    	}
	    }
	}

	public void updateScrollPosition(int value){
		scrollPane.getVerticalScrollBar().setValue(value);
	}
	
}
