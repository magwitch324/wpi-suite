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

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import net.miginfocom.swing.MigLayout;


/**
 * This panel will show notifications, search widgets.
 */
@SuppressWarnings("serial")
public class TeamCalendarTab extends JSplitPane {
	
	//private IterationCalendar calendarView;
	
	private JButton nextYear;
	private JButton prevYear;
	private JButton nextMonth;
	private JButton prevMonth;
	private JButton today;
	
	private JLabel selectionLabel;
	
	public TeamCalendarTab() {
		JScrollPane scrollPane = new JScrollPane();
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new MigLayout());
		
		JPanel buttonPanel = new JPanel();
		
		nextYear = new JButton(">>");
		nextYear.setBackground(Color.WHITE);
		nextYear.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		nextMonth = new JButton(">");
		nextMonth.setBackground(Color.WHITE);
		nextMonth.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		today = new JButton ("Today");
		today.setBackground(Color.WHITE);
		today.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		prevMonth = new JButton("<");
		prevMonth.setBackground(Color.WHITE);
		prevMonth.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		prevYear = new JButton("<<");
		prevYear.setBackground(Color.WHITE);
		prevYear.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		//setupButtonListeners();
		
		buttonPanel.add(prevYear);
		buttonPanel.add(prevMonth);
		buttonPanel.add(today);
		buttonPanel.add(nextMonth);
		buttonPanel.add(nextYear);
		
		contentPanel.add(buttonPanel, "alignx center, dock north");
	}
}
