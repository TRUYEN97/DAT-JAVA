/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.nextone.view.component.imp;

import com.nextone.input.serial.MCUSerialHandler;
import com.nextone.view.component.UpdateValuePanel;
import java.awt.Color;

/**
 *
 * @author Admin
 */
public class CarStatusPanal extends UpdateValuePanel {

    /**
     * Creates new form CarStatusPanal
     */
    private final MCUSerialHandler serialHandler;
    private final Color defaultColor;

    public CarStatusPanal() {
        initComponents();
        this.defaultColor = getBackground();
        this.stValue.setValue("0");
        this.serialHandler = MCUSerialHandler.getInstance();
    }

    private boolean st = false;
    private int carStOld = -1;

    @Override
    protected void updateValues() {
        boolean conn = this.serialHandler.isConnect();
        if (conn != st) {
            st = conn;
            if (conn) {
                setBackground(new Color(140, 240, 140, 86));
            } else {
                setBackground(defaultColor);
            }
        }
        this.stAt.setStatus(this.carModel.isAt());
        this.stNt.setStatus(this.carModel.isNt());
        this.stNp.setStatus(this.carModel.isNp());
        this.stPt.setStatus(this.carModel.isPt());
        this.stCm.setStatus(this.carModel.isCm());
        this.stAt.setStatus(this.carModel.isAt());
        this.stS1.setStatus(this.carModel.isS1());
        this.stS2.setStatus(this.carModel.isS2());
        this.stS3.setStatus(this.carModel.isS3());
        this.stS4.setStatus(this.carModel.isS4());
        int n = this.carModel.getGearBoxValue();
        this.stValue.setValue(String.format("%s", n == 0 ? "N" : n));
        int carSt = this.carModel.getStatus();
        if (this.carStOld != carSt) {
            this.carStOld = carSt;
            if (carSt == 0) {
                this.stValue.setIconName("Dừng");
            } else if (carSt > 0) {
                this.stValue.setIconName("Tiến");
            } else if (carSt < 0) {
                this.stValue.setIconName("Lùi");
            }
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

        stAt = new com.nextone.view.element.StatusPanel();
        stPt = new com.nextone.view.element.StatusPanel();
        stNt = new com.nextone.view.element.StatusPanel();
        stNp = new com.nextone.view.element.StatusPanel();
        stValue = new com.nextone.view.element.StatusPanel();
        stCm = new com.nextone.view.element.StatusPanel();
        stS2 = new com.nextone.view.element.StatusPanel();
        stS1 = new com.nextone.view.element.StatusPanel();
        stS4 = new com.nextone.view.element.StatusPanel();
        stS3 = new com.nextone.view.element.StatusPanel();

        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 153, 255)));
        setOpaque(false);

        stAt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/AT.png"))); // NOI18N
        stAt.setIconName("Dây an toàn");

        stPt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/PT.png"))); // NOI18N
        stPt.setIconName("Phanh tay");

        stNt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/NT.png"))); // NOI18N
        stNt.setIconName("Xi nhan trái");

        stNp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/NP.png"))); // NOI18N
        stNp.setIconName("Xi nhan phải");

        stValue.setIcon(null);
        stValue.setIconName("...");

        stCm.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/CM.png"))); // NOI18N
        stCm.setIconName("Chết máy");

        stS2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/circle.png"))); // NOI18N
        stS2.setIconName("S2");

        stS1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/circle.png"))); // NOI18N
        stS1.setIconName("S1");

        stS4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/circle.png"))); // NOI18N
        stS4.setIconName("S4");

        stS3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/circle.png"))); // NOI18N
        stS3.setIconName("S3");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(stPt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)
                    .addComponent(stNt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)
                    .addComponent(stCm, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)
                    .addComponent(stS1, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)
                    .addComponent(stS3, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(stAt, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
                    .addComponent(stNp, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
                    .addComponent(stValue, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
                    .addComponent(stS2, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
                    .addComponent(stS4, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(stNt, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
                    .addComponent(stNp, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(stAt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(stPt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(stValue, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(stCm, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE))
                .addGap(0, 4, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(stS2, javax.swing.GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE)
                    .addComponent(stS1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(stS3, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(stS4, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.nextone.view.element.StatusPanel stAt;
    private com.nextone.view.element.StatusPanel stCm;
    private com.nextone.view.element.StatusPanel stNp;
    private com.nextone.view.element.StatusPanel stNt;
    private com.nextone.view.element.StatusPanel stPt;
    private com.nextone.view.element.StatusPanel stS1;
    private com.nextone.view.element.StatusPanel stS2;
    private com.nextone.view.element.StatusPanel stS3;
    private com.nextone.view.element.StatusPanel stS4;
    private com.nextone.view.element.StatusPanel stValue;
    // End of variables declaration//GEN-END:variables

}