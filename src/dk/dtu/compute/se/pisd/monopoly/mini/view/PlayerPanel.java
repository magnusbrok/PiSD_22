package dk.dtu.compute.se.pisd.monopoly.mini.view;


import dk.dtu.compute.se.pisd.monopoly.mini.model.Game;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Player;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Property;
import dk.dtu.compute.se.pisd.monopoly.mini.model.properties.RealEstate;

import javax.swing.*;
import java.awt.*;

public class PlayerPanel extends JFrame {
    private Player player;
    private JFrame frame;
    JPanel panel = new JPanel();

    public PlayerPanel(Game game, Player player){
        this.player = player;
        //this.game = game;

        frame = new JFrame((player.getName()));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(700,135);
        frame.setLocation(700, game.getPlayers().indexOf(player)*135);
        panel.setLayout(new BoxLayout(panel,BoxLayout.X_AXIS));
        //panel.setBorder(BorderFactory.createCompoundBorder());

        frame.setContentPane(panel);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.validate();

    }

    public void update() {
        panel.removeAll();


        JPanel panel = new JPanel();
        panel.setMinimumSize(new Dimension(100 , 100));
        panel.setPreferredSize(new Dimension(100, 100));
        panel.setMaximumSize(new Dimension(100, 100));
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        panel.setBackground(player.getColor());


        //Insetting labels into the Playerpanel
        JLabel label = new JLabel(" Navn:   "+ player.getName());
        panel.add(label);

        label = new JLabel(" Balance:   " + this.player.getBalance());
        panel.add(label);

        if (player.isInPrison()) {
            label = new JLabel(" Status:   In Jail");
            panel.add(label);
        }
        if (player.isBroke()){
        label = new JLabel(" Status:   Broke");
        panel.add(label);
        }

        //TODO Maybe compine properties of same color
        this.getContentPane().setLayout(null);
        frame.add(panel);

        JPanel propertyPanel = new JPanel();

        for (Property property : player.getOwnedProperties()){

            propertyPanel = new JPanel();
            propertyPanel.setMinimumSize(new Dimension(80 , 100));
            propertyPanel.setPreferredSize(new Dimension(100, 100));
            propertyPanel.setMaximumSize(new Dimension(110, 100));
            propertyPanel.setLayout(new BoxLayout(propertyPanel,BoxLayout.Y_AXIS));
            propertyPanel.setBorder(BorderFactory.createLineBorder(Color.black, 1));

            label = new JLabel(" " + property.getName());
            propertyPanel.add(label);

            if (property instanceof RealEstate) {
                RealEstate realEstate = (RealEstate) property;
                propertyPanel.setBackground(realEstate.getColor());
                label = new JLabel(" HouseCost:   "+ realEstate.getHousecost());
                propertyPanel.add(label);
                label = new JLabel(" Houses:   "+ realEstate.getHouses());
                propertyPanel.add(label);
                label = new JLabel(" Rent:   "+ realEstate.getBaseRent());
                propertyPanel.add(label);
            }
            frame.add(propertyPanel);
        }
        frame.revalidate();
        frame.repaint();
    }
}
