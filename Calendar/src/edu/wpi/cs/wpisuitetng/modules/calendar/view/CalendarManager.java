package edu.wpi.cs.wpisuitetng.modules.calendar.view;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.calendar.CalendarException;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.CalendarData;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.CalendarDataModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.Commitment;

public class CalendarManager {

	CalendarData calData;
	
	private CalendarManager() {
		
	}
	
	public CalendarManager getInstance() {
		update();
		return this;
	}
	
	public void update() {
		if (CalendarDataModel.getInstance().getCalendarData(
				ConfigManager.getConfig().getProjectName()) == null) {
			CalendarData createdCal = new CalendarData(ConfigManager
					.getConfig().getProjectName());
			CalendarDataModel.getInstance().addCalendarData(createdCal);
		}
		calData = CalendarDataModel.getInstance().getCalendarData(
				ConfigManager.getConfig().getProjectName());
		
	}
	
	/**
	 * Filter the calendar data to data between 
	 * the start and end Calendars
	 * This is INCLUSIVE for both start and end
	 * @param start
	 * @param end
	 * @return
	 */
	public CalendarData filter(Calendar start, Calendar end) {

		Calendar commitDate = Calendar.getInstance();
		
		// copy calData and clear commitment list
		CalendarData newCal = calData;
		newCal.getCommitments().removeAll();
		
		// get the list of commitments
		List<Commitment> commits;
		commits = calData.getCommitments().getCommitments();
		
		// move start and end to make the function inclusive
		start.add(Calendar.DAY_OF_MONTH, -1);
		end.add(Calendar.DAY_OF_MONTH, 1);
		
		// iterate and add all commitments between start and end
		// to the newCal
		for (Commitment commit : commits) {
			commitDate.setTime(commit.getDueDate());
			if (commitDate.after(start) && commitDate.before(end)) {
				newCal.addCommitment(commit);
			} 
		}
		
		return newCal;
	}
	
	/**
	 * Filters the calendar data to the "amount"
	 * around the given date
	 * ex: 11/23/2013, Calendar.MONTH -> All of November
	 * @param date
	 * @param amount
	 * @return
	 * @throws CalendarException 
	 */
	public CalendarData filter(Calendar date, int amount) throws CalendarException {
		Calendar start = (Calendar) date.clone();
		Calendar end   = (Calendar) start.clone();
		
		
		/* All methods here add the given amount, then roll back
		 * one day to specify range by the first and last day 
		 * within that range
		 */
		if (amount == Calendar.DAY_OF_MONTH || amount == Calendar.DAY_OF_WEEK || amount == Calendar.DAY_OF_YEAR) {
			// Do nothing. This allows error checking at end
		}
		else if (amount == Calendar.WEEK_OF_MONTH || amount == Calendar.WEEK_OF_YEAR) {
			start.set(Calendar.DAY_OF_WEEK, start.getFirstDayOfWeek());
			end = (Calendar) start.clone();
			end.add(Calendar.WEEK_OF_YEAR, 1);
			end.add(Calendar.DAY_OF_YEAR, -1);
		}
		else if (amount == Calendar.MONTH) {
			start.set(Calendar.DAY_OF_MONTH, 1);
			end = (Calendar) start.clone();
			end.add(Calendar.MONTH, 1);
			end.add(Calendar.DAY_OF_YEAR, -1);
		}
		else if (amount == Calendar.YEAR) {
			start.set(Calendar.DAY_OF_YEAR, 1);
			end = (Calendar) start.clone();
			end.add(Calendar.YEAR, 1);
			end.add(Calendar.DAY_OF_YEAR, -1);
		} else {
			throw new CalendarException("Invalid amount! Can only filter around day, week, month, and year types");
		}
		
		return filter(start, end);
	}
	
}
