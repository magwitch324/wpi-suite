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
import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import edu.wpi.cs.wpisuitetng.modules.calendar.CalendarStandard;
import edu.wpi.cs.wpisuitetng.modules.calendar.controller.UpdatePropsController;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.CalendarData;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.CalendarProps;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.CommitmentList;

/*
 * Sources:
 * Icons were developed using images obtained at: 
 * [1] http://changepointnea.com/~changepo/cms-assets/images/161361.calendar-icon.png
 * [2] http://pixabay.com/p-37197/?no_redirect
 */


public abstract class AbCalendar extends JPanel {
	protected boolean initialized;
	protected CalendarData calData;
	protected CalendarProps calProps;

	protected enum types {
		DAY(0),
		WEEK(1),
		MONTH(2),
		YEAR(3);

		private int currentType;

		private types(int currentType) {
			this.currentType = currentType;
		}

		public int getCurrentType() {
			return currentType;
		}
	}

	protected types currenttype = types.DAY;
	protected GregorianCalendar mycal;

	protected JPanel viewpanel = new JPanel(); 
	protected JToggleButton[] viewbtns = new JToggleButton[4];
	protected JCheckBox showcom;

	protected int[] viewsizeval = {Calendar.DATE, Calendar.WEEK_OF_YEAR, Calendar.MONTH, Calendar.YEAR};
	protected CalendarView calView;
	protected CommitmentList commitments;

	public AbCalendar(){
		
		super();
		initialized = false;
		mycal = new GregorianCalendar();

		super.setBackground(Color.WHITE);

		// Draws GUI
		drawThis();
		
	}


	abstract void drawThis();

