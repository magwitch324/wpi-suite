package edu.wpi.cs.wpisuitetng.modules.calendar.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.border.EmptyBorder;

import edu.wpi.cs.wpisuitetng.modules.calendar.controller.GetCalendarDataController;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.CalendarData;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.AbCalendar.types;

/*
 * Sources:
 * Icons were developed using images obtained at: 
 * [1] http://changepointnea.com/~changepo/cms-assets/images/161361.calendar-icon.png
 * [2] http://pixabay.com/p-37197/?no_redirect
 */


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
	protected GregorianCalendar mycal;
	
	protected JPanel viewpanel = new JPanel(); 
	protected JToggleButton[] viewbtns = new JToggleButton[4];
	protected JCheckBox showcom;
	
	protected int[] viewsizeval = {Calendar.DATE, Calendar.WEEK_OF_YEAR, Calendar.MONTH, Calendar.YEAR};
	protected Font defualtfont = new Font("Arial", 1, 14);
	protected CalendarView calView;
	
	public AbCalendar(){
		super();

		mycal = new GregorianCalendar();

		super.setBackground(Color.WHITE);

		// Draws GUI
		drawThis();
		initialized = false;
	}
	
	
	abstract void drawThis();
	
	protected JComponent getViewButtonPanel(){
		JPanel apane = new JPanel();
		apane.setLayout(new GridLayout(1,4));
		
		viewbtns[0] = new JToggleButton();
		try {
			Image img = ImageIO.read(getClass().getResource("Day_Icon.png"));
		    this.viewbtns[0].setIcon(new ImageIcon(img));
		    this.viewbtns[0].setBorder(BorderFactory.createEmptyBorder());
		    viewbtns[0].setContentAreaFilled(false);
		} catch (IOException ex) {}
		catch(IllegalArgumentException ex){
			this.viewbtns[0].setIcon(new ImageIcon());
			this.viewbtns[0].setText("Day");
		} 
		
//		viewbtns[0].setFont(defualtfont);
		viewbtns[0].setBackground(Color.WHITE);
		viewbtns[0].addActionListener(new ActionListener() {
			 
            public void actionPerformed(ActionEvent e)
            {
                //Execute when button is pressed
            	switchview(types.DAY);
            }
        });
		
		
		viewbtns[1] = new JToggleButton();
		try {
			Image img = ImageIO.read(getClass().getResource("Week_Icon.png"));
		    this.viewbtns[1].setIcon(new ImageIcon(img));
		    this.viewbtns[1].setBorder(BorderFactory.createEmptyBorder());
		    viewbtns[1].setContentAreaFilled(false);
		} catch (IOException ex) {}
		catch(IllegalArgumentException ex){
			this.viewbtns[1].setIcon(new ImageIcon());
			this.viewbtns[1].setText("Week");
		}
		
//		viewbtns[1].setFont(defualtfont);
		viewbtns[1].setBackground(Color.WHITE);
		viewbtns[1].addActionListener(new ActionListener() {
			 
            public void actionPerformed(ActionEvent e)
            {
                //Execute when button is pressed
            	switchview(types.WEEK);
            }
        });
		
		viewbtns[2] = new JToggleButton();
		try {
			Image img = ImageIO.read(getClass().getResource("Month_Icon.png"));
		    this.viewbtns[2].setIcon(new ImageIcon(img));	
		    this.viewbtns[2].setBorder(BorderFactory.createEmptyBorder());
		    viewbtns[2].setContentAreaFilled(false);
		} catch (IOException ex) {}
		catch(IllegalArgumentException ex){
			this.viewbtns[2].setIcon(new ImageIcon());
			this.viewbtns[2].setText("Month");
		}
		
//		viewbtns[2].setFont(defualtfont);
		viewbtns[2].setBackground(Color.WHITE);
		viewbtns[2].addActionListener(new ActionListener() {
			 
            public void actionPerformed(ActionEvent e)
            {
                //Execute when button is pressed
            	switchview(types.MONTH);
            }
        });
		
		viewbtns[3] = new JToggleButton();
		try {
			Image img = ImageIO.read(getClass().getResource("Year_Icon.png"));
		    this.viewbtns[3].setIcon(new ImageIcon(img));
		    this.viewbtns[3].setBorder(BorderFactory.createEmptyBorder());
		    viewbtns[3].setContentAreaFilled(false);
		} catch (IOException ex) {}
		catch(IllegalArgumentException ex){
			this.viewbtns[3].setIcon(new ImageIcon());
			this.viewbtns[3].setText("Year");
		}
		
//		viewbtns[3].setFont(defualtfont);
		viewbtns[3].setBackground(Color.WHITE);
		viewbtns[3].addActionListener(new ActionListener() {
			 
            public void actionPerformed(ActionEvent e)
            {
                //Execute when button is pressed
            	switchview(types.YEAR);
            }
        });
		
		for (JToggleButton btn : viewbtns) {
			btn.setBorder(new EmptyBorder(0, 0, 0, 15));
			apane.add(btn);
			apane.setBackground(Color.WHITE);
		}
	
		return apane;
	}
	
	protected void stepCalendar(int step){
		if(step == 0){
			mycal = new GregorianCalendar();
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
		apane.setBackground(Color.WHITE);
		
		JButton backwardbutton = new JButton("<<");
		backwardbutton.setBackground(Color.WHITE);
		backwardbutton.setFont(defualtfont);
		backwardbutton.addActionListener(new ActionListener() {
			 
            public void actionPerformed(ActionEvent e)
            {
                //Execute when button is pressed
            	stepCalendar(-1);
            }
        });
		
		JButton todaybutton = new JButton("Today");
		todaybutton.setBackground(Color.WHITE);
		todaybutton.setFont(defualtfont);
		todaybutton.addActionListener(new ActionListener() {
			 
            public void actionPerformed(ActionEvent e)
            {
                //Execute when button is pressed
            	stepCalendar(0);
            }
        });
		
		JButton forwardbutton = new JButton(">>");
		forwardbutton.setBackground(Color.WHITE);
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
			calView = (new MonthView(mycal, this));
			calView.displayCalData(calData);
			viewpanel.add(calView);			
			break;
		case(3):
			//TODO YearView
			break;
		default:
			//TODO error
			break;
		}
		
		viewpanel.revalidate();
		viewpanel.repaint();
		
	}
	
	public void setCal(GregorianCalendar changeto){
		mycal.setTime(changeto.getTime());
		setView();
	}
	
	public void setCalsetView(GregorianCalendar acal, TeamCalendar.types switchtype)
	{
		mycal.setTime(acal.getTime());
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
