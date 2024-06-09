/*
    Jason Jeong, Daniel Qian, Tony Liu
    6/10/24

    Advanced CS Topics Semester 2 Project
    Brick class defines the characteristics of a brick.

*/

// import
import java.awt.*;

public class Brick {
    // instance variables
    private int x;
    private int y;
    private int width;
    private int height;
    private Color color;
    private boolean isDestroyed;
    private boolean scoreCounted; 
    

    // constructor
    public Brick(int x, int y, int width, int height, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.isDestroyed = false;
        this.scoreCounted = false;
    }

    // getters and setters for instance variables
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isDestroyed() {
        return isDestroyed;
    }

    public boolean scoreCounted() {
        return scoreCounted;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setDestroyed(boolean isDestroyed) {
        this.isDestroyed = isDestroyed;
    }

    public void setScoreCounted(boolean scoreCounted) {
        this.scoreCounted = scoreCounted;
    }

    // returns the rectangular bounds of the brick
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

}