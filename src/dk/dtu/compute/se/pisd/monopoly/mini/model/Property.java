package dk.dtu.compute.se.pisd.monopoly.mini.model;

import dk.dtu.compute.se.pisd.monopoly.mini.controller.GameController;
import dk.dtu.compute.se.pisd.monopoly.mini.model.exceptions.PlayerBrokeException;
import dk.dtu.compute.se.pisd.monopoly.mini.model.properties.Brewery;
import dk.dtu.compute.se.pisd.monopoly.mini.model.properties.Ferry;
import dk.dtu.compute.se.pisd.monopoly.mini.model.properties.RealEstate;

/**
 * Property represent all the spaces that can be owned by a player. Property could be abstract
 * since most properties a extended into either RealEstate, Ferry or Brewery.
 * Has the basic information needed for its child classes.
 * @author Magnus and Gustav.
 */
public class Property extends Space {


    private int cost;
    private int rent;
    private int baseRent;

    private Player owner = null;

    /**
     * Most important method for spaces doAction handles what happens to a player
     * when they land on the space.
     * @param controller the controller in charge of the game
     * @param player the involved player
     * @throws PlayerBrokeException If a player goes broke when landing on the property.
     * @author Magnus and Gustav.
     */
    @Override
    public void doAction(GameController controller, Player player) throws PlayerBrokeException{
        if (owner == null) {
            controller.offerToBuy(this, player);
        } else if (!owner.equals(player)) {
            // TODO also check whether the property is mortgaged
            if(this instanceof RealEstate){
                RealEstate realEstate = (RealEstate) this;
                realEstate.computeRent(realEstate);
            }

            if(this instanceof Ferry){
                Ferry ferry = (Ferry) this;
                setRent(ferry.getRent());
            }
            if (this instanceof Brewery){
                Brewery brewery = (Brewery) this;
                brewery.setRent(brewery.getMultiplier()*player.getSumOfDies());
            }
            controller.payment(player, rent, owner);
        }
    }

    /**
     * Returns the cost of this property.
     *
     * @return the cost of this property
     */
    public int getCost() {
        return cost;
    }

    /**
     * Sets the cost of this property.
     *
     * @param cost the new cost of this property
     */
    public void setCost(int cost) {
        this.cost = cost;
        notifyChange();
    }

    /**
     * Returns the rent to be payed for this property.
     *
     * @return the rent for this property
     */
    public int getRent() {
        return rent;
    }

    /**
     * Sets the rent for this property.
     *
     * @param rent the new rent for this property
     */
    public void setRent(int rent) {
        this.rent = rent;
        notifyChange();
    }

    /**
     * Returns the owner of this property. The value is <code>null</code>,
     * if the property currently does not have an owner.
     *
     * @return the owner of the property or <code>null</code>
     */
    public Player getOwner() {
        return owner;
    }

    /**
     * Sets the owner of this property  to the given owner (which can be
     * <code>null</code>).
     *
     * @param player the new owner of the property
     */
    public void setOwner(Player player) {
        this.owner = player;
        notifyChange();
    }

    public int getBaseRent() {
        return baseRent;
    }

    public void setBaseRent(int baseRent) {
        this.baseRent = baseRent;
    }
}
