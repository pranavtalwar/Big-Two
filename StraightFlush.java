import java.util.Arrays;
/**
 * This class is a subclass of the Hand class, and are used to model a hand of Straight Flush. 
 * It overrides getTopCard,isValid and getType method that it inherits from Hand class.
 * 
 * @author Pranav Talwar
 *
 */
@SuppressWarnings("serial")
public class StraightFlush extends Hand{
	
	/**
	 * Constructor for the StraightFlush type hand. Calls the constructor of Hand superclass.
	 * 
	 * @param player Player who plays the hand
	 * @param cards  List of card played by the player
	 */
	public StraightFlush(CardGamePlayer player, CardList cards) {
		super(player, cards);
	}
	
	/* (non-Javadoc)
	 * Returns the top card of the hand.
	 * 
	 * @see Hand#getTopCard()
	 */
	public Card getTopCard() {
		int[] handranks = new int[5];
		for(int i=0;i<5;i++) {
			if(this.getCard(i).getRank()==0) {
				handranks[i] = 13;
			}
			else if(this.getCard(i).getRank()==1) {
				handranks[i] = 14;
			}
			else {
				handranks[i] = this.getCard(i).getRank();
			}
		}
		Arrays.sort(handranks);
		if(handranks[4]>=13) {
			handranks[4]-=13;
		}
		int returnIndex = 0;
		for(int i=1;i<5;i++) {
			if(this.getCard(i).getRank() == handranks[4]) {
				returnIndex = i;
			}
		}
		return this.getCard(returnIndex);
	}
	
	/* (non-Javadoc)
	 * Checks whether the hand is a Straight Flush.
	 * 
	 * @see Hand#isValid()
	 */
	public boolean isValid() {
		if(this.size() == 5) {
			if((this.getCard(0).getSuit() == this.getCard(1).getSuit()) && (this.getCard(1).getSuit() == this.getCard(2).getSuit()) && (this.getCard(2).getSuit() == this.getCard(3).getSuit()) && (this.getCard(3).getSuit() == this.getCard(4).getSuit())) {
				int[] handranks = {this.getCard(0).getRank(),this.getCard(1).getRank(),this.getCard(2).getRank(),this.getCard(3).getRank(),this.getCard(4).getRank()};
				for(int i =0;i<handranks.length; i++) {
					if(handranks[i] == 0) {
						handranks[i] = 13;
					}
					else if(handranks[i]==1) {
						handranks[i] = 14;
					}
				}
				Arrays.sort(handranks);
				int counter = handranks[0];
				boolean flag = true;
				for(int i=1;i<handranks.length;i++) {
					if(handranks[i] == (counter+1)) {
						counter = handranks[i];
					}
					else {
						flag = false;
						break;
					}
				}
				return flag;
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
		return "StraightFlush";	
	}

}
