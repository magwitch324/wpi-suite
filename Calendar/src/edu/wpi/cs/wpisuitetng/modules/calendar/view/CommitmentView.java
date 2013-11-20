
/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.calendar.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.List;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpringLayout;

import edu.wpi.cs.wpisuitetng.modules.calendar.models.*;

import edu.wpi.cs.wpisuitetng.modules.calendar.models.CalendarData;


/**
 * @author cttibbetts
 *
 */
public class CommitmentView extends JPanel {

	JPanel commitPanel;
	
	public CommitmentView(CalendarData calData) {
	
		

		commitPanel = new JPanel();
	      JScrollPane scrollPane = new JScrollPane(commitPanel, 
	    		  ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, 
	    		  ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	      add(scrollPane, BorderLayout.CENTER );
	      
	      SpringLayout layout = new SpringLayout();
	      setLayout(layout);
	      layout.putConstraint(SpringLayout.WEST, scrollPane, 0, SpringLayout.WEST, this);
	      layout.putConstraint(SpringLayout.EAST, scrollPane, 0, SpringLayout.EAST, this);
	      layout.putConstraint(SpringLayout.NORTH, scrollPane, 0, SpringLayout.NORTH, this);
	      layout.putConstraint(SpringLayout.SOUTH, scrollPane, 0, SpringLayout.SOUTH, this);
	      scrollPane.setViewportView(commitPanel);
	      
	        commitPanel.setLayout(new BoxLayout(commitPanel, BoxLayout.Y_AXIS));
	        //test data will be where event data is handled
	        
	     //   List<Commitment> commitmentList = CalendarDataModel.getInstance().getCalendarData(
	        Commitment commit = new Commitment();
	        commit.setName("Commitment Name");
	        commit.setDueDate(new Date());
	        commit.setDescription("The description of this commitment is right here. This will be shown as the description.");
	        JPanel commitmentPanel = new JPanel();
	        commitmentPanel.setBackground(Color.LIGHT_GRAY);
	        commitmentPanel.setBorder((BorderFactory.createMatteBorder(
                    -1, -1, -1, -1, Color.GRAY)));
	        JLabel name = new JLabel("Name: "+commit.getName());
	        JLabel date = new JLabel("Due Date: "+ commit.getDueDate());
	        JLabel description = new JLabel("Description: "+ commit.getDescription());
	        //description.setMaximumSize(new Dimension(250, 1000));
	        commitmentPanel.setLayout(new BoxLayout(commitmentPanel, BoxLayout.Y_AXIS));
	        commitmentPanel.add(name);
	        commitmentPanel.add(date);
	        commitmentPanel.add(description);
	        
		    //  SpringLayout layoutDescription = new SpringLayout();
		   //   description.setLayout(layoutDescription);
		     // layoutDescription.putConstraint(SpringLayout.WEST, description, 0, SpringLayout.WEST, commitmentPanel);
		    //  layoutDescription.putConstraint(SpringLayout.EAST, description, 0, SpringLayout.EAST, commitmentPanel);
		    //  layoutDescription.putConstraint(SpringLayout.NORTH, description, 0, SpringLayout.NORTH, commitmentPanel);
		   //   layoutDescription.putConstraint(SpringLayout.SOUTH, description, 0, SpringLayout.SOUTH, commitmentPanel);
	        
	        commitPanel.add(commitmentPanel);
	      /*  String testData = new String("Commitment1: did a commitment here it is move on to next");
	        for(int i = 0; i< 150; i++){
	        	JLabel commit = new JLabel(testData);
	        	JLabel line = new JLabel("\n");
	        	commitPanel.add(commit);
	        }
	        */
	
		
		
		
		
	}
	
}
