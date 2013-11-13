/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Andrew Hurle
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.calendar.view.maintab.secondarytabs;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import edu.wpi.cs.wpisuitetng.modules.calendar.monthtab.CalendarMonth;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.GUIEventController;
import net.miginfocom.swing.MigLayout;

/**
 * This panel will show notifications, search widgets.
 */
@SuppressWarnings("serial")
public class MyCalendarTab extends JSplitPane {
	//private IterationCalendar_incomplete calendarView;
	
	private JButton nextYear;
	private JButton prevYear;
	private JButton nextMonth;
	private JButton prevMonth;
	private JButton today;
	
	private CalendarMonth calMonth;
	
	private JLabel selectionLabel;
	public MyCalendarTab() {
		//MyCalendarTab > scrollPane > contentPanel > buttonPanel
		JScrollPane scrollPane = new JScrollPane();
		
		JPanel contentPanel = new JPanel();
		
		contentPanel.setLayout(new MigLayout());
		
		
		JPanel buttonPanel = new JPanel();
		
		nextYear = new JButton(">>");
		nextYear.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		nextMonth = new JButton(">");
		nextMonth.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		today = new JButton ("Today");
		today.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		prevMonth = new JButton("<");
		prevMonth.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		prevYear = new JButton("<<");
		prevYear.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		//setupButtonListeners();
		
		buttonPanel.add(prevYear);
		buttonPanel.add(prevMonth);
		buttonPanel.add(today);
		buttonPanel.add(nextMonth);
		buttonPanel.add(nextYear);

		
		contentPanel.add(buttonPanel, "alignx center, dock north");
 		
		JPanel calendarPanel = new JPanel(new BorderLayout());
		//calendarView = new IterationCalendar_incomplete(this);
		//calendarPanel.add(calendarView, BorderLayout.CENTER);
				
		//calendarPanel.add(calendarView, BorderLayout.CENTER);	
		
		//JPanel selectionPanel = new JPanel(new MigLayout("height 100:100:100, width 150:150:150","",""));
		//selectionPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		//selectionLabel = new JLabel("No Iteration Selected.");
		//selectionPanel.add(selectionLabel);
		
		//JPanel keyPanel = new JPanel(new MigLayout("height 100:100:100, width 150:150:150","", ""));
		//keyPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		//JLabel keyLabel = new JLabel("Key:");
				
		//JPanel selectedIteration = new JPanel ();
		//selectedIteration.add(new JLabel("Selected Iteration"));
		//selectedIteration.setBackground(IterationCalendar.SELECTION);
		 		
		//JPanel allIterations = new JPanel();
		//allIterations.add(new JLabel("All Iterations"));
		//allIterations.setBackground(IterationCalendar.START_END_DAY);
		
		//keyPanel.add(keyLabel, "alignx center, push, span, wrap");
		//keyPanel.add(selectedIteration, "alignx left, push, span, wrap");
		//keyPanel.add(allIterations, "alignx left, push, span, wrap");
		//JPanel keyWrapper = new JPanel(new MigLayout());	
		
		//keyWrapper.add(keyPanel, "wrap");
		//keyWrapper.add(selectionPanel);
		
		//calendarPanel.add(keyWrapper, BorderLayout.EAST);
		
		
		
		//contentPanel.add(calendarPanel, "alignx center, push, span");
		
		
		scrollPane.setViewportView(contentPanel);
		this.setRightComponent(scrollPane);
		this.setDividerLocation(180);
		//GUIEventController.getInstance().setIterationOverview(this);
	}
}
