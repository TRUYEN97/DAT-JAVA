/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nextone.model.input.yard;

/**
 *
 * @author Admin
 */
public class TrafficLightModel {
    
    public static final int GREEN = 0;
    public static final int YELLOW = 1;
    public static final int RED = 2;
    private int trafficLight;
    private int time;

    public TrafficLightModel() {
        this.trafficLight = GREEN;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
    
    public int getTrafficLight() {
        return trafficLight;
    }

    public void setTrafficLight(int trafficLight) {
        this.trafficLight = trafficLight;
    }
}
