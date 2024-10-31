/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.contest.impContest;

import com.qt.contest.AbsContest;

/**
 *
 * @author Admin
 */
public abstract class AbsSaHinhContest extends AbsContest{
    
    public AbsSaHinhContest(String name, String nameSound, boolean sayContestName, boolean soundIn, boolean soundOut, int timeout) {
        super(name, nameSound, sayContestName, soundIn, soundOut, timeout);
    }
    
    @Override
    public void end() {
        super.end(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        this.carModel.setDistance(0);
    }
    
}
