package edu.wpi.cs.wpisuitetng.modules.calendar.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Date;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.Event;


public class Event extends AbstractModel implements Comparator<Event>{

        /** the ID of the event */
        private int id; // TODO: move ID stuff to server side?

        /** the name of the event */
        private String name;

        /** the start date of the event */
        private Date startTime;
        
        /** the end date of the event */
        private Date endTime;

        /** a short description of the event */
        private String description;
        
        /** a list of participants of the event */
        private List<String> participants;

        /**
         * Constructs a Event with default characteristics
         */
        public Event() {
                super();
                name = description = "";
                startTime = new Date(0);
                endTime = new Date(0);
                participants = new ArrayList<String>();
        }

        /**
         * Construct a Event with required properties provided and others set
         * to default
         * 
         * @param id
         *            The ID number of the event
         * @param name
         *            The name of the event
         * @param description
         *            A short description of the event
         * @param participants
         *                           An array of names of participants
         */
        // need to phase out supplying the ID
        public Event(int id, String name, String description, Date startTime, Date endTime, String[] people) {
                this();
                this.id = id;
                this.name = name;
                this.description = description;
                this.startTime = startTime;
                this.endTime = endTime;
                Collections.addAll(this.participants, people);
        }


        /**
         * Returns an instance of Event constructed using the given
         * Event encoded as a JSON string.
         * 
         * @param json
         *            JSON-encoded Event to deserialize
        
         * @return the Event contained in the given JSON */
        public static Event fromJson(String json) {
                final Gson parser = new Gson();
                return parser.fromJson(json, Event.class);
        }

        /**
         * /**Getter for the id
         * 
        
         * @return the id */
        public int getId() {
                return id;
        }

        /**
         * Setter for the id
         * 
         * @param id
         *            the id to set
         */
        public void setId(int id) {
                this.id = id;
        }

        /**
         * getter for the name
         * 
        
         * @return the name */
        public String getName() {
                return name;
        }

        /**
         * setter for the name
         * 
         * @param name
         *            the name to set
         */
        public void setName(String n) {
//                if (!n.equals(this.name)) {
//                        String originalName = this.name;
//                        String newName = n;
//                        if (newName.length() > 100)
//                                newName = newName.substring(0, 100);
//                        String message = ("Name changed from " + originalName + " to " + newName);
//                        this.history.add(message);                        
//                }
                this.name = n;
                if (name.length() > 100)
                        this.name = n.substring(0, 100);
        }


        /**
         * Getter for the description
         * 
        
         * @return the description */
        public String getDescription() {
                return description;
        }

        /**
         * Setter for the description
         * 
         * @param description
         *            the description to set
         */
        public void setDescription(String desc) {
//                if (!desc.equals(this.description) && !wasCreated) {
//                        this.history.add("Description changed");                        
//                }
                this.description = desc;
        }



        /**
         * Getter for participants
         * 
        
         * @return the list of strings representing the users for whom the
         *         event has been involved. */
        public List<String> getParticipants() {
                return participants;
        }

        /**
         * Setter for participants
         * 
         * @param participants
         *            the list of strings representing the people who the
         *            event is involved.
         */
        public void setParticipants(List<String> participants) {
                this.participants = participants;
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
        /**This returns a Json encoded String representation of this event object.
         * 
         * @return a Json encoded String representation of this event
         * 
         */
        public String toJSON() {
                return new Gson().toJson(this, Event.class);
        }

        /**
         * Returns an array of Events parsed from the given JSON-encoded
         * string.
         * 
         * @param json
         *            string containing a JSON-encoded array of Event
        
         * @return an array of Event deserialized from the given JSON string */
        public static Event[] fromJsonArray(String json) {
                final Gson parser = new Gson();
                return parser.fromJson(json, Event[].class);
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
         * Copies all of the values from the given event to this event.
         * 
         * @param toCopyFrom
         *            the event to copy from.
         */
        public void copyFrom(Event toCopyFrom) {
                this.description = toCopyFrom.description;
                this.name = toCopyFrom.name;
                this.startTime = toCopyFrom.startTime;
                this.endTime = toCopyFrom.endTime;
        }

        public Date getStartTime() {
                
                return this.startTime;
        }

        public Date getEndTime() {
                
                return this.endTime;
        }

        @Override
        public int compare(Event o1, Event o2) {
                return o1.startTime.compareTo(o2.getStartTime());
        }
        
}