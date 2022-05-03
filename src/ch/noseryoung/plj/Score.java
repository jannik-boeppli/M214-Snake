package ch.noseryoung.plj;

import javax.swing.*;
import java.awt.*;

public class Score extends JPanel {
    private int score;
    private int highScore;

    private JLabel scoreLabel;
    private JLabel highScoreLabel;

    public Score() {
        scoreLabel = new JLabel("Score: " + score, SwingConstants.CENTER);
        scoreLabel.setForeground(Color.red);

        highScoreLabel = new JLabel("High Score: " + highScore, SwingConstants.CENTER);
        highScoreLabel.setForeground(Color.red);

        this.setBackground(Color.DARK_GRAY);

        this.setLayout(new GridLayout());
        this.add(scoreLabel);
        this.add(highScoreLabel);
    }

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
