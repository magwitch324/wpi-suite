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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;

import org.jdesktop.swingx.border.MatteBorderExt;

import edu.wpi.cs.wpisuitetng.modules.calendar.CalendarStandard;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.Commitment;

public class DayPane extends JPanel implements ICalPane {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JPanel mainPanel = new JPanel();
	GregorianCalendar day;
	private DetailedDay daypane;
	private SpringLayout layout;
	private JScrollPane scrollPane;
	private JPanel header = new JPanel();
	
	/**
	 * Create the panel.
	 */
	public DayPane(GregorianCalendar datecalendar) {
		
		day = new GregorianCalendar();
		day.setTime(datecalendar.getTime());
		
		setLayout(new GridLayout(1,1));


		// HOURS
		scrollPane = new JScrollPane(mainPanel, 
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, 
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.getVerticalScrollBar().setBackground(CalendarStandard.CalendarYellow);
		scrollPane.getVerticalScrollBar().setUnitIncrement(20);
		scrollPane.setMinimumSize(new Dimension(300, 300));
		scrollPane.setBackground(CalendarStandard.CalendarRed);
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
		add(scrollPane);


		// Create the header panel
		header.setLayout(new GridLayout(1, 2));
		header.setBackground(CalendarStandard.CalendarRed);
		header.setBorder(new MatteBorder(0, 0, 2, 0, Color.GRAY));
		header.setPreferredSize(new Dimension(10, 40));

		// Create and set the label "Events"
		JLabel eventlabel = new JLabel("<html><font color='white'><b>"
				+ "Events" + "</b></font></html>", SwingConstants.CENTER);
		eventlabel.setFont(CalendarStandard.CalendarFont.deriveFont(14));
		header.add(eventlabel);

		// add apane to the header of the scrollpane
		scrollPane.setColumnHeaderView(header);
		
		
		layout = new SpringLayout();
		mainPanel.setLayout(layout);
		
		mainPanel.setPreferredSize(new Dimension(30, 2000));

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


	public void refresh() {
		// TODO Auto-generated method stub
		
		mainPanel.removeAll();
		
		setLayout(new GridLayout(1,1));

		if (daypane == null)
			daypane = new DetailedDay(day, new CommitDetailedPane(day, new ArrayList<Commitment>()));
		daypane.setBackground(CalendarStandard.CalendarYellow);
		layout.putConstraint(SpringLayout.WEST, daypane, 0, SpringLayout.WEST, mainPanel);
		layout.putConstraint(SpringLayout.NORTH, daypane, 0, SpringLayout.NORTH, mainPanel);
		layout.putConstraint(SpringLayout.SOUTH, daypane, 0, SpringLayout.SOUTH, mainPanel);
		layout.putConstraint(SpringLayout.EAST, daypane, 0, SpringLayout.EAST, mainPanel);
		mainPanel.add(daypane);


		//scrollPane.setColumnHeaderView(labelPane);

		mainPanel.revalidate();
		mainPanel.repaint();
		scrollPane.revalidate();
		scrollPane.repaint();
		scrollPane.getVerticalScrollBar().setValue(GUIEventController.getInstance().getScrollBarValue());

	}


	/** Displays commitments on DetailedDay
	 * @param commList List of commitments to be displayed
	 * @param dayTeamCommList 
	 */
	public void displayCommitments(List<Commitment> commList) {
		System.out.println("comms: " + commList);
		//if we are supposed to display commitments
		if(commList != null){
			
			// Create and set the label "Commitments" for when ShowCommitments
			// is checked
			if (header.getComponentCount() < 2){//Checks to make sure that the label doesn't already exist
				JLabel commitlabel = new JLabel("<html><font color='white'><b>"
						+ "Commitments" + "</b></font></html>",
						SwingConstants.CENTER);
				commitlabel.setFont(CalendarStandard.CalendarFont.deriveFont(14));
				header.add(commitlabel);
			}

			daypane = new DetailedDay(day, new CommitDetailedPane(day, commList));
		}
		else{
			daypane = new DetailedDay(day);
		}
		refresh();

	}

	protected JComponent getTimesBar(double height){
		JPanel apane = new JPanel();
		apane.setBackground(CalendarStandard.CalendarRed);
		apane.setBorder(new MatteBorder(0, 0, 0, 2, Color.GRAY));
		SpringLayout layout = new SpringLayout();
		apane.setLayout(layout);

		String[] times = {"12:00", "1 AM","2:00","3:00","4:00","5:00","6:00",
				"7:00","8:00","9:00","10:00","11:00","12 PM",
				"1:00","2:00","3:00","4:00","5:00","6:00",
				"7:00","8:00","9:00","10:00","11:00"};

		int max = 0;

		for(int i = 1; i < 24; i++){
			JLabel alab = new JLabel("<html><font color='white'><b>" + times[i]
					+ "</b></font></html>", SwingConstants.CENTER);
			alab.setFont(CalendarStandard.CalendarFontBold);
			layout.putConstraint(SpringLayout.VERTICAL_CENTER, alab,
					(int) (height * i / 24.0), SpringLayout.NORTH, apane);
			layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, alab, 0, SpringLayout.HORIZONTAL_CENTER,
					apane);
			max = alab.getPreferredSize().width > max ? alab.getPreferredSize().width
					: max;
			apane.add(alab);
		}

		apane.setPreferredSize(new Dimension(max+5, (int)height));
		apane.setSize(new Dimension(max+5, (int)height));

		return apane;
	}

	@Override
	public JPanel getPane() {
		// TODO Auto-generated method stub
		return this;
	}
	
	public void updateScrollPosition(int value){
		scrollPane.getVerticalScrollBar().setValue(value);
	}
}