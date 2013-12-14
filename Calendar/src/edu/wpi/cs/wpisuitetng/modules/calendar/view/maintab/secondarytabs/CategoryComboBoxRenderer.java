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

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.Category;

public class CategoryComboBoxRenderer extends JPanel implements
		ListCellRenderer<Category> {
	private JPanel colorIcon;
	private JLabel categoryName;
	
	public CategoryComboBoxRenderer(){
		super();
		setOpaque(true);
		setAlignmentX(LEFT_ALIGNMENT);
		setAlignmentY(CENTER_ALIGNMENT);
	}
	
	@Override
	public Component getListCellRendererComponent(JList<? extends Category> list,
			Category value, int index, boolean isSelected, boolean cellHasFocus) {
		
		colorIcon = new JPanel();
		colorIcon.setBackground(value.getCategoryColor());
		categoryName = new JLabel(value.getName());
		
		add(colorIcon);
		add(categoryName);
		
        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        }
        else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }
		
		
		return this;
	}

}
