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
public class DuongS extends AbsContestHasMutiLine {

    private final CheckWheelCrossedLine crossedLine;

    public DuongS(YardRankModel contestConfig, List<ContestConfig> contestConfigs, int speedLimit) {
        super(ConstKey.CONTEST_NAME.CHU_S, 120, contestConfigs);
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
        return this.carModel.getDistance() >= getContestConfig().getDistanceOut()
                && (this.carModel.isT1() || this.carModel.isT2());
    }

    @Override
    protected boolean isAccept() {
        return this.carModel.isT1() || this.carModel.isT2() || this.carModel.isT3();
    }

}
