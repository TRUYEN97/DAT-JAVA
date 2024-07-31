/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.qt.view.element;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.Timer;

/**
 *
 * @author Admin
 */
public class StatusPanel extends javax.swing.JPanel {

    private final Timer timer;
    private boolean blink;
    private boolean status;
    private Icon icon;
    protected int round;
    private Color onColor;
    private Color offColor;

    /**
     * Creates new form StatusPanel
     */
    public StatusPanel() {
        initComponents();
        setBackground(new Color(242, 242, 242, 10));
        this.setOpaque(false);
        round = 30;
        blink = false;
        this.timer = new Timer(500, (e) -> {
            if (blink) {
                status = !status;
                repaint();
            }
        });
    }

    public void setFontNameTop(Font fontName) {
        this.lbNameTop.setFont(fontName);
    }
    
    public void setFontName(Font fontName) {
        this.lbName.setFont(fontName);
    }

    public void setFontValue(Font fontValue) {
        this.lbIcon.setFont(fontValue);
    }
    

    public Color getOnColor() {
        return onColor;
    }

    public void setOnColor(Color onColor) {
        this.onColor = onColor;
    }

    public Color getOffColor() {
        return offColor;
    }

    public void setOffColor(Color offColor) {
        this.offColor = offColor;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public void setBlink(boolean blink) {
        this.blink = blink;
    }

    public void setValue(String value) {
        this.lbIcon.setText(value);
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
        lbIcon.setIcon(icon);
    }
    
    public void setMouseAdapter(MouseAdapter mouseAdapter){
        lbIcon.addMouseListener(mouseAdapter);
    }

    public void setIconPath(String iconName) {
        try {
            icon = new ImageIcon(getClass().getResource(iconName));
//            icon = Util.resizeImageIcon(icon, this.lbIcon.getWidth(), this.lbIcon.getHeight());
        } catch (Exception e) {
            e.printStackTrace();
        }
        lbIcon.setIcon(icon);
    }

    public void setIconName(String name) {
        this.lbName.setText(name);
    }
    
    public void setIconNameTop(String name) {
        this.lbNameTop.setText(name);
    }

    public void setStatus(boolean st) {
        if (st) {
            setOn();
        } else {
            setOff();
        }
    }

    public void setOn() {
        if (blink) {
            if (!this.timer.isRunning()) {
                this.timer.start();
            }
        } else if (!status) {
            status = true;
            repaint();
        }
    }

    public void setOff() {
        if (this.timer.isRunning()) {
            this.timer.stop();
        } else if (status) {
            status = false;
            repaint();
        }
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(new Color(0, 0, 0));
        g2.drawRoundRect(this.lbIcon.getX(), this.lbIcon.getY() + 4, this.lbIcon.getWidth() - 6, this.lbIcon.getHeight() - 6, round - 5, round);
//        g2.drawRoundRect(0, 0, this.getWidth() , this.getHeight(), round, round);
        super.paintBorder(g);
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), round, round);
        g2.setPaint(new GradientPaint(0, 0, Color.white, 0, this.lbIcon.getHeight(), offColor == null? Color.LIGHT_GRAY: offColor));
        g2.fillRoundRect(this.lbIcon.getX(), this.lbIcon.getY(), this.lbIcon.getWidth(), this.lbIcon.getHeight() - 2, round, round);
        g2.fillRoundRect(this.lbIcon.getX(), this.lbIcon.getY() + 4, this.lbIcon.getWidth() - 6, this.lbIcon.getHeight() - 6, round - 5, round);
        super.paint(g);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if (status) {
            g2.setPaint(new GradientPaint(0, 0, Color.white, 0, this.lbIcon.getHeight(), onColor == null? Color.YELLOW: onColor));
        } else {
            g2.setPaint(new GradientPaint(0, 0, Color.white, 0, this.lbIcon.getHeight(), offColor == null? Color.LIGHT_GRAY: offColor));
        }
        g2.fillRoundRect(this.lbIcon.getX(), this.lbIcon.getY() + 4, this.lbIcon.getWidth() - 6, this.lbIcon.getHeight() - 6, round - 5, round);
        super.paintComponent(g);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lbNameTop = new javax.swing.JLabel();
        lbName = new javax.swing.JLabel();
        lbIcon = new javax.swing.JLabel();

        setOpaque(false);

        lbNameTop.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbNameTop.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbNameTop.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        lbNameTop.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lbNameTop.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        lbName.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbName.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        lbName.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lbName.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        lbIcon.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        lbIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbIcon.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE)
                    .addComponent(lbIcon, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbNameTop, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addComponent(lbNameTop)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbIcon, javax.swing.GroupLayout.DEFAULT_SIZE, 73, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbName)
                .addGap(4, 4, 4))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lbIcon;
    private javax.swing.JLabel lbName;
    private javax.swing.JLabel lbNameTop;
    // End of variables declaration//GEN-END:variables
}
