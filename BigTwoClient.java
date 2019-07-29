import java.io.*;
import java.net.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * The BigTwoClient class implements the CardGame interface and NetworkGame interface. 
 * It is used to model a Big Two card game that supports 4 players playing over the internet.
 * 
 * @author Pranav Talwar
 *
 */
public class BigTwoClient implements CardGame, NetworkGame{
	
	/*
	 * An integer specifying the number of players.
	 */
	private int numOfPlayers;
	
	/*
	 * A deck object for the BigTwo game.
	 */
	private Deck deck;
	
	/*
	 * A list of all players in the game 
	 */
	private ArrayList<CardGamePlayer> playerList; 
	
	/*
	 * A list of hands played on the table.
	 */
	private ArrayList<Hand> handsOnTable; 
	
	/*
	 * An integer specifying the playerID (i.e., index) of the local player.
	 */
	private int playerID;
	
	/*
	 * A string specifying the name of the local player.
	 */
	private String playerName;
	
	/*
	 * A string specifying the IP address of the game server.
	 */
	private String serverIP;
	
	/*
	 * An integer specifying the TCP port of the game server.
	 */
	private int serverPort;
	
	/*
	 * A socket connection to the game server.
	 */
	private Socket sock;
	
	/*
	 * An ObjectOutputStream for sending messages to the server.
	 */
	private ObjectOutputStream oos;
	
	/*
	 * An ObjectInputStream for receiving messages from the server.
	 */
	private ObjectInputStream ois;
	
	/*
	 * An int type specifying the index of the active player.
	 */
	private int currentIdx; 
	
	/*
	 * A table object which is provides the necessary GUI.
	 */
	private BigTwoTable table; 
	
	/**
	 *  A constructor for creating the BigTwo card game. 
	 */
	public BigTwoClient()
	{
		playerList = new ArrayList<CardGamePlayer> ();
		for(int i = 0; i < 4;i++)
		{
			CardGamePlayer player = new CardGamePlayer();
			playerList.add(player);
		}
		
		numOfPlayers = playerList.size();
		handsOnTable = new ArrayList<Hand>();
		table = new BigTwoTable(this);
		
		String name = (String) JOptionPane.showInputDialog("Enter your name: ");
		if(name!="" || name!=null) {
			setPlayerName(name);
			
		}
		else {
			setPlayerName("Default_Name");
		}
		setServerIP("127.0.0.1");
		setServerPort(2396);
		makeConnection();
		table.disable();
		table.repaint();
		currentIdx = -1;
	}
	
	@Override
	public int getNumOfPlayers() {
		return numOfPlayers;
	}
	
	@Override
	public Deck getDeck()
	{
		return deck;
	}
	
	@Override
	public ArrayList<CardGamePlayer> getPlayerList()
	{
		return playerList;		
    }
	
	@Override
	public ArrayList<Hand> getHandsOnTable()
	{
		return handsOnTable;
	}
	
	@Override
	public int getCurrentIdx()
	{
		return currentIdx;	
	}
	
	@Override
	public void start(Deck Deck)
	{
		// removing all cards from the table
		handsOnTable.clear();
		
		//removing cards from players hands
		for(int i=0;i<4;i++) {
			playerList.get(i).getCardsInHand().removeAllCards();
		}
		
		// giving cards to players
		int player = 0;
		for(int i = 0; i <52; i++) 
		{
			playerList.get(player%4).addCard(Deck.getCard(i));
			player++;
		}
		
		// sorting cards present in player's hands
		playerList.get(0).getCardsInHand().sort();
		playerList.get(1).getCardsInHand().sort();
		playerList.get(2).getCardsInHand().sort();
		playerList.get(3).getCardsInHand().sort();
		
		//finding the beginner player
		Card beginningCard = new Card(0,2);
		for(int i = 0; i < getNumOfPlayers();i++) 
		{
			if(playerList.get(i).getCardsInHand().contains(beginningCard))
			{
				currentIdx= i;
				break;	
			}
		}
		table.setActivePlayer(getPlayerID());
		table.printMsg("All players are ready. Game starts.");
		table.printMsg(getPlayerList().get(currentIdx).getName() + "'s turn");
		table.repaint();
	}
	
	@Override
	public void makeMove(int playerID, int [] cardIdx)
	{
		CardGameMessage move = new CardGameMessage(CardGameMessage.MOVE, playerID, cardIdx);
		sendMessage(move);
	}
	
