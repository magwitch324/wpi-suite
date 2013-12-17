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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JSpinner.DateEditor;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jdesktop.swingx.JXDatePicker;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.calendar.CalendarStandard;
import edu.wpi.cs.wpisuitetng.modules.calendar.controller.UpdateCalendarDataController;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.Category;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.Event;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.RepeatingEvent;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.RepeatingEvent.RepeatType;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.CalendarData;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.CalendarDataModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.GUIEventController;


/**
 * Create/edit event tab
 * @author CS Anonymous
 * @version $Revision: 1.0 $
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
	private JPanel rdbtnPanel;
	private JRadioButton rdbtnPersonal;
	private JRadioButton rdbtnTeam;
	private JLabel lblType;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JButton btnCancel;
	private final Date tmpDate = new Date(); 
	//user for convert the date to default format
	private final String inputDate = (new SimpleDateFormat("MM/dd/yyyy").format(tmpDate)); 
	//the date user input
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
	private JLabel lblRepeatError;
	private int startTempHour = 1;
	private int startTempMin = 1;
	private int endTempMin = 1;
	private int endTempHour = 1;
	private int openedFrom;
	private Category uncategorized;



	/*
	 * Sources:
	 * Icons were developed using images obtained at: 
	 * [1] https://svn.apache.org/repos/asf/openoffice/
	 * symphony/trunk/main/extras/source/gallery/symbols/
	 * [2] http://www.clker.com/clipart-red-round.html
	 * [3] http://www.iconsdb.com/red-icons/delete-icon.html
	 */



	/**
	 * @author CS Anonymous
	 */
	private enum EditingMode {
		ADDING(0),
		EDITING(1);
		private final int currentMode;

		private EditingMode(int currentMode) {
			this.currentMode = currentMode;
		}
	}

	/**
	 * @wbp.parser.constructor
	 */
	public EventTab(int openedFrom) {
		this(openedFrom, (new GregorianCalendar()).getTime());
	}
	
	/**
	 * Create the panel.
	 * @param openedFrom int
	 */
	public EventTab(int openedFrom, Date inputTime) {
		this.openedFrom = openedFrom;
		initFlag = false;

		final GridBagLayout gridBagLayout = new GridBagLayout();
		setLayout(gridBagLayout);
		final JPanel spacePanel1 = new JPanel();
		final JPanel spacePanel2 = new JPanel();
		formPanel = new JPanel();
		formPanel.setBackground(Color.WHITE);
		formPanel.setPreferredSize(new Dimension(500, 600));
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
		constraints.weightx = 3;
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
		final GridBagLayout gbl = new GridBagLayout();
		gbl.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		gbl.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0};
		gbl.rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl.columnWidths = new int[] {0, 0, 0, 0};
		formPanel.setLayout(gbl);

		//Name label
		final JLabel lblName = new JLabel("<html><body style='width: 80px'><font>" + "Name" + "</font>" 
				+ "<font color=red>" + "*" + "</font>" 
				+ "<font>" + ":" + "</font></html>");
		lblName.setHorizontalAlignment(SwingConstants.RIGHT);
		final GridBagConstraints gbc = new GridBagConstraints();
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
		gbc_nameTextField.insets = new Insets(0, 0, 0, 5);
		gbc_nameTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_nameTextField.weightx = 10;
		gbc_nameTextField.weighty = 1;
		gbc_nameTextField.gridx = 1;
		gbc_nameTextField.gridy = 0;
		gbc.gridwidth = 3;
		formPanel.add(nameTextField, gbc_nameTextField);



		//Description label
		final JLabel lblDesc = new JLabel("<html><body style='width: 80px'>Description:</html>");
		lblDesc.setHorizontalAlignment(SwingConstants.RIGHT);
		final GridBagConstraints gbc_lblDesc = new GridBagConstraints();
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
		final GridBagConstraints gbc_descriptionTextField = new GridBagConstraints();
		gbc_descriptionTextField.gridwidth = 3;
		gbc_descriptionTextField.fill = GridBagConstraints.BOTH;
		gbc_descriptionTextField.insets = new Insets(0, 0, 0, 5);
		gbc_descriptionTextField.weightx = 10;
		gbc_descriptionTextField.weighty = 5;
		gbc_descriptionTextField.gridx = 1;
		gbc_descriptionTextField.gridy = 1;
		gbc.gridwidth = 3;
		descriptionScrollPane = new JScrollPane(descriptionTextArea);
		formPanel.add(descriptionScrollPane, gbc_descriptionTextField);
		descriptionScrollPane.setMaximumSize(new Dimension(10000000, 10));
		descriptionScrollPane.getViewport().setMaximumSize(new Dimension(10000000, 10));


		//Category label
		final JLabel lblCategory = new JLabel("<html><body style='width: 80px'>Category:</html>");
		lblCategory.setHorizontalAlignment(SwingConstants.RIGHT);
		final GridBagConstraints gbc_lblCategory = new GridBagConstraints();
		gbc_lblCategory.anchor = GridBagConstraints.EAST;
		gbc_lblCategory.insets = new Insets(0, 0, 5, 5);
		gbc_lblCategory.gridx = 0;
		gbc_lblCategory.gridy = 2;
		gbc_lblCategory.weightx = 1;
		gbc_lblCategory.weighty = 1;
		formPanel.add(lblCategory, gbc_lblCategory);

		//Create category box, add two dummy categories
		categoryComboBox = new JComboBox<Category>();
		categoryComboBox.setRenderer(new CategoryComboBoxRenderer());
		categoryComboBox.setBackground(CalendarStandard.CalendarYellow);
		uncategorized = new Category("[None]", Color.WHITE, false);
		uncategorized.setID(0);


		final GridBagConstraints gbc_categoryComboBox = new GridBagConstraints();
		gbc_categoryComboBox.gridwidth = 3;
		gbc_categoryComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_categoryComboBox.insets = new Insets(0, 0, 0, 5);
		gbc_categoryComboBox.gridx = 1;
		gbc_categoryComboBox.gridy = 2;
		gbc_categoryComboBox.weightx = 10;
		gbc_categoryComboBox.weighty = 1;
		gbc.gridwidth = 3;
		formPanel.add(categoryComboBox, gbc_categoryComboBox);


		lblType = new JLabel("<html><body style='width: 80px'>Type:</html>");
		lblType.setHorizontalAlignment(SwingConstants.RIGHT);
		final GridBagConstraints gbc_lblType = new GridBagConstraints();
		gbc_lblType.anchor = GridBagConstraints.EAST;
		gbc_lblType.insets = new Insets(0, 0, 5, 5);
		gbc_lblType.gridx = 0;
		gbc_lblType.gridy = 3;
		formPanel.add(lblType, gbc_lblType);

		rdbtnPanel = new JPanel();
		rdbtnPanel.setBackground(Color.WHITE);
		final GridBagConstraints gbc_rdbtnPanel = new GridBagConstraints();
		gbc_rdbtnPanel.gridwidth = 3;
		gbc_rdbtnPanel.insets = new Insets(0, 0, 0, 5);
		gbc_rdbtnPanel.fill = GridBagConstraints.BOTH;
		gbc_rdbtnPanel.gridx = 1;
		gbc_rdbtnPanel.gridy = 3;
		formPanel.add(rdbtnPanel, gbc_rdbtnPanel);

		rdbtnPersonal = new JRadioButton("Personal");
		rdbtnPersonal.setBackground(Color.WHITE);
		buttonGroup.add(rdbtnPersonal);
		rdbtnPanel.add(rdbtnPersonal);

		rdbtnTeam = new JRadioButton("Team");
		rdbtnTeam.setBackground(Color.WHITE);
		buttonGroup.add(rdbtnTeam);
		rdbtnPanel.add(rdbtnTeam);

		rdbtnPersonal.setSelected(true);

		updateCategoryList();

		//Date label
		final JLabel lblDate_1 = new JLabel("<html><body style='width: 80px'><font>" + "Start Date" + "</font>" 
				+ "<font color=red>" + "*" + "</font>" 
				+ "<font>" + ":" + "</font></html>");
		lblDate_1.setHorizontalAlignment(SwingConstants.RIGHT);
		final GridBagConstraints gbc_lblDate_1 = new GridBagConstraints();
		gbc_lblDate_1.fill = GridBagConstraints.VERTICAL;
		gbc_lblDate_1.anchor = GridBagConstraints.EAST;
		gbc_lblDate_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblDate_1.gridx = 0;
		gbc_lblDate_1.gridy = 4;
		gbc_lblDate_1.weightx = 1;
		gbc_lblDate_1.weighty = 1;
		formPanel.add(lblDate_1, gbc_lblDate_1);

		//Time label
		final JLabel lblTime = new JLabel("<html><body style='width: 80px'><font>" + "Start Time" + "</font>" 
				+ "<font color=red>" + "*" + "</font>" 
				+ "<font>" + ":" + "</font></html>");
		lblTime.setHorizontalAlignment(SwingConstants.RIGHT);
		final GridBagConstraints gbc_lblTime = new GridBagConstraints();
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
		startHourEditor.getTextField().setBackground(CalendarStandard.CalendarYellow);
		startHourSpinner.setEditor(startHourEditor);
		startHourEditor.getTextField().setFocusLostBehavior(JFormattedTextField.PERSIST);

		JLabel colon = new JLabel(":");
		startSpinnerPanel.add(colon);

		startMinuteSpinner = new JSpinner( new SpinnerDateModelMinute());
		startMinuteSpinner.setFont(new Font("Tahoma", Font.PLAIN, 18));
		startSpinnerPanel.add(startMinuteSpinner);
		startMinuteEditor = new JSpinner.DateEditor(startMinuteSpinner, "mm");
		startMinuteEditor.getTextField().setBackground(CalendarStandard.CalendarYellow);
		startMinuteSpinner.setEditor(startMinuteEditor);
		startMinuteEditor.getTextField().setFocusLostBehavior(JFormattedTextField.PERSIST);

		startAMPMSpinner = new JSpinner(new SpinnerDateModelAMPM());
		startAMPMSpinner.setFont(new Font("Tahoma", Font.PLAIN, 18));
		startSpinnerPanel.add(startAMPMSpinner);
		startAMPMEditor = new JSpinner.DateEditor(startAMPMSpinner, "a");
		startAMPMEditor.getTextField().setBackground(CalendarStandard.CalendarYellow);
		startAMPMSpinner.setEditor(startAMPMEditor);
		startAMPMEditor.getTextField().setFocusLostBehavior(JFormattedTextField.PERSIST);
		final GridBagConstraints gbc_startspinner = new GridBagConstraints();
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
		final GridBagConstraints gbc_lblTimeError = new GridBagConstraints();
		gbc_lblTimeError.insets = new Insets(0, 0, 5, 0);
		gbc_lblTimeError.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblTimeError.gridx = 3;
		gbc_lblTimeError.gridy = 5;
		gbc_lblTimeError.weightx = 1;
		gbc_lblTimeError.weighty = 1;
		formPanel.add(lblTimeError, gbc_lblTimeError);

		//Invalid Date label
		lblDateError = new JLabel("<html><font color='red'>"
				+ "Please enter a valid date (MM/DD/YYYY).</font></html>");
		lblDateError.setVisible(false);
		lblDateError.setHorizontalAlignment(SwingConstants.LEFT);
		final GridBagConstraints gbc_lblDateError = new GridBagConstraints();
		gbc_lblDateError.insets = new Insets(0, 0, 5, 5);
		gbc_lblDateError.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblDateError.gridx = 1;
		gbc_lblDateError.gridy = 5;
		gbc_lblDateError.weightx = 1;
		gbc_lblDateError.weighty = 1;
		formPanel.add(lblDateError, gbc_lblDateError);




		//DatePicker box
		startDatePicker = new JXDatePicker();
		startDatePicker.getEditor().setBackground(CalendarStandard.CalendarYellow);
		final GridBagConstraints gbc_jdp = new GridBagConstraints();
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
		final SimpleDateFormat format1 = new SimpleDateFormat( "MM/dd/yyyy" );
		final SimpleDateFormat format2 = new SimpleDateFormat( "MM.dd.yyyy" );
		startDatePicker.setFormats(new DateFormat[] {format1, format2});


		////////////////////////////////////////////




		//End Date/Time Forms

		//Date label
		final JLabel lblDate_2 = new JLabel("<html><body style='width: 80px'><font>" + "End Date" + "</font>" 
				+ "<font color=red>" + "*" + "</font>" 
				+ "<font>" + ":" + "</font></html>");
		lblDate_2.setHorizontalAlignment(SwingConstants.RIGHT);
		final GridBagConstraints gbc_lblDate_2 = new GridBagConstraints();
		gbc_lblDate_2.fill = GridBagConstraints.VERTICAL;
		gbc_lblDate_2.anchor = GridBagConstraints.EAST;
		gbc_lblDate_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblDate_2.gridx = 0;
		gbc_lblDate_2.gridy = 6;
		gbc_lblDate_2.weightx = 1;
		gbc_lblDate_2.weighty = 1;
		formPanel.add(lblDate_2, gbc_lblDate_2);

		//Time2 label
		final JLabel lblTime2 = new JLabel("<html><body style='width: 80px'><font>" + "End Time" + "</font>" 
				+ "<font color=red>" + "*" + "</font>" 
				+ "<font>" + ":" + "</font></html>");
		lblTime2.setHorizontalAlignment(SwingConstants.RIGHT);
		final GridBagConstraints gbc_lblTime2 = new GridBagConstraints();
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
		endHourEditor.getTextField().setBackground(CalendarStandard.CalendarYellow);
		endHourSpinner.setEditor(endHourEditor);
		endHourEditor.getTextField().setFocusLostBehavior(JFormattedTextField.PERSIST);

		colon = new JLabel(":");
		endSpinnerPanel.add(colon);

		endMinuteSpinner = new JSpinner( new SpinnerDateModelMinute());
		endMinuteSpinner.setFont(new Font("Tahoma", Font.PLAIN, 18));
		endSpinnerPanel.add(endMinuteSpinner);
		endMinuteEditor = new JSpinner.DateEditor(endMinuteSpinner, "mm");
		endMinuteEditor.getTextField().setBackground(CalendarStandard.CalendarYellow);
		endMinuteSpinner.setEditor(endMinuteEditor);
		endMinuteEditor.getTextField().setFocusLostBehavior(JFormattedTextField.PERSIST);

		endAMPMSpinner = new JSpinner(new SpinnerDateModelAMPM());
		endAMPMSpinner.setFont(new Font("Tahoma", Font.PLAIN, 18));
		endSpinnerPanel.add(endAMPMSpinner);
		endAMPMEditor = new JSpinner.DateEditor(endAMPMSpinner, "a");
		endAMPMEditor.getTextField().setBackground(CalendarStandard.CalendarYellow);
		endAMPMSpinner.setEditor(endAMPMEditor);
		endAMPMEditor.getTextField().setFocusLostBehavior(JFormattedTextField.PERSIST);
		final GridBagConstraints gbc_spinner2 = new GridBagConstraints();
		gbc_spinner2.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinner2.insets = new Insets(0, 0, 0, 5);
		gbc_spinner2.gridx = 3;
		gbc_spinner2.gridy = 6;
		gbc_spinner2.weightx = 1.0;
		gbc_spinner2.weighty = 3;
		formPanel.add(endSpinnerPanel, gbc_spinner2);

		//Invalid Time label
		lblTimeError2 = new JLabel(" ");
		lblTimeError2.setHorizontalAlignment(SwingConstants.CENTER);
		final GridBagConstraints gbc_lblTimeError2 = new GridBagConstraints();
		gbc_lblTimeError2.insets = new Insets(0, 0, 5, 0);
		gbc_lblTimeError2.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblTimeError2.gridx = 3;
		gbc_lblTimeError2.gridy = 7;
		gbc_lblTimeError2.weightx = 1;
		gbc_lblTimeError2.weighty = 1;
		formPanel.add(lblTimeError2, gbc_lblTimeError2);

		//Invalid Date label
		lblDateError2 = new JLabel("<html><font color='red'>"
				+ "Please enter a valid date (MM/DD/YYYY).</font></html>");
		lblDateError2.setVisible(false);
		lblDateError2.setHorizontalAlignment(SwingConstants.LEFT);
		final GridBagConstraints gbc_lblDateError2 = new GridBagConstraints();
		gbc_lblDateError2.insets = new Insets(0, 0, 5, 5);
		gbc_lblDateError2.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblDateError2.gridx = 1;
		gbc_lblDateError2.gridy = 7;
		gbc_lblDateError2.weightx = 1;
		gbc_lblDateError2.weighty = 1;
		formPanel.add(lblDateError2, gbc_lblDateError2);

		//DatePicker box
		endDatePicker = new JXDatePicker();
		endDatePicker.getEditor().setBackground(CalendarStandard.CalendarYellow);
		final GridBagConstraints gbc_jdp2 = new GridBagConstraints();
		gbc_jdp2.insets = new Insets(0, 0, 0, 5);
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

		final GregorianCalendar c = new GregorianCalendar();
		c.setTime(inputTime);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		endDatePicker.setDate(c.getTime());
		startDate = c;
		startDatePicker.setDate(c.getTime());

		//Sets time value of end and start spinners
		oldStartTime = new GregorianCalendar();
		oldStartTime.setTime(inputTime);
		setStartDate(oldStartTime);
		oldStartTime = new GregorianCalendar();
		oldStartTime.add(Calendar.MINUTE, 31);
		setEndDate(oldStartTime);
		oldStartTime = new GregorianCalendar();

		//Add Repeat Label
		lblRepeat = new JLabel("<html><body style='width: 80px'>Repetition:</html>");
		lblRepeat.setHorizontalAlignment(SwingConstants.RIGHT);
		final GridBagConstraints gbc_lblRepeat = new GridBagConstraints();
		gbc_lblRepeat.anchor = GridBagConstraints.EAST;
		gbc_lblRepeat.fill = GridBagConstraints.VERTICAL;
		gbc_lblRepeat.insets = new Insets(0, 0, 5, 5);
		gbc_lblRepeat.gridx = 0;
		gbc_lblRepeat.gridy = 8;
		gbc_lblRepeat.weighty = 1;
		formPanel.add(lblRepeat, gbc_lblRepeat);

		//Add Repeat Checkbox
		repeatCheckBox = new JCheckBox("<html><body style='width: 80px'>Repeats?</html>");
		repeatCheckBox.setBackground(Color.WHITE);
		final GridBagConstraints gbc_repeatCheckBox = new GridBagConstraints();
		gbc_repeatCheckBox.gridwidth = 1;
		gbc_repeatCheckBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_repeatCheckBox.insets = new Insets(0, 0, 0, 5);
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
				checkRepeatVsDuration();
				repeatAmt.setEnabled(repeatCheckBox.isSelected());
				if(repeatAmt.getText().equals("") && repeatCheckBox.isSelected()){
					repeatAmt.setText("2");
				}
				checkSaveBtnStatus();
			}

		});

		//Add Repeat type Label
		lblRepeatType = new JLabel("<html><body style='width: 80px'>Repeat Type:</html>");
		lblRepeatType.setHorizontalAlignment(SwingConstants.RIGHT);
		final GridBagConstraints gbc_lblRepeatType = new GridBagConstraints();
		gbc_lblRepeatType.anchor = GridBagConstraints.EAST;
		gbc_lblRepeatType.fill = GridBagConstraints.VERTICAL;
		gbc_lblRepeatType.insets = new Insets(0, 0, 5, 5);
		gbc_lblRepeatType.gridx = 2;
		gbc_lblRepeatType.gridy = 8;
		gbc_lblRepeatType.weighty = 1;
		formPanel.add(lblRepeatType, gbc_lblRepeatType);

		//Add Repeat ComboBox
		final String[] repeatStrings = {"Daily", "Weekly", "Monthly"};
		repeatTypeComboBox = new JComboBox<String>(repeatStrings);
		repeatTypeComboBox.setBackground(CalendarStandard.CalendarYellow);
		repeatTypeComboBox.setSelectedIndex(0);
		final GridBagConstraints gbc_repeatTypeComboBox = new GridBagConstraints();
		gbc_repeatTypeComboBox.gridwidth = 1;
		gbc_repeatTypeComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_repeatTypeComboBox.insets = new Insets(0, 0, 0, 5);
		gbc_repeatTypeComboBox.gridx = 3;
		gbc_repeatTypeComboBox.gridy = 8;
		gbc_repeatTypeComboBox.weightx = 10;
		gbc_repeatTypeComboBox.weighty = 1;
		formPanel.add(repeatTypeComboBox, gbc_repeatTypeComboBox);
		repeatTypeComboBox.setEnabled(false);
		//we only want this active when repeat checkbox is checked
		repeatTypeComboBox.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				checkRepeatDuration();
				checkSaveBtnStatus();

			}

		});


		//Add Repetitions Label
		lblNumberRepetitions = new JLabel("<html><body style='width: 80px'># of Occurrences:</html>");
		lblNumberRepetitions.setHorizontalAlignment(SwingConstants.RIGHT);
		final GridBagConstraints gbc_lblNumberRepetitions = new GridBagConstraints();
		gbc_lblNumberRepetitions.anchor = GridBagConstraints.EAST;
		gbc_lblNumberRepetitions.fill = GridBagConstraints.VERTICAL;
		gbc_lblNumberRepetitions.insets = new Insets(0, 0, 5, 5);
		gbc_lblNumberRepetitions.gridx = 0;
		gbc_lblNumberRepetitions.gridy = 10;
		gbc_lblNumberRepetitions.weighty = 1;
		formPanel.add(lblNumberRepetitions, gbc_lblNumberRepetitions);

		//Add Repeat Text Field
		repeatAmt = new JTextField();
		repeatAmt.setBackground(CalendarStandard.CalendarYellow);
		final GridBagConstraints gbc_repeatAmt = new GridBagConstraints();
		gbc_repeatAmt.gridwidth = 3;
		gbc_repeatAmt.fill = GridBagConstraints.HORIZONTAL;
		gbc_repeatAmt.insets = new Insets(0, 0, 0, 5);
		gbc_repeatAmt.gridx = 1;
		gbc_repeatAmt.gridy = 10;
		gbc_repeatAmt.weightx = 10;
		gbc_repeatAmt.weighty = 1;
		formPanel.add(repeatAmt, gbc_repeatAmt);
		repeatAmt.setEnabled(false);//we only want this active when repeat checkbox is checked
		repeatAmt.addKeyListener(new KeyListener(){

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

		//Invalid Repeat label
		lblRepeatError = new JLabel(" ");
		lblRepeatError.setHorizontalAlignment(SwingConstants.LEFT);
		final GridBagConstraints gbc_lblRepeatError = new GridBagConstraints();
		gbc_lblRepeatError.insets = new Insets(0, 0, 0, 0);
		gbc_lblRepeatError.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblRepeatError.gridx = 1;
		gbc_lblRepeatError.gridy = 9;
		gbc_lblRepeatError.weightx = 0;
		gbc_lblRepeatError.weighty = 0;
		//lblRepeatError.setMaximumSize(new Dimension(10, 10));
		formPanel.add(lblRepeatError, gbc_lblRepeatError);


		buttonPanel = new JPanel(new BorderLayout(30, 0));
		buttonPanel.setBackground(Color.WHITE);
		//Add Event button

		try {
			final Image img = ImageIO.read(getClass().getResource("Save_Icon.png"));
			btnAddEvent = new JButton("Save Event", new ImageIcon(img));
		} catch (IOException ex) {}
		catch(IllegalArgumentException ex){
			btnCancel.setText("Save Event");
		}

		btnAddEvent.setCursor(new Cursor(Cursor.HAND_CURSOR)); 
		// To change cursor as it moves over this button
		btnAddEvent.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addEvent();
			}
		});
		
		btnAddEvent.setEnabled(false);
	

		final GridBagConstraints gbc_btnPanel = new GridBagConstraints();
		gbc_btnPanel.gridwidth = 3;
		gbc_btnPanel.insets = new Insets(0, 0, 0, 5);
		gbc_btnPanel.anchor = GridBagConstraints.CENTER;
		gbc_btnPanel.gridx = 1;
		gbc_btnPanel.gridy = 11;

		//Add Cancel button
		try {
			final Image img = ImageIO.read(getClass().getResource("Cancel_Icon.png"));
			btnCancel = new JButton("Cancel", new ImageIcon(img));
		} catch (IOException ex) {}
		catch(IllegalArgumentException ex){
			btnCancel.setText("Cancel");
		}

		btnCancel.setCursor(new Cursor(Cursor.HAND_CURSOR)); 
		// To change cursor as it moves over this button
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
	 * Method checkRepeatVsDuration.
	 */
	protected void checkRepeatVsDuration() {
		/*GregorianCalendar combinedStart = new GregorianCalendar();
		combinedStart.setTime(startDatePicker.getDate());
		combinedStart.set(Calendar.MINUTE, startTempMin);
		combinedStart.set(Calendar.HOUR, startTempHour);

		GregorianCalendar combinedEnd = new GregorianCalendar();
		combinedEnd.setTime(endDatePicker.getDate());
		combinedEnd.set(Calendar.MINUTE, endTempMin);
		combinedEnd.set(Calendar.HOUR, endTempHour);

		System.out.println(get + ":" + startTempMin + "   " + endTempHour + ":" + endTempMin)*/

		final long diff = getEndDate().getTime().getTime() - getStartDate().getTime().getTime();

		final int diffDays =  (int) (diff / (24 * 1000 * 60 * 60));
		lblRepeatError.setText(" ");
		if(diffDays >= 29){
			repeatCheckBox.setSelected(false);
			repeatTypeComboBox.setEnabled(false);
			repeatAmt.setEnabled(false);
			lblRepeatError.setText("<html><font color='red'>"
					+ "Duration cannot be greater than"
					+ " a month for repeating events.</font></html>");
		}
		else if(diffDays >= 7){
			repeatTypeComboBox.setSelectedIndex(2);
		}
		else if(diffDays >= 1){
			repeatTypeComboBox.setSelectedIndex(1);
		}
		else{
			repeatTypeComboBox.setSelectedIndex(0);
		}


	}

	private void checkRepeatDuration(){

		if(repeatCheckBox.isSelected()){
			final long diff = getEndDate().getTime().getTime() - getStartDate().getTime().getTime();

			final int diffDays =  (int) (diff / (24 * 1000 * 60 * 60));
			lblRepeatError.setText(" ");
			if(diffDays >= 29){
				repeatCheckBox.setSelected(false);
				repeatTypeComboBox.setEnabled(false);
				repeatAmt.setEnabled(false);
				lblRepeatError.setText("<html><font color='red'>"
						+ "Duration cannot be greater than"
						+ " a month for repeating events.</font></html>");
			}
			else if(diffDays >= 7 && repeatTypeComboBox.getSelectedIndex() < 2){
				repeatTypeComboBox.setBackground(Color.getHSBColor(3, 0.3f, 1f));
				lblRepeatError.setText("<html><font color='red'>"
						+ "Frequency must be greater than duration.</font></html>");
			}
			else if(diffDays >= 1 && repeatTypeComboBox.getSelectedIndex() == 0){
				repeatTypeComboBox.setBackground(Color.getHSBColor(3, 0.3f, 1f));
				lblRepeatError.setText("<html><font color='red'>"
						+ "Frequency must be greater than duration.</font></html>");
			}
			else{
				repeatTypeComboBox.setBackground(CalendarStandard.CalendarYellow);
			}
		}
		else{
			lblRepeatError.setText(" ");
			repeatTypeComboBox.setBackground(CalendarStandard.CalendarYellow);
		}
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
				final String txt = startDatePicker.getEditor().getText();
				try {
					startDatePicker.getEditor().commitEdit();

					final GregorianCalendar cal = new GregorianCalendar();
					cal.setTime(startDatePicker.getDate());

					if(cal.get(Calendar.YEAR) < 1900) {
						startDatePicker.getEditor().setText(txt);
					}

					checkStartDatePickerStatus();
					
					updateEndTimeAndDate();
					checkSaveBtnStatus();
				} catch (ParseException e1) {
					checkStartDatePickerStatus();
					
					updateEndTimeAndDate();
					checkSaveBtnStatus();
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
				if(e.getKeyCode() == KeyEvent.VK_ENTER){
					checkStartDatePickerStatus();
					
					updateEndTimeAndDate();
					checkSaveBtnStatus();
				}
			}
		});

		startDatePicker.getEditor().addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				final char vChar = e.getKeyChar();
				if (!(Character.isDigit(vChar)
						|| (vChar == '/')
						|| (vChar == KeyEvent.VK_BACK_SPACE)
						|| (vChar == KeyEvent.VK_DELETE))) {
					e.consume();
				}
			}
		});



		//		//Triggered on enter.
		//		startDatePicker.addActionListener(new ActionListener() {
		//			@Override
		//			public void actionPerformed(ActionEvent e) {
		//				checkStartDatePickerStatus();
		//				checkSaveBtnStatus();
		//			}
		//		});


		//Triggered on change.
		startDatePicker.addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				// TODO Auto-generated method stub
				checkStartDatePickerStatus();
				
				updateEndTimeAndDate();
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
				final String txt = endDatePicker.getEditor().getText();
				try {
					endDatePicker.getEditor().commitEdit();

					final GregorianCalendar cal = new GregorianCalendar();
					cal.setTime(endDatePicker.getDate());

					if(cal.get(Calendar.YEAR) < 1900) {
						endDatePicker.getEditor().setText(txt);
					}

					checkEndDatePickerStatus();
					
					checkEndBeforeStart();
					checkSaveBtnStatus();
				} catch (ParseException e1) {
					checkEndDatePickerStatus();
					
					checkEndBeforeStart();
					checkSaveBtnStatus();
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
				final char vChar = e.getKeyChar();
				if (!(Character.isDigit(vChar)
						|| (vChar == '/')
						|| (vChar == KeyEvent.VK_BACK_SPACE)
						|| (vChar == KeyEvent.VK_DELETE))) {
					e.consume();
				}
			}
		});



		//		//Triggered on enter.
		//		endDatePicker.addActionListener(new ActionListener() {
		//			@Override
		//			public void actionPerformed(ActionEvent e) {
		//				checkEndDatePickerStatus();
		//				checkSaveBtnStatus();
		//				checkEndBeforeStart();
		//
		//			}
		//		});

		//Triggered on change.
		endDatePicker.addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				// TODO Auto-generated method stub
				checkEndDatePickerStatus();
				
				checkEndBeforeStart();
				checkSaveBtnStatus();
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
					//e1.printStackTrace();
				}
			}
		});

		startHourEditor.getTextField().addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				final char vChar = e.getKeyChar();
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
				
				startTempHour = Integer.parseInt(
						startHourEditor.getTextField().getText().toString());
				checkStartTimeSpinnerStatus(startHourSpinner);

				updateEndTimeAndDate();
				checkSaveBtnStatus();
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
					//e1.printStackTrace();
				}
			}
		});

		startMinuteEditor.getTextField().addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				final char vChar = e.getKeyChar();
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
				 final GregorianCalendar cal = new GregorianCalendar();
				 cal.setTime((Date) startMinuteSpinner.getValue());
				 final int currentHour = cal.get(Calendar.HOUR);
				 startTempMin = Integer.parseInt(
						 startMinuteEditor.getTextField().getText().toString());
				 checkStartTimeSpinnerStatus(startMinuteSpinner);

				 if(currentHour == 1) {
					 cal.setTime(getStartDate().getTime());
					 cal.add(Calendar.HOUR, 1);  
					 setStartDate(cal);
				 }

				 if(currentHour == 11) {
					 cal.setTime(getStartDate().getTime());
					 cal.add(Calendar.HOUR, -1);  
					 setStartDate(cal);
				 }
				 
				 updateEndTimeAndDate();
				 checkSaveBtnStatus();

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
				 final char vChar = e.getKeyChar();
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
				 
				 updateEndTimeAndDate();
				 checkSaveBtnStatus();
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
					//e1.printStackTrace();
				}
			}
		});

		endHourEditor.getTextField().addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				final char vChar = e.getKeyChar();
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
				
				endTempHour = Integer.parseInt(endHourEditor.getTextField().getText().toString());
				checkEndTimeSpinnerStatus(endHourSpinner);
				checkEndBeforeStart();
				checkSaveBtnStatus();
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
					//e1.printStackTrace();
				}
			}
		});

		endMinuteEditor.getTextField().addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				final char vChar = e.getKeyChar();
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
				 final GregorianCalendar cal = new GregorianCalendar();
				 cal.setTime((Date) endMinuteSpinner.getValue());
				 final int currentHour = cal.get(Calendar.HOUR);
				 endTempMin = Integer.parseInt(endMinuteEditor.getTextField().getText().toString());
				 checkEndTimeSpinnerStatus(endMinuteSpinner);

				 if(currentHour == 1) {
					 cal.setTime(getEndDate().getTime());
					 cal.add(Calendar.HOUR, 1);  
					 setEndDate(cal);
				 }

				 if(currentHour == 11) {
					 cal.setTime(getEndDate().getTime());
					 cal.add(Calendar.HOUR, -1);  
					 setEndDate(cal);
				 }
				 
				 checkEndBeforeStart();
				 checkSaveBtnStatus();
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
					// e1.printStackTrace();
				 }
			 }
		 });

		 endAMPMEditor.getTextField().addKeyListener(new KeyAdapter() {
			 public void keyTyped(KeyEvent e) {
				 final char vChar = e.getKeyChar();
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
				 
				 checkEndBeforeStart();
				 checkSaveBtnStatus();
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



	/**
	 * Method checkEndBeforeStart.
	 */
	protected void checkEndBeforeStart() {
		if(initFlag){
			if(getEndDate().getTime().before(getStartDate().getTime()))
			{
				setEndDate(getStartDate());
			}
			checkRepeatDuration();
		}
	}

	/**
	 * Method updateEndTimeAndDate.
	 */
	protected void updateEndTimeAndDate() {
		final long diff = getStartDate().getTime().getTime() - oldStartTime.getTime().getTime();
		final GregorianCalendar cal = getEndDate();
		cal.setTime(new Date(cal.getTime().getTime() + diff));

		setEndDate(cal);
		oldStartTime = getStartDate();
		checkRepeatDuration();
	}


	private GregorianCalendar getStartDate()
	{
		final GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(oldStartTime.getTime());
		final GregorianCalendar calTemp = new GregorianCalendar();
		calTemp.setTime(startDatePicker.getDate());
		calTemp.set(Calendar.MILLISECOND, 0);
		calTemp.set(Calendar.SECOND, 0);
		cal.setTime(calTemp.getTime());


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
		final GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(oldStartTime.getTime());
		final GregorianCalendar calTemp = new GregorianCalendar();
		calTemp.setTime(endDatePicker.getDate());
		calTemp.set(Calendar.MILLISECOND, 0);
		calTemp.set(Calendar.SECOND, 0);
		cal.setTime(calTemp.getTime());


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
		startAMPMSpinner.setValue(date.getTime());
		date.set(Calendar.HOUR, 0);
		startMinuteSpinner.setValue(date.getTime());

	}

	private void setEndDate(GregorianCalendar date)
	{
		endDatePicker.setDate(date.getTime());
		endHourSpinner.setValue(date.getTime());
		endAMPMSpinner.setValue(date.getTime());
		date.set(Calendar.HOUR, 0);
		endMinuteSpinner.setValue(date.getTime());
	}


	/**
	 * Controls the enable state of the save button 
	 * by checking all user editable elements in commitment tab.
	 */
	private void checkSaveBtnStatus(){

		if (initFlag){
			if(nameTextField.getText().equals("") ||
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
					//System.out.println("Current commit hour is " 
					//+ editingEvent.getDueDate().get(Calendar.HOUR_OF_DAY));
					//System.out.println("Time in milli is " + calDate.getTimeInMillis());
					//System.out.println("Commit time in milli is " 
					//+ editingEvent.getDueDate().getTimeInMillis());

					//make sure something changed
					if (nameTextField.getText().equals(editingEvent.getName()) 
							&& descriptionTextArea.getText().equals(editingEvent.getDescription())

							&& ((Category)categoryComboBox.getSelectedItem()).getID() 
							== editingEvent.getCategoryID()
							&& getStartDate().getTime().equals(
									editingEvent.getStartTime().getTime())
									&& getEndDate().getTime().equals(
											editingEvent.getEndTime().getTime()))
					{
						btnAddEvent.setEnabled(false);
						return;
					}
				}
				if(repeatCheckBox.isSelected()){
					try {
						if (Integer.parseInt(repeatAmt.getText()) <= 1){
							btnAddEvent.setEnabled(false);
							return;
						}
					} catch (Exception ex){
						btnAddEvent.setEnabled(false);
						return;
					}
					
				}
				
				//checks to see if there are any errors and prevents saving if there are
				if(!checkNoErrors()){
					btnAddEvent.setEnabled(false);
					return;
				}
				btnAddEvent.setEnabled(true);
			}
			
	

		}
	}
	
	/**
	 * Function used to check if there are any errors in any fields
	 * @returns true if no errors
	 */
	private boolean checkNoErrors(){
		boolean result = true;
		//check for repeat event duration error
		if(!lblRepeatError.getText().equals(" ") &&
				!lblRepeatError.getText().equals("<html><font color='red'>"
				+ "Duration cannot be greater than a month for repeating events.</font></html>")){
			//if there is error text displayed
			result = false;
		}
		if(lblDateError.isVisible()){
			//if there is error text displayed
			result = false;
		}
		if(lblDateError2.isVisible()){
			//if there is error text displayed
			result = false;
		}
		return result;
	}


	/**
	 * Create a event tab in editing mode.
	 * @param event Event
	 * @param openedFrom int
	 */
	public EventTab(Event event, int openedFrom) {
		this(openedFrom);

		initFlag = false; //We need this to deal with the nested constructors

		editingEvent = event;
		mode = EditingMode.EDITING;

		nameTextField.setText(editingEvent.getName());
		descriptionTextArea.setText(editingEvent.getDescription());
		if(!editingEvent.getIsPersonal())
		{
			rdbtnTeam.setSelected(true);
		}
		else
		{
			rdbtnPersonal.setSelected(true);
		}


		rdbtnTeam.setEnabled(false);
		rdbtnPersonal.setEnabled(false);

		updateCategoryList();
		
		// gets Caldata
		CalendarData calData;
		if (rdbtnPersonal.isSelected()){
				calData = CalendarDataModel.getInstance().getCalendarData(
						ConfigManager.getConfig().getProjectName() + 
						"-" + ConfigManager.getConfig().getUserName()); 
		}
		else{
			calData = CalendarDataModel.getInstance().getCalendarData(
					ConfigManager.getConfig().getProjectName()); 
		}
		
		if (editingEvent.getCategoryID() != 0){
			categoryComboBox.setSelectedItem(
					calData.getCategories().getCategory(editingEvent.getCategoryID()));
		} else {
			categoryComboBox.setSelectedItem(uncategorized);
		}

		setStartDate(editingEvent.getStartTime());
		startDatePicker.setDate(editingEvent.getStartTime().getTime());
		setEndDate(editingEvent.getEndTime());
		endDatePicker.setDate(editingEvent.getEndTime().getTime());

		//handle repetition fields
		if(event.getIsRepeating()){
			CalendarData repCalData;
			//we need the calData so that we can get the actual repeating event from it
			// the event that the tab was opened with is just a dummy event so that the GUI
			// can display it
			if (rdbtnPersonal.isSelected()){
				repCalData = CalendarDataModel.getInstance().getCalendarData(
						ConfigManager.getConfig().getProjectName() + 
						"-" + ConfigManager.getConfig().getUserName()); 
				isTeamEvent = false;
			}
			else{
				repCalData = CalendarDataModel.getInstance().getCalendarData(
						ConfigManager.getConfig().getProjectName()); 
				isTeamEvent = true;
			}
			editingRepeatingEvent = repCalData.getRepeatingEvents().get(event.getID());
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

		repeatCheckBox.setEnabled(false);
		//Don't want people changing this for now
		// it would not be worth the effort to implement right now
		// we might be able to enable it later

		// Add Delete Button
		try {
			final Image img = ImageIO.read(getClass().getResource("Delete_Icon.png"));
			btnDelete = new JButton("Delete Event", new ImageIcon(img));
		} catch (IOException ex) {}
		catch(IllegalArgumentException ex){
			btnDelete.setText("Delete Event");
		}

		btnDelete.setCursor(new Cursor(Cursor.HAND_CURSOR)); 
		// To change cursor as it moves over this button
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
	 * Updates the category list in the CategoryComboBox
	 */
	protected void updateCategoryList(){
		initFlag = false; //prevents listeners from running
		
		final int selectedCategory;
		
		if(categoryComboBox.getSelectedItem() != null){
			selectedCategory = ((Category) categoryComboBox.getSelectedItem()).getID();
		} else {
			selectedCategory = 0;
		}
		
		//removes the current data from the ComboBox
		categoryComboBox.removeAllItems();

		//adds the "none" category
		categoryComboBox.addItem(uncategorized);

		// gets Caldata
		CalendarData calData;
		if (rdbtnPersonal.isSelected()){
			calData = CalendarDataModel.getInstance().getCalendarData(
					ConfigManager.getConfig().getProjectName() + 
					"-" + ConfigManager.getConfig().getUserName()); 
		}
		else{
			calData = CalendarDataModel.getInstance().getCalendarData(
					ConfigManager.getConfig().getProjectName()); 
		}

		//extracts the category list
		final List<Category> categories = calData.getCategories().getCategories();

		//adds the categories to the comboBox
		for (Category cat:categories){
			categoryComboBox.addItem(cat);
		}

		
		if(selectedCategory != 0){
			categoryComboBox.setSelectedItem(calData.getCategories().getCategory(selectedCategory));
		}
		
		initFlag = true;

	}


	/**
	 * Close this event tab
	
	 */
	protected void removeTab() {
		GUIEventController.getInstance().removeEventTab(this, openedFrom);
	}
	/**
	 * Close this event tab when cancel is hit
	 */
	protected void removeTabCancel() {
		GUIEventController.getInstance().removeEventTab(this, openedFrom);
	}



	/**
	 * Adds listeners for all editable elements in commitment tab.
	 * Calls addTimeSpinnerListeners() and addDatePickerListeners() 
	 * which are helper functions defined outside this method.
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


		rdbtnTeam.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				categoryComboBox.setSelectedIndex(0);
				updateCategoryList();

			}

		});

		rdbtnPersonal.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				categoryComboBox.setSelectedIndex(0);
				updateCategoryList();

			}

		});
		

		//Adds a listener to the tab so we can refresh the category list if it was edited
		addComponentListener(new ComponentListener(){

			@Override
			public void componentResized(ComponentEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void componentMoved(ComponentEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void componentShown(ComponentEvent e) {

				updateCategoryList();

			}

			@Override
			public void componentHidden(ComponentEvent e) {
				// TODO Auto-generated method stub

			}

		});
	}


	/**
	 * Adds new event with information contained in fields
	 */
	private void addEvent() {


		if(nameTextField.getText().equals("") || startDatePicker.getDate() == null){
			return;
		}

		CalendarData calData;
		if (rdbtnPersonal.isSelected()){
			calData = CalendarDataModel.getInstance().getCalendarData(
					ConfigManager.getConfig().getProjectName() + 
					"-" + ConfigManager.getConfig().getUserName()); 
			isTeamEvent = false;
		}
		else{
			calData = CalendarDataModel.getInstance().getCalendarData(
					ConfigManager.getConfig().getProjectName()); 
			isTeamEvent = true;
		}
		//		for(Event event: calData.getEvents().getEvents())
		//		{
		//			System.out.println("Event name: " + event.getName()+", id: "+ event.getId());
		//		}

		//repeat events are handled separately because if the tab is editing a repeating event,
		// then it was opened with a dummy event
		if (repeatCheckBox.isSelected()){
			RepeatingEvent newRepEvent;
			if(mode == EditingMode.ADDING)
			{
				newRepEvent = new RepeatingEvent();
			}
			else {
				newRepEvent = editingRepeatingEvent;
			}

			// set fields
			if(isTeamEvent){
				newRepEvent.setIsPersonal(false);
			}
			else{
				newRepEvent.setIsPersonal(true);
			}

			newRepEvent.setCategoryID(((Category)categoryComboBox.getSelectedItem()).getID());
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
			{
				calData.addRepeatingEvent(newRepEvent);
			}
			else
			{
				calData.getRepeatingEvents().update(newRepEvent);
			}

			UpdateCalendarDataController.getInstance().updateCalendarData(calData);


			this.removeTab();

		} else {

			Event newEvent;
			if(mode == EditingMode.ADDING)
			{
				newEvent = new Event();
			}
			else
			{
				newEvent = editingEvent;
			}

			if(isTeamEvent){
				newEvent.setIsPersonal(false);
			}
			else{
				newEvent.setIsPersonal(true);
			}


			newEvent.setCategoryID(((Category)categoryComboBox.getSelectedItem()).getID());


			newEvent.setDescription(descriptionTextArea.getText());




			//set due date
			newEvent.setStartTime(getStartDate());
			newEvent.setEndTime(getEndDate());
			newEvent.setName(nameTextField.getText());

			if (mode == EditingMode.ADDING)
			{
				calData.addEvent(newEvent);
			}
			else
			{
				calData.getEvents().update(newEvent);
			}

			UpdateCalendarDataController.getInstance().updateCalendarData(calData);


			this.removeTab();
		}
	}


	/**
	 * Method deleteEvent.
	 */
	protected void deleteEvent() {

		CalendarData calData;
		if (rdbtnPersonal.isSelected()){
			calData = CalendarDataModel.getInstance().getCalendarData(
					ConfigManager.getConfig().getProjectName() +
					"-" + ConfigManager.getConfig().getUserName()); 
			isTeamEvent = false;
		}
		else{
			calData = CalendarDataModel.getInstance().getCalendarData(
					ConfigManager.getConfig().getProjectName()); 
			isTeamEvent = true;
		}

		if (repeatCheckBox.isSelected()){
			//repeating events are stored separately so they need to be deleted separately
			calData.getRepeatingEvents().removeEvent(editingRepeatingEvent.getID());
		} else {
			calData.getEvents().removeEvent(editingEvent.getID());
		}

		UpdateCalendarDataController.getInstance().updateCalendarData(calData);
		removeTab();
	}


	/**
	 * Checks text field in datepicker's editor.
	 * @return boolean that indicates whether the input in the editor is valid.
	 */
	private boolean isBadStartInputDate() {
		boolean result;
		Date date = null;
		final GregorianCalendar cal = new GregorianCalendar();
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
		final GregorianCalendar cal = new GregorianCalendar();
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
		if(initFlag){
			if(isBadStartInputDate()) {
				startDatePicker.getEditor().setBackground(Color.getHSBColor(3, 0.3f, 1f));
				lblDateError.setVisible(true);
			}
			else {
				final SimpleDateFormat dt = new SimpleDateFormat("MM/dd/yyyy"); 
				startDatePicker.getEditor().setBackground(CalendarStandard.CalendarYellow);
				startDatePicker.getEditor().setText(dt.format(startDatePicker.getDate()));
				lblDateError.setVisible(false);
			}
		}
	}

	private void checkEndDatePickerStatus() {
		if(initFlag){
			if(isBadEndInputDate()) {
				endDatePicker.getEditor().setBackground(Color.getHSBColor(3, 0.3f, 1f));
				lblDateError2.setVisible(true);
			}
			else {
				final SimpleDateFormat dt = new SimpleDateFormat("MM/dd/yyyy"); 
				endDatePicker.getEditor().setBackground(CalendarStandard.CalendarYellow);
				endDatePicker.getEditor().setText(dt.format(endDatePicker.getDate()));
				lblDateError2.setVisible(false);
			}
		}
	}

	private void checkStartTimeSpinnerStatus(JSpinner spinner) {
		if(initFlag){
			final DateEditor editor = (DateEditor)spinner.getEditor();
			if(isBadInputTime(editor) || startTempHour < 1 || 
					startTempHour > 12 || startTempMin > 59) {
				editor.getTextField().setBackground(Color.getHSBColor(3, 0.3f, 1f));
				lblTimeError.setText("<html><font color='red'>"
						+ "Please enter a valid time.</font></html>");
			}
			else {
				editor.getTextField().setBackground(CalendarStandard.CalendarYellow);
				lblTimeError.setText(" ");
			}
		}
	}

	private void checkEndTimeSpinnerStatus(JSpinner spinner) {
		if(initFlag){
			final DateEditor editor = (DateEditor)spinner.getEditor();
			if(isBadInputTime(editor) || endTempHour < 1 || endTempHour > 12 || endTempMin > 59) {
				editor.getTextField().setBackground(Color.getHSBColor(3, 0.3f, 1f));
				lblTimeError2.setText("<html><font color='red'>"
						+ "Please enter a valid time.</font></html>");
			}
			else {
				editor.getTextField().setBackground(CalendarStandard.CalendarYellow);
				lblTimeError2.setText(" ");
			}
		}
	}

	private boolean isBadInputTime(DateEditor editor) {
		boolean result;
		final SimpleDateFormat format = editor.getFormat();
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



