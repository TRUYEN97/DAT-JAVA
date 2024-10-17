/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.controller.settingElement;

import com.qt.view.frame.MenuShowRoom;

/**
 *
 * @author Admin
 */
public class SettingShowRoom implements Runnable {

    private final MenuShowRoom<IElementSetting> ShowRoomBroad;
    private final VerifyPassword verifyPassword;

    public SettingShowRoom(int row, int col) {
        this.verifyPassword = new VerifyPassword();
        this.ShowRoomBroad = new MenuShowRoom<>(IElementSetting.class,
                (t) -> {
                    if (t == null) {
                        return false;
                    }
                    t.run();
                    return true;
                },
                (t) -> {
                    if (t == null) {
                        return false;
                    }
                    t.close();
                    return true;
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
