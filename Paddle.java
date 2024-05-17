public class Paddle {
    private int x;
    private int y;
    private int width; 
    private int height; 
    private int paddleSpeed; 

    // constructor
    public Paddle(int x, int y, int width, int height, int paddleSpeed) {
        this.x = x;
        this.y = y;
        this.width = width; 
        this.height = height; 
        this.paddleSpeed = 0; 

    }

    // returns x coordinate
    public int getX() {
        return x;
    }

    // returns y coordinate
    public int getY() {
        return y;
    }

    public int getWidth() {
        return width; 
    }

    public int getHeight() {
        return height; 
    }

    // sets x coordinate
    public void setX(int x) {
        this.x = x;
    }

    // sets y coordinate
    public void setY(int y) {
        this.y = y;
    }
    
    public void setPaddleSpeed(int paddleSpeed) {
        this.paddleSpeed = paddleSpeed; 
    }

    public void move() {
        y += paddleSpeed; 
        if (y < 0) {
            y = 0; 
        }
    }
}