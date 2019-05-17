package dk.dtu.compute.se.pisd.monopoly.mini.controller;

import dk.dtu.compute.se.pisd.monopoly.mini.DAL.GameDAO;
import dk.dtu.compute.se.pisd.monopoly.mini.model.*;
import dk.dtu.compute.se.pisd.monopoly.mini.model.exceptions.DALException;
import dk.dtu.compute.se.pisd.monopoly.mini.model.exceptions.GameEndedException;
import dk.dtu.compute.se.pisd.monopoly.mini.model.exceptions.PlayerBrokeException;
import dk.dtu.compute.se.pisd.monopoly.mini.model.properties.Brewery;
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
 * The <code>doAction()</code> methods of the
 * {@link dk.dtu.compute.se.pisd.monopoly.mini.model.Space} and
 * the {@link dk.dtu.compute.se.pisd.monopoly.mini.model.Card}
 * can be implemented based on the basic actions and activities
 * of this game controller.
 *
 * @author Original Ekkart Kindler, ekki@dtu.dk
 * @author edited by
 */
public class GameController {

	private Game game;

	private GUI gui;

	private View view;

	private GameDAO gameDAO = new GameDAO();

	private boolean disposed = false;



	/**
	 * Constructor for a controller of a game.
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
	 */
	public void initializeGUI() {
		this.view = new View(game, gui);
	}

	/**
	 * First method to interact with the user. Either loads a game from the database or directs to other sub methods
	 * depending on if the players want a default game with 3 unnamed players, or make a a new game with custom playernames.
	 * @author Magnus
	 */
	public void makeGame () {

		String selection = gui.getUserSelection("Velkommen til  MiniMonopoly! Vil du starte et nyt spil, eller fortsætte et tidligere spil?",
				"DEFAULT GAME", "Start nyt spil", "Hent spil");
		if (selection == "Hent spil"){
			try{
				List<Integer> gameIDs = gameDAO.getGameIds();
				if (gameIDs.size() == 0) {
					selection = gui.getUserSelection("Der er ingen gemte spil i øjeblikket", "DEFAULT GAME", "Start nyt spil");
				}
				if (gameIDs.size() != 0) {
				int gameID = gui.getUserInteger("Følgende spil er gemte \n "+gameIDs.toString() +
						"\n Indtast game ID" +
						"\n Hvis du hellere vil starte et nyt spil, så indtast et tal der ikke er fra listen");
				while (!gameDAO.getGameIds().contains(gameID)) {
					gameID = gui.getUserInteger("Du indtastede et gameID som ikke eksisterer. Prøv venligst igen!" +
							"\n Følgende spil er gemte: \n" + gameIDs.toString());
				}
				game.setGameID(gameID);
				gameDAO.loadGame(game);
				}

			}catch (DALException e){
				e.printStackTrace();
			}
		}
		if (selection == "Start nyt spil"){
			makePlayers();
		}
		if (selection == "DEFAULT GAME"){

			makeDefaultGame();

		}
	}

