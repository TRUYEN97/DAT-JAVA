/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.nextone.view.component.imp;

import com.nextone.common.ConstKey;
import com.nextone.view.component.UpdateValuePanel;

/**
 *
 * @author Admin
 */
public class ChooseContestShPanel extends UpdateValuePanel {

    /**
     * Creates new form ChooseContestShPanel
     */
    public ChooseContestShPanel() {
        initComponents();
        btChoose.setMouseClicked((design) -> {
            this.carModel.setRemoteValue(ConstKey.KEY_BOARD.SBD);
        });
    }

    @Override
    public void start() {
    }

    @Override
    public void stop() {
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btChoose = new com.nextone.view.element.ButtonDesign();

        setOpaque(false);

        btChoose.setText("Chọn bài thi");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btChoose, javax.swing.GroupLayout.DEFAULT_SIZE, 279, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btChoose, javax.swing.GroupLayout.DEFAULT_SIZE, 72, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.nextone.view.element.ButtonDesign btChoose;
    // End of variables declaration//GEN-END:variables
}
