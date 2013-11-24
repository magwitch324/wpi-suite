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
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import edu.wpi.cs.wpisuitetng.modules.calendar.models.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.CommitmentList;

public class CommitDetailedPane extends JPanel {
	
	Calendar adate;
	private List<Commitment> personalCommits;
	private List<Commitment> teamCommits;
	
	public CommitDetailedPane(Calendar adate, CommitmentList personalCommList, CommitmentList teamCommList){
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
		
		this.personalCommits = personalCommList.getCommitments();
		this.teamCommits = teamCommList.getCommitments();
		this.adate = (Calendar)adate.clone();
		
		this.didResize();

		this.setBackground(new Color(0,0,0,0));
		System.out.println( "end" );
	}
	
	protected void didResize(){
		System.out.println( "did resize" );
		thehalfblocks[] halfblocks = new thehalfblocks[48];
		this.removeAll();
		
		int x = (int)this.getSize().getWidth();
		int y = (int)this.getSize().getHeight();
		SpringLayout layout = (SpringLayout)this.getLayout();

		
//			List<thehalfblocks> dayComms = new ArrayList<thehalfblocks>();
			
			for(Commitment comm : teamCommits)
			{
				Calendar cal = new GregorianCalendar();
				cal.setTime(comm.getDueDate());
				int pos = cal.get(Calendar.HOUR_OF_DAY)*2;
				pos += (cal.get(Calendar.MINUTE) == 30) ? 1 : 0;
				if (halfblocks[pos] == null)
					halfblocks[pos] = new thehalfblocks(comm , pos);
				halfblocks[pos].addCommitment(comm);
			}
			
		for( int i = 0; i < 48; i ++){
			if(halfblocks[i]!=null)
			{
				JButton button = new JButton("Test");
				layout.putConstraint(SpringLayout.WEST, halfblocks[i], 0, SpringLayout.WEST, this);
				layout.putConstraint(SpringLayout.EAST, halfblocks[i], 0, SpringLayout.EAST, this);
				layout.putConstraint(SpringLayout.NORTH, halfblocks[i], (int)(y/48.0*i) + 1, SpringLayout.NORTH, this);
				layout.putConstraint(SpringLayout.SOUTH, halfblocks[i], (int)(y/48.0*(i+1)) - 1, SpringLayout.NORTH, this);
				this.add(halfblocks[i]);
			}
		}
		
		this.revalidate();
		this.repaint();
	}
	
	protected class thehalfblocks extends JPanel{
		private int size;
		private GridLayout layout;
		private int pos;
		public thehalfblocks(Commitment commit, int pos){
			super();
			this.pos = pos;
			size = 0;
			layout = new GridLayout(1,1,0,1);
			this.addCommitment(commit);
			this.setLayout(layout);
			this.setBackground(new Color(0,0,0,0));
		}
		
		public void addCommitment(Commitment newComm)
		{
			size++;
			layout.setColumns(size);
			this.add(this.getComPanel(newComm), SwingConstants.CENTER);
			
		}
		
		private JComponent getComPanel(Commitment tochange){
			JPanel apane = new JPanel();
			apane.setBackground(new Color(255,255,255));
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

		public int getPos() {
			return pos;
		}
		
		
	}
}
