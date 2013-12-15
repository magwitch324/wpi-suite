/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: CS Anonymous
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.calendar.view.month;

import java.awt.AWTEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.JLayer;
import javax.swing.Timer;
import javax.swing.plaf.LayerUI;

/**
 * Layer UI that listens for a click so the month day can expand
 * and if the mouse leaves so it can shrink
 */
@SuppressWarnings("serial")
public class ScrollUI extends LayerUI<JComponent> implements ActionListener {
	MonthDayPane pane;
	boolean is_in = false;

	/**
	 * Constructor for the ScrollUI
	 * @param p the MonthDayPane that should change based on mouse events
	 */
	public ScrollUI(MonthDayPane p) {
		pane = p;
	}

	/**
	 * Sets the event mask to listen for mouse events
	 */
	@Override
	public void installUI(JComponent c) {
		super.installUI(c);
		final JLayer jlayer = (JLayer) c;
		jlayer.setLayerEventMask(AWTEvent.MOUSE_EVENT_MASK);
	}
	
	/**
	 * Clears the event mask
	 */
	@Override
	public void uninstallUI(JComponent c) {
		final JLayer jlayer = (JLayer) c;
		jlayer.setLayerEventMask(0);
		super.uninstallUI(c);
	}

	/**
	 * Process the mouse event
	 */
	@Override
	protected void processMouseEvent(MouseEvent e, JLayer l) {
		//the mouse has entered so keep track of it
		if (e.getID() == MouseEvent.MOUSE_ENTERED) {
			is_in = true;
		}

		//the mouse may have exited so set a timer to eventually shrink it
		if (e.getID() == MouseEvent.MOUSE_EXITED) {
			is_in = false;

			final Timer atimer = new Timer(5, this);
			atimer.setDelay(5);
			atimer.setRepeats(false);
			atimer.setInitialDelay(5);
			atimer.start();

		}
		
		//the layer was clicked so this should go big
		if (e.getID() == MouseEvent.MOUSE_CLICKED) {
			if (e.getClickCount() == 1) {
				pane.goBig();
			}
		}
	}

	/**
	 * Checks whether the mouse actually exited the layer without immediate re-entry
	 * if it really exited then it should shrink
	 * if it did not then it will do nothing
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (!is_in) {
			pane.goSmall();
		}
	}

}