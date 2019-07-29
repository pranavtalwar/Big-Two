/**
 * This class is a subclass of the Hand class, and are used to model a hand of Quad. 
 * It overrides getTopCard,isValid and getType method that it inherits from Hand class.
 * 
 * @author Pranav Talwar
 *
 */
@SuppressWarnings("serial")
public class Quad extends Hand{
	
	/**
	 * Constructor for the Quad type hand. Calls the constructor of Hand super class.
	 * 
	 * @param player Player who plays the hand
	 * @param cards  List of card played by the player
	 */
	public Quad(CardGamePlayer player, CardList cards) {
		super(player, cards);
	}
	
	/* (non-Javadoc)
	 * Returns the top card of the hand
	 * @see Hand#getTopCard()
	 */

	public Card getTopCard() {
		this.sort();
		if(this.getCard(1).getRank() == this.getCard(4).getRank()) {
			return this.getCard(4);
		}
		else if(this.getCard(0).getRank() == this.getCard(3).getRank()) {
			return this.getCard(3);
		}
		return null;
	}
	
	
	/* (non-Javadoc)
	 * Checks whether the hand is a Quad.
	 * 
	 * @see Hand#isValid()
	 */
	public boolean isValid() {
		if(this.size() == 5) {
			this.sort();
			if((this.getCard(0).getRank() == this.getCard(1).getRank()) && (this.getCard(1).getRank() == this.getCard(2).getRank()) && (this.getCard(2).getRank() == this.getCard(3).getRank())) {
				return true;
			}
			else if((this.getCard(1).getRank() == this.getCard(2).getRank()) && (this.getCard(2).getRank() == this.getCard(3).getRank()) && (this.getCard(3).getRank() == this.getCard(4).getRank())) {
				return true;
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
		return "Quad";	
	}
	

}
