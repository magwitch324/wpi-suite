package edu.wpi.cs.wpisuitetng.modules.calendar.view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.GridLayout;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import edu.wpi.cs.wpisuitetng.modules.calendar.models.Commitment;

public class CommitmentCalViewPanel extends JPanel {

	private Commitment comm;
	
	public CommitmentCalViewPanel(Commitment comm)
	{
		//TODO add function for clicking to go to the editor
		this.comm = comm;
		
		GregorianCalendar acal = new GregorianCalendar();
		acal.setTime(comm.getDueDate().getTime());
		String time = "Time - " + acal.get(Calendar.HOUR) + ":" + 
					(acal.get(Calendar.MINUTE) > 10 ? 
							acal.get(Calendar.MINUTE) :
							("0" + acal.get(Calendar.MINUTE)));
		if(acal.get(Calendar.HOUR_OF_DAY) < 24)
			time += " AM";
		else
			time += " PM";
		
		
		String name = comm.getName();
		//String descr = "Descr: " + comm.getDescription();
		String tag = comm.getIsPersonal() ? "[Personal]" : "[Team]";
		setLayout(new GridLayout(2,1));
		setCursor(new Cursor(Cursor.HAND_CURSOR)); // To change cursor as it moves over this text
		JLabel alab = new JLabel(name, JLabel.CENTER);
		//alab.setSize( alab.getPreferredSize() );
		alab.setBackground(new Color(0,0,0,0));
		add(alab, SwingConstants.CENTER);
		
		alab = new JLabel(tag, JLabel.CENTER);
		//alab.setSize( alab.getPreferredSize() );
		alab.setBackground(new Color(0,0,0,0));
		add(alab, SwingConstants.CENTER);
	}

	public Commitment getCommitment() {
		return comm;
	}

}
