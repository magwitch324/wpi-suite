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
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import javax.swing.colorchooser.ColorSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.calendar.CalendarStandard;
import edu.wpi.cs.wpisuitetng.modules.calendar.controller.UpdateCalendarDataController;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.Category;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.CalendarData;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.CalendarDataModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.GUIEventController;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.maintab.secondarytabs.CategoryTab.CategoryMode;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.beans.PropertyChangeListener;

/**
 * AddEditCategoryPanel is created by clicking the new category button
 * in the category tab.
 * @author CS Anonymous
 * @version $Revision: 1.0 $
 */
public class AddEditCategoryPanel extends JPanel {

	private JTextField textFieldName;
	private JRadioButton rdbtnTeam;
	private JColorChooser colorPickerPanel;
	private JRadioButton rdbtnPersonal;
	private JButton btnSave;
	private JButton btnCancel;
	CategoryTab.CategoryMode mode;
	private Category editingCategory;
	private JPanel colorPreviewPanel;
	private JButton btnDelete;


	/**
	 * Constructor for AddEditCategoryPanel.
	 * @param mode CategoryTab.CategoryMode
	 */
	public AddEditCategoryPanel() {
		mode = CategoryTab.CategoryMode.ADDING;
		setupUI();
	}
	
	/**
	 * Constructor 
	 * @param isPersonal
	 */
	public AddEditCategoryPanel(boolean isPersonal){
		this();
		if(isPersonal){
			rdbtnPersonal.setSelected(true);
		} else {
			rdbtnTeam.setSelected(true);
		}
	}
	
	public void setupUI()
	{
		setBackground(Color.WHITE);
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		final Component horizontalGlue = Box.createHorizontalGlue();
		add(horizontalGlue);
		
		final JPanel addEditFormPanel = new JPanel();
		addEditFormPanel.setMinimumSize(new Dimension(430, 10));
		addEditFormPanel.setBackground(Color.WHITE);
		addEditFormPanel.setPreferredSize(new Dimension(430, 10));
		addEditFormPanel.setMaximumSize(new Dimension(430, 4000));
		add(addEditFormPanel);
		final GridBagLayout gbl_addEditFormPanel = new GridBagLayout();
		gbl_addEditFormPanel.columnWidths = new int[]{0, 0, 0};
		gbl_addEditFormPanel.rowHeights = new int[] {0, 0, 0, 0, 0};
		gbl_addEditFormPanel.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_addEditFormPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0};
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
		textFieldName.addKeyListener(new KeyListener(){

			@Override
			public void keyTyped(KeyEvent e) {
				
			}

			@Override
			public void keyPressed(KeyEvent e) {
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if(textFieldName.getText().equals("") || textFieldName.getText().trim().equals("")){
					btnSave.setEnabled(false);
				} else { 
					if (mode == CategoryMode.EDITING){
						if (textFieldName.getText().equals(editingCategory.getName())
								&& colorPickerPanel.getColor().equals(
										editingCategory.getCategoryColor())){
							btnSave.setEnabled(false);
							return;
						}
					}
					btnSave.setEnabled(true);
				}
			}
			
		});
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
		
		// Personal radio button
		rdbtnPersonal = new JRadioButton("Personal");
		rdbtnPersonal.setBackground(Color.WHITE);
		horizontalBox.add(rdbtnPersonal);
		teamPersonalRadioButtons.add(rdbtnPersonal);
		rdbtnPersonal.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		// Team radio button
		rdbtnTeam = new JRadioButton("Team");
		rdbtnTeam.setBackground(Color.WHITE);
		horizontalBox.add(rdbtnTeam);
		teamPersonalRadioButtons.add(rdbtnTeam);
		rdbtnTeam.setSelected(true);	//sets default to team
		rdbtnTeam.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		final JLabel lblColor = new JLabel("<html><font>" + "Color" + "</font>" 
											+ "<font color=red>" + "*" + "</font>" 
											+ "<font>" + ":" + "</font></html>");
		final GridBagConstraints gbc_lblColor = new GridBagConstraints();
		gbc_lblColor.anchor = GridBagConstraints.EAST;
		gbc_lblColor.insets = new Insets(0, 0, 5, 5);
		gbc_lblColor.gridx = 0;
		gbc_lblColor.gridy = 2;
		addEditFormPanel.add(lblColor, gbc_lblColor);
		
