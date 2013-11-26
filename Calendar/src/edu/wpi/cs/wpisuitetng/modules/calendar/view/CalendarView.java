/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.calendar.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import edu.wpi.cs.wpisuitetng.modules.calendar.models.CalendarData;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.CommitmentList;


public abstract class CalendarView extends JSplitPane {
	
	private ICalPane calPane;
	protected CommitmentView commitments;
	private String dateRange;
	
	
	/**
	 * Constructor
	 * Sets up the panel with the refresh function
	 */
	public CalendarView(GregorianCalendar calendar) {
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
		labelPanel.setMinimumSize(new Dimension(330, 50));
		
		JLabel dateLabel = new JLabel("<html><body style='width: 100%'><center>" + dateRange + "</center></html>", SwingConstants.CENTER);
		dateLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
		
		labelPanel.add(dateLabel);
		
		panel.add(labelPanel);
		
		// View All Commitments Button - NOT SURE HOW TO CENTER???
		JButton viewAllCommitmentsButton = new JButton("View All Commitments");
		panel.add(viewAllCommitmentsButton);
		
		// the action listener for the Create View All Commitments Button
		viewAllCommitmentsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GUIEventController.getInstance().createViewCommitmentsTab();
			}	
		});		
		
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
	abstract public void setRange(GregorianCalendar calendar);
	
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
	 * @param showCommsOnCalPane 
	 * @param calData Calendar Data to be displayed
	 * @param showTeamData 
	 * @param showCommitments 
	 */
	abstract public void displayCalData(CommitmentList commList);
	
	
	
}
