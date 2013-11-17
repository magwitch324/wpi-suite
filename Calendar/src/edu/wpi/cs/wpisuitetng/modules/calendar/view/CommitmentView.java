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
	
		
		//setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		//JLabel jHeader = new JLabel("Commitments");
	/*	jHeader.setAlignmentY(TOP_ALIGNMENT);
		jHeader.setAlignmentX(CENTER_ALIGNMENT);
		add(jHeader);
		*/
  /*      JScrollPane scrollPane = new JScrollPane();
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
	    scrollPane.getVerticalScrollBar().setValue(800);
	    
        commitPanel = new JPanel();
        //Grab Commitments
        commitPanel.setLayout(new BoxLayout(commitPanel, BoxLayout.Y_AXIS));
        String testData = new String("Commitment1: did a commitment here it is move on to next");
        for(int i = 0; i< 150; i++){
        	JLabel commit = new JLabel(testData);
        	JLabel line = new JLabel("\n");
        	commitPanel.add(commit);
        }
        
        //
        commitPanel.setPreferredSize(new Dimension(400, 400));
		commitPanel.setBackground(Color.WHITE);
		//commitPanel.setAlignmentX(RIGHT_ALIGNMENT);
		//commitPanel.setAlignmentY(TOP_ALIGNMENT);
		scrollPane.setViewportView(commitPanel);
		//this.setPreferredSize(new Dimension(500, 1000));
		add(scrollPane);
		*/
		commitPanel = new JPanel();
	      JScrollPane scrollPane = new JScrollPane(commitPanel, 
	    		  ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, 
	    		  ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	      //scrollPane.setMinimumSize(new Dimension(300, 300));
	      add(scrollPane, BorderLayout.CENTER);
	      
	     // BorderLayout b = new BorderLayout();
	     // b.addLayoutComponent(commitPanel, BorderLayout.CENTER);
	        commitPanel.setLayout(new BoxLayout(commitPanel, BoxLayout.Y_AXIS));
	        String testData = new String("Commitment1: did a commitment here it is move on to next");
	        for(int i = 0; i< 150; i++){
	        	JLabel commit = new JLabel(testData);
	        	JLabel line = new JLabel("\n");
	        	commitPanel.add(commit);
	        }
	        
	     //  commitPanel.setPreferredSize(new Dimension(300, 350));
		 //    commitPanel.setMinimumSize(new Dimension(350,300)); 
		   // scrollPane.setRowHeaderView(getTimesBar(commitPanel.getPreferredSize().getHeight()));
		    scrollPane.getVerticalScrollBar().setValue(800);

		
		
		
		
	}
	
}
