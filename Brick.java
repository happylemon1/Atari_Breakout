import java.awt.*;

public class Brick {
    private int x;
    private int y;
    private int width;
    private int height;
    private Color color;
    private boolean isDestroyed; 
    

    public Brick(int x, int y, int width, int height, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.isDestroyed = false;
    }

    // Getters and setters for position, width, height, color, health
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

    // returns the rectangular bounds of the brick
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

}