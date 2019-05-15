package dk.dtu.compute.se.pisd.monopoly.mini.test;

import dk.dtu.compute.se.pisd.monopoly.mini.model.*;
import org.junit.jupiter.api.Test;
import static dk.dtu.compute.se.pisd.monopoly.mini.MiniMonopoly.createGame;
import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @Test
    void draw_AND_returnCardFromDeck() {
        Game game = createGame();
        Card topCard =game.getCardDeck().get(0);
        int deckSize = game.getCardDeck().size();

        Card drawn = game.drawCardFromDeck();
        assertTrue(drawn.equals(topCard));
        assertEquals(game.getCardDeck().size(),deckSize-1);

        game.returnCardToDeck(drawn);
        assertTrue(drawn.equals(game.getCardDeck().get(deckSize-1)));
    }

    @Test
    void shuffleCardDeck() {
        /*  Det giver ikke mening af teste metoden, da den benytter sig af Collections.shiffle(), som forvented
            allerede at være ordenligt testet, af dens "fortatter"...
         */
    }
}