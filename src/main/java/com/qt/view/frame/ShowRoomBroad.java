/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.qt.view.frame;

import com.qt.common.ConstKey;
import com.qt.common.ErrorLog;
import com.qt.common.communication.Communicate.IgetName;
import com.qt.view.AbsKeylistenerFrame;
import com.qt.view.element.ButtonDesign;
import java.awt.GridLayout;
import javax.swing.JPanel;
import com.qt.controller.settingElement.IElementSetting;
import com.qt.pretreatment.KeyEventManagement;
import com.qt.pretreatment.KeyEventsPackage;
import com.qt.view.interfaces.IActionCallback;
import com.qt.view.interfaces.MouseClicked;
import java.lang.reflect.Array;
import javax.swing.Timer;

/**
 *
 * @author Admin
 * @param <T>
 */
public class ShowRoomBroad<T extends IgetName> extends AbsKeylistenerFrame implements Runnable {

    private final T[] elements;
    private final ButtonDesign[] buttonDesigns;
    private final MouseClicked mouseClick;
    private final KeyEventManagement eventManagement;
    private final KeyEventsPackage eventsPackage;
    private final int row, col;
    private IActionCallback<T> okAction;
    private IActionCallback<T> closeAction;
    private IActionCallback<T> timeOutAction;
    private int index = 0;
    private int timeOut = 10;
    private final Timer timer;

    /**
     * Creates new form SettingFrame
     *
     * @param clazz
     * @param okACtion
     * @param closeActon
     * @param row
     * @param col
     */
    public ShowRoomBroad(Class<T> clazz,
            IActionCallback<T> okACtion,
            IActionCallback<T> closeActon,
            int row, int col) {
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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ShowRoomBroad.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        initComponents();
        this.row = row <= 0 ? 1 : row;
        this.col = col <= 0 ? 1 : col;
        this.okAction = okACtion;
        this.closeAction = closeActon;
        int tt = col * row;
        this.buttonDesigns = new ButtonDesign[tt];
        this.elements = (T[]) Array.newInstance(clazz, tt);
        this.pnHome.setLayout(new GridLayout(row, col));
        this.eventManagement = KeyEventManagement.getInstance();
        this.eventsPackage = new KeyEventsPackage(getClass().getSimpleName(), true);
        this.eventsPackage.getEventButtonBlink().putButtonBlinkEvent(btCancel.getValue(), btCancel);
        this.timer = new Timer(timeOut * 1000, (e) -> {
            if (this.timeOutAction != null) {
                this.timeOutAction.action(this.elements[index]);
            }
        });
        btCancel.setMouseClicked((design) -> {
            close();
            if (this.timeOutAction != null) {
                this.timeOutAction.action(this.elements[index]);
            }
        });
        this.eventsPackage.putEvent(ConstKey.KEY_BOARD.CANCEL, (key) -> {
            close();
        });
        this.eventsPackage.putEvent(ConstKey.KEY_BOARD.LEFT, (key) -> {
            this.timer.restart();
            index -= 1;
            highLight(false);
        });
        this.eventsPackage.putEvent(ConstKey.KEY_BOARD.RIGHT, (key) -> {
            this.timer.restart();
            index += 1;
            highLight(true);
        });
        this.eventsPackage.putEvent(ConstKey.KEY_BOARD.UP, (key) -> {
            this.timer.restart();
            if (index >= col) {
                index -= col;
            } else {
                index = tt - col + index;
            }
            highLight(false);
        });
        this.eventsPackage.putEvent(ConstKey.KEY_BOARD.DOWN, (key) -> {
            this.timer.restart();
            if (index <= tt - col) {
                index += col;
            } else {
                index = col - tt + index;
            }
            highLight(true);
        });
        this.eventsPackage.putEvent(ConstKey.KEY_BOARD.OK, (key) -> {
            this.timer.restart();
            if (this.okAction == null) {
                return;
            }
            this.okAction.action(this.elements[index]);
        });
        this.mouseClick = (bt) -> {
            this.timer.restart();
            if (bt != null && this.okAction != null) {
                for (int i = 0; i < this.elements.length; i++) {
                    if (this.elements[i] != null
                            && this.buttonDesigns[i].equals(bt)) {
                        index = i;
                        highLight(true);
                        this.okAction.action(this.elements[index]);
                        break;
                    }
                }
            }
        };
        JPanel panel;
        for (int i = 0; i < tt; i++) {
            panel = new JPanel(new GridLayout(1, 1, 5, 5));
            this.buttonDesigns[i] = new ButtonDesign();
            this.buttonDesigns[i].setVisible(false);
            this.buttonDesigns[i].setMouseClicked(mouseClick);
            panel.add(this.buttonDesigns[i]);
            this.pnHome.add(panel);
        }
    }

