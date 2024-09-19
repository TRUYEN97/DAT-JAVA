/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.contest.impCondition;

import com.qt.contest.AbsCondition;
import com.qt.controller.ProcessModelHandle;

/**
 *
 * @author Admin
 */
public class ContainContestChecker extends AbsCondition {

    private final ProcessModelHandle modelHandle;
    private final String name;
    private final boolean checkContain;

    public ContainContestChecker(String name, boolean checkIsContain, ProcessModelHandle modelHandle) {
        this.name = name;
        this.modelHandle = modelHandle;
        this.checkContain = checkIsContain;
        this.important = true;
    }

    @Override
    public boolean checkPassed() {
        return modelHandle.containContestClass(name) == checkContain;
    }

}
