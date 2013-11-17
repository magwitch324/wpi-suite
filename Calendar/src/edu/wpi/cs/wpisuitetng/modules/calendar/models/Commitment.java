package edu.wpi.cs.wpisuitetng.modules.calendar.models.category;

import java.util.Date;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;

public class Commitment extends AbstractModel {
	// Commitment characteristics.
	private int id;
	private String name;
	private Date startDate;
	private Date endDate;
	private String description;
	private String participants;
	private int categoryId;
	private int repeat;

	//Getters
	public int getId() 				{ return id;}
	public String getName() 		{return name;}
	public Date getStartDate() 		{return startDate;}
	public Date getEndDate() 		{return endDate;}
	public String getDescription() 	{return description;}
	public String getParticipants() {return participants;}
	public int getCategoryId()	 	{return categoryId;}
	public int getRepeat() 			{return repeat;}

	//Setters
	public void setId		   (int id)				{this.id = id;}
	public void setName		   (String name) 		{this.name = name;}
	public void setStartDay	   (Date startDate)	    {this.startDate = startDate;}
	public void setEndDate	   (Date endDate) 		{this.endDate = endDate;}
	public void setDescription (String description) {this.description = description;}
	public void setParticipants(String participants){this.participants = participants;}
	public void setCategoryId  (int categoryId) 	{this.categoryId = categoryId;}
	public void setRepeat      (int repeat) 		{this.repeat = repeat;}

	public Commitment() {
		super();
		name = description = participants = "";
		startDate = new Date(0);
		endDate = new Date(0);
	}
	
	public Commitment(int id, String name, Date startDate, Date endDate,
					String description, String participants, int categoryId, int repeat) {
		this();
		this.id = id;
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
		this.description = description;
		this.participants = participants;
		this.categoryId = categoryId;
		this.repeat = repeat;
	}

	/**
	 * Returns an instance of Commitment constructed using the given
	 * Commitment encoded as a JSON string.
	 * 
	 * @param json
	 *            JSON-encoded Commitment to deserialize
	
	 * @return the Commitment contained in the given JSON */
	public static Commitment fromJson(String json) {
		final Gson parser = new Gson();
		return parser.fromJson(json, Commitment.class);
	}

	/**
	 * Method save.
	 * @see edu.wpi.cs.wpisuitetng.modules.Model#save()
	 */
	@Override
	public void save() {
		// TODO Auto-generated method stub

	}

	/**
	 * Method delete.
	 * @see edu.wpi.cs.wpisuitetng.modules.Model#delete()
	 */
	@Override
	public void delete() {
		// TODO Auto-generated method stub

	}

	/**
	 * Method toJSON.
	
	
	 * @return String * @see edu.wpi.cs.wpisuitetng.modules.Model#toJSON() * @see edu.wpi.cs.wpisuitetng.modules.Model#toJSON()
	 */
	@Override
	/**This returns a Json encoded String representation of this commitment object.
	 * 
	 * @return a Json encoded String representation of this commitment
	 * 
	 */
	public String toJSON() {
		return new Gson().toJson(this, Commitment.class);
	}

	/**
	 * Returns an array of Commitments parsed from the given JSON-encoded
	 * string.
	 * 
	 * @param json
	 *            string containing a JSON-encoded array of Commitment
	
	 * @return an array of Commitment deserialized from the given JSON string */
	public static Commitment[] fromJsonArray(String json) {
		final Gson parser = new Gson();
		return parser.fromJson(json, Commitment[].class);
	}

	/**
	 * Method identify.
	 * @param o Object
	
	
	 * @return Boolean * @see edu.wpi.cs.wpisuitetng.modules.Model#identify(Object) * @see edu.wpi.cs.wpisuitetng.modules.Model#identify(Object)
	 */
	@Override
	public Boolean identify(Object o) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Method toString.
	
	
	 * @return String * @see edu.wpi.cs.wpisuitetng.modules.Model#toString() * @see edu.wpi.cs.wpisuitetng.modules.Model#toString()
	 */
	@Override
	public String toString() {
		return this.getName();
	}


	
	/**
	 * Copies all of the values from the given commitment to this commitment.
	 * 
	 * @param toCopyFrom
	 *            the commitment to copy from.
	 */
	public void copyFrom(Commitment toCopyFrom) {
		this.id = 			toCopyFrom.getId();
		this.name = 		toCopyFrom.getName();
		this.startDate =	toCopyFrom.getStartDate();
		this.endDate = 		toCopyFrom.getEndDate();
		this.description =  toCopyFrom.getDescription();
		this.participants = toCopyFrom.getParticipants();
		this.categoryId = 	toCopyFrom.getCategoryId();
		this.repeat = 		toCopyFrom.getRepeat();

	}
	
}
