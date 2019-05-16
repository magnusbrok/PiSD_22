package dk.dtu.compute.se.pisd.monopoly.mini.model;


import dk.dtu.compute.se.pisd.monopoly.mini.controller.GameController;
import dk.dtu.compute.se.pisd.monopoly.mini.model.exceptions.PlayerBrokeException;

/**
 * The space that charges tax on players when they land on it.
 * This could be expanded to instead charge based on a players assets.
 * @author Ida.
 */
public class StateTax extends Space {

    /**
     * The action when a player lands on the space.
     * @param controller the controller in charge of the game
     * @param player the involved player
     * @throws PlayerBrokeException If a player goes broke when paying tax.
     * @author Ida
     */
    @Override
    public void doAction(GameController controller, Player player) throws PlayerBrokeException{
        controller.paymentToBank(player,100);
}
}