	@Override
	public void checkMove(int playerID, int [] cardIdx)
	{
		CardList cardlist = new CardList(); 
		boolean isValid = true; 
		Card beginningCard = new Card(0,2);
		
		if(cardIdx!= null) {
			cardlist = playerList.get(playerID).play(cardIdx);
			Hand hand = composeHand(playerList.get(playerID), cardlist);
			
			if(handsOnTable.isEmpty())
			{
				//checks if beginning move contains 3 of diamonds
				if(hand.contains(beginningCard) && hand.isEmpty()==false && hand.isValid()==true)
					isValid = true; 
				else
					isValid = false;
			}
			else {
				if(handsOnTable.get(handsOnTable.size() - 1).getPlayer() != playerList.get(playerID))
				{
					if(!hand.isEmpty()) {
						isValid = hand.beats(handsOnTable.get(handsOnTable.size() - 1));
					}
					else {
						isValid = false;
					}
				}
				else {
					if(!hand.isEmpty()) {
						isValid = true;
					}
					else {
						isValid= false;
					}
				}	
			}
			if(isValid && hand.isValid()) {
				
				//removing played cards from the players hands
				for(int i=0;i<cardlist.size();i++)
				{
					playerList.get(playerID).getCardsInHand().removeCard(cardlist.getCard(i)); 
				}
				
				table.printMsg("{" + hand.getType() + "} " + hand);
				handsOnTable.add(hand);
				currentIdx = (currentIdx + 1) % 4;
				table.setActivePlayer(currentIdx);
				table.printMsg(getPlayerList().get(currentIdx).getName()+ "'s turn");
			}
			else
			{
				table.printMsg(cardlist +" <= Not a legal move!!!");
			}
		}
		else {
			
			// handles passing by a player
			if(!handsOnTable.isEmpty() && handsOnTable.get(handsOnTable.size()-1).getPlayer() != playerList.get(playerID)) {
				currentIdx = (currentIdx+1)%4;
				table.setActivePlayer(currentIdx);
				table.printMsg("{Pass}");
				table.printMsg(getPlayerList().get(currentIdx).getName() + "'s turn");
				isValid = true;
			}
			else {
				table.printMsg("Not a legal move!!!");
				isValid= false;
			}
		}
		
		table.repaint();
		
		// checking for end of game
		if(endOfGame()) {
			
			table.setActivePlayer(-1);
			table.repaint();
			String message = "Game ends.\n";
			
			//check who wins and how many cards the other players have
			for(int i = 0; i < playerList.size();i++)
			{
					if(playerList.get(i).getCardsInHand().size() == 0)
					{
						message+="Player " + i + " wins the game.\n"; 
					}
				
				else
				{
					message+="Player " + i + " has " + playerList.get(i).getCardsInHand().size() + " cards in hand.\n"; // list the number of cards left in the other players' hand
				}
			}
			table.disable();
			for (int i=0; i<4; ++i)
	        {
	          getPlayerList().get(i).removeAllCards();
	        }
			JOptionPane.showMessageDialog(null, message);
			CardGameMessage ready = new CardGameMessage(CardGameMessage.READY,-1,null);
			sendMessage(ready);
		}
	}
	
	@Override
	public boolean endOfGame() {
		for(int i=0;i<playerList.size();i++)
		{
			if(playerList.get(i).getNumOfCards() == 0) {
				
				return true;
			}
		}
		return false;
	}
		
	@Override
	public int getPlayerID() {
		return playerID;
	}
	
	@Override
	public void setPlayerID(int playerID) {
		this.playerID = playerID;
	}
	
	@Override
	public String getPlayerName() {
		return playerName;
	}
	
	@Override
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	
	@Override
	public String getServerIP() {
		return serverIP;
	}
	
	@Override
	public void setServerIP(String serverIP) {
		this.serverIP = serverIP;
	}
	
	@Override
	public int getServerPort() {
		return serverPort;
	}
	
