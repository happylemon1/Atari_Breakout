// import
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameCanvas extends JPanel implements ActionListener {
    private static final int SCREEN_HEIGHT = 700;
    private static final int SCREEN_WIDTH = 700;
    private BrickLayout brickLayout;
    private Ball ball;
    private Paddle paddle;
    private Timer timer;
    private boolean isRunning = false;
    private int delay = 10;
    private int score = 0;
    private int lives = 3;
    private int level = 1;

    public GameCanvas(Paddle paddle, Ball ball, BrickLayout brickLayout) {
        this.paddle = paddle;
        this.ball = ball;
        this.brickLayout = brickLayout;

        // setting up frame and panel
        this.setBackground(Color.BLACK);
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
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
        drawPaddle(g);
        drawBall(g);
        drawBricks(g);
    }

    public void drawPaddle(Graphics g) {
        // drawing paddle
        g.setColor(Color.DARK_GRAY);
        g.fillRect(paddle.getX(), paddle.getY(), paddle.getWidth(), paddle.getHeight());
        g.setColor(Color.WHITE);
        g.drawRect(paddle.getX(), paddle.getY(), paddle.getWidth(), paddle.getHeight());

        // drawing lines in paddle
        for (int i = 1; i < 5; i++) {
            g.drawLine(paddle.getX() + i * 20, paddle.getY(), paddle.getX() + i * 20, paddle.getY() + 10);
        }
        
    }

    public void drawBall(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillOval(ball.getX(), ball.getY(), ball.getRadius() * 2, ball.getRadius() * 2);

    }
    public void handleCollisions(int x, int y) {
        ball.WallCollision(SCREEN_WIDTH, SCREEN_HEIGHT);
        ball.PaddleCollision(paddle); 
        ball.BrickCollision(brickLayout); 
    }
    public void moveBall() {
        if (ball.getNumCollisions() == 4 || ball.getNumCollisions() == 12) {
            ball.increaseSpeed(); 
        }

        if (ball.getY() + ball.getRadius() >= SCREEN_HEIGHT) {
            lives--; 
            ball.setX(350); 
            ball.setY(350); 
            ball.setdX(0); 
            ball.setdY(4); 
            System.out.println(lives);
        }
        int newX = ball.getX() + ball.getdX(); 
        int newY = ball.getY() + ball.getdY(); 

        ball.setX(newX); 
        ball.setY(newY); 
        
        handleCollisions(newX, newY); 
        //ball.WallCollision(SCREEN_WIDTH, SCREEN_HEIGHT); 
        //ball.PaddleCollision(paddle); 
        
        /*
         if (newX <= 0 || newX + ball.getRadius() * 2 >= SCREEN_WIDTH) {
            ball.setdX(-ball.getdX()); 
        }

        if (newY <= 0 || newY + ball.getRadius() * 2 >= SCREEN_HEIGHT) {
            ball.setdY(-ball.getdY()); 
        }
         */

    }

    public void drawBricks(Graphics g) {
        // 2D array layout
        Brick[][] bricks = brickLayout.getLayout();
        // drawing the bricks
        for (int row = 0; row < bricks.length; row++) {
            for (int col = 0; col < bricks[row].length; col++) {
                if (bricks[row][col].isDestroyed() != true) {
                    Brick brick = bricks[row][col];
                    g.setColor(brick.getColor());
                    g.fillRect(brick.getX(), brick.getY(), brick.getWidth(), brick.getHeight());
                    g.setColor(Color.BLACK);
                    g.drawRect(brick.getX(), brick.getY(), brick.getWidth(), brick.getHeight());
                }
            }
        }
    }
        
    @Override
    public void actionPerformed(ActionEvent e) {
        if (isRunning) {
            moveBall(); 
        }

        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            // using switch statement to move paddle depending on key pressed
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    paddle.moveLeft();
                    break;
                case KeyEvent.VK_RIGHT:
                    paddle.moveRight();
                    break;
            }

        }

    }

}