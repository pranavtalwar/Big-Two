/**
 * This class is a subclass of the Hand class, and are used to model a hand of Pair. 
 * It overrides getTopCard,isValid and getType method that it inherits from Hand class.
 * 
 * @author Pranav Talwar
 *
 */
@SuppressWarnings("serial")
public class Pair extends Hand{
	
	/**
	 * Constructor for the Pair type hand. Calls the constructor of Hand super class.
	 * 
	 * @param player Player who plays the hand
	 * @param cards  List of card played by the player
	 */
	public Pair(CardGamePlayer player, CardList cards) {
		super(player, cards);
	}
	
	/* (non-Javadoc)
	 * Returns the top card of the hand.
	 * 
	 * @see Hand#getTopCard()
	 */
	public Card getTopCard() {
		this.sort();
		return this.getCard(1);
		
	}
	
	/* (non-Javadoc)
	 * Checks whether the hand is a Pair.
	 * 
	 * @see Hand#isValid()
	 */
	public boolean isValid() {
		if(this.size() == 2) {
			if(this.getCard(0).getRank() == this.getCard(1).getRank()) {
				return true;
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
		return "Pair";
	}
	

}
