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
import java.text.SimpleDateFormat;
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
import javax.swing.border.MatteBorder;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.calendar.CalendarStandard;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.CombinedCommitmentList;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.Status;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.CalendarData;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.CalendarDataModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.CalendarProps;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.CalendarPropsModel;

/*
 * This class is used for creating the commitment View 
 * tab that shows all commitments including those 
 * that have been completed.
 * 
 * */

 /* @author CS Anonymous
  * @version $Revision: 1.0 $
  */
@SuppressWarnings("serial")
public class CommitmentFullView extends JPanel{

	AbCalendar tcalendar;
	AbCalendar pcalendar;
	JPanel commitPanel;
	JScrollPane scrollPane;
	JPanel header;
	private CalendarProps calProps;
	private boolean initialized;

	List<Commitment> commitmentList = new ArrayList<Commitment>();
	private int namesort = 0;
	private int datesort = 0;
	private int dessort = 0;
	private int statussort = 0;
	JButton jName;
	JButton jDueDate;
	JButton jDescription;
	JButton jStatus;

	JRadioButton bothRadioButton;
	JRadioButton personalRadioButton;
	JRadioButton teamRadioButton;
	ButtonGroup viewSwitchGroup;

	/**
	 */
	public enum ViewingMode {
		TEAM, PERSONAL, BOTH;		
	};
	ViewingMode mode;

	/*Constructor creates main scrolling Panel and 
	 * sets tcalendar which will grab teams commitments*/
	/**
	 * Constructor for CommitmentFullView.
	 * @param teamCalendar AbCalendar
	 * @param personalCalendar AbCalendar
	 */
	public CommitmentFullView(AbCalendar teamCalendar, AbCalendar personalCalendar) {
		initialized = false;
		tcalendar = teamCalendar;
		pcalendar = personalCalendar;

		mode = ViewingMode.TEAM;

		commitPanel = new JPanel();

		// Header panel
		header = new JPanel();
		header.setBackground(Color.WHITE);
		header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
		header.setBorder(new EmptyBorder(5, 5, 5, 5));
		header.setBorder(new MatteBorder(0, 0, 2, 0, Color.BLACK));

		scrollPane = new JScrollPane(commitPanel, 
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, 
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		add(scrollPane, BorderLayout.CENTER );
		scrollPane.getVerticalScrollBar().setUnitIncrement(20);
		scrollPane.getHorizontalScrollBar().setUnitIncrement(20);

		// Sets the UPPER RIGHT corner box
		final JPanel cornerBoxUR = new JPanel();
		cornerBoxUR.setBackground(Color.WHITE);
		scrollPane.setCorner(ScrollPaneConstants.UPPER_RIGHT_CORNER,
				cornerBoxUR);
		add(scrollPane);

		/*spring layout to allow adjustments to size of screen without messing up panels*/
		final SpringLayout layout = new SpringLayout();
		setLayout(layout);
		layout.putConstraint(SpringLayout.WEST, scrollPane, 0, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.EAST, scrollPane, 0, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.NORTH, scrollPane, 0, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.SOUTH, scrollPane, 0, SpringLayout.SOUTH, this);
		scrollPane.setViewportView(commitPanel);

		setCommitlist();
		setupPanels();
		initialized = true;
		applyCalProps();
	}
	/*Sets the calendars commitments to the commitmentList array to populate panel*/
	private void setCommitlist() {

		if (mode == ViewingMode.TEAM){
			if(tcalendar.getCalData() != null){
				commitmentList = tcalendar.getCalData().getCommitments().getCommitments();
			}
		} else if (mode == ViewingMode.PERSONAL){
			if(pcalendar.getCalData() != null){
			commitmentList = pcalendar.getCalData().getCommitments().getCommitments();
			}
		} else if(tcalendar.getCalData() != null && pcalendar.getCalData() != null) { 
			// here mode == ViewingMode.BOTH
			final CombinedCommitmentList combinedList = new CombinedCommitmentList(
					new ArrayList<Commitment>(
							pcalendar.getCalData().getCommitments().getCommitments()));
			final CalendarData teamData = CalendarDataModel.getInstance()
					.getCalendarData(ConfigManager.getConfig().getProjectName());

			/*if we are supposed to show team data, 
			 * we need to put the team commitments into the list in the right order*/
			for (int i = 0; i < teamData.getCommitments()
					.getCommitments().size(); i++) {
				combinedList.add(teamData.getCommitments()
						.getCommitments().get(i));
			}
			commitmentList = combinedList.getCommitments();
		}
	}

	/*commit panel is populated with all events 
	 * which are in separate panels that can be scrolled and clicked*/
	private void setupPanels() {
		commitPanel.setLayout(new BoxLayout(commitPanel, BoxLayout.Y_AXIS));
		commitPanel.setBorder(new EmptyBorder(5, 5, 10, 5));
		commitPanel.setBackground(Color.WHITE);

		header.removeAll();


		final JPanel viewSwitcher = new JPanel();

		final SpringLayout switcherLayout = new SpringLayout();

		viewSwitcher.setLayout(switcherLayout);
		viewSwitcher.setBackground(Color.WHITE);

		teamRadioButton = new JRadioButton("Team");
		teamRadioButton.setBackground(Color.WHITE);
		teamRadioButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); 
		// To change cursor as it moves over this radio button
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


		personalRadioButton = new JRadioButton("Personal");
		personalRadioButton.setBackground(Color.WHITE);
		personalRadioButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); 
		// To change cursor as it moves over this radio button
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


		switcherLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, personalRadioButton,
				0, SpringLayout.HORIZONTAL_CENTER, viewSwitcher);
		switcherLayout.putConstraint(SpringLayout.VERTICAL_CENTER, personalRadioButton, 
				0, SpringLayout.VERTICAL_CENTER, viewSwitcher);

		bothRadioButton = new JRadioButton("Both");
		bothRadioButton.setBackground(Color.WHITE);
		bothRadioButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); 
		// To change cursor as it moves over this radio button
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
		bothRadioButton.setMinimumSize(new Dimension(100, 50));
		bothRadioButton.setMaximumSize(new Dimension(100, 50));
		bothRadioButton.setAlignmentX(CENTER_ALIGNMENT);

		viewSwitchGroup = new ButtonGroup();
		viewSwitchGroup.add(teamRadioButton);
		viewSwitchGroup.add(personalRadioButton);
		viewSwitchGroup.add(bothRadioButton);

		switcherLayout.putConstraint(SpringLayout.EAST, teamRadioButton, 
				0, SpringLayout.WEST, personalRadioButton);
		switcherLayout.putConstraint(SpringLayout.VERTICAL_CENTER, teamRadioButton, 
				0, SpringLayout.VERTICAL_CENTER, viewSwitcher);
		switcherLayout.putConstraint(SpringLayout.WEST, bothRadioButton,
				0, SpringLayout.EAST, personalRadioButton);
		switcherLayout.putConstraint(SpringLayout.VERTICAL_CENTER, bothRadioButton,
				0, SpringLayout.VERTICAL_CENTER, viewSwitcher);



		viewSwitcher.setPreferredSize(new Dimension(300, 50));
		viewSwitcher.setMaximumSize(new Dimension(20000, 50));

		header.add(viewSwitcher);

		final JPanel topButtons = new JPanel();

		final GridLayout experimentLayout = new GridLayout(0, 4);
		topButtons.setLayout(experimentLayout);
		//topButtons.setLayout(new BoxLayout(topButtons, BoxLayout.X_AXIS));
		jName = new JButton("<html><font color='white'><b>"
				+ "Name" + "</b></font></html>");
		if(namesort == 1){
			try {
				final Image img = ImageIO.read(getClass().getResource("UpArrow_Icon.png"));
				jName.setIcon(new ImageIcon(img));
				jName.setText("<html><font color='white'><b>"
						+ "Name" + "</b></font></html>");
			} catch (IOException ex) {}
			catch(IllegalArgumentException ex){
				jName.setText("<html><font color='white'><b>"
						+ "Name ^" + "</b></font></html>");
			}
		}
		else if(namesort == 2){
			try {
				final Image img = ImageIO.read(getClass().getResource("DownArrow_Icon.png"));
				jName.setIcon(new ImageIcon(img));
				jName.setText("<html><font color='white'><b>"
						+ "Name" + "</b></font></html>");
			} catch (IOException ex) {}
			catch(IllegalArgumentException ex){
				jName.setText("<html><font color='white'><b>"
						+ "Name v" + "</b></font></html>");
			}
		}


		//		jName.setContentAreaFilled(false);
		jName.setBackground(CalendarStandard.CalendarRed);
		jName.setCursor(new Cursor(Cursor.HAND_CURSOR)); 
		// To change cursor as it moves over this button
		//sort by name
		jName.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				datesort = 0;
				dessort = 0;
				statussort = 0;
				Collections.sort(commitmentList);
				if(namesort == 1){
					namesort = 2;
					Collections.reverse(commitmentList);
				}
				else if(namesort == 2 || namesort == 0){
					namesort = 1;
				}
				updateView();
			}

		});



		jDueDate = new JButton("<html><font color='white'><b>"
				+ "Due Date" + "</b></font></html>");
		//		jDueDate.setContentAreaFilled(false);
		jDueDate.setBackground(CalendarStandard.CalendarRed);

		if(datesort == 1){
			try {
				final Image img = ImageIO.read(getClass().getResource("UpArrow_Icon.png"));
				jDueDate.setIcon(new ImageIcon(img));
				jDueDate.setText("<html><font color='white'><b>"
						+ "Due Date" + "</b></font></html>");
			} catch (IOException ex) {}
			catch(IllegalArgumentException ex){
				jDueDate.setText("<html><font color='white'><b>"
						+ "Due Date ^" + "</b></font></html>");
			}
		}
		else if(datesort == 2){
			try {
				final Image img = ImageIO.read(getClass().getResource("DownArrow_Icon.png"));
				jDueDate.setIcon(new ImageIcon(img));
				jDueDate.setText("<html><font color='white'><b>"
						+ "Due Date" + "</b></font></html>");
			} catch (IOException ex) {}
			catch(IllegalArgumentException ex){
				jDueDate.setText("<html><font color='white'><b>"
						+ "Due Date v" + "</b></font></html>");
			}
		}
		//jDueDate.setContentAreaFilled(false);

		jDueDate.setCursor(new Cursor(Cursor.HAND_CURSOR)); 
		// To change cursor as it moves over this button

		// sort by date 
		jDueDate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				namesort = 0;
				dessort = 0;
				statussort = 0;
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
				if(datesort == 1){
					datesort = 2;
					Collections.reverse(commitmentList);
				}
				else if(datesort == 2 || datesort == 0){
					datesort = 1;
				}
				updateView();
			}			
		});


		jDescription = new JButton("<html><font color='white'><b>"
				+ "Description" + "</b></font></html>");
		//		jDescription.setContentAreaFilled(false);
		jDescription.setBackground(CalendarStandard.CalendarRed);
		if(dessort == 1){
			try {
				final Image img = ImageIO.read(getClass().getResource("UpArrow_Icon.png"));
				jDescription.setIcon(new ImageIcon(img));
				jDescription.setText("<html><font color='white'><b>"
						+ "Description" + "</b></font></html>");
			} catch (IOException ex) {}
			catch(IllegalArgumentException ex){
				jDescription.setText("<html><font color='white'><b>"
						+ "Description ^" + "</b></font></html>");
			}
		}
		else if(dessort == 2){
			try {
				final Image img = ImageIO.read(getClass().getResource("DownArrow_Icon.png"));
				jDescription.setIcon(new ImageIcon(img));
				jDescription.setText("<html><font color='white'><b>"
						+ "Description" + "</b></font></html>");
			} catch (IOException ex) {}
			catch(IllegalArgumentException ex){
				jDescription.setText("<html><font color='white'><b>"
						+ "Description v" + "</b></font></html>");
			}
		}
		//jDescription.setContentAreaFilled(false);

		jDescription.setCursor(new Cursor(Cursor.HAND_CURSOR));
		// To change cursor as it moves over this button
		jDescription.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				namesort = 0;
				datesort = 0;
				statussort = 0;
				Collections.sort(commitmentList, new Comparator<Commitment>() {

					@Override 
					public int compare(Commitment c1, Commitment c2) {
						return c1.getDescription().compareTo(c2.getDescription());
					}		
				});
				if(dessort == 1){
					dessort = 2;
					Collections.reverse(commitmentList);
				}
				else if(dessort == 2 || dessort == 0){
					dessort = 1;
				}
				updateView();
			}			
		});

		jStatus = new JButton("<html><font color='white'><b>"
				+ "Status" + "</b></font></html>");
		if(statussort == 1){
			try {
				final Image img = ImageIO.read(getClass().getResource("UpArrow_Icon.png"));
				jStatus.setIcon(new ImageIcon(img));
				jStatus.setText("<html><font color='white'><b>"
						+ "Status" + "</b></font></html>");
			} catch (IOException ex) {}
			catch(IllegalArgumentException ex){
				jStatus.setText("<html><font color='white'><b>"
						+ "Status ^" + "</b></font></html>");
			}
		}
		else if(statussort == 2){
			try {
				final Image img = ImageIO.read(getClass().getResource("DownArrow_Icon.png"));
				jStatus.setIcon(new ImageIcon(img));
				jStatus.setText("<html><font color='white'><b>"
						+ "Status" + "</b></font></html>");
			} catch (IOException ex) {}
			catch(IllegalArgumentException ex){
				jStatus.setText("<html><font color='white'><b>"
						+ "Status v" + "</b></font></html>");
			}
		}
		//		jStatus.setContentAreaFilled(false);
		jStatus.setBackground(CalendarStandard.CalendarRed);
		jStatus.setCursor(new Cursor(Cursor.HAND_CURSOR)); 
		// To change cursor as it moves over this button

		jStatus.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				namesort = 0;
				datesort = 0;
				dessort = 0;
				Collections.sort(commitmentList, new Comparator<Commitment>() {

					@Override 
					public int compare(Commitment c1, Commitment c2) {
						return c1.getStatus().convertToString(c1.getStatus().getId()).compareTo(
								c2.getStatus().convertToString(c2.getStatus().getId()));

					}		
				});
				if(statussort == 1){
					statussort = 2;
					Collections.reverse(commitmentList);
				}
				else if(statussort == 2 || statussort == 0){
					statussort = 1;
				}
				updateView();
			}			
		});

		final GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.LINE_START;
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		topButtons.add(jName, c);
		topButtons.add(jDueDate, c);
		topButtons.add(jDescription, c);
		topButtons.add(jStatus, c);
		topButtons.setPreferredSize(new Dimension(300, 50));
		topButtons.setMaximumSize(new Dimension(20000, 50));
		final Border loweredbevel1 = BorderFactory.createLoweredBevelBorder();
		topButtons.setBorder(loweredbevel1);
		topButtons.setBorder(new MatteBorder(5, 5, 5, 5, Color.WHITE));

		header.add(topButtons);

		scrollPane.setColumnHeaderView(header);
		//JSeparator sep = new JSeparator();
		//commitPanel.add(sep);
		for(int i = 0; i < commitmentList.size(); i++){
			CommitmentViewPanel commitmentPanel = new CommitmentViewPanel(commitmentList.get(i));
			Image nameImg;
			Image scaleImg;
			JLabel name = new JLabel(commitmentList.get(i).getName(), JLabel.LEFT);
			name.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
			try {
				if (commitmentList.get(i).getIsPersonal())
				{	
					nameImg = ImageIO.read(getClass().getResource("PersonalCommitment_Icon.png"));
					scaleImg = nameImg.getScaledInstance(25, 25, Image.SCALE_SMOOTH);
					name.setIcon(new ImageIcon(scaleImg));
				}
				else
				{
					nameImg = ImageIO.read(getClass().getResource("TeamCommitment_Icon.png"));
					scaleImg = nameImg.getScaledInstance(25, 25, Image.SCALE_SMOOTH);
					name.setIcon(new ImageIcon(scaleImg));
				}
			} catch (IOException | IllegalArgumentException e) {

			}

			SimpleDateFormat df = new SimpleDateFormat();
			df.applyPattern("EEEE, MMMM d, y - hh:mm a");
			
			JLabel date = new JLabel("" + 
			df.format(commitmentList.get(i).getDueDate().getTime()), JLabel.LEFT);
			date.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
			JLabel description = new JLabel("<HTML>" + 
			commitmentList.get(i).getDescription() + "</HTML>", JLabel.LEFT);
			description.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
			JLabel status = new JLabel(Status.convertToString(
					commitmentList.get(i).getStatus().id), JLabel.LEFT);
			status.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

			commitmentPanel.setLayout(experimentLayout);
			//GridBagConstraints c = new GridBagConstraints();
			c.anchor = GridBagConstraints.BASELINE_LEADING;
			c.fill = GridBagConstraints.BASELINE_LEADING;
			c.weightx = 1;
			commitmentPanel.add(name, c);
			commitmentPanel.add(date, c);
			commitmentPanel.add(description, c);
			commitmentPanel.add(status, c);
			commitmentPanel.setBackground(CalendarStandard.CalendarYellow);
			//			commitmentPanel.setBackground(new Color(222,184,135));
			commitmentPanel.setPreferredSize(new Dimension(300, 75));
			commitmentPanel.setMaximumSize(new Dimension(20000, 75));
			Border loweredbevel = BorderFactory.createLoweredBevelBorder();
			commitmentPanel.setBorder(loweredbevel);
			commitmentPanel.setCursor(new Cursor(Cursor.HAND_CURSOR)); 
			// To change cursor as it moves over this commitment pannel
			commitmentPanel.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (e.getClickCount() >= 1)
						GUIEventController.getInstance().editCommitment(
								((CommitmentViewPanel)e.getComponent()).getCommitment());
				}		
			});

			commitPanel.add(commitmentPanel);
		}
	}

	/**
	 * Method updateList.
	 */
	public void updateList(){
		commitPanel.removeAll();
		setCommitlist();
		setupPanels();
	}

	/**
	 * Method updateView.
	 */
	public void updateView(){
		commitPanel.removeAll();
		setupPanels();
	}

	private void switchView(ViewingMode newMode){
		mode = newMode;
		calProps.setCommViewMode(mode.ordinal());
		this.updateList();
	}

	/**
	 * Used after cal props has been fetched from the server.
	 */
	protected void applyCalProps(){	

		calProps = CalendarPropsModel.getInstance().getCalendarProps(
				ConfigManager.getConfig().getProjectName() + "-"
						+ ConfigManager.getConfig().getUserName() + "-PROPS");
		if(initialized && calProps != null){
			mode =  ViewingMode.values()[calProps.getCommViewMode()];
			

			switch (calProps.getCommViewMode()){
			case 0: viewSwitchGroup.setSelected(teamRadioButton.getModel(), true);
			break;
			case 1: viewSwitchGroup.setSelected(personalRadioButton.getModel(), true);
			break;
			case 2: viewSwitchGroup.setSelected(bothRadioButton.getModel(), true);
			break;
			}

			updateList();

		}
	}	

}
