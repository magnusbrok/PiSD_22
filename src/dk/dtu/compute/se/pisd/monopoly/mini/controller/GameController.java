package dk.dtu.compute.se.pisd.monopoly.mini.controller;

import dk.dtu.compute.se.pisd.monopoly.mini.DAL.GameDAO;
import dk.dtu.compute.se.pisd.monopoly.mini.model.*;
import dk.dtu.compute.se.pisd.monopoly.mini.model.exceptions.DALException;
import dk.dtu.compute.se.pisd.monopoly.mini.model.exceptions.GameEndedException;
import dk.dtu.compute.se.pisd.monopoly.mini.model.exceptions.PlayerBrokeException;
import dk.dtu.compute.se.pisd.monopoly.mini.model.properties.Ferry;
import dk.dtu.compute.se.pisd.monopoly.mini.model.properties.RealEstate;
import dk.dtu.compute.se.pisd.monopoly.mini.view.View;
import gui_main.GUI;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The overall controller of a Monopoly game. It provides access
 * to all basic actions and activities for the game. All other
 * activities of the game, should be implemented by referring
 * to the basic actions and activities in this class.
 *
 * Note that this controller is far from being finished and many
 * things could be done in a much nicer and cleaner way! But, it
 * shows the general idea of how the model, view (GUI), and the
 * controller could work with each other, and how different parts
 * of the game's activities can be separated from each other, so
 * that different parts can be added and extended independently
 * from each other.
 *
 * For fully implementing the game, it will probably be necessary
 * to add more of these basic actions in this class.
 *
 * The <code>doAction()</code> methods of the
 * {@link dk.dtu.compute.se.pisd.monopoly.mini.model.Space} and
 * the {@link dk.dtu.compute.se.pisd.monopoly.mini.model.Card}
 * can be implemented based on the basic actions and activities
 * of this game controller.
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 */
public class GameController {

	private Game game;

	private GUI gui;

	private View view;

	private GameDAO gameDAO = new GameDAO();

	private boolean disposed = false;



	/**
	 * Constructor for a controller of a game.
	 *
	 * @param game the game
	 */
	public GameController(Game game) {
		super();
		this.game = game;

		gui = new GUI();
	}

	/**
	 * This method will initialize the GUI. It should be called after
	 * the players of the game are created. As of now, the initialization
	 * assumes that the spaces of the game fit to the fields of the GUI;
	 * this could eventually be changed, by creating the GUI fields
	 * based on the underlying game's spaces (fields).
	 */
	public void initializeGUI() {
		this.view = new View(game, gui);
	}

	/**
	 * method called in main to start creating a game. Ask through the GUI if the player wants
	 * to make a new game or load one. Either calls loadGame method or makePlayers method.
	 */
	public void makeGame () {

		String selection = gui.getUserSelection("Welcome to MiniMonopoly! Would you like to start af new game or continue a previous game?",
				"DEFAULT GAME", "Start new game", "Load game");
		if (selection == "Load game"){
			try{
				List<Integer> gameIDs = gameDAO.getGameIds();
				int gameID = gui.getUserInteger("Følgende spil er gemte \n "+gameIDs.toString() +
						"\n Please enter game ID" +
						"\n Hvis du hellere vil starte et nyt spil, så indtast et tal der ikke er fra listen");
				while (!gameDAO.getGameIds().contains(gameID)) {
					gameID = gui.getUserInteger("You entered a gameID that doesn't exist. Please... Try again" +
					"\n Følgende spil er gemte: \n" + gameIDs.toString());
				}
				game.setGameID(gameID);
				gameDAO.loadGame(game);

				for (Space space : game.getSpaces()){
					if (space instanceof RealEstate) {
						RealEstate property = (RealEstate) space;
						//view.update(property);
					}
				}
			}catch (DALException e){
				e.printStackTrace();
			}
		}
		if (selection == "Start new game"){
			makePlayers();
		}
		if (selection == "DEFAULT GAME"){
// Til test af spillet hvor man ikke gidder lave spillere osv.
			Player p = new Player();
			p.setPlayerID(1);
			p.setName("Player 1");
			p.setCurrentPosition(game.getSpaces().get(0));
			p.setColor(new Color(255, 82, 62));
			game.addPlayer(p);

			p = new Player();
			p.setPlayerID(2);
			p.setName("Player 2");
			p.setCurrentPosition(game.getSpaces().get(0));
			p.setColor(new Color(255, 211, 27));
			game.addPlayer(p);

			p = new Player();
			p.setPlayerID(3);
			p.setName("Player 3");
			p.setCurrentPosition(game.getSpaces().get(0));
			p.setColor(new Color(40, 147, 30));
			game.addPlayer(p);

		}
	}

