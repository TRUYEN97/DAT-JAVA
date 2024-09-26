/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.contest.impContest.shB2;

import com.qt.common.ConstKey;
import com.qt.contest.AbsContest;
import com.qt.contest.impCondition.CheckWheelCrossedLine;
import com.qt.contest.impCondition.OnOffImp.CheckDistanceIntoContest;
import com.qt.model.input.yard.RoadZ;

/**
 *
 * @author Admin
 */
public class VetBanhXe extends AbsContest {

    private final CheckDistanceIntoContest distanceIntoContest;
    private final CheckWheelCrossedLine crossedPath;
    private final CheckWheelCrossedLine crossedLine;
    private final double distanceOut;
    private final double distanceOutPath;
    private double oldDistnce;

    public VetBanhXe(RoadZ roadZ, double lowerLimit,
            double upperLimit, double distanceOutPath, double distanceOut) {
        super(ConstKey.CONTEST_NAME.VET_BANH_XE,
                ConstKey.CONTEST_NAME.VET_BANH_XE,
                true, true, true, 120);
        this.distanceOut = distanceOut;
        this.distanceOutPath = distanceOutPath;
        this.distanceIntoContest = new CheckDistanceIntoContest(true, 10, 12);
        this.crossedPath = new CheckWheelCrossedLine(5, () -> {
            return roadZ.isWheelPath();
        });
        this.crossedLine = new CheckWheelCrossedLine(5, () -> {
            return roadZ.isWheelCrossideLine();
        });
        this.conditionIntoHandle.addConditon(distanceIntoContest);
        this.conditionIntoHandle.addConditon(crossedPath);
        this.conditionIntoHandle.addConditon(crossedLine);
    }

    @Override
    protected void init() {
    }

    private int count = 0;
    private boolean checkPathLineDone = false;

    @Override
    protected boolean loop() {
        if (this.carModel.isT2()) {
            count++;
        }
        if (this.carModel.isT3()) {
            count++;
        }
        if (!checkPathLineDone && getDetaDistance(this.oldDistnce) > distanceOutPath) {
            if (count == 2) {
                addErrorCode(ConstKey.ERR.WHEEL_OUT_OF_PATH);
            }
            crossedPath.stop();
            checkPathLineDone = true;
        }
        return getDetaDistance(this.oldDistnce) > distanceOut;
    }

    @Override
    protected boolean isIntoContest() {
        if (this.carModel.isT1()) {
            count = 0;
            checkPathLineDone = false;
            this.oldDistnce = this.carModel.getDistance();
            this.distanceIntoContest.setOldDistance(
                    this.dataTestTransfer.getData(ConstKey.DATA_TRANSFER.OLD_DISTANCE, -1));
        }
        return false;
    }

}
