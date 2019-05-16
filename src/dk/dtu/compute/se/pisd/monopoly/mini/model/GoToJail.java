package dk.dtu.compute.se.pisd.monopoly.mini.model;

import dk.dtu.compute.se.pisd.monopoly.mini.controller.GameController;
import dk.dtu.compute.se.pisd.monopoly.mini.model.exceptions.GameEndedException;
import dk.dtu.compute.se.pisd.monopoly.mini.model.exceptions.PlayerBrokeException;

/**
 * @author Ida - s185041
 */

public class GoToJail extends Space {

    @Override
    public void doAction(GameController controller, Player player) throws PlayerBrokeException, GameEndedException {
            controller.gotoJail(player);

    }
}
