import java.awt.*;

public class Ball {
    private int x;
    private int y;
    private int radius; 
    private int dX; 
    private int dY; 
    private int numCollisions = 0;

    // constructor
    public Ball(int x, int y, int radius, int dX, int dY) {
        this.x = x;
        this.y = y;
        this.radius = radius; 
        this.dX = dX; 
        this.dY = dY; 
    }

    // returns x coordinate
    public int getX() {
        return x;
    }

    // returns y coordinate
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

    // sets x coordinate
    public void setX(int x) {
        this.x = x;
    }

    // sets y coordinate
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
        setdX(0);
        setdY(4);
    }

    public void WallCollision(int width, int height) {
        if (x - radius <= 0 || x + radius >= width) {
            dX = -dX; 
        }

        if (y - radius <= 0) {
            dY = -dY; 
        }
        
    }

    public void PaddleCollision(Paddle p) {
        int ballBottom = y + radius;
        int paddleTop = p.getY(); 
        int paddleLeft = p.getX() ;
        int paddleRight = p.getX() + p.getWidth();
         
        if (ballBottom == paddleTop) {
            if (x >= paddleLeft && x <= paddleRight) {
                dY = -dY; 
            }
            
        }
        
        /* 
        if (x >= paddleLeft && x <= paddleRight) {
            System.out.println("paddleLeft: " + paddleLeft); 
            System.out.println("paddleLeft: " + paddleRight); 
            dX = -dX; 
            System.out.println("newdX: " + dX);
        }  
        */
         

        

         

    }

    public void move() {
        x += dX; 
        y += dY;
    }

    public void increaseSpeed() {
        double speedMultiplier = 1.2;
        double velocity = Math.sqrt(dX * dX + dY * dY);
        double newVelocity = velocity * speedMultiplier;
        double angle = Math.atan2(dY, dX);

        dX = (int) (newVelocity * Math.cos(angle));
        dY = (int) (newVelocity * Math.sin(angle));
    }
    
    // BrickCollision() uses for loops to check if coordinates of ball are same as coordinates of any brick, then setting its status to destroyed and creating collision
    public void BrickCollision(BrickLayout brickLayout) {
        Brick[][] bricks = brickLayout.getLayout();
        for (int row = 0; row < brickLayout.getRows(); row++) {
            for (int col = 0; col < brickLayout.getCols(); col++) {
                Brick brick = bricks[row][col];
                if ((brick != null) && (!brick.isDestroyed())) {
                    // checks if bounds of brick overlaps with bounds of ball to check for collision
                    if (getBounds().intersects(brick.getBounds())) {
                        brick.setDestroyed(true);
                        numCollisions++;

                        // adjusts ball's direction based on collision horizontally
                        if (x + radius - dX <= brick.getX() || x - radius - dX >= brick.getX() + brick.getWidth()) {
                            dX = -dX;
                        }

                        // adjusts ball's direction based on collision vertically
                        if (y + radius - dY <= brick.getY() || y - radius - dY >= brick.getY() + brick.getHeight()) {
                            dY = -dY;
                        }
                    }
                }
            }
        }

    }

}