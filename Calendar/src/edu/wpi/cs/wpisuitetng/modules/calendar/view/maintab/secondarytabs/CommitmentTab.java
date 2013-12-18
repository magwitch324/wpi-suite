/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: CS Anonymous
 ******************************************************************************/
/*
 * Sources:
 * Icons were developed using images obtained at: 
 * [1] https://svn.apache.org/repos/asf/openoffice/
 * symphony/trunk/main/extras/source/gallery/symbols/
 * [2] http://www.clker.com/clipart-red-round.html
 * [3] http://www.iconsdb.com/red-icons/delete-icon.html
 */
package edu.wpi.cs.wpisuitetng.modules.calendar.view.maintab.secondarytabs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
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
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicSpinnerUI;

import org.jdesktop.swingx.JXDatePicker;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.calendar.CalendarStandard;
import edu.wpi.cs.wpisuitetng.modules.calendar.controller.UpdateCalendarDataController;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.Category;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.Event;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.Status;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.CalendarData;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.CalendarDataModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.GUIEventController;

/**
 * Create/edit commitment tab.
 * 
 * @author CS Anonymous
 * @version $Revision: 1.0 $
 */
@SuppressWarnings("serial")
public class CommitmentTab extends JPanel {

	// Main panel for everything.
	private JPanel formPanel;

	// JLabels
	private JLabel lblName;
	private JLabel lblDesc;
	private JLabel lblCategory;
	private JLabel lblType;
	private JLabel lblTime;
	private JLabel lblDate;
	private JLabel lblStatus;
	// Error messages
	private JLabel lblDateError;
	private JLabel lblTimeError;

	// Editable elements
	// Name
	private JTextField nameTextField;
	// Description
	private JTextArea descriptionTextField;
	// Category
	private JComboBox<Category> categoryComboBox;
	// Type
	private JPanel rdbtnPanel;
	private ButtonGroup rdbtnGroup;
	private JRadioButton rdbtnPersonal;
	private JRadioButton rdbtnTeam;
	// Time
	private JPanel spinnerPanel;
	private JSpinner hourSpinner;
	private JSpinner minuteSpinner;
	private JSpinner AMPMSpinner;
	private JSpinner.DateEditor hourEditor;
	private JSpinner.DateEditor minuteEditor;
	private JSpinner.DateEditor AMPMEditor;
	// Date
	private JXDatePicker datePicker;
	// Status
	private JComboBox<String> statusComboBox;

	// Buttons and their panel
	private JPanel buttonPanel;
	private JButton btnSaveCommitment;
	private JButton btnDelete;
	private JButton btnCancel;
	private int openedFrom;

	// Helper variables
	private boolean initFlag; // to keep things from running before we fully
								// initialize
	private boolean isTeamComm;
	private Commitment editingCommitment;
	private EditingMode mode = EditingMode.ADDING;

	/**
	 * @author Tianci
	 */
	public enum EditingMode {
		ADDING, EDITING;
	}

	/**
	 * @author Tianci
	 */
	public enum enumTimeSpinner {
		HOUR, MINUTE, AMPM;
	}

	private int tempHour;
	private int tempMin;
	private boolean hourErr;
	private boolean minuteErr;
	private boolean ampmErr;
	private boolean upArrowAction;
	private boolean downArrowAction;
	private boolean firstRun;
	private Category uncategorized;
	private JLabel colon;
	private JScrollPane scrollPane;

	/**
	 * Create the panel.
	 * 
	 * @param openedFrom
	 *            int
	 * @wbp.parser.constructor
	 */
	public CommitmentTab(int openedFrom) {
		this.openedFrom = openedFrom;
		initFlag = false;
		this.setBackground(Color.WHITE);
		setLayout(new GridBagLayout());
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
		constraints.gridx = 3;
		constraints.weighty = 1;
		constraints.fill = GridBagConstraints.BOTH;
		add(spacePanel2, constraints);

		// form uses GridBagLayout w/ two columns
		final GridBagLayout gbl = new GridBagLayout();
		gbl.rowWeights = new double[] { 0.0, 1.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0,
				0.0, 0.0 };
		gbl.columnWeights = new double[] { 0.0, 1.0 };
		gbl.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl.columnWidths = new int[] { 0, 0 };
		formPanel.setLayout(gbl);

		addLabels();
		addEditableElements();
		setDefaultValuesForEditableElements();
		addEditableElementsListeners();
		addButtonPanel();

		initFlag = true;
	}

