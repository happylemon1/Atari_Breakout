import java.awt.*;

public class BrickLayout{
    private int numRows;
    private int numCols;
    private int width;
    private int height;
    private Brick[][] layout;

    public BrickLayout(int numRows, int numCols, int width, int height) {
        this.numRows = numRows;
        this.numCols = numCols;
        this.width = width;
        this.height = height;
        layout = new Brick[numRows][numCols];
        initializeLayout();
    }

    public Brick[][] getLayout() {
        return layout;
    }

    public int getRows() {
        return numRows;
    }

    public int getCols() {
        return numCols;
    }

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

    private Color getRowColor(int row) {
        switch (row) {
            case 0: return Color.PINK;
            case 1: return Color.RED;
            case 2: return Color.ORANGE;
            case 3: return Color.YELLOW;
            case 4: return Color.GREEN;
            case 5: return Color.BLUE;
            case 6: return Color.MAGENTA;
            case 7: return Color.CYAN;
            default: return Color.GRAY;
        }
    }


}