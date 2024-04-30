// import
import javax.swing.*;
import java.awt.*;

public class GameCanvas extends JPanel {
    private static final int SCREEN_HEIGHT = 700;
    private static final int SCREEN_WIDTH = 700;
    private Timer timer;
    private boolean isRunning = false;
    private int delay;
    private int score = 0;
    private int lives = 3;
    private int level = 1;

    public GameCanvas() {

        // setting up frame and panel
        this.setBackground(Color.BLACK);
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setFocusable(true);
        JFrame gameFrame = new JFrame("Atari Breakout");
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.add(this);
        gameFrame.setVisible(true);
        gameFrame.setResizable(false);
        gameFrame.pack();
        gameFrame.setLocationRelativeTo(null);

    }

    // starts game and timer
    public void startGame() {
        isRunning = true;
        timer = new Timer(delay, this);
        timer.start();
    }

    // drawing objects
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (isRunning) {
            
        }
    }

    public void drawPaddle() {
        
    }

    public void drawBall() {

    }

    public void drawBricks() {

    }
}