package net.kkolyan.evo.awt;

import net.kkolyan.evo.plain.Renderer;
import net.kkolyan.evo.plain.Scene;
import net.kkolyan.evo.plain.Vector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.JFrame;
import javax.swing.WindowConstants;
import javax.swing.event.MouseInputAdapter;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.font.LineMetrics;
import java.awt.geom.Rectangle2D;

/**
 * @author nplekhanov
 */
public class GraphicEngine {
    private static final Logger logger = LoggerFactory.getLogger(GraphicEngine.class);
    private static final double ZOOM_FACTOR = 1.1;
    private Scene scene;
    private double fps;

    private JFrame frame = new JFrame();

    private int scalePower;
    private Vector offset = new Vector();

    private double getScale() {
        return Math.pow(ZOOM_FACTOR, scalePower);
    }

    public GraphicEngine(Scene scene) {
        this.scene = scene;
        setSize(200, 200);
        setFps(30);
    }

    public void setFps(double fps) {
        this.fps = fps;
    }

    public void setSize(int width, int height) {
        Dimension size = new Dimension(width, height);

        frame.setSize(size);

        Dimension ss = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(ss.width / 2 - size.width / 2, ss.height / 2 - size.height / 2);
    }

    public void start() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                final Component component = new SceneRenderer();

                frame.getContentPane().add(component);
                frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                frame.setVisible(true);


                MouseInputAdapter mouseInputAdapter = new MouseInputAdapter() {
                    @Override
                    public void mouseWheelMoved(MouseWheelEvent e) {
                        scalePower += e.getScrollAmount() * e.getWheelRotation();
                    }

                    private Point lastPoint;

                    @Override
                    public void mousePressed(MouseEvent e) {
                        if (e.getButton() == MouseEvent.BUTTON1) {
                            lastPoint = e.getPoint();
                        }
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                        if (e.getButton() == MouseEvent.BUTTON1) {
                            lastPoint = null;
                        }
                    }

                    @Override
                    public void mouseDragged(MouseEvent e) {
                        if (lastPoint != null) {
                            Vector t = new Vector();
                            t.setX(e.getX() - lastPoint.getX());
                            t.setY(e.getY() - lastPoint.getY());
                            t.multiply(getScale()); //screen to scene metrics
                            offset.transpose(t);
                            lastPoint = e.getPoint();
                        }
                    }
                };
                component.addMouseListener(mouseInputAdapter);
                component.addMouseMotionListener(mouseInputAdapter);
                component.addMouseWheelListener(mouseInputAdapter);

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            while (frame.isVisible()) {
                                component.repaint();
                                Thread.sleep((long) (1000.0 / fps));
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            logger.error(e.toString(), e);
                        } finally {
                            logger.info("{} stopped", Thread.currentThread().getName());
                        }
                    }
                }, "Canvas updater");
                thread.start();
            }
        });
    }

    public void stop() {
        frame.dispose();
    }

    private class SceneRenderer extends Component implements Renderer {
        private Graphics2D canvas;

        private int deScale(double n) {
            return (int) (n / getScale()); // scene to screen metrics
        }

        @Override
        public void drawEllipse(double centerX, double centerY, double radiusX, double radiusY, Color color, boolean fill) {
            if (Double.isNaN(centerX) || Double.isNaN(centerY)) {
                throw new IllegalArgumentException("NaN is not supported");
            }
            int x = deScale(offset.getX() + centerX - radiusX) + getWidth() / 2;
            int y = deScale(offset.getY() + centerY - radiusY) + getHeight() / 2;
            int width = deScale(radiusX * 2);
            int height = deScale(radiusY * 2);

            canvas.setColor(color);
            if (fill) {
                canvas.fillOval(x, y, width, height);
            } else {
                canvas.drawOval(x, y, width, height);
            }
        }

        @Override
        public void drawText(String text, int x, int y, Color color) {
            canvas.setColor(color);
            canvas.drawString(text, x, y);
        }

        @Override
        public void drawLabel(double x, double y, String text, Color color) {
            Rectangle2D bounds = canvas.getFontMetrics().getStringBounds(text, canvas);
            int screenX = deScale(offset.getX() + x - 0*bounds.getWidth()/2) + getWidth() / 2;
            int screenY = deScale(offset.getY() + y - 0*bounds.getHeight()/2) + getHeight() / 2;
            canvas.setColor(color);
            canvas.drawString(text, screenX, screenY);
        }

        @Override
        public void paint(Graphics g) {
            canvas = (Graphics2D) g;
            canvas.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            canvas.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            canvas.setColor(Color.BLACK);
            canvas.fillRect(0, 0, getWidth(), getHeight());
            scene.render(this);
        }
    }
}
