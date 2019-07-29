/**
 * The Hand class is an abstract subclass of the CardList class, and is used to model a hand of cards. 
 * It has a private instance variable for storing the player who plays this hand. It also has methods 
 * for getting the player of this hand, checking if it is a valid hand, getting the type of this hand, 
 * getting the top card of this hand, and checking if it beats a specified hand. It also has a sort method that
 * it overwrites to make sort the cards in the hand in ascending order. 
 * 
 * 
 * @author Pranav Talwar
 *
 */
@SuppressWarnings("serial")
public class Hand extends CardList implements Interface{
	
	/**
	 * Player who plays the hand.
	 */
	private CardGamePlayer player;
	
	/**
	 * Constructor for Hand class that gives value to player and cards(instance variable of the CardList).
	 * 
	 * @param player Player who played the hand.
	 * @param cards list of card that the player played.
	 */
	public Hand(CardGamePlayer player, CardList cards) {
		this.player = new CardGamePlayer();
		this.player = player;
		for(int i=0;i<cards.size();i++) {
			this.addCard(cards.getCard(i));
		}
	}
	
	/**
	 * Getter function for player who played this hand.
	 * 
	 * @return player of the current hand object.
	 */
	public CardGamePlayer getPlayer() {
		return this.player;
	}
	
	/**
	 * This method returns the top card of the hand.
	 * 
	 * @return returns the top card of the particular hand formed.
	 */
	public Card getTopCard() {
		return null;
	}
	
	/**
	 * This method compares two hands and checks whether this hand beats the hand send through the argument.
	 * 
	 * @param Hand The hand that needs to be compared with this hand.
	 * @return true if this hand beats the hand send as an argument, false otherwise.
	 */
	public boolean beats(Hand hand) {
		
		String[] Hierachy = {"Straight", "Flush","FullHouse","Quad", "StraightFlush"};
		
		if(hand.size() == 1 && this.size() == 1 && this.isValid() && hand.isValid()) {
			if(this.getTopCard().compareTo(hand.getTopCard()) == 1) {
				return true;
			}
		}
		else if(hand.size() == 2 && this.size() ==2 && this.isValid() && hand.isValid()) {
			if(this.getTopCard().compareTo(hand.getTopCard()) == 1) {
				return true;
			}
		}
		else if(hand.size() ==3 && this.size() ==3 && this.isValid() && hand.isValid()) {
			if(this.getTopCard().compareTo(hand.getTopCard()) == 1) {
				return true;
			}
		}
		else if(hand.size() == 5 && this.size()==5) {
				if(this.getType() == hand.getType()) {
					if(this.getTopCard().compareTo(hand.getTopCard())==1) {
						return true;
					}
					else {
						return false;
					}
				}
				else {
					if(this.getType()==Hierachy[4]) {
						return true;
					}
					else if(this.getType()==Hierachy[3]) {
						if(hand.getType()==Hierachy[4]) {
							return false;
						}
						else {
							return true;
						}
					}
					else if(this.getType()==Hierachy[2]) {
						if(hand.getType()==Hierachy[4] || hand.getType()==Hierachy[3]) {
							return false;
						}
						else {
							return true;
						}
					}
					else if(this.getType()==Hierachy[1]) {
						if(hand.getType()==Hierachy[4] || hand.getType()==Hierachy[3] || hand.getType()==Hierachy[2]) {
							return false;
						}
						else {
							return true;
						}
					}
					else if(this.getType()==Hierachy[0]) {
						return false;
					}	
				}
		}
		return false;
	}
	
	/**
	 * Returns true if the hand is a of a valid type and false otherwise
	 * 
	 * @return true if hand is valid, false otherwise
	 */
	public boolean isValid() {
		return false;
	}
	
	/**
	 * Returns the name of the type of hand
	 * 
	 * @returnR the name of the type of hand in string
	 */
	public String getType() {
		return "";
	}

}
