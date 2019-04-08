package dk.dtu.compute.se.pisd.monopoly.mini.DAL;

import dk.dtu.compute.se.pisd.monopoly.mini.model.Game;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Player;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Property;
import dk.dtu.compute.se.pisd.monopoly.mini.model.exceptions.DALException;
import dk.dtu.compute.se.pisd.monopoly.mini.model.properties.RealEstate;
import dk.dtu.compute.se.pisd.monopoly.mini.model.properties.Utility;

import java.awt.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GameDAO implements IGameDAO {

    private Connection createConnection() throws DALException {
        try {

            return DriverManager.getConnection("jdbc:mysql://ec2-52-30-211-3.eu-west-1.compute.amazonaws.com/s185037?"
                    + "user=s185037&password=7KZWv1fdgUsV6uSlvhLVb");
        } catch (SQLException e)    {
            throw new DALException(e.getMessage());
        }
    }

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
                }
                if (game.getSpaces().get(i) instanceof Utility) {
                    Utility utility = (Utility) game.getSpaces().get(i);
                    if (utility.getOwner() != null) {
                        statement = c.prepareStatement("INSERT INTO Property VALUES (?, ?, ?, ?)");
                        statement.setInt(1 , i);
                        statement.setInt(2, utility.getOwner().getPlayerID());
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

    @Override
    public boolean updateGame(Game game) throws DALException {
        try{
        deleteGame(game);
        createGame(game);
        }catch (DALException e){
            throw new DALException(e.getMessage());
        }

        return false;
    }

    @Override
    public boolean loadGame(Game game) throws DALException{
        try (Connection c = createConnection()){

            //TODO make players now only works with 3 players corruntly works could use some trimming.
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


        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
        return true;
    }

    @Override
    public void deleteGame(Game game) throws DALException {
        try(Connection c = createConnection()){
            PreparedStatement statement = c.prepareStatement("DELETE FROM Game WHERE g_ID = ?");
            statement.setInt(1,game.getGameID());
            statement.executeUpdate();

            statement = c.prepareStatement("DELETE FROM Player WHERE g_ID = ?");
            statement.setInt(1,game.getGameID());
            statement.executeUpdate();

            statement = c.prepareStatement("DELETE FROM Property WHERE g_ID = ?");
            statement.setInt(1,game.getGameID());
            statement.executeUpdate();

        }catch(SQLException e){
            throw new DALException(e.getMessage());
        }
    }

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

    private void makePlayerFromResultSet(ResultSet resultSet, Game game){

        List<Color> pColor = new ArrayList<>();
        pColor.add(new Color(255,0,0));
        pColor.add(new Color(0,255,0));
        pColor.add(new Color(0,0,255));
        pColor.add(new Color(255,255,0));
        pColor.add(new Color(0,255,255));
        pColor.add(new Color(255,0,255));

        try {
            Player player = new Player();
            player.setPlayerID(resultSet.getInt("pl_ID"));
            player.setName(resultSet.getString("name"));
            player.setCurrentPosition(game.getSpaces().get(resultSet.getInt("position")));
            player.setBalance(resultSet.getInt("balance"));
            player.setColor(pColor.get(player.getPlayerID()-1));
            if (resultSet.getInt("inprison") == 1) {
                player.setInPrison(true);
            }
            if (resultSet.getInt("broke") == 1) {
                player.setBroke(true);
            }
            game.addPlayer(player);

/**
            int playerID = resultSet.getInt("pl_ID");
            Player player = game.getPlayers().get(playerID - 1);
            player.setPlayerID(playerID);
            player.setName(resultSet.getString("name"));
            player.setCurrentPosition(game.getSpaces().get(resultSet.getInt("position")));
            player.setBalance(resultSet.getInt("balance"));
            if (resultSet.getInt("inprison") == 1) {
                player.setInPrison(true);
            }
            if (resultSet.getInt("broke") == 1) {
                player.setBroke(true);
            }
 **/
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    private void makePropertyFromResultSet(ResultSet resultSet, Game game){
        try{
            Property property = (Property) game.getSpaces().get(resultSet.getInt("pr_ID"));
            Player player = game.getPlayers().get(resultSet.getInt("pl_ID")-1);

            if (property instanceof RealEstate) {
                RealEstate realEstate = (RealEstate) property;
                realEstate.setOwner(player);
                player.addOwnedProperty(realEstate);
                realEstate.setHouses(resultSet.getInt("houses"));
            }
            if (property instanceof Utility) {
                Utility utility = (Utility) property;
                utility.setOwner(player);
                player.addOwnedProperty(utility);
            }


        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
