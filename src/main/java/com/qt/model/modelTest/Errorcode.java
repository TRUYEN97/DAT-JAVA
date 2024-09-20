/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.model.modelTest;

/**
 *
 * @author Admin
 */

public class ErrorCode {
    protected final String errKey;
    protected final int errPoint;

    public ErrorCode(String errKey, int errPoint) {
        this.errKey = errKey;
        this.errPoint = errPoint;
    }

    public String getErrKey() {
        return errKey;
    }

    public int getErrPoint() {
        return errPoint;
    }
    
    
}
