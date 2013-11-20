package edu.wpi.cs.wpisuitetng.modules.calendar.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
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
	
	//JComponent[] halfblocks = new JComponent[48];
	
	public CommitDetailedPane(Calendar adate, ArrayList<Commitment> commits){
		super();
		System.out.println( "start" );
		this.setLayout(new GridLayout(48,1));

		Calendar acalit = (Calendar)adate.clone();
		
		
		for(int index = 0; index < 48; index++){
			ArrayList<Commitment> tomake = new ArrayList<Commitment>();
			for( int i = 0; i < commits.size(); i++){
				Calendar acal = Calendar.getInstance();
				acal.setTime(commits.get(i).getDueDate());
				if(adate.get(Calendar.DATE) == acal.get(Calendar.DATE) &&
						adate.get(Calendar.MONTH) == acal.get(Calendar.MONTH) &&
						adate.get(Calendar.YEAR) == acal.get(Calendar.YEAR)){
					
					int pos = acal.get(Calendar.HOUR_OF_DAY)*2;
					pos += acal.get(Calendar.MINUTE) == 30 ? 1 : 0;
					
					if (pos == index){
						tomake.add(commits.get(i));
					}
				}	
			}
			this.add(new thehalfblocks(tomake));
		}

		this.setBackground(new Color(0,0,0,0));
		System.out.println( "end" );
	}
	
	/*protected void didResize(){
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
	}*/
	
	protected class thehalfblocks extends JPanel{
		public thehalfblocks(ArrayList<Commitment> commits){
			super();
			if(commits.size() > 0)
				this.setLayout(new GridLayout(1, 1, 0, 1));
			
			Iterator<Commitment> it = commits.iterator();
			
			int i = 0;
			while(it.hasNext() && i < 1){
				i++;
				this.add(this.getComPanel(it.next()));
			}
			
			this.setBackground(new Color(0,0,0,0));
			
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
