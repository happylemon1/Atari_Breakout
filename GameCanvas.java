/*
    Jason Jeong, Daniel Qian, Tony Liu
    6/10/24

    Advanced CS Topics Semester 2 Project
    GameCanvas class creates the graphics of the game and implements the game logic.

*/

// import
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class GameCanvas extends JPanel implements ActionListener, MouseMotionListener, MouseListener {
    // instance variables
    private static final int SCREEN_HEIGHT = 700;
    private static final int SCREEN_WIDTH = 700;
    private BrickLayout brickLayout;
    private Ball ball;
    private Paddle paddle;
    private Timer timer;
    private boolean isRunning = false;
    private boolean waitingForRespawn = true;
    public boolean gameWon = false;
    private int delay = 10;
    private int score = 0;
    private int lives = 3;
    private Font arcadeFont;

    // constructor
    public GameCanvas(Paddle paddle, Ball ball, BrickLayout brickLayout) {
        this.paddle = paddle;
        this.ball = ball;
        this.brickLayout = brickLayout;

        // setting up frame and panel
        this.setBackground(Color.BLACK);
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        this.addMouseMotionListener(this);
        this.addMouseListener(this); // Add mouse listener
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
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
            // fallback to default font if custom font fails to load
            arcadeFont = new Font("SansSerif", Font.BOLD, 30);
        }

    }

    // startGame() starts game and timer
    public void startGame() {
        isRunning = true;
        // reset waiting flag
        waitingForRespawn = false;
        // initial ball respawn
        ball.respawn();
        timer = new Timer(delay, this);
        timer.start();
    }

    // drawing objects based on state of game
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (isRunning) {
            drawPaddle(g);
            drawBall(g);
            drawBricks(g);
            displayScoreAndLives(g);
        } 
        else if (waitingForRespawn) {
            drawPaddle(g);
            drawBricks(g);
            displayScoreAndLives(g);
        }
        else if (gameWon) {
            displayWinScreen(g);
            displayScoreAndLives(g);
        }
        else {
            displayScoreAndLives(g);
            endGame(g);
        }

    }

    // drawPaddle() draws the paddle
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

    // drawBall() draws the ball
    public void drawBall(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillOval(ball.getX(), ball.getY(), ball.getRadius() * 2, ball.getRadius() * 2);
    }

    // handleCollisions() checks for the various collision types
    public void handleCollisions(int x, int y) {
        ball.wallCollision(SCREEN_WIDTH, SCREEN_HEIGHT);
        ball.paddleCollision(paddle); 
        ball.brickCollision(brickLayout); 
    }

    // moveBall() moves the ball's position based on speed and collisions
    public void moveBall() {
        // checks if game is completed, max score after all bricks broken is 360
        if (score == 360) {
            gameWon = true;
            isRunning = false;
        }

        /*
        // increases speed after certain number of collisions
        if (ball.getNumCollisions() == 4 || ball.getNumCollisions() == 12) {
            ball.increaseSpeed(); 
        }
        */

        // checks if ball goes beyond bottom boundary
        if (ball.getY() + ball.getRadius() >= SCREEN_HEIGHT) {
            lives--; 
            // if user runs out of lives, stop the game and timer
            if (lives == 0) {
                isRunning = false;
                timer.stop();
            }
            else {
                // set flag to wait for respawn
                waitingForRespawn = true;
                // pause game
                isRunning = false;
            }

        }

        // updating ball's x and y
        int newX = ball.getX() + ball.getdX(); 
        int newY = ball.getY() + ball.getdY(); 
        ball.setX(newX); 
        ball.setY(newY); 
        // checking for collisions
        handleCollisions(newX, newY); 

    }

    // drawBricks() draws the bricks
    public void drawBricks(Graphics g) {
        // 2D array layout
        Brick[][] bricks = brickLayout.getLayout();
        // drawing the bricks
        for (int row = 0; row < bricks.length; row++) {
            for (int col = 0; col < bricks[row].length; col++) {
                if (!bricks[row][col].isDestroyed()) {
                    Brick brick = bricks[row][col];
                    g.setColor(brick.getColor());
                    g.fillRect(brick.getX(), brick.getY(), brick.getWidth(), brick.getHeight());
                    g.setColor(Color.BLACK);
                    g.drawRect(brick.getX(), brick.getY(), brick.getWidth(), brick.getHeight());
                }
                // if brick is destroyed and score is not counted update score
                else if (!bricks[row][col].scoreCounted()) {
                    Color color = bricks[row][col].getColor();
                    // sets brick to score counted
                    bricks[row][col].setScoreCounted(true);
                    // uses if and else if statements to update score according to brick's color
                    if (color.equals(new Color(0, 255, 255))) {
                        score += 1;
                    } else if (color.equals(new Color(138, 43, 226))) {
                        score += 2;
                    } else if (color.equals(new Color(30, 144, 255))) {
                        score += 3;
                    } else if (color.equals(new Color(50, 205, 50))) {
                        score += 4;
                    } else if (color.equals(new Color(255, 255, 0))) {
                        score += 5;
                    } else if (color.equals(new Color(255, 165, 0))) {
                        score += 6;
                    } else if (color.equals(new Color(255, 69, 0))) {
                        score += 7;
                    } else if (color.equals(new Color(255, 105, 180))) {
                        score += 8;
                    }
                }
            }
        }

    }

    // displayScoreAndLives() writes the current score and number of lives
    public void displayScoreAndLives(Graphics g) {
        // drawing score
        g.setColor(Color.WHITE);
        g.setFont(arcadeFont);
        FontMetrics metrics = g.getFontMetrics();
        g.drawString("Score: " + score, 10, metrics.getAscent());
        // drawing number of lives
        int circleDiameter = 20;
        int yPosition = SCREEN_HEIGHT - circleDiameter - 10;
        g.setColor(new Color(255, 0, 0));
        // traversing through number of lives and drawing each circle representing each life
        for (int i = 0; i < lives; i++) {
            g.fillOval(10 + i * (circleDiameter + 10), yPosition, circleDiameter, circleDiameter);
        }

    }

    // displayMessage() shows a message on the screen
    public void displayMessage(Graphics g, String message) {
        g.setColor(Color.WHITE);
        g.setFont(arcadeFont);
        FontMetrics metrics = g.getFontMetrics();
        int x = (SCREEN_WIDTH - metrics.stringWidth(message)) / 2;
        int y = (SCREEN_HEIGHT - metrics.getHeight()) / 2;
        g.drawString(message, x, y);
    }

    // displayWinScreen() shows a win screen when user beats the game
    public void displayWinScreen(Graphics g) {
        g.setColor(Color.GREEN);
        g.setFont(arcadeFont);
        FontMetrics metrics = g.getFontMetrics();
        g.drawString("You Win!", (SCREEN_WIDTH - metrics.stringWidth("You Win!")) / 2, SCREEN_HEIGHT / 2);
    }

    // endGame() displays end message
    public void endGame(Graphics g) {
        g.setColor(Color.RED);
        g.setFont(arcadeFont);
        FontMetrics metrics = g.getFontMetrics();
        g.drawString("Game Over", (SCREEN_WIDTH - metrics.stringWidth("Game Over")) / 2, SCREEN_HEIGHT / 2);
    }

    // restartGame() restarts the game
    public void restartGame() {
        // resetting score, lives, initial speed
        score = 0;
        lives = 3;
        Brick[][] bricks = brickLayout.getLayout();
        // traverses layout and sets it back to default where none of blocks are destroyed
        for (int row = 0; row < bricks.length; row++) {
            for (int col = 0; col < bricks[row].length; col++) {
                if (bricks[row][col].isDestroyed()) {
                    bricks[row][col].setDestroyed(false);
                    bricks[row][col].setScoreCounted(false);
                }
            }
        }
        startGame();

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

    // mouseClicked() respawns ball if mouse is clicked when waiting for respawn
    @Override
    public void mouseClicked(MouseEvent e) {
        if (waitingForRespawn) {
            waitingForRespawn = false;
            isRunning = true;
            // respawn the ball
            ball.respawn();
            // restart the timer
            timer.restart();
        }

    }

    // needed overidden methods from MouseListener interface
    @Override
    public void mousePressed(MouseEvent e) { }

    @Override
    public void mouseReleased(MouseEvent e) { }

    @Override
    public void mouseEntered(MouseEvent e) { }

    @Override
    public void mouseExited(MouseEvent e) { }

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
                // if enter button is pressed when game is not running, game restarts
                case KeyEvent.VK_ENTER:
                    if ((!isRunning) && (!waitingForRespawn)) {
                        restartGame();
                    }
                    break;
            }
        }

    }

}
