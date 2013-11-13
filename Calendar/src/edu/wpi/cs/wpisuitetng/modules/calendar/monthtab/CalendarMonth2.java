package edu.wpi.cs.wpisuitetng.modules.calendar.monthtab;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.Calendar;
import java.util.Date;

import org.jdesktop.swingx.JXMonthView;
import org.jdesktop.swingx.calendar.DateSelectionModel.SelectionMode;

public class CalendarMonth2 extends JXMonthView implements ActionListener, KeyListener{
	
	public static final Color START_END_DAY = new Color(47, 150, 9);
	public static final Color SELECTION = new Color(236,252,144);
	public static final Color UNSELECTABLE = Color.red;
	
	private Date startDate = null;
	private Date endDate = null;

	/**
	 * Create the panel.
	 */
	public CalendarMonth2() {
		buildLayout();
	}
	
	private void buildLayout()
	{
		this.setPreferredColumnCount(1);
		this.setPreferredRowCount(1);
		this.setZoomable(true);
		this.setSelectionBackground(SELECTION);
		this.setFlaggedDayForeground(START_END_DAY);
		this.setSelectionMode(SelectionMode.SINGLE_INTERVAL_SELECTION);
		this.setAlignmentX(CENTER_ALIGNMENT);
		this.addActionListener(this);
		this.addMouseMotionListener(new MouseMotionListener()
		{
			@Override
			public void mouseDragged(MouseEvent e) {				
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();
				
				Date forLocation = CalendarMonth2.this.getDayAtLocation(x, y);
				
				if(forLocation != null)
				{
					CalendarMonth2.this.setToolTipText(null);
				}
			}
		});
		
		this.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				if(e.getClickCount() == 2)
				{
					int x = e.getX();
					int y = e.getY();
					Date forClick = CalendarMonth2.this.getDayAtLocation(x, y);
				}	
			}
		});
		
		this.addKeyListener(this);
	}
	private void handleSelectionForPanel()
	{
			//check that the selected dates are valid dates.
			boolean validSelection = true;

			Date secondDate = this.getSelectionModel().getLastSelectionDate();
			Calendar firstDate = Calendar.getInstance();
			firstDate.setTime(this.getSelectionModel().getFirstSelectionDate());
			
			if(isUnselectableDate(firstDate.getTime()) || isUnselectableDate(secondDate)) validSelection = false;
			
			//if any date in the interval is unselectable, its invalid.
			while(firstDate.getTime().before(secondDate))
			{
				if(isUnselectableDate(firstDate.getTime()))
				{
					validSelection = false;
					break;
				}
				
				firstDate.add(Calendar.DAY_OF_YEAR, 1);
			}
		
			if(validSelection)
			{
				//if is valid date, update it as date in parent.
				startDate = this.getSelectionModel().getFirstSelectionDate();
				endDate = this.getSelectionModel().getLastSelectionDate();
			}
			else
			{
				//otherwise, return back to previous selection.
				this.getSelectionModel().clearSelection();
				if(startDate != null && endDate != null)
				{
					this.getSelectionModel().setSelectionInterval(startDate, endDate);
				}
			}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		handleSelectionForPanel();
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		handleSelectionForPanel();		
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		handleSelectionForPanel();		
	}
	@Override
	public void keyReleased(KeyEvent e) {
		handleSelectionForPanel();		
	}

}
