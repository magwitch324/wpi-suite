
/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.calendar.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.Calendar;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpringLayout;

/**
 * @author cttibbetts
 *
 */
public class CommitmentView extends JPanel {

	JPanel commitPanel;
	
	public CommitmentView() {
	
		

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
	        String testData = new String("Commitment1: did a commitment here it is move on to next");
	        for(int i = 0; i< 150; i++){
	        	JLabel commit = new JLabel(testData);
	        	JLabel line = new JLabel("\n");
	        	commitPanel.add(commit);
	        }
	        
	
		
		
		
		
	}
	
}
