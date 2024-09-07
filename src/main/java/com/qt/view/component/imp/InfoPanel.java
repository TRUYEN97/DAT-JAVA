/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.qt.view.component.imp;

import com.qt.common.ConstKey;
import com.qt.common.ErrorLog;
import com.qt.common.Util;
import com.qt.common.communication.Communicate.Impl.Cmd.Cmd;
import com.qt.input.camera.CameraRunner;
import com.qt.view.component.UpdateValuePanel;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Properties;
import javax.swing.JLabel;

/**
 *
 * @author Admin
 */
public class InfoPanel extends UpdateValuePanel {


    /**
     * Creates new form InfoPanel
     */
    public InfoPanel() {
        super(1000);
        initComponents();
        setBackground(new Color(255, 255, 255, 70));
        this.stId.setMouseAdapter(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                carModel.setRemoteValue(ConstKey.KEY_BOARD.SBD);
            }
        });
        this.stCarId.setMouseAdapter(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                carModel.setRemoteValue(ConstKey.KEY_BOARD.SO_XE);
            }

        });
        this.btIn.setMouseClicked((design) -> {
            this.carModel.setRemoteValue(ConstKey.KEY_BOARD.IN);
        });
        CameraRunner.getInstance().setImageLabel(lbImg);
        try {
            Properties properties = new Properties();
            properties.load(getClass().getResourceAsStream("/config.properties"));
            String ip = properties.getProperty(ConstKey.SERVER_PING_ADDR);
            new Thread(() -> {
                Cmd cmd = new Cmd();
                while (true) {
                    if (cmd.ping(ip, 1)) {
                        this.stServer.setOn();
                    } else {
                        this.stServer.setOff();
                    }
                    Util.delay(1000);
                }
            }).start();
        } catch (IOException ex) {
            ex.printStackTrace();
            ErrorLog.addError("ApiService-constructor", ex);
        }
    }

    public JLabel getLbImg() {
        return lbImg;
    }

    @Override
    protected void updateValues() {
        this.stCarId.setValue(this.testDataModel.getCarId());
        this.stId.setValue(this.testDataModel.getId());
        this.stModeName.setValue(this.testDataModel.getModeName());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        stId = new com.qt.view.element.StatusPanel();
        stCarId = new com.qt.view.element.StatusPanel();
        stModeName = new com.qt.view.element.StatusPanel();
        btIn = new com.qt.view.element.ButtonDesign();
        lbImg = new javax.swing.JLabel();
        stServer = new com.qt.view.element.StatusPanel();

        setOpaque(false);

        stId.setIconName("SBD");

        stCarId.setIconName("Số xe");

        stModeName.setIconName("Phần thi");

        btIn.setFontValue(new java.awt.Font("Arial", 1, 18)); // NOI18N
        btIn.setText("In phiếu điểm");

        lbImg.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        stServer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/server.png"))); // NOI18N
        stServer.setOnColor(new java.awt.Color(102, 255, 204));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lbImg, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(stModeName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(stCarId, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(stId, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(stServer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btIn, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbImg, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(stCarId, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(stId, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(stModeName, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btIn, javax.swing.GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE)
                    .addComponent(stServer, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap(12, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.qt.view.element.ButtonDesign btIn;
    private javax.swing.JLabel lbImg;
    private com.qt.view.element.StatusPanel stCarId;
    private com.qt.view.element.StatusPanel stId;
    private com.qt.view.element.StatusPanel stModeName;
    private com.qt.view.element.StatusPanel stServer;
    // End of variables declaration//GEN-END:variables

}