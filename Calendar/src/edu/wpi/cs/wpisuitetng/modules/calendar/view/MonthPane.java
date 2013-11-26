package edu.wpi.cs.wpisuitetng.modules.calendar.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Calendar;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class MonthPane extends JScrollPane implements ICalPane {
	JPanel mainview;
	
	public MonthPane(Calendar acal){
		super();
		mainview = new JPanel();
		mainview.setMinimumSize(new Dimension(700, 700));
		mainview.setLayout(new GridLayout(6,7,1,1));
		this.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		this.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.setViewportView(mainview);
		
		int month = acal.get(Calendar.MONTH);
		Calendar itcal = (Calendar)acal.clone();
		
		while(itcal.get(Calendar.DATE) != 1){
			itcal.add(Calendar.DATE, -1);
		}
		
		while(itcal.get(Calendar.DAY_OF_WEEK) != itcal.getFirstDayOfWeek()){
			itcal.add(Calendar.DATE, -1);
		}
		
		for(int i = 0; i < 42; i++){
			JPanel aday = new JPanel();
			if(month == itcal.get(Calendar.MONTH))
				aday.setBackground(Color.WHITE);
			else
				aday.setBackground(Color.GRAY);
			JLabel alab = new JLabel("" + itcal.get(Calendar.DATE));
			alab.setFont(new Font("Arial", 1, 14));
			alab.setBackground(new Color(0,0,0,0));
			aday.add(alab);
			aday.addMouseListener(new AMouseEvent(acal, null));
			mainview.add(aday);
			
			itcal.add(Calendar.DATE, 1);
		}
	}
	public JPanel getPane(){
		JPanel apanel = new JPanel();
		apanel.setLayout(new GridLayout(1,1));
		apanel.add(this);
		return apanel;
	}
	protected class AMouseEvent implements MouseListener{
		AbCalendar abCalendar;
		Calendar adate;
		
		public AMouseEvent(Calendar adate, AbCalendar abCalendar){
			this.adate = adate;
			this.abCalendar = abCalendar;
		}
	
		public void mousePressed(MouseEvent e) {

	    }

	    public void mouseReleased(MouseEvent e) {

	    }

	    public void mouseEntered(MouseEvent e) {
	    }

	    public void mouseExited(MouseEvent e) {

	    }

	    public void mouseClicked(MouseEvent e) {
	    	if(e.getClickCount() > 1){
	    		//abCalendar.setCalsetView(adate, TeamCalendar.types.DAY);
	    	}
	    }
	}
}
