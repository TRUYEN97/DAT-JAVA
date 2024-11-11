/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.qt.view.frame;

import com.qt.common.CarConfig;
import com.qt.controller.modeController.ModeManagement;
import com.qt.mode.AbsTestMode;
import com.qt.view.AbsKeylistenerFrame;

/**
 *
 * @author Admin
 */
public class ModeShowRoom extends AbsKeylistenerFrame {

    private final ShowRoomBroad<AbsTestMode> showRoomBroad;
    private final ModeManagement modeManagement;
    private final CarConfig carConfig;

    /**
     * Creates new form ModeShowRoom
     *
     * @param modeManagement
     */
    public ModeShowRoom(ModeManagement modeManagement) {
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
            java.util.logging.Logger.getLogger(ModeShowRoom.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ModeShowRoom.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ModeShowRoom.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ModeShowRoom.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        initComponents();
        this.modeManagement = modeManagement;
        this.showRoomBroad = new ShowRoomBroad<>(AbsTestMode.class,
                null, null, 3, 3
        );
        this.showRoomBroad.setOkAction((t) -> {
            modeManagement.updateMode(t);
            close();
            return true;
        });
        this.showRoomBroad.setTimeOut(10);
        this.showRoomBroad.setTimeOutAction((t) -> {
            modeManagement.updateMode(t);
            close();
            return true;
        });
        this.jPanel1.add(this.showRoomBroad);
        this.carConfig = CarConfig.getInstance();
    }

    private void close() {
        this.setVisible(false);
        this.showRoomBroad.close();
    }

    private void init() {
        this.showRoomBroad.removeAllElement();
        for (AbsTestMode absTestMode : this.modeManagement.getModes()) {
            this.showRoomBroad.addElement(absTestMode);
        }
    }

    public void display() {
        var modes = this.modeManagement.getModes();
        if (modes.isEmpty()) {
            return;
        }
        if (modes.size() > 1) {
            init();
            int index = this.carConfig.getIndexOfModel();
            this.setVisible(true);
            this.showRoomBroad.display(index);
        } else {
            modeManagement.updateMode(modes.get(0));
        }
    }
    
    public int getIndexOfMode(){
        return this.showRoomBroad.getIndex();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Choose mode");
        setAlwaysOnTop(true);
        setUndecorated(true);
        setType(java.awt.Window.Type.UTILITY);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.LINE_AXIS));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 571, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 356, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:
        this.showRoomBroad.close();
    }//GEN-LAST:event_formWindowClosed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
