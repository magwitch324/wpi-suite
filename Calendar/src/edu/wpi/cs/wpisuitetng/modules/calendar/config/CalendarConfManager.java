/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: CS Anonymous
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.calendar.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class CalendarConfManager {


	/** The file name to use when storing the configuration */
	public static final String configFileName = "./calendar.conf";

	/** The singleton instance of ConfigMgr */
	private static CalendarConfManager instance = null;

	/** The configuration of the client */
	private static CalendarConfiguration config;
	
	private CalendarConfManager() {
		config = new CalendarConfiguration();

	}
	
	/**
	 * Returns the configuration
	 * @return the configuration
	 */
	public static CalendarConfiguration getConfig() {
		getInstance();
		return config;
	}
	
	/**
	 * Returns the singleton instance of ConfigManager
	 * @return the singleton instance of ConfigManager
	 */
	public static CalendarConfManager getInstance() {
		if (instance == null) {
			loadConfig();
		}
		return instance;
	}
	
	/**
	 * Loads the configuration from a file
	 */
	public static void loadConfig() {
		// if instance is null, construct the ConfigMgr
		if (instance == null) {
			instance = new CalendarConfManager();
		}
		
		CalendarConfiguration fileConfig = null;
		// Load the config from the configuration file if it exists
		try {
			FileInputStream fin = new FileInputStream(configFileName);
			ObjectInputStream in = new ObjectInputStream(fin);
			fileConfig = (CalendarConfiguration) in.readObject();
			in.close();
			fin.close();
		}
		catch (FileNotFoundException e) {
			// there is no config file, use the default one
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			System.out.println("An error occurred reading the Calendar config from a file!");
			e.printStackTrace();
		}
		if(fileConfig != null) {
			config = fileConfig;
		}
	}
	
	/**
	 * Writes the configuration to a file
	 */
	public static void writeConfig() {
		// Check that the configuration was constructed before trying to write it
		if (config == null) {
			throw new RuntimeException("Tried to write Calendar config before it was created!");
		}
		
		// Write the configuration
		try {
			FileOutputStream fout = new FileOutputStream(configFileName);
			ObjectOutputStream out = new ObjectOutputStream(fout);
			out.writeObject(config);
			out.close();
			fout.close();
		} catch (FileNotFoundException e) {
			System.out.println("Could not open/create the Calendar config file!");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("An error occurred writing the Calendar config to a file!");
			e.printStackTrace();
		}
		
	}

}
