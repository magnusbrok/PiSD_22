package dk.dtu.compute.se.pisd.monopoly.mini.model.properties;

import dk.dtu.compute.se.pisd.monopoly.mini.model.Player;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Property;

public class Brewery extends Property {

    private int sumOfDies;
    private int ownedBreweries;
    private int multiplier;



    public Brewery(String name, int baseRent, int cost){

        super.setBaseRent(baseRent);
        super.setRent(super.getBaseRent());
        super.setCost(cost);
        super.setName(name);
        multiplier = 100;
    }

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
