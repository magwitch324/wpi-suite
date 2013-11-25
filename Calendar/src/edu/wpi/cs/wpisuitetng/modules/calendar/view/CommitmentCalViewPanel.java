package edu.wpi.cs.wpisuitetng.modules.calendar.view;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.Calendar;

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
		
		Calendar acal = (Calendar)Calendar.getInstance().clone();
		acal.setTime(comm.getDueDate());
		String time = "Time - " + acal.get(Calendar.HOUR) + ":" + 
					(acal.get(Calendar.MINUTE) > 10 ? 
							acal.get(Calendar.MINUTE) :
							("0" + acal.get(Calendar.MINUTE)));
		if(acal.get(Calendar.HOUR_OF_DAY) < 24)
			time += " AM";
		else
			time += " PM";
		
		
		String name = "Name: " + comm.getName();
		String descr = "Descr: " + comm.getDescription();
		setLayout(new GridLayout(2,1));
		JLabel alab = new JLabel(descr, JLabel.CENTER);
		//alab.setSize( alab.getPreferredSize() );
		alab.setBackground(new Color(0,0,0,0));
		add(alab, SwingConstants.CENTER);
		
		alab = new JLabel(name, JLabel.CENTER);
		//alab.setSize( alab.getPreferredSize() );
		alab.setBackground(new Color(0,0,0,0));
		add(alab, SwingConstants.CENTER);
	}

	public Commitment getCommitment() {
		return comm;
	}

}
