package dk.dtu.compute.se.pisd.monopoly.mini.model.properties;

        import dk.dtu.compute.se.pisd.monopoly.mini.model.Player;
        import dk.dtu.compute.se.pisd.monopoly.mini.model.Property;

/**
 * A specific property, which represents real estate on which houses
 * and hotels can be built. Note that this class does not have details
 * yet and needs to be implemented.
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 */
public class RealEstate extends Property{


        private int houses;
        private int housecost;
        private int maxhouses = 4;

        public void buildhouse (Player player, RealEstate realEstate) {
                if (player.getBalance() >= housecost && houses < maxhouses) {
                        player.payMoney(housecost);
                        houses++;
                        notifyChange();
                }
        }

        public void computeRent(RealEstate realEstate) {
                int newRent = realEstate.getBaseRent();
                newRent = newRent + (2 * houses * realEstate.getBaseRent());
                realEstate.setRent(newRent);
                notifyChange();
        }

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
}
