package edu.wpi.cs.wpisuitetng.modules.calendar.view.maintab.secondarytabs;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.SpringLayout;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.JTable;
import javax.swing.JList;
import javax.swing.JLabel;

import java.awt.Component;

import javax.swing.Box;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import javax.swing.BoxLayout;
import javax.swing.AbstractListModel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JButton;

import edu.wpi.cs.wpisuitetng.modules.calendar.models.CalendarDataModel;

public class CategoryTab extends JPanel {

	public CategoryTab() {
		
		createBaseUI();
		populateCategoryList();
		
		
		
		//load right pane
		
		
		
	}


	private void createBaseUI() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 0, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		add(panel, gbc_panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		Box horizontalBox = Box.createHorizontalBox();
		panel.add(horizontalBox);
		ButtonGroup teamPersonalRadioButtons = new ButtonGroup();
		
		JRadioButton rdbtnTeam = new JRadioButton("Team");
		teamPersonalRadioButtons.add(rdbtnTeam);
		horizontalBox.add(rdbtnTeam);
		
		JRadioButton rdbtnPersonal = new JRadioButton("Personal");
		teamPersonalRadioButtons.add(rdbtnPersonal);
		horizontalBox.add(rdbtnPersonal);
		
		JRadioButton rdbtnBoth = new JRadioButton("Both");
		rdbtnBoth.setSelected(true);
		teamPersonalRadioButtons.add(rdbtnBoth);
		horizontalBox.add(rdbtnBoth);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		panel.add(scrollPane);
		
		JPanel panel_2 = new JPanel();
		scrollPane.setViewportView(panel_2);
		
		Box horizontalBox_1 = Box.createHorizontalBox();
		panel.add(horizontalBox_1);
		
		JButton btnDelete = new JButton("Delete");
		horizontalBox_1.add(btnDelete);
		
		JButton btnEdit = new JButton("Edit");
		horizontalBox_1.add(btnEdit);
		
		JButton btnNew = new JButton("New");
		horizontalBox_1.add(btnNew);
		
		JPanel panel_1 = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 1;
		gbc_panel_1.gridy = 0;
		add(panel_1, gbc_panel_1);
		
		Component horizontalStrut = Box.createHorizontalStrut(300);
		panel_1.add(horizontalStrut);		
	}
	
	private void populateCategoryList() {
		
		
		
	}


}