	/**
	 * The basic method for making each player in the game. Only happens if the player wants to start a fresh game.
	 * Ask how many are playing with the min and max number of players. Then creates each player with defualt ID's and colors
	 * Name is collected via the GUI.
	 */
	public void makePlayers() {
		int minPlayers = 3;
		int maxPlayers = 6;
		int totalPlayers = gui.getUserInteger("How many are playing? Please enter a number between " + minPlayers + " and " + maxPlayers);
		while (totalPlayers < minPlayers || totalPlayers > maxPlayers) {
			totalPlayers = gui.getUserInteger("You entered a number of players outside the accepted interval. Please... Try again");
		}
		List<Color> pColor = game.getColors();

		for (int i = 1; i <= totalPlayers; i++) {
			Player p = new Player();
			p.setPlayerID(i);
			String playerName = gui.getUserString("Hvilket navn skal player "+ i + " have?");
			p.setName(playerName);
			p.setCurrentPosition(game.getSpaces().get(0));
			p.setColor(pColor.get(i - 1));
			game.addPlayer(p);
		}
	}

	/**
	 * The main method to start the game. The game is started with the
	 * current player of the game; this makes it possible to resume a
	 * game at any point.
	 */
	public void play() {
		List<Player> players = game.getPlayers();
		Player c = game.getCurrentPlayer();

		int current = 0;
		for (int i = 0; i < players.size(); i++) {
			Player p = players.get(i);
			if (c.equals(p)) {
				current = i;
			}
		}

		boolean terminated = false;
		while (!terminated) {
			Player player = players.get(current);
			if (!player.isBroke()) {
				try {
					this.makeMove(player);
				} catch (PlayerBrokeException e) {
					// We could react to the player having gone broke
				} catch (GameEndedException e){
					gui.showMessage(e.getMessage());
					terminated = true;
				}
			}
			//TODO INSERT "buyQuestion" method here instead. (Asks everyone instead of just one player). /TMJ

			// Check whether we have a winner
			Player winner = null;
			int countActive = 0;
			for (Player p : players) {
				if (!p.isBroke()) {
					countActive++;
					winner = p;
				}
			}
			if (countActive == 1) {
				gui.showMessage(
						"Player " + winner.getName() +
								" has won with " + winner.getBalance() + "$.");
				break;
			} else if (countActive < 1) {
				// This can actually happen in very rare conditions and only
				// if the last player makes a stupid mistake (like buying something
				// in an auction in the same round when the last but one player went
				// bankrupt)
				gui.showMessage(
						"All players are broke.");
				break;
			}
			// TODO offer all players the options to trade etc.


		current = (current + 1) % players.size();
		game.setCurrentPlayer(players.get(current));
		if (current == 0) {
			String selection = gui.getUserSelection(
					"A round is finished. Do you want to continue the game?",
					"yes",
					"no");
			if (selection.equals("no")) {
				selection = gui.getUserSelection("Do you want to save the game?", "yes", "no");
				if (selection.equals("yes")) {
					GameDAO gameDAO = new GameDAO();
					try {
						List<Integer> gameIDs = gameDAO.getGameIds();
						game.setGameID(gui.getUserInteger("Følgende spil er allerede gemte \n" +
								gameIDs.toString() + "\n Hvilket ID skal dit save gemmes under?" +
								"\n Ved at vælge et id der allerede er gemt overskrives gemmet"));
						if (gameIDs.contains(game.getGameID())) {
							gameDAO.updateGame(game);
						} else gameDAO.createGame(game);
					} catch (DALException e) {
						e.printStackTrace();
					}
					terminated = true;
				}
				else terminated = true;
				}

			}
		}

		dispose();
	}

