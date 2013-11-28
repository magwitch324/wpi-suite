package edu.wpi.cs.wpisuitetng.modules.calendar.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpringLayout;

import edu.wpi.cs.wpisuitetng.modules.calendar.models.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.CalendarStandard;

public class MonthPane extends JScrollPane implements ICalPane {
	JPanel mainview;

	public MonthPane(Calendar acal, AbCalendar tcalendar) {
		super();
		mainview = new JPanel();
		mainview.setMinimumSize(new Dimension(700, 700));
		mainview.setLayout(new GridLayout(6, 7, 1, 1));
		this.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		this.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.setViewportView(mainview);

		int month = acal.get(Calendar.MONTH);
		GregorianCalendar itcal = new GregorianCalendar();
		itcal.setTime(acal.getTime());

		ArrayList<Commitment> fulllist = new ArrayList<Commitment>(tcalendar
				.getCalData().getCommitments().getCommitments());
		ArrayList<Commitment> uselist = new ArrayList<Commitment>();

		Iterator<Commitment> it = fulllist.iterator();

		while (it.hasNext()) {
			Commitment temp = it.next();
			if (temp.getDueDate().get(Calendar.MONTH) == month)
				uselist.add(temp);
		}

		while (itcal.get(Calendar.DATE) != 1) {
			itcal.add(Calendar.DATE, -1);
		}

		while (itcal.get(Calendar.DAY_OF_WEEK) != itcal.getFirstDayOfWeek()) {
			itcal.add(Calendar.DATE, -1);
		}

		for (int i = 0; i < 42; i++) {
			ArrayList<Commitment> todaylist = new ArrayList<Commitment>();
			Iterator<Commitment> it2 = uselist.iterator();
			
			while (it2.hasNext()) {
				Commitment temp = it2.next();
				if (temp.getDueDate().get(Calendar.DATE) == itcal.get(Calendar.DATE))
					todaylist.add(temp);
			}
			
			JPanel aday = getDay(itcal, todaylist);
			if (month == itcal.get(Calendar.MONTH))
				aday.setBackground(Color.WHITE);
			else
				aday.setBackground(Color.GRAY);
			
			aday.addMouseListener(new AMouseEvent(acal, null));
			mainview.add(aday);

			itcal.add(Calendar.DATE, 1);
		}
	}

	protected JPanel getDay(GregorianCalendar cal, ArrayList<Commitment> commits) {
		final JPanel apanel = new JPanel();
		SpringLayout layout = new SpringLayout();
		apanel.setLayout(layout);

		final JLabel daylab = new JLabel("" + cal.get(Calendar.DATE));
		layout.putConstraint(SpringLayout.NORTH, daylab, 0, SpringLayout.NORTH,
				apanel);
		layout.putConstraint(SpringLayout.WEST, daylab, 0, SpringLayout.WEST,
				apanel);
		daylab.setFont(new Font("Arial", 1, 14));
		daylab.setBackground(new Color(0, 0, 0, 0));
		apanel.add(daylab);

		final int[] hits = new int[24];

		Iterator<Commitment> it = commits.iterator();

		while (it.hasNext()) {
			Commitment acom = it.next();
			GregorianCalendar tempcal = (GregorianCalendar) acom.getDueDate().clone();
			int pos = tempcal.get(Calendar.HOUR_OF_DAY);
			hits[pos]++;
		}

		apanel.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				int y = (int) apanel.getSize().getHeight();
				int offset = (int) daylab.getPreferredSize().getHeight();
				apanel.removeAll();
				apanel.add(daylab);
				for (int i = 0; i < 24; i++) {
					if(hits[i] !=0){
						SpringLayout layout = (SpringLayout)apanel.getLayout();
						JSeparator line = new JSeparator();
						layout.putConstraint(SpringLayout.NORTH, line, (int)(((y - offset)/24.0 *i) + offset) ,
								SpringLayout.NORTH, apanel);
						layout.putConstraint(SpringLayout.WEST, line, 0, SpringLayout.WEST,
								apanel);
						if (hits[i] <= 3) {
							layout.putConstraint(SpringLayout.EAST, line, hits[i] * 7,
									SpringLayout.WEST, apanel);
						} else {
							layout.putConstraint(SpringLayout.EAST, line, 21,
									SpringLayout.WEST, apanel);
						}
						apanel.add(line);
					}
				}
			}
		});

		return apanel;
	}

	public JPanel getPane() {
		JPanel apanel = new JPanel();
		apanel.setLayout(new GridLayout(1, 1));
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
