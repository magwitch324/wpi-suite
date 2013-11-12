package edu.wpi.cs.wpisuitetng.modules.calendar.week;

import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.border.LineBorder;

import java.awt.Color;

import javax.swing.JComboBox;
import javax.swing.JTextPane;

import java.awt.List;

import javax.swing.JInternalFrame;
import javax.swing.JTabbedPane;

import java.awt.BorderLayout;

import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JFormattedTextField;
import javax.swing.Box;

import java.awt.GridLayout;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JEditorPane;

public class WeekView extends JPanel {

	/**
	 * Create the panel.
	 */
	public WeekView() {
		setLayout(null);
		
		GregorianCalendar start = startDate();
		String weekName = new SimpleDateFormat("MMM").format(start.getTime()) + " " + start.get(Calendar.DAY_OF_MONTH);
		String sunday = start.get(Calendar.DAY_OF_MONTH) + " Sunday";
		start.add(Calendar.DAY_OF_YEAR, 1);
		String monday = start.get(Calendar.DAY_OF_MONTH) + " Monday";
		start.add(Calendar.DAY_OF_YEAR, 1);
		String tuesday = start.get(Calendar.DAY_OF_MONTH) + " Tuesday";
		start.add(Calendar.DAY_OF_YEAR, 1);
		String wednesday = start.get(Calendar.DAY_OF_MONTH) + " Wednesday";
		start.add(Calendar.DAY_OF_YEAR, 1);
		String thursday = start.get(Calendar.DAY_OF_MONTH) + " Thursday";
		start.add(Calendar.DAY_OF_YEAR, 1);
		String friday = start.get(Calendar.DAY_OF_MONTH) + " Friday";
		start.add(Calendar.DAY_OF_YEAR, 1);
		String saturday = start.get(Calendar.DAY_OF_MONTH) + " Saturday";
		weekName += " - " + new SimpleDateFormat("MMM").format(start.getTime()) + " " + start.get(Calendar.DAY_OF_MONTH);
						
		
		JPanel panel = new JPanel();
		panel.setBounds(0, -11, 500, 300);
		add(panel);
		panel.setLayout(null);
		
		JLabel lblMonthdayyear = new JLabel(weekName);
		lblMonthdayyear.setBounds(210, 11, 80, 14);
		lblMonthdayyear.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblMonthdayyear);
		
		JButton btnNewButton = new JButton(monday);
		btnNewButton.setBounds(123, 36, 60, 23);
		panel.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Tue");
		btnNewButton_1.setBounds(183, 36, 60, 23);
		panel.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Wed");
		btnNewButton_2.setBounds(245, 36, 60, 23);
		panel.add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("Thu");
		btnNewButton_3.setBounds(305, 36, 60, 23);
		panel.add(btnNewButton_3);
		
		JButton btnNewButton_4 = new JButton("Fri");
		btnNewButton_4.setBounds(368, 36, 60, 23);
		panel.add(btnNewButton_4);
		
		JButton btnNewButton_5 = new JButton("Sat");
		btnNewButton_5.setBounds(430, 36, 60, 23);
		panel.add(btnNewButton_5);
		
		JButton btnNewButton_6 = new JButton(sunday);
		btnNewButton_6.setBounds(62, 36, 60, 23);
		panel.add(btnNewButton_6);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(62, 70, 428, 203);
		panel.add(panel_1);
		panel_1.setLayout(new GridLayout(0, 7, 0, 0));
		
		JEditorPane editorPane = new JEditorPane();
		editorPane.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1.add(editorPane);
		
		JEditorPane editorPane_1 = new JEditorPane();
		editorPane_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1.add(editorPane_1);
		
		JEditorPane editorPane_2 = new JEditorPane();
		editorPane_2.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1.add(editorPane_2);
		
		JEditorPane editorPane_3 = new JEditorPane();
		editorPane_3.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1.add(editorPane_3);
		
		JEditorPane editorPane_4 = new JEditorPane();
		editorPane_4.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1.add(editorPane_4);
		
		JEditorPane editorPane_5 = new JEditorPane();
		editorPane_5.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1.add(editorPane_5);
		
		JEditorPane editorPane_6 = new JEditorPane();
		editorPane_6.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1.add(editorPane_6);

	}
	public GregorianCalendar startDate(){
		GregorianCalendar tmp = new GregorianCalendar();
		tmp.add(Calendar.DAY_OF_WEEK, 
	              tmp.getFirstDayOfWeek() - tmp.get(Calendar.DAY_OF_WEEK));
		return tmp;
	}
}