		final JPanel colorPreviewContainer = new JPanel();
		colorPreviewContainer.setBackground(Color.WHITE);
		final GridBagConstraints gbc_colorPreviewContainer = new GridBagConstraints();
		gbc_colorPreviewContainer.insets = new Insets(0, 0, 5, 0);
		gbc_colorPreviewContainer.fill = GridBagConstraints.BOTH;
		gbc_colorPreviewContainer.gridx = 1;
		gbc_colorPreviewContainer.gridy = 2;
		addEditFormPanel.add(colorPreviewContainer, gbc_colorPreviewContainer);
		colorPreviewContainer.setLayout(new BoxLayout(colorPreviewContainer, BoxLayout.X_AXIS));
		
		colorPreviewPanel = new JPanel();
		colorPreviewPanel.setMaximumSize(new Dimension(30, 30));
		colorPreviewPanel.setMinimumSize(new Dimension(30, 30));
		colorPreviewPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		colorPreviewPanel.setPreferredSize(new Dimension(30, 30));
		colorPreviewContainer.add(colorPreviewPanel);
		
		final JPanel colorContainer1 = new JPanel();
		colorContainer1.setBackground(Color.WHITE);
		final GridBagConstraints gbc_panel_3 = new GridBagConstraints();
		gbc_panel_3.fill = GridBagConstraints.BOTH;
		gbc_panel_3.insets = new Insets(0, 0, 5, 0);
		gbc_panel_3.gridx = 1;
		gbc_panel_3.gridy = 3;
		addEditFormPanel.add(colorContainer1, gbc_panel_3);
		colorContainer1.setLayout(new BoxLayout(colorContainer1, BoxLayout.X_AXIS));
		
		final JPanel colorContainer2 = new JPanel();
		colorContainer2.setBackground(Color.WHITE);
		colorContainer2.setMaximumSize(new Dimension(346, 32767));
		colorContainer2.setPreferredSize(new Dimension(346, 106));
		colorContainer1.add(colorContainer2);
		colorContainer2.setLayout(null);
		
		colorPickerPanel = new JColorChooser();
		colorPickerPanel.setBounds(1, 1, 416, 104);
		colorPickerPanel.setMinimumSize(new Dimension(613, 100));
		colorContainer2.add(colorPickerPanel);
		colorPickerPanel.setMaximumSize(new Dimension(480, 2147483647));
		colorPickerPanel.setPreferredSize(new Dimension(400, 100));
		final AbstractColorChooserPanel panel = colorPickerPanel.getChooserPanels()[0];
		final Component[] panelComponents = panel.getComponents();
		for(Component comp:panelComponents){
		}
		final AbstractColorChooserPanel[] panels = { panel };
		colorPickerPanel.setChooserPanels(panels);
		colorPickerPanel.setBackground(Color.WHITE);
		colorPickerPanel.setPreviewPanel(new JPanel());
		colorPickerPanel.setBorder(textFieldName.getBorder());
		
		colorPickerPanel.setColor(Color.BLUE);
		colorPickerPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
		colorPreviewPanel.setBackground(Color.BLUE);
		
		final Box horizontalBox_1 = Box.createHorizontalBox();
		final GridBagConstraints gbc_horizontalBox_1 = new GridBagConstraints();
		gbc_horizontalBox_1.gridx = 1;
		gbc_horizontalBox_1.gridy = 4;
		addEditFormPanel.add(horizontalBox_1, gbc_horizontalBox_1);
		
		btnSave = new JButton();
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
		btnSave.setEnabled(false);

		
		final Component horizontalStrut = Box.createHorizontalStrut(20);

		horizontalBox_1.add(horizontalStrut);
		
		btnCancel = new JButton();
		try {
			final Image img = ImageIO.read(getClass().getResource("Cancel_Icon.png"));
			btnCancel.setIcon(new ImageIcon(img));
		} catch (IOException ex) {}
		catch(IllegalArgumentException ex){
			btnCancel.setIcon(new ImageIcon());
		}
		btnCancel.setText("Cancel");
		btnCancel.setCursor(new Cursor(Cursor.HAND_CURSOR)); 
		
		horizontalBox_1.add(btnCancel);
		
