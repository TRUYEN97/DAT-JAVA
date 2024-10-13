/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.qt.view.frame;

import com.qt.common.ConstKey;
import com.qt.common.Util;
import com.qt.common.timer.WaitTime.Class.TimeS;
import com.qt.controller.modeController.ModeManagement;
import com.qt.mode.AbsTestMode;
import com.qt.pretreatment.KeyEventManagement;
import com.qt.pretreatment.KeyEventsPackage;
import com.qt.view.AbsKeylistenerFrame;
import com.qt.view.element.ButtonDesign;
import com.qt.view.interfaces.MouseClicked;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class ChooseModeFrame extends AbsKeylistenerFrame {

    private final KeyEventsPackage eventsPackage;
    private final TimeS timeS;
    private final List<AbsTestMode> absTestModes;
    private final List<ButtonDesign> buttonDesigns;
    private final MouseClicked mouseClick;
    private final ModeManagement modeManagement;
    private final KeyEventManagement eventManagement;
    private int index;
    private boolean stop;

    /**
     * Creates new form ChooseModeFrame
     *
     * @param modeManagement
     */
    public ChooseModeFrame(ModeManagement modeManagement) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ChooseModeFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ChooseModeFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ChooseModeFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ChooseModeFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        initComponents();
        setBackground(new Color(0, 0, 0, 0));
        this.stop = false;
        this.timeS = new TimeS(10);
        this.modeManagement = modeManagement;
        this.absTestModes = modeManagement.getModes();
        this.buttonDesigns = new ArrayList<>();
        this.eventsPackage = new KeyEventsPackage(getClass().getSimpleName());
        this.eventManagement = KeyEventManagement.getInstance();
        this.eventsPackage.putEvent(ConstKey.KEY_BOARD.RIGHT, (key) -> {
            if (index < buttonDesigns.size() - 1) {
                index += 1;
            } else {
                index = 0;
            }
            this.timeS.update();
        });
        this.eventsPackage.putEvent(ConstKey.KEY_BOARD.LEFT, (key) -> {
            if (index > 0) {
                index -= 1;
            } else {
                index = buttonDesigns.size() - 1;
            }
            this.timeS.update();
        });
        this.eventsPackage.putEvent(ConstKey.KEY_BOARD.OK, (key) -> {
            stop = true;
        });
        this.mouseClick = (bt) -> {
            if (bt != null) {
                index = buttonDesigns.indexOf(bt);
                stop = true;
            }
        };
    }

    private void init() {
        this.pnHome.removeAll();
        this.pnHome.setLayout(new GridLayout(1, 1, 2, 2));
        ButtonDesign buttonDesign;
        for (AbsTestMode absTestMode : absTestModes) {
            if (absTestMode == null) {
                continue;
            }
            buttonDesign = new ButtonDesign();
            buttonDesign.setText(absTestMode.getFullName());
            buttonDesign.setMouseClicked(mouseClick);
            this.pnHome.add(buttonDesign);
            this.buttonDesigns.add(buttonDesign);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnHome = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.LINE_AXIS));

        pnHome.setOpaque(false);

        javax.swing.GroupLayout pnHomeLayout = new javax.swing.GroupLayout(pnHome);
        pnHome.setLayout(pnHomeLayout);
        pnHomeLayout.setHorizontalGroup(
            pnHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 946, Short.MAX_VALUE)
        );
        pnHomeLayout.setVerticalGroup(
            pnHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 97, Short.MAX_VALUE)
        );

        getContentPane().add(pnHome);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    public void display() {
        if (absTestModes.isEmpty()) {
            return;
        }
        if (absTestModes.size() > 1) {
            init();
            eventManagement.addKeyEventBackAge(eventsPackage);
            stop = false;
            setVisible(true);
            timeS.update();
            while (timeS.onTime() && !stop) {
                for (int i = 0; i < buttonDesigns.size(); i++) {
                    if (i == index) {
                        buttonDesigns.get(index).hightLight();
                    } else {
                        buttonDesigns.get(i).removeHightLight();
                    }
                }
                Util.delay(100);
            }
            eventManagement.remove(eventsPackage);
            setVisible(false);
            dispose();
        }
        AbsTestMode absTestMode = absTestModes.get(index);
        modeManagement.updateMode(absTestMode == null ? absTestModes.get(0) : absTestMode);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel pnHome;
    // End of variables declaration//GEN-END:variables
}
