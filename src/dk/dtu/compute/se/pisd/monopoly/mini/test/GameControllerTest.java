package dk.dtu.compute.se.pisd.monopoly.mini.test;

import dk.dtu.compute.se.pisd.monopoly.mini.controller.GameController;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Game;
import org.junit.jupiter.api.Test;

import static dk.dtu.compute.se.pisd.monopoly.mini.MiniMonopoly.createGame;

class GameControllerTest {
    private Game game;
    private GameController controller;


    @Test
    void initializeGame() {
        game = createGame();
        controller = new GameController(game);
        controller.makeDefaultGame();

    }
    @Test
    void makeMove() {




    }

    @Test
    void houseOffer() {
    }

    @Test
    void moveToSpace() {
    }

    @Test
    void takeChanceCard() {
    }

    @Test
    void returnChanceCardToDeck() {
    }

    @Test
    void obtainCash() {
    }

    @Test
    void payment() {
    }

    @Test
    void paymentFromBank() {
    }

    @Test
    void paymentToBank() {
    }

    @Test
    void playerBrokeTo() {
    }

    @Test
    void playerBrokeToBank() {
    }
}