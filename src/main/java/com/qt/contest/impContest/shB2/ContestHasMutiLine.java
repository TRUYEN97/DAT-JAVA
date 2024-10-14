/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.contest.impContest.shB2;

import com.qt.common.ConstKey;
import com.qt.contest.AbsContest;
import com.qt.contest.impCondition.OnOffImp.CheckDistanceIntoContest;
import com.qt.model.yardConfigMode.ContestConfig;
import java.util.List;

/**
 *
 * @author Admin
 */
public abstract class ContestHasMutiLine extends AbsContest {

    protected final CheckDistanceIntoContest distanceIntoContest;
    protected int index = -1;

    public ContestHasMutiLine(String name, String nameSound, boolean sayContestName, boolean soundIn, boolean soundOut, int timeout, List<ContestConfig> contestConfigs) {
        super(name, nameSound, sayContestName, soundIn, soundOut, timeout);
        this.distanceIntoContest = new CheckDistanceIntoContest(contestConfigs);
    }
    

    protected boolean getWheelCrosserLineStatus(List<Boolean> status) {
        if (status.size() <= index || index == -1) {
            return false;
        }
        return status.get(index);
    }

    protected boolean checkIntoContest(double oldDistance) {
        this.index = this.distanceIntoContest.check(oldDistance);
        if (this.index == -1) {
            addErrorCode(ConstKey.ERR.WRONG_LANE);
            this.importantError.setIsImportantError();
            return false;
        } else {
            return true;
        }
    }

}
