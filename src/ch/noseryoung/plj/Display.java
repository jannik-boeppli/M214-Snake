package ch.noseryoung.plj;

import javax.swing.*;
import java.awt.*;

public class Display extends JFrame {
    private Score score;
    private Snake snake;

    public Display(){
        score = new Score();
        snake = new Snake(score);
        createFrame();
        setUpDisplay();
    }

    private void setUpDisplay(){
        this.setTitle("M214 - Snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setSize(1000, 600);
    }

    // TODO: Add a layout to the frame and add the components to the frame
    private void createFrame(){}
}
