package edu.wpi.cs.wpisuitetng.modules.calendar.view;

import java.awt.LayoutManager;

import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.modules.calendar.models.Commitment;

public class CommitmentViewPanel extends JPanel {

	private Commitment comm;
	
	public CommitmentViewPanel() {
		// TODO Auto-generated constructor stub
	}

	public CommitmentViewPanel(LayoutManager layout) {
		super(layout);
		// TODO Auto-generated constructor stub
	}

	public CommitmentViewPanel(Commitment commitment) {
		// TODO Auto-generated constructor stub
		this.comm = commitment;
	}

	public Commitment getCommitment() {
		return comm;
	}

	public void setCommitment(Commitment comm) {
		this.comm = comm;
	}

	

}
