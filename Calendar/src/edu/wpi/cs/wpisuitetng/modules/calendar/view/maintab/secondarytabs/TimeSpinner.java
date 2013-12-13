package edu.wpi.cs.wpisuitetng.modules.calendar.view.maintab.secondarytabs;

import java.awt.Color;
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
 */
public class TimeSpinner extends JSpinner {
	
	private JSpinner.DateEditor editor;
	private boolean upArrowAction;
	private boolean downArrowAction;
	private enumTimeSpinner myType;
	private int fallBackValueInt;
	private String fallBackValueStr;
	private boolean errorVisible;
	
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
				//checkTimeSpinnerStatus(myType);
				//refreshTemp();
				//checkSaveBtnStatus();
			}
		});
	}

	
	private void refreshTemp() {
		switch(myType){
		case HOUR:
			fallBackValueInt = Integer.parseInt(editor.getTextField().getText());
			if (upArrowAction) {
				if (fallBackValueInt == 12)
					fallBackValueInt = 1;
				else {
					fallBackValueInt++;
				}
			}
			else if (downArrowAction)
				if (fallBackValueInt == 1)
					fallBackValueInt = 12;
				else {
					fallBackValueInt--;
				}
			break;
		case MINUTE:
			fallBackValueInt = Integer.parseInt(editor.getTextField().getText());
			System.out.println("before increment tempMin" + fallBackValueInt);
			if (upArrowAction) {
				if (fallBackValueInt == 59)
					fallBackValueInt = 0;
				else {
					fallBackValueInt++;
				}
			}
			else if (downArrowAction)
				if (fallBackValueInt == 0)
					fallBackValueInt = 59;
				else {
					fallBackValueInt--;
				}
			break;
		case AMPM:
			fallBackValueStr = editor.getTextField().getText();

			System.out.println("new tempAMPM is " + fallBackValueStr);
			
			break;
		}
	}
	
	private void checkTimeSpinnerStatus(enumTimeSpinner hour) {

		int currentText = 0;
		
		//System.out.println(tempAMPMString);
		switch (hour) {
		case HOUR:
			currentText = Integer.parseInt(editor.getTextField().getText());
				if(currentText < 1 || currentText > 12) {
					editor.getTextField().setText(Integer.toString(fallBackValueInt));
					editor.getTextField().setBackground(Color.getHSBColor(3, 0.3f, 1f));
					errorVisible = true;
				}
				else {
					editor.getTextField().setBackground(CalendarStandard.CalendarYellow);
					errorVisible = false;
				}
			break;
			
		case MINUTE:
			currentText = Integer.parseInt(editor.getTextField().getText());
			System.out.println("curent text before check" + currentText);
			System.out.println("curent temp before check" + fallBackValueInt);
				if(currentText < 0 || currentText > 59) {
					editor.getTextField().setText(Integer.toString(fallBackValueInt));
					editor.getTextField().setBackground(Color.getHSBColor(3, 0.3f, 1f));
					errorVisible = true;
				}
				else {
					editor.getTextField().setBackground(CalendarStandard.CalendarYellow);
					errorVisible = false;
			}
			break;
		case AMPM:
			String tempAMPMString = editor.getTextField().getText().toUpperCase();
			System.out.println("Input is " + tempAMPMString);
			
			if(!tempAMPMString.equals("AM") && !tempAMPMString.equals("PM")) {
				editor.getTextField().setText(fallBackValueStr);
				editor.getTextField().setBackground(Color.getHSBColor(3, 0.3f, 1f));
				errorVisible = true;
			}
			else {
				editor.getTextField().setBackground(CalendarStandard.CalendarYellow);
					errorVisible = false;
		}
			break;
		}
}
	
	class SpinnerUI extends BasicSpinnerUI  {
		protected Component createNextButton()  
		  {  
		    JButton btnUp = (JButton)super.createNextButton();  
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
		    JButton btnDown = (JButton)super.createPreviousButton();  
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
