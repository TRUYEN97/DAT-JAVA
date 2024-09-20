/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.model.modelTest;

/**
 *
 * @author Admin
 */
public class ErrorCodeInfo extends ErrorCode{

    protected String errName;

    public ErrorCodeInfo(String errKey, int errPoint, String errName) {
        super(errKey, errPoint);
        this.errName = errName;
    }

    public String getErrName() {
        return errName;
    }
}
