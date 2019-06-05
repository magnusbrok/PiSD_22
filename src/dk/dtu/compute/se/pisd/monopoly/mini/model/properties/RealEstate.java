package dk.dtu.compute.se.pisd.monopoly.mini.model.properties;

import dk.dtu.compute.se.pisd.monopoly.mini.model.Player;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Property;

import java.awt.*;

/**
 * Class for handling the streets of the matador game. This type of property
 * can be expanded on via building extra houses/hotels that further increase rent.
 * RealEstate are also gruped via colors since you only can buy houses if you own every realEstate of a given
 * Color group. We handle hotels like a 5th house since they work the same way, but when showed in the GUI
 * the player is asked about hotels, and a hotel figure is placed on the GUI Street.
 * @author Everyone.
 */
public class RealEstate extends Property{


        private int houses;
        private int housecost;
        private int maxhouses = 5;
        private Color color;
        private int groupID = 0; // ID for realEstate grouping by colors.


        /**
         * The constructor for creating a RealEstate object.
         * @param BaseRent The starting rent without houses.
         * @param cost The price of purchase
         * @param housecost The cost of adding houses/hotels.
         * @author Everyone.
         */
        public RealEstate (int BaseRent, int cost, int housecost){
            super.setBaseRent(BaseRent);
            super.setCost(cost);
            this.housecost = housecost;
            super.setRent(super.getBaseRent());

        }

        /**
         * Method called to build a house on a realEstate. Called through the gameController
         * after each players turn.
         * @param player The player wanting to build a house.
         * @param realEstate The realEstate to build on.
         * @author Everyone.
         */
        public void buildhouse (Player player, RealEstate realEstate) {
                if (player.getBalance() >= housecost && houses < maxhouses) {
                        houses++;
                        computeRent(realEstate);
                        player.payMoney(housecost);
                        notifyChange();
                }
        }

        /**
         * Method to sell houses on a property by reducing number of houses with 1 and repaying
         * half of the price of that house to the RealEstate owner. Called through the optainCash method
         * in the GameController.
         * @author Magnus.
         */
        public void sellHouse() {
                if (getHouses() > 1) {
                        setHouses(getHouses()-1);
                        getOwner().receiveMoney(getHousecost()/2);
                }

        }

        /**
         * Sells every house on a property, and repays half the money spent on them.
         * Used when a player trades a property since you aren't allowed to trade properties with houses on.
         * @author Magnus
         */
        public void sellAllHouses() {
                int soldHouses = getHouses();
                setHouses(0);
                getOwner().receiveMoney((getHousecost()/2)*soldHouses);
        }

        /**
         * Calculates the rent of RealEstate bases on the number of houses.
         * @param realEstate The realEstate that needs calculating.
         * @author Everyone.
         */
        public void computeRent(RealEstate realEstate) {

                int newRent = realEstate.getBaseRent() + (2 * houses * realEstate.getBaseRent());
                realEstate.setRent(newRent);
                notifyChange();

        }



        //Getters and setters

        public int getMaxhouses() {
                return maxhouses;
        }

        public void setMaxhouses(int maxhouses) {
                this.maxhouses = maxhouses;
                notifyChange();
        }

        public int getHouses() {
                return houses;
        }

        public void setHouses(int houses) {
                this.houses = houses;
                computeRent(this);
                notifyChange();
        }

        public int getHousecost() {
                return housecost;
        }

        public void setHousecost(int housecost) {
                this.housecost = housecost;
                notifyChange();
        }

        public Color getColor() {
                return color;
        }

        public void setColor(Color color) {
                this.color = color;
        }

        public int getGroupID() {
                return groupID;
        }

        public void setGroupID(int groupID) {
                this.groupID = groupID;
        }

}
