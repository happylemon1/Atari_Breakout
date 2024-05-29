// import
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class GameCanvas extends JPanel implements ActionListener, MouseMotionListener {
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
    private Font arcadeFont;

    public GameCanvas(Paddle paddle, Ball ball, BrickLayout brickLayout) {
        this.paddle = paddle;
        this.ball = ball;
        this.brickLayout = brickLayout;

        // setting up frame and panel
        this.setBackground(Color.BLACK);
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        this.addMouseMotionListener((MouseMotionListener) this);
        JFrame gameFrame = new JFrame("Atari Breakout");
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.add(this);
        gameFrame.setVisible(true);
        gameFrame.setResizable(false);
        gameFrame.pack();
        gameFrame.setLocationRelativeTo(null);

        // loading custom arcade font
        try {
            arcadeFont = Font.createFont(Font.TRUETYPE_FONT, new File("PressStart2P-Regular.ttf")).deriveFont(30f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(arcadeFont);
        } 
        catch (IOException | FontFormatException e) {
            e.printStackTrace();
            // fallback to default font if custom font fails to load
            arcadeFont = new Font("SansSerif", Font.BOLD, 30);
        }

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
            drawPaddle(g);
            drawBall(g);
            drawBricks(g);
            displayScore(g);
        }
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
            lives --; 
            ball.respawn();
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
                // if brick is destroyed and score is not counted update score
                else if (bricks[row][col].scoreCounted() != true) {
                    Color color = bricks[row][col].getColor();
                    // sets brick to score counted
                    bricks[row][col].setScoreCounted(true);
                    // uses if and else if statements to update score according to brick's color
                    if (color.equals(new Color(0, 255, 255))) {
                        score += 1;
                    }
                    else if (color.equals(new Color(138, 43, 226))) {
                        score += 2;
                    }
                    else if (color.equals(new Color(30, 144, 255))) {
                        score += 3;
                    }
                    else if (color.equals(new Color(50, 205, 50))) {
                        score += 4;
                    }
                    else if (color.equals(new Color(255, 255, 0))) {
                        score += 5;
                    }
                    else if (color.equals(new Color(255, 165, 0))) {
                        score += 6;
                    }
                    else if (color.equals(new Color(255, 69, 0))) {
                        score += 7;
                    }
                    else if (color.equals(new Color(255, 105, 180))) {
                        score += 8;
                    }
                
                }
            }
        }
    }

    // displayScore() writes the current score  
    public void displayScore(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(arcadeFont);
        FontMetrics metrics = g.getFontMetrics();
        g.drawString("Score: " + score, 10, metrics.getAscent());
    }

    // mouseMoved() updates paddle movement based on mouse movement
    @Override
    public void mouseMoved(MouseEvent e) {
        // updates paddle position based on mouse position, accounting for paddle width
        paddle.setX(e.getX() - paddle.getWidth() / 2);
        // makes sure paddle stays in game boundaries
        if (paddle.getX() < 0) {
            paddle.setX(0);
        } 
        else if (paddle.getX() + paddle.getWidth() > SCREEN_WIDTH) {
            paddle.setX(SCREEN_WIDTH - paddle.getWidth());
        }
    }

    // mouseDragged() updates paddle movement if mouse is dragged
    @Override
    public void mouseDragged(MouseEvent e) {
        // updates paddle position based on mouse position, accounting for paddle width
        paddle.setX(e.getX() - paddle.getWidth() / 2);
        // makes sure paddle stays in game boundaries
        if (paddle.getX() < 0) {
            paddle.setX(0);
        } 
        else if (paddle.getX() + paddle.getWidth() > SCREEN_WIDTH) {
            paddle.setX(SCREEN_WIDTH - paddle.getWidth());
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