/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: CS Anonymous
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.calendar.view;

import java.awt.Color;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.SpringLayout;

import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.Commitment;

/** 
 * JPanel overlayed on the left side of the DetailedDay view, used to display commitments

 *
 * @author  CS Anonymous
 * @version $Revision: 1.0 $
 */
@SuppressWarnings("serial")
public class CommitDetailedPane extends JPanel {
	
	List<Commitment> commits;
	GregorianCalendar adate;
	/**
	 * Constructor for CommitDetailedPane.
	 * @param adate GregorianCalendar
	 * @param commits List<Commitment>
	 */
	public CommitDetailedPane(GregorianCalendar adate, List<Commitment> commits){
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

		this.setBackground(new Color(0, 0, 0, 0));
	}
	
	/**
	 * Method didResize.
	 */
	protected void didResize(){
		final HalfHourBlock[] halfBlocks = new HalfHourBlock[48];
		this.removeAll();
		
		final int y = (int)this.getSize().getHeight();
		final SpringLayout layout = (SpringLayout)this.getLayout();

//		Old code 
//		
//		for(int index = 0; index < 48; index++){
//			ArrayList<Commitment> tomake = new ArrayList<Commitment>();
//			for( int i = 0; i < commits.size(); i++){
//				GregorianCalendar acal = new GregorianCalendar();
//				acal.setTime(commits.get(i).getDueDate().getTime());
//				if(adate.get(Calendar.DATE) == acal.get(Calendar.DATE) &&
//						adate.get(Calendar.MONTH) == acal.get(Calendar.MONTH) &&
//						adate.get(Calendar.YEAR) == acal.get(Calendar.YEAR)){
//					
//					int pos = acal.get(Calendar.HOUR_OF_DAY)*2;
//					pos += acal.get(Calendar.MINUTE) == 30 ? 1 : 0;
//					
//					if (pos == index){
//						tomake.add(commits.get(i));
//					}
//				}	
//			}
//			halfblocks[index] = new thehalfblocks(tomake);
//		}
		
//			List<thehalfblocks> dayComms = new ArrayList<thehalfblocks>();
			
			for(Commitment comm : commits)
			{
				Calendar cal = new GregorianCalendar();
				cal.setTime(comm.getDueDate().getTime());
				int pos = cal.get(Calendar.HOUR_OF_DAY) * 2;
				pos += (cal.get(Calendar.MINUTE) == 30) ? 1 : 0;
				if (halfBlocks[pos] == null)
					{halfBlocks[pos] = new HalfHourBlock(pos);}
				halfBlocks[pos].addTeamCommitment(comm);
			}
			
		for( int i = 0; i < 48; i ++){
			if(halfBlocks[i] != null)
			{
				layout.putConstraint(SpringLayout.WEST, halfBlocks[i], 0, SpringLayout.WEST, this);
				layout.putConstraint(SpringLayout.EAST, halfBlocks[i], 0, SpringLayout.EAST, this);
				layout.putConstraint(SpringLayout.NORTH, halfBlocks[i], 
						(int)(y / 48.0 * i) + 1, SpringLayout.NORTH, this);
				layout.putConstraint(SpringLayout.SOUTH, halfBlocks[i], 
						(int)(y / 48.0 * (i + 1)) - 1, SpringLayout.NORTH, this);
				this.add(halfBlocks[i]);
			}
		}
		
		this.revalidate();
		this.repaint();
	}
	
}
