/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.model.config;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Admin
 */
@Getter
@Setter
public class ChangeIdModel {

    private String name;
    private int tr = 0;
    private int dv = 0;
    private int ch = 0;

    public int getValue() {
        return dv + ch * 10 + tr * 100;
    }

    public String getStringValue() {
        StringBuilder builder = new StringBuilder();
        if (tr > 0) {
            builder.append(String.valueOf(tr));
            builder.append(String.valueOf(ch));
        } else if (ch > 0) {
            builder.append(String.valueOf(ch));
        }
        builder.append(String.valueOf(dv));
        return builder.toString();
    }

    public int trUp() {
        return tr = (tr >= 9 ? 0 : tr + 1);
    }

    public int dvUp() {
        return dv = (dv >= 9 ? 0 : dv + 1);
    }

    public int chUp() {
        return ch = (ch >= 9 ? 0 : ch + 1);
    }
}
