/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.model.modelTest;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Admin
 */
@Getter
@Setter
public class Errorcode {

    private String name;
    private int score;

    public Errorcode() {
    }

    public Errorcode(String name, int score) {
        this.name = name;
        this.score = score;
    }
}
