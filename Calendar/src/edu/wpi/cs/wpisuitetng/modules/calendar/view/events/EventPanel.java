package edu.wpi.cs.wpisuitetng.modules.calendar.view.events;

/* 
 * This is now a depreciated module. Delete when possible
 */

import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.JLabel;

import edu.wpi.cs.wpisuitetng.modules.calendar.controller.GetEventsController;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.Event;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.EventModel;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class EventPanel extends JPanel {
	private JTextPane namePane;
	private JTextPane datePane;
	private JTextPane descriptionPane;
	/**
	 * Create the panel.
	 */
	public EventPanel() {
		setLayout(null);
		
		namePane = new JTextPane();
		namePane.setBounds(130, 90, 93, 20);
		add(namePane);
		
		JLabel lblDate = new JLabel("Name");
		lblDate.setBounds(74, 96, 46, 14);
		add(lblDate);
		
		descriptionPane = new JTextPane();
		descriptionPane.setBounds(130, 152, 160, 75);
		add(descriptionPane);
		
		JLabel lblDescription = new JLabel("Description");
		lblDescription.setBounds(41, 158, 79, 14);
		add(lblDescription);
		
		JLabel label_1 = new JLabel("Date");
		label_1.setBounds(74, 127, 46, 14);
		add(label_1);
		
		datePane = new JTextPane();
		datePane.setBounds(130, 121, 93, 20);
		add(datePane);
		
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				reloadFields();
			}
		});
		btnNewButton.setBounds(102, 251, 89, 23);
		add(btnNewButton);
		
		//This line retrieves the events from the database
		GetEventsController.getInstance().retrieveEvents();
	}
	
	private void reloadFields()
	{
		Event dispEvent = EventModel.getInstance().getEvent(3);
		namePane.setText(dispEvent.getName());
		datePane.setText(dispEvent.getDate().toString());
		descriptionPane.setText(dispEvent.getDescription());

	}
}
