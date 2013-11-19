package edu.wpi.cs.wpisuitetng.modules.calendar.view.maintab.secondarytabs;

import javax.swing.JPanel;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeListener;
import javax.swing.text.DefaultFormatter;

import org.jdesktop.swingx.JXDatePicker;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.calendar.controller.UpdateCalendarDataController;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.CalendarData;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.CalendarDataModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.Category;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.CategoryList;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.GUIEventController;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.Insets;

import javax.swing.JTextPane;

import java.awt.event.InputMethodListener;
import java.awt.event.InputMethodEvent;

import javax.swing.event.ChangeEvent;
import javax.swing.JButton;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Component;

import javax.swing.Box;
import javax.swing.border.LineBorder;

import java.awt.Color;

import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;

/**
 * @author sfp
 *
 */
public class CommitmentTab extends JPanel {
	private JTextField nameTextField;
	private GridBagConstraints gbc_nameTextField;
	private JSpinner timeSpinner;
	
	SpinnerDateModelHalfHour spinnerModel = new SpinnerDateModelHalfHour();  
	private JButton btnAddCommitment;
	private JComboBox<Category> categoryComboBox;
	private JTextArea descriptionTextArea;
	private JXDatePicker datePicker;
	private JScrollPane descPane;
	private JPanel panel;
	private JSpinner.DateEditor timeEditor;
	private JPanel panel_1;
	private JRadioButton rdbtnPersonal;
	private JRadioButton rdbtnTeam;
	private JLabel lblType;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	/**
	 * Create the panel.
	 */
	public CommitmentTab() {
		//Sets new commitment form to left of pane
		setLayout(new BorderLayout());
		JPanel formPanel = new JPanel();
		formPanel.setPreferredSize(new Dimension(400,800));
		add(formPanel, BorderLayout.WEST);
		
		// form uses GridBagLayout w/ two columns
		GridBagLayout gbl = new GridBagLayout();
		gbl.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0};
		gbl.columnWeights = new double[]{0.0, 1.0};
		gbl.rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0};
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
        gbc_nameTextField.weightx = 3;
        gbc_nameTextField.weighty = 1;
        gbc_nameTextField.gridx = 1;
        gbc_nameTextField.gridy = 0;
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
		descPane = new JScrollPane();
		descPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		descPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		//Text area for description
		descriptionTextArea = new JTextArea();
		descriptionTextArea.setPreferredSize(new Dimension(280,160));
		descPane.setViewportView(descriptionTextArea);
		descriptionTextArea.setLineWrap(true);
		GridBagConstraints gbc_descriptionTextField = new GridBagConstraints();
		gbc_descriptionTextField.anchor = GridBagConstraints.WEST;
		gbc_descriptionTextField.insets = new Insets(0, 0, 5, 0);
        gbc_descriptionTextField.weightx = 3;
        gbc_descriptionTextField.weighty = 5;
        gbc_descriptionTextField.gridx = 1;
        gbc_descriptionTextField.gridy = 1;
		formPanel.add(descPane, gbc_descriptionTextField);
		
		
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
		categoryComboBox.addItem(new Category(4, "Cat2"));

		GridBagConstraints gbc_categoryComboBox = new GridBagConstraints();
		gbc_categoryComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_categoryComboBox.insets = new Insets(0, 0, 5, 0);
		gbc_categoryComboBox.gridx = 1;
		gbc_categoryComboBox.gridy = 2;
		gbc_categoryComboBox.weightx = 3;
		gbc_categoryComboBox.weighty = 1;
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
		
		//Time spinner, half hour resolution
		timeSpinner = new JSpinner( new SpinnerDateModelHalfHour());
	    timeSpinner.setModel(spinnerModel);
		timeEditor = new JSpinner.DateEditor(timeSpinner, "hh:mm a");
		timeSpinner.setEditor(timeEditor);
		//Rounds the spinner to 30 or 00
		addTimeRoundingEvent();
		GridBagConstraints gbc_spinner = new GridBagConstraints();
		gbc_spinner.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinner.insets = new Insets(0, 0, 5, 0);
		gbc_spinner.gridx = 1;
		gbc_spinner.gridy = 4;
		gbc_spinner.weightx = 1;
		gbc_spinner.weighty = 3;
		formPanel.add(timeSpinner, gbc_spinner);
		
		//Date label
		JLabel lblDate_1 = new JLabel("Date:");
		lblDate_1.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_lblDate_1 = new GridBagConstraints();
		gbc_lblDate_1.fill = GridBagConstraints.VERTICAL;
		gbc_lblDate_1.anchor = GridBagConstraints.EAST;
		gbc_lblDate_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblDate_1.gridx = 0;
		gbc_lblDate_1.gridy = 5;
		gbc_lblDate_1.weightx = 1;
		gbc_lblDate_1.weighty = 1;
		formPanel.add(lblDate_1, gbc_lblDate_1);
		
		//DatePicker box
		datePicker = new JXDatePicker();
		GridBagConstraints gbc_jdp = new GridBagConstraints();
		gbc_jdp.insets = new Insets(0, 0, 5, 0);
		gbc_jdp.fill = GridBagConstraints.HORIZONTAL;
		gbc_jdp.gridx = 1;
		gbc_jdp.gridy = 5;
		gbc_jdp.weightx = 1;
		gbc_jdp.weighty = 3;
		formPanel.add(datePicker, gbc_jdp);
		
		//Add Commitment button
		btnAddCommitment = new JButton("Add Commitment");
		btnAddCommitment.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				addCommitment();
				
			}

			
		});
		
		
		GridBagConstraints gbc_btnAddCommitment = new GridBagConstraints();
		gbc_btnAddCommitment.insets = new Insets(0, 0, 5, 0);
		gbc_btnAddCommitment.anchor = GridBagConstraints.CENTER;
		gbc_btnAddCommitment.gridx = 1;
		gbc_btnAddCommitment.gridy = 6;
		formPanel.add(btnAddCommitment, gbc_btnAddCommitment);
		
		
		//Dummy panel that fixes the left column width (GridBagLayout sucks)
		JPanel dummyPanel = new JPanel();
		GridBagConstraints gbc_panel5 = new GridBagConstraints();
		gbc_panel5.fill = GridBagConstraints.BOTH;
		gbc_panel5.insets = new Insets(0, 0, 0, 100);
		gbc_panel5.gridx = 0;
		gbc_panel5.gridy = 7;
		formPanel.add(dummyPanel, gbc_panel5);
		
	}

	
	/**
	 * Add an event handler to round the spinner minute value when not 0 or 30
	 */
	private void addTimeRoundingEvent() {
		timeSpinner.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				Calendar c = new GregorianCalendar();
				//get time value from spinner
				c.setTime((Date)timeSpinner.getValue());
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
					spinnerModel.setValue(c.getTime());
					
				}
			}
		});
	}


	/**
	 * Adds new commitment with information contained in fields
	 */
	private void addCommitment() {
		// TODO Auto-generated method stub
		Commitment newComm = new Commitment();
		CalendarData calData;
//		if (this.rdbtnPersonal.isSelected())
//			calData = CalendarDataModel.getInstance().getCalendarData(ConfigManager.getConfig().getUserName() + "/" ConfigManager.getConfig().getUserName()); 
//		else
			calData = CalendarDataModel.getInstance().getCalendarData(ConfigManager.getConfig().getProjectName()); 
		newComm.setCategoryId(((Category)this.categoryComboBox.getSelectedItem()).getId());
		newComm.setDescription(this.descriptionTextArea.getText());
		
		//Parse date and time info
		Calendar calDate = new GregorianCalendar();
		Calendar calTime = new GregorianCalendar();
		calDate.setTime(this.datePicker.getDate());
		calTime.setTime((Date)timeSpinner.getValue());
		calDate.set(Calendar.HOUR_OF_DAY, calTime.get(Calendar.HOUR_OF_DAY));
		calDate.set(Calendar.MINUTE, calTime.get(Calendar.MINUTE));
		
		//set due date
		newComm.setDueDate(calDate.getTime());
		newComm.setId(calData.getCommitments().getNextID());
		newComm.setName(this.nameTextField.getText());
		

		calData.addCommitment(newComm);
		UpdateCalendarDataController.getInstance().updateCalendarData(calData);
 
 
	}
}



