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

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.Category;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.CategoryList;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.Filter;

/**
 * Renders the combo box icon for filters.
 * @author CS Anonymous
 * @version $Revision: 1.0 $
 */
public class FilterComboBoxRenderer extends JPanel implements
		ListCellRenderer<Filter> {

	private BufferedImage colorImage;
	private ImageIcon colorIcon;
	private JLabel labelItem = new JLabel();
	
	/**
	 * Constructor for CategoryComboBoxRenderer.
	 */
	public FilterComboBoxRenderer(){
		setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
         
        labelItem.setOpaque(true);
        labelItem.setHorizontalAlignment(JLabel.LEFT);
         
        add(labelItem, constraints);
        setBackground(Color.LIGHT_GRAY);
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
	public Component getListCellRendererComponent(JList<? extends Filter> list,
			Filter value, int index, boolean isSelected, boolean cellHasFocus) {
		if(value != null){
			CategoryList teamCats = GUIEventController.getInstance().getCalendar().getTeamCalData().getCategories();
			CategoryList myCats = GUIEventController.getInstance().getCalendar().getMyCalData().getCategories();
			//gets the drawing area ready
			for(int c:value.getActiveCategories()){
				
			}
			colorImage = new BufferedImage(20, 20, BufferedImage.TYPE_BYTE_INDEXED);
			final Graphics2D graphics = colorImage.createGraphics();

			//Fills in the image with the desired color
			//graphics.setPaint(value.getCategoryColor());
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
			//setText(value.getName());
			//setIcon(colorIcon);
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
