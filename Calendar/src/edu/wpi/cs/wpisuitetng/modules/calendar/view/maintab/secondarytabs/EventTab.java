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

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeListener;

import org.jdesktop.swingx.JXDatePicker;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.calendar.controller.UpdateCalendarDataController;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.CalendarData;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.CalendarDataModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.Category;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.Event;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.RepeatingEvent;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.RepeatingEvent.RepeatType;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.GUIEventController;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Insets;

import javax.swing.event.ChangeEvent;
import javax.swing.JButton;

import java.awt.Color;

import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;

/**
 * @author sfp
 *
 */
public class EventTab extends JPanel {
	private GregorianCalendar startDate;
	private GregorianCalendar startTime;
	private JTextField nameTextField;
	private GridBagConstraints gbc_nameTextField;
	private JSpinner startTimeSpinner;
	private JSpinner endTimeSpinner;
	private boolean isTeamEvent;
	SpinnerDateModelMinute startSpinnerModel = new SpinnerDateModelMinute();  
	SpinnerDateModelMinute endSpinnerModel = new SpinnerDateModelMinute();  
	private JButton btnAddEvent;
	private JComboBox<Category> categoryComboBox;
	private JTextArea descriptionTextArea;
	private JXDatePicker startDatePicker;
	private JXDatePicker endDatePicker;
	private JScrollPane descPane;
	private JPanel panel;
	private JSpinner.DateEditor endTimeEditor;
	private JPanel panel_1;
	private JRadioButton rdbtnPersonal;
	private JRadioButton rdbtnTeam;
	private JLabel lblType;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JButton btnCancel;
	private Date tmpDate = new Date(); //user for convert the date to default format
	private String inputDate = (new SimpleDateFormat("MM/dd/yyyy EEE").format(tmpDate)); //the date user input
	private boolean badDate;
	private boolean badTime;
	private Event editingEvent;
	private EditingMode mode = EditingMode.ADDING;
	private JButton btnDelete;
	private JPanel buttonPanel;
	private JPanel formPanel;
	private boolean initFlag; //to keep things from running before we fully intialize
	private JSpinner.DateEditor startTimeEditor;
	private JCheckBox repeatCheckBox;
	private JLabel lblRepeat;
	private JTextField repeatAmt;
	private JComboBox<String> repeatTypeComboBox;
	private JLabel lblRepeatType;
	private JLabel lblNumberRepetitions;
	private RepeatingEvent editingRepeatingEvent;
	
	
	
	/*
	 * Sources:
	 * Icons were developed using images obtained at: 
	 * [1] https://svn.apache.org/repos/asf/openoffice/symphony/trunk/main/extras/source/gallery/symbols/
	 * [2] http://www.clker.com/clipart-red-round.html
	 * [3] http://www.iconsdb.com/red-icons/delete-icon.html
	 */
	
	
	
	private enum EditingMode {
		ADDING(0),
		EDITING(1);
		private int currentMode;
		
		private EditingMode(int currentMode) {
			this.currentMode = currentMode;
		}
	}
	
	/**
	 * Create the panel.
	 */
	public EventTab() {
		this.initFlag = false;
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		setLayout(gridBagLayout);
		JPanel spacePanel1 = new JPanel();
		JPanel spacePanel2 = new JPanel();
		formPanel = new JPanel();
		formPanel.setPreferredSize(new Dimension(500,600));
		formPanel.setMinimumSize(new Dimension(500, 600));
		spacePanel1.setMinimumSize(formPanel.getSize());
		spacePanel2.setMinimumSize(formPanel.getSize());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.weightx = 1;
		constraints.gridx = 0;
		constraints.weighty = 1;
		constraints.fill = GridBagConstraints.BOTH;
		add(spacePanel1, constraints);
		constraints = new GridBagConstraints();
		constraints.weightx = 2;
		constraints.gridx = 1;
		constraints.weighty = 1;
		constraints.fill = GridBagConstraints.BOTH;
		add(formPanel, constraints);
		constraints = new GridBagConstraints();
		constraints.weightx = 1;
		constraints.gridx = 2;
		constraints.weighty = 1;
		constraints.fill = GridBagConstraints.BOTH;
		add(spacePanel2, constraints);
		
		// form uses GridBagLayout w/ two columns
		GridBagLayout gbl = new GridBagLayout();
		gbl.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		gbl.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0};
		gbl.rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl.columnWidths = new int[] {0, 0, 0, 0};
		formPanel.setLayout(gbl);
		
		//Name label
		JLabel lblName = new JLabel("Name:");
		lblName.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(0, 0, 5, 5);
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;
		formPanel.add(lblName, gbc);
		
		//Name text field
		nameTextField = new JTextField();
		gbc_nameTextField = new GridBagConstraints();
		gbc_nameTextField.gridwidth = 3;
		gbc_nameTextField.insets = new Insets(0, 0, 5, 0);
		gbc_nameTextField.fill = GridBagConstraints.HORIZONTAL;
        gbc_nameTextField.weightx = 10;
        gbc_nameTextField.weighty = 1;
        gbc_nameTextField.gridx = 1;
        gbc_nameTextField.gridy = 0;
        gbc.gridwidth = 3;
		formPanel.add(nameTextField, gbc_nameTextField);
		
		nameTextField.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				listenerHelper();
			}
			
			
			
			
		});
		
		
		//Description label
		JLabel lblDesc = new JLabel("Description:");
		lblDesc.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_lblDesc = new GridBagConstraints();
		gbc_lblDesc.fill = GridBagConstraints.BOTH;
		gbc_lblDesc.insets = new Insets(0, 0, 5, 5);
		gbc_lblDesc.gridx = 0;
		gbc_lblDesc.gridy = 1;
		formPanel.add(lblDesc, gbc_lblDesc);
		
		
		//Scrollpane for description text area
//		descPane = new JScrollPane();
//		descPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
//		descPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		//Text area for description
		descriptionTextArea = new JTextArea();
