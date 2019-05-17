package dk.dtu.compute.se.pisd.monopoly.mini.test;

import dk.dtu.compute.se.pisd.monopoly.mini.model.Player;
import dk.dtu.compute.se.pisd.monopoly.mini.model.properties.RealEstate;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test of methods relating to RealEstate class.
 * @author Magnus
 */
public class RealEstateTest {
    private RealEstate testRealEstate = new RealEstate(100,1000, 150);
    private Player testPlayer = new Player();

    @Test
    void computeRent() {
        testRealEstate.setHouses(testRealEstate.getMaxhouses());
        testRealEstate.computeRent(testRealEstate);
        int expected = testRealEstate.getBaseRent() + (2 * testRealEstate.getHouses() * testRealEstate.getBaseRent());

        assertEquals(expected, testRealEstate.getRent());

    }

    @Test
    void SellAndBuyHouses() {
        testRealEstate.setOwner(testPlayer);
        testRealEstate.setHouses(testRealEstate.getMaxhouses());

        int expected = testPlayer.getBalance()+(testRealEstate.getHousecost()/2)*testRealEstate.getMaxhouses();

        testRealEstate.sellAllHouses();

        assertEquals(0, testRealEstate.getHouses());
        assertEquals(expected,testPlayer.getBalance());

        expected = testPlayer.getBalance()-testRealEstate.getHousecost();
        testRealEstate.buildhouse(testPlayer,testRealEstate);

        assertEquals(1, testRealEstate.getHouses());
        assertEquals(expected,testPlayer.getBalance());
    }
}