	protected JComponent getViewButtonPanel(){
		JPanel apane = new JPanel();
		apane.setBackground(Color.WHITE);
		apane.setLayout(new GridLayout(1,4,15,0));

		viewbtns[0] = new JToggleButton();

		try {
			Image img = ImageIO.read(getClass().getResource("Day_Icon.png"));
			this.viewbtns[0].setIcon(new ImageIcon(img));
			this.viewbtns[0].setBorder(BorderFactory.createEmptyBorder());
			viewbtns[0].setContentAreaFilled(false);
		} catch (IOException ex) {}
		catch(IllegalArgumentException ex){
			this.viewbtns[0].setIcon(new ImageIcon());
			this.viewbtns[0].setText("Day");
		} 

		viewbtns[0].setCursor(new Cursor(Cursor.HAND_CURSOR)); // To change cursor as it moves over this icon
		viewbtns[0].setBackground(Color.WHITE);
		viewbtns[0].addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e)
			{
				//Execute when button is pressed
				switchview(types.DAY);
				viewbtns[0].setBorder(BorderFactory.createDashedBorder(CalendarStandard.CalendarRed, 2, 2, 1, true));
				viewbtns[1].setBorder(BorderFactory.createEmptyBorder());
				viewbtns[2].setBorder(BorderFactory.createEmptyBorder());
				viewbtns[3].setBorder(BorderFactory.createEmptyBorder());
			}
		});

		viewbtns[0].setBorder(BorderFactory.createDashedBorder(CalendarStandard.CalendarRed, 2, 2, 1, true));
		apane.add(viewbtns[0]);		
		

		viewbtns[1] = new JToggleButton();

		try {
			Image img = ImageIO.read(getClass().getResource("Week_Icon.png"));
			this.viewbtns[1].setIcon(new ImageIcon(img));
			this.viewbtns[1].setBorder(BorderFactory.createEmptyBorder());
			viewbtns[1].setContentAreaFilled(false);
		} catch (IOException ex) {}
		catch(IllegalArgumentException ex){
			this.viewbtns[1].setIcon(new ImageIcon());
			this.viewbtns[1].setText("Week");
		}

		viewbtns[1].setCursor(new Cursor(Cursor.HAND_CURSOR)); // To change cursor as it moves over this icon
		viewbtns[1].setBackground(Color.WHITE);
		viewbtns[1].addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e)
			{
				//Execute when button is pressed
				switchview(types.WEEK);
				viewbtns[1].setBorder(BorderFactory.createDashedBorder(CalendarStandard.CalendarRed, 2, 2, 1, true));
				viewbtns[0].setBorder(BorderFactory.createEmptyBorder());
				viewbtns[2].setBorder(BorderFactory.createEmptyBorder());
				viewbtns[3].setBorder(BorderFactory.createEmptyBorder());
			}
		});
		
		apane.add(viewbtns[1]);
		

		viewbtns[2] = new JToggleButton();

		try {
			Image img = ImageIO.read(getClass().getResource("Month_Icon.png"));
			this.viewbtns[2].setIcon(new ImageIcon(img));	
			this.viewbtns[2].setBorder(BorderFactory.createEmptyBorder());
			viewbtns[2].setContentAreaFilled(false);
		} catch (IOException ex) {}
		catch(IllegalArgumentException ex){
			this.viewbtns[2].setIcon(new ImageIcon());
			this.viewbtns[2].setText("Month");
		}

		viewbtns[2].setCursor(new Cursor(Cursor.HAND_CURSOR)); // To change cursor as it moves over this icon
		viewbtns[2].setBackground(Color.WHITE);
		viewbtns[2].addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e)
			{
				//Execute when button is pressed
				switchview(types.MONTH);
				viewbtns[2].setBorder(BorderFactory.createDashedBorder(CalendarStandard.CalendarRed, 2, 2, 1, true));
				viewbtns[0].setBorder(BorderFactory.createEmptyBorder());
				viewbtns[1].setBorder(BorderFactory.createEmptyBorder());
				viewbtns[3].setBorder(BorderFactory.createEmptyBorder());
			}
		});
		
		apane.add(viewbtns[2]);
		

		viewbtns[3] = new JToggleButton();

		try {
			Image img = ImageIO.read(getClass().getResource("Year_Icon.png"));
			this.viewbtns[3].setIcon(new ImageIcon(img));
			this.viewbtns[3].setBorder(BorderFactory.createEmptyBorder());
			viewbtns[3].setContentAreaFilled(false);
		} catch (IOException ex) {}
		catch(IllegalArgumentException ex){
			this.viewbtns[3].setIcon(new ImageIcon());
			this.viewbtns[3].setText("Year");
		}

		viewbtns[3].setCursor(new Cursor(Cursor.HAND_CURSOR)); // To change cursor as it moves over this icon
		viewbtns[3].setBackground(Color.WHITE);
		viewbtns[3].addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e)
			{
				//Execute when button is pressed
				switchview(types.YEAR);
				viewbtns[3].setBorder(BorderFactory.createDashedBorder(CalendarStandard.CalendarRed, 2, 2, 1, true));
				viewbtns[0].setBorder(BorderFactory.createEmptyBorder());
				viewbtns[1].setBorder(BorderFactory.createEmptyBorder());
				viewbtns[2].setBorder(BorderFactory.createEmptyBorder());
			}
		});
		
		apane.add(viewbtns[3]);
		
		return apane;
	}

	protected void stepCalendar(int step){
		if(step == 0){
			mycal = new GregorianCalendar();
		}
		else{
			mycal.add(viewsizeval[currenttype.getCurrentType()], step);
		}
		setView();
	}

	protected void switchview(types changeto){
		if(currenttype != changeto){

			for(int i = 0; i < 4; i++){
				viewbtns[i].setSelected(false);
			}

			currenttype = changeto;
			setView();
		}

		viewbtns[changeto.getCurrentType()].setSelected(true);
	}

	protected JComponent getDatePanel(){
		JPanel apane = new JPanel();
		apane.setBackground(Color.WHITE);

		JButton backwardbutton = new JButton("<<");
		backwardbutton.setBackground(Color.WHITE);
		backwardbutton.setFont(CalendarStandard.CalendarFont);
		backwardbutton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // To change cursor as it moves over this button
		backwardbutton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e)
			{
				//Execute when button is pressed
				stepCalendar(-1);
			}
		});

		JButton todaybutton = new JButton("Today");
		todaybutton.setBackground(Color.WHITE);
		todaybutton.setFont(CalendarStandard.CalendarFont);
		todaybutton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // To change cursor as it moves over this button
		todaybutton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e)
			{
				//Execute when button is pressed
				stepCalendar(0);
			}
		});

		JButton forwardbutton = new JButton(">>");
		forwardbutton.setBackground(Color.WHITE);
		forwardbutton.setFont(CalendarStandard.CalendarFont);
		forwardbutton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // To change cursor as it moves over this button
		forwardbutton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e)
			{
				//Execute when button is pressed
				stepCalendar(1);
			}
		});

		apane.add(backwardbutton);
		apane.add(todaybutton);
		apane.add(forwardbutton);
		return apane;
	}

	
	// Adds create new commitment button
