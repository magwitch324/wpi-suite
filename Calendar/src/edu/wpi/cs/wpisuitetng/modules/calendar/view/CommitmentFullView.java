package edu.wpi.cs.wpisuitetng.modules.calendar.view;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import edu.wpi.cs.wpisuitetng.modules.calendar.CalendarStandard;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.Commitment.Status;

/*
 * This class is used for creating the commitment View 
 * tab that shows all commitments including those 
 * that have been completed.
 * 
 * */

public class CommitmentFullView extends JPanel{

	AbCalendar tcalendar;
	JPanel commitPanel;
	List<Commitment> commitmentList = new ArrayList();
	
	/*Constructor creates main scrolling Panel and sets tcalendar which will grab teams commitments*/
	public CommitmentFullView(AbCalendar abCalendar) {
		this.tcalendar = abCalendar;
		
		commitPanel = new JPanel();
		JScrollPane scrollPane = new JScrollPane(commitPanel, 
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, 
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		add(scrollPane, BorderLayout.CENTER );
		scrollPane.getVerticalScrollBar().setUnitIncrement(20);
		/*spring layout to allow adjustments to size of screen without messing up panels*/
		SpringLayout layout = new SpringLayout();
		setLayout(layout);
		layout.putConstraint(SpringLayout.WEST, scrollPane, 0, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.EAST, scrollPane, 0, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.NORTH, scrollPane, 0, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.SOUTH, scrollPane, 0, SpringLayout.SOUTH, this);
		scrollPane.setViewportView(commitPanel);
		
		setCommitlist();
		setupPanels();
	}
	/*Sets the calendars commitments to the commitmentList array to populate panel*/
	private void setCommitlist() {
		if(tcalendar.getCalData() != null){
			commitmentList = tcalendar.getCalData().getCommitments().getCommitments();
		}
	}
	
	/*commit panel is populated with all events which are in separate panels that can be scrolled and clicked*/
	private void setupPanels() {
		commitPanel.setLayout(new BoxLayout(commitPanel, BoxLayout.Y_AXIS));
		commitPanel.setBorder(new EmptyBorder(10, 5, 10 , 20));
		commitPanel.setBackground(Color.WHITE);

		// print something when we do not  have any commitments
		if(commitmentList.size() == 0) {			
			JLabel message = new JLabel("<html><body style='width: 100%'><center>There are no commitments to display</center></html>", SwingConstants.CENTER);
			message.setBorder(new EmptyBorder(0, 0, 15, 0));
			commitPanel.add(message, BorderLayout.CENTER);
		}
		
		for(int i = 0; i < commitmentList.size(); i++){
			CommitmentViewPanel commitmentPanel = new CommitmentViewPanel(commitmentList.get(i));
			JLabel name = new JLabel("Name: "+commitmentList.get(i).getName());
			JLabel date = new JLabel("Due Date: "+ commitmentList.get(i).getDueDate().getTime());
			JLabel description = new JLabel("<HTML>Description: "+ commitmentList.get(i).getDescription()+"</HTML>");
			JLabel status = new JLabel("Status: " + Status.convertToString(commitmentList.get(i).getStatus().id));
			commitmentPanel.setLayout(new BoxLayout(commitmentPanel, BoxLayout.Y_AXIS));
			GridBagConstraints c = new GridBagConstraints();
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 1;
			commitmentPanel.add(name,c);
			commitmentPanel.add(date,c);
			commitmentPanel.add(description,c);
			commitmentPanel.add(status,c);
			commitmentPanel.setBackground(CalendarStandard.CalendarYellow);
//			commitmentPanel.setBackground(new Color(222,184,135));

			Border loweredbevel = BorderFactory.createLoweredBevelBorder();
			commitmentPanel.setBorder(loweredbevel);
			commitmentPanel.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (e.getClickCount() > 1)
						GUIEventController.getInstance().editCommitment(((CommitmentViewPanel)e.getComponent()).getCommitment());
				}		
			});
			
			commitPanel.add(commitmentPanel);
		}
	}
	
	
}
