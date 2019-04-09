package dk.dtu.compute.se.pisd.monopoly.mini;

import dk.dtu.compute.se.pisd.monopoly.mini.controller.GameController;
import dk.dtu.compute.se.pisd.monopoly.mini.model.*;
import dk.dtu.compute.se.pisd.monopoly.mini.model.cards.CardMove;
import dk.dtu.compute.se.pisd.monopoly.mini.model.cards.CardReceiveMoneyFromBank;
import dk.dtu.compute.se.pisd.monopoly.mini.model.cards.PayTax;
import dk.dtu.compute.se.pisd.monopoly.mini.model.properties.RealEstate;
import dk.dtu.compute.se.pisd.monopoly.mini.model.properties.Utility;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Main class for setting up and running a (Mini-)Monoploy game.
 * Has the method to create the spaces on the gameboard, and some other things
 * that are permanent for each game.
 * @author original Ekkart Kindler, ekki@dtu.dk
 * ediitet by Group C
 */
public class MiniMonopoly {
	
	/**
	 * Creates the initial static situation of a Monopoly game. Note
	 * that the players are not created here, and the chance cards
	 * are not shuffled here.
	 *
	 * @return the initial game board and (not shuffled) deck of chance cards 
	 */
	public static Game createGame() {
		// Create the initial Game set up (note that, in this simple
		// setup, we use only 11 spaces). Note also that this setup
		// could actually be loaded from a file or database instead
		// of creating it programmatically. This will be discussed
		// later in this course.
		Game game = new Game();

		List<Color> pColor = new ArrayList<>();
		pColor.add(new Color(255,80,55));
		pColor.add(new Color(220, 165, 11));
		pColor.add(new Color(38,135,30));
		pColor.add(new Color(86, 112, 227));
		pColor.add(new Color(255,0,255));
		pColor.add(new Color(0,255,255));

		game.setColors(pColor);

		Space go = new Space();
		go.setName("Go");
		game.addSpace(go);
		
		RealEstate p = new RealEstate(50,60,100);
		p.setColor(new Color(102,153,255));
		p.setName("Rødovrevej");
		game.addSpace(p);

		
		Chance chance = new Chance();
		chance.setName("Chance");
		game.addSpace(chance);
		
		p = new RealEstate(50,60,100);
		p.setColor(new Color(102,153,255));
		p.setName("Hvidovrevej");
		game.addSpace(p);
		
		Tax t = new Tax();
		t.setName("Pay tax (10% on Cash)");
		game.addSpace(t);

		Utility s = new Utility();
		s.setName("Øresund");
		s.setCost(200);
		s.setRent(500);
		game.addSpace(s);

		p = new RealEstate(70,100,100);
		p.setName("Roskildevej");
		p.setColor(new Color(255, 156, 149));
		game.addSpace(p);
		
		chance = new Chance();
		chance.setName("Chance");
		game.addSpace(chance);
		
		p = new RealEstate(70,100,100);
		p.setColor(new Color(255, 156, 149));
		p.setName("Valby Langgade");
		game.addSpace(p);
		
		p = new RealEstate(150,120,100);
		p.setName("Allégade");
		p.setColor(new Color(255,156,149));
		game.addSpace(p);
		
		Space prison = new Space();
		prison.setName("Prison");
		game.addSpace(prison);
		
		p = new RealEstate(200,140,100);
		p.setName("Frederiksberg Allé");
		p.setColor(new Color(102, 204, 31));
		game.addSpace(p);

		s = new Utility();
		s.setName("Tuborg tapperi");
		s.setCost(200);
		s.setRent(300);
		game.addSpace(s);
		
		p = new RealEstate(200,140,100);
		p.setColor(new Color(102, 204, 31));
		p.setName("Bülowsvej");
		game.addSpace(p);
		
		p = new RealEstate(250,140,100);
		p.setName("Gl. Kongevej");
		p.setColor(new Color(102, 204, 31));
		game.addSpace(p);

		s = new Utility();
		s.setName("D.F.D.S.");
		s.setCost(200);
		s.setRent(500);
		game.addSpace(s);

		p = new RealEstate(250,180,100);
		p.setName("Bernstorffsvej");
		p.setColor(new Color(155, 155, 155));
		game.addSpace(p);

		chance = new Chance();
		chance.setName("Chance");
		game.addSpace(chance);

		p = new RealEstate(250,180,100);
		p.setName("Hellerupvej");
		p.setColor(new Color(155,155,155));
		game.addSpace(p);

		p = new RealEstate(250,180,100);
		p.setName("Strandvejen");
		p.setColor(new Color(155,155,155));
		game.addSpace(p);

		/** Not sure about the houseCost and baseRent -IC */

 		Space h = new Space();
		h.setName("Helle");
		game.addSpace(h);

		p = new RealEstate(200,220,100);
		p.setName("Trianglen");
		p.setColor(Color.RED);
		game.addSpace(p);

		chance = new Chance();
		chance.setName("Chance");
		game.addSpace(chance);

		p = new RealEstate(200,220,100);
		p.setName("Østerbrogade");
		p.setColor(Color.RED);
		game.addSpace(p);

		p = new RealEstate(200,220,100);
		p.setName("Grønningen");
		p.setColor(Color.RED);
		game.addSpace(p);

		s = new Utility();
		s.setName("Å. S.");
		s.setCost(2000);
		s.setRent(500);
		game.addSpace(s);

		p = new RealEstate(150,260,100);
		p.setName("Bredgade");
		p.setColor(new Color(255,255,255));
		game.addSpace(p);

		p = new RealEstate(150,260,100);
		p.setName("Kgs. Nytorv");
		p.setColor(new Color(255,255,255));
		game.addSpace(p);

		s = new Utility();
		s.setName("Calsberg");
		s.setCost(150);
		s.setRent(200);
		game.addSpace(s);

		p = new RealEstate(150,260,100);
		p.setName("Østergade");
		p.setColor(new Color(255,255,255));
		game.addSpace(p);

		GoToJail g = new GoToJail();
		g.setName("Go To Prison");
		game.addSpace(g);

		p = new RealEstate(250,300,100);
		p.setName("Amagertorv");
		p.setColor(new Color(250, 241, 11));
		game.addSpace(p);

		p = new RealEstate(250,300,100);
		p.setName("Vimmelstaftet");
		p.setColor(new Color(250, 241, 11));
		game.addSpace(p);

		chance = new Chance();
		chance.setName("Chance");
		game.addSpace(chance);

		p = new RealEstate(250,320,100);
		p.setName("Nygade");
		p.setColor(new Color(250, 241,11));
		game.addSpace(p);

		s = new Utility();
		s.setName("Bornholm");
		s.setCost(200);
		s.setRent(500);
		game.addSpace(s);

		chance = new Chance();
		chance.setName("Chance");
		game.addSpace(chance);

		p = new RealEstate(150,350,200);
		p.setName("Frederiksberggade");
		p.setColor(new Color(186, 46, 203));
		game.addSpace(p);

		StateTax st = new StateTax();
		st.setName("Pay 100$ to the bank");
		game.addSpace(st);


		p = new RealEstate(150,400,200);
		p.setName("Rådhuspladsen");
		p.setColor(new Color(171,39,203));
		game.addSpace(p);


		List<Card> cards = new ArrayList<Card>();
		
		CardMove move = new CardMove();
		move.setTarget(game.getSpaces().get(9));
		move.setText("Move to Allégade!");
		cards.add(move);
		
		PayTax tax = new PayTax();
		tax.setText("Pay 10% income tax!");
		cards.add(tax);
		
		CardReceiveMoneyFromBank b = new CardReceiveMoneyFromBank();
		b.setText("You receive 100$ from the bank.");
		b.setAmount(100);
		cards.add(b);
		game.setCardDeck(cards);

		return game;
	}

	/**
	 * This method is only used in the DAO_tester. Player creation is now handled in GameController or GAMEDAO if game is loaded from DB.
	 * Do not use for final implementation
	 */
	public static void createPlayers(Game game) {


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
	 * The main method which creates a game, shuffles the chance
	 * cards. Make game either starts creating players or loads a previous game
	 * if wanted. Then initializes GUI and plays the game
	 * @param args not used
	 */
	public static void main(String[] args) {
		Game game = createGame();
		game.shuffleCardDeck();

		//createPlayers(game);

		GameController controller = new GameController(game);
		// call this if you want load functions controller.makeGame();
		createPlayers(game);
		controller.initializeGUI();
		controller.play();

		}
	}
