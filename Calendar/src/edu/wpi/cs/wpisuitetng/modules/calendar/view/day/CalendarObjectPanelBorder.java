package edu.wpi.cs.wpisuitetng.modules.calendar.view.day;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Area;
import java.awt.geom.RoundRectangle2D;

import javax.swing.border.AbstractBorder;
import javax.swing.border.Border;

@SuppressWarnings("serial")
public class CalendarObjectPanelBorder extends AbstractBorder{
	int left = 5;
	int top = 1;
	
	public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
		final Color foreground_color = c.getForeground();
		final Color background_color = c.getBackground();
		final Color transparent = new Color(0, 0, 0, 0);
		final Graphics2D g2 = (Graphics2D) g;
		
		if(width > 100){
			top = 1;
			left = 10;
		}
		else if(width > 30){
			top = 1;
			left = 7;
		}
		else{
			top = 1;
			left = 5;
		}
		
		g2.setRenderingHints(new RenderingHints( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));
		
		g2.setColor(transparent);
		g2.drawRect(x, y, width, height);
		
		g2.setColor(Color.BLACK);
		g2.fillRoundRect(x, y, width, height, 0, 0);
		
		g2.setColor(foreground_color);
		g2.fillRoundRect(x + 1, y + 1, width - 2, height - 2, 0, 0);

		g2.setColor(background_color);
		g2.fillRoundRect(x + left, y + top, width - (left + 1), height - (top + 1), 0, 0);
	}
	
	public Insets getBorderInsets(Component c){
		return new Insets(1, 1, 1, 1);
	}

}
