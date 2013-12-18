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
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.Status;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.month.CalendarObjectWrapperBorder;


/**
 * Commitment view panel is a commitment JPanel for
 * category tab, commitment full view,
 * and commitment view.
 * @author CS Anonymous
 * @version $Revision: 1.0 $
 */
 @SuppressWarnings("serial")
public class CommitmentFullViewPanel extends JPanel {

	public enum Sort_Type{
		NAME, DUE_DATE, STATUS, DESCRIPTION
	};
	 
	private Commitment commitment;
	
	/**
	 * Constructor for CommitmentViewPanel.
	 */
	public CommitmentFullViewPanel(Commitment c) {
		commitment = c;
		this.setLayout(new GridLayout(1, 4));
		//The name label with icon
		final JLabel namelabel = new JLabel(commitment.getName(), JLabel.LEFT);
		namelabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
		try {
			Image nameImg;
			Image scaleImg;
			if (commitment.getIsPersonal()){
				nameImg = ImageIO.read(getClass().getResource("PersonalCommitment_Icon.png"));
				scaleImg = nameImg.getScaledInstance(25, 25, Image.SCALE_SMOOTH);
				namelabel.setIcon(new ImageIcon(scaleImg));
			}
			else{
				nameImg = ImageIO.read(getClass().getResource("TeamCommitment_Icon.png"));
				scaleImg = nameImg.getScaledInstance(25, 25, Image.SCALE_SMOOTH);
				namelabel.setIcon(new ImageIcon(scaleImg));
			}
		} catch (IOException | IllegalArgumentException exception) {

		}
		//gets the current category color 
		Color fore_color;
		try {
			if (commitment.getIsPersonal()) {
				fore_color = GUIEventController.getInstance().getCalendar()
						.getMyCalData().getCategories()
						.getCategory(commitment.getCategoryID()).getCategoryColor();
			} else {
				fore_color = GUIEventController.getInstance().getCalendar()
						.getTeamCalData().getCategories()
						.getCategory(commitment.getCategoryID()).getCategoryColor();
			}
		} catch (java.lang.NullPointerException excep) {
			fore_color = Color.WHITE;
		}
		this.setBorder(BorderFactory.createCompoundBorder(BorderFactory
				.createLoweredBevelBorder(), new CalendarObjectWrapperBorder(
				fore_color, CalendarStandard.CalendarYellow)));
		
		//Formatter used for dates
		final SimpleDateFormat df = new SimpleDateFormat();
		df.applyPattern("EEEE, MMMM d, y - hh:mm a");
		
		//Label for the start time of the event
		final JLabel due_date = new JLabel("" + 
		df.format(commitment.getDueDate().getTime()), JLabel.LEFT);
		due_date.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
		
		//Label for the end time of the event
		final JLabel status = new JLabel("" + 
		Status.convertToString(commitment.getStatus().getId()), JLabel.LEFT);
		status.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
		
		//JLabel for the description of the event
		final JLabel description = new JLabel("<HTML>" + 
		commitment.getDescription() + "</HTML>", JLabel.LEFT);
		description.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
		
		this.add(namelabel);
		this.add(due_date);
		this.add(status);
		this.add(description);

		this.setBackground(CalendarStandard.CalendarYellow);
		this.setPreferredSize(new Dimension(300, 75));
		this.setMaximumSize(new Dimension(20000, 75));
		this.setCursor(new Cursor(Cursor.HAND_CURSOR)); 
		this.setToolTipText("Click to Edit or Delete this Commitment");
		// To change cursor as it moves over this commitment pannel
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() >= 1) {
					GUIEventController.getInstance().editCommitment(commitment);
				}
			}
		});
	}

	public Commitment getCommitment() {
		return commitment;
	}

	public void setCommitment(Commitment newCommitment) {
		commitment = newCommitment;
	}
	
	/**
	 * Compare this with the given event view panel based on the given sort type
	 * @param other the event view panel to compare
	 * @param sort_type	the property that is being compared
	 * @return 0 if they are the same for the sort_type
	 * 		   1 if this is greater than other for the sort_type
	 * 		   -1 if this is lesser than other for the sort_type
	 */
	public int compareTo(CommitmentFullViewPanel other, Sort_Type sort_type){
		//compare based on name
		if(sort_type == Sort_Type.NAME){
			final String myname = commitment.getName();
			final String othername = other.commitment.getName();

			return myname.compareTo(othername);
		}
		//compare based on the due date
		else if(sort_type == Sort_Type.DUE_DATE){
			final GregorianCalendar mycal = commitment.getDueDate();
			final GregorianCalendar othercal = other.commitment.getDueDate();

			if(mycal.before(othercal)){
				return 1;
			}
			else if(mycal.after(othercal)){
				return -1;
			}
			else{
				return 0;
			}
		}
		//compare based on the end date
		else if(sort_type == Sort_Type.STATUS){
			final Status mystat = commitment.getStatus();
			final Status otherstat = other.commitment.getStatus();

			if(mystat.getId() > otherstat.getId()){
				return 1;
			}
			else if(mystat.getId() < otherstat.getId()){
				return -1;
			}
			else{
				return 0;
			}
		}
		//compare based on the description
		else if(sort_type == Sort_Type.DESCRIPTION){
			final String mydesc = commitment.getDescription();
			final String otherdesc = other.commitment.getDescription();

			return mydesc.compareTo(otherdesc);
		}
		//the sort type was undefined so return equal
		return 0;
	}

}
