package dk.dtu.compute.se.pisd.monopoly.mini.model.properties;

import dk.dtu.compute.se.pisd.monopoly.mini.model.Player;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Property;

/**
 * Special type of property. Rent is fixed but increases each time a player owns an additional ferry.
 * @author Gustav.
 */
public class Ferry extends Property {
    private int ownedFerries;


    /**
     * Constructor for the Ferry.
     * @param name The name of the Ferry.
     * @param baseRent The starting rent.
     * @param cost The cost of purchasing the Ferry.
     * @author Gustav.
     */
    public Ferry(String name, int baseRent, int cost){

        super.setBaseRent(baseRent);
        super.setRent(super.getBaseRent());
        super.setCost(cost);
        super.setName(name);

    }

    /**
     * Method to calculate and increase the rent each time a player owns a additonal ferry.
     * @param ferry the ferry who's rent need calculating.
     * @author Gustav.
     */
    public void computeRent (Ferry ferry){
        if (ferry.getOwner() != null) {
            Player player = ferry.getOwner();
            ferry.setOwnedFerries(0);
            for (Property property : player.getOwnedProperties()){
                if (property instanceof Ferry){
                    ownedFerries++;
                }
            }
            if (ownedFerries == 1) {
                setRent(getBaseRent());
            }
            if (ownedFerries == 2) {
                setRent(getBaseRent()*2);
            }
            if (ownedFerries == 3) {
                setRent(getBaseRent()*4);
            }
            if (ownedFerries == 4){
                setRent(getBaseRent()*8);
            }
        }
        notifyChange();
    }

    // Getters and setters

    public void setOwnedFerries(int ownedFerries) {
        this.ownedFerries = ownedFerries;
    }

    public int getOwnedFerries(Player player) {
            ownedFerries = 0;
        for (Property property : player.getOwnedProperties()){
            if (property instanceof Ferry){
                ownedFerries++;
            }
        }
        return ownedFerries;
    }






}
