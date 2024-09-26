/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.model.input.yard;

/**
 *
 * @author Admin
 */
public class YardRankModel {

    public static final int GREEN = 0;
    public static final int YELLOW = 1;
    public static final int RED = 2;

    public YardRankModel() {
        this.roadZ = new RoadZ();
        this.roadS = false;
        this.packing = false;
        this.packing1 = false;
        this.trafficLight = GREEN;
        this.trafficLight1 = GREEN;
    }

    private final RoadZ roadZ;
    private boolean roadS;
    private boolean packing;
    private boolean packing1;
    private int trafficLight;
    private int trafficLight1;

    public int getTrafficLight() {
        return trafficLight;
    }

    public void setTrafficLight(int trafficLight) {
        this.trafficLight = trafficLight;
    }

    public int getTrafficLight1() {
        return trafficLight1;
    }

    public void setTrafficLight1(int trafficLight1) {
        this.trafficLight1 = trafficLight1;
    }

    public RoadZ getRoadZ() {
        return roadZ;
    }

    public boolean isRoadS() {
        return roadS;
    }

    public void setRoadS(boolean roadS) {
        this.roadS = roadS;
    }

    public boolean isPacking() {
        return packing;
    }

    public void setPacking(boolean packing) {
        this.packing = packing;
    }

    public boolean isPacking1() {
        return packing1;
    }

    public void setPacking1(boolean packing1) {
        this.packing1 = packing1;
    }

}
