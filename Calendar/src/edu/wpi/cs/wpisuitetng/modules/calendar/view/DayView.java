package edu.wpi.cs.wpisuitetng.modules.calendar.view;
 
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.LineBorder;
 
public class DayView extends JPanel {
       /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JPanel mainPanel;
 
       /**
       * Create the panel.
       */
       public DayView() {
    	   
              setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
              
              // HEADER
              JPanel jHeader = new JPanel();
              
              DateFormat ft = new SimpleDateFormat("EEEE, MMMM d, yyyy");
              Date today = new Date();
              JLabel date = new JLabel(ft.format(today));
              date.setAlignmentX(CENTER_ALIGNMENT);
              date.setAlignmentY(CENTER_ALIGNMENT);
              date.setFont(new Font(null, 1, 20));
              jHeader.add(date);
              
              jHeader.setPreferredSize(new Dimension(10000, 250));
              add(jHeader);
              
              
              // HOURS
              JScrollPane scrollPane = new JScrollPane();
              scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
              scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
              add(scrollPane);
              mainPanel = new JPanel();
              scrollPane.setViewportView(mainPanel);
              mainPanel.setPreferredSize(new Dimension(480, 1600));
              GridBagLayout gbl_mainPanel = new GridBagLayout();
              mainPanel.setLayout(gbl_mainPanel);
              for (int i = 0; i < 24; i++) {
                     JPanel hrLabelPanel = new JPanel();
                     hrLabelPanel.setBackground(Color.white);
                     GridBagConstraints gbc_pnlHr = new GridBagConstraints();
                     gbc_pnlHr.fill = GridBagConstraints.BOTH;
                     gbc_pnlHr.weightx = 1;
                     gbc_pnlHr.weighty = 1;
                     gbc_pnlHr.gridx = 0;
                     gbc_pnlHr.gridy = i;
                     mainPanel.add(hrLabelPanel, gbc_pnlHr);
                     String ampm = i + 1 <= 12 ? " AM" : " PM";
                     JLabel lblHrLabel = new JLabel(Integer.toString((i + 11) % 12 + 1)
                                  + ampm);
                     lblHrLabel.setBackground(Color.white);
                     hrLabelPanel.add(lblHrLabel);
                     hrLabelPanel.setBorder(new LineBorder(Color.darkGray, 1, false));
                     JPanel eventPanel = new JPanel();
                     GridBagConstraints gbc_eventPanel = new GridBagConstraints();
                     gbc_eventPanel.fill = GridBagConstraints.BOTH;
                     gbc_eventPanel.weightx = 9;
                     gbc_eventPanel.weighty = 1;
                     gbc_eventPanel.gridx = 1;
                     gbc_eventPanel.gridy = i;
                     mainPanel.add(eventPanel, gbc_eventPanel);
                     eventPanel.setBorder(new LineBorder(Color.darkGray, 1, false));
                     eventPanel.setLayout(new GridLayout(2, 1, 0, 0));
                     JPanel firstHalf = new JPanel();
                     JPanel secondHalf = new JPanel();
                     firstHalf.setBorder(new LineBorder(Color.lightGray, 1, false));
                     firstHalf.setBackground(Color.white);
                     secondHalf.setBorder(new LineBorder(Color.lightGray, 1, false));
                     secondHalf.setBackground(Color.white);
                     eventPanel.add(firstHalf);
                     eventPanel.add(secondHalf);
              }
       }
}