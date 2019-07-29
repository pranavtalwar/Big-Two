/** 
 * The BigTwoDeck class is a subclass of the Deck class, and is used to model a deck of cards used in a Big Two card game. 
 * It overrides the initialize() method it inherited from the Deck class to create a deck of Big Two cards.
 *  
 * @author Pranav Talwar
 *
 */
@SuppressWarnings("serial")
public class BigTwoDeck extends Deck{
	
	/**
	 * Initialize the deck of cards(big two cards) for big two game .
	 */
	public void initialize() {
		removeAllCards();
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 13; j++) {
				BigTwoCard card = new BigTwoCard(i, j);
				addCard(card);
			}
		}
	}

}
