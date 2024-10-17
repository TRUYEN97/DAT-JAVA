/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.controller.settingElement.imp.yardSetting;

import com.qt.controller.settingElement.IElementSetting;
import com.qt.view.frame.KeyBoardFrame;
import com.qt.view.interfaces.IActionCallback;

/**
 *
 * @author Admin
 * @param <T>
 */
public class LineConfigSetting<T extends Number> implements IElementSetting {

    private final String name;
    private T value;
    private final KeyBoardFrame keyBoardFrame;
    private final IActionCallback<T> updateActionCallback;

    public LineConfigSetting(String name, T value, IActionCallback<T> updateActionCallback) {
        this.name = name;
        this.value = value;
        this.updateActionCallback = updateActionCallback;
        this.keyBoardFrame = new KeyBoardFrame();
        this.keyBoardFrame.setMaxNum(10);
        this.keyBoardFrame.setRemoveZero(true);
        if (value instanceof Double || value instanceof Float) {
            this.keyBoardFrame.setHasPonit(true);
        } else {
            this.keyBoardFrame.setHasPonit(false);
        }
    }

    @Override
    public void close() {
        this.keyBoardFrame.cancel();
    }

    @Override
    public void run() {
        String valString = this.keyBoardFrame.getValue(name, String.valueOf(value));
        if (valString != null) {
            T temp = null;
            if (value instanceof Integer) {
                temp = (T) Integer.valueOf(valString);
            } else if (value instanceof Double) {
                temp = (T) Double.valueOf(valString);
            } else if (value instanceof Float) {
                temp = (T) Float.valueOf(valString);
            }
            if (updateActionCallback == null || updateActionCallback.action(temp)) {
                value = temp;
            }
        }
    }

    @Override
    public String getName() {
        return name;
    }

}
