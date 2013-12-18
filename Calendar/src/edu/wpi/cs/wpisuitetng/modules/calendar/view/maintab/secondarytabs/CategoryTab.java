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
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.calendar.CalendarStandard;
import edu.wpi.cs.wpisuitetng.modules.calendar.controller.UpdateCalendarDataController;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.Category;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.CategoryList;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.CalendarData;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.CalendarDataModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.CalendarProperties;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.CalendarPropertiesModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.GUIEventController;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

/**
 * Create/edit catergory tab.
 * @author CS Anonymous
 * @version $Revision: 1.0 $
 */
@SuppressWarnings("serial")
public class CategoryTab extends JPanel {

	private final CategoryList teamCategories;
	private final CategoryList personalCategories;
	private JRadioButton rdbtnPersonal;
	private JRadioButton rdbtnBoth;
	private JButton btnDelete;
	private JButton btnEdit;
	private JButton btnNew;
	private JRadioButton rdbtnTeam;
	private JPanel categoryListPanel;
	private SpringLayout categoryListLayout;
	private AddEditCategoryPanel addEditPanel;
	private JPanel viewPanel;
	private CategoryMode mode;
	private Component viewPanelStrut;
	private JTextField textFieldName;
	private JScrollPane scrollPane;
	protected List<CategoryPanel> selectedCategories;
	private CalendarProperties calProps;
	private boolean initialized;
	private Component btnNewStrut;
	private Box buttonBox;

	/**
	 * @author CS Anonymous
	 */
	public enum CategoryMode {
		ADDING(0),
		EDITING(1),
		VIEWING(2);
		private final int currentMode;
		
		private CategoryMode(int currentMode) {
			this.currentMode = currentMode;
		}
	}
	
	/**
	 * Constructor for CategoryTab.
	 */
	public CategoryTab() {
		initialized = false;
		createBaseUI();
		
		//Load category lists from CalendarDataModel
		teamCategories = CalendarDataModel.getInstance().getCalendarData(
				ConfigManager.getConfig().getProjectName()).getCategories(); 
		personalCategories = CalendarDataModel.getInstance().getCalendarData(
				ConfigManager.getConfig().getProjectName() + 
				"-" + ConfigManager.getConfig().getUserName()).getCategories(); 
		
		populateCategoryList();
		
		addListeners();
		
		//initialize in "viewing" mode
		setupViewingView();
		setBackground(Color.WHITE);
		initialized = true;
		applyCalProps();
		refreshCategoryListPanel();
	}




