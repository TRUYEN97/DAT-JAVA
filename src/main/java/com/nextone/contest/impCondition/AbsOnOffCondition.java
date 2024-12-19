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
public abstract class AbsOnOffCondition extends AbsCondition{
    protected boolean reCheck = true;

    @Override
    public boolean checkCondition() {
        if (signal()) {
            if (reCheck) {
                reCheck = false;
                action();
                return false;
            }
        }else if(!reCheck){
            reCheck = true;
        }
        return true;
    }

    protected abstract boolean signal();
    protected abstract void action();
    
    
}
