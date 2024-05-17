public class AtariBreakout {
    // main() method
    public static void main(String[] args) {
        Paddle paddle = new Paddle(300, 640, 5, 10, 6);
        // change dX and dY later
        Ball ball = new Ball(350, 350, 10, 0, 0);
        GameCanvas gameCanvas = new GameCanvas(paddle, ball);
    }
}