	/**
	 * This method implements a activity of asingle move of the given player.
	 * It throws a {@link dk.dtu.compute.se.pisd.monopoly.mini.model.exceptions.PlayerBrokeException}
	 * if the player goes broke in this move. Note that this is still a very
	 * basic implementation of the move of a player; many aspects are still
	 * missing.
	 * 
	 * @param player the player making the move
	 * @throws PlayerBrokeException if the player goes broke during the move
	 */
	public void makeMove(Player player) throws PlayerBrokeException, GameEndedException {

		boolean castDouble;
		int doublesCount = 0;
		do {
			int die1 = (int) (1 + 6.0*Math.random());
			int die2 = (int) (1 + 6.0*Math.random());
			castDouble = (die1 == die2);
			gui.setDice(die1, die2);
			player.setSumOfDies(die1+die2);

			if (castDouble) {
				doublesCount++;
				if (doublesCount > 2) {
					gui.showMessage("Player " + player.getName() + " has cast the third double and goes to jail!");
					gotoJail(player);
					return;
				}
			}
			if (player.isInPrison()) {
				getOutOfJail(player);
			}
			if (!player.isInPrison()) {
				// make the actual move by computing the new position and then
				// executing the action moving the player to that space
				int pos = player.getCurrentPosition().getIndex();
				List<Space> spaces = game.getSpaces();
				int newPos = (pos + die1 + die2) % spaces.size();
				Space space = spaces.get(newPos);
				moveToSpace(player, space);
				if (castDouble) {
					gui.showMessage("Player " + player.getName() + " cast a double and makes another move.");
				}
			}
			buyQuestion(player);

		} while (castDouble);
	}

	/**
	 * Used at the end of each turn to ask players if they want to buy a house for any of their realEstates
	 * loops through all owned properties. Only asks if the player has any realEstate
	 * @author Everyone
	 * @param player the player currently playing
	 */
	public void buyQuestion(Player player) {
		// TODO make method to properly check if a player can buy houses
		boolean hasRealEstate = false;
		for (Property property : player.getOwnedProperties()) {
			if (property instanceof RealEstate)
				hasRealEstate = true;
		}
		if (hasRealEstate) {
			String selection = gui.getUserSelection(
					"Do you want to build any houses or hotels?",
					"yes",
					"no");
			if (selection.equals("yes")) {
				for (Property property : player.getOwnedProperties()) {
					if (property instanceof RealEstate) {
						RealEstate realEstate = ((RealEstate) property);
						if (realEstate.getHouses() < 4) {
							selection = gui.getUserSelection(
									"Do you want to buy a house on " + property.getName(),
									"yes",
									"no");
							if (selection.equals("yes")) {
								realEstate.buildhouse(player,realEstate);
								gui.showMessage("You purchased a house!");
							}
						}
						else if (realEstate.getHouses() == 4) {
							selection = gui.getUserSelection(
									"Do you want to buy a hotel on " + property.getName(),
									"yes",
									"no");
							if (selection.equals("yes")) {
								realEstate.buildhouse(player, realEstate);
								gui.showMessage("You purchased a hotel!");
							}
						}
					}
				}
			}
		}
	}




    /**
     * This method implements the activity of moving the player to the new position,
     * including all actions associated with moving the player to the new position.
     *
     * @param player the moved player
     * @param space the space to which the player moves
     * @throws PlayerBrokeException when the player goes broke doing the action on that space
     */
    public void moveToSpace(Player player, Space space) throws PlayerBrokeException, GameEndedException {
        int posOld = player.getCurrentPosition().getIndex();
        player.setCurrentPosition(space);

		if (posOld > player.getCurrentPosition().getIndex()) {
			// Note that this assumes that the game has more than 12 spaces here!
			// TODO: the amount of 2000$ should not be a fixed constant here (could also
			//       be configured in the Game class.
			gui.showMessage("Player " + player.getName() + " receives 2000$ for passing Go!");
			this.paymentFromBank(player, 2000);
		}		
		gui.showMessage(player.getName() + " arrives at " + space.getIndex() + ": " +  space.getName() + ".");
		
		// Execute the action associated with the respective space. Note
		// that this is delegated to the field, which implements this action
		space.doAction(this, player);

        if (player.getBalance() < 0) {
            throw new GameEndedException(game);
        }
    }

	/**
	 * The method implements the action of a player going directly to jail.
	 * 
	 * @param player the player going to jail
	 */
	public void gotoJail(Player player)  {
		// Field #10 is in the default game board of Monopoly the field
		// representing the prison.
		// TODO the 10 should not be hard coded
		player.setCurrentPosition(game.getSpaces().get(10));
		player.setInPrison(true);
	}

