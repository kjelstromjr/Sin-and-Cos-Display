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

public class Main implements Runnable {
    Thread thread;
    Window w;
    boolean running;
    //int tick = 10000000, tickCount = 0;
    int x = 0, y = 0;
    double deg = 0;


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
        g.dispose();
        bs.show();
    }

    public static void main(String[] args) {
        new Main();
    }
}