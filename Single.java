/**
 * This class is a subclass of the Hand class, and are used to model a hand of Single. 
 * It overrides getTopCard,isValid and getType method that it inherits from Hand class.
 * 
 * @author Pranav Talwar
 *
 */
@SuppressWarnings("serial")
public class Single extends Hand{
	
	/**
	 * Constructor for the Single type hand. Calls the constructor of Hand superclass.
	 * 
	 * @param player Player who plays the hand
	 * @param cards  List of card played by the player
	 */	
	public Single(CardGamePlayer player, CardList cards) {
		super(player,cards);
	}
	
	/* (non-Javadoc)
	 * Returns the top card of the hand.
	 * 
	 * @see Hand#getTopCard()
	 */
	public Card getTopCard() {
		return this.getCard(0);
	}
	
	
	/* (non-Javadoc)
	 * Checks whether the hand is a Single.
	 * 
	 * @see Hand#isValid()
	 */
	public boolean isValid() {
		if(this.size()==1) {
			return true;
		}
		return false;
	}
	
	/* (non-Javadoc)
	 * Returns type of string.
	 * 
	 * @see Hand#getType()
	 */
	public String getType() {
		return "Single";
	}
	

}