		if (mode == CategoryTab.CategoryMode.EDITING)
		{
			final Component horizontalStrut2 = Box.createHorizontalStrut(20);
	
			horizontalBox_1.add(horizontalStrut2);
			
			// Add Delete Button
			btnDelete = new JButton();
			try {
				final Image img = ImageIO.read(getClass().getResource("Delete_Icon.png"));
				final Image newimg = img.getScaledInstance( 30, 30,  java.awt.Image.SCALE_SMOOTH ) ;
				btnDelete = new JButton("Delete", new ImageIcon(newimg));
			} catch (IOException ex) {}
			catch(IllegalArgumentException ex){
				btnDelete.setText("Delete");
			}
			horizontalBox_1.add(btnDelete);
		}
		
		final Component horizontalGlue_1 = Box.createHorizontalGlue();

		add(horizontalGlue_1);
		
		//LISTENERS
		
		btnCancel.setCursor(new Cursor(Cursor.HAND_CURSOR)); 
		// To change cursor as it moves over this button
		// Action listener for cancel button 
		btnCancel.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				close();
			}
		});
		
		// Action listener for save button 
		btnSave.setCursor(new Cursor(Cursor.HAND_CURSOR)); 
		// To change cursor as it moves over this button
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addCategory();
				close();
			}
		});
		final ColorSelectionModel model = colorPickerPanel.getSelectionModel();
		final ChangeListener changeListener = new ChangeListener() {
	      public void stateChanged(ChangeEvent changeEvent) {
	        colorPreviewPanel.setBackground(colorPickerPanel.getColor());
			if(textFieldName.getText().equals("") || textFieldName.getText().trim().equals("")){
				btnSave.setEnabled(false);
			} else { 
				if (mode == CategoryMode.EDITING){
					if (textFieldName.getText().equals(editingCategory.getName())
							&& colorPickerPanel.getColor().equals(
									editingCategory.getCategoryColor())){
						btnSave.setEnabled(false);
						return;
					}
				}
				btnSave.setEnabled(true);
			}
	      }
	    };
	    model.addChangeListener(changeListener);
	}
	
	protected void close() {
		// TODO Auto-generated method stub
		this.setVisible(false);
	}

	public AddEditCategoryPanel(Category category) {
		mode = CategoryTab.CategoryMode.EDITING;
		setupUI();
		
		editingCategory = category;
		textFieldName.setText(category.getName());
		if(category.getIsPersonal())
			{
				rdbtnPersonal.setSelected(true);
			}
		else
			{
				rdbtnTeam.setSelected(true);
			}
		
		colorPickerPanel.setColor(category.getCategoryColor());
		colorPreviewPanel.setBackground(category.getCategoryColor());
		//Disable changing team/personal
		rdbtnPersonal.setEnabled(false);
		rdbtnTeam.setEnabled(false);
	}

	/**
	 * Controls the enable state of the save button 
	 * by checking all user editable elements in commitment tab.
	 * 
	 * STILL IN PROGRESS - DOESN'T WORK/NOT IMPLEMENTED
	 */
	private void checkSaveBtnStatus(){
		
		if(textFieldName.getText().equals("")) { 
			btnSave.setEnabled(false);
		} else {
			btnSave.setEnabled(true);
		}
	}
	
	/**
	 * Adds new category with information contained in fields
	 */
	private void addCategory() {

		CalendarData calData;
		// Name
		final String name = textFieldName.getText();
		// Team or Personal
		boolean isPersonal;
		
		if (!rdbtnTeam.isSelected()){
			calData = CalendarDataModel.getInstance().getCalendarData(
					ConfigManager.getConfig().getProjectName() + 
					"-" + ConfigManager.getConfig().getUserName()); 
			isPersonal = true;
		} else {
			calData = CalendarDataModel.getInstance().getCalendarData(
					ConfigManager.getConfig().getProjectName()); 
			isPersonal = false;
		}
		
		// Color
		final Color catColor = colorPickerPanel.getColor();
		
		// Creates and adds new category
		if(mode == CategoryTab.CategoryMode.ADDING)
			{
				calData.addCategory(new Category(name, catColor, isPersonal));
			}
		else
		{
			editingCategory.setName(name);
			editingCategory.setCategoryColor(catColor);
			editingCategory.setPersonal(isPersonal);
			calData.getCategories().update(editingCategory);
		}
		UpdateCalendarDataController.getInstance().updateCalendarData(calData);
	}
	
	

}
