/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.calendar.view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.Calendar;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;

/**
 * @author cttibbetts
 *
 */
public class WeekPane extends JPanel implements ICalPane {
	JPanel mainPanel = new JPanel();
	 
    /**
    * Create the panel.
    */
    public WeekPane() {
		   		
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
		      
			    JComponent days = getDays();
			    layout.putConstraint(SpringLayout.WEST, days, 0, SpringLayout.WEST, mainPanel);
			    layout.putConstraint(SpringLayout.NORTH, days, 0, SpringLayout.NORTH, mainPanel);
			    layout.putConstraint(SpringLayout.SOUTH, days, 0, SpringLayout.SOUTH, mainPanel);
			    layout.putConstraint(SpringLayout.EAST, days, 0, SpringLayout.EAST, mainPanel);
			    mainPanel.add(days);
			    
			    scrollPane.setColumnHeaderView(getHeader((int)mainPanel.getPreferredSize().getWidth()));
			    
			    scrollPane.revalidate();
		      
    }

    protected JComponent getHeader(int width){
    	String[] weekdays = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" };
    	
    	JPanel apane = new JPanel();
    	apane.setLayout(new GridLayout(1,7));

    	int height = 0;
	    for(int i = 0; i<7; i++){
	    	apane.add( new JLabel(weekdays[i], SwingConstants.CENTER));
	    	height = (int) new JLabel(weekdays[i]).getPreferredSize().getHeight();
	    }
	    
    	//apane.setPreferredSize(new Dimension(width, height));
    	
    	return apane;
    }
    
    protected JComponent getDays(){
    	JPanel apane = new JPanel();
	    apane.setLayout(new GridLayout(1,7));
	    for(int i = 0; i<7; i++){
	    	apane.add( new DetailedDay(Calendar.getInstance()));
	    }
    	return apane;
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
