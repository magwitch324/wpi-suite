package edu.wpi.cs.loginactivity;

/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: CS Anonymous
 ******************************************************************************/

import java.util.GregorianCalendar;



public class DaySingleton {
	
	private final int PESONAL = 0;
	private final int TEAM = 1;
	private final int BOTH = 2;
	private long selectedDay;
	private String userName;
	private String projectName;
	private int myTeam;
	private String calStatus;
	private long currentDay;
	
	private static final DaySingleton INSTANCE = new DaySingleton();
	
	private DaySingleton() {
		GregorianCalendar g = new GregorianCalendar();
		currentDay = g.getTimeInMillis();
	}
	
	public static DaySingleton getInstance() {
		
		return INSTANCE;
	}
	
	public long getSelectedDay() {
		return selectedDay;
	}
	
	public void setSelectedDay(long selectedDay) {
		this.selectedDay = selectedDay;
	}

	public String getProjectName() {
		return projectName;
	}
	public String getUserName() {
		return userName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setTeam(int myTeam) {
		this.myTeam = myTeam;
	}
	public int myTeam() {
		return myTeam;
	}
	public int getBOTH() {
		return BOTH;
	}
	public int getPESONAL() {
		return PESONAL;
	}
	public int getTEAM() {
		return TEAM;
	}
	
	public String getCalStatus() {
		return calStatus;
	}
	
	public void setCalStatus(String calStatus) {
		this.calStatus = calStatus;
	}
	public long getCurrentDay() {
		return currentDay;
	}
	
}
