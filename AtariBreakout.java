public class AtariBreakout {
    // main() method
    public static void main(String[] args) {
        Paddle paddle = new Paddle(350, 20);
        GameCanvas gameCanvas = new GameCanvas(paddle);
    }
}

