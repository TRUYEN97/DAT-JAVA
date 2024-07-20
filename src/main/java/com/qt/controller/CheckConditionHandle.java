/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.controller;

import com.qt.contest.AbsCondition;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class CheckConditionHandle {

    private final List<AbsCondition> conditions;

    public CheckConditionHandle() {
        this.conditions = new ArrayList<>();
    }
    
    public void addConditon(AbsCondition condition){
        if (condition == null) {
            return ;
        }
        this.conditions.add(condition);
    }

    public boolean checkTestCondisions() {
        for (AbsCondition condition : conditions) {
            if (!condition.checkPassed() && condition.isImportant()) {
                return false;
            }
        }
        return true;
    }
}
