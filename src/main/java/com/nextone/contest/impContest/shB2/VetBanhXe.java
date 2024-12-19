/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nextone.contest.impContest.shB2;

import com.nextone.common.ConstKey;
import com.nextone.contest.impCondition.CheckWheelCrossedLine;
import com.nextone.contest.impCondition.OnOffImp.CheckOverSpeedLimit;
import com.nextone.model.input.yard.YardRankModel;
import com.nextone.model.yardConfigMode.ContestConfig;
import java.util.List;

/**
 *
 * @author Admin
 */
public class VetBanhXe extends AbsContestHasMutiLine {

    private final CheckWheelCrossedLine crossedLine;
    private final CheckWheelCrossedLine crossedPath;

    public VetBanhXe(YardRankModel yardModel, List<ContestConfig> vetBanhXeConfigs, int speedLimit) {
        super(ConstKey.CONTEST_NAME.VET_BANH_XE, 120, vetBanhXeConfigs);
        this.crossedLine = new CheckWheelCrossedLine(5, () -> {
            return getWheelCrosserLineStatus(yardModel.getRoadZs1());
        });
        this.crossedPath = new CheckWheelCrossedLine(5, () -> {
            return getWheelCrosserLineStatus(yardModel.getRoadZs());
        });
        this.conditionBeginHandle.addConditon(new CheckOverSpeedLimit(speedLimit));
        this.conditionIntoHandle.addConditon(this.crossedPath);
    }

    @Override
    protected void init() {
    }

    private boolean into = false;
    private boolean checkPathLineDone = false;

    @Override
    protected boolean loop() {
        if (this.carModel.getDistance() < 2) {
            return false;
        }
        if (this.carModel.isT2()) {
            into = true;
        }
        if (!checkPathLineDone) {
            if (this.carModel.getDistance() >= getContestConfig().getDistanceLine()) {
                if (!into) {
                    addErrorCode(ConstKey.ERR.WHEEL_OUT_OF_PATH);
                }
                checkPathLineDone = true;
            }
        } else {
            this.crossedPath.stop();
            this.crossedLine.start();
            this.crossedLine.checkPassed();
        }
        if (checkPathLineDone && (this.carModel.isT1() || this.carModel.isT2())) {
            this.crossedLine.stop();
            return true;
        }
        return false;
    }

    @Override
    protected boolean isAccept() {
        if (this.carModel.isT1() || this.carModel.isT2()) {
            this.into = false;
            this.checkPathLineDone = false;
            return true;
        }
        return false;
    }

}
