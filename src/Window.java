import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.Toolkit;

public class Window {

    private JFrame frame;

    //private double screenWidth = 1.5;
    //private double screenHeight = 1.5;
    //private int screenX;
    //private int screenY;

    public final int WIDTH;
    public final int HEIGHT;

    public Window() {
        frame = new JFrame();

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        WIDTH = (int) screenSize.getWidth();
        HEIGHT = (int) screenSize.getHeight();

        frame.setSize(WIDTH, HEIGHT);
		frame.setVisible(true);
        frame.setTitle("Physics");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public JFrame getFrame() {
        return frame;
    }
}