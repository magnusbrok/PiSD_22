package dk.dtu.compute.se.pisd.monopoly.mini.DAL;

import dk.dtu.compute.se.pisd.monopoly.mini.model.Game;
import dk.dtu.compute.se.pisd.monopoly.mini.model.exceptions.DALException;

import java.util.List;

public interface IGameDAO {

    void createGame(Game game) throws DALException;

    boolean updateGame(Game game);

    boolean loadGame (Game game) throws DALException;

    List<Integer> getGameIds();
}
