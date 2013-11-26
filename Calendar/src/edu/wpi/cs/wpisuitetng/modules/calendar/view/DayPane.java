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
import java.util.ArrayList;
import java.util.GregorianCalendar;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import org.jdesktop.swingx.border.MatteBorderExt;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.calendar.CalendarStandard;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.CalendarData;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.CalendarDataModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.CommitmentList;

public class DayPane extends JPanel implements ICalPane {
	private static final long serialVersionUID = 1L;
	JPanel mainPanel = new JPanel();
	
	private DetailedDay daypane;

	/**
	 * Create the panel.
	 */

	public DayPane(GregorianCalendar datecalendar, AbCalendar abCalendar) {

		final boolean showCommitements = abCalendar.getShowCommitements();
		final boolean showTeamCommitments = abCalendar.getShowTeamData();
		mainPanel.setBackground(CalendarStandard.CalendarYellow);

		SpringLayout layout = new SpringLayout();

		// Set up the scrolling pane
		JScrollPane scrollPane = new JScrollPane(mainPanel,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.getVerticalScrollBar().setUnitIncrement(20);
		
		// Set color within the scrollbar
		scrollPane.getVerticalScrollBar().setBackground(CalendarStandard.CalendarYellow);
		
		scrollPane.setMinimumSize(new Dimension(300, 300));
		scrollPane.setBackground(CalendarStandard.CalendarRed);

		setLayout(new GridLayout(1, 1));
		
		mainPanel.setLayout(layout);
		mainPanel.setPreferredSize(new Dimension(30, 2000));
		
		if (showCommitements) {

			// Sets the UPPER LEFT corner box
			JPanel cornerBoxUL = new JPanel();
			cornerBoxUL.setBackground(CalendarStandard.CalendarRed);
			cornerBoxUL.setBorder(new MatteBorderExt(0, 0, 2, 0, Color.BLACK));
			scrollPane.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER,
					cornerBoxUL);
			
			// Sets the UPPER RIGHT corner box
			JPanel cornerBoxUR = new JPanel();
			cornerBoxUR.setBackground(CalendarStandard.CalendarRed);
			cornerBoxUR.setBorder(new MatteBorderExt(0, 0, 2, 0, Color.BLACK));
			scrollPane.setCorner(ScrollPaneConstants.UPPER_RIGHT_CORNER,
					cornerBoxUR);
			
			// Create the header panel
			JPanel header = new JPanel();
			header.setLayout(new GridLayout(1, 2));
			header.setBackground(CalendarStandard.CalendarRed);
			header.setBorder(new MatteBorder(0, 0, 2, 0, Color.BLACK));

			// Create and set the label "Events" for when ShowCommitments is
			// checked
			JLabel eventlabel = new JLabel("<html><font color='white'><b>"
					+ "Events" + "</b></font></html>", SwingConstants.CENTER);
			eventlabel.setFont(CalendarStandard.CalendarFont.deriveFont(14));
			header.add(eventlabel);

			// Create and set the label "Commitments" for when ShowCommitments
			// is checked
			JLabel commitlabel = new JLabel("<html><font color='white'><b>"
					+ "Commitments" + "</b></font></html>",
					SwingConstants.CENTER);
			commitlabel.setFont(CalendarStandard.CalendarFont.deriveFont(14));
			header.add(commitlabel);

			// add apane to the header of the scrollpane
			scrollPane.setColumnHeaderView(header);

			// Populate the pane with commitments
			ArrayList<Commitment> commitmentList = new ArrayList<Commitment>();
			CalendarData teamCommitments = CalendarDataModel
					.getInstance()
					.getCalendarData(ConfigManager.getConfig().getProjectName());
			if (!showTeamCommitments && showCommitements) {
				commitmentList = new ArrayList<Commitment>(abCalendar
						.getCalData().getCommitments().getCommitments());
			}
			if (showTeamCommitments && showCommitements) {
				commitmentList = new ArrayList<Commitment>(abCalendar
						.getCalData().getCommitments().getCommitments());
				commitmentList.addAll(teamCommitments.getCommitments()
						.getCommitments());
			}
			
			daypane = new DetailedDay(datecalendar, new CommitDetailedPane(datecalendar, commitmentList));

		} else {
			daypane = new DetailedDay(datecalendar);
		}
		
		scrollPane.setRowHeaderView(getTimesBar(mainPanel
				.getPreferredSize().getHeight()));
		scrollPane.getVerticalScrollBar().setValue(800);
		
		// Add ScrollPane to the main panel
		add(scrollPane);

		layout.putConstraint(SpringLayout.WEST, daypane, 0, SpringLayout.WEST,
				mainPanel);
		layout.putConstraint(SpringLayout.NORTH, daypane, 0,
				SpringLayout.NORTH, mainPanel);
		layout.putConstraint(SpringLayout.SOUTH, daypane, 0,
				SpringLayout.SOUTH, mainPanel);
		layout.putConstraint(SpringLayout.EAST, daypane, 0, SpringLayout.EAST,
				mainPanel);

		mainPanel.add(daypane);

	}

	/**
	 * Displays commitments on DetailedDay
	 * 
	 * @param commList
	 *            List of commitments to be displayed
	 */
	public void displayCommitments(CommitmentList commList) {

		/*
		 * if(commList!=null) daypane.displayCommitments(commList);
		 * daypane.revalidate(); daypane.repaint();
		 */
	}

	protected JComponent getTimesBar(double height) {
		JPanel apane = new JPanel();
		apane.setBackground(CalendarStandard.CalendarRed);
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
		// TODO Auto-generated method stub
		return this;
	}
}