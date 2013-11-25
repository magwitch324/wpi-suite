/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.calendar.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import edu.wpi.cs.wpisuitetng.modules.calendar.models.CalendarData;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.CommitmentList;


public class WeekPane extends JPanel implements ICalPane {
	JPanel mainPanel = new JPanel();
	JScrollPane scrollPane = new JScrollPane(mainPanel, 
			ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, 
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	Calendar mydate;
	private JComponent days;
	private SpringLayout layout;
	
    /**
    * Create the panel.
    */

	public WeekPane(Calendar datecalendar) {

		mydate = (Calendar)datecalendar.clone();
		mydate.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
//	   	while(mydate.get(Calendar.DAY_OF_WEEK) != mydate.getFirstDayOfWeek() ){
//	   		mydate.add(Calendar.DATE, -1);
//	   	}
	   	
	   	setLayout(new GridLayout(1,1));
		scrollPane.setMinimumSize(new Dimension(500, 300));
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		scrollPane.setMinimumSize(new Dimension(500, 300));
		scrollPane.getVerticalScrollBar().setUnitIncrement(20);
		
		layout = new SpringLayout();
		mainPanel.setLayout(layout);
		mainPanel.setPreferredSize(new Dimension(30, 2000));		
		
		
		
		scrollPane.setColumnHeaderView(getHeader(0));
		scrollPane.setRowHeaderView(getTimesBar(mainPanel.getPreferredSize().getHeight()));
		scrollPane.getVerticalScrollBar().setValue(800);   
		
		
//	   	if(abCalendar.getShowCommitements()){
		
		this.add(scrollPane);
	   	
		scrollPane.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				GUIEventController.getInstance().setScrollBarValue(((JScrollPane)e.getSource()).getVerticalScrollBar().getValue());
			}
			
		});
		
		
	   	scrollPane.addComponentListener(new ComponentAdapter(){
			public void componentResized(ComponentEvent e){
				
				scrollPane.setColumnHeaderView(getHeader(0));
				System.out.println(scrollPane.getColumnHeader().getSize().getWidth() + " : " + 					scrollPane.getColumnHeader().getView().getPreferredSize().getWidth());				if(scrollPane.getColumnHeader().getSize().getWidth() <
						scrollPane.getColumnHeader().getView().getPreferredSize().getWidth()){
					scrollPane.setColumnHeaderView(getHeader(1));
				}
				
				scrollPane.revalidate();
				if(scrollPane.getColumnHeader().getSize().getWidth() <
						scrollPane.getColumnHeader().getView().getPreferredSize().getWidth()){
					scrollPane.setColumnHeaderView(getHeader(2));
				}
				
				scrollPane.revalidate();
			}
		});
	}


    /** Creates header of day names above detailed day
     * @param use
     * @return
     */
    protected JComponent getHeader(int use){
    	String[][] weekdays = {{"Sunday, ", "Monday, ", "Tuesday, ",
    						"Wednesday, ", "Thursday, ", "Friday, ", "Saturday, " },
    						{"Sun, ", "Mon, ", "Tue, ","Wed, ", "Thu, ", "Fri, ", "Sat, " },
    						{"Sun", "Mon", "Tue","Wed", "Thu", "Fri", "Sat" }};
    	
    	Calendar acal = (Calendar)mydate.clone();
    	if(use < 2){
    		for(int i=0; i < 7; i++) {
    			weekdays[use][i] += acal.get(Calendar.DATE);
    			acal.add(Calendar.DATE, 1);
    		}
    	}
    	
    	JPanel apane = new JPanel();
    	apane.setBackground(Color.RED);
    	SpringLayout layout = new SpringLayout();
    	GridLayout g = new GridLayout(1,7);
    	
    	apane.setLayout(g);
    	int height = 0;
    	
	    for(int i = 0; i<7; i++){
	    	JLabel alab = new JLabel(weekdays[use][i], SwingConstants.CENTER);
	    	alab.setFont(new Font("SansSerif", 1, 12));
	    	apane.add( alab );
	    }
    
    	return apane;
    }

    
    /** Creates JPanel of commitment boxes for overlaying on detailed day
     * @param personalCommList
     * @param teamCommList
     * @return
     */
    protected JComponent getDays(CommitmentList personalCommList, CommitmentList teamCommList){
    	JPanel apane = new JPanel();
    	apane.setBackground(Color.WHITE);
	    apane.setLayout(new GridLayout(1,7));
    	String[] weekdays = {"Sunday, ", "Monday, ", "Tuesday, ",
				"Wednesday, ", "Thursday, ", "Friday, ", "Saturday, " };
    	int initial = mydate.get(Calendar.DATE);
    	
       	for(int i=0; i < 7; i++) {
    		weekdays[i] += (initial + i);
    	}
       	
       	//Initialize variables for placing each commitment on correct day
       	CommitmentList[] personal = new CommitmentList[7];
       	CommitmentList[] team = new CommitmentList[7];
       	for(int i = 0; i<7; i++)
       	{
       		personal[i] = new CommitmentList();
       		team[i] = new CommitmentList();
       	}
       	Calendar cal = new GregorianCalendar();
       	
       	//Place each commitment on its right day
       	for(Commitment comm: personalCommList.getCommitments())
       	{
       		cal.setTime(comm.getDueDate());
       		personal[cal.get(Calendar.DAY_OF_WEEK) - 1].addCommitment(comm); //day of week is 1 - 7

       	}
       	for(Commitment comm: teamCommList.getCommitments())
       	{
       		cal.setTime(comm.getDueDate());
       		team[cal.get(Calendar.DAY_OF_WEEK) - 1].addCommitment(comm);
       	}
       	
    	cal.setTime(mydate.getTime());
	    for(int i = 0; i<7; i++){
	    	DetailedDay aday = new DetailedDay(cal,new CommitDetailedPane(cal, personal[i], team[i]));
	    	System.out.println(cal.getTime().toString());
	    	aday.addMouseListener(new AMouseEvent(cal.getTime()));
	    	apane.add( aday );
	    	cal.add(Calendar.DATE, 1);
	    }
    	return apane;
    }
    
