package edu.wpi.cs.wpisuitetng.modules.calendar;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTable;

import java.awt.Component;

import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import java.awt.Color;
import java.util.Date;

public class WeekView extends JPanel implements ICalenderView{
	private JTable table;

	/**
	 * Create the panel.
	 */
	public WeekView() {
		setLayout(null);
		JLabel lblMonthdayyear = new JLabel("Month/Day/Year");
		lblMonthdayyear.setBounds(183, 5, 79, 14);
		add(lblMonthdayyear);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 30, 0, 0);
		scrollPane.setBounds(10, 30, 1200, 600);
		add(scrollPane);
		
		String[] columns = {"Time", "Sun", " Mon", "Tue", "Wed", "Thur", "Fri", "Sat"};
		String[][] data = {{"12:00AM", "", "", "", "", "", "", ""},
				           {"", "", "", "", "", "", "", ""},
		                   {"1:00AM", "", "", "", "", "", "", ""},
		                   {"", "", "", "", "", "", "", ""},
		                   {"2:00AM", "", "", "", "", "", "", ""},
		                   {"", "", "", "", "", "", "", ""},
		                   {"3:00AM", "", "", "", "", "", "", ""},
		                   {"", "", "", "", "", "", "", ""},
		                   {"4:00AM", "", "", "", "", "", "", ""},
		                   {"", "", "", "", "", "", "", ""},
		                   {"5:00AM", "", "", "", "", "", "", ""},
		                   {"", "", "", "", "", "", "", ""},
		                   {"6:00AM", "", "", "", "", "", "", ""},
		                   {"", "", "", "", "", "", "", ""},
		                   {"7:00AM", "", "", "", "", "", "", ""},
		                   {"", "", "", "", "", "", "", ""},
		                   {"8:00AM", "", "", "", "", "", "", ""},
		                   {"", "", "", "", "", "", "", ""},
		                   {"9:00AM", "", "", "", "", "", "", ""},
		                   {"", "", "", "", "", "", "", ""},
		                   {"10:00AM", "", "", "", "", "", "", ""},
		                   {"", "", "", "", "", "", "", ""},
		                   {"11:00AM", "", "", "", "", "", "", ""},
		                   {"", "", "", "", "", "", "", ""},
		                   {"12:00PM", "", "", "", "", "", "", ""},
		                   {"", "", "", "", "", "", "", ""},
		                   {"1:00PM", "", "", "", "", "", "", ""},
		                   {"", "", "", "", "", "", "", ""},
		                   {"2:00PM", "", "", "", "", "", "", ""},
		                   {"", "", "", "", "", "", "", ""},
		                   {"3:00PM", "", "", "", "", "", "", ""},
		                   {"", "", "", "", "", "", "", ""},
		                   {"4:00PM", "", "", "", "", "", "", ""},
		                   {"", "", "", "", "", "", "", ""},
		                   {"5:00PM", "", "", "", "", "", "", ""},
		                   {"", "", "", "", "", "", "", ""},
		                   {"6:00PM", "", "", "", "", "", "", ""},
		                   {"", "", "", "", "", "", "", ""},
		                   {"7:00PM", "", "", "", "", "", "", ""},
		                   {"", "", "", "", "", "", "", ""},
		                   {"8:00PM", "", "", "", "", "", "", ""},
		                   {"", "", "", "", "", "", "", ""},
		                   {"9:00PM", "", "", "", "", "", "", ""},
		                   {"", "", "", "", "", "", "", ""},
		                   {"10:00PM", "", "", "", "", "", "", ""},
		                   {"", "", "", "", "", "", "", ""},
		                   {"11:00PM", "", "", "", "", "", "", ""},
		                   {"", "", "", "", "", "", "", ""}};
		
		DefaultTableModel model = new DefaultTableModel(data, columns);   
		
		table = new JTable(model);
		scrollPane.setViewportView(table);
	}

	@Override
	public JPanel getCalPanel() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public void goToDate(Date adate) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Date getDate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void skipForward() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void skipBackward() {
		// TODO Auto-generated method stub
		
	}
}
