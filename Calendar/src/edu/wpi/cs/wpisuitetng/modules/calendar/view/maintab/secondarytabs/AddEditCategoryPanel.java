package edu.wpi.cs.wpisuitetng.modules.calendar.view.maintab.secondarytabs;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.Box;
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
import javax.swing.JToggleButton;

import java.awt.GridLayout;

import javax.swing.JButton;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import edu.wpi.cs.wpisuitetng.modules.calendar.view.CommitmentViewPanel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.GUIEventController;

public class AddEditCategoryPanel extends JPanel {

	private JTextField textFieldName;
	private JRadioButton rdbtnTeam_1;
	

	public AddEditCategoryPanel(CategoryTab.CategoryMode mode) {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		Component horizontalGlue = Box.createHorizontalGlue();
		add(horizontalGlue);
		
		JPanel addEditFormPanel = new JPanel();
		addEditFormPanel.setPreferredSize(new Dimension(400, 10));
		addEditFormPanel.setMaximumSize(new Dimension(400, 4000));
		add(addEditFormPanel);
		GridBagLayout gbl_addEditFormPanel = new GridBagLayout();
		gbl_addEditFormPanel.columnWidths = new int[]{0, 0, 0};
		gbl_addEditFormPanel.rowHeights = new int[] {0, 0, 0, 0};
		gbl_addEditFormPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_addEditFormPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0};
		addEditFormPanel.setLayout(gbl_addEditFormPanel);
		
		JLabel lblName = new JLabel("Name:");
		GridBagConstraints gbc_lblName = new GridBagConstraints();
		gbc_lblName.anchor = GridBagConstraints.EAST;
		gbc_lblName.insets = new Insets(0, 0, 5, 5);
		gbc_lblName.gridx = 0;
		gbc_lblName.gridy = 0;
		addEditFormPanel.add(lblName, gbc_lblName);
		
		textFieldName = new JTextField();
		GridBagConstraints gbc_textFieldName = new GridBagConstraints();
		gbc_textFieldName.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldName.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldName.gridx = 1;
		gbc_textFieldName.gridy = 0;
		addEditFormPanel.add(textFieldName, gbc_textFieldName);
		textFieldName.setColumns(10);
		
		JLabel lblType = new JLabel("Type:");
		GridBagConstraints gbc_lblType = new GridBagConstraints();
		gbc_lblType.anchor = GridBagConstraints.EAST;
		gbc_lblType.insets = new Insets(0, 0, 5, 5);
		gbc_lblType.gridx = 0;
		gbc_lblType.gridy = 1;
		addEditFormPanel.add(lblType, gbc_lblType);
		
		Box horizontalBox = Box.createHorizontalBox();
		GridBagConstraints gbc_horizontalBox = new GridBagConstraints();
		gbc_horizontalBox.insets = new Insets(0, 0, 5, 0);
		gbc_horizontalBox.gridx = 1;
		gbc_horizontalBox.gridy = 1;
		addEditFormPanel.add(horizontalBox, gbc_horizontalBox);
		
		rdbtnTeam_1 = new JRadioButton("Team");
		horizontalBox.add(rdbtnTeam_1);
		
		JRadioButton rdbtnPersonal = new JRadioButton("Personal");
		horizontalBox.add(rdbtnPersonal);
		
		JLabel lblColor = new JLabel("Color:");
		GridBagConstraints gbc_lblColor = new GridBagConstraints();
		gbc_lblColor.anchor = GridBagConstraints.EAST;
		gbc_lblColor.insets = new Insets(0, 0, 5, 5);
		gbc_lblColor.gridx = 0;
		gbc_lblColor.gridy = 2;
		addEditFormPanel.add(lblColor, gbc_lblColor);
		
		ColorPickerPanel colorPickerPanel = new ColorPickerPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 2;
		addEditFormPanel.add(colorPickerPanel, gbc_panel);
		
		
		Box horizontalBox_1 = Box.createHorizontalBox();
		GridBagConstraints gbc_horizontalBox_1 = new GridBagConstraints();
		gbc_horizontalBox_1.gridx = 1;
		gbc_horizontalBox_1.gridy = 3;
		addEditFormPanel.add(horizontalBox_1, gbc_horizontalBox_1);
		
		JButton btnCancel = new JButton("Cancel");
		horizontalBox_1.add(btnCancel);
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		horizontalBox_1.add(horizontalStrut);
		
		JButton btnSave = new JButton("Save");
		horizontalBox_1.add(btnSave);
		
		
		
		Component horizontalGlue_1 = Box.createHorizontalGlue();
		add(horizontalGlue_1);
		if(mode == CategoryTab.CategoryMode.EDITING);
	}

	
	/*
	 * Color picker class consisting of a 4 x 4 matrix of colors
	 * 
	 */
	class ColorPickerPanel extends JPanel {

		Color color;
		ColorBox selectedBox;
		public ColorPickerPanel() {
			super();
			
			setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
			
			Component horizontalGlue_2 = Box.createHorizontalGlue();
			add(horizontalGlue_2);
			
			JPanel colorPicker = new JPanel();
			add(colorPicker);
			
			
			Component verticalStrut = Box.createVerticalStrut(20);
			verticalStrut.setMaximumSize(new Dimension(0, 20));
			add(verticalStrut);
			
			Component horizontalGlue_3 = Box.createHorizontalGlue();
			add(horizontalGlue_3);
			
			
			colorPicker.setPreferredSize(new Dimension(200, 200));
			colorPicker.setMaximumSize(new Dimension(200, 200));
			colorPicker.setLayout(new GridLayout(4, 4, 3, 3));
			for(int i = 0; i < 16; i++)
			{
					ColorBox colorBox = new ColorBox(Color.red);
					colorBox.setBorder(new LineBorder(new Color(240, 240, 240), 2));
					if (i == 0)
						selectedBox = colorBox;
					
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
		private class ColorBox extends JPanel {
			private Color boxColor;
			public ColorBox(Color color)
			{
				super();
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
