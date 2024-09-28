/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.model.yardConfigMode;

/**
 *
 * @author Admin
 */
public class ContestConfig {

    private double distanceOut;
    private double distanceLine;
    private double distanceLowerLimit;
    private double distanceUpperLimit;
    public ContestConfig() {
    }

    public ContestConfig(double distanceOut, double distanceLine, double distanceLowerLimit, double distanceUpperLimit) {
        this.distanceOut = distanceOut;
        this.distanceLine = distanceLine;
        this.distanceLowerLimit = distanceLowerLimit;
        this.distanceUpperLimit = distanceUpperLimit;
    }

    public double getDistanceOut() {
        return distanceOut;
    }

    public void setDistanceOut(double distanceOut) {
        this.distanceOut = distanceOut;
    }

    public double getDistanceLine() {
        return distanceLine;
    }

    public void setDistanceLine(double distanceLine) {
        this.distanceLine = distanceLine;
    }

    public double getDistanceLowerLimit() {
        return distanceLowerLimit;
    }

    public void setDistanceLowerLimit(double distanceLowerLimit) {
        this.distanceLowerLimit = distanceLowerLimit;
    }

    public double getDistanceUpperLimit() {
        return distanceUpperLimit;
    }

    public void setDistanceUpperLimit(double distanceUpperLimit) {
        this.distanceUpperLimit = distanceUpperLimit;
    }
    
}
