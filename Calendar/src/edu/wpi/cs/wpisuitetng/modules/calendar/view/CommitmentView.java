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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import edu.wpi.cs.wpisuitetng.modules.calendar.CalendarStandard;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.Status;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.month.CalendarObjectWrapperBorder;

/**
 * Commitment view contains and shows a list of commitments in a jpanel
 * @author CS Anonymous
 * @version $Revision: 1.0 $
 */
@SuppressWarnings("serial")
public class CommitmentView extends JPanel {

	JPanel commitPanel;
	JScrollPane scrollPane;
	private List<Commitment> commitmentList = new ArrayList<Commitment>();
	private final List<CommitmentViewPanel> panels = new ArrayList<CommitmentViewPanel>();
	int commitpanel_height = 0;
	/**
	 * Constructor for CommitmentView.
	 */
	public CommitmentView() {
		commitPanel = new JPanel();
		scrollPane = new JScrollPane(commitPanel, 
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, 
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		add(scrollPane, BorderLayout.CENTER );
		scrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		scrollPane.getVerticalScrollBar().setUnitIncrement(20);

		scrollPane.getViewport().addComponentListener(new ComponentAdapter(){
			@Override
			public void componentResized(ComponentEvent e) {
				resetSize();
			}
		});
		final SpringLayout layout = new SpringLayout();
		setLayout(layout);
		layout.putConstraint(SpringLayout.WEST, scrollPane, 0, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.EAST, scrollPane, 0, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.NORTH, scrollPane, 0, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.SOUTH, scrollPane, 0, SpringLayout.SOUTH, this);
	}

	/**
	 * Method updateCommData.
	 * @param commitmentList List<Commitment>
	 */
	public void updateCommData(List<Commitment> commitmentList) {
		this.commitmentList = commitmentList;
		update();
	}

	/**
	 * Method update.
	 */
	public void update(){
		 
		commitPanel.removeAll();
		panels.removeAll(panels);
		final SpringLayout commPanelLayout = new SpringLayout();
		commitPanel.setLayout(commPanelLayout);
		commitPanel.setBackground(Color.WHITE);
		final List<CommitmentViewPanel> commPanelList = new ArrayList<CommitmentViewPanel>();
		int n = 0;//adjusted index to take hidden commitments into account
		
		// print something when we do not  have any commitments
		if(commitmentList.size() == 0) {
			final JLabel message = new JLabel("<html><body style='width: 100%'><center>"
					+ "There are no commitments to display</center></html>", SwingConstants.CENTER);
			message.setBackground(Color.WHITE);
			message.setAlignmentX(CENTER_ALIGNMENT);
			message.setOpaque(true);
			message.setBorder(new EmptyBorder(0, 0, 15, 0));
			commitPanel.add(message, BorderLayout.CENTER);
			return;
		}
		
		for(int i = 0; i < commitmentList.size(); i++){
			if (commitmentList.get(i).getStatus().id != 2) {//Skips over completed commitments
				CommitmentViewPanel commitmentPanel = new CommitmentViewPanel(commitmentList.get(i));
				panels.add(commitmentPanel);
				commitmentPanel.setBackground(CalendarStandard.CalendarYellow);
				
				//sets the border to the needed border
				Color fore_color;
				try {
					if (commitmentList.get(i).getIsPersonal()) {
						fore_color = GUIEventController.getInstance().getCalendar()
								.getMyCalData().getCategories()
								.getCategory(commitmentList.get(i).getCategoryID()).getCategoryColor();
					} else {
						fore_color = GUIEventController.getInstance().getCalendar()
								.getTeamCalData().getCategories()
								.getCategory(commitmentList.get(i).getCategoryID()).getCategoryColor();
					}
				} catch (java.lang.NullPointerException excep) {
					fore_color = Color.WHITE;
				}
				commitmentPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory
						.createLoweredBevelBorder(), new CalendarObjectWrapperBorder(
						fore_color, CalendarStandard.CalendarYellow)));

				Image nameImg;
				Image scaleImg;
				JLabel tag = new JLabel();
				try {
						if (commitmentList.get(i).getIsPersonal())
						{
							nameImg = ImageIO.read(AbCalendar.class.getResource(
									"PersonalCommitment_Icon.png"));
							scaleImg = nameImg.getScaledInstance(25, 25, Image.SCALE_SMOOTH);
							tag.setText("[Personal]");
							tag.setIcon(new ImageIcon(scaleImg));
						}
						else
						{
							nameImg = ImageIO.read(AbCalendar.class.getResource(
									"TeamCommitment_Icon.png"));
							scaleImg = nameImg.getScaledInstance(25, 25, Image.SCALE_SMOOTH);
							tag.setText("[Team]");
							tag.setIcon(new ImageIcon(scaleImg));
						}
				} catch (IOException | IllegalArgumentException e) {
					if (commitmentList.get(i).getIsPersonal())
					{
						tag.setText("[Personal]");
					}
					else
					{
						tag.setText("[Team]");
					}

				}
				
				
				Commitment comm = commitmentList.get(i);
				
				JLabel name = new JLabel("Name: " + comm.getName());
				
				// Setting up date string
				SimpleDateFormat df = new SimpleDateFormat();
				df.applyPattern("EEEE, MMMM d, y");
				
				// Date
				JLabel date = new JLabel("Due Date: " + df.format(comm.getDueDate().getTime()));
				
				// Description and status 
				JLabel description = new JLabel("<HTML>Description: " 
				+ comm.getDescription() + "</HTML>");
				JLabel status = new JLabel("Status: " 
				+ Status.convertToString(comm.getStatus().id));
				
				
				description.addComponentListener(new ComponentAdapter(){
					@Override
					public void componentResized(ComponentEvent e) {
						resetSize();
					}
				});
				
				
				// Setting up time string
				SimpleDateFormat tm = new SimpleDateFormat();
				tm.applyPattern("hh:mm a");
				
				// Get time
				JLabel time = new JLabel("Due Time: " + tm.format(comm.getDueDate().getTime()));

				
				//code for setting the category
				String cat_str = "Category: ";
				try{
					if (commitmentList.get(i).getIsPersonal()){
						cat_str += GUIEventController.getInstance().getCalendar()
								.getMyCalData().getCategories()
								.getCategory(commitmentList.get(i).getCategoryID()).getName();	
					}
					else{
						cat_str += GUIEventController.getInstance().getCalendar()
								.getTeamCalData().getCategories()
								.getCategory(commitmentList.get(i).getCategoryID()).getName();	
					}
				}
				catch(java.lang.NullPointerException excep){
					//no associated category
					cat_str += "none";
				}
				
				final JLabel category = new JLabel(cat_str, JLabel.LEFT);
				
				commitmentPanel.setLayout(new GridBagLayout());
				GridBagConstraints c = new GridBagConstraints();
				c.anchor = GridBagConstraints.LINE_START;
				c.fill = GridBagConstraints.HORIZONTAL;
				c.weightx = 1;
				c.gridx = n;
				commitmentPanel.add(tag, c);
				commitmentPanel.add(name, c);
				commitmentPanel.add(date, c);
				commitmentPanel.add(time, c);
				commitmentPanel.add(description, c);
				commitmentPanel.add(status, c);
				commitmentPanel.add(category, c);
				commitmentPanel.setCursor(new Cursor(Cursor.HAND_CURSOR)); 
				commitmentPanel.setToolTipText("Click to Edit or Delete this Commitment");

				commitmentPanel.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						if (e.getClickCount() >= 1)
							{
							GUIEventController.getInstance().editCommitment(
									((CommitmentViewPanel)e.getComponent()).getCommitment());
							}
					}
				});

				commPanelList.add(n, commitmentPanel);
				if(n > 0)
					{
					commPanelLayout.putConstraint(SpringLayout.NORTH, commitmentPanel, 
							2, SpringLayout.SOUTH, commPanelList.get(n - 1));
					}
				else
					{
					commPanelLayout.putConstraint(SpringLayout.NORTH, commitmentPanel, 
							1, SpringLayout.NORTH, commitPanel);
					}

				commPanelLayout.putConstraint(SpringLayout.WEST, commitmentPanel, 
						0, SpringLayout.WEST, commitPanel);
				commPanelLayout.putConstraint(SpringLayout.EAST, commitmentPanel,
						0, SpringLayout.EAST, commitPanel);

				commitPanel.add(commitmentPanel);
				JSeparator separator = new JSeparator();
				separator.setOrientation(JSeparator.HORIZONTAL);
				commPanelLayout.putConstraint(SpringLayout.NORTH, separator, 
						1, SpringLayout.SOUTH, commitmentPanel);
				commPanelLayout.putConstraint(SpringLayout.WEST, separator,
						0, SpringLayout.WEST, commitPanel);
				commPanelLayout.putConstraint(SpringLayout.EAST, separator, 
						0, SpringLayout.EAST, commitPanel);

				commitPanel.add(separator);
				n++;
			}
		}
	
		commitPanel.revalidate();
		commitPanel.repaint();
		
		for(CommitmentViewPanel commitmentPanel : panels){
			commitmentPanel.setSize(commitmentPanel.getPreferredSize());
		}
		
		resetSize();
	}
	
	protected void resetSize(){
		int height = 8;
		int width = scrollPane.getViewport().getSize().width;
		for(CommitmentViewPanel commitmentPanel : panels){
			height += commitmentPanel.getSize().getHeight() + 2;
		}
		commitPanel.setPreferredSize(new Dimension(width, height));
		
		height = 8;
		width = scrollPane.getViewport().getSize().width;
		for(CommitmentViewPanel commitmentPanel : panels){
			height += commitmentPanel.getSize().getHeight() + 2;
		}
		commitPanel.setPreferredSize(new Dimension(width, height));
		
		commitPanel.revalidate();
		commitPanel.repaint();
	}

}
