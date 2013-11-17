package edu.wpi.cs.wpisuitetng.modules.calendar.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import edu.wpi.cs.wpisuitetng.modules.calendar.controller.events.AddCategoryController;
//import edu.wpi.cs.wpisuitetng.modules.requirementmanager.controller.AddRequirementController;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.Category;

public class CommitmentList {

	/**
	 * The list in which all the commitment for a single project are contained
	 */
	private List<Commitment> commitments;
	private int nextId;


	/**
	 * Constructs an empty list of categories for the project
	 */
	public CommitmentList (){
		commitments = new ArrayList<Commitment>();
		nextId = 0;
	}

	/**
	 * Adds a single commitment to the commitment of the project
	 * 
	 * @param newReq The commitment to be added to the list of commitments in the project
	 */
	public void addCommitment(Commitment newCommitment){
		int i = 0;
		newCommitment.setId(nextId);
		nextId++;
		if(commitments.size() != 0){
			while (i < commitments.size()){
				if(newCommitment.getDueDate().before(commitments.get(i).getDueDate())){
					break;
				}
				i++;
			}
		}
		// add the commitment
		commitments.add(i, newCommitment);
	}
	/**
	 * Returns the Commitment with the given ID
	 * 
	 * @param id The ID number of the Commitment to be returned

	 * @return the Commitment for the id or null if the commitment is not found */
	public Commitment getCommitment(int id)
	{
		Commitment temp = null;
		// iterate through list of categories until id is found
		for (int i=0; i < this.commitments.size(); i++){
			temp = commitments.get(i);
			if (temp.getId() == id){
				break;
			}
		}
		return temp;
	}
	/**
	 * Removes the Commitment with the given ID
	 * 
	 * @param removeId The ID number of the commitment to be removed from the list of commitments in the project
	 */
	public void removeCommmitment(int removeId){
		// iterate through list of categories until id of project is found
		for (int i=0; i < this.commitments.size(); i++){
			if (commitments.get(i).getId() == removeId){
				// remove the id
				commitments.remove(i);
				break;
			}
		}
	}

	/**
	 * Provides the number of elements in the list of Commitments for the project. 
	 * 
	 * @return the number of commitments in the project * @see javax.swing.ListModel#getSize() * @see javax.swing.ListModel#getSize() * @see javax.swing.ListModel#getSize()
	 */
	public int getSize() {
		return commitments.size();
	}

	/**
	 * 
	 * Provides the next ID number that should be used for a new commitment that is created.
	 * 

	 * @return the next open id number */
	public int getNextID()
	{

		return this.nextId++;
	}

	/**
	 * This function takes an index and finds the commitment in the list of categories
	 * for the project. Used internally by the JList in NewCommitmentList.
	 * 
	 * @param index The index of the commitment to be returned



	 * @return the commitment associated with the provided index * @see javax.swing.ListModel#getElementAt(int) * @see javax.swing.ListModel#getElementAt(int) * @see javax.swing.ListModel#getElementAt(int)
	 */
	public Commitment getElementAt(int index) {
		return commitments.get(commitments.size() - 1 - index);
	}

	/**
	 * Removes all commitments from this model
	 * 
	 * NOTE: One cannot simply construct a new instance of
	 * the model, because other classes in this module have
	 * references to it. Hence, we manually remove each category
	 * from the model.
	 */
	public void removeAll() {
		commitments.removeAll(getCommitments());
	}

	/**
	 * Adds the given array of commitments to the list
	 * 
	 * @param categories the array of commitments to add
	 */
	public void addCommitments(Commitment[] array) {
		int i = 0;
		while(i < array.length){
			commitments.add(array[i]);
		}
	}

	/**
	 * Returns the list of the commitments

	 * @return the commitments held within the CommitmentList. */
	public List<Commitment> getCommitments() {
		return commitments;
	}
	
	/**
	 * Update the commitment list
	 * 
	 * @param the commitment to be update
	 */
	public void update (Commitment newCommitment) {
		commitments.remove(getCommitment(newCommitment.getId()));
		commitments.add(newCommitment);
	}
}
