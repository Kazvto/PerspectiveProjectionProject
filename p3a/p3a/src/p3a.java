import java.awt.*;
import javax.swing.*;

public class p3a extends JPanel {
    double sx = 100, sy = 100, sz = 100;

    double[][] cubes = {
        {-1, 1, -1}, //a 
        {1, 1, -1}, //b
        {1, -1, -1}, //c
        {-1, -1, -1},//d
        {-1, 1, 1}, //e
        {1, 1, 1}, //f
        {1, -1, 1}, //g
        {-1, -1, 1}};//h

    int[][] edges = {
     {0, 1}, //ab
     {1, 2}, //bc
     {2, 3}, //cd
     {3, 0}, //da
     {4, 5}, //ef
     {5, 6}, //fg
     {6, 7}, //gh
     {4, 7}, //ge
    {0, 4}, //
    {1, 5}, 
    {2, 6}, 
    {2, 6}, 
    {3, 7}
};

    public p3a() {
        setPreferredSize(new Dimension(640, 640));
        setBackground(Color.white);
        
        // transformations
        distance(0);
        scale(cubes, 0, 0 , 0, sx, sy, sz);
        rotateX(cubes, 0);
        rotateY(cubes, 0);
        rotateZ(cubes, 0);
        translate(cubes,0,0,0);
        eye(20, 20, 20);
        

        

    }
    // translate
    public static void translate(double[][] points, double tx, double ty, double tz) {
        for (int i = 0; i < points.length; i++) {
            points[i][0] += tx;
            points[i][1] += ty;
            points[i][2] += tz;
        }
    }
    // scale
    public static void scale(double[][] points, double cx, double cy, double cz, double sx, double sy, double sz) {
        for (int i = 0; i < points.length; i++) {
            points[i][0] = sx * (points[i][0] - cx) + cx;
            points[i][1] = sy * (points[i][1] - cy) + cy;
            points[i][2] = sz * (points[i][2] - cz) + cz;
        }
    }
    // rotate around x axis
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
    // rotate around y axis
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
    // rotate around z axis
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
    public void eye(double ex, double ey, double ez) {
        // Translate the cube so the eye is at the origin
        translate(cubes, -ex, -ey, -ez);
    
        // Rotate the cube so the z-axis is aligned with the line of sight
        double thetaX = Math.atan2(ez, ey);
        double thetaY = Math.atan2(ez, ex);
        rotateX(cubes, thetaX);
        rotateY(cubes, thetaY);
    
        // Translate the cube back to its original position
        translate(cubes, ex, ey, ez);
    }

    void distance(double d){
        sx = sx + d;
        sy = sy + d;
        sz = sz + d;
    }
    
    // draw cube
    void drawCube(Graphics2D g) {
        g.translate(getWidth() / 2, getHeight() / 2);
        double d = 500; // distance between viewer and screen
        double[][] points2D = new double[cubes.length][2];
    
        // Apply perspective projection to each point
        for (int i = 0; i < cubes.length; i++) {
            double x = cubes[i][0];
            double y = cubes[i][1];
            double z = cubes[i][2];
            points2D[i][0] = x / (z + d);
            points2D[i][1] = y / (z + d);
        }
    
        // Draw the edges
        for (int i = 0; i < edges.length; i++) {
            int p1 = edges[i][0];
            int p2 = edges[i][1];
            double x1 = points2D[p1][0];
            double y1 = points2D[p1][1];
            double x2 = points2D[p2][0];
            double y2 = points2D[p2][1];
            g.drawLine((int)(x1 * getWidth()), (int)(y1 * getHeight()), (int)(x2 * getWidth()), (int)(y2 * getHeight()));
        }
    }
    
    @Override
    public void paintComponent(Graphics gg) {
        super.paintComponent(gg);
        Graphics2D g = (Graphics2D) gg;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        drawCube(g);
    }
    // main
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame f = new JFrame();
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setTitle("Perspective Projection");
            f.setResizable(false);
            f.add(new p3a(), BorderLayout.CENTER);
            f.pack();
            f.setLocationRelativeTo(null);
            f.setVisible(true);
        });
        
    }

}