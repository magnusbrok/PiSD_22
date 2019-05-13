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
		
		RealEstate p = new RealEstate(5,120,100);
		p.setColor(realEstateColorList.get(0)); // Index 0, blå-ish
		p.setGroupID(realEstateGroups[0]);
		p.setName("Rødovrevej");
		game.addSpace(p);

		Chance chance = new Chance();
		chance.setName("Chance");
		game.addSpace(chance);
		
		p = new RealEstate(5,120,100);
		p.setColor(realEstateColorList.get(0)); // Index 0, blå-ish
		p.setGroupID(realEstateGroups[0]);
		p.setName("Hvidovrevej");
		game.addSpace(p);

		Tax t = new Tax();

		t.setName("Pay tax (10% on Cash)");
		game.addSpace(t);

		Ferry s = new Ferry("Øresund", 500, 200);
		game.addSpace(s);

		p = new RealEstate(10,200,100);
		p.setName("Roskildevej");
		p.setColor(realEstateColorList.get(1)); // Index 1, pink-ish
		p.setGroupID(realEstateGroups[1]);
		game.addSpace(p);

		chance = new Chance();
		chance.setName("Chance");
		game.addSpace(chance);
		
		p = new RealEstate(10,200,100);
		p.setColor(realEstateColorList.get(1)); // Index 1, pink-ish
		p.setGroupID(realEstateGroups[1]);
		p.setName("Valby Langgade");
		game.addSpace(p);

		p = new RealEstate(15,240,100);
		p.setName("Allegade");
		p.setColor(realEstateColorList.get(1)); // Index 1, pink-ish
		p.setGroupID(realEstateGroups[1]);
		game.addSpace(p);

		Space prison = new Space();
		prison.setName("Prison");
		game.addSpace(prison);
		
		p = new RealEstate(20,280,200);
		p.setName("Frederiksberg Allé");
		p.setColor(realEstateColorList.get(2)); // Index 2, grøn-ish
		p.setGroupID(realEstateGroups[2]);
		game.addSpace(p);

		Brewery b = new Brewery("Tuborg tapperi", 10, 300);
		b.setName("Tuborg tapperi");
		b.setCost(150);
		b.setRent(300);
		game.addSpace(b);
		
		p = new RealEstate(20,280,200);
		p.setColor(realEstateColorList.get(2)); // Index 2, grøn-ish
		p.setGroupID(realEstateGroups[2]);
		p.setName("Bulowsvej");
		game.addSpace(p);

		p = new RealEstate(25,280,200);
		p.setName("Gl. Kongevej");
		p.setColor(realEstateColorList.get(2)); // Index 2, grøn-ish
		p.setGroupID(realEstateGroups[2]);
		game.addSpace(p);

		s = new Ferry("D.F.D.S", 50, 400);
		game.addSpace(s);

		p = new RealEstate(30,360,200);
		p.setName("Bernstorffsvej");
		p.setColor(realEstateColorList.get(3)); // Index 3, grå-ish
		p.setGroupID(realEstateGroups[3]);
		game.addSpace(p);

		chance = new Chance();
		chance.setName("Chance");
		game.addSpace(chance);

		p = new RealEstate(30,360,200);
		p.setName("Hellerupvej");
		p.setColor(realEstateColorList.get(3)); // Index 3, grå-ish
		p.setGroupID(realEstateGroups[3]);
		game.addSpace(p);

		p = new RealEstate(35,400,200);
		p.setName("Strandvejen");
		p.setColor(realEstateColorList.get(3)); // Index 3, grå-ish
		p.setGroupID(realEstateGroups[3]);
		game.addSpace(p);

 		Space h = new Space();
		h.setName("Fri parkering");
		game.addSpace(h);

		p = new RealEstate(35,440,300);
		p.setName("Trianglen");
		p.setColor(realEstateColorList.get(4)); // Index 4, rød
		p.setGroupID(realEstateGroups[4]);
		game.addSpace(p);

		chance = new Chance();
		chance.setName("Chance");
		game.addSpace(chance);

		p = new RealEstate(35,440,300);
		p.setName("Østerbrogade");
		p.setColor(realEstateColorList.get(4)); // Index 4, rød
		p.setGroupID(realEstateGroups[4]);
		game.addSpace(p);

		p = new RealEstate(40,480,300);
		p.setName("Grønningen");
		p.setColor(realEstateColorList.get(4)); // Index 4, rød
		p.setGroupID(realEstateGroups[4]);
		game.addSpace(p);

		s = new Ferry("Å. S.", 50, 400);
		game.addSpace(s);

		p = new RealEstate(45,520,300);
		p.setName("Bredgade");
		p.setColor(realEstateColorList.get(5)); // Index 5, hvid
		p.setGroupID(realEstateGroups[5]);
		game.addSpace(p);

		p = new RealEstate(45,520,300);
		p.setName("Kgs. Nytorv");
		p.setColor(realEstateColorList.get(5)); // Index 5, hvid
		p.setGroupID(realEstateGroups[5]);
		game.addSpace(p);


		b = new Brewery("Carlsberg", 10, 300);
		game.addSpace(b);

		p = new RealEstate(50,560,300);
		p.setName("Østergade");
		p.setColor(realEstateColorList.get(5)); // Index 5, hvid
		p.setGroupID(realEstateGroups[5]);
		game.addSpace(p);


		GoToJail g = new GoToJail();
		g.setName("Gå fængsel");
		game.addSpace(g);

		p = new RealEstate(55,600,400);
		p.setName("Amagertorv");
		p.setColor(realEstateColorList.get(6)); // Index 6, gul
		p.setGroupID(realEstateGroups[6]);
		game.addSpace(p);


		p = new RealEstate(55,600,400);
		p.setName("Vimmelstaftet");
		p.setColor(realEstateColorList.get(6)); // Index 6, gul
		p.setGroupID(realEstateGroups[6]);
		game.addSpace(p);


		chance = new Chance();
		chance.setName("Chance");
		game.addSpace(chance);

		p = new RealEstate(60,640,400);
		p.setName("Nygade");
		p.setColor(realEstateColorList.get(6)); // Index 6, gul
		p.setGroupID(realEstateGroups[6]);
		game.addSpace(p);


		s = new Ferry("Bornholm", 50, 400);
		game.addSpace(s);

		chance = new Chance();
		chance.setName("Chance");
		game.addSpace(chance);

		p = new RealEstate(70,700,400);
		p.setName("Frederiksberggade");
		p.setColor(realEstateColorList.get(7)); // Index 7, lilla
		p.setGroupID(realEstateGroups[7]);
		game.addSpace(p);


		StateTax st = new StateTax();
		st.setName("Pay 100$ to the bank");
		game.addSpace(st);


		p = new RealEstate(100,800,400);
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