//		descriptionTextArea.setPreferredSize(new Dimension(500,160));
//		descPane.setViewportView(descriptionTextArea);
		descriptionTextArea.setLineWrap(true);
		GridBagConstraints gbc_descriptionTextField = new GridBagConstraints();
		gbc_descriptionTextField.gridwidth = 3;
		gbc_descriptionTextField.fill = GridBagConstraints.BOTH;
		gbc_descriptionTextField.insets = new Insets(0, 0, 5, 0);
        gbc_descriptionTextField.weightx = 10;
        gbc_descriptionTextField.weighty = 5;
        gbc_descriptionTextField.gridx = 1;
        gbc_descriptionTextField.gridy = 1;
        gbc.gridwidth = 3;
		formPanel.add(descriptionTextArea, gbc_descriptionTextField);
		
		descriptionTextArea.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				listenerHelper();
			}
			
			
			
			
		});
		
		//Category label
		JLabel lblCategory = new JLabel("Category:");
		lblCategory.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_lblCategory = new GridBagConstraints();
		gbc_lblCategory.anchor = GridBagConstraints.EAST;
		gbc_lblCategory.insets = new Insets(0, 0, 5, 5);
		gbc_lblCategory.gridx = 0;
		gbc_lblCategory.gridy = 2;
		gbc_lblCategory.weightx = 1;
		gbc_lblCategory.weighty = 1;
		formPanel.add(lblCategory, gbc_lblCategory);
		
		//Create category box, add two dummy categories
		categoryComboBox = new JComboBox<Category>();
		categoryComboBox.addItem(new Category(4, "Cat1"));
		categoryComboBox.addItem(new Category(5, "Cat2"));

		GridBagConstraints gbc_categoryComboBox = new GridBagConstraints();
		gbc_categoryComboBox.gridwidth = 3;
		gbc_categoryComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_categoryComboBox.insets = new Insets(0, 0, 5, 0);
		gbc_categoryComboBox.gridx = 1;
		gbc_categoryComboBox.gridy = 2;
		gbc_categoryComboBox.weightx = 10;
		gbc_categoryComboBox.weighty = 1;
        gbc.gridwidth = 3;
		formPanel.add(categoryComboBox, gbc_categoryComboBox);
		
		categoryComboBox.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				listenerHelper();
			}
			
		});
		
		lblType = new JLabel("Type:");
		lblType.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_lblType = new GridBagConstraints();
		gbc_lblType.anchor = GridBagConstraints.EAST;
		gbc_lblType.insets = new Insets(0, 0, 5, 5);
		gbc_lblType.gridx = 0;
		gbc_lblType.gridy = 3;
		formPanel.add(lblType, gbc_lblType);
		
		panel_1 = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.gridwidth = 3;
		gbc_panel_1.insets = new Insets(0, 0, 5, 0);
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 1;
		gbc_panel_1.gridy = 3;
		formPanel.add(panel_1, gbc_panel_1);
		
		rdbtnPersonal = new JRadioButton("Personal");
		buttonGroup.add(rdbtnPersonal);
		panel_1.add(rdbtnPersonal);
		
		rdbtnTeam = new JRadioButton("Team");
		buttonGroup.add(rdbtnTeam);
		panel_1.add(rdbtnTeam);
		
		rdbtnTeam.setSelected(true);
		
		//Date label
		JLabel lblDate_1 = new JLabel("Start Date:");
		lblDate_1.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_lblDate_1 = new GridBagConstraints();
		gbc_lblDate_1.fill = GridBagConstraints.VERTICAL;
		gbc_lblDate_1.anchor = GridBagConstraints.EAST;
		gbc_lblDate_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblDate_1.gridx = 0;
		gbc_lblDate_1.gridy = 4;
		gbc_lblDate_1.weightx = 1;
		gbc_lblDate_1.weighty = 1;
		formPanel.add(lblDate_1, gbc_lblDate_1);
		
		//Time label
		JLabel lblTime = new JLabel("Start Time:");
		lblTime.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_lblTime = new GridBagConstraints();
		gbc_lblTime.anchor = GridBagConstraints.EAST;
		gbc_lblTime.fill = GridBagConstraints.VERTICAL;
		gbc_lblTime.insets = new Insets(0, 0, 5, 5);
		gbc_lblTime.gridx = 2;
		gbc_lblTime.gridy = 4;
		gbc_lblTime.weighty = 1;
		formPanel.add(lblTime, gbc_lblTime);
		
		//Time spinner, half hour resolution
		startTimeSpinner = new JSpinner( new SpinnerDateModelMinute());
		startTimeSpinner.setModel(startSpinnerModel);
		startTimeEditor = new JSpinner.DateEditor(startTimeSpinner, "hh:mm a");
		startTimeSpinner.setEditor(startTimeEditor);
		GridBagConstraints gbc_spinner = new GridBagConstraints();
		gbc_spinner.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinner.insets = new Insets(0, 0, 5, 0);
		gbc_spinner.gridx = 3;
		gbc_spinner.gridy = 4;
		gbc_spinner.weightx = 1;
		gbc_spinner.weighty = 3;
		formPanel.add(startTimeSpinner, gbc_spinner);
		
		
	
		
		//Invalid Time label
		final JLabel lblTimeError = new JLabel("Please enter a valid time.");
		lblTimeError.setVisible(false);
		
		//Invalid Date label
		final JLabel lblDateError = new JLabel("<html><font color='red'>Please enter a valid date (MM/DD/YYYY).</font></html>");
		lblDateError.setVisible(false);
		lblDateError.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblDateError = new GridBagConstraints();
		gbc_lblDateError.insets = new Insets(0, 0, 5, 5);
		gbc_lblDateError.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblDateError.gridx = 1;
		gbc_lblDateError.gridy = 5;
		gbc_lblDateError.weightx = 1;
		gbc_lblDateError.weighty = 1;		
		formPanel.add(lblDateError, gbc_lblDateError);
		lblTimeError.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblTimeError = new GridBagConstraints();
		gbc_lblTimeError.insets = new Insets(0, 0, 5, 0);
		gbc_lblTimeError.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblTimeError.gridx = 3;
		gbc_lblTimeError.gridy = 5;
		gbc_lblTimeError.weightx = 1;
		gbc_lblTimeError.weighty = 1;		
		formPanel.add(lblTimeError, gbc_lblTimeError);
		
		
		
		
		//DatePicker box
		startDatePicker = new JXDatePicker();
		GridBagConstraints gbc_jdp = new GridBagConstraints();
		gbc_jdp.insets = new Insets(0, 0, 5, 5);
		gbc_jdp.fill = GridBagConstraints.HORIZONTAL;
		gbc_jdp.gridx = 1;
		gbc_jdp.gridy = 4;
		gbc_jdp.weightx = 1;
		gbc_jdp.weighty = 3;
		formPanel.add(startDatePicker, gbc_jdp);
		//Calendar calendar = datePicker.getMonthView().getCalendar();
		//calendar.setTime(new Date());
		//datePicker.getMonthView().setLowerBound(calendar.getTime());
		SimpleDateFormat format1 = new SimpleDateFormat( "MM/dd/yyyy EEE" );
		SimpleDateFormat format2 = new SimpleDateFormat( "MM/dd/yyyy" );
		SimpleDateFormat format3 = new SimpleDateFormat( "MM.dd.yyyy" );
		SimpleDateFormat format4 = new SimpleDateFormat( "MM.dd.yyyy EEE" );
		startDatePicker.setFormats(new DateFormat[] {format1, format2, format3, format4});
		
		
