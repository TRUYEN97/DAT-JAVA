/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.nextone.view.component.imp;

import com.nextone.common.ConstKey;
import com.nextone.contest.AbsContest;
import com.nextone.view.component.UpdateValuePanel;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 *
 * @author Admin
 */
public class ValuePanal extends UpdateValuePanel {

    /**
     * Creates new form ValuePanal
     */
    public ValuePanal() {
        super(1000);
        initComponents();
        setOpaque(false);
        setBackground(new Color(255, 255, 255, 70));
        this.stScore.setMouseAdapter(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ValuePanal.this.carModel.setRemoteValue(ConstKey.KEY_BOARD.SHOW_ERROR);
            }
        });
    }

    @Override
    protected void updateValues() {
        this.stDistance.setValue(String.format("%.1f", this.carModel.getDistance()));
        this.stV.setValue(String.format("%.1f", this.carModel.getSpeed()));
        this.stRpm.setValue(String.format("%s", this.carModel.getRpm()));
        this.stScore.setValue(String.format("%s", this.testDataModel.getScore()));
        if (this.testDataModel.getTestTime() == 0) {
            this.stTotalTime.setValue("0");
        } else {
            long t = this.testDataModel.getTestTime() / 1000;
            long m = t / 60;
            long s = t - (m * 60);
            this.stTotalTime.setValue(String.format("%d:%d", m, s));
        }
        AbsContest contest = this.testDataModel.getContest();
        if (contest != null) {
            if (contest.getTestTime() == 0) {
                this.stTime.setValue("0");
            } else {
                long t = contest.getTestTime() / 1000;
                long m = t / 60;
                long s = t - (m * 60);
                this.stTime.setValue(String.format("%d:%d", m, s));
            }
            this.stCurrContest.setValue(contest.getName());
        } else {
            this.stTime.setValue("0");
            this.stCurrContest.setValue("...");
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

        stDistance = new com.nextone.view.element.StatusPanel();
        stV = new com.nextone.view.element.StatusPanel();
        stTime = new com.nextone.view.element.StatusPanel();
        stTotalTime = new com.nextone.view.element.StatusPanel();
        stRpm = new com.nextone.view.element.StatusPanel();
        stScore = new com.nextone.view.element.StatusPanel();
        stCurrContest = new com.nextone.view.element.StatusPanel();

        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 153, 255)));

        stDistance.setIconName("S (m)");
        stDistance.setValue("0");

        stV.setIconName("V (Km/h)");
        stV.setValue("0");

        stTime.setIconName("T (S)");
        stTime.setValue("0");

        stTotalTime.setIconName("TT(s)");
        stTotalTime.setValue("0");

        stRpm.setIconName("RPM");
        stRpm.setValue("0");

        stScore.setIconName("Điểm");
        stScore.setValue("0");

        stCurrContest.setIconName("");
        stCurrContest.setIconNameTop("Phần thi hiện tại");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(stCurrContest, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(stTotalTime, javax.swing.GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE)
                            .addComponent(stDistance, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(stScore, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, 0)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(stV, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
                            .addComponent(stTime, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(stRpm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(stDistance, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
                    .addComponent(stV, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(stTotalTime, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(stTime, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(stScore, javax.swing.GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE)
                    .addComponent(stRpm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(stCurrContest, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.nextone.view.element.StatusPanel stCurrContest;
    private com.nextone.view.element.StatusPanel stDistance;
    private com.nextone.view.element.StatusPanel stRpm;
    private com.nextone.view.element.StatusPanel stScore;
    private com.nextone.view.element.StatusPanel stTime;
    private com.nextone.view.element.StatusPanel stTotalTime;
    private com.nextone.view.element.StatusPanel stV;
    // End of variables declaration//GEN-END:variables

}
