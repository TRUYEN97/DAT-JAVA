/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nextone.pretreatment;

import com.nextone.view.element.ButtonDesign;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Admin
 */
public class KeyEventButtonBlink {
    
    private final Map<String, ButtonDesign> buttonDesigns;
    
    public KeyEventButtonBlink() {
        this.buttonDesigns = new HashMap<>();
    }
    
    public Map<String, ButtonDesign> getButtonDesigns() {
        return buttonDesigns;
    }
    
    public void putButtonBlinkEvent(String key, ButtonDesign buttonDesign) {
        if (key == null || buttonDesign == null) {
            return;
        }
        buttonDesigns.put(key, buttonDesign);
    }
    
    public void removeButtonBlinkEvent(String key) {
        if (key == null) {
            return;
        }
        buttonDesigns.remove(key);
    }
    
    public void addButtonBlinkEvent(ButtonDesign buttonDesign) {
        if (buttonDesign == null || 
                this.buttonDesigns.containsKey(buttonDesign.getValue())) {
            return;
        }
        this.buttonDesigns.put(buttonDesign.getValue(), buttonDesign);
    }
    
    public void removeButtonBlinkEvent(ButtonDesign buttonDesign) {
        if (buttonDesign == null) {
            return;
        }
        removeButtonBlinkEvent(buttonDesign.getValue());
    }
    
    public void addAllButtonBlinkEvent(Map<String, ButtonDesign> buttonDesigns) {
        if (buttonDesigns == null) {
            return;
        }
        for (String key : buttonDesigns.keySet()) {
            if (this.buttonDesigns.containsKey(key)) {
                continue;
            }
            this.buttonDesigns.put(key, buttonDesigns.get(key));
        }
    }
    
    public void attack(String key) {
        if (this.buttonDesigns.containsKey(key)) {
            this.buttonDesigns.get(key).blink();
        }
    }
}
