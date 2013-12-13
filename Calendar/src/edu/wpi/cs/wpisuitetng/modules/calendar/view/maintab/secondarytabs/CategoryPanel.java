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
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {};
		gridBagLayout.rowHeights = new int[]{0, 0};
		gridBagLayout.columnWeights = new double[]{0.0};
		gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		Box horizontalBox = Box.createHorizontalBox();
		GridBagConstraints gbc_horizontalBox = new GridBagConstraints();
		gbc_horizontalBox.gridx = 0;
		gbc_horizontalBox.gridy = 0;
		add(horizontalBox, gbc_horizontalBox);
		
		colorBox = new JPanel();
		horizontalBox.add(colorBox);
		colorBox.setBackground(Color.RED);
		
		Component horizontalStrut = Box.createHorizontalStrut(12);
		colorBox.add(horizontalStrut);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(8);
		horizontalBox.add(horizontalStrut_1);
		JPanel panel_1 = new JPanel();
		panel_1.setOpaque(false);
		horizontalBox.add(panel_1);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.Y_AXIS));
		lblCategoryName = new JLabel();
		panel_1.add(lblCategoryName);
		lblCategoryName.setFont(CalendarStandard.CalendarFontBold);
		// TODO Auto-generated constructor stub
	}
	
	public CategoryPanel(Category cat) {
		this();
		this.category = cat;
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
