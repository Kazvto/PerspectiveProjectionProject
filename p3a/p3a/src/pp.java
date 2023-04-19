import java.awt.*;
import javax.swing.*;

public class pp extends JPanel {

    private static final double VIEWING_DISTANCE = 60.0; // in cm
    private static final double SCREEN_SIZE = 30.0; // in cm
    private static final int SCREEN_RESOLUTION = 500; // in pixels
    private static final int SCREEN_ORIGIN_X = SCREEN_RESOLUTION / 2;
    private static final int SCREEN_ORIGIN_Y = SCREEN_RESOLUTION / 2;
    private static final double ZE = -1.0; // viewing axis points to origin
    private static final double XE = 0.0; // X-axis lies in Z=7.5 plane

    pp(){
        translate(cubes, 0, 0, 0);
        rotateX(cubes, 0);
        
    };

    // Cube coordinates
    private static final double[][] cubes = {
        {-1, 1, -1}, {1, 1, -1}, {1, -1, -1}, {-1, -1, -1}, // front face
        {-1, 1, 1}, {1, 1, 1}, {1, -1, 1}, {-1, -1, 1} // back face
    };

    // Cube edges
    private static final int[][] edges = {
        {0, 1}, {1, 3}, {3, 2}, {2, 0}, {4, 5}, {5, 7}, {7, 6},
    {6, 4}, {0, 4}, {1, 5}, {2, 6}, {3, 7} // connecting edges
    };

    // Perspective projection function
    private static double[] project(double x, double y, double z) {
        double[] p = new double[2];
        p[0] = SCREEN_ORIGIN_X + (x - XE * z) * (VIEWING_DISTANCE / z);
        p[1] = SCREEN_ORIGIN_Y + (y - ZE * z) * (VIEWING_DISTANCE / z);
        return p;
    }

    public static void translate(double[][] points, double tx, double ty, double tz) {
        for (int i = 0; i < points.length; i++) {
            points[i][0] += tx;
            points[i][1] += ty;
            points[i][2] += tz;
        }
    }
    
    public static void scale(double[][] points, double cx, double cy, double cz, double sx, double sy, double sz) {
        for (int i = 0; i < points.length; i++) {
            points[i][0] = sx * (points[i][0] - cx) + cx;
            points[i][1] = sy * (points[i][1] - cy) + cy;
            points[i][2] = sz * (points[i][2] - cz) + cz;
        }
    }

    public static void rotateX(double[][] points, double angle) {
        double sinAngle = Math.sin(angle);
        double cosAngle = Math.cos(angle);
        for (int i = 0; i < points.length; i++) {
            double y = points[i][1];
            double z = points[i][2];
            points[i][1] = y * cosAngle - z * sinAngle;
            points[i][2] = y * sinAngle + z * cosAngle;
        }
    }
    
    public static void rotateY(double[][] points, double angle) {
        double sinAngle = Math.sin(angle);
        double cosAngle = Math.cos(angle);
        for (int i = 0; i < points.length; i++) {
            double x = points[i][0];
            double z = points[i][2];
            points[i][0] = x * cosAngle - z * sinAngle;
            points[i][2] = x * sinAngle + z * cosAngle;
        }
    }

    public static void rotateZ(double[][] points, double angle) {
        double sinAngle = Math.sin(angle);
        double cosAngle = Math.cos(angle);
        for (int i = 0; i < points.length; i++) {
            double x = points[i][0];
            double y = points[i][1];
            points[i][0] = x * cosAngle - y * sinAngle;
            points[i][1] = x * sinAngle + y * cosAngle;
        }
    }
    
    

    // Paint method for drawing the cube
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw each edge of the cube
        g.setColor(Color.BLACK);
        for (int[] edge : edges) {
            double[] p1 = project(cubes[edge[0]][0], cubes[edge[0]][1], cubes[edge[0]][2]);
            double[] p2 = project(cubes[edge[1]][0], cubes[edge[1]][1], cubes[edge[1]][2]);
            g.drawLine((int) p1[0], (int) p1[1], (int) p2[0], (int) p2[1]);
        }
    }

    // Main method for creating and showing the JFrame
    public static void main(String[] args) {
        JFrame frame = new JFrame("Cube Projection");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(SCREEN_RESOLUTION, SCREEN_RESOLUTION);
        frame.setLocationRelativeTo(null);
        pp panel = new pp();
        panel.setBackground(Color.WHITE);
        frame.add(panel);
        frame.setVisible(true);


    }
}
