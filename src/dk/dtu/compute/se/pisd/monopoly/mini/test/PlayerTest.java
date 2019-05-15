package dk.dtu.compute.se.pisd.monopoly.mini.test;

import dk.dtu.compute.se.pisd.monopoly.mini.model.*;
import dk.dtu.compute.se.pisd.monopoly.mini.model.cards.*;
import dk.dtu.compute.se.pisd.monopoly.mini.model.properties.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlayerTest {

    @Test
    void receiveMoney() {
        Player player = new Player();
        int startbalance = 4000;
        int recieved = 50;
        player.setBalance(startbalance);
        player.receiveMoney(recieved);

        assertEquals(startbalance+recieved,player.getBalance());
    }

    @Test
    void payMoney() {
        Player player = new Player();
        int startbalance = 4000;
        int payed = 50;
        player.setBalance(startbalance);
        player.payMoney(payed);

        assertEquals(startbalance-payed,player.getBalance());
    }

    @Test
    void add_removeOwnedProperty() {
        Player player = new Player();

        RealEstate realEstate = new RealEstate(5,120,100);
        Ferry ferry = new Ferry("Øresund", 50, 400);
        Brewery brewery = new Brewery("Tuborg tapperi", 10, 300);

        player.addOwnedProperty(realEstate);
        player.addOwnedProperty(ferry);
        player.addOwnedProperty(brewery);
        assertEquals(3,player.getOwnedProperties().size());

        player.removeOwnedProperty(realEstate);
        assertEquals(2,player.getOwnedProperties().size());

        player.removeAllProperties();
        assertEquals(0,player.getOwnedProperties().size());
    }

    @Test
    void removeOwnedCard() {
        Player player = new Player();

        List<Card> ownedCardsList = new ArrayList<>();
        CardMove move = new CardMove();
        PayTax tax = new PayTax();
        CardReceiveMoneyFromBank ba = new CardReceiveMoneyFromBank();
        ownedCardsList.add(move);
        ownedCardsList.add(tax);
        ownedCardsList.add(ba);

        player.setOwnedCards(ownedCardsList);
        assertEquals(3,player.getOwnedCards().size());

        player.removeOwnedCard(move);
        assertEquals(2,player.getOwnedCards().size());
    }
}