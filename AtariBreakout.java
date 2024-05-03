public class AtariBreakout {
    // main() method
    public static void main(String[] args) {
        Paddle paddle = new Paddle(350, 650);
        GameCanvas gameCanvas = new GameCanvas(paddle);
    }
}

