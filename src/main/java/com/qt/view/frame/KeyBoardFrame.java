/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.qt.view.frame;

import com.qt.common.ConstKey;
import com.qt.common.Util;
import com.qt.model.config.ChangeIdModel;
import com.qt.pretreatment.KeyEventManagement;
import com.qt.pretreatment.KeyEventsPackage;
import com.qt.view.AbsKeylistenerFrame;
import com.qt.view.element.ButtonDesign;
import com.qt.view.interfaces.MouseClicked;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.Timer;

/**
 *
 * @author Admin
 */
public class KeyBoardFrame extends AbsKeylistenerFrame {

    private static int INDEX_INSATNCE = 0;
    public static final String SO_ENCODER = "Encoder/m";
    public static final String SBD = "Số báo danh";
    public static final String SX = "Số xe";
    public static final String PASSWORD = "Mật khẩu";
    public static final String DELAY_DIGNEL_LIGHT = "Độ chễ xi Nhan";

    private final ChangeIdModel model;
    private final MouseClicked mouseClick;
    private final KeyEventManagement eventManagement;
    private final KeyEventsPackage eventsPackage;
    private final Timer timer;
    private final boolean passwordMode;
    private int st;
    private int maxNum;
    private boolean removeZero;
    private boolean hasPonit;

    public KeyBoardFrame() {
        this(false);
    }

