/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.contest.impCondition;

import com.qt.contest.AbsCondition;

/**
 *
 * @author Admin
 */
public class ImportantError extends AbsCondition {

    @Override
    protected boolean checkCondition() {
        return true;
    }

    public void setIsImportantError() {
        this.hasFail = true;
    }

}
