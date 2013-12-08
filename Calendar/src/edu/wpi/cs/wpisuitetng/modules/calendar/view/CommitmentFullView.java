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
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpringLayout;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.calendar.CalendarStandard;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.CombinedCommitmentList;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.Status;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.CalendarData;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.CalendarDataModel;

/*
 * This class is used for creating the commitment View 
 * tab that shows all commitments including those 
 * that have been completed.
 * 
 * */

@SuppressWarnings("serial")
public class CommitmentFullView extends JPanel{

	AbCalendar tcalendar;
	AbCalendar pcalendar;
	JPanel commitPanel;
	List<Commitment> commitmentList = new ArrayList<Commitment>();
	public enum ViewingMode {
		TEAM, PERSONAL, BOTH;		
	};
	ViewingMode mode;
	
	/*Constructor creates main scrolling Panel and sets tcalendar which will grab teams commitments*/
	public CommitmentFullView(AbCalendar teamCalendar, AbCalendar personalCalendar) {
		this.tcalendar = teamCalendar;
		this.pcalendar = personalCalendar;
		
		this.mode = ViewingMode.TEAM;
		
		commitPanel = new JPanel();
		JScrollPane scrollPane = new JScrollPane(commitPanel, 
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, 
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		add(scrollPane, BorderLayout.CENTER );
		scrollPane.getVerticalScrollBar().setUnitIncrement(20);
		scrollPane.getHorizontalScrollBar().setUnitIncrement(20);
		/*spring layout to allow adjustments to size of screen without messing up panels*/
		SpringLayout layout = new SpringLayout();
		setLayout(layout);
		layout.putConstraint(SpringLayout.WEST, scrollPane, 0, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.EAST, scrollPane, 0, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.NORTH, scrollPane, 0, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.SOUTH, scrollPane, 0, SpringLayout.SOUTH, this);
		scrollPane.setViewportView(commitPanel);
		
		setCommitlist();
		setupPanels();
	}
	/*Sets the calendars commitments to the commitmentList array to populate panel*/
	private void setCommitlist() {

		if (mode == ViewingMode.TEAM){
			if(tcalendar.getCalData() != null){
				commitmentList = tcalendar.getCalData().getCommitments().getCommitments();
			}
		} else if (mode == ViewingMode.PERSONAL){
			commitmentList = pcalendar.getCalData().getCommitments().getCommitments();
		} else { // here mode == ViewingMode.BOTH
			CombinedCommitmentList combinedList = new CombinedCommitmentList(
					new ArrayList<Commitment>(pcalendar.getCalData().getCommitments().getCommitments()));
			CalendarData teamData = CalendarDataModel.getInstance()
					.getCalendarData(ConfigManager.getConfig().getProjectName());

			//if we are supposed to show team data, we need to put the team commitments into the list in the right order
			for (int i = 0; i < teamData.getCommitments()
					.getCommitments().size(); i++) {
				combinedList.add(teamData.getCommitments()
						.getCommitments().get(i));
			}
			commitmentList = combinedList.getCommitments();
		}
	}
	
	/*commit panel is populated with all events which are in separate panels that can be scrolled and clicked*/
	private void setupPanels() {
		commitPanel.setLayout(new BoxLayout(commitPanel, BoxLayout.Y_AXIS));
		commitPanel.setBorder(new EmptyBorder(10, 5, 10 , 20));
		commitPanel.setBackground(Color.WHITE);
		
		
		JPanel viewSwitcher = new JPanel();

		SpringLayout switcherLayout = new SpringLayout();

		viewSwitcher.setLayout(switcherLayout);
		viewSwitcher.setBackground(Color.WHITE);
		
		final JRadioButton teamRadioButton = new JRadioButton("Team");
		teamRadioButton.setBackground(Color.WHITE);
		teamRadioButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // To change cursor as it moves over this radio button
		teamRadioButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				switchView(ViewingMode.TEAM);
			}
			
		});
		viewSwitcher.add(teamRadioButton, SpringLayout.WEST);
		if (mode == ViewingMode.TEAM){
			teamRadioButton.setSelected(true);
		}
		
		
		final JRadioButton personalRadioButton = new JRadioButton("Personal");
		personalRadioButton.setBackground(Color.WHITE);
		personalRadioButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // To change cursor as it moves over this radio button
		personalRadioButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				switchView(ViewingMode.PERSONAL);
			}
			
		});
		viewSwitcher.add(personalRadioButton, SpringLayout.HORIZONTAL_CENTER);
		if (mode == ViewingMode.PERSONAL){
			personalRadioButton.setSelected(true);
		}

		
		switcherLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, personalRadioButton, 0, SpringLayout.HORIZONTAL_CENTER, viewSwitcher);
		switcherLayout.putConstraint(SpringLayout.VERTICAL_CENTER, personalRadioButton, 0, SpringLayout.VERTICAL_CENTER, viewSwitcher);
		
		final JRadioButton bothRadioButton = new JRadioButton("Both");
		bothRadioButton.setBackground(Color.WHITE);
		bothRadioButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // To change cursor as it moves over this radio button
		bothRadioButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				switchView(ViewingMode.BOTH);
			}
			
		});
		viewSwitcher.add(bothRadioButton, SpringLayout.EAST);
		if (mode == ViewingMode.BOTH){
			bothRadioButton.setSelected(true);
		}
		bothRadioButton.setMinimumSize(new Dimension(100,50));
		bothRadioButton.setMaximumSize(new Dimension(100,50));
		bothRadioButton.setAlignmentX(CENTER_ALIGNMENT);
		
		ButtonGroup viewSwitchGroup = new ButtonGroup();
		viewSwitchGroup.add(teamRadioButton);
		viewSwitchGroup.add(personalRadioButton);
		viewSwitchGroup.add(bothRadioButton);
		
		
		switcherLayout.putConstraint(SpringLayout.EAST, teamRadioButton, 0, SpringLayout.WEST, personalRadioButton);
		switcherLayout.putConstraint(SpringLayout.VERTICAL_CENTER, teamRadioButton, 0, SpringLayout.VERTICAL_CENTER, viewSwitcher);
		switcherLayout.putConstraint(SpringLayout.WEST, bothRadioButton, 0, SpringLayout.EAST, personalRadioButton);
		switcherLayout.putConstraint(SpringLayout.VERTICAL_CENTER, bothRadioButton, 0, SpringLayout.VERTICAL_CENTER, viewSwitcher);

		
		
		viewSwitcher.setPreferredSize(new Dimension(300,50));
		viewSwitcher.setMaximumSize(new Dimension(20000, 50));
		
		commitPanel.add(viewSwitcher);
		
		JPanel topButtons = new JPanel();
		
		GridLayout experimentLayout = new GridLayout(0,4);
		topButtons.setLayout(experimentLayout);
		//topButtons.setLayout(new BoxLayout(topButtons, BoxLayout.X_AXIS));
		JButton jName = new JButton("Name");
		jName.setContentAreaFilled(false);
		jName.setCursor(new Cursor(Cursor.HAND_CURSOR)); // To change cursor as it moves over this button
		//sort by name
		jName.addMouseListener(new MouseAdapter() {
			@Override
		public void mouseClicked(MouseEvent e) {
				Collections.sort(commitmentList);
				update2();
			}
			
		});
		
		
		JButton jDueDate = new JButton("Due Date");
		jDueDate.setContentAreaFilled(false);
		jDueDate.setCursor(new Cursor(Cursor.HAND_CURSOR)); // To change cursor as it moves over this button
		
		// sort by date 
		jDueDate.addMouseListener(new MouseAdapter() {
			@Override
		public void mouseClicked(MouseEvent e) {
				Collections.sort(commitmentList, new Comparator<Commitment>() {
				
				@Override 
				public int compare(Commitment c1, Commitment c2) {
					if(c1.getDueDate().before(c2.getDueDate()))
						return -1;
					else if(c1.getDueDate().after(c2.getDueDate())) 
						return 1;
					else
						return 0;
				}				
				});
				update2();
			}			
		});

		JButton jDescription = new JButton("Description");
		jDescription.setContentAreaFilled(false);
		jDescription.setCursor(new Cursor(Cursor.HAND_CURSOR)); // To change cursor as it moves over this button
		
		JButton jStatus = new JButton("Status");
		jStatus.setContentAreaFilled(false);
		jStatus.setCursor(new Cursor(Cursor.HAND_CURSOR)); // To change cursor as it moves over this button
		
		// sort by status TODO
		jStatus.addMouseListener(new MouseAdapter() {
			@Override
		public void mouseClicked(MouseEvent e) {
				Collections.sort(commitmentList, new Comparator<Commitment>() {
					
				@Override 
				public int compare(Commitment c1, Commitment c2) {
					return c1.getStatus().convertToString(c1.getStatus().getId()).compareTo(c2.getStatus().convertToString(c2.getStatus().getId()));

				}		
				});
				update2();
			}			
		});

		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.LINE_START;
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		topButtons.add(jName,c);
		topButtons.add(jDueDate,c);
		topButtons.add(jDescription,c);
		topButtons.add(jStatus,c);
		topButtons.setPreferredSize(new Dimension(300,75));
		topButtons.setMaximumSize(new Dimension(20000, 75));
		Border loweredbevel1 = BorderFactory.createLoweredBevelBorder();
		topButtons.setBorder(loweredbevel1);
		commitPanel.add(topButtons);
		//JSeparator sep = new JSeparator();
		//commitPanel.add(sep);
		for(int i = 0; i < commitmentList.size(); i++){
			CommitmentViewPanel commitmentPanel = new CommitmentViewPanel(commitmentList.get(i));
			Image nameImg;
			Image scaleImg;
			JLabel name = new JLabel(commitmentList.get(i).getName(),JLabel.CENTER);
			try {
				if (commitmentList.get(i).getIsPersonal())
				{	
					nameImg = ImageIO.read(getClass().getResource("PersonalCommitment_Icon.png"));
					scaleImg = nameImg.getScaledInstance(25,25, Image.SCALE_SMOOTH);
					name.setIcon(new ImageIcon(scaleImg));
				}
				else
				{
					nameImg = ImageIO.read(getClass().getResource("TeamCommitment_Icon.png"));
					scaleImg = nameImg.getScaledInstance(25,25, Image.SCALE_SMOOTH);
					name.setIcon(new ImageIcon(scaleImg));
				}
			} catch (IOException | IllegalArgumentException e) {

			}
			JLabel date = new JLabel(""+commitmentList.get(i).getDueDate().getTime(),JLabel.CENTER);
			JLabel description = new JLabel("<HTML>"+ commitmentList.get(i).getDescription()+"</HTML>",JLabel.CENTER);
			JLabel status = new JLabel(Status.convertToString(commitmentList.get(i).getStatus().id),JLabel.CENTER);
			commitmentPanel.setLayout(experimentLayout);
			//GridBagConstraints c = new GridBagConstraints();
			c.anchor = GridBagConstraints.CENTER;
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 1;
			commitmentPanel.add(name,c);
			commitmentPanel.add(date,c);
			commitmentPanel.add(description,c);
			commitmentPanel.add(status,c);
			commitmentPanel.setBackground(CalendarStandard.CalendarYellow);
//			commitmentPanel.setBackground(new Color(222,184,135));
			commitmentPanel.setPreferredSize(new Dimension(300,75));
			commitmentPanel.setMaximumSize(new Dimension(20000,75));
			Border loweredbevel = BorderFactory.createLoweredBevelBorder();
			commitmentPanel.setBorder(loweredbevel);
			commitmentPanel.setCursor(new Cursor(Cursor.HAND_CURSOR)); // To change cursor as it moves over this commitment pannel
			commitmentPanel.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (e.getClickCount() >= 1)
						GUIEventController.getInstance().editCommitment(((CommitmentViewPanel)e.getComponent()).getCommitment());
				}		
			});
			
			commitPanel.add(commitmentPanel);
		}
	}
	
	public void update(){
		commitPanel.removeAll();
		setCommitlist();
		setupPanels();
	}
	
	public void update2(){
		commitPanel.removeAll();
		setupPanels();
	}
	
	private void switchView(ViewingMode newMode){
		this.mode = newMode;
		this.update();
	}
	
	
	
}
