/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: CS Anonymous
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.calendar.datatypes;

import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.LONG;
import static java.util.Calendar.YEAR;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.ArrayList;
import java.util.Locale;

import edu.wpi.cs.wpisuitetng.modules.calendar.CalendarException;


/**
 * Abstract list class that is currently used to
 * create commitmentlist, combined commitmentlist,
 * repeatingeventlist,
 * eventlist, and combined eventlist.
 * @author CS Anonymous
 * @version $Revision: 1.0 $
 */
public abstract class CalendarObjectList<T extends CalendarObject> {

	protected List<T> calendarObjects;
	protected int nextID;

	/**
	 * Constructor
	 */
	protected CalendarObjectList() {
		calendarObjects = new ArrayList<T>();
		nextID = 0;
	}

	/**
	 * Method add.
	 * @param newObject T
	 */
	public abstract void add(T newObject);

	/**
	 * Returns the CalendarObject with the given ID
	 * 
	 * @param id
	 *            The ID number of the CalendarObject to be returned
	 * 
	
	 * @return the CalendarObject for the id or null if the CalendarObject is
	 *         not found */
	public T get(int id) {
		T temp = null;
		// iterate through list of CalendarObjects until id is found
		for (int i = 0; i < calendarObjects.size(); i++) {
			temp = calendarObjects.get(i);
			if (temp.getID() == id) {
				break;
			}
		}
		return temp;
	}

	/**
	 * Removes the CalendarObject with the given ID
	 * 
	 * @param removeId
	 *            The ID number of the CalendarObject to be removed from the
	 *            list of CalendarObjects in the project
	 */
	public void remove(int removeId) {
		// iterate through list of CalendarObjects until id of project is found
		for (int i = 0; i < calendarObjects.size(); i++) {
			if (calendarObjects.get(i).getID() == removeId) {
				// remove the id
				calendarObjects.remove(i);
				break;
			}
		}
	}

	/**
	 * Provides the number of elements in the list of CalendarObjects for the
	 * project.
	 * 
	 * @return the number of CalendarObjects in the project
	 */
	public int getSize() {
		return calendarObjects.size();
	}

	/**
	 * 
	 * Provides the next ID number that should be used for a new CalendarObject that is
	 * created.
	 * 
	 * @return the next open id number
	 */
	public int getNextID() {
		return nextID++;
	}
	
	/**
	 * This function takes an index and finds the CalnedarObject in the list of
	 * categories for the project. 
	 * 
	 * @param index
	 *            The index of the CalnedarObject to be returned
	 *  
	
	 * @return the CalnedarObject associated with the provided index  */
	public T getElementAt(int index) {
		return calendarObjects.get(calendarObjects.size() - 1 - index);
	}
	
	/**
	 * Removes all CalendarObjects from this model
	 * 
	 * NOTE: One cannot simply construct a new instance of the model, because
	 * other classes in this module have references to it. Hence, we manually
	 * remove each CalendarObjects from the model.
	 */
	public void removeAll() {
		calendarObjects.removeAll(calendarObjects);
	}

	/**
	 * Returns the list of the CalendarObjects
	 * 
	 * @return the calendarObjects held within the calendarObjects list.
	 */
	public List<T> getCalendarObjects() {
		return calendarObjects;
	}
	
	/**
	 * Adds the given array of CalendarObjects to the list
	 * 
	 * @param array
	 *            the array of CalendarObjects to add
	 */
	public void addCalendarObjects(T[] array) {
		int i = 0;
		while (i < array.length) {
			calendarObjects.add(array[i]);
			i++;
		}
	}
	
	/**
	 * Update the calendarObject list
	 * 
	
	 * @param newObject T
	 */
	abstract void update(T newObject);
	
	/**
	 * Filter the calendarObject list to data on a specific date
	 * 
	 * @param date
	
	
	 * @return ArrayList of calendarObject on date * @throws CalendarException * @throws CalendarException
	 */
	public List<T> filter(GregorianCalendar date) throws CalendarException {

		return filter(date, Calendar.DAY_OF_MONTH);

	}
	
	/**
	 * Filter the calendarObjects list to data between the start and end Calendars
	 * This is INCLUSIVE for both start and end
	 * 
	 * @param start
	 * @param end
	
	
	 * @return List<T> */
	public abstract List<T> filter(GregorianCalendar start,
			GregorianCalendar end);

	
	/**
	 * Filters the calendar data to the "amount" around the given date ex:
	 * 11/23/2013, Calendar.MONTH -> All of November
	 * 
	 * @param date
	 * @param amount
	
	
	
	 * @return List<T> * @throws CalendarException */
	public List<T> filter(GregorianCalendar date, int amount)
			throws CalendarException {
		final GregorianCalendar start = new GregorianCalendar();
		start.setTime(date.getTime());
		start.set(Calendar.HOUR_OF_DAY, 0);
		start.set(Calendar.MINUTE, 0);
		start.set(Calendar.SECOND, 0);

		final GregorianCalendar end = new GregorianCalendar();

		/*
		 * All methods here add the given amount, then roll back one day to
		 * specify range by the first and last day within that range
		 */
		if (amount == Calendar.DAY_OF_MONTH || amount == Calendar.DAY_OF_WEEK
				|| amount == Calendar.DAY_OF_YEAR) {
			end.setTime(start.getTime());
			end.set(Calendar.HOUR_OF_DAY, 23);
			end.set(Calendar.MINUTE, 59);
			end.set(Calendar.SECOND, 59);
		} else if (amount == Calendar.WEEK_OF_MONTH
				|| amount == Calendar.WEEK_OF_YEAR) {
			start.set(Calendar.DAY_OF_WEEK, start.getFirstDayOfWeek());
			end.setTime(start.getTime());
			end.set(Calendar.HOUR_OF_DAY, 23);
			end.set(Calendar.MINUTE, 59);
			end.set(Calendar.SECOND, 59);
			end.add(Calendar.WEEK_OF_YEAR, 1);
			end.add(Calendar.DAY_OF_YEAR, -1);

		} else if (amount == Calendar.MONTH) {
			start.set(Calendar.DATE, 1);
			end.setTime(start.getTime());
			end.set(Calendar.HOUR_OF_DAY, 23);
			end.set(Calendar.MINUTE, 59);
			end.set(Calendar.SECOND, 59);
			end.add(Calendar.MONTH, 1);
			end.add(Calendar.DAY_OF_YEAR, -1);
		} else if (amount == Calendar.YEAR) {
			start.set(Calendar.DAY_OF_YEAR, 1);
			end.setTime(start.getTime());
			end.set(Calendar.HOUR_OF_DAY, 23);
			end.set(Calendar.MINUTE, 59);
			end.set(Calendar.SECOND, 59);
			end.add(Calendar.YEAR, 1);
			end.add(Calendar.DAY_OF_YEAR, -1);
		} else {
			throw new CalendarException(
					"Invalid amount! Can only filter around day, week, month, and year types");
		}


		return filter(start, end);
	}

	/**
	 * Method printcalendar.
	 * @param cal GregorianCalendar
	 */
	public void printcalendar(GregorianCalendar cal) {
		final String dayName = cal.getDisplayName(GregorianCalendar.DAY_OF_WEEK,
				LONG, Locale.ENGLISH);
		final int dayNum = cal.get(DAY_OF_MONTH);
		final String monthName = cal.getDisplayName(GregorianCalendar.MONTH, LONG,
				Locale.ENGLISH);
		final int year = cal.get(YEAR);
		System.out.println(dayName + ", " + monthName + " " + dayNum + ", "
				+ year);
	}
}
