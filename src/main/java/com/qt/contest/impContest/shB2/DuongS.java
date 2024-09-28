/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.contest.impContest.shB2;

import com.qt.common.ConstKey;
import com.qt.contest.AbsContest;
import com.qt.contest.impCondition.CheckWheelCrossedLine;
import com.qt.contest.impCondition.OnOffImp.CheckDistanceIntoContest;
import com.qt.contest.impCondition.OnOffImp.CheckOverSpeedLimit;
import com.qt.model.input.yard.YardRankModel;
import com.qt.model.yardConfigMode.ContestConfig;

/**
 *
 * @author Admin
 */
public class DuongS extends AbsContest {

    private final CheckDistanceIntoContest distanceIntoContest;
    private final CheckWheelCrossedLine crossedLine;
    private double oldDistance;

    public DuongS(YardRankModel yardRankModel, ContestConfig contestConfig, int speedLimit) {
        super(ConstKey.CONTEST_NAME.CHU_S, ConstKey.CONTEST_NAME.CHU_S, true, true, true, 120);
        this.distanceIntoContest = new CheckDistanceIntoContest(true, 
                contestConfig.getDistanceLowerLimit(), contestConfig.getDistanceUpperLimit());
        this.crossedLine = new CheckWheelCrossedLine(5, () -> {
            return yardRankModel.isRoadS();
        });
        this.conditionBeginHandle.addConditon(new CheckOverSpeedLimit(speedLimit));
        this.conditionIntoHandle.addConditon(crossedLine);
    }

    @Override
    protected void init() {
    }

    @Override
    protected boolean loop() {
        return getDetaDistance(oldDistance) > 2 && this.carModel.isT1();
    }

    @Override
    protected boolean isIntoContest() {
        if (this.carModel.isT1()) {
            this.oldDistance = this.carModel.getDistance();
            this.distanceIntoContest.setOldDistance(
                    this.dataTestTransfer.getData(ConstKey.DATA_TRANSFER.OLD_DISTANCE, -1));
        }
        return false;
    }

}
