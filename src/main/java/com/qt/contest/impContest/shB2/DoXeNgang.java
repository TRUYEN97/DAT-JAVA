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
public class DoXeNgang extends AbsContest {

    private final CheckDistanceIntoContest distanceIntoContest;
    private final CheckWheelCrossedLine crossedLine;
    private double oldDistance;
    private final double distanceOut;

    public DoXeNgang(YardRankModel yardRankModel, ContestConfig contestConfig, int speedLimit) {
        super(ConstKey.CONTEST_NAME.GHEP_XE_DOC,
                ConstKey.CONTEST_NAME.GHEP_XE_DOC, true, true, true, 120);
        this.distanceOut = contestConfig.getDistanceOut();
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

    private boolean success = false;

    @Override
    protected boolean loop() {
        if (!success && this.carModel.isT1() && this.carModel.isT2()
                && this.carModel.isT3()) {
            success = true;
            soundPlayer.successSound();
            this.oldDistance = this.carModel.getDistance();
        }
        if (getDetaDistance(oldDistance) > distanceOut) {
            if (!success) {
                addErrorCode(ConstKey.ERR.IGNORED_PARKING);
            }
            return true;
        }
        return false;
    }

    @Override
    protected boolean isIntoContest() {
        if (this.carModel.isT2()) {
            this.distanceIntoContest.setOldDistance(
                    this.dataTestTransfer.getData(ConstKey.DATA_TRANSFER.OLD_DISTANCE, -1));
        }
        return false;
    }

}
