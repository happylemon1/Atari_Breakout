public class AtariBreakout {
    // main() method
    public static void main(String[] args) {
        Paddle paddle = new Paddle(300, 640, 5, 10, 6);
        Ball ball = new Ball(350, 350, 10, 0, 4);
        BrickLayout brickLayout = new BrickLayout(8, 10, 70, 30);
        GameCanvas gameCanvas = new GameCanvas(paddle, ball, brickLayout);

        gameCanvas.startGame(); 
    }
}
