package dk.dtu.compute.se.pisd.monopoly.mini.view;


import dk.dtu.compute.se.pisd.monopoly.mini.model.Game;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Player;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Property;
import dk.dtu.compute.se.pisd.monopoly.mini.model.properties.Brewery;
import dk.dtu.compute.se.pisd.monopoly.mini.model.properties.Ferry;
import dk.dtu.compute.se.pisd.monopoly.mini.model.properties.RealEstate;

import javax.swing.*;
import java.awt.*;

/**
 * Class for making Jframes with info about each player and their owned properties.
 * Currently each player gets their own panel, with information regarding themselves.
 * @author Magnus, Tim, Siff og Ida
 */
public class PlayerPanel extends JFrame {
    private Player player;
    private JFrame frame;
    JPanel panel = new JPanel();

    /**
     * The constructor for the permanent playerFrame. only happens once and isn't changed during the game.
     * @param game The game that's playing
     * @param player the player that needs a frame.
     * @author Magnus and Tim.
     */
    public PlayerPanel(Game game, Player player){
        this.player = player;

        frame = new JFrame((player.getName()));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(900,135);
        frame.setLocation(700, game.getPlayers().indexOf(player)*135);
        panel.setLayout(new BoxLayout(panel,BoxLayout.X_AXIS));

        frame.setContentPane(panel);
        frame.setResizable(true);
        frame.setVisible(true);
        frame.validate();

    }

    /**
     * update method that handles the drawing and updating of each player frame.
     * method adds info about the player in 1 panel and adds another panel for each property owned with
     * info regarding that property.
     * Works by first clearing the panel and then redrawing it again.
     * Is called in the view class via updatePlayer method.
     * @author Magnus
     */
    public void update() {

        panel.removeAll();

        JPanel panel = new JPanel();
        panel.setMinimumSize(new Dimension(100, 100));
        panel.setPreferredSize(new Dimension(100, 100));
        panel.setMaximumSize(new Dimension(100, 100));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        panel.setBackground(player.getColor());


        //Insetting labels into the Playerpanel
        JLabel label = new JLabel(" Navn:   " + player.getName());
        panel.add(label);

        label = new JLabel(" Balance:   " + this.player.getBalance());
        panel.add(label);

        if (player.isInPrison()) {
            label = new JLabel(" Status:   In Jail");
            panel.add(label);
        }
        if (player.isBroke()) {
            label = new JLabel(" Status:   Broke");
            panel.add(label);
        }

        frame.add(panel);

        this.getContentPane().setLayout(null);
        // Here we create each propertyPanel.
        // Reacting differently depending on what kind of property it is.
        for (Property property : player.getOwnedProperties()) {

            JPanel propertyPanel = new JPanel();
            propertyPanel.setMinimumSize(new Dimension(80, 100));
            propertyPanel.setPreferredSize(new Dimension(100, 100));
            propertyPanel.setMaximumSize(new Dimension(110, 100));
            propertyPanel.setLayout(new BoxLayout(propertyPanel, BoxLayout.Y_AXIS));
            propertyPanel.setBorder(BorderFactory.createLineBorder(Color.black, 1));

            label = new JLabel(" " + property.getName());
            propertyPanel.add(label);

            if (property instanceof RealEstate) {
                RealEstate realEstate = (RealEstate) property;
                propertyPanel.setBackground(realEstate.getColor());
                label = new JLabel(" HouseCost:   " + realEstate.getHousecost());
                propertyPanel.add(label);
                label = new JLabel(" Houses:   " + realEstate.getHouses());
                propertyPanel.add(label);
                if (realEstate.getRent() != 0) {
                    label = new JLabel(" Rent:   " + realEstate.getRent());
                }
                if (realEstate.getRent() == 0) {
                    label = new JLabel("Rent:   " + realEstate.getBaseRent());
                }
                propertyPanel.add(label);
            }

            if (property instanceof Ferry) {
                Ferry ferry = (Ferry) property;
                label = new JLabel(" FerryCost:   " + ferry.getCost());
                propertyPanel.add(label);
                label = new JLabel(" Ferries:   " + ferry.getOwnedFerries(player));
                propertyPanel.add(label);
                label = new JLabel(" Rent:   " + ferry.getRent());
                propertyPanel.add(label);
            }
            if (property instanceof Brewery) {
                Brewery brewery = (Brewery) property;
                label = new JLabel(" Breweries:   "  + brewery.getOwnedBreweries());
                propertyPanel.add(label);
                label = new JLabel(" Multiplier:   " + brewery.getMultiplier() + "x");
                propertyPanel.add(label);
            }

            frame.add(propertyPanel);
        }
        frame.revalidate();
        frame.repaint();
    }
}
