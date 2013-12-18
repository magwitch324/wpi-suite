package edu.wpi.cs.wpisuitetng.modules.calendar.view.month;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;

import javax.swing.border.AbstractBorder;

@SuppressWarnings("serial")
public class CalendarObjectWrapperBorder extends AbstractBorder{
	int left = 5;
	Color foreground;
	Color background;
	
	
	public CalendarObjectWrapperBorder(Color fore, Color back){
		foreground = fore;
		background = back;
	}
	
	public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
		final Color foreground_color = foreground;
		final Color background_color = background;
		final Graphics2D g2 = (Graphics2D) g;
		
		if(width > 100){
			left = 7;
		}
		else if(width > 40){
			left = 5;
		}
		else{
			left = 3;
		}
		
		g2.setRenderingHints(new RenderingHints( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));
		
		g2.setColor(foreground_color);
		g2.fillRoundRect(x, y, width, height, 0, 0);

		g2.setColor(background_color);
		g2.fillRoundRect(x + left, y, width - (left), height, 0, 0);
	}
	
	public Insets getBorderInsets(Component c){
		return new Insets(0, 7, 0, 0);
	}

}
