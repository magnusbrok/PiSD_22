package dk.dtu.compute.se.pisd.monopoly.mini.DAL;

import dk.dtu.compute.se.pisd.monopoly.mini.model.Game;
import dk.dtu.compute.se.pisd.monopoly.mini.model.exceptions.DALException;

import java.util.List;

/**
 * Interface for our GameDAO has basic CRUD methods and getGameIDs.
 * @author Magnus
 */
public interface IGameDAO {

    void createGame(Game game) throws DALException;

    boolean updateGame(Game game) throws DALException;

    boolean loadGame (Game game) throws DALException;

    void deleteGame (Game game) throws DALException;

    List<Integer> getGameIds() throws DALException;
}