	/**
	 * Mthod used for getting a player out of jail either by payment or by die roll
	 * @param player the player currently in prison
	 * @throws PlayerBrokeException if the player gets forced to buy out of prison and goes broke
	 * @Author Magnus og Ida.
	 */
	public void getOutOfJail (Player player) throws PlayerBrokeException{
		int bail = 500;

		if (player.isInPrison()) {
			String selection = gui.getUserSelection(player.getName() + " er i fængsel vil du købe dig ud eller slå efter dobbelt" +
					"", "Ja", "Nej");

			if (selection == "Ja") {
				paymentToBank(player, bail);
				player.setInPrison(false);
			}
			if (selection == "Nej") {
				int die1 = (int) (1 + 6.0 * Math.random());
				int die2 = (int) (1 + 6.0 * Math.random());
				boolean castDouble = (die1 == die2);
				gui.setDice(die1, die2);
				if (castDouble) {
					gui.showMessage("Du slog dobbelt og kommer ud af fængsel");
					player.setInPrison(false);
				} else  {
					gui.showMessage("Du slog ikke dobbelt og må side en runde over");
					player.setTurnsInJail(player.getTurnsInJail()+1);
				}
			}
			if (player.getTurnsInJail() == 3) {
				gui.showMessage("Du har ikke kunne slå dig ud efter 3 forsøg, og skal derfor betale for at komme ud");
				paymentToBank(player, 500);
				player.setInPrison(false);
				player.setTurnsInJail(0);
			}
		}
	}
	
	/**
	 * The method implementing the activity of taking a chance card.
	 * 
	 * @param player the player taking a chance card
	 * @throws PlayerBrokeException if the player goes broke by this activity
	 */
	public  void takeChanceCard(Player player) throws PlayerBrokeException, GameEndedException{
		Card card = game.drawCardFromDeck();
		gui.displayChanceCard(card.getText());
		gui.showMessage("Player " + player.getName() + " draws a chance card.");

		try {
			card.doAction(this, player);
		} finally {
			gui.displayChanceCard("done");
		}
	}
	
	/**
	 * This method implements the action returning a drawn card or a card keep with
	 * the player for some time back to the bottom of the card deck.
	 * 
	 * @param card returned card
	 */
	public void returnChanceCardToDeck(Card card) {
		game.returnCardToDeck(card);
	}
	
	/**
	 * This method implements the activity where a player can obtain
	 * cash by selling houses back to the bank, by mortgaging own properties,
	 * or by selling properties to other players. This method is called, whenever
	 * the player does not have enough cash available for an action. If
	 * the player does not manage to free at least the given amount of money,
	 * the player will be broke; this is to help the player make the right
	 * choices for freeing enough money.
	 *  
	 * @param player the player
	 * @param amount the amount the player should have available after the act
	 */
	public void obtainCash(Player player, int amount) {
		// TODO implement
	}
	
	/**
	 * This method implements the activity of offering a player to buy
	 * a property. This is typically triggered by a player arriving on
	 * an property that is not sold yet. If the player chooses not to
	 * buy, the property will be set for auction.
	 * 
	 * @param property the property to be sold
	 * @param player the player the property is offered to
	 * @throws PlayerBrokeException when the player chooses to buy but could not afford it
	 */
	public void offerToBuy(Property property, Player player) throws PlayerBrokeException, GameEndedException {
		// TODO We might also allow the player to obtainCash before
		// the actual offer, to see whether he can free enough cash
		// for the sale.
	
		String choice = gui.getUserSelection(
				"Player " + player.getName() +
				": Do you want to buy " + property.getName() +
				" for " + property.getCost() + "$?",
				"yes",
				"no");

        if (choice.equals("yes") && player.getBalance() >= property.getCost()) {
    		try {
    			paymentToBank(player, property.getCost());
    		} catch (PlayerBrokeException e) {
    			// if the payment fails due to the player being broke,
    			// an auction (among the other players is started
    			auction(property);
    			// then the current move is aborted by casting the
    			// PlayerBrokeException again
    			throw e;
    		}
    		player.addOwnedProperty(property);
    		property.setOwner(player);
    		if (property instanceof Ferry) {
    			for (Property property1 : player.getOwnedProperties()) {
    				if (property1 instanceof  Ferry) {
    					Ferry ferry = (Ferry) property1;
    					ferry.computeRent(ferry);
					}
				}
			}
    		return;
        }
        
		// In case the player does not buy the property,
		// an auction is started
		auction(property);
	}
	
	/**
	 * This method implements a payment activity to another player,
	 * which involves the player to obtain some cash on the way, in case he does
	 * not have enough cash available to pay right away. If he cannot free
	 * enough money in the process, the player will go bankrupt.
	 * 
	 * @param payer the player making the payment
	 * @param amount the payed amount
	 * @param receiver the beneficiary of the payment
	 * @throws PlayerBrokeException when the payer goes broke by this payment
	 */
	public void payment(Player payer, int amount, Player receiver) throws PlayerBrokeException {
		if (payer.getBalance() < amount) {
			obtainCash(payer, amount);
			if (payer.getBalance() < amount) {
				playerBrokeTo(payer,receiver);
				throw new PlayerBrokeException(payer);
			}
		}
		gui.showMessage("Player " + payer.getName() + " pays " +  amount + "$ to player " + receiver.getName() + ".");
		payer.payMoney(amount);
		receiver.receiveMoney(amount);
	}
	
