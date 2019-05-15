package dk.dtu.compute.se.pisd.monopoly.mini.DAL;

import dk.dtu.compute.se.pisd.monopoly.mini.model.Game;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Player;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Property;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Space;
import dk.dtu.compute.se.pisd.monopoly.mini.model.exceptions.DALException;
import dk.dtu.compute.se.pisd.monopoly.mini.model.properties.RealEstate;

import java.awt.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Data acces object responsible for handling database implementation
 * @author Magnus and Siff
 */
public class GameDAO implements IGameDAO {
    private final String Port = ":";
    private final String dbAdress = "jdbc:mysql://ec2-52-30-211-3.eu-west-1.compute.amazonaws.com"+ Port + "/";
    private final String dbUser = "s185037";
    private final String dbPassWord = "7KZWv1fdgUsV6uSlvhLVb";

    /**
     * Method for connecting to the database, is called in most sub methods.
     * @return returns the active connection.
     * @throws DALException handles errors with DAL.
     */
    private Connection createConnection() throws DALException {
        try {

            return DriverManager.getConnection(dbAdress + dbUser + "?"
                    + "user=" + dbUser + "&password="+dbPassWord);
        } catch (SQLException e)    {
            throw new DALException(e.getMessage());
        }
    }

    /**
     * method for saving the game in the database. Collect the data from the current game
     * and then saves them in our 3 database tables.
     * @param game the current game that needs to be saved.
     * @throws DALException handles errors with DAL.
     */
    @Override
    public void createGame(Game game) throws DALException {

        LocalDate date = LocalDate.now();
        try (Connection c = createConnection()) {
            PreparedStatement statement = c.prepareStatement("INSERT INTO Game VALUES (?, ?, ?)");
            statement.setInt(1 , game.getGameID());
            statement.setString(2, date.toString());
            statement.setInt(3, game.getCurrentPlayer().getPlayerID()); //index starts at 0
            statement.executeUpdate();

           for (int i = 0 ; i < game.getPlayers().size() ; i++) {
                Player player = game.getPlayers().get(i);
                statement = c.prepareStatement("INSERT INTO Player VALUES (?, ?, ?, ?, ?, ?, ?)");
                statement.setInt(1 , player.getPlayerID());
                statement.setString(2, player.getName());
                statement.setInt(3, player.getCurrentPosition().getIndex()); //index starts
                statement.setInt(4, player.getBalance());
                statement.setBoolean(5, player.isInPrison());
                statement.setBoolean(6, player.isBroke());
                statement.setInt(7, game.getGameID());
                statement.executeUpdate();

           }
           for (int i = 0 ; i < game.getSpaces().size() ; i++) {

                if (game.getSpaces().get(i) instanceof RealEstate) {
                    RealEstate realEstate = (RealEstate) game.getSpaces().get(i);
                    if (realEstate.getOwner() != null) {
                    statement = c.prepareStatement("INSERT INTO Property VALUES (?, ?, ?, ?)");
                    statement.setInt(1 , i);
                    statement.setInt(2, realEstate.getOwner().getPlayerID());
                    statement.setInt(3, realEstate.getHouses());
                    statement.setInt(4, game.getGameID());
                    statement.executeUpdate();
                    }
                } else if (game.getSpaces().get(i) instanceof Property){
                    Property property = (Property) game.getSpaces().get(i);
                        if (property.getOwner() != null) {
                            statement = c.prepareStatement("INSERT INTO Property VALUES (?, ?, ?, ?)");
                            statement.setInt(1 , i);
                            statement.setInt(2, property.getOwner().getPlayerID());
                            statement.setInt(3, 0);
                            statement.setInt(4, game.getGameID());
                            statement.executeUpdate();
                        }
                    }
                }
        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
    }

    /**
     * Simple method that can be called if one wants to overwrite a existing save with another.
     * @param game the game that wants to be saved.
     * @return returns true if method worked as intented.
     * @throws DALException handles errors with DAL.
     */
    @Override
    public boolean updateGame(Game game) throws DALException {
        try{
        deleteGame(game);
        createGame(game);
        }catch (DALException e){
            throw new DALException(e.getMessage());
        }

        return true;
    }

    /**
     * method called for loading a game uses sub method makePlayer and makeProperty from resultset.
     * @param game The game that needs to be loaded
     * @return the loaded game
     * @throws DALException handles errors with DAL.
     */
    @Override
    public boolean loadGame(Game game) throws DALException{
        try (Connection c = createConnection()){

            //TODO corruntly works could use some trimming.
            PreparedStatement statement = c.prepareStatement("SELECT * FROM Player WHERE g_ID = ?");
            statement.setInt(1, game.getGameID());
            ResultSet resultSet = statement.executeQuery();


            while(resultSet.next()){
                makePlayerFromResultSet(resultSet, game);
            }
            // sets currentplayer
            statement = c.prepareStatement("SELECT * FROM Game WHERE g_ID = ?");
            statement.setInt(1, game.getGameID());
            resultSet = statement.executeQuery();
            resultSet.next();
            int playerID = resultSet.getInt("currentPlayer");
            Player player = game.getPlayers().get(playerID-1);
            game.setCurrentPlayer(player);


            statement = c.prepareStatement("SELECT * FROM Property WHERE g_ID = ?");
            statement.setInt(1, game.getGameID());
            resultSet = statement.executeQuery();
            while(resultSet.next()){
                makePropertyFromResultSet(resultSet, game);
            }
            for (Space space : game.getSpaces()) {
                space.setName(space.getName());
            }




        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
        return true;
    }

    /**
     * Removes a game from the database bases on the gameID. Removes data from all 3 tables
     * @param game the game that needs to be removed.
     * @throws DALException handles errors with DAL.
     */
    @Override
    public void deleteGame(Game game) throws DALException {
        try(Connection c = createConnection()){

            PreparedStatement statement = c.prepareStatement("DELETE FROM Game WHERE g_ID = ?");
            statement.setInt(1,game.getGameID());
            statement.executeUpdate();

        }catch(SQLException e){
            throw new DALException(e.getMessage());
        }
    }

    /**
     * collects all the gameIDs stored in the database.
     * Used for showing user which games are availible to load.
     * @return returns a List of gameIds (int)
     * @throws DALException handles errors with DAL.
     */
    @Override
    public List<Integer> getGameIds() throws DALException {
        try(Connection c = createConnection()){
            Statement statement = c.createStatement();
            ResultSet resultset = statement.executeQuery("SELECT g_ID FROM Game");

            List<Integer> gameList = new ArrayList<Integer>();
            while(resultset.next()){
                gameList.add(resultset.getInt("g_ID"));
            }

            return gameList;

        }catch (SQLException e){
            throw new DALException(e.getMessage());
        }
    }

    /**
     * Submethod for making each player collected via a resultset.
     * @param resultSet the resultset collected from loadGame method
     * @param game the Game that need changing.
     */
    private void makePlayerFromResultSet(ResultSet resultSet, Game game){

        List<Color> pColor = game.getColors();
        try {
            Player player = new Player();
            player.setPlayerID(resultSet.getInt("pl_ID"));
            player.setName(resultSet.getString("name"));
            player.setCurrentPosition(game.getSpaces().get(resultSet.getInt("position")));
            player.setBalance(resultSet.getInt("balance"));
            player.setColor(pColor.get(player.getPlayerID()-1));
            player.setInPrison(resultSet.getBoolean("inprison"));
            player.setBroke(resultSet.getBoolean("broke"));
            game.addPlayer(player);


        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    /**
     * Submethod for making each property collected via a resultset.
     * @param resultSet the resultset collected from loadGame method
     * @param game the Game that need changing.
     */
    private void makePropertyFromResultSet(ResultSet resultSet, Game game){
        try{
            Property property = (Property) game.getSpaces().get(resultSet.getInt("pr_ID"));
            Player player = game.getPlayers().get(resultSet.getInt("pl_ID")-1);

            if (property instanceof RealEstate) {
                RealEstate realEstate = (RealEstate) property;
                realEstate.setOwner(player);
                player.addOwnedProperty(realEstate);
                realEstate.setHouses(resultSet.getInt("houses"));
            } else {
                property.setOwner(player);
                player.addOwnedProperty(property);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
