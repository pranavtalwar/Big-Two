/**
 * The BigTwoCard class is a subclass of the Card class, and is used to model a card used in a Big Two card game.
 * It overrides the compareTo() method it inherited from the Card class to reflect the ordering of cards 
 * used in a Big Two card game. 
 * 
 * @author Pranav Talwar
 *
 */
@SuppressWarnings("serial")
public class BigTwoCard extends Card{
	
	/**
	 * Creates an instance of BigTwoCard by calling the constructor(super) of Card class.
	 * 
	 * @param suit
	 *            an int value between 0 and 3 representing the suit of a card:
	 *            <p>
	 *            0 = Diamond, 1 = Club, 2 = Heart, 3 = Spade
	 * @param rank
	 *            an int value between 0 and 12 representing the rank of a card:
	 *            <p>
	 *            0 = 'A', 1 = '2', 2 = '3', ..., 8 = '9', 9 = '10', 10 = 'J', 11
	 *            = 'Q', 12 = 'K'
	 */
	public BigTwoCard(int suit, int rank) {
		super(suit,rank);
	}
	
	/**
	 * Overriding compareTo of card class. Locally makes adjustments to incorporate the change in the rank order for Big Two Game.
	 * Sets rank of A and Two higher than rest of the cards.
	 * 
	 * @param card The card with which user wants to compare. 
	 */
	public int compareTo(Card card) {
		int this_rank = this.rank;
		int card_rank = card.rank;
		
		if(this_rank ==0) 
			this_rank = 13;
		else if(this_rank == 1) 
			this_rank = 14;
		if(card_rank == 0) 
			card_rank = 13;
		else if(card_rank == 1) 
			card_rank = 14;
		
		if(this_rank < card_rank) 
			return -1;
		else if(this_rank > card_rank) 
			return 1;
		else if(this.getSuit()< card.getSuit()) 
			return -1;
		else if(this.getSuit() > card.getSuit()) 
			return 1;
		return 0;
	}

}
