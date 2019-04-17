package dk.dtu.compute.se.pisd.monopoly.mini;

import dk.dtu.compute.se.pisd.monopoly.mini.controller.GameController;
import dk.dtu.compute.se.pisd.monopoly.mini.model.*;
import dk.dtu.compute.se.pisd.monopoly.mini.model.cards.CardMove;
import dk.dtu.compute.se.pisd.monopoly.mini.model.cards.CardPayMoneyToBank;
import dk.dtu.compute.se.pisd.monopoly.mini.model.cards.CardReceiveMoneyFromBank;
import dk.dtu.compute.se.pisd.monopoly.mini.model.cards.PayTax;
import dk.dtu.compute.se.pisd.monopoly.mini.model.properties.Brewery;
import dk.dtu.compute.se.pisd.monopoly.mini.model.properties.Ferry;
import dk.dtu.compute.se.pisd.monopoly.mini.model.properties.RealEstate;

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

		List<Color> pColor = new ArrayList<>();	// Color list for players
		pColor.add(new Color(255,80,55));
		pColor.add(new Color(220, 165, 11));
		pColor.add(new Color(38,135,30));
		pColor.add(new Color(86, 112, 227));
		pColor.add(new Color(255,0,255));
		pColor.add(new Color(0,255,255));

		List<Color> realEstateColorList = new ArrayList<>();	// Color list for realEstates
		realEstateColorList.add(new Color(75, 155, 225));
		realEstateColorList.add(new Color(255, 135, 120));
		realEstateColorList.add(new Color(102, 204, 0));
		realEstateColorList.add(new Color(153, 153, 153));
		realEstateColorList.add(new Color(255, 0, 0));
		realEstateColorList.add(new Color(255,255,255));
		realEstateColorList.add(new Color(255, 255,50));
		realEstateColorList.add(new Color(150, 60, 150));

		int[] realEstateGroups = new int[] {1,2,3,4,5,6,7,8};

		game.setColors(pColor);

		Space go = new Space();
		go.setName("Go");
		game.addSpace(go);
		
		RealEstate p = new RealEstate(50,60,1000);
		p.setColor(realEstateColorList.get(0)); // Index 0, blå-ish
		p.setGroupID(realEstateGroups[0]);
		p.setName("Rødovrevej");
		game.addSpace(p);

		Chance chance = new Chance();
		chance.setName("Chance");
		game.addSpace(chance);
		
		p = new RealEstate(50,60,1000);
		p.setColor(realEstateColorList.get(0)); // Index 0, blå-ish
		p.setGroupID(realEstateGroups[0]);
		p.setName("Hvidovrevej");
		game.addSpace(p);

		Tax t = new Tax();
		t.setName("Pay tax (10% on Cash)");
		game.addSpace(t);

		Ferry s = new Ferry("Øresund", 500, 200);
		game.addSpace(s);

		p = new RealEstate(100,100,1000);
		p.setName("Roskildevej");
		p.setColor(realEstateColorList.get(1)); // Index 1, pink-ish
		p.setGroupID(realEstateGroups[1]);
		game.addSpace(p);

		chance = new Chance();
		chance.setName("Chance");
		game.addSpace(chance);
		
		p = new RealEstate(100,100,1000);
		p.setColor(realEstateColorList.get(1)); // Index 1, pink-ish
		p.setGroupID(realEstateGroups[1]);
		p.setName("Valby Langgade");
		game.addSpace(p);

		p = new RealEstate(150,120,1000);
		p.setName("Allégade");
		p.setColor(realEstateColorList.get(1)); // Index 1, pink-ish
		p.setGroupID(realEstateGroups[1]);
		game.addSpace(p);

		Space prison = new Space();
		prison.setName("Prison");
		game.addSpace(prison);
		
		p = new RealEstate(200,140,2000);
		p.setName("Frederiksberg Allé");
		p.setColor(realEstateColorList.get(2)); // Index 2, grøn-ish
		p.setGroupID(realEstateGroups[2]);
		game.addSpace(p);

		Brewery b = new Brewery("Tuborg tapperi", 300, 150);
		b.setName("Tuborg tapperi");
		b.setCost(150);
		b.setRent(300);
		game.addSpace(b);
		
		p = new RealEstate(200,140,2000);
		p.setColor(realEstateColorList.get(2)); // Index 2, grøn-ish
		p.setGroupID(realEstateGroups[2]);
		p.setName("Bülowsvej");
		game.addSpace(p);

		p = new RealEstate(250,140,2000);
		p.setName("Gl. Kongevej");
		p.setColor(realEstateColorList.get(2)); // Index 2, grøn-ish
		p.setGroupID(realEstateGroups[2]);
		game.addSpace(p);

		s = new Ferry("D.F.D.S", 500, 200);
		game.addSpace(s);

		p = new RealEstate(300,180,2000);
		p.setName("Bernstorffsvej");
		p.setColor(realEstateColorList.get(3)); // Index 3, grå-ish
		p.setGroupID(realEstateGroups[3]);
		game.addSpace(p);

		chance = new Chance();
		chance.setName("Chance");
		game.addSpace(chance);

		p = new RealEstate(300,180,2000);
		p.setName("Hellerupvej");
		p.setColor(realEstateColorList.get(3)); // Index 3, grå-ish
		p.setGroupID(realEstateGroups[3]);
		game.addSpace(p);

		p = new RealEstate(350,180,2000);
		p.setName("Strandvejen");
		p.setColor(realEstateColorList.get(3)); // Index 3, grå-ish
		p.setGroupID(realEstateGroups[3]);
		game.addSpace(p);

 		Space h = new Space();
		h.setName("Helle");
		game.addSpace(h);

		p = new RealEstate(350,220,3000);
		p.setName("Trianglen");
		p.setColor(realEstateColorList.get(4)); // Index 4, rød
		p.setGroupID(realEstateGroups[4]);
		game.addSpace(p);


		chance = new Chance();
		chance.setName("Chance");
		game.addSpace(chance);

		p = new RealEstate(350,220,3000);
		p.setName("Østerbrogade");
		p.setColor(realEstateColorList.get(4)); // Index 4, rød
		p.setGroupID(realEstateGroups[4]);
		game.addSpace(p);

		p = new RealEstate(400,220,3000);
		p.setName("Grønningen");
		p.setColor(realEstateColorList.get(4)); // Index 4, rød
		p.setGroupID(realEstateGroups[4]);
		game.addSpace(p);

		s = new Ferry("Å. S.", 500, 200);
		game.addSpace(s);

		p = new RealEstate(450,260,3000);
		p.setName("Bredgade");
		p.setColor(realEstateColorList.get(5)); // Index 5, hvid
		p.setGroupID(realEstateGroups[5]);
		game.addSpace(p);

		p = new RealEstate(450,260,3000);
		p.setName("Kgs. Nytorv");
		p.setColor(realEstateColorList.get(5)); // Index 5, hvid
		p.setGroupID(realEstateGroups[5]);
		game.addSpace(p);


		b = new Brewery("Carlsberg", 200, 150);
		game.addSpace(b);

		p = new RealEstate(500,280,3000);
		p.setName("Østergade");
		p.setColor(realEstateColorList.get(5)); // Index 5, hvid
		p.setGroupID(realEstateGroups[5]);
		game.addSpace(p);


		GoToJail g = new GoToJail();
		g.setName("Go To Prison");
		game.addSpace(g);

		p = new RealEstate(550,300,4000);
		p.setName("Amagertorv");
		p.setColor(realEstateColorList.get(6)); // Index 6, gul
		p.setGroupID(realEstateGroups[6]);
		game.addSpace(p);


		p = new RealEstate(550,300,4000);
		p.setName("Vimmelstaftet");
		p.setColor(realEstateColorList.get(6)); // Index 6, gul
		p.setGroupID(realEstateGroups[6]);
		game.addSpace(p);


		chance = new Chance();
		chance.setName("Chance");
		game.addSpace(chance);

		p = new RealEstate(600,320,4000);
		p.setName("Nygade");
		p.setColor(realEstateColorList.get(6)); // Index 6, gul
		p.setGroupID(realEstateGroups[6]);
		game.addSpace(p);


		s = new Ferry("Bornholm", 500, 200);
		game.addSpace(s);

		chance = new Chance();
		chance.setName("Chance");
		game.addSpace(chance);

		p = new RealEstate(700,350,4000);
		p.setName("Frederiksberggade");
		p.setColor(realEstateColorList.get(7)); // Index 7, lilla
		p.setGroupID(realEstateGroups[7]);
		game.addSpace(p);


		StateTax st = new StateTax();
		st.setName("Pay 100$ to the bank");
		game.addSpace(st);


		p = new RealEstate(1000,400,4000);
		p.setName("Rådhuspladsen");
		p.setColor(realEstateColorList.get(7)); // Index 7, lilla
		p.setGroupID(realEstateGroups[7]);
		game.addSpace(p);


		List<Card> cards = new ArrayList<Card>();

		CardMove move = new CardMove();
		move.setTarget(game.getSpaces().get(9));
		move.setText("Move to Allégade!");
		cards.add(move);
		
		PayTax tax = new PayTax();
		tax.setText("Pay 10% income tax!");
		cards.add(tax);
		
		CardReceiveMoneyFromBank ba = new CardReceiveMoneyFromBank();
		ba.setText("You receive 100$ from the bank.");
		ba.setAmount(100);
		cards.add(ba);


		CardReceiveMoneyFromBank stockYield = new CardReceiveMoneyFromBank();
		stockYield.setText("You receive 200$ as you get yield from your Stocks!");
        stockYield.setAmount(200);
        cards.add(stockYield);

        CardPayMoneyToBank dental = new CardPayMoneyToBank();
        dental.setText("You pay 100$ as you receive your dental bill");
        dental.setAmount(100);
        cards.add(dental);


        CardPayMoneyToBank ticket = new CardPayMoneyToBank();
        ticket.setText("You pay 200$ as you get a parking ticket");
        ticket.setAmount(200);
        cards.add(ticket);


        CardPayMoneyToBank customs = new CardPayMoneyToBank();
        customs.setText("You pay 100$ as you get caught in customs with too many cigarettes ");
        customs.setAmount(100);
        cards.add(customs);


        CardPayMoneyToBank carInsurance = new CardPayMoneyToBank();
        carInsurance.setText("You pay 500$ for your car insurance");
        carInsurance.setAmount(500);
        cards.add(carInsurance);


        CardPayMoneyToBank carRepair = new CardPayMoneyToBank();
        carRepair.setText("Oh no your car breaks down. You pay 600$ for the repair of your car");
        carRepair.setAmount(600);
        cards.add(carRepair);

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

		GameController controller = new GameController(game);
		controller.makeGame(); //USE default game if you only want 3 players
		controller.initializeGUI();
		controller.play();

		}
	}