//    protected JComponent getCommits(CalendarData calData){
//    	JPanel firstpane = new JPanel();
//    	JPanel secondpane = new JPanel();
//    	SpringLayout layout = new SpringLayout();
//    	firstpane.setLayout(layout);
//    	//System.out.println(getTimesBar(100.0).getPreferredSize().width);
//		layout.putConstraint(SpringLayout.WEST, secondpane, 
//								getTimesBar(100.0).getPreferredSize().width,
//								SpringLayout.WEST, firstpane);
//		layout.putConstraint(SpringLayout.NORTH, secondpane, 0, SpringLayout.NORTH, firstpane);
//		layout.putConstraint(SpringLayout.SOUTH, secondpane, 0, SpringLayout.SOUTH, firstpane);
//		layout.putConstraint(SpringLayout.EAST, secondpane, -15, SpringLayout.EAST, firstpane);
//    	firstpane.add(secondpane);
//    	
//		secondpane.setLayout(new GridLayout(1, 7, 0, 5));
//    	
//	    for(int i = 0; i<7; i++){
//	    	Calendar acal = (Calendar)mydate.clone();
//	    	acal.add(Calendar.DAY_OF_MONTH, 1);
//	    	//TODO add function
//	    	//JPanel viewpane = getWeekCommitPaneforDate( acal );
//	    	
//	    	CommitmentView commits = new CommitmentView(calData);
//	    	
//	    	ArrayList<Commitment> comList = new ArrayList<Commitment>(calData.getCommitments().getCommitments());
//	    	ArrayList<Commitment> newList = new ArrayList<Commitment>();
//	    	for (Commitment c : comList) {
//	    		if ((c.getDueDate().getDate()  == acal.get(Calendar.DAY_OF_MONTH) ||
//	    			 c.getDueDate().getMonth() == acal.get(Calendar.MONTH)) ||
//	    			 c.getDueDate().getYear()  == acal.get(Calendar.YEAR)) {
//	    			newList.add(c);
//	    		}
//	    	}
//	    	
//	    	commits.setCommList(newList);
//	    	
//			JScrollPane ascrollpane = new JScrollPane(commits, 
//					ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, 
//					ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
//			ascrollpane.getVerticalScrollBar().setUnitIncrement(20);
//			ascrollpane.setMinimumSize(new Dimension(10,40));
//			secondpane.add(ascrollpane);
//	    }
//	    
//    	return firstpane;
//    }
    
    
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
			alab.setFont(new Font("Arial", 1, 12));
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
	
	/** Displays commitments on DetailedDays
     * @param commList List of commitments to be displayed
	 * @param dayTeamCommList 
     */
    public void displayCommitments(CommitmentList personalCommList, CommitmentList teamCommList) {
		  
    	days = getDays(personalCommList, teamCommList);
    	refresh();
	}

	
	private void refresh() {
		mainPanel.removeAll();
		if (days == null)
			days = getDays(new CommitmentList(), new CommitmentList());
		
 		layout.putConstraint(SpringLayout.WEST, days, 0, SpringLayout.WEST, mainPanel);
 		layout.putConstraint(SpringLayout.NORTH, days, 0, SpringLayout.NORTH, mainPanel);
 		layout.putConstraint(SpringLayout.SOUTH, days, 0, SpringLayout.SOUTH, mainPanel);
 		layout.putConstraint(SpringLayout.EAST, days, 0, SpringLayout.EAST, mainPanel);
 		mainPanel.add(days);
    	mainPanel.revalidate();
    	mainPanel.repaint();
    	
   	 	scrollPane.getVerticalScrollBar().setValue(GUIEventController.getInstance().getScrollBarValue());

	}


	protected class AMouseEvent implements MouseListener{
		Calendar adate = new GregorianCalendar();
		
		public AMouseEvent(Date adate){
			this.adate.setTime(adate);
		}
	
		public void mousePressed(MouseEvent e) {

	    }

	    public void mouseReleased(MouseEvent e) {

	    }

	    public void mouseEntered(MouseEvent e) {
	    }

	    public void mouseExited(MouseEvent e) {

	    }

	    public void mouseClicked(MouseEvent e) {
	    	if(e.getClickCount() > 1){
	    		//save scroll bar location
	    		GUIEventController.getInstance().setScrollBarValue(scrollPane.getVerticalScrollBar().getValue());
	    		//switch to day view
	    		GUIEventController.getInstance().switchView(adate, AbCalendar.types.DAY);
	    	}
	    }
	}


	
}
