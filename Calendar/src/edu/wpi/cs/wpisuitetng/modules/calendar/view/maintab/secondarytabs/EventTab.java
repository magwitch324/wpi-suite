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
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner.DateEditor;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeListener;

import org.jdesktop.swingx.JXDatePicker;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.calendar.CalendarStandard;
import edu.wpi.cs.wpisuitetng.modules.calendar.controller.UpdateCalendarDataController;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.CalendarData;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.CalendarDataModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.Category;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.Event;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.RepeatingEvent;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.Status;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.RepeatingEvent.RepeatType;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.GUIEventController;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.maintab.secondarytabs.CommitmentTab.EditingMode;

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
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
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
	private GregorianCalendar oldStartTime;
	private JTextField nameTextField;
	private GridBagConstraints gbc_nameTextField;
	private boolean isTeamEvent;
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
	private String inputDate = (new SimpleDateFormat("MM/dd/yyyy").format(tmpDate)); //the date user input
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
	private JScrollPane descriptionScrollPane;
	private JPanel startSpinnerPanel;
	private JSpinner startHourSpinner;
	private DateEditor startHourEditor;
	private JSpinner startMinuteSpinner;
	private DateEditor startMinuteEditor;
	private JSpinner startAMPMSpinner;
	private DateEditor startAMPMEditor;
	private DateEditor endAMPMEditor;
	private JSpinner endAMPMSpinner;
	private DateEditor endMinuteEditor;
	private Container endSpinnerPanel;
	private JSpinner endMinuteSpinner;
	private DateEditor endHourEditor;
	private JSpinner endHourSpinner;
	private JLabel lblTimeError;
	private JLabel lblDateError;
	private JLabel lblDateError2;
	private JLabel lblTimeError2;
	private int startTempHour = 1;
	private int startTempMin = 1;
	private int endTempMin = 1;
	private int endTempHour = 1;
	private int openedFrom;
	
	
	
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
	public EventTab(int openedFrom) {
		this.openedFrom = openedFrom;
		initFlag = false;
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		setLayout(gridBagLayout);
		JPanel spacePanel1 = new JPanel();
		JPanel spacePanel2 = new JPanel();
		formPanel = new JPanel();
		formPanel.setBackground(Color.WHITE);
		formPanel.setPreferredSize(new Dimension(500,600));
		formPanel.setMinimumSize(new Dimension(500, 600));
		spacePanel1.setMinimumSize(formPanel.getSize());
		spacePanel1.setBackground(Color.WHITE);
		spacePanel2.setMinimumSize(formPanel.getSize());
		spacePanel2.setBackground(Color.WHITE);
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
		nameTextField.setBackground(CalendarStandard.CalendarYellow);
		nameTextField.setBorder(BorderFactory.createLineBorder(Color.GRAY));
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
		descriptionTextArea.setBackground(CalendarStandard.CalendarYellow);
		GridBagConstraints gbc_descriptionTextField = new GridBagConstraints();
		gbc_descriptionTextField.gridwidth = 3;
		gbc_descriptionTextField.fill = GridBagConstraints.BOTH;
		gbc_descriptionTextField.insets = new Insets(0, 0, 5, 0);
        gbc_descriptionTextField.weightx = 10;
        gbc_descriptionTextField.weighty = 5;
        gbc_descriptionTextField.gridx = 1;
        gbc_descriptionTextField.gridy = 1;
        gbc.gridwidth = 3;
        descriptionScrollPane = new JScrollPane(descriptionTextArea);
		formPanel.add(descriptionScrollPane, gbc_descriptionTextField);
		descriptionScrollPane.setMaximumSize(new Dimension(10000000,10));
		descriptionScrollPane.getViewport().setMaximumSize(new Dimension(10000000,10));
		
		
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
		categoryComboBox.setBackground(CalendarStandard.CalendarYellow);
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
		
		
		lblType = new JLabel("Type:");
		lblType.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_lblType = new GridBagConstraints();
		gbc_lblType.anchor = GridBagConstraints.EAST;
		gbc_lblType.insets = new Insets(0, 0, 5, 5);
		gbc_lblType.gridx = 0;
		gbc_lblType.gridy = 3;
		formPanel.add(lblType, gbc_lblType);
		
		panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.gridwidth = 3;
		gbc_panel_1.insets = new Insets(0, 0, 5, 0);
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 1;
		gbc_panel_1.gridy = 3;
		formPanel.add(panel_1, gbc_panel_1);
		
		rdbtnPersonal = new JRadioButton("Personal");
		rdbtnPersonal.setBackground(Color.WHITE);
		buttonGroup.add(rdbtnPersonal);
		panel_1.add(rdbtnPersonal);
		
		rdbtnTeam = new JRadioButton("Team");
		rdbtnTeam.setBackground(Color.WHITE);
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
//		startTimeSpinner = new JSpinner( new SpinnerDateModelMinute());
//		startTimeSpinner.setModel(startSpinnerModel);
//		startTimeEditor = new JSpinner.DateEditor(startTimeSpinner, "hh:mm a");
//		startTimeSpinner.setEditor(startTimeEditor);
		// Create time spinner panel.
		startSpinnerPanel = new JPanel();
		startSpinnerPanel.setBackground(Color.WHITE);
		
		startSpinnerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
				
		
		// Create time spinners, hour, minute, and AM_PM
		startHourSpinner = new JSpinner( new SpinnerDateModelHour());
		startHourSpinner.setFont(new Font("Tahoma", Font.PLAIN, 18));
		startSpinnerPanel.add(startHourSpinner);
		startHourEditor = new JSpinner.DateEditor(startHourSpinner, "hh");
		startHourSpinner.setEditor(startHourEditor);
		startHourEditor.getTextField().setFocusLostBehavior(JFormattedTextField.PERSIST);

		JLabel colon = new JLabel(":");
		startSpinnerPanel.add(colon);
		
		startMinuteSpinner = new JSpinner( new SpinnerDateModelMinute());
		startMinuteSpinner.setFont(new Font("Tahoma", Font.PLAIN, 18));
		startSpinnerPanel.add(startMinuteSpinner);
		startMinuteEditor = new JSpinner.DateEditor(startMinuteSpinner, "mm");
		startMinuteSpinner.setEditor(startMinuteEditor);
		startMinuteEditor.getTextField().setFocusLostBehavior(JFormattedTextField.PERSIST);

		startAMPMSpinner = new JSpinner(new SpinnerDateModelAMPM());
		startAMPMSpinner.setFont(new Font("Tahoma", Font.PLAIN, 18));
		startSpinnerPanel.add(startAMPMSpinner);
		startAMPMEditor = new JSpinner.DateEditor(startAMPMSpinner, "a");
		startAMPMSpinner.setEditor(startAMPMEditor);
		startAMPMEditor.getTextField().setFocusLostBehavior(JFormattedTextField.PERSIST);
		GridBagConstraints gbc_startspinner = new GridBagConstraints();
		gbc_startspinner.fill = GridBagConstraints.HORIZONTAL;
		gbc_startspinner.insets = new Insets(0, 0, 5, 0);
		gbc_startspinner.gridx = 3;
		gbc_startspinner.gridy = 4;
		gbc_startspinner.weightx = 1;
		gbc_startspinner.weighty = 3;
		formPanel.add(startSpinnerPanel, gbc_startspinner);
		
		
	
		
		//Invalid Time label
		lblTimeError = new JLabel(" ");
		lblTimeError.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblTimeError = new GridBagConstraints();
		gbc_lblTimeError.insets = new Insets(0, 0, 5, 0);
		gbc_lblTimeError.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblTimeError.gridx = 3;
		gbc_lblTimeError.gridy = 5;
		gbc_lblTimeError.weightx = 1;
		gbc_lblTimeError.weighty = 1;		
		formPanel.add(lblTimeError, gbc_lblTimeError);
		
		//Invalid Date label
		lblDateError = new JLabel("<html><font color='red'>Please enter a valid date (MM/DD/YYYY).</font></html>");
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
		//Calendar calendar = startDatePicker.getMonthView().getCalendar();
		//calendar.setTime(new Date());
		//startDatePicker.getMonthView().setLowerBound(calendar.getTime());
		SimpleDateFormat format1 = new SimpleDateFormat( "MM/dd/yyyy" );
		SimpleDateFormat format2 = new SimpleDateFormat( "MM.dd.yyyy" );
		startDatePicker.setFormats(new DateFormat[] {format1, format2});
		
		
////////////////////////////////////////////
		
		

		
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
//		endTimeSpinner = new JSpinner( new SpinnerDateModelMinute());
//		endTimeSpinner.setModel(endSpinnerModel);
//		endTimeEditor = new JSpinner.DateEditor(endTimeSpinner, "hh:mm a");
//		endTimeSpinner.setEditor(endTimeEditor);
		// Create time spinner panel.
		endSpinnerPanel = new JPanel();
		endSpinnerPanel.setBackground(Color.WHITE);
		
		endSpinnerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
				
		
		// Create time spinners, hour, minute, and AM_PM
		endHourSpinner = new JSpinner( new SpinnerDateModelHour());
		endHourSpinner.setFont(new Font("Tahoma", Font.PLAIN, 18));
		endSpinnerPanel.add(endHourSpinner);
		endHourEditor = new JSpinner.DateEditor(endHourSpinner, "hh");
		endHourSpinner.setEditor(endHourEditor);
		endHourEditor.getTextField().setFocusLostBehavior(JFormattedTextField.PERSIST);

		colon = new JLabel(":");
		endSpinnerPanel.add(colon);
		
		endMinuteSpinner = new JSpinner( new SpinnerDateModelMinute());
		endMinuteSpinner.setFont(new Font("Tahoma", Font.PLAIN, 18));
		endSpinnerPanel.add(endMinuteSpinner);
		endMinuteEditor = new JSpinner.DateEditor(endMinuteSpinner, "mm");
		endMinuteSpinner.setEditor(endMinuteEditor);
		endMinuteEditor.getTextField().setFocusLostBehavior(JFormattedTextField.PERSIST);

		endAMPMSpinner = new JSpinner(new SpinnerDateModelAMPM());
		endAMPMSpinner.setFont(new Font("Tahoma", Font.PLAIN, 18));
		endSpinnerPanel.add(endAMPMSpinner);
		endAMPMEditor = new JSpinner.DateEditor(endAMPMSpinner, "a");
		endAMPMSpinner.setEditor(endAMPMEditor);
		endAMPMEditor.getTextField().setFocusLostBehavior(JFormattedTextField.PERSIST);
		GridBagConstraints gbc_spinner2 = new GridBagConstraints();
		gbc_spinner2.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinner2.insets = new Insets(0, 0, 5, 0);
		gbc_spinner2.gridx = 3;
		gbc_spinner2.gridy = 6;
		gbc_spinner2.weightx = 1.0;
		gbc_spinner2.weighty = 3;
		formPanel.add(endSpinnerPanel, gbc_spinner2);
		
		
		
		
		//Invalid Time label
		lblTimeError2 = new JLabel(" ");
		lblTimeError2.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblTimeError2 = new GridBagConstraints();
		gbc_lblTimeError2.insets = new Insets(0, 0, 5, 0);
		gbc_lblTimeError2.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblTimeError2.gridx = 3;
		gbc_lblTimeError2.gridy = 7;
		gbc_lblTimeError2.weightx = 1;
		gbc_lblTimeError2.weighty = 1;		
		formPanel.add(lblTimeError2, gbc_lblTimeError2);
		
		//Invalid Date label
		lblDateError2 = new JLabel("<html><font color='red'>Please enter a valid date (MM/DD/YYYY).</font></html>");
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
		//Calendar calendar = startDatePicker.getMonthView().getCalendar();
		//calendar.setTime(new Date());
		//startDatePicker.getMonthView().setLowerBound(calendar.getTime());
		endDatePicker.setFormats(new DateFormat[] {format1, format2});
		
		GregorianCalendar c = new GregorianCalendar();
	    c.set(Calendar.HOUR_OF_DAY, 0);
	    c.set(Calendar.MINUTE, 0);
	    c.set(Calendar.SECOND, 0);
		endDatePicker.setDate(c.getTime());
	    startDate = c;
		startDatePicker.setDate(c.getTime());
		
		
		
/////////////////////////////////////////		

		
		//Sets time value of end and start spinners
		oldStartTime = new GregorianCalendar();
		setStartDate(oldStartTime);
		setEndDate(oldStartTime);
		
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
		repeatCheckBox.setBackground(Color.WHITE);
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
				checkSaveBtnStatus();
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
		repeatTypeComboBox.setBackground(CalendarStandard.CalendarYellow);
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
		repeatTypeComboBox.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				checkSaveBtnStatus();
				
			}
			
		});
		
		
		//Add Repetitions Label
		lblNumberRepetitions = new JLabel("# of Occurrences:");
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
		repeatAmt.setBackground(CalendarStandard.CalendarYellow);
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
		repeatAmt.addKeyListener(new KeyListener(){

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				checkSaveBtnStatus();
			}
			
		});
		
		
		buttonPanel = new JPanel(new BorderLayout(30,0));
		buttonPanel.setBackground(Color.WHITE);
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
				removeTabCancel();
			}
			
		});
		
		buttonPanel.add(btnAddEvent, BorderLayout.WEST);
		buttonPanel.add(btnCancel, BorderLayout.CENTER);
	                    				// Set the horizontal gap
		formPanel.add(buttonPanel, gbc_btnPanel);
		
		initFlag = true;
		
		addEditableElementsListeners();
	}

	
	/**
	 * Helper function that sets up listeners only for date picker.
	 */
	private void addStartDatePickerListeners() {
		
		//Mouse focus handler
		startDatePicker.getEditor().addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
			}

			@Override
			public void focusLost(FocusEvent e) {
				String txt = startDatePicker.getEditor().getText();
				try {
					startDatePicker.getEditor().commitEdit();
					
					GregorianCalendar cal = new GregorianCalendar();
					cal.setTime(startDatePicker.getDate());
					
					if(cal.get(Calendar.YEAR) < 1900) {
						startDatePicker.getEditor().setText(txt);
					}
					
					checkStartDatePickerStatus();
					checkSaveBtnStatus();
					checkEndBeforeStart();
				} catch (ParseException e1) {
					checkStartDatePickerStatus();
					checkSaveBtnStatus();
					checkEndBeforeStart();
				}

			}
		});
		
		//Real time keyboard input check
		startDatePicker.getEditor().addKeyListener(new KeyListener(){

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				checkSaveBtnStatus();
			}

		});
		
		startDatePicker.getEditor().addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char vChar = e.getKeyChar();
                if (!(Character.isDigit(vChar)
                		|| (vChar == '/')
                        || (vChar == KeyEvent.VK_BACK_SPACE)
                        || (vChar == KeyEvent.VK_DELETE))) {
                    e.consume();
                }
            }
        });
		
		
		
		//Triggered on enter.
		startDatePicker.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				checkStartDatePickerStatus();
				checkSaveBtnStatus();
			}
		});
	
	}
	
	
	/**
	 * Helper function that sets up listeners only for date picker.
	 */
	private void addEndDatePickerListeners() {
		
		//Mouse focus handler
		endDatePicker.getEditor().addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
			}

			@Override
			public void focusLost(FocusEvent e) {
				String txt = endDatePicker.getEditor().getText();
				try {
					endDatePicker.getEditor().commitEdit();
					
					GregorianCalendar cal = new GregorianCalendar();
					cal.setTime(endDatePicker.getDate());
					
					if(cal.get(Calendar.YEAR) < 1900) {
						endDatePicker.getEditor().setText(txt);
					}
					
					checkEndDatePickerStatus();
					checkSaveBtnStatus();
					checkEndBeforeStart();
				} catch (ParseException e1) {
					checkEndDatePickerStatus();
					checkSaveBtnStatus();
					checkEndBeforeStart();
				}

			}
		});
		
		//Real time keyboard input check
		endDatePicker.getEditor().addKeyListener(new KeyListener(){

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				//checkSaveBtnStatus();
				//checkEndDatePickerStatus();
				//checkEndBeforeStart();
			}

		});
		
		endDatePicker.getEditor().addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char vChar = e.getKeyChar();
                if (!(Character.isDigit(vChar)
                		|| (vChar == '/')
                        || (vChar == KeyEvent.VK_BACK_SPACE)
                        || (vChar == KeyEvent.VK_DELETE))) {
                    e.consume();
                }
            }
        });
		
		
		
		//Triggered on enter.
		endDatePicker.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				checkEndDatePickerStatus();
				checkSaveBtnStatus();
				checkEndBeforeStart();

			}
		});
	
	}
	
	
	private void addStartTimeSpinnerListeners() {
		startHourEditor.getTextField().addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
			}

			@Override
			public void focusLost(FocusEvent e) {
				try {
					startHourEditor.getTextField().commitEdit();
					checkStartTimeSpinnerStatus(startHourSpinner);
					checkSaveBtnStatus();
				} catch (ParseException e1) {
					checkStartTimeSpinnerStatus(startHourSpinner);
					checkSaveBtnStatus();
					e1.printStackTrace();
				}
			}
		});
		
		startHourEditor.getTextField().addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char vChar = e.getKeyChar();
                if (!(Character.isDigit(vChar)
                        || (vChar == KeyEvent.VK_BACK_SPACE)
                        || (vChar == KeyEvent.VK_DELETE))) {
                    e.consume();
                }
            }
        });
		
		
		//Some edit specific listeners
		//These are here to avoid possible NullPointer exceptions while opening the tab 
		startHourSpinner.addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent e) {
				checkSaveBtnStatus();
				startTempHour = Integer.parseInt(startHourEditor.getTextField().getText().toString());
				checkStartTimeSpinnerStatus(startHourSpinner);
				updateEndTime();
			}
		});
		
		startHourSpinner.addFocusListener(new FocusListener() {
			
			@Override
			public void focusGained(FocusEvent e) {
				checkSaveBtnStatus();
			}

			@Override
			public void focusLost(FocusEvent e) {
				checkSaveBtnStatus();
			}
		});

		
		startMinuteEditor.getTextField().addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
			}

			@Override
			public void focusLost(FocusEvent e) {
				try {
					startMinuteEditor.getTextField().commitEdit();
					checkStartTimeSpinnerStatus(startMinuteSpinner);
					checkSaveBtnStatus();
				} catch (ParseException e1) {
					checkStartTimeSpinnerStatus(startMinuteSpinner);
					checkSaveBtnStatus();
					e1.printStackTrace();
				}
			}
		});
		
		startMinuteEditor.getTextField().addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char vChar = e.getKeyChar();
                if (!(Character.isDigit(vChar)
                        || (vChar == KeyEvent.VK_BACK_SPACE)
                        || (vChar == KeyEvent.VK_DELETE))) {
                    e.consume();
                }
            }
        });
		
		/**
		 * Rounds minute input to the closest 30s everytime the text field changes.
		 */
		startMinuteSpinner.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				GregorianCalendar cal = new GregorianCalendar();
				cal.setTime((Date) startMinuteSpinner.getValue());
				int currentHour = cal.get(Calendar.HOUR);
				startTempMin = Integer.parseInt(startMinuteEditor.getTextField().getText().toString());
				checkStartTimeSpinnerStatus(startMinuteSpinner);
				
				if(currentHour == 1) {
					cal.setTime((Date) startHourSpinner.getValue());
					cal.add(Calendar.HOUR, 1);  
					startHourSpinner.setValue(cal.getTime());
				}
				
				if(currentHour == 11) {
					cal.setTime((Date) startHourSpinner.getValue());
					cal.add(Calendar.HOUR, -1);  
					startHourSpinner.setValue(cal.getTime());
				}
				checkSaveBtnStatus();
				updateEndTime();

			}
		});
		
		startMinuteSpinner.addFocusListener(new FocusListener() {
			
			@Override
			public void focusGained(FocusEvent e) {
				checkSaveBtnStatus();
			}

			@Override
			public void focusLost(FocusEvent e) {
				checkSaveBtnStatus();
			}
		});
		
		startAMPMEditor.getTextField().addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
			}

			@Override
			public void focusLost(FocusEvent e) {
				try {
					startAMPMEditor.getTextField().commitEdit();
					checkStartTimeSpinnerStatus(startAMPMSpinner);
					checkSaveBtnStatus();
				} catch (ParseException e1) {
					checkStartTimeSpinnerStatus(startAMPMSpinner);
					checkSaveBtnStatus();
				}
			}
		});
		
		startAMPMEditor.getTextField().addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char vChar = e.getKeyChar();
                if (!((vChar == 'A')
                		|| (vChar == 'P')
                		|| (vChar == 'M')
                		|| (vChar == 'a')
                		|| (vChar == 'p')
                		|| (vChar == 'm')
                        || (vChar == KeyEvent.VK_BACK_SPACE)
                        || (vChar == KeyEvent.VK_DELETE))) {
                    e.consume();
                }
            }
        });
		
		//Some edit specific listeners
		//These are here to avoid possible NullPointer exceptions while opening the tab 
		startAMPMSpinner.addChangeListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent e) {
				checkSaveBtnStatus();
				updateEndTime();
			}
		});
		
		startAMPMSpinner.addFocusListener(new FocusListener() {
			
			@Override
			public void focusGained(FocusEvent e) {
				checkSaveBtnStatus();
			}

			@Override
			public void focusLost(FocusEvent e) {
				checkSaveBtnStatus();
			}
		});
		
	}
	
	
	
	private void addEndTimeSpinnerListeners() {
		endHourEditor.getTextField().addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
			}

			@Override
			public void focusLost(FocusEvent e) {
				try {
					endHourEditor.getTextField().commitEdit();
					checkEndTimeSpinnerStatus(endHourSpinner);
					checkSaveBtnStatus();
				} catch (ParseException e1) {
					checkEndTimeSpinnerStatus(endHourSpinner);
					checkSaveBtnStatus();
					e1.printStackTrace();
				}
			}
		});
		
		endHourEditor.getTextField().addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char vChar = e.getKeyChar();
                if (!(Character.isDigit(vChar)
                        || (vChar == KeyEvent.VK_BACK_SPACE)
                        || (vChar == KeyEvent.VK_DELETE))) {
                    e.consume();
                }
            }
        });
		
		
		//Some edit specific listeners
		//These are here to avoid possible NullPointer exceptions while opening the tab 
		endHourSpinner.addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent e) {
				checkSaveBtnStatus();
				endTempHour = Integer.parseInt(endHourEditor.getTextField().getText().toString());
				checkEndTimeSpinnerStatus(endHourSpinner);
				checkEndBeforeStart();
			}
		});
		
		endHourSpinner.addFocusListener(new FocusListener() {
			
			@Override
			public void focusGained(FocusEvent e) {
				checkSaveBtnStatus();
			}

			@Override
			public void focusLost(FocusEvent e) {
				checkSaveBtnStatus();
			}
		});

		
		endMinuteEditor.getTextField().addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
			}

			@Override
			public void focusLost(FocusEvent e) {
				try {
					endMinuteEditor.getTextField().commitEdit();
					checkEndTimeSpinnerStatus(endMinuteSpinner);
					checkSaveBtnStatus();
				} catch (ParseException e1) {
					checkEndTimeSpinnerStatus(endMinuteSpinner);
					checkSaveBtnStatus();
					e1.printStackTrace();
				}
			}
		});
		
		endMinuteEditor.getTextField().addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char vChar = e.getKeyChar();
                if (!(Character.isDigit(vChar)
                        || (vChar == KeyEvent.VK_BACK_SPACE)
                        || (vChar == KeyEvent.VK_DELETE))) {
                    e.consume();
                }
            }
        });
		
		/**
		 * Rounds minute input to the closest 30s everytime the text field changes.
		 */
		endMinuteSpinner.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				GregorianCalendar cal = new GregorianCalendar();
				cal.setTime((Date) endMinuteSpinner.getValue());
				int currentHour = cal.get(Calendar.HOUR);
				endTempMin = Integer.parseInt(endMinuteEditor.getTextField().getText().toString());
				checkEndTimeSpinnerStatus(endMinuteSpinner);
				
				if(currentHour == 1) {
					cal.setTime((Date) endHourSpinner.getValue());
					cal.add(Calendar.HOUR, 1);  
					endHourSpinner.setValue(cal.getTime());
				}
				
				if(currentHour == 11) {
					cal.setTime((Date) endHourSpinner.getValue());
					cal.add(Calendar.HOUR, -1);  
					endHourSpinner.setValue(cal.getTime());
				}
				checkSaveBtnStatus();
				checkEndBeforeStart();
			}
		});
		
		endMinuteSpinner.addFocusListener(new FocusListener() {
			
			@Override
			public void focusGained(FocusEvent e) {
				checkSaveBtnStatus();
			}

			@Override
			public void focusLost(FocusEvent e) {
				checkSaveBtnStatus();
			}
		});
		
		endAMPMEditor.getTextField().addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
			}

			@Override
			public void focusLost(FocusEvent e) {
				try {
					endAMPMEditor.getTextField().commitEdit();
					checkEndTimeSpinnerStatus(endAMPMSpinner);
					checkSaveBtnStatus();
				} catch (ParseException e1) {
					checkEndTimeSpinnerStatus(endAMPMSpinner);
					checkSaveBtnStatus();
					e1.printStackTrace();
				}
			}
		});
		
		endAMPMEditor.getTextField().addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char vChar = e.getKeyChar();
                if (!((vChar == 'A')
                		|| (vChar == 'P')
                		|| (vChar == 'M')
                		|| (vChar == 'a')
                		|| (vChar == 'p')
                		|| (vChar == 'm')
                        || (vChar == KeyEvent.VK_BACK_SPACE)
                        || (vChar == KeyEvent.VK_DELETE))) {
                    e.consume();
                }
            }
        });
		
		//Some edit specific listeners
		//These are here to avoid possible NullPointer exceptions while opening the tab 
		endAMPMSpinner.addChangeListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent e) {
				checkSaveBtnStatus();
				checkEndBeforeStart();
			}
		});
		
		endAMPMSpinner.addFocusListener(new FocusListener() {
			
			@Override
			public void focusGained(FocusEvent e) {
				checkSaveBtnStatus();
			}

			@Override
			public void focusLost(FocusEvent e) {
				checkSaveBtnStatus();
			}
		});
		
	}



	protected void checkEndBeforeStart() {
		if(getEndDate().getTime().before(getStartDate().getTime()))
				setEndDate(getStartDate());
	}


	protected void updateEndTime() {
		long diff = getStartDate().getTime().getTime() - oldStartTime.getTime().getTime();
		GregorianCalendar cal = getEndDate();
		cal.setTime(new Date(cal.getTime().getTime() + diff));
		setEndDate(cal);
		oldStartTime = getStartDate();
	}


	private GregorianCalendar getStartDate()
	{
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(oldStartTime.getTime());
		GregorianCalendar calTemp = new GregorianCalendar();
		calTemp.setTime(startDatePicker.getDate());
		cal.set(Calendar.DATE, calTemp.get(Calendar.DATE));
		

		calTemp.setTime(startDatePicker.getDate());
		cal.set(Calendar.DATE, calTemp.get(Calendar.DATE));
		

		calTemp.setTime((Date)startHourSpinner.getValue());
		cal.set(Calendar.HOUR, calTemp.get(Calendar.HOUR));
		
		calTemp.setTime((Date)startMinuteSpinner.getValue());
		cal.set(Calendar.MINUTE, calTemp.get(Calendar.MINUTE));
		
		calTemp.setTime((Date)startAMPMSpinner.getValue());
		cal.set(Calendar.AM_PM, calTemp.get(Calendar.AM_PM));
		
		return cal;
		
	}
	
	private GregorianCalendar getEndDate()
	{
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(oldStartTime.getTime());
		GregorianCalendar calTemp = new GregorianCalendar();
		calTemp.setTime(endDatePicker.getDate());
		cal.set(Calendar.DATE, calTemp.get(Calendar.DATE));
		

		calTemp.setTime(endDatePicker.getDate());
		cal.set(Calendar.DATE, calTemp.get(Calendar.DATE));
		

		calTemp.setTime((Date)endHourSpinner.getValue());
		cal.set(Calendar.HOUR, calTemp.get(Calendar.HOUR));
		
		calTemp.setTime((Date)endMinuteSpinner.getValue());
		cal.set(Calendar.MINUTE, calTemp.get(Calendar.MINUTE));
		
		calTemp.setTime((Date)endAMPMSpinner.getValue());
		cal.set(Calendar.AM_PM, calTemp.get(Calendar.AM_PM));
		
		return cal;
		
	}
	
	private void setStartDate(GregorianCalendar date)
	{
		endDatePicker.setDate(date.getTime());
		startHourSpinner.setValue(date.getTime());
		startMinuteSpinner.setValue(date.getTime());
		startAMPMSpinner.setValue(date.getTime());
	}
	
	private void setEndDate(GregorianCalendar date)
	{
		endDatePicker.setDate(date.getTime());
		endHourSpinner.setValue(date.getTime());
		endMinuteSpinner.setValue(date.getTime());
		endAMPMSpinner.setValue(date.getTime());
	}
	
	
	/**
	 * Controls the enable state of the save button by checking all user editable elements in commitment tab.
	 */
	private void checkSaveBtnStatus(){
		
		if (initFlag){
			if(		nameTextField.getText().equals("") ||
					startDatePicker.getDate() == null || //data validation
					endDatePicker.getDate() == null || //data validation

					nameTextField.getText().trim().length() == 0) 
			{
				btnAddEvent.setEnabled(false);
			}
			else {
				if (mode == EditingMode.EDITING) {
					//get some date data
					//Parse date and time info
					
					//System.out.println("AMPM is " + calAMPM.get(Calendar.AM_PM));
					//System.out.println("Hour of day is " + calHour.get(Calendar.HOUR_OF_DAY));
					//System.out.println("Current commit hour is " + editingEvent.getDueDate().get(Calendar.HOUR_OF_DAY));
					//System.out.println("Time in milli is " + calDate.getTimeInMillis());
					//System.out.println("Commit time in milli is " + editingEvent.getDueDate().getTimeInMillis());
					
					//make sure something changed
					if (nameTextField.getText().equals(editingEvent.getName()) 
							&& descriptionTextArea.getText().equals(editingEvent.getDescription())
							&& ((Category)categoryComboBox.getSelectedItem()).getId() == editingEvent.getCategoryID()
							&& getStartDate().getTime().equals(editingEvent.getStartTime().getTime())
							&& getEndDate().getTime().equals(editingEvent.getEndTime().getTime()))
					{
						btnAddEvent.setEnabled(false);
						return;
					}
				}
				if(repeatCheckBox.isSelected()){
					try {
						if (Integer.parseInt(repeatAmt.getText()) > 1){
							btnAddEvent.setEnabled(true);	
						} else {
							btnAddEvent.setEnabled(false);
						}
					} catch (Exception ex){
						btnAddEvent.setEnabled(false);
					}
					return;
				}
				btnAddEvent.setEnabled(true);
			}

		}
	}


	/**
	 * Create a event tab in editing mode.
	 */
	public EventTab(Event event, int openedFrom) {
		this(openedFrom);
		
		initFlag = false; //We need this to deal with the nested constructors
		
		editingEvent = event;
		mode = EditingMode.EDITING;
		
		nameTextField.setText(editingEvent.getName());
		descriptionTextArea.setText(editingEvent.getDescription());
		
		categoryComboBox.setSelectedItem(editingEvent.getCategoryID());
		
		
		if(!editingEvent.getIsPersonal())
			rdbtnTeam.setSelected(true);
		else
			rdbtnPersonal.setSelected(true);
		
		
		rdbtnTeam.setEnabled(false);
		rdbtnPersonal.setEnabled(false);
		

		setStartDate(editingEvent.getStartTime());
		startDatePicker.setDate(editingEvent.getStartTime().getTime());
		setEndDate(editingEvent.getEndTime());
		endDatePicker.setDate(editingEvent.getEndTime().getTime());

		//handle repetition fields
		if(event.getIsRepeating()){
			CalendarData calData;
			if (rdbtnPersonal.isSelected()){
				calData = CalendarDataModel.getInstance().getCalendarData(ConfigManager.getConfig().getProjectName() + "-" + ConfigManager.getConfig().getUserName()); 
				isTeamEvent = false;
			}
			else{
				calData = CalendarDataModel.getInstance().getCalendarData(ConfigManager.getConfig().getProjectName()); 
				isTeamEvent = true;
			}
			editingRepeatingEvent = calData.getRepeatingEvents().get(event.getID());
			repeatCheckBox.setSelected(true);
			repeatAmt.setText(Integer.toString(editingRepeatingEvent.getRepetitions()));
			repeatAmt.setEnabled(true);
			if (editingRepeatingEvent.getRepType() == RepeatType.MONTH){
				repeatTypeComboBox.setSelectedIndex(2);
			} else if (editingRepeatingEvent.getRepType() == RepeatType.WEEK){
				repeatTypeComboBox.setSelectedIndex(1);
			} else {
				repeatTypeComboBox.setSelectedIndex(0);
			}
			repeatTypeComboBox.setEnabled(true);
			//pull times from the Repeating event
			//otherwise the initial occurrence will get set to the double-clicked day
			setStartDate(editingRepeatingEvent.getStartTime());
			startDatePicker.setDate(editingRepeatingEvent.getStartTime().getTime());
			setEndDate(editingRepeatingEvent.getEndTime());
			endDatePicker.setDate(editingRepeatingEvent.getEndTime().getTime());
		}
		
		repeatCheckBox.setEnabled(false);//Don't want people changing this for now
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
	
		
		
		initFlag = true;

	}
	

	/**
	 * Close this event tab
	 */
	protected void removeTab(int goTo) {
		GUIEventController.getInstance().removeEventTab(this, goTo);
	}
	/**
	 * Close this event tab when cancel is hit
	 */
	protected void removeTabCancel() {
		GUIEventController.getInstance().removeEventTab(this, openedFrom);
	}

	
	
	/**
	 * Adds listeners for all editable elements in commitment tab.
	 * Calls addTimeSpinnerListeners() and addDatePickerListeners() which are helper functions defined outside this method.
	 */
	private void addEditableElementsListeners() {
		nameTextField.addKeyListener(new KeyListener() {
			@Override
			public void keyReleased(KeyEvent e) {
				checkSaveBtnStatus();
			}
			// Unused.
			@Override
			public void keyTyped(KeyEvent e) {
			}
			@Override
			public void keyPressed(KeyEvent e) {
			}
		});
		
		descriptionTextArea.addKeyListener(new KeyListener() {
			@Override
			public void keyReleased(KeyEvent e) {
				checkSaveBtnStatus();
			}
			// Unused.
			@Override
			public void keyTyped(KeyEvent e) {
			}
			@Override
			public void keyPressed(KeyEvent e) {
			}
		});
		
		categoryComboBox.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				checkSaveBtnStatus();
			}
		});
		
		addStartTimeSpinnerListeners();
		addStartDatePickerListeners();
		addEndTimeSpinnerListeners();
		addEndDatePickerListeners();
		
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
		if (rdbtnPersonal.isSelected()){
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

			newRepEvent.setCategoryID(((Category)categoryComboBox.getSelectedItem()).getId());
			newRepEvent.setDescription(descriptionTextArea.getText());


			//set start/end date
			newRepEvent.setStartTime(getStartDate());
			newRepEvent.setEndTime(getEndDate());
			newRepEvent.setName(nameTextField.getText());
			
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


			this.removeTab(isTeamEvent ? 1 : 0);

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

			newEvent.setCategoryID(((Category)categoryComboBox.getSelectedItem()).getId());
			newEvent.setDescription(descriptionTextArea.getText());


			

			//set due date
			newEvent.setStartTime(getStartDate());
			newEvent.setEndTime(getEndDate());
			newEvent.setName(nameTextField.getText());

			if (mode == EditingMode.ADDING)
				calData.addEvent(newEvent);
			else
				calData.getEvents().update(newEvent);

			UpdateCalendarDataController.getInstance().updateCalendarData(calData);


			this.removeTab(isTeamEvent ? 1 : 0);
		}
	}
	

	protected void deleteEvent() {
		// TODO Auto-generated method stub
		CalendarData calData;
		if (rdbtnPersonal.isSelected()){
			calData = CalendarDataModel.getInstance().getCalendarData(ConfigManager.getConfig().getProjectName() + "-" + ConfigManager.getConfig().getUserName()); 
			isTeamEvent = false;
		}
		else{
			calData = CalendarDataModel.getInstance().getCalendarData(ConfigManager.getConfig().getProjectName()); 
			isTeamEvent = true;
		}

		if (repeatCheckBox.isSelected()){
			calData.getRepeatingEvents().removeEvent(editingRepeatingEvent.getID());
		} else {
			calData.getEvents().removeEvent(editingEvent.getID());
		}
		
		UpdateCalendarDataController.getInstance().updateCalendarData(calData);
		removeTab(isTeamEvent ? 1 : 0);
	}


	/**
	 * Checks text field in datepicker's editor.
	 * @return boolean that indicates whether the input in the editor is valid.
	 */
	private boolean isBadStartInputDate() {
		boolean result;
		Date date = null;
		GregorianCalendar cal = new GregorianCalendar();
		if(startDatePicker.getDate() == null) {
			result = true;
		}
		else {
			cal.setTime(startDatePicker.getDate());
			for(DateFormat formatter : startDatePicker.getFormats()) {
				try{
					formatter.setLenient(false);
					date = formatter.parse(startDatePicker.getEditor().getText());
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
		}

		if(date == null || cal.get(Calendar.YEAR) < 1900 || cal.get(Calendar.YEAR) > 9999) {
			result = true;
		}
		else{
			result = false;
		}
		return result;
	}
	
	/**
	 * Checks text field in datepicker's editor.
	 * @return boolean that indicates whether the input in the editor is valid.
	 */
	private boolean isBadEndInputDate() {
		boolean result;
		Date date = null;
		GregorianCalendar cal = new GregorianCalendar();
		if(endDatePicker.getDate() == null) {
			result = true;
		}
		else {
			cal.setTime(endDatePicker.getDate());
			for(DateFormat formatter : endDatePicker.getFormats()) {
				try{
					formatter.setLenient(false);
					date = formatter.parse(endDatePicker.getEditor().getText());
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
		}

		if(date == null || cal.get(Calendar.YEAR) < 1900 || cal.get(Calendar.YEAR) > 9999) {
			result = true;
		}
		else{
			result = false;
		}
		return result;
	}
	
	private void checkStartDatePickerStatus() {
		if(isBadStartInputDate()) {
			startDatePicker.getEditor().setBackground(Color.getHSBColor(3, 0.3f, 1f));
			lblDateError.setVisible(true);
		}
		else {
			SimpleDateFormat dt = new SimpleDateFormat("MM/dd/yyyy"); 
			startDatePicker.getEditor().setBackground(Color.WHITE);
			startDatePicker.getEditor().setText(dt.format(startDatePicker.getDate()));
			lblDateError.setVisible(false);
		}
	}
	
	private void checkEndDatePickerStatus() {
		if(isBadEndInputDate()) {
			endDatePicker.getEditor().setBackground(Color.getHSBColor(3, 0.3f, 1f));
			lblDateError2.setVisible(true);
		}
		else {
			SimpleDateFormat dt = new SimpleDateFormat("MM/dd/yyyy"); 
			endDatePicker.getEditor().setBackground(Color.WHITE);
			endDatePicker.getEditor().setText(dt.format(endDatePicker.getDate()));
			lblDateError2.setVisible(false);
		}
	}
	
	private void checkStartTimeSpinnerStatus(JSpinner spinner) {
		DateEditor editor = (DateEditor)spinner.getEditor();
		if(isBadInputTime(editor) || startTempHour < 1 || startTempHour > 12 || startTempMin > 59) {
			editor.getTextField().setBackground(Color.getHSBColor(3, 0.3f, 1f));
			lblTimeError.setText("<html><font color='red'>Please enter a valid time.</font></html>");
		}
		else {
			editor.getTextField().setBackground(Color.WHITE);
			lblTimeError.setText(" ");
		}
	}
	
	private void checkEndTimeSpinnerStatus(JSpinner spinner) {
		DateEditor editor = (DateEditor)spinner.getEditor();
		if(isBadInputTime(editor) || endTempHour < 1 || endTempHour > 12 || endTempMin > 59) {
			editor.getTextField().setBackground(Color.getHSBColor(3, 0.3f, 1f));
			lblTimeError2.setText("<html><font color='red'>Please enter a valid time.</font></html>");
		}
		else {
			editor.getTextField().setBackground(Color.WHITE);
			lblTimeError2.setText(" ");
		}
	}
	
	private boolean isBadInputTime(DateEditor editor) {
		boolean result;
		SimpleDateFormat format = editor.getFormat();
		Date date = null;
		
		try {
			date = format.parse(editor.getTextField().getText().trim());
		}
		catch (ParseException e) {
		}
		catch(NullPointerException ne){
			result = false;
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



