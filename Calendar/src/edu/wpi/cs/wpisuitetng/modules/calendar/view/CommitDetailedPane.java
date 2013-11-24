package edu.wpi.cs.wpisuitetng.modules.calendar.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.Calendar;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Iterator;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import edu.wpi.cs.wpisuitetng.modules.calendar.models.Commitment;

public class CommitDetailedPane extends JPanel {
	
	ArrayList<Commitment> commits;
	GregorianCalendar adate;
	public CommitDetailedPane(GregorianCalendar adate, ArrayList<Commitment> commits){
		super();
		
		System.out.println( "start" );
		this.setLayout(new SpringLayout());
		this.addComponentListener(new ComponentListener() {
		    public void componentResized(ComponentEvent e) {didResize();}
	
			@Override
			public void componentMoved(ComponentEvent e) {}
	
			@Override
			public void componentShown(ComponentEvent e) {}
	
			@Override
			public void componentHidden(ComponentEvent e) {}
		});
		
		this.commits = commits;
		this.adate = new GregorianCalendar();
		this.adate.setTime(adate.getTime());
		
		this.didResize();

		this.setBackground(new Color(0,0,0,0));
		System.out.println( "end" );
	}
	
	protected void didResize(){
		System.out.println( "did resize" );
		JComponent[] halfblocks = new JComponent[48];
		this.removeAll();
		
		int x = (int)this.getSize().getWidth();
		int y = (int)this.getSize().getHeight();
		SpringLayout layout = (SpringLayout)this.getLayout();

		for(int index = 0; index < 48; index++){
			ArrayList<Commitment> tomake = new ArrayList<Commitment>();
			for( int i = 0; i < commits.size(); i++){
				GregorianCalendar acal = new GregorianCalendar();
				acal.setTime(commits.get(i).getDueDate().getTime());
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
			halfblocks[index] = new thehalfblocks(tomake);
		}
		
		for( int i = 0; i < 48; i ++){
			layout.putConstraint(SpringLayout.WEST, halfblocks[i], 0, SpringLayout.WEST, this);
			layout.putConstraint(SpringLayout.EAST, halfblocks[i], 0, SpringLayout.EAST, this);
			layout.putConstraint(SpringLayout.NORTH, halfblocks[i], (int)(y/48.0*i) + 1, SpringLayout.NORTH, this);
			layout.putConstraint(SpringLayout.SOUTH, halfblocks[i], (int)(y/48.0*(i+1)) - 1, SpringLayout.NORTH, this);
			this.add(halfblocks[i]);
		}
		
		this.revalidate();
		this.repaint();
	}
	
	protected class thehalfblocks extends JPanel{
		public thehalfblocks(ArrayList<Commitment> commits){
			super();
			if(commits.size() > 0){
				this.setLayout(new GridLayout(1, commits.size(), 0, 1));
				System.out.println(commits.size());
			}
			
			Iterator<Commitment> it = commits.iterator();
			
			while(it.hasNext()){
				this.add(this.getComPanel(it.next()), SwingConstants.CENTER);
			}
			
			this.setBackground(new Color(0,0,0,0));
		}
		
		private JComponent getComPanel(Commitment tochange){
			JPanel apane = new JPanel();
			apane.setBackground(new Color(255,255,255));
			//TODO add function for clicking to go to the editor
			
			GregorianCalendar acal = new GregorianCalendar();
			acal.setTime(tochange.getDueDate().getTime());
			String time = "Time - " + acal.get(Calendar.HOUR) + ":" + 
						(acal.get(Calendar.MINUTE) > 10 ? 
								acal.get(Calendar.MINUTE) :
								("0" + acal.get(Calendar.MINUTE)));
			if(acal.get(Calendar.HOUR_OF_DAY) < 24)
				time += " AM";
			else
				time += " PM";
			
			
			String name = "Name: " + tochange.getName();
			String descr = "Descr: " + tochange.getDescription();
			apane.setLayout(new GridLayout(2,1));
			JLabel alab = new JLabel(descr, JLabel.CENTER);
			//alab.setSize( alab.getPreferredSize() );
			alab.setBackground(new Color(0,0,0,0));
			apane.add(alab, SwingConstants.CENTER);
			
			alab = new JLabel(name, JLabel.CENTER);
			//alab.setSize( alab.getPreferredSize() );
			alab.setBackground(new Color(0,0,0,0));
			apane.add(alab, SwingConstants.CENTER);

			LineBorder roundedLineBorder = new LineBorder(Color.black, 1, true);
			apane.setBorder(roundedLineBorder);
			return apane;
		}
		
		
	}
}
