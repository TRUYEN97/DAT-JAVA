/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.nextone.view.frame;

import com.nextone.controller.ErrorcodeHandle;
import com.nextone.model.modelTest.ErrorcodeWithContestNameModel;
import com.nextone.view.AbsKeylistenerFrame;
import java.util.List;
import javax.swing.Timer;

/**
 *
 * @author Admin
 */
public class ShowErrorcode extends AbsKeylistenerFrame {

    private final List<ErrorcodeWithContestNameModel> ewcnms;
    private final Timer timer;

    /**
     * Creates new form ShowErrorcode
     */
    public ShowErrorcode() {
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
            java.util.logging.Logger.getLogger(ShowErrorcode.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ShowErrorcode.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ShowErrorcode.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ShowErrorcode.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        initComponents();
        this.listErrorcode.addKeyListener(keyListener);
        this.ewcnms = ErrorcodeHandle.getInstance().getEwcnms();
        this.timer = new Timer(1000, (e) -> {
            if (this.listErrorcode.getCount() != ewcnms.size()) {
                this.listErrorcode.clear();
                for (ErrorcodeWithContestNameModel ewcnm : ewcnms) {
                    if (ewcnm == null) {
                        continue;
                    }
                    this.listErrorcode.addItem(ewcnm);
                }
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        listErrorcode = new com.nextone.view.swing.ListErrorcode<>();
        btErrorcode = new com.nextone.view.element.ButtonDesign();
        btScore = new com.nextone.view.element.ButtonDesign();
        btName = new com.nextone.view.element.ButtonDesign();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Lỗi");
        setAlwaysOnTop(true);
        setBackground(new java.awt.Color(204, 204, 255));
        setLocation(new java.awt.Point(50, 100));
        setResizable(false);
        setType(java.awt.Window.Type.UTILITY);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane2.setOpaque(false);

        listErrorcode.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        listErrorcode.setOpaque(false);
        jScrollPane2.setViewportView(listErrorcode);

        btErrorcode.setText("Mã Lỗi");

        btScore.setText("Điểm");

        btName.setText("Bài thi");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 438, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(btErrorcode, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btScore, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btName, javax.swing.GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE)
                    .addComponent(btScore, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(btErrorcode, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 325, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:
        this.timer.stop();
    }//GEN-LAST:event_formWindowClosed

    public void display() {
        java.awt.EventQueue.invokeLater(() -> {
            this.timer.start();
            setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.nextone.view.element.ButtonDesign btErrorcode;
    private com.nextone.view.element.ButtonDesign btName;
    private com.nextone.view.element.ButtonDesign btScore;
    private javax.swing.JScrollPane jScrollPane2;
    private com.nextone.view.swing.ListErrorcode<String> listErrorcode;
    // End of variables declaration//GEN-END:variables
}
