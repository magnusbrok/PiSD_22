package dk.dtu.compute.se.pisd.monopoly.mini.DAL;

import dk.dtu.compute.se.pisd.monopoly.mini.MiniMonopoly;
import dk.dtu.compute.se.pisd.monopoly.mini.controller.GameController;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Game;
import dk.dtu.compute.se.pisd.monopoly.mini.model.exceptions.DALException;
import dk.dtu.compute.se.pisd.monopoly.mini.model.properties.RealEstate;

/**
 * A testing enviroment for testing the DAO or other things.
 */
public class DAO_Tester {



    public static void main(String[] args) {
        GameDAO DAO = new GameDAO();
        MiniMonopoly monopoly = new MiniMonopoly();
            Game game = monopoly.createGame();
        GameController controller = new GameController(game);
        controller.makeDefaultGame();

            game.getCurrentPlayer().setInPrison(true);
            game.getCurrentPlayer().setCurrentPosition(game.getSpaces().get(5));


        System.out.println("Test af DAO");

        RealEstate testRealEstate = (RealEstate) game.getSpaces().get(3);


        try {
            game.setGameID(1);
            DAO.loadGame(game);
            System.out.println("got save");

        }catch (DALException e){
            e.printStackTrace();
        }

    }


}
