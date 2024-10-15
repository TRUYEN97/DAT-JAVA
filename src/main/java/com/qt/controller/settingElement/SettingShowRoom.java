/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.controller.settingElement;

import com.qt.view.frame.ShowRoomBroad;

/**
 *
 * @author Admin
 */
public class SettingShowRoom implements Runnable {

    private final ShowRoomBroad<IElementSetting> ShowRoomBroad;
    private final VerifyPassword verifyPassword;

    public SettingShowRoom(int row, int col) {
        this.verifyPassword = new VerifyPassword();
        this.ShowRoomBroad = new ShowRoomBroad<>(IElementSetting.class,
                (t) -> {
                    if (t == null) {
                        return;
                    }
                    t.run();
                },
                (t) -> {
                    if (t == null) {
                        return;
                    }
                    t.close();
                }, row, col);
    }

    public boolean addElementSetting(IElementSetting element) {
        return this.ShowRoomBroad.addElement(element);
    }

    @Override
    public void run() {
        if (this.verifyPassword.check()) {
            this.ShowRoomBroad.display();
        }
    }

}
