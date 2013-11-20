/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.calendar.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Calendar;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import edu.wpi.cs.wpisuitetng.modules.calendar.models.CalendarData;

/**
 * @author cttibbetts
 *
 */
public abstract class CalendarView extends JSplitPane {
	
	private ICalPane calPane;
	private CommitmentView commitments;
	private String dateRange;
	
	
	/**
	 * Constructor
	 * Sets up the panel with the refresh function
	 */
	public CalendarView(Calendar calendar) {
	}
	/**
	 * create and display View componenets
	 */
	public void refresh() {
		
		setLeftComponent(calPane.getPane());
		setRightComponent(makeRightView());
		setResizeWeight(1.0);
		
	}
	
	/**
	 * @return
	 */
	private JPanel makeRightView() {
		
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		JPanel labelPanel = new JPanel();
		labelPanel.setLayout(new GridLayout(1,1,0,0));
		labelPanel.setBorder(new EmptyBorder(0, 10, 0 , 10));
		labelPanel.setMinimumSize(new Dimension(300, 75));
		labelPanel.setPreferredSize(new Dimension(300, 175));
		
		JLabel dateLabel = new JLabel("<html><body style='width: 100%'><center>" + dateRange + "</center></html>", SwingConstants.CENTER);
		dateLabel.setFont(new Font("Arial", Font.BOLD, 16));
		
		labelPanel.add(dateLabel);
		
		panel.add(labelPanel);
		
		JSeparator separator = new JSeparator();
		separator.setOrientation(VERTICAL_SPLIT);
		panel.add(separator);
		panel.add(commitments, BorderLayout.CENTER);
		
		return panel;
		
	}
	
	/**
	 * set the label for the current date/dates
	 * @param range
	 */
	protected void setLabel(String range) {
		dateRange = range;
	}
	
	/**
	 * set the new date range for the calendar
	 * @param calendar
	 */
	abstract public void setRange(Calendar calendar);
	
	public void setCalPane(ICalPane pane) {
		// TODO Auto-generated method stub
		this.calPane = pane;
	}
	
	public void setCommitmentView(CommitmentView comm) {
		// TODO Auto-generated method stub
		this.commitments = comm;
	}
	
	/** Display calendar data in internal panels, decides what commitments 
	 * fall within range
	 * @param calData Calendar Data to be displayed
	 */
	abstract public void displayCalData(CalendarData calData);
	
	
	
}