//		startDatePicker.getEditor().addKeyListener(new KeyListener(){
//
//			@Override
//			public void keyTyped(KeyEvent e) {
//			}
//
//			@Override
//			public void keyPressed(KeyEvent e) {
//				startDatePicker.getEditor().setBackground(Color.WHITE);
//				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
//					checkBadDate();
//				}
//			}
//
//			@Override
//			public void keyReleased(KeyEvent e) {
//				inputDate = startDatePicker.getEditor().getText().trim();
//				listenerHelper();
//				
//				// This next line checks for a blank date field, DO NOT REMOVE
//				if(nameTextField.getText().equals("") || startDatePicker.getEditor().getText().equals("")
//                        || nameTextField.getText().trim().length() == 0){
//                btnAddEvent.setEnabled(false);
//				}
//
//				//boolean orignValue = initFlag;
//				//initFlag = true;
//				//listenerHelper();
//				//initFlag = orignValue;
//			}
//
//		});
//		
//		startDatePicker.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				startDatePicker.getEditor().setBackground(Color.WHITE);
//				inputDate = startDatePicker.getEditor().getText().trim();
//			}
//			
//		});
//		
//		startDatePicker.getEditor().addFocusListener(new FocusListener() {
//			@Override
//			public void focusGained(FocusEvent e) {
//				if(badDate) {
//					startDatePicker.getEditor().setBackground(Color.getHSBColor(3, 0.3f, 1f));
//					startDatePicker.getEditor().selectAll();
//					lblDateError.setVisible(true);
//					badDate = false;
//				}
//				/*
//				else if(badDate){
//					datePicker.getEditor().setText("The date is not valid");
//					datePicker.getEditor().setBackground(Color.red);
//					badDate = false;
//				}
//				*/
//				else{
//					//try {
//						SimpleDateFormat dt = new SimpleDateFormat("MM/dd/yyyy EEE"); 
//						startDatePicker.getEditor().setBackground(Color.WHITE);
//						startDatePicker.getEditor().setText(dt.format(startDatePicker.getDate()));
//						startDatePicker.getEditor().selectAll();
//						listenerHelper();
//					//}catch(NullPointerException ne) {
//					//	datePicker.getEditor().setText("The date is not valid");
//					//	datePicker.getEditor().setBackground(Color.red);
//					//}
//				}
//			}
//
//			@Override
//			public void focusLost(FocusEvent e) {
//				if(isBadInputDate()){
//					badDate = true;
//					startDatePicker.requestFocus();
//				}
//				/*
//				else{
//					Date date = null;
//					for(DateFormat formatter : datePicker.getFormats()) {
//						try {
//							date = formatter.parse(inputDate);
//						} catch (ParseException e1) {
//
//						}
//					}
//					if(date.compareTo(new Date()) < 0) {
//						badDate = true;
//						datePicker.requestFocus();
//					}
//				}
//				*/
//				else{
//					startDatePicker.getEditor().setBackground(Color.WHITE);
//					lblDateError.setVisible(false);
//				}
//				listenerHelper();
//			}
//		});
//		startDatePicker.addFocusListener(new FocusListener() {
//
//			@Override
//			public void focusGained(FocusEvent e) {
//				startDatePicker.getEditor().setBackground(Color.WHITE);
//				listenerHelper();
//			}
//
//			@Override
//			public void focusLost(FocusEvent e) {
//				startDatePicker.getEditor().setBackground(Color.WHITE);
//				listenerHelper();
//				
//			}
//
//			
//			
//			
//			
//		});
//		

		
		

		
		//End Date/Time Forms
		
		//Date label
		JLabel lblDate_2 = new JLabel("End Date:");
		lblDate_2.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_lblDate_2 = new GridBagConstraints();
		gbc_lblDate_2.fill = GridBagConstraints.VERTICAL;
		gbc_lblDate_2.anchor = GridBagConstraints.EAST;
		gbc_lblDate_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblDate_2.gridx = 0;
		gbc_lblDate_2.gridy = 6;
		gbc_lblDate_2.weightx = 1;
		gbc_lblDate_2.weighty = 1;
		formPanel.add(lblDate_2, gbc_lblDate_2);
		
		//Time2 label
		JLabel lblTime2 = new JLabel("End Time:");
		lblTime2.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_lblTime2 = new GridBagConstraints();
		gbc_lblTime2.anchor = GridBagConstraints.EAST;
		gbc_lblTime2.fill = GridBagConstraints.VERTICAL;
		gbc_lblTime2.insets = new Insets(0, 0, 5, 5);
		gbc_lblTime2.gridx = 2;
		gbc_lblTime2.gridy = 6;
		gbc_lblTime2.weighty = 1;
		formPanel.add(lblTime2, gbc_lblTime2);
		
		//Time spinner, half hour resolution
		endTimeSpinner = new JSpinner( new SpinnerDateModelMinute());
		endTimeSpinner.setModel(endSpinnerModel);
		endTimeEditor = new JSpinner.DateEditor(endTimeSpinner, "hh:mm a");
		endTimeSpinner.setEditor(endTimeEditor);
		GridBagConstraints gbc_spinner2 = new GridBagConstraints();
		gbc_spinner2.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinner2.insets = new Insets(0, 0, 5, 0);
		gbc_spinner2.gridx = 3;
		gbc_spinner2.gridy = 6;
		gbc_spinner2.weightx = 1.0;
		gbc_spinner2.weighty = 3;
		formPanel.add(endTimeSpinner, gbc_spinner2);
		
		
		
		
		
		//Invalid Time label
		final JLabel lblTimeError2 = new JLabel("Please enter a valid time.");
		lblTimeError2.setVisible(false);
		
		//Invalid Date label
		final JLabel lblDateError2 = new JLabel("<html><font color='red'>Please enter a valid date (MM/DD/YYYY).</font></html>");
		lblDateError2.setVisible(false);
		lblDateError2.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblDateError2 = new GridBagConstraints();
		gbc_lblDateError2.insets = new Insets(0, 0, 5, 5);
		gbc_lblDateError2.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblDateError2.gridx = 1;
		gbc_lblDateError2.gridy = 7;
		gbc_lblDateError2.weightx = 1;
		gbc_lblDateError2.weighty = 1;		
		formPanel.add(lblDateError2, gbc_lblDateError2);
		lblTimeError2.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblTimeError2 = new GridBagConstraints();
		gbc_lblTimeError2.insets = new Insets(0, 0, 5, 0);
		gbc_lblTimeError2.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblTimeError2.gridx = 3;
		gbc_lblTimeError2.gridy = 7;
		gbc_lblTimeError2.weightx = 1;
		gbc_lblTimeError2.weighty = 1;		
		formPanel.add(lblTimeError2, gbc_lblTimeError2);
		
		
		
		
		//DatePicker box
		endDatePicker = new JXDatePicker();
		GridBagConstraints gbc_jdp2 = new GridBagConstraints();
		gbc_jdp2.insets = new Insets(0, 0, 5, 5);
		gbc_jdp2.fill = GridBagConstraints.HORIZONTAL;
		gbc_jdp2.gridx = 1;
		gbc_jdp2.gridy = 6;
		gbc_jdp2.weightx = 1;
		gbc_jdp2.weighty = 3;
		formPanel.add(endDatePicker, gbc_jdp2);
		//Calendar calendar = datePicker.getMonthView().getCalendar();
		//calendar.setTime(new Date());
		//datePicker.getMonthView().setLowerBound(calendar.getTime());
		endDatePicker.setFormats(new DateFormat[] {format1, format2, format3, format4});
		
		GregorianCalendar c = new GregorianCalendar();
	    c.set(Calendar.HOUR_OF_DAY, 0);
	    c.set(Calendar.MINUTE, 0);
	    c.set(Calendar.SECOND, 0);
		endDatePicker.setDate(c.getTime());
	    startDate = c;
		startDatePicker.setDate(c.getTime());
		
		
		startDatePicker.addPropertyChangeListener(new PropertyChangeListener(){
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				listenerHelper();
				
				long diffDate = startDatePicker.getDate().getTime() - startDate.getTime().getTime();
				endDatePicker.setDate(new Date(endDatePicker.getDate().getTime() + diffDate));
				startDate.setTime(startDatePicker.getDate());
			}}
		);
		
		
		endDatePicker.addPropertyChangeListener(new PropertyChangeListener(){

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				checkStartEnd();
				listenerHelper();
			}}
				);
		
