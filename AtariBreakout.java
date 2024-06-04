public class AtariBreakout {
    // main() method
    public static void main(String[] args) {
        // initializing objects
        Paddle paddle = new Paddle(300, 640, 100, 10, 20);
        Ball ball = new Ball(350, 350, 10, 4, 4);
        BrickLayout brickLayout = new BrickLayout(8, 10, 70, 30);
        GameCanvas gameCanvas = new GameCanvas(paddle, ball, brickLayout);
        // starting game
        gameCanvas.startGame(); 
    }
}