    /**
     * Creates new form ChangeCarIdFrame
     *
     * @param pwMode
     */
    public KeyBoardFrame(boolean pwMode) {
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
            java.util.logging.Logger.getLogger(KeyBoardFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(KeyBoardFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(KeyBoardFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(KeyBoardFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        INDEX_INSATNCE += 1;
        this.passwordMode = pwMode;
        this.maxNum = 3;
        this.removeZero = true;
        this.hasPonit = false;
        initComponents();
//        setBackground(new Color(0, 0, 0, 0));
        this.timer = new Timer(10000, (e) -> {
            st = -1;
        });
        this.model = new ChangeIdModel();
        this.eventManagement = KeyEventManagement.getInstance();
        this.eventsPackage = new KeyEventsPackage(getClass().getSimpleName() + INDEX_INSATNCE, true);
        this.addButton(bt0);
        this.addButton(bt1);
        this.addButton(bt2);
        this.addButton(bt3);
        this.addButton(bt4);
        this.addButton(bt5);
        this.addButton(bt6);
        this.addButton(bt7);
        this.addButton(bt8);
        this.addButton(bt9);
        this.addButton(btPoint);
        this.addButton(ConstKey.KEY_BOARD.BACKSPACE, btBackspace);
        this.addButton(btCancel);
        this.addButton(ConstKey.KEY_BOARD.OK, btOk);
        this.mouseClick = (bt) -> {
            if (bt != null) {
                String val = bt.getValue();
                keyCheck(val);
            }
        };
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                KeyBoardFrame.this.eventManagement.remove(eventsPackage);
                st = -1;
                timer.stop();
            }
        });
        initKeyPackage();
        initMouseClickEvent();
    }

    public void setHasPonit(boolean hasPonit) {
        this.hasPonit = hasPonit;
    }

    public void setRemoveZero(boolean removeZero) {
        this.removeZero = removeZero;
    }

    public void setMaxNum(int maxNum) {
        if (maxNum < 1) {
            this.maxNum = 1;
        } else if (maxNum > 10) {
            this.maxNum = 10;
        }
        this.maxNum = maxNum;
    }

    public void setWaitTimeMs(int time) {
        this.timer.setDelay(time);
    }

    private void initMouseClickEvent() {
        for (ButtonDesign buttonDesign : eventsPackage.getEventButtonBlink().getButtonDesigns().values()) {
            buttonDesign.setMouseClicked(mouseClick);
        }
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
        if (val.equals(ConstKey.KEY_BOARD.CANCEL)) {
            st = -1;
        } else if (val.equalsIgnoreCase("ok") || val.equals(ConstKey.KEY_BOARD.OK)) {
            st = 1;
        } else if (val.equals("<--") || val.equals(ConstKey.KEY_BOARD.BACKSPACE)) {
            keyBackspaceClick();
        } else {
            keyNumbeClick(val);
        }
        updateValue();
        this.timer.restart();
    }

    private void keyNumbeClick(String val) {
        String modelVal = this.model.getStringValue();
        if (modelVal.length() >= this.maxNum || !val.matches("^[0-9]|\\.$")) {
            return;
        }
        if (val.equals(".")) {
            if (hasPonit && !modelVal.isBlank() && !modelVal.contains(".")) {
                this.model.setValue(modelVal.concat(val));
            }
        } else if (modelVal.equals("0") && removeZero) {
            this.model.setValue(val);
        } else {
            this.model.setValue(modelVal.concat(val));
        }
    }

    private void keyBackspaceClick() {
        String modelVal = this.model.getStringValue();
        if (!modelVal.isBlank()) {
            int length = modelVal.length();
            if (length == 1) {
                this.model.setValue("");
            } else {
                this.model.setValue(modelVal.substring(0, modelVal.length() - 1));
            }
        }
    }

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
        btPoint = new com.qt.view.element.ButtonDesign();
        bt0 = new com.qt.view.element.ButtonDesign();
        btBackspace = new com.qt.view.element.ButtonDesign();
        btCancel = new com.qt.view.element.ButtonDesign();
        btOk = new com.qt.view.element.ButtonDesign();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Nhập");
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

        btPoint.setText(".");
        keybroadPanel.add(btPoint);

        bt0.setText("0");
        keybroadPanel.add(bt0);

        btBackspace.setText("<--");
        keybroadPanel.add(btBackspace);

        btCancel.setOnColor(new java.awt.Color(255, 102, 102));
        btCancel.setText("Cancel");

        btOk.setOnColor(new java.awt.Color(0, 255, 102));
        btOk.setText("OK");

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
                            .addComponent(keybroadPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 361, Short.MAX_VALUE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(btCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btOk, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(stValue, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(keybroadPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btCancel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btOk, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(8, 8, 8))
        );

        getContentPane().add(jPanel2);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    public String getValue(String name) {
        return getValue(name, "");
    }

    public String getValue(String name, String value) {
        if (name == null || (isVisible() && !this.model.getName().equalsIgnoreCase(name))) {
            return null;
        }
        this.eventManagement.addKeyEventBackAge(eventsPackage);
        this.model.setName(name);
        this.model.setValue(value == null ? "" : value);
        this.stValue.setIconNameTop(this.model.getName());
        updateValue();
        setVisible(true);
        this.st = 0;
        this.timer.start();
        while (this.st == 0) {
            Util.delay(500);
        }
        setVisible(false);
        dispose();
        String result = this.model.getStringValue();
        if (result != null && !result.isBlank() && result.charAt(result.length() - 1) == '.') {
            this.model.setValue(result.substring(0, result.length() - 1));
        }
        return st > 0 ? this.model.getStringValue() : null;
    }

    private void updateValue() {
        String val = this.model.getStringValue();
        if (passwordMode) {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < val.length(); i++) {
                builder.append("*");
            }
            this.stValue.setValue(builder.toString());
        } else {
            this.stValue.setValue(val);
        }
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
    private com.qt.view.element.ButtonDesign btPoint;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel keybroadPanel;
    private com.qt.view.element.StatusPanel stValue;
    // End of variables declaration//GEN-END:variables

    private void addButton(ButtonDesign bt) {
        this.eventsPackage.getEventButtonBlink().putButtonBlinkEvent(bt.getValue(), bt);
    }

    private void addButton(String key, ButtonDesign bt) {
        this.eventsPackage.getEventButtonBlink().putButtonBlinkEvent(key, bt);
    }

    public void cancel() {
        st = -1;
    }

    public void setDefaultValue(String value) {
        if (value == null) {
            return;
        }
        this.model.setValue(value);
    }
}
