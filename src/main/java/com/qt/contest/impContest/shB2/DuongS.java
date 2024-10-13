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
public class DuongS extends ContestHasMutiLine {

    private final CheckWheelCrossedLine crossedLine;
    private double oldDistance;
    private double distanceOut = 1;

    public DuongS(YardRankModel contestConfig, List<ContestConfig> contestConfigs, int speedLimit) {
        super(ConstKey.CONTEST_NAME.CHU_S, ConstKey.CONTEST_NAME.CHU_S, true, true, true, 120, contestConfigs);
        this.crossedLine = new CheckWheelCrossedLine(5, () -> {
            return getWheelCrosserLineStatus(contestConfig.getRoadSs());
        });
        this.conditionBeginHandle.addConditon(new CheckOverSpeedLimit(speedLimit));
        this.conditionIntoHandle.addConditon(crossedLine);
    }

    @Override
    protected void init() {
    }

    @Override
    protected boolean loop() {
        return getDetaDistance(oldDistance) > distanceOut
                && (this.carModel.isT1() || this.carModel.isT2() || this.carModel.isT3())
                && this.carModel.getStatus() == ConstKey.CAR_ST.FORWARD;
    }

    @Override
    protected boolean isIntoContest() {
        if (this.carModel.isT1() && this.carModel.getStatus() == ConstKey.CAR_ST.FORWARD) {
            this.oldDistance = this.carModel.getDistance();
            if (checkIntoContest(this.oldDistance)) {
                this.distanceOut = this.distanceIntoContest.getContestConfig().getDistanceOut();
            } else {
                stop();
            }
            return true;
        }
        return false;
    }

}
