/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: CS Anonymous
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.calendar.view.year;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

import edu.wpi.cs.wpisuitetng.modules.calendar.CalendarStandard;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.Event;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.AbCalendar;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.GUIEventController;

/**
 * Single day used for a year view
 * @author CS Anonymous
 * @version $Revision: 1.0 $
 */
@SuppressWarnings("serial")
public class YearDayPane extends JPanel{
	GregorianCalendar scal;
	boolean active = false;
	List<Commitment> commlist = null;
	int numcomm = -1;
	int numevent = 0;
	BackgroundColor bgc_withcomm, bgc;

	/**
	 * The constructor for year day pane
	 * @param acal the current day to display
	 * @param month	the current month, used to decide whether acal is part of the month
	 */
	public YearDayPane(final GregorianCalendar acal, int month){
		this.setBorder(BorderFactory.createSoftBevelBorder(BevelBorder.RAISED));
		scal = (GregorianCalendar)acal.clone();
		this.setLayout(new GridLayout(1, 1));
		final JLabel lbl = new JLabel("" + scal.get(Calendar.DATE), SwingConstants.CENTER);
		active = month == acal.get(Calendar.MONTH);

		if(active){
			lbl.setForeground(Color.black);
			this.setBackground(CalendarStandard.CalendarYellow);

			bgc_withcomm = new BackgroundColor(
					CalendarStandard.CalendarYellow, CalendarStandard.HeatMapRed, 10);
			bgc = new BackgroundColor(
					CalendarStandard.CalendarYellow, CalendarStandard.HeatMapRed, 5);

			//adds double click feature to the days
			this.addMouseListener(new MouseAdapter(){
				public void mouseClicked(MouseEvent e){
					if(e.getClickCount() > 1){
						GUIEventController.getInstance().switchView(scal, AbCalendar.types.DAY);
					}
				}
			});
		}
		else{
			lbl.setForeground(new Color(180, 180, 180));
			this.setBackground(new Color(220, 220, 220));
		}
		this.add(lbl);
		this.setPreferredSize(lbl.getPreferredSize());

	}

	/**
	 * Displays the number of commitments via a heat map in the background
	 * @param commList the list of commitments to show
	 */
	public void displayCommitments(List<Commitment> commList) {
		if(active){
			if(commList == null){
				numcomm = -1;
				this.setBackground(bgc.getColoratStep(numevent));
			}
			else{
				numcomm = commList.size();
				this.setBackground(bgc_withcomm.getColoratStep(numevent + numcomm));
			}
		}
	}

	/**
	 * Displays the number of events via a heat map in the background
	 * @param eventList the list of events to show
	 */
	public void displayEvents(List<Event> eventList) {
		if(active){
			if(eventList == null){
				numevent = 0;
			}
			else{
				numevent = eventList.size();
			}

			if(numcomm == -1){
				this.setBackground(bgc.getColoratStep(numevent));
			}
			else{
				this.setBackground(bgc_withcomm.getColoratStep(numevent + numcomm));
			}
		}
	}

	/**
	 * Internal class used to calculate color in incremental steps between a range of colors
	 * @author CS Anonymous
	 * @version $Revision: 1.0 $
	 */
	protected class BackgroundColor{
		protected Color lower;
		protected Color higher;
		double steps;
		/**
		 * Constructor for BackgroundColor
		 * @param l the lower threshold of the color range, 0 step
		 * @param h the upper threshold of the color range, steps step
		 * @param steps the number of steps possible in the range
		 */
		BackgroundColor(Color l, Color h, int steps){
			this.steps = steps;
			lower = l;
			higher = h;
		}

		/**
		 * Find the color at the the given step in the color range
		 * @param step the step desired to find the color

		 * @return the color at the specific step */
		public Color getColoratStep(int step){
			if(step > steps){
				step = (int)steps;
			}
			final int red, green, blue;
			red = (int)((higher.getRed() - lower.getRed()) * (step / steps) + lower.getRed());
			green = (int)((higher.getGreen() - lower.getGreen())
					* (step / steps) + lower.getGreen());
			blue = (int)((higher.getBlue() - lower.getBlue()) 
					* (step / steps) + lower.getBlue());

			return new Color(red, green, blue);
		}
	}
}
