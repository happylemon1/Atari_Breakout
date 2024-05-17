public class Paddle {
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
        x += paddleSpeed; 

        if (x < 0) {
            x = 0; 
        }
        else if (x + width > screenWidth) {
            x = screenWidth - width; 
        }
    }

    public void BallCollision(Ball ball) {
        int ballY = ball.getY() - ball.getRadius(); 
        int ballX = ball.getX(); 

        if (ballY <= y && ballX <= x) {
            double paddleCenterX = x + width / 2.0; 
            double paddleCenterY = y + height / 2.0; 
            double collisionAngle = Math.atan2(y - paddleCenterY, x - paddleCenterX); 
            double bounceBackAngle = Math.PI - collisionAngle;
                
            double velocity = Math.sqrt(ball.getdX() * ball.getdX() + ball.getdY() * ball.getdY()); 
        
            ball.setdX((int) (velocity * Math.cos(bounceBackAngle)));
            ball.setdY((int) (velocity * Math.sin(bounceBackAngle)));
        }
            
    }
    
}