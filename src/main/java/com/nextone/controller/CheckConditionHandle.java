/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nextone.controller;

import com.nextone.contest.AbsCondition;
import com.nextone.model.modelTest.contest.ContestDataModel;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class CheckConditionHandle {

    private final List<AbsCondition> conditions;
    private final ContestDataModel contestDataModel;

    public CheckConditionHandle() {
        this(null);
    }

    public CheckConditionHandle(ContestDataModel contestDataModel) {
        this.conditions = new ArrayList<>();
        this.contestDataModel = contestDataModel;
    }

    public void addConditon(AbsCondition condition) {
        if (condition == null) {
            return;
        }
        if (this.contestDataModel != null) {
            condition.setContestDataModel(contestDataModel);
        }
        this.conditions.add(condition);
    }

    public void start() {
        for (AbsCondition condition : conditions) {
            if (condition != null) {
                condition.start();
            }
        }
    }

    public void stop() {
        for (AbsCondition condition : conditions) {
            if (condition != null) {
                condition.stop();
            }
        }
    }

    public boolean isTestCondisionsFailed() {
        for (AbsCondition condition : conditions) {
            if (condition.isTestCondisionsFailed()) {
                return true;
            }
        }
        return false;
    }

    public void clear() {
        this.conditions.clear();
    }
}
