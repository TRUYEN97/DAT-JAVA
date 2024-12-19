/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.nextone.view.frame;

import com.nextone.common.ConstKey;
import com.nextone.common.communication.Communicate.IgetName;
import com.nextone.pretreatment.KeyEventsPackage;
import com.nextone.view.AbsKeylistenerFrame;
import com.nextone.view.interfaces.IActionCallback;
import com.nextone.view.interfaces.ICreater;
import lombok.Setter;

/**
 *
 * @author Admin
 * @param <T>
 */
@Setter
public class EditElementShowRoom<T extends IgetName> extends AbsKeylistenerFrame implements Runnable {

    private final ShowRoomBroad<T> showRoomBroad;
    private final KeyEventsPackage eventsPackage;
    private IActionCallback<T> okAction;
    private ICreater<T> addAction;
    private IActionCallback<T> deleteAction;

    /**
     * Creates new form EditElementShowRoom * @param clazz
     *
     * @param clazz
     * @param selectACtion
     * @param closeActon
     * @param row
     * @param col
     */
    public EditElementShowRoom(Class<T> clazz,
            IActionCallback<T> selectACtion,
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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(EditElementShowRoom.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EditElementShowRoom.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EditElementShowRoom.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EditElementShowRoom.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        initComponents();
        this.showRoomBroad = new ShowRoomBroad<>(clazz, selectACtion, closeActon, row, col);
        this.pnHome.add(this.showRoomBroad);
        this.eventsPackage = this.showRoomBroad.getEventsPackage();
        this.eventsPackage.getEventButtonBlink().putButtonBlinkEvent(this.btCancel.getValue(), this.btCancel);
        this.eventsPackage.getEventButtonBlink().putButtonBlinkEvent(ConstKey.KEY_BOARD.SBD, this.btAdd);
        this.eventsPackage.getEventButtonBlink().putButtonBlinkEvent(ConstKey.KEY_BOARD.BACKSPACE, this.btDelete);
        this.eventsPackage.getEventButtonBlink().putButtonBlinkEvent(ConstKey.KEY_BOARD.OK, this.btOk);
        this.btCancel.setMouseClicked((design) -> {
            close();
        });
        this.btDelete.setMouseClicked((design) -> {
            deleteElem();
        });
        this.btAdd.setMouseClicked((design) -> {
            addNewElem();
        });
        this.btOk.setMouseClicked((design) -> {
            chooseElem();
        });
        this.eventsPackage.putEvent(ConstKey.KEY_BOARD.CANCEL, (key) -> {
            close();
        });
        this.eventsPackage.putEvent(ConstKey.KEY_BOARD.BACKSPACE, (key) -> {
            deleteElem();
        });
        this.eventsPackage.putEvent(ConstKey.KEY_BOARD.SBD, (key) -> {
            addNewElem();
        });
        this.eventsPackage.putEvent(ConstKey.KEY_BOARD.OK, (key) -> {
            chooseElem();
        });
    }

    public void display() {
        this.setVisible(true);
        this.showRoomBroad.display();
    }

    public void close() {
        setVisible(false);
        this.showRoomBroad.close();
    }

    @Override
    public void run() {
        display();
    }

    public void removeAllElement() {
        this.showRoomBroad.removeAllElement();
    }

    public void setShowRoomName(String name) {
        this.jLabel1.setText(name);
    }

    public void stopTimer() {
        this.showRoomBroad.stopTimer();
    }

    public void setTimeOutAction(IActionCallback<T> timeOutAction) {
        this.showRoomBroad.setTimeOutAction(timeOutAction);
    }

    public void setTimeOut(int timeOut) {
        this.showRoomBroad.setTimeOut(timeOut);
    }

    public void setSelectAction(IActionCallback<T> selectAction) {
        this.showRoomBroad.setOkAction(selectAction);
    }
    public void setOkAction(IActionCallback<T> okAction) {
        this.okAction = okAction;
    }

    public void setCloseAction(IActionCallback<T> closeAction) {
        this.showRoomBroad.setCloseAction(closeAction);
    }

    public boolean removeElement(int index) {
        return this.showRoomBroad.removeElement(index);
    }

    public void removeElement(T element) {
        this.showRoomBroad.removeElement(element);
    }

    public boolean addElement(T element) {
        return this.showRoomBroad.addElement(element);
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
        btOk = new com.nextone.view.element.ButtonDesign();
        jLabel1 = new javax.swing.JLabel();
        btCancel = new com.nextone.view.element.ButtonDesign();
        btDelete = new com.nextone.view.element.ButtonDesign();
        btAdd = new com.nextone.view.element.ButtonDesign();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);
        setUndecorated(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        pnHome.setBackground(new java.awt.Color(204, 204, 204));
        pnHome.setLayout(new java.awt.GridLayout(1, 0));

        btOk.setOnColor(new java.awt.Color(0, 255, 102));
        btOk.setText("Ok");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        btCancel.setOnColor(new java.awt.Color(255, 102, 102));
        btCancel.setText("Cancel");

        btDelete.setOnColor(new java.awt.Color(255, 102, 102));
        btDelete.setText("Delete");

        btAdd.setOnColor(new java.awt.Color(0, 204, 204));
        btAdd.setText("Add");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btOk, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(pnHome, javax.swing.GroupLayout.PREFERRED_SIZE, 544, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnHome, javax.swing.GroupLayout.DEFAULT_SIZE, 335, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(btCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addComponent(btDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btOk, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
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
    private com.nextone.view.element.ButtonDesign btAdd;
    private com.nextone.view.element.ButtonDesign btCancel;
    private com.nextone.view.element.ButtonDesign btDelete;
    private com.nextone.view.element.ButtonDesign btOk;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel pnHome;
    // End of variables declaration//GEN-END:variables

    private void deleteElem() {
        T elem = this.showRoomBroad.getCurrentElement();
        if (elem == null) {
            return;
        }
        if (this.deleteAction == null || this.deleteAction.action(elem)) {
            this.showRoomBroad.removeElement(elem);
        }
    }

    private void addNewElem() {
        if (this.addAction != null) {
            T elem = this.addAction.create();
            if (elem == null) {
                return;
            }
            this.showRoomBroad.addElement(elem);
        }
    }

    private void chooseElem() {
        T elem = this.showRoomBroad.getCurrentElement();
        if (elem == null) {
            return;
        }
        if (this.okAction != null) {
            this.okAction.action(elem);
        }
    }
}
