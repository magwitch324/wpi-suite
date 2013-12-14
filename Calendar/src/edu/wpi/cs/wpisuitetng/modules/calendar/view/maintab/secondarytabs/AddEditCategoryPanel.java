/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: CS Anonymous
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.calendar.view.maintab.secondarytabs;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax/**
 * @author sfp
 *
 */
/**
 * @author sfp
 *
 */
/**
 * @author sfp
 *
 */
/**
 * @author sfp
 *
 */
/**
 * @author sfp
 *
 */
/**
 * @author sfp
 *
 */
/**
 * @author sfp
 *
 */
/**
 * @author sfp
 *
 */
/**
 * @author sfp
 *
 */
.swing.JTextField;
import javax.swing.SwingConstants;

import java.awt.GridLayout;

import javax.swing.JButton;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.border.LineBorder;

import edu.wpi.cs.wpisuitetng.modules.calendar.CalendarStandard;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.GUIEventController;

/**
 * @author CS Anonymous
 * @version $Revision: 1.0 $
 */
public class AddEditCategoryPanel extends JPanel {

	private final JTextField textFieldName;
	private final JRadioButton rdbtnTeam_1;
	

	/**
	 * Constructor for AddEditCategoryPanel.
	 * @param mode CategoryTab.CategoryMode
	 */
	public AddEditCategoryPanel(CategoryTab.CategoryMode mode) {
		setBackground(Color.WHITE);
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		final Component horizontalGlue = Box.createHorizontalGlue();
		add(horizontalGlue);
		
		final JPanel addEditFormPanel = new JPanel();
		addEditFormPanel.setBackground(Color.WHITE);
		addEditFormPanel.setPreferredSize(new Dimension(400, 10));
		addEditFormPanel.setMaximumSize(new Dimension(400, 4000));
		add(addEditFormPanel);
		final GridBagLayout gbl_addEditFormPanel = new GridBagLayout();
		gbl_addEditFormPanel.columnWidths = new int[]{0, 0, 0};
		gbl_addEditFormPanel.rowHeights = new int[] {0, 0, 0, 0};
		gbl_addEditFormPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_addEditFormPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0};
		addEditFormPanel.setLayout(gbl_addEditFormPanel);
		

		final JLabel lblName = new JLabel("<html><font>" + "Name" + "</font>" 
											+ "<font color=red>" + "*" + "</font>" 
											+ "<font>" + ":" + "</font></html>");
		final GridBagConstraints gbc_lblName = new GridBagConstraints();
		gbc_lblName.anchor = GridBagConstraints.EAST;
		gbc_lblName.insets = new Insets(0, 0, 5, 5);
		gbc_lblName.gridx = 0;
		gbc_lblName.gridy = 0;
		addEditFormPanel.add(lblName, gbc_lblName);
		
		textFieldName = new JTextField();
		textFieldName.setBackground(CalendarStandard.CalendarYellow);
		final GridBagConstraints gbc_textFieldName = new GridBagConstraints();
		gbc_textFieldName.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldName.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldName.gridx = 1;
		gbc_textFieldName.gridy = 0;
		addEditFormPanel.add(textFieldName, gbc_textFieldName);
		textFieldName.setColumns(10);
		
		final JLabel lblType = new JLabel("<html><font>" + "Type" + "</font>" 
											+ "<font color=red>" + "*" + "</font>" 
											+ "<font>" + ":" + "</font></html>");
		final GridBagConstraints gbc_lblType = new GridBagConstraints();
		gbc_lblType.anchor = GridBagConstraints.EAST;
		gbc_lblType.insets = new Insets(0, 0, 5, 5);
		gbc_lblType.gridx = 0;
		gbc_lblType.gridy = 1;
		addEditFormPanel.add(lblType, gbc_lblType);
		
		final Box horizontalBox = Box.createHorizontalBox();
		final GridBagConstraints gbc_horizontalBox = new GridBagConstraints();
		gbc_horizontalBox.insets = new Insets(0, 0, 5, 0);
		gbc_horizontalBox.gridx = 1;
		gbc_horizontalBox.gridy = 1;
		addEditFormPanel.add(horizontalBox, gbc_horizontalBox);
		
		final ButtonGroup teamPersonalRadioButtons = new ButtonGroup();
		
		rdbtnTeam_1 = new JRadioButton("Team");
		rdbtnTeam_1.setBackground(Color.WHITE);
		horizontalBox.add(rdbtnTeam_1);
		teamPersonalRadioButtons.add(rdbtnTeam_1);
		
		final JRadioButton rdbtnPersonal = new JRadioButton("Personal");
		rdbtnPersonal.setBackground(Color.WHITE);
		horizontalBox.add(rdbtnPersonal);
		teamPersonalRadioButtons.add(rdbtnPersonal);
		
