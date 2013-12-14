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

import javax.swing.JPanel;

/**
 *
 * @author CS Anonymous
 * @version $Revision: 1.0 $
 */
public interface ICalPane {

	public JPanel getPane();
	
	/**
	 * Method updateScrollPosition.
	 * @param value int
	 */
	public void updateScrollPosition(int value);
	
	/**
	 * Method refresh.
	 */
	public void refresh();
	
}
