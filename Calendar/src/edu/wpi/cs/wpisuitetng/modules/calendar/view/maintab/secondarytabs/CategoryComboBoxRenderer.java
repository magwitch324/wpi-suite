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
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.Category;

/**
 * Renders a category ComboBox used in commitment tab and event tab.
 * @author CS Anonymous
 * @version $Revision: 1.0 $
 */
public class CategoryComboBoxRenderer extends JLabel implements
		ListCellRenderer<Category> {

	private BufferedImage colorImage;
	private ImageIcon colorIcon;
	
	/**
	 * Constructor for CategoryComboBoxRenderer.
	 */
	public CategoryComboBoxRenderer(){
		setOpaque(true);
		setHorizontalAlignment(LEFT);
		setVerticalAlignment(CENTER);
	}
	
	/**
	 * Paints the component
	 * @param list JList<? extends Category>
	 * @param value Category
	 * @param index int
	 * @param isSelected boolean
	 * @param cellHasFocus boolean
	
	 * @return Component */
	@Override
	public Component getListCellRendererComponent(JList<? extends Category> list,
			Category value, int index, boolean isSelected, boolean cellHasFocus) {
		if(value != null){
			//gets the drawing area ready
			colorImage = new BufferedImage(20, 20, BufferedImage.TYPE_BYTE_INDEXED);
			final Graphics2D graphics = colorImage.createGraphics();

			//Fills in the image with the desired color
			graphics.setPaint(value.getCategoryColor());
			graphics.fillRect(0, 0, colorImage.getWidth(), colorImage.getHeight());

			//Draws a black border on around the image, 
			//the "-1"s are to keep it from going out of bounds
			graphics.setPaint(Color.BLACK);
			graphics.drawLine(0, 0, 0, colorImage.getHeight());
			graphics.drawLine(0, 0, colorImage.getWidth(), 0);
			graphics.drawLine(colorImage.getWidth() - 1, 0, 
					colorImage.getWidth() - 1, colorImage.getHeight() - 1);
			graphics.drawLine(0, colorImage.getHeight() - 1, 
					colorImage.getWidth() - 1, colorImage.getHeight() - 1);

			//Create Icon from image
			colorIcon = new ImageIcon(colorImage);

			//sets the fields of the label
			setText(value.getName());
			setIcon(colorIcon);
		}
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
