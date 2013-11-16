package edu.wpi.cs.wpisuitetng.modules.calendar.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.SpringLayout;

public class TeamCalendar extends JPanel implements ICalendar {
	
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
	
	types currenttype = types.DAY;
	Calendar mycal = Calendar.getInstance();
	
	JPanel viewpanel = new JPanel(); 
	JToggleButton[] viewbtns = new JToggleButton[4];
	
	int[] viewsizeval = {Calendar.DATE, Calendar.WEEK_OF_YEAR, Calendar.MONTH, Calendar.YEAR};
	
	
	public TeamCalendar() {
		super();
		drawThis();
	}
	
	protected void drawThis(){
		SpringLayout layout = new SpringLayout();
		setLayout(layout);
		
		JComponent viewbtnpanel = getViewButtonPanel();
		layout.putConstraint(SpringLayout.WEST, viewbtnpanel, 5, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, viewbtnpanel, 5, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.EAST, viewbtnpanel, -30, SpringLayout.HORIZONTAL_CENTER, this);
		this.add(viewbtnpanel);
		
		JComponent datepanel = getDatePanel();
		layout.putConstraint(SpringLayout.NORTH, datepanel, 5, SpringLayout.SOUTH, viewbtnpanel);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, datepanel, 0, SpringLayout.HORIZONTAL_CENTER, this);
		this.add(datepanel);
		
		JComboBox filter = new JComboBox();
		layout.putConstraint(SpringLayout.WEST, filter, 30, SpringLayout.HORIZONTAL_CENTER, this);
		layout.putConstraint(SpringLayout.NORTH, filter, 5, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.EAST, filter, -5, SpringLayout.EAST, this);
		this.add(filter);
		
		layout.putConstraint(SpringLayout.WEST, viewpanel, 5, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, viewpanel, 5, SpringLayout.SOUTH, datepanel);
		layout.putConstraint(SpringLayout.EAST, viewpanel, 5, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.SOUTH, viewpanel, 5, SpringLayout.SOUTH, this);
		
		this.add(viewpanel);
		
		setView();
		
	}
	
	protected JComponent getViewButtonPanel(){
		JPanel apane = new JPanel();
		
		viewbtns[0] = new JToggleButton("Day");
		viewbtns[0].addActionListener(new ActionListener() {
			 
            public void actionPerformed(ActionEvent e)
            {
                //Execute when button is pressed
            	switchview(types.DAY);
            }
        });
		
		
		viewbtns[1] = new JToggleButton("Week");
		viewbtns[1].addActionListener(new ActionListener() {
			 
            public void actionPerformed(ActionEvent e)
            {
                //Execute when button is pressed
            	switchview(types.WEEK);
            }
        });
		
		viewbtns[2] = new JToggleButton("Month");
		viewbtns[2].addActionListener(new ActionListener() {
			 
            public void actionPerformed(ActionEvent e)
            {
                //Execute when button is pressed
            	switchview(types.MONTH);
            }
        });
		
		viewbtns[3] = new JToggleButton("Year");
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
		backwardbutton.addActionListener(new ActionListener() {
			 
            public void actionPerformed(ActionEvent e)
            {
                //Execute when button is pressed
            	stepCalendar(-1);
            }
        });
		
		JButton todaybutton = new JButton("Today");
		todaybutton.addActionListener(new ActionListener() {
			 
            public void actionPerformed(ActionEvent e)
            {
                //Execute when button is pressed
            	stepCalendar(0);
            }
        });
		
		JButton forwardbutton = new JButton(">>");
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
		//TODO do views
		
	}
	
	
	
	
}
