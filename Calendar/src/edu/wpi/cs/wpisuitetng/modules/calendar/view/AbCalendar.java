package edu.wpi.cs.wpisuitetng.modules.calendar.view;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import edu.wpi.cs.wpisuitetng.modules.calendar.controller.GetCalendarDataController;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.CalendarData;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.AbCalendar.types;

public abstract class AbCalendar extends JPanel {
	protected boolean initialized;
	protected CalendarData calData;
	
	protected enum types {
		DAY(0),
		WEEK(1),
		MONTH(2),
		YEAR(3);
	
		private int currentType;
		
		private types(int currentType) {
			this.currentType = currentType;
		}
		
		public int getCurrentType() {
			return currentType;
		}
	}
	
	protected types currenttype = types.DAY;
	protected Calendar mycal;;
	
	protected JPanel viewpanel = new JPanel(); 
	protected JToggleButton[] viewbtns = new JToggleButton[4];
	protected JCheckBox showcom;
	
	protected int[] viewsizeval = {Calendar.DATE, Calendar.WEEK_OF_YEAR, Calendar.MONTH, Calendar.YEAR};
	protected Font defualtfont = new Font("Arial", 1, 14);
	protected CalendarView calView;
	
	public AbCalendar(){
		super();
		mycal = Calendar.getInstance();

		// Draws GUI
		drawThis();
		initialized = false;
	}
	
	
	abstract void drawThis();
	
	protected JComponent getViewButtonPanel(){
		JPanel apane = new JPanel();
		apane.setLayout(new GridLayout(1,4));
		
		viewbtns[0] = new JToggleButton("Day");
		viewbtns[0].setFont(defualtfont);
		viewbtns[0].addActionListener(new ActionListener() {
			 
            public void actionPerformed(ActionEvent e)
            {
                //Execute when button is pressed
            	switchview(types.DAY);
            }
        });
		
		
		viewbtns[1] = new JToggleButton("Week");
		viewbtns[1].setFont(defualtfont);
		viewbtns[1].addActionListener(new ActionListener() {
			 
            public void actionPerformed(ActionEvent e)
            {
                //Execute when button is pressed
            	switchview(types.WEEK);
            }
        });
		
		viewbtns[2] = new JToggleButton("Month");
		viewbtns[2].setFont(defualtfont);
		viewbtns[2].addActionListener(new ActionListener() {
			 
            public void actionPerformed(ActionEvent e)
            {
                //Execute when button is pressed
            	switchview(types.MONTH);
            }
        });
		
		viewbtns[3] = new JToggleButton("Year");
		viewbtns[3].setFont(defualtfont);
		viewbtns[3].addActionListener(new ActionListener() {
			 
            public void actionPerformed(ActionEvent e)
            {
                //Execute when button is pressed
            	switchview(types.YEAR);
            }
        });
		
		apane.add(viewbtns[0]);
		apane.add(viewbtns[1]);
		apane.add(viewbtns[2]);
		apane.add(viewbtns[3]);
		return apane;
	}
	
	protected void stepCalendar(int step){
		if(step == 0){
			mycal = Calendar.getInstance();
		}
		else{
			mycal.add(viewsizeval[currenttype.getCurrentType()], step);
		}
		setView();
	}
	
	protected void switchview(types changeto){
		if(currenttype != changeto){
			
			for(int i = 0; i < 4; i++){
				viewbtns[i].setSelected(false);
			}
			
			currenttype = changeto;
			setView();
		}
		
		viewbtns[changeto.getCurrentType()].setSelected(true);
	}
	
	protected JComponent getDatePanel(){
		JPanel apane = new JPanel();
		
		JButton backwardbutton = new JButton("<<");
		backwardbutton.setFont(defualtfont);
		backwardbutton.addActionListener(new ActionListener() {
			 
            public void actionPerformed(ActionEvent e)
            {
                //Execute when button is pressed
            	stepCalendar(-1);
            }
        });
		
		JButton todaybutton = new JButton("Today");
		todaybutton.setFont(defualtfont);
		todaybutton.addActionListener(new ActionListener() {
			 
            public void actionPerformed(ActionEvent e)
            {
                //Execute when button is pressed
            	stepCalendar(0);
            }
        });
		
		JButton forwardbutton = new JButton(">>");
		forwardbutton.setFont(defualtfont);
		forwardbutton.addActionListener(new ActionListener() {
			 
            public void actionPerformed(ActionEvent e)
            {
                //Execute when button is pressed
            	stepCalendar(1);
            }
        });
		
		apane.add(backwardbutton);
		apane.add(todaybutton);
		apane.add(forwardbutton);
		return apane;
	}
	
	public JComponent getComponent() {
		return this; 
	}
	
	protected void setView(){
		viewpanel.removeAll();
		viewpanel.setLayout(new GridLayout(1,1));
		//TODO do views
		switch(currenttype.getCurrentType()){
		case(0):
			calView = (new DayView(mycal, this));
			calView.displayCalData(calData);
			viewpanel.add(calView);
			break;
		case(1):
			calView = (new WeekView(mycal, this));
			calView.displayCalData(calData);
			viewpanel.add(calView);
			break;
		case(2):
			//TODO dayview
			break;
		case(3):
			//TODO dayview
			break;
		default:
			//TODO error
			break;
		}
		
		viewpanel.revalidate();
		viewpanel.repaint();
		
	}
	
	public void setCal(Calendar changeto){
		mycal = (Calendar)changeto.clone();
		setView();
	}
	
	public void setCalsetView(Calendar acal, TeamCalendar.types switchtype)
	{
		mycal = (Calendar)acal.clone();
		switchview(switchtype);
		setView();
	}
	
	public boolean getShowCommitements(){
		return showcom.isSelected();
	}
	
	public CalendarData getCalData(){
		return this.calData;
	}
	/**
	 * Overrides the paintComponent method to retrieve the requirements on the first painting.
	 * 
	 * @param g	The component object to paint
	 */
	@Override
	public void paintComponent(Graphics g)
	{
		if(!initialized)
		{
			try 
			{
				GetCalendarDataController.getInstance().retrieveCalendarData();
				System.out.println("retrieved on initialization2");
				/*if (CalendarDataModel.getInstance().getCalendarData(ConfigManager.getConfig().getProjectName()) == null){
					System.out.println("CREATING NEW CALDATA");
					CalendarData createdCal = new CalendarData(ConfigManager.getConfig().getProjectName());
					CalendarDataModel.getInstance().addCalendarData(createdCal);
				}*/
				initialized = true;
			}
			catch (Exception e)
			{

			}
		}
		super.paintComponent(g);
	}
	
	abstract void updateCalData();
	abstract public boolean getShowTeamData();
	
}
