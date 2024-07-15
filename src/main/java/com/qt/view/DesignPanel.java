/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.view;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;
import lombok.Setter;

/**
 *
 * @author Admin
 */
@Setter
public class DesignPanel extends JPanel {

    protected Color color;
    protected Color color1;
    protected int x;
    protected int y;
    protected int w;
    protected int h;
    protected int round;
    protected boolean followWidth;

    public DesignPanel() {
        x = 0;
        y = 0;
        w = -1;
        h = -1;
        round = 0;
        followWidth = true;
    }

    public void setX(int x) {
        if (x < 0) {
            return;
        }
        this.x = x;
    }

    public void setY(int y) {
        if (y < 0) {
            return;
        }
        this.y = y;
    }

    public void setW(int w) {
        if (w < 0) {
            return;
        }
        this.w = w;
    }

    public void setH(int h) {
        if (h < 0) {
            return;
        }
        this.h = h;
    }

    public void setRound(int round) {
        if (round < 0) {
            return;
        }
        this.round = round;
    }

//    @Override
//    protected void paintComponent(Graphics g) {
//        if (w == -1) {
//            w = getWidth();
//        }
//        if (h == -1) {
//            h = getHeight();
//        }
//        Graphics2D g2 = (Graphics2D) g;
//        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//        if (color == null || color1 == null) {
//            g2.setColor(getBackground());
//        } else {
//            GradientPaint gp = new GradientPaint(x, y, color, 0, h, color1);
//            if (followWidth) {
//                gp = new GradientPaint(x, y, color, w, 0, color1);
//            }
//            g2.setPaint(gp);
//        }
//        g2.fillRoundRect(x, y, w, h, round, round);
//        super.paintComponent(g);
//    }

}
