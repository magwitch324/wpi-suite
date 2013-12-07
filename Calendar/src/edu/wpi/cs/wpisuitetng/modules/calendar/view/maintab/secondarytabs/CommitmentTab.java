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
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.Category;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.Commitment.Status;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.CalendarData;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.CalendarDataModel;
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
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Insets;

import javax.swing.event.ChangeEvent;
import javax.swing.JButton;

import java.awt.Color;

import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import java.awt.FlowLayout;
import javax.swing.SpinnerModel;
import java.awt.GridLayout;
import java.awt.Font;

public class CommitmentTab extends JPanel {
	private JTextField nameTextField;
	private GridBagConstraints gbc_nameTextField;
	private JSpinner hourSpinner;
	private JSpinner minuteSpinner;
	private JSpinner AMPMSpinner;
	private boolean isTeamComm;
	private JButton btnAddCommitment;
	private JComboBox<Category> categoryComboBox;
	private JTextArea descriptionTextArea;
	private JXDatePicker datePicker;
	private JScrollPane descPane;
	private JPanel panel;
	private JSpinner.DateEditor hourEditor;
	private JSpinner.DateEditor minuteEditor;
	private JSpinner.DateEditor AMPMEditor;
	private JPanel panel_1;
	private JRadioButton rdbtnPersonal;
	private JRadioButton rdbtnTeam;
	private JLabel lblType;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JButton btnCancel;
	private Date tmpDate = new Date(); //user for convert the date to default format
	private boolean badDate;
	private boolean badTime;
	private Commitment editingCommitment;
	private EditingMode mode = EditingMode.ADDING;
	private JButton btnDelete;
	private JComboBox statusComboBox;
	private JPanel buttonPanel;
	private JPanel formPanel;
	private JLabel statusLabel;
	private boolean initFlag; //to keep things from running before we fully intialize
	private JPanel spinnerPanel;
	
	
	
	
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
	public CommitmentTab() {
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
		constraints.weighty = 1;
		constraints.fill = GridBagConstraints.BOTH;
		add(spacePanel1, constraints);
		add(formPanel, constraints);
		add(spacePanel2, constraints);
		
		// form uses GridBagLayout w/ two columns
		GridBagLayout gbl = new GridBagLayout();
		gbl.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		gbl.columnWeights = new double[]{0.0, 1.0};
		gbl.rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl.columnWidths = new int[] {0, 0};
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
		gbc_nameTextField.insets = new Insets(0, 0, 5, 0);
		gbc_nameTextField.fill = GridBagConstraints.HORIZONTAL;
        gbc_nameTextField.weightx = 10;
        gbc_nameTextField.weighty = 1;
        gbc_nameTextField.gridx = 1;
        gbc_nameTextField.gridy = 0;
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
		gbc_descriptionTextField.fill = GridBagConstraints.BOTH;
		gbc_descriptionTextField.insets = new Insets(0, 0, 5, 0);
        gbc_descriptionTextField.weightx = 10;
        gbc_descriptionTextField.weighty = 5;
        gbc_descriptionTextField.gridx = 1;
        gbc_descriptionTextField.gridy = 1;
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
		gbc_categoryComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_categoryComboBox.insets = new Insets(0, 0, 5, 0);
		gbc_categoryComboBox.gridx = 1;
		gbc_categoryComboBox.gridy = 2;
		gbc_categoryComboBox.weightx = 10;
		gbc_categoryComboBox.weighty = 1;
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

		
		spinnerPanel = new JPanel();
		GridBagConstraints gbc_spinnerPanel = new GridBagConstraints();
		gbc_spinnerPanel.insets = new Insets(0, 0, 5, 0);
		gbc_spinnerPanel.fill = GridBagConstraints.BOTH;
		gbc_spinnerPanel.gridx = 1;
		gbc_spinnerPanel.gridy = 4;
		formPanel.add(spinnerPanel, gbc_spinnerPanel);
		spinnerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		//Time spinner, half hour resolution
		hourSpinner = new JSpinner( new SpinnerDateModelHour());
		hourSpinner.setFont(new Font("Tahoma", Font.PLAIN, 18));
		spinnerPanel.add(hourSpinner);
		hourEditor = new JSpinner.DateEditor(hourSpinner, "hh");
		hourSpinner.setEditor(hourEditor);
		
		minuteSpinner = new JSpinner( new SpinnerDateModelHalfHour());
		minuteSpinner.setFont(new Font("Tahoma", Font.PLAIN, 18));
		spinnerPanel.add(minuteSpinner);
		minuteEditor = new JSpinner.DateEditor(minuteSpinner, "mm");
		minuteSpinner.setEditor(minuteEditor);
		
		AMPMSpinner = new JSpinner(new SpinnerDateModelAMPM());
		AMPMSpinner.setFont(new Font("Tahoma", Font.PLAIN, 18));
		spinnerPanel.add(AMPMSpinner);
		AMPMEditor = new JSpinner.DateEditor(AMPMSpinner, "a");
		AMPMSpinner.setEditor(AMPMEditor);
		
		//Rounds the spinner to 30 or 00
		addTimeRoundingEvent();
		
		hourSpinner.setValue(new GregorianCalendar().getTime());
		minuteSpinner.setValue(new GregorianCalendar().getTime());
		AMPMSpinner.setValue(new GregorianCalendar().getTime());
		
		//Time label
		JLabel lblTime = new JLabel("Time:");
		lblTime.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_lblTime = new GridBagConstraints();
		gbc_lblTime.anchor = GridBagConstraints.EAST;
		gbc_lblTime.fill = GridBagConstraints.VERTICAL;
		gbc_lblTime.insets = new Insets(0, 0, 5, 5);
		gbc_lblTime.gridx = 0;
		gbc_lblTime.gridy = 4;
		gbc_lblTime.weightx = 1;
		gbc_lblTime.weighty = 1;
		formPanel.add(lblTime, gbc_lblTime);

		
		//Invalid Date label
		final JLabel lblDateError = new JLabel("<html><font color='red'>Please enter a valid date (MM/DD/YYYY).</font></html>");
		lblDateError.setVisible(false);
		lblDateError.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblDateError = new GridBagConstraints();
		gbc_lblDateError.insets = new Insets(0, 0, 5, 0);
		gbc_lblDateError.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblDateError.gridx = 1;
		gbc_lblDateError.gridy = 8;
		gbc_lblDateError.weightx = 1;
		gbc_lblDateError.weighty = 1;		
		formPanel.add(lblDateError, gbc_lblDateError);
		
		//Date label
		JLabel lblDate_1 = new JLabel("Date:");
		lblDate_1.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_lblDate_1 = new GridBagConstraints();
		gbc_lblDate_1.fill = GridBagConstraints.VERTICAL;
		gbc_lblDate_1.anchor = GridBagConstraints.EAST;
		gbc_lblDate_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblDate_1.gridx = 0;
		gbc_lblDate_1.gridy = 7;
		gbc_lblDate_1.weightx = 1;
		gbc_lblDate_1.weighty = 1;
		formPanel.add(lblDate_1, gbc_lblDate_1);
		
		//DatePicker box
		datePicker = new JXDatePicker();
		datePicker.getEditor().setFont(new Font("Tahoma", Font.PLAIN, 13));
		GridBagConstraints gbc_jdp = new GridBagConstraints();
		gbc_jdp.insets = new Insets(0, 0, 5, 0);
		gbc_jdp.fill = GridBagConstraints.HORIZONTAL;
		gbc_jdp.gridx = 1;
		gbc_jdp.gridy = 7;
		gbc_jdp.weightx = 1;
		gbc_jdp.weighty = 3;
		formPanel.add(datePicker, gbc_jdp);

		SimpleDateFormat format1 = new SimpleDateFormat( "MM/dd/yyyy EEE" );
		SimpleDateFormat format2 = new SimpleDateFormat( "MM/dd/yyyy" );
		SimpleDateFormat format3 = new SimpleDateFormat( "MM.dd.yyyy" );
		SimpleDateFormat format4 = new SimpleDateFormat( "MM.dd.yyyy EEE" );
		datePicker.setFormats(new DateFormat[] {format1, format2, format3, format4});
		datePicker.addKeyListener(new KeyListener() {

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
		
		datePicker.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				datePicker.getEditor().setBackground(Color.WHITE);
			}
			
		});
		
