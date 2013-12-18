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

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicSpinnerUI;

import edu.wpi.cs.wpisuitetng.modules.calendar.CalendarStandard;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.maintab.secondarytabs.CommitmentTab.enumTimeSpinner;


/**
 * New time spinner which has error checking ability.
 * @author CS Anonymous
 * @version $Revision: 1.0 $
 */
public class TimeSpinner extends JSpinner {
	
	private JSpinner.DateEditor editor;
	private boolean upArrowAction;
	private boolean downArrowAction;
	private final enumTimeSpinner myType;
	private int fallBackValueInt;
	private String fallBackValueStr;
	private boolean errorVisible;
	
	/**
	 * Constructor for TimeSpinner.
	 * @param type enumTimeSpinner
	 * @param model SpinnerDateModel
	 */
	public TimeSpinner(enumTimeSpinner type, SpinnerDateModel model) {
		super(model);
		myType = type;
		this.setFont(new Font("Tahoma", Font.PLAIN, 18));
		this.setUI(new SpinnerUI());
		switch(type) {
			case HOUR:
				editor = new JSpinner.DateEditor(this, "hh");
				break;
			case MINUTE:
				editor = new JSpinner.DateEditor(this, "mm");
				break;
			case AMPM:
				editor = new JSpinner.DateEditor(this, "a");
				break;
		}
		
		this.setEditor(editor);
		editor.getTextField().setBackground(CalendarStandard.CalendarYellow);
		editor.getTextField().setFocusLostBehavior(JFormattedTextField.PERSIST);
		
		this.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
			}
		});
	}

	/**
	 * @author CS Anonymous
	 */
	class SpinnerUI extends BasicSpinnerUI  {
		protected Component createNextButton()  
		  {  
			final JButton btnUp = (JButton)super.createNextButton();  
		    btnUp.addActionListener(new ActionListener(){  
		      public void actionPerformed(ActionEvent ae){
			        System.out.println("Going up");  
		    	  try {
		    		upArrowAction = true;
		    		downArrowAction = false;
					editor.getTextField().commitEdit();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

		      }  
		          });  
		    return btnUp;  
		  }  
		  protected Component createPreviousButton()  
		  {  
			 final JButton btnDown = (JButton)super.createPreviousButton();  
		    btnDown.addActionListener(new ActionListener(){  
		      public void actionPerformed(ActionEvent ae){ 
		    	  try {
		    		downArrowAction = true;
		    		upArrowAction = false;
					editor.getTextField().commitEdit();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

		      }  
		    });  
		    return btnDown;  
		  }  
	}
}
