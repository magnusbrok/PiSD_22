package dk.dtu.compute.se.pisd.monopoly.mini.DAL;

import dk.dtu.compute.se.pisd.monopoly.mini.model.Game;
import dk.dtu.compute.se.pisd.monopoly.mini.model.exceptions.DALException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
        try (Connection c = createConnection()) {

            //TODO Insert Game into GameTable
            PreparedStatement statement = c.prepareStatement("INSERT INTO Game VALUES (?, ?, ?)");
            statement.setInt(1 , 0);
            statement.setString(2, "test");
            statement.setInt(3, game.getCurrentPlayer().getCurrentPosition().getIndex()); //index starts at 0

            //TODO Insert Player into PlayerTable
/**
            for (int i = 0 ; i < game.getPlayers().size() ; i++) {
                Player player = game.getPlayers().get(i);
            statement = c.prepareStatement("INSERT INTO Player VALUES (?, ?, ?, ?, ?, ?, )");
            statement.setInt(1 , i);
            statement.setString(2, player.getName());
            statement.setInt(3, player.getCurrentPosition().getIndex()); //index starts
            statement.setInt(4, player.getBalance());
            statement.setBoolean(5, player.isInPrison());
            statement.setBoolean(6, player.isBroke());
            //statement.setInt(7, 0);

            statement.executeUpdate();

            }
**/
            //TODO insert property into properties


            statement.executeUpdate();


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
