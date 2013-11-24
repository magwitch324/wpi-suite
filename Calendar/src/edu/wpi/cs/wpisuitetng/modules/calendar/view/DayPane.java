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
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
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
	Calendar day;
	private DetailedDay daypane;
	private SpringLayout layout;
	private JScrollPane scrollPane;
	private JPanel labelPane;
	
 
       /**
       * Create the panel.
       */


	public DayPane(Calendar datecalendar, CalendarData calData) {

		day = datecalendar;
//			// HOURS
//			JScrollPane scrollPane = new JScrollPane(mainPanel, 
//					  ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, 
//					  ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
//			scrollPane.getVerticalScrollBar().setUnitIncrement(20);
//			scrollPane.setMinimumSize(new Dimension(300, 300));
//			add(scrollPane);
//			  
//			JPanel apane = new JPanel();
//	    	apane.setLayout(new GridLayout(1,2));
//
//		    JLabel eventlabel = new JLabel("Events", SwingConstants.CENTER);
//		    eventlabel.setFont(new Font("Arial", 1, 14));
//		    apane.add( eventlabel );
//		    
//		    JLabel commitlabel = new JLabel("Commitments", SwingConstants.CENTER);
//		    commitlabel.setFont(new Font("Arial", 1, 14));
//		    apane.add( commitlabel );
//		    
//		    scrollPane.setColumnHeaderView(apane);
//		    	
//			SpringLayout layout = new SpringLayout();
//			mainPanel.setLayout(layout);
//			mainPanel.setPreferredSize(new Dimension(30, 2000));
//			      
//			scrollPane.setRowHeaderView(getTimesBar(mainPanel.getPreferredSize().getHeight()));
//			scrollPane.getVerticalScrollBar().setValue(800);
//			
//
//			ArrayList<Commitment> commitmentList = new ArrayList<Commitment>();
//			CalendarData teamCommitments = CalendarDataModel.getInstance().getCalendarData(ConfigManager.getConfig().getProjectName());
//			if(!showTeamCommitments&&showCommitments){
//				commitmentList = new ArrayList<Commitment>(calData.getCommitments().getCommitments());
//			}
//			if(showTeamCommitments&&showCommitments){
//				commitmentList = new ArrayList<Commitment>(calData.getCommitments().getCommitments());
//				commitmentList.addAll(teamCommitments.getCommitments().getCommitments());
//			}

			

		scrollPane = new JScrollPane(mainPanel, 
						  ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, 
						  ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
				scrollPane.getVerticalScrollBar().setUnitIncrement(20);
				scrollPane.setMinimumSize(new Dimension(300, 500));
				add(scrollPane);
				  
				layout = new SpringLayout();
				mainPanel.setLayout(layout);
				mainPanel.setPreferredSize(new Dimension(30, 2000));
				  
				labelPane = new JPanel();
		    	labelPane.setLayout(new GridLayout(1,2));

			    JLabel eventlabel = new JLabel("Events", SwingConstants.CENTER);
			    eventlabel.setFont(new Font("Arial", 1, 14));
			    labelPane.add( eventlabel );
			    
			    JLabel commitlabel = new JLabel("Commitments", SwingConstants.CENTER);
			    commitlabel.setFont(new Font("Arial", 1, 14));
			    labelPane.add( commitlabel );
			    
				
				scrollPane.setRowHeaderView(getTimesBar(mainPanel.getPreferredSize().getHeight()));
				scrollPane.addMouseListener(new MouseAdapter(){
					@Override
					public void mouseExited(MouseEvent e) {
						// TODO Auto-generated method stub
						GUIEventController.getInstance().setScrollBarValue(((JScrollPane)e.getSource()).getVerticalScrollBar().getValue());
					}
					
				});
				refresh();
				
				
				
				

		  
	}

    
    private void refresh() {
		// TODO Auto-generated method stub
    	
    	mainPanel.removeAll();
    	setLayout(new GridLayout(1,1));
		
		if (daypane == null)
			daypane = new DetailedDay(day, new CommitDetailedPane(day, new CommitmentList(), new CommitmentList()));
		
		layout.putConstraint(SpringLayout.WEST, daypane, 0, SpringLayout.WEST, mainPanel);
		layout.putConstraint(SpringLayout.NORTH, daypane, 0, SpringLayout.NORTH, mainPanel);
		layout.putConstraint(SpringLayout.SOUTH, daypane, 0, SpringLayout.SOUTH, mainPanel);
		layout.putConstraint(SpringLayout.EAST, daypane, 0, SpringLayout.EAST, mainPanel);
		mainPanel.add(daypane);
		

	    scrollPane.setColumnHeaderView(labelPane);
		
   	 	mainPanel.revalidate();
   	 	mainPanel.repaint();
   	 	scrollPane.revalidate();
   	 	scrollPane.repaint();
   	 	scrollPane.getVerticalScrollBar().setValue(GUIEventController.getInstance().getScrollBarValue());
		
	}


	private boolean getShowCommitments() {
		
    	AbCalendar abCalendar= GUIEventController.getInstance().getSelectedCalendar();
    	if (abCalendar != null)
    		return abCalendar.getShowCommitments();
    	else
    		return false;
	}


	private boolean getShowTeamData() {
		AbCalendar abCalendar= GUIEventController.getInstance().getSelectedCalendar();
    	if (abCalendar != null)
    		return abCalendar.getShowTeamData();
    	else
    		return false;
	}


	/** Displays commitments on DetailedDay
     * @param commList List of commitments to be displayed
	 * @param dayTeamCommList 
     */
    public void displayCommitments(CommitmentList personalCommList, CommitmentList teamCommList) {
		  	 
    	daypane = new DetailedDay(day, new CommitDetailedPane(day, personalCommList, teamCommList)); 
    	refresh();

	}

	protected JComponent getTimesBar(double height){
    	 JPanel apane = new JPanel();
    	 apane.setBackground(Color.RED);
    	 SpringLayout layout = new SpringLayout();
    	 apane.setLayout(layout);
    	 
    	 String[] times = {"12:00", "1 AM","2:00","3:00","4:00","5:00","6:00",
    			 		 "7:00","8:00","9:00","10:00","11:00","12 PM",
    			 		 "1:00","2:00","3:00","4:00","5:00","6:00",
    			 		 "7:00","8:00","9:00","10:00","11:00"};

	    int max = 0;
	    
    	 for(int i = 1; i < 24; i++){
			    JLabel alab = new JLabel(times[i]);
			    alab.setFont(new Font("Arial", 1, 14));
			    layout.putConstraint(SpringLayout.VERTICAL_CENTER, alab, (int)(height*i/24.0), SpringLayout.NORTH, apane);
			    layout.putConstraint(SpringLayout.EAST, alab, 0, SpringLayout.EAST, apane);
			    max = alab.getPreferredSize().width > max ? alab.getPreferredSize().width : max;
			    apane.add(alab);
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