//	protected JComponent ButtonsPanelCreate(){
//		JPanel apane = new JPanel();
//		apane.setBackground(Color.WHITE);
//		
//		//apane.setLayout(new BoxLayout(apane, BoxLayout.X_AXIS));
//		//this.setPreferredWidth(1200);
//		
//		JButton createCommitButton= new JButton();
//		createCommitButton.setBackground(Color.WHITE);
//		createCommitButton.setFont(CalendarStandard.CalendarFont);
//		createCommitButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // To change cursor as it moves over this button
//		//createCommitButton.setPreferredSize(new Dimension(400, 800));
//		//createCommitButton.setHorizontalAlignment(SwingConstants.CENTER);
//		
//		try {
//			Image img = ImageIO.read(getClass().getResource("AddCommitment_Icon.png"));
//		    createCommitButton.setIcon(new ImageIcon(img));
//		    createCommitButton.setBorder(BorderFactory.createEmptyBorder());
//		    createCommitButton.setContentAreaFilled(false);
//		    
//		} catch (IOException ex) {}
//		catch(IllegalArgumentException ex){
//			createCommitButton.setIcon(new ImageIcon());
//			createCommitButton.setText("Commitment");
//		}
//		
//		// the action listener for the Create Requirement Button
//		createCommitButton.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				// bring up a create requirement pane if not in Multiple Requirement Editing Mode
//				//if (!ViewEventController.getInstance().getOverviewTable().getEditFlag()) {
//					GUIEventController.getInstance().createCommitment();
//			//	}
//			}
//		});		
//		
//		//createCommitButton.setBorder(new EmptyBorder(0, 0, 0, 15));
//		//contentPanel.setOpaque(false);
//		
//		apane.add(createCommitButton);
//		return apane;
//	}
	
	public JComponent getComponent() {
		return this; 
	}

	protected void setView(){
		viewpanel.removeAll();
		//System.out.println("Cal COUNT start: " + viewpanel.getComponentCount());
		viewpanel.setLayout(new GridLayout(1,1));
		//TODO do views
		switch(currenttype.getCurrentType()){
		case(0):
			calView = (new DayView(mycal));
		displayCalData();
		viewpanel.add(calView);
		break;
		case(1):
			calView = (new WeekView(mycal));
		displayCalData();
		viewpanel.add(calView);
		break;
		case(2):
			calView = (new MonthView(mycal));
		displayCalData();
		viewpanel.add(calView);			
		break;
		case(3):
			//TODO YearView
			break;
		default:
			//TODO error
			break;
		}

		viewpanel.revalidate();
		viewpanel.repaint();
		//System.out.println("Cal COUNT end: " + viewpanel.getComponentCount());

	}

	public void setCal(GregorianCalendar changeto){
		mycal.setTime(changeto.getTime());
		setView();
	}

	public void setCalsetView(GregorianCalendar acal, TeamCalendar.types switchtype)
	{
		mycal.setTime(acal.getTime());
		switchview(switchtype);
		setView();
	}

	public boolean getShowCommitments(){
		return showcom.isSelected();
	}

	public CalendarData getCalData(){
		return this.calData;
	}


	abstract void updateCalData();
	abstract protected void displayCalData();
	abstract public boolean getShowTeamData();
	abstract void applyCalProps();

	
	/**
	 * This function is called on Janeway shutdown to save the calendar props.
	 */
	public void saveProps(){
		UpdatePropsController.getInstance().updateCalendarProps(calProps);
	}

}
