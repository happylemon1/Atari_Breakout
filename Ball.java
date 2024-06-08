// import
import java.awt.*;
import java.util.Random;

public class Ball {
    // instance variables
    private int x;
    private int y;
    private int radius; 
    private int dX; 
    private int dY; 
    private double velocity; 
    private double angle; 
    private int numCollisions = 0;
    private Random rand = new Random();
    private final int INITIAL_SPEED = 4;
    private final int MAX_SPEED = 10;

    // constructor
    public Ball(int x, int y, int radius, int dX, int dY) {
        this.x = x;
        this.y = y;
        this.radius = radius; 
        this.dX = dX; 
        this.dY = dY; 
        this.velocity = Math.sqrt(dX * dX + dY * dY);
        this.angle = Math.atan2(dY, dX);
    }

    // getter methods
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getRadius() {
        return radius; 
    }

    public int getdX() {
        return dX; 
    }

    public int getdY() {
        return dY;
    }

    public int getNumCollisions() {
        return numCollisions; 
    }

    // setter methods
    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setdX(int dX) {
        this.dX = dX;
    }

    public void setdY(int dY) {
        this.dY = dY;
    }

    public void setNumCollisions(int numCollisions) {
        this.numCollisions = numCollisions; 
    }

    // getBounds() returns the bounding box of the ball
    public Rectangle getBounds() {
        return new Rectangle(x - radius, y - radius, radius * 2, radius * 2);
    }

    // respawn() resets balls position and speed
    public void respawn() {
        setX(350);
        setY(350);
        setdX(rand.nextInt(3) + 1 * (rand.nextBoolean() ? 1 : -1));
        setdY(INITIAL_SPEED);
        this.velocity = Math.sqrt(dX * dX + dY * dY);
        this.angle = Math.atan2(dY, dX);
        // Reset numCollisions to 0
        numCollisions = 0;
    }

    // wallCollision() adjusts balls movement if ball collides with a side boundary
    public void wallCollision(int width, int height) {
        // adjusts if balll hits right or left boundary
        if (x - radius <= 0 || x + radius >= width) {
            dX = -dX; 
            angle = Math.atan2(dY, dX);
        }

        // adjusts if ball hits top boundary
        if (y - radius <= 0) {
            dY = -dY; 
            angle = Math.atan2(dY, dX);
        }
    }

    // paddleCollision() adjusts ball movement depending on where and how ball hits paddle
    public void paddleCollision(Paddle p) {
        int ballBottom = y + radius;
        int paddleTop = p.getY(); 
        int paddleLeft = p.getX();
        int paddleRight = p.getX() + p.getWidth();

        if (ballBottom >= paddleTop && ballBottom <= paddleTop + getdY() && x + radius >= paddleLeft && x - radius <= paddleRight) {
            double collisionPoint = (x - (p.getX() + p.getWidth() / 2.0)) / (p.getWidth() / 2.0);
            angle = collisionPoint * (Math.PI / 4);
            dY = -Math.abs(dY);
            dX = -Math.abs(dX); 
            y = paddleTop - radius;
        }
    }

    // move() moves ball
    public void move() {
        x += dX; 
        y += dY;
    }

    // increaseSpeed() increases the balls speed
    public void increaseSpeed() {
        double speedMultiplier = 1.1;
        velocity *= speedMultiplier;
        if (velocity > MAX_SPEED) {
            velocity = MAX_SPEED;
        }
        dX = (int) (velocity * Math.cos(angle));
        dY = (int) (velocity * Math.sin(angle));
    }

    // brickCollision() uses for loops to check if coordinates of ball are same as coordinates of any brick, then setting its status to destroyed and creating collision
    public void brickCollision(BrickLayout brickLayout) {
        Brick[][] bricks = brickLayout.getLayout();
        for (int row = 0; row < brickLayout.getRows(); row++) {
            for (int col = 0; col < brickLayout.getCols(); col++) {
                Brick brick = bricks[row][col];
                if ((brick != null) && (!brick.isDestroyed())) {
                    if (getBounds().intersects(brick.getBounds())) {
                        brick.setDestroyed(true);
                        numCollisions++;
                        if (x + radius - dX <= brick.getX() || x - radius - dX >= brick.getX() + brick.getWidth()) {
                            dX = -dX;
                            angle = Math.atan2(dY, dX);
                        }
                        if (y + radius - dY <= brick.getY() || y - radius - dY >= brick.getY() + brick.getHeight()) {
                            dY = -dY;
                            angle = Math.atan2(dY, dX);
                        }
                    }
                }
            }
        }
    }

}
