/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.contest.impContest.shB2;

import com.qt.common.ConstKey;
import com.qt.contest.impCondition.CheckWheelCrossedLine;
import com.qt.contest.impCondition.OnOffImp.CheckOverSpeedLimit;
import com.qt.model.input.yard.YardRankModel;
import com.qt.model.yardConfigMode.ContestConfig;
import java.util.List;

/**
 *
 * @author Admin
 */
public class VetBanhXe extends ContestHasMutiLine {

    private final CheckWheelCrossedLine crossedPath;
    private final CheckWheelCrossedLine crossedLine;
    private double distanceOut;
    private double distanceOutPath;
    private double oldDistance;

    public VetBanhXe(YardRankModel yardModel, List<ContestConfig> contestConfigs, int speedLimit) {
        super(ConstKey.CONTEST_NAME.VET_BANH_XE,
                ConstKey.CONTEST_NAME.VET_BANH_XE,
                true, true, true, 120, contestConfigs);
        this.distanceOut = 7;
        this.distanceOutPath = 3;
        this.crossedPath = new CheckWheelCrossedLine(5, () -> {
            return getWheelCrosserLineStatus(yardModel.getRoadZs());
        });
        this.crossedLine = new CheckWheelCrossedLine(5, () -> {
            return getWheelCrosserLineStatus(yardModel.getRoadZs());
        });
        this.conditionBeginHandle.addConditon(new CheckOverSpeedLimit(speedLimit));
        this.conditionIntoHandle.addConditon(crossedPath);
        this.conditionIntoHandle.addConditon(crossedLine);
    }

    @Override
    protected void init() {
    }

    private boolean into = false;
    private boolean into1 = false;
    private boolean checkPathLineDone = false;

    @Override
    protected boolean loop() {
        if (getDetaDistance(this.oldDistance) < 1.5 ) {
            return false;
        }
        if (this.carModel.isT2()) {
            into = true;
        }
        if (this.carModel.isT3()) {
            into1 = true;
        }
        if (!checkPathLineDone
                && getDetaDistance(this.oldDistance) >= distanceOutPath
                && this.carModel.isT1()) {
            if (!into || !into1) {
                addErrorCode(ConstKey.ERR.WHEEL_OUT_OF_PATH);
            }
            crossedPath.stop();
            checkPathLineDone = true;
        }
        return getDetaDistance(this.oldDistance) > distanceOut 
                && (this.carModel.isT1() || this.carModel.isT2() || this.carModel.isT3());
    }

    @Override
    protected boolean isIntoContest() {
        if (this.carModel.isT3()) {
            into = false;
            into1 = false;
            checkPathLineDone = false;
            this.oldDistance = this.carModel.getDistance();
            if (checkIntoContest(this.oldDistance)) {
                this.distanceOut = this.distanceIntoContest.getContestConfig().getDistanceOut();
                this.distanceOutPath = this.distanceIntoContest.getContestConfig().getDistanceLine();
            }
            return true;
        }
        return false;
    }

}
