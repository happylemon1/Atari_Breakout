public class Ball {
    private int x;
    private int y;
    private int radius; 
    private int dX; 
    private int dY; 

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

    public void WallCollision(int width, int height) {
        if (x - radius <= 0 || x + radius >= width) {
            dX = -dX; 
        }

        if (y - radius <= 0 || y + radius >= height) {
            dY = -dY; 
        }
        
    }

    public void PaddleCollision(Paddle p) {
        int ballBottom = y + radius;
        int paddleTop = p.getY(); 
        int paddleLeft = p.getX() ;
        int paddleRight = p.getX() + p.getWidth();

        /* 
        if ((ballBottom == paddleTop) && ((x >= paddleLeft) && (x <= paddleRight))) {
            dX = -dX; 
            dY = -dY; 
            System.out.println("paddleLeft: " + paddleLeft); 
            System.out.println("paddleRight: " + paddleRight); 
            System.out.println("BallBottom" + ballBottom); 
            System.out.println("PaddleTop: " + paddleTop); 
            System.out.println("Collision point: " + x + ", Collision point: " + y); 

        }
        */
        
         


        if (ballBottom == paddleTop) {
            System.out.println("BallBottom " + ballBottom); 
            System.out.println("PaddleTop: " + paddleTop); 
            System.out.println("Collision point: " + x + "," + y); 
            System.out.println("paddleLeft: " + paddleLeft);
            System.out.println("paddleRight: " + paddleRight); 
            if (x >= paddleLeft && x <= paddleRight) {
                System.out.println("paddleLeft: " + paddleLeft); 
                System.out.println("paddleLeft: " + paddleRight); 
                dX = -dX; 
                dY = -dY; 
                System.out.println("newdX: " + dX);
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
    
    public void BrickCollision() {

    }
}