		final JLabel lblColor = new JLabel("<html><font>" + "Color" + "</font>" 
											+ "<font color=red>" + "*" + "</font>" 
											+ "<font>" + ":" + "</font></html>");
		final GridBagConstraints gbc_lblColor = new GridBagConstraints();
		gbc_lblColor.anchor = GridBagConstraints.EAST;
		gbc_lblColor.insets = new Insets(0, 0, 5, 5);
		gbc_lblColor.gridx = 0;
		gbc_lblColor.gridy = 2;
		addEditFormPanel.add(lblColor, gbc_lblColor);
		
		final ColorPickerPanel colorPickerPanel = new ColorPickerPanel();
		final GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 2;
		addEditFormPanel.add(colorPickerPanel, gbc_panel);
		
		
		final Box horizontalBox_1 = Box.createHorizontalBox();
		final GridBagConstraints gbc_horizontalBox_1 = new GridBagConstraints();
		gbc_horizontalBox_1.gridx = 1;
		gbc_horizontalBox_1.gridy = 3;
		addEditFormPanel.add(horizontalBox_1, gbc_horizontalBox_1);
		
		final JButton btnCancel = new JButton();
		try {
			final Image img = ImageIO.read(getClass().getResource("Cancel_Icon.png"));
			btnCancel.setIcon(new ImageIcon(img));
		} catch (IOException ex) {}
		catch(IllegalArgumentException ex){
			btnCancel.setIcon(new ImageIcon());
		}
		btnCancel.setText("Cancel");
		btnCancel.setCursor(new Cursor(Cursor.HAND_CURSOR)); 
		// To change cursor as it moves over this button
		
		
		// Action listener for cancel button 
		btnCancel.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				final int tabIndex = 
						GUIEventController.getInstance().getMainView().getSelectedIndex();
				GUIEventController.getInstance().getMainView().setComponentAt(
						tabIndex, new CategoryTab());
			}
			
			
		});
		horizontalBox_1.add(btnCancel);
		
		
		final Component horizontalStrut = Box.createHorizontalStrut(20);
		horizontalBox_1.add(horizontalStrut);
		
		final JButton btnSave = new JButton();
		try {
			final Image img = ImageIO.read(getClass().getResource("Save_Icon.png"));
			btnSave.setIcon(new ImageIcon(img));
		} catch (IOException ex) {}
		catch(IllegalArgumentException ex){
			btnSave.setIcon(new ImageIcon());
		}
		btnSave.setText("Save");
		btnSave.setCursor(new Cursor(Cursor.HAND_CURSOR)); 
		// To change cursor as it moves over this button		
		horizontalBox_1.add(btnSave);
		
		final Component horizontalGlue_1 = Box.createHorizontalGlue();
		add(horizontalGlue_1);
	}

	
	/*
	 * Color picker class consisting of a 4 x 4 matrix of colors
	 * 
	 */
	/**
	 * @author Tianci
	 */
	class ColorPickerPanel extends JPanel {

		Color color;
		ColorBox selectedBox;
		/**
		 * Constructor for ColorPickerPanel.
		 */
		public ColorPickerPanel() {
			
			setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
			
			final Component horizontalGlue_2 = Box.createHorizontalGlue();
			add(horizontalGlue_2);
			
			final JPanel colorPicker = new JPanel();
			add(colorPicker);
			
			
			final Component verticalStrut = Box.createVerticalStrut(20);
			verticalStrut.setMaximumSize(new Dimension(0, 20));
			add(verticalStrut);
			
			final Component horizontalGlue_3 = Box.createHorizontalGlue();
			add(horizontalGlue_3);
			
			
			colorPicker.setPreferredSize(new Dimension(200, 200));
			colorPicker.setMaximumSize(new Dimension(200, 200));
			colorPicker.setLayout(new GridLayout(4, 4, 3, 3));
			colorPicker.setBackground(Color.WHITE);
			for(int i = 0; i < 16; i++)
			{
					ColorBox colorBox = new ColorBox(Color.red);
					colorBox.setBorder(new LineBorder(new Color(240, 240, 240), 2));
					if (i == 0)
						{
						selectedBox = colorBox;
						}
					
					colorBox.addMouseListener(new MouseAdapter() {
						@Override
						public void mousePressed(MouseEvent e) {
							setSelectedColorBox(e.getComponent());
						}
					});
					colorPicker.add(colorBox);
			}
			
			
			setBackground(color);
		}
		/*
		 * Sets the selected color, adding border and updating color field
		 * 
		 */
		protected void setSelectedColorBox(Component component) {
			selectedBox.setBorder(new LineBorder(new Color(240, 240, 240), 2));
			selectedBox = (ColorBox) component;
			selectedBox.setBorder(new LineBorder(Color.black, 2));
			color = selectedBox.getColor();
			
		}
		
		public Color getColor() {
			return color;
		}

		/*
		 * JPanel for each color box
		 */
		/**
		 * @author Tianci
		 */
		private class ColorBox extends JPanel {
			private final Color boxColor;
			/**
			 * Constructor for ColorBox.
			 * @param color Color
			 */
			public ColorBox(Color color)
			{
				boxColor = color;
				setBackground(color);
				
			}
			
			public Color getColor()
			{
				return boxColor;
			}
		}
	}

}
