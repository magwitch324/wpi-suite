/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: CS Anonymous
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.calendar.view.maintab.secondarytabs;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.modules.calendar.CalendarStandard;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.Event;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.AbCalendar;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.GUIEventController;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.month.CalendarObjectWrapperBorder;

/**
 * JPanel that is responsible for displaying EventFullView.
 * 
 * @author CS Anonymous
 * @version $Revision: 1.0 $
 */
@SuppressWarnings("serial")
public class EventViewPanel extends JPanel {
	private final Event event;

	public enum Sort_Type {
		NAME, START_DATE, END_DATE, DESCRIPTION
	};

	/**
	 * Constructor for EventViewPanel.
	 * 
	 * @param e
	 *            Event
	 */
	public EventViewPanel(Event e) {
		event = e;
		this.setLayout(new GridLayout(1, 4));
		// The name label with icon
		final JLabel namelabel = new JLabel(event.getName(), JLabel.LEFT);
		namelabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
		try {
			Image nameImg;
			Image scaleImg;
			if (event.getIsPersonal()) {
				nameImg = ImageIO.read(AbCalendar.class.getResource(
						"PersonalEvent_Icon.png"));
				scaleImg = nameImg
						.getScaledInstance(25, 25, Image.SCALE_SMOOTH);
				namelabel.setIcon(new ImageIcon(scaleImg));
			} else {
				nameImg = ImageIO.read(AbCalendar.class.getResource(
						"TeamEvent_Icon.png"));
				scaleImg = nameImg
						.getScaledInstance(25, 25, Image.SCALE_SMOOTH);
				namelabel.setIcon(new ImageIcon(scaleImg));
			}
		} catch (IOException | IllegalArgumentException exception) {

		}

		//gets the current category color 
		Color fore_color;
		try {
			if (event.getIsPersonal()) {
				fore_color = GUIEventController.getInstance().getCalendar()
						.getMyCalData().getCategories()
						.getCategory(event.getCategoryID()).getCategoryColor();
			} else {
				fore_color = GUIEventController.getInstance().getCalendar()
						.getTeamCalData().getCategories()
						.getCategory(event.getCategoryID()).getCategoryColor();
			}
		} catch (java.lang.NullPointerException excep) {
			fore_color = Color.WHITE;
		}
		this.setBorder(BorderFactory.createCompoundBorder(BorderFactory
				.createLoweredBevelBorder(), new CalendarObjectWrapperBorder(
				fore_color, CalendarStandard.CalendarYellow)));
		
		// Formatter used for dates
		final SimpleDateFormat df = new SimpleDateFormat();
		df.applyPattern("EEEE, MMMM d, y - hh:mm a");

		// Label for the start time of the event
		final JLabel start_date = new JLabel(""
				+ df.format(event.getStartTime().getTime()), JLabel.LEFT);
		start_date.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

		// Label for the end time of the event
		final JLabel end_date = new JLabel(""
				+ df.format(event.getEndTime().getTime()), JLabel.LEFT);
		end_date.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

		// JLabel for the description of the event
		final JLabel description = new JLabel("<HTML>" + event.getDescription()
				+ "</HTML>", JLabel.LEFT);
		description.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 5));

		this.add(namelabel);
		this.add(start_date);
		this.add(end_date);
		this.add(description);

		this.setPreferredSize(new Dimension(300, 100));
		this.setMaximumSize(new Dimension(20000, 75));
		this.setCursor(new Cursor(Cursor.HAND_CURSOR));
		this.setToolTipText("Click to Edit or Delete this Event");
		// To change cursor as it moves over this commitment pannel
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() >= 1) {
					GUIEventController.getInstance().editEvent(event);
				}
			}
		});
	}

	/**
	 * Compare this with the given event view panel based on the given sort type
	 * 
	 * @param other
	 *            the event view panel to compare
	 * @param sort_type
	 *            the property that is being compared
	 * @return 0 if they are the same for the sort_type 1 if this is greater
	 *         than other for the sort_type -1 if this is lesser than other for
	 *         the sort_type
	 */
	public int compareTo(EventViewPanel other, Sort_Type sort_type) {
		// compare based on name
		if (sort_type == Sort_Type.NAME) {
			final String myname = event.getName();
			final String othername = other.event.getName();

			return myname.compareTo(othername);
		}
		// compare based on the start date
		else if (sort_type == Sort_Type.START_DATE) {
			final GregorianCalendar mycal = event.getStartTime();
			final GregorianCalendar othercal = other.event.getStartTime();

			if (mycal.before(othercal)) {
				return 1;
			} else if (mycal.after(othercal)) {
				return -1;
			} else {
				return 0;
			}
		}
		// compare based on the end date
		else if (sort_type == Sort_Type.END_DATE) {
			final GregorianCalendar mycal = event.getEndTime();
			final GregorianCalendar othercal = other.event.getEndTime();

			if (mycal.before(othercal)) {
				return 1;
			} else if (mycal.after(othercal)) {
				return -1;
			} else {
				return 0;
			}
		}
		// compare based on the description
		else if (sort_type == Sort_Type.DESCRIPTION) {
			final String mydesc = event.getDescription();
			final String otherdesc = other.event.getDescription();

			return mydesc.compareTo(otherdesc);
		}
		// the sort type was undefined so return equal
		return 0;
	}

}
