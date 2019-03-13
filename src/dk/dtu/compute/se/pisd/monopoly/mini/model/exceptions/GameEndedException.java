package dk.dtu.compute.se.pisd.monopoly.mini.model.exceptions;

import dk.dtu.compute.se.pisd.monopoly.mini.model.Game;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Player;

public class GameEndedException extends Exception {
    private Game game;

    public GameEndedException(Game game) {
        super("The game have ended");
        this.game = game;
    }

    /**
     * Returns the Game that ended
     *
     * @return the Game that ended
     */
    Game getGame() {
        return game;
    }

}