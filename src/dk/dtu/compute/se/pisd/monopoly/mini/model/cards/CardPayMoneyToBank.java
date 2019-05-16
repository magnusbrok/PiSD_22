package dk.dtu.compute.se.pisd.monopoly.mini.model.cards;

import dk.dtu.compute.se.pisd.monopoly.mini.controller.GameController;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Card;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Player;
import dk.dtu.compute.se.pisd.monopoly.mini.model.exceptions.GameEndedException;
import dk.dtu.compute.se.pisd.monopoly.mini.model.exceptions.PlayerBrokeException;

/**
 * Class for a card that forces a player to pay money to the bank.
 * @author Mads
 */
public class CardPayMoneyToBank extends Card {
    private int amount;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void doAction(GameController controller, Player player) throws PlayerBrokeException, GameEndedException {
        try {
            controller.paymentToBank(player, amount);
        } finally {
            super.doAction(controller, player);
        }
    }
}