//		endDatePicker.getEditor().addKeyListener(new KeyListener(){
//
//			@Override
//			public void keyTyped(KeyEvent e) {
//			}
//
//			@Override
//			public void keyPressed(KeyEvent e) {
//				endDatePicker.getEditor().setBackground(Color.WHITE);
//				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
//					checkBadDate();
//				}
//			}
//
//			@Override
//			public void keyReleased(KeyEvent e) {
//				inputDate = endDatePicker.getEditor().getText().trim();
//				listenerHelper();
//				
//				// This next line checks for a blank date field, DO NOT REMOVE
//				if(nameTextField.getText().equals("") || endDatePicker.getEditor().getText().equals("")
//                        || nameTextField.getText().trim().length() == 0){
//                btnAddEvent.setEnabled(false);
//				}
//
//				//boolean orignValue = initFlag;
//				//initFlag = true;
//				//listenerHelper();
//				//initFlag = orignValue;
//			}
//
//		});
//		endDatePicker.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				endDatePicker.getEditor().setBackground(Color.WHITE);
//				inputDate = endDatePicker.getEditor().getText().trim();
//			}
//			
//		});
//		
//		endDatePicker.getEditor().addFocusListener(new FocusListener() {
//			@Override
//			public void focusGained(FocusEvent e) {
//				if(badDate) {
//					endDatePicker.getEditor().setBackground(Color.getHSBColor(3, 0.3f, 1f));
//					endDatePicker.getEditor().selectAll();
//					lblDateError.setVisible(true);
//					badDate = false;
//				}
//				/*
//				else if(badDate){
//					datePicker.getEditor().setText("The date is not valid");
//					datePicker.getEditor().setBackground(Color.red);
//					badDate = false;
//				}
//				*/
//				else{
//					//try {
//						SimpleDateFormat dt = new SimpleDateFormat("MM/dd/yyyy EEE"); 
//						endDatePicker.getEditor().setBackground(Color.WHITE);
//						endDatePicker.getEditor().setText(dt.format(endDatePicker.getDate()));
//						endDatePicker.getEditor().selectAll();
//						listenerHelper();
//					//}catch(NullPointerException ne) {
//					//	datePicker.getEditor().setText("The date is not valid");
//					//	datePicker.getEditor().setBackground(Color.red);
//					//}
//				}
//			}
//
//			@Override
//			public void focusLost(FocusEvent e) {
//				if(isBadInputDate()){
//					badDate = true;
//					endDatePicker.requestFocus();
//				}
//				/*
//				else{
//					Date date = null;
//					for(DateFormat formatter : datePicker.getFormats()) {
//						try {
//							date = formatter.parse(inputDate);
//						} catch (ParseException e1) {
//
//						}
//					}
//					if(date.compareTo(new Date()) < 0) {
//						badDate = true;
//						datePicker.requestFocus();
//					}
//				}
//				*/
//				else{
//					endDatePicker.getEditor().setBackground(Color.WHITE);
//					lblDateError.setVisible(false);
//				}
//				listenerHelper();
//			}
//		});
//		endDatePicker.addFocusListener(new FocusListener() {
//
//			@Override
//			public void focusGained(FocusEvent e) {
//				endDatePicker.getEditor().setBackground(Color.WHITE);
//				listenerHelper();
//			}
//
//			@Override
//			public void focusLost(FocusEvent e) {
//				endDatePicker.getEditor().setBackground(Color.WHITE);
//				listenerHelper();
//				
//			}
//
//			
//			
//			
//			
//		});
		

		
		//adds handler to set both start and end spinners to nearest 30 or 00
		addTimeRoundingEvent();
		//Sets time value of end and start spinners
		startTime = new GregorianCalendar();
		endTimeSpinner.setValue(startTime.getTime());
		startTimeSpinner.setValue(startTime.getTime());
		
		//Add Repeat Label
		lblRepeat = new JLabel("Repetition:");
		lblRepeat.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_lblRepeat = new GridBagConstraints();
		gbc_lblRepeat.anchor = GridBagConstraints.EAST;
		gbc_lblRepeat.fill = GridBagConstraints.VERTICAL;
		gbc_lblRepeat.insets = new Insets(0, 0, 5, 5);
		gbc_lblRepeat.gridx = 0;
		gbc_lblRepeat.gridy = 8;
		gbc_lblRepeat.weighty = 1;
		formPanel.add(lblRepeat, gbc_lblRepeat);
		
		//Add Repeat Checkbox
		repeatCheckBox = new JCheckBox("Repeats?");
		GridBagConstraints gbc_repeatCheckBox = new GridBagConstraints();
		gbc_repeatCheckBox.gridwidth = 1;
		gbc_repeatCheckBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_repeatCheckBox.insets = new Insets(0, 0, 5, 0);
		gbc_repeatCheckBox.gridx = 1;
		gbc_repeatCheckBox.gridy = 8;
		gbc_repeatCheckBox.weightx = 10;
		gbc_repeatCheckBox.weighty = 1;
		formPanel.add(repeatCheckBox, gbc_repeatCheckBox);
		repeatCheckBox.setSelected(false);
		repeatCheckBox.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				repeatTypeComboBox.setEnabled(repeatCheckBox.isSelected());
				repeatAmt.setEnabled(repeatCheckBox.isSelected());
			}
			
		});
		
		//Add Repeat type Label
		lblRepeatType = new JLabel("Repeat Type");
		lblRepeatType.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_lblRepeatType = new GridBagConstraints();
		gbc_lblRepeatType.anchor = GridBagConstraints.EAST;
		gbc_lblRepeatType.fill = GridBagConstraints.VERTICAL;
		gbc_lblRepeatType.insets = new Insets(0, 0, 5, 5);
		gbc_lblRepeatType.gridx = 2;
		gbc_lblRepeatType.gridy = 8;
		gbc_lblRepeatType.weighty = 1;
		formPanel.add(lblRepeatType, gbc_lblRepeatType);
		
		//Add Repeat ComboBox
		String[] repeatStrings = {"Daily", "Weekly", "Monthly"};
		repeatTypeComboBox = new JComboBox<String>(repeatStrings);
		repeatTypeComboBox.setSelectedIndex(0);
		GridBagConstraints gbc_repeatTypeComboBox = new GridBagConstraints();
		gbc_repeatTypeComboBox.gridwidth = 1;
		gbc_repeatTypeComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_repeatTypeComboBox.insets = new Insets(0, 0, 5, 0);
		gbc_repeatTypeComboBox.gridx = 3;
		gbc_repeatTypeComboBox.gridy = 8;
		gbc_repeatTypeComboBox.weightx = 10;
		gbc_repeatTypeComboBox.weighty = 1;
		formPanel.add(repeatTypeComboBox, gbc_repeatTypeComboBox);
		repeatTypeComboBox.setEnabled(false);//we only want this active when repeat checkbox is checked
		
		//Add Repetitions Label
		lblNumberRepetitions = new JLabel("# of Repetitions:");
		lblNumberRepetitions.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_lblNumberRepetitions = new GridBagConstraints();
		gbc_lblNumberRepetitions.anchor = GridBagConstraints.EAST;
		gbc_lblNumberRepetitions.fill = GridBagConstraints.VERTICAL;
		gbc_lblNumberRepetitions.insets = new Insets(0, 0, 5, 5);
		gbc_lblNumberRepetitions.gridx = 0;
		gbc_lblNumberRepetitions.gridy = 9;
		gbc_lblNumberRepetitions.weighty = 1;
		formPanel.add(lblNumberRepetitions, gbc_lblNumberRepetitions);
		
		//Add Repeat Text Field
		repeatAmt = new JTextField();
		GridBagConstraints gbc_repeatAmt = new GridBagConstraints();
		gbc_repeatAmt.gridwidth = 3;
		gbc_repeatAmt.fill = GridBagConstraints.HORIZONTAL;
		gbc_repeatAmt.insets = new Insets(0, 0, 5, 0);
		gbc_repeatAmt.gridx = 1;
		gbc_repeatAmt.gridy = 9;
		gbc_repeatAmt.weightx = 10;
		gbc_repeatAmt.weighty = 1;
		formPanel.add(repeatAmt, gbc_repeatAmt);
		repeatAmt.setEnabled(false);//we only want this active when repeat checkbox is checked
		
		buttonPanel = new JPanel(new BorderLayout(30,0));
		//Add Event button
		
		try {
			Image img = ImageIO.read(getClass().getResource("Save_Icon.png"));
			btnAddEvent = new JButton("Save Event", new ImageIcon(img));
		} catch (IOException ex) {}
		catch(IllegalArgumentException ex){
			btnCancel.setText("Save Event");
		}

		btnAddEvent.setCursor(new Cursor(Cursor.HAND_CURSOR)); // To change cursor as it moves over this button
		btnAddEvent.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				addEvent();
			}
			
			
		});
		
		
		
		btnAddEvent.setEnabled(false);
		
		
		
		GridBagConstraints gbc_btnPanel = new GridBagConstraints();
		gbc_btnPanel.gridwidth = 3;
		gbc_btnPanel.insets = new Insets(0, 0, 0, 5);
		gbc_btnPanel.anchor = GridBagConstraints.CENTER;
		gbc_btnPanel.gridx = 1;
		gbc_btnPanel.gridy = 10;
		
		//Add Cancel button

		try {
			Image img = ImageIO.read(getClass().getResource("Cancel_Icon.png"));
			btnCancel = new JButton("Cancel", new ImageIcon(img));
		} catch (IOException ex) {}
		catch(IllegalArgumentException ex){
			btnCancel.setText("Cancel");
		}
		
		btnCancel.setCursor(new Cursor(Cursor.HAND_CURSOR)); // To change cursor as it moves over this button
		btnCancel.addActionListener(new ActionListener() {
		
		
			@Override
			public void actionPerformed(ActionEvent e) {
				removeTab();
			}
			
		});
		
		buttonPanel.add(btnAddEvent, BorderLayout.WEST);
		buttonPanel.add(btnCancel, BorderLayout.CENTER);
	                    				// Set the horizontal gap
		formPanel.add(buttonPanel, gbc_btnPanel);
		
		this.initFlag = true;
	}

	/**
	 * Create a event tab in editing mode.
	 */
	public EventTab(Event event, CalendarData calData) {
		this();
		
		this.initFlag = false; //We need this to deal with the nested constructors
		
		editingEvent = event;
		this.mode = EditingMode.EDITING;
		
		this.nameTextField.setText(editingEvent.getName());
		this.descriptionTextArea.setText(editingEvent.getDescription());
		
		this.categoryComboBox.setSelectedItem(editingEvent.getCategoryID());
		
		
		if(!editingEvent.getIsPersonal())
			this.rdbtnTeam.setSelected(true);
		else
			this.rdbtnPersonal.setSelected(true);
		
		
		this.rdbtnTeam.setEnabled(false);
		this.rdbtnPersonal.setEnabled(false);
		

		this.startTimeSpinner.setValue(editingEvent.getStartTime().getTime());
		this.startDatePicker.setDate(editingEvent.getStartTime().getTime());
		this.endTimeSpinner.setValue(editingEvent.getEndTime().getTime());
		this.endDatePicker.setDate(editingEvent.getEndTime().getTime());

		//handle repetition fields
		if(event.getIsRepeating()){
			this.editingRepeatingEvent = calData.getRepeatingEvents().get(event.getID());
			this.repeatCheckBox.setSelected(true);
			this.repeatAmt.setText(Integer.toString(this.editingRepeatingEvent.getRepetitions()));
			this.repeatAmt.setEnabled(true);
			if (this.editingRepeatingEvent.getRepType() == RepeatType.MONTH){
				this.repeatTypeComboBox.setSelectedIndex(2);
			} else if (this.editingRepeatingEvent.getRepType() == RepeatType.WEEK){
				this.repeatTypeComboBox.setSelectedIndex(1);
			} else {
				this.repeatTypeComboBox.setSelectedIndex(0);
			}
			this.repeatTypeComboBox.setEnabled(true);
			//pull time from the Repeating event
			//otherwise the initial occurrence will get set to the double-clicked day
			this.startTimeSpinner.setValue(editingRepeatingEvent.getStartTime().getTime());
			this.startDatePicker.setDate(editingRepeatingEvent.getStartTime().getTime());
			this.endTimeSpinner.setValue(editingRepeatingEvent.getEndTime().getTime());
			this.endDatePicker.setDate(editingRepeatingEvent.getEndTime().getTime());
		}
		
		this.repeatCheckBox.setEnabled(false);//Don't want people changing this for now
											  // we might be able to enable it later
		
		// Add Delete Button
		try {
			Image img = ImageIO.read(getClass().getResource("Delete_Icon.png"));
			btnDelete = new JButton("Delete Event", new ImageIcon(img));
		} catch (IOException ex) {}
		catch(IllegalArgumentException ex){
			btnDelete.setText("Delete Event");
		}
		
		btnDelete.setCursor(new Cursor(Cursor.HAND_CURSOR)); // To change cursor as it moves over this button
		btnDelete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				deleteEvent();
			}
			
		});
		buttonPanel.add(btnDelete, BorderLayout.LINE_END);
	
		//Some edit specific listeners
		//These are here to avoid possible NullPointer exceptions while opening the tab 
		startTimeSpinner.addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent e) {
				listenerHelper();
				
			}
			
			
		});
		
		/*
		datePicker.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				listenerHelper();
				
			}
			
			
		});
		*/
		startDatePicker.getEditor().addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				listenerHelper();
				
			}
			
			
		});
		
		
		this.initFlag = true;

	}
	

	/**
	 * Close this event tab
	 */
	protected void removeTab() {
		GUIEventController.getInstance().removeEventTab(this, isTeamEvent);
	}


	
	
	/**
	 * Add an event handler to round the spinner minute value when not 0 or 30
	 */
	private void addTimeRoundingEvent() {
		startTimeSpinner.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				GregorianCalendar c = new GregorianCalendar();
				//get time value from spinner
				c.setTime((Date)startTimeSpinner.getValue());
				int minutesVal = c.get(Calendar.MINUTE);
				int hourVal = c.get(Calendar.HOUR);
				int newMinutesVal;
				int newHourVal = hourVal;
				
				//round value if not 0 or 30 mins
				if(minutesVal != 0 && minutesVal != 30)
				{	
					if (minutesVal < 15)
						newMinutesVal = 0;
					else if (minutesVal > 15 && minutesVal < 45)
						newMinutesVal = 30;
					else
					{
						newMinutesVal = 0;
						newHourVal += 1;
					}
					c.set(Calendar.MINUTE, newMinutesVal);
					c.set(Calendar.HOUR, newHourVal);
					//set spinner time value
					startSpinnerModel.setValue(c.getTime());
					
				}
				
				//increment end time appropriately to keep same event duration
				long timeDiff = c.getTime().getTime() - startTime.getTime().getTime();
				startTime.setTime(c.getTime());
				c.setTime((Date)endTimeSpinner.getValue());
				c.setTime(new Date(c.getTime().getTime() + timeDiff));
				endTimeSpinner.setValue(c.getTime());
			}
		});
		endTimeSpinner.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				GregorianCalendar c = new GregorianCalendar();
				//get time value from spinner
				c.setTime((Date)endTimeSpinner.getValue());
				int minutesVal = c.get(Calendar.MINUTE);
				int hourVal = c.get(Calendar.HOUR);
				int newMinutesVal;
				int newHourVal = hourVal;
				
				//round value if not 0 or 30 mins
				if(minutesVal != 0 && minutesVal != 30)
				{	
					if (minutesVal < 15)
						newMinutesVal = 0;
					else if (minutesVal > 15 && minutesVal < 45)
						newMinutesVal = 30;
					else
					{
						newMinutesVal = 0;
						newHourVal += 1;
					}
					c.set(Calendar.MINUTE, newMinutesVal);
					c.set(Calendar.HOUR, newHourVal);
					//set spinner time value
					endSpinnerModel.setValue(c.getTime());
					
				}
				checkStartEnd();
			}

			
		});
	}


	private void checkStartEnd() {
		// TODO Auto-generated method stub
		GregorianCalendar endingDate = new GregorianCalendar();
		endingDate.setTime(endDatePicker.getDate());
		endingDate.set(Calendar.HOUR_OF_DAY, 0);
		endingDate.set(Calendar.MINUTE, 0);
		endingDate.set(Calendar.SECOND, 0);
		endingDate.set(Calendar.MILLISECOND, 0);

		GregorianCalendar startingDate = new GregorianCalendar();
		startingDate.setTime(startDatePicker.getDate());
		startingDate.set(Calendar.HOUR_OF_DAY, 0);
		startingDate.set(Calendar.MINUTE, 0);
		startingDate.set(Calendar.SECOND, 0);
		startingDate.set(Calendar.MILLISECOND, 0);
		if(endingDate.getTime().before(startingDate.getTime()))
			endDatePicker.setDate(startDatePicker.getDate());
		System.out.println(endTimeSpinner.getValue());
		System.out.println(startTimeSpinner.getValue());

		if(((Date)endTimeSpinner.getValue()).before((Date)startTimeSpinner.getValue()))
		{
			if(!endingDate.getTime().after(startingDate.getTime()))
				endTimeSpinner.setValue((Date)startTimeSpinner.getValue());
		}
	}
	
	/**
	 * Adds new event with information contained in fields
	 */
	private void addEvent() {
		// TODO Auto-generated method stub


		if(nameTextField.getText().equals("") || startDatePicker.getDate() == null){
			return;
		}

		CalendarData calData;
		if (this.rdbtnPersonal.isSelected()){
			calData = CalendarDataModel.getInstance().getCalendarData(ConfigManager.getConfig().getProjectName() + "-" + ConfigManager.getConfig().getUserName()); 
			isTeamEvent = false;
		}
		else{
			calData = CalendarDataModel.getInstance().getCalendarData(ConfigManager.getConfig().getProjectName()); 
			isTeamEvent = true;
		}
		//		for(Event event: calData.getEvents().getEvents())
		//		{
		//			System.out.println("Event name: " + event.getName()+", id: "+ event.getId());
		//		}
		if (repeatCheckBox.isSelected()){
			RepeatingEvent newRepEvent;
			if(mode == EditingMode.ADDING)
			{
				newRepEvent = new RepeatingEvent();
			}
			else {
				newRepEvent = editingRepeatingEvent;
			}

			if(isTeamEvent){
				newRepEvent.setIsPersonal(false);
			}
			else{
				newRepEvent.setIsPersonal(true);
			}

			newRepEvent.setCategoryID(((Category)this.categoryComboBox.getSelectedItem()).getId());
			newRepEvent.setDescription(this.descriptionTextArea.getText());


			//Parse date and time info
			GregorianCalendar calStartDate = new GregorianCalendar();
			GregorianCalendar calStartTime = new GregorianCalendar();
			calStartDate.setTime(this.startDatePicker.getDate());
			calStartTime.setTime((Date)startTimeSpinner.getValue());
			calStartDate.set(Calendar.HOUR_OF_DAY, calStartTime.get(Calendar.HOUR_OF_DAY));
			calStartDate.set(Calendar.MINUTE, calStartTime.get(Calendar.MINUTE));

			GregorianCalendar calEndDate = new GregorianCalendar();
			GregorianCalendar calEndTime = new GregorianCalendar();
			calEndDate.setTime(this.endDatePicker.getDate());
			calEndTime.setTime((Date)endTimeSpinner.getValue());
			calEndDate.set(Calendar.HOUR_OF_DAY, calEndTime.get(Calendar.HOUR_OF_DAY));
			calEndDate.set(Calendar.MINUTE, calEndTime.get(Calendar.MINUTE));

			//set due date
			newRepEvent.setStartTime(calStartDate);
			newRepEvent.setEndTime(calEndDate);
			newRepEvent.setName(this.nameTextField.getText());
			
			//Set repeating fields
			if (repeatTypeComboBox.getSelectedIndex() == 2){
				newRepEvent.setRepType(RepeatType.MONTH);
			} else if (repeatTypeComboBox.getSelectedIndex() == 1){
				newRepEvent.setRepType(RepeatType.WEEK);
			} else {
				newRepEvent.setRepType(RepeatType.DAY);
			}
			
			newRepEvent.setRepetitions(Integer.parseInt(repeatAmt.getText()));


			if (mode == EditingMode.ADDING)
				calData.addRepeatingEvent(newRepEvent);
			else
				calData.getRepeatingEvents().update(newRepEvent);

			UpdateCalendarDataController.getInstance().updateCalendarData(calData);


			this.removeTab();

		} else {

			Event newEvent;
			if(mode == EditingMode.ADDING)
			{
				newEvent = new Event();
			}
			else
				newEvent = editingEvent;

			if(isTeamEvent){
				newEvent.setIsPersonal(false);
			}
			else{
				newEvent.setIsPersonal(true);
			}

			newEvent.setCategoryID(((Category)this.categoryComboBox.getSelectedItem()).getId());
			newEvent.setDescription(this.descriptionTextArea.getText());


			//Parse date and time info
			GregorianCalendar calStartDate = new GregorianCalendar();
			GregorianCalendar calStartTime = new GregorianCalendar();
			calStartDate.setTime(this.startDatePicker.getDate());
			calStartTime.setTime((Date)startTimeSpinner.getValue());
			calStartDate.set(Calendar.HOUR_OF_DAY, calStartTime.get(Calendar.HOUR_OF_DAY));
			calStartDate.set(Calendar.MINUTE, calStartTime.get(Calendar.MINUTE));

			GregorianCalendar calEndDate = new GregorianCalendar();
			GregorianCalendar calEndTime = new GregorianCalendar();
			calEndDate.setTime(this.endDatePicker.getDate());
			calEndTime.setTime((Date)endTimeSpinner.getValue());
			calEndDate.set(Calendar.HOUR_OF_DAY, calEndTime.get(Calendar.HOUR_OF_DAY));
			calEndDate.set(Calendar.MINUTE, calEndTime.get(Calendar.MINUTE));

			//set due date
			newEvent.setStartTime(calStartDate);
			newEvent.setEndTime(calEndDate);
			newEvent.setName(this.nameTextField.getText());

			if (mode == EditingMode.ADDING)
				calData.addEvent(newEvent);
			else
				calData.getEvents().update(newEvent);

			UpdateCalendarDataController.getInstance().updateCalendarData(calData);


			this.removeTab();
		}
	}
	

	protected void deleteEvent() {
		// TODO Auto-generated method stub
		CalendarData calData;
	if (this.rdbtnPersonal.isSelected()){
			calData = CalendarDataModel.getInstance().getCalendarData(ConfigManager.getConfig().getProjectName() + "-" + ConfigManager.getConfig().getUserName()); 
			isTeamEvent = false;
	}
	else{
		calData = CalendarDataModel.getInstance().getCalendarData(ConfigManager.getConfig().getProjectName()); 
		isTeamEvent = true;
	}
		calData.getEvents().removeEvent(editingEvent.getID());
		UpdateCalendarDataController.getInstance().updateCalendarData(calData);
		removeTab();
	}

	/**
	 * Controls the enable state of the save button
	 */
	private void listenerHelper(){
		
		if (initFlag){
			if(nameTextField.getText().equals("") || startDatePicker.getDate() == null || //data validation
					endDatePicker.getDate() == null || nameTextField.getText().trim().length() == 0){
				btnAddEvent.setEnabled(false);
			} else {
				if (mode == EditingMode.EDITING){
					//get some date data
					Calendar calStartDate = new GregorianCalendar();
					Calendar calEndDate = new GregorianCalendar();
					Calendar calTime = new GregorianCalendar();
					calStartDate.setTime(this.startDatePicker.getDate());
					calTime.setTime((Date)startTimeSpinner.getValue());
					calStartDate.set(Calendar.HOUR_OF_DAY, calTime.get(Calendar.HOUR_OF_DAY));
					calStartDate.set(Calendar.MINUTE, calTime.get(Calendar.MINUTE));
					
					calEndDate.setTime(this.endDatePicker.getDate());
					calTime.setTime((Date)endTimeSpinner.getValue());
					calEndDate.set(Calendar.HOUR_OF_DAY, calTime.get(Calendar.HOUR_OF_DAY));
					calEndDate.set(Calendar.MINUTE, calTime.get(Calendar.MINUTE));
					
					//make sure something changed
					if (this.nameTextField.getText().equals(editingEvent.getName()) 
							&& this.descriptionTextArea.getText().equals(editingEvent.getDescription())){
							//&& ((Category)this.categoryComboBox.getSelectedItem()).getId() == editingEvent.getCategoryId()
							//&& calStartDate.getTime().equals(editingEvent.getStartDate().getTime())
							//&& calEndDate.getTime().equals(editingEvent.getEndDate().getTime()))

							
						btnAddEvent.setEnabled(false);
						return;
					}
				}
				btnAddEvent.setEnabled(true);
			}

		}
	}
	
	/**
	 * check if the user enters a bad date
	 */
	private void checkBadDate() {
		boolean isABadDate = isABadDate();
		if(isABadDate) {
			startDatePicker.getEditor().setText(">> " + inputDate + " <<" + "is not a valid date format(MM/dd/YYYY)." );
			startDatePicker.getEditor().setBackground(Color.red);
			startDatePicker.getEditor().selectAll();
		}
		else{
			//Date currentDate = new Date();
			//if(tmpDate.compareTo(currentDate) >= 0) {
				String showDate = startDatePicker.getFormats()[0].format(tmpDate);
				startDatePicker.getEditor().setText(showDate);
			//}
			//else{
			//	datePicker.getEditor().setText("The date is not valid");
			//	datePicker.getEditor().setBackground(Color.red);
			//}
		}
	}
	
	private boolean isABadDate() {
		boolean result;
		startDatePicker.getEditor().setBackground(Color.WHITE);
		tmpDate = null;
		inputDate = startDatePicker.getEditor().getText().trim();
		for(DateFormat formatter : startDatePicker.getFormats()) {
			try{
				formatter.setLenient(false);
				tmpDate = formatter.parse(inputDate);
			}catch(ParseException pe){
				//try next formatter
			}
		}
		if(tmpDate == null) {
			result = true;
		}
		else{
			result = false;
		}
		return result;
	}
	
	private boolean isBadInputDate() {
		boolean result;
		Date date = null;
		for(DateFormat formatter : startDatePicker.getFormats()) {
			try{
				formatter.setLenient(false);
				date = formatter.parse(inputDate);
			}catch(ParseException pe){
				//try next formatter
			}
			catch(NullPointerException ne){
				result = false;
			}
		}
		if(date == null) {
			result = true;
		}
		else{
			result = false;
		}
		return result;
	}

}



