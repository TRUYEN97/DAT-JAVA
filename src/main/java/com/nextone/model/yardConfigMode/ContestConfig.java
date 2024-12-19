/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nextone.model.yardConfigMode;

/**
 *
 * @author Admin
 */
public class ContestConfig {

    private double distanceOut = 0;
    private double distanceLine = 0;
    private double distanceLowerLimit = 0;
    private double distanceUpperLimit = 0;
    private Integer indexOfYardInput = 0;
    
    public ContestConfig(){}

    public ContestConfig(double distanceOut, double distanceLine, double distanceLowerLimit, double distanceUpperLimit) {
        this(distanceOut, distanceLine, distanceLowerLimit, distanceUpperLimit, 0);
    }

    public ContestConfig(double distanceOut, double distanceLine,
            double distanceLowerLimit, double distanceUpperLimit,
            int indexOfYardInput) {
        this.distanceOut = distanceOut;
        this.distanceLine = distanceLine;
        this.distanceLowerLimit = distanceLowerLimit;
        this.distanceUpperLimit = distanceUpperLimit;
        this.indexOfYardInput = indexOfYardInput;
    }

    public void setIndexOfYardInput(Integer indexOfYardInput) {
        this.indexOfYardInput = indexOfYardInput;
    }

    public Integer getIndexOfYardInput() {
        return indexOfYardInput;
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