	/**
	 * Method to create the default game.
	 * Mostly used for testing purposes or to start a quick game.
	 * @author Magnus and Tim
	 */
	public void makeDefaultGame() {
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
	/**
	 * The basic method for making each player in the game. Only happens if the player wants to start a fresh game.
	 * Ask how many are playing with the min and max number of players. Then creates each player with defualt ID's and colors
	 * Names are collected via the GUI.
	 * @author Magnus and Tim
	 */
	public void makePlayers() {
		int minPlayers = 3;
		int maxPlayers = 6;
		int totalPlayers = gui.getUserInteger("Hvor mange spillere er I? Indtast et nummer mellem " + minPlayers + " og " + maxPlayers);
		while (totalPlayers < minPlayers || totalPlayers > maxPlayers) {
			totalPlayers = gui.getUserInteger("Det indtastede antal spillere, er uden for det accepterede interval. Indtast et andet nummer");
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
	 * game at any point. The method handles the entire flow of the game, and is the last method to be called in our main.
	 * @author Magnus
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
					String selection = gui.getUserSelection("Det er din tur "+ player.getName()+". Vil du gerne bytte med nogen?", "Nej","Ja");
					if (selection == "Ja") {
						trade(player);
					}
					this.makeMove(player);
				} catch (PlayerBrokeException e) {
					// We could react to the player having gone broke
				} catch (GameEndedException e){
					gui.showMessage(e.getMessage());
					terminated = true;
				}
			}

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
								" har vundet med " + winner.getBalance() + "$.");
				break;
			} else if (countActive < 1) {
				// This can actually happen in very rare conditions and only
				// if the last player makes a stupid mistake (like buying something
				// in an auction in the same round when the last but one player went
				// bankrupt)
				gui.showMessage(
						"Alle spillere er gået fallit.");
				break;
			}

			current = (current + 1) % players.size();
			game.setCurrentPlayer(players.get(current));
			if (current == 0) {
				String selection = gui.getUserSelection(
						"En runde er afsluttet. Vil I fortsætte med at spille?",
						"Ja",
						"Nej");
				if (selection.equals("Nej")) {
					selection = gui.getUserSelection("Vil I gemme spille?", "Ja", "Nej");
					if (selection.equals("Ja")) {
						GameDAO gameDAO = new GameDAO();
						try {
							List<Integer> gameIDs = gameDAO.getGameIds();
							game.setGameID(gui.getUserInteger("Følgende spil er allerede gemte \n" +
									gameIDs.toString() + "\n Hvilket ID skal dit spil gemmes under?" +
									"\n Ved at vælge et id der allerede er gemt, overskrives det gemte spil"));
							if (gameIDs.contains(game.getGameID())) {
								gameDAO.updateGame(game);
							} else gameDAO.createGame(game);
							gui.showMessage("Du gemte dit spil og kan lukke programmet");
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
	 * This method implements a activity of a single move of the given player.
	 * It throws a {@link dk.dtu.compute.se.pisd.monopoly.mini.model.exceptions.PlayerBrokeException}
	 * if the player goes broke in this move. Note that this is still a very
	 * basic implementation of the move of a player; many aspects are still
	 * missing.
	 *
	 * @param player the player making the move
	 * @throws PlayerBrokeException if the player goes broke during the move
	 * @author Magnus and Ida.
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
					gui.showMessage("Player " + player.getName() + " har for tredje gang i træk kastet to af samme slags, og ryger derfor i fængsel!");
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
					gui.showMessage("Player " + player.getName() + " har kastet to af samme slags, og må derfor kaste igen.");
				}
			}
			houseOffer(player);

		} while (castDouble);
	}

	/**
	 * Method to start a trade between 2 players. The method handles a the purchase of a property from another player.
	 * The purchase can be payed with cash, property exchange, or both at the same time.
	 * @param buyingPlayer the player starting the trade.
	 * @author Magnus
	 */
	public void trade(Player buyingPlayer) {

		Boolean doneWithTrading = false;
		Player targetPlayer = new Player();
		Property targetProperty = new Property();
		Property buyerProperty = new Property();
		int buyerOffer = 0;

		while (!doneWithTrading) {
			String targetPlayerName = gui.getUserString("Hvem vil du gerne trade med?");

			for (Player player : game.getPlayers()) {
				if (player.getName().equals(targetPlayerName)) {
					targetPlayer = player;
				}
			}

			String  targetPropertyName = gui.getUserString("Hvilken grund vil du gerne købe af "+ targetPlayer.getName() +"?");

			for (Property property: targetPlayer.getOwnedProperties()) {
				if (property.getName().equals(targetPropertyName)) {
					targetProperty = property;
				}
			}

			String selection = gui.getUserSelection("Hvad vil du betale med?", "1 grund + evt. kontanter", "Kun kontanter");

			if (selection == "1 grund + evt. kontanter") {

				String buyerPropertyName = gui.getUserString("Hvilken grund vil du gerne give i bytte?");
				for (Property property: buyingPlayer.getOwnedProperties()) {
					if (property.getName().equals(buyerPropertyName)) {
						buyerProperty = property;
					}
				}
				buyerOffer = gui.getUserInteger("Hvor meget vil du betale yderligere?");

				if (targetProperty instanceof RealEstate) {
					((RealEstate) targetProperty).sellAllHouses();
				}

				buyingPlayer.removeOwnedProperty(buyerProperty);
				buyingPlayer.addOwnedProperty(targetProperty);
				targetProperty.setOwner(buyingPlayer);

				buyerProperty.setOwner(targetPlayer);
				targetPlayer.addOwnedProperty(buyerProperty);
				targetPlayer.removeOwnedProperty(targetProperty);

				gui.showMessage(buyingPlayer.getName() + " Købte " + targetProperty.getName() + " af " + targetPlayer.getName() + " for " + buyerProperty.getName() + " og " + buyerOffer+ "$");


			}

			if (selection == "Kun kontanter") {

				buyerOffer = gui.getUserInteger("Hvor meget vil du betale for " + targetProperty.getName() +"?");

				if (targetProperty instanceof RealEstate) {
					((RealEstate) targetProperty).sellAllHouses();
				}

				buyingPlayer.addOwnedProperty(targetProperty);
				targetProperty.setOwner(buyingPlayer);
				targetPlayer.removeOwnedProperty(targetProperty);

				gui.showMessage(buyingPlayer.getName() + " Købte " + targetProperty.getName() + " af " + targetPlayer.getName() + " for " +  buyerOffer+ "$");
			}

			try {
				payment(buyingPlayer, buyerOffer, targetPlayer);
			} catch (PlayerBrokeException e) {
				e.printStackTrace();
			}
			selection = gui.getUserSelection("Er du færdig med at bytte?", "Ja", "Nej");
			if (selection == "Ja") {
				doneWithTrading = true;
			}
		}
	}


	/**
	 * Used at the end of each turn to ask players if they want to buy a house for any of their realEstates
	 * loops through all owned properties. Only asks if the player has any realEstate
	 * @param player the player currently playing
	 * @author Everyone
	 */
	public void houseOffer(Player player) {

		String selection;

		for (Property property : player.getOwnedProperties()){
			if (property instanceof RealEstate) {
				RealEstate realEstate = (RealEstate) property;

				if (canBuyHouse(realEstate) && player.getBalance() >= realEstate.getHousecost()){

					if (realEstate.getHouses() < 4) {
						selection = gui.getUserSelection(
								"Vil du bygge et hus på " + property.getName(),
								"Ja",
								"Nej");
						if (selection.equals("Ja")) {
							realEstate.buildhouse(player,realEstate);
							gui.showMessage("Du har købt et hus!");
						}
					}
					else if (realEstate.getHouses() == 4) {
						selection = gui.getUserSelection(
								"Vil du bygge et hotel på " + property.getName(),
								"Ja",
								"Nej");
						if (selection.equals("Ja")) {
							realEstate.buildhouse(player, realEstate);
							gui.showMessage("Du har købt et hotel!");
						}
					}
				}
			}
		}
	}

	/**
	 * Checks how many realEstates are in a specific Group, and then checks if the player owns all the required properties
	 * Method is called in houseOffer for each owned realEstae a player has at the end of his turn.
	 * @param realEstate The realEstate in question. Used to get the owner and the group ID
	 * @return True if the player can buy houses on the realEstate, and false if not
	 * @author Magnus and Tim
	 */
	private boolean canBuyHouse(RealEstate realEstate) {
		int neededProperties = 0;
		int ownedProperties = 0;

		for (Space space : game.getSpaces()) {
			if (space instanceof RealEstate){
				RealEstate estate = (RealEstate) space;
				if (estate.getGroupID() == realEstate.getGroupID()){
					neededProperties++;

					if (estate.getOwner() == realEstate.getOwner()){
						ownedProperties++;
					}
				}
			}
		}
		if (neededProperties == ownedProperties){
			return true;
		} else return false;
	}

	/**
	 * This method implements the activity of moving the player to the new position,
	 * including all actions associated with moving the player to the new position.
	 *
	 * @param player the moved player
	 * @param space the space to which the player moves
	 * @throws PlayerBrokeException when the player goes broke doing the action on that space
	 * @author edited Magnus and Ida
	 */
	public void moveToSpace(Player player, Space space) throws PlayerBrokeException, GameEndedException {
		int posOld = player.getCurrentPosition().getIndex();
		player.setCurrentPosition(space);

		if (posOld > player.getCurrentPosition().getIndex()) {
			gui.showMessage("Player " + player.getName() + " modtager 400$ for at passere Start!");
			this.paymentFromBank(player, 400);
		}
		gui.showMessage(player.getName() + " lander på " + space.getIndex() + ": " +  space.getName() + ".");

		// Execute the action associated with the respective space. Note
		// that this is delegated to the field, which implements this action
		space.doAction(this, player);

		if (player.getBalance() < 0) {
			throw new GameEndedException(game);
		}
	}

	/**
	 * The method implements the action of a player going directly to jail.
	 * @param player the player going to jail
	 */
	public void gotoJail(Player player)  {
		// Field #10 is in the default game board of Monopoly the field
		// representing the prison.
		player.setCurrentPosition(game.getSpaces().get(10));
		player.setInPrison(true);
	}

	/**
	 * Mthod used for getting a player out of jail either by payment or by die roll
	 * @param player the player currently in prison
	 * @throws PlayerBrokeException if the player gets forced to buy out of prison and goes broke
	 * @author Magnus og Ida.
	 */
	public void getOutOfJail (Player player) throws PlayerBrokeException{
		int bail = 100;

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
				paymentToBank(player, bail);
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
		gui.showMessage(player.getName() + " trækker et chancekort.");

		try {
			card.doAction(this, player);
		} finally {
			gui.displayChanceCard("done");
		}
	}

	/**
	 * This method implements the action of returning a drawn card or a card keept by
	 * the player for some time back to the bottom of the card deck.
	 * @param card returned card
	 */
	public void returnChanceCardToDeck(Card card) {
		game.returnCardToDeck(card);
	}

	/**
	 * This method implements the activity where a player can obtain
	 * cash by selling houses back to the bank. This method is called, whenever
	 * the player does not have enough cash available for an action. If
	 * the player does not manage to free at least the given amount of money,
	 * the player will be broke; this is to help the player make the right
	 * choices for freeing enough money.
	 *
	 * @param player the player
	 * @param amount the amount the player should have available after the act
	 * @author Magnus
	 */
	public void obtainCash(Player player, int amount) {
		int timesSoldHouses  = 0;

		while (player.getBalance() <= amount && timesSoldHouses <= 5) {
			for (Property property : player.getOwnedProperties()) {
				if (property instanceof RealEstate) {
					if (((RealEstate) property).getHouses() > 1) {
						((RealEstate) property).sellHouse();
					}
				}
			}
			timesSoldHouses++;
		}
	}

	/**
	 * This method implements the activity of offering a player to buy
	 * a property. This is typically triggered by a player arriving on
	 * an property that is not sold yet. If the player chooses not to
	 * buy, the property will be set for auction.
	 *
	 * @param property the property to be sold
	 * @param player the player the property is offered to
	 * @throws PlayerBrokeException when the player chooses to buy but could not afford it.
	 * @author Everyone.
	 */
	public void offerToBuy(Property property, Player player) throws PlayerBrokeException {

		String choice = gui.getUserSelection(
				"Player " + player.getName() +
						": Vil du købe " + property.getName() +
						" for " + property.getCost() + "$?",
				"Ja",
				"Nej");

		if (choice.equals("Ja") && player.getBalance() >= property.getCost()) {
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
			if (property instanceof Brewery) {
				for (Property property1 : player.getOwnedProperties()) {
					if (property1 instanceof  Brewery) {
						Brewery brewery = (Brewery) property1;
						brewery.computeMultiplier(brewery);
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
		gui.showMessage(payer.getName() + " betalte " +  amount + "$ til " + receiver.getName() + ".");
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
	public void   paymentFromBank(Player player, int amount) {
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
		gui.showMessage("Player " + player.getName() + " betaler " +  amount + "$ til banken.");
		player.payMoney(amount);
	}

	/**
	 * This method implements the activity of auctioning a property. Works by making a list of all the players, and
	 * removing the first player of the list. If the player makes and acceptable bid he is added to the back of the list.
	 * method keeps looting until only 1 player is left, and he the gets the property.
	 * @param property the property which is for auction
	 * @Author Magnus og Siff
	 */
	public void auction(Property property) {

		List<Player> bidders = new ArrayList<Player>();
		for (Player player : game.getPlayers()){
			if (!player.isBroke()) {
				bidders.add(player);
			}
		}
		int highestBid = 0;
		while (bidders.size() > 1) {
			Player bidder = bidders.remove(0);
			int bid = gui.getUserInteger(bidder.getName() + " Hvad vil du byde? Det højeste byd er: " + highestBid);
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


		gui.showMessage(property.getName()+" var købt på auktion af "+bidders.get(0).getName() +" for "+ highestBid);
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

		for (Property property: brokePlayer.getOwnedProperties()) {
			property.setOwner(benificiary);
			benificiary.addOwnedProperty(property);
		}
		brokePlayer.removeAllProperties();

		while (!brokePlayer.getOwnedCards().isEmpty()) {
			game.returnCardToDeck(brokePlayer.getOwnedCards().get(0));
		}

		gui.showMessage("Player " + brokePlayer.getName() + "gik fallit og overførte alle"
				+ "aktiver til " + benificiary.getName());
	}

	/**
	 * Action handling the situation when a player is broke to the bank.
	 *
	 * @param player the broke player
	 */
	public void playerBrokeToBank(Player player) {

		player.setBalance(0);
		player.setBroke(true);

		for (Property property: player.getOwnedProperties()) {
			property.setOwner(null);
			if (property instanceof RealEstate) {
				RealEstate realEstate = (RealEstate) property;
				realEstate.setHouses(0);
			}
		}
		player.removeAllProperties();

		gui.showMessage("Player " + player.getName() + " gik fallit");

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
		}
	}

}