		datePicker.getEditor().addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				if(badDate) {
					datePicker.getEditor().setBackground(Color.getHSBColor(3, 0.3f, 1f));
					datePicker.getEditor().selectAll();
					lblDateError.setVisible(true);
					badDate = false;
				}

				else{
						SimpleDateFormat dt = new SimpleDateFormat("MM/dd/yyyy EEE"); 
						datePicker.getEditor().setBackground(Color.WHITE);
						datePicker.getEditor().setText(dt.format(datePicker.getDate()));
						datePicker.getEditor().selectAll();
						listenerHelper();
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				
				if(isBadInputDate()){
					badDate = true;
					datePicker.requestFocus();
				}
				else{
					datePicker.getEditor().setBackground(Color.WHITE);
					lblDateError.setVisible(false);
				}
				listenerHelper();
			}
		});
		datePicker.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
				datePicker.getEditor().setBackground(Color.WHITE);
				listenerHelper();
			}

			@Override
			public void focusLost(FocusEvent e) {
				datePicker.getEditor().setBackground(Color.WHITE);
				listenerHelper();
				
			}

			
			
			
			
		});
		

		
		GregorianCalendar c = new GregorianCalendar();
	    c.set(Calendar.HOUR_OF_DAY, 0);
	    c.set(Calendar.MINUTE, 0);
	    c.set(Calendar.SECOND, 0);
		datePicker.setDate(c.getTime());
		
		
		buttonPanel = new JPanel(new BorderLayout(30,0));
		//Add Commitment button
		
		try {
			Image img = ImageIO.read(getClass().getResource("Save_Icon.png"));
			btnAddCommitment = new JButton("Save Commitment", new ImageIcon(img));
		} catch (IOException ex) {}
		catch(IllegalArgumentException ex){
			btnCancel.setText("Save Commitment");
		}

		btnAddCommitment.setCursor(new Cursor(Cursor.HAND_CURSOR)); // To change cursor as it moves over this button
		btnAddCommitment.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				addCommitment();
			}
			
			
		});
		
		datePicker.addPropertyChangeListener(new PropertyChangeListener(){

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				listenerHelper();
			}}
				);
		
		datePicker.getEditor().addKeyListener(new KeyListener(){

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				listenerHelper();
				
				// This next line checks for a blank date field, DO NOT REMOVE
				if(nameTextField.getText().equals("") || datePicker.getEditor().getText().equals("")
                        || nameTextField.getText().trim().length() == 0){
                btnAddCommitment.setEnabled(false);
				}

				//boolean orignValue = initFlag;
				//initFlag = true;
				//listenerHelper();
				//initFlag = orignValue;
			}

		});
		
		btnAddCommitment.setEnabled(false);
		
		
		
		GridBagConstraints gbc_btnPanel = new GridBagConstraints();
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
		
		buttonPanel.add(btnAddCommitment, BorderLayout.WEST);
		buttonPanel.add(btnCancel, BorderLayout.CENTER);
	                    				// Set the horizontal gap
		formPanel.add(buttonPanel, gbc_btnPanel);
		
		String[] statusStrings = {"New", "In Progress", "Completed"};
		statusComboBox = new JComboBox(statusStrings);
		
		
		statusComboBox.setSelectedIndex(0);
		GridBagConstraints gbc_statusComboBox = new GridBagConstraints();
		gbc_statusComboBox.insets = new Insets(0, 0, 5, 0);
		gbc_statusComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_statusComboBox.gridx = 1;		
		gbc_statusComboBox.gridy = 9;
		gbc_statusComboBox.weightx = 1;
		gbc_statusComboBox.weighty = 3;

		formPanel.add(statusComboBox,gbc_statusComboBox);
		
		statusLabel = new JLabel("Status:");
		GridBagConstraints gbc_statusLabel = new GridBagConstraints();
		gbc_statusLabel.insets = new Insets(0, 0, 5, 5);
		gbc_statusLabel.fill = GridBagConstraints.VERTICAL;
		gbc_statusLabel.anchor = GridBagConstraints.EAST;
		gbc_statusLabel.gridx = 0;
		gbc_statusLabel.gridy = 9;
		gbc_statusLabel.weightx = 1;
		gbc_statusLabel.weighty = 3;

		formPanel.add(statusLabel,gbc_statusLabel);
		
		this.initFlag = true;
	}

	/**
	 * Create a commitment tab in editing mode.
	 */
	public CommitmentTab(Commitment commToEdit) {
		this();
		
		this.initFlag = false; //We need this to deal with the nested constructors
		
		editingCommitment = commToEdit;
		this.mode = EditingMode.EDITING;
		
		this.nameTextField.setText(editingCommitment.getName());
		this.descriptionTextArea.setText(editingCommitment.getDescription());
		this.categoryComboBox.setSelectedItem(editingCommitment.getCategoryId());
		
		if(!editingCommitment.getIsPersonal())
			this.rdbtnTeam.setSelected(true);
		else
			this.rdbtnPersonal.setSelected(true);
		
		this.rdbtnTeam.setEnabled(false);
		this.rdbtnPersonal.setEnabled(false);
		

		this.hourSpinner.setValue(editingCommitment.getDueDate().getTime());
		this.minuteSpinner.setValue(editingCommitment.getDueDate().getTime());
		this.AMPMSpinner.setValue(editingCommitment.getDueDate().getTime());
		this.datePicker.setDate(editingCommitment.getDueDate().getTime());

		
		statusComboBox.setSelectedIndex(commToEdit.getStatus().id);
		
		// Add Delete Button
		try {
			Image img = ImageIO.read(getClass().getResource("Delete_Icon.png"));
			btnDelete = new JButton("Delete Commitment", new ImageIcon(img));
		} catch (IOException ex) {}
		catch(IllegalArgumentException ex){
			btnDelete.setText("Delete Commitment");
		}
		
		btnDelete.setCursor(new Cursor(Cursor.HAND_CURSOR)); // To change cursor as it moves over this button
		btnDelete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				deleteCommitment();
			}
			
		});
		buttonPanel.add(btnDelete, BorderLayout.LINE_END);
	
		//Some edit specific listeners
		//These are here to avoid possible NullPointer exceptions while opening the tab 
		hourSpinner.addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent e) {
				listenerHelper();
				
			}
			
			
		});

		//Some edit specific listeners
		//These are here to avoid possible NullPointer exceptions while opening the tab 
		minuteSpinner.addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent e) {
				listenerHelper();
				
			}
			
			
		});
		//Some edit specific listeners
		//These are here to avoid possible NullPointer exceptions while opening the tab 
		AMPMSpinner.addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent e) {
				listenerHelper();
				
			}
			
			
		});
		datePicker.getEditor().addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				listenerHelper();
				
			}
			
			
		});
		
		statusComboBox.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				listenerHelper();
				
			}
			
			
		});
		
		this.initFlag = true;

	}
	

	/**
	 * Close this commitment tab
	 */
	protected void removeTab() {
		GUIEventController.getInstance().removeCommTab(this, isTeamComm);
	}


	
	
	/**
	 * Add an event handler to round the spinner minute value when not 0 or 30
	 */
	private void addTimeRoundingEvent() {
		minuteSpinner.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				GregorianCalendar c = new GregorianCalendar();
				//get time value from spinner
				c.setTime((Date)minuteSpinner.getValue());
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
					minuteSpinner.getModel().setValue(c.getTime());
					
				}
			}
		});
	}


	/**
	 * Adds new commitment with information contained in fields
	 */
	private void addCommitment() {
		// TODO Auto-generated method stub

		
		if(nameTextField.getText().equals("") || datePicker.getDate() == null){
			return;
		}

		CalendarData calData;
		if (this.rdbtnPersonal.isSelected()){
			calData = CalendarDataModel.getInstance().getCalendarData(ConfigManager.getConfig().getProjectName() + "-" + ConfigManager.getConfig().getUserName()); 
			isTeamComm = false;
		}
		else{
			calData = CalendarDataModel.getInstance().getCalendarData(ConfigManager.getConfig().getProjectName()); 
			isTeamComm = true;
		}
		for(Commitment comm: calData.getCommitments().getCommitments())
		{
			System.out.println("Commitment name: " + comm.getName()+", id: "+ comm.getId());
		}
		Commitment newComm;
		if(mode == EditingMode.ADDING)
		{
			newComm = new Commitment();
		}
		else
			newComm = editingCommitment;
		
		if(isTeamComm){
			newComm.setIsPersonal(false);
		}
		else{
			newComm.setIsPersonal(true);
		}
		
		newComm.setCategoryId(((Category)this.categoryComboBox.getSelectedItem()).getId());
		newComm.setDescription(this.descriptionTextArea.getText());
		

		newComm.setStatus(Status.getStatusValue(statusComboBox.getSelectedIndex()));
		
		
		//Parse date and time info
		GregorianCalendar calDate = new GregorianCalendar();
		GregorianCalendar calHour = new GregorianCalendar();
		GregorianCalendar calMinute = new GregorianCalendar();
		GregorianCalendar calAMPM = new GregorianCalendar();
		
		calDate.setTime(this.datePicker.getDate());
		calHour.setTime((Date)hourSpinner.getValue());
		calMinute.setTime((Date)minuteSpinner.getValue());
		calAMPM.setTime((Date)AMPMSpinner.getValue());
		
		calDate.set(Calendar.HOUR, calHour.get(Calendar.HOUR));
		calDate.set(Calendar.MINUTE, calMinute.get(Calendar.MINUTE));
		calDate.set(Calendar.AM_PM, calAMPM.get(Calendar.AM_PM));
		
		//System.out.println("AMPM is " + calHour.get(Calendar.AM_PM));
		//System.out.println("24h is " + calHour.get(Calendar.HOUR));
		
		//set due date
		newComm.setDueDate(calDate);
		newComm.setName(this.nameTextField.getText());
		
		if (mode == EditingMode.ADDING)
			calData.addCommitment(newComm);
		else
			calData.getCommitments().update(newComm);

		UpdateCalendarDataController.getInstance().updateCalendarData(calData);

 
		this.removeTab();

	}
	

	protected void deleteCommitment() {
		// TODO Auto-generated method stub
		CalendarData calData;
	if (this.rdbtnPersonal.isSelected()){
			calData = CalendarDataModel.getInstance().getCalendarData(ConfigManager.getConfig().getProjectName() + "-" + ConfigManager.getConfig().getUserName()); 
			isTeamComm = false;
	}
	else{
		calData = CalendarDataModel.getInstance().getCalendarData(ConfigManager.getConfig().getProjectName()); 
		isTeamComm = true;
	}
		calData.getCommitments().removeCommmitment(editingCommitment.getId());
		UpdateCalendarDataController.getInstance().updateCalendarData(calData);
		removeTab();
	}

	/**
	 * Controls the enable state of the save button
	 */
	private void listenerHelper(){
		
		if (initFlag){
			if(nameTextField.getText().equals("") || datePicker.getDate() == null || //data validation
					nameTextField.getText().trim().length() == 0){
				btnAddCommitment.setEnabled(false);
			} else {
				if (mode == EditingMode.EDITING){
					//get some date data
					//Parse date and time info
					GregorianCalendar calDate = new GregorianCalendar();
					GregorianCalendar calHour = new GregorianCalendar();
					GregorianCalendar calMinute = new GregorianCalendar();
					GregorianCalendar calAMPM = new GregorianCalendar();
					
					calDate.setTime(this.datePicker.getDate());
					calHour.setTime((Date)hourSpinner.getValue());
					calMinute.setTime((Date)minuteSpinner.getValue());
					calAMPM.setTime((Date)AMPMSpinner.getValue());
					
					calDate.set(Calendar.HOUR, calHour.get(Calendar.HOUR));
					calDate.set(Calendar.MINUTE, calMinute.get(Calendar.MINUTE));
					calDate.set(Calendar.AM_PM, calAMPM.get(Calendar.AM_PM));
					//System.out.println("AMPM is " + calAMPM.get(Calendar.AM_PM));
					//System.out.println("Hour of day is " + calHour.get(Calendar.HOUR_OF_DAY));
					//System.out.println("Current commit hour is " + editingCommitment.getDueDate().get(Calendar.HOUR_OF_DAY));
					//System.out.println("Time in milli is " + calDate.getTimeInMillis());
					//System.out.println("Commit time in milli is " + editingCommitment.getDueDate().getTimeInMillis());
					
					//make sure something changed
					if (this.nameTextField.getText().equals(editingCommitment.getName()) 
							&& this.descriptionTextArea.getText().equals(editingCommitment.getDescription())
							&& ((Category)this.categoryComboBox.getSelectedItem()).getId() == editingCommitment.getCategoryId()
							&& Status.getStatusValue(statusComboBox.getSelectedIndex()).equals(editingCommitment.getStatus())
							&& calDate.getTime().equals(editingCommitment.getDueDate().getTime())
							){
						btnAddCommitment.setEnabled(false);
						return;
					}
				}
				btnAddCommitment.setEnabled(true);
			}

		}
	}
	
	private boolean isBadInputDate() {
		boolean result;
		Date date = null;
		for(DateFormat formatter : datePicker.getFormats()) {
			try{
				formatter.setLenient(false);
				date = formatter.parse(datePicker.getEditor().getText().trim());
			}catch(ParseException pe){
				//try next formatter
			}
			catch(NullPointerException ne){
				result = false;
			}
			
			if(date != null) {
				break;
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



