/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.contest.impContest;

import com.qt.common.ConstKey;
import com.qt.common.Util;
import com.qt.contest.AbsContest;

/**
 *
 * @author Admin
 */
public class GiamToc extends AbsContest{

    public GiamToc() {
        this(ConstKey.CT_NAME.GIAM_TOC);
    }
    
    public GiamToc(String name) {
        super( name, true);
    }

    @Override
    public boolean loop() {
        System.out.println("tg");
        Util.delay(6000);
        return true;
    }

    @Override
    protected boolean isIntoContest() {
        Util.delay(1000);
        return true;
    }
    
}
