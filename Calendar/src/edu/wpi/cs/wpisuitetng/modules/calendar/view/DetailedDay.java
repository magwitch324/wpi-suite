package edu.wpi.cs.wpisuitetng.modules.calendar.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.Calendar;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SpringLayout;

public class DetailedDay extends JPanel {
	
	JSeparator[] halfhourmarks= new JSeparator[48];
	SpringLayout layout = new SpringLayout();
	
	DetailedDay(Calendar adate){
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
				// TODO Auto-generated method stub
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
			add(halfhourmarks[i]);
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
}
