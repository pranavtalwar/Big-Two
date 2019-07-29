/**
 * This class is a subclass of the Hand class, and are used to model a hand of Triple. 
 * It overrides getTopCard,isValid and getType method that it inherits from Hand class.
 * 
 * @author Pranav Talwar
 *
 */

@SuppressWarnings("serial")
public class Triple extends Hand{
	
	/**
	 * Constructor for the Triple type hand. Calls the constructor of Hand super class.
	 * 
	 * @param player Player who plays the hand
	 * @param cards  List of card played by the player
	 */
	public Triple(CardGamePlayer player, CardList cards) {
		super(player,cards);
	}
	
	/* (non-Javadoc)
	 * Returns the top card of the hand.
	 * 
	 * @see Hand#getTopCard()
	 */
	public Card getTopCard() {
		this.sort();
		return this.getCard(2);
	}
	
	/* (non-Javadoc)
	 * Checks whether the hand is a Triple.
	 * 
	 * @see Hand#isValid()
	 */
	public boolean isValid() {
		if(this.size() == 3) {
			if(this.getCard(0).getRank() == this.getCard(1).getRank()) {
				if(this.getCard(1).getRank() == this.getCard(2).getRank()) {
					return true;
				}
				else {
					return false;
				}	
			}
			else {
				return false;
			}
		}
		return false;
	}
	
	/* (non-Javadoc)
	 * Returns type of string.
	 * 
	 * @see Hand#getType()
	 */
	public String getType() {
		return "Triple";	
	}
}
