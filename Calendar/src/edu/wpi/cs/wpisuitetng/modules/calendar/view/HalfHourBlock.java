package edu.wpi.cs.wpisuitetng.modules.calendar.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.Commitment;

/** JPanel containing however many CommitmentCalViewPanels fall on a certain half hour
 * @author sfp
 *
 */
public class HalfHourBlock extends JPanel{
	private int size;
	private GridLayout layout;
	private int pos;
	private List<JComponent> teamComPanels = new ArrayList<JComponent>();
	private List<JComponent> personalComPanels = new ArrayList<JComponent>();
	
	/** Constructor
	 * @param pos Time value position of block (0 - 47)
	 */
	public HalfHourBlock(int pos){
		super();
		this.pos = pos;
		size = 0;
		layout = new GridLayout(1,1,0,1);
		this.setLayout(layout);
		this.setBackground(new Color(0,0,0,0));
	}
	
	/** Add a team commitment panel
	 * @param newComm
	 */
	public void addTeamCommitment(Commitment newComm)
	{
		size++;
		layout.setColumns(size);
		JComponent comPanel = this.getTeamComPanel(newComm);
		this.add(comPanel, SwingConstants.CENTER);
		comPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 1){
					setFull(e.getComponent());
				}
			}		
		});
		teamComPanels.add(comPanel);
	}
	
	/**	Add a personal commitment panel
	 * @param newComm
	 */
	public void addPersonalCommitment(Commitment newComm)
	{
		size++;
		layout.setColumns(size);
		JComponent comPanel = this.getPersonalComPanel(newComm);
		this.add(comPanel, SwingConstants.CENTER);
		comPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 1){
					setFull(e.getComponent());
				}
			}		
		});
		personalComPanels.add(comPanel);
		
	}
	
	/** 
	 * Create a display panel for a team commitment
	 * @param tochange 
	 * @return
	 */
	private JComponent getTeamComPanel(Commitment tochange){
		CommitmentCalViewPanel apane = new CommitmentCalViewPanel(tochange);
		apane.setBackground(new Color(255,255,255));
		

		LineBorder roundedLineBorder = new LineBorder(Color.black, 1, true);
		apane.setBorder(roundedLineBorder);
		
		apane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() > 1){
					GUIEventController.getInstance().editCommitment(((CommitmentCalViewPanel)e.getComponent()).getCommitment());
				}
			}		
		});
		
		
		return apane;
	}
	
	/**
	 * Maximize a certain component, so that it takes up the whole half hour block until mouse exit
	 * @param maximizeComponent
	 */
	protected void setFull(Component maximizeComponent) {
		// TODO Auto-generated method stub
		layout.setColumns(1);
		this.removeAll();
		this.add(maximizeComponent);
		this.setLayout(layout);
		maximizeComponent.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				restoreDefault();
			}		
		});
		this.revalidate();
		this.repaint();
	}

	/**
	 * Restore the half hour block to its normal appearance after one component is maximized
	 */
	protected void restoreDefault() {
		// TODO Auto-generated method stub
		
		size = teamComPanels.size() + personalComPanels.size();
		layout.setColumns(size);
		this.setLayout(layout);
		this.removeAll();
		for (JComponent comp: teamComPanels)
		{
			this.add(comp);
		}
		for (JComponent comp: personalComPanels)
		{
			this.add(comp);
		}

		this.revalidate();
		this.repaint();
	}

	/** Create a display panel for a personal commitment
	 * @param tochange
	 * @return
	 */
	private JComponent getPersonalComPanel(Commitment tochange){
		CommitmentCalViewPanel apane = new CommitmentCalViewPanel(tochange);
		apane.setBackground(new Color(255,255,255));
		

		LineBorder roundedLineBorder = new LineBorder(Color.black, 1, true);
		apane.setBorder(roundedLineBorder);
		
		apane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() > 1){
					GUIEventController.getInstance().editCommitment(((CommitmentCalViewPanel)e.getComponent()).getCommitment());
				}
			}		
		});
		
		
		return apane;
	}

	/** Get the 0-47 time position value
	 * @return the time position value
	 */
	public int getPos() {
		return pos;
	}
	
	
}