	/**
	 * Instantiate components of UI
	 */
	private void createBaseUI() {
		
		
		viewPanel = new JPanel();
		
		viewPanel.setLayout(new BoxLayout(viewPanel, BoxLayout.Y_AXIS));
		viewPanel.setBackground(Color.WHITE);
		
		final Box horizontalBox = Box.createHorizontalBox();
		viewPanel.add(horizontalBox);
		final ButtonGroup teamPersonalRadioButtons = new ButtonGroup();
		
		rdbtnPersonal = new JRadioButton("Personal");
		rdbtnPersonal.setBackground(Color.WHITE);
		teamPersonalRadioButtons.add(rdbtnPersonal);
		horizontalBox.add(rdbtnPersonal);
		rdbtnPersonal.setSelected(true);
		
		rdbtnTeam = new JRadioButton("Team");
		rdbtnTeam.setBackground(Color.WHITE);
		teamPersonalRadioButtons.add(rdbtnTeam);
		horizontalBox.add(rdbtnTeam);
		
		rdbtnBoth = new JRadioButton("Both");
		rdbtnBoth.setBackground(Color.WHITE);

		teamPersonalRadioButtons.add(rdbtnBoth);
		horizontalBox.add(rdbtnBoth);
		
		final JPanel categoryListLabelPanel = new JPanel();
		categoryListLabelPanel.setMaximumSize(new Dimension(32767, 24));
		categoryListLabelPanel.setPreferredSize(new Dimension(10, 24));
		categoryListLabelPanel.setBackground(new Color(196, 0, 14));
		viewPanel.add(categoryListLabelPanel);
		categoryListLabelPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));
		
		final JLabel categoryListLabel = new JLabel("List of Categories");
		categoryListLabel.setPreferredSize(new Dimension(130, 24));
		categoryListLabelPanel.add(categoryListLabel);
		categoryListLabel.setForeground(Color.WHITE);
		categoryListLabel.setBackground(new Color(196, 0, 14));
		
		
		scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.getVerticalScrollBar().setBackground(CalendarStandard.CalendarYellow);
		scrollPane.setBackground(Color.WHITE);
		viewPanel.add(scrollPane);
		
		categoryListPanel = new JPanel();
		scrollPane.setViewportView(categoryListPanel);
		categoryListPanel.setBackground(Color.WHITE);
		categoryListLayout = new SpringLayout();
		categoryListPanel.setLayout(categoryListLayout);
		
		buttonBox = Box.createHorizontalBox();
		viewPanel.add(buttonBox);
		

		//New Category button
		btnNew = new JButton();
		try {
			final Image img = ImageIO.read(getClass().getResource("New_Icon.png"));
			btnNew = new JButton("New Category", new ImageIcon(img));
		} catch (IOException ex) {}
		catch(IllegalArgumentException ex){
			btnNew.setText("New Category");
		}
		
		buttonBox.add(btnNew);

		btnNewStrut = Box.createHorizontalStrut(10);
		btnNewStrut.setMaximumSize(new Dimension(10, 0));
		buttonBox.add(btnNewStrut);
		
		//Add Edit button
		btnEdit = new JButton();
		try {
			final Image img = ImageIO.read(getClass().getResource("Edit_Icon.png"));
			btnEdit = new JButton("Edit Category", new ImageIcon(img));
		} catch (IOException ex) {}
		catch(IllegalArgumentException ex){
			btnEdit.setText("Edit Category");
		}
		btnEdit.setEnabled(false);
		
		
		buttonBox.add(btnEdit);
		
		final Component horizontalStrut_2 = Box.createHorizontalStrut(10);
		horizontalStrut_2.setMaximumSize(new Dimension(10, 0));
		buttonBox.add(horizontalStrut_2);
		
		// Add Delete Button
		btnDelete = new JButton();
		try {
			final Image img = ImageIO.read(getClass().getResource("Delete_Icon.png"));
			final Image newimg = img.getScaledInstance( 30, 30,  java.awt.Image.SCALE_SMOOTH ) ;
			btnDelete = new JButton("Delete Category", new ImageIcon(newimg));
		} catch (IOException ex) {}
		catch(IllegalArgumentException ex){
			btnDelete.setText("Delete Category");
		}
		btnDelete.setEnabled(false);
		
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				CalendarData calData;
			
				for(CategoryPanel catPane : selectedCategories ) {
					GUIEventController.getInstance().scrubCategory(catPane.getCategory());
					if(catPane.getCategory().getIsPersonal()) {
						calData = CalendarDataModel.getInstance().getCalendarData(
								ConfigManager.getConfig().getProjectName() +
								"-" + ConfigManager.getConfig().getUserName()); 
						personalCategories.remove(catPane.getCategory().getID());
						UpdateCalendarDataController.getInstance().updateCalendarData(calData);
					} else {
						calData = CalendarDataModel.getInstance().getCalendarData(
								ConfigManager.getConfig().getProjectName()); 
						teamCategories.remove(catPane.getCategory().getID());
						UpdateCalendarDataController.getInstance().updateCalendarData(calData);
					}
				}
			refreshCategoryListPanel();
			btnEdit.setEnabled(false);
			btnDelete.setEnabled(false);
			}
		});
		
		
		buttonBox.add(btnDelete);
		
		
		viewPanelStrut = Box.createHorizontalStrut(450);
		viewPanelStrut.setMaximumSize(new Dimension(450, 0));
		viewPanel.add(viewPanelStrut);
		
		addEditPanel = new AddEditCategoryPanel();
		addEditPanel.setMinimumSize(new Dimension(460, 10));
		addEditPanel.setPreferredSize(new Dimension(460, 10));
		selectedCategories = new ArrayList<CategoryPanel>();

	}
	
	/**
	 * Populate the list of categories with data from team and personal category lists
	 */
	private void populateCategoryList() {

		final List<Category> catList = new ArrayList<Category>();
		final CategoryList bothCategories = new CategoryList();
		if(rdbtnPersonal.isSelected()) {
			catList.addAll(personalCategories.getCategories());
		} else if(rdbtnTeam.isSelected()) {
			catList.addAll(teamCategories.getCategories());
		} else {
			final Category[] bothCatArray = 
					new Category[teamCategories.getSize() + personalCategories.getSize()];
			catList.addAll(teamCategories.getCategories());
			catList.addAll(personalCategories.getCategories());
			for(int i = 0; i < catList.size(); i++)
			{
				bothCatArray[i] = catList.get(i);
			}
			bothCategories.addCategories(bothCatArray);
			bothCategories.sortByAlphabet();
			catList.clear();
			catList.addAll(bothCategories.getCategories());
		}
		
		// CategoryPanel to keep track of spring layout constraints of previously added panel
		CategoryPanel oldCatPanel = new CategoryPanel(); 
		CategoryPanel catPanel = new CategoryPanel();
		for(int i = 0; i < catList.size(); i++)
		{
			catPanel = new CategoryPanel(catList.get(i));
			//If first panel, add to top of list panel
			if (i == 0)
			{
				categoryListLayout.putConstraint(SpringLayout.NORTH, catPanel, 
						1, SpringLayout.NORTH, categoryListPanel);
				categoryListLayout.putConstraint(SpringLayout.WEST, catPanel, 
						1, SpringLayout.WEST, categoryListPanel);
				categoryListLayout.putConstraint(SpringLayout.EAST, catPanel,
						1, SpringLayout.EAST, categoryListPanel);
			}
			else
			{
				//add panel below previous panel
				categoryListLayout.putConstraint(SpringLayout.NORTH, catPanel, 
						1, SpringLayout.SOUTH, oldCatPanel);
				categoryListLayout.putConstraint(SpringLayout.WEST, catPanel, 
						1, SpringLayout.WEST, categoryListPanel);
				categoryListLayout.putConstraint(SpringLayout.EAST, catPanel, 
						1, SpringLayout.EAST, categoryListPanel);
			}

			categoryListPanel.add(catPanel);
			
			
			catPanel.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (e.getClickCount() > 1)
						{
							editCategory(((CategoryPanel)e.getComponent()).getCategory());
							
						}
					if (e.getClickCount() == 1)
					{
						CategoryPanel comp = (CategoryPanel) e.getComponent();
						if(!selectedCategories.isEmpty() && ! e.isControlDown())
						{
							removeSelectedCategories(); //clear existing selections
						}
						selectedCategories.add(comp);
						comp.setSelected(true);
						btnDelete.setEnabled(true);
						btnEdit.setEnabled(true);

						
					}
					
				}
			});
			
			
			oldCatPanel = catPanel; //update oldCatPanel to be previously added panel
		}
		
		categoryListLayout.putConstraint(SpringLayout.SOUTH, 
				categoryListPanel, 0, SpringLayout.SOUTH, catPanel);
	}
	

	protected void removeSelectedCategories() {
		// TODO Auto-generated method stub
		for (CategoryPanel catPanel: selectedCategories)
		{
			catPanel.setSelected(false);
		}
		selectedCategories.clear();
	}




	protected void editCategory(Category category) {
		addEditPanel = new AddEditCategoryPanel(category);
		addEditPanel.setMinimumSize(new Dimension(460, 10));
		addEditPanel.setPreferredSize(new Dimension(460, 10));
		setupAddView();
	}




	/**
	 * Add event handlers to GUI components
	 */
	private void addListeners() {
		btnNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addEditPanel = new AddEditCategoryPanel(!(rdbtnTeam.isSelected()));
				setupAddView();
			}
		});
		
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editCategory(selectedCategories.get(0).getCategory());
			}
		});
		
		rdbtnTeam.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				calProps.setCategoryTabView(1);
				refreshCategoryListPanel();
				btnDelete.setEnabled(false);
				btnEdit.setEnabled(false);
			}
		});
		
		rdbtnPersonal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				calProps.setCategoryTabView(0);
				refreshCategoryListPanel();
				btnDelete.setEnabled(false);
				btnEdit.setEnabled(false);
			}
		});
		
		rdbtnBoth.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				calProps.setCategoryTabView(2);
				refreshCategoryListPanel();
				btnDelete.setEnabled(false);
				btnEdit.setEnabled(false);
			}
		});
	}
	

	/**
	 * Method refreshCategoryListPanel.
	 */
	protected void refreshCategoryListPanel() {
		categoryListPanel.removeAll();
		populateCategoryList();
		scrollPane.revalidate();
		scrollPane.repaint();

	}




	/**
	 * Setup the Adding view, where a user will create a new Category and save it.
	 * The category list will still be visible on the left
	 */
	void setupAddView()
	{
		mode = CategoryMode.ADDING;
		this.removeAll();
		final GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {0, 0};
		gridBagLayout.rowHeights = new int[]{0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0};
		gridBagLayout.rowWeights = new double[]{1.0};
		setLayout(gridBagLayout);
		
		final GridBagConstraints gbc_viewPanel = new GridBagConstraints();
		gbc_viewPanel.insets = new Insets(0, 15, 0, 15);
		gbc_viewPanel.fill = GridBagConstraints.BOTH;
		gbc_viewPanel.gridx = 0;
		gbc_viewPanel.gridy = 0;
		add(viewPanel, gbc_viewPanel);
		
		final GridBagConstraints gbc_addEditPanel = new GridBagConstraints();
		gbc_addEditPanel.fill = GridBagConstraints.BOTH;
		gbc_addEditPanel.gridx = 1;
		gbc_addEditPanel.gridy = 0;
		add(addEditPanel, gbc_addEditPanel);
		
		buttonBox.setVisible(false);
		
		revalidate();
		repaint();
		
		addEditPanel.addComponentListener(new ComponentListener() {

			@Override
			public void componentResized(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void componentMoved(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void componentShown(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void componentHidden(ComponentEvent e) {
				// TODO Auto-generated method stub
				refreshCategoryListPanel();
				setupViewingView();
			}
			
		});
	}
	
	/**
	 * Set up the view for "viewing" mode, where the user can browse and
	 * interact with the list of categories
	 */
	void setupViewingView()
	{
		mode = CategoryMode.VIEWING;
		this.removeAll();
		final GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 1.0};
		gridBagLayout.rowWeights = new double[]{1.0};
		setLayout(gridBagLayout);
		
		final GridBagConstraints gbc_viewPanel = new GridBagConstraints();
		gbc_viewPanel.insets = new Insets(0, 0, 0, 5);
		gbc_viewPanel.fill = GridBagConstraints.BOTH;
		gbc_viewPanel.gridx = 1;
		gbc_viewPanel.gridy = 0;
		add(viewPanel, gbc_viewPanel);

		buttonBox.setVisible(true);

		
		//set size of view panel
		
		revalidate();
		repaint();
	}
	
	/**
	 * Used after cal props has been fetched from the server.
	 */
	protected void applyCalProps(){

		calProps = CalendarPropertiesModel.getInstance().getCalendarProps(
				ConfigManager.getConfig().getProjectName() + "-"
						+ ConfigManager.getConfig().getUserName() + "-PROPS");
		if(calProps != null && initialized){
			switch(calProps.getCategoryTabView()){
			case 0: rdbtnPersonal.setSelected(true);
			break;
			case 1: rdbtnTeam.setSelected(true);
			break;
			case 2: rdbtnBoth.setSelected(true);
			}
		}
	}
}
