package dk.dtu.compute.se.pisd.monopoly.mini.view;

import dk.dtu.compute.se.pisd.designpatterns.Observer;
import dk.dtu.compute.se.pisd.designpatterns.Subject;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Game;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Player;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Property;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Space;
import dk.dtu.compute.se.pisd.monopoly.mini.model.properties.Brewery;
import dk.dtu.compute.se.pisd.monopoly.mini.model.properties.Ferry;
import dk.dtu.compute.se.pisd.monopoly.mini.model.properties.RealEstate;
import gui_fields.*;
import gui_fields.GUI_Car.Pattern;
import gui_fields.GUI_Car.Type;
import gui_main.GUI;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * This class implements a view on the Monopoly game based
 * on the original Matador GUI; it serves as a kind of
 * adapter to the Matador GUI. This class realizes the
 * MVC-principle on top of the Matador GUI. In particular,
 * the view implements an observer of the model in the
 * sense of the MVC-principle, which updates the GUI when
 * the state of the game (model) changes.
 * 
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 */
public class View implements Observer {

	private Game game;
	private GUI gui;
	
	private Map<Player,GUI_Player> player2GuiPlayer = new HashMap<Player,GUI_Player>();
	private Map<Player,Integer> player2position = new HashMap<Player,Integer>();
	private Map<Space,GUI_Field> space2GuiField = new HashMap<Space,GUI_Field>();
	private Map<Player,PlayerPanel> player2Playerpanel = new HashMap<>();

	
	private boolean disposed = false;
	
	/**
	 * Constructor for the view of a game based on a game and an already
	 * running Matador GUI.
	 * 
	 * @param game the game
	 * @param gui the GUI
	 */
	public View(Game game, GUI gui) {
		this.game = game;
		this.gui = gui;
		GUI_Field[] guiFields = gui.getFields();
		
		int i = 0;
		for (Space space: game.getSpaces()) {

			// TODO, here we assume that the games fields fit to the GUI's fields;
			// the GUI fields should actually be created according to the game's
			// fields
			space2GuiField.put(space, guiFields[i++]);
			
			// TODO we should also register with the properties as observer; but
			// the current version does not update anything for the spaces, so we do not
			// register the view as an observer for now
			if (space instanceof Property ) {
				space.attach(this);
			}

		}
		
		// create the players in the GUI
		for (Player player: game.getPlayers()) {
			GUI_Car car = new GUI_Car(player.getColor(), Color.black, Type.CAR, Pattern.FILL);
			GUI_Player guiPlayer = new GUI_Player(player.getName(), player.getBalance(), car);
			player2GuiPlayer.put(player, guiPlayer);
			gui.addPlayer(guiPlayer);

			player2Playerpanel.put(player, new PlayerPanel(game, player));



			// player2position.put(player, 0);
			
			// register this view with the player as an observer, in order to update the
			// player's state in the GUI
			player.attach(this);
			
			updatePlayer(player);
		}
	}
	
	@Override
	public void update(Subject subject) {
		if (!disposed) {
			if (subject instanceof Player) {
				updatePlayer((Player) subject);
			}

			if (subject instanceof RealEstate) {
				updateProperty((RealEstate) subject);
			}

			if (subject instanceof Ferry) {
				updateProperty((Ferry) subject);
			}

			if (subject instanceof Brewery) {
				updateProperty((Brewery) subject);
			}
		}
	}
	
	/**
	 * This method updates a player's state in the GUI. Right now, this
	 * concerns the players position and balance only. But, this should
	 * also include other information (being i prison, available cards,
	 * ...)
	 * 
	 * @param player the player who's state is to be updated
	 */
	private void updatePlayer(Player player) {
		GUI_Player guiPlayer = this.player2GuiPlayer.get(player);

		if (guiPlayer != null) {
			guiPlayer.setBalance(player.getBalance());

			GUI_Field[] guiFields = gui.getFields();
			Integer oldPosition = player2position.get(player);
			if (oldPosition != null && oldPosition < guiFields.length) {
				guiFields[oldPosition].setCar(guiPlayer, false);
			}
			int pos = player.getCurrentPosition().getIndex();
			if (pos < guiFields.length) {
				player2position.put(player,pos);
				guiFields[pos].setCar(guiPlayer, true);
			}

			String name = player.getName();
			if (player.isBroke()) {
			} else if (player.isInPrison()) {
				guiPlayer.setName(player.getName() + " (in prison)");
			}
			if (!name.equals(guiPlayer.getName())) {
				guiPlayer.setName(name);
			}
			player2Playerpanel.get(player).update();



		}
	}

	private void updateProperty (Property property) {
		GUI_Field guiField = this.space2GuiField.get(property);
		if (guiField instanceof GUI_Ownable) {
			GUI_Ownable guiProperty = (GUI_Ownable) guiField;
			Player owner = property.getOwner();
			if (owner != null) {
				guiProperty.setBorder(owner.getColor());
				guiProperty.setOwnerName(owner.getName());

				if (property instanceof RealEstate) {
					RealEstate realEstate = ((RealEstate) property);

					if (guiProperty instanceof GUI_Street && realEstate.getHouses() < 5) {
						((GUI_Street) guiProperty).setHouses(realEstate.getHouses());
					} else {
						((GUI_Street) guiProperty).setHouses(0);
						((GUI_Street) guiProperty).setHotel(true);
					}
				}
				player2Playerpanel.get(owner).update();
			}
			else {
				guiProperty.setBorder(null);
				guiProperty.setOwnerName(null);
			}

		}

	}
	
	public void dispose() {
		if (!disposed) {
			disposed = true;
			for (Player player: game.getPlayers()) {
				// unregister from the player as observer
				player.detach(this);
			}
			for (Space space: game.getSpaces()) {
				space.detach(this);
			}
		}
	}

}
