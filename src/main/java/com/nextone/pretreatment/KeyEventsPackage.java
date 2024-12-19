/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nextone.pretreatment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Admin
 */
public class KeyEventsPackage {

    protected final List<IKeyEvent> anyKeyEvents;
    protected final Map<String, IKeyEvent> keyEvents;
    protected final KeyEventButtonBlink eventButtonBlink;
    private boolean justMe;
    private final String name;

    public KeyEventsPackage(String name) {
        this(name, false);
    }

    public KeyEventsPackage(String name, boolean justMe) {
        this.keyEvents = new HashMap<>();
        this.anyKeyEvents = new ArrayList<>();
        this.justMe = justMe;
        this.name = name;
        this.eventButtonBlink = new KeyEventButtonBlink();
    }

    public KeyEventButtonBlink getEventButtonBlink() {
        return eventButtonBlink;
    }

    public void merge(KeyEventsPackage keyEventsPackage) {
        putEvents(keyEventsPackage.keyEvents);
        addAllAnyKeyEvent(keyEventsPackage.anyKeyEvents);
        eventButtonBlink.addAllButtonBlinkEvent(keyEventsPackage.eventButtonBlink.getButtonDesigns());
    }

    public void addAnyKeyEvent(IKeyEvent event) {
        if (event == null || this.anyKeyEvents.contains(event)) {
            return;
        }
        this.anyKeyEvents.add(event);
    }

    public void addAllAnyKeyEvent(List<IKeyEvent> events) {
        if (events == null) {
            return;
        }
        for (IKeyEvent event : events) {
            addAnyKeyEvent(event);
        }
    }

    public void putEvent(String key, IKeyEvent event) {
        if (event == null || this.keyEvents.containsKey(key)) {
            return;
        }
        this.keyEvents.put(key, event);
    }

    public void putEvents(Map<String, IKeyEvent> modeKeyEvents) {
        if (modeKeyEvents == null) {
            return;
        }
        for (String key : modeKeyEvents.keySet()) {
            if (keyEvents.containsKey(key)) {
                continue;
            }
            this.keyEvents.put(key, modeKeyEvents.get(key));
        }
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

    public void attack(String key) {
        new Thread(() -> {
            this.eventButtonBlink.attack(key);
            IKeyEvent event = keyEvents.get(key);
            if (event != null) {
                event.action(key);
            } else {
                for (IKeyEvent anyKeyEvent : anyKeyEvents) {
                    if (anyKeyEvent == null) {
                        continue;
                    }
                    anyKeyEvent.action(key);
                }
            }
        }).start();
    }

    boolean isName(String name) {
        if (this.name == null || name == null) {
            return false;
        }
        return this.name.equalsIgnoreCase(name);
    }

    public void clear() {
        this.keyEvents.clear();
        this.anyKeyEvents.clear();
    }

    public void remove(String key) {
        this.keyEvents.remove(key);
    }

    public void remove(IKeyEvent event) {
        this.anyKeyEvents.remove(event);
    }

}
