package dk.dtu.compute.se.pisd.monopoly.mini.model.properties;

        import dk.dtu.compute.se.pisd.monopoly.mini.model.Player;
        import dk.dtu.compute.se.pisd.monopoly.mini.model.Property;

        import java.awt.*;

/**
 * A specific property, which represents real estate on which houses
 * and hotels can be built. Note that this class does not have details
 * yet and needs to be implemented.
 *
 * @author Everyone
 *
 */
public class RealEstate extends Property{


        private int houses;
        private int housecost;
        private int maxhouses = 5;
        private Color color;
        private int groupID = 0; // ID for realEstate grouping by colors.




        public RealEstate (int BaseRent, int cost, int housecost){
            super.setBaseRent(BaseRent);
            super.setCost(cost);
            this.housecost = housecost;
            super.setRent(super.getBaseRent());

        }

        public void buildhouse (Player player, RealEstate realEstate) {
                if (player.getBalance() >= housecost && houses < maxhouses) {
                        houses++;
                        computeRent(realEstate);
                        player.payMoney(housecost);
                        notifyChange();
                }
        }

        public void sellHouse(Player player) {

        }

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
