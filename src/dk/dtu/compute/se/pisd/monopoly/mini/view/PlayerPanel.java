package dk.dtu.compute.se.pisd.monopoly.mini.view;


import dk.dtu.compute.se.pisd.monopoly.mini.model.Game;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Player;

import javax.swing.*;
import java.awt.*;

public class PlayerPanel extends JFrame {
    Player player;
    Game game;
    JFrame frame;

    public PlayerPanel(Game game, Player player){
        this.validate();

        frame = new JFrame(player.getName());
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        JLabel label1 = new JLabel("Balance: " + player.getBalance());
        JLabel label2 = new JLabel("Properties owned: " + player.getOwnedProperties());


        panel.add(label1);
        panel.add(label2);

        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(label1, BorderLayout.WEST);
        frame.pack();
        frame.setSize(500,100);
        frame.setVisible(true);

    }

    public void update() {
        this.revalidate();
        this.repaint();


    }
}
