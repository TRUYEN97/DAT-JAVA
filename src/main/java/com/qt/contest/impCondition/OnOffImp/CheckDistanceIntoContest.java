/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.contest.impCondition.OnOffImp;

import com.qt.common.ConstKey;
import com.qt.contest.impCondition.AbsOnOffCondition;

/**
 *
 * @author Admin
 */
public class CheckDistanceIntoContest extends AbsOnOffCondition{

    private double oldDistance;
    private final double lowerLimit;
    private final double upperLimit;

    public CheckDistanceIntoContest(boolean important, double lowerLimit, double upperLimit) {
        this.oldDistance = -1;
        this.important = important;
        this.lowerLimit = lowerLimit;
        this.upperLimit = upperLimit;
    }

    public void setOldDistance(double oldDistance) {
        this.oldDistance = oldDistance;
    }
    
    
    @Override
    protected boolean signal() {
        if (oldDistance < 0) {
            return true;
        }
        double deta = this.carModel.getDistance() - oldDistance;
        oldDistance = -1;
        return deta <= upperLimit && deta >= lowerLimit;
    }

    @Override
    protected void action() {
        this.setErrorcode(ConstKey.ERR.WRONG_LANE);
    }
    
}
