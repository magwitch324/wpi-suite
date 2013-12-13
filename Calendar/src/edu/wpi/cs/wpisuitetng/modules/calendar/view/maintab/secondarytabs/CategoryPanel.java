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
import java.awt.LayoutManager;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

import edu.wpi.cs.wpisuitetng.modules.calendar.CalendarStandard;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.Category;

import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.Box;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.SwingConstants;
import javax.swing.BoxLayout;
import java.awt.GridLayout;

/**
 * Custom JPanel for each category in the category list
 * within CategoryTab
 *
 */
public class CategoryPanel extends JPanel {

	private Category category;
	private Color color;
	private JLabel lblCategoryName;
	private JPanel colorBox;
	
	public CategoryPanel() {
		setPreferredSize(new Dimension(80, 50));
		setBackground(CalendarStandard.CalendarYellow);
		Border loweredbevel = BorderFactory.createLoweredBevelBorder();
		setBorder(loweredbevel);
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		Box horizontalBox = Box.createHorizontalBox();
		add(horizontalBox);
		
		colorBox = new JPanel();
		colorBox.setMaximumSize(new Dimension(20, 32767));
		horizontalBox.add(colorBox);
		colorBox.setBackground(Color.RED);
		
		Component horizontalStrut = Box.createHorizontalStrut(12);
		colorBox.add(horizontalStrut);
		JPanel panel_1 = new JPanel();
		panel_1.setPreferredSize(new Dimension(100, 10));
		panel_1.setOpaque(false);
		horizontalBox.add(panel_1);
		panel_1.setLayout(new GridLayout(0, 1, 0, 0));
		lblCategoryName = new JLabel();
		lblCategoryName.setHorizontalTextPosition(SwingConstants.CENTER);
		lblCategoryName.setHorizontalAlignment(SwingConstants.CENTER);
		
		panel_1.add(lblCategoryName);
		lblCategoryName.setFont(CalendarStandard.CalendarFontBold);
		// TODO Auto-generated constructor stub
	}
	
	public CategoryPanel(Category cat) {
		this();
		category = cat;
		setColorBox(cat.getCategoryColor());
		setCategoryName(cat.getName());	
	}

	private void setCategoryName(String name) {
		lblCategoryName.setText(name);
	}

	private void setColorBox(Color categoryColor) {
		colorBox.setBackground(categoryColor);
	}

	public CategoryPanel(LayoutManager layout) {
		super(layout);
		// TODO Auto-generated constructor stub
	}

	public CategoryPanel(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		// TODO Auto-generated constructor stub
	}

	public CategoryPanel(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		// TODO Auto-generated constructor stub
	}

}
