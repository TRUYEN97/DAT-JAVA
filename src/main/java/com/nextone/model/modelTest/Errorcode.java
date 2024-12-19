/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nextone.model.modelTest;

/**
 *
 * @author Admin
 */

public class Errorcode {
    protected String errKey;
    protected int errPoint;
    protected String errName;

    public Errorcode() {
    }

    public void setErrKey(String errKey) {
        this.errKey = errKey;
    }

    public void setErrPoint(int errPoint) {
        this.errPoint = errPoint;
    }

    public String getErrName() {
        return errName;
    }
    

    public Errorcode(String errKey, int errPoint, String errName) {
        this.errKey = errKey;
        this.errPoint = errPoint;
        this.errName = errName;
    }

    public String getErrKey() {
        return errKey;
    }

    public int getErrPoint() {
        return errPoint;
    }
    
    
}
