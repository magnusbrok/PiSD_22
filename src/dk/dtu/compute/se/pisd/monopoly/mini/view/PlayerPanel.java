package dk.dtu.compute.se.pisd.monopoly.mini.view;


import dk.dtu.compute.se.pisd.monopoly.mini.model.Game;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Player;

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
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        //panel.setBorder(BorderFactory.createCompoundBorder());

        frame.setContentPane(panel);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.validate();

    }

    public void update() {
        panel.removeAll();

        JPanel playerPanel = new JPanel();
        playerPanel.setPreferredSize(new Dimension(80 , 90));
        playerPanel.setPreferredSize(new Dimension(90, 100));
        playerPanel.setMaximumSize(new Dimension(120, 110));
        playerPanel.setLayout(new BoxLayout(playerPanel,BoxLayout.Y_AXIS));
        playerPanel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        playerPanel.setBackground(player.getColor());


        //Insetting labels into the Playerpanel
        JLabel label = new JLabel(" Balance:   " + this.player.getBalance());
        playerPanel.add(label);

        label = new JLabel(" Navn:   "+ player.getName());
        playerPanel.add(label);

        if (player.isInPrison()) {
            label = new JLabel(" Status:   In Jail");
            playerPanel.add(label);
        }
        if (player.isBroke()){
        label = new JLabel(" Status:   Broke");
        playerPanel.add(label);
        }

        //Draw owned properties
        //TODO fix below code
        JPanel propertyPanel = new JPanel();
        for (int i = 0 ; i < player.getOwnedProperties().size() ; i++)

            propertyPanel = new JPanel();
            propertyPanel.setPreferredSize(new Dimension(80 , 90));
            propertyPanel.setPreferredSize(new Dimension(90, 100));
            propertyPanel.setMaximumSize(new Dimension(120, 110));
            propertyPanel.setLayout(new BoxLayout(propertyPanel,BoxLayout.X_AXIS));
            propertyPanel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
            propertyPanel.setBackground(player.getColor());
            label = new JLabel(""+ player.getOwnedProperties());
            propertyPanel.add(label);



        frame.add(playerPanel);
        //frame.add(propertyPanel);
        frame.revalidate();

    }

}
