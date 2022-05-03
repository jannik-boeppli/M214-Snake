package ch.noseryoung.plj;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class Snake extends JPanel implements ActionListener {
    private boolean running;
    private char direction = 'D';

    private final int MOVE_RATE = 150;
    private final int AMOUNT_FIELD_BLOCK_WIDTH = 25;
    private final int AMOUNT_FIELD_BLOCK_HEIGHT = 25;
    private final int START_LENGTH = 3;
    private final int ADD_LENGTH = 1;

    private Score score;
    private Timer timer;
    private Random random;
    private ArrayList<Character> input;

    private final Color headColor = new Color(0x00D452);
    private final Color bodyColor = new Color(0x02864C);
    private final Color appleColor = new Color(0xff0000);

    private int[][] snake;
    private int[] apple;

    public Snake(Score score) {
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());

        this.score = score;
        this.running = true;

        this.random = new Random();
        this.input = new ArrayList<>();

        startGame();
    }

    private void gameOver(Graphics g) {
        g.setColor(appleColor);

        g.setFont(new Font("Ink Free", Font.BOLD, 50));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Game Over",
                (this.getWidth() - metrics.stringWidth("Game Over")) / 2,
                this.getHeight() / 2);

        g.setFont(new Font("Ink Free", Font.BOLD, 20));
        metrics = getFontMetrics(g.getFont());
        g.drawString("Press enter to play again",
                (this.getWidth() - metrics.stringWidth("Press enter to play again")) / 2,
                this.getHeight() / 2 + 50);
    }

    // TODO: Add the snake and the apple to the panel using graphics
    private void drawField(Graphics g) {
        int unitWidth = this.getWidth() / AMOUNT_FIELD_BLOCK_WIDTH;
        int unitHeight = this.getHeight() / AMOUNT_FIELD_BLOCK_HEIGHT;

    }

    private void helperGrid(Graphics g) {
        g.setColor(Color.WHITE);

        for (int i = 1; i < AMOUNT_FIELD_BLOCK_HEIGHT; i++) {
            g.drawLine(0, this.getHeight() * i / AMOUNT_FIELD_BLOCK_HEIGHT,
                    this.getWidth(), this.getHeight() * i / AMOUNT_FIELD_BLOCK_HEIGHT);
        }

        for (int i = 1; i < AMOUNT_FIELD_BLOCK_WIDTH; i++) {
            g.drawLine(this.getWidth() * i / AMOUNT_FIELD_BLOCK_WIDTH, 0,
                    this.getWidth() * i / AMOUNT_FIELD_BLOCK_WIDTH, this.getHeight());
        }
    }

    private void startGame() {
        this.snake = new int[START_LENGTH][2];
        this.apple = new int[2];
        this.input.clear();

        setUpSnake();
        setNewApple();

        this.timer = new Timer(MOVE_RATE, this);
        this.timer.start();
    }

    private void setUpSnake() {
        for (int i = 0; i < snake.length; i++) {
            snake[i] = new int[]{snake.length - i, AMOUNT_FIELD_BLOCK_HEIGHT - (AMOUNT_FIELD_BLOCK_HEIGHT / 2)};
        }
    }

    public void setNewApple() {
        boolean isValidPosition = false;
        do {
            int random2 = random.nextInt(AMOUNT_FIELD_BLOCK_WIDTH);
            int random1 = random.nextInt(AMOUNT_FIELD_BLOCK_HEIGHT);

            this.apple[0] = random1;
            this.apple[1] = random2;

            for (int[] ints : snake) {
                if (ints[0] == apple[0] && ints[1] == apple[1]) {
                    isValidPosition = true;
                    break;
                }
            }
        } while (isValidPosition);
    }

    private void moveSnake() {
        if(!input.isEmpty()) {
            direction = input.get(0);
            input.remove(0);
        }

        for (int i = snake.length - 1; i > 0; i--) {
            snake[i][0] = snake[i - 1][0];
            snake[i][1] = snake[i - 1][1];
        }

        switch (direction) {
            case 'D' -> snake[0][0]++;
            case 'A' -> snake[0][0]--;
            case 'S' -> snake[0][1]++;
            case 'W' -> snake[0][1]--;
            default -> {
            }
        }
    }

    private void checkCollision() {
        if (snake[0][0] < 0 || snake[0][0] >= AMOUNT_FIELD_BLOCK_WIDTH) {
            running = false;
        }

        if (snake[0][1] < 0 || snake[0][1] >= AMOUNT_FIELD_BLOCK_HEIGHT) {
            running = false;
        }

        for (int i = 1; i < snake.length; i++) {
            if (snake[0][0] == snake[i][0] && snake[0][1] == snake[i][1]) {
                running = false;
                break;
            }
        }
    }

    private void checkApple() {
        if (snake[0][0] == apple[0] && snake[0][1] == apple[1]) {
            score.setScore(score.getScore() + 1);
            extendSnake();
            setNewApple();
        }
    }

    private void extendSnake() {
        int[][] newSnake = new int[snake.length + ADD_LENGTH][2];
        for (int i = 0; i < snake.length; i++) {
            newSnake[i] = new int[]{snake[i][0], snake[i][1]};
        }
        for (int i = 0; i < ADD_LENGTH; i++) {
            newSnake[newSnake.length - i - 1] = new int[]{-1, -1};
        }
        snake = newSnake;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
//        helperGrid(g);
        if (running) {
            drawField(g);
        } else {
            gameOver(g);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            moveSnake();
            checkApple();
            checkCollision();
        } else {
            timer.stop();
        }
        paintComponent(this.getGraphics());
    }

    public class MyKeyAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            char character = input.isEmpty() ? direction : input.get(input.size() - 1);
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT, 'A' -> {
                    if (character != 'D' && character != 'A') {
                        input.add('A');
                    }
                }
                case KeyEvent.VK_RIGHT, 'D' -> {
                    if (character != 'A' && character != 'D') {
                        input.add('D');
                    }
                }
                case KeyEvent.VK_UP, 'W' -> {
                    if (character != 'S' && character != 'W') {
                        input.add('W');
                    }
                }
                case KeyEvent.VK_DOWN, 'S' -> {
                    if (character != 'W' && character != 'S') {
                        input.add('S');
                    }
                }
                case KeyEvent.VK_ENTER -> {
                    if (!running) {
                        if (score.getScore() > score.getHighScore()) {
                            score.setHighScore(score.getScore());
                        }
                        direction = 'D';
                        running = true;
                        score.setScore(0);
                        startGame();
                    }
                }
                default -> {
                }
            }
        }
    }
}
