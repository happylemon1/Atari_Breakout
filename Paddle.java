public class Paddle {
    private int x;
    private int y;

    // constructor
    public Paddle(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // returns x coordinate
    public int getX() {
        return x;
    }

    // returns y coordinate
    public int getY() {
        return y;
    }

    // sets x coordinate
    public void setX(int x) {
        this.x = x;
    }

    // sets y coordinate
    public void setY(int y) {
        this.y = y;
    }
    
}