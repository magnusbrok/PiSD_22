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
        playerPanel.setPreferredSize(new Dimension(90, 100));
        playerPanel.setLayout(new BoxLayout(playerPanel,BoxLayout.Y_AXIS));
        playerPanel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        playerPanel.setBackground(player.getColor());


        //Insetting labels
        JLabel label = new JLabel(" Balance:      " + this.player.getBalance());
        playerPanel.add(label);

        label = new JLabel(" Navn:"+ player.getName());
        playerPanel.add(label);

        frame.add(playerPanel);
        frame.revalidate();

    }

}
