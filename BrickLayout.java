/*
    Jason Jeong, Daniel Qian, Tony Liu
    6/10/24

    Advanced CS Topics Semester 2 Project
    BrickLayout class stores the layout and colors of the brick formation.

*/

// import
import java.awt.*;

public class BrickLayout {
    // instance variables
    private int numRows;
    private int numCols;
    private int width;
    private int height;
    private Brick[][] layout;

    // constructor
    public BrickLayout(int numRows, int numCols, int width, int height) {
        this.numRows = numRows;
        this.numCols = numCols;
        this.width = width;
        this.height = height;
        layout = new Brick[numRows][numCols];
        initializeLayout();
    }

    // getter methods
    public Brick[][] getLayout() {
        return layout;
    }

    public int getRows() {
        return numRows;
    }

    public int getCols() {
        return numCols;
    }

    // initalizeLayout() initializes the layout of the bricks in a 2D array of Brick objects
    private void initializeLayout() {
        for (int row = 0; row < numRows; row++) {
            Color color = getRowColor(row);
            for (int col = 0; col < numCols; col++) {
                int x = col * width;
                int y = row * height;
                layout[row][col] = new Brick(x, y, width, height, color);
            }
        }
    }

    // getRowColor() returns color based on a row number parameter
    private Color getRowColor(int row) {
        switch (row) {
        case 0: return new Color(255, 105, 180);
        case 1: return new Color(255, 69, 0);   
        case 2: return new Color(255, 165, 0);  
        case 3: return new Color(255, 255, 0);   
        case 4: return new Color(50, 205, 50);   
        case 5: return new Color(30, 144, 255); 
        case 6: return new Color(138, 43, 226);  
        case 7: return new Color(0, 255, 255); 
        default: return new Color(169, 169, 169);
        }
    }

}