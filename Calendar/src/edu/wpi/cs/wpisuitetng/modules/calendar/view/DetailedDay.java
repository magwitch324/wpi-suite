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
	private JPanel backgroundPanel;
	private JPanel foregroundPanel;
	
	
	
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
		OverlayLayout layerLayout = new OverlayLayout(this);
		this.setLayout(layerLayout);
		foregroundPanel = new JPanel();
		this.add(foregroundPanel);
		SpringLayout fLayout = new SpringLayout();
		foregroundPanel.setLayout(fLayout);
		foregroundPanel.setOpaque(false);
		
		backgroundPanel = new JPanel();
		backgroundPanel.setMinimumSize(new Dimension(50, 800));
		backgroundPanel.setPreferredSize(new Dimension(50, 800));
		this.add(backgroundPanel);
		backgroundPanel.setLayout(layout);
		
		
		
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
			backgroundPanel.add(halfhourmarks[i]);
			
			
				
		}
		
		layout.putConstraint(SpringLayout.NORTH, halfhourmarks[0], 0, SpringLayout.NORTH, backgroundPanel);
		
		this.didResize();
	}
	
	public JPanel get(){
		return this;
	}
	
	protected void didResize(){
		int x = (int)(((backgroundPanel.getSize().getWidth())*0.01)*((backgroundPanel.getSize().getWidth())*0.01));
		x = x > 5 ? x : 5;
		x = x < 15 ? x : 15;
		layout.putConstraint(SpringLayout.WEST, halfhourmarks[0], x, SpringLayout.WEST, backgroundPanel);
		layout.putConstraint(SpringLayout.EAST, halfhourmarks[0], -x, SpringLayout.EAST, backgroundPanel);
		
		for(int i = 1; i < 48; i++){
			int val = x;
			if(i%2==1)
				val*=2;

			layout.putConstraint(SpringLayout.VERTICAL_CENTER, halfhourmarks[i], 
								(int)((backgroundPanel.getSize().getHeight())*i/48.0),
								SpringLayout.NORTH, backgroundPanel);
			
			layout.putConstraint(SpringLayout.WEST, halfhourmarks[i], val, SpringLayout.WEST, backgroundPanel);
			layout.putConstraint(SpringLayout.EAST, halfhourmarks[i], -val, SpringLayout.EAST, backgroundPanel);
			
			
		}
		
		backgroundPanel.revalidate();
		backgroundPanel.repaint();
	}
	
	public void addCommitment(Commitment comm)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(comm.getDueDate());
		int loc = cal.get(Calendar.HOUR_OF_DAY)*2;
		if (cal.get(Calendar.MINUTE) == 30)
			loc += 1;
		JPanel test = new JPanel();
		test.setBackground(Color.red);
		backgroundPanel.add(test);				
		layout.putConstraint(SpringLayout.NORTH, test, 0, SpringLayout.NORTH, halfhourmarks[loc]);
		layout.putConstraint(SpringLayout.EAST, test, 0, SpringLayout.EAST, backgroundPanel);
		layout.putConstraint(SpringLayout.WEST, test, 0, SpringLayout.WEST, backgroundPanel);
		layout.putConstraint(SpringLayout.SOUTH, test, 0, SpringLayout.NORTH, halfhourmarks[loc+1]);
	}

	public void addCommitments(CommitmentList commList) {
		// TODO Auto-generated method stub
		for(int i = 0; i < commList.getSize(); i++)
			addCommitment(commList.getElementAt(i));
	}
}
