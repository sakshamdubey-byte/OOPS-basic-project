import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.*;

public class Main {
    public static void main(String args[]) {
        // this will add a frame to the game
        int board_width = 600;
        int boards_height = 600;
        JFrame frame = new JFrame("SNAKE GAME");
        frame.setVisible(true);
        frame.setSize(board_width, boards_height);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        gameplay gp = new gameplay(board_width, boards_height);
        frame.add(gp);
        frame.setVisible(true);
        frame.pack();

        // rough idea ,we will divide area into 24*24 pixel ,each pixelis a unit of
        // snkae
        // so a 1s0 lenght snake =10units
        gp.requestFocus(); // this will listen to key pressess
    }
}