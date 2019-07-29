
/**
 * This public is a abstract interface for two instance methods namely, isValid() and getType().
 * 
 * @author Pranav Talwar
 *
 */
public interface Interface {
	
	/**
	 * A method to check if the concerned hand is a valid hand or not
	 * @return true or false if the hand is of the type you are searching for
	 */
	
	public abstract boolean isValid();
	
	/**
	 * A method to return the type of Hand 
	 * @return a string which states type of hand. Return "Flush" for Flush object, "StraightFlush" for StraightFlush object
	 */
	
	public abstract String getType();

}