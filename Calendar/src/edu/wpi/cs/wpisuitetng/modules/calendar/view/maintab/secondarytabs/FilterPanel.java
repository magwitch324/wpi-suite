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
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import edu.wpi.cs.wpisuitetng.modules.calendar.CalendarStandard;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.Filter;

public class FilterPanel extends JPanel{
	private Filter filter;
	private JLabel lblFilterName;
	
	public FilterPanel(){
		setPreferredSize(new Dimension(80, 50));
		setMaximumSize(new Dimension(80,50));
		setMinimumSize(new Dimension(80,50));
		setBackground(CalendarStandard.CalendarYellow);
		final Border loweredbevel = BorderFactory.createLoweredBevelBorder();
		setBorder(loweredbevel);
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		final JPanel aPanel = new JPanel();
		aPanel.setPreferredSize(new Dimension(100, 10));
		aPanel.setOpaque(false);
		
		aPanel.setLayout(new GridLayout(0, 1, 0, 0));
		lblFilterName = new JLabel();
		lblFilterName.setHorizontalTextPosition(SwingConstants.CENTER);
		lblFilterName.setHorizontalAlignment(SwingConstants.CENTER);
		aPanel.add(lblFilterName);
		lblFilterName.setFont(CalendarStandard.CalendarFontBold);
		add(aPanel);
	}
	
	/**
	 * Constructor for FilterPanel.
	 * @param cat Filter
	 */
	public FilterPanel(Filter aFilter) {
		this();
		filter = aFilter;
		setFilterName(aFilter.getName());
	}

	private void setFilterName(String name) {
		System.out.println("name:" + name);
		lblFilterName.setText(name);
	}

	/**
	 * Constructor for FilterPanel.
	 * @param layout LayoutManager
	 */
	public FilterPanel(LayoutManager layout) {
		super(layout);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor for FilterPanel.
	 * @param isDoubleBuffered boolean
	 */
	public FilterPanel(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor for FilterPanel.
	 * @param layout LayoutManager
	 * @param isDoubleBuffered boolean
	 */
	public FilterPanel(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		// TODO Auto-generated constructor stub
	}
}
