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
 * 
 * @author Ekkart Kindler, ekki@dtu.dk
 *
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
		Space go = new Space();
		go.setName("Go");
		game.addSpace(go);
		
		RealEstate p = new RealEstate();
		p.setColor(new Color(102,153,255));
		p.setName("Rødovrevej");
		p.setCost(60);
		p.setHousecost(100);
		p.setBaseRent(50);
		game.addSpace(p);

		
		Chance chance = new Chance();
		chance.setName("Chance");
		game.addSpace(chance);
		
		p = new RealEstate();
		p.setColor(new Color(102,153,255));
		p.setName("Hvidovrevej");
		p.setCost(60);
		p.setHousecost(100);
		p.setBaseRent(50);
		game.addSpace(p);
		
		Tax t = new Tax();
		t.setName("Pay tax (10% on Cash)");
		game.addSpace(t);

		Utility s = new Utility();
		s.setName("Øresund");
		s.setCost(200);
		s.setRent(500);
		game.addSpace(s);

		p = new RealEstate();
		p.setName("Roskildevej");
		p.setColor(new Color(255,139,135));
		p.setCost(100);
		p.setHousecost(100);
		p.setBaseRent(70);
		game.addSpace(p);
		
		chance = new Chance();
		chance.setName("Chance");
		game.addSpace(chance);
		
		p = new RealEstate();
		p.setColor(new Color(255, 139, 135));
		p.setName("Valby Langgade");
		p.setCost(100);
		p.setHousecost(100);
		p.setBaseRent(70);
		game.addSpace(p);
		
		p = new RealEstate();
		p.setName("Allégade");
		p.setColor(new Color(255,139,135));
		p.setCost(120);
		p.setHousecost(100);
		p.setBaseRent(150);
		game.addSpace(p);
		
		Space prison = new Space();
		prison.setName("Prison");
		game.addSpace(prison);
		
		p = new RealEstate();
		p.setName("Frederiksberg Allé");
		p.setColor(new Color(102, 204, 31));
		p.setCost(140);
		p.setHousecost(100);
		p.setBaseRent(200);
		game.addSpace(p);

		s = new Utility();
		s.setName("Tuborg tapperi");
		s.setCost(200);
		s.setRent(300);
		game.addSpace(s);
		
		p = new RealEstate();
		p.setColor(new Color(102, 204, 31));
		p.setName("Bülowsvej");
		p.setCost(140);
		p.setHousecost(100);
		p.setBaseRent(200);
		game.addSpace(p);
		
		p = new RealEstate();
		p.setName("Gl. Kongevej");
		p.setColor(new Color(102, 204, 31));
		p.setCost(140);
		p.setHousecost(100);
		p.setBaseRent(250);
		game.addSpace(p);

		s = new Utility();
		s.setName("D.F.D.S.");
		s.setCost(200);
		s.setRent(500);
		game.addSpace(s);

		p = new RealEstate();
		p.setName("Bernstorffsvej");
		p.setColor(new Color(155, 155, 155));
		p.setCost(180);
		p.setHousecost(100);
		p.setBaseRent(250);
		game.addSpace(p);

		chance = new Chance();
		chance.setName("Chance");
		game.addSpace(chance);

		p = new RealEstate();
		p.setName("Hellerupvej");
		p.setColor(new Color(155,155,155));
		p.setCost(180);
		p.setHousecost(100);
		p.setBaseRent(250);
		game.addSpace(p);

		p = new RealEstate();
		p.setName("Strandvejen");
		p.setColor(new Color(155,155,155));
		p.setCost(180);
		p.setHousecost(100);
		p.setBaseRent(250);
		game.addSpace(p);

		/** Not sure about the houseCost and baseRent -IC */

 		Space h = new Space();
		h.setName("Helle");
		game.addSpace(h);

		p = new RealEstate();
		p.setName("Trianglen");
		p.setColor(Color.RED);
		p.setCost(220);
		p.setHousecost(100);
		p.setBaseRent(200);
		game.addSpace(p);

		chance = new Chance();
		chance.setName("Chance");
		game.addSpace(chance);

		p = new RealEstate();
		p.setName("Østerbrogade");
		p.setColor(Color.RED);
		p.setCost(220);
		p.setHousecost(100);
		p.setBaseRent(200);
		game.addSpace(p);

		p = new RealEstate();
		p.setName("Grønningen");
		p.setColor(Color.RED);
		p.setCost(220);
		p.setHousecost(100);
		p.setBaseRent(200);
		game.addSpace(p);

		s = new Utility();
		s.setName("Å. S.");
		s.setCost(2000);
		s.setRent(500);
		game.addSpace(s);

		p = new RealEstate();
		p.setName("Bredgade");
		p.setColor(new Color(255,255,255));
		p.setCost(260);
		p.setHousecost(100);
		p.setBaseRent(150);
		game.addSpace(p);

		p = new RealEstate();
		p.setName("Kgs. Nytorv");
		p.setColor(new Color(255,255,255));
		p.setCost(260);
		p.setHousecost(100);
		p.setBaseRent(150);
		game.addSpace(p);

		s = new Utility();
		s.setName("Calsberg");
		s.setCost(150);
		s.setRent(200);
		game.addSpace(s);

		p = new RealEstate();
		p.setName("Østergade");
		p.setColor(new Color(255,255,255));
		p.setCost(260);
		p.setHousecost(100);
		p.setBaseRent(150);
		game.addSpace(p);

		GoToJail g = new GoToJail();
		g.setName("Go To Prison");
		game.addSpace(g);

		p = new RealEstate();
		p.setName("Amagertorv");
		p.setColor(new Color(250, 241, 11));
		p.setCost(300);
		p.setHousecost(100);
		p.setBaseRent(250);
		game.addSpace(p);

		p = new RealEstate();
		p.setName("Vimmelstaftet");
		p.setColor(new Color(250, 241, 11));
		p.setCost(300);
		p.setHousecost(100);
		p.setBaseRent(250);
		game.addSpace(p);

		chance = new Chance();
		chance.setName("Chance");
		game.addSpace(chance);

		p = new RealEstate();
		p.setName("Nygade");
		p.setColor(new Color(250, 241,11));
		p.setCost(320);
		p.setHousecost(100);
		p.setBaseRent(250);
		game.addSpace(p);

		s = new Utility();
		s.setName("Bornholm");
		s.setCost(200);
		s.setRent(500);
		game.addSpace(s);

		chance = new Chance();
		chance.setName("Chance");
		game.addSpace(chance);

		p = new RealEstate();
		p.setName("Frederiksberggade");
		p.setColor(new Color(186, 46, 203));
		p.setCost(350);
		p.setHousecost(200);
		p.setBaseRent(150);
		game.addSpace(p);

		StateTax st = new StateTax();
		st.setName("Pay 100$ to the bank");
		game.addSpace(st);


		p = new RealEstate();
		p.setName("Rådhuspladsen");
		p.setColor(new Color(171,39,203));
		p.setCost(400);
		p.setHousecost(200);
		p.setBaseRent(150);
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
	 * This method will be called before the game is started to create
	 * the participating players.
	 */
	public static void createPlayers(Game game) {
		// TODO the players should eventually be created interactively or
		// be loaded from a database
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
	 * cards, creates players, and then starts the game. Note
	 * that, eventually, the game could be loaded from a database.
	 * 
	 * @param args not used
	 */
	public static void main(String[] args) {
		Game game = createGame();
		game.shuffleCardDeck();

		createPlayers(game);

		GameController controller = new GameController(game);
		controller.initializeGUI();
		controller.play();

		}
	}