    public void stopTimer() {
        this.timer.stop();
    }

    public void setTimeOutAction(IActionCallback<T> timeOutAction) {
        this.timeOutAction = timeOutAction;
    }

    public void setTimeOut(int timeOut) {
        if (timeOut < 5) {
            timeOut = 5;
        }
        this.timer.setDelay(timeOut * 1000);
    }

    public void setOkAction(IActionCallback<T> okAction) {
        this.okAction = okAction;
    }

    public void setCloseAction(IActionCallback<T> closeAction) {
        this.closeAction = closeAction;
    }

    public boolean removeElement(int index) {
        if (index >= 0 && index < this.elements.length && this.elements[index] == null) {
            setElem(index, null);
            return true;
        }
        return false;
    }

    public void removeElement(IElementSetting element) {
        if (element == null) {
            return;
        }
        for (int i = 0; i < this.elements.length; i++) {
            if (this.elements[i] == null) {
                continue;
            }
            if (this.elements[i].equals(element)) {
                setElem(i, null);
            }
        }
    }

    public boolean addElement(T element) {
        if (element == null) {
            return false;
        }
        try {
            for (int i = 0; i < this.elements.length; i++) {
                if (this.elements[i] == null) {
                    setElem(i, element);
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            ErrorLog.addError(this, e);
            return false;
        }
    }

    private void setElem(int i, T element) {
        this.elements[i] = element;
        this.buttonDesigns[i].setText(element != null ? element.getName() : "");
        this.buttonDesigns[i].setVisible(element != null);
        this.buttonDesigns[i].setMouseClicked(element != null ? mouseClick : null);
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
        btCancel = new com.qt.view.element.ButtonDesign();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Setting");
        setUndecorated(true);
        setType(java.awt.Window.Type.UTILITY);

        pnHome.setBackground(new java.awt.Color(204, 204, 204));
        pnHome.setLayout(new java.awt.GridLayout(1, 0));

        btCancel.setOnColor(new java.awt.Color(255, 102, 102));
        btCancel.setText("Cancel");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pnHome, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btCancel, javax.swing.GroupLayout.DEFAULT_SIZE, 563, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnHome, javax.swing.GroupLayout.DEFAULT_SIZE, 345, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    public void display() {
        index = -1;
        highLight(true);
        this.eventManagement.addKeyEventBackAge(eventsPackage);
        setVisible(true);
        this.timer.start();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.qt.view.element.ButtonDesign btCancel;
    private javax.swing.JPanel pnHome;
    // End of variables declaration//GEN-END:variables

    public void close() {
        this.setVisible(false);
        this.eventManagement.remove(eventsPackage);
        this.timer.stop();
        if (this.closeAction != null) {
            for (T element : elements) {
                if (element != null) {
                    this.closeAction.action(element);
                }
            }
        }
    }

    private boolean highLight(boolean st) {
        int tt = (row * col) - 1;
        if (index < 0) {
            index = tt;
        } else if (index > tt) {
            index = 0;
        }
        int temp = index;
        while (true) {
            if (this.elements[temp] != null) {
                for (int i = 0; i < this.buttonDesigns.length; i++) {
                    if (i == temp) {
                        this.buttonDesigns[i].hightLight();
                    } else {
                        this.buttonDesigns[i].removeHightLight();
                    }
                }
                index = temp;
                return true;
            }
            if (st) {
                temp++;
            } else {
                temp--;
            }
            if (temp < 0) {
                temp = tt;
            } else if (temp > tt) {
                temp = 0;
            }
            if (index == temp) {
                return false;
            }
        }
    }

    @Override
    public void run() {
        display();
    }

    public void removeAllElement() {
        for (int i = 0; i < this.elements.length; i++) {
            removeElement(i);
        }
    }
}