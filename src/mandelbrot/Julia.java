/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mandelbrot;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
/**
 *
 * @author testtube24
 */
public class Julia {

    JFrame jf;
    DrawPanel dp;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Use the arrow keys to adjust constants. Click to zoom.");
        new Julia().run();
    }

    int w, h;
    
    double cx, cy = 0;
    final double dc = 0.01;

    double minX = -2;
    double maxX = 2;
    double minY = -2;
    double maxY = 2;

    int gen = 50;

    private void run() {
        jf = new JFrame("Julia");
        jf.setDefaultCloseOperation(jf.EXIT_ON_CLOSE);

        dp = new DrawPanel();
        jf.getContentPane().add(BorderLayout.CENTER, dp);

        jf.setSize(1000, 1000);
        jf.setLocation(50, 50);
        jf.setVisible(true);
        jf.setResizable(false);
        
        jf.addMouseListener(new MouseListen());
        jf.addKeyListener(new KeyListen());

        w = jf.getWidth();
        h = jf.getHeight();

    }

    public int recurseFunc(double cReal, double cImag, double zReal, double zImag, int gen) {
        double holdReal = zReal * zReal - zImag * zImag + cReal;
        zImag = 2 * zReal * zImag + cImag;
        zReal = holdReal;

        if (gen > 1 && abs(zReal, zImag) < 2) {
            return recurseFunc(cReal, cImag, zReal, zImag, gen - 1);
        } else if (gen > 1) {
            return gen;
        } else {
            return abs(zReal, zImag) < 2 ? 0 : 1;
        }
    }

    public double abs(double real, double imag) {
        return Math.sqrt(real * real + imag * imag);
    }

    public class DrawPanel extends JPanel {

        public void paintComponent(Graphics g) {
            double x, y;

            double color;

            for (int xVar = 0; xVar < w; xVar++) {
                x = (xVar) * (maxX - minX) / w + minX;
                for (int yVar = 9; yVar < h; yVar++) {
                    y = (yVar) * (maxY - minY) / h + minY;
                    color = recurseFunc(cx, cy, x, y, gen);

                    if (color == 0) {
                        g.setColor(Color.BLACK);
                    } else {
                        color = 1 - (color / gen) * (color / gen);
                        g.setColor(new Color((int) (color * 255), (int) (color * 255), 255));
                    }

                    g.fillRect(xVar, yVar, 1, 1);
                }
            }
        }
    }

    public class MouseListen extends MouseAdapter {
        public void mousePressed(MouseEvent m) {
            double mouseX = m.getX();
            double mouseY = m.getY();
            
            mouseX = (mouseX) * (maxX - minX) / w + minX;
            mouseY = (mouseY) * (maxY - minY) / h + minY;
            
            minX = mouseX - (maxX - minX) / 4;
            maxX = mouseX + (maxX - minX) / 4;
            
            minY = mouseY - (maxY - minY) / 4;
            maxY = mouseY + (maxY - minY) / 4;
            
            jf.repaint();
        }
    }
    
    public class KeyListen extends KeyAdapter {
        public void keyPressed(KeyEvent k) {
            
            if (k.getKeyCode() == KeyEvent.VK_UP)
                cy += dc;
            else if (k.getKeyCode() == KeyEvent.VK_DOWN)
                cy -= dc;
            else if (k.getKeyCode() == KeyEvent.VK_RIGHT)
                cx += dc;
            else if (k.getKeyCode() == KeyEvent.VK_LEFT)
                cx -= dc;
            
            jf.repaint();
        }
    }

}
