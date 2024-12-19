/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nextone.contest.impCondition;

import com.nextone.contest.AbsCondition;

/**
 *
 * @author Admin
 */
public class ImportantError extends AbsCondition {

    @Override
    protected boolean checkCondition() {
        return !this.hasFail;
    }

    public void setIsImportantError() {
        this.important = true;
        this.hasFail = true;
    }

}
