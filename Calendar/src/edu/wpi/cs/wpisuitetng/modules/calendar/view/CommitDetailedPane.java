package edu.wpi.cs.wpisuitetng.modules.calendar.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Calendar;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import edu.wpi.cs.wpisuitetng.modules.calendar.models.Commitment;

public class CommitDetailedPane extends JPanel {
	JComponent[] halfblocks = new JComponent[48];
	public CommitDetailedPane(Calendar adate, ArrayList<Commitment> commits){
		super();
		this.setLayout(new GridLayout(7,1));
		Calendar acalit = (Calendar)adate.clone();
		acalit.clear(Calendar.HOUR_OF_DAY);
		acalit.clear(Calendar.MINUTE);
		acalit.clear(Calendar.SECOND);
		acalit.clear(Calendar.MILLISECOND);
		
		Iterator<Commitment> it = commits.iterator();
		Commitment cur = null;
		while(it.hasNext()){
			cur = it.next();
		}
		while(cur != null && it.hasNext() && cur.getDueDate().before(acalit.getTime())){
			cur = it.next();
		}
		for(int i = 0; i < 48; i++){
			ArrayList<Commitment> tomake = new ArrayList<Commitment>();
			
			acalit.add(Calendar.MINUTE, 30);
			while(cur != null && it.hasNext()){
				tomake.add(cur);
				cur = it.next();
				if(cur.getDueDate().before(acalit.getTime()) ){
					break;
				}
			}
			
			if(cur.getDueDate().before(acalit.getTime()) ){
				tomake.add(cur);
			}
			
			halfblocks[i] = new halfblocks(tomake);
			halfblocks[i].setBackground(new Color(0,0,0,0));
			this.add(halfblocks[i]);
		}
		
		this.setBackground(new Color(0,0,0,0));
		
	}
	
	protected void didResize(){
		System.out.println( "did resize" );
		int x = (int)this.getSize().getWidth();
		int y = (int)this.getSize().getHeight();
		SpringLayout layout = (SpringLayout)this.getLayout();
		
		for( int i = 0; i < 48; i ++){
			layout.putConstraint(SpringLayout.WEST, halfblocks[i], 0, SpringLayout.WEST, this);
			layout.putConstraint(SpringLayout.EAST, halfblocks[i], 0, SpringLayout.EAST, this);
			layout.putConstraint(SpringLayout.NORTH, halfblocks[i], (int)(y/48.0*i) + 1, SpringLayout.NORTH, this);
			layout.putConstraint(SpringLayout.SOUTH, halfblocks[i], (int)(y/48.0*(i+1)) - 1, SpringLayout.NORTH, this);
		}
		
		this.revalidate();
		this.repaint();
	}
	
	protected class halfblocks extends JPanel{
		public halfblocks(ArrayList<Commitment> commits){
			super();
			this.setLayout(new GridLayout(1, commits.size(), 0, 1));
			
			Iterator<Commitment> it = commits.iterator();
			
			while(it.hasNext()){
				this.add(this.getComPanel(it.next()));
			}
			
		}
		
		private JComponent getComPanel(Commitment tochange){
			JPanel apane = new JPanel();
			apane.setBackground(new Color(0,0,0,0));
			//TODO add function for clicking to go to the editor
			
			Calendar acal = (Calendar)Calendar.getInstance().clone();
			acal.setTime(tochange.getDueDate());
			String time = "Time - " + acal.get(Calendar.HOUR) + ":" + 
						(acal.get(Calendar.MINUTE) > 10 ? 
								acal.get(Calendar.MINUTE) :
								("0" + acal.get(Calendar.MINUTE)));
			if(acal.get(Calendar.HOUR_OF_DAY) < 24)
				time += " AM";
			else
				time += " PM";
			
			
			String name = "Name - " + tochange.getName();
			String descr = "Descr - " + tochange.getDescription();
			apane.setLayout(new GridLayout(2,1));
			
			JLabel alab = new JLabel(time + " " + name);
			//alab.setSize( alab.getPreferredSize() );
			alab.setBackground(new Color(0,0,0,0));
			apane.add(alab);
			
			alab = new JLabel(descr);
			//alab.setSize( alab.getPreferredSize() );
			alab.setBackground(new Color(0,0,0,0));
			apane.add(alab);
			
			//apane.setPreferredSize(new Dimension(100,100));
			return apane;
		}
		
		
	}
}
