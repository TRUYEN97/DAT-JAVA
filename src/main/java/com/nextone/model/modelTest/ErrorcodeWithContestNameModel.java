/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nextone.model.modelTest;

import lombok.Getter;

/**
 *
 * @author Admin
 */
@Getter
public class ErrorcodeWithContestNameModel extends Errorcode {

    private final String contestName;

    public ErrorcodeWithContestNameModel(Errorcode errorcode, String contestName) {
        super(errorcode.getErrKey(), errorcode.getErrPoint(), errorcode.getErrName());
        this.contestName = contestName == null ? "" : contestName;
    }

    public ErrorcodeWithContestNameModel(String name, int score, String contestName, String des) {
        super(name, score, des);
        this.contestName = contestName;
    }

}
