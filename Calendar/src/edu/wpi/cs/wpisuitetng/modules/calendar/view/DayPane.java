package edu.wpi.cs.wpisuitetng.modules.calendar.view;
 
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpringLayout;
import javax.swing.border.LineBorder;

import edu.wpi.cs.wpisuitetng.modules.calendar.controller.GetCalendarDataController;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.CalendarData;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.CalendarDataModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.CommitmentList;
 
public class DayPane extends JPanel implements ICalPane {
       /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JPanel mainPanel = new JPanel();
 
       /**
       * Create the panel.
       */
       public DayPane() {
		   	 
    	   
		      setLayout(new GridLayout(1,1));
		
		      // HOURS
		      JScrollPane scrollPane = new JScrollPane(mainPanel, 
		    		  ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, 
		    		  ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		      scrollPane.setMinimumSize(new Dimension(300, 300));
		      add(scrollPane);
		      
		      SpringLayout layout = new SpringLayout();
		      mainPanel.setLayout(layout);
		      mainPanel.setPreferredSize(new Dimension(30, 2000));
			      
			    scrollPane.setRowHeaderView(getTimesBar(mainPanel.getPreferredSize().getHeight()));
			    scrollPane.getVerticalScrollBar().setValue(800);
			    
			    DetailedDay daypane = new DetailedDay(Calendar.getInstance());
			    loadCommitments(daypane);
			    
			    
			    layout.putConstraint(SpringLayout.WEST, daypane, 0, SpringLayout.WEST, mainPanel);
			    layout.putConstraint(SpringLayout.NORTH, daypane, 0, SpringLayout.NORTH, mainPanel);
			    layout.putConstraint(SpringLayout.SOUTH, daypane, 0, SpringLayout.SOUTH, mainPanel);
			    layout.putConstraint(SpringLayout.EAST, daypane, 0, SpringLayout.EAST, mainPanel);
			    mainPanel.add(daypane);
  
       }

     /** Populate detailed day with commitments
     * @param dayPane
     * 
     */
    private void loadCommitments(DetailedDay daypane) {
		// TODO Auto-generated method stub
    	 CalendarDataModel calDataModel = CalendarDataModel.getInstance();
 	   	CalendarData calData = calDataModel.getCalendarData(7); //get calData with ID of 7
 	   	CommitmentList commList = null;
 	   	if(calData != null)
 	   		 commList = calData.getCommitments();
    	 
    	 if(commList!=null)
    		 daypane.addCommitments(commList);
	}

	protected JComponent getTimesBar(double height){
    	 JPanel apane = new JPanel();
    	 SpringLayout layout = new SpringLayout();
    	 apane.setLayout(layout);
    	 
    	 int y = (int)(height/24.0);
    	 String[] times = {"12:00", "1 AM","2:00","3:00","4:00","5:00","6:00",
    			 		 "7:00","8:00","9:00","10:00","11:00","12 PM",
    			 		 "1:00","2:00","3:00","4:00","5:00","6:00",
    			 		 "7:00","8:00","9:00","10:00","11:00"};
    	 JLabel cur, last;
    	 
		cur = new JLabel(times[1]);
		//layout.putConstraint(SpringLayout.WEST, cur, 0, SpringLayout.WEST, apane);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, cur, y, SpringLayout.NORTH, apane);
	    layout.putConstraint(SpringLayout.EAST, cur, 0, SpringLayout.EAST, apane);
	    int max = cur.getPreferredSize().width;
	    apane.add(cur);
	    last = cur;
	    
    	 for(int i = 2; i < 24; i++){
			    cur = new JLabel(times[i]);
			    //layout.putConstraint(SpringLayout.WEST, cur, 0, SpringLayout.WEST, apane);
			    layout.putConstraint(SpringLayout.VERTICAL_CENTER, cur, (int)(height*i/24.0), SpringLayout.NORTH, apane);
			    layout.putConstraint(SpringLayout.EAST, cur, 0, SpringLayout.EAST, apane);
			    max = cur.getPreferredSize().width > max ? cur.getPreferredSize().width : max;
			    apane.add(cur);
			    last = cur;
    	 }
    	 
     	 apane.setPreferredSize(new Dimension(max+5, (int)height));
     	 apane.setSize(new Dimension(max+5, (int)height));
     	 
    	 
    	 return apane;
     }
     
     
	@Override
	public JPanel getPane() {
		// TODO Auto-generated method stub
		return this;
	}
}