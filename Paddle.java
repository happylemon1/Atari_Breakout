public class Paddle {
    // instance variables
    private int x;
    private int y;
    private int width; 
    private int height; 
    private int paddleSpeed; 
    private int screenWidth = 700; 

    // constructor
    public Paddle(int x, int y, int width, int height, int paddleSpeed) {
        this.x = x;
        this.y = y;
        this.width = width; 
        this.height = height; 
        this.paddleSpeed = paddleSpeed;
    }

    // getter methods
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width; 
    }

    public int getHeight() {
        return height; 
    }

    public int getPaddleSpeed() {
        return paddleSpeed;
    }

    // setter methods
    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
    
    public void setPaddleSpeed(int paddleSpeed) {
        this.paddleSpeed = paddleSpeed; 
    }
    
    // moveRight() moves the paddle to the right according to the paddle speed
    public void moveRight() {
        x += paddleSpeed; 

        // checks if paddle moves beyong right boundary, preventing it from moving past it
        if (x + width > screenWidth) {
            x = screenWidth - width; 
        }
    }

    // moveLeft() moves the paddle to the left according to the paddle speed
    public void moveLeft() {
        x -= paddleSpeed; 

        // checks if paddle moves beyond left boundary, preventing it from moving past it
        if (x < 0) {
            x = 0; 
        }
    }
    
}