	/**
	 * Create a commitment tab in editing mode.
	 * 
	 * @param commToEdit
	 *            Commitment
	 * @param openedFrom
	 *            int
	 */
	public CommitmentTab(Commitment commToEdit, int openedFrom) {
		this(openedFrom);

		initFlag = false; // We need this to deal with the nested constructors

		buttonPanel.add(btnDelete, BorderLayout.LINE_END);

		/**
		 * Initialize Delete Commitment button.////////////////
		 */
		// Load icon, create instance, and set text.
		try {
			final Image img = ImageIO.read(getClass().getResource(
					"Delete_Icon.png"));
			btnDelete = new JButton("Delete Commitment", new ImageIcon(img));
		} catch (IOException ex) {
		} catch (IllegalArgumentException ex) {
			btnDelete.setText("Delete Commitment");
		}

		// To change cursor as it moves over this button
		btnDelete.setCursor(new Cursor(Cursor.HAND_CURSOR));

		// Add listener to perform action when button is pressed.
		btnDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				deleteCommitment();
			}
		});
		btnDelete.setEnabled(false);
		buttonPanel.add(btnDelete, BorderLayout.LINE_END);

		editingCommitment = commToEdit;
		mode = EditingMode.EDITING;

		nameTextField.setText(editingCommitment.getName());
		descriptionTextField.setText(editingCommitment.getDescription());

		if (!editingCommitment.getIsPersonal()) {
			rdbtnTeam.setSelected(true);
		} else {
			rdbtnPersonal.setSelected(true);
		}

		rdbtnTeam.setEnabled(false);
		rdbtnPersonal.setEnabled(false);

		updateCategoryList();

		// gets Caldata
		CalendarData calData;
		if (rdbtnPersonal.isSelected()) {
			calData = CalendarDataModel.getInstance().getCalendarData(
					ConfigManager.getConfig().getProjectName() + "-"
							+ ConfigManager.getConfig().getUserName());
		} else {
			calData = CalendarDataModel.getInstance().getCalendarData(
					ConfigManager.getConfig().getProjectName());
		}

		hourSpinner.setValue(editingCommitment.getDueDate().getTime());
		minuteSpinner.setValue(editingCommitment.getDueDate().getTime());
		AMPMSpinner.setValue(editingCommitment.getDueDate().getTime());
		datePicker.setDate(editingCommitment.getDueDate().getTime());

		statusComboBox.setSelectedIndex(commToEdit.getStatus().id);

		btnDelete.setEnabled(true);

		if (editingCommitment.getCategoryID() != 0) {
			categoryComboBox.setSelectedItem(calData.getCategories()
					.getCategory(editingCommitment.getCategoryID()));
		} else {
			categoryComboBox.setSelectedItem(uncategorized);
		}

		initFlag = true;

	}

	/**
	 * Create all labels in commitment tab.
	 */
	private void addLabels() {
		// Name label
		final JLabel lblName = new JLabel(
				"<html><body style='width: 80px'><font>" + "Name" + "</font>"
						+ "<font color=red>" + "*" + "</font>" + "<font>" + ":"
						+ "</font></html>");
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

		// Description label
		lblDesc = new JLabel(
				"<html><body style='width: 80px'>Description:</html>");
		lblDesc.setHorizontalAlignment(SwingConstants.RIGHT);
		final GridBagConstraints gbc_lblDesc = new GridBagConstraints();
		gbc_lblDesc.fill = GridBagConstraints.BOTH;
		gbc_lblDesc.insets = new Insets(0, 0, 5, 5);
		gbc_lblDesc.gridx = 0;
		gbc_lblDesc.gridy = 1;
		formPanel.add(lblDesc, gbc_lblDesc);

		// Category label
		lblCategory = new JLabel(
				"<html><body style='width: 80px'>Category:</html>");
		lblCategory.setHorizontalAlignment(SwingConstants.RIGHT);
		final GridBagConstraints gbc_lblCategory = new GridBagConstraints();
		gbc_lblCategory.anchor = GridBagConstraints.EAST;
		gbc_lblCategory.insets = new Insets(0, 0, 5, 5);
		gbc_lblCategory.gridx = 0;
		gbc_lblCategory.gridy = 4;
		gbc_lblCategory.weightx = 1;
		gbc_lblCategory.weighty = 1;
		formPanel.add(lblCategory, gbc_lblCategory);

		// Type label
		lblType = new JLabel("<html><body style='width: 80px'>Type:</html>");
		lblType.setHorizontalAlignment(SwingConstants.RIGHT);
		final GridBagConstraints gbc_lblType = new GridBagConstraints();
		gbc_lblType.anchor = GridBagConstraints.EAST;
		gbc_lblType.insets = new Insets(0, 0, 5, 5);
		gbc_lblType.gridx = 0;
		gbc_lblType.gridy = 5;
		formPanel.add(lblType, gbc_lblType);

		// Time label
		final JLabel lblTime = new JLabel(
				"<html><body style='width: 80px'><font>" + "Time" + "</font>"
						+ "<font color=red>" + "*" + "</font>" + "<font>" + ":"
						+ "</font></html>");
		lblTime.setHorizontalAlignment(SwingConstants.RIGHT);
		final GridBagConstraints gbc_lblTime = new GridBagConstraints();
		gbc_lblTime.anchor = GridBagConstraints.EAST;
		gbc_lblTime.fill = GridBagConstraints.VERTICAL;
		gbc_lblTime.insets = new Insets(0, 0, 5, 5);
		gbc_lblTime.gridx = 0;
		gbc_lblTime.gridy = 6;
		gbc_lblTime.weightx = 1;
		gbc_lblTime.weighty = 1;
		formPanel.add(lblTime, gbc_lblTime);

		// Invalid Time label
		lblTimeError = new JLabel(" ");
		lblTimeError.setVisible(true);
		lblTimeError.setHorizontalAlignment(SwingConstants.LEFT);
		final GridBagConstraints gbc_lblTimeError = new GridBagConstraints();
		gbc_lblTimeError.insets = new Insets(0, 0, 5, 0);
		gbc_lblTimeError.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblTimeError.gridx = 1;
		gbc_lblTimeError.gridy = 7;
		gbc_lblTimeError.weightx = 1;
		gbc_lblTimeError.weighty = 1;
		formPanel.add(lblTimeError, gbc_lblTimeError);

		// Date label
		final JLabel lblDate = new JLabel(
				"<html><body style='width: 80px'><font>" + "Date" + "</font>"
						+ "<font color=red>" + "*" + "</font>" + "<font>" + ":"
						+ "</font></html>");
		lblDate.setHorizontalAlignment(SwingConstants.RIGHT);
		final GridBagConstraints gbc_lblDate = new GridBagConstraints();
		gbc_lblDate.fill = GridBagConstraints.VERTICAL;
		gbc_lblDate.anchor = GridBagConstraints.EAST;
		gbc_lblDate.insets = new Insets(0, 0, 5, 5);
		gbc_lblDate.gridx = 0;
		gbc_lblDate.gridy = 8;
		gbc_lblDate.weightx = 1;
		gbc_lblDate.weighty = 1;
		formPanel.add(lblDate, gbc_lblDate);

		// Invalid Date label
		lblDateError = new JLabel(
				"<html><body style='width: 80px'><font color='red'>"
						+ "Please enter a valid date (MM/DD/YYYY).</font></html>");
		lblDateError.setVisible(false);
		lblDateError.setHorizontalAlignment(SwingConstants.LEFT);
		final GridBagConstraints gbc_lblDateError = new GridBagConstraints();
		gbc_lblDateError.insets = new Insets(0, 0, 5, 0);
		gbc_lblDateError.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblDateError.gridx = 1;
		gbc_lblDateError.gridy = 9;
		gbc_lblDateError.weightx = 1;
		gbc_lblDateError.weighty = 1;
		formPanel.add(lblDateError, gbc_lblDateError);

		// Status label
		lblStatus = new JLabel("<html><body style='width: 80px'>Status:</html>");
		// lblStatus.setBackground(CalendarStandard.CalendarRed);
		// lblStatus.setForeground(Color.WHITE);
		final GridBagConstraints gbc_lblStatus = new GridBagConstraints();
		gbc_lblStatus.insets = new Insets(0, 0, 5, 5);
		gbc_lblStatus.fill = GridBagConstraints.VERTICAL;
		gbc_lblStatus.anchor = GridBagConstraints.EAST;
		gbc_lblStatus.gridx = 0;
		gbc_lblStatus.gridy = 10;
		gbc_lblStatus.weightx = 1;
		gbc_lblStatus.weighty = 3;
		formPanel.add(lblStatus, gbc_lblStatus);

	}

	/**
	 * Construct all editable elements in commitment tab without listeners.
	 */
	private void addEditableElements() {

		// Name text field
		nameTextField = new JTextField();
		nameTextField.setBackground(CalendarStandard.CalendarYellow);
		nameTextField.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		final GridBagConstraints gbc_nameTextField = new GridBagConstraints();
		gbc_nameTextField.insets = new Insets(0, 0, 5, 0);
		gbc_nameTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_nameTextField.weightx = 10;
		gbc_nameTextField.weighty = 1;
		gbc_nameTextField.gridx = 1;
		gbc_nameTextField.gridy = 0;
		formPanel.add(nameTextField, gbc_nameTextField);

		// scroll pane for description text field.
		scrollPane = new JScrollPane();
		scrollPane
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setMinimumSize(new Dimension(23, 60));
		scrollPane.setPreferredSize(new Dimension(2, 60));
		final GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridheight = 3;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.insets = new Insets(0, 0, 0, 5);
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 1;
		formPanel.add(scrollPane, gbc_scrollPane);

		// Text area for description. Inside a scroll pane.
		descriptionTextField = new JTextArea();
		scrollPane.setViewportView(descriptionTextField);
		// descriptionTextField.setPreferredSize(new Dimension(500,160));
		// descPane.setViewportView(descriptionTextField);
		descriptionTextField.setLineWrap(true);
		descriptionTextField.setBackground(CalendarStandard.CalendarYellow);

		// Create category box, add two dummy categories
		categoryComboBox = new JComboBox<Category>();
		categoryComboBox.setRenderer(new CategoryComboBoxRenderer());
		categoryComboBox.setBackground(CalendarStandard.CalendarYellow);
		uncategorized = new Category("Uncategorized", Color.WHITE, false);
		uncategorized.setID(0);

		final GridBagConstraints gbc_categoryComboBox = new GridBagConstraints();
		gbc_categoryComboBox.insets = new Insets(0, 0, 5, 0);
		gbc_categoryComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_categoryComboBox.gridx = 1;
		gbc_categoryComboBox.gridy = 4;
		gbc_categoryComboBox.weightx = 10;
		gbc_categoryComboBox.weighty = 1;
		formPanel.add(categoryComboBox, gbc_categoryComboBox);

		// Create radio button panel.
		rdbtnPanel = new JPanel();
		rdbtnPanel.setBackground(Color.WHITE);
		final GridBagConstraints gbc_rdbtnPanel = new GridBagConstraints();
		gbc_rdbtnPanel.insets = new Insets(0, 0, 5, 0);
		gbc_rdbtnPanel.fill = GridBagConstraints.BOTH;
		gbc_rdbtnPanel.gridx = 1;
		gbc_rdbtnPanel.gridy = 5;
		formPanel.add(rdbtnPanel, gbc_rdbtnPanel);
		rdbtnGroup = new ButtonGroup();

		// Create buttons and add to rdbtnGroup.
		rdbtnPersonal = new JRadioButton("Personal");
		rdbtnPersonal.setBackground(Color.WHITE);
		rdbtnGroup.add(rdbtnPersonal);
		rdbtnPanel.add(rdbtnPersonal);
		rdbtnPersonal.setCursor(new Cursor(Cursor.HAND_CURSOR));

		rdbtnTeam = new JRadioButton("Team");
		rdbtnTeam.setBackground(Color.WHITE);
		rdbtnGroup.add(rdbtnTeam);
		rdbtnPanel.add(rdbtnTeam);
		rdbtnTeam.setCursor(new Cursor(Cursor.HAND_CURSOR));

		// Create time spinner panel.
		spinnerPanel = new JPanel();
		spinnerPanel.setBackground(Color.WHITE);
		final GridBagConstraints gbc_spinnerPanel = new GridBagConstraints();
		gbc_spinnerPanel.insets = new Insets(0, 0, 5, 0);
		gbc_spinnerPanel.fill = GridBagConstraints.BOTH;
		gbc_spinnerPanel.gridx = 1;
		gbc_spinnerPanel.gridy = 6;
		formPanel.add(spinnerPanel, gbc_spinnerPanel);
		spinnerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		// Create time spinners, hour, minute, and AM_PM
		hourSpinner = new JSpinner(new SpinnerDateModelHour());
		hourSpinner.setFont(new Font("Tahoma", Font.PLAIN, 18));
		hourSpinner.setUI(new SpinnerUI());
		spinnerPanel.add(hourSpinner);
		hourEditor = new JSpinner.DateEditor(hourSpinner, "hh");
		hourSpinner.setEditor(hourEditor);
		hourEditor.getTextField()
				.setBackground(CalendarStandard.CalendarYellow);
		hourEditor.getTextField().setFocusLostBehavior(
				JFormattedTextField.PERSIST);

		colon = new JLabel(":");
		spinnerPanel.add(colon);

		minuteSpinner = new JSpinner(new SpinnerDateModelMinute());
		minuteSpinner.setFont(new Font("Tahoma", Font.PLAIN, 18));
		minuteSpinner.setUI(new SpinnerUI());
		spinnerPanel.add(minuteSpinner);
		minuteEditor = new JSpinner.DateEditor(minuteSpinner, "mm");
		minuteSpinner.setEditor(minuteEditor);
		minuteEditor.getTextField().setBackground(
				CalendarStandard.CalendarYellow);
		minuteEditor.getTextField().setFocusLostBehavior(
				JFormattedTextField.PERSIST);

		AMPMSpinner = new JSpinner(new SpinnerDateModelAMPM());
		AMPMSpinner.setFont(new Font("Tahoma", Font.PLAIN, 18));
		AMPMSpinner.setUI(new SpinnerUI());
		spinnerPanel.add(AMPMSpinner);
		AMPMEditor = new JSpinner.DateEditor(AMPMSpinner, "a");
		AMPMEditor.getTextField()
				.setBackground(CalendarStandard.CalendarYellow);
		AMPMSpinner.setEditor(AMPMEditor);
		AMPMEditor.getTextField().setFocusLostBehavior(
				JFormattedTextField.PERSIST);

		// Create DatePicker and editor.
		datePicker = new JXDatePicker();
		datePicker.getEditor().setFont(new Font("Tahoma", Font.PLAIN, 13));
		datePicker.getEditor()
				.setFocusLostBehavior(JFormattedTextField.PERSIST);
		datePicker.putClientProperty("JDatePicker.backgroundOnEditable",
				Boolean.TRUE);
		datePicker.getEditor().setBackground(CalendarStandard.CalendarYellow);
		final GridBagConstraints gbc_jdp = new GridBagConstraints();
		gbc_jdp.insets = new Insets(0, 0, 5, 0);
		gbc_jdp.fill = GridBagConstraints.HORIZONTAL;
		gbc_jdp.gridx = 1;
		gbc_jdp.gridy = 8;
		gbc_jdp.weightx = 1;
		gbc_jdp.weighty = 3;
		formPanel.add(datePicker, gbc_jdp);

		// Set acceptable date formats for date picker. Deprecated? @Frank
		final SimpleDateFormat format1 = new SimpleDateFormat("MM/dd/yyyy");
		final SimpleDateFormat format2 = new SimpleDateFormat("MM.dd.yyyy");
		datePicker.setFormats(new DateFormat[] { format1, format2 });

		// Create status combo box.
		final String[] statusStrings = { "New", "In Progress", "Completed" };
		statusComboBox = new JComboBox<String>(statusStrings);
		statusComboBox.setBackground(CalendarStandard.CalendarYellow);
		statusComboBox.setSelectedIndex(0);
		final GridBagConstraints gbc_statusComboBox = new GridBagConstraints();
		gbc_statusComboBox.insets = new Insets(0, 0, 5, 0);
		gbc_statusComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_statusComboBox.gridx = 1;
		gbc_statusComboBox.gridy = 10;
		gbc_statusComboBox.weightx = 1;
		gbc_statusComboBox.weighty = 3;
		formPanel.add(statusComboBox, gbc_statusComboBox);

		// updates the list of categories
		// this is here to avoid a NullPointerException
		updateCategoryList();
	}

	/**
	 * Adds listeners for all editable elements in commitment tab. Calls
	 * addTimeSpinnerListeners() and addDatePickerListeners() which are helper
	 * functions defined outside this method.
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

		descriptionTextField.addKeyListener(new KeyListener() {
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

		categoryComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				checkSaveBtnStatus();

			}
		});

		categoryComboBox.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				checkSaveBtnStatus();

			}

		});

		addTimeSpinnerListeners();
		addDatePickerListeners();

		statusComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				checkSaveBtnStatus();
			}
		});

		rdbtnTeam.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				categoryComboBox.setSelectedIndex(0);
				updateCategoryList();
			}

		});

		rdbtnPersonal.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				categoryComboBox.setSelectedIndex(0);
				updateCategoryList();

			}

		});

		// Adds a listener to the tab so we can refresh the category list if it
		// was edited
		addComponentListener(new ComponentListener() {

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
	 * Sets default values like date and time for spinners and date picker. Must
	 * set values after listeners are added because automatic rounding of the
	 * minute spinner is done by a listener.
	 */
	private void setDefaultValuesForEditableElements() {
		rdbtnPersonal.setSelected(true);
		final GregorianCalendar cal = new GregorianCalendar();
		hourSpinner.setValue(cal.getTime());
		minuteSpinner.setValue(cal.getTime());
		AMPMSpinner.setValue(cal.getTime());
		datePicker.setDate(cal.getTime());
		cal.setTime((Date) minuteSpinner.getValue());
		tempHour = cal.get(Calendar.HOUR);
		tempMin = cal.get(Calendar.MINUTE);
		hourErr = false;
		minuteErr = false;
		ampmErr = false;
	}

	private void addButtonPanel() {
		/**
		 * Adds the button panel to Commitment tab. Delete button is disabled on
		 * default. This method adds listeners for the buttons to as the
		 * listeners are all relatively short.
		 */

		/**
		 * Initialize button panel instance and its constraints.
		 */
		buttonPanel = new JPanel(new BorderLayout(30, 0));
		buttonPanel.setBackground(Color.WHITE);
		final GridBagConstraints gbc_btnPanel = new GridBagConstraints();
		gbc_btnPanel.anchor = GridBagConstraints.CENTER;
		gbc_btnPanel.gridx = 1;
		gbc_btnPanel.gridy = 11;

		/**
		 * Initialize Save Commitment button.////////////////
		 */

		// Load icon, create instance, and set text.
		try {
			final Image img = ImageIO.read(getClass().getResource(
					"Save_Icon.png"));
			btnSaveCommitment = new JButton("Save Commitment", new ImageIcon(
					img));
		} catch (IOException ex) {
		} catch (IllegalArgumentException ex) {
			btnSaveCommitment.setText("Save Commitment");
		}
		// To change cursor as it moves over this button
		btnSaveCommitment.setCursor(new Cursor(Cursor.HAND_CURSOR));

		// Add listener to perform action when button is pressed.
		btnSaveCommitment.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addCommitment();
			}
		});
		// AddCommitment button disabled on default.
		btnSaveCommitment.setEnabled(false);

		/**
		 * Initialize Cancel button.////////////////
		 */

		// Load icon, create instance, and set text.
		try {
			final Image img = ImageIO.read(getClass().getResource(
					"Cancel_Icon.png"));
			btnCancel = new JButton("Cancel", new ImageIcon(img));
		} catch (IOException ex) {
		} catch (IllegalArgumentException ex) {
			btnCancel.setText("Cancel");
		}

		// To change cursor as it moves over this button
		btnCancel.setCursor(new Cursor(Cursor.HAND_CURSOR));

		// Add listener to perform action when button is pressed.
		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				removeTab();
			}
		});

		/**
		 * Initialize Delete Commitment button.////////////////
		 */
		// Load icon, create instance, and set text.
		try {
			final Image img = ImageIO.read(getClass().getResource(
					"Delete_Icon.png"));
			btnDelete = new JButton("Delete Commitment", new ImageIcon(img));
		} catch (IOException ex) {
		} catch (IllegalArgumentException ex) {
			btnDelete.setText("Delete Commitment");
		}

		// To change cursor as it moves over this button
		btnDelete.setCursor(new Cursor(Cursor.HAND_CURSOR));

		// Add listener to perform action when button is pressed.
		btnDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				deleteCommitment();
			}
		});
		btnDelete.setEnabled(false);

		/**
		 * Add buttons to button panel, and add button panel to main panel for
		 * commitment tab. Delete button is disabled on default.
		 */
		buttonPanel.add(btnSaveCommitment, BorderLayout.WEST);
		buttonPanel.add(btnCancel, BorderLayout.CENTER);
		// Set the horizontal gap
		formPanel.add(buttonPanel, gbc_btnPanel);
	}

	/**
	 * Helper function that sets up listeners only for time spinners. Time
	 * spinners include hour, minute, and AMPM. Java listeners are not
	 * guaranteed to be called in a defined order. For this particular code, the
	 * call order tends to be:
	 * 
	 * If mouse clicked off from form to submit change: (When input has not
	 * changed) Focus gained -> focus lost -> commit edit (When input is within
	 * range) Focus gained -> Focus lost -> commit edit -> State changed (When
	 * input is out of range) Focus gained -> Focus lost -> commit edit -> State
	 * changed If hit enter key to submit change: (When input has not changed)
	 * focus gained -> enter key -> focus colon -> action performed -> focus
	 * lost -> commit edit (When input is within range) focus gained -> enter
	 * key -> focus colon -> state changed -> ActionPerformed -> Focus lost ->
	 * commit edit (When input is out of range) focus gained -> enter key ->
	 * focus colon -> state changed -> ActionPerformed -> Focus lost -> commit
	 * edit -> state changed If arrow button to submit change: (When input has
	 * not changed) up/down button started -> up/down button finished -> state
	 * changed ->focus lost -> commit edit (When input is within range) up/down
	 * button started -> state changed -> up/down button finished -> state
	 * changed -> focus lost -> commit edit (When input is out of range) up/down
	 * button started -> state changed -> up/down button finished -> state
	 * changed -> state changed -> focus lost -> commit edit
	 */

	private void addTimeSpinnerListeners() {

		hourEditor.getTextField().addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
				// System.out.println("Hour Editor text field focus gained");
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (upArrowAction || downArrowAction) {
					upArrowAction = false;
					downArrowAction = false;
				} else {
					try {
						// System.out.println("Focus lost, arrow actions set to false, commit edit");
						hourEditor.getTextField().commitEdit();
						// System.out.println("focus lost commit edit finished,
						// tempHour is " + tempHour + " ");
						checkTimeSpinnerStatus(hourSpinner,
								enumTimeSpinner.HOUR);
						checkSaveBtnStatus();
					} catch (ParseException e1) {
						checkTimeSpinnerStatus(hourSpinner,
								enumTimeSpinner.HOUR);
						checkSaveBtnStatus();
						e1.printStackTrace();
					}
				}
			}
		});

		hourEditor.getTextField().addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				final char vChar = e.getKeyChar();
				if (!(Character.isDigit(vChar)
						|| (vChar == KeyEvent.VK_BACK_SPACE) || (vChar == KeyEvent.VK_DELETE))) {
					e.consume();
				}
			}
		});

		// Change listener is called by commitEdit
		hourSpinner.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				GregorianCalendar cal = new GregorianCalendar();
				cal.setTime((Date) hourSpinner.getValue());

				boolean AMPMFlag;
				final int currentAMPM = cal.get(Calendar.AM_PM);

				// System.out.println("change event, arrow is "
				// + upArrowAction + " " + downArrowAction + " temp is " +
				// tempHour);
				if (upArrowAction || downArrowAction) {
					// System.out.println("Arrow action mode");
					if (firstRun) {
						// System.out.println("FIRST RUN!!");
						if ((Integer.parseInt(hourEditor.getTextField()
								.getText()) == 11 && upArrowAction)
								|| (Integer.parseInt(hourEditor.getTextField()
										.getText()) == 12 && downArrowAction)) {
							// System.out.println("HourSpinner rollover, " +
							// upArrowAction + " " + downArrowAction +
							// " text is " +
							// AMPMEditor.getTextField().getText());
							cal.setTime((Date) AMPMSpinner.getValue());
							cal.add(Calendar.AM_PM, 1);
							AMPMSpinner.setValue(cal.getTime());
						}

						if (Integer.parseInt(hourEditor.getTextField()
								.getText()) > 12) {
							// System.out.println("Larger than 12.");
							hourEditor.getTextField().setBackground(
									Color.getHSBColor(3, 0.3f, 1f));
							lblTimeError
									.setText("<html><font color='red'>"
											+ "Please enter a valid time.</font></html>");
							hourErr = true;
						} else {
							lblTimeError.setText(" ");
							hourEditor.getTextField().setBackground(
									CalendarStandard.CalendarYellow);
							hourErr = false;
						}
						firstRun = false;
					}

					// ///
					// boolean AMPMFlag;
					cal = new GregorianCalendar();
					cal.setTime((Date) hourSpinner.getValue());

					if (cal.get(Calendar.HOUR) == 12
							|| cal.get(Calendar.HOUR) == 01) {
						// System.out.println("AMPMflag is true.
						// cal time is " + cal.getTime().toString());
						AMPMFlag = true;
					} else {
						AMPMFlag = false;
					}

					if (AMPMFlag) {
						cal.setTime((Date) AMPMSpinner.getValue());
						cal.add(Calendar.AM_PM, 1);
						AMPMSpinner.setValue(cal.getTime());
					}

					// checkTimeSpinnerStatus(hourSpinner,
					// enumTimeSpinner.HOUR);
					checkSaveBtnStatus();
				} else {
					// System.out.println("tempHour set to text field "
					// + hourEditor.getTextField().getText());
					tempHour = Integer.parseInt(hourEditor.getTextField()
							.getText().toString());
					checkTimeSpinnerStatus(hourSpinner, enumTimeSpinner.HOUR);
					// System.out.println("Cal time is " +
					// cal.getTime().toString());

					if (cal.get(Calendar.AM_PM) == 1) {
						// System.out.println("Hourflag is true. cal time is "
						// + cal.getTime().toString());
						AMPMFlag = true;
					} else {
						AMPMFlag = false;
					}

					if (AMPMFlag) {
						cal.setTime((Date) AMPMSpinner.getValue());
						cal.add(Calendar.AM_PM, 1);
						AMPMSpinner.setValue(cal.getTime());
					}
					checkSaveBtnStatus();
				}
			}
		});

		minuteEditor.getTextField().addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (upArrowAction || downArrowAction) {
					upArrowAction = false;
					downArrowAction = false;
				} else {
					try {
						// System.out.println("Focus lost, arrow actions set to false, commit edit");

						minuteEditor.getTextField().commitEdit();
						// System.out.println("focus lost commit edit finished,
						// tempMINUTE is " + tempMin + " ");
						checkTimeSpinnerStatus(minuteSpinner,
								enumTimeSpinner.MINUTE);
						checkSaveBtnStatus();
					} catch (ParseException e1) {
						checkTimeSpinnerStatus(minuteSpinner,
								enumTimeSpinner.MINUTE);
						checkSaveBtnStatus();
						e1.printStackTrace();
					}
				}
			}
		});

		minuteEditor.getTextField().addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				final char vChar = e.getKeyChar();
				if (!(Character.isDigit(vChar)
						|| (vChar == KeyEvent.VK_BACK_SPACE) || (vChar == KeyEvent.VK_DELETE))) {
					e.consume();
				}
			}
		});

		/**
		 * Rounds minute input to the closest 30s everytime the text field
		 * changes.
		 */
		minuteSpinner.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {

				boolean hourFlag;
				final GregorianCalendar cal = new GregorianCalendar();
				cal.setTime((Date) minuteSpinner.getValue());
				int currentHour = cal.get(Calendar.HOUR);

				// System.out.println("change event, arrow is " + upArrowAction
				// + " " + downArrowAction + " temp is " + tempMin);
				if (upArrowAction || downArrowAction) {
					// System.out.println("Arrow action mode");
					if (firstRun) {
						if (Integer.parseInt(minuteEditor.getTextField()
								.getText()) == 59 && upArrowAction) {
							cal.setTime((Date) hourSpinner.getValue());
							cal.add(Calendar.HOUR, 1);
							hourSpinner.setValue(cal.getTime());
						}
						if (Integer.parseInt(minuteEditor.getTextField()
								.getText()) == 00 && downArrowAction) {
							cal.setTime((Date) hourSpinner.getValue());
							cal.add(Calendar.HOUR, -1);
							hourSpinner.setValue(cal.getTime());
						}
						if (Integer.parseInt(minuteEditor.getTextField()
								.getText()) > 59) {
							minuteEditor.getTextField().setBackground(
									Color.getHSBColor(3, 0.3f, 1f));
							lblTimeError
									.setText("<html><font color='red'>"
											+ "Please enter a valid time.</font></html>");
							minuteErr = true;
						} else {
							lblTimeError.setText(" ");
							minuteEditor.getTextField().setBackground(
									CalendarStandard.CalendarYellow);
							minuteErr = false;
						}
						firstRun = false;
					}

					// checkTimeSpinnerStatus(minuteSpinner,
					// enumTimeSpinner.HOUR);
					checkSaveBtnStatus();
				} else {
					checkSaveBtnStatus();
					// System.out.println("tempMin set to text field "
					// + minuteEditor.getTextField().getText());
					tempMin = Integer.parseInt(minuteEditor.getTextField()
							.getText().toString());
					cal.setTime((Date) minuteSpinner.getValue());
					currentHour = cal.get(Calendar.HOUR);

					// System.out.println("BEFORE check , tempMin is " +
					// tempMin);
					checkTimeSpinnerStatus(minuteSpinner,
							enumTimeSpinner.MINUTE);

					// System.out.println("Cal time is " +
					// cal.getTime().toString());

					if (cal.get(Calendar.MINUTE) == 59
							|| cal.get(Calendar.MINUTE) == 00) {
						// System.out.println("Hourflag is true. cal time is "
						// + cal.getTime().toString());
						hourFlag = true;
					} else {
						hourFlag = false;
					}

					if (hourFlag) {
						if (currentHour == 1) {
							cal.setTime((Date) hourSpinner.getValue());
							cal.add(Calendar.HOUR, 1);
							hourSpinner.setValue(cal.getTime());
						}

						if (currentHour == 11) {
							cal.setTime((Date) hourSpinner.getValue());
							cal.add(Calendar.HOUR, -1);
							hourSpinner.setValue(cal.getTime());
						}
					}
				}
				checkSaveBtnStatus();
			}
		});

		AMPMEditor.getTextField().addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
			}

			@Override
			public void focusLost(FocusEvent e) {
				// System.out.println("focus lost, committing.");
				try {
					upArrowAction = false;
					downArrowAction = false;
					AMPMEditor.getTextField().commitEdit();
					checkTimeSpinnerStatus(AMPMSpinner, enumTimeSpinner.AMPM);
					checkSaveBtnStatus();
				} catch (ParseException e1) {
					checkTimeSpinnerStatus(AMPMSpinner, enumTimeSpinner.AMPM);
					checkSaveBtnStatus();
				}
			}
		});

		AMPMEditor.getTextField().addKeyListener(new KeyAdapter() {

			public void keyTyped(KeyEvent e) {
				final char vChar = e.getKeyChar();
				if (!((vChar == 'A') || (vChar == 'P') || (vChar == 'M')
						|| (vChar == 'a') || (vChar == 'p') || (vChar == 'm')
						|| (vChar == KeyEvent.VK_BACK_SPACE) || (vChar == KeyEvent.VK_DELETE))) {
					e.consume();
				}
			}
		});

		// Some edit specific listeners
		// These are here to avoid possible NullPointer exceptions while opening
		// the tab
		AMPMSpinner.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				// System.out.println("change happened, checking status.");
				checkTimeSpinnerStatus(AMPMSpinner, enumTimeSpinner.AMPM);
				checkSaveBtnStatus();
			}
		});
	}

	/**
	 * Helper function that sets up listeners only for date picker.
	 */
	private void addDatePickerListeners() {

		// Mouse focus handler
		datePicker.getEditor().addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
			}

			@Override
			public void focusLost(FocusEvent e) {
				final String txt = datePicker.getEditor().getText();
				try {
					datePicker.getEditor().commitEdit();

					final GregorianCalendar cal = new GregorianCalendar();
					cal.setTime(datePicker.getDate());

					if (cal.get(Calendar.YEAR) < 1900) {
						datePicker.getEditor().setText(txt);
					}

					checkDatePickerStatus();
					checkSaveBtnStatus();
				} catch (ParseException e1) {
					checkDatePickerStatus();
					checkSaveBtnStatus();
				}

			}
		});

		// Real time keyboard input check
		datePicker.getEditor().addKeyListener(new KeyListener() {

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

		datePicker.getEditor().addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				final char vChar = e.getKeyChar();
				if (!(Character.isDigit(vChar) || (vChar == '/')
						|| (vChar == KeyEvent.VK_BACK_SPACE) || (vChar == KeyEvent.VK_DELETE))) {
					e.consume();
				}
			}
		});

		// Triggered on enter.
		datePicker.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				checkDatePickerStatus();
				checkSaveBtnStatus();
			}
		});
	}

	/**
	 * Updates the category list in the CategoryComboBox
	 */
	protected void updateCategoryList() {

		final boolean currentInitFlag = initFlag;

		final int selectedCategory;

		if (categoryComboBox.getSelectedItem() != null) {
			selectedCategory = ((Category) categoryComboBox.getSelectedItem())
					.getID();
		} else {
			selectedCategory = 0;
		}

		if (currentInitFlag) {
			initFlag = false;// prevents Listeners from running
		}

		// removes the current data from the ComboBox
		categoryComboBox.removeAllItems();

		// adds the "none" category
		categoryComboBox.addItem(uncategorized);

		// gets Caldata
		CalendarData calData;
		if (rdbtnPersonal.isSelected()) {
			calData = CalendarDataModel.getInstance().getCalendarData(
					ConfigManager.getConfig().getProjectName() + "-"
							+ ConfigManager.getConfig().getUserName());
		} else {
			calData = CalendarDataModel.getInstance().getCalendarData(
					ConfigManager.getConfig().getProjectName());
		}

		// extracts the category list
		final List<Category> categories = calData.getCategories()
				.getCategories();

		// adds the categories to the comboBox
		for (Category cat : categories) {
			categoryComboBox.addItem(cat);
		}

		if (selectedCategory != 0) {
			categoryComboBox.setSelectedItem(calData.getCategories()
					.getCategory(selectedCategory));
		}

		initFlag = currentInitFlag;

	}

	/**
	 * Close this commitment tab
	 */
	protected void removeTab() {
		GUIEventController.getInstance().removeCommTab(this, openedFrom);
	}

	/**
	 * Adds new commitment with information contained in fields.
	 */
	private void addCommitment() {
		// TODO Auto-generated method stub

		if (nameTextField.getText().equals("") || datePicker.getDate() == null) {
			return;
		}

		CalendarData calData;
		if (rdbtnPersonal.isSelected()) {
			calData = CalendarDataModel.getInstance().getCalendarData(
					ConfigManager.getConfig().getProjectName() + "-"
							+ ConfigManager.getConfig().getUserName());
			isTeamComm = false;
		} else {
			calData = CalendarDataModel.getInstance().getCalendarData(
					ConfigManager.getConfig().getProjectName());
			isTeamComm = true;
		}
		for (Commitment comm : calData.getCommitments().getCommitments()) {
			// System.out.println("Commitment name: " + comm.getName() +
			// ", id: " + comm.getID());
		}
		Commitment newComm;
		if (mode == EditingMode.ADDING) {
			newComm = new Commitment();
		} else {
			newComm = editingCommitment;
		}

		if (isTeamComm) {
			newComm.setIsPersonal(false);
		} else {
			newComm.setIsPersonal(true);
		}

		newComm.setCategoryID(((Category) categoryComboBox.getSelectedItem())
				.getID());

		newComm.setDescription(descriptionTextField.getText());

		newComm.setStatus(Status.getStatusValue(statusComboBox
				.getSelectedIndex()));

		// Parse date and time info
		final GregorianCalendar calDate = new GregorianCalendar();
		final GregorianCalendar calHour = new GregorianCalendar();
		final GregorianCalendar calMinute = new GregorianCalendar();
		final GregorianCalendar calAMPM = new GregorianCalendar();

		calDate.setTime(datePicker.getDate());
		calHour.setTime((Date) hourSpinner.getValue());
		calMinute.setTime((Date) minuteSpinner.getValue());
		calAMPM.setTime((Date) AMPMSpinner.getValue());

		calDate.set(Calendar.HOUR, calHour.get(Calendar.HOUR));
		calDate.set(Calendar.MINUTE, calMinute.get(Calendar.MINUTE));
		calDate.set(Calendar.AM_PM, calAMPM.get(Calendar.AM_PM));

		// //System.out.println("AMPM is " + calHour.get(Calendar.AM_PM));
		// //System.out.println("24h is " + calHour.get(Calendar.HOUR));

		// set due date
		newComm.setDueDate(calDate);
		newComm.setName(nameTextField.getText());

		if (mode == EditingMode.ADDING) {
			calData.addCommitment(newComm);

			/**
			 * COMMENT THIS OUT TO NOT ADD A LOT OF COMMITMENTS The script to
			 * add a bunch of commitments
			 */
			GregorianCalendar day = new GregorianCalendar(2013,
					Calendar.JANUARY, 1, 8, 00, 00);
			GregorianCalendar lastDay = new GregorianCalendar();
			lastDay.setTime(day.getTime());
			lastDay.add(Calendar.YEAR, 1);
			Random rnd = new Random();
			String[] commitments = { "Meeting", "Party", "Shindig", "Meal" };
			String[] names = { "Anthony", "Andrew", "Frank", "Julie", "Pavel",
					"Sam", "Sean", "Seiichiro", "Thom", "Teresa", "Tim",
					"Tucker", "Coach Mike" };
			while (day.before(lastDay)) {
				CalendarStandard.printcalendar(lastDay);
				GregorianCalendar set = new GregorianCalendar();
				set.setTime(day.getTime());
				set.add(Calendar.HOUR, rnd.nextInt(10));
				String commitment = commitments[rnd.nextInt(4)];
				String name = names[rnd.nextInt(13)];

				CalendarData calendarData;
				if (rdbtnPersonal.isSelected()) {
					calendarData = CalendarDataModel.getInstance()
							.getCalendarData(
									ConfigManager.getConfig().getProjectName()
											+ "-"
											+ ConfigManager.getConfig()
													.getUserName());
				} else {
					calendarData = CalendarDataModel.getInstance()
							.getCalendarData(
									ConfigManager.getConfig().getProjectName());
				}

				int catIndex = rnd
						.nextInt(calData.getCategories().getSize());

				if (rnd.nextInt(2) == 1) { // add event
					GregorianCalendar endTime = new GregorianCalendar();
					endTime.setTime(set.getTime());
					endTime.add(Calendar.HOUR, rnd.nextInt(4) + 1);
					String[] people = { names[rnd.nextInt(13)],
							names[rnd.nextInt(13)], names[rnd.nextInt(13)] };
					Event newEvent = new Event("A long " + commitment,
							"Event with " + people[0] + ", " + people[1]
									+ ", and " + people[2], set, endTime,
							people, calendarData.getCategories()
									.getElementAt(catIndex).getID(),
							rdbtnPersonal.isSelected());
					calData.addEvent(newEvent);
				} else {
					Commitment newCommitment = new Commitment(commitment
							+ " with " + name, set, "No Description",
							calendarData.getCategories().getElementAt(catIndex)
									.getID(), rdbtnPersonal.isSelected());
					calData.addCommitment(newCommitment);
				}

				day.add(Calendar.DAY_OF_YEAR, rnd.nextInt(7));
			}
			// END SCRIPT

		} else {
			calData.getCommitments().update(newComm);
		}

		UpdateCalendarDataController.getInstance().updateCalendarData(calData);

		this.removeTab();

	}

	/**
	 * Delete a commitment.
	 */
	protected void deleteCommitment() {
		CalendarData calData;
		if (rdbtnPersonal.isSelected()) {
			calData = CalendarDataModel.getInstance().getCalendarData(
					ConfigManager.getConfig().getProjectName() + "-"
							+ ConfigManager.getConfig().getUserName());
			isTeamComm = false;
		} else {
			calData = CalendarDataModel.getInstance().getCalendarData(
					ConfigManager.getConfig().getProjectName());
			isTeamComm = true;
		}
		calData.getCommitments().removeCommmitment(editingCommitment.getID());
		UpdateCalendarDataController.getInstance().updateCalendarData(calData);
		removeTab();
	}

	/**
	 * Controls the enable state of the save button by checking all user
	 * editable elements in commitment tab.
	 */
	private void checkSaveBtnStatus() {

		if (initFlag) {
			if (nameTextField.getText().equals("")
					|| datePicker.getDate() == null
					|| nameTextField.getText().trim().length() == 0 || hourErr
					|| minuteErr || ampmErr || lblDateError.isVisible()) {
				btnSaveCommitment.setEnabled(false);
			} else {
				if (mode == EditingMode.EDITING) {
					// get some date data
					// Parse date and time info
					final GregorianCalendar calDate = new GregorianCalendar();
					final GregorianCalendar calHour = new GregorianCalendar();
					final GregorianCalendar calMinute = new GregorianCalendar();
					final GregorianCalendar calAMPM = new GregorianCalendar();

					calDate.setTime(datePicker.getDate());
					calHour.setTime((Date) hourSpinner.getValue());
					calMinute.setTime((Date) minuteSpinner.getValue());
					calAMPM.setTime((Date) AMPMSpinner.getValue());

					calDate.set(Calendar.HOUR, calHour.get(Calendar.HOUR));
					calDate.set(Calendar.MINUTE, calMinute.get(Calendar.MINUTE));
					calDate.set(Calendar.AM_PM, calAMPM.get(Calendar.AM_PM));

					if (nameTextField.getText().equals(
							editingCommitment.getName())
							&& descriptionTextField.getText().equals(
									editingCommitment.getDescription())
							&& ((Category) categoryComboBox.getSelectedItem())
									.getID() == editingCommitment
									.getCategoryID()
							&& Status.getStatusValue(
									statusComboBox.getSelectedIndex()).equals(
									editingCommitment.getStatus())
							&& calDate.getTime().equals(
									editingCommitment.getDueDate().getTime())) {
						btnSaveCommitment.setEnabled(false);
						return;
					}
				}
				btnSaveCommitment.setEnabled(true);
			}

		}
	}

	/**
	 * Checks text field in datepicker's editor.
	 * 
	 * @return boolean that indicates whether the input in the editor is valid.
	 */
	private boolean isBadInputDate() {
		boolean result;
		Date date = null;
		final GregorianCalendar cal = new GregorianCalendar();
		if (datePicker.getDate() == null) {
			result = true;
		} else {
			cal.setTime(datePicker.getDate());
			for (DateFormat formatter : datePicker.getFormats()) {
				try {
					formatter.setLenient(false);
					date = formatter.parse(datePicker.getEditor().getText());
				} catch (ParseException pe) {
					// try next formatter
				} catch (NullPointerException ne) {
					result = false;
				}

				if (date != null) {
					break;
				}
			}
		}

		if (date == null || cal.get(Calendar.YEAR) < 1900
				|| cal.get(Calendar.YEAR) > 9999) {
			result = true;
		} else {
			result = false;
		}
		return result;
	}

	private void checkDatePickerStatus() {
		if (isBadInputDate()) {
			datePicker.getEditor()
					.setBackground(Color.getHSBColor(3, 0.3f, 1f));
			lblDateError.setVisible(true);
		} else {
			final SimpleDateFormat dt = new SimpleDateFormat("MM/dd/yyyy");
			datePicker.getEditor().setBackground(
					CalendarStandard.CalendarYellow);
			datePicker.getEditor().setText(dt.format(datePicker.getDate()));
			lblDateError.setVisible(false);
		}
	}

	private boolean isBadInputTime(DateEditor editor) {
		boolean result;
		final SimpleDateFormat format = editor.getFormat();
		Date date = null;

		try {
			date = format.parse(editor.getTextField().getText().trim());
		} catch (ParseException e) {
		} catch (NullPointerException ne) {
			result = false;
		}

		if (date == null) {
			result = true;
		} else {
			result = false;
		}
		return result;
	}

	private boolean checkTimeSpinnerStatus(JSpinner spinner,
			enumTimeSpinner type) {
		// System.out.println("checking time spinner status, tempMin is " +
		// tempMin);
		switch (type) {
		case HOUR:
			if (initFlag) {
				final DateEditor editor = (DateEditor) spinner.getEditor();
				if (isBadInputTime(editor) || tempHour < 1 || tempHour > 12) {
					editor.getTextField().setBackground(
							Color.getHSBColor(3, 0.3f, 1f));
					lblTimeError.setText("<html><font color='red'>"
							+ "Please enter a valid time.</font></html>");
					hourErr = true;
				} else {
					editor.getTextField().setBackground(
							CalendarStandard.CalendarYellow);
					hourErr = false;
					if (!minuteErr && !ampmErr) {
						lblTimeError.setText(" ");
					}
				}
			}
			break;
		case MINUTE:
			if (initFlag) {
				// System.out.println("in case minute, tempMin is" + tempMin);
				final DateEditor editor = (DateEditor) spinner.getEditor();
				if (isBadInputTime(editor) || tempMin > 59) {
					// System.out.println("in case bad minute,,, set things.");
					editor.getTextField().setBackground(
							Color.getHSBColor(3, 0.3f, 1f));
					lblTimeError.setText("<html><font color='red'>"
							+ "Please enter a valid time.</font></html>");
					minuteErr = true;
				} else {
					// System.out.println("in case ok minute, cancel things.");
					editor.getTextField().setBackground(
							CalendarStandard.CalendarYellow);
					minuteErr = false;
					if (!hourErr && !ampmErr) {
						lblTimeError.setText(" ");
					}
				}
			}
			break;
		case AMPM:
			if (initFlag) {
				final DateEditor editor = (DateEditor) spinner.getEditor();
				final String AMPMText = editor.getTextField().getText()
						.toUpperCase();
				if (!AMPMText.equals("AM") && !AMPMText.equals("PM")) {
					editor.getTextField().setBackground(
							Color.getHSBColor(3, 0.3f, 1f));
					lblTimeError.setText("<html><font color='red'>"
							+ "Please enter a valid time.</font></html>");
					ampmErr = true;
				} else {
					editor.getTextField().setBackground(
							CalendarStandard.CalendarYellow);
					ampmErr = false;
					if (!hourErr && !minuteErr) {
						lblTimeError.setText(" ");
					}
				}
			}
			break;
		}
		return false;
	}

	class SpinnerUI extends BasicSpinnerUI {
		protected Component createNextButton() {
			final JButton btnUp = (JButton) super.createNextButton();
			btnUp.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ae) {
					// System.out.println("Going up");
					try {
						upArrowAction = true;
						downArrowAction = false;
						firstRun = true;
						hourEditor.getTextField().commitEdit();
						minuteEditor.getTextField().commitEdit();
						AMPMEditor.getTextField().commitEdit();
					} catch (ParseException e) {
						// TODO Auto-generated catch block
					}
					// System.out.println("Going up done.");
					colon.requestFocus();
				}
			});
			return btnUp;
		}

		protected Component createPreviousButton() {
			final JButton btnDown = (JButton) super.createPreviousButton();
			btnDown.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ae) {
					try {
						downArrowAction = true;
						upArrowAction = false;
						firstRun = true;
						hourEditor.getTextField().commitEdit();
						minuteEditor.getTextField().commitEdit();
						AMPMEditor.getTextField().commitEdit();
					} catch (ParseException e) {
						// TODO Auto-generated catch block
					}
					colon.requestFocus();
				}
			});
			return btnDown;
		}
	}

}
