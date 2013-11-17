/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.calendar.view;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Calendar;

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
		
		labelPanel.setMinimumSize(new Dimension(200, 100));
		labelPanel.setPreferredSize(new Dimension(200, 100));
		labelPanel.setMaximumSize(new Dimension(200, 100));
		JLabel dateLabel = new JLabel("<html><center><body style='width: 180px'>" + dateRange + "</center></html>");

		dateLabel.setFont(new Font("Arial", Font.PLAIN, 16));
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
	
	
	
}
