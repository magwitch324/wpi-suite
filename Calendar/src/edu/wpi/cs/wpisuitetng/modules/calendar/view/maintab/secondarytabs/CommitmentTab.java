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

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
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

public class CommitmentTab extends JPanel {
	private JTextField textField;
	private GridBagConstraints gbc_1;
	private JSpinner spinner;
	
	SpinnerDateModelHalfHour model1 = new SpinnerDateModelHalfHour();  
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
		
		textField = new JTextField();
		gbc_1 = new GridBagConstraints();
		gbc_1.insets = new Insets(0, 0, 5, 0);
		gbc_1.fill = GridBagConstraints.HORIZONTAL;
        gbc_1.weightx = 3;
        gbc_1.weighty = 1;
        gbc_1.gridx = 1;
        gbc_1.gridy = 0;
		formPanel.add(textField, gbc_1);
		
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
		
		JTextPane textFieldDesc = new JTextPane();
		GridBagConstraints gbc_3 = new GridBagConstraints();
		gbc_3.insets = new Insets(0, 0, 5, 0);
		gbc_3.fill = GridBagConstraints.BOTH;
        gbc_3.weightx = 3;
        gbc_3.weighty = 5;
        gbc_3.gridx = 1;
        gbc_3.gridy = 1;
		formPanel.add(textFieldDesc, gbc_3);
		
		
		
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
		
		JComboBox comboBox = new JComboBox();
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.insets = new Insets(0, 0, 5, 0);
		gbc_comboBox.gridx = 1;
		gbc_comboBox.gridy = 2;
		gbc_comboBox.weightx = 3;
		gbc_comboBox.weighty = 1;
		formPanel.add(comboBox, gbc_comboBox);
		
		
		
		
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
		
		spinner = new JSpinner( new SpinnerDateModelHalfHour());
	    spinner.setModel(model1);
		
		JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(spinner, "hh:mm a");
		spinner.setEditor(timeEditor);
		spinner.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				Calendar c = new GregorianCalendar();
				c.setTime((Date)spinner.getValue());
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
//					spinner.setModel(model1);
					
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
		formPanel.add(spinner, gbc_spinner);
		
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
		
		JXDatePicker jdp = new JXDatePicker();
		
		GridBagConstraints gbc_jdp = new GridBagConstraints();
		gbc_jdp.insets = new Insets(0, 0, 5, 0);
		gbc_jdp.fill = GridBagConstraints.HORIZONTAL;
		gbc_jdp.gridx = 1;
		gbc_jdp.gridy = 4;
		gbc_jdp.weightx = 1;
		gbc_jdp.weighty = 3;
		formPanel.add(jdp, gbc_jdp);
		
		JButton btnAddCommitment = new JButton("Add Commitment");
		GridBagConstraints gbc_btnAddCommitment = new GridBagConstraints();
		gbc_btnAddCommitment.anchor = GridBagConstraints.CENTER;
		gbc_btnAddCommitment.weightx = 5;
		gbc_btnAddCommitment.weighty = 1;
		gbc_btnAddCommitment.gridx = 1;
		gbc_btnAddCommitment.gridy = 5;
		formPanel.add(btnAddCommitment, gbc_btnAddCommitment);
		
	}

	
	
	
	
}



