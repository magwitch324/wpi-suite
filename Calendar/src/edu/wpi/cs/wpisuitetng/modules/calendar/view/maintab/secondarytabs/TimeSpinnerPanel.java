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

import javax.swing.JLabel;
import javax.swing.JSpinner;

/**
 * Time Spinner Panel that contains the hour, minute, and AMPM spinners to be
 * ready for import into gui.
 * @author CS Anonymous
 * @version $Revision: 1.0 $
 */
public class TimeSpinnerPanel {
	private JSpinner hourSpinner;
	private JSpinner minuteSpinner;
	private JSpinner AMPMSpinner;
	
	private JLabel colon;
}
