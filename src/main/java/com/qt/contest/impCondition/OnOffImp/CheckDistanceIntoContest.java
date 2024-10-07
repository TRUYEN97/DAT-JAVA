/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.contest.impCondition.OnOffImp;

import com.qt.input.serial.MCUSerialHandler;
import com.qt.model.input.CarModel;
import com.qt.model.yardConfigMode.ContestConfig;
import java.util.List;

/**
 *
 * @author Admin
 */
public class CheckDistanceIntoContest {

    private final List<ContestConfig> contestConfigs;
    private final CarModel carModel;
    private ContestConfig contestConfig;

    public CheckDistanceIntoContest(List<ContestConfig> contestConfigs) {
        this.contestConfigs = contestConfigs;
        this.carModel = MCUSerialHandler.getInstance().getModel();
    }

    public List<ContestConfig> getContestConfigs() {
        return contestConfigs;
    }

    public ContestConfig getContestConfig() {
        return contestConfig == null ? new ContestConfig() : contestConfig;
    }

    public int check(double oldDistance) {
        double deta = this.carModel.getDistance() - oldDistance;
        ContestConfig config;
        for (int i = 0; i < contestConfigs.size(); i++) {
            if ((config = contestConfigs.get(i)) == null) {
                continue;
            }
            if (deta <= config.getDistanceUpperLimit()
                    && deta >= config.getDistanceLowerLimit()) {
                this.contestConfig = config;
                return i;
            }
        }
        return -1;
    }

}
