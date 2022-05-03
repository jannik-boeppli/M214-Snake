package ch.noseryoung.plj;

import javax.swing.*;
import java.awt.*;

public class Score extends JPanel {
    private int score;
    private int highScore;

    private JLabel scoreLabel;
    private JLabel highScoreLabel;

    // TODO: Add a layout to the panel and add the labels to it
    public Score() {}

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
        scoreLabel.setText("Score: " + score);
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
        highScoreLabel.setText("High Score: " + highScore);
    }
}
