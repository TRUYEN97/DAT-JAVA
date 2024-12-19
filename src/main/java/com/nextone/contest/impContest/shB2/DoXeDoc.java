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
public class DoXeDoc extends AbsContestHasMutiLine {

    private final CheckWheelCrossedLine crossedLine;
    private double oldDistance;

    public DoXeDoc(YardRankModel yardRankModel, List<ContestConfig> contestConfigs, int speedLimit) {
        super(ConstKey.CONTEST_NAME.GHEP_XE_DOC, 120, contestConfigs);
        this.crossedLine = new CheckWheelCrossedLine(5, () -> {
            return getWheelCrosserLineStatus(yardRankModel.getPackings());
        });
        this.conditionBeginHandle.addConditon(new CheckOverSpeedLimit(speedLimit));
        this.conditionIntoHandle.addConditon(crossedLine);
    }

    @Override
    protected void init() {
    }

    @Override
    public void end() {
        super.end(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        this.carModel.setDistance(oldDistance);
    }
    
    

    private boolean success = false;
    private boolean hasIntoPaking = false;

    @Override
    protected boolean loop() {
        if (this.carModel.getStatus() == ConstKey.CAR_ST.BACKWARD) {
            if (!hasIntoPaking && this.carModel.isT2()) {
                hasIntoPaking = true;
            }
            if (!success && this.carModel.isT2()
                    && this.carModel.isT3()) {
                success = true;
                soundPlayer.successSound();
            }
        }
        if (this.carModel.getDistance() > getContestConfig().getDistanceOut() && this.carModel.isT1()) {
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
    protected boolean isAccept() {
        if(this.carModel.isT1() || this.carModel.isT2()) {
            hasIntoPaking = false;
            success = false;
            oldDistance = this.carModel.getDistance();
            return true;
        }
        return false;
    }

}
