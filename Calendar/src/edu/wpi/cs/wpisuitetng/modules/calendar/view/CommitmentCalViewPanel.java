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

import java.awt.Cursor;
import java.awt.Image;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import edu.wpi.cs.wpisuitetng.modules.calendar.CalendarStandard;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.Commitment;

/**
 * To be added.
 * @author CS Anonymous
 * @version $Revision: 1.0 $
 */
 @SuppressWarnings("serial")
public class CommitmentCalViewPanel extends JPanel {

	private final Commitment comm;
	
	/**
	 * Constructor for CommitmentCalViewPanel.
	 * @param comm Commitment
	 */
	public CommitmentCalViewPanel(Commitment comm)
	{
		//TODO add function for clicking to go to the editor
		this.comm = comm;
		
		final GregorianCalendar acal = new GregorianCalendar();
		acal.setTime(comm.getDueDate().getTime());
		if(acal.get(Calendar.HOUR_OF_DAY) < 24) {
		} else {
		}
		
		
		final String name = comm.getName();

		setCursor(new Cursor(Cursor.HAND_CURSOR)); // To change cursor as it moves over this text

		JLabel alab = new JLabel(comm.getDescription(), JLabel.CENTER);
		alab.setBackground(CalendarStandard.CalendarYellow);
		add(alab, SwingConstants.CENTER);
		
		Image nameImg;
		final Image scaleImg;
		
		try {
			if (comm.getIsPersonal())
			{
				nameImg = ImageIO.read(AbCalendar.class.getResource("PersonalCommitment_Icon.png"));
				
			}
			else
			{
				nameImg = ImageIO.read(AbCalendar.class.getResource("TeamCommitment_Icon.png"));
			}
			scaleImg = nameImg.getScaledInstance(25, 25, Image.SCALE_SMOOTH);
			alab = new JLabel(name, new ImageIcon(scaleImg), JLabel.CENTER);
		} catch (IOException e) {
			alab = new JLabel(name, JLabel.CENTER);
		}
		
		add(alab, SwingConstants.CENTER);
		
	}

	public Commitment getCommitment() {
		return comm;
	}

}
