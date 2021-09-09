import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

public class Main implements Runnable {
    Thread thread;
    Window w;
    boolean running;
    //int tick = 10000000, tickCount = 0;
    int x = 0, y = 0;
    double deg = 0;
    ArrayList<int[]> sin = new ArrayList<int[]>();
    ArrayList<int[]> cos = new ArrayList<int[]>();


    public Main() {
        w = new Window();
        start();
    }

    public synchronized void start() {
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    public synchronized void stop() {
        try {
            thread.join();
            running = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while (running) {
            update();
            draw();
        }
        stop();
    }

    public void update() {
        if (x == 0 && y == 0) {
            x = w.WIDTH / 2 - 5;
            y = w.HEIGHT / 2 - 105;
        } else {
            x = (int) Math.round(Math.cos(deg) * 100) + w.WIDTH / 2 - 5;
            y = (int) Math.round(Math.sin(deg) * 100) + w.HEIGHT / 2 - 5;
        }
        deg += 0.01;
        if (deg >= 360) {
            deg = 0.0;
        }

        ArrayList<int[]> remove = new ArrayList<int[]>();
        for (int[] circle : sin) {
            circle[0]++;
            if (circle[0] >= w.WIDTH) {
                remove.add(circle);
            }
        }
        for (int[] circle : remove) {
            sin.remove(circle);
        }
        for (int[] circle : cos) {
            circle[1]--;
            if (circle[1] <= 0) {
                remove.add(circle);
            }
        }
        for (int[] circle : remove) {
            cos.remove(circle);
        }
        int[] sinCircle = {w.WIDTH / 2 + 100, y};
        sin.add(sinCircle);
        int[] cosCircle = {x, w.HEIGHT / 2 - 100};
        cos.add(cosCircle);
    }

    public void draw() {
        BufferStrategy bs = w.getFrame().getBufferStrategy();
        if (bs == null) {
            w.getFrame().createBufferStrategy(2);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, w.WIDTH, w.HEIGHT);
        g.setColor(Color.GREEN);
        g.drawOval(w.WIDTH / 2 - 100, w.HEIGHT / 2 - 100, 200, 200);
        g.fillOval(w.WIDTH / 2 - 5, w.HEIGHT / 2 - 5, 10, 10);
        g.fillOval(x, y, 10, 10);
        g.drawLine(w.WIDTH / 2, w.HEIGHT / 2, x + 5, y + 5);
        g.drawLine(w.WIDTH / 2, w.HEIGHT / 2, x + 5, w.HEIGHT / 2);
        g.drawLine(x + 5, y + 5, x + 5, w.HEIGHT / 2);
        for (int[] circle : sin) {
            g.fillOval(circle[0], circle[1], 2, 2);
        }
        for (int[] circle : cos) {
            g.fillOval(circle[0], circle[1], 2, 2);
        }
        g.drawLine(x + 5, y + 5, sin.get(sin.size() - 1)[0], sin.get(sin.size() - 1)[1]);
        g.drawLine(x + 5, y + 5, cos.get(cos.size() - 1)[0], cos.get(cos.size() - 1)[1]);
        //System.out.println(sin.size() + ", " + cos.size());
        g.dispose();
        bs.show();
    }

    public static void main(String[] args) {
        new Main();
    }
}