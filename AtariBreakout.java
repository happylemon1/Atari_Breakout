public class AtariBreakout {
    // main() method
    public static void main(String[] args) {
        Paddle paddle = new Paddle(300, 640, 5, 10, 6);
        GameCanvas gameCanvas = new GameCanvas(paddle);
    }
}

