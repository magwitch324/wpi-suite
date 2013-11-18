package edu.wpi.cs.wpisuitetng.modules.calendar.view.maintab.secondarytabs;

import javax.swing.JPanel;


import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeListener;
import javax.swing.text.DefaultFormatter;

import org.jdesktop.swingx.JXDatePicker;

import edu.wpi.cs.wpisuitetng.modules.calendar.models.CalendarData;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.CalendarDataModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.Category;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.CategoryList;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.Commitment;

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

/**
 * @author sfp
 *
 */
public class CommitmentTab extends JPanel {
	private JTextField nameTextField;
	private GridBagConstraints gbc_nameTextField;
	private JSpinner categorySpinner;
	
	SpinnerDateModelHalfHour model1 = new SpinnerDateModelHalfHour();  
	private JButton btnAddCommitment;
	private JComboBox<Category> categoryComboBox;
	private JTextPane descriptionTextField;
	private JXDatePicker datePicker;
	/**
	 * Create the panel.
	 */
	public CommitmentTab() {
		setLayout(new BorderLayout());
		JPanel formPanel = new JPanel();
		formPanel.setPreferredSize(new Dimension(400,800));
		add(formPanel, BorderLayout.WEST);
		
		GridBagLayout gbl = new GridBagLayout();
		gbl.rowHeights = new int[] {0, 0, 0, 0, 0, 0};
		gbl.columnWidths = new int[] {0, 0};
		formPanel.setLayout(gbl);
		
		JLabel lblName = new JLabel("Name:");
		lblName.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(0, 0, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;
		formPanel.add(lblName, gbc);
		
		nameTextField = new JTextField();
		gbc_nameTextField = new GridBagConstraints();
		gbc_nameTextField.insets = new Insets(0, 0, 5, 0);
		gbc_nameTextField.fill = GridBagConstraints.HORIZONTAL;
        gbc_nameTextField.weightx = 3;
        gbc_nameTextField.weighty = 1;
        gbc_nameTextField.gridx = 1;
        gbc_nameTextField.gridy = 0;
		formPanel.add(nameTextField, gbc_nameTextField);
		
		JLabel lblDesc = new JLabel("Description:");
		lblDesc.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_2 = new GridBagConstraints();
		gbc_2.insets = new Insets(0, 0, 5, 5);
        gbc_2.fill = GridBagConstraints.HORIZONTAL;
        gbc_2.weightx = 1;
        gbc_2.weighty = 5;
        gbc_2.gridx = 0;
        gbc_2.gridy = 1;
		formPanel.add(lblDesc, gbc_2);
		
		descriptionTextField = new JTextPane();
		GridBagConstraints gbc_descriptionTextField = new GridBagConstraints();
		gbc_descriptionTextField.insets = new Insets(0, 0, 5, 0);
		gbc_descriptionTextField.fill = GridBagConstraints.BOTH;
        gbc_descriptionTextField.weightx = 3;
        gbc_descriptionTextField.weighty = 5;
        gbc_descriptionTextField.gridx = 1;
        gbc_descriptionTextField.gridy = 1;
		formPanel.add(descriptionTextField, gbc_descriptionTextField);
		
		
		
		JLabel lblDate = new JLabel("Category:");
		lblDate.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_lblDate = new GridBagConstraints();
		gbc_lblDate.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblDate.insets = new Insets(0, 0, 5, 5);
		gbc_lblDate.gridx = 0;
		gbc_lblDate.gridy = 2;
		gbc_lblDate.weightx = 1;
		gbc_lblDate.weighty = 1;
		formPanel.add(lblDate, gbc_lblDate);
		
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
		
		
		
		
		JLabel lblTime = new JLabel("Time:");
		lblTime.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_lblTime = new GridBagConstraints();
		gbc_lblTime.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblTime.insets = new Insets(0, 0, 5, 5);
		gbc_lblTime.gridx = 0;
		gbc_lblTime.gridy = 3;
		gbc_lblTime.weightx = 1;
		gbc_lblTime.weighty = 1;
		formPanel.add(lblTime, gbc_lblTime);
		
		categorySpinner = new JSpinner( new SpinnerDateModelHalfHour());
	    categorySpinner.setModel(model1);
		
		JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(categorySpinner, "hh:mm a");
		categorySpinner.setEditor(timeEditor);
		
		//Rounds the spinner to 30 or 00
		categorySpinner.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				Calendar c = new GregorianCalendar();
				c.setTime((Date)categorySpinner.getValue());
				int minutesVal = c.get(Calendar.MINUTE);
				int hourVal = c.get(Calendar.HOUR);
				int newMinutesVal;
				int newHourVal = hourVal;
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
					model1.setValue(c.getTime());
					
				}
			}
		});
		
		
		
		GridBagConstraints gbc_spinner = new GridBagConstraints();
		gbc_spinner.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinner.insets = new Insets(0, 0, 5, 0);
		gbc_spinner.gridx = 1;
		gbc_spinner.gridy = 3;
		gbc_spinner.weightx = 1;
		gbc_spinner.weighty = 3;
		formPanel.add(categorySpinner, gbc_spinner);
		
		JLabel lblDate_1 = new JLabel("Date:");
		lblDate_1.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_lblDate_1 = new GridBagConstraints();
		gbc_lblDate_1.anchor = GridBagConstraints.EAST;
		gbc_lblDate.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblDate_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblDate_1.gridx = 0;
		gbc_lblDate_1.gridy = 4;
		gbc_lblDate_1.weightx = 1;
		gbc_lblDate_1.weighty = 1;
		formPanel.add(lblDate_1, gbc_lblDate_1);
		
		datePicker = new JXDatePicker();
		
		GridBagConstraints gbc_jdp = new GridBagConstraints();
		gbc_jdp.insets = new Insets(0, 0, 5, 0);
		gbc_jdp.fill = GridBagConstraints.HORIZONTAL;
		gbc_jdp.gridx = 1;
		gbc_jdp.gridy = 4;
		gbc_jdp.weightx = 1;
		gbc_jdp.weighty = 3;
		formPanel.add(datePicker, gbc_jdp);
		
		btnAddCommitment = new JButton("Add Commitment");
		btnAddCommitment.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				addCommitment();
				
			}

			
		});
		GridBagConstraints gbc_btnAddCommitment = new GridBagConstraints();
		gbc_btnAddCommitment.anchor = GridBagConstraints.CENTER;
		gbc_btnAddCommitment.weightx = 5;
		gbc_btnAddCommitment.weighty = 1;
		gbc_btnAddCommitment.gridx = 1;
		gbc_btnAddCommitment.gridy = 5;
		formPanel.add(btnAddCommitment, gbc_btnAddCommitment);
		
	}

	
	
	
	
	public JButton getBtnAddCommitment() {
		return btnAddCommitment;
	}
	public JSpinner getSpinner() {
		return categorySpinner;
	}
	public JComboBox getCategoryComboBox() {
		return categoryComboBox;
	}
	public JTextPane getDescriptionTextField() {
		return descriptionTextField;
	}
	public JTextField getNameTextField() {
		return nameTextField;
	}
	
	/**
	 * Adds new commitment with information contained in fields
	 */
	private void addCommitment() {
		// TODO Auto-generated method stub
		Commitment newComm = new Commitment();
		CalendarData calData = new CalendarData(); //needs to change to get existing CalendarData
		newComm.setCategoryId(((Category)this.categoryComboBox.getSelectedItem()).getId());
		newComm.setDescription(this.descriptionTextField.getText());
		newComm.setDueDate(this.datePicker.getDate());
		newComm.setId(8);
		newComm.setName(this.nameTextField.getText());
		

		calData.addCommitment(newComm);
		CalendarDataModel.getInstance().addCalendarData(calData);
	}
}



