/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.controller.modeController;

import com.qt.mode.AbsTestMode;
import com.qt.view.frame.ShowRoomBroad;

/**
 *
 * @author Admin
 */
public class ModeShowRoom {

    private final ShowRoomBroad<AbsTestMode> showRoomBroad;
    private final ModeManagement modeManagement;

    public ModeShowRoom(ModeManagement modeManagement) {
        this.modeManagement = modeManagement;
        this.showRoomBroad = new ShowRoomBroad<>(AbsTestMode.class,
                null, null, 3, 3
        );
        this.showRoomBroad.setOkAction((t) -> {
            modeManagement.updateMode(t);
            this.showRoomBroad.close();
        });
        this.showRoomBroad.setTimeOut(10);
        this.showRoomBroad.setTimeOutAction((t) -> {
            modeManagement.updateMode(t);
            this.showRoomBroad.close();
        });
    }

    private void init() {
        this.showRoomBroad.removeAllElement();
        for (AbsTestMode absTestMode : this.modeManagement.getModes()) {
            this.showRoomBroad.addElement(absTestMode);
        }
    }

    public void display() {
        var modes = this.modeManagement.getModes();
        if (modes.isEmpty()) {
            return;
        }
        if (modes.size() > 1) {
            init();
            this.showRoomBroad.display();
        } else {
            modeManagement.updateMode(modes.get(0));
        }
    }

    public boolean isShowing() {
        return this.showRoomBroad.isVisible();
    }

}
