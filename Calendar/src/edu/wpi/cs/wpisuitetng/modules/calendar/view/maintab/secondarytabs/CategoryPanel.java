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
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import edu.wpi.cs.wpisuitetng.modules.calendar.CalendarStandard;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.Category;

/**
 * Custom JPanel for each category in the category list
 * within CategoryTab
 *
 * @author CS Anonymous
 * @version $Revision: 1.0 $
 */
public class CategoryPanel extends JPanel {

	private Category category;
	private Color color;
	private JLabel lblCategoryName;
	private JPanel colorBox;
	private boolean selected = false;
	
	/**
	 * Constructor for CategoryPanel.
	 */
	public CategoryPanel() {
		setPreferredSize(new Dimension(80, 50));
		setMaximumSize(new Dimension(80, 50));
		setMinimumSize(new Dimension(80, 50));
		setBackground(CalendarStandard.CalendarYellow);
		final Border loweredbevel = BorderFactory.createLoweredBevelBorder();
		setBorder(loweredbevel);
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		final Box horizontalBox = Box.createHorizontalBox();
		add(horizontalBox);
		
		colorBox = new JPanel();
		colorBox.setMaximumSize(new Dimension(20, 32767));
		horizontalBox.add(colorBox);
		colorBox.setBackground(Color.RED);
		
		final Component horizontalStrut = Box.createHorizontalStrut(12);
		colorBox.add(horizontalStrut);
		final JPanel panel_1 = new JPanel();
		panel_1.setPreferredSize(new Dimension(100, 10));
		panel_1.setOpaque(false);
		horizontalBox.add(panel_1);
		panel_1.setLayout(new GridLayout(0, 1, 0, 0));
		lblCategoryName = new JLabel();
		lblCategoryName.setHorizontalTextPosition(SwingConstants.CENTER);
		lblCategoryName.setHorizontalAlignment(SwingConstants.CENTER);
		lblCategoryName.setCursor(new Cursor(Cursor.HAND_CURSOR));
		panel_1.add(lblCategoryName);
		lblCategoryName.setFont(CalendarStandard.CalendarFontBold);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Constructor for CategoryPanel.
	 * @param cat Category
	 */
	public CategoryPanel(Category cat) {
		this();
		setCategory(cat);
		setColorBox(cat.getCategoryColor());
		setCategoryName(cat.getName());
	}

	private void setCategory(Category cat) {
		category = cat;
	}

	private void setCategoryName(String name) {
		lblCategoryName.setText(name);
	}

	private void setColorBox(Color categoryColor) {
		colorBox.setBackground(categoryColor);
	}

	/**
	 * Constructor for CategoryPanel.
	 * @param layout LayoutManager
	 */
	public CategoryPanel(LayoutManager layout) {
		super(layout);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor for CategoryPanel.
	 * @param isDoubleBuffered boolean
	 */
	public CategoryPanel(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor for CategoryPanel.
	 * @param layout LayoutManager
	 * @param isDoubleBuffered boolean
	 */
	public CategoryPanel(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		// TODO Auto-generated constructor stub
	}

	public Category getCategory() {
		return category;
	}

	public void setSelected(boolean b) {
		// TODO Auto-generated method stub
		selected = b;
		if(b)
			setBackground(CalendarStandard.HeatMapRed);
		else
			setBackground(CalendarStandard.CalendarYellow);
		
	}

	public boolean getSelected() {
		// TODO Auto-generated method stub
		return selected;
	}


}
