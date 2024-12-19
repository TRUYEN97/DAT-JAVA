/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.nextone.view.modeView;

import javax.swing.JLabel;


/**
 *
 * @author Admin
 */
public class SaHinhDebug extends AbsModeView {

    /**
     * Creates new form PanelHome
     */
    public SaHinhDebug() {
        initComponents();
//        setOpaque(false);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        infoPanel = new com.nextone.view.component.imp.InfoPanel();
        valuePanal = new com.nextone.view.component.imp.ValuePanal();
        carStatusPanal = new com.nextone.view.component.imp.CarStatusPanal();
        senserView1 = new com.nextone.view.component.imp.SenserView();
        chooseContestShPanel1 = new com.nextone.view.component.imp.ChooseContestShPanel();

        setBackground(new java.awt.Color(0, 153, 153));

        infoPanel.setBackground(new java.awt.Color(102, 102, 255));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(infoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(valuePanal, javax.swing.GroupLayout.DEFAULT_SIZE, 384, Short.MAX_VALUE)
                    .addComponent(senserView1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(chooseContestShPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(carStatusPanal, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(carStatusPanal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(valuePanal, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(senserView1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(chooseContestShPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(infoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.nextone.view.component.imp.CarStatusPanal carStatusPanal;
    private com.nextone.view.component.imp.ChooseContestShPanel chooseContestShPanel1;
    private com.nextone.view.component.imp.InfoPanel infoPanel;
    private com.nextone.view.component.imp.SenserView senserView1;
    private com.nextone.view.component.imp.ValuePanal valuePanal;
    // End of variables declaration//GEN-END:variables

    @Override
    public JLabel getImgLabel() {
        return this.infoPanel.getImgLabel();
    }

    @Override
    public void start() {
        this.carStatusPanal.start();
        this.infoPanel.start();
        this.senserView1.start();
        this.valuePanal.start();
    }

    @Override
    public void stop() {
        this.carStatusPanal.stop();
        this.infoPanel.stop();
        this.senserView1.stop();
        this.valuePanal.stop();
    }

    @Override
    public boolean isStarted() {
        return this.carStatusPanal.isStarted();
    }

}