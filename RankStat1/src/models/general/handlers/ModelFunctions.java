package models.general.handlers;

import exceptions.NormalException;

/**
 * Simple function interface to implement in all variant models.
 * 
 * @author Andreas Theys.
 * @version 1.0
 */
public interface ModelFunctions {

	/**
	 * Functions for each model to implement.
	 */

	/**
	 * Function to determine home win probability.
	 * 
	 * @return probability measure.
	 * @throws NormalException 
	 */
	public double homeWin() throws NormalException;

	/**
	 * Function to determine draw probability.
	 * 
	 * @return probability measure.
	 */
	public double draw() throws NormalException;

	/**
	 * Function to determine away win probability.
	 * 
	 * @return probability measure.
	 */
	public double awayWin() throws NormalException;
	
	/**
	 * Generates model ID label.
	 * 
	 * @return ID String of the model.
	 */
	public String toString();

}