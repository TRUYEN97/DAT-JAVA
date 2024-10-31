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

    private final CheckWheelCrossedLine crossedLine;
    private double distanceOut;
    private double distanceOutPath;

    public VetBanhXe(YardRankModel yardModel, List<ContestConfig> contestConfigs, int speedLimit) {
        super(ConstKey.CONTEST_NAME.VET_BANH_XE,
                ConstKey.CONTEST_NAME.VET_BANH_XE,
                true, true, true, 120, contestConfigs);
        this.distanceOut = 7;
        this.distanceOutPath = 3;
        this.crossedLine = new CheckWheelCrossedLine(5, () -> {
            return getWheelCrosserLineStatus(yardModel.getRoadZs());
        });
        this.conditionBeginHandle.addConditon(new CheckOverSpeedLimit(speedLimit));
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
        if (this.carModel.getDistance() < 2) {
            return false;
        }
        if (this.carModel.isT2()) {
            into = true;
        }
        if (into && this.carModel.isT3()) {
            into1 = true;
        }
        if (!checkPathLineDone
                && this.carModel.getDistance() >= distanceOutPath) {
            if (!into || !into1) {
                addErrorCode(ConstKey.ERR.WHEEL_OUT_OF_PATH);
            }
            checkPathLineDone = true;
        }
        return checkPathLineDone && (this.carModel.isT1() || this.carModel.isT2());
    }

    @Override
    protected boolean isIntoContest() {
        if (this.carModel.isT1()) {
            into = false;
            into1 = false;
            checkPathLineDone = false;
            if (checkIntoContest(this.carModel.getDistance())) {
                this.distanceOut = this.distanceIntoContest.getContestConfig().getDistanceOut();
                this.distanceOutPath = this.distanceIntoContest.getContestConfig().getDistanceLine();
            }
            this.carModel.setDistance(0);
            return true;
        }
        return false;
    }

}