	/**
	 * This method implements the action of a player receiving money from
	 * the bank.
	 * 
	 * @param player the player receiving the money
	 * @param amount the amount
	 */
	public void paymentFromBank(Player player, int amount) {
		player.receiveMoney(amount);
	}

	/**
	 * This method implements the activity of a player making a payment to
	 * the bank. Note that this might involve the player to obtain some
	 * cash; in case he cannot free enough cash, he will go bankrupt
	 * to the bank. 
	 * 
	 * @param player the player making the payment
	 * @param amount the amount
	 * @throws PlayerBrokeException when the player goes broke by the payment
	 */
	public void paymentToBank(Player player, int amount) throws PlayerBrokeException{
		if (amount > player.getBalance()) {
			obtainCash(player, amount);
			if (amount > player.getBalance()) {
				playerBrokeToBank(player);
				throw new PlayerBrokeException(player);

			}
			
		}
		gui.showMessage("Player " + player.getName() + " pays " +  amount + "$ to the bank.");
		player.payMoney(amount);
	}
	
	/**
	 * This method implements the activity of auctioning a property.
	 * 
	 * @param property the property which is for auction
	 */
	public void auction(Property property) {
		// TODO gør det så at det er den næste spiller i rækken der byder først. exp player 2 afviser at købe en grund
		// så bliver det spiller 3 der byder først så player 1 osv.
		List<Player> bidders = new ArrayList<Player>();
		for (Player player : game.getPlayers()){
			if (!player.isBroke()) {
				bidders.add(player);
			}
		}
		int highestBid = 0;
		while (bidders.size() > 1) {
			Player bidder = bidders.remove(0);
			int bid = gui.getUserInteger(bidder.getName() + "What do you want to bid? Current bid: " + highestBid);
			if (bid > highestBid && bid <= bidder.getBalance()) {
				highestBid = bid;
				bidders.add(bidder);
			}
		}
		try {
			paymentToBank(bidders.get(0), highestBid);
		} catch (PlayerBrokeException e) {
			e.printStackTrace();
		}
		bidders.get(0).addOwnedProperty(property);
		property.setOwner(bidders.get(0));


		gui.showMessage(property.getName()+" was bought on auction by "+bidders.get(0).getName() +" for "+ highestBid);
	}
	
	/**
	 * Action handling the situation when one player is broke to another
	 * player. All money and properties are transferred to the other player.
	 *  
	 * @param brokePlayer the broke player
	 * @param benificiary the player who receives the money and assets
	 */
	public void playerBrokeTo(Player brokePlayer, Player benificiary) {
		int amount = brokePlayer.getBalance();
		benificiary.receiveMoney(amount);
		brokePlayer.setBalance(0);
		brokePlayer.setBroke(true);

		// TODO We assume here, that the broke player has already sold all his houses! But, if
		// not, we could make sure at this point that all houses are removed from
		// properties (properties with houses on are not supposed to be transferred, neither
		// in a trade between players, nor when  player goes broke to another player)
		for (Property property: brokePlayer.getOwnedProperties()) {
			property.setOwner(benificiary);
			benificiary.addOwnedProperty(property);
		}	
		brokePlayer.removeAllProperties();
		
		while (!brokePlayer.getOwnedCards().isEmpty()) {
			game.returnCardToDeck(brokePlayer.getOwnedCards().get(0));
		}
		
		gui.showMessage("Player " + brokePlayer.getName() + "went broke and transfered all"
				+ "assets to " + benificiary.getName());
	}
	
	/**
	 * Action handling the situation when a player is broke to the bank.
	 * 
	 * @param player the broke player
	 */
	public void playerBrokeToBank(Player player) {

		player.setBalance(0);
		player.setBroke(true);
		
		// TODO we also need to remove the houses and the mortgage from the properties 

		for (Property property: player.getOwnedProperties()) {
			property.setOwner(null);
		}
		player.removeAllProperties();
		
		gui.showMessage("Player " + player.getName() + " went broke");
		
		while (!player.getOwnedCards().isEmpty()) {
			game.returnCardToDeck(player.getOwnedCards().get(0));
		}
	}
	
	/**
	 * Method for disposing of this controller and cleaning up its resources.
	 */
	public void dispose() {
		if (!disposed && view != null) {
			disposed = true;
			if (view != null) {
				view.dispose();
				view = null;
			}
			// TODO we should also dispose of the GUI here. But this works only
			//      for my private version of the GUI and not for the GUI currently
			//      deployed via Maven (or other official versions);
		}
	}

}
