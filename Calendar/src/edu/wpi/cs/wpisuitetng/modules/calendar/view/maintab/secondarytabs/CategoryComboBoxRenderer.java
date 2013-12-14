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
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.Category;

public class CategoryComboBoxRenderer extends JLabel implements
		ListCellRenderer<Category> {

	private BufferedImage colorImage;
	private ImageIcon colorIcon;
	
	public CategoryComboBoxRenderer(){
		setOpaque(true);
		setHorizontalAlignment(LEFT);
		setVerticalAlignment(CENTER);
	}
	
	/**
	 * Paints the component
	 */
	@Override
	public Component getListCellRendererComponent(JList<? extends Category> list,
			Category value, int index, boolean isSelected, boolean cellHasFocus) {
		//gets the drawing area ready
		colorImage = new BufferedImage(20, 20, BufferedImage.TYPE_BYTE_INDEXED);
		Graphics2D graphics = colorImage.createGraphics();
		
		//Fills in the image with the desired color
		graphics.setPaint(value.getCategoryColor());
		graphics.fillRect(0, 0, colorImage.getWidth(), colorImage.getHeight());
		
		//Draws a black border on around the image, the "-1"s are to keep it from going out of bounds
		graphics.setPaint(Color.BLACK);
		graphics.drawLine(0, 0, 0, colorImage.getHeight());
		graphics.drawLine(0, 0, colorImage.getWidth(), 0);
		graphics.drawLine(colorImage.getWidth()-1, 0, colorImage.getWidth()-1, colorImage.getHeight()-1);
		graphics.drawLine(0, colorImage.getHeight()-1, colorImage.getWidth()-1, colorImage.getHeight()-1);
		
		//Create Icon from image
		colorIcon = new ImageIcon(colorImage);
		
		//sets the fields of the label
		setText(value.getName());
		setIcon(colorIcon);
		
		//sets the proper background color for the label
        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        }
        else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }
		
		//returns the component
		return this;
	}

}
