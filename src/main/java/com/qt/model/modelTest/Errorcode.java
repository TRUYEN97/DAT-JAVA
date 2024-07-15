/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.model.modelTest;

import lombok.Getter;

/**
 *
 * @author Admin
 */
@Getter
public class Errorcode {

    private final String name;
    private final int score;

    public Errorcode(String name, int score) {
        this.name = name;
        this.score = score;
    }
}
