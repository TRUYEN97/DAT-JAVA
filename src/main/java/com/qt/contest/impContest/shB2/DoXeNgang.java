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
public class DoXeNgang extends ContestHasMutiLine {

    private final CheckWheelCrossedLine crossedLine;
    private double oldDistance;
    private double distanceOut = 1;

    public DoXeNgang(YardRankModel yardRankModel, List<ContestConfig> contestConfigs, int speedLimit) {
        super(ConstKey.CONTEST_NAME.GHEP_XE_DOC,
                ConstKey.CONTEST_NAME.GHEP_XE_DOC, true, true, true, 120, contestConfigs);
        this.crossedLine = new CheckWheelCrossedLine(5, () -> {
            return getWheelCrosserLineStatus(yardRankModel.getPacking1s());
        });
        this.conditionBeginHandle.addConditon(new CheckOverSpeedLimit(speedLimit));
        this.conditionIntoHandle.addConditon(crossedLine);
    }

    @Override
    protected void init() {
    }

    private boolean success = false;
    private boolean hasIntoPaking = false;

    @Override
    protected boolean loop() {
        if (this.carModel.getStatus() == ConstKey.CAR_ST.BACKWARD) {
            if (!hasIntoPaking && this.carModel.isT3() 
                    && getDetaDistance(oldDistance) < 0.3) {
                hasIntoPaking = true;
            }
            if (!success && this.carModel.isT1() && this.carModel.isT2()
                    && this.carModel.isT3()) {
                success = true;
                soundPlayer.successSound();
            }
        }
        if (getDetaDistance(oldDistance) > distanceOut) {
            if (!success) {
                if (hasIntoPaking) {
                    addErrorCode(ConstKey.ERR.PARCKED_WRONG_POS);
                } else {
                    addErrorCode(ConstKey.ERR.IGNORED_PARKING);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    protected boolean isIntoContest() {
        if (this.carModel.isT2() && this.carModel.getStatus() == ConstKey.CAR_ST.BACKWARD) {
            hasIntoPaking = false;
            success = false;
            this.oldDistance = this.carModel.getDistance();
            if (this.checkIntoContest(this.oldDistance)) {
                this.distanceOut = this.distanceIntoContest.getContestConfig().getDistanceOut();
            }else{
                stop();
            }
            return true;
        }
        return false;
    }

}
