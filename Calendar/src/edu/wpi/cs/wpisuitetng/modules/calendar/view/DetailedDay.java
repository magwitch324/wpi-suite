package edu.wpi.cs.wpisuitetng.modules.calendar.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.Calendar;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.OverlayLayout;
import javax.swing.SpringLayout;
import javax.swing.border.LineBorder;

import edu.wpi.cs.wpisuitetng.modules.calendar.controller.GetCalendarDataController;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.CalendarData;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.CommitmentList;

public class DetailedDay extends JPanel {
	
	JSeparator[] halfhourmarks= new JSeparator[48];
	SpringLayout layout = new SpringLayout();
	
	
	
	public DetailedDay(Calendar adate){
		super();		
		this.setMinimumSize(new Dimension(50, 800));
		this.setPreferredSize(new Dimension(50, 800));
		this.addComponentListener(new ComponentListener() {
		    public void componentResized(ComponentEvent e) {
		        // do stuff    
		    	didResize();
		    }

			@Override
			public void componentMoved(ComponentEvent e) {
				// TODO Auto-generated method stub	
			}

			@Override
			public void componentShown(ComponentEvent e) {
				System.out.println("here!!!");
			}

			@Override
			public void componentHidden(ComponentEvent e) {
				// TODO Auto-generated method stub
			}
		});
		
		
		this.setLayout(layout);
		
		
		
		//half hour marks code
		for(int i = 0; i < 48; i++){
			halfhourmarks[i] = new JSeparator();
			
			Color col;
			if(i%2==0){
				col = Color.BLACK;
			}
			else{
				col = Color.GRAY;
			}
			halfhourmarks[i].setBackground(col);
			this.add(halfhourmarks[i]);
			
			
				
		}
		
		layout.putConstraint(SpringLayout.NORTH, halfhourmarks[0], 0, SpringLayout.NORTH, this);
		
		this.didResize();
	}
	
	public JPanel get(){
		return this;
	}
	
	protected void didResize(){
		int x = (int)(((this.getSize().getWidth())*0.01)*((this.getSize().getWidth())*0.01));
		x = x > 5 ? x : 5;
		x = x < 15 ? x : 15;
		layout.putConstraint(SpringLayout.WEST, halfhourmarks[0], x, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.EAST, halfhourmarks[0], -x, SpringLayout.EAST, this);
		
		for(int i = 1; i < 48; i++){
			int val = x;
			if(i%2==1)
				val*=2;

			layout.putConstraint(SpringLayout.VERTICAL_CENTER, halfhourmarks[i], 
								(int)((this.getSize().getHeight())*i/48.0),
								SpringLayout.NORTH, this);
			
			layout.putConstraint(SpringLayout.WEST, halfhourmarks[i], val, SpringLayout.WEST, this);
			layout.putConstraint(SpringLayout.EAST, halfhourmarks[i], -val, SpringLayout.EAST, this);
			
			
		}
		
		this.revalidate();
		this.repaint();
	}
	
	/**
	 * @param comm Commitment to be added to the display
	 */
	public void addCommitment(Commitment comm)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(comm.getDueDate());
		
		//map hour to location in terms of halfhourmarks (0 - 47)
		int loc = cal.get(Calendar.HOUR_OF_DAY)*2;
		if (cal.get(Calendar.MINUTE) == 30)
			loc += 1;
		
		//add white panel in appropriate half hour slot
		JPanel commPanel = new JPanel();
		commPanel.setBackground(Color.white);
		this.add(commPanel);				
		layout.putConstraint(SpringLayout.NORTH, commPanel, 0, SpringLayout.NORTH, halfhourmarks[loc]);
		layout.putConstraint(SpringLayout.EAST, commPanel, 0, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.WEST, commPanel, 0, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.SOUTH, commPanel, 0, SpringLayout.NORTH, halfhourmarks[loc+1]);
	}

	/**
	 * @param commList CommitmentList to be added to the display
	 */
	public void addCommitments(CommitmentList commList) {
		// TODO Auto-generated method stub
		for(int i = 0; i < commList.getSize(); i++)
			addCommitment(commList.getElementAt(i));
	}
}