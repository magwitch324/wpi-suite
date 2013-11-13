package edu.wpi.cs.wpisuitetng.modules.calendar.week;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTable;

import java.awt.Component;

import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class WeekView3 extends JPanel {
	private JTable table;

	/**
	 * Create the panel.
	 */
	public WeekView3() {
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
                                        
        
		
		
		JLabel lblMonthdayyear = new JLabel(weekName);
		lblMonthdayyear.setBounds(183, 5, 79, 14);
		add(lblMonthdayyear);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 30, 0, 0);
		scrollPane.setBounds(10, 30, 430, 259);
		add(scrollPane);
		
		String[] columns = {"Time", sunday, monday, tuesday, wednesday, thursday, friday, saturday};
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
    public GregorianCalendar startDate(){
        GregorianCalendar tmp = new GregorianCalendar();
        tmp.add(Calendar.DAY_OF_WEEK, 
              tmp.getFirstDayOfWeek() - tmp.get(Calendar.DAY_OF_WEEK));
        return tmp;
}
}
