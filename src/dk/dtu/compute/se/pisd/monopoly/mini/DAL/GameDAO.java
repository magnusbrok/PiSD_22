package dk.dtu.compute.se.pisd.monopoly.mini.DAL;

import dk.dtu.compute.se.pisd.monopoly.mini.model.Game;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Player;
import dk.dtu.compute.se.pisd.monopoly.mini.model.exceptions.DALException;
import dk.dtu.compute.se.pisd.monopoly.mini.model.properties.RealEstate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
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

            //TODO Insert Game into GameTable
            PreparedStatement statement = c.prepareStatement("INSERT INTO Game VALUES (?, ?, ?)");
            statement.setInt(1 , game.getGameID());
            statement.setString(2, date.toString());
            statement.setInt(2, 3);
            statement.setInt(3, game.getCurrentPlayer().getPlayerID()); //index starts at 0
            statement.executeUpdate();
            //TODO Insert Players into PlayerTable

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

            //TODO insert properties into properties

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
            }





        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
    }

    @Override
    public boolean updateGame(Game game) {
        return false;
    }

    @Override
    public boolean loadGame(Game game) {
        return false;
    }

    @Override
    public List<Integer> getGameIds() {
        return null;
    }


}
