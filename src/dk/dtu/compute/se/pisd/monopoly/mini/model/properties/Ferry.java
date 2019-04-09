package dk.dtu.compute.se.pisd.monopoly.mini.model.properties;

import dk.dtu.compute.se.pisd.monopoly.mini.model.Player;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Property;

public class Ferry extends Property {
    private int ownedFerries;



    public Ferry(String name, int baseRent, int cost){

        super.setBaseRent(baseRent);
        super.setRent(super.getBaseRent());
        super.setCost(cost);
        super.setName(name);

    }

    public void computeRent (Ferry ferry){
        if (ferry.getOwner() != null) {
            Player player = ferry.getOwner();
            ferry.setOwnedFerries(0);
            for (Property property : player.getOwnedProperties()){
                if (property instanceof Ferry){
                    ownedFerries++;
                }
            }
            ferry.setRent(getBaseRent()*ownedFerries);
            ferry.getOwner().setName(ferry.getOwner().getName());
        }

        notifyChange();

    }


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