	@Override
	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}
	
	/**
	 * A function which tells if the client is connected to the server or not.
	 * 
	 * @return boolean true if client is connected to server, otherwise false
	 */
	public boolean isConnected() {
		if(sock.isClosed()) {
			return false;
		}
		return true;
	}
	
	@Override
	public void makeConnection() {
		try {
			sock = new Socket(getServerIP(), getServerPort());
			oos = new ObjectOutputStream(sock.getOutputStream());
			Runnable job = new ServerHandler();
			Thread thread = new Thread(job);
			thread.start();
			
			CardGameMessage joinGame = new CardGameMessage(CardGameMessage.JOIN, -1, this.getPlayerName());
			sendMessage(joinGame);
			
			CardGameMessage readyGame = new CardGameMessage(CardGameMessage.READY, -1, null);
			sendMessage(readyGame);
			
			table.repaint();
					
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}	
	}

	@Override
	public void parseMessage(GameMessage message) {
		if(message.getType()==CardGameMessage.FULL) {
			table.printMsg("The server is full and cannot be joined!");
			try {
				sock.close();
			}
			catch(IOException e) {
				e.printStackTrace();
			}
		}
		else if(message.getType()==CardGameMessage.JOIN) {
			getPlayerList().get(message.getPlayerID()).setName((String)message.getData());
			table.repaint();
			table.printMsg("Player " + playerList.get(message.getPlayerID()).getName() + " joined the game!");
		}
		
		else if(message.getType()==CardGameMessage.MOVE) {
			checkMove(message.getPlayerID(),(int[])message.getData());
			table.repaint();
		}
		else if(message.getType()==CardGameMessage.MSG) {
			table.printChatMsg((String)message.getData());
		}
		else if(message.getType()==CardGameMessage.PLAYER_LIST) {
			setPlayerID(message.getPlayerID());
			table.setActivePlayer(message.getPlayerID());
			for(int i=0;i<getNumOfPlayers();i++)	
			{
				if(((String[])message.getData())[i]!=null) {
					getPlayerList().get(i).setName(((String[])message.getData())[i]);
				}
			}
		}
		else if(message.getType()==CardGameMessage.QUIT) {
			table.printMsg("Player " + message.getPlayerID() + " " + playerList.get(message.getPlayerID()).getName() + " left the game.");
			getPlayerList().get(message.getPlayerID()).setName("");
			if(!endOfGame()) {
				table.disable();
				CardGameMessage msg = new CardGameMessage(CardGameMessage.READY, -1, null);
				sendMessage(msg);
				for(int i=0;i<4;i++) {
					getPlayerList().get(i).removeAllCards();
				}
				table.repaint();
			}
			table.repaint();
		}
		else if(message.getType()==CardGameMessage.READY) {
			table.printMsg("Player " + message.getPlayerID() + " is ready.");
		}
		else if(message.getType()==CardGameMessage.START) {
			deck = (BigTwoDeck) message.getData();
			start(deck);
			table.enable();
			table.repaint();
		}
	}
	
	@Override
	public void sendMessage(GameMessage message) {
		try {
			oos.writeObject(message);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * An inner class that implements the Runnable interface.
	 * 
	 * @author Pranav Talwar
	 *
	 */
	class ServerHandler implements Runnable{

		@Override
		public void run() {	
			CardGameMessage message = null;
			try
			{
				ois = new ObjectInputStream(sock.getInputStream());
				while(!sock.isClosed()) {
					if((message = (CardGameMessage) ois.readObject()) != null)
					{
						parseMessage(message);
					}	
				}
				
				ois.close();
			} 
			
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			
			table.repaint();
		}
		
	}
	
	/**
	 * A method to return a valid hand from all the list of cards played by the player. 
	 * 
	 * @param player A CardGamePlayer object which contains the list of players in the game.
	 * @param cards A CardList object which contains list of cards played by the active player.
	 * 
	 * @return the type of hand 
	 */
	public static Hand composeHand(CardGamePlayer player, CardList cards)
	{
		StraightFlush straightflush = new StraightFlush(player,cards); 
		if(straightflush.isValid())
		{
			return straightflush; 
		}
		Quad quad = new Quad(player,cards); 
		if(quad.isValid())
		{
			return quad; 
		}
		FullHouse fullhouse = new FullHouse(player,cards); 
		if(fullhouse.isValid())
		{
			return fullhouse; 
		}
		Flush flush = new Flush(player,cards); 
		if(flush.isValid())
		{
			return flush; 
		}
		Straight straight = new Straight(player,cards); 
		if(straight.isValid())
		{	
			return straight; 
		}
		Triple triple = new Triple(player,cards); 
		if(triple.isValid())
		{
			return triple; 
		}
		Pair pair = new Pair(player,cards); 
		if(pair.isValid())
		{
			return pair; 
		}
		Single single = new Single(player,cards); 
		if(single.isValid())
		{
			return single; 
		}	
		return new Hand(player, cards); 
	}
 
	/**
	 * A method for creating an instance of BigTwoClient.
	 * 
	 * @param args unused
	 */
	public static void main(String [] args)
	{
		BigTwoClient game = new BigTwoClient();
	}

	
}
