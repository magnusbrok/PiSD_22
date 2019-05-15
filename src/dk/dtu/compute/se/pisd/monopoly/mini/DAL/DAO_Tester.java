package dk.dtu.compute.se.pisd.monopoly.mini.DAL;

import dk.dtu.compute.se.pisd.monopoly.mini.MiniMonopoly;
import dk.dtu.compute.se.pisd.monopoly.mini.controller.GameController;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Game;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Player;
import dk.dtu.compute.se.pisd.monopoly.mini.model.exceptions.DALException;
import dk.dtu.compute.se.pisd.monopoly.mini.model.properties.RealEstate;

/**
 * A testing enviroment without GUI inplementation
 */
public class DAO_Tester {



    public static void main(String[] args) {
        GameDAO DAO = new GameDAO();
        MiniMonopoly monopoly = new MiniMonopoly();
            Game game = monopoly.createGame();
        GameController controller = new GameController(game);
        controller.makeDefaultGame();

            game.setGameID(10);
            game.getCurrentPlayer().setInPrison(true);
            game.getCurrentPlayer().setCurrentPosition(game.getSpaces().get(5));


        System.out.println("Test af DAO");

        RealEstate testRealEstate = (RealEstate) game.getSpaces().get(3);



/** Prints all players
        for (int i = 0 ; i < game.getPlayers().size()  ; i++) {


        Player p = game.getPlayers().get(i);
            System.out.println(p.getName() + " " + p.getBalance() +" " + p.isBroke() + " " + p.isInPrison());
            System.out.println("currPoss: " + p.getCurrentPosition().getName());


        }
 **/

/** prints all RealEstates
        for (int i = 0 ; i < game.getSpaces().size() ; i++ ){

            if (game.getSpaces().get(i) instanceof RealEstate){
                RealEstate realEstate = (RealEstate) game.getSpaces().get(i);
                System.out.println(realEstate.getName() + " " + realEstate.getIndex() + " " + realEstate.getHouses());

            }


        }
 **/

        try {
            game.setGameID(1);
            DAO.loadGame(game);
            System.out.println("got save");
            for (int i = 0 ; i < game.getPlayers().size() ; i++) {

                Player testPlayer = game.getPlayers().get(i);
                System.out.println(testPlayer.getName() + " " + testPlayer.getPlayerID());
                System.out.println(testPlayer.getBalance());
                System.out.println(testPlayer.isBroke());
                System.out.println(testPlayer.getCurrentPosition().getIndex());

            }

            RealEstate testProperty = (RealEstate) game.getSpaces().get(3);
            System.out.println(testProperty.getOwner().getName());
            System.out.println(testProperty.getHouses());



        }catch (DALException e){
            e.printStackTrace();
        }

    }


}
