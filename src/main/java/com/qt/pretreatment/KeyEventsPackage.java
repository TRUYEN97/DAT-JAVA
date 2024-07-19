/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.pretreatment;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Admin
 */
public class KeyEventsPackage {

    private final Map<Integer, IKeyEvent> modeKeyEvents;
    private boolean justMe;
    private final String name;

    public KeyEventsPackage(String name) {
        this(name, false);
    }

    public KeyEventsPackage(String name, boolean justMe) {
        this.modeKeyEvents = new HashMap<>();
        this.justMe = justMe;
        this.name = name;
    }

    public void putEvent(int key, IKeyEvent event) {
        if (event == null) {
            return;
        }
        this.modeKeyEvents.put(key, event);
    }

    public void putEvents(Map<Integer, IKeyEvent> modeKeyEvents) {
        if (modeKeyEvents == null) {
            return;
        }
        this.modeKeyEvents.putAll(modeKeyEvents);
    }

    public String getName() {
        return name;
    }

    public void setJustMe(boolean justMe) {
        this.justMe = justMe;
    }

    public boolean isJustMe() {
        return justMe;
    }

    public IKeyEvent getEven(int key) {
        return modeKeyEvents.get(key);
    }

    boolean isName(String name) {
        if (this.name == null || name == null) {
            return false;
        }
        return this.name.equalsIgnoreCase(name);
    }

    public void clear() {
        this.modeKeyEvents.clear();
    }

    public void remove(int key) {
        this.modeKeyEvents.remove(key);
    }

}
