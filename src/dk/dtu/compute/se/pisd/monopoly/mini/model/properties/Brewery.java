package dk.dtu.compute.se.pisd.monopoly.mini.model.properties;

import dk.dtu.compute.se.pisd.monopoly.mini.model.Player;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Property;

/**
 * A extension of the Property class. Breweries are unique properties where
 * the rent i calculated based on the sum of dice rolled by the player landing on it.
 * The rent i doubled if a player owns 2 breweries.
 * @author Gustav.
 */
public class Brewery extends Property {

    private int sumOfDies;
    private int ownedBreweries;
    private int multiplier;


    /**
     * Constructor for the brewery object.
     * @param name Of the brewery
     * @param baseRent the starting rent.
     * @param cost The cost to buy the brewery.
     * @author Gustav.
     */
    public Brewery(String name, int baseRent, int cost){

        super.setBaseRent(baseRent);
        super.setRent(super.getBaseRent());
        super.setCost(cost);
        super.setName(name);
        multiplier = 100;
    }

    /**
     * Calculator to compute the rent that must be payed.
     * @param Brewery the object whos rent needs calculating.
     * @author Gustav.
     */
    public void computeMultiplier (Brewery Brewery){
        if (Brewery.getOwner() != null) {
            Player player = Brewery.getOwner();
            Brewery.setOwnedBreweries(0);
            for (Property property : player.getOwnedProperties()){
                if (property instanceof Brewery){
                    ownedBreweries++;
                }
            }
            setMultiplier(ownedBreweries*multiplier);
        }

        notifyChange();
    }

// getters and setters.

    public void setSumOfDies(int sumOfDies) {
        this.sumOfDies = sumOfDies;
    }

    public int getSumOfDies() {
        return sumOfDies;
    }

    public void setOwnedBreweries(int ownedBreweries) {
        this.ownedBreweries = ownedBreweries;
    }

    public int getOwnedBreweries() {
        return ownedBreweries;
    }

    public int getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(int multiplier) {
        this.multiplier = multiplier;
    }
}
