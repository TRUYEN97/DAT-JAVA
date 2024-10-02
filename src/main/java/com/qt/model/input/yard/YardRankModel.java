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


    public YardRankModel() {
        this.roadZ = new RoadZ();
        this.roadS = false;
        this.packing = false;
        this.packing1 = false;
    }

    private final RoadZ roadZ;
    private boolean roadS;
    private boolean packing;
    private boolean packing1;

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
