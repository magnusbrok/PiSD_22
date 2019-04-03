package dk.dtu.compute.se.pisd.monopoly.mini.DAL;

import dk.dtu.compute.se.pisd.monopoly.mini.MiniMonopoly;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Game;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Player;
import dk.dtu.compute.se.pisd.monopoly.mini.model.exceptions.DALException;
import dk.dtu.compute.se.pisd.monopoly.mini.model.properties.RealEstate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class DAO_Tester {



    public static void main(String[] args) {
        GameDAO DAO = new GameDAO();
        MiniMonopoly monopoly = new MiniMonopoly();
            Game game = monopoly.createGame();
            monopoly.createPlayers(game);
            game.setGameID(1);
            game.getCurrentPlayer().setInPrison(true);
            game.getCurrentPlayer().setCurrentPosition(game.getSpaces().get(5));

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        LocalDate date = LocalDate.now();
        String dato = date.toString();

        System.out.println(dato);
        System.out.println("Test af DAO");
        //System.out.println(game.getPlayers().size());

        game.getSpaces().get(1).setName("Pølsevej");
        RealEstate test = (RealEstate) game.getSpaces().get(3);
        test.setHouses(3);
        test.setOwner(game.getCurrentPlayer());
        test = (RealEstate) game.getSpaces().get(1);
        test.setOwner(game.getPlayers().get(2));


        for (int i = 0 ; i < game.getPlayers().size()  ; i++) {


        Player p = game.getPlayers().get(i);
            System.out.println(p.getName() + " " + p.getBalance() +" " + p.isBroke() + " " + p.isInPrison());
            System.out.println("currPoss: " + p.getCurrentPosition().getName());


        }

        for (int i = 0 ; i < game.getSpaces().size() ; i++ ){

            if (game.getSpaces().get(i) instanceof RealEstate){
                RealEstate realEstate = (RealEstate) game.getSpaces().get(i);
                System.out.println(realEstate.getName() + " " + realEstate.getIndex() + " " + realEstate.getHouses());

            }


        }

        try {
        DAO.createGame(game);
            System.out.println("made save");

        }catch (DALException e){
            e.printStackTrace();
        }

    }


}
