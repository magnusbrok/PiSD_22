package dk.dtu.compute.se.pisd.monopoly.mini.view;


import dk.dtu.compute.se.pisd.monopoly.mini.model.Game;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Player;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Property;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import java.awt.*;

public class PlayerPanel extends JFrame {
    private Player player;
    private Game game;
    private JFrame frame;
    JPanel panel = new JPanel();

    public PlayerPanel(Game game, Player player){
        this.player = player;
        this.game = game;

        frame = new JFrame((player.getName()));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(500,150);
        frame.setLocation(700, game.getPlayers().indexOf(player)*150);
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
        JLabel label = new JLabel(" Balance:   " + this.player.getBalance());
        panel.add(label);

        label = new JLabel(" Navn:   "+ player.getName());
        panel.add(label);

        if (player.isInPrison()) {
            label = new JLabel(" Status:   In Jail");
            panel.add(label);
        }
        if (player.isBroke()){
        label = new JLabel(" Status:   Broke");
        panel.add(label);
        }

        //Draw owned properties
        //TODO fix below code
        this.getContentPane().setLayout(null);

        JPanel propertyPanel = new JPanel();

        frame.add(panel);
        for (Property property : player.getOwnedProperties()){


            propertyPanel = new JPanel();
            propertyPanel.setMinimumSize(new Dimension(100 , 100));
            propertyPanel.setPreferredSize(new Dimension(100, 100));
            propertyPanel.setMaximumSize(new Dimension(100, 100));
            propertyPanel.setLayout(new BoxLayout(propertyPanel,BoxLayout.X_AXIS));
            propertyPanel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
            propertyPanel.setBackground(player.getColor());
            label = new JLabel(""+ property.getName());
            propertyPanel.add(label);
            frame.add(propertyPanel);

        }





        frame.revalidate();
        frame.repaint();

    }

}
