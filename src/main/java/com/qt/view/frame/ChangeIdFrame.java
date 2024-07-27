/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.qt.view.frame;

import com.qt.common.ConstKey;
import com.qt.controller.ApiService;
import com.qt.controller.ProcessModelHandle;
import com.qt.model.config.ChangeIdModel;
import com.qt.model.input.UserModel;
import com.qt.model.modelTest.process.ProcessModel;
import com.qt.output.SoundPlayer;
import com.qt.pretreatment.KeyEventManagement;
import com.qt.pretreatment.KeyEventsPackage;
import com.qt.view.AbsKeylistenerFrame;
import com.qt.view.interfaces.MouseClicked;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 *
 * @author Admin
 */
public class ChangeIdFrame extends AbsKeylistenerFrame {

//    private static volatile ChangeIdFrame instance;
    private final ChangeIdModel model;
    private final MouseClicked mouseClick;
    private final KeyEventManagement eventManagement;
    private final KeyEventsPackage eventsPackage;
    private final ProcessModel processModel;
    private final SoundPlayer soundPlayer;
    private final ApiService apiService;

//    public static ChangeIdFrame getInstance() {
//        ChangeIdFrame idFrame = instance;
//        if (idFrame == null) {
//            synchronized (ChangeIdFrame.class) {
//                idFrame = instance;
//                if (idFrame == null) {
//                    instance = idFrame = new ChangeIdFrame();
//                }
//            }
//        }
//        return idFrame;
//    }
    /**
     * Creates new form ChangeCarIdFrame
     */
    public ChangeIdFrame() {
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
            java.util.logging.Logger.getLogger(ChangeIdFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ChangeIdFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ChangeIdFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ChangeIdFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        initComponents();
//        setBackground(new Color(0, 0, 0, 0));
        this.model = new ChangeIdModel();
        this.soundPlayer = SoundPlayer.getInstance();
        this.eventManagement = KeyEventManagement.getInstance();
        this.processModel = ProcessModelHandle.getInstance().getProcessModel();
        this.eventsPackage = new KeyEventsPackage(getClass().getSimpleName(), true);
        this.apiService = ApiService.getInstance();
        this.mouseClick = (bt) -> {
            if (bt != null) {
                String val = bt.getValue();
                keyCheck(val);
            }
        };
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                ChangeIdFrame.this.eventManagement.remove(eventsPackage);
            }
        });
        initKeyPackage();
        initMouseClickEvent();
    }

    private void initMouseClickEvent() {
        this.bt0.setMouseClicked(mouseClick);
        this.bt1.setMouseClicked(mouseClick);
        this.bt2.setMouseClicked(mouseClick);
        this.bt3.setMouseClicked(mouseClick);
        this.bt4.setMouseClicked(mouseClick);
        this.bt5.setMouseClicked(mouseClick);
        this.bt6.setMouseClicked(mouseClick);
        this.bt7.setMouseClicked(mouseClick);
        this.bt8.setMouseClicked(mouseClick);
        this.bt9.setMouseClicked(mouseClick);
        this.bt0.setMouseClicked(mouseClick);
        this.btOk.setMouseClicked(mouseClick);
        this.btBackspace.setMouseClicked(mouseClick);
        this.btCancel.setMouseClicked(mouseClick);
    }

    private void initKeyPackage() {
        this.eventsPackage.addAnyKeyEvent((key) -> {
            keyCheck(key);
        });
    }

    private void keyCheck(String val) {
        if (val == null || val.isBlank()) {
            return;
        }
        if (val.equalsIgnoreCase("x") || val.equals(ConstKey.KEY_BOARD.CANCEL)) {
            this.dispose();
        } else if (val.equalsIgnoreCase("ok") || val.equals(ConstKey.KEY_BOARD.OK)) {
            keyOkClick();
        } else if (val.equals("<--") || val.equals(ConstKey.KEY_BOARD.BACKSPACE)) {
            keyBackspaceClick();
        } else {
            keyNumbeClick(val);
        }
        this.stValue.setValue(this.model.getStringValue());
    }

    private void keyNumbeClick(String val) {
        String modelVal = this.model.getStringValue();
        if (modelVal.length() >= 3
                || val.length() != 1
                || val.charAt(0) < '0'
                || val.charAt(0) > '9') {
            return;
        }
        if (modelVal.equals("0")) {
            this.model.setValue(val);
        } else {
            this.model.setValue(modelVal.concat(val));
        }
    }

    private void keyBackspaceClick() {
        String modelVal = this.model.getStringValue();
        if (!modelVal.isBlank()) {
            if (modelVal.length() == 1) {
                this.model.setValue("");
            } else {
                this.model.setValue(modelVal.substring(0, modelVal.length() - 1));
            }
        }
    }

    private void keyOkClick() {
        if (ProcessModelHandle.getInstance().isTesting()) {
            return;
        }
        String modelVal = this.model.getStringValue();
        if (modelVal.isBlank()) {
            modelVal = "0";
        }
        dispose();
        if (this.model.getName().equals(SBD)) {
            UserModel userModel = this.apiService.checkUserId(modelVal);
            if (userModel != null) {
                ProcessModelHandle.getInstance().setUserId(userModel);
                this.soundPlayer.welcomeId(this.processModel.getId());
            } else {
                this.soundPlayer.userIdInvalid();
            }
        } else {
            if (this.apiService.checkCarId(modelVal)) {
                ProcessModelHandle.getInstance().setCarID(modelVal);
                this.soundPlayer.welcomeCarId(this.processModel.getCarId());
            } else {
                this.soundPlayer.carIdInvalid();
            }
        }
    }
    public static final String SBD = "Số báo danh";
    public static final String SX = "Số xe";

    public ChangeIdModel getModel() {
        return model;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        stValue = new com.qt.view.element.StatusPanel();
        keybroadPanel = new javax.swing.JPanel();
        bt1 = new com.qt.view.element.ButtonDesign();
        bt2 = new com.qt.view.element.ButtonDesign();
        bt3 = new com.qt.view.element.ButtonDesign();
        bt4 = new com.qt.view.element.ButtonDesign();
        bt5 = new com.qt.view.element.ButtonDesign();
        bt6 = new com.qt.view.element.ButtonDesign();
        bt7 = new com.qt.view.element.ButtonDesign();
        bt8 = new com.qt.view.element.ButtonDesign();
        bt9 = new com.qt.view.element.ButtonDesign();
        btOk = new com.qt.view.element.ButtonDesign();
        bt0 = new com.qt.view.element.ButtonDesign();
        btBackspace = new com.qt.view.element.ButtonDesign();
        btCancel = new com.qt.view.element.ButtonDesign();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);
        setLocation(new java.awt.Point(0, 0));
        setName("ChangeIdFrame"); // NOI18N
        setUndecorated(true);
        setResizable(false);
        setType(java.awt.Window.Type.UTILITY);
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.LINE_AXIS));

        stValue.setIconNameTop("Name");

        keybroadPanel.setLayout(new java.awt.GridLayout(4, 3));

        bt1.setText("1");
        keybroadPanel.add(bt1);

        bt2.setText("2");
        keybroadPanel.add(bt2);

        bt3.setText("3");
        keybroadPanel.add(bt3);

        bt4.setText("4");
        keybroadPanel.add(bt4);

        bt5.setText("5");
        keybroadPanel.add(bt5);

        bt6.setText("6");
        keybroadPanel.add(bt6);

        bt7.setText("7");
        keybroadPanel.add(bt7);

        bt8.setText("8");
        keybroadPanel.add(bt8);

        bt9.setText("9");
        keybroadPanel.add(bt9);

        btOk.setOnColor(new java.awt.Color(0, 255, 102));
        btOk.setText("OK");
        keybroadPanel.add(btOk);

        bt0.setText("0");
        keybroadPanel.add(bt0);

        btBackspace.setText("<--");
        keybroadPanel.add(btBackspace);

        btCancel.setOnColor(new java.awt.Color(255, 102, 102));
        btCancel.setText("X");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(stValue, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(keybroadPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 361, Short.MAX_VALUE)
                            .addComponent(btCancel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(stValue, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(keybroadPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btCancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8))
        );

        getContentPane().add(jPanel2);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    public void display(String name) {
        if (name == null || (isVisible() && !this.model.getName().equalsIgnoreCase(name))) {
            return;
        }
        java.awt.EventQueue.invokeLater(() -> {
            this.eventManagement.addKeyEventBackAge(eventsPackage);
            this.model.setName(name);
            if (name.equalsIgnoreCase(SBD)) {
                this.model.setValue(this.processModel.getId());
                this.soundPlayer.inputId();
            } else {
                this.model.setValue(this.processModel.getCarId());
                this.soundPlayer.inputCarId();
            }
            this.stValue.setIconNameTop(this.model.getName());
            this.stValue.setValue(this.model.getStringValue());
            setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.qt.view.element.ButtonDesign bt0;
    private com.qt.view.element.ButtonDesign bt1;
    private com.qt.view.element.ButtonDesign bt2;
    private com.qt.view.element.ButtonDesign bt3;
    private com.qt.view.element.ButtonDesign bt4;
    private com.qt.view.element.ButtonDesign bt5;
    private com.qt.view.element.ButtonDesign bt6;
    private com.qt.view.element.ButtonDesign bt7;
    private com.qt.view.element.ButtonDesign bt8;
    private com.qt.view.element.ButtonDesign bt9;
    private com.qt.view.element.ButtonDesign btBackspace;
    private com.qt.view.element.ButtonDesign btCancel;
    private com.qt.view.element.ButtonDesign btOk;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel keybroadPanel;
    private com.qt.view.element.StatusPanel stValue;
    // End of variables declaration//GEN-END:variables
}
