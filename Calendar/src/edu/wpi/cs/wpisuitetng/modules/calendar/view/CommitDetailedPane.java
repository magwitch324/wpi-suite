package edu.wpi.cs.wpisuitetng.modules.calendar.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

/** 
 * JPanel overlayed on the left side of the DetailedDay view, used to display commitments
 * @author 
 *
 */
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
		HalfHourBlock[] halfBlocks = new HalfHourBlock[48];
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
				if (halfBlocks[pos] == null)
					halfBlocks[pos] = new HalfHourBlock(pos);
				halfBlocks[pos].addTeamCommitment(comm);
			}
			
		for( int i = 0; i < 48; i ++){
			if(halfBlocks[i]!=null)
			{
				layout.putConstraint(SpringLayout.WEST, halfBlocks[i], 0, SpringLayout.WEST, this);
				layout.putConstraint(SpringLayout.EAST, halfBlocks[i], 0, SpringLayout.EAST, this);
				layout.putConstraint(SpringLayout.NORTH, halfBlocks[i], (int)(y/48.0*i) + 1, SpringLayout.NORTH, this);
				layout.putConstraint(SpringLayout.SOUTH, halfBlocks[i], (int)(y/48.0*(i+1)) - 1, SpringLayout.NORTH, this);
				this.add(halfBlocks[i]);
			}
		}
		
		this.revalidate();
		this.repaint();
	}
	